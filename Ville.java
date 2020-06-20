//Cell is displayed and delimited by {...}, Taxi by <...>,Client by [...], Pair by (...), when it comes to a LinkedList, the contents of list are always organized by [...]
import java.util.HashMap;
import java.util.HashSet;

public class Ville {
	public int height, width;
	public Cell[][] grid;
	HashMap<Pair, Cell> cells = new HashMap<Pair, Cell>();
	HashSet<Taxi> taxis = new HashSet<Taxi>();

	Ville() {
		this.height =10;
		this.width = 10;
		this.grid = new Cell[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Pair pos = new Pair(i, j);
				Cell cell = new Cell(pos);
				this.grid[i][j] = cell;
				this.cells.put(pos, cell);
				Taxi t = new Taxi(new Pair(i, j));
				this.taxis.add(t);
				this.grid[i][j].taxis.add(t);
			}
		}
	}

	public static void main(String[] args) {
		int toltime = 20;  // total times of operation
		int t = 0;
		Ville paris = new Ville();
		int height = paris.height;
		int width = paris.width;
		double lambda = 0.5; // the rate of occurrence of client in each point.
		// initialization
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Client client = new Client(new Pair(i, j),
						new Pair((int) (Math.random() * height), (int) (Math.random() * width)));
				paris.grid[i][j].taxis.getFirst().addClient(client);
			}
		}
		// display the original city
		System.out.println("The original city");
		for (int i = 0; i < paris.grid.length; i++) {
			for (int j = 0; j < paris.grid[i].length; j++)
				//System.out.print(paris.grid[i][j]);
			System.out.print(paris.grid[i][j].clients.size()+" ");
			System.out.print("\n");
		}
		/*// show status of taxis
		System.out.println("original status of taxis");
		for (Taxi taxi : paris.taxis) {
			System.out.print(taxi + "	");
			System.out.println(taxi.clients);
		}*/

		// operation, to be completed
		for (; t < toltime; t++) {
			// let taxis move one step
			for (Taxi taxi : paris.taxis) {
				taxi.move();
			}

			// update the taxis of each cell. remove all first, then add one by one
			for (int i = 0; i < height; i++)
				for (int j = 0; j < width; j++)
					paris.grid[i][j].taxis.clear();
			for (Taxi taxi : paris.taxis) {
				paris.cells.get(new Pair(taxi.pos.h,taxi.pos.w)).taxis.add(taxi);
				paris.grid[taxi.pos.h][taxi.pos.w].addTaxi(taxi);
			}
			
			//generate new client in each cell
			for (int i = 0; i < height; i++)
				for (int j = 0; j < width; j++) {
					int k = rand_p(lambda);      // k is the number of new clients in each cell
					if(k==0) continue;
					Client newClient;
					for ( int n=0;n<k;n++ ) {
						newClient = new Client(new Pair(i, j),
								new Pair((int) (Math.random() * height), (int) (Math.random() * width)));
						paris.grid[i][j].addClient(newClient);
					}
				}
			// pickup new clients , still wait for refinement		
/*			for(Cell cell : paris.cells.values()) {
				if(cell.noc==0)
					continue;
				for(Taxi taxi: cell.taxis) {
					if(taxi.noc >= taxi.capacity){
						continue;
					}
					boolean pick=true;
					for(Client c : taxi.clients) {
						if(c.willing==false) {
							pick=false;
						}
					}
					if(pick==true) {
						taxi.pickUp(cell.clients.removeFirst().pos);
					}
				}
			}*/
			//pick up a new client or not
			for(Cell cell: paris.cells.values()) {
				if(cell.clients.size() == 0) continue;
				//boolean flag = false;
				//System.out.println("client size is : " +cell.clients.size()  +" in this cell");
				//a decider a quel l'order gerer les clients, ici pour l'instant juste iterer par cell, et chaque cell 1 client chaque fois
				//ici taxi dans les region que le temps d'arrive ne depasse pas uplimite de waiting time, supposon 1/3 du temps de voyage.
				Client nclient = cell.clients.peek();
				//System.out.println("client pos, dest sont " + nclient.pos + nclient.dest);
				int dist =(int) Math.sqrt((double)(Pair.dist(nclient.pos, nclient.dest))) ;
				int est, west, north, south;
					est = (nclient.pos.w + dist*1/10 +1 < width-1) ? nclient.pos.w + dist*1/10 +1 : width -1 ;
					west = (nclient.pos.w - dist*1/10 -1 > 0) ? nclient.pos.w - dist*1/10 -1 : 0 ;
					north = (nclient.pos.h + dist*1/10 +1 < height - 1) ? nclient.pos.h + dist*1/10 + 1 : height - 1 ;
					south = (nclient.pos.h - dist*1/10 -1  > 0) ? nclient.pos.h - dist*1/10 - 1 : 0 ;
				//System.out.println(dist +" "+ nclient.pos.h+" " + nclient.pos.w +" "+ "est =" + est + " west = " + west + " south =" + south + " north="  + north);
				//while(flag == false) {
				labelA:
				for(int i = west; i< est ;i++)
					for(int j = south; j< north; j++) {
						for(Taxi tx : paris.grid[j][i].taxis) {
							//System.out.println("this taxi currently has " + tx.clients.size() + "client");
							//System.out.println("currently we are in the cell [i,j] = "+ "[" +i +"," + j + "]");
							if(tx.pick(nclient)== true) {
								//System.out.println("client size is : " +cell.clients.size());
								//if(cell.clients.size() != 0) 
								cell.clients.removeFirst();
								//System.out.println("get new client\n");
								//flag = true;
								break labelA;
							}
						}
					}
				//}
			}
		
		}

		// display the city after operation
		int totalleft= 0;
		System.out.println("city after operation");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				//System.out.print(paris.grid[i][j]);
				System.out.print(paris.grid[i][j].clients.size()+" ");
				totalleft += paris.grid[i][j].clients.size();
			}
			System.out.print("\n");
		}
		System.out.println(" \n N Client waiting: "+ totalleft);
		// show status of taxis
		/*System.out.println("final status of taxis");
		for (Taxi taxi : paris.taxis) {
			System.out.print(taxi + "	");
			System.out.println(taxi.clients);
		}*/

	}

	// Generate an integer of poison process
	public static int rand_p(double lambda) {
		int k = 0;
		double prob = Math.exp(-lambda);
		double cdf = prob;
		double u = (double) Math.random();
		while (u >= cdf) {
			k++;
			prob *= lambda / k;
			cdf += prob;
		}
		return k;
	}

}
