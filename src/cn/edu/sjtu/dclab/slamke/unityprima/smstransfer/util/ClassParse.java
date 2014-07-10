package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.util;

import java.lang.reflect.Type;
import java.util.List;

import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSMO;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSSent;
import cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.pojo.SMSWSend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ClassParse {

	private Gson gson;

	public ClassParse() {
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}

	public String object2String(Object object){
		try {
			if (object != null) {
				String result = gson.toJson(object);
				return result;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public String smsList2String(List<SMSWSend> smsList) {
		try {
			if (smsList != null) {
				String result = gson.toJson(smsList);
				return result;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public Object string2Class(String content,Type type) {
		try {
			if (content != null) {
				Object record = gson.fromJson(content, type);
				return record;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public SMSMO string2SMSMO(String content) {
		try {
			Type type = new TypeToken<SMSMO>() {
			}.getType();
			if (content != null) {
				SMSMO record = gson.fromJson(content, type);
				return record;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Long> string2LongList(String content) {
		try {
			Type type = new TypeToken<List<Long>>() {
			}.getType();
			if (content != null) {
				List<Long> record = gson.fromJson(content, type);
				return record;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public List<SMSSent> string2SMSSentList(String content) {
		try {
			Type type = new TypeToken<List<SMSSent>>() {
			}.getType();
			if (content != null) {
				List<SMSSent> record = gson.fromJson(content, type);
				return record;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
}
