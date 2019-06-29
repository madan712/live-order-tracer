package com.silverbars.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.silverbars.exception.InvalidException;
import com.silverbars.model.Order;

@RunWith(MockitoJUnitRunner.class)
public class OrderValidatorTest {

	@InjectMocks
	private OrderValidator orderValidator;
	
	@Test(expected = InvalidException.class)
	public void testValidateOrderWhenNullOrderThenThrowInvalidException() throws InvalidException {
		orderValidator.validateOrder(null);
	}
	
	@Test(expected = InvalidException.class)
	public void testValidateOrderWhenInvalidUserIdThenThrowInvalidException() throws InvalidException {
		orderValidator.validateOrder(Order.builder().build());
	}

}
