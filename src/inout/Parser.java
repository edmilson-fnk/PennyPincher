package inout;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.CardInfo;
import data.Info;

public class Parser {

	public static Collection<CardInfo> createListFromFile(String filePath) throws Exception {
		Collection<CardInfo> cards = new TreeSet<CardInfo>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        Pattern pat = Pattern.compile("([0-9]+) (.*)");
			String line = br.readLine();
			int l = 0;

		    while (line != null && !line.isEmpty()) {
				l++;
				cards.add(parseLine(cards, line, l, pat));
		        
		        line = br.readLine();
		    }
		}
		
		return cards;
	}

	private static CardInfo parseLine(Collection<CardInfo> cards, String line, int lineNumber, Pattern pat) throws Exception {
		Matcher matcher = pat.matcher(line);
		
		if (matcher.find()) {
		    String qtd = matcher.group(1);
		    String card = matcher.group(2);
		    
		    return new CardInfo(card, Integer.parseInt(qtd), BigDecimal.ZERO);
		} else {
			throw new Exception("Line " + lineNumber + " out of structure. Try using \"{number} {name}\", just like in \"2 Mox Opal\"");
		}
	}
	
	public static StringBuilder createSheetContent(Map<String, Info> map, Collection<CardInfo> tuples) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Loja;");
		for (CardInfo card : tuples) {
			sb.append(card + ";");
		}
		
		sb.append("Total;\n");
		
		for (String store : map.keySet()) {
			sb.append(store + ";");
			BigDecimal v = BigDecimal.ZERO;
			
			for (CardInfo t : map.get(store).getList()) {
				BigDecimal total = t.getValue().multiply(new BigDecimal(t.getQuantity()));
				sb.append(total + ";");
				v = v.add(total);
			}
			
			sb.append(v + ";\n");
		}
		return sb;
	}
}
