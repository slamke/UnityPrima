package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao;

import java.util.List;

import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSMO;

public interface ISMSMODao {
	
	public boolean insertSMSMO(SMSMO mo);
	public boolean insertSMSMOList(List<SMSMO> moList);
}
