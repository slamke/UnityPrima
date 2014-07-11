package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao.ISMSSentDao;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSSent;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util.Utils;

public class SMSSentDaoImpl implements ISMSSentDao {
	private DBAccess dbAccess;

	public SMSSentDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
		dbAccess = new DBAccess(Utils.getDataBaseIP(), Utils.getDataBasePort(),
				Utils.getDataBaseUser(), Utils.getDataBasePassword(),
				Utils.getSMSDataBase());
	}
	
	@Override
	public boolean insertSMSSent(SMSSent sent) {
		// TODO Auto-generated method stub
		if (sent == null) {
			return false;
		}
		PreparedStatement ps = null;
		Connection con = dbAccess.getConnection();
		if (con != null) {
			String sql = "insert into "
					+ SMSSent.TABLE_NAME
					+ " (WsendId,SendSN,Mbno,SMS,Wtime,SubmitTime,PRI,PsendTime,PlastSendTime,"
					+"[Status],SubmitRespTime,ReceivedTime,SubmitStatus,ReceivedStatus)"
					+" values (?, ?, ?, ?,  ?, ?, ?, ?,  ?, ?, ?, ?,  ?, ?) ";
			System.out.println("insertSMSSent sql:"+sql);
			try {
				ps = con.prepareStatement(sql);
				ps.setLong(1, sent.getWsendId());
				ps.setString(2, sent.getSendSn());
				ps.setString(3, sent.getMbno());
				ps.setString(4, sent.getSms());
				
				if (sent.getWtime()!= null) {
					ps.setTimestamp(5, sent.getWtime());	
				}else {
					ps.setTimestamp(5, null);
				}
				if (sent.getSubmitTime() != null) {
					ps.setTimestamp(6, sent.getSubmitTime());
				}else {
					ps.setTimestamp(6, null);
				}
				
				ps.setShort(7, sent.getPri());
				
				if (sent.getPsendTime() != null) {
					ps.setTimestamp(8, sent.getPsendTime());
				}else {
					ps.setTimestamp(8, null);
				}
				
				
				if (sent.getPlastSendTime() != null) {
					ps.setTimestamp(9, sent.getPlastSendTime());
				}else {
					ps.setTimestamp(9, null);
				}
				
				ps.setString(10, sent.getStatus());
				
				if (sent.getSubmitRespTime() != null) {
					ps.setTimestamp(11, sent.getSubmitRespTime());
				}else {
					ps.setTimestamp(11, null);
				}
				
				if (sent.getReveivedTime() != null) {
					ps.setTimestamp(12, sent.getReveivedTime());
				}else {
					ps.setTimestamp(12, null);
				}
				
				
				ps.setString(13, sent.getSubmitStatus());
				ps.setString(14, sent.getReveivedStatus());
				
				int i = ps.executeUpdate();
				System.out.println("i=" + i);
				boolean res = false;
				if (i == 1) {
					res = true;
				}
				DBAccess.close(ps);
				DBAccess.close(con);
				return res;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return false;
			}
		}

		return false;
	}

}
