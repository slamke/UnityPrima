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
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util.MD5;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util.Message;

@Path("/transfer")
public class TransferService {
	
	private static Logger Log = Logger.getLogger(TransferService.class);
	private ISMSWSendDao sendDao;
	private ISMSSentDao sentDao;
	
	public TransferService() {
		sendDao = new SMSWSendDaoImpl();
		sentDao = new SMSSentDaoImpl();
	}
	/**
	 * 短信发送后，若有状态报告返回（接收成功或失败），则系统将该短信记录从tb_Wsend表移至tb_Sent表中；
	 * 若超过发送时间24小时后状态报告仍未返回，则搬运模块程序也会将该短信记录从tb_Wsend表移至tb_Sent表，因此所有的短信记录最终都将保存至tb_Sent表
	 * 若短信有“预发送时间”和“最后发送时间”，且当前时间大于“最后发送时间”，无论短信的状态是否为0，短信搬运模块应当把该短信搬运到该表中
	 * @param key
	 * @return
	 */
	@POST
	@Path("/notify")
	@Produces("application/json")
	public String transferSMS(@FormParam("key")String key){
		Log.debug("提示服务器进行短信搬运，时间："+Calendar.getInstance().getTime().toString());
		String code = Calendar.YEAR+"-"+Calendar.MONTH+"-"+Calendar.DAY_OF_MONTH+"unityprima";
		if (key!= null && key.equals(new MD5().getMD5Str(code))) {
			System.out.println("搬运短信");
			List<SMSWSend> smswSends = sendDao.geSmswSendDisableList();
			if(smswSends == null || smswSends.size() == 0){
				Log.debug("提示服务器进行短信搬运成功：无待搬运短信");
				return Message.SUCCESS;
			}else {
				for (int i = 0; i < smswSends.size(); i++) {
					SMSWSend send = smswSends.get(i);
					SMSSent sent = new SMSSent(send);
					boolean result = sentDao.insertSMSSent(sent);
					if (result) {
						sendDao.deleteSMSWSend(send.getId());
					}
					Log.debug("成功搬运短信，send ID标志："+send.getId());
				}
				Log.debug("短信搬运成功，短信搬运条数为："+smswSends.size());
				return Message.SUCCESS;
			}
		}else {
			Log.debug("提示服务器进行短信搬运失败：验证码错误");
			return Message.ERROR;
		}
	}
}
