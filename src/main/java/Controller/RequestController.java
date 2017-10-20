package Controller;
import Module.*;
import java.util.*;

public class RequestController {
	private int cutoff=0;
	private int sumSeats=0;
	private List<Request> reqlist=new ArrayList<>();
	
	// parse input
	public void parseRequest(List<String> input){
		for(int i=0;i<input.size();i++){
			String[] req=input.get(i).split("\\s+");
			int numSeat=Integer.parseInt(req[1]);
			sumSeats+=numSeat;
			if(sumSeats<Layout.capacity){				
				Request r=new Request(req[0],numSeat);
				reqlist.add(r);
			}else{
				cutoff=i;
				System.out.println("Can only process the requests before "+req[0]+".");
				System.out.println("Seats are sold out for "+req[0]+"and afterwards.");
				break;
			}			
		}
	}
	
	public int getSumSeats(){
		return sumSeats;
	}
	
	public List<Request> getReqList(){
		return reqlist;
	}
	
	public int getCutoff(){
		return cutoff;
	}
}
