import java.util.HashSet;
import java.util.LinkedList;

public class Cell {
	final Pair pos;
	int noc=0;  //number of clients
	int not=0;  // number of taxis
	//HashSet<Taxi> taxis=new HashSet<Taxi>();
	LinkedList<Taxi> taxis= new LinkedList<Taxi>();
	//HashSet<Client> clients=new HashSet<Client>();
	LinkedList<Client> clients =new LinkedList<Client>();
	String property;
	
	Cell(Pair pos){
		this.pos=pos;
	}
	Cell(Pair pos,LinkedList<Taxi> taxis, String property){
		this.pos=pos;
		this.taxis=taxis;
		this.property=property;
	}
	
	/*Cell(Pair pos, HashSet<Taxi> taxis, String property){
		this.pos=pos;
		this.taxis=taxis;
		this.property=property;
	}*/
	
	public void addTaxi(Taxi taxi) {
		this.taxis.add(taxi);
		not++;
	}
	public void delTaxi(Taxi taxi) {
		this.taxis.remove(taxi);
		not--;
	}
	public void addClient(Client client) {
		this.clients.add(client);
		noc++;
	}
	public void delClient(Client client) {
		this.clients.remove(client);
		noc--;
	}

	
	public String toString() {
		return "{"+"("+this.pos.h+" , "+this.pos.w+ ")"+", "+taxis.size()+", "+clients.size()+"}";
	}
}
