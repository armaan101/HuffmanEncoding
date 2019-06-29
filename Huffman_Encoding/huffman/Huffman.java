package huffman;
import java.util.HashMap;
import java.util.Map;
import huffman.MinHeap;

public class Huffman {
	private String original_str;
	private String encoded_str;
	private String decoded_str;
	public HashMap<Character,Integer> hmap_count;
	public HashMap<Character,String> hmap_code;
	public HashMap<String,Character> hmap_code_reverse;
	private MinHeap<Node> heap;
	private int counter;
	private int treeSize;
	private Node root;
	
	private class Node implements Comparable<Node>{
		int id;
		int weight;
		char ch;
		Node left;
		Node right;
		
		Node(char ch,int weight,Node left,Node right) {
			this.id=counter++;
			this.ch=ch;
			this.weight=weight;
			this.left=left;
			this.right=right;
			
		}
		
		@Override
		public int compareTo(Node other) {
			return this.weight-other.weight;
		}
	}
	
	public Huffman(String original_str) {
		this.counter=0;
		this.treeSize=0;
		this.original_str=original_str;
		this.hmap_count=new HashMap<>();
		this.hmap_code=new HashMap<>();
		this.hmap_code_reverse=new HashMap<>();
		
		
		count_freq();
		build_tree();
		build_code_table();
		
	}
	
	private void count_freq() {
		for(int i=0;i<this.original_str.length();i++) {
			char ch= this.original_str.charAt(i);
			if(!this.hmap_count.containsKey(ch)) {
				this.hmap_count.put(ch, 1);
			}
			else {
				int rv=this.hmap_count.get(ch);
				this.hmap_count.put(ch, rv+1);
			}
		}
	}
	
	private void build_tree() {
		build_MinHeap();
		Node left,right;
		while(!this.heap.isEmpty()) {
			left=this.heap.remove();
			this.treeSize++;
			if(!this.heap.isEmpty()) {
				right=this.heap.remove();
				this.treeSize++;
				this.root=new Node('\0',left.weight+right.weight,left,right);
			}
			else {
				this.root=new Node('\0',left.weight,left,null);
			}
			if(!this.heap.isEmpty()) {
				this.heap.add(this.root);	
			}
			else {
				this.treeSize++;
				break;
			}
		}
	}
	
	private void build_MinHeap() {
		Node arr[]=new Node[hmap_count.size()];
		int i=0;
		for(Map.Entry<Character,Integer> entry: hmap_count.entrySet()) {
			char ch=entry.getKey();
			int wt=entry.getValue();
			arr[i++]=new Node(ch,wt,null,null);
		}
		this.heap=new MinHeap<>(arr);
	}
	
	private void build_code_table() {
		String s="";
		Node temp=this.root;
		build_code_table_util(temp,s);
	}
	
	private void build_code_table_util(Node node,String s) {
		if(node==null)
			return;
		if(node.left==null && node.right==null) {
			hmap_code.put(node.ch, s);
			hmap_code_reverse.put(s,node.ch);
		}
		else {
			if(node.left!=null) {
				build_code_table_util(node.left, s+'0');	
			}
			if(node.right!=null) {
				build_code_table_util(node.right, s+'1');
			}
			
		}
	}
	public String encode() {
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<original_str.length();i++) {
			sb.append(hmap_code.get(original_str.charAt(i)));
		}
		this.encoded_str=sb.toString();
		return this.encoded_str;
	}
	
	public String decode() {
		StringBuilder sb=new StringBuilder();
		String s="";
		for(int i=0;i<this.encoded_str.length();i++) {
			s=s+this.encoded_str.charAt(i);
			if(hmap_code_reverse.containsKey(s)) {
				sb.append(hmap_code_reverse.get(s));
				s="";
			}
		}
		this.decoded_str=sb.toString();
		return this.decoded_str;
	}
}
