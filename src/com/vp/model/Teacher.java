package com.vp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Teacher implements Serializable{

	private int id;
	private String code;
	private String name;
	private String gender;
	private String marriage;
	private String address;
	private String phone;
	private int teachingage;
	private String nativeplace;
	private String nation;
	private String politics;
	private String title;
	private String idcard;
	private Date birthday;
	private String status;
	private Date workdate;
	private Date quitdate;
	private String whereabouts;
	private String quitreason;
	private Date retiredate;
	private String rank;
	private Date rankDate;
	private Date titleDate;
	// 在多方定义一个一方的引用
	private Department department;
	private Set<Education> educations;
	private Set<Attendance> attendances;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getTeachingage() {
		return teachingage;
	}
	public void setTeachingage(int teachingage) {
		this.teachingage = teachingage;
	}
	public String getNativeplace() {
		return nativeplace;
	}
	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getPolitics() {
		return politics;
	}
	public void setPolitics(String politics) {
		this.politics = politics;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getWorkdate() {
		return workdate;
	}
	public void setWorkdate(Date workdate) {
		this.workdate = workdate;
	}
	public Date getQuitdate() {
		return quitdate;
	}
	public void setQuitdate(Date quitdate) {
		this.quitdate = quitdate;
	}
	public String getWhereabouts() {
		return whereabouts;
	}
	public void setWhereabouts(String whereabouts) {
		this.whereabouts = whereabouts;
	}
	public String getQuitreason() {
		return quitreason;
	}
	public void setQuitreason(String quitreason) {
		this.quitreason = quitreason;
	}
	public Date getRetiredate() {
		return retiredate;
	}
	public void setRetiredate(Date retiredate) {
		this.retiredate = retiredate;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public Date getRankDate() {
		return rankDate;
	}
	public void setRankDate(Date rankDate) {
		this.rankDate = rankDate;
	}
	public Date getTitleDate() {
		return titleDate;
	}
	public void setTitleDate(Date titleDate) {
		this.titleDate = titleDate;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Set<Education> getEducations() {
		return educations;
	}
	public void setEducations(Set<Education> educations) {
		this.educations = educations;
	}
	public Set<Attendance> getAttendances() {
		return attendances;
	}
	public void setAttendances(Set<Attendance> attendances) {
		this.attendances = attendances;
	}
	

	
}
