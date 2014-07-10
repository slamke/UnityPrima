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
	 * ���ʹ������еĶ���
	 * ���ݶ���status==0���ҵ�ǰʱ�����Ԥ����ʱ�䣬����С�������ʱ��
	 * �������ȼ�����ֵԽ�󼶱�Խ��,���ȷ��ͣ�
	 * @return �����Ͷ����б�
	 */
	@POST
	@Path("/list")
	@Produces("application/json")
	public String getSMSSendList(@FormParam("key") String key){
		Log.debug("��ȡ���ŷ����б�ʱ�䣺"+Calendar.getInstance().getTime().toString());
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
	
	/**
	 * ����android�ͻ��˳ɹ����Ͷ��ź�Ļ�ִ����ִ����Ϊ��Ӧ��sent list
	 * �յ���ִ������ת�ƶ���
	 * @return ִ��״̬��Ϣ
	 */
	@POST
	@Path("/feedback")
	@Produces("application/json")
	public String getFeedBackFromTerminal(@FormParam("key") String key,@FormParam("content") String content){
		Log.debug("����android�ͻ��˳ɹ����Ͷ��ź�Ļ�ִ��ʱ�䣺"+Calendar.getInstance().getTime().toString()+" ���ݣ�"+content);
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
					//���·���״̬���¼�
					send.setSubmitTime(sent.getSubmitTime());
					taskDao.updateSMSWSendSubmit(send);
					//���˶���
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
							//ɾ��send�еĶ���
							taskDao.deleteSMSWSend(sent.getWsendId());
						}
						sBuffer.append("��ת�ƶ���ID��Send����"+sent.getWsendId());
					}
				}
				Log.debug("����android�ͻ��˳ɹ����Ͷ��ź�Ļ�ִ��ת�ƶ�������Ϊ��"+sentList.size()+" ��־��"+sBuffer.toString());
			}else {
				Log.debug("����android�ͻ��˳ɹ����Ͷ��ź�Ļ�ִ����������ʧ��");
			}
			return Message.SUCCESS;
		}else {
			Log.debug("����android�ͻ��˳ɹ����Ͷ��ź�Ļ�ִ����֤�����");
			return Message.ERROR;
		}
	}

}
