package tech;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.Info;
import data.Tuple;

public class Bringer {

	private static final String RELATORIO = "/home/ihealth/Downloads/planilha.csv";
	private static final String CARDS_FILE = "/home/ihealth/Downloads/cards.txt";

	public static void main(String[] args) throws Exception {
		Map<String, Info> map = new HashMap<String, Info>();
		
		Collection<Tuple> cards = createList();
		
		for (Tuple card : cards) {
			getCardInfo(map, card);
		}
		
		fillMap(map, cards);
		
		saveFile(map, cards);
	}

	private static Collection<Tuple> createList() throws FileNotFoundException, IOException {
		Collection<Tuple> cards = new TreeSet<Tuple>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(CARDS_FILE))) {
			String line = br.readLine();

		    while (line != null && !line.isEmpty()) {
		        Pattern pat = Pattern.compile("([0-9]+) (.*)");
		        Matcher m = pat.matcher(line);
		        
		        if (m.find()) {
		            String qtd = m.group(1);
		            String card = m.group(2);
		            
		            Tuple t = new Tuple(card, Integer.parseInt(qtd), BigDecimal.ZERO);
		            
		            cards.add(t);
		        } else {
		        	throw new RuntimeException("Arquivo fora de estrutura");
		        }
		        
		        line = br.readLine();
		    }
		}
		
		return cards;
	}

	private static void fillMap(Map<String, Info> map, Collection<Tuple> cards) {
		for (String store : map.keySet()) {
			Info info = map.get(store);
			
			for (Tuple tuple : cards) {
				if (!info.containsCard(tuple.getCard())) {
					info.addLowest(tuple);
				}
			}
		}
	}

	private static void saveFile(Map<String, Info> map, Collection<Tuple> tuples) throws FileNotFoundException {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Loja;");
		for (Tuple card : tuples) {
			sb.append(card + ";");
		}
		
		sb.append("Total;\n");
		
		for (String store : map.keySet()) {
			sb.append(store + ";");
			BigDecimal v = BigDecimal.ZERO;
			
			for (Tuple t : map.get(store).getList()) {
				BigDecimal total = t.getValue().multiply(new BigDecimal(t.getQuantity()));
				sb.append(total + ";");
				v = v.add(total);
			}
			
			sb.append(v + ";\n");
		}
		
		PrintWriter out = new PrintWriter(RELATORIO);
		out.println(sb.toString());
		out.close();
	}

	private static void getCardInfo(Map<String, Info> map, Tuple line) throws UnsupportedEncodingException, IOException, Exception {
		String baseUrl = "https://www.ligamagic.com.br/?view=cards/card&card=";
		String strUrl = baseUrl + URLEncoder.encode(line.getCard(), "UTF-8");
		Document doc = Jsoup.connect(strUrl).timeout(10000).get();
		
		Element table = doc.getElementById("cotacao-1");
		
		if (table == null) {
			throw new Exception("Card " + line + " not found.");
		}
		
		Elements lines = table.getElementsByTag("tbody").first().getElementsByTag("tr");
		
		for (Element elem : lines) {
			Element img = elem.getElementsByClass("banner-loja").first().getElementsByTag("img").first();
			
			String store = img.attr("title");
			String[] priceStr = elem.getElementsByClass("lj b").first().text().split(" ");
			
			BigDecimal price = new BigDecimal(priceStr[priceStr.length - 1].replace(",", "."));
			
			Tuple t = new Tuple(line.getCard(), line.getQuantity(), price);
			
			if (!map.containsKey(store)) {
				map.put(store, new Info());
			}
			
			map.get(store).addLowest(t);
		}
	}
	
}
