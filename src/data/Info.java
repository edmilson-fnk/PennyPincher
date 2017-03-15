package data;

import java.math.BigDecimal;
import java.util.TreeSet;

public class Info {
	
	private TreeSet<Tuple> list;
	private BigDecimal value;

	public Info() {
		this.list = new TreeSet<Tuple>();
		this.value = BigDecimal.ZERO;
	}
	
	public TreeSet<Tuple> getList() {
		return list;
	}

	public void setList(TreeSet<Tuple> list) {
		this.list = list;
	}

	public void addLowest(Tuple t) {
		Tuple card = null;
		for (Tuple e : list) {
			if (e.getCard().equalsIgnoreCase(t.getCard())) {
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
		for (Tuple t : list) {
			if (t.getCard().equalsIgnoreCase(card)) {
				return true;
			}
		}
		
		return false;
	}
	
}
