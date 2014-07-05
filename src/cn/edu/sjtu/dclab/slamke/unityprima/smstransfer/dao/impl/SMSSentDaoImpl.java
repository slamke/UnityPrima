package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;

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
		PreparedStatement ps = null;
		Connection con = dbAccess.getConnection();
		Statement statement = dbAccess.getStatement(con);
		if (con != null) {
			String sql = "insert into "
					+ SMSSent.TABLE_NAME
					+ " (WsendId,SendSN,Mbno,SMS,Wtime,SubmitTime,PRI,PsendTime,PlastSendTime,"
					+"[Status],SubmitRespTime,ReceivedTime,SubmitStatus,ReceivedStatus)"
					+" values (?, ?, ?, ?,  ?, ?, ?, ?,  ?, ?, ?, ?,  ?, ?,) ";
			System.out.println("insertSMSSent sql:"+sql);
			try {
				ps = con.prepareStatement(sql);
				if (sent == null) {
					return false;
				}
				ps.setLong(1, sent.getWsendId());
				ps.setString(2, sent.getSendSn());
				ps.setString(3, sent.getMbno());
				ps.setString(4, sent.getSms());
				
				if (sent.getWtime()!= null) {
					ps.setDate(5, new Date(sent.getWtime().getTime()));	
				}else {
					ps.setDate(5, null);
				}
				if (sent.getSubmitTime() != null) {
					ps.setDate(6, new Date(sent.getSubmitTime().getTime()));
				}else {
					ps.setDate(6, null);
				}
				
				ps.setShort(7, sent.getPri());
				
				if (sent.getPsendTime() != null) {
					ps.setDate(8, new Date(sent.getPsendTime().getTime()));
				}else {
					ps.setDate(8, null);
				}
				
				
				if (sent.getPlastSendTime() != null) {
					ps.setDate(9, new Date(sent.getPlastSendTime().getTime()));
				}else {
					ps.setDate(9, null);
				}
				
				ps.setString(10, sent.getStatus());
				
				if (sent.getSubmitRespTime() != null) {
					ps.setDate(11, new Date(sent.getSubmitRespTime().getTime()));
				}else {
					ps.setDate(11, null);
				}
				
				if (sent.getReveivedTime() != null) {
					ps.setDate(12, new Date(sent.getReveivedTime().getTime()));
				}else {
					ps.setDate(12, null);
				}
				
				
				ps.setString(13, sent.getSubmitStatus());
				ps.setString(14, sent.getReveivedStatus());
				
				int i = ps.executeUpdate();
				System.out.println("i=" + i);
				boolean res = false;
				if (i == 1) {
					res = true;
				}
				DBAccess.close(statement);
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
