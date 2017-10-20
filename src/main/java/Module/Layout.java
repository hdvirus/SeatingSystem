package Module;

import java.util.*;

public class Layout {
	public static int numRow=10;
	public static int numCol=20;
	public static int capacity=numRow*numCol;

	private Row[] rowLayout=new Row[numRow];
	private Map<String,ArrayList<String>> seatMap=new HashMap<>();
	private int curNewRow=0;

	public void insertNewRow(List<Request> reqlist){
		rowLayout[curNewRow]=new Row(curNewRow,reqlist);
		int colStart=1; char rowChar=(char) ('A'+curNewRow);

		for(Request req:reqlist){
			String reqId=req.getReqId();
			ArrayList<String> seats;
			if(seatMap.containsKey(reqId)) seats=seatMap.get(reqId);
			else {
				seats=new ArrayList<String>();
				seatMap.put(reqId,seats);
			}

			for(int count=0;count<req.getSeat();count++){
				if(colStart>numCol){
					System.out.println("Invalid input row!");
					System.exit(-1); 
				}
				seats.add(""+rowChar+colStart);
				colStart++;
			}
		}
		rowLayout[curNewRow].setRemainStart(colStart);
		curNewRow++;
	}

	public void printLayout(){
		for(int i=0;i<curNewRow;i++){
			System.out.print("Row"+(i+1)+": ");
			rowLayout[i].printRow();
		}
	}
	public int getSize(){
		return curNewRow;
	}
	public Map<String,ArrayList<String>> getSeatMap(){
		return seatMap;
	}
}
