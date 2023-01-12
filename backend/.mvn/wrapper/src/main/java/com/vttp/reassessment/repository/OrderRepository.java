package com.vttp.reassessment.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonObject;
import com.vttp.reassessment.model.Order;

@Repository
public class OrderRepository {

    private static final String SQL_INSERT_ORDER = "insert into orders (name, email, phone, title, description, image) values (?, ?, ?, ?, ?, ?);";
    private static final String SQL_GET_ORDERS_BY_EMAIL = "select * from orders where email = ?;";
    
    @Autowired
    private JdbcTemplate template;

    public void createOrder(Order order) {
        template.update(SQL_INSERT_ORDER, order.getName(), order.getEmail(), order.getPhone(), order.getTitle(), order.getDescription(), order.getImage());
    }

    public List<Order> getOrdersByEmail(String email) {
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_ORDERS_BY_EMAIL, email);
        List<Order> orders = new ArrayList<>();
        while(rs.next()) {
            orders.add(Order.create(rs));
        }
        return orders;
    }
}