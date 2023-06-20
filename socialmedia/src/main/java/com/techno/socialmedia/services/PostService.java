package com.techno.socialmedia.services;

import com.techno.socialmedia.dto.PostDTO;
import com.techno.socialmedia.entities.Post;
import com.techno.socialmedia.entities.User;
import com.techno.socialmedia.repositories.PostRepository;
import com.techno.socialmedia.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public List<PostDTO> getAllPosts() {
        List<Post> posts = (List<Post>) postRepository.findAll();
        return mapPostsToDTO(posts);
    }
    public List<PostDTO> getLikedPostsByUserId(Long userId)
    {
        List<Post> likedPosts = postRepository.findLikedPostsByUserId(userId);
        List<PostDTO> likedPostDTOs = mapPostsToDTO(likedPosts);
        return likedPostDTOs;
    }

    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        return mapPostToDTO(post);
    }

    public PostDTO createPost(PostDTO postDTO, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setUser(user);
        Post createdPost = postRepository.save(post);
        return modelMapper.map(createdPost, PostDTO.class);
    }

    public PostDTO updatePost(PostDTO postDTO) {
        Post existingPost = postRepository.findById(postDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        existingPost.setContent(postDTO.getContent());

        Post updatedPost = postRepository.save(existingPost);
        return modelMapper.map(updatedPost, PostDTO.class);
    }

    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new EntityNotFoundException("Post not found");
        }
        postRepository.deleteById(postId);
    }

    public List<PostDTO> getPostsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Post> userPosts = postRepository.findByUser(user);
        return mapPostsToDTO(userPosts);
    }

    private List<PostDTO> mapPostsToDTO(List<Post> posts) {
        return posts.stream()
                .map(this::mapPostToDTO)
                .collect(Collectors.toList());
    }

    private PostDTO mapPostToDTO(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }
}
