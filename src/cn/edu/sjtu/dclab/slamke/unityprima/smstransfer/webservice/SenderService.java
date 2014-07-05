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
	 * ���ʹ������еĶ���
	 * ���ݶ���status==0���ҵ�ǰʱ�����Ԥ����ʱ�䣬����С�������ʱ��
	 * �������ȼ�����ֵԽ�󼶱�Խ��,���ȷ��ͣ�
	 * @return �����Ͷ����б�
	 */
	@POST
	@Path("/list")
	@Produces("application/json")
	public String getSMSSendList(@FormParam("key") String key){
		Log.debug("��ȡ���ŷ����б�ʱ�䣺"+Calendar.getInstance().toString());
		String code = Calendar.YEAR+"-"+Calendar.MONTH+"-"+Calendar.DAY_OF_MONTH+"unityprima";
		if (key!= null && key.equals(new MD5().getMD5Str(code))) {
			List<SMSWSend> smswSends = taskDao.getSMSWSendList();
			if(smswSends == null || smswSends.size() == 0){
				Log.debug("��ȡ���ŷ����б�ɹ����޴����Ͷ���");
				return Message.SUCCESS;
			}else {
				StringBuffer sBuffer = new StringBuffer();
				for (int i = 0; i < smswSends.size(); i++) {
					sBuffer.append("id:"+smswSends.get(i).getId()+" ");
				}
				Log.debug("��ȡ���ŷ����б�ɹ��������Ͷ�������Ϊ��"+smswSends.size()+" ��־��"+sBuffer.toString());
				return new ClassParse().object2String(smswSends);
			}
		}else {
			Log.debug("��ȡ���ŷ����б�ʧ�ܣ���֤�����");
			return Message.ERROR;
		}
	}

}
