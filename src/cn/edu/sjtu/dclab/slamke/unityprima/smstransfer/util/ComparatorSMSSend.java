package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util;

import java.util.Comparator;

import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSWSend;

public class ComparatorSMSSend implements Comparator<SMSWSend> {

	@Override
	public int compare(SMSWSend o1, SMSWSend o2) {
		// TODO Auto-generated method stub
		int flag = 0;
		if (o1.getPri() > o2.getPri()) {
			flag = 1;
		}else if(o1.getPri() < o2.getPri()){
			flag = -1;
		} 
		if (flag == 0) {
			return o2.getPsendTime().compareTo(o1.getPlastSendTime());
		}
		return flag;
	}

}
