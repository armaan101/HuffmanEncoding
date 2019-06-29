package huffman;
import java.util.ArrayList;
public class MinHeap<T extends Comparable<T>>{
	private ArrayList<T> heap;
	
	public MinHeap(T[] arr) {
		this.heap=new ArrayList<>();
		for(T value : arr) {
			this.heap.add(value);
		}
		for(int i=(this.heap.size())/2-1;i>=0;i--) {
			this.downheapify(i);
		}
	}
	public int size() {
		return this.heap.size();
	}
	public T getMin() {
		return this.heap.get(0);
	}
	public boolean isEmpty() {
		return this.heap.size()==0;
	}
	public void add(T data) {
		this.heap.add(data);
		this.upheapify(this.heap.size()-1);
	}
	private boolean isLarger(int pi,int ci) {
		T p_val=this.heap.get(pi);
		T ci_val=this.heap.get(ci);
		
		return p_val.compareTo(ci_val)>0; 
	}
	private void upheapify(int ci) {
		if(ci==0)
			return;
		
		int pi=(ci-1)/2;
		if(isLarger(pi,ci)) {
			this.swap(pi, ci);
			this.upheapify(pi);
		}
		
		
	}
	private void downheapify(int pi) {
		int lc=2*pi+1;
		int rc=2*pi+2;
		
		int smallest=pi;
		if(lc<this.heap.size()&& isLarger(smallest,lc)) {
			smallest=lc;
		}
		if(rc<this.heap.size()&&isLarger(smallest,rc)) {
			smallest=rc;
		}
		if(smallest!=pi) {
			this.swap(pi,smallest);
			this.downheapify(smallest);
		}
	}
	private void swap(int i,int j) {
		T temp=this.heap.get(i);
		this.heap.set(i, this.heap.get(j));
		this.heap.set(j, temp);
	}
	public T remove() {
		T item=this.heap.get(0);
		this.swap(0,this.heap.size()-1);
		this.heap.remove(this.heap.size()-1);
		this.downheapify(0);
		return item;
	}
	public void display() {
		this.display(0);
	}
	private void display(int idx) {
		int lci=2*idx+1;
		int rci=2*idx+2;
		String ans="";
		if(lci<this.heap.size()) {
			T lc = this.heap.get(lci);
			ans+=lc+" =>";
		}
		else {
			ans+="END =>";
		}
		ans+=this.heap.get(idx);
		if(rci<this.heap.size()) {
			T rc = this.heap.get(rci);
			ans+= "<= "+rc;
		}
		else {
			ans+="<= END";
		}
		System.out.println(ans);
		
		if (lci < this.heap.size()) {
			this.display(lci);
		}

		if (rci < this.heap.size()) {
			this.display(rci);
		}
	}
}
