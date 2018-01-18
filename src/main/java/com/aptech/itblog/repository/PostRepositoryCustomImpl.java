package com.aptech.itblog.repository;

import com.aptech.itblog.model.TrendViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    @Autowired
    MongoTemplate mongoTemplate;

    public List<Object> getLatestPostGroupByCategory() {
        Aggregation aggregation = newAggregation(
                sort(Sort.Direction.DESC, "createdAt").and(Sort.Direction.ASC, "categoryId"),
                group("categoryId")
                        .push(Aggregation.ROOT).as("docs"),
                project("top_four").


        );

        //Convert the aggregation result into a List
        AggregationResults<TrendViews> groupResults
                = mongoTemplate.aggregate(aggregation, "Trend", TrendViews.class);
        List<TrendViews> result = groupResults.getMappedResults();
        return result;
    }
}
