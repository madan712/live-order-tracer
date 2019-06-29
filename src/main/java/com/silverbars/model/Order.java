package com.silverbars.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data

@AllArgsConstructor

@Builder
public class Order {

	private Integer orderId;

	private String userId;

	private BigDecimal quantity;

	private BigDecimal price;

	private OrderType type;

	private Status status;
}