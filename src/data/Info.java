package data;

import java.math.BigDecimal;
import java.util.TreeSet;

public class Info {
	
	private TreeSet<CardInfo> list;
	private BigDecimal value;

	public Info() {
		this.list = new TreeSet<CardInfo>();
		this.value = BigDecimal.ZERO;
	}
	
	public TreeSet<CardInfo> getList() {
		return list;
	}

	public void setList(TreeSet<CardInfo> list) {
		this.list = list;
	}

	public void addLowest(CardInfo t) {
		CardInfo card = null;
		for (CardInfo e : list) {
			if (e.getCardName().equalsIgnoreCase(t.getCardName())) {
				card = e;
				break;
			}
		}
		
		if (card == null) {
			this.list.add(t);
			this.value = this.value.add(t.getValue());
		} else if (t.getValue().compareTo(card.getValue()) < 0) {
			this.value = this.value.subtract(card.getValue());
			card.setValue(t.getValue());
			this.value = this.value.add(t.getValue());
		}
	}
	
	public boolean containsCard(String card) {
		for (CardInfo t : list) {
			if (t.getCardName().equalsIgnoreCase(card)) {
				return true;
			}
		}
		
		return false;
	}
	
}
