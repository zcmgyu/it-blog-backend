package com.aptech.itblog.service;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.model.PostByCategory;
import com.aptech.itblog.repository.CategoryRepository;
import com.aptech.itblog.repository.PostRepository;
import com.aptech.itblog.repository.PostRepositoryCustom;
import com.aptech.itblog.repository.TrendRepsitoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

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
        // Set author id
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Set author
        post.setAuthor(user);

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
    public Page<Post> getPagePostByAuthorId(String authorId, Pageable pageable) {
        return postRepository.findAllByAuthorId(authorId, pageable);
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

        // Set raw content
        currentPost.setRawContent(post.getRawContent());

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


    @Override
    public List<PostByCategory> getTop4TrendPostByCategory() {
        return postRepositoryCustom.getTrendPostGroupByCategory();
    }

    @Override
    public List<PostByCategory> getTop4LatestPostByCategory() {
        return postRepositoryCustom.getLatestPostGroupByCategory();
    }

}
