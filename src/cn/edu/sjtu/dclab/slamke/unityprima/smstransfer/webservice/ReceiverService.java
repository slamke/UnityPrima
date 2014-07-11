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
	 * �ѽ��յĶ��ŷŵ����Ž��ձ�
	 * �ڶ��ű��浽�ñ�,��Ӧ��ɾ���ֻ��еļ�¼,����ս�ù����ֻ��洢��Ӱ���ֻ�����
	 * @return �ɹ�����Ķ���id list
	 */
	@POST
	@Path("/items")
	@Produces("application/json")
	public String uploadSMSMO(@FormParam("key") String key,@FormParam("content") String content){
		Log.debug("���ն��ţ�ʱ�䣺"+Calendar.getInstance().getTime().toString());
		String code = Calendar.YEAR+"-"+Calendar.MONTH+"-"+Calendar.DAY_OF_MONTH+"unityprima";
		if (key!= null && key.equals(new MD5().getMD5Str(code))) {
			System.out.println("���ն���");
			ClassParse parse = new ClassParse();
			Log.debug("���ն��ţ����ݣ�"+content);
			Type type = new TypeToken<ArrayList<SMSMO>>(){}.getType();
			@SuppressWarnings("unchecked")
			ArrayList<SMSMO> moList = (ArrayList<SMSMO>)parse.string2Class(content, type);
			boolean result = moDao.insertSMSMOList(moList);
			if(result){
				Log.debug("���ն��ųɹ�������������"+moList.size());
				return Message.SUCCESS;
			}else {
				Log.debug("���ն���ʧ�ܣ�ϵͳ�ڲ�����");
				return Message.ERROR;
			}
		}else {
			Log.debug("���ն���ʧ�ܣ���֤�����");
			return Message.ERROR;
		}
	}
}
