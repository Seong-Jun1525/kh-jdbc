package com.kh.model.vo;

import java.sql.Date;

/** VO (Value Object)
 * - 저장용 객체
 * - 한 명의 회원에 대한 데이터를 저장하기 위한 객체
 * 		=> DB 테이블에서 한 행의 데이터를 의미
 * 
 */

public class Member {
	// 필드부 -> Member 테이블의 컬럼 정보와 매칭시켜 정의
	
	/*
		MEMBERNO	NUMBER
		MEMBERID	VARCHAR2(20 BYTE)
		MEMBERPW	VARCHAR2(20 BYTE)
		GENDER	CHAR(1 BYTE)
		AGE	NUMBER
		EMAIL	VARCHAR2(30 BYTE)
		ADDRESS	VARCHAR2(100 BYTE)
		PHONE	VARCHAR2(13 BYTE)
		HOBBY	VARCHAR2(50 BYTE)
		ENROLLDATE	DATE
	*/
	private int memberNo;
	private String memberId;
	private String memberPw;
	private char gender;
	private int age;
	private String email;
	private String address;
	private String phone;
	private String hobby;
	private Date enrollDate;

	public Member() {}
	
	public Member(String id, String pw, char gender, int age) {
		super();
		this.memberId = id;
		this.memberPw = pw;
		this.gender = gender;
		this.age = age;
	}
	
	public Member(String id, String pw, char gender, String email, String phone, String addr, String hobby) {
		super();
		this.memberId = id;
		this.memberPw = pw;
		this.gender = gender;
		this.email = email;
		this.address = addr;
		this.phone = phone;
		this.hobby = hobby;
	}

	public Member(int memberNo, String memberId, String memberPw, char gender, int age, String email, String address,
			String phone, String hobby, Date enrollDate) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.hobby = hobby;
		this.enrollDate = enrollDate;
	}

	public Member(String id) {
		this.memberId = id;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", memberId=" + memberId + ", memberPw=" + memberPw + ", gender="
				+ gender + ", age=" + age + ", email=" + email + ", address=" + address + ", phone=" + phone
				+ ", hobby=" + hobby + ", enrollDate=" + enrollDate + "]";
	}
	
}
