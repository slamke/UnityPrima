package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.dao.ISMSMODao;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSMO;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util.Utils;

public class SMSMODaoImpl implements ISMSMODao {

	private DBAccess dbAccess;

	public SMSMODaoImpl() {
		super();
		// TODO Auto-generated constructor stub
		dbAccess = new DBAccess(Utils.getDataBaseIP(), Utils.getDataBasePort(),
				Utils.getDataBaseUser(), Utils.getDataBasePassword(),
				Utils.getSMSDataBase());
	}
	
	@Override
	public boolean insertSMSMO(SMSMO mo) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection con = dbAccess.getConnection();
		Statement statement = dbAccess.getStatement(con);
		if (con != null) {
			String sql = "insert into "
					+ SMSMO.TABLE_NAME
					+ " (SMS,Times,Mbno,SendSN) values (?, ?, ?, ?) ";
			System.out.println("insertSMSMO sql:" + sql);
			try {
				ps = con.prepareStatement(sql);
				if (mo == null) {
					return false;
				}
				ps.setString(1, mo.getSms());
				if (mo.getTimes() != null) {
					ps.setDate(2, new Date(mo.getTimes().getTime()));
				}else {
					ps.setDate(2, null);
				}
				ps.setString(3, mo.getMbno());
				ps.setString(4, mo.getSendSN());
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

	@Override
	public boolean insertSMSMOList(List<SMSMO> moList) {
		// TODO Auto-generated method stub
		if (moList == null) {
			return false;
		}
		boolean result = true;
		for (int i = 0; i < moList.size(); i++) {
			result = insertSMSMO(moList.get(i));
			if (!result) {
				break;
			}
		}
		return result;
	}
}