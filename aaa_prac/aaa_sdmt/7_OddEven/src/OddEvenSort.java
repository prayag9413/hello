public class OddEvenSort {
    
    public static String sort(int array[]){
    	int n = array.length;
    	String result = "";
    	for (int i = 0; i < n/2; i++ ) {
    		for (int j = 0; j+1 < n; j += 2)
    			if (array[j] > array[j+1]) {
    				int T = array[j];
    				array[j] = array[j+1];
    				array[j+1] = T;
    			}
    	for (int j = 1; j+1 < array.length; j += 2)
    		if (array[j] > array[j+1]) {
    			int T = array[j];
    			array[j] = array[j+1];
    			array[j+1] = T;
    		}
    	}
    	for(int i = 0; i < array.length; i++)
    	{
    		result+=array[i]+" ";
    	}
    	System.out.println("result "+result);
    	return result;
    }

}
