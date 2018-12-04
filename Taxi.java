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
			System.out.println("route is empty");
			return;
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
}
