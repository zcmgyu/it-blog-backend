package com.aptech.itblog.converter;

import com.aptech.itblog.collection.Category;
import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.model.PostByCategory;
import com.aptech.itblog.model.PostByCategoryDTO;
import com.aptech.itblog.model.PostDTO;
import com.aptech.itblog.model.UserDTO;
import com.aptech.itblog.repository.CategoryRepository;
import com.aptech.itblog.repository.UserRepository;
import com.aptech.itblog.utils.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public PostDTO convertToDto(Post post) {
        PostDTO postDto = modelMapper.map(post, PostDTO.class);
        postDto.setTransliterated(StringUtils.convertToHyphenCase(post.getTitle()));
        return postDto;
    }

    public PostByCategoryDTO convertToPostByCategoryDTO(PostByCategory postByCategory) {
        PostByCategoryDTO postByCategoryDTO = modelMapper.map(postByCategory, PostByCategoryDTO.class);
        Category category = categoryRepository.findOne(postByCategory.get_id());
        // Reset properties
        postByCategoryDTO.setId(category.getId());
        postByCategoryDTO.setCategory(category.getName());
        // Set top 4
        List<PostDTO> top4 = postByCategory.getTop4().stream().map(post -> convertToDto(post)).collect(Collectors.toList());
        postByCategoryDTO.setTop4(top4);
        return postByCategoryDTO;
    }
}
