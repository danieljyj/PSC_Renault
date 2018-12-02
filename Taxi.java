import java.util.HashSet;
import java.util.Queue;

public class Taxi {
	//int imma;
	Pair pos;
	final int capacity=6;
	HashSet<Client> clients=new HashSet<Client>();
	
	Taxi(Pair pos){
		this.pos=pos; 
	}
	
	public void addClient(Client client) {
		this.clients.add(client);
	}
	public void delClient(Client client) {
		this.clients.remove(client);
	}
	public void route() {
		
	}
	public void move() {
		
	}
}
