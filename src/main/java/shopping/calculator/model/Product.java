package shopping.calculator.model;

import java.math.BigDecimal;
import java.util.Objects;

import shopping.calculator.enums.Uom;

public class Product {
	
	private String name;
	private BigDecimal price;
	private Uom unitOfMeasurement;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Uom getUnitOfMeasurement() {
		return unitOfMeasurement;
	}
	public void setUnitOfMeasurement(Uom unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}
	
	// making a simple implementation of equals based on name attribute
	@Override
	public boolean equals(Object obj) {
		return this.name.toUpperCase().equals( ((Product)obj).name.toUpperCase()); 
	}
	
	@Override
	public int hashCode(){
	    return Objects.hashCode(this.name);
	}


}
