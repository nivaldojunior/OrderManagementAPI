package br.com.OrderManagementAPI.repository;

import br.com.OrderManagementAPI.model.Item;
import br.com.OrderManagementAPI.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<StockMovement, Long> {

    Optional<StockMovement> findByItem(Item item);

}
