package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.webservice;

public class TransferService {
/**
 * 短信发送后，若有状态报告返回（接收成功或失败），则系统将该短信记录从tb_Wsend表移至tb_Sent表中；
 * 若超过发送时间24小时后状态报告仍未返回，则搬运模块程序也会将该短信记录从tb_Wsend表移至tb_Sent表，因此所有的短信记录最终都将保存至tb_Sent表
 * 
 * 若短信有“预发送时间”和“最后发送时间”，且当前时间大于“最后发送时间”，无论短信的状态是否为0，短信搬运模块应当把该短信搬运到该表中
 * @param key
 * @return
 */
public String transferSMS(String key){
	return null;
}
}
