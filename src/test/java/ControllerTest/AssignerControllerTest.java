package ControllerTest;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import Controller.AssignerController;
import Controller.RequestController;
import Module.*;

public class AssignerControllerTest {

	@Test
	public void testpairTwoSum() {
		String[] lines={"R001 2","R002 4","R003 4","R004 18","R005 36","R006 8","R007 20"};
		ArrayList<String> input=new ArrayList<>(Arrays.asList(lines));		

		RequestController reqctrl=new RequestController();
		reqctrl.parseRequest(input);	
		List<Request> reqlist=reqctrl.getReqList();

		AssignerController asgnCtrl=new AssignerController(reqlist,reqctrl.getSumSeats());
		asgnCtrl.pairTwoSum();
		Layout optLayout=asgnCtrl.getOptLayout();
		//optLayout.printLayout();

		assertEquals(4, optLayout.getSize());
		assertEquals(new HashSet<Integer>(Arrays.asList(4,8)),asgnCtrl.reqMap.keySet());
	}

	@Test
	public void testbreakParts() {
		String[] lines={"R001 3","R002 4","R003 8","R004 18","R005 16"};
		ArrayList<String> input=new ArrayList<>(Arrays.asList(lines));		

		RequestController reqctrl=new RequestController();
		reqctrl.parseRequest(input);	
		List<Request> reqlist=reqctrl.getReqList();

		AssignerController asgnCtrl=new AssignerController(reqlist,reqctrl.getSumSeats());
		asgnCtrl.pairTwoSum();
		asgnCtrl.breakParts(18);
		Layout optLayout=asgnCtrl.getOptLayout();
		//optLayout.printLayout();

		assertEquals(2, optLayout.getSize());
	}

	@Test
	public void testbreakOverCol() {
		AssignerController asgnCtrl=new AssignerController(null,0);
		int remain=asgnCtrl.breakOverCol(new Request("R001", 48));;
		assertEquals(8,remain);

		remain=asgnCtrl.breakOverCol(new Request("R001", 60));;
		assertEquals(0,remain);
	}
	
	@Test
	public void testAssign1() {
		String[] lines={"R001 3","R002 4","R003 4","R004 18","R006 8","R007 19"};
		ArrayList<String> input=new ArrayList<>(Arrays.asList(lines));		

		RequestController reqctrl=new RequestController();
		reqctrl.parseRequest(input);	
		List<Request> reqlist=reqctrl.getReqList();

		AssignerController asgnCtrl=new AssignerController(reqlist,reqctrl.getSumSeats());
		asgnCtrl.assign();
		assertEquals(new HashSet<Integer>(Arrays.asList(3,4,5)),asgnCtrl.reqMap.keySet());
		
//		Layout optLayout=asgnCtrl.getOptLayout();
//		optLayout.printLayout();
	}
	@Test
	public void testAssign2() {
//		String[] lines={"R001 17","R002 3","R003 2","R004 2","R005 8","R006 8","R007 13", "R008 6","R009 10","R010 12","R011 19"};
//		String[] lines={"R001 7","R002 7","R003 7","R004 7","R005 7","R006 7","R007 7", "R008 7"};
		String[] lines={"R001 3","R002 3","R003 3","R004 13","R005 83","R006 3","R007 3", "R008 43"};
		
		ArrayList<String> input=new ArrayList<>(Arrays.asList(lines));		

		RequestController reqctrl=new RequestController();
		reqctrl.parseRequest(input);	
		List<Request> reqlist=reqctrl.getReqList();

		AssignerController asgnCtrl=new AssignerController(reqlist,reqctrl.getSumSeats());
		asgnCtrl.assign();
		
//		Layout optLayout=asgnCtrl.getOptLayout();
//		optLayout.printLayout();
	}
	@Test
	public void testinsertSubset() {
		String[] lines={"R001 1","R002 3","R003 2","R004 2","R005 8","R006 8","R007 5", "R008 6","R009 10","R010 12"};
		ArrayList<String> input=new ArrayList<>(Arrays.asList(lines));		

		RequestController reqctrl=new RequestController();
		reqctrl.parseRequest(input);	
		List<Request> reqlist=reqctrl.getReqList();

		AssignerController asgnCtrl=new AssignerController(reqlist,reqctrl.getSumSeats());
		asgnCtrl.assign();
			
//		Layout optLayout=asgnCtrl.getOptLayout();
//		optLayout.printLayout();
	}
	
	@Test
	public void testSubset() {
		AssignerController asgnCtrl=new AssignerController(null,0);
		asgnCtrl.subsetSum(Arrays.asList(new Integer[]{1,2,2,3,5,6,8,10,12}));
//		for(List<Integer> list:asgnCtrl.subset){
//			System.out.println(list);
//		}
		assertEquals(13,asgnCtrl.subset.size());
	}
}
