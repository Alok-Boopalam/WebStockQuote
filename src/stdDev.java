import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


public class stdDev {

	public static void calculate(Multimap<String, Float> list) throws IOException {
		
		Multimap<String, Float> list1 = list;
		float maxstd=0;
		String name= "";
		
		Object[] q;
		
		
		
		for (String key : list1.keySet())
		{
		    Collection<Float> value = list1.get(key);
		  	q= value.toArray();
		  	float min = (float)q[0];
			float max = (float)q[0];
		 
			for (int i = 1; i <= q.length - 1; i++) {
				if (max < (float)q[i]) {
					max = (float)q[i];
				}
		 
				if (min >(float)q[i]) {
					min = (float)q[i];
				}
			}
		 
			System.out.println("min: " + min + "\nmax: " + max);
			for(int j=0;j<q.length ;j++)
			{
				
				q[j] =(((float)q[j]) - min)/(max - min);
			}
			float sum = 0;
			for(int j=0;j<q.length ;j++)
			{
				sum = sum+((float)q[j]);
			}
			float avg= sum/q.length;
			
			List<Float> diff = new ArrayList<>();
			for(int j=0;j<q.length ;j++)
			{
				float difference = ((float)q[j])-avg;
				diff.add(difference);
			}
			

			List<Float> squares = new ArrayList<>();
			for(float difference : diff)
			{
				float square = difference*difference;
				squares.add(square);
			}
			
			sum=0;
			for(float number:squares)
			{
				sum=sum+number;
			}
			
			float result = sum/(q.length-1);
			float std = (float) Math.sqrt(result);
			System.out.println(key);
			System.out.println(std);
			if (std>maxstd)
			{
				maxstd = std;
				name = key;
			}
			
		}
	System.out.println("The symbol of the stock that fluctuates most is "+name);
	FileWriter write = new FileWriter("91527264.txt",true);
	BufferedWriter towrite1 = new BufferedWriter(write);
	 towrite1.write("The symbol of the stock that fluctuates most is "+name);
	towrite1.close();
	}
}
