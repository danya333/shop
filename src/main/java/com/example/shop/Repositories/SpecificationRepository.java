package com.example.shop.Repositories;

import com.example.shop.models.Category;
import com.example.shop.models.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecificationRepository extends JpaRepository<Specification, Long> {

    List<Specification> findAllByCategory(Category category);
}
