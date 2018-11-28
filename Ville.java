
public class Ville {
	private int height, width;
	private Cell[][] grid;
	
	Ville(){
		this.height=100;
		this.width=100;
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
		for(;t<time;t++) {	
			for(int i=0;i<paris.grid.length;i++) {
				for(int j=0;j<paris.grid[i].length;j++) {
					System.out.print(paris.grid[i][j]);
				}
				System.out.print("\n");
			}
		}
	}
}
