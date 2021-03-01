
public class Card {
	private String color;
	private int value;
	boolean isChosen;
	
	Card(){
		isChosen = false;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	public String valToString()
	{
		String val ="";
    	if(this.value == 11) val = "W";
    	if(this.value == 12) val = "D";
    	if(this.value == 13) val = "K";
    	if(this.value == 14) val = "A";
    	if(this.value < 11) val = String.valueOf(this.value);
    	
    	return val;
	}	
    
	
    public String toString(){	
		
		String number = Integer.toString(this.value);
		
    	String val ="";
    	if(this.value == 11) val = "W";
    	if(this.value == 12) val = "D";
    	if(this.value == 13) val = "K";
    	if(this.value == 14) val = "A";
    	if(this.value < 11) val = String.valueOf(this.value);
		
    	String col ="";
    	if(this.color.equals("spade")) col = "\u2660";
    	if(this.color.equals("diamond")) col = "\u2666";
    	if(this.color.equals("club")) col = "\u2663";
    	if(this.color.equals("heart")) col = "\u2665";
		
		
    	String s =
    			String.format("┌─────────┐\n"
    			+ 			  " │%s              │\n"
    			+ 			  " │                 │\n"
    			+			  " │      %s        │\n"
    			+ 		 	  " │                 │\n"
    			+ 			  " │             %s │\n"
    			+ 		 	  " └─────────┘\n", val,col,val);

    	
    	String ss =
    			String.format(" 		┌─────────┐\n"
    			+ 			  " 		│%s                │\n"
    			+ 		 	  "  		│                  │\n"
    			+			  " 		│        %s       │\n"
    			+ 		 	  "  		│                  │\n"
    			+ 			  "  		│               %s │\n"
    			+ 		 	  "  		└─────────┘\n", val,col,val);
    	
    	if(isChosen) 
    		return ss;
    	
    	else return s;

    }
	/* console format
	String.format("┌─────────┐\n"
	+ 			  " │%s        │\n"
	+ 			  " │         │\n"
	+ 		 	  " │         │\n"
	+			  " │    %s    │\n"
	+ 			  " │         │\n"
	+ 		 	  " │         │\n"
	+ 			  " │       %s │\n"
	+ 		 	  " └─────────┘\n", val,col,val);
	
	    			String.format(" 		┌─────────┐\n"
	+ 			  " 		│%s        │\n"
	+ 			  " 		│         │\n"
	+ 		 	  "  		│         │\n"
	+			  " 		│    %s    │\n"
	+ 			  "  		│         │\n"
	+ 		 	  "  		│         │\n"
	+ 			  "  		│       %s │\n"
	+ 		 	  "  		└─────────┘\n", val,col,val);
*/
}
