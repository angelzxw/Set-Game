package game.set;

public class Card {

	// Card fields
	protected int number;
	protected int symbol;
	protected int shading;
	protected int color;
	
	// Card constructor
	public Card(int newNum, int newSym, int newShad, int newCol) {
		number = newNum;
		symbol = newSym;
		shading = newShad;
		color = newCol;
	}
		
	// Card overwritten hashCode
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + color;
		result = prime * result + number;
		result = prime * result + shading;
		result = prime * result + symbol;
		return result;
	}

	// Card overwritten equals
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (color != other.color)
			return false;
		if (number != other.number)
			return false;
		if (shading != other.shading)
			return false;
		if (symbol != other.symbol)
			return false;
		return true;
	}

	/*
	public boolean equals(Card card) {
		if( (this.number == card.number) &&
			(this.symbol == card.symbol) &&
			(this.shading == card.shading) &&
			(this.color == card.color) ) {
			return true;
		}
		else {
			return false;
		}
	}
	*/

	// Card toString
	public String toString() {
		return number + " " + symbol + " " + shading + " " + color + '\n';
	}
}