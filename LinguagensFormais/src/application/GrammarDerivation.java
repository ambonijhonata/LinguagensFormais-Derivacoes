package application;

import java.util.*;

public class GrammarDerivation {
	private Map<String, List<String>> productions;

	public GrammarDerivation(Map<String, List<String>> productions) {
		this.productions = productions;
	}

	public String deriveSentence(String symbol) {
		StringBuilder derivation = new StringBuilder();
		generateDerivationUsingStack(symbol, derivation);
		return derivation.toString();
	}

	private void generateDerivationUsingStack(String symbol, StringBuilder derivation) {
		Deque<String> stack = new ArrayDeque<>();
		stack.push(symbol);

		while (!stack.isEmpty()) {
			String currentSymbol = stack.pop();

			if (!productions.containsKey(currentSymbol)) {
				derivation.append(currentSymbol).append(" ");
			} else {
				List<String> choices = productions.get(currentSymbol);

				// Itera sobre todas as produções em ordem
				for (String choice : choices) {
					String[] symbols = choice.trim().split("\\s+");
					for (int i = symbols.length - 1; i >= 0; i--) {
						stack.push(symbols[i]);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Informe as regras de produção da gramática:");
		Map<String, List<String>> productions = new HashMap<>();
		String line;

		while (!(line = scanner.nextLine()).isEmpty()) {
			String[] parts = line.split("::=");
			String nonTerminal = parts[0].trim();
			List<String> choices = Arrays.asList(parts[1].trim().split("\\|"));
			productions.put(nonTerminal, choices);
		}

		GrammarDerivation grammarDerivation = new GrammarDerivation(productions);

		System.out.println("Informe o símbolo inicial:");
		String initialSymbol = scanner.nextLine();

		String derivedSentence = grammarDerivation.deriveSentence(initialSymbol);
		System.out.println("Derivation: " + derivedSentence);
	}
}
