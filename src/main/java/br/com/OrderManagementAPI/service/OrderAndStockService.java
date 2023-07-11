package br.com.OrderManagementAPI.service;

import br.com.OrderManagementAPI.model.Item;
import br.com.OrderManagementAPI.model.Order;
import br.com.OrderManagementAPI.model.StockMovement;
import br.com.OrderManagementAPI.repository.OrderRepository;
import br.com.OrderManagementAPI.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderAndStockService {

    private final OrderRepository orderRepository;

    private final StockRepository stockRepository;

    private final EmailSender emailSender;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.orElse(null);
    }

    public boolean orderCanBeCompleted(StockMovement currentStock, Order order){
        return currentStock != null && currentStock.getQuantity() >= order.getQuantity();
    }

    public Order tryToCompleteOrder(StockMovement currentStock, Order order) {
        if(orderCanBeCompleted(currentStock, order)){
            currentStock.setQuantity(currentStock.getQuantity() - order.getQuantity());
            log.info("Stock ID: {} updated to complete Order ID: {}",
                    currentStock.getId(), order.getId());
            stockRepository.save(currentStock);
            order.setComplete(true);
            log.info("Order ID: {} completed", order.getId());
            emailSender.sendEmail(order);
        }else{
            log.info("Order ID: {} cannot be completed due to insufficient stock of Item ID: {}",
                    order.getId(), order.getItem().getId());
            order.setComplete(false);
        }
        return orderRepository.save(order);
    }

    public Order saveOrderAndTryToComplete(Order order) {
        StockMovement currentStock = getStockMovementByItem(order.getItem());
        return this.tryToCompleteOrder(currentStock, order);
    }

    public Order saveOrderAndTryToComplete(StockMovement currentStock, Order order) {
        return this.tryToCompleteOrder(currentStock, order);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order existingOrder = orderOptional.get();

            if(existingOrder.isComplete()) {
                log.info("You cannot edit orders that is already complete");
                return null;
            }

            existingOrder.setItem(updatedOrder.getItem());
            existingOrder.setQuantity(updatedOrder.getQuantity());
            existingOrder.setUser(updatedOrder.getUser());

            return this.saveOrderAndTryToComplete(existingOrder);
        } else {
            return null;
        }
    }

    public boolean deleteOrder(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            orderRepository.delete(orderOptional.get());
            return true;
        } else {
            return false;
        }
    }

    public List<StockMovement> getAllStockMovements() {
        return stockRepository.findAll();
    }

    public StockMovement getStockMovementById(Long id) {
        Optional<StockMovement> stockMovementOptional = stockRepository.findById(id);
        return stockMovementOptional.orElse(null);
    }

    public StockMovement saveStockAndTryToCompleteOrders(StockMovement newStock){
        List<Order> orderList = orderRepository.findByItemAndCompleteIsFalseOrderByCreationDate(newStock.getItem());
        boolean stockUpdated = false;
        for(Order o: orderList){
            if(orderCanBeCompleted(newStock, o)){
                Order orderSaved = saveOrderAndTryToComplete(newStock, o);
                stockUpdated = true;
                log.info("Order with ID: {} updated to complete", orderSaved.getId());
            }
        }
        if (!stockUpdated) {
            return stockRepository.save(newStock);
        } else {
            return newStock;
        }
    }

    public StockMovement createStockMovement(StockMovement stockMovement) {
        Optional<StockMovement> stockOptional = stockRepository.findByItem(stockMovement.getItem());
        if(stockOptional.isPresent()){
            StockMovement stockGet = stockOptional.get();
            return this.updateStockMovement(stockGet.getId(), stockMovement);
        }
        return this.saveStockAndTryToCompleteOrders(stockMovement);
    }

    public StockMovement updateStockMovement(Long id, StockMovement newStock) {
        Optional<StockMovement> stockOptional = stockRepository.findById(id);
        if (stockOptional.isPresent()) {
            StockMovement currentStock = stockOptional.get();

            if(newStock.getQuantity() > currentStock.getQuantity()){
                currentStock.setQuantity(newStock.getQuantity());
                return this.saveStockAndTryToCompleteOrders(currentStock);
            }else{
                return this.stockRepository.save(newStock);
            }
        }
        return null;
    }

    public boolean deleteStockMovement(Long id) {
        Optional<StockMovement> stockMovementOptional = stockRepository.findById(id);
        if (stockMovementOptional.isPresent()) {
            stockRepository.delete(stockMovementOptional.get());
            return true;
        } else {
            return false;
        }
    }

    public StockMovement getStockMovementByItem(Item item) {
        return stockRepository.findByItem(item).orElse(null);
    }

}
