package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateParse {
	public Date string2Date(String dateString)
	{
		if(dateString == null || dateString.equals(""))
		{
			return null;
		}
		SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
	   			return format.parse(dateString+" 00:00:01");   		
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public String date2String(Date date)
	{
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
				return format.format(date).subSequence(0, 8).toString();
		}
		else {
			return null;
		}
	}
	
	public String date2AnotherString(Date date)
	{
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return format.format(date).subSequence(0, 10).toString();
		}
		else {
			return null;
		}
	}
}
