package bin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


import org.apache.poi.hssf.usermodel.HSSFCell;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 *类ReadFile
 *读取文件，分析文件，转换为Json字符串，构造对象类，转换为目的的excel文件
 */
public class ReadFile {
	static long start = System.currentTimeMillis();
	private static HSSFWorkbook wb;
	
	public static void main(String[] args){
		
		String myFileTarget = args[0];
		String fileOutDir = args[1];
		System.out.println("初始化文件"+myFileTarget);
		String jsonString = readTxtFile(myFileTarget);
		System.out.println("读取文件完成");
		try {
			//这里的filedir要用/结尾，不然在linux系统中容易误判断
			//已证明的**/software 会生成文件software/yunnan
			ArrayListToExcel(jsonToList(jsonString),fileOutDir+"yunnan"+currentMonthDay()+"-v1.0.xls");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		/*String myFileTarget = "D:\\cmos\\微信\\每天提取数据\\yunnan20161013.txt";
		String fileOutDir = "D:\\cmos\\微信\\每天提取数据";
		System.out.println("初始化文件"+myFileTarget);
		String jsonString = readTxtFile(myFileTarget);
		System.out.println("读取文件完成");
		try {
			ArrayListToExcel(jsonToList(jsonString),fileOutDir+"\\云南数据提取"+"1001"+"-v1.0.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		
		
		long stop = System.currentTimeMillis();
		System.out.println("Program running "+(stop-start)/1000+"s");
	}
	
	public static String readTxtFile(String filePath){
		String result ="";
		try{
				String encoding = "utf-8";
				File file = new File(filePath);
				if(file.isFile() && file.exists()){
					InputStreamReader read = new InputStreamReader(
							new FileInputStream(file),encoding);
					BufferedReader bufferedReader = new BufferedReader(read);
					String lineTxt = null;
					while((lineTxt = bufferedReader.readLine())!=null){
						result += lineTxt;
					}
					read.close();
				}else{
					System.out.println("Can't find file.");
				}
				
			} catch(Exception e){
				System.out.println("Read file Error.");
				e.printStackTrace();
			}
		return result;
	}
	
	public static ArrayList<EveryRecord> jsonToList(String stringLines){
		ArrayList<EveryRecord> myList = new ArrayList<EveryRecord>();
		
		//这里是遇到在txt编辑器打开后保存后json文档开头多了一个字符的问题
		//用substring(1)解决这个问题，在linux系统中该问题不复现
		String myString = stringLines.substring(0);
		JSONObject object = JSONObject.fromObject(myString);
		
		long timeNow = System.currentTimeMillis();
		System.out.println("加载文件创建JSON对象完成."+"共用时："+(timeNow - start)/1000 +"s");
		//System.out.println(object.toString());
		
		JSONObject myObject = object.getJSONObject("hits");
		JSONArray myArray = myObject.getJSONArray("hits");
		
		Iterator<?> iter = myArray.iterator();
		int countRow=0;
		while(iter.hasNext()){

			String str1 = iter.next().toString();
			//System.out.println(str1);
			JSONObject everyPersion = JSONObject.fromObject(str1);
			//System.out.println(everyPersion.get("fields"));
			
			JSONObject everyPersion1 = JSONObject.fromObject(everyPersion.get("fields"));
			
			String createTime = "";
			String nickname = "";
			String phone = "";
			if(everyPersion1.containsKey("createTime")){
				createTime = everyPersion1.get("createTime").toString();
			}else {
				createTime = "";
			}
		    
			if(everyPersion1.containsKey("nickname")){
				nickname = everyPersion1.get("nickname").toString();
			}else{
				nickname = "";
			}
		    
			if(everyPersion1.containsKey("phone")){
				phone = everyPersion1.get("phone").toString();
			}else{
				phone = "";
			}
		    
		    EveryRecord aRecord = new EveryRecord(createTime,nickname,phone);
		    myList.add(aRecord); 
		    
		    if(countRow % 100 ==0)
		    	System.out.println("正在分析第 "+countRow+" 条数据");
		    
		    countRow++;
		}
		return myList;
	}
	
	
	/*
	 * 用来把arraylist类型的collection写入excel
	 * 这里写入的excel还是03版的excel，行数最大为65535，有限制，后期更改为07版的excel
	 */
	public static void  ArrayListToExcel(ArrayList<EveryRecord> mylist, String fileURL){
		wb = new HSSFWorkbook();
		
		//生产excel表的表头
		HSSFSheet  sheet =  wb.createSheet("1");
		HSSFRow  row =   sheet.createRow(0);
	    row.createCell(0).setCellValue("时间");
	    row.createCell(1).setCellValue("昵称");
	    row.createCell(2).setCellValue("手机号");
	    
	    //将队列中的每一个元素遍历输出到excel中
	    //队列中的每一个值均为EveryRecord对象
	    Iterator<EveryRecord> iter = mylist.iterator();
	    int count = 1;
	    while(iter.hasNext()){
	    	EveryRecord a = iter.next();
	    	HSSFRow myRow =  sheet.createRow(count);
	    	HSSFCell myCellCreateTime = myRow.createCell(0);
	    	
	    	//设置日期格式
	    	try {
	    		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		myCellCreateTime.setCellValue(myFormat.format(a.getCreateTimeDate()));
				//myCellCreateTime.setCellValue(a.getCreateTimeDate().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	
	    	myRow.createCell(1).setCellValue(a.getNickname());
	    	myRow.createCell(2).setCellValue(a.getPhone());
	    	
	    	if(count % 100 ==0)
	    		System.out.println("正在写入第 "+count+" 条数据。");
	    	count++;
	    }
	    
	    try {
	    	//write out the workbook to an OutputStream.
			FileOutputStream fileOut = new FileOutputStream(fileURL);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("There is something wrong with the excel output.");
			e.printStackTrace();
		}
	}
	
	//返回当前的日期如10-3
	public static String currentMonthDay() throws ParseException{
		long a = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat();
		String currentTime = sdf.format(a);
		Date myDate = sdf.parse(currentTime);
		
		SimpleDateFormat mySdf = new SimpleDateFormat("yyyMMdd");
		return mySdf.format(myDate);
	}
}
