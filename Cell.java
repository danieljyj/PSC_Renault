import java.util.Vector;

public class Cell {
	final int posx;
	final int posy;
	//Taxi[] t;
	Vector<Taxi> taxis;
	String property;
	
	Cell(int posx, int posy){
		this.posx=posx;
		this.posy=posy;
	}
	public String toString() {
		return "( "+posx+" , "+posy+ ")";
	}
	
	Cell(int posx, int posy, Vector<Taxi> taxis, String property){
		this.posx=posx;
		this.posy=posy;
		this.taxis=taxis;
		this.property=property;
	}
	
	
	
	
	
	
	
	
}
