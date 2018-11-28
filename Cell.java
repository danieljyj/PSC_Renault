import java.util.HashSet;

public class Cell {
	final int posx;
	final int posy;
	//Taxi[] t;
	HashSet<Taxi> taxis=new HashSet<Taxi>();
	HashSet<Client> clients=new HashSet<Client>();
	String property;
	
	Cell(int posx, int posy){
		this.posx=posx;
		this.posy=posy;
	}
	Cell(int posx, int posy, HashSet<Taxi> taxis, String property){
		this.posx=posx;
		this.posy=posy;
		this.taxis=taxis;
		this.property=property;
	}
	
	public void addTaxi(Taxi taxi) {
		this.taxis.add(taxi);
	}
	public void delTaxi(Taxi taxi) {
		this.taxis.remove(taxi);
	}
	public void addClient(Client client) {
		this.clients.add(client);
	}
	public void delClient(Client client) {
		this.clients.remove(client);
	}

	
	public String toString() {
		return "["+"("+posx+" , "+posy+ ")"+", "+taxis.size()+", "+clients.size()+"]";
	}
}
