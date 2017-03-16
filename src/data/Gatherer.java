package data;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Gatherer {

	public static Map<String, Info> getCardsInfo(Collection<CardInfo> cards) throws UnsupportedEncodingException, IOException, Exception {
		Map<String, Info> map = new HashMap<>();
		
		for (CardInfo card : cards) {
			getCardInfo(map, card);
		}
		
		return map;
	}

	
	public static void getCardInfo(Map<String, Info> map, CardInfo cardInfo) throws UnsupportedEncodingException, IOException, Exception {
		String baseUrl = "https://www.ligamagic.com.br/?view=cards/card&card=";
		String strUrl = baseUrl + URLEncoder.encode(cardInfo.getCardName(), "UTF-8");
		Document doc = Jsoup.connect(strUrl).timeout(10000).get();
		
		Element table = doc.getElementById("cotacao-1");
		
		if (table == null) {
			throw new Exception("Card " + cardInfo + " not found.");
		}
		
		Elements lines = table.getElementsByTag("tbody").first().getElementsByTag("tr");
		
		for (Element elem : lines) {
			Element img = elem.getElementsByClass("banner-loja").first().getElementsByTag("img").first();
			
			String store = img.attr("title");
			String[] priceStr = elem.getElementsByClass("lj b").first().text().split(" ");
			
			BigDecimal price = new BigDecimal(priceStr[priceStr.length - 1].replace(",", "."));
			
			CardInfo t = new CardInfo(cardInfo.getCardName(), cardInfo.getQuantity(), price);
			
			if (!map.containsKey(store)) {
				map.put(store, new Info());
			}
			
			map.get(store).addLowest(t);
		}
	}
	
}
