package com.aptech.itblog.repository;

import com.aptech.itblog.model.PostByCategory;
import com.aptech.itblog.model.TrendViews;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    @Autowired
    MongoTemplate mongoTemplate;

    public List<PostByCategory> getLatestPostGroupByCategory() {
        Aggregation aggregation = newAggregation(
                sort(Sort.Direction.DESC, "createdAt").and(Sort.Direction.ASC, "categoryId"),
                group("categoryId")
                        .push(Aggregation.ROOT).as("docs"),
                project().and("docs").slice(4).as("top_4")
        );

        //Convert the aggregation result into a List
        // BasicDBObject
        AggregationResults<PostByCategory> groupResults
                = mongoTemplate.aggregate(aggregation, "Post", PostByCategory.class);
        List<PostByCategory> result = groupResults.getMappedResults();
//                .stream()
//                .map(item -> (LinkedHashMap) item).collect(Collectors.toList());
//        List<Map> result = groupResults.getMappedResults();
        return result;
    }
}
