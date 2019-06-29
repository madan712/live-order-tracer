package com.silverbars.service;

import static com.silverbars.util.Constant.USER1;
import static com.silverbars.util.Constant.USER2;
import static com.silverbars.util.Constant.USER3;
import static com.silverbars.util.Constant.USER4;
import static com.silverbars.util.Constant.INVALID_USER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.silverbars.model.Order;
import com.silverbars.model.OrderGroup;
import com.silverbars.model.OrderType;
import com.silverbars.model.Status;
import com.silverbars.util.TestConstant;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest {

	@InjectMocks
	private ApplicationService applicationService;

	@Test
	public void testPlaceOrderWhenOrderIsPassesThenItIsAddedToOrderList() {
		int orderId1 = applicationService.placeOrder(
				TestConstant.getDummyOrderWithOrderId(USER1, new BigDecimal(3.5), new BigDecimal(303), OrderType.SELL));

		assertEquals(1, orderId1);
		assertEquals(1, applicationService.getOrderList().size());

		int orderId2 = applicationService.placeOrder(
				TestConstant.getDummyOrderWithOrderId(USER2, new BigDecimal(1.5), new BigDecimal(304), OrderType.BUY));

		assertEquals(2, orderId2);
		assertEquals(2, applicationService.getOrderList().size());

	}

	@Test
	public void testCancelOrderWhenOrderIsPassesThenItIsCanceled() {
		int insertedOrderId = applicationService.placeOrder(
				TestConstant.getDummyOrderWithOrderId(USER1, new BigDecimal(3.5), new BigDecimal(303), OrderType.SELL));

		assertEquals(1, insertedOrderId);
		assertEquals(1, applicationService.getOrderList().size());

		int canceledOrderId = applicationService.cancelOrder(1);

		assertEquals(1, canceledOrderId);

		Order order = applicationService.getOrderList().stream().findAny().get();

		assertEquals(Status.INACTIVE, order.getStatus());

	}

	@Test
	public void testGetUserOrdersWhenInvalidUserIdThenNoOrderIsReterived() {
		List<Order> orderList = applicationService.getUserOrders(INVALID_USER);
		assertNotNull(orderList);
		assertEquals(0, orderList.size());
	}

	@Test
	public void testGetUserOrdersWhenValidUserIdThenOrderListIsReterived() {
		int insertedOrderId = applicationService.placeOrder(
				TestConstant.getDummyOrderWithOrderId(USER1, new BigDecimal(3.5), new BigDecimal(303), OrderType.SELL));

		assertEquals(1, insertedOrderId);
		assertEquals(1, applicationService.getOrderList().size());

		List<Order> orderList = applicationService.getUserOrders(USER1);
		assertNotNull(orderList);
		assertEquals(1, orderList.size());
	}

	@Test
	public void testGetSellSummary() {
		int orderId1 = applicationService.placeOrder(
				TestConstant.getDummyOrderWithOrderId(USER1, new BigDecimal(3.5), new BigDecimal(306), OrderType.SELL));
		int orderId2 = applicationService.placeOrder(
				TestConstant.getDummyOrderWithOrderId(USER2, new BigDecimal(1.2), new BigDecimal(310), OrderType.SELL));
		int orderId3 = applicationService.placeOrder(
				TestConstant.getDummyOrderWithOrderId(USER3, new BigDecimal(1.5), new BigDecimal(307), OrderType.SELL));
		int orderId4 = applicationService.placeOrder(
				TestConstant.getDummyOrderWithOrderId(USER4, new BigDecimal(2.0), new BigDecimal(306), OrderType.SELL));

		assertEquals(1, orderId1);
		assertEquals(2, orderId2);
		assertEquals(3, orderId3);
		assertEquals(4, orderId4);
		assertEquals(4, applicationService.getOrderList().size());

		List<OrderGroup> orderGroupList = applicationService.getSellSummary();

		assertNotNull(orderGroupList);
		assertEquals(3, orderGroupList.size());

		OrderGroup orderGroup1 = OrderGroup.builder().quantity(new BigDecimal(5.5)).price(new BigDecimal(306)).build();
		assertEquals(orderGroup1, orderGroupList.get(0));

		OrderGroup orderGroup2 = OrderGroup.builder().quantity(new BigDecimal(1.5)).price(new BigDecimal(307)).build();
		assertEquals(orderGroup2, orderGroupList.get(1));

		OrderGroup orderGroup3 = OrderGroup.builder().quantity(new BigDecimal(1.2)).price(new BigDecimal(310)).build();
		assertEquals(orderGroup3, orderGroupList.get(2));

	}
}
