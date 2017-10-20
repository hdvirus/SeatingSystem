package ControllerTest;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import Controller.RequestController;
import Module.Request;

public class RequestControllerTest {
	
	@Test
	public void testParseRequest() {
		String[] lines={"R001 2","R002 4","R003 4","R004 3"};
		ArrayList<String> input=new ArrayList<>(Arrays.asList(lines));		
		
		RequestController reqctrl=new RequestController();
		reqctrl.parseRequest(input);	
		List<Request> reqlist=reqctrl.getReqList();
		
		assertEquals(4, reqlist.size());
		assertEquals(1, reqlist.get(0).getId());
		assertEquals(2, reqlist.get(0).getSeat());
		assertEquals(2, reqlist.get(1).getId());
		assertEquals(4, reqlist.get(1).getSeat());
		assertEquals(3, reqlist.get(2).getId());
		assertEquals(4, reqlist.get(2).getSeat());
		assertEquals(4, reqlist.get(3).getId());
		assertEquals(3, reqlist.get(3).getSeat());
	}

}
