import java.util.*;
import java.util.Collections;




class Solution{
public static void main(String args[]){

 
Scanner reader = new Scanner(System.in);  


List<Node> list_of_nodes = new ArrayList<Node>();
boolean tempflag=true;
boolean inputflag=true;
List<String> nodes_to_avoid = new ArrayList<String>();
List<String> peggy_start_nodes = new ArrayList<String>();
List<String> sam_start_nodes = new ArrayList<String>();
List<String> output = new ArrayList<String>();

long startTime = System.currentTimeMillis();



while(inputflag)
{
	String line=reader.nextLine();
	boolean nodeflag=true;
	
	if(line.equals("Avoid:"))
	{	
		String nextline=reader.nextLine();
		String[] temp = nextline.split("\\s+");
		for( String x : temp ) {
			nodes_to_avoid.add(x);
		}	
		nodeflag=false;
	}
	
	if(line.equals("Peggy:"))
	{	
		String nextline=reader.nextLine();
		String[] temp = nextline.split("\\s+");
		for( String x : temp ) {
			peggy_start_nodes.add(x);
		}
		nodeflag=false;
	}
	
	if(line.equals("Sam:"))
	{	
		String nextline=reader.nextLine();
		String[] temp = nextline.split("\\s+");
		for( String x : temp ) {
			sam_start_nodes.add(x);
		}
		nodeflag=false;
		inputflag=false;
	}
	
	if(line.equals("Map:"))
	{
		nodeflag=false;
	}	
	
	if(nodeflag){	
		String[] nodes_of_edge = line.split("\\s+");
		String from_node=nodes_of_edge[0];
		String to_node=nodes_of_edge[1];
		boolean from_node_flag=false;
		boolean to_node_flag=false;
		
		
		for( Node temp_node : list_of_nodes ) {
		//System.out.println(temp_node.id);
		//System.out.println(temp_node.Upnodes);
		//System.out.println(temp_node.Downnodes);
		//System.out.println("--------------------------------------------------");
         if(temp_node.id.equals(from_node))
		 {
			 from_node_flag=true;
			 temp_node.Downnodes.add(to_node);
					
		 }
		 if(temp_node.id.equals(to_node))
		 {
			 to_node_flag=true;
			 temp_node.Upnodes.add(from_node);
					
		 }
		 
		 if(from_node_flag && to_node_flag )
		 {
			 break;
					
		 }
		}
		//System.out.println("----------------------flag----------------------------");
		//System.out.println(from_node_flag);
		//System.out.println(to_node_flag);
		//System.out.println("----------------------flag----------------------------");
		
		if(!from_node_flag)
		{
			Node temp=new Node();
			temp.id=from_node;
			temp.Downnodes.add(to_node);
			list_of_nodes.add(temp);
		}
		
		if(!to_node_flag)
		{
			Node temp=new Node();
			temp.id=to_node;
			temp.Upnodes.add(from_node);
			list_of_nodes.add(temp);
		}
	
		
		
	}
	
	
	
}
long endTime = System.currentTimeMillis();

long duration1 = (endTime - startTime);
System.out.println("Time taken for graph creation is ---->"+duration1+" millisecs");
/*
for( Node temp_node : list_of_nodes ) {
System.out.println("-----------------------------------------------");
System.out.println(temp_node.id);	
System.out.println(temp_node.Downnodes);	
System.out.println(temp_node.Upnodes);	
System.out.println("-----------------------------------------------");
}
System.out.println(nodes_to_avoid);
System.out.println(peggy_start_nodes);
System.out.println(sam_start_nodes);	
*/
startTime = System.currentTimeMillis();


List<String> peggy_visit_nodes_temp = new ArrayList<String>(peggy_start_nodes);
List<String> peggy_visit_nodes_final = new ArrayList<String>();
int temp_peggy_flag=peggy_visit_nodes_temp.size();
while(temp_peggy_flag>0)
{
	Node temp_outer_node=new Node();
	boolean unavoided=true;
	String temp_id=peggy_visit_nodes_temp.get(0);
	for (String d:nodes_to_avoid)
	{
		if(d.equals(temp_id))
		unavoided=false;		
	}
	
	if (unavoided)
	{
	for( Node temp_inner_node : list_of_nodes ) {//getting node object of matching temp_id
		if(temp_inner_node.id.equals(temp_id))
		{
			temp_outer_node=temp_inner_node;
			break;
		}
		
	}
	for(String z:temp_outer_node.Downnodes)//each node on stack
	{
		boolean flag1=true;
		for(String b:peggy_visit_nodes_temp)
		{
		if(z.equals(b))
			{flag1=false;
			break;}
		}
		
		if(flag1){
		for(String c:peggy_visit_nodes_final)
		{
		if(z.equals(c))
			{flag1=false;
			break;}
		
		}
		}
		if(flag1){
		peggy_visit_nodes_temp.add(z);
		}
		
	}
	}
	if(unavoided)
	peggy_visit_nodes_final.add(temp_id);
	peggy_visit_nodes_temp.remove(0);
	temp_peggy_flag=peggy_visit_nodes_temp.size();
	
	
}
endTime = System.currentTimeMillis();

long duration2 = (endTime - startTime);
System.out.println("Time taken to find nodes Peggy can visit is ---->"+duration2+" millisecs");


//System.out.println(peggy_visit_nodes_final);
startTime = System.currentTimeMillis();


List<String> sam_visit_nodes_temp = new ArrayList<String>(sam_start_nodes);
List<String> sam_visit_nodes_final = new ArrayList<String>();
int temp_sam_flag=sam_visit_nodes_temp.size();
while(temp_sam_flag>0)
{
	Node temp_outer_node=new Node();
	boolean unavoided=true;
	String temp_id=sam_visit_nodes_temp.get(0);
	for (String d:nodes_to_avoid)
	{
		if(d.equals(temp_id))
		unavoided=false;		
	}
	
	if (unavoided)
	{
	for( Node temp_inner_node : list_of_nodes ) {//getting node object of matching temp_id
		if(temp_inner_node.id.equals(temp_id))
		{
			temp_outer_node=temp_inner_node;
			break;
		}
		
	}
	for(String z:temp_outer_node.Upnodes)//each node on stack
	{
		boolean flag1=true;
		for(String b:sam_visit_nodes_temp)
		{
		if(z.equals(b))
			{flag1=false;
			break;}
		}
		
		if(flag1){
		for(String c:sam_visit_nodes_final)
		{
		if(z.equals(c))
			{flag1=false;
			break;}
		
		}
		}
		if(flag1){
		sam_visit_nodes_temp.add(z);
		}
		
	}
	}
	if(unavoided)
	sam_visit_nodes_final.add(temp_id);
	sam_visit_nodes_temp.remove(0);
	temp_sam_flag=sam_visit_nodes_temp.size();
	
	
}
endTime = System.currentTimeMillis();

long duration3 = (endTime - startTime);
System.out.println("Time taken to find nodes Sam can visit is ---->"+duration3+" millisecs");

//System.out.println(sam_visit_nodes_final);
startTime = System.currentTimeMillis();
for(String e:peggy_visit_nodes_final)
{
	if(sam_visit_nodes_final.contains(e))
	{output.add(e);}
}
endTime = System.currentTimeMillis();

long duration4 = (endTime - startTime);
System.out.println("Time taken to find nodes common to both(meeting nodes) is ---->"+duration4+" millisecs");


startTime = System.currentTimeMillis();
Collections.sort(output);
endTime = System.currentTimeMillis();
long duration5 = (endTime - startTime);
System.out.println("Time taken to sort the meeting nodes is---->"+duration5+" millisecs");

long duration6=duration1 + duration2 +duration3 +duration4 +duration5;
System.out.println("Total Time taken is---->"+duration6+" millisecs");


/*
startTime = System.currentTimeMillis();
String[] stockArr = new String[output.size()];
stockArr = output.toArray(stockArr);
//startTime = System.currentTimeMillis();
MyQuickSort sorter = new MyQuickSort();
sorter.sort(stockArr);
endTime = System.currentTimeMillis();
duration = (endTime - startTime);
System.out.println("quicksortis ------------------------------"+duration);
*/
System.out.println("output size is ------------------------------"+(output.size()));
for(String f:output)
{
//System.out.println(f);
}

}
}


class Node{
String id;
List<String> Downnodes = new ArrayList<String>();
List<String> Upnodes = new ArrayList<String>();
	
	
	
}


 

 

 
class MyQuickSort {
     
    private String array[];
    private int length;
 
    public void sort(String[] inputArr) {
         
        if (inputArr == null || inputArr.length == 0) {
            return;
        }
        this.array = inputArr;
        length = inputArr.length;
        quickSort(0, length - 1);
    }
 
    private void quickSort(int lowerIndex, int higherIndex) {
         
        int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot number, I am taking pivot as middle index number
        String pivot = array[lowerIndex+(higherIndex-lowerIndex)/2];
        // Divide Stringo two arrays
        while (i <= j) {
            /**
             * In each iteration, we will identify a number from left side which 
             * is greater then the pivot value, and also we will identify a number 
             * from right side which is less then the pivot value. Once the search 
             * is done, then we exchange both numbers.
             */
            while (array[i].compareTo(pivot)<0) {
                i++;
            }
            while (array[j].compareTo(pivot)>0) {
                j--;
            }
            if (i <= j) {
                exchangeNumbers(i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }
 
    private void exchangeNumbers(int i, int j) {
        String temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
     
    
         
        
    
}







