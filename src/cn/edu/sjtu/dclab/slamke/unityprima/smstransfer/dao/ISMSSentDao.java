package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao;

import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSSent;

public interface ISMSSentDao {
	/**
	 * 短信回执正确后，立即进行转移。
	 * @param sent
	 * @return
	 */
	public boolean insertSMSSent(SMSSent sent);
}
