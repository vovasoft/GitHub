package com.xcloud.schedule.appdomain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * retention collection 对应的实体类。
 *
 */
public class RetentionOut {

	private Integer date;
	private Integer cacl;
	private String channel;
	private String parent;
	private String child;
	private String seq;
	private Integer new_user;
	private List<HashMap<String, Integer>> ret = new ArrayList<HashMap<String,Integer>>();
	
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/
	public RetentionOut() {
	}
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param date
	* @param cacl
	* @param channel
	* @param parent
	* @param child
	* @param seq
	* @param new_user
	* @param ret
	*/
	public RetentionOut(Integer date, Integer cacl, String channel,
			String parent, String child, String seq, Integer new_user,
			List<HashMap<String, Integer>> ret) {
		this.date = date;
		this.cacl = cacl;
		this.channel = channel;
		this.parent = parent;
		this.child = child;
		this.seq = seq;
		this.new_user = new_user;
		this.ret = ret;
	}
	public Integer getDate() {
		return date;
	}
	public void setDate(Integer date) {
		this.date = date;
	}
	public Integer getCacl() {
		return cacl;
	}
	public void setCacl(Integer cacl) {
		this.cacl = cacl;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getChild() {
		return child;
	}
	public void setChild(String child) {
		this.child = child;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public Integer getNew_user() {
		return new_user;
	}
	public void setNew_user(Integer new_user) {
		this.new_user = new_user;
	}
	public List<HashMap<String, Integer>> getRet() {
		return ret;
	}
	public void setRet(List<HashMap<String, Integer>> ret) {
		this.ret = ret;
	}
	
}
