
public class Token {
	int id, lineNum;
	// The next 2 are objects instead of primitives so we can set them to null to have a dynamically changing toString
	Integer intValue; 
	Float floatValue;
	
	String tokenText; 
	
	public Token(int id, int lineNum, String tokenText, Integer intValue, Float floatValue) {
		this.id = id;
		this.lineNum = lineNum;
		this.tokenText = tokenText;
		this.intValue = intValue;
		this.floatValue = floatValue;
	}
	
	public String toString() {
		
		String ret = "(Tok: " + this.id + " " + "line= " + this.lineNum 
				+ " " + "str= " + "\""+this.tokenText +"\"";
		
		if(!(this.intValue == null)) {
			ret += " int= " + this.intValue;
		}
		
		if(!(this.floatValue == null)) {
			ret += " float= " + this.floatValue;
		}
		
		ret += ")";
		
		
		return ret;
	}
}
