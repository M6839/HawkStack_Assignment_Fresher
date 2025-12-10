
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueElement {
	public static int uniqueElemets(List<Integer> li) {
		Set<Integer> uniqueEle=new HashSet<>();
		if(li.size()==0) {
			return 0;
		}
		else {
			for(int num:li) {
				uniqueEle.add(num);
			}
			return uniqueEle.size();
		}
		
		
	}
	public static void main(String[] args) {
		List<Integer> li=new ArrayList<>(List.of(1,2,2,3,4,4));
//		List<Integer> li=new ArrayList<>();
		System.out.println("The number of Unique Elements are: "+uniqueElemets(li));
	}
	

}
