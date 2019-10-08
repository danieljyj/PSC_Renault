
import java.util.HashSet;
import java.util.LinkedList;

public class Taxi {
	final int imma;
	final int capacity = 6;
	Pair pos;
	int inservice=0;
	int distance = 0;
	int noc = 0; // number of clients boarded
	int nac = 0; // number of allocated clients but not boarded
	double averageClient=0;
	int emptyDistance;
	int accumNumOfClient=0;   //accumulated number of clients when the taxi moves and currently has at least one client.
	LinkedList<Client> clients;
	LinkedList<Pair> route;
	int historyClient=0;
	HashSet<Integer> tasks; // id of clients allocated to this taxi but not boarded yet.
	LinkedList<Pair> historyRoute;

	Taxi(Pair pos, int imma) {
		this.imma = imma;
		this.pos = pos;
		this.noc = 0;
		this.nac = 0;
		this.clients = new LinkedList<Client>();
		this.route = new LinkedList<Pair>();
		this.historyClient = 0;
		this.tasks = new HashSet<Integer>(); // id of clients allocated to this taxi but not boarded yet.
		this.historyRoute = new LinkedList<Pair>();
	}

	private void addClient(Client nclient) {
		assert (this.noc < this.capacity);
		assert (nclient.status == cStatus.ALLCOCATED);
		this.clients.add(nclient);
		this.noc++;
		this.nac--;
	}

	public void delClient(Client nclient) {
		this.clients.remove(nclient);
		this.noc--;
	}

	public String toString() {
		return "<" + "imma:" + imma + ", " + pos + ", " + "noc=" + noc + ", nac=" + nac + ", " + "route= " + this.route
				+ ">";
	}

	// move this taxi to the first target in route in a random direction,return true
	// means that taxi arrived at its first target
	public boolean move() {
		historyRoute.add(new Pair(this.pos.h,this.pos.w));
		if (route.isEmpty()) {
			assert (this.noc == 0);
			return false; // no client, no move
		}
		
		Pair p = this.route.getFirst(); // p is the first target, it can be the destination of a client in the taxi or
										// it can be the position of a allocated client
		double u = Math.random();
		if (this.pos.h == p.h) {
			if (this.pos.w < p.w) {
				this.pos.w++;
				distance++;
				if(this.noc==0) emptyDistance++;
				else {
					accumNumOfClient+=this.noc;
				}
			}
			else if (this.pos.w > p.w) {
				this.pos.w--;
				distance++;
				if(this.noc==0) emptyDistance++;
				else {
					accumNumOfClient+=this.noc;
				}
			}
				
		} else if (this.pos.w == p.w) {
			if (this.pos.h < p.h) {
				this.pos.h++;
				distance++;
				if(this.noc==0) emptyDistance++;
				else {
					accumNumOfClient+=this.noc;
				}
			}
			else if (this.pos.h > p.h) {
				this.pos.h--;
				distance++;
				if(this.noc==0) emptyDistance++;
				else {
					accumNumOfClient+=this.noc;
				}
			}
				
		} else {
			if (u < 0.5) {
				if (this.pos.h < p.h)
					this.pos.h++;
				else
					this.pos.h--;
			} else {
				if (this.pos.w < p.w)
					this.pos.w++;
				else
					this.pos.w--;
			}
			distance++;
			if(this.noc==0) emptyDistance++;
			else {
				accumNumOfClient+=this.noc;
			}
		}

		if (this.pos.equals(p)) { // check if certain clients in this taxi has arrived
			p = route.removeFirst();
			HashSet<Client> getOffClients = new HashSet<Client>();
			for (Client c : this.clients) {
				if (c.dest.equals(p)) { // these are the clients who will get off
					getOffClients.add(c);
				}
			}
			for (Client c : getOffClients) {
				this.delClient(c);
				this.historyClient++; // new client is added in the head of linkedlist in consideration of time complexity
			}
			return true;
		}
		return false;

	}

