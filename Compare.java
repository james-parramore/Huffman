import java.util.Comparator;
//custom comparator for priority queue to compare our objects by weight
public class Compare implements Comparator<HuffTree> {

	@Override
	public int compare(HuffTree o1, HuffTree o2) {
		
		if (o1.weight() < o2.weight())
			return -1;
        else if (o1.weight() > o2.weight())                                  
        	return 1;
        else 
        	return 0;
	}

}
