

public class Client {
	final int id;
	final Pair pos;
	final Pair dest;
	final int dist;
	int taxi;
	cStatus status=cStatus.WAITING;
	//type of trip;
	int appearTime=0;
	int boardTime=-1;
	double tolerancetime=Integer.MAX_VALUE;	//tolerance time of waiting a taxi or in the taxi, not sure useful or not
	boolean willing=true;  //willing of transit(agree or not to pick up another client, if he has already shared a ride, then willing= false )
	Client(){
		this.pos=new Pair(Integer.MAX_VALUE,Integer.MAX_VALUE);
		this.dest=new Pair(Integer.MAX_VALUE,Integer.MAX_VALUE);
		this.id=Integer.MAX_VALUE;
		this.dist=Integer.MAX_VALUE;
	}
	Client(Pair pos, Pair dest,int id, int t){
		this.pos=pos;
		this.dest=dest;
		this.id=id;
		this.dist=Math.abs(dest.h-pos.h)+Math.abs(dest.w-pos.w);
		this.appearTime=t;
	}
	public String toString() {
		return  "["+"id:"+id+", "+pos+", "+ dest+ "]";
	}
	public int hashCode() {	
		return id& 0x7fffffff;	
	}
	
	public boolean equals(Object o) {
		Client p=(Client) o;
		return this.id==p.id;
	}
}
