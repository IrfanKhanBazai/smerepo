package shopping.calculator.repository;

import java.util.List;
import java.util.Optional;

import shopping.calculator.model.Product;

public interface IProductRepository {
	
	public List<Product> initializeProductRepository();
	
	public Optional<Product> findProductByName(String name);

}
