package com.betsegaw.tacos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.betsegaw.tacos.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
