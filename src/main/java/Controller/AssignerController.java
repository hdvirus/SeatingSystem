package Controller;
import Module.*;

import java.util.*;

public class AssignerController {
	private Layout optimalLayout=new Layout();
	List<Request> reqList;
	public TreeMap<Integer,PriorityQueue<Request>> reqMap=new TreeMap<>();
	int half=Layout.numCol/2,remainSeat=0;
	public Set<List<Integer>> subset=new HashSet<>();
	
	public AssignerController(List<Request> reqList,int sumSeats){
		this.reqList=reqList;
		this.remainSeat=Layout.capacity-sumSeats;
	}

	public void assign(){
		// 1. pair two requests where the sum of their numOfSeats equals to numCol=20
		// and also construct the TreeMap
		pairTwoSum();

		// 2. break large request to two parts
		// break the request with largest key if it + smallest key > 20
		while(true){
			int first=reqMap.firstKey(),last=reqMap.lastKey();
			if(last>Layout.numCol-first) breakParts(last);
			else break;
		}

		// 3. subset sum
		// construct sorted request list
		List<Integer> nums=new ArrayList<>();
		for(int key:reqMap.keySet()){
			int count=reqMap.get(key).size();
			while(count-->0){
				nums.add(key);
			}
		}
		subsetSum(nums);

		// sort subset sum list
		List<List<Integer>> subsetlist=sortSubset();
		// insert subset as new Row into Layout 
		insertSubset(subsetlist);

		// 4. greedy algorithm: fit smaller key first, break larger key once if necessary
		int curSeat=0;
		List<Request> newRow=new ArrayList<>();
		Set<Integer> keyset=reqMap.keySet();
		for(int key: keyset) {
			// To avoid concurrent modification exception.
			if(!reqMap.containsKey(key)) continue;
			PriorityQueue<Request> minHeap = reqMap.get(key);
			while(minHeap.size()>0){
				if(curSeat+key<Layout.numCol){
					curSeat+=key;
					newRow.add(minHeap.poll());
				}else if(curSeat+key>Layout.numCol){
					if(Layout.numCol-curSeat==1 && remainSeat>0){
						optimalLayout.insertNewRow(newRow);
						remainSeat--;
					}else{
						int lastKey=reqMap.lastKey();
						PriorityQueue<Request> lastHeap=reqMap.get(lastKey);
						int part1=Layout.numCol-curSeat,part2=lastHeap.peek().getSeat()-part1;
						if(part2==1 && remainSeat>=part1){
							optimalLayout.insertNewRow(newRow);
							remainSeat-=part1;
							newRow=new ArrayList<>();
							curSeat=0;
						}else{
							Request lastReq=lastHeap.poll();

							newRow.add(new Request(lastReq.getReqId(),part1));

							optimalLayout.insertNewRow(newRow);
							newRow=new ArrayList<>();
							newRow.add(new Request(lastReq.getReqId(),part2));
							curSeat=part2;
						}
					}
				}
				else{
					optimalLayout.insertNewRow(newRow);
					newRow=new ArrayList<>();
					curSeat=0;
				}
			}
		}
		if(newRow.size()>0) optimalLayout.insertNewRow(newRow);
	}
	
	// Sort subsets so that the one contains smaller requests has higher priority.
	public List<List<Integer>> sortSubset(){
		List<List<Integer>> subsetlist=new ArrayList<>(subset);
		Collections.sort(subsetlist,new Comparator<List<Integer>>(){
			public int compare(List<Integer> a, List<Integer> b){
				int la=a.size(),lb=b.size();
				if(la==lb){
					int i=0;
					for(;i<la && a.get(i)==b.get(i);i++);
					return a.get(i)-b.get(i);
				}
				return lb-la;
			}
		});
		return subsetlist;
	}
	// insert subset as a new Row into Layout 
	public void insertSubset(List<List<Integer>> subsetlist){
		for(List<Integer> curList:subsetlist){
			boolean isValid=true;
			for(int key:curList){
				if(!reqMap.containsKey(key)){
					isValid=false; break;
				}
			}
			if(isValid){
				List<Request> newRow=new ArrayList<>();
				for(int key:curList){
					PriorityQueue<Request> minHeap= reqMap.get(key);
					newRow.add(minHeap.poll());
					if(minHeap.size()==0) reqMap.remove(key);
				}
				optimalLayout.insertNewRow(newRow);
			}
		}
	}

