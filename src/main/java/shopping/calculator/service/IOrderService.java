package shopping.calculator.service;

import java.util.List;

import shopping.calculator.exceptions.UnIdentifiedItemException;
import shopping.calculator.model.Order;

public interface IOrderService {
	
	public Order createOrder(List<String> items) throws UnIdentifiedItemException;
}
