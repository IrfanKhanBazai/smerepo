package shopping.calculator.service;

import java.math.BigDecimal;

import shopping.calculator.model.Order;

public interface IPricingService {
	
public BigDecimal calculateGrossPrice(Order order) ;
	
	public BigDecimal calculateFinalPrice(Order order) ;

}
