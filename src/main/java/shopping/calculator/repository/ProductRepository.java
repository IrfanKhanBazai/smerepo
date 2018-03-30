package shopping.calculator.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import shopping.calculator.enums.Uom;
import shopping.calculator.model.Product;

public class ProductRepository implements IProductRepository{
	
	List<Product> products = new ArrayList<Product> ();	
	
	@Override
	public List<Product> initializeProductRepository() {
		Product apple = new Product();
		apple.setName("APPLES");
		apple.setPrice(new BigDecimal(1.00));
		apple.setUnitOfMeasurement(Uom.BAG);
		
		Product soup = new Product();
		soup.setName("SOUP");
		soup.setPrice(new BigDecimal(0.65));
		soup.setUnitOfMeasurement(Uom.TIN);
		
		Product bread = new Product();
		bread.setName("BREAD");
		bread.setPrice(new BigDecimal(0.80));
		bread.setUnitOfMeasurement(Uom.LOAF);
		
		Product milk = new Product();
		milk.setName("MILK");
		milk.setPrice(new BigDecimal(1.30));
		milk.setUnitOfMeasurement(Uom.BOTTLE);
		
		products.add(apple);
		products.add(soup);
		products.add(bread);
		products.add(milk);
		
		return products;
		
	}
	
	 @Override
	 public Optional<Product> findProductByName(String name) {
		 return products.stream().filter(t -> t.getName().equalsIgnoreCase((name))).findFirst();
	 }
	
	

}
