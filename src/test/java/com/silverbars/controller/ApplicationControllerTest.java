package com.silverbars.controller;

import static com.silverbars.util.Constant.INVALID_ORDER_ID;
import static com.silverbars.util.Constant.ORDER_ID;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.silverbars.exception.InvalidException;
import com.silverbars.model.Order;
import com.silverbars.model.OrderType;
import com.silverbars.service.ApplicationService;
import com.silverbars.util.OrderValidator;
import com.silverbars.util.TestConstant;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationControllerTest {

	@InjectMocks
	private ApplicationController applicationController;

	@Mock
	private OrderValidator orderValidator;

	@Mock
	private ApplicationService applicationService;

	@Test
	public void testPlaceOrderWhenValidOrderThenReturnOrderId() {

		when(applicationService.placeOrder(any(Order.class))).thenReturn(ORDER_ID);

		Integer orderId = applicationController
				.placeOrder(TestConstant.getDummyOrderWithOrderId("user1", new BigDecimal(3.5), new BigDecimal(303), OrderType.SELL));

		assertEquals(ORDER_ID, orderId);

	}

	@Test
	public void testPlaceOrderWhenInvalidOrderThenReturnMinusOne() throws InvalidException {

		Order invalidOrder = null;

		doThrow(new InvalidException("Null order cannot be placed")).when(orderValidator).validateOrder(invalidOrder);

		Integer orderId = applicationController.placeOrder(invalidOrder);

		assertEquals(INVALID_ORDER_ID, orderId);
	}
	
	@Test
	public void testCancelOrderWhenValidOrderIdThenRetuenDeletedOrderId() {
		
		when(applicationService.cancelOrder(ORDER_ID)).thenReturn(ORDER_ID);

		Integer orderId = applicationController.cancelOrder(ORDER_ID);
		
		assertEquals(ORDER_ID, orderId);
	}

	@Test
	public void testCancelOrderWhenInvalidOrderIdThenReturnMinusOne() throws InvalidException {
		
		doThrow(new InvalidException("Null orderId cannot be deleted")).when(orderValidator).validateOrderId(null);
		
		Integer orderId = applicationController.cancelOrder(null);
		
		assertEquals(INVALID_ORDER_ID, orderId);
	}
}
