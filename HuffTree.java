
public class HuffTree {
	//HuffTree class to generate a tree with nodes that have both character and weight attributes
	private int weight;
	private char element;
	private HuffTree left;
	private HuffTree right;
	private Boolean isLeaf;
	
	//constructor for leaf, isLeaf bool is true, has no left or right children
	HuffTree(char el, int wt){ 
		
		element = el; weight = wt; left = null; right = null; isLeaf = true;
	
	}
	
	HuffTree(HuffTree l, HuffTree r, int wt){
		//constructor for an internal node, isLeaf is false, has left and right children
		left = l; right = r; weight = wt; isLeaf = false; element = (char)0;
		
	}
	//getter methods for private members
	int weight(){
		return weight; 
		
	 }
	char element(){
		return element; 
		
	 }
	HuffTree left(){
		return left; 
		
	 }
	HuffTree right(){
		return right; 
		
	 }
	Boolean isLeaf(){
		return isLeaf;
	}
}
