package Exem.Shchuruk.ProjectManager0.controllers;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Exem.Shchuruk.ProjectManager0.AppHelpLogic;
import Exem.Shchuruk.ProjectManager0.entities.Company;
import Exem.Shchuruk.ProjectManager0.entities.Position;
import Exem.Shchuruk.ProjectManager0.entities.Programmer;
import Exem.Shchuruk.ProjectManager0.entities.Project;
import Exem.Shchuruk.ProjectManager0.repositories.CompanyRepository;
import Exem.Shchuruk.ProjectManager0.repositories.ProgrammerRepository;
import Exem.Shchuruk.ProjectManager0.repositories.ProjectRepository;

@Controller
public class ProgrammerController {

	@Autowired
	private ProgrammerRepository programmerRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@RequestMapping(value = "/projects/{projectId}/proposedProgrammers", method = RequestMethod.GET)
	public String programmers(@PathVariable("projectId") String strProjectId, Model model){
		//удаление неразрывных пробелов;
		strProjectId = strProjectId.replaceAll("[\u00A0]","");
		Long projectId = Long.parseLong(strProjectId);
		this.programmerRepository.deleteByCompany(null);
		generateProgrammers();
		model.addAttribute("programmers", this.programmerRepository.findByCompany(null));
		model.addAttribute("projectId", projectId);
		model.addAttribute("company", this.companyRepository.findOne(Company.ID));
		return "programmers";
	}
	
	@RequestMapping(value = "//projects/{projectId}/proposedProgrammers/hireProgrammer/{id}", method = RequestMethod.GET)
	public String hireProgrammer(@PathVariable("projectId") String strProjectId,
							  @PathVariable("id") String strId, Model model){
		//удаление неразрывных пробелов;
		strProjectId = strProjectId.replaceAll("[\u00A0]","");
		strId = strId.replaceAll("[\u00A0]", "");
		Long projectId = Long.parseLong(strProjectId);
		Long id = Long.parseLong(strId);
		Programmer programmer = this.programmerRepository.findOne(id);
		Project project = this.projectRepository.findOne(projectId);
		Company company = this.companyRepository.findOne(Company.ID);
		
		programmer.setCompany(company);
		programmer.setProject(project);
		programmer.setStartWork(new Date());
		this.programmerRepository.save(programmer);
		
		return "redirect:/projects";
	}
	
	@RequestMapping(value = "/projects/{projectId}/programmers/dismissProgrammer/{id}", method = RequestMethod.GET)
	public String dismissProgrammer(@PathVariable("projectId") String strProjectId,
							  @PathVariable("id") String strId, Model model){
		//удаление неразрывных пробелов;
		strProjectId = strProjectId.replaceAll("[\u00A0]","");
		strId = strId.replaceAll("[\u00A0]", "");
		Long projectId = Long.parseLong(strProjectId);
		Long id = Long.parseLong(strId);
		Company company = this.companyRepository.findOne(Company.ID);
		Project project = this.projectRepository.findOne(projectId);
		Programmer programmer = this.programmerRepository.findOne(id);
		
		company.setBalance(AppHelpLogic.balanceAfterPaySalary(programmer, company));
		project.setWorkTime(AppHelpLogic.projectWorkTimeAfterProgrammerWork(programmer, project));
		
		this.programmerRepository.delete(id);
		return "redirect:/projects";
	}
	
	private void generateProgrammers(){
		Random rand = new Random();
		String[] names = { "Jason", "Arthur", "Daniel", "Eustace", "Peter",
						   "Matthew", "Peregrine", "Robert", "Anthony", "William",
						   "Kristin", "Bertina", "Rose", "Joan", "Dorothy"};
		
		String[] surnames = { "Page", "Jones", "Heath", "Simmons", "Washington",
							  "Scott", "Wilkins", "Brooks", "Kennedy", "Lambert",
							  "Higgins", "Morton", "Bradford", "Blankenship", "Banks"};
		
		Position[] positions = { Position.TRAINER, Position.JUNIOR, Position.MIDDLE,
								 Position.SENEOR, Position.TEAM_LEAD};
		
		for(int i = 0; i < 10; i++)
		{
			Programmer programmer = new Programmer();
			
			programmer.setName(names[rand.nextInt(names.length )]);
			programmer.setSurname(surnames[rand.nextInt(surnames.length)]);
			programmer.setPosition(positions[rand.nextInt(positions.length)]);
			
			programmer.setSalary(
					rand.nextInt(programmer.getMaxSalary() - programmer.getMinSalary())
							+ programmer.getMinSalary()
					);
			if(programmer.getPosition() == Position.TRAINER)
			{
				programmer.setWorkExp(0);
			} else {
				programmer.setWorkExp(
						rand.nextInt(programmer.getMaxWorkExp() - programmer.getMinWorkExp())
								+ programmer.getMinWorkExp()
						);
			}
			this.programmerRepository.save(programmer);
		}
	}
}
