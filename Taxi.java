import java.util.HashSet;
import java.util.LinkedList;

public class Taxi {
	// int imma;
	//Cell cell;
	Pair pos;
	final int capacity = 6;
	int noc; //number of clients
	//HashSet<Client> clients = new HashSet<Client>();  //这里用hashset或者linkedlist暂且不影响下面
	LinkedList<Client> clients = new LinkedList<Client>();
	LinkedList<Pair> route = new LinkedList<Pair>();
	
	Taxi(Pair pos) {
		this.pos = pos;
		this.noc=0;
	}
	/*Taxi(Pair pos , Cell cell){
		this.pos=pos;
		this.cell=cell;
	}*/
	public void addClient(Client client) {
		if(noc==capacity) {
			System.out.println("error: noc reached already capacity!");
			return;
		}
		this.clients.add(client);
		calRoute();
		this.noc++;
	}
	public void delClient(Client client) {
		this.clients.remove(client);
		this.noc--;
	}
	public String toString() {
		return  "<"+pos+", " + "noc=" + noc + ", "+"route= "+this.route+">";
	}
	
	// pick up a client, wait for refinement
	public void pickUp(Pair pos) {
		route.addFirst(pos);
	}

	// calculate the route of taxi, stock only every client's destination
	public void calRoute() {
		for (Client c : this.clients) {
			if (!this.route.contains(c.dest)) {
				int dist = Pair.dist(this.pos, c.dest);
				if (route.isEmpty()) {
					route.add(c.dest);
				} else {
					boolean hasAdded=false;
					int i = 0;
					for (Pair p : route) {
						if (Pair.dist(this.pos, p) > dist) {
							route.add(i, c.dest);
							hasAdded=true;
							break;
						}
						i++;
					}
					if(!hasAdded)
						route.addLast(c.dest);
				}
			}
		}
	}
	//wait for refinement
	public void move() {		
		if(route.isEmpty()) {
			//System.out.println("route is empty");
			return;//no client, no move
			
		}
		Pair p= this.route.getFirst();
		if(this.pos.h<p.h)
			this.pos.h++;
		else if(this.pos.h>p.h)
			this.pos.h--;
		else if(this.pos.w<p.w)
			this.pos.w++;
		else if(this.pos.w>p.w)
			this.pos.w--;
		if(this.pos.equals(p)) {
			p=route.removeFirst();
			for(Client c : this.clients) {
				if (c.dest.equals(p))
					this.delClient(c);
			}
		}

	}
	
	//effectuer pick up, si taxi prend ce client, return true, update taxi statu; else return false;
	public boolean pick(Client nclient) {
		if(! this.pickornot(nclient)) return false;
		this.clients.addLast(nclient);
		if(this.clients.size() > 1)
			this.clients.getFirst().willing = false;
		//System.out.println("nclient is added");
		this.route.add(nclient.dest);
		this.route.addFirst(nclient.pos);
		return true;
	}
	//decide whether or not pickup a client.
	//criterion 1. newclient willing to(current distance of taxi within 1/3 distance total)
	//criterion 2. current client in taxi will accept(distance within 1/4 total distance of original)
	//criterion 3. capacity of taxi permet.
	public boolean pickornot(Client nclient) {
		//for(Client c : this.clients) if(c.willing == false) return false;
		//en fait juste verifier le premier est suffisant, car noc ne depasse jamais 2 dans le cas courrant.
		if(this.clients.size() == 0) return true;//车上没人就去接
		Client current = this.clients.getFirst();
		if(current.willing == false) return false;
		int disofcurrent = Pair.dist(current.dest, current.pos);
		//if(Pair.dist(this.pos, nclient.pos) >= 1/3 * Pair.dist(nclient.dest, nclient.pos)) return false;
		int dist1;
		dist1= Pair.dist(this.pos, nclient.pos);
		//nclient will arrive his destination first
		if(Pair.dist(nclient.pos, current.dest) > Pair.dist(nclient.pos, nclient.dest)) {
			if(dist1 + Pair.dist(nclient.pos, nclient.dest) + Pair.dist(nclient.dest,current.dest)
				> 5/4*disofcurrent) 
				return false;
			else return true;
		}
		else {//current client will arrive his destination first.
			if(dist1 + Pair.dist(nclient.pos, current.dest) > disofcurrent)
				return false;
			else return true;
		}
	}
}
