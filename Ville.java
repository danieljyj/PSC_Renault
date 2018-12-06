//Cell is displayed and delimited by {...}, Taxi by <...>,Client by [...], Pair by (...), when it comes to a LinkedList, the contents of list are always organized by [...]
import java.util.HashMap;
import java.util.HashSet;

public class Ville {
	public int height, width;
	public Cell[][] grid;
	HashMap<Pair, Cell> cells = new HashMap<Pair, Cell>();
	HashSet<Taxi> taxis = new HashSet<Taxi>();

	Ville() {
		this.height = 10;
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
		int toltime = 1;  // total times of operation
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
				System.out.print(paris.grid[i][j]);
			System.out.print("\n");
		}
		// show status of taxis
		System.out.println("original status of taxis");
		for (Taxi taxi : paris.taxis) {
			System.out.print(taxi + "	");
			System.out.println(taxi.clients);
		}

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
		
		}

		// display the city after operation
		System.out.println("city after operation");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++)
				System.out.print(paris.grid[i][j]);
			System.out.print("\n");
		}
		// show status of taxis
		System.out.println("final status of taxis");
		for (Taxi taxi : paris.taxis) {
			System.out.print(taxi + "	");
			System.out.println(taxi.clients);
		}

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