	// check if there is any subset whose sum is 20
	public void subsetSum(List<Integer> nums){
		int n=nums.size();
		boolean[][] dp=new boolean[n+1][Layout.numCol+1];
		dp[0][0]=true;

		for(int i=1;i<n+1;i++){
			for(int j=0;j<Layout.numCol+1;j++){
				if(j>=nums.get(i-1)) dp[i][j]=dp[i-1][j] || dp[i-1][j-nums.get(i-1)];
				else dp[i][j]=dp[i-1][j];
			}
		}

		if(dp[n][Layout.numCol]){
			subsetPath(new ArrayList<Integer>(),nums, n,Layout.numCol,dp);
		}
	}

	// track back subset to get paths
	private void subsetPath(List<Integer> result, List<Integer> nums, int i, int target, boolean[][] dp){
		if(target==0){
			subset.add(result);
			return;
		}
		if(dp[i-1][target]){
			subsetPath(new ArrayList<Integer>(result),nums,i-1,target,dp);
		}
		if(target>=nums.get(i-1) && dp[i-1][target-nums.get(i-1)]){
			result.add(nums.get(i-1));
			subsetPath(result,nums,i-1,target-nums.get(i-1),dp);
		}
	}

	// break large requests into two parts
	public void breakParts(int key){
		PriorityQueue<Request> minHeap1=reqMap.get(key);
		Request cur=minHeap1.poll();

		// break Request cur to two parts
		// pair part1 with the Request near half
		int lower=reqMap.lowerKey(half), higher=reqMap.higherKey(half);
		int target=half-lower<higher-half ?lower:higher;
		PriorityQueue<Request> minHeap2=reqMap.get(target);
		Request theOther=minHeap2.poll();

		int part1=Layout.numCol-target;
		optimalLayout.insertNewRow(Arrays.asList(new Request[]{theOther,new Request(cur.getReqId(),part1)}));
		int part2=key-part1;
		insertKey(part2,new Request(cur.getReqId(),part2));	

		if(minHeap1.size()==0) reqMap.remove(key);
		if(minHeap2.size()==0) reqMap.remove(target);
	}

	// handle numSeat >= 20
	public int breakOverCol(Request req){
		int cur=req.getSeat();
		while(cur>=20){
			optimalLayout.insertNewRow(Arrays.asList(new Request[]{new Request(req.getReqId(),20)}));
			cur-=20;
		}
		return cur;
	}

	// pair two requests that sums to 20 into a new row
	public void pairTwoSum(){
		for(Request req: reqList){
			int cur=req.getSeat();
			if(cur>=20) {
				// break the requests that is over 20
				cur=breakOverCol(req);
				req=new Request(req.getReqId(),cur);
			}
			if(cur==0) continue;

			int target=Layout.numCol-cur;

			if(reqMap.containsKey(target) && reqMap.get(target).size()>=1){
				// pair	two requests if their sum = Num of Col = 20			
				// use minHeap to offer the earliest request( with smaller requestID)
				PriorityQueue<Request> minHeap=reqMap.get(target);
				Request theOther=minHeap.poll();
				optimalLayout.insertNewRow(Arrays.asList(new Request[]{theOther,req}));
				if(minHeap.size()==0) reqMap.remove(target);
			}else{
				// didn't find pairs
				insertKey(cur,req);	
			}
		}
	}
	
	// insert the broken request to TreeMap
	private void insertKey(int cur, Request req){
		if(reqMap.containsKey(cur)){
			PriorityQueue<Request> minHeap=reqMap.get(cur);
			minHeap.offer(req);
		}else{
			PriorityQueue<Request> minHeap=new PriorityQueue<>(11,new Comparator<Request>(){
				public int compare(Request a, Request b){
					return a.getId()-b.getId();
				}
			});
			minHeap.offer(req);
			reqMap.put(cur, minHeap);
		}	
	}
	// get optimal layout
	public Layout getOptLayout(){
		return optimalLayout;
	}
}
