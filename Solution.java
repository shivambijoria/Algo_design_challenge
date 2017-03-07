import java.util.*;
import java.util.Collections;


class Node{
String id;
List<String> Downnodes = new ArrayList<String>();
List<String> Upnodes = new ArrayList<String>();
}


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
	for( Node temp_inner_node : list_of_nodes ) {
		if(temp_inner_node.id.equals(temp_id))
		{
			temp_outer_node=temp_inner_node;
			break;
		}
		
	}
	for(String z:temp_outer_node.Downnodes)
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
	for( Node temp_inner_node : list_of_nodes ) {
		if(temp_inner_node.id.equals(temp_id))
		{
			temp_outer_node=temp_inner_node;
			break;
		}
	}
	for(String z:temp_outer_node.Upnodes)
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

for(String e:peggy_visit_nodes_final)
{
	if(sam_visit_nodes_final.contains(e))
	{output.add(e);}
}

Collections.sort(output);

for(String f:output)
{
System.out.println(f);
}

}
}




 
