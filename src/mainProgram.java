import java.io.*;
import java.util.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


public class mainProgram {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		ArrayList<String> sym = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>();
		sym.add("MMM");sym.add("AXP");sym.add("AAPL");sym.add("BA");sym.add("CAT");sym.add("CVX");sym.add("CSCO");sym.add("KO");sym.add("DD");sym.add("XOM");sym.add("GE");sym.add("GS");sym.add("HD");sym.add("INTC");sym.add("IBM");sym.add("JNJ");sym.add("JPM");sym.add("MCD");sym.add("MRK");sym.add("MSFT");sym.add("NKE");sym.add("PFE");sym.add("PG");sym.add("TRV");sym.add("UNH");sym.add("UTX");sym.add("VZ");sym.add("V");sym.add("WMT");sym.add("DIS");
		Multimap<String, Float> list = ArrayListMultimap.create();
		File file = new File("91527264.txt");
		 
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter write = new FileWriter("91527264.txt",true);
		BufferedWriter towrite = new BufferedWriter(write);
		
		for(int k=0;k<144;k++)
		{	
		for(int i=0;i<sym.size();i++)
		{
		AccessData ad = new AccessData(sym.get(i));
		float res = Float.parseFloat(ad.getQuote());
		list.put(sym.get(i), res);
		
	    }
        System.out.print(k+1);
	
		
        if(k!=143)
		Thread.sleep(10 *60 *1000); 
		}
		
		for (String key : list.keySet())
		{
		     Collection<Float> value = list.get(key);
		     
		     towrite.write("Stock of "+key+" is "+value+"\n");
		}

		towrite.close();
		stdDev std = new stdDev();
		std.calculate(list);
		
	}
}
