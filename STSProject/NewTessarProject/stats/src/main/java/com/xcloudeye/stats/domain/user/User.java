package com.xcloudeye.stats.domain.user;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long uid;
	private String username;
	private String password;
	private String email;
	private String skype;
	private Integer addtime;
	private Integer changetime;
	private String group;
	private Integer status;
	
	
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/
	public User() {
	}
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param uid
	* @param username
	* @param password
	* @param email
	* @param skype
	* @param addtime
	* @param group
	* @param status
	*/
	public User(long uid, String username, String password, String email,
			String skype, Integer addtime, Integer changetime, String group, Integer status) {
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.email = email;
		this.skype = skype;
		this.addtime = addtime;
		this.changetime = changetime;
		this.group = group;
		this.status = status;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSkype() {
		return skype;
	}
	public void setSkype(String skype) {
		this.skype = skype;
	}
	public Integer getAddtime() {
		return addtime;
	}
	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getChangetime() {
		return changetime;
	}
	public void setChangetime(Integer changetime) {
		this.changetime = changetime;
	}
	
}
