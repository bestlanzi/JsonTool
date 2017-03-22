package bin;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args){
		String a = "hello world";
		System.out.println(a.length());
		System.out.println(a.substring(1,a.length()-1));
		System.out.println(Double.MAX_VALUE);
		
		/*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		double b = 1474905574299;
		int b = 1474905574299;
		long b = 1474905574;
		String d = simpleDateFormat.format(b);
		
		try {
			Date aa = (Date) simpleDateFormat.parse(d);
			System.out.println(aa);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		double aaa = 1.7976931348623157;
		for (int i=0; i<308; i++)
		{
			aaa = aaa*Math.E;
		}
		System.out.println(aaa);
		
		String bb = "1474905574299";
		System.out.println(bb.substring(0,10));
		
		SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Long cc = Long.parseLong(bb);
		String dddString = si.format(cc);
		try {
			Date date  = si.parse(dddString);
			System.out.println(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    System.out.println(System.currentTimeMillis());
	    SimpleDateFormat s = new SimpleDateFormat();
	    long myLong = (System.currentTimeMillis());
	    String myLong1 = s.format(myLong);
	   
	    try {
			Date myDate = s.parse(myLong1);
			System.out.println(myDate);
			
			SimpleDateFormat ss = new SimpleDateFormat("MM-dd");
			System.out.println(ss.format(myDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
		
	}
}
