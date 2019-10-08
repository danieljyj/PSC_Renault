import java.util.HashSet;
import java.util.LinkedList;



public class Cell {
	final Pair pos;
	int noc=0;  //number of clients
	int nwc=0;  //number of waiting clients
	int nac=0;	//number of allocated clients
	int not=0;  // number of taxis
	LinkedList<Taxi> taxis;
	LinkedList<Client> wclients;
	LinkedList<Client> aclients;

	Cell(Pair pos){
		this.pos=pos;
		this.taxis= new LinkedList<Taxi>();
		this.wclients =new LinkedList<Client>();
		this.aclients =new LinkedList<Client>();
	}
	
	public void addTaxi(Taxi taxi) {
		this.taxis.add(taxi);
		not++;
	}
	public void delTaxi(Taxi taxi) {
		this.taxis.remove(taxi);
		not--;
	}
	public void taxiClear() {
		this.taxis.clear();
		this.not=0;
	}
	public void addwClient(Client client) {
		noc++;
		assert(client.status==cStatus.WAITING);
		this.wclients.add(client);
		nwc++;	
	}
	public void allocateClient(Client nclient) {
		assert(nclient.status==cStatus.WAITING) ;
		this.wclients.remove(nclient);
		nwc--;
		nclient.status=cStatus.ALLCOCATED;
		this.aclients.add(nclient);
		nac++;
	}

	public void delaClient(Client client) {
		assert(client.status==cStatus.ALLCOCATED);
		this.aclients.remove(client);
		nac--;
		noc--;
	}


	
	public LinkedList<Taxi> getTaxis(){
		return this.taxis;
	}

	public String toString() {
		return "{"+"("+this.pos.h+" , "+this.pos.w+ ")"+", "+taxis.size()+", nwc="+nwc+", nac="+nac+"}";
	}
}
