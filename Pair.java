
public class Pair {
	int x;
	int y;
	
	Pair(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Pair p) {
		if(this.x == p.x && this.y == p.y)
			return true;
		return false;
	}

}
