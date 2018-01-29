package com.aptech.itblog.service;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.Trend;
import com.aptech.itblog.repository.PostRepository;
import com.aptech.itblog.repository.TrendRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class GAService {
    private static final Logger log = LoggerFactory.getLogger(GAService.class);

    private static final String APPLICATION_NAME = "IT Blog - Analytics Reporting";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String KEY_FILE_LOCATION = "/client_secrets.json";
    private static final String VIEW_ID = "168014843";

    @Autowired
    private TrendRepository trendRepository;

    @Autowired
    private PostRepository postRepository;


    AnalyticsReporting service;

    public GAService() throws GeneralSecurityException, IOException {
        service = initializeAnalyticsReporting();
    }

    @Scheduled(fixedRate = 1200000)
    public void schedule() throws IOException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        log.info("The time is now {}", dateFormat.format(new Date()));

        GetReportsResponse response = getReport(service);
        // Store into DB
        storeIntoDB(response);
    }

//    public static void main(String[] args) {
//        try {
//        AnalyticsReporting service = initializeAnalyticsReporting();
//        GetReportsResponse response = getReport(service);
//            printResponse(response);
//            GAService.class.newInstance().storeIntoDB(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    GetReportsResponse response;


    /**
     * Initializes an Analytics Reporting API V4 service object.
     *
     * @return An authorized Analytics Reporting API V4 service object.
     * @throws IOException
     * @throws GeneralSecurityException
     */
    static private AnalyticsReporting initializeAnalyticsReporting() throws GeneralSecurityException, IOException {

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = GoogleCredential
                .fromStream(GAService.class.getResourceAsStream(KEY_FILE_LOCATION))
                .createScoped(AnalyticsReportingScopes.all());

        // Construct the Analytics Reporting service object.
        return new AnalyticsReporting.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME).build();
    }

    /**
     * Queries the Analytics Reporting API V4.
     *
     * @param service An authorized Analytics Reporting API V4 service object.
     * @return GetReportResponse The Analytics Reporting API V4 response.
     * @throws IOException
     */
    private static GetReportsResponse getReport(AnalyticsReporting service) throws IOException {
        // Create the DateRange object.
        DateRange dateRange = new DateRange();
        dateRange.setStartDate("7DaysAgo");
        dateRange.setEndDate("today");

        // Create the Metrics object.
        Metric sessions = new Metric()
                .setExpression("ga:sessions")
                .setAlias("sessions");

        // Create the Metrics pageviews
        Metric pageviews = new Metric()
                .setExpression("ga:pageviews")
                .setAlias("pageviews");


        Dimension pageTitle = new Dimension().setName("ga:pageTitle");
        Dimension pagePath = new Dimension().setName("ga:pagePath");
        Dimension date = new Dimension().setName("ga:date");

        // Create the ReportRequest object.
        ReportRequest request = new ReportRequest()
                .setViewId(VIEW_ID)
                .setDateRanges(Arrays.asList(dateRange))
                .setMetrics(Arrays.asList(sessions, pageviews))
                .setDimensions(Arrays.asList(pageTitle, pagePath, date));

        ArrayList<ReportRequest> requests = new ArrayList<>();
        requests.add(request);

        // Create the GetReportsRequest object.
        GetReportsRequest getReport = new GetReportsRequest()
                .setReportRequests(requests);


        // Call the batchGet method.
        GetReportsResponse response = service.reports().batchGet(getReport).execute();

        // Return the response.
        return response;
    }


    /**
     * Parses and stores the Analytics Reporting API V4 response.
     *
     * @param response An Analytics Reporting API V4 response.
     */
    private void storeIntoDB(GetReportsResponse response) throws ParseException {
        trendRepository.deleteAll();
        for (Report report : response.getReports()) {
            ColumnHeader header = report.getColumnHeader();
            List<String> dimensionHeaders = header.getDimensions();
            List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
            List<ReportRow> rows = report.getData().getRows();

            if (rows == null) {
                System.out.println("No data found for " + VIEW_ID);
                return;
            }

            ArrayList<Trend> trends = new ArrayList();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

            for (ReportRow row : rows) {
                List<String> dimensions = row.getDimensions();
                List<DateRangeValues> metrics = row.getMetrics();
                // Init trend
                String title = null;
                Post post = null;
                Long views = null;
                Date activeDate = null;

                for (int i = 0; i < dimensionHeaders.size() && i < dimensions.size(); i++) {
                    System.out.println(dimensionHeaders.get(i) + ": " + dimensions.get(i));

                    String dimensionValue = dimensions.get(i);
                    switch (dimensionHeaders.get(i)) {
                        case "ga:pageTitle":
                            title = dimensionValue;
                            break;
                        case "ga:pagePath":
                            String postId = getPostIdFromUri(dimensionValue);
                            post = postRepository.findOne(postId);
                            break;
                        case "ga:date":
                            activeDate = formatter.parse(dimensionValue);
                            break;
                        default:
                            break;
                    }
                }

                for (int j = 0; j < metrics.size(); j++) {
                    System.out.print("Date Range (" + j + "): ");
                    DateRangeValues values = metrics.get(j);
                    for (int k = 0; k < values.getValues().size() && k < metricHeaders.size(); k++) {
                        switch (metricHeaders.get(k).getName()) {
                            case "pageviews":
                                views = Long.parseLong(values.getValues().get(k));
                                break;
                            default:
                                break;
                        }
                        System.out.println(metricHeaders.get(k).getName() + ": " + values.getValues().get(k));
                    }
                }

                if (title == null || post == null || views == null || activeDate == null) {
                    continue;
                }
                // Add trend to list
                Trend trend = trendRepository.findByPostId(post.getId());
                if (trend == null) {
                    trend = new Trend(title, post, views, post.getCategoryId(), activeDate);
                } else {
                    trend.setTitle(title);
                    long previousViews = trend.getViews();
                    trend.setViews(previousViews + views);
                    trend.setActiveDate(activeDate);
                    trend.setPost(post);
                    trend.setCategoryId(post.getCategoryId());
                }
                trendRepository.save(trend);
                System.out.println("====================================================================================");
                System.out.println("trend_id: " + trend.getId());
                System.out.println("post: " + trend.getPost().getId());
                System.out.println("====================================================================================");

            }
            // Save list trend records to DB
//            trendRepository.save(trends);
        }
    }


    private String getPostIdFromUri(String uri) {
        String regex = "\\/posts\\/(.*?)\\/";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(uri);
        if(m.find()){
            return m.group(1);
        }
        return null;
    }

    /**
     * Parses and prints the Analytics Reporting API V4 response.
     *
     * @param response An Analytics Reporting API V4 response.
     */
