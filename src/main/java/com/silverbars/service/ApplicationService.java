package com.silverbars.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.silverbars.model.Order;
import com.silverbars.model.OrderGroup;
import com.silverbars.model.OrderType;
import com.silverbars.model.Status;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Service
public class ApplicationService {

	// In memory storage variables
	private AtomicInteger orderIdGenerator = new AtomicInteger();
	private List<Order> orderList = new ArrayList<>();

	public synchronized Integer placeOrder(Order order) {
		order.setOrderId(orderIdGenerator.incrementAndGet());
		order.setStatus(Status.ACTIVE);
		orderList.add(order);

		return order.getOrderId();
	}

	public Integer cancelOrder(Integer orderId) {
		Order order = orderList.stream().filter(o -> orderId.equals(o.getOrderId())).findAny().get();
		order.setStatus(Status.INACTIVE);

		return order.getOrderId();
	}

	public List<Order> getUserOrders(String userId) {
		return orderList.stream().filter(o -> userId.equals(o.getUserId())).collect(Collectors.toList());
	}

	public List<OrderGroup> getBuySummary() {
		List<OrderGroup> orderGroupList = new ArrayList<>();

		orderList.stream().filter(o -> Status.ACTIVE == o.getStatus() && OrderType.BUY == o.getType())
				.collect(Collectors.groupingBy(Order::getPrice,
						Collectors.reducing(BigDecimal.ZERO, Order::getQuantity, BigDecimal::add)))
				.forEach((p, q) -> {
					orderGroupList.add(OrderGroup.builder().price(p).quantity(q).build());
				});

		orderGroupList.sort((OrderGroup g1, OrderGroup g2) -> {
			return g2.getPrice().compareTo(g1.getPrice());
		});

		return orderGroupList;
	}

	public List<OrderGroup> getSellSummary() {
		List<OrderGroup> orderGroupList = new ArrayList<>();

		orderList.stream().filter(o -> Status.ACTIVE == o.getStatus() && OrderType.SELL == o.getType())
				.collect(Collectors.groupingBy(Order::getPrice,
						Collectors.reducing(BigDecimal.ZERO, Order::getQuantity, BigDecimal::add)))
				.forEach((p, q) -> {
					orderGroupList.add(OrderGroup.builder().price(p).quantity(q).build());
				});

		orderGroupList.sort((OrderGroup g1, OrderGroup g2) -> {
			return g1.getPrice().compareTo(g2.getPrice());
		});

		return orderGroupList;
	}
}
