package com.rakesh.blog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rakesh.blog.entities.Category;
import com.rakesh.blog.entities.Post;
import com.rakesh.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	Page<Post> findByUser(User user, Pageable pageable);

	Page<Post> findByCategory(Category category, Pageable pageable);

	List<Post> findByTitleContaining(String title);
}
