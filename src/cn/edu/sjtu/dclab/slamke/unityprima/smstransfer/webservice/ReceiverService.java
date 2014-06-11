package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.webservice;

import javax.ws.rs.FormParam;

public class ReceiverService {
	/**
	 * 把接收的短信放到短信接收表
	 * 在短信保存到该表,后应当删除手机中的记录,以免战用过多手机存储而影响手机运行
	 * @return 成功插入的短信id list
	 */
	public String uploadSMSMO(@FormParam("key") String key,@FormParam("content") String content){
		return null;
	}
}
