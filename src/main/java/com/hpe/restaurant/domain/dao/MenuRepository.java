package com.hpe.restaurant.domain.dao;

import com.hpe.restaurant.domain.entity.Menu;
import com.hpe.restaurant.domain.entity.MenuId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, MenuId> {
}
