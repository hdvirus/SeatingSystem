package Module;
import java.util.*;

public class Row {
	private int rowId;
	private int remainStart;
	private List<Request> list;
	
	public Row(int rowId, List<Request> list){
		this.rowId=rowId;
		this.list=list;		
	}
	public void printRow(){
		for(Request req:list){
			System.out.print(req.getReqId()+" "+req.getSeat()+"  ");
		}
		System.out.println();
	}
	public void setRemainStart(int start){
		this.remainStart=start;
	}
	public int getId(){
		return rowId;
	}
	public int getRemainStart(){
		return remainStart;
	}
	public List<Request> getReqList(){
		return list;
	}
	
}
