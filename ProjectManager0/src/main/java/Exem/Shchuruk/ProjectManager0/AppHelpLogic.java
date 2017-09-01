package Exem.Shchuruk.ProjectManager0;

import java.util.Date;

import Exem.Shchuruk.ProjectManager0.entities.Company;
import Exem.Shchuruk.ProjectManager0.entities.Programmer;
import Exem.Shchuruk.ProjectManager0.entities.Project;

public class AppHelpLogic {
	
	public static String dateToTimeStr(Date date){
		String mines = "";
		int totalSeconds = (int)((date.getTime()) / 1000);
		if(totalSeconds < 0){
			mines = "-";
			totalSeconds = totalSeconds * -1;
		}
		int hours = totalSeconds / (60 * 60);
		int minutes = (totalSeconds / 60) - hours * 60;
		int seconds = totalSeconds - (minutes * 60) - (hours * 60 * 60);
		return mines + String.format("%d:%02d:%02d", hours, minutes, seconds);
	}
	
	public static int balanceAfterPaySalary(Programmer programmer, Company company){
		return AppHelpLogic.balanceAfterPaySalary(company.getBalance(), programmer.getSalary(), 
											programmer.getWorkTime().getTime());
	}
	
	public static int balanceAfterPaySalary(int currentBalance, int salary, long workTimeMillis){
		int balance = currentBalance - (int)(salary * ((float)(workTimeMillis / 1000l) / 60f));
		return balance;
	}
	
	
	public static Date projectWorkTimeAfterProgrammerWork(Programmer programmer, Project project){
		Date date = new Date(project.getWorkTime().getTime()
				- (long)(programmer.getWorkTime().getTime() * programmer.getProductivity()));
		return date;
	}
	
}
