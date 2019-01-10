import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HuffmanEncoder implements HuffmanCoding {
	
	//recursive function starts at the root and traverses every node, adding a 0 for left traversal, 1 for right. 
	//function builds a string of zeros and ones representing character code at the leaf approached
	public String recursiveTraverse(HuffTree root, String string)
    {
		
		if(root.isLeaf()){
	        return root.element() + " " + string + "\n";	
		}
		return recursiveTraverse(root.left(), string + "0") + recursiveTraverse(root.right(), string + "1");
        
    }
		
	//take a file as input and create a table with characters and frequencies//print the characters and frequencies
	public String getFrequencies(File inputFile) throws FileNotFoundException{
		
		if(inputFile == null)
			throw new FileNotFoundException();
		
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		StringBuilder sb=new StringBuilder();  
		int nextChar;
		String str = "";
		char c;
		int[] count = new int[96];
		
		//while not at the end of the file, c equals the next character in the string
		try{
			while ((nextChar = br.read()) != -1) {
				
				c = ((char) nextChar);
		//if the character is withing the limit for ASCII characters being searched for, increment the counter for the index that represents that character
				if (c >= (char)32 && c <= (char)127)
					count[c - ' ']++;
				
			}//move through the array and add the character and the frequency to a string in the proper format
			for (int i = 32; i < 128; i++) {
				if(count[i - ' '] != 0){
					sb.append((char)i + " " + count[i - ' '] + "\n");
				}
				
			}
			str = sb.toString();
			//System.out.print(str);
			
		}
		
		catch (IOException e){
			e.printStackTrace();
			
		}
	return str;
	
	}
	//take a file as input and create a Huffman Tree
	public HuffTree buildTree(File inputFile) throws FileNotFoundException, Exception{
		
		try {
			PriorityQueue<HuffTree> queue=new PriorityQueue<HuffTree>(new Compare());
			String string = getFrequencies(inputFile);
            Scanner scanner = new Scanner(string);
  //while the file has another line, split the line at a space reached, add the character and the freq to a new hufftree object, add object to the queue
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] details = line.split("\\s+");
                char el = line.charAt(0);
                int weight = Integer.parseInt(details[1]);
                HuffTree huff = new HuffTree(el, weight);
                queue.add(huff);
            }

            
            scanner.close();
            HuffTree root = null;
            //remove first 2 elements, create a new hufftree object and add the objects as left and right children, and add the sum of their weights as object weight
            while (queue.size() > 1) { // While two items left
            	
            	HuffTree tmp1 = queue.poll();
            	HuffTree tmp2 = queue.poll();
            	HuffTree tmp3 = new HuffTree(tmp1, tmp2, tmp1.weight() + tmp2.weight());
            	root = tmp3;
            	queue.add(tmp3);   // Return new tree to heap
              
            }
            
            return root; 
            
        } catch (FileNotFoundException e) {         
            e.printStackTrace();
            return null;
        }
		
    }
	//take a file and a HuffTree and encode the file.
	//output a string of 1's and 0's representing the file
	public String encodeFile(File inputFile, HuffTree huffTree) throws FileNotFoundException{
	//call traverseHuffmanTree, add the result to a string
		if(inputFile==null)
			throw new FileNotFoundException();
		if(huffTree == null)
			throw new NullPointerException();
		try {
	
		String string;
		Scanner scanner;
		string = traverseHuffmanTree(huffTree);
        scanner = new Scanner(string);
        ArrayList<String> codeList = new ArrayList<String>();
        char[] charList = new char[128];
        int j = 0;
        //while the scanner has another line, split the line at a space, add character to a char array, and add the code to a string array
		while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] details = line.split("\\s+");
            char element = line.charAt(0);
            String code = details[1];
            codeList.add(code);
            charList[j] = element;
            j++;
        }
		
		scanner.close();
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(inputFile)); 
			int nextChar;
			char c;
			StringBuilder sb=new StringBuilder();
			//while not at the end of the file, read the next character
			//move through the code list, if the character in the character list matches the character currently visited in the file, we add the code to a string
			while ((nextChar = br.read()) != -1) {
				c = ((char) nextChar);
				if (c >= (char)32 && c <= (char)127){
					for(int i = 0;i < codeList.size();i++){
						if(charList[i] == c){
							sb.append(codeList.get(i));
							
							}
						}
					}
				}
			br.close();
			return sb.toString();
		}
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
	}
		catch (Exception e1) {
			e1.printStackTrace();
			return null;
			
		}

	}
		
	
	//take a String and HuffTree and output the decoded words
	public String decodeFile(String code, HuffTree huffTree) throws Exception{
		
		Scanner scanner = new Scanner(code); 
		StringBuilder sb=new StringBuilder();
		char c;
		HuffTree temp = huffTree;
		String str = scanner.next();
		
		//if the character currently visited in the string is a 0, we move left, and increment our counter to move to the next character in the string
		//if the character is a 1, move right and increment
		//when the node is no longer an internal node, we add the element at that leaf node to a string, and repeat
			int i = 0;
			while(i<str.length()){
		    while(!temp.isLeaf()){
		    	
		    	c = str.charAt(i);
		    	if(c == '0'){
		    		temp = temp.left();
		    		i++;
		    	}
		    	if(c == '1'){
					temp = temp.right();
					i++;
		    	}
		    }
	    	sb.append(temp.element());
	    	temp = huffTree;
		    }
		    
	    	scanner.close();
	    	return sb.toString();
	}
	//print the characters and their codes
	public String traverseHuffmanTree(HuffTree huffTree) throws Exception{
		
		if (huffTree == null){
			System.out.print("tree is null");
			throw new Exception();
		}
		else{
		//call recursiveTraverse and add the result to a string
		//split the string when a space is visited
		//add the character to a character array, and the code to a string array
			String str = recursiveTraverse(huffTree,"");
			StringBuilder sb = new StringBuilder();
		 	Scanner scanner = new Scanner(str);
	        ArrayList<String> codeList = new ArrayList<String>();
	        char[] charList = new char[128];
	        
	        int j = 0;
			while(scanner.hasNextLine()){
	            String line = scanner.nextLine();
	            String[] details = line.split("\\s+");
	            char element = line.charAt(0);
	            String code = details[1];
	            codeList.add(code);
	            charList[j] = element;
	            j++;
	        }
			//use a form of selection sort to sort in ASCII order in the character list, and also sort the code array so all characters match their codes
			//finally add the character and the code to a string
			scanner.close();
			for(int i= 0;i<codeList.size();i++){
				int min_idx = i;
	            for (int k = i+1; k < codeList.size(); k++)
	                if (charList[k] < charList[min_idx])
	                	min_idx = k;
	                
	            char temp = charList[min_idx];
	            String temp2 = codeList.get(min_idx);
	            charList[min_idx] = charList[i];
	            codeList.set(min_idx,codeList.get(i));
	            charList[i] = temp;
	            codeList.set(i, temp2);
	            sb.append(charList[i] + " " + codeList.get(i) + "\n");
			}
		//System.out.print(sb.toString());
		return sb.toString();
		}
		
		
	}
	
	public static void main(String[] args){
		/*
		HuffmanEncoder huff = new HuffmanEncoder();
		File file = new File("randTest.txt");
		
			try {
				HuffTree root = huff.buildTree(file);
				String temp = huff.traverseHuffmanTree(root);
				String temp2 = huff.encodeFile(file, root);
				 String temp3 = huff.decodeFile(temp2, root);
				 String temp4 = huff.getFrequencies(file);
				System.out.print(temp);
				System.out.println(temp2);
				System.out.println(temp3);
				System.out.print(temp4);
			} catch (Exception e) {
				e.printStackTrace();
			}
				*/
	}

}


			