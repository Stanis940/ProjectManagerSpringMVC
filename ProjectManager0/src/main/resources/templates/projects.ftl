<#-- @ftlvariable name="projects" type="entities.Project" -->
<#-- @ftlvariable name="company" type="entities.Company" -->
<#-- @ftlvariable name="game_over" type="boolean" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Projects</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/app.css" rel="stylesheet">
</head>
<body>
	<#assign max_programmers_on_project = 5>
	<br>
	<div class="container">
		<div class="label label-primary pull-right">
			<a href="/projects/proposedProjects">Take new project...</a>
		</div>
		<div class="header">
			<h1>Taken projects list</h1>
		</div>
		<div class="li-div header-div project-color-header project-border">
			<div class="col-md-2">Project name</div>
			<div class="col-md-2">Payment</div>
			<div class="col-md-2">Work time</div>
			<div class="col-md-2">Time</div>
			<div class="col-md-2">Action</div>
			<div class="col-md-2">Action</div>
		</div>
		<#list projects as project>
			<div class="li-div project-color project-border">
				<div class="col-md-2">${project.name}</div>
				<div class="col-md-2">${project.payment}</div>
				<div class="col-md-2">${project.getWorkTimeStr()}</div>
				<div class="col-md-2">${project.getRemainingTimeStr()}</div>
				<div class="col-md-2">
					<a href="/projects/removeProject/${project.id}">
						Remove project
					</a>
				</div>
				<div class="col-md-2">
					<#if project.programmers?size < max_programmers_on_project>
					<a href="/projects/${project.id}/proposedProgrammers">
						Hire programmer
					</a>
					<#else>
						Max programmers
					</#if>
				</div>
			</div>
			<#if project.programmers?size != 0>
			<table class="table sub_table programmer-border">
				<tr class="programmer-color-header">
					<th>${project.programmers?size}/${max_programmers_on_project}</th>
					<th>Name</th>
					<th>Position</th>
					<th>Expirience</th>
					<th>Productivity</th>
					<th>Salary</th>
					<th>Work time</th>
					<th>Action</th>
				</tr>
				<#assign count = 0>
				<#assign totalProductivity = 0>
				<#assign totalSalary = 0>
				<#list project.programmers as programmer>
					<#assign count++>
					<#assign totalProductivity = totalProductivity + programmer.getProductivity()>
					<#assign totalSalary = totalSalary + programmer.salary>
					<tr class="programmer-color">
						<td>${count}</td>
						<td>${programmer.name} ${programmer.surname}</td>
						<td>${programmer.position}</td>
						<td>${programmer.workExp}</td>
						<td>${programmer.getProductivity()}</td>
						<td>${programmer.salary}</td>
						<td>${programmer.getWorkTimeStr()}</td>
						<td><a href="/projects/${project.id}/programmers/dismissProgrammer/${programmer.id}">Dismiss programmer</a></td>
					</tr>
				</#list>
				<tr class="programmer-color">
						<td>-</td>
						<td>Total:</td>
						<td>-</td>
						<td>-</td>
						<td>${totalProductivity}</td>
						<td>${totalSalary}</td>
						<td>-</td>
						<td>-</td>
					</tr>
			</table>
			</#if>
		</#list>
	</div>
	<div class="balance">
		Balance: ${company.balance}$
	</div>
	<#if game_over??>
		<#if game_over == true>
			<div class="game-over win-game">
				You win! But you can continue game
			</div>
		</#if>
		<#if game_over == false>
			<div class="game-over lose-game">
				<a href="/"> Start new game</a>
				<br>
				You are bankrupt! Game Over! 
			</div>
		</#if>
	</#if>
	
	<script src="/js/bootstrap.min.js"></script>
	
	<script type='text/javascript'>
		setInterval("document.location.reload()", 1000);
	</script>
</body>
</html>