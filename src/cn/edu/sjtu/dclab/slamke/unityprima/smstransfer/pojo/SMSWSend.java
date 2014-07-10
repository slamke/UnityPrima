package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * ���Ŵ�����,����:tb_WSend
 * ���д����͵Ķ��ż�¼�������ڶ��Ŵ������У����ŷ��ͽ��̽���ѯ���ű�
 * ���ŵ�״̬��Ϊ'Submit'ʱӦ���Ѷ��Ű��˵�tb_Sent��,���ڸñ���ɾ��
 * ���ŷ��ͺ�,Ӧ��ɾ�����ֻ��еļ�¼, ����ս�ù����ֻ��洢��Ӱ���ֻ�����
 * 
 * ���ŷ��ͺ�����״̬���淵�أ����ճɹ���ʧ�ܣ�����ϵͳ���ö��ż�¼��tb_Wsend������tb_Sent���У�
 * ����������ʱ��24Сʱ��״̬������δ���أ������ģ�����Ҳ�Ὣ�ö��ż�¼��tb_Wsend������tb_Sent��
 * ������еĶ��ż�¼���ն���������tb_Sent��
 * 
 * @author sunke
 *
 */
public class SMSWSend implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1123L;

	public static final String TABLE_NAME = "tb_WSend";
	
	/**
	 * Id	Id	bigint	����,��ʶ
	 */
	private long id;
	/**
	 * Mbno	���պ���	Nvarchar(20)	�ǿ�
	 */
	private String mbno;
	/**
	 * SendSN	���ͺ���	Nvarchar(20)	�ǿ�
	 */
	private String sendsn;
	/**
	 * SMS	��������	Nvarchar(max)	�ǿ�
	 */
	private String sms; 
	/**
	 * Wtime	����д��ʱ��	Datetime	�ǿ�
	 */
	private Timestamp wtime;
	/**
	 * SubmitTime	����ʱ��	Datetime	
	 */
	private Date submitTime;
	/**
	 * PRI	�������ȼ�	Tinyint	ֵԽ�󼶱�Խ��,���ȷ���
	 */
	private short pri;
	/**
	 * PsendTime	Ԥ����ʱ��	Datetime	
	 */
	private Date psendTime;
	/**
	 * PlastSendTime	�����ʱ��	Datetime	���ŵ���Чʱ��,������ʱ�䲻����
	 * �������С�Ԥ����ʱ�䡱�͡������ʱ�䡱���ҵ�ǰʱ����ڡ������ʱ�䡱�����۶��ŵ�״̬�Ƿ�Ϊ0�����Ű���ģ��Ӧ���Ѹö��Ű��˵��ñ���
	 */
	private Date plastSendTime;
	/**
	 * Status	״̬	Nvarchar(30)	��ʼֵӦΪ'0'�����������ͣ�������Ϻ���ֶλ����Ϊ'Submit'
	 * //�������ŵ�״̬Ϊ0������Ű���ģ�鲻�ܰѸö��Ű��˵��ñ��У�
	 */
	private String status;
	/**
	 * Remark	��ע	Nvarchar(max)	
	 */
	private String remark;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMbno() {
		return mbno;
	}
	public void setMbno(String mbno) {
		this.mbno = mbno;
	}
	public String getSendsn() {
		return sendsn;
	}
	public void setSendsn(String sendsn) {
		this.sendsn = sendsn;
	}
	public String getSms() {
		return sms;
	}
	public void setSms(String sms) {
		this.sms = sms;
	}
	
	public Timestamp getWtime() {
		return wtime;
	}
	public void setWtime(Timestamp wtime) {
		this.wtime = wtime;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public short getPri() {
		return pri;
	}
	public void setPri(short pri) {
		this.pri = pri;
	}
	public Date getPsendTime() {
		return psendTime;
	}
	public void setPsendTime(Date psendTime) {
		this.psendTime = psendTime;
	}
	public Date getPlastSendTime() {
		return plastSendTime;
	}
	public void setPlastSendTime(Date plastSendTime) {
		this.plastSendTime = plastSendTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
