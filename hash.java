import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;


public class hash {
	public static void main(String[] args) {
		Cell[][] grid=new Cell[2][2];
		HashMap<Pair, Cell> cells = new HashMap<Pair, Cell>();

	
		for(int i=0;i<2;i++) {
			for(int j=0;j<2;j++) {
				Pair pos = new Pair(i,j);
				Cell cell = new Cell(pos);
				grid[i][j]=cell;
				cells.put(pos, cell);
			}
		}	
		
		Cell c= cells.get(new Pair(1,0));
		c.taxis.add(new Taxi(new Pair(1,0)));
		System.out.println(c);
		System.out.println(grid[1][0]);
		
	}
	


}
