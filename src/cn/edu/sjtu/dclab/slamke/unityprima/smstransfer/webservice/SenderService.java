package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.webservice;


public class SenderService {
/**
 * 发送待发表中的短信
 * 根据短信status==0，且当前时间大于预发送时间，并且小于最后发送时间
 * 根据优先级排序（值越大级别越高,优先发送）
 * @return 待发送短信列表
 */
public String getSMSSendList(String key){
	return null;
}

}
