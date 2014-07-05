package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao.ISMSWSendDao;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSWSend;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util.ComparatorSMSSend;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util.Utils;

public class SMSWSendDaoImpl implements ISMSWSendDao {

	private DBAccess dbAccess;

	public SMSWSendDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
		dbAccess = new DBAccess(Utils.getDataBaseIP(), Utils.getDataBasePort(),
				Utils.getDataBaseUser(), Utils.getDataBasePassword(),
				Utils.getSMSDataBase());
	}
	
	private List<SMSWSend> getSMSSendStatusZero(){
		List<SMSWSend> tasks = new ArrayList<SMSWSend>();
		// TODO Auto-generated method stub
		String sql = "select * from "+ SMSWSend.TABLE_NAME+" where [Status]='0'";
		System.out.println("getSMSWSendList sql:"+sql);
		Connection con = dbAccess.getConnection();  
        Statement statement = dbAccess.getStatement(con); 
        ResultSet rs = dbAccess.getResultSetQuery(statement, sql);  
        try {  
            while(rs.next()){
            	SMSWSend send = new SMSWSend();
            	send.setId(rs.getLong("Id"));
            	send.setMbno(rs.getString("Mbno"));
            	send.setSendsn(rs.getString("SendSN"));
            	send.setSms(rs.getString("SMS"));
            	send.setWtime(rs.getDate("Wtime"));
            	send.setSubmitTime(rs.getDate("SubmitTime"));
            	send.setPri(rs.getShort("PRI"));
            	send.setPsendTime(rs.getDate("PsendTime"));
            	send.setPlastSendTime(rs.getDate("PlastSendTime"));
            	send.setStatus(rs.getString("Status"));
            	send.setRemark(rs.getString("Remark"));
            	tasks.add(send);
            }  
            DBAccess.close(rs);
            DBAccess.close(statement);
            DBAccess.close(con);
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
        return tasks;
	}
	
	@Override
	public List<SMSWSend> getSMSWSendList() {
		// TODO Auto-generated method stub
		List<SMSWSend> tasks = getSMSSendStatusZero();
        for (int i = tasks.size() -1;i >-1 ;i-- ) {
        	SMSWSend smswSend = tasks.get(i);
			/**
			 * 无预发送时间和最后发送时间或者当期时间超过预计发送时间并且当前时间早于最晚发送时间
			 */
        	//该短信已经发送，等待反馈中
        	if (smswSend.getSubmitTime() != null) {
				tasks.remove(i);
				continue;
			}
        	//该短信尚未到发送时间
        	if (smswSend.getPsendTime() != null) {
				if (smswSend.getPsendTime().compareTo(new java.util.Date()) < 0 ) {
					tasks.remove(i);
					continue;
				}
			}
        	//该短信已过发送时刻，等待搬运
        	if (smswSend.getPlastSendTime() != null) {
				if (smswSend.getPlastSendTime().compareTo(new java.util.Date()) < 0 ) {
					tasks.remove(i);
					continue;
				}
			}
		}
        ComparatorSMSSend comparator=new ComparatorSMSSend();
        Collections.sort(tasks, comparator);
        return tasks;
	}

	@Override
	public boolean deleteSMSWSend(long id) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection con = dbAccess.getConnection();
		if (con != null) {
			String sql = "delete from "
					+ SMSWSend.TABLE_NAME
					+ " where id="+id;
			System.out.println("deleteSMSWSend sql:" + sql);
			try {
				ps = con.prepareStatement(sql);
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

	@Override
	public boolean updateSMSWSendSubmit(SMSWSend send) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection con = dbAccess.getConnection();  
//        String sql = "update "+ SMSWSend.TABLE_NAME+
//        		" set SubmitTime=? where Id="+send+ ";";
        String sql = "update "+ SMSWSend.TABLE_NAME+
        		" set SubmitTime=? where Id=?";
		if (con != null) {			
			try {
				ps = con.prepareStatement(sql);
				if (send.getSubmitTime() != null) {
					ps.setDate(1, new Date(send.getSubmitTime().getTime()));
				}else {
					ps.setDate(1, null);
				}
				ps.setLong(2, send.getId());
				int i = ps.executeUpdate();
				DBAccess.close(ps);
	            DBAccess.close(con);
				System.out.println("i=" + i);
				if (i == 1) {
					return true;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean updateSMSWSendReceive(SMSWSend send) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SMSWSend> geSmswSendDisableList() {
		// TODO Auto-generated method stub
		final long ONEDAY =86400000;
		List<SMSWSend> sends = getSMSSendStatusZero();
		List<SMSWSend> disList = new ArrayList<SMSWSend>();
		for (SMSWSend smswSend : sends) {
			//若超过发送时间24小时后状态报告仍未返回，则搬运模块程序也会将该短信记录从tb_Wsend表移至tb_Sent表
			if (smswSend.getSubmitTime() != null) {
				if ((Calendar.getInstance().getTime().getTime() - smswSend.getSubmitTime().getTime()) > ONEDAY) {
					disList.add(smswSend);
				}
			}
			//若短信有“预发送时间”和“最后发送时间”，且当前时间大于“最后发送时间”，无论短信的状态是否为0，短信搬运模块应当把该短信搬运到该表中
			if (smswSend.getPlastSendTime() != null) {
				if (smswSend.getPlastSendTime().getTime() < Calendar.getInstance().getTime().getTime()) {
					disList.add(smswSend);
				}
			}
		}
		return disList;
	}

}
