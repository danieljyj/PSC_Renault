
public class Client {
	Pair pos;
	Pair dest;
	//type of trip;
	double waittime;
	double traveltime;
	double tolerancetime;
	boolean willing=true;  //willing of transit
	Client(Pair pos, Pair dest){
		this.pos=pos;
		this.dest=dest;
	}
	Client(Pair pos, Pair dest, double tolerancetime) {
		this.pos=pos;
		this.dest=dest;
		this.tolerancetime = tolerancetime;
	}
	public String toString() {
		return  "["+pos+", "+ dest+ "]";
	}
	
	
}
