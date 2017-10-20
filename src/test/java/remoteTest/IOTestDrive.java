package remoteTest;
import java.util.*;

import remote.IOfile;

public class IOTestDrive {
	public static void main(String[] args){
		String inPath="/Users/jingchen/Documents/java_project/MovieTheaterSeating/data/ReservationData.txt";
		String outPath="/Users/jingchen/Documents/java_project/MovieTheaterSeating/data/Output.txt";
		
		IOfile io=new IOfile(inPath,outPath);
		for(String s:io.getInput()){
			System.out.println(s);
		}
		
		String[] lines={"R001 I1,I2","R002 F16,F17,F18,F19","R003 A1,A2,A3,A4","R004 J4,J5,J6"};
		ArrayList<String> output=new ArrayList<>(Arrays.asList(lines));
		io.writeFile(output);
	}
}
