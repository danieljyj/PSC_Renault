import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import org.omg.CORBA.Current;

public class Taxi {
	//int imma;
	Pair pos;
	final int capacity=6;
	HashSet<Client> clients=new HashSet<Client>();
	LinkedList<Pair> route;   
	LinkedList<Pair> dests = new LinkedList<Pair>();
	
	Taxi(Pair pos){
		this.pos = pos; 
		this.route = new LinkedList<Pair>();
	}
	
	public void addClient(Client client) {
		if()
		this.clients.add(client);
		dests.add(client.des);
		
		
	}
	public void delClient(Client client) {
		this.clients.remove(client);
		int count = 0;
		for(Pair i: dests) {
			if (client.des == i)
				break;
			count ++;
		}
		this.dests.remove(count);
		
	}
	public LinkedList<Pair> routesearch() {
		//il faut que etant donnee liste dests, il y a une route qui va passer tout les Pair dans dests. 
	}
	
	public boolean prendre(LinkedList<Pair> nroute, int currentT) {
		for(Client current : clients)
			if(  currentT - current.timer < nroute.size() ) return false;
		return true;
	}
}
