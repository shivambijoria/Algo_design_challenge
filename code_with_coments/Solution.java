import java.util.*;
import java.util.Collections;

/*Node class representing each vertex,id is the string which is used to identify the object like in edges,downnodes is a list consisting of list of node's
ids which DIRECTLY lie on a downstream from this edge.Upnodes is list of node's ids which have DIRECTLY lie on a upstream from this node.
*/
class Node{
String id;
List<String> Downnodes = new ArrayList<String>();
List<String> Upnodes = new ArrayList<String>();
}

//it is divided into 5 parts for easy understanding
class Solution{
public static void main(String args[]){
Scanner reader = new Scanner(System.in);  
List<Node> list_of_nodes = new ArrayList<Node>();//this list will contain all the nodes(vertices) which comes in edges.
boolean tempflag=true;
boolean inputflag=true;//this flag is used to process all the edges and will be falsified as we encounter Avoid: in our input
List<String> nodes_to_avoid = new ArrayList<String>();//list to store all the vertices in the line following Avoid: in the input:
List<String> peggy_start_nodes = new ArrayList<String>();//list to store all the vertices in the line following Peggy: in the input:
List<String> sam_start_nodes = new ArrayList<String>();//list to store all the vertices in the line following Sam: in the input
List<String> output = new ArrayList<String>();
/////////////////////////////////////// PART1 BEGINS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//PART1 -it will digest all the input will creat the internal graph representation,consisting of all nodes(each object with string id,its upstream
// and downstream neighbours),nodes to avoid and starting positions of peggy and sam 
while(inputflag)
{
	String line=reader.nextLine();//for reading every line of input 
	boolean nodeflag=true;//will be ON on every loop execution
	if(line.equals("Avoid:"))//if input line is Avoid:
	{	
		String nextline=reader.nextLine();
		String[] temp = nextline.split("\\s+");
		for( String x : temp ) {//loop for storing every node to be avoided in the corresponding list
			nodes_to_avoid.add(x);
		}	
		nodeflag=false;//switched off for now
	}
	
	if(line.equals("Peggy:"))
	{	
		String nextline=reader.nextLine();
		String[] temp = nextline.split("\\s+");
		for( String x : temp ) {//loop for storing every node that is starting node of peggy in the corresponding list
			peggy_start_nodes.add(x);
		}
		nodeflag=false;//switched off for now
	}
	
	if(line.equals("Sam:"))
	{	
		String nextline=reader.nextLine();
		String[] temp = nextline.split("\\s+");
		for( String x : temp ) {
			sam_start_nodes.add(x);//loop for storing every node that is starting node of Sam in the corresponding list
		}
		nodeflag=false;//switched off off for now
		inputflag=false;//switched off,now the outer while loop wont repeat as input is over and part1 ends.
	}
	
	if(line.equals("Map:"))//we just pass this line,doing nothing
	{
		nodeflag=false;//switched off for now
	}	
	
	//if the input line is an edge THAT MEANS it is not an MAP: line and  AVOID:,SAM:,PEGGY or its subsequent line. 
	if(nodeflag){	
		String[] nodes_of_edge = line.split("\\s+");
		String from_node=nodes_of_edge[0];//source vertex of the edge,node on the upstream for second edge
		String to_node=nodes_of_edge[1];//destination vertex of the edge,node on the downstream for the first edge
		boolean from_node_flag=false;//flag to keep track whether source node is coming for the first time 
		boolean to_node_flag=false;//flag to keep track whether destination node is coming for the first time 
		
		for( Node temp_node : list_of_nodes ) {//this loop to check source and destination node with all the nodes that have been encountered till now.
		if(temp_node.id.equals(from_node))//if source node has already been encountered
		 {
			 from_node_flag=true;//flag adjusted to show source node has already been encountered
			 temp_node.Downnodes.add(to_node);//destination node added to downstream nodes of source node
		}
		 if(temp_node.id.equals(to_node))//if destination node has already been encountered
		 {
			 to_node_flag=true;//flag adjusted to show destination node has already been encountered
			 temp_node.Upnodes.add(from_node);//source node added to upstream nodes of destination node.
		 }
		 if(from_node_flag && to_node_flag )
		 {
			 break;//the node checking of all nodes encountered till now is STOPPED because both source node and destination node were
			 //already encountered and have been taken care of.
		 }
		}
		if(!from_node_flag)//if source node is encountered for the first time
		{
			Node temp=new Node();//new node object created,with right id and destination node as the first entry into downstream nodes list
			temp.id=from_node;
			temp.Downnodes.add(to_node);
			list_of_nodes.add(temp);
		}
		
		if(!to_node_flag)//if destination node is encountered for the first time
		{
			Node temp=new Node();//new node object created,with right id and source node as the first entry into upstream nodes list
			temp.id=to_node;
			temp.Upnodes.add(from_node);
			list_of_nodes.add(temp);
		}
	}
}
///////////////////////PART1 ENDS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

///////////////PART2 STARTS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//PART2 builds a list which contains all the nodes which peggy can visit 
List<String> peggy_visit_nodes_temp = new ArrayList<String>(peggy_start_nodes);//this list doing the job of queue keeping the nodes which peggy
// can visit and have yet not been processed.queue initialized with all the starting locations of peggy.
List<String> peggy_visit_nodes_final = new ArrayList<String>();//this list is populated as nodes in peggy_visit_nodes_temp(queue) are processed
//completely
int temp_peggy_flag=peggy_visit_nodes_temp.size();
while(temp_peggy_flag>0)//This loop continues,untill all the nodes in queue peggy_visit_nodes_temp are processed.
{
	Node temp_outer_node=new Node();
	boolean unavoided=true;//this flag keeps track whether the node's is on avoid list or not
	String temp_id=peggy_visit_nodes_temp.get(0);//gets the first node from our queue
	for (String d:nodes_to_avoid)//checks whether that node is on avoid list or not.
	{
		if(d.equals(temp_id))
		unavoided=false;//if this node has to be avoided the flag shows that		
	}
	if (unavoided)//proceed further if only the node is not on avoided nodes list.
	{
	for( Node temp_inner_node : list_of_nodes ) {//loop to get the object whose Node.id is our temp_id(queue's first string)
		if(temp_inner_node.id.equals(temp_id))
		{
			temp_outer_node=temp_inner_node;
			break;
		}
		
	}
	for(String z:temp_outer_node.Downnodes)//Now,we have got the corresponding object whose id is on queue
	//all nodes on that objects downstream list are processed,to check whether they have been encountered earlier(are present on temp list or final list)
	//if not than they would be put on queue.
	{
		boolean flag1=true;
		for(String b:peggy_visit_nodes_temp)//checks whether that node is already on queue or not
		{
		if(z.equals(b))
			{flag1=false;
			break;}
		}
		if(flag1){
		for(String c:peggy_visit_nodes_final)//checks whether that node is already on processed nodes list or not
		{
		if(z.equals(c))
			{flag1=false;
			break;}
		
		}
		}
		if(flag1){//if it is not both on temp queue and processed nodes list than it is added to our temp queue
		peggy_visit_nodes_temp.add(z);
		}
		
	}
	}
	if(unavoided)//now the first element of queue has passed all the pocessing and then it is removed from queue and moved to proccessed nodes list
	peggy_visit_nodes_final.add(temp_id);
	peggy_visit_nodes_temp.remove(0);
	temp_peggy_flag=peggy_visit_nodes_temp.size();//this is the flag of outer loop which gets the size of temp queue as soon as it becomes <=0,PART2 
	//is finished and we have extracted all the locations peggy can be.
}
////////////////////////////////////PART2 ENDS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

////////////////////////////////////PART3 STARTS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//THIS PART IS EXACTLY SIMILAR to part2,just here we find all the nodes sam can travel to
List<String> sam_visit_nodes_temp = new ArrayList<String>(sam_start_nodes);//this list doing the job of queue keeping the nodes which sam
// can visit and have yet not been processed.queue initialized with all the starting locations of sam.
List<String> sam_visit_nodes_final = new ArrayList<String>();//this list is populated as nodes in sam_visit_nodes_temp(queue) are processed
//completely
int temp_sam_flag=sam_visit_nodes_temp.size();
while(temp_sam_flag>0)//This loop continues,untill all the nodes in queue sam_visit_nodes_temp are processed.
{
	Node temp_outer_node=new Node();
	boolean unavoided=true;//this flag keeps track whether the node's id is on avoid list or not
	String temp_id=sam_visit_nodes_temp.get(0);//gets the first node from our queue
	for (String d:nodes_to_avoid)//checks whether that node is on avoid list or not.
	{
		if(d.equals(temp_id))
		unavoided=false;	//if this node has to be avoided the flag shows that		
	}
	if (unavoided)//proceed further if only the node is not on avoided nodes list.
	{
	for( Node temp_inner_node : list_of_nodes ) {//loop to get the object whose Node.id is our temp_id(queue's first string)
		if(temp_inner_node.id.equals(temp_id))
		{
			temp_outer_node=temp_inner_node;
			break;
		}
	}
	for(String z:temp_outer_node.Upnodes)//Now,we have got the corresponding object whose id is on queue
	//all nodes on that objects upstream list are processed,to check whether they have been encountered earlier(are present on temp list or final list)
	//if not than they would be put on queue.
	{
		boolean flag1=true;
		for(String b:sam_visit_nodes_temp)//checks whether that node is already on queue or not
		{
		if(z.equals(b))
			{flag1=false;
			break;}
		}
		
		if(flag1){
		for(String c:sam_visit_nodes_final)//checks whether that node is already on processed nodes list or not
		{
		if(z.equals(c))
			{flag1=false;
			break;}
		
		}
		}
		if(flag1){//if it is not both on temp queue and processed nodes list than it is added to our temp queue
		sam_visit_nodes_temp.add(z);
		}
	}
	}
	if(unavoided)//now the first element of queue has passed all the pocessing and then it is removed from queue and moved to proccessed nodes list
	sam_visit_nodes_final.add(temp_id);
	sam_visit_nodes_temp.remove(0);
	temp_sam_flag=sam_visit_nodes_temp.size();//this is the flag of outer loop which gets the size of temp queue as soon as it becomes <=0,PART2 
	//is finished and we have extracted all the locations Sam can be
}
////////////////////////PART3 ENDS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

////////////////////PART4 STARTS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//our aim is to find the common locations where both peggy and sam can be,it is a simple: one location of peggy is matched with all locations 
//with all the locations of sam,if it matches it is added to final output list.its complexity is O(|sam possible loc|*|peggy possible loc|)
//i have'nt done something fancy like binary search and all because first i have to sort both the list and this will take all the advantages in time
//saving which i would have accumulated and it is taking less than 100 millisecs for upto 500k(total time~80 secs) edges.So,it is less than .0011% of all computational time.
 
for(String e:peggy_visit_nodes_final)
{
	if(sam_visit_nodes_final.contains(e))
	{output.add(e);}
}
//////////////////////PART4 ENDS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
////////////////////PART5 STARTS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//the output list is sorted,i haven't done sorting like quicksort etc which could provide O(nlogn ) because in the results both were near
//8-10 millisec for 500k edges (total time~80 secs) which is .000001% of all time taken.

Collections.sort(output);

////////////////////PART5 ENDS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

for(String f:output)
{
System.out.println(f);//printing each vertex which is valid meeting vertex of sam and peggy on new-line.s
}

}
}




 
