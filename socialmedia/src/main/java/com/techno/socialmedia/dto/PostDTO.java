package com.techno.socialmedia.dto;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
public class PostDTO {
    private Long id;
    private String content;
    @UpdateTimestamp
    private Timestamp createdAt;
    private UserDTO user;
}
