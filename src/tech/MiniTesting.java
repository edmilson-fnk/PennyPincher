package tech;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiniTesting {

	public static void main(String[] args) {
		String line = "1 [Foil] Throne of the God-Pharaoh";
		
		Pattern pat = Pattern.compile("([0-9]+) \\[(.*)\\] (.*)");
		Matcher matcher = pat.matcher(line);
		
		if (matcher.find()) {
			String qtd = matcher.group(1);
			String tipo = matcher.group(2);
			String card = matcher.group(3);
			
			System.out.println("Qtd : " + qtd + "\nTipo: " + tipo + "\nCard: " + card);
		} else {
			System.out.println("Erro");
		}
	    
	}
	
}
