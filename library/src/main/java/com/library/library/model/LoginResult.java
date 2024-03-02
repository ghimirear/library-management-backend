package com.library.library.model;

import com.library.library.entity.User;
import lombok.Data;

@Data
public class LoginResult {
    private User user;
    private String errorMessage;

}
