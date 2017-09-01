<#-- @ftlvariable name="programmers" type="entities.Programmer" -->
<#-- @ftlvariable name="company" type="entities.Company" -->
<#-- @ftlvariable name="projectId" type="Long" -->
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
			<h1>Labor market</h1>
		</div>
		<table class="table programmer-border">
			<tr class="programmer-color-header">
				<th>Name</th>
				<th>Position</th>
				<th>Expirience</th>
				<th>Productivity</th>
				<th>Salary</th>
				<th>Action</th>
			</tr>
			<#list programmers as programmer>
				<tr class="programmer-color">
					<td>${programmer.name} ${programmer.surname}</td>
					<td>${programmer.position}</td>
					<td>${programmer.workExp}</td>
					<td>${programmer.getProductivity()}</td>
					<td>${programmer.salary}</td>
					<td><a href="/projects/${projectId}/proposedProgrammers/hireProgrammer/${programmer.id}">Hire programmer</a></td>
				</tr>
			</#list>
		</table>
		<p class="text-right"><a class="link-to" href="/projects/${projectId}/proposedProgrammers">Reload</a></p>
		<p class="text-right"><a class="link-to" href="/projects">Back</a></p>
	</div>
	
	<script src="/js/bootstrap.min.js"></script>
</body>