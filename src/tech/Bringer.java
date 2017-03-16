package tech;

import java.util.Collection;
import java.util.Map;

import data.CardInfo;
import data.Gatherer;
import data.Info;
import inout.Operations;
import inout.Parser;

public class Bringer {

	private static final String REPORT = "sheet.csv";
	private static final String CARDS_FILE = "cards.txt";

	public static void main(String[] args) throws Exception {
		Collection<CardInfo> cards = Parser.createListFromFile(CARDS_FILE);
		
		Map<String, Info> map = Gatherer.getCardsInfo(cards);
		
		fillMap(map, cards);
		
		StringBuilder sb = Parser.createSheetContent(map, cards);
		Operations.saveFile(sb.toString(), REPORT);
	}

	private static void fillMap(Map<String, Info> map, Collection<CardInfo> cards) {
		for (String store : map.keySet()) {
			Info info = map.get(store);
			
			for (CardInfo tuple : cards) {
				if (!info.containsCard(tuple.getCardName())) {
					info.addLowest(tuple);
				}
			}
		}
	}

}
