
public class Client {
	int posx;
	int posy;
	int destx;
	int desty;
	//typedetrajet;
	double waittime;
	double traveltime;
	double tolerancetime;
	Client(int posx, int posy, int destx, int desty){
		this.posx = posx;
		this.posy = posy;
		this.destx = destx;
		this.desty = desty;
	}
	Client(int posx, int posy, int destx, int desty, double tolerancetime) {
		this.posx = posx;
		this.posy = posy;
		this.destx = destx;
		this.desty = desty;
		this.tolerancetime = tolerancetime;
	}
	
	
}
