package data;

import java.math.BigDecimal;

public class CardInfo implements Comparable<CardInfo> {

	private BigDecimal price;
	private String cardName;
	private int quantity;
	private boolean hasCard;
	
	private String option;
	
	public CardInfo(String card, int quantity, BigDecimal price) {
		this.cardName = card;
		this.price = price;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
		this.hasCard = price != null && price.compareTo(BigDecimal.ZERO) == 0 ? false : true;
	}

	public boolean hasCard() {
		return hasCard;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
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
