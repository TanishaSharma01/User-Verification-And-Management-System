package com.nagarro.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
 
//USer Entity
@Entity
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id") //user id
    private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="age")
    private int age;
	
	@Column(name="gender")
    private String gender;
	
	@Column(name="dob")
    private String dob;
	
	@Column(name="nationality")
    private String nationality;
    
    @Column(name="verificationStatus")
    private String verificationStatus;
    
	@CreationTimestamp
	@Column(nullable=false, updatable=false)
    private LocalDateTime dateCreated;
    
    @UpdateTimestamp
    private LocalDateTime dateModified;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDateTime getDateModified() {
		return dateModified;
	}

	public void setDateModified(LocalDateTime dateModified) {
		this.dateModified = dateModified;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", dob=" + dob
				+ ", nationality=" + nationality + ", verificationStatus=" + verificationStatus + ", dateCreated="
				+ dateCreated + ", dateModified=" + dateModified + "]";
	}
	
}
