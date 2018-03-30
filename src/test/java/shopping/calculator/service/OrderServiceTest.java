package shopping.calculator.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import shopping.calculator.exceptions.UnIdentifiedItemException;
import shopping.calculator.model.Order;
import shopping.calculator.repository.DiscountRepository;
import shopping.calculator.repository.IDiscountRepository;
import shopping.calculator.repository.IProductRepository;
import shopping.calculator.repository.ProductRepository;

public class OrderServiceTest {
	
	IProductRepository productRepository = new ProductRepository();
	IDiscountRepository discountRepository  = new DiscountRepository();
	
	@Before
	public void initializeRepositories() {
		productRepository.initializeProductRepository();
		discountRepository.initializeDiscountRepository(productRepository);
	}
	
	@Test
	public void testCreateOrder() throws UnIdentifiedItemException {
		
		IOrderService orderService = new OrderService(productRepository);
		
		Order order = orderService.createOrder(Arrays.asList("Soup","soup","Apples", "Bread"));
		
		assertEquals(order.getLineItems().size(),3);
		assertEquals(order.getLineItems().get(0).getProduct().getName(),"SOUP");
		assertEquals(order.getLineItems().get(0).getQuantity(),2);
		assertEquals(order.getLineItems().get(1).getProduct().getName(),"APPLES");
		assertEquals(order.getLineItems().get(1).getQuantity(),1);
		assertEquals(order.getLineItems().get(2).getProduct().getName(),"BREAD");
		assertEquals(order.getLineItems().get(2).getQuantity(),1);
	}
	
	@Test(expected = UnIdentifiedItemException.class) 
	public void testCreateOrderThrowsException() throws UnIdentifiedItemException {
		
		IOrderService orderService = new OrderService(productRepository);
		orderService.createOrder(Arrays.asList("IncorrectItemName","Soup","Apples", "Bread"));
		
		
	}

}
