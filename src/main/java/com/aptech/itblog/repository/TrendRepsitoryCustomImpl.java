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
public class TrendRepsitoryCustomImpl implements TrendRepsitoryCustom {
    @Autowired
    MongoTemplate mongoTemplate;

    public List<TrendViews> getTopTrend() {
        Aggregation aggregation = newAggregation(
                group("postId")
                        .sum("views").as("views"),
                sort(Sort.Direction.DESC, "views")

        );

        //Convert the aggregation result into a List
        AggregationResults<TrendViews> groupResults
                = mongoTemplate.aggregate(aggregation, "Trend", TrendViews.class);
        List<TrendViews> result = groupResults.getMappedResults();
        return result;
    }
}
