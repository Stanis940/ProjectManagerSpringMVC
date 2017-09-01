package Exem.Shchuruk.ProjectManager0.entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import Exem.Shchuruk.ProjectManager0.AppHelpLogic;

@Entity
@Table(name = "programmer")
public class Programmer {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "surname", nullable = false)
	private String surname;
	
	@Column(name = "position", nullable = false)
    @Enumerated(EnumType.STRING)
	private Position position;
	
	@Column(name = "salary", nullable = false)
	private int salary;
	
	@Column(name = "workExp", nullable = false)
	private int workExp;
	
	@Column(name = "startWork", nullable = true)
	private Date startWork;
	
	@ManyToOne
	@JoinColumn(name = "company_id", nullable=true) 
	private Company company;
	
	@ManyToOne
	@JoinColumn(name = "project_id", nullable=true) 
	private Project project;
	
	public Programmer(){
		this.position = Position.TRAINER;
	}
	
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	public int getWorkExp() {
		return workExp;
	}

	public void setWorkExp(int workExp) {
		this.workExp = workExp;
	}
	
	public Date getStartWork() {
		return startWork;
	}

	public void setStartWork(Date startWork) {
		this.startWork = startWork;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public Date getWorkTime(){
		 Date date = new Date(new Date().getTime() - this.getStartWork().getTime());
	     return date;
	}
	
	public String getWorkTimeStr(){
		 Date date = new Date(new Date().getTime() - this.getStartWork().getTime());
	     return AppHelpLogic.dateToTimeStr(date);
	}

	public float getProductivity() {
		float productivity = 0;
		if(this.position == Position.TRAINER)   productivity = 0.05f;
		if(this.position == Position.JUNIOR)    productivity = 0.15f;
		if(this.position == Position.MIDDLE)    productivity = 0.3f;
		if(this.position == Position.SENEOR)    productivity = 0.6f;
		if(this.position == Position.TEAM_LEAD) productivity = 1f;
		return productivity;
	}
	
	public int getMinSalary(){
		int minPayment = 0;
		if(this.position == Position.TRAINER)   minPayment = 0;
		if(this.position == Position.JUNIOR)    minPayment = 300;
		if(this.position == Position.MIDDLE)    minPayment = 800;
		if(this.position == Position.SENEOR)    minPayment = 1800;
		if(this.position == Position.TEAM_LEAD) minPayment = 4000;
		return minPayment;
	}
	
	public int getMaxSalary(){
		int maxPayment = 0;
		if(this.position == Position.TRAINER)   maxPayment = 150;
		if(this.position == Position.JUNIOR)    maxPayment = 600;
		if(this.position == Position.MIDDLE)    maxPayment = 1600;
		if(this.position == Position.SENEOR)    maxPayment = 3600;
		if(this.position == Position.TEAM_LEAD) maxPayment = 7000;
		return maxPayment;
	}
	
	public int getMinWorkExp(){
		int minWorkExp = 0;
		if(this.position == Position.TRAINER)   minWorkExp = 0;
		if(this.position == Position.JUNIOR)    minWorkExp = 0;
		if(this.position == Position.MIDDLE)    minWorkExp = 12;
		if(this.position == Position.SENEOR)    minWorkExp = 36;
		if(this.position == Position.TEAM_LEAD) minWorkExp = 36;
		return minWorkExp;
	}
	
	public int getMaxWorkExp(){
		int maxWorkExp = 0;
		if(this.position == Position.TRAINER)   maxWorkExp = 0;
		if(this.position == Position.JUNIOR)    maxWorkExp = 12;
		if(this.position == Position.MIDDLE)    maxWorkExp = 36;
		if(this.position == Position.SENEOR)    maxWorkExp = 60;
		if(this.position == Position.TEAM_LEAD) maxWorkExp = 60;
		return maxWorkExp;
	}
}