	// a function that decides whether this taxi can take a given client;
	public boolean pickOrNot(Client nclient) {
		assert (this.noc + this.nac <= capacity);
		if (this.noc + this.nac == this.capacity) return false;
		if ((noc + nac) == 0) { // the taxi is empty and has no target
			this.route.add(nclient.pos);
			this.route.addLast(nclient.dest);
			this.tasks.add(nclient.id);
			//assert(this.route.size()!=0);
			this.nac++;
			return true;
		} else if (this.shareOrNot(nclient)) { // if taxi is not empty or has already a target, it depends whether
												// clients can share a ride.
			this.tasks.add(nclient.id); // If clients can share a ride, we just add this client into taxi's task, we
										// have already added this client in route, and we will add this client in
										// clients when pick succeed.
			assert(this.route.size()!=0);
			this.nac++;
			return true;
		} else {
			return false;
		}
	}

	// this function is called when the taxi arrived at the position of one of its
	// tasks, it will pick up the client.
	public void pickSuccess(Client nclient) {
		assert (this.noc < this.capacity);
		assert (nclient.status == cStatus.ALLCOCATED);
		this.tasks.remove(nclient.id);
		this.addClient(nclient);
		nclient.taxi=this.imma;
	}

	// a function decides whether clients can share a ride. When true, this function
	// add the pos and dest of new clients in the route as well.
	public boolean shareOrNot(Client client) {
		assert ((this.noc + this.nac) > 0);
		//assert (this.route.size() != 0);
		
		 if(this.route.size()==0) { this.route.add(client.pos);
		 this.route.add(client.dest); }
		 
		assert (client != null);
		boolean flag1 = false;
		boolean flag2 = false;
		Pair r1 = this.route.peek(); // r1 is the 1st target in route

		// check the client.pos conditions

		if (r1.equals(client.pos))
			flag1 = true; // flag1==true means that client.pos is between this.pos and r1
		else if (client.pos.h >= this.pos.h && client.pos.h <= r1.h) {
			if (client.pos.w >= this.pos.w && client.pos.w <= r1.w)
				flag1 = true;
			else if (client.pos.w <= this.pos.w && client.pos.w >= r1.w)
				flag1 = true;
		} else if (client.pos.h <= this.pos.h && client.pos.h >= r1.h) {
			if (client.pos.w >= this.pos.w && client.pos.w <= r1.w)
				flag1 = true;
			else if (client.pos.w <= this.pos.w && client.pos.w >= r1.w)
				flag1 = true;
		}
		if (flag1 == false)
			return false;
		// check the client.dest conditions,
		Pair r = client.pos;
		int index = 0;
		for (Pair r2 : this.route) {
			if (r2.equals(client.dest))
				flag2 = true;
			else if (client.dest.h >= r.h && client.dest.h <= r2.h) {
				if (client.dest.w >= r.w && client.dest.w <= r2.w)
					flag2 = true;
				else if (client.dest.w <= r.w && client.dest.w >= r2.w)
					flag2 = true;
			} else if (client.dest.h <= r.h && client.dest.h >= r2.h) {
				if (client.dest.w >= r.w && client.dest.w <= r2.w)
					flag2 = true;
				else if (client.dest.w <= r.w && client.dest.w >= r2.w)
					flag2 = true;
			}
			if (flag2 == true) {
				this.route.add(index, client.dest);
				break;
			} else {
				r = r2;
				index++;
			}
		}
		// here, we need still check if the last target in route is between client.pos
		// and client.dest
		if (flag2 == false) {
			if (r.equals(client.dest))
				flag2 = true; // after the precedent loop, r becomes the last target in route
			else if (r.h >= client.pos.h && r.h <= client.dest.h) {
				if (r.w >= client.pos.w && r.w <= client.dest.w)
					flag2 = true;
				else if (r.w <= client.pos.w && r.w >= client.dest.w)
					flag2 = true;
			} else if (r.h <= client.pos.h && r.h >= client.dest.h) {
				if (r.w >= client.pos.w && r.w <= client.dest.w)
					flag2 = true;
				else if (r.w <= client.pos.w && r.w >= client.dest.w)
					flag2 = true;
			}
			if (flag2 == true) {
				this.route.addLast(client.dest);
			}
		}
		if (flag1 && flag2) {
			this.route.addFirst(client.pos); // do not forget that we haven't added the client.pos
		}

		return flag1 && flag2;
	}
	
	
	
	public int hashCode() {	
		return imma& 0x7fffffff;	
	}
	
	public boolean equals(Object o) {
		Taxi p=(Taxi) o;
		return this.imma==p.imma;
	}
}
