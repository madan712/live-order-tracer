package com.silverbars.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.silverbars.exception.InvalidException;
import com.silverbars.model.Order;
import com.silverbars.model.OrderGroup;
import com.silverbars.service.ApplicationService;
import com.silverbars.util.OrderValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/silverbars")
@RestController
public class ApplicationController {

	@Autowired
	private OrderValidator orderValidator;

	@Autowired
	private ApplicationService applicationService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/order")
	public Integer placeOrder(@RequestBody Order order) {
		log.debug("order : {}", order);
		try {
			orderValidator.validateOrder(order);

			return applicationService.placeOrder(order);

		} catch (InvalidException e) {
			log.error("InvalidException : {}", e);
			return -1;
		}
	}

	@DeleteMapping(path = "/{orderId}")
	public Integer cancelOrder(@PathVariable Integer orderId) {
		log.debug("orderId : {}", orderId);
		try {
			orderValidator.validateOrderId(orderId);

			return applicationService.cancelOrder(orderId);

		} catch (InvalidException e) {
			log.error("InvalidException : {}", e);
			return -1;
		}
	}

	@GetMapping("/orders/{userId}")
	public List<Order> getUserOrders(@PathVariable String userId) {
		log.debug("userId : {}", userId);
		try {
			orderValidator.validateUserId(userId);

			return applicationService.getUserOrders(userId);

		} catch (InvalidException e) {
			log.error("InvalidException : {}", e);
			return null;
		}
	}

	@GetMapping("/orders/buy")
	public List<OrderGroup> getBuySummary() {
		log.debug("getBuySummary() called");
		return applicationService.getBuySummary();
	}

	@GetMapping("/orders/sell")
	public List<OrderGroup> getSellSummary() {
		log.debug("getSellSummary() called");
		return applicationService.getSellSummary();
	}

}
