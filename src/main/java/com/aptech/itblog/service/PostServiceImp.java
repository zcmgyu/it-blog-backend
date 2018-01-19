package com.aptech.itblog.service;

import com.aptech.itblog.collection.Category;
import com.aptech.itblog.collection.Post;
import com.aptech.itblog.model.PostByCategory;
import com.aptech.itblog.model.TrendViews;
import com.aptech.itblog.repository.CategoryRepository;
import com.aptech.itblog.repository.PostRepository;
import com.aptech.itblog.repository.PostRepositoryCustom;
import com.aptech.itblog.repository.TrendRepsitoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImp implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TrendRepsitoryCustom trendRepsitoryCustom;

    @Autowired
    PostRepositoryCustom postRepositoryCustom;

    @Override
    public Post createPost(Post post) {
        LinkedHashMap<String, ArrayList> content = (LinkedHashMap) post.getContent();
        ArrayList<LinkedHashMap<String, String>> blocks = content.get("blocks");

        // Create title
        LinkedHashMap<String, String> firstBlock = blocks.get(0);
        String title = firstBlock.get("text");
        post.setTitle(title);

        // Set first image
        String image = getFirstImage(blocks);
        post.setImage(image);

        // Set public
        post.setPublicPost(true);

        // Set created date
        post.setCreateAt(new Date());

        return postRepository.save(post);
    }

    @Override
    public Page<Post> getPagePost(Pageable pageable) {
        return postRepository.findAll(pageable);
    }


    @Override
    public Post getPost(String postId) {
        return postRepository.findOne(postId);
    }

    @Override
    public Post updatePost(String id, Post post) {
        Post currentPost = postRepository.findOne(id);
        LinkedHashMap<String, ArrayList> content = (LinkedHashMap) post.getContent();
        ArrayList<LinkedHashMap<String, String>> blocks = content.get("blocks");

        // Create title
        LinkedHashMap<String, String> firstBlock = blocks.get(0);
        String title = firstBlock.get("text");
        currentPost.setTitle(title);

        // Set content
        currentPost.setContent(post.getContent());

        // Set tags
        currentPost.setTags(post.getTags());

        // Set category
        currentPost.setCategoryId(post.getCategoryId());

        // Set short content
        currentPost.setShortContent(post.getShortContent());

        // Get first image
        String image = getFirstImage(blocks);
        currentPost.setImage(image);

        // Update in modified
        currentPost.setModifiedAt(new Date());

        return postRepository.save(currentPost);
    }

    @Override
    public boolean deletePost(String postId) {

        postRepository.delete(postId);
        return true;
    }

    private String getFirstImage(ArrayList<LinkedHashMap<String, String>> blocks) {
        String image = null;
        for (LinkedHashMap block : blocks) {
            String type = (String) block.get("type");
            if (type.equals("image")) {
                LinkedHashMap data = (LinkedHashMap<String, LinkedHashMap>) block.get("data");
                image = (String) data.get("url");
                break;
            }
        }

        return image;
    }

//    @Override
//    public LinkedHashMap<String, List<Post>> getTop4LatestPostByCategory() {
//        List<Category> categoryList = categoryRepository.findAll();
//
//        LinkedHashMap<String, List<Post>> categoryMap = new LinkedHashMap();

//        for (Category category: categoryList) {
//            List<Post> postList = postRepository.findTop4ByCategoryIdAndPublicPostInOrderByCreateAtDesc(category.getId(), true);
//            categoryMap.put(category.getName(), postList);
//        }
//        postRepositoryCustom.getLatestPostGroupByCategory();
//
//        return categoryMap;
//    }


    @Override
    public List<PostByCategory> getTop4LatestPostByCategory() {
        return postRepositoryCustom.getLatestPostGroupByCategory();
    }

    @Override
    public LinkedHashMap<String, List<Post>> getTop4TrendingPostByCategory() {
        // Get list category
        List<Category> categoryList = categoryRepository.findAll();

        // Get list trend-views
        List<TrendViews> trendViews = trendRepsitoryCustom.getTopTrend();

        // Get list ids
        List<String> ids = trendViews.stream().map(TrendViews::getId).collect(Collectors.toList());

        LinkedHashMap<String, List<Post>> categoryPostMap = new LinkedHashMap();

        for (Category category: categoryList) {
            List<Post> postList = postRepository.findTop4ByCategoryIdAndPublicPostInOrderByCreateAtDesc(category.getId(), true);
            categoryPostMap.put(category.getName(), postList);
        }

        return categoryPostMap;
    }
}
