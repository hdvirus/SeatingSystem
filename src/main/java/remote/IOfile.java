package remote;

import java.io.*;
import java.util.*;

public class IOfile{
	private List<String> input;
	private String inPath,outPath;

	public IOfile(String inPath, String outPath){
		this.inPath=inPath;
		this.outPath=outPath;
		input=new ArrayList<>();
		readFile();
	}

	public List<String> getInput(){
		return input;
	}

	// read input file given path
	private void readFile(){
		String line;
		BufferedReader br=null;
		try{
			FileReader fr=new FileReader(inPath);
			br=new BufferedReader(fr);
			while ((line=br.readLine())!=null){
				input.add(line);
			}
		}catch(IOException ioex){
			System.out.println("Cannot find input file!");
			throw new RuntimeException("Exception occurs when reading input file.", ioex);		
		}finally {
			try {
				if(br!=null) br.close();
			} catch (IOException ioex2) {
				System.err.println(this.getClass().getName() + ".readFile - Cannot close reader" + ioex2.toString()); 

			}
		}
	}

	// write output file 
	public void writeFile(List<String> output){
		BufferedWriter bw = null;
		try {
			File file = new File(outPath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			for(String line:output){
				bw.write(line+"\n");
			}			
		} catch (IOException ex) {
			System.out.println("Warning: Output file is not completely written!");
			throw new RuntimeException("Exception occurs when writing output file.", ex);
		}
		finally{ 
			try{
				if(bw!=null) bw.close();					
			}catch(IOException ioex2){
				System.err.println(this.getClass().getName() + ".writeFile - Cannot close writer" + ioex2.toString()); 
			}
		}
	}
}