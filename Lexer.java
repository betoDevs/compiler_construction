import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Lexer {


	public static void main(String[] args) {
		AnalyseText at;

		try {
			Scanner scanner = new Scanner(new File("src/text.txt"));
			at = new AnalyseText(scanner);
		} catch (FileNotFoundException e) {
			System.err.println("File not found.");
			System.exit(0);
		}
//		Token token = new Token(0, 0, "dude", null, 10.1f);
//		System.out.println(token.toString());
		
		
	}

}
