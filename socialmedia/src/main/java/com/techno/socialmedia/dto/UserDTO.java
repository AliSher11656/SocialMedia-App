package com.techno.socialmedia.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dateOfBirth;
}
