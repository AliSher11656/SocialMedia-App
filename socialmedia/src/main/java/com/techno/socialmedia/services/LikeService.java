package com.techno.socialmedia.services;

import com.techno.socialmedia.dto.LikeDTO;
import com.techno.socialmedia.dto.PostDTO;
import com.techno.socialmedia.dto.UserDTO;
import com.techno.socialmedia.entities.Like;
import com.techno.socialmedia.entities.Post;
import com.techno.socialmedia.entities.User;
import com.techno.socialmedia.repositories.LikeRepository;
import com.techno.socialmedia.repositories.PostRepository;
import com.techno.socialmedia.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, ModelMapper modelMapper, UserRepository userRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public LikeDTO createLike(Long postId, Long userId) {
        Like like = new Like();
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalPost.isPresent() && optionalUser.isPresent()) {
            Post post = optionalPost.get();
            User user = optionalUser.get();

            like.setPost(post);
            like.setUser(user);

            Like createdLike = likeRepository.save(like);
            return modelMapper.map(createdLike, LikeDTO.class);
        } else {
            throw new IllegalArgumentException("Invalid postId or userId");
        }
    }

    public void deleteLike(Long likeId) {
        if (!likeRepository.existsById(likeId)) {
            throw new EntityNotFoundException("Like not found");
        }
        likeRepository.deleteById(likeId);
    }
}

