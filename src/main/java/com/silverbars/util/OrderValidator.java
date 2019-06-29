package com.silverbars.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.silverbars.exception.InvalidException;
import com.silverbars.model.Order;

@Component
public class OrderValidator {

	public void validateOrder(Order order) throws InvalidException {
		if (order == null) {
			throw new InvalidException("Null order cannot be placed");
		}

		if (StringUtils.isBlank(order.getUserId())) {
			throw new InvalidException("User id cannot be blank");
		}

		// MORE VALIDATIONS CAN BE ADDED HERE
	}

	public void validateOrderId(Integer orderId) throws InvalidException {
		if (orderId == null) {
			throw new InvalidException("Null orderId cannot be deleted");
		}

		// MORE VALIDATIONS CAN BE ADDED HERE

	}

	public void validateUserId(String userId) throws InvalidException {
		if (StringUtils.isBlank(userId)) {
			throw new InvalidException("User id cannot be blank");
		}

		// MORE VALIDATIONS CAN BE ADDED HERE
	}

}
