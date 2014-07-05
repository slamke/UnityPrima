package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao;

import java.util.List;

import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSWSend;

public interface ISMSWSendDao {
	/**
	 * 获取短信发送列表
	 * 条件：状态为0，无预发送时间和最后发送时间 
	 * 或者当期时间超过预计发送时间并且当前时间早于最晚发送时间
	 * @return
	 */
	public List<SMSWSend> getSMSWSendList();
	
	/**
	 * 获取短信失效列表
	 * 条件：若超过发送时间24小时后状态报告仍未返回，则搬运模块程序也会将该短信记录从tb_Wsend表移至tb_Sent表
	 * 若短信有“预发送时间”和“最后发送时间”，且当前时间大于“最后发送时间”，无论短信的状态是否为0，短信搬运模块应当把该短信搬运到该表中
	 * @return
	 */
	public List<SMSWSend> geSmswSendDisableList();
	/**
	 * 短信成功发送，或者超时24小时后，删除短信
	 * @param id
	 * @return
	 */
	public boolean deleteSMSWSend(long id);
	
	/**
	 * 发送后，更新待发送短信的发送状态：status 时间
	 * @param send
	 */
	public boolean updateSMSWSendSubmit(SMSWSend send);
	/**
	 * 发送后，更新待发送短信的接收状态：status 时间
	 * @param send
	 */
	public boolean updateSMSWSendReceive(SMSWSend send);
}
