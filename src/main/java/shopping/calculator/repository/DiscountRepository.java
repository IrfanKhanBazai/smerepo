package shopping.calculator.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import shopping.calculator.discount.engine.BuyXGetYHalfPriceDiscount;
import shopping.calculator.discount.engine.Discounts;
import shopping.calculator.discount.engine.PercentageDiscount;
import shopping.calculator.model.Product;

public class DiscountRepository implements IDiscountRepository {
	
    private List<Discounts> discounts = new ArrayList<Discounts> ();	
	
	public List<Discounts> initializeDiscountRepository(IProductRepository productRepository) {
		
		Product apple = productRepository.findProductByName("APPLES").get();
		Product soup = productRepository.findProductByName("SOUP").get();
		Product bread = productRepository.findProductByName("BREAD").get();
		
		PercentageDiscount percDiscount = new PercentageDiscount("10 % Apple Discount", "Apples 10% off :", new BigDecimal(0.10),LocalDate.now() , LocalDate.of(2019, 01, 30),apple);
		BuyXGetYHalfPriceDiscount groupDiscount = new BuyXGetYHalfPriceDiscount("2 tins for Soup Discount", "Loaf of bread for half price :", new BigDecimal(0.50),LocalDate.now() , LocalDate.of(2019, 01, 30),soup,2,bread);
		
		discounts.add(percDiscount);
		discounts.add(groupDiscount);
		
		return discounts;
		
	}
	
	@Override
	public List<Discounts> getAllActiveDiscounts() {
		return discounts.stream().filter(t -> t.isDiscountApplicable()).collect(Collectors.toList());
	}
	

}
