import java.util.HashSet;

public class Ville {
	public int height, width;
	public Cell[][] grid;
	HashSet<Taxi> taxis=new HashSet<Taxi>();
	
	Ville(){
		this.height=2;
		this.width=2;
		this.grid=new Cell[height][width];
		for(int i=0;i<grid.length;i++) {
			for(int j=0;j<grid[i].length;j++) {
				this.grid[i][j]=new Cell(new Pair(i,j));
				Taxi t=new Taxi(new Pair(i,j));
				this.taxis.add(t);
				this.grid[i][j].taxis.add(t);
			}
		}
		
	}
	

	
	public static void main(String[] args) {
		int toltime=2;
		int t=0;
		Ville paris=new Ville();
		int height =paris.height;int width=paris.width; 
		double lambda=0.5;  //the rate of occurrence in each point.
		//initialization
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				Client client = new Client(new Pair(i,j),new Pair((int) Math.random()*height, (int) Math.random()*width));
				paris.grid[i][j].addClient(client);
				paris.grid[i][j].taxis.getFirst().addClient(client);
			}
		}
		
		//operation, to be completed
		for(;t<toltime;t++) {	
			//let taxi move one step
			for(int i=0;i<height;i++){
				for(int j=0;j<width;j++) {
					for(Taxi taxi : paris.grid[i][j].taxis) {
						taxi.move();
						paris.grid[i][j].taxis.remove(taxi);
					}
				}
			}
			//update the taxis of each cell
			for(Taxi taxi : paris.taxis) {
				paris.grid[taxi.pos.h][taxi.pos.w].taxis.add(taxi);
			}
		

		}
		
		for(Taxi taxi : paris.taxis) {
			System.out.println(taxi);
			System.out.println(taxi.clients);
		}
		//display the city
		/*for(int i=0;i<paris.grid.length;i++) {
			for(int j=0;j<paris.grid[i].length;j++) {
				System.out.print(paris.grid[i][j]);
			}
			System.out.print("\n");
		}*/
	}
	
	
	//Generate an integer of poison process
	public static int rand_p(double lambda) {
		int k=0;
		double prob=Math.exp(-lambda);
		double cdf=prob;
		double u=(double)Math.random();
		while (u>=cdf){
			k++;
			prob*=lambda/k;
			cdf+=prob;
		}
		return k;
	}

}
