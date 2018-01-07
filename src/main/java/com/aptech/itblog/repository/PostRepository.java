package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String>
//        QueryDslPredicateExecutor<Post>
{

//    @Query("{ 'name' : ?0 }")
//    @Query("{ 'age' : { $gt: ?0, $lt: ?1 } }")
//    List<Post> findByAuthorIdLike(String idAuthor);
//
//    @Override
//    default public void customize(
//            QuerydslBindings bindings, Post root) {
//        bindings.bind(String.class)
//                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
//        bindings.excluding(root.email);
//    }
}
