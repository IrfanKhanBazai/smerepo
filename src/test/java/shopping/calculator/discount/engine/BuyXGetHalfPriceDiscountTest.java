package shopping.calculator.discount.engine;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import shopping.calculator.discount.engine.BuyXGetYHalfPriceDiscount;
import shopping.calculator.discount.engine.Discounts;
import shopping.calculator.enums.Uom;
import shopping.calculator.model.LineItem;
import shopping.calculator.model.Order;
import shopping.calculator.model.Product;

public class BuyXGetHalfPriceDiscountTest {
	
	@Test
	public void testApplyDiscountSuccessfully () {
		
		Product soup = new Product();
		soup.setName("Soup");
		soup.setPrice(new BigDecimal(.65));
		soup.setUnitOfMeasurement(Uom.TIN);
		
		Product bread = new Product();
		bread.setName("Bread");
		bread.setPrice(new BigDecimal(.80));
		bread.setUnitOfMeasurement(Uom.LOAF);
		
		Order order = createOrderItem(soup, 2,bread,1);
		
		Discounts buyXGetYHalfPriceDiscount = new BuyXGetYHalfPriceDiscount("2 tins for Soup Discount", "Loaf of bread for half price:", new BigDecimal(0.50),LocalDate.now() , LocalDate.of(2019, 01, 30),soup,2,bread);
		
		buyXGetYHalfPriceDiscount.applyDiscount(order);
		LineItem lineItem = order.getLineItems().get(1);
		BigDecimal expectedDiscount = new BigDecimal(.40).setScale(2, RoundingMode.HALF_EVEN);
		
		assertEquals(lineItem.getDiscountAmount().setScale(2, RoundingMode.HALF_EVEN),expectedDiscount);
		assertEquals(order.getDiscountDetails().size(),1);
		assertEquals(order.getDiscountDetails().get(0) , "Loaf of bread for half price:-40 p");
	}
	
	@Test
	public void testApplyDiscountNotApplicable () {
		
		Product soup = new Product();
		soup.setName("Soup");
		soup.setPrice(new BigDecimal(.65));
		soup.setUnitOfMeasurement(Uom.TIN);
		
		Product bread = new Product();
		bread.setName("Bread");
		bread.setPrice(new BigDecimal(.80));
		bread.setUnitOfMeasurement(Uom.LOAF);
		
		Order order = createOrderItem(soup, 1,bread,1);
		
		Discounts buyXGetYHalfPriceDiscount = new BuyXGetYHalfPriceDiscount("2 tins for Soup Discount", "Loaf of bread for half price", new BigDecimal(0.50),LocalDate.now() , LocalDate.of(2019, 01, 30),soup,2,bread);
		
		buyXGetYHalfPriceDiscount.applyDiscount(order);
		LineItem lineItem = order.getLineItems().get(1);
		BigDecimal expectedDiscount = new BigDecimal(0.0).setScale(2, RoundingMode.HALF_EVEN);
		
		assertEquals(lineItem.getDiscountAmount().setScale(2, RoundingMode.HALF_EVEN),expectedDiscount);
		assertEquals(order.getDiscountDetails().size(),0);
	}
	
	private Order createOrderItem(Product product1 , int quantity1, Product product2, int quantity2) {
		
		Order order = new Order();
		LineItem lineItem1 = new LineItem();
		
		lineItem1.setProduct(product1);
		lineItem1.setQuantity(quantity1);
		
		LineItem lineItem2 = new LineItem();
		
		lineItem2.setProduct(product2);
		lineItem2.setQuantity(quantity2);
		
		List<LineItem> lineItems = new ArrayList<>();
		
		lineItems.add(lineItem1);
		lineItems.add(lineItem2);
		order.setLineItems(lineItems);
		return order;
	}
	

}
