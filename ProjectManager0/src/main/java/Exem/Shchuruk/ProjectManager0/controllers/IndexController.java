package Exem.Shchuruk.ProjectManager0.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Exem.Shchuruk.ProjectManager0.entities.Company;
import Exem.Shchuruk.ProjectManager0.repositories.CompanyRepository;

@Controller
public class IndexController {
	
	@Autowired
	private CompanyRepository companyRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(){
		return "home";
	}
	
	@RequestMapping(value = "/startGame", method = RequestMethod.GET)
	public String startGame(){
		this.companyRepository.deleteAll();
		this.companyRepository.save(new Company());
		return "redirect:/projects";
	}
}
