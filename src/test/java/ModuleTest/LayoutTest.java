package ModuleTest;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import Controller.*;
import Module.*;

public class LayoutTest {

	@Test
	public void testInsertNewRow() {
		String[] lines={"R001 2","R002 4","R003 4","R004 18","R005 16"};
		ArrayList<String> input=new ArrayList<>(Arrays.asList(lines));		
		
		RequestController reqctrl=new RequestController();
		reqctrl.parseRequest(input);	
		List<Request> reqlist=reqctrl.getReqList();
		
		Layout layout=new Layout();
		layout.insertNewRow(Arrays.asList(new Request[]{reqlist.get(0),reqlist.get(3)}));
		layout.insertNewRow(Arrays.asList(new Request[]{reqlist.get(1),reqlist.get(4)}));
		
		Map<String,ArrayList<String>> seatMap=layout.getSeatMap();
		assertEquals(seatMap.get("R001"), Arrays.asList(new String[]{"A1","A2"}));
		assertEquals(seatMap.get("R002"), Arrays.asList(new String[]{"B1","B2","B3","B4"}));
		
//		layout.printLayout();
	}

}
