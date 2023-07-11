package br.com.OrderManagementAPI.utils;

import br.com.OrderManagementAPI.model.Item;
import br.com.OrderManagementAPI.model.Order;
import br.com.OrderManagementAPI.model.StockMovement;
import br.com.OrderManagementAPI.model.User;

import java.util.ArrayList;
import java.util.List;

public class ModelMocks {

    public static Order getOrderMock(boolean complete){
        return Order.builder()
                .id(1L)
                .item(Item.builder().id(1L).build())
                .user(User.builder().id(1L).name("User").email("user@user.com").build())
                .quantity(2)
                .complete(complete)
                .build();
    }

    public static StockMovement getStockMock(int quantity){
        return StockMovement.builder()
                .id(1L)
                .item(Item.builder().id(1L).build())
                .quantity(quantity)
                .build();
    }

    public static List<Order> getOrderListMock() {
        List<Order> result = new ArrayList<>();
        result.add(Order.builder()
                .id(1L)
                .item(Item.builder().id(1L).build())
                .user(User.builder().id(1L).name("User").email("user@user.com").build())
                .quantity(1)
                .complete(false)
                .build());
        result.add(Order.builder()
                .id(2L)
                .item(Item.builder().id(1L).build())
                .user(User.builder().id(1L).name("User2").email("user2@user.com").build())
                .quantity(1)
                .complete(false)
                .build());

        return result;

    }
}
