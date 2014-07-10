package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.webservice;

import java.util.Calendar;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao.ISMSSentDao;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao.ISMSWSendDao;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao.impl.SMSSentDaoImpl;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao.impl.SMSWSendDaoImpl;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSSent;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSWSend;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util.ClassParse;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util.MD5;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util.Message;

@Path("/send")
public class SenderService {
	
	private static Logger Log = Logger.getLogger(SenderService.class);
	private ISMSWSendDao taskDao;
	private ISMSSentDao sentDao;
	
	public SenderService() {
		taskDao = new SMSWSendDaoImpl();
		sentDao = new SMSSentDaoImpl();
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
		Log.debug("获取短信发送列表，时间："+Calendar.getInstance().getTime().toString());
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
	
	/**
	 * 接收android客户端成功发送短信后的回执，回执参数为对应的sent list
	 * 收到回执后，立刻转移短信
	 * @return 执行状态信息
	 */
	@POST
	@Path("/feedback")
	@Produces("application/json")
	public String getFeedBackFromTerminal(@FormParam("key") String key,@FormParam("content") String content){
		Log.debug("接收android客户端成功发送短信后的回执，时间："+Calendar.getInstance().getTime().toString()+" 内容："+content);
		String code = Calendar.YEAR+"-"+Calendar.MONTH+"-"+Calendar.DAY_OF_MONTH+"unityprima";
		if (key!= null && key.equals(new MD5().getMD5Str(code))) {
			ClassParse parse = new ClassParse();
			List<SMSSent> sentList = parse.string2SMSSentList(content);
			StringBuffer sBuffer = new StringBuffer();
			if (sentList != null) {
				for (SMSSent sent : sentList) {
					SMSWSend send = taskDao.getSmswSendById(sent.getWsendId());
					if (send == null) {
						continue;
					}
					//更新发送状态和事件
					send.setSubmitTime(sent.getSubmitTime());
					taskDao.updateSMSWSendSubmit(send);
					//搬运短信
					if(send != null){
						SMSSent save = new SMSSent(send);
						save.setReveivedStatus(sent.getReveivedStatus());
						save.setReveivedTime(sent.getReveivedTime());
						save.setStatus("Submit");
						save.setWsendId(sent.getWsendId());
						save.setSubmitRespTime(sent.getSubmitRespTime());
						save.setSubmitStatus(sent.getSubmitStatus());
						save.setSubmitTime(sent.getSubmitTime());
						boolean res= sentDao.insertSMSSent(save);
						if (res) {
							//删除send中的短信
							taskDao.deleteSMSWSend(sent.getWsendId());
						}
						sBuffer.append("，转移短信ID（Send）："+sent.getWsendId());
					}
				}
				Log.debug("接收android客户端成功发送短信后的回执，转移短信条数为："+sentList.size()+" 标志："+sBuffer.toString());
			}else {
				Log.debug("接收android客户端成功发送短信后的回执：解析数据失败");
			}
			return Message.SUCCESS;
		}else {
			Log.debug("接收android客户端成功发送短信后的回执：验证码错误");
			return Message.ERROR;
		}
	}

}
