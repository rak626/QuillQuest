package com.rakesh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rakesh.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
