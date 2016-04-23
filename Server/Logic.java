package game.set;

import java.util.ArrayList;

public class Logic {
	
	// Logic fields
	protected ArrayList<Card> CompleteCardList;
	protected ArrayList<ArrayList<Card>> SetList, CompleteSetList;
	protected int CompleteSetCount;
	
	// Logic constructor
	public Logic() {
		
		CompleteCardList = new ArrayList<Card>();		
		
		// Fill up card list
		for(int numType = 1; numType <= 3; numType++) {
			for(int symType = 1; symType <= 3; symType++) {
				for(int shadType = 1; shadType <= 3; shadType++) {
					for(int colType = 1; colType <= 3; colType++) {
						CompleteCardList.add(new Card(numType, symType, shadType, colType));
					}
				}
			}
		}
		
		CompleteSetList = getSets(CompleteCardList);
		CompleteSetCount = getSetCount(CompleteSetList);
	}
	
	// Logic methods
    public boolean isSet(Card a, Card b, Card c) {
    	
    	if( ((a.number == b.number) && (b.number == c.number)) ||
    		((a.number != b.number) && (b.number != c.number) && (a.number != c.number)) ) {
    		
    		if( ((a.symbol == b.symbol) && (b.symbol == c.symbol)) ||
    	    	((a.symbol != b.symbol) && (b.symbol != c.symbol) && (a.symbol != c.symbol)) ) {
    			
    			if( ((a.shading == b.shading) && (b.shading == c.shading)) ||
    	    	    ((a.shading != b.shading) && (b.shading != c.shading) && (a.shading != c.shading)) ) {
    				
    				if( ((a.color == b.color) && (b.color == c.color)) ||
    	    	    	    ((a.color != b.color) && (b.color != c.color) && (a.color != c.color)) ) {
    					
    					return true;
    				}
    			}
    		}
    	}
    	
    	return false;
    }
    
    // gets all possible sets from the playing field or in total
    public ArrayList<ArrayList<Card>> getSets(ArrayList<Card> pool) {
    	
    	ArrayList<ArrayList<Card>> setList = new ArrayList<ArrayList<Card>>();
    	if(pool == null) return setList;
    	
    	int poolSize = pool.size();
    	for(int ia = 0; ia < poolSize; ia++) {
    		Card a = pool.get(ia);
    		for(int ib = ia + 1; ib < poolSize; ib++) {
    			Card b = pool.get(ib);
    			for(int ic = ib + 1; ic < poolSize; ic++) {
    				Card c = pool.get(ic);
    				if(isSet(a, b, c)) {
    					ArrayList<Card> set = new ArrayList<Card>();
    					set.add(a);
    					set.add(b);
    					set.add(c);
    					setList.add(set);
    				}
    			}
    		}
    	}
    	
    	return setList;
    }
    
    public int getSetCount(ArrayList<ArrayList<Card>> setList) {
    	return setList.size();
    }
    
    public boolean noMoreSets(ArrayList<ArrayList<Card>> setList) {
    	int setCount = getSetCount(setList);
    	if(setCount > 0) {
    		return false;
    	}
    	return true;
    }
}
