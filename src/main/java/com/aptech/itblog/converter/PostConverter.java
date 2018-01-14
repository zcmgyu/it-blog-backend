package com.aptech.itblog.converter;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.model.PostDTO;
import com.aptech.itblog.model.UserDTO;
import com.aptech.itblog.repository.UserRepository;
import com.aptech.itblog.utils.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserRepository userRepository;

    public PostDTO convertToDto(Post post) {
        PostDTO postDto = modelMapper.map(post, PostDTO.class);
        postDto.setTransliterated(StringUtils.convertToHyphenCase(post.getTitle()));
        // Set user
        User user = userRepository.findById(post.getAuthorId());
        UserDTO userDTO = userConverter.convertToDto(user);
        postDto.setUser(userDTO);
        return postDto;
    }
}
