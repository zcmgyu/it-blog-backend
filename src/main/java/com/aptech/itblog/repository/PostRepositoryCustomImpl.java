package com.aptech.itblog.repository;

import com.aptech.itblog.model.PostByCategory;
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

    public List<PostByCategory> getLatestPostGroupByCategory() {
        Aggregation aggregation = newAggregation(
                sort(Sort.Direction.DESC, "categoryId").and(Sort.Direction.DESC, "createAt"),
                group("categoryId")
                        .push(Aggregation.ROOT).as("posts"),
                project().and("posts").slice(4).as("top4")
        );


        // Convert the aggregation result into a List
        // BasicDBObject
        AggregationResults<PostByCategory> groupResults
                = mongoTemplate.aggregate(aggregation, "Post", PostByCategory.class);
        List<PostByCategory> result = groupResults.getMappedResults();

        return result;
    }

    @Override
    public List<PostByCategory> getTrendPostGroupByCategory() {
        Aggregation aggregation = newAggregation(
                sort(Sort.Direction.DESC, "post.categoryId").and(Sort.Direction.DESC, "views"),
                group("post.categoryId")
                        .push(Aggregation.ROOT.concat(".post")).as("posts"),
                project().and("posts").slice(4).as("top4")
        );


        // Convert the aggregation result into a List
        // BasicDBObject
        AggregationResults<PostByCategory> groupResults
                = mongoTemplate.aggregate(aggregation, "Trend", PostByCategory.class);
        List<PostByCategory> result = groupResults.getMappedResults();

        return result;
    }


}
