package Exem.Shchuruk.ProjectManager0.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import Exem.Shchuruk.ProjectManager0.entities.Company;
import Exem.Shchuruk.ProjectManager0.entities.Programmer;

public interface ProgrammerRepository extends CrudRepository<Programmer, Long> {
	
	public Iterable<Programmer> findByCompany(Company company);
	
	@Transactional
	public Iterable<Programmer> deleteByCompany(Company company);
}
