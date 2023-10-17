package application;

import java.util.*;

public class Main {
	private static Map<String, String[]> productions;
	private final static String[] examples = {"S:= mbS | ifS | ncB; A:= qzB | po | xb; B:= wd | bvB | jvS;", "S:= daA | wkS | ndS; A:= gp | cb | abA; B:= cyB | tf | wv;", "S:= zhS | iiA | crS; A:= av | hs | eaB; B:= uf | jr | hhB;"};
	private static final String startSymbol = "S";
	
	public Main(Map<String, String[]> productions) {
		this.productions = productions;
	}

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        productions = new HashMap<>();
        
        System.out.println("Selecione uma opção:");
        System.out.println("1) Gerar sentenças aleatórias: ");
    	System.out.println("2) Informar gramática: (Exemplo: S:= Cde | deA; A:= Ea | D | S; C:= ded; D:=deA|S; E:=ead;)");
    	System.out.println("0) Para sair");
    	
        int option = scanner.nextInt();        
        
        while(option != 0) {        	
        	if(option == 1) {
        		int contador = 1;
        		for(String s : examples) {        			
        			System.out.println("Exemplo #" + contador);
        			System.out.println("Gramática: " + s);
        			formatGrammarInProgram(s);
        			generateRandomSentence();
        			productions.clear();
        			contador++;
        		}
        	} else if(option == 2) {
        		System.out.println("Informe as regras de produção da gramática (Exemplo: S:= Cde | deA; A:= Ea | D | S; C:= ded; D:=deA|S; E:=ead):");
        		scanner.nextLine();
        		String grammar = scanner.nextLine();
        		formatGrammarInProgram(grammar);
        		String sentence = generateRandomSentence();
        		System.out.println(sentence);
        	}
        	
        	System.out.println();
        	System.out.println("Selecione uma opção:");
            System.out.println("1) Gerar sentenças aleatórias: ");
        	System.out.println("2) Informar gramática: Exemplo: S:= Cde | deA; A:= Ea | D | S; C:= ded; D:=deA|S; E:=ead):");
        	System.out.println("0) Para sair");        	
        	option = scanner.nextInt();
        }
    }
	
	public static void formatGrammarInProgram(String gramamr) {		
		String[] groupTerminalsAndNotTerminals = gramamr.trim().split(";");
        
        for(String s : groupTerminalsAndNotTerminals) {
        	String[] parts = s.split(":=");
        	String nonTerminal = parts[0].trim();
        	String[] terminals = parts[1].trim().split("\\s*\\|\\s*");       
        	productions.put(nonTerminal, terminals);            	
        }   
	}

	private static boolean isTerminal(Character currentSymbol) {
		return !productions.containsKey(currentSymbol.toString());
	}	
	
	public static String generateRandomSentence() {
		Stack<Character> stack = new Stack<>();
		StringBuilder output = new StringBuilder();

		String[] productionsByTerminal = productions.get(startSymbol);
		String selectionProduction = productionsByTerminal[new Random().nextInt(productionsByTerminal.length)];
		
		Character emptySimbol = 'ε';
		if(selectionProduction.equals(emptySimbol)) {
			return output.append(emptySimbol).toString();			
		}
		
		for (int i = selectionProduction.length() - 1; i >= 0; i--) {
			stack.push(selectionProduction.charAt(i));
		}

		while (!stack.isEmpty()) {
			Character currentSymbol = stack.pop();

			if(currentSymbol.equals(emptySimbol)) {
				return output.toString();
			}
			
			if (isTerminal(currentSymbol)) {
				output.append(currentSymbol);
			} else {
				String[] choices = productions.get(currentSymbol.toString());
				String chosenProduction = choices[new Random().nextInt(choices.length)];

				for (int i = chosenProduction.length() - 1; i >= 0; i--) {
					stack.push(chosenProduction.charAt(i));
				}
			}
		}

		return output.toString();
	}

}