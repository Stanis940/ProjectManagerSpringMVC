<#-- @ftlvariable name="projects" type="entities.Project" -->
<#-- @ftlvariable name="company" type="entities.Company" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Take project list</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/app.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="header">
			<h1>Projects list</h1>
		</div>
		<table class="table project-border">
			<tr class="project-color-header">
				<th>Project name</th>
				<th>Payment</th>
				<th>Work time</th>
				<th>Time</th>
				<th>Action</th>
			</tr>
			<#list projects as project>
				<tr class="project-color">
					<td>${project.name}</td>
					<td>${project.payment}</td>
					<td>${project.getWorkTimeStr()}</td>
					<td>${project.getRemainingTimeStr()}</td>
					<td><a href="/projects/proposedProjects/takeProject/${project.id}">Take project</a></td>
					
				</tr>
			</#list>
		</table>
		<p class="text-right"><a class="link-to" href="/projects/proposedProjects">Reload</a></p>
		<p class="text-right"><a class="link-to" href="/projects">Back</a></p>
	</div>
	
	<script src="/js/bootstrap.min.js"></script>
</body>