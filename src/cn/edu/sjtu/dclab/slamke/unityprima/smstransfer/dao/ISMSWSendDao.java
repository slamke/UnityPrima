package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao;

import java.util.List;

import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSWSend;

public interface ISMSWSendDao {
	/**
	 * ��ȡ���ŷ����б�
	 * ������״̬Ϊ0����Ԥ����ʱ��������ʱ�� 
	 * ���ߵ���ʱ�䳬��Ԥ�Ʒ���ʱ�䲢�ҵ�ǰʱ������������ʱ��
	 * @return
	 */
	public List<SMSWSend> getSMSWSendList();
	
	/**
	 * ��ȡ����ʧЧ�б�
	 * ����������������ʱ��24Сʱ��״̬������δ���أ������ģ�����Ҳ�Ὣ�ö��ż�¼��tb_Wsend������tb_Sent��
	 * �������С�Ԥ����ʱ�䡱�͡������ʱ�䡱���ҵ�ǰʱ����ڡ������ʱ�䡱�����۶��ŵ�״̬�Ƿ�Ϊ0�����Ű���ģ��Ӧ���Ѹö��Ű��˵��ñ���
	 * @return
	 */
	public List<SMSWSend> geSmswSendDisableList();
	/**
	 * ���ųɹ����ͣ����߳�ʱ24Сʱ��ɾ������
	 * @param id
	 * @return
	 */
	public boolean deleteSMSWSend(long id);
	
	/**
	 * ���ͺ󣬸��´����Ͷ��ŵķ���״̬��status ʱ��
	 * @param send
	 */
	public boolean updateSMSWSendSubmit(SMSWSend send);
	/**
	 * ���ͺ󣬸��´����Ͷ��ŵĽ���״̬��status ʱ��
	 * @param send
	 */
	public boolean updateSMSWSendReceive(SMSWSend send);
}
