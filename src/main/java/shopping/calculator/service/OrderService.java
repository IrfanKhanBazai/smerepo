package shopping.calculator.service;

import java.util.List;
import java.util.Optional;

import shopping.calculator.exceptions.UnIdentifiedItemException;
import shopping.calculator.model.Order;
import shopping.calculator.model.Product;
import shopping.calculator.repository.IProductRepository;

public class OrderService implements IOrderService {
	
	private IProductRepository productRepository;
	
	public OrderService (IProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public Order createOrder(List<String> items) throws UnIdentifiedItemException {
		
		Order order = new Order();
		
		for (String item : items) {
			
			Optional<Product> product = productRepository.findProductByName(item.toUpperCase());
			if (!product.isPresent()) {
				 throw new UnIdentifiedItemException("Item not recognized");
			}
			
			//to avoid adding same product twice
			if(order.productAlreadyExist(product.get())){
				continue;
			}

			// count the quantity of a particular product in a given Order
			long productCount = items.stream().filter(t -> t.equalsIgnoreCase(item)).count();
			
			order.addItem(productRepository.findProductByName(item).get(), productCount);
			
		}
		return order;
	}
}
