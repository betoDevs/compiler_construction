import java.io.BufferedReader; 
import java.io.File;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;



public class Lexer{
	public static void main(String[] args){
		Map<Integer, String> dict = createKeyWordDict();
		
		for(Map.Entry<Integer,String> i: dict.entrySet()){
			System.out.println("key = " + i.getKey() +
								", value = " + i.getValue());
		}

		String s = createToken("10", 1, "prog");
		System.out.println(s);
		Map<List<String>, Integer> j = read("test.txt");
	}

	public static Map<List<String>, Integer> read(String path){
		Map<List<String>, Integer> s = new HashMap<>();
		String current;
		File file = new File(path);
		try {
			Scanner read = new Scanner(file);
			//read.useDelimiter(" ");
			int lineCount = 1;
			while(read.hasNext()){
				current = read.nextLine();
				List<String> tmp = new ArrayList<String>(Arrays.asList(current.split(" ")));
				s.put(tmp, lineCount);
				System.out.println("Adding: " + tmp + " at line " + lineCount);
				lineCount++;
				tmp.clear();
			}
			read.close();
			return s;
		}
		catch(IOException e)
		{
			System.out.println("Something wrong with path");
			return s;
		}
	}

	//map keywork to designated token
	public static Map<Integer, String> createKeyWordDict(){
		Map<Integer, String> dict = new HashMap<>();
		//2-5 are ambigous.
		dict.put(2, "id");
		dict.put(3, "Integer");
		dict.put(4, "Float");
		dict.put(5, "String");

		//one keyword per key value.
		dict.put(10, "prog");
		dict.put(11, "main");
		dict.put(12, "fcn");
		dict.put(13, "class");
		dict.put(15, "float");
		dict.put(16, "int");
		dict.put(17, "string");
		dict.put(18, "if");
		dict.put(19, "elseif");
		dict.put(20, "else");
		dict.put(21, "while");
		dict.put(22, "input");
		dict.put(23, "print");
		dict.put(24, "new");
		dict.put(25, "return");
		dict.put(26, "var");
		dict.put(6, ",");
		dict.put(7, ";");
		dict.put(31, "<");
		dict.put(32, ">");
		dict.put(33, "{");
		dict.put(34, "}");
		dict.put(35, "[");
		dict.put(36, "]");
		dict.put(37, "(");
		dict.put(38, ")");
		dict.put(41, "*");
		dict.put(42, "^");
		dict.put(43, ":");
		dict.put(44, ".");
		dict.put(45, "=");
		dict.put(46, "-");
		dict.put(47, "+");
		dict.put(48, "/");
		dict.put(51, "->");
		dict.put(52, "==");
		dict.put(53, "!=");
		dict.put(54, "<=");
		dict.put(55, ">=");
		dict.put(56, "<<");
		dict.put(57, ">>");
		return dict;
	}

	//needs to format the output to match indentation
	public static String createToken(String id, int line, String token){
		return "(Tok: " + id + " line= " + Integer.toString(line) + " str= \""+token+"\")";
	}
}