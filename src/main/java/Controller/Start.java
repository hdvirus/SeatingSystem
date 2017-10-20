package Controller;

import remote.IOfile;

import java.util.*;

import Module.*;

public class Start {
	public static void main(String[] args){
		if(args.length!=1){
			System.out.println(args.length);
			System.out.println("Invalid input! Please enter the input file path.");
			System.exit(-1);
		}
		String inPath=args[0];
		// handle the output path
		String[] parts=inPath.split("/");
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<parts.length-1;i++){
			sb.append(parts[i]).append("/");
		}
		
		System.out.println(inPath);
		sb.append("Output.txt");
		String outPath=sb.toString();
		
		IOfile io=new IOfile(inPath,outPath);
		List<String> inputStr=io.getInput();

		RequestController reqCtrl=new RequestController();
		reqCtrl.parseRequest(inputStr);
		List<Request> reqList=reqCtrl.getReqList();

		AssignerController asgCtrl=new AssignerController(reqList,reqCtrl.getSumSeats());
		asgCtrl.assign();

		Layout optLayout=asgCtrl.getOptLayout();
//		System.out.println("Layout map:");
//		optLayout.printLayout();

		LayoutController layoutCtrl=new LayoutController();
		List<String> seats=layoutCtrl.parseSeatMap(optLayout.getSeatMap());

		io.writeFile(seats);
		System.out.println("Output path is:");
		System.out.println(outPath);
	}
}
