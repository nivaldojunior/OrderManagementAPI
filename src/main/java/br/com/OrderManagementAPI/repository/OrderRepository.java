package br.com.OrderManagementAPI.repository;

import br.com.OrderManagementAPI.model.Item;
import br.com.OrderManagementAPI.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByItemAndCompleteIsFalseOrderByCreationDate(Item item);
}
