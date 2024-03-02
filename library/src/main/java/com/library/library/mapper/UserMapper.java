package com.library.library.mapper;

import com.library.library.dto.UserDto;
import com.library.library.entity.User;

public class UserMapper {

    public static UserDto mapTOUserDto(User user){
        UserDto userDto = new UserDto(
          user.getUserId(),
          user.getEmail(),
          user.getName()
        );
        return userDto;
    }
}
