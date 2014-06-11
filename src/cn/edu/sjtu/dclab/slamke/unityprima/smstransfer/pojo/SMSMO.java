package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo;

import java.util.Date;

/**
 * ���Ž��ձ�,����tb_MO
 * ����˵�����ֻ��յ����ź󣬽�������tb_MO��
 * �ڶ��ű��浽�ñ�,��Ӧ��ɾ���ֻ��еļ�¼,����ս�ù����ֻ��洢��Ӱ���ֻ�����
 * @author sunke
 *
 */
public class SMSMO {
	public static final String TABLE_NAME = "tb_MO";
	/**
	 * Id	Id	bigint	����,��ʶ
	 */
	private long id;
	/**
	 * SMS	��������	Nvarchar(max)	
	 */
	private String sms;
	/**
	 * Times	����ʱ��	Datetime	
	 */
	private Date times;
	/**
	 * Mbno	�ظ��ֻ���	Nvarchar(30)	  �����ŵ��ֻ���
	 */
	private String mbno;
	/**
	 * SendSN	�ظ����˺�	Nvarchar(30)	��ǰ�ֻ�(�����ֻ�)�ĺ���
	 */
	private String sendSN;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSms() {
		return sms;
	}
	public void setSms(String sms) {
		this.sms = sms;
	}
	public Date getTimes() {
		return times;
	}
	public void setTimes(Date times) {
		this.times = times;
	}
	public String getMbno() {
		return mbno;
	}
	public void setMbno(String mbno) {
		this.mbno = mbno;
	}
	public String getSendSN() {
		return sendSN;
	}
	public void setSendSN(String sendSN) {
		this.sendSN = sendSN;
	}
}
