import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Taxi {
	// int imma;
	Pair pos;
	final int capacity = 6;
	HashSet<Client> clients = new HashSet<Client>();
	LinkedList<Pair> route = new LinkedList<Pair>();

	Taxi(Pair pos) {
		this.pos = pos;
	}

	public void addClient(Client client) {
		this.clients.add(client);
	}

	public void delClient(Client client) {
		this.clients.remove(client);
	}

	// TO BE COMPLETED
	public void route() {
		for (Client c : clients) {
			if (!route.contains(c.dest)) {
				int dist = Pair.dist(this.pos, c.dest);
				if (route == null) {
					route.add(c.dest);
				} else {
					int i = 0;
					for (Pair p : route) {
						if (Pair.dist(p, this.pos) > dist)
							route.add(i, c.dest);
					}
				}
			}
		}
	}

	public void move() {
		if(route.isEmpty())
			return;
		Pair p= route.getFirst();
		if(this.pos.x<p.x)
			this.pos.x++;
		else if(this.pos.x<p.x)
			this.pos.x--;
		else if(this.pos.y<p.y)
			this.pos.y++;
		else if(this.pos.y>p.y)
			this.pos.y--;
		if(this.pos.equals(p)) {
			p=route.removeFirst();
			for(Client c:clients) {
				if (c.dest.equals(p))
					clients.remove(c);
			}
		}
	}
}
