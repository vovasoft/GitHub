package com.xcloudeye.stats.domain.db;

public class LoginPlatform {

	private String uid;
	private String username;
	private String gid;
	private String sid;
	private Integer addtime;
	private String fbid;
	private String appname;
	private String ip;
	private Integer date;
	private String client;
	private String os;
	private String action;
	private long rcvtime;
	
	
	
	
	/**
	 * 
	 */
	public LoginPlatform() {
	}
	/**
	 * @param uid
	 * @param username
	 * @param gid
	 * @param sid
	 * @param addtime
	 * @param fbid
	 * @param appname
	 * @param ip
	 * @param date
	 * @param client
	 * @param os
	 * @param action
	 * @param rcvtime
	 */
	public LoginPlatform(String uid, String username, String gid, String sid,
			Integer addtime, String fbid, String appname, String ip, Integer date,
			String client, String os, String action, long rcvtime) {
		this.uid = uid;
		this.username = username;
		this.gid = gid;
		this.sid = sid;
		this.addtime = addtime;
		this.fbid = fbid;
		this.appname = appname;
		this.ip = ip;
		this.date = date;
		this.client = client;
		this.os = os;
		this.action = action;
		this.rcvtime = rcvtime;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public Integer getAddtime() {
		return addtime;
	}
	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
	public String getFbid() {
		return fbid;
	}
	public void setFbid(String fbid) {
		this.fbid = fbid;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getDate() {
		return date;
	}
	public void setDate(Integer date) {
		this.date = date;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public long getRcvtime() {
		return rcvtime;
	}
	public void setRcvtime(long rcvtime) {
		this.rcvtime = rcvtime;
	}
	
	
}
