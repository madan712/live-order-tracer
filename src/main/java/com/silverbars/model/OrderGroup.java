package com.silverbars.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data

@AllArgsConstructor

@Builder
public class OrderGroup {

	private BigDecimal quantity;

	private BigDecimal price;
}
