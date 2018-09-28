import java.util.ArrayList;
import java.util.Scanner;

public class AnalyseText {
	boolean quoting;// this helps with determining if something is a string or not
	boolean gettingID;
	boolean gettingNum;
	boolean checkingKeyWords;
	Scanner scanner;
	int lineNum = 0;
	ArrayList<Token> tokenList;
	ArrayList<Character> digits;
	ArrayList<Character> letters;

	AnalyseText(Scanner scanner) {
		this.scanner = scanner;
		tokenList = new ArrayList<Token>();
		quoting = false;
		gettingID = false;
		gettingNum = false;
		checkingKeyWords = false;
		digits = new ArrayList<Character>();
		digits.add('0');
		digits.add('1');
		digits.add('2');
		digits.add('3');
		digits.add('4');
		digits.add('5');
		digits.add('6');
		digits.add('7');
		digits.add('8');
		digits.add('9');

		letters = new ArrayList<Character>();
		letters.add('a');
		letters.add('b');
		letters.add('c');
		letters.add('d');
		letters.add('e');
		letters.add('f');
		letters.add('g');
		letters.add('h');
		letters.add('i');
		letters.add('j');
		letters.add('k');
		letters.add('l');
		letters.add('m');
		letters.add('n');
		letters.add('o');
		letters.add('p');
		letters.add('q');
		letters.add('r');
		letters.add('s');
		letters.add('t');
		letters.add('u');
		letters.add('v');
		letters.add('w');
		letters.add('x');
		letters.add('y');
		letters.add('z');

		letters.add('A');
		letters.add('B');
		letters.add('C');
		letters.add('D');
		letters.add('E');
		letters.add('F');
		letters.add('G');
		letters.add('H');
		letters.add('I');
		letters.add('J');
		letters.add('K');
		letters.add('L');
		letters.add('M');
		letters.add('N');
		letters.add('O');
		letters.add('P');
		letters.add('Q');
		letters.add('R');
		letters.add('S');
		letters.add('T');
		letters.add('U');
		letters.add('V');
		letters.add('W');
		letters.add('X');
		letters.add('Y');
		letters.add('Z');

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
			String keyWordGroup="";//store potential key word chars;
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
					if(ch[i]== ' ') {
						//TODO: check for keywords then check for nums and ids
						keyWordGroup ="";
					} else {
						keyWordGroup +=ch[i];
					}
					if (!checkingKeyWords) {
						// parse other tokens

						// skip the rest of the line if we get a comment
						if (i < ch.length - 1) {
							if (ch[i] == '/' && ch[i + 1] == '/') {
								break;
							}
						}
						// done with comment skipping

						checkUnpairedDelimiters(ch[i], lineNum);
						if (ch.length > 1) {
							if (i < 1) {
								checkPairedDelimiters(ch[i], '`', ch[i + 1], lineNum);
							} else if (i == ch.length - 1) {
								checkPairedDelimiters(ch[i], ch[i - 1], '`', lineNum);// if there is no next char then
																						// pass
																						// an
																						// unUsed char to make compares
																						// correct
							} else {
								checkPairedDelimiters(ch[i], ch[i - 1], ch[i + 1], lineNum);
							}

							if (i < 1) {
								checkOtherPunctuation(ch[i], '`', ch[i + 1], lineNum);
							} else if (i == ch.length - 1) {
								checkOtherPunctuation(ch[i], ch[i - 1], '`', lineNum);// if there is no next char then
																						// pass
																						// an
																						// unUsed char to make compares
																						// correct
							} else {
								checkOtherPunctuation(ch[i], ch[i - 1], ch[i + 1], lineNum);
							}
						} else if (ch.length == 1) {
							checkPairedDelimiters(ch[i], '`', '`', lineNum);
							checkOtherPunctuation(ch[i], '`', '`', lineNum);

						}
						if (i < ch.length - 1) {
							checkMultiCharOps(ch[i], ch[i + 1], lineNum);
						}
					}
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

	private void checkPairedDelimiters(char ch, char preChar, char nxtChar, int lineNum) {
		if (ch == '<' && preChar != '<' && nxtChar != '<' && nxtChar != '=') {
			tokenList.add(new Token(31, lineNum, String.valueOf(ch), null, null));
		}

		if (ch == '>' && preChar != '-' && preChar != '>' && nxtChar != '>' && nxtChar != '=') {
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

	private void checkOtherPunctuation(char ch, char preChar, char nxtChar, int lineNum) {
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
		if (ch == '=' && nxtChar != '=' && preChar != '<' && preChar != '>' && preChar != '=') {// special
			tokenList.add(new Token(45, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == '-' && nxtChar != '>') {
			tokenList.add(new Token(46, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == '+') {
			tokenList.add(new Token(47, lineNum, String.valueOf(ch), null, null));
		}
		if (ch == '/') {
			tokenList.add(new Token(48, lineNum, String.valueOf(ch), null, null));
		}
	}

	private void checkMultiCharOps(char ch, char nxtChar, int lineNum) {
		if (ch == '-' && nxtChar == '>') {
			tokenList.add(new Token(51, lineNum, "->", null, null));
		}
		if (ch == '=' && nxtChar == '=') {
			tokenList.add(new Token(52, lineNum, "==", null, null));// special
		}
		if (ch == '!' && nxtChar == '=') {
			tokenList.add(new Token(53, lineNum, "!=", null, null));// special
		}
		if (ch == '<' && nxtChar == '=') {
			tokenList.add(new Token(54, lineNum, "<=", null, null));// special
		}
		if (ch == '>' && nxtChar == '=') {
			tokenList.add(new Token(55, lineNum, ">=", null, null));// special
		}
		if (ch == '<' && nxtChar == '<') {
			tokenList.add(new Token(56, lineNum, "<<", null, null));// special
		}
		if (ch == '>' && nxtChar == '>') {
			tokenList.add(new Token(57, lineNum, ">>", null, null));// special
		}
	}

}
