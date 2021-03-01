import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Card {
	
	private String color;
	private int value;
	boolean isChosen;
	ImageIcon icon;
	

	
	Card() {
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
	
	public ImageIcon getIcon() throws IOException
	{
		String end;
		//if(this.getColor().equals("spade"))  end = "es";
		//else end = "s";
		String val ="";
    	if(this.value == 11) val = "jack";
    	if(this.value == 12) val = "queen";
    	if(this.value == 13) val = "king";
    	if(this.value == 14) val = "ace";
    	if(this.value < 11) val = String.valueOf(this.value);
		
		String url = "img/" + val +"_of_" +this.color + "s" + ".png";
		BufferedImage myPicture = ImageIO.read(new File(url));
		Image image = myPicture;
		this.icon = new ImageIcon(image.getScaledInstance(57, 88, Image.SCALE_SMOOTH));
		return icon;
		
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
