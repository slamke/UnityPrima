package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao;

import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSSent;

public interface ISMSSentDao {
	/**
	 * ���Ż�ִ��ȷ����������ת�ơ�
	 * @param sent
	 * @return
	 */
	public boolean insertSMSSent(SMSSent sent);
}
