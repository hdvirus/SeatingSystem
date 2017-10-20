package Module;

public class Request {
	private String reqId;
	private int id;
	private int numOfSeat;
	
	public Request(String reqId, int numOfSeat){
		this.reqId=reqId;
		this.id=Integer.parseInt(reqId.substring(1,4));
		this.numOfSeat=numOfSeat;
	}
	
	public String getReqId(){
		return reqId;
	}
	public int getId(){
		return id;
	}
	public int getSeat(){
		return numOfSeat;
	}
}
