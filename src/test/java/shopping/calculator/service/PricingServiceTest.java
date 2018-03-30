package shopping.calculator.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import shopping.calculator.enums.Uom;
import shopping.calculator.model.LineItem;
import shopping.calculator.model.Order;
import shopping.calculator.model.Product;
import shopping.calculator.repository.DiscountRepository;
import shopping.calculator.repository.IDiscountRepository;
import shopping.calculator.repository.IProductRepository;
import shopping.calculator.repository.ProductRepository;

public class PricingServiceTest {
	
	IProductRepository productRepository = new ProductRepository();
	IDiscountRepository discountRepository  = new DiscountRepository();
	
	@Before
	public void initializeRepositories() {
		productRepository.initializeProductRepository();
		discountRepository.initializeDiscountRepository(productRepository);
	}
	
	@Test
	public void testCalculateGrossPrice() {
		IPricingService pricingService = new PricingService(discountRepository);
		Product product = new Product();
		product.setName("Apples");
		product.setPrice(new BigDecimal(1));
		product.setUnitOfMeasurement(Uom.BAG);
		Order order = createOrderItem(product , 2);
		BigDecimal grossPrice = pricingService.calculateGrossPrice(order);
		
		assertEquals(grossPrice,new BigDecimal(2).setScale(2));
	}
	
	@Test
	public void testCalculateFinalPrice() {
		IPricingService pricingService = new PricingService(discountRepository);
		Product product = new Product();
		product.setName("Apples");
		product.setPrice(new BigDecimal(1));
		product.setUnitOfMeasurement(Uom.BAG);
		Order order = createOrderItem(product , 2);
		BigDecimal grossPrice = pricingService.calculateFinalPrice(order);
		
		// 10% discount on each Apple
		assertEquals(grossPrice,new BigDecimal(1.80).setScale(2,RoundingMode.HALF_EVEN));
		
	}
	
	private Order createOrderItem(Product product , int quantity) {
		
		Order order = new Order();
		LineItem lineItem = new LineItem();
		
		lineItem.setProduct(product);
		lineItem.setQuantity(quantity);
		
		List<LineItem> lineItems = new ArrayList<>();
		
		lineItems.add(lineItem);
		order.setLineItems(lineItems);
		return order;
		
	}

}
