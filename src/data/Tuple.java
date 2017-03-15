package data;

import java.math.BigDecimal;

public class Tuple implements Comparable<Tuple> {

	private String card;
	private int quantity;
	private BigDecimal value;
	private boolean hasCard;
	
	public Tuple(String card, int quantity, BigDecimal price) {
		this.card = card;
		this.value = price;
		this.quantity = quantity;
		this.hasCard = price == BigDecimal.ZERO ? false : true;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
		this.hasCard = value == BigDecimal.ZERO ? false : true;
	}

	public boolean isHasCard() {
		return hasCard;
	}

	@Override
	public int compareTo(Tuple t) {
		return this.toString().compareTo(t.toString());
	}
	
	@Override
	public String toString() {
		return this.card + " - " + this.quantity;
	}
}
