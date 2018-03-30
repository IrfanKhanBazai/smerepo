package shopping.calculator.app;

import java.math.BigDecimal;
import java.util.Arrays;

import shopping.calculator.exceptions.NoShoppingItemFoundException;
import shopping.calculator.exceptions.UnIdentifiedItemException;
import shopping.calculator.model.Order;
import shopping.calculator.repository.DiscountRepository;
import shopping.calculator.repository.IDiscountRepository;
import shopping.calculator.repository.IProductRepository;
import shopping.calculator.repository.ProductRepository;
import shopping.calculator.service.IOrderService;
import shopping.calculator.service.IPricingService;
import shopping.calculator.service.OrderService;
import shopping.calculator.service.PricingService;

public class ShoppingCalculatorMain {

	public static void main(String[] args) {
		
		try {
		
		 // Initializing repositories- this will be not needed if proper persistence layer is used like Spring repositories.
			IProductRepository productRepository = new ProductRepository();
			IDiscountRepository discountRepository  = new DiscountRepository();
			
			productRepository.initializeProductRepository();
			discountRepository.initializeDiscountRepository(productRepository);
			
					
			IOrderService orderService = new OrderService(productRepository);		
			IPricingService pricingService = new PricingService(discountRepository);
		
			
			if (args == null || args.length == 0) {
				throw new NoShoppingItemFoundException("No items found to calculate pricing");
			} 
			
			Order order = orderService.createOrder(Arrays.asList(args));
			
			BigDecimal grossPrice = pricingService.calculateGrossPrice(order);
			
			BigDecimal finalPrice = pricingService.calculateFinalPrice(order);
			
			System.out.println("SubTotal: £" + grossPrice);
			
			if (order.getDiscountDetails().isEmpty()) {
				System.out.println("(no offers available)");
			} else {
				order.getDiscountDetails().stream().forEach(System.out::println);
			}
			System.out.println("Total: £" + finalPrice);
		}		
		catch(NoShoppingItemFoundException exc) {			
			System.out.println("No items found in the basket");
		}
		catch(UnIdentifiedItemException exc) {			
			System.out.println("At least one item not recognized in the basket");
		}
	}
}
