package Controller;

import java.util.*;

public class LayoutController {
	// parse seats assigned to reservation requests
		public List<String> parseSeatMap(Map<String,ArrayList<String>> map){
			List<String> output=new ArrayList<>();
			for(Map.Entry<String, ArrayList<String>> entry:map.entrySet()){
				StringBuilder sb=new StringBuilder();
				sb.append(entry.getKey()).append(" ");
				for(String s:entry.getValue()){
					sb.append(s).append(",");
				}
				sb.deleteCharAt(sb.length()-1);
				output.add(sb.toString());
			}
			
			Collections.sort(output);
			return output;
		}
}
