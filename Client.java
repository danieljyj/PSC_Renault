
public class Client {
	Pair pos;
	Pair dest;
	//type of trip;
	double waittime;
	double traveltime;
	double tolerancetime;	//tolerance time of waiting a taxi or in the taxi, not sure useful or not
	boolean willing=true;  //willing of transit(agree or not to pick up another client, if he has already shared a ride, then willing= false )
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
