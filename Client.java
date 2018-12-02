
public class Client {
	Pair pos;
	Pair dest;
	//typedetrajet;
	double waittime;
	double traveltime;
	double tolerancetime;
	Client(Pair pos, Pair dest){
		this.pos=pos;
		this.dest=dest;
	}
	Client(Pair pos, Pair dest, double tolerancetime) {
		this.pos=pos;
		this.dest=dest;
		this.tolerancetime = tolerancetime;
	}
	
	
}
