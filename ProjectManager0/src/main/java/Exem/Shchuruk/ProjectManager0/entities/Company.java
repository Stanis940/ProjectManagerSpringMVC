package Exem.Shchuruk.ProjectManager0.entities;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "companies")
public class Company {
	
	public static final Long ID = 1l;
	public static final int WIN_BALANCE = 50000;
	public static final int START_BALANCE = 10000;
	
	@Id
    @Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "balance", nullable = false)
	private int balance;
	
	@OneToMany(mappedBy="company", cascade = CascadeType.ALL
			, fetch = FetchType.LAZY)
	private List<Project> projects;
	
	@OneToMany(mappedBy="company", cascade = CascadeType.ALL
			, fetch = FetchType.LAZY)
	private List<Programmer> programmers;
	
	public Company(){
		this.id = Company.ID;
		this.projects = new ArrayList<Project>();
		this.programmers = new ArrayList<Programmer>();
		this.balance = Company.START_BALANCE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Programmer> getProgrammers() {
		return programmers;
	}

	public void setProgrammers(List<Programmer> programmers) {
		this.programmers = programmers;
	}
	
}
