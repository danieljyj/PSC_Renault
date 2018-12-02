
public class Ville {
	public int height, width;
	public Cell[][] grid;
	
	Ville(){
		this.height=10;
		this.width=10;
		this.grid=new Cell[height][width];
		for(int i=0;i<grid.length;i++) {
			for(int j=0;j<grid[i].length;j++) {
				this.grid[i][j]=new Cell(new Pair(i,j));
			}
		}
	}
	

	
	public static void main(String[] args) {
		int toltime=1;
		int t=0;
		Ville paris=new Ville();
		//initialisation
		for(int i=0;i<paris.width;i++) {
			for(int j=0;j<paris.height;j++) {
				paris.grid[i][j].addTaxi(new Taxi(new Pair(i,j)));
				paris.grid[i][j].addClient(new Client(new Pair(i,j),new Pair((int) Math.random()*paris.height, (int) Math.random()*paris.width)));
			}
		}
		
		//fonctionner
		for(;t<toltime;t++) {	
			for(int i=0;i<paris.width;i++){
				for(int j=0;j<paris.height;j++) {
					for(Taxi taxi : paris.grid[i][j].taxis) {
						taxi.move();
					}
				}
			}
			
			
			
			
			
			
		//afficher cette ville 
			for(int i=0;i<paris.grid.length;i++) {
				for(int j=0;j<paris.grid[i].length;j++) {
					System.out.print(paris.grid[i][j]);
				}
				System.out.print("\n");
			}
		}
	}
}
