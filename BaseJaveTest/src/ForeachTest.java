import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ForeachTest {

	public static void main(String[] args) {
		int[] numArray = { 1, 2, 3, 4, 5, 6, 7, 8 };

		/*
		 * for (int i = 0; i < numArray.length; i++) {
		 * System.out.print(numArray[i]+" "); }
		 */

		// for(类型 变量:遍历对象){}
		for (int a : numArray) {
			System.out.print(a + " ");
		}
		/*
		 * private List<HashMap<String, String>> contactList = new
		 * ArrayList<HashMap<String, String>>();
		 * 
		 * HashMap<String, String> hashMap = new HashMap<String, String>();
		 * 
		 * hashMap.put("wuhuiquan", "13590835880"); hashMap.put("qiuxiaojun",
		 * "13424515045");
		 */

	}

}
