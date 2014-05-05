<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>  
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>  
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>To Do List</title>
	
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
	
	<script language="javascript" type="text/javascript" src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

	<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	
    <script data-require="angular.js@*" data-semver="1.2.0-rc3-nonmin" src="http://code.angularjs.org/1.2.0-rc.3/angular.js"></script>
    <script data-require="ng-table@*" data-semver="0.3.0" src="http://bazalt-cms.com/assets/ng-table/0.3.0/ng-table.js"></script>
    
    <link data-require="ng-table@*" data-semver="0.3.0" rel="stylesheet" href="http://bazalt-cms.com/assets/ng-table/0.3.0/ng-table.css" />

	<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/js/MainController.js"></script>
	
	<style type="text/css">
		td { padding: 0.2em 1em; }
		th { text-align: center; }
		thead {
		    border-bottom: 2px solid black; 
		    cursor: pointer;  
		}
		.sort-true {
		     background:no-repeat right center url(data:image/gif;base64,R0lGODlhCgAKALMAAHFxcYKCgp2dnaampq+vr83NzeHh4f///wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAkAAAgAIf/8SUNDUkdCRzEwMTIAAAUwYXBwbAIgAABtbnRyUkdCIFhZWiAH2QACABkACwAaAAthY3NwQVBQTAAAAABhcHBsAAAAAAAAAAAAAAAAAAAAAAAA9tYAAQAAAADTLWFwcGwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAtkc2NtAAABCAAAAvJkZXNjAAAD/AAAAG9nWFlaAAAEbAAAABR3dHB0AAAEgAAAABRyWFlaAAAElAAAABRiWFlaAAAEqAAAABRyVFJDAAAEvAAAAA5jcHJ0AAAEzAAAADhjaGFkAAAFBAAAACxn/1RSQwAABLwAAAAOYlRSQwAABLwAAAAObWx1YwAAAAAAAAARAAAADGVuVVMAAAAmAAACfmVzRVMAAAAmAAABgmRhREsAAAAuAAAB6mRlREUAAAAsAAABqGZpRkkAAAAoAAAA3GZyRlUAAAAoAAABKml0SVQAAAAoAAACVm5sTkwAAAAoAAACGG5iTk8AAAAmAAABBHB0QlIAAAAmAAABgnN2U0UAAAAmAAABBGphSlAAAAAaAAABUmtvS1IAAAAWAAACQHpoVFcAAAAWAAABbHpoQ04AAAAWAAAB1HJ1UlUAAAAiAAACpHBsUEwAAAAsAAACxgBZAGwAZQBpAG4AZf8AbgAgAFIARwBCAC0AcAByAG8AZgBpAGkAbABpAEcAZQBuAGUAcgBpAHMAawAgAFIARwBCAC0AcAByAG8AZgBpAGwAUAByAG8AZgBpAGwAIABHAOkAbgDpAHIAaQBxAHUAZQAgAFIAVgBCTgCCLAAgAFIARwBCACAw1zDtMNUwoTCkMOuQGnUoACAAUgBHAEIAIIJyX2ljz4/wAFAAZQByAGYAaQBsACAAUgBHAEIAIABHAGUAbgDpAHIAaQBjAG8AQQBsAGwAZwBlAG0AZQBpAG4AZQBzACAAUgBHAEIALQBQAHIAbwBmAGkAbGZukBoAIABSAEcAQgAgY8+P8GX/h072AEcAZQBuAGUAcgBlAGwAIABSAEcAQgAtAGIAZQBzAGsAcgBpAHYAZQBsAHMAZQBBAGwAZwBlAG0AZQBlAG4AIABSAEcAQgAtAHAAcgBvAGYAaQBlAGzHfLwYACAAUgBHAEIAINUEuFzTDMd8AFAAcgBvAGYAaQBsAG8AIABSAEcAQgAgAEcAZQBuAGUAcgBpAGMAbwBHAGUAbgBlAHIAaQBjACAAUgBHAEIAIABQAHIAbwBmAGkAbABlBB4EMQRJBDgEOQAgBD8EQAQ+BEQEOAQ7BEwAIABSAEcAQgBVAG4AaQB3AGUAcgBzAGEAbABuAHkAIABwAHIAbwBm/wBpAGwAIABSAEcAQgAAZGVzYwAAAAAAAAAUR2VuZXJpYyBSR0IgUHJvZmlsZQAAAAAAAAAAAAAAFEdlbmVyaWMgUkdCIFByb2ZpbGUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFhZWiAAAAAAAABadQAArHMAABc0WFlaIAAAAAAAAPNSAAEAAAABFs9YWVogAAAAAAAAdE0AAD3uAAAD0FhZWiAAAAAAAAAoGgAAFZ8AALg2Y3VydgAAAAAAAAABAc0AAHRleHQAAAAAQ29weXJpZ2h0IDIwMDcgQXBwbGUgSW5jLkMsIGFsbCByaWdodHMgcmVzZXJ2ZWQuAHNmMzIAAAAAAAEMQgAABd7///MmAAAHkgAA/ZH///ui///9owAAA9wAAMBsACwAAAAACgAKAAAEJZAMIcakQZjNtyhFxwEIIRofAookUnapu26t+6KFLYe1TgQ5VwQAOw%3D%3D);
		}
		.sort-false {
		    background:no-repeat right center url(data:image/gif;base64,R0lGODlhCgAKALMAAHFxcYKCgp2dnaampq+vr83NzeHh4f///wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAkAAAgAIf/8SUNDUkdCRzEwMTIAAAUwYXBwbAIgAABtbnRyUkdCIFhZWiAH2QACABkACwAaAAthY3NwQVBQTAAAAABhcHBsAAAAAAAAAAAAAAAAAAAAAAAA9tYAAQAAAADTLWFwcGwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAtkc2NtAAABCAAAAvJkZXNjAAAD/AAAAG9nWFlaAAAEbAAAABR3dHB0AAAEgAAAABRyWFlaAAAElAAAABRiWFlaAAAEqAAAABRyVFJDAAAEvAAAAA5jcHJ0AAAEzAAAADhjaGFkAAAFBAAAACxn/1RSQwAABLwAAAAOYlRSQwAABLwAAAAObWx1YwAAAAAAAAARAAAADGVuVVMAAAAmAAACfmVzRVMAAAAmAAABgmRhREsAAAAuAAAB6mRlREUAAAAsAAABqGZpRkkAAAAoAAAA3GZyRlUAAAAoAAABKml0SVQAAAAoAAACVm5sTkwAAAAoAAACGG5iTk8AAAAmAAABBHB0QlIAAAAmAAABgnN2U0UAAAAmAAABBGphSlAAAAAaAAABUmtvS1IAAAAWAAACQHpoVFcAAAAWAAABbHpoQ04AAAAWAAAB1HJ1UlUAAAAiAAACpHBsUEwAAAAsAAACxgBZAGwAZQBpAG4AZf8AbgAgAFIARwBCAC0AcAByAG8AZgBpAGkAbABpAEcAZQBuAGUAcgBpAHMAawAgAFIARwBCAC0AcAByAG8AZgBpAGwAUAByAG8AZgBpAGwAIABHAOkAbgDpAHIAaQBxAHUAZQAgAFIAVgBCTgCCLAAgAFIARwBCACAw1zDtMNUwoTCkMOuQGnUoACAAUgBHAEIAIIJyX2ljz4/wAFAAZQByAGYAaQBsACAAUgBHAEIAIABHAGUAbgDpAHIAaQBjAG8AQQBsAGwAZwBlAG0AZQBpAG4AZQBzACAAUgBHAEIALQBQAHIAbwBmAGkAbGZukBoAIABSAEcAQgAgY8+P8GX/h072AEcAZQBuAGUAcgBlAGwAIABSAEcAQgAtAGIAZQBzAGsAcgBpAHYAZQBsAHMAZQBBAGwAZwBlAG0AZQBlAG4AIABSAEcAQgAtAHAAcgBvAGYAaQBlAGzHfLwYACAAUgBHAEIAINUEuFzTDMd8AFAAcgBvAGYAaQBsAG8AIABSAEcAQgAgAEcAZQBuAGUAcgBpAGMAbwBHAGUAbgBlAHIAaQBjACAAUgBHAEIAIABQAHIAbwBmAGkAbABlBB4EMQRJBDgEOQAgBD8EQAQ+BEQEOAQ7BEwAIABSAEcAQgBVAG4AaQB3AGUAcgBzAGEAbABuAHkAIABwAHIAbwBm/wBpAGwAIABSAEcAQgAAZGVzYwAAAAAAAAAUR2VuZXJpYyBSR0IgUHJvZmlsZQAAAAAAAAAAAAAAFEdlbmVyaWMgUkdCIFByb2ZpbGUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFhZWiAAAAAAAABadQAArHMAABc0WFlaIAAAAAAAAPNSAAEAAAABFs9YWVogAAAAAAAAdE0AAD3uAAAD0FhZWiAAAAAAAAAoGgAAFZ8AALg2Y3VydgAAAAAAAAABAc0AAHRleHQAAAAAQ29weXJpZ2h0IDIwMDcgQXBwbGUgSW5jLkMsIGFsbCByaWdodHMgcmVzZXJ2ZWQuAHNmMzIAAAAAAAEMQgAABd7///MmAAAHkgAA/ZH///ui///9owAAA9wAAMBsACwAAAAACgAKAAAEJRBJREKZsxQDsCSGIVzZFnYTGIqktp7fG46uzAn2TAyCMPC9QAQAOw%3D%3D);
		}	
	</style>
</head>

<body ng-app="myApp">
	
	<div ng-controller="MainController">
		<div class="container">
			<div class="row">
				
				<BR>
				
				<div class="col-sm-10">
					<form name="userForm" class="simple_form form-horizontal" role="form" novalidate>
						<input type="hidden" id="id" ng-model="id" />
						<div class="form-group">
							<label class="col-sm-5 control-label">&nbsp;</label> 
							<div class="col-sm-5">
								<h3>Add / Modify / Delete TODO items</h3>
							</div>
							<div class="col-sm-4">
								Welcome {{loggedUser.userName}}, <a href="<c:url value="/logout" />" > Logout</a>
							</div>
						</div>
						
						<div class="form-group" ng-class="{ 'has-error' : userForm.description.$error.required }">
							<label class="col-sm-5 control-label">Description</label> 
							<div class="col-sm-5">
								<input type="text" name="description" class="form-control" ng-model="description" required>
							</div>
							<div class="col-sm-4">
								<p ng-show="userForm.description.$error.required" class="help-block">Description is required.</p>
							</div>
						</div>
						
						<div class="form-group" ng-class="{ 'has-error' : userForm.priority.$error.selectedvalue }">
							<label class="col-sm-5 control-label">Priority</label> 
							<div class="col-sm-5">
								<select id="priority" name="priority" class="form-control" ng-model="priority" ng-selectedvalue>
									<option value="-1"></option>
									<option ng-repeat="prior in priorities" value="{{prior.id}}">{{prior.name}}</option>
								</select>
							</div>
							<div class="col-sm-4">
								<p ng-show="userForm.priority.$error.selectedvalue" class="help-block">Priority is required.</p>
							</div>
						</div>
						
						<div class="form-group" ng-class="{ 'has-error' : userForm.status.$error.selectedvalue }">
							<label class="col-sm-5 control-label">Status</label> 
							<div class="col-sm-5">
								<select id="status" name="status" class="form-control" ng-model="status" ng-selectedvalue ng-disabled="!isModify || isCompleted">
									<option value="-1"></option>
									<option ng-repeat="stat in statuses" value="{{stat.id}}" ng-selected="(status == stat.id)?'selected':''">{{stat.name}}</option>
								</select>
							</div>
							<div class="col-sm-4">
								<p ng-show="userForm.status.$error.selectedvalue" class="help-block">Status is required.</p>
							</div>
						</div>

						<div class="form-group" ng-class="{ 'has-error' : userForm.duedate.$error.required || userForm.duedate.$error.validatedate || userForm.duedate.$error.dategreaterthantoday }">
							<label class="col-sm-5 control-label">Due Date</label>
							<div class="col-sm-5">
								<input type="text" name="duedate" class="form-control" ng-model="duedate" required ng-validatedate ng-dategreaterthantoday>
							</div>
							<div class="col-sm-4">
								<p ng-show="userForm.duedate.$error.required" class="help-block">Due Date is required.</p>
								<p ng-show="userForm.duedate.$error.validatedate" class="help-block">Invalid date format.</p>
								<p ng-show="userForm.duedate.$error.dategreaterthantoday" class="help-block">Date must be greather than today.</p>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-5 control-label">Create Date</label>
							<div class="col-sm-5">
								<input type="text" name="createdate" class="form-control" ng-model="createdate" readonly="readonly">
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-5 control-label">&nbsp;</label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" ng-disabled="userForm.$invalid" ng-show="!isModify" ng-click="addItem()">Add</button>
								<button type="button" class="btn btn-primary" ng-disabled="userForm.$invalid" ng-show="isModify" ng-click="addItem()">Add New</button>
								<button type="button" class="btn btn-primary" ng-disabled="userForm.$invalid" ng-show="isModify && !isCompleted" ng-click="modifyItem()">Modify</button>
								<button type="button" class="btn btn-primary" ng-click="clearDate()">Clear</button>
							</div>
						</div>
					</form>
				</div>
				<table width="80%" ng-show="items.length">
				    <thead>
				        <tr>
				        	<th style="cursor:context-menu" width="40%">Description</th>
				         	<th style="cursor:context-menu" width="15%">Status</th>
				        	<th ng-class="selectedCls('priority')" ng-click="changeSorting('priority')" width="15%">Priority</th>
				        	<th style="cursor:context-menu" width="15%">Create Date</th>
				        	<th ng-class="selectedCls('dueDate')" ng-click="changeSorting('dueDate')" width="15%">Due Date</th>
				        	<th style="cursor:context-menu" width="15%">&nbsp;</th>
				        </tr>
				    </thead>
				    <tbody>
				        <tr ng-repeat="row in items | orderBy: sort.column : sort.descending">
				            <td><a ng-click="loadItem({{row.id}})">{{row.description}}</a></td>
				            <td>{{getSatusDesc(row.status)}}</td>
				            <td>{{getPriorityDesc(row.priority)}}</td> 
				           <td>{{getDate(row.creationDate)}}</td> 
				           <td>{{getDate(row.dueDate)}}</td> 
				           <td><a ng-click="deleteItem({{row.id}})">delete</a></td> 
				        </tr>
				    </tbody>
				</table>
				
				<table width="80%" ng-show="itemsCompleted.length">
				    <thead>
				        <tr>
				        	<th style="cursor:context-menu" width="40%">Description</th>
				         	<th style="cursor:context-menu" width="15%">Status</th>
				        	<th ng-class="selectedClsCompleted('priority')" ng-click="changeSortingCompleted('priority')" width="15%">Priority</th>
				        	<th style="cursor:context-menu" width="15%">Create Date</th>
				        	<th ng-class="selectedClsCompleted('dueDate')" ng-click="changeSortingCompleted('dueDate')" width="15%">Due Date</th>
				        </tr>
				    </thead>
				    <tbody>
				        <tr ng-repeat="row in itemsCompleted | orderBy: sortcompleted.column : sortcompleted.descending">
				            <td><a ng-click="loadItem({{row.id}})">{{row.description}}</a></td>
				            <td>{{getSatusDesc(row.status)}}</td>
				            <td>{{getPriorityDesc(row.priority)}}</td> 
				           <td>{{getDate(row.creationDate)}}</td> 
				           <td>{{getDate(row.dueDate)}}</td> 
				        </tr>
				    </tbody>
				</table>
			</div>
		</div>
		<br>
	</div>

</body>
</html>