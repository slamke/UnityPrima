package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.webservice;

import java.util.Calendar;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao.ISMSWSendDao;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao.impl.SMSWSendDaoImpl;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSWSend;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util.ClassParse;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util.MD5;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util.Message;

@Path("/send")
public class SenderService {
	
	private static Logger Log = Logger.getLogger(SenderService.class);
	private ISMSWSendDao taskDao;
	
	public SenderService() {
		taskDao = new SMSWSendDaoImpl();
	}
	/**
	 * 发送待发表中的短信
	 * 根据短信status==0，且当前时间大于预发送时间，并且小于最后发送时间
	 * 根据优先级排序（值越大级别越高,优先发送）
	 * @return 待发送短信列表
	 */
	@POST
	@Path("/list")
	@Produces("application/json")
	public String getSMSSendList(@FormParam("key") String key){
		Log.debug("获取短信发送列表，时间："+Calendar.getInstance().toString());
		String code = Calendar.YEAR+"-"+Calendar.MONTH+"-"+Calendar.DAY_OF_MONTH+"unityprima";
		if (key!= null && key.equals(new MD5().getMD5Str(code))) {
			List<SMSWSend> smswSends = taskDao.getSMSWSendList();
			if(smswSends == null || smswSends.size() == 0){
				Log.debug("获取短信发送列表成功：无待发送短信");
				return Message.SUCCESS;
			}else {
				StringBuffer sBuffer = new StringBuffer();
				for (int i = 0; i < smswSends.size(); i++) {
					sBuffer.append("id:"+smswSends.get(i).getId()+" ");
				}
				Log.debug("获取短信发送列表成功，待发送短信条数为："+smswSends.size()+" 标志："+sBuffer.toString());
				return new ClassParse().object2String(smswSends);
			}
		}else {
			Log.debug("获取短信发送列表失败：验证码错误");
			return Message.ERROR;
		}
	}

}
