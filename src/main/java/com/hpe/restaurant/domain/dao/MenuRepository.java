package com.hpe.restaurant.domain.dao;

import com.hpe.restaurant.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
