import java.util.ArrayList;
import java.util.Scanner;

public class AnalyseText {
	boolean quoting;// this helps with determining if something is a string or not
	Scanner scanner;
	int lineNum = 0;
	ArrayList<Token> tokenList;

	AnalyseText(Scanner scanner) {
		this.scanner = scanner;
		tokenList = new ArrayList<Token>();
		quoting = false;
		analyse();
	}

	private void analyse() {
		checkString();
	}

	private void checkString() {
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			lineNum++;
			quoting = false;

			String charGroup = "";// store quoted text temporarily
			ArrayList<String> fin = new ArrayList<String>();// finalized quote
			char[] ch = line.toCharArray();// make the characters itterable
			for (int i = 0; i < ch.length; i++) {
				if (ch[i] == '"' && !quoting) {
					quoting = true;

				} else if ((ch[i] == '"' && quoting)) {
					quoting = false;
					charGroup += ch[i];

					/*
					 * If chars pass criteria then put them in the token list as a 5(string)
					 */
					tokenList.add(new Token(5, lineNum, charGroup.replace("\"", ""), null, null));

					charGroup = "";
				}

				if (quoting) {
					charGroup += ch[i];
				}

				// Done with string checking

				/*
				 * we can parse other tokens when we are not quoting if we are quoting then we
				 * are dealing with a string and should not be trying to identify tokens within
				 * strings.
				 */

				if (!quoting) {
					// parse other tokens

					// skip the rest of the line if we get a comment
					if (i < ch.length - 1) {
						if (ch[i] == '/' && ch[i + 1] == '/') {
							break;
						}
					}
					// done with comment skipping

					checkUnpairedDelimiters(ch[i], lineNum);
					checkPairedDelimiters(ch[i], lineNum);
					checkOtherPunctuation(ch[i], lineNum);

				}
			}

		}
		for (Token t : tokenList) {
			System.out.println(t.toString());
		}
	}

	private void checkUnpairedDelimiters(char ch, int lineNum) {
		if (ch == ',') {
			tokenList.add(new Token(6, lineNum, String.valueOf(ch), null, null));
		}

		if (ch == ';') {
			tokenList.add(new Token(7, lineNum, String.valueOf(ch), null, null));
		}
	}

	private void checkPairedDelimiters(char ch, int lineNum) {
		if (ch == '<') {
			tokenList.add(new Token(31, lineNum, String.valueOf(ch), null, null));
		}

		if (ch == '>') {
			tokenList.add(new Token(32, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == '{') {
			tokenList.add(new Token(33, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == '}') {
			tokenList.add(new Token(34, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == '[') {
			tokenList.add(new Token(35, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == ']') {
			tokenList.add(new Token(36, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == '(') {
			tokenList.add(new Token(37, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == ')') {
			tokenList.add(new Token(38, lineNum, String.valueOf(ch), null, null));
		}
	}

	private void checkOtherPunctuation(char ch, int lineNum) {
		if (ch == '*') {
			tokenList.add(new Token(41, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == '^') {
			tokenList.add(new Token(42, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == ':') {
			tokenList.add(new Token(43, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == '.') {
			tokenList.add(new Token(44, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == '=') {
			tokenList.add(new Token(45, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == '-') {
			tokenList.add(new Token(46, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == '+') {
			tokenList.add(new Token(47, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == '/') {
			tokenList.add(new Token(48, lineNum, String.valueOf(ch), null, null));
		}
	}

}
