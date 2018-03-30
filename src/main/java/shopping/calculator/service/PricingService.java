package shopping.calculator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import shopping.calculator.discount.engine.Discounts;
import shopping.calculator.model.LineItem;
import shopping.calculator.model.Order;
import shopping.calculator.repository.DiscountRepository;
import shopping.calculator.repository.IDiscountRepository;

public class PricingService implements IPricingService{

	private IDiscountRepository discountRepository;
	
	public PricingService (IDiscountRepository discountRepository) {
		this.discountRepository = discountRepository;
	}

	@Override
	public BigDecimal calculateGrossPrice(Order order) {
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (LineItem item : order.getLineItems()) {
			BigDecimal subTotal = calculateSubTotalBeforeDiscount(item.getProduct().getPrice(),item.getQuantity());
			
			totalPrice = totalPrice.add(subTotal);
		}
		totalPrice = totalPrice.setScale(2,RoundingMode.HALF_UP );
		return totalPrice;
	}

	@Override
	public BigDecimal calculateFinalPrice(Order order) {
		List<Discounts> discounts  =  discountRepository.getAllActiveDiscounts();
		
		for (Discounts discount : discounts)  {
			discount.applyDiscount(order);
		}
		
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (LineItem item : order.getLineItems()) {
			
			BigDecimal subTotal = calculateSubTotalAfterDiscount(item.getProduct().getPrice(),item.getQuantity(),item.getDiscountAmount());
			totalPrice = totalPrice.add(subTotal);
		}
		totalPrice = totalPrice.setScale(2,RoundingMode.HALF_UP );
		return totalPrice;
	}
	
	private BigDecimal calculateSubTotalBeforeDiscount(BigDecimal price, long quantity) {		
		return (price.multiply(new BigDecimal(quantity)));
	}
	
	public  BigDecimal calculateSubTotalAfterDiscount(BigDecimal price, long quantity,BigDecimal discountAmount ) {
		return (price.multiply(new BigDecimal(quantity))).subtract(discountAmount);
	}



	public void setDiscountRepository(DiscountRepository discountRepository) {
		this.discountRepository = discountRepository;
	}

}
