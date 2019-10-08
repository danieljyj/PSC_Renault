//Cell is displayed and delimited by {...}, Taxi by <...>,Client by [...], Pair by (...), when it comes to a LinkedList, the contents of list are always organized by [...]

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class Ville {
	public int height, width;
	public Cell[][] grid;
	int not;
	int tolDistOfClient=0;
	double utilisation;
	HashMap<Pair, Cell> cells = new HashMap<Pair, Cell>();
	HashSet<Taxi> taxis = new HashSet<Taxi>();
	HashMap<Integer, Client> clients=new HashMap<Integer, Client>();

	
	Ville(int height, int width, int notpc ) {
		this.height =height;
		this.width = width;
		this.grid = new Cell[height][width];
		int imma=1;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Pair pos = new Pair(i, j);
				Cell cell = new Cell(pos);
				this.grid[i][j] = cell;
				this.cells.put(pos, cell);
				for(int k=0;k<notpc;k++) {
					Taxi t = new Taxi(new Pair(i, j),imma++);
					this.taxis.add(t);
					this.grid[i][j].addTaxi(t);
				}
				this.not+=notpc;
			}
		}	
	}

	public static String simulation (int taxiPerCell, double lambda, timeInaDay time) {
// Parameters:		
		// !!!!!!!!!!!!According queuing theory: lambda, number of taxi per cell n, and length of city l, should conform the relationship:  
		//lambda*1.4l/(2*n) ~ 1, if it's far greater than 1, there will be an explosion of waiting clients. if smaller than 1, the system converges.
		//double lambda = 1; // the rate of occurrence of client in each cell.  
		int toltime = 800;  // total times of operation，we admit that 1h=20 iteration. size of ville is 900km^2
		int height = 20;   // the length of one cell is 1.5km, so a 30km/h taxi can move cell in 3 minutes(so t++ means 3 minutes has passed)
		int width = 20;
		int notpc=taxiPerCell;       // number of taxi per cell
		
		
		
		Ville paris = new Ville(height,width, notpc);
		int t = 0;    //time
		int id=1;   // id of client

		// operation
		for (; t < toltime; t++) {
			//System.out.println("loop"+t);
			// let taxis move one step
			double outserviceTaxi=0;
			for (Taxi taxi : paris.taxis) {
				boolean arrived =taxi.move();  //arrived==true means that taxi has arrived its first target,check if there is a client waiting for it
				if(t>=(toltime-10)&&(taxi.noc+taxi.nac)==0) {
					outserviceTaxi++;
				}
					
				if (arrived) {
					boolean flag=false;
					HashSet<Client> pickUpClient= new HashSet<Client>();
					for(Client c : paris.grid[taxi.pos.h][taxi.pos.w].aclients) {
						if(taxi.tasks.contains(c.id)) {
							pickUpClient.add(c);
							flag=true;   //flag ==true means that there are clients who are distributed to this taxi 
						}
					}
					if(flag==true) {
						for(Client c : pickUpClient) {
							taxi.pickSuccess(c);
							c.boardTime=t;
							assert(c.status==cStatus.ALLCOCATED);
							paris.grid[taxi.pos.h][taxi.pos.w].delaClient(c);
						}	
					}
				}
				
			}
			if(t>=(toltime-10)) {
				paris.utilisation+=(notpc*width*height-outserviceTaxi)/(notpc*width*height);
			}
			


			// after the move of all taxis, update the taxis of each cell. remove all first, then add one by one
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					paris.grid[i][j].taxiClear();
				}
			}
			
			for (Taxi taxi : paris.taxis) {
				paris.grid[taxi.pos.h][taxi.pos.w].addTaxi(taxi);	
			}
			
			//generate new client according loi de poisson in each cell
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					id+=paris.generateClient(i, j, lambda, time ,id,t);
				}
			}
		
			//pick up a new client or not in the same cell
			for(Cell cell : paris.cells.values()) {
				//System.out.println(cell);
				if(cell.nwc==0) continue;
				for(int i=0 ; i<cell.nwc;i++) {
					//System.out.println("loop");
					Client nclient=cell.wclients.peek();
					assert(nclient.status==cStatus.WAITING);
					assert(nclient.pos.equals(cell.pos));
					boolean flag =waitForTaxi(cell, nclient);  //flag == true means that there is a taxi has accepted this client
					if(flag==true) {
						cell.allocateClient(nclient);
					}
					if (flag==false) break; //if the first client in cell.wclients isn't allocated, same for those after.
				}
			}
			// pick a new client or not in the four adjacent cells
			for(Cell cell: paris.cells.values()) {
				if(cell.nwc==0) continue;
				for(int i=0 ; i<cell.nwc;i++) {	
					Client nclient=cell.wclients.peek();
					assert(nclient.status==cStatus.WAITING);
					boolean flag=waitForAroundTaxi(cell,nclient,paris);
					if(flag==true) {
						cell.allocateClient(nclient);
					}
					if(flag==false) break;
				}
			}
					
		}

		// display the city after operation
		int totalleft= 0;
		System.out.println("city after operation:\n");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(paris.grid[i][j].nwc+" ");
				totalleft += paris.grid[i][j].nwc;
			}
			System.out.print("\n");
		}
		
		// show status of taxis
		int tolServedClients = 0;
		int tolDistance=0;
		int tolEmptyDistance=0;
		double rateOfSharing=0;
		int numOfActiveTaxi =0;
		int tolNumOfTaxis=0;
		double averageWaitTime=0;
		System.out.println("\nfinal status of taxis:");
		System.out.println("Number of clients serviced by each taxi: ");
		for (Taxi taxi : paris.taxis) {
			System.out.print(taxi.historyClient+" ");
			if(taxi.historyClient!=0) numOfActiveTaxi++;
			tolServedClients+= taxi.historyClient;
			tolDistance+= taxi.distance;
			tolEmptyDistance+=taxi.emptyDistance;
			tolNumOfTaxis++;
			
		}
		System.out.println("\naverage number of clients when taxi has at least one clients: ");
		for (Taxi taxi : paris.taxis) {
			double temp=0;
			if(taxi.distance-taxi.emptyDistance!=0) {
				temp=(double)(taxi.accumNumOfClient)/(taxi.distance-taxi.emptyDistance);
				System.out.printf("%4.2f ",temp);
			}
			rateOfSharing+=temp;
		}
		int temp=0;
		for(Client c : paris.clients.values()) {
			if(c.boardTime!=-1) {
				averageWaitTime+=c.boardTime-c.appearTime;
				temp++;
			}
		}
		averageWaitTime/=temp;
		rateOfSharing= rateOfSharing/numOfActiveTaxi;
		double rho=paris.utilisation/10.;
		int not= paris.height*paris.width*notpc;
		System.out.println("\nNumber of active taxis : "+numOfActiveTaxi+"	Total number of taxis : "+ tolNumOfTaxis);
		System.out.printf("Average rate of sharing : %4.2f\n", rateOfSharing);
		System.out.printf("utilisation of taxis : %4.2f\n", rho);
		
		
		System.out.println("\nfinal status of Ville:");
		System.out.println("Number of clients waiting: "+ totalleft);
		System.out.println("Sum of clients serviced : "+ tolServedClients);
		System.out.printf("Average waitting time: %4.2f\n", averageWaitTime);
		double clientRatio=((double)totalleft)/tolServedClients;
		
		System.out.println("\nTotal distance of taxis : "+ tolDistance);
		System.out.println("Total empty distance of taxis : "+ tolEmptyDistance);
		System.out.println("Total distance of clients : "+ paris.tolDistOfClient);
		double distanceRatio=((double)tolDistance)/paris.tolDistOfClient;

		return not + " " + rho + " " + averageWaitTime + " " + totalleft + " " + tolServedClients + " " + clientRatio
				+ " " + rateOfSharing + " " + tolDistance + " " + paris.tolDistOfClient + " " + distanceRatio + "	"
				+ tolEmptyDistance;

}

	// Generate an integer of poison process
	public static int rand_p(double lambda) {
		int k = 0;
		double prob = Math.exp(-lambda);
		double cdf = prob;
		double u = Math.random();
		while (u >= cdf) {
			k++;
			prob *= lambda / k;
			cdf += prob;
		}
		return k;
	}
	static boolean waitForTaxi (Cell cell, Client nclient) {
		boolean flag=false;
		for(Taxi tt : cell.taxis) {
			if(tt.pickOrNot(nclient) == true) { //true means that the taxi can take this client, meanwhile the client is allocated to this taxi
				flag = true;
				break;      
			}
		}
		return flag; 
	}
	static boolean waitForAroundTaxi(Cell cell, Client nclient, Ville paris) {
		boolean flag=false;
		double u= Math.random();
		boolean up=true;
		boolean down=true;
		boolean left=true;
		boolean right=true;
		if(cell.pos.h==0 ) up=false;
		if(cell.pos.h==paris.height-1) down=false;
		if(cell.pos.w==0) left=false;
		if(cell.pos.w==paris.width-1) right=false;
		if(u<0.25) {
			if(up==true) 	{flag=waitForTaxi(paris.grid[cell.pos.h-1][cell.pos.w],nclient);}
			if(flag==false) {if(left==true)  flag=waitForTaxi(paris.grid[cell.pos.h][cell.pos.w-1],nclient);	}
			if(flag==false) {if(down==true)  flag=waitForTaxi(paris.grid[cell.pos.h+1][cell.pos.w],nclient);	}
			if(flag==false) {if(right==true) flag=waitForTaxi(paris.grid[cell.pos.h][cell.pos.w+1],nclient);	}
		}
		else if (0.25<=u&&u<0.5) {
			if(left==true)	flag=waitForTaxi(paris.grid[cell.pos.h][cell.pos.w-1],nclient);
			if(flag==false) {if(down==true)  flag=waitForTaxi(paris.grid[cell.pos.h+1][cell.pos.w],nclient);	}
			if(flag==false) {if(right==true) flag=waitForTaxi(paris.grid[cell.pos.h][cell.pos.w+1],nclient);	}
			if(flag==false) {if(up==true)	 flag=waitForTaxi(paris.grid[cell.pos.h-1][cell.pos.w],nclient);	}
		}
		if(0.5<=u&&u<0.75) {
			if(down==true)	flag=waitForTaxi(paris.grid[cell.pos.h+1][cell.pos.w],nclient);
			if(flag==false) {if(right==true) flag=waitForTaxi(paris.grid[cell.pos.h][cell.pos.w+1],nclient);	}
			if(flag==false) {if(up==true)	 flag=waitForTaxi(paris.grid[cell.pos.h-1][cell.pos.w],nclient);	}
			if(flag==false) {if(left==true)  flag=waitForTaxi(paris.grid[cell.pos.h][cell.pos.w-1],nclient);	}
		}
		else{
			if(right==true)	flag=waitForTaxi(paris.grid[cell.pos.h][cell.pos.w+1],nclient);
			if(flag==false) {if(up==true)	 flag=waitForTaxi(paris.grid[cell.pos.h-1][cell.pos.w],nclient);	}
			if(flag==false) {if(left==true)  flag=waitForTaxi(paris.grid[cell.pos.h][cell.pos.w-1],nclient);	}
			if(flag==false) {if(down==true)  flag=waitForTaxi(paris.grid[cell.pos.h+1][cell.pos.w],nclient);	}
		}
		return flag;
	}
	
	public int generateClient(int i, int j, double lambda, timeInaDay time, int id, int t) {
		int num = id;
		if ((i < 2 && j < 2) || (i < 2 && j > 17) || (i > 17 && j < 2) || (i > 17 && j > 17)) {
			if (time == timeInaDay.MATIN)
				lambda = lambda * 1.5;
			else if (time == timeInaDay.SOIR) {
				lambda = lambda*0.5;
			}
		}
		if(i==2&&j<=2||j==2&&i<=2||(i==2&&j>=17)||(i<=2&&j==17)||(i==17&&j<=2||i>=17&&j==2)||(i==17&&j>=17)||(i>=17&&j==17)) {
			if (time == timeInaDay.MATIN)
				lambda = lambda * 1.2;
			else if (time == timeInaDay.SOIR) {
				lambda = lambda *0.8;
			}
		}
		if (i < 12 && i > 7 && j < 12 && j > 7) {
			if (time == timeInaDay.MATIN)
				lambda = lambda *0.5;
			else if (time == timeInaDay.SOIR) {
				lambda = lambda * 1.5;
			}
		}
		if(((i==7||i==12)&&(j>=7&&j<=12))||((j==7||j==12)&&(i<=12&&i>=7))) {
			if (time == timeInaDay.MATIN)
				lambda = lambda * 0.8;
			else if (time == timeInaDay.SOIR) {
				lambda = lambda *1.2;
			}
		}

		int k = rand_p(lambda); // k is the number of new clients in each cell
		if (k == 0)
			return 0;
		Client newClient = new Client();
		for (int n = 0; n < k; n++) {
			double u = Math.random();
			if (u < 0.6) {
				Pair dest = new Pair((int) (Math.random() * height), (int) (Math.random() * width));
				while (Pair.dist(new Pair(i, j), dest) <= 1) {
					dest = new Pair((int) (Math.random() * height), (int) (Math.random() * width));
				}
				newClient = new Client(new Pair(i, j), dest, num++, t);
				this.grid[i][j].addwClient(newClient);
				this.clients.put(num - 1, newClient);
				this.tolDistOfClient += newClient.dist;

			} else if (0.6 <= u && u < 0.9) {
				Pair dest = new Pair((int) (Math.random() * height), (int) (Math.random() * width));
				while (Pair.dist(new Pair(i, j), dest) <= 1) {
					dest = new Pair((int) (Math.random() * height), (int) (Math.random() * width));
				}
				for (int m = 0; m < 2; m++) {
					newClient = new Client(new Pair(i, j), dest, num++, t);
					this.grid[i][j].addwClient(newClient);
					this.clients.put(num - 1, newClient);
					this.tolDistOfClient += newClient.dist;
				}
			} else {
				Pair dest = new Pair((int) (Math.random() * height), (int) (Math.random() * width));
				while (Pair.dist(new Pair(i, j), dest) <= 1) {
					dest = new Pair((int) (Math.random() * height), (int) (Math.random() * width));
				}
				for (int m = 0; m < 3; m++) {
					newClient = new Client(new Pair(i, j), dest, num++, t);
					this.grid[i][j].addwClient(newClient);
					this.clients.put(num - 1, newClient);
					this.tolDistOfClient += newClient.dist;
				}
			}
		}

		return num - id;
	}
	

}







