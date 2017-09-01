package Exem.Shchuruk.ProjectManager0.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import Exem.Shchuruk.ProjectManager0.entities.Company;
import Exem.Shchuruk.ProjectManager0.entities.Project;


public interface ProjectRepository extends CrudRepository<Project, Long> {
	
	public Iterable<Project> findByCompany(Company company);
	
	@Transactional
	public Iterable<Project> deleteByCompany(Company company);
}
