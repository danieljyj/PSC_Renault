
public class Ville {
	public int height, width;
	public Cell[][] grid;
	
	Ville(){
		this.height=10;
		this.width=10;
		this.grid=new Cell[height][width];
		for(int i=0;i<grid.length;i++) {
			for(int j=0;j<grid[i].length;j++) {
				this.grid[i][j]=new Cell(i,j);
			}
		}
	}
	

	
	public static void main(String[] args) {
		int time=1;
		int t=0;
		Ville paris=new Ville();
		//initialisation
		for(int i=0;i<paris.grid.length;i++) {
			for(int j=0;j<paris.grid[i].length;j++) {
				paris.grid[i][j].addTaxi(new Taxi(i,j));
				paris.grid[i][j].addClient(new Client(i,j,(int) Math.random()*paris.height, (int) Math.random()*paris.width));
			}
		}
		
		//fonctionner
		for(;t<time;t++) {	
			
			
			
			
			
			
			
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
