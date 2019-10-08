import java.io.*;

public class Main {
	
	public static void main(String[] args) {
		
		double lambda=1.5;
		int taxiPerCell = 52;
		
		try {
			BufferedWriter out1 = new BufferedWriter(new FileWriter("result_lambda=1.0ORDINARY.txt"));
			//BufferedWriter out2 = new BufferedWriter(new FileWriter("result_lambda=1.0MATIN.txt"));
			//BufferedWriter out3 = new BufferedWriter(new FileWriter("result_lambda=1.0SOIR.txt"));
			String s="not"+"	"+"rho"+"	"+"averageWaitTime"+"	"+"totalLeftClients"+"	"+"tolServedClients"+"	"+"clientRatio"+"	"+"rateOfSharing"+"	"+"taxiDistance"+"	"+"clientDistance"+"	"+"distanceRatio"+"	"+"tolEmptyDistance";         
			out1.write(s);
			out1.write("\n");
			//out2.write(s);
			//out2.write("\n");
			//out3.write(s);
			//out3.write("\n");
			
			//for (; taxiPerCell <= ; taxiPerCell = taxiPerCell + 2) {
				System.out.println("taxiPerCell= "+taxiPerCell);
				String l1=Ville.simulation(taxiPerCell, lambda, timeInaDay.ORDINARY);
				//String l2=Ville.simulation(taxiPerCell, lambda, timeInaDay.MATIN);
				//String l3=Ville.simulation(taxiPerCell, lambda, timeInaDay.SOIR);
				out1.write(l1);
				out1.write("\n");
				//out2.write(l2);
				//out2.write("\n");
				//out3.write(l3);
				//out3.write("\n");
			//}
			out1.close();
			//out2.close();
			//out3.close();
		} catch (IOException e) {
		}

	}
}
