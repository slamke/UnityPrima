package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util;

import java.util.Comparator;

import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSWSend;

public class ComparatorSMSSend implements Comparator<SMSWSend> {

	//½µÐò > -1 < 1 == 0
	@Override
	public int compare(SMSWSend o1, SMSWSend o2) {
		// TODO Auto-generated method stub
		int flag = 0;
		if (o1.getPri() > o2.getPri()) {
			flag = -1;
		}else if(o1.getPri() < o2.getPri()){
			flag = 1;
		} 
		if (flag == 0) {
			if (o2.getPsendTime() == null) {
				return -1;
			}else if (o1.getPsendTime() == null) {
				return 1;
			}
			return o1.getPsendTime().compareTo(o2.getPsendTime());
		}
		return flag;
	}

}
