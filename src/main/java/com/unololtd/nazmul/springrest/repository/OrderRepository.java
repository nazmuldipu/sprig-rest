package com.unololtd.nazmul.springrest.repository;

import com.unololtd.nazmul.springrest.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
