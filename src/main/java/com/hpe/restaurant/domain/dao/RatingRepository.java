package com.hpe.restaurant.domain.dao;

import com.hpe.restaurant.domain.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
