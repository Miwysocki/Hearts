import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextArea;

public class Player {

	String name;
	int points;
	int cardsOnHand;

	List<Card> hand;
	
	Card playedCard;
	
	JTextArea playerArea;
	
	Player(String _name) {
		this.name = _name;
		this.points = 0;
		this.hand = new LinkedList<Card>();
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	

	
}
