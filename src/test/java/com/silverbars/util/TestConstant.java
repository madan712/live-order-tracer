package com.silverbars.util;

import static com.silverbars.util.Constant.ORDER_ID;

import java.math.BigDecimal;

import com.silverbars.model.Order;
import com.silverbars.model.OrderType;
import com.silverbars.model.Status;

public class TestConstant {

	public static Order getDummyOrderWithOrderId(String userId, BigDecimal quantity, BigDecimal price, OrderType type) {

		Order dummyOrder = getDummyOrderWithoutOrderId(userId, quantity, price, type);
		dummyOrder.setOrderId(ORDER_ID);
		return dummyOrder;
	}

	public static Order getDummyOrderWithoutOrderId(String userId, BigDecimal quantity, BigDecimal price,
			OrderType type) {
		return Order.builder().userId(userId).quantity(quantity).price(price).type(type).status(Status.ACTIVE).build();
	}
}
