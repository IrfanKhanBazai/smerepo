package shopping.calculator.discount.engine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import shopping.calculator.model.LineItem;
import shopping.calculator.model.Order;
import shopping.calculator.model.Product;

public class BuyXGetYHalfPriceDiscount extends Discounts {
	
	private Product eligibleProduct;
	private int eligibleProductQuantity;
	
	// product on which the discount will be applied
	private Product targetDiscountProduct;
	
	
	public BuyXGetYHalfPriceDiscount(String name, 
									String description, 
									BigDecimal discountRate,
									LocalDate validFrom,
									LocalDate expiryDate,
									Product eligibleProduct,
									int eligibleProductQuantity,
									Product targetDiscountProduct
									)
	{
		super(name,description,discountRate,validFrom,expiryDate);
		this.eligibleProduct = eligibleProduct; 
		this.eligibleProductQuantity = eligibleProductQuantity;
		this.targetDiscountProduct = targetDiscountProduct;
	}
	
	public void applyDiscount(Order order) {		
		
		Optional<LineItem> eligibleLineItem = order.getLineItems().stream()
							.filter(t -> t.getProduct().equals(eligibleProduct)).findFirst();
		
		Optional<LineItem> targetLineItem = order.getLineItems().stream()
				.filter(t -> t.getProduct().equals(targetDiscountProduct)).findFirst();
		
		
		if (eligibleLineItem.isPresent()) {					
			long itemQuantity = eligibleLineItem.get().getQuantity();
			//ratio of total no. of eligible items per no. of items required for a discount.
			long noOfDiscounts = (itemQuantity /eligibleProductQuantity);			
			if (noOfDiscounts > 0 && targetLineItem.isPresent()) {
				// multiple discounts cannot be applicable on the same target item
				//therefore discountFactor should be the lowest of noOfDiscounts or quantity of lineItem
				long discountFactor =  (targetLineItem.get().getQuantity() < noOfDiscounts) ? targetLineItem.get().getQuantity() : noOfDiscounts;				
				BigDecimal discountAmount = calculateDiscountAmount(targetLineItem.get().getProduct().getPrice(),discountFactor);
	        	targetLineItem.get().setDiscountAmount(discountAmount);	
	        	
				for (int count = 0 ; count < discountFactor; count++) {
					order.addDiscountDetails(createDiscountDetails(targetLineItem.get().getProduct().getPrice()));
				}
			}			
		}	
	}
	
	private BigDecimal calculateDiscountAmount(BigDecimal price, long discountFactor) {
		return discountRate.multiply(new BigDecimal(discountFactor)).multiply(price);
	}
	
	
}
