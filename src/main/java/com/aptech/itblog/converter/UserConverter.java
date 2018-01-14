package com.aptech.itblog.converter;

import com.aptech.itblog.collection.User;
import com.aptech.itblog.model.PostDTO;
import com.aptech.itblog.model.UserDTO;
import com.aptech.itblog.utils.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserConverter {
    @Autowired
    private ModelMapper modelMapper;

    public UserDTO convertToDto(User user) {
        UserDTO userDto = modelMapper.map(user, UserDTO.class);
        return userDto;
    }
}
