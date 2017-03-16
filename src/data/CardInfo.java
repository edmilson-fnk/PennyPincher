package data;

import java.math.BigDecimal;

public class CardInfo implements Comparable<CardInfo> {

	private BigDecimal value;
	private String cardName;
	private boolean hasCard;
	private int quantity;
	
	public CardInfo(String card, int quantity, BigDecimal price) {
		this.cardName = card;
		this.value = price;
		this.quantity = quantity;
		this.hasCard = price == BigDecimal.ZERO ? false : true;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
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
		this.hasCard = value != null && value.compareTo(BigDecimal.ZERO) == 0 ? false : true;
	}

	public boolean hasCard() {
		return hasCard;
	}

	@Override
	public int compareTo(CardInfo t) {
		return this.toString().compareTo(t.toString());
	}
	
	@Override
	public String toString() {
		return this.cardName + " - " + this.quantity;
	}
}
