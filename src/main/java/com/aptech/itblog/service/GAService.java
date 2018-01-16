package com.aptech.itblog.service;

import com.aptech.itblog.collection.Trend;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class GAService {
    private static final Logger log = LoggerFactory.getLogger(GAService.class);

    private static final String APPLICATION_NAME = "IT Blog - Analytics Reporting";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String KEY_FILE_LOCATION = "/client_secrets.json";
    private static final String VIEW_ID = "166693231";

    @Autowired
    private TrendRepository trendRepository;

//    public GAService() throws GeneralSecurityException, IOException {
//        AnalyticsReporting service = initializeAnalyticsReporting();
//        GetReportsResponse response = getReport(service);
//        printResponse(response);
//    }

    public static void main(String[] args) {
        try {
            AnalyticsReporting service = initializeAnalyticsReporting();
            GetReportsResponse response = getReport(service);
            printResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 20000)
    public void reportCurrentTime() throws GeneralSecurityException, IOException {
        AnalyticsReporting service = initializeAnalyticsReporting();
        GetReportsResponse response = getReport(service);


        log.info("The time is now {}", dateFormat.format(new Date()));
    }


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

        // Create the ReportRequest object.
        ReportRequest request = new ReportRequest()
                .setViewId(VIEW_ID)
                .setDateRanges(Arrays.asList(dateRange))
                .setMetrics(Arrays.asList(sessions, pageviews))
                .setDimensions(Arrays.asList(pageTitle, pagePath));

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
    private static void storeIntoDB(GetReportsResponse response) {
        for (Report report : response.getReports()) {
            ColumnHeader header = report.getColumnHeader();
            List<String> dimensionHeaders = header.getDimensions();
            List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
            List<ReportRow> rows = report.getData().getRows();

            if (rows == null) {
                System.out.println("No data found for " + VIEW_ID);
                return;
            }

            for (ReportRow row : rows) {
                List<String> dimensions = row.getDimensions();
                List<DateRangeValues> metrics = row.getMetrics();
                // Init trend
                Trend trend = new Trend();


                for (int i = 0; i < dimensionHeaders.size() && i < dimensions.size(); i++) {
                    System.out.println(dimensionHeaders.get(i) + ": " + dimensions.get(i));

                    String dimensionValue = dimensions.get(i);
                    switch (dimensionHeaders.get(i)) {
                        case "ga:pageTitle":
                            trend.setTitle(dimensionValue);
                            break;
                        case "ga:pagePath":
                            trend.setPath(dimensionValue);
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
                                trend.setViews(Long.parseLong(values.getValues().get(k)));
                                break;
                            default:
                                break;
                        }
                        System.out.println(metricHeaders.get(k).getName() + ": " + values.getValues().get(k));
                    }
                }
                System.out.println("====================================================================================");
                System.out.println("====================================================================================");
            }
        }
    }


    /**
     * Parses and prints the Analytics Reporting API V4 response.
     *
     * @param response An Analytics Reporting API V4 response.
     */
    private static void printResponse(GetReportsResponse response) {
        for (Report report : response.getReports()) {
            ColumnHeader header = report.getColumnHeader();
            List<String> dimensionHeaders = header.getDimensions();
            List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
            List<ReportRow> rows = report.getData().getRows();

            if (rows == null) {
                System.out.println("No data found for " + VIEW_ID);
                return;
            }

            for (ReportRow row : rows) {
                List<String> dimensions = row.getDimensions();
                List<DateRangeValues> metrics = row.getMetrics();
                // Init trend
                Trend trend = new Trend();


                for (int i = 0; i < dimensionHeaders.size() && i < dimensions.size(); i++) {
                    System.out.println(dimensionHeaders.get(i) + ": " + dimensions.get(i));

                    String dimensionValue = dimensions.get(i);
                    switch (dimensionHeaders.get(i)) {
                        case "ga:pageTitle":
                            trend.setTitle(dimensionValue);
                        case "ga:pagePath":
                            trend.setPath(dimensionValue);
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
                                trend.setViews(Long.parseLong(values.getValues().get(k)));
                                break;
                            default:
                                break;
                        }
                        System.out.println(metricHeaders.get(k).getName() + ": " + values.getValues().get(k));
                    }
                }
                System.out.println("====================================================================================");
                System.out.println("====================================================================================");
            }
        }
    }
}
