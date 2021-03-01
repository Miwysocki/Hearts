import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Exchanger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
	
	public static List<Card> Deck;
	static Player human, c1 ,c2 ,c3;
	public static int chosenIndex;
	static int round, load;//1,2,3,4
	public static Player begins ;
	public static boolean heartsAllowed;
	public static boolean entered, space, loadedState;

	
	JFrame window;
	Container con;
	JPanel playPanel,humanPanel,c1Panel, c2Panel, c3Panel, textPanel;
	static JTextArea humanArea;
	static JTextArea humanArea2;
	static JLabel label;
	static JTextArea humanPlayed,c1Area, c2Area, c3Area;
	
	static void initDeck() 
	{
		Deck = new LinkedList<Card>();
		for (int j = 0; j < 4; j++) 
		{
			String col="";
			
			switch (j) { 
			case 0: 
				col = "diamond";
				break;
			case 1:
				col = "heart";
				break;
			case 2:
				col = "club";
				break;
			case 3:
				col = "spade";
				break;
			}
				 
			for(int i = 2; i <= 14; i++) 
			{
				Card e = new Card();
				e.setColor(col);
				e.setValue(i);
				e.isChosen = false;
				Deck.add(e);
			}
						
		}
		//shuffle
		Collections.shuffle(Deck);
	}
	
	static void initPlayers() {
		 human = new Player("human");
		 human.playerArea = humanPlayed;
		 c1 = new Player("c1");
		 c1.playerArea = c1Area;
		 c2 = new Player("c2");
		 c2.playerArea = c2Area;
		 c3 = new Player("c3");
		 c3.playerArea = c3Area;
		 
	}
	
	static void deal() {
		for(int i = 1 ; i <= 13; i++ ) {
			Card n = Deck.remove(0);
			human.hand.add(n);
		}
		for(int i = 1 ; i <= 13; i++ ) {
			Card n = Deck.remove(0);
			c1.hand.add(n);
		}
		for(int i = 1 ; i <= 13; i++ ) {
			Card n = Deck.remove(0);
			c2.hand.add(n);
		}
		for(int i = 1 ; i <= 13; i++ ) {
			Card n = Deck.remove(0);
			c3.hand.add(n);
		}
		
		human.cardsOnHand = 13;
	}
	
	static void showCards() {
		for(int i=0; i<13; i++)
		{
			System.out.println( " " + human.hand.get(i).toString() );
		}
	}
	
	static void cardComparison(Card hc,Card c1c,Card c2c,Card c3c, Card startCard, Player startPlayer) {
		Player taker = startPlayer;
		int highest = startCard.getValue();
		int hearts=0;
		boolean spadeDame = false;
		
		if(hc.getColor().equals("heart")) hearts++;
		if(c1c.getColor().equals("heart")) hearts++;
		if(c2c.getColor().equals("heart")) hearts++;
		if(c3c.getColor().equals("heart")) hearts++;
		
		if(hc.getColor().equals("spade") && hc.getValue() == 12) 
			{spadeDame = true;}
		if(c1c.getColor().equals("spade") && c1c.getValue() == 12)
			spadeDame = true;
		if(c2c.getColor().equals("spade") && c2c.getValue() == 12) 
			spadeDame = true;
		if(c3c.getColor().equals("spade") && c3c.getValue() == 12)
			spadeDame = true;
		
		
		for(int i=0; i< 2;i++) //selecting the highest
		{
			if(hc.getColor().equals(startCard.getColor())  && hc.getValue() > highest) {
				taker = human;
				highest = hc.getValue();
				}
			if(c1c.getColor().equals(startCard.getColor()) && c1c.getValue() > highest) {
				taker = c1;
				highest = c1c.getValue();
				}
			if(c2c.getColor().equals(startCard.getColor()) && c2c.getValue() > highest) {
				taker = c2;
				highest = c2c.getValue();
				}
			if(c3c.getColor().equals(startCard.getColor()) && c3c.getValue() > highest) {
				taker = c3;
				highest = c3c.getValue();
				}
		}
		
		 if(spadeDame) taker.points += 13;
		 taker.points += hearts;
		 begins = taker;
	}
	
	static void  exchange() {
		if(round != 4) {
			System.out.println("choose 3 cards");
			label.setText("choose 3 cards");
			Card card1,card2,card3, g1,g2,g3;//given by comp
			card1 = chooseForExchange();
			human.hand.remove(chosenIndex);
			showHumanCards();
			//human.hand.remove(card1);
			label.setText("choose 2 cards");
			card2 = chooseForExchange();
			human.hand.remove(chosenIndex);
			showHumanCards();
			label.setText("choose 1 card");
			card3 = chooseForExchange();
			human.hand.remove(chosenIndex);
			Player komp = c1;
			
			card1.isChosen = false;
			card2.isChosen = false;
			card3.isChosen = false;
			
			Random generator = new Random();
			int i1,i2,i3;
			i1 =generator.nextInt(13);
			i2 =generator.nextInt(13);
			i3 =generator.nextInt(13);
			
			if(round == 1) {
				komp = c1;
			}
			if(round == 2) {
				komp = c2;
			}
			if(round == 3) {
				komp = c3;
			}
			
			//comp giving
			g1 = komp.hand.get(i1);
			g2 = komp.hand.get(i2);
			g3 = komp.hand.get(i3);
			
			komp.hand.remove(g1);
			komp.hand.remove(g2);
			komp.hand.remove(g3);
			
			
			komp.hand.add(card1);
			komp.hand.add(card2);
			komp.hand.add(card3);
			
			showHumanCards();
			human.hand.add(g1);
			human.hand.add(g2);
			human.hand.add(g3);
			human.cardsOnHand += 3;
			
		}
		
	}

	
	

	
	 static void showHumanCards()
	{
		 String humanCards ="";
		 for (int i = 0; i < human.cardsOnHand && i<7; i++) {
			humanCards += human.hand.get(i).toString();
		}
		 
		 humanArea.setText(humanCards);
		 
		 String humanCards2 ="";
		 for (int i = 7; i < human.cardsOnHand && i >=7; i++) {
			humanCards2 += human.hand.get(i).toString();
		}
		 
		 humanArea2.setText(humanCards2);
		//humanArea.setText(human.hand.get(0).toString()+human.hand.get(2).toString());
		 
		
	}
	
	public Main() {
		window = new JFrame("Hearts <3");
		window.setSize(1500, 1000);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(Color.white);
		window.setLayout(null);
		//window.setLayout(new FlowLayout());
		//window.setLayout(new GridLayout());
		//window.setLayout(new BorderLayout());

		con = window.getContentPane();
		
		playPanel = new JPanel();
		playPanel.setBounds(0,0, 400, 1000);
		playPanel.setBackground(Color.white);
		//playPanel.setForeground(Color.black);
		
		c1Panel = new JPanel();
		c1Panel.setBounds(700,100, 100, 180);
		c1Panel.setBackground(Color.white);
		
		c2Panel = new JPanel();
		c2Panel.setBounds(1300,450, 100, 180);
		c2Panel.setBackground(Color.white);
		
		c3Panel = new JPanel();
		c3Panel.setBounds(700,750, 100, 180);
		c3Panel.setBackground(Color.white);
		
		textPanel = new JPanel();
		textPanel.setBounds(650,450, 200, 50);
		textPanel.setBackground(Color.white);
		
		humanPanel = new JPanel();
		humanPanel.setBounds(300,450, 250, 180);
		humanPanel.setBackground(Color.white);
		
		
		humanArea = new JTextArea();
		humanArea.setBounds(0, 0, 100, 1000);
		humanArea.addKeyListener(new CustomKeyListener());
		playPanel.add(humanArea);
		
		humanArea2 = new JTextArea();
		humanArea2.addKeyListener(new CustomKeyListener());
		humanArea2.setBounds(200, 50, 50, 800);
		playPanel.add(humanArea2);
		
		label = new JLabel();
		textPanel.add(label);

		
		humanPlayed = new JTextArea();
		humanPlayed.setBounds(300, 450, 50, 800);
		humanPanel.add(humanPlayed);
		
		c1Area = new JTextArea();
		c1Area.setBounds(200, 50, 50, 800);
		c1Panel.add(c1Area);
		
		c2Area = new JTextArea();
		c2Area.setBounds(200, 50, 50, 800);
		c2Panel.add(c2Area);
		
		c3Area = new JTextArea();
		c3Area.setBounds(200, 50, 50, 800);
		c3Panel.add(c3Area);
	
		con.add(playPanel);
		con.add(c1Panel);
		con.add(c2Panel);
		con.add(c3Panel);
		con.add(textPanel);
		con.add(humanPanel);

		
		playPanel.addKeyListener(new CustomKeyListener());
		//window.addKeyListener(new CustomKeyListener());
		window.setVisible(true);
	}

	public static void main(String[] args) throws Exception  {

			//initialization
		new Main();
		entered = false;
		space =  false;
		initPlayers();
		begins = c1;
		round =1;
		loadedState = false;
		
		askToLoad();
		entered = false;
		
		while(true) {
			//start new round
			if(!loadedState)
			{
				initDeck();
				deal();
				showHumanCards();
				showCards();
				exchange();
			
				heartsAllowed = false;
				
				switch (round) {
				case 1:
					begins = c1;
					break;
				case 2:
					begins = c2;
					break;
				case 3:
					begins = c3;
					break;
				case 4:
					begins = human;
					break;
				}
			
			} else showHumanCards();
			
			while(human.cardsOnHand != 0) { //1 wziatka
				play();
				space = false;
				if(human.cardsOnHand != 0) writeJson();
				loadedState = false;
			}
			
			
			if(round != 4) round++;
			else round = 1;
			checkPoints();
			
		}
				
				
	}
	
	private static void askToLoad() throws Exception {
		// TODO Auto-generated method stub~
		space = false;
		load = 0;
		label.setText("press space to choose");
		while(!space) 
		{
			System.out.println("choose");
			if(load == 0) {
				human.playerArea.setText(">>>New Game<<< \nLoad Game");
			} else human.playerArea.setText("New Game \n>>>Load Game<<<");
		}
		
		human.playerArea.setText("");
		space = false;
		if(load == 1) readJson();
	}

	static void checkPoints() 
	{

			
			List<Player> playerList = new LinkedList<Player>();
			playerList.add(human);	
			playerList.add(c1);
			playerList.add(c2);
			playerList.add(c3);
			
			Player winner;
			boolean gameOver = false;
			int bestScore = 100;
			
			for (Player player : playerList) {
				player.playerArea.setText("Points: "+ player.points);
				if(player.points >= 100) gameOver = true;
				if(player.points < bestScore) winner = player;
			}
			
		while(!space)
		{
				System.out.println("results");
			if(gameOver) {
				label.setText("Game over!!");
			}
			else label.setText("Press space for next round");
		}						
		
		for (Player player : playerList) {
			player.playerArea.setText("");
		}
		space = false;
	}
	
	public static Card playCard(Player pl, String color) {
		
		if(pl == human) {
			label.setText("Your tourn");
			return chooseCard(color);
		}
		
		if(pl == begins) {
			//play the lowest
			for (int i = 2; i <= 14; i++) { 
				for (Card card : pl.hand) {
					if(!card.getColor().equals("heart") ||(card.getColor().equals("heart") && heartsAllowed) ) {
					if (card.getValue() <= i)
						return card;
					}
				} 
			}
		}
		
		Card lowest = null;
		boolean hasColor=false;
		Card highest;

		for (Card card : pl.hand) {
			if(card.getColor().equals(color)) {
				hasColor = true;
				lowest = card;
			}
		}
		
		if(hasColor) {
			for (Card card : pl.hand) {
				if(card.getColor().equals(color) && card.getValue() < lowest.getValue()) {
					lowest = card;
				}
			}
			return lowest;
		}
		else { //if no color play heart
			for (Card card : pl.hand) {
				if(card.getColor().equals("heart")) {
					return card;
				}
			}
		
			//else play highest
			 highest = pl.hand.get(0);
			for (Card card : pl.hand) {
				if(card.getValue() > highest.getValue()) highest = card;
		}
	}
		return highest;
	}
	
	private static void play() {
		// TODO Auto-generated method stub
		//kolejnosc
		
		Player k2 = null,k3 = null,k4 = null;
		if(begins == c1) {
			
			k2 = c2; k3=c3; k4 = human;
		}
		if(begins == c2) {
			
			k2 = c3; k3 = human; k4 = c1;
		}
		if(begins == c3) {
			k2 = human; k3 = c1; k4 = c2; 
		}
		if(begins == human) {
			k2 = c1; k3 = c2; k4 = c3; 
		}
		
		List<Player> playerList = new LinkedList<Player>();
		playerList.add(begins);	
		playerList.add(k2);
		playerList.add(k3);
		playerList.add(k4);

		String startColor ="";
		Card firstPlayed = null;
		
		for (Player p : playerList) {
			if(p == begins) {
				 firstPlayed = playCard(begins, "");
				startColor = firstPlayed.getColor();
				begins.playedCard = firstPlayed;
				
				//test
					if(human.cardsOnHand < 10)
					{int dsaf = 3;}
				
				begins.playerArea.setText(begins.playedCard.toString());
			}
			else 
			{
				p.playedCard = playCard(p,startColor);
				p.playerArea.setText(p.playedCard.toString());
			}
		}
		
		cardComparison(human.playedCard, c1.playedCard, c2.playedCard, c3.playedCard, firstPlayed, begins);
		
		//removing cards from hands
		for (Player player : playerList) {
			player.hand.remove(player.playedCard);
		}
		
		while(!space) {
			System.out.println("Press space to continue");
			label.setText("Press space");
			}
		space = false;
		showHumanCards();
		
		human.playerArea.setText("");
		c1.playerArea.setText("");
		c2.playerArea.setText("");
		c3.playerArea.setText("");
	}
	
	static Card chooseCard(String startColor) 
	{
		
		//checking if has needed color
		boolean hasColor = false;
		for (Card card : human.hand) {
			if(card.getColor() == startColor) hasColor = true;
		}
		
		if(hasColor == false && human != begins) label.setText("Your turn! (any color)");
		
		while(true) {
			System.out.println("choose !!!");
			if(entered && begins == human) break;
			if(entered ) 
			{
				if(human.hand.get(chosenIndex).getColor().equals(startColor) || hasColor == false) break;
				else {
					entered = false;
					label.setText("invalid color!");
				}
			} 

		}
		
		human.cardsOnHand--;
		entered = false;
		return human.hand.get(chosenIndex);
	}
	
	
	static Card chooseForExchange() {
		
		while(!entered) {
			System.out.println("choose !!!");
		}
		
		human.cardsOnHand--;
		entered = false;
		return human.hand.get(chosenIndex);
	}
	

	
	
	   class CustomKeyListener implements KeyListener{
		      public void keyTyped(KeyEvent e) {
		      }
		  	@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			    int keyCode = e.getKeyCode();
			    switch( keyCode ) { 
			        case KeyEvent.VK_ENTER:
			        	System.out.println("choosen index: "+chosenIndex);
			        	entered = true;
			        	break;
			        case KeyEvent.VK_UP:
			            // handle up 
			        	if(chosenIndex != 0) {
			        		chosenIndex--;
			        		human.hand.get(chosenIndex+1).isChosen = false;
			        		human.hand.get(chosenIndex).isChosen = true;
			        	}
			        	load = 0;
			            break;
			        case KeyEvent.VK_DOWN:
			            // handle down 
			        	if(chosenIndex < human.cardsOnHand-1) {
			        		chosenIndex++;
			        		human.hand.get(chosenIndex-1).isChosen = false;
			        		human.hand.get(chosenIndex).isChosen = true;
			        	}
			        	load = 1;
			            break;
			        case KeyEvent.VK_LEFT:
			            // handle left
			            break;
			        case KeyEvent.VK_RIGHT :
			            // handle right
			            break;
			        case KeyEvent.VK_SPACE :
			        	space = true;
			        	break;
			     }
			    showHumanCards();
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
		   }


	  static void  writeJson() throws IOException 
	   {
		  if(human.cardsOnHand == 0) return;
		  
		    JSONObject jsonObject = new JSONObject();
		    jsonObject.put("humanScore", human.points);
		    jsonObject.put("c1Score", c1.points);
		    jsonObject.put("c2Score", c2.points);
		    jsonObject.put("c3Score", c3.points);
		    
		    jsonObject.put("howManyCards", human.cardsOnHand);
		    
		    int whoBegins = 0;
		    if(begins == human) whoBegins = 0;
		    if(begins == c1) whoBegins = 1;
		    if(begins == c2) whoBegins = 2;
		    if(begins == c3) whoBegins = 3;
		    
		    jsonObject.put("whoBegins", whoBegins);
		    
			List<Player> playerList = new LinkedList<Player>();
			playerList.add(human);	
			playerList.add(c1);
			playerList.add(c2);
			playerList.add(c3);
		    
		    
		    for (Player p : playerList) {
				for (int i = 0; i < human.cardsOnHand; i++) {
					Card c;
					c = p.hand.get(i);

					if (c != null) {
						jsonObject.put(p.name+"CardColor" + i, c.getColor());
						jsonObject.put(p.name+"CardValue" + i, c.getValue());
					}
				} 
			}
		    
			Files.write(Paths.get("scores.json"), jsonObject.toJSONString().getBytes());
	   }
	   
	  
	  static  void readJson() throws Exception 
	   {
		  
		    JSONObject jsonObject = (JSONObject) readJsonSimpleDemo("scores.json");
		    human.points =  ((Long) jsonObject.get("humanScore")).intValue();
		    c1.points = ((Long) jsonObject.get("c1Score")).intValue();
		    c2.points = ((Long) jsonObject.get("c2Score")).intValue();
		    c3.points = ((Long) jsonObject.get("c3Score")).intValue();
		    
		    human.cardsOnHand = ((Long) jsonObject.get("howManyCards")).intValue();
		    
		    int whoBegins = ((Long) jsonObject.get("whoBegins")).intValue();
		    
		    if(whoBegins == 0) begins = human;
		    if(whoBegins == 1) begins = c1;
		    if(whoBegins == 2) begins = c2;
		    if(whoBegins == 3) begins = c3;
		    
			List<Player> playerList = new LinkedList<Player>();
			playerList.add(human);	
			playerList.add(c1);
			playerList.add(c2);
			playerList.add(c3);
		    
		    
		    for (Player p : playerList) {
				for (int i = 0; i < human.cardsOnHand; i++) {
					Card c = new Card();

					if (c != null) {
						c.setColor((String) jsonObject.get(p.name+"CardColor" + i));
						c.setValue(((Long) jsonObject.get(p.name+"CardValue" + i)).intValue());
						p.hand.add(c);
					}

				} 
			}
			loadedState = true;
	   }
	   
	   
	   public static Object readJsonSimpleDemo(String filename) throws Exception {
		    FileReader reader = new FileReader(filename);
		    JSONParser jsonParser = new JSONParser();
		    return jsonParser.parse(reader);
		}




}
