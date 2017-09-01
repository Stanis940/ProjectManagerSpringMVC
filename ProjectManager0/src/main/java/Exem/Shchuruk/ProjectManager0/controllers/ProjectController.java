package Exem.Shchuruk.ProjectManager0.controllers;

import java.util.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Exem.Shchuruk.ProjectManager0.AppHelpLogic;
import Exem.Shchuruk.ProjectManager0.entities.Company;
import Exem.Shchuruk.ProjectManager0.entities.Programmer;
import Exem.Shchuruk.ProjectManager0.entities.Project;
import Exem.Shchuruk.ProjectManager0.repositories.CompanyRepository;
import Exem.Shchuruk.ProjectManager0.repositories.ProgrammerRepository;
import Exem.Shchuruk.ProjectManager0.repositories.ProjectRepository;

@Controller
public class ProjectController {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private ProgrammerRepository programmerRepository;
	
	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public String projects(Model model){
		Company company = getCompany();
		Iterable<Project> projects = this.projectRepository.findByCompany(company);
		for(Project project : projects){		
			for(Programmer programmer : project.getProgrammers()){
				company.setBalance(AppHelpLogic.balanceAfterPaySalary(programmer, company));				
				project.setWorkTime(AppHelpLogic.projectWorkTimeAfterProgrammerWork(programmer, project));
			}
			
			if(project.getWorkTime().getTime() <= 0){
				long ExcessTimeMillis = (long)( project.getWorkTime().getTime()
											/ project.getTotalProgrammersProductivity() );
				
				if(ExcessTimeMillis <= project.getRemainingTime().getTime()){
					for(Programmer programmer : project.getProgrammers()){
						company.setBalance(AppHelpLogic.balanceAfterPaySalary(company.getBalance(), 
														programmer.getSalary(), ExcessTimeMillis));		
					}
					company.setBalance(company.getBalance() + project.getPayment());
					this.companyRepository.save(company);
					this.projectRepository.delete(project);
					continue;
				}
			}
			
			if(project.getRemainingTime().getTime() < 0){
				for(Programmer programmer : project.getProgrammers()){
					company.setBalance(AppHelpLogic.balanceAfterPaySalary(company.getBalance(), 
							programmer.getSalary(), project.getRemainingTime().getTime()));				
				}
				this.companyRepository.save(company);
				this.projectRepository.delete(project);
				continue;
			}
		}
		
		if(company.getBalance() < 0){
			this.companyRepository.save(company);
			this.programmerRepository.deleteAll();
			this.projectRepository.deleteAll();
			model.addAttribute("game_over", false);
		}
		if(company.getBalance() >= Company.WIN_BALANCE){
			model.addAttribute("game_over", true);
		}
		
		model.addAttribute("projects", projects);
		model.addAttribute("company", company);
		return "projects";
	}
	
	@RequestMapping(value = "/projects/proposedProjects", method = RequestMethod.GET)
	public String proposedProjects(Model model){
		this.projectRepository.deleteByCompany(null);
		generateProjects();
		model.addAttribute("projects", this.projectRepository.findByCompany(null));
		model.addAttribute("company", this.companyRepository.findOne(Company.ID));
		return "proposed_projects";
	}
	
	@RequestMapping(value = "/projects/proposedProjects/takeProject/{id}", method = RequestMethod.GET)
	public String takeProject(@PathVariable("id") String strId, Model model){
		//удаление неразрывных пробелов;
		strId = strId.replaceAll("[\u00A0]","");
		Long id = Long.parseLong(strId);
		
		Company company = getCompany();
		Project project = this.projectRepository.findOne(id);
		project.setCompany(company);
		this.projectRepository.save(project);
		return "redirect:/projects";
	}
	
	@RequestMapping(value = "/projects/removeProject/{id}", method = RequestMethod.GET)
	public String removeProject(@PathVariable("id") String strId, Model model){
		//удаление неразрывных пробелов;
		strId = strId.replaceAll("[\u00A0]","");
		Long id = Long.parseLong(strId);
		
		Company company = this.companyRepository.findOne(Company.ID);
		Project project = this.projectRepository.findOne(id);
		for(Programmer programmer : project.getProgrammers()){
			company.setBalance(AppHelpLogic.balanceAfterPaySalary(programmer, company));
		}
		this.companyRepository.save(company);
		
		this.projectRepository.delete(id);
		return "redirect:/projects";
	}
	
	@RequestMapping(value = "/projects/generate", method = RequestMethod.GET)
	public String generate(Model model){
		this.projectRepository.deleteByCompany(null);
		generateProjects();
		return "redirect:/projects";
	}
	
	private void generateProjects(){
		String[] names = {"InternetShop", "WebSite", "Game", "Application", "Messanger" };
		Random rand = new Random();
		for(int i = 0; i < 10; i++){		
			float coefPersonMinutes = (float)rand.nextInt(9) + 0.5f ;        		//Количество чел/мин труда
			float coefDeviations = 1f;									        	//Коэф случайных отклонений
			float coefRequirementsOfTime = 1f;										//Коэф повышенных требований ко времени
			float coefRequirementsOfPayment = 1f;									//Коэф повышенных требований к оплате за проект
			
			if(rand.nextBoolean() == true) {
				coefRequirementsOfTime = (float)(rand.nextInt(300) + 100) / 100f;	
				coefRequirementsOfPayment = 1f + (coefRequirementsOfTime * 0.1f);
			} else {
				coefRequirementsOfTime = (float)(rand.nextInt(75) + 25) / 100f;
				coefRequirementsOfPayment = 1f - ((1f / coefRequirementsOfTime) * 0.05f);
			}
			
			Project project = new Project();
			project.setName(names[rand.nextInt(4)]);
			
			coefDeviations = (float)(rand.nextInt(40) - 20 + 100) / 100f;
			project.setPayment( (int)(4000 * coefPersonMinutes * coefDeviations * coefRequirementsOfPayment) );
			
			coefDeviations = (float)(rand.nextInt(40) - 20 + 100) / 100f;
			project.setWorkTime(new Date((long)(60000f * coefPersonMinutes * coefDeviations)));
				
			Date time = new Date(new Date().getTime() + 20000
					+ (long)((project.getWorkTime().getTime()) / coefRequirementsOfTime)
					);
			project.setTime(time);       
			this.projectRepository.save(project);
		}
	}
	
	private Company getCompany(){
		Company company = this.companyRepository.findOne(Company.ID);
		if(company == null)
		{
			company = new Company();
			this.companyRepository.save(company);
		}
		return company;
	}
}
