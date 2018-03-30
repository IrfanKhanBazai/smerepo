package shopping.calculator.repository;

import java.util.List;

import shopping.calculator.discount.engine.Discounts;

public interface IDiscountRepository {
	
	public List<Discounts> initializeDiscountRepository(IProductRepository productRepository);
	public List<Discounts> getAllActiveDiscounts();

}
