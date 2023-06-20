package com.techno.socialmedia.utils;

import com.techno.socialmedia.dto.CommentDTO;
import com.techno.socialmedia.dto.LikeDTO;
import com.techno.socialmedia.dto.PostDTO;
import com.techno.socialmedia.dto.UserDTO;
import com.techno.socialmedia.entities.Comment;
import com.techno.socialmedia.entities.Like;
import com.techno.socialmedia.entities.Post;
import com.techno.socialmedia.entities.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configure model mapping options
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        // Create a type map for User to UserDTO mapping
        TypeMap<User, UserDTO> userToUserDtoTypeMap = modelMapper.createTypeMap(User.class, UserDTO.class);

        // Map User fields to UserDTO fields
        userToUserDtoTypeMap.addMapping(User::getId, UserDTO::setId);
        userToUserDtoTypeMap.addMapping(User::getUsername, UserDTO::setUsername);
        userToUserDtoTypeMap.addMapping(User::getEmail, UserDTO::setEmail);
        userToUserDtoTypeMap.addMapping(User::getFirstName, UserDTO::setFirstName);
        userToUserDtoTypeMap.addMapping(User::getLastName, UserDTO::setLastName);

        // Create a type map for Post to PostDTO mapping
        TypeMap<Post, PostDTO> postToPostDtoTypeMap = modelMapper.createTypeMap(Post.class, PostDTO.class);

        // Map Post fields to PostDTO fields
        postToPostDtoTypeMap.addMapping(Post::getId, PostDTO::setId);
        postToPostDtoTypeMap.addMapping(Post::getContent, PostDTO::setContent);

        // Create a type map for Comment to CommentDTO mapping
        TypeMap<Comment, CommentDTO> commentToCommentDtoTypeMap = modelMapper.createTypeMap(Comment.class, CommentDTO.class);

        // Map Comment fields to CommentDTO fields
        commentToCommentDtoTypeMap.addMapping(Comment::getId, CommentDTO::setId);
        commentToCommentDtoTypeMap.addMapping(Comment::getContent, CommentDTO::setContent);

        // Create a type map for Like to LikeDTO mapping
        TypeMap<Like, LikeDTO> likeToLikeDtoTypeMap = modelMapper.createTypeMap(Like.class, LikeDTO.class);

        // Map Like fields to LikeDTO fields
        likeToLikeDtoTypeMap.addMapping(Like::getId, LikeDTO::setId);

        return modelMapper;
    }
}
