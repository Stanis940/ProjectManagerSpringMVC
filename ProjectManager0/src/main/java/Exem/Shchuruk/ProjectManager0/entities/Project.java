package Exem.Shchuruk.ProjectManager0.entities;

import java.util.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import Exem.Shchuruk.ProjectManager0.AppHelpLogic;

@Entity
@Table(name = "projects")
public class Project {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "payment", nullable = false)
	private int payment;
	
	@Column(name = "workTime", nullable = false)
	private Date workTime;
	
	@Column(name = "time", nullable = false)
	private Date time;
	
	@ManyToOne
	@JoinColumn(name = "company_id", nullable=true) 
	private Company company;
	
	@OneToMany(mappedBy="project", cascade = CascadeType.ALL
			, fetch = FetchType.LAZY)
	private List<Programmer> programmers;
	
	public Project(){
		this.time = new Time(0l);
		this.programmers = new ArrayList<Programmer>();
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

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public Date getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Date  workTime) {
		this.workTime = workTime;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Programmer> getProgrammers() {
		return programmers;
	}

	public void setProgrammers(List<Programmer> programmers) {
		this.programmers = programmers;
	}
	
	public String getWorkTimeStr(){
		return AppHelpLogic.dateToTimeStr(this.workTime);
	}
	
	public String getTimeStr(){
		return AppHelpLogic.dateToTimeStr(this.time);
	}
	
	public Date getRemainingTime(){
		Date date = new Date(this.time.getTime() - new Date().getTime());
		return date;
	}
	
	public String getRemainingTimeStr(){
		return AppHelpLogic.dateToTimeStr(this.getRemainingTime());
	}
	
	public float getTotalProgrammersProductivity(){
		float productivity = 0f;
		for(Programmer programmer : this.programmers){
			productivity += programmer.getProductivity();
		}
		return productivity;
	}
}