//    private static void printResponse(GetReportsResponse response) throws ParseException {
//        for (Report report : response.getReports()) {
//            ColumnHeader header = report.getColumnHeader();
//            List<String> dimensionHeaders = header.getDimensions();
//            List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
//            List<ReportRow> rows = report.getData().getRows();
//
//            if (rows == null) {
//                System.out.println("No data found for " + VIEW_ID);
//                return;
//            }
//
//            for (ReportRow row : rows) {
//                List<String> dimensions = row.getDimensions();
//                List<DateRangeValues> metrics = row.getMetrics();
//                // Init trend
//                Trend trend = new Trend();
//
//
//                for (int i = 0; i < dimensionHeaders.size() && i < dimensions.size(); i++) {
//                    System.out.println(dimensionHeaders.get(i) + ": " + dimensions.get(i));
//
//                    String dimensionValue = dimensions.get(i);
//                    switch (dimensionHeaders.get(i)) {
//                        case "ga:pageTitle":
//                            trend.setTitle(dimensionValue);
//                            break;
//                        case "ga:pagePath":
//                            trend.setPath(dimensionValue);
//                            break;
//                        case "ga:date":
//                            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
//                            System.out.println("DATE-TIME: " + dimensionValue);
//                            trend.setActivedDate(formatter.parse(dimensionValue));
//                            break;
//                        default:
//                            break;
//                    }
//                }
//
//                for (int j = 0; j < metrics.size(); j++) {
//                    System.out.print("Date Range (" + j + "): ");
//                    DateRangeValues values = metrics.get(j);
//                    for (int k = 0; k < values.getValues().size() && k < metricHeaders.size(); k++) {
//                        switch (metricHeaders.get(k).getName()) {
//                            case "pageviews":
//                                trend.setViews(Long.parseLong(values.getValues().get(k)));
//                            default:
//                        }
//                        System.out.println(metricHeaders.get(k).getName() + ": " + values.getValues().get(k));
//                    }
//                }
//                System.out.println("====================================================================================");
//                System.out.println("====================================================================================");
//            }
//        }
//    }
}
