package br.com.OrderManagementAPI.service;

import br.com.OrderManagementAPI.model.Order;
import br.com.OrderManagementAPI.model.StockMovement;
import br.com.OrderManagementAPI.repository.OrderRepository;
import br.com.OrderManagementAPI.repository.StockRepository;
import br.com.OrderManagementAPI.utils.ModelMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderAndStockServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private OrderAndStockService orderAndStockService;

    @Test
    public void testCreateOrderAndCompleteOrder() {
        Order orderMock = ModelMocks.getOrderMock(false);
        StockMovement stockMock = ModelMocks.getStockMock(2);

        when(orderRepository.save(any())).thenReturn(orderMock);
        when(stockRepository.findByItem(any())).thenReturn(Optional.of(stockMock));
        doNothing().when(emailSender).sendEmail(any());

        Order createdOrder = orderAndStockService.saveOrderAndTryToComplete(orderMock);

        assertTrue(createdOrder.isComplete());
    }

    @Test
    public void testCreateOrderAndDoNotCompleteOrder() {
        Order orderMock = ModelMocks.getOrderMock(false);
        StockMovement stockMock = ModelMocks.getStockMock(1);


        when(orderRepository.save(any())).thenReturn(orderMock);
        when(stockRepository.findByItem(any())).thenReturn(Optional.of(stockMock));

        Order createdOrder = orderAndStockService.saveOrderAndTryToComplete(orderMock);

        assertFalse(createdOrder.isComplete());
    }

    @Test
    public void testUpdateOrderComplete() {
        Order orderMock = ModelMocks.getOrderMock(true);

        when(orderRepository.findById(any())).thenReturn(Optional.of(orderMock));

        Order result = orderAndStockService.updateOrder(orderMock.getId(), orderMock);

        assertNull(result);
    }


    @Test
    void createStockMovementAndCompleteTwoOrders() {
        List<Order> orderListMock = ModelMocks.getOrderListMock();
        StockMovement newStockMock = ModelMocks.getStockMock(2);

        when(stockRepository.findByItem(any())).thenReturn(Optional.empty());
        when(orderRepository.findByItemAndCompleteIsFalseOrderByCreationDate(any())).thenReturn(orderListMock);
        when(orderRepository.save(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());
        doNothing().when(emailSender).sendEmail(any());

        StockMovement updatedStock = orderAndStockService.createStockMovement(newStockMock);

        assertNotNull(updatedStock);

        verify(stockRepository, times(2)).save(any());
        verify(orderRepository, times(2)).save(any());
    }

    @Test
    void updateStockToLessQuantity() {
        StockMovement oldStockMock = ModelMocks.getStockMock(2);
        StockMovement newStockMock = ModelMocks.getStockMock(1);

        when(stockRepository.findById(any())).thenReturn(Optional.of(oldStockMock));
        when(stockRepository.save(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        StockMovement updatedStock = orderAndStockService.updateStockMovement(newStockMock.getId(), newStockMock);

        assertNotNull(updatedStock);

        verify(stockRepository, times(1)).save(any());
    }
}