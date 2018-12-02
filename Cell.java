import java.util.HashSet;

public class Cell {
	final Pair pos;
	//Taxi[] t;
	HashSet<Taxi> taxis=new HashSet<Taxi>();
	HashSet<Client> clients=new HashSet<Client>();
	String property;
	
	Cell(Pair pos){
		this.pos = pos;
	}
	Cell(Pair pos, HashSet<Taxi> taxis, String property){
		this.pos = pos;
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
		return "["+"("+pos.x+" , "+pos.y+ ")"+", "+taxis.size()+", "+clients.size()+"]";
	}
}
