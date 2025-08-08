package com.budgetbootstrapper.authorization.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String telephone;

    private String email;

    private String jwtToken;

}