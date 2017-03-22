package bin;

import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class EveryRecord {

	//创建时间记录
	String createTime;
	
	//昵称
	String nickname;
	
	//手机号码
	String phone;
	
	public String getCreateTime() {
		return createTime;
	}
	
	public Date getCreateTimeDate() throws ParseException{
		//先定义一个SimpleDateFormat格式
		SimpleDateFormat mySimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		
		//把string类型的createTime转换为long类型
		Long createTimeLong = Long.parseLong(this.createTime);
		String d = mySimpleDateFormat.format(createTimeLong);
	
		Date date = (Date) mySimpleDateFormat.parse(d);
		return date;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public EveryRecord(String createTime, String nickname, String phone) {
		super();
		this.createTime = createTime;
		this.nickname = nickname;
		this.phone = phone;
		removeBracket();
		//convertCreateTime();
	}
	
	private void removeBracket(){
		
		createTime = createTime.substring(1,createTime.length()-1);
		if(nickname !="")
			nickname = nickname.substring(2,nickname.length()-2);
		phone = phone.substring(2,phone.length()-2);
		
	}
	
	//把createTime从unix时间转换
	private void convertCreateTime(){
		//long类型不能直接参与运算，转换为double类型
		double createTimeDouble = Double.parseDouble(this.createTime);
		createTimeDouble = (createTimeDouble/1000+8*3600)/86400+70*365+19;
		//int createTimeInt = (int) Math.round(createTimeDouble);
		BigDecimal bDecimal = new BigDecimal(createTimeDouble);
		createTimeDouble = bDecimal.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.createTime = Double.toString(createTimeDouble);
	}
	
	public String toString(){
		return this.createTime+"\t"+this.nickname+"\t"+this.phone;
	}
		
}
