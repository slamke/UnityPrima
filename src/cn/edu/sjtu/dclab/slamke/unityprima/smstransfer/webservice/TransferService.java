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
	 * ���ŷ��ͺ�����״̬���淵�أ����ճɹ���ʧ�ܣ�����ϵͳ���ö��ż�¼��tb_Wsend������tb_Sent���У�
	 * ����������ʱ��24Сʱ��״̬������δ���أ������ģ�����Ҳ�Ὣ�ö��ż�¼��tb_Wsend������tb_Sent��������еĶ��ż�¼���ն���������tb_Sent��
	 * �������С�Ԥ����ʱ�䡱�͡������ʱ�䡱���ҵ�ǰʱ����ڡ������ʱ�䡱�����۶��ŵ�״̬�Ƿ�Ϊ0�����Ű���ģ��Ӧ���Ѹö��Ű��˵��ñ���
	 * @param key
	 * @return
	 */
	@POST
	@Path("/notify")
	@Produces("application/json")
	public String transferSMS(@FormParam("key")String key){
		Log.debug("��ʾ���������ж��Ű��ˣ�ʱ�䣺"+Calendar.getInstance().getTime().toString());
		String code = Calendar.YEAR+"-"+Calendar.MONTH+"-"+Calendar.DAY_OF_MONTH+"unityprima";
		if (key!= null && key.equals(new MD5().getMD5Str(code))) {
			System.out.println("���˶���");
			List<SMSWSend> smswSends = sendDao.geSmswSendDisableList();
			if(smswSends == null || smswSends.size() == 0){
				Log.debug("��ʾ���������ж��Ű��˳ɹ����޴����˶���");
				return Message.SUCCESS;
			}else {
				for (int i = 0; i < smswSends.size(); i++) {
					SMSWSend send = smswSends.get(i);
					SMSSent sent = new SMSSent(send);
					boolean result = sentDao.insertSMSSent(sent);
					if (result) {
						sendDao.deleteSMSWSend(send.getId());
					}
					Log.debug("�ɹ����˶��ţ�send ID��־��"+send.getId());
				}
				Log.debug("���Ű��˳ɹ������Ű�������Ϊ��"+smswSends.size());
				return Message.SUCCESS;
			}
		}else {
			Log.debug("��ʾ���������ж��Ű���ʧ�ܣ���֤�����");
			return Message.ERROR;
		}
	}
}
