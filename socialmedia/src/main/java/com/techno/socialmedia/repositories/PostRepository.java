package com.techno.socialmedia.repositories;

import com.techno.socialmedia.entities.Post;
import com.techno.socialmedia.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByUser(User user);

    List<Post> findLikedPostsByUserId(Long userId);
}
