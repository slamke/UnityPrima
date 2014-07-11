package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.webservice;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao.ISMSMODao;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao.impl.SMSMODaoImpl;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSMO;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util.ClassParse;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util.MD5;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util.Message;

import com.google.gson.reflect.TypeToken;
@Path("/receive")
public class ReceiverService {
	private static Logger Log = Logger.getLogger(ReceiverService.class);
	private ISMSMODao moDao;
	
	public ReceiverService() {
		moDao = new SMSMODaoImpl();
	}
	/**
	 * 把接收的短信放到短信接收表
	 * 在短信保存到该表,后应当删除手机中的记录,以免战用过多手机存储而影响手机运行
	 * @return 成功插入的短信id list
	 */
	@POST
	@Path("/items")
	@Produces("application/json")
	public String uploadSMSMO(@FormParam("key") String key,@FormParam("content") String content){
		Log.debug("接收短信，时间："+Calendar.getInstance().getTime().toString());
		String code = Calendar.YEAR+"-"+Calendar.MONTH+"-"+Calendar.DAY_OF_MONTH+"unityprima";
		if (key!= null && key.equals(new MD5().getMD5Str(code))) {
			System.out.println("接收短信");
			ClassParse parse = new ClassParse();
			Log.debug("接收短信，内容："+content);
			Type type = new TypeToken<ArrayList<SMSMO>>(){}.getType();
			@SuppressWarnings("unchecked")
			ArrayList<SMSMO> moList = (ArrayList<SMSMO>)parse.string2Class(content, type);
			boolean result = moDao.insertSMSMOList(moList);
			if(result){
				Log.debug("接收短信成功，共计条数："+moList.size());
				return Message.SUCCESS;
			}else {
				Log.debug("接收短信失败：系统内部错误");
				return Message.ERROR;
			}
		}else {
			Log.debug("接收短信失败：验证码错误");
			return Message.ERROR;
		}
	}
}
