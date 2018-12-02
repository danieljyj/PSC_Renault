
public class Client {
	Pair pos;
	Pair des;
	int timer;
	//typedetrajet;
	double waittime;
	double traveltime;
	double tolerancetime;
	Client(Pair pos, Pair des){
		this.pos = pos;
		this.des = des;
		this.timer = 0;
	}
	Client(Pair pos, Pair des, double tolerancetime) {
		this.pos = pos;
		this.des = des;
		this.tolerancetime = tolerancetime;
		this.timer = 0;
	}
	
	
}
