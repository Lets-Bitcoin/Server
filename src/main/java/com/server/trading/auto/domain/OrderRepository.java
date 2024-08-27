package com.server.trading.auto.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(
            "SELECT o " +
            "FROM Order o " +
            "ORDER BY o.id " +
            "LIMIT 1")
    Optional<Order> findLastOrder();
}
