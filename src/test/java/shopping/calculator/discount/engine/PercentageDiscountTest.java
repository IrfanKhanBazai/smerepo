package shopping.calculator.discount.engine;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import shopping.calculator.discount.engine.Discounts;
import shopping.calculator.discount.engine.PercentageDiscount;
import shopping.calculator.enums.Uom;
import shopping.calculator.model.LineItem;
import shopping.calculator.model.Order;
import shopping.calculator.model.Product;

public class PercentageDiscountTest {
	
	@Test
	public void testApplyDiscountMultipleItems () {
		
		Product product = new Product();
		product.setName("Apple");
		product.setPrice(new BigDecimal(1));
		product.setUnitOfMeasurement(Uom.BAG);
		
		Order order = createOrderItem(product, 2);
		
		Discounts percDiscount = new PercentageDiscount("10 % Apple Discount", "Apples 10% off :", new BigDecimal(0.10),LocalDate.now() , LocalDate.of(2019, 01, 30),product);
		
		percDiscount.applyDiscount(order);
		LineItem lineItem = order.getLineItems().get(0);
		assertEquals(lineItem.getDiscountAmount().setScale(2, RoundingMode.HALF_EVEN),new BigDecimal(.20).setScale(2, RoundingMode.HALF_EVEN));
		assertEquals(order.getDiscountDetails().size(),2);
		assertEquals(order.getDiscountDetails().get(0) , "Apples 10% off :-10 p");
		assertEquals(order.getDiscountDetails().get(1) , "Apples 10% off :-10 p");

	}
	
	@Test
	public void testApplyDiscountSingleItem () {
		
		Product product = new Product();
		product.setName("Apple");
		product.setPrice(new BigDecimal(1));
		product.setUnitOfMeasurement(Uom.BAG);
		
		Order order = createOrderItem(product, 1);
		
		Discounts percDiscount = new PercentageDiscount("10 % Apple Discount", "Apples 10% off :", new BigDecimal(0.10),LocalDate.now() , LocalDate.of(2019, 01, 30),product);
		
		percDiscount.applyDiscount(order);
		LineItem lineItem = order.getLineItems().get(0);
		BigDecimal expectedDiscount = new BigDecimal(.10).setScale(2, RoundingMode.HALF_EVEN);
		assertEquals(lineItem.getDiscountAmount().setScale(2, RoundingMode.HALF_EVEN),expectedDiscount);
		assertEquals(order.getDiscountDetails().size(),1);
		assertEquals(order.getDiscountDetails().get(0) , "Apples 10% off :-10 p");

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
