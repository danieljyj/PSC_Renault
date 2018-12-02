
public class Pair {
	public int x;
	public int y;
	Pair(int x, int y){
		this.x=x;
		this.y=y;
	}
	static int dist(Pair a, Pair b) {
		int distx=b.x-a.x;
		int disty=b.y-a.y;
		return distx*distx+disty*disty;
	}
	
	public boolean equals(Object o) {
		Pair p=(Pair) o;
		return p.x==this.x&&p.y==this.y;
	}
}
