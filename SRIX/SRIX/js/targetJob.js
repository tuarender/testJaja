/*-------This script use in targetJob.jsp only------------*/

// array of target Location //
var targetRegion = [] ;
var targetState = [] ;
var targetCity = [] ;

function getJobTypesForPc()
{
	var container = $('div.errorContainer');
	$.ajax(
	{
		type: "GET",
 		url: '/TargetJobTypeServ',
 		data: {'service':'view','idResume':idResume},
 		async:false,
 		success: function(data)
 		{
 			var obj = jQuery.parseJSON(data);
   			if(obj.success==1)
   			{
   				if(obj.jobTypes.length>0)
   				{
   					for(var i=0; i<obj.jobTypes.length; i++)
   					{
   						$("#"+obj.jobTypes[i].jobType).prop( "checked", true );
   					}
   					$('#partJobType').val(1);
   				}
   				else
   				{
   					$('#partJobType').val('');
   				}
   			}
       		else
       		{
       			$('#partJobType').val('');
       			$('div.container li').remove();
       			for(var i=0; i<obj.errors.length; i++)
       			{
       				container.append('<li><label class="error">'+obj.errors[i]+'</label></li>');
       			}
       			container.css({'display':'block'});
       			$('html, body').animate({scrollTop: '0px'}, 300); 
       		}
 		}
	});
}

//Target JobType
function getJobTypes()
{
	var container = $('div.errorContainer');
	$.ajax(
	{
		type: "GET",
 		url: '/TargetJobTypeServ',
 		data: {'service':'view','idResume':idResume},
 		async:false,
 		success: function(data)
 		{
 			var obj = jQuery.parseJSON(data);
   			if(obj.success==1)
   			{
   				if(obj.jobTypes.length>0)
   				{
   					var html='<ul>';
   					for(var i=0; i<obj.jobTypes.length; i++)
   					{
   						html+='<li>'+obj.names[i]+'</li>';
   						$('#idJobType option[value="'+obj.jobTypes[i].jobType+'"]').prop('selected',true);
   					}
   					html+='</ul>';
   					$('#jobTypeList').html(html);
   					$('#partJobType').val(1);
   				}
   				else
   				{
   					$('#jobTypeList').html('');
   					$('#partJobType').val('');
   				}
   			}
       		else
       		{
       			$('div.errorContainer li').remove();
       			for(var i=0; i<obj.errors.length; i++)
       			{
       				container.append('<li><label class="error">'+obj.errors[i]+'</label></li>');
       			}
       			container.css({'display':'block'});
       			$('html, body').animate({scrollTop: '0px'}, 300); 
       		}
 		}
	});
}	


//Target JobField
function getTargetJobField()
{
	$.ajax(
	{
		type: "GET",
 		url: '/TargetJobFieldServ',
 		data: {'service':'view','idResume':idResume},
 		async:false,
 		success: function(data)
 		{
 			var obj = jQuery.parseJSON(data);
 			if(obj.success==1)
			{
 				if(obj.jobFields.length>0)
 				{
					var html='<ol>';
					for(var i=0; i<obj.jobFields.length; i++)
					{
						html+='<li class="answer">'+obj.jobFieldNames[i]+'<br>'+globalSubField+' : '+obj.subFieldNames[i]+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteTargetJobField('+idResume+','+obj.jobFields[i].idJobfield+','+obj.jobFields[i].idSubfield+');">'+globalDelete+'</a></li>';
					}
					html+='</ol>'; 	
					$('#jobFieldList').html(html);
					$('#partJobField').val('1');
 				}
 				else
 				{
					$('#jobFieldList').html('');
					$('#partJobField').val('');
 				}
			}
			if(obj.jobFields.length>=3)
			{
				$('#addJobFieldLayer').hide();
			}
			else
			{
				$('#addJobFieldLayer').show();
			}
 		}
	});
}

function deleteTargetJobField(idResume, idJobField, idSubField)
{
	$.ajax(
	{
		type: "POST",
 		url: '/TargetJobFieldServ',
 		data: {'idResume':idResume,'service':'delete','idJobField':idJobField,'idSubField':idSubField},
 		async:false,
 		success: function(data)
 		{
 			var obj = jQuery.parseJSON(data);
 			if(obj.success==1)
			{
				getTargetJobField();
			}
 		}
	});
}

function getSubField(idField)
{
	if(idField>0)
	{
		$.ajax(
		{
			url:'/AjaxServ?service=SubField&idJobField='+idField+'&idLanguage='+idLanguage,
			success:function(result)
			{
				var subField = jQuery.parseJSON(result);
				$('#idSubField option').remove();
				$('#idSubField').append('<option value="">'+globalSelect+'</option>');
				for (var i=0; i<subField.length; i++) 
				{
			        $('#idSubField').append('<option value="'+subField[i].idSubfield+'">'+subField[i].subfieldName+'</option>');
			    }
			    $('#idSubField').append('<option value="-1">'+globalOther+'</option>');
			  
			    $('#subFieldLayer').show();
			    
			}
		});
	}
	else
	{
		$('#subFieldLayer').hide();
	}
}	


//---Target Industry---
function deleteTargetIndustry(idResume, idIndustry)
{
	$.ajax(
	{
		type: "GET",
 		url: '/TargetIndustryServ',
 		data: {'idResume':idResume,'service':'delete','idIndustry':idIndustry},
 		async:false,
 		success: function(data)
 		{
 			var obj = jQuery.parseJSON(data);
 			if(obj.success==1)
			{
				getTargetIndustry();
			}
 		}
	});
}

function getTargetIndustry()
{
	$.ajax(
	{
		type: "GET",
 		url: '/TargetIndustryServ',
 		data: {'service':'view','idResume':idResume},
 		async:false,
 		success: function(data)
 		{
 			var html='';
 			var obj = jQuery.parseJSON(data);
 			if(obj.success==1)
			{
				if(obj.industries.length>0)
				{
					html='<ol>';
					for(var i=0; i<obj.industries.length; i++)
					{
						html+='<li class="answer">'+obj.names[i]+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteTargetIndustry('+idResume+','+obj.industries[i].idIndustry+');">'+globalDelete+'</a></li>';
					}
					html+='</ol>';
					$('#industryList').html(html);
					$('#partIndustry').val('1');
				}
				else
				{
					$('#industryList').html(html);
					$('#partIndustry').val('');
				}
			}
			if(obj.industries.length>=3)
			{
				$('#addIndustryLayer').hide();
			}
			else
			{
				$('#addIndustryLayer').show();
			}
 		}
	});
}

function getCity(idRegion ,idState)
{
	if(idState>0 && idRegion != 11)
	{
		$.ajax(
		{
			url:'/AjaxServ?service=City&idCountry='+idCountry+'&idState='+idState+'&idLanguage='+idLanguage,
			success:function(result)
			{
				var cities = jQuery.parseJSON(result);
				if(cities.length>0)
				{
					$('#idCity option').remove();
					$('#idCity').append("<option value='0'>"+globalAll+"</option>");
					for (var i=0; i<cities.length; i++) 
					{
						if(targetCity.indexOf(cities[i].idCity)==-1)
						{
							$('#idCity').append("<option value='"+cities[i].idCity+"'>"+cities[i].cityName+"</option>");
						}
						else
						{
							$('#idCity').append("<option value='"+cities[i].idCity+"' disabled>"+cities[i].cityName+"</option>");
						}
	   		 		}
					$('#otherCity').val('');
					$('#cityLayer').show();
					$('#otherCityLayer').hide();
					$('#otherCity').val("");
				}
				else
				{
					$('#idCity').val('-1');
					$('#cityLayer').hide();
					$('#otherCityLayer').show();
					//$('#otherCity').val(globalAll);
				}
			}
		});
	}
	else
	{
		$('#idCity').val('');
		$('#cityLayer').hide();
		$('#otherCityLayer').hide();
	}
}

//Outside Location
function getStates(idCountry)
{
	if(idCountry>0)
	{
		$.ajax(
		{
			url:'/AjaxServ?service=State&idCountry='+idCountry+'&idLanguage='+idLanguage,
			success:function(result)
			{
				var states = jQuery.parseJSON(result);
				if(states.length>0)
				{
					$('#idStateOutside option').remove();
					$('#idStateOutside').append("<option value='0'>"+globalAll+"</option>");
					for (var i=0; i<states.length; i++) 
					{
						$('#idStateOutside').append("<option value='"+states[i].idState+"'>"+states[i].stateName+"</option>");
	   		 		}	
					$('#otherStateOutside').val('');
					$('#stateOutsideLayer').show();
					$('#otherStateOutsideLayer').hide();
				}
				else
				{
					$('#idStateOutside').val('-1');
					$('#stateOutsideLayer').hide();
					$('#otherStateOutsideLayer').show();
				}
			}
		});
	}
	else
	{
		$('#idStateOutside').val('-1');
		$('#stateOutsideLayer').hide();
		$('#otherStateOutsideLayer').show();
	}
}

function clearLocationPopup()
{
	$('.idRegionLayer #idRegion').empty();
	$('.idStateLayer #idState').empty();
	$('#cityLayer #idCity').empty();
	$('#otherCityLayer #otherCity').val("");
	$('.idStateLayer').hide();
	$('#cityLayer').hide();
	$('#otherCityLayer').hide();
	
	getRegionList();
}



function getTargetInsideLocation()
{
	$.ajax(
	{
		type: "GET",
 		url: '/TargetLocationServ',
 		data: {'service':'view','idResume':idResume},
 		async:false,
 		success: function(data)
 		{
 			var obj = jQuery.parseJSON(data);
 			if(obj.success==1)
			{
 				targetRegion = [] ;
				targetState = [] ;
				targetCity = [] ;
 				if(obj.targetLocation.length>0)
 				{
 					var html='<ol>';
 					for(var i=0; i<obj.targetLocation.length; i++)
					{
 						var location = obj.targetLocation[i];
 						html+='<li class="answer">'+location.stateName;
						html+='&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteTargetInsideLocation('+idResume+','+location.type+','+location.idRegion+','+location.idLocation+');">'+globalDelete+'</a></li>';
						
						if(location.idRegion>0 && location.type==1)
						{
							targetRegion.push(location.idRegion);
						}
						else if(location.idState>0 && location.type == 2 && location.idState!=77)
						{
							targetState.push(location.idState);
						}
						else if(location.idState == 77 && location.type == 2)
						{
							if(location.idCity>0)
							{
								targetCity.push(location.idCity)
							}
							else
							{
								targetState.push(location.idState);
							}
						}
						else if(location.type==3 && location.idRegion==11 && location.idLocation==0)
						{
							targetRegion.push(location.idRegion);
						}
						else if(location.type==3 && location.idRegion==11 && location.idLocation>0)
						{
							targetState.push(location.idLocation);
						}
							
							
					}
					$('#locationList').html(html);
					$('#partInsideLocation').val('1');
					clearLocationPopup();
 				}
 				else
 				{
 					$('#locationList').html('');
 					$('#partInsideLocation').val('');
 				}
			}
			if(obj.targetLocation.length>=20 || targetRegion.length==6)
			{
				$('#addLocationLayer').hide();
			}
			else
			{
				$('#addLocationLayer').show();
			}
 		}
	});	
}


function getTargetOutsideLocation()
{
	$.ajax(
	{
		type: "GET",
 		url: '/TargetOutsideLocationServ',
 		data: {'service':'view','idResume':idResume},
 		async:false,
 		success: function(data)
 		{
 			var obj = jQuery.parseJSON(data);
 			if(obj.success==1)
			{
 				if(obj.locations)
 				{
	 				if(obj.locations.length>0)
	 				{
						var html='<ol>';
						for(var i=0; i<obj.locations.length; i++)
						{
							html+='<li class="answer">'+obj.countryNames[i];
							if(obj.locations[i].idState==0)
							{
								html+='&nbsp;&nbsp;'+globalAll;
							}
							else
							{
								html+='&nbsp;&nbsp;'+obj.stateNames[i];
							}
							html+='&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteTargetOutsideLocation('+idResume+','+obj.locations[i].idLocation+');">'+globalDelete+'</a></li>';
						}
						html+='</ol>';
						$('#outsideLocationList').html(html);
						$('#outsideLocationLayer').show();
						$('#outsideLayer').hide();
						$('#partOutsideLocation').val('1');
						$('#partWillWorkOutside').val('1');
	 				}
	 				else
	 				{
	 					if($('input[name="outside"]').val()==1)
	 					{
	 						$('#partOutsideLocation').val('');
	 					}
	 					$('#outsideLocationList').html('');
	 					$('#outsideLayer').show();
	 					$("input[name=outside][value=0]").prop('checked', true);
	 					$('#partOutsideLocation').removeClass('required');
	 					$('#partWillWorkOutside').removeClass('required');
	 					$('#outsideLocationLayer').hide();
	 				}
				
					if(obj.locations.length>=20)
					{
						$('#addOutsideLocationLayer').hide();
					}
					else
					{
						$('#addOutsideLocationLayer').show();
					}
 				}
			}
 		}
	});	
	
}

function deleteTargetInsideLocation(idResume,type,idRegion,idLocation)
{
	$.ajax(
	{
		type: "GET",
 		url: '/TargetLocationServ',
 		data: {'idResume':idResume,'service':'delete','idLocation':idLocation,'idRegion':idRegion,'type':type},
 		async:false,
 		success: function(data)
 		{
 			var obj = jQuery.parseJSON(data);
 			if(obj.success==1)
			{
				getTargetInsideLocation();
				clearLocationPopup();
			}
 		}
	});
}


function deleteTargetOutsideLocation(idResume, idLocation)
{
	$.ajax(
	{
		type: "GET",
 		url: '/TargetLocationServ',
 		data: {'idResume':idResume,'service':'delete','idLocation':idLocation,'type':2},
 		async:false,
 		success: function(data)
 		{
 			var obj = jQuery.parseJSON(data);
 			if(obj.success==1)
			{
				getTargetOutsideLocation();
			}
 		}
	});
}

//Target Job Extension
function getTargetJobExtension()
{
	$.ajax(
	{
		type: "GET",
 		url: '/TargetJobServ',
 		data: {'service':'view','idResume':idResume},
 		async:false,
 		success: function(data)
 		{
 			var obj = jQuery.parseJSON(data);
 			if(obj.success==1 && obj.targetJobs)
 			{
 				//set Work Permit
 				if(obj.targetJobs.workPermit && obj.targetJobs.workPermit!='')
 				{
 					$('input[name=workPermit][value="'+obj.targetJobs.workPermit+'"]').prop("checked", true);
 					$('#partWorkPermit').val('1');
 				}
 				else
 				{
 					$('#partWorkPermit').val('');
 				}
 				
 				//set Relocate
 				if(obj.targetJobs.relocate && obj.targetJobs.relocate!='')
 				{
 					$('input[name=relocate][value="'+obj.targetJobs.relocate+'"]').prop("checked", true);
 					$('#partRelocate').val('1');
 				}
 				else
 				{
 					$('#partRelocate').val('');
 				}
 				
 				//set Travel
 				if(obj.targetJobs.travel && obj.targetJobs.travel>0)
 				{
 					$('input[name=travel][value="'+obj.targetJobs.travel+'"]').prop("checked", true);
 					$('#partTravel').val('1');
 				}
 				else
 				{
 					$('#partTravel').val('');
 				}
 				
 				//set salary
 				if(obj.targetJobs.minExpectedSalary && obj.targetJobs.maxExpectedSalary)
 				{
 					if(obj.targetJobs.minExpectedSalary==-1 && obj.targetJobs.maxExpectedSalary==-1)
 					{
 						$('input[name=salaryChoice][value="0"]').prop("checked", true);
 						
 					}
	 				else if(obj.targetJobs.minExpectedSalary>0 && obj.targetJobs.maxExpectedSalary>0)
	 				{
						$('input[name=salaryChoice][value="1"]').prop("checked", true);
						$('#minSalary').val(obj.targetJobs.minExpectedSalary);
						$('#maxSalary').val(obj.targetJobs.maxExpectedSalary);
						$('select[name=salaryPer] option[value="'+obj.targetJobs.expectedSalaryPer+'"]').prop("selected", true);
						$('select[name=currency] option[value="'+obj.targetJobs.salaryCurrency+'"]').prop("selected", true);
	 				}
 					$('#partSalary').val('1');
 				}
 				else
 				{
 					$('#partSalary').val('');
 				}
 				//set Job Notice
 				if(obj.targetJobs.startJob!=null)
 				{
 					$('input[name="noticeStatus"][value="1"]').prop("checked",true);
 					$('#partNotice').val('1');
 				}
 				else if(obj.targetJobs.startJobNotice>0)
 				{
 					$('select[name="notice"] option[value="'+obj.targetJobs.startJobNotice+'"]').prop("selected",true); 
 					$('input[name="noticeStatus"][value="0"]').prop("checked",true); 	
 					$('#partNotice').val('1');
 				}
 				else
 				{
 					$('#partNotice').val('');
 				}
			}
 		}
 	});	
}

//JobUpdate
function getJobUpdate()
{
	$.ajax(
	{
		type: "GET",
 		url: '/TargetJobUpdateServ',
 		data: {'service':'view','idResume':idResume},
 		async:false,
 		success: function(data)
 		{
 			var obj = jQuery.parseJSON(data);
 			if(obj.status)
 			{
 				if(obj.status=='NO')
 				{
 					$('input[name=jobUpdate][value="0"]').prop("checked", true);
 	 				$('#partJobUpdate').val('1');
 				}
 				else if(obj.status!='')
 				{
 					$('input[name=jobUpdate][value="1"]').prop("checked", true);
 					$('select[name="jobUpdateDay"] option[value="'+obj.status+'"]').prop("selected", true);
	 				$('#partJobUpdate').val('1');
	 			}
 				else
 				{
 	 				$('#partJobUpdate').val(''); 					
 				}
 			}
 			else
 			{
 				$('#partJobUpdate').val('');
 			}
 			
 		}
 	});	
}

function clearAllCity(){
	if($('#otherCity').val()==(""+globalAll)){
		$('#otherCity').val("");
	}
}

function getRegionList()
{
	$.ajax(
	{
		type: "GET",
		url: '/TargetLocationServ',
		data: {'service':'getRegionList','idResume':idResume},
		async:false,
		success: function(data)
		{
			var obj = jQuery.parseJSON(data);
			if(obj.success==1)
			{
				var regions = obj.regions;
				if(regions.length>0)
				{
					$('.idRegionLayer #idRegion').empty();
					$('.idRegionLayer #idRegion').append("<option value='-1'>"+globalSelect+"</option>");
					for(var i=0;i<regions.length;i++)
					{
						if(targetRegion.indexOf(regions[i].idRegion)==-1)
						{
							$('.idRegionLayer #idRegion').append("<option value='"+regions[i].idRegion+"'>"+regions[i].name+"</option>")
						}
						else
						{
							$('.idRegionLayer #idRegion').append("<option value='"+regions[i].idRegion+"' disabled>"+regions[i].name+"</option>")
						}
					}
				}
			}
		}
	});
}

function getStateByRegion(idRegion)
{
	if(idRegion > 0)
	{
		if(idRegion == 11)
		{
			$('.idStateLayer #stateTopic').text(industrialStr);
		}
		else
		{
			$('.idStateLayer #stateTopic').text(provinceStr);
		}
		$.ajax(
		{
			type: "GET",
			url: '/TargetLocationServ',
			data: {'service':'getStateByRegion','idResume':idResume,'idRegion':idRegion},
			async:false,
			success: function(data)
			{
				var obj = jQuery.parseJSON(data);
				if(obj.success==1)
				{
					var states = obj.states;
					if(states.length>0)
					{
						$('.idStateLayer #idState').empty();
						$('.idStateLayer #idState').append("<option value='0'>"+globalAll+"</option>");
						for(var i=0;i<states.length;i++)
						{
							if(targetState.indexOf(states[i].idState)==-1)
							{
								$('.idStateLayer #idState').append("<option value='"+states[i].idState+"'>"+states[i].stateName+"</option>")
							}
							else
							{
								$('.idStateLayer #idState').append("<option value='"+states[i].idState+"' disabled>"+states[i].stateName+"</option>")
							}
						}
						$('.idStateLayer').show();
					}
				}
			}
		});
	}
	else
	{
		$('.idStateLayer #idState').empty();
		$('.idStateLayer').hide();
		$('#cityLayer').hide();
		$('#otherCityLayer').hide();
	}
}

//-------------Caller Part-----------------

$(document).ready(function()
{
	var container = $('div.errorContainer');		

	$('#loc').on('hidden.bs.modal', function () {
		clearLocationPopup();
	})
	//----JobType----	
	getJobTypesForPc();
	$('input[name=idJobTypePc]').change(function()
	{
		if($('input[name=idJobTypePc]:checked').length==0)
		{
			$('#partJobType').val('');
		}
		$('#jobTypePcFrm').submit();
	});
	
	$('#jobTypePcFrm').validate(
	{
		errorContainer: container,
		errorLabelContainer: $("ol", container),
		wrapper: 'li',
		focusInvalid: false,
		invalidHandler: function(form, validator) 
		{
			$('html, body').animate({scrollTop: '0px'}, 300);      
		},
		rules:
		{
			idJobTypePc:
			{
				required:true
			}
		},			  
		messages: 
		{
			idJobTypePc:
			{
				required:globalJobtype
			}		  			
		},
		submitHandler:function(form)
		{
			$.ajax(
			{
				type: "POST",
	   			url: '/TargetJobTypeServ',
	   			data: $('#jobTypePcFrm').serialize(),
	   			async:false,
	   			success: function(data)
	   			{
	   				var obj = jQuery.parseJSON(data);
	   				if(obj.success==1)
	   				{
	   					getJobTypesForPc();
	   				}
	       			else
	       			{
	       				$('div.errorContainer li').remove();
	       				for(var i=0; i<obj.errors.length; i++)
	       				{
	       					container.append('<li><label class="error">'+obj.errors[i]+'</label></li>');
	       				}
	       				container.css({'display':'block'});
	       				$('html, body').animate({scrollTop: '0px'}, 300); 
	       			}
	   			}
	 		});
	 		return false;
		}
	});
	
	
	getJobTypes();
	$('#idJobType').change(function()
	{
		$('#jobTypeFrm').submit();
	});

	$('#jobTypeFrm').validate(
	{
		errorContainer: container,
		errorLabelContainer: $("ol", container),
		wrapper: 'li',
		focusInvalid: false,
		invalidHandler: function(form, validator) 
		{
			$('html, body').animate({scrollTop: '0px'}, 300);      
		},
		rules:
		{
			idJobType:
			{
				required:true
			}
		},			  
		messages: 
		{
			idJobType:
			{
				required:globalRequire
			}		  			
		},
		submitHandler:function(form)
		{
			$.ajax(
			{
				type: "POST",
	   			url: '/TargetJobTypeServ',
	   			data: $('#jobTypeFrm').serialize(),
	   			async:false,
	   			success: function(data)
	   			{
	   				var obj = jQuery.parseJSON(data);
	   				if(obj.success==1)
	   				{
	  					getJobTypes();
	  					getJobTypesForPc();
	   				}
	       			else
	       			{
	       				$('div.errorContainer li').remove();
	       				for(var i=0; i<obj.errors.length; i++)
	       				{
	       					container.append('<li><label class="error">'+obj.errors[i]+'</label></li>');
	       				}
	       				container.css({'display':'block'});
	       				$('html, body').animate({scrollTop: '0px'}, 300); 
	       			}
	   			}
	 		});
	 		return false;
		}
	});


	//----Target JobField----
	getTargetJobField();
	$('#idJobField').change(function() 
	{
		if($('#idJobField').val()==-1)
		{
			$('#otherJobFieldLayer').show();
			$('#idSubField option').remove();
			$('#idSubField').append('<option value="-1" selected>'+globalOther+'</option>');
			$('#otherSubFieldLayer').show();
			$('#subFieldLayer').show();
			$("#otherJobField").rules("add", 
			{ 
				required: true,
				messages:
				{
					required:globalRequire
				}
			});
			
			$("#otherSubField").rules("add", 
			{ 
				required: true,
				messages:
				{
					required:globalRequire
				}
			});
		}
		else
		{
			$('#otherJobField').rules('remove');
			$('#otherSubField').rules('remove');
			$('#otherJobFieldLayer').hide();
			$('#otherSubFieldLayer').hide();
			getSubField($(this).val());
		}
		$('#idJobField').hide();
		$('#idJobField').fadeIn(100);
	});
	
	$('#idSubField').change(function()
	{
		if($('#idSubField').val()==-1)
		{
			$("#otherSubField").rules("add", 
			{ 
				required: true,
				messages:
				{
					required:globalRequire
				}
			});
			$('#otherSubFieldLayer').show();
		}
		else
		{
			$('#otherSubField').rules('remove');
			$('#otherSubFieldLayer').hide();
		}
		$('#idSubField').hide();
		$('#idSubField').fadeIn(100);
	});		
	
	$('#jfLink').click(function()
	{
		$('#idJobField option[value=""]').prop('selected',true);
		$('#idSubField option[value=""]').remove();
		$('#subFieldLayer').hide();
		$('#otherJobField').val('');
		$('#otherSubField').val('');
		$('#otherJobFieldLayer').hide();
		$('#otherSubFieldLayer').hide();
	});

	$('#jobFieldFrm').validate(
	{
		focusInvalid: false,
		rules:
		{
			idJobField:
			{
				required:true
			},
		 	idSubField:
		 	{
		  		required:true
		  	}
		},			  
		messages: 
		{
			idJobField:
			{
				required:globalRequire
			},
			idSubField:
			{
				required:globalRequire
		  	}		  			
		},
		submitHandler:function(form)
		{
			$.ajax(
			{
				type: "POST",
	   			url: '/TargetJobFieldServ',
	   			data: $('#jobFieldFrm').serialize(),
	   			async:false,
	   			success: function(data)
	   			{
	   				var obj = jQuery.parseJSON(data);
	   				if(obj.success==1)
	   				{
	   					getTargetJobField();
	   					$('#jf').modal('hide');
	   				}
	   			}
	 		});
	 		return false ;
		}
	});
	
	
	//Target Industry
	getTargetIndustry();
	$('#indLink').click(function()
	{
		$('#idIndustry option[value=""]').prop('selected',true);
		$('#otherIndustry').val('');
		$('#otherIndustryLayer').hide();
	});	
	
	$('#idIndustry').change(function() 
	{
		if($('#idIndustry').val()==-1)
		{
			$('#otherIndustryLayer').show();
			$('#otherIndustry').val('');
			$('#otherIndustry').rules('add', 
			{ 
				required: true,
				messages:
				{
					required:globalRequire
				}
			});
		}
		else
		{
			$('#otherIndustry').rules('remove');
			$('#otherIndustryLayer').hide();
			
		}
		$('#idIndustry').hide();
		$('#idIndustry').fadeIn(100);
	});	
	
	$('#industryFrm').validate(
	{
		focusInvalid: false,
		rules:
		{
			idIndustry:
			{
				required:true
			}
		},			  
		messages: 
		{
			idIndustry:
			{
				required:globalRequire
			}		  			
		},
		submitHandler:function(form)
		{
			$.ajax(
			{
				type: "POST",
	   			url: '/TargetIndustryServ',
	   			data: $('#industryFrm').serialize(),
	   			async:false,
	   			success: function(data)
	   			{
	   				var obj = jQuery.parseJSON(data);
	   				if(obj.success==1)
	   				{
	   					getTargetIndustry();
	   					$('#Indus').modal('hide');
	   				}
	   			}
	 		});
	 		return false ;
		}		
	});
	
	//---Target Location--
	getRegionList();
	getTargetInsideLocation();
	$('#locLink').click(function()
	{
		/*$('#idState option[value=""]').prop('selected',true);
		$('#otherCity').val('');
		$('#idCity').val('-1');
		$('#cityLayer').hide();
		$('#otherCityLayer').show();
		*/
		$('#idStateLayer').hide();
		$('#cityLayer').hide();
		$('#otherCityLayer').hide();
	});
	
	$('#idRegion').change(function(){
		var idRegion = $(this).val();
		getStateByRegion(idRegion);
	});
	
	$('#idState').change(function()
	{
		$('#idState').hide();
		$('#idState').fadeIn(100);
		getCity($('#idRegion').val(),$(this).val());
	});
	
	$('#locationFrm').validate(
	{
		focusInvalid: false,
		rules:
		{
			idState:
			{
				required:true
			}
		},			  
		messages: 
		{
			idState:
			{
				required:globalRequire
			}		  			
		},
		submitHandler:function(form)
		{
			$.ajax(
			{
				type: "POST",
	   			url: '/TargetLocationServ',
	   			data: $('#locationFrm').serialize(),
	   			async:false,
	   			success: function(data)
	   			{
	   				var obj = jQuery.parseJSON(data);
	   				if(obj.success==1)
	   				{
	   					getTargetInsideLocation();
	   					$('#loc').modal('hide');
	   				}
	   			}
	 		});
	 		return false ;
		}		
	});	
	
	//---TargetJob Extension---
	getTargetJobExtension();
	
	//work permit
	$('input[name=workPermit]').click(function()
	{
		$.ajax(
		{
			type: "POST",
   			url: '/TargetJobServ',
   			data: {'service':'setWorkPermit','idResume':idResume,'workPermit':$(this).val()},
   			async:false,
   			success: function(data)
   			{
   				var obj = jQuery.parseJSON(data);
   				if(obj.success==1)
   				{
   					$('#partWorkPermit').val('1');
   				}
   				else
   				{
   					$('#partWorkPermit').val('');
   				}
   			}
 		});
	});
	
	$('input[name="salaryChoice"]').change(function()
	{
		if($(this).val()==1)
		{
			$('#minSalary').rules('add', 
			{ 
				required: true,
				digits:true,
				messages:
				{
					required:globalSalaryMin,
					digits:globalSalaryNumMin 
				}
			});		
			
			$('#maxSalary').rules('add', 
			{ 
				required: true,
				digits:true,
				messages:
				{
					required:globalSalaryMax,
					digits:globalSalaryNumMax 
				}
			});	
		}
		else
		{
			$('#minSalary').val('');
			$('#maxSalary').val('');
			$('#minSalary').rules('remove');
			$('#maxSalary').rules('remove');
		}
		if($(this).val()==1 && $('#minSalary').val()=='' &&  $('#maxSalary').val()=='')
		{
			$('#partSalary').val('');
		}
		$('#salaryFrm').submit();
	});
	
	$('#minSalary').blur(function() 
	{
		$('input[name=salaryChoice][value="1"]').prop("checked", true);
		if($('input[name="salaryChoice"]').val()==1)
		{
			$('#minSalary').rules('add', 
			{ 
				required: true,
				digits:true,
				messages:
				{
					required:globalSalaryMin,
					digits:globalSalaryNumMin 
				}
			});		
			$('#maxSalary').rules('add', 
			{ 
				required: true,
				digits:true,
				messages:
				{
					required:globalSalaryMax,
					digits:globalSalaryNumMax 
				}
			});		
		}
		else
		{
			$('#maxSalary').rules('remove');
		}
		if($('input[name="salaryChoice"]').val()==1 && $('#minSalary').val()=='' &&  $('#maxSalary').val()=='')
		{
			$('#partSalary').val('');
		}
		$('#salaryFrm').submit();
	});

	$('#maxSalary').blur(function() 
	{
		$('input[name=salaryChoice][value="1"]').prop("checked", true);
		$('#minSalary').rules('add', 
		{ 
			required: true,
			digits:true,
			messages:
			{
				required:globalSalaryMin,
				digits:globalSalaryNumMin 
			}
		});		
		
		$('#maxSalary').rules('add', 
		{ 
			required: true,
			digits:true,
			messages:
			{
				required:globalSalaryMax,
				digits:globalSalaryNumMax 
			}
		});			
		if($('input[name="salaryChoice"]').val()==1 && $('#minSalary').val()=='' &&  $('#maxSalary').val()=='')
		{
			$('#partSalary').val('');
		}
		$('#salaryFrm').submit();
	});
	
	$('#salaryFrm select').bind("change", function() 
	{
		$('#salaryFrm').submit();
	});	
	
	$('#salaryFrm').validate(
	{
		ignore: [],
		errorContainer: container,
		errorLabelContainer: $("ol", container),
		wrapper: 'li',
		focusInvalid: false,
		invalidHandler: function(form, validator) 
		{
			//$('html, body').animate({scrollTop: '0px'}, 300);      
		},
		submitHandler:function(form)
		{
			$.ajax(
			{
				type: "POST",
	   			url: '/TargetJobServ',
	   			data: $('#salaryFrm').serialize(),
	   			async:true,
	   			success: function(data)
	   			{
	   				var obj = jQuery.parseJSON(data);
	   				if(obj.success==1)
	   				{
	   					$('#partSalary').val('1');
	   				}
	   				else
	   				{
	   					$('#partSalary').val('');
	   				}
	   			}
	 		});
			$('#salaryFrm').hide();
			$('#salaryFrm').fadeIn(100);
	 		return false ;
		}		
	});	

	//relocate
	$('input[name=relocate]').click(function()
	{
		$.ajax(
		{
			type: "POST",
   			url: '/TargetJobServ',
   			data: {'service':'setRelocate','idResume':idResume,'relocate':$(this).val()},
   			async:false,
   			success: function(data)
   			{
   				var obj = jQuery.parseJSON(data);
   				if(obj.success==1)
   				{
   					$('#partRelocate').val('1');
   				}
   				else
   				{
   					$('#partRelocate').val('');
   				}
   			}
 		});
	});	
	
	//travel
	$('input[name=travel]').click(function()
	{
		$.ajax(
		{
			type: "POST",
   			url: '/TargetJobServ',
   			data: {'service':'setTravel','idResume':idResume,'travel':$(this).val()},
   			async:false,
   			success: function(data)
   			{
   				var obj = jQuery.parseJSON(data);
   				if(obj.success==1)
   				{
   					$('#partTravel').val('1');
   				}
   				else
   				{
   					$('#partTravel').val('');
   				}
   			}
 		});
	});
	
	//Outside Location
	getTargetOutsideLocation();
	$('input[name="outside"]').click(function()
	{
		if($(this).val()==1)
		{
			$('#partOutsideLocation').addClass('required');
			$('#outsideLocationLayer').show();
		}
		else
		{
			$('#outsideLocationLayer').hide();
			$('#partOutsideLocation').removeClass('required');
		}
		$('#partWillWorkOutside').removeClass('required');
	});
	
	$('#locOutButton').click(function()
	{
		$('input[name="outsideWorkPermit"]').prop('checked',false);
		$('#idCountry option[value=""]').prop('selected',true);
		$('#otherStateOutside').val('');
		$('#idState').val('-1');
		$('#stateOutsideLayer').hide();
		$('#otherStateOutsideLayer').show();			
	});	
	
	$('#idCountry').change(function()
	{
		$('#idCountry').hide();
		$('#idCountry').fadeIn(100);
		getStates($(this).val());
	});	
	
	$('#outsideLocationFrm').validate(
	{
		focusInvalid: false,
		rules:
		{
			idCountry:
			{
				required:true
			},
			outsideWorkPermit:
			{
				required:true
			}
		},			  
		messages: 
		{
			idCountry:
			{
				required:globalRequire
			},
			outsideWorkPermit:
			{
				required:globalRequire
			}
		},
		submitHandler:function(form)
		{
			$.ajax(
			{
				type: "POST",
	   			url: '/TargetOutsideLocationServ',
	   			data: $('#outsideLocationFrm').serialize(),
	   			async:false,
	   			success: function(data)
	   			{
	   				var obj = jQuery.parseJSON(data);
	   				if(obj.success==1)
	   				{
	   					getTargetOutsideLocation();
	   					$('#locOut').modal('hide');
	   				}
	   			}
	 		});
	 		return false ;
		}		
	});	
	
	
	//Job Update
	if(idResume==0)
	{
		getJobUpdate();
		$('#jobUpdateDay').change(function()
		{
			$('input[name=jobUpdate][value="1"]').prop("checked", true);
		});
		
		$('.juFrm').change(function()
		{
			if($('input[name=jobUpdate]:checked', '#jobUpdateFrm').val()==1)
			{
				$('#jobUpdateDay').rules('add', 
				{ 
					required: true,
					messages:
					{
						required:globalRequire
					}
				});	
				if($('#jobUpdateDay').val()=='')
				{
					$('select[name="jobUpdateDay"] option[value="ALL"]').prop("selected", true);
					$('#jobUpdateDay').focus();
				}
			}
			else
			{
				$('#jobUpdateDay').rules('remove');	
				$('#jobUpdateDay').removeClass('error');
				$('select[name="jobUpdateDay"] option[value=""]').prop("selected", true);
			}		
			$('#jobUpdateFrm').submit();
		});	
		
		$('#jobUpdateFrm').validate(
		{
			focusInvalid: false,
			errorPlacement: function(error,element) 
			{
			    return true;
			},
			submitHandler:function(form)
			{
				$.ajax(
				{
					type: "POST",
		   			url: '/TargetJobUpdateServ',
		   			data: $('#jobUpdateFrm').serialize(),
		   			async:true,
		   			success: function(data)
		   			{
		   				var obj = jQuery.parseJSON(data);
		   				if(obj.success==1)
		   				{
		   					$('#partJobUpdate').val('1');
		   				}
		   				else
		   				{
		   					$('#partJobUpdate').val('');
		   				}
		   			}
		 		});
		 		return false ;
			}		
		});	
	}
	 
	//Notice
	$('.startNotice').change(function()
	{
		$('#noticeFrm').hide();
		$('#noticeFrm').fadeIn(100);
		$('input[name="noticeStatus"][value="1"]').prop('checked',true);
		$('#startMonth').addClass('required');
		$('#startDay').addClass('required');
		$('#startYear').addClass('required');
		$('#partNotice').val('');
		$('#noticeFrm').submit();
	});

	$('#notice').change(function()
	{
		$('input[name="noticeStatus"][value="0"]').prop('checked',true);
		$('#startMonth').val('');
		$('#startDay').val('');
		$('#startYear').val('');
		$('#startMonth').removeClass('required');
		$('#startDay').removeClass('required');
		$('#startYear').removeClass('required');
		$('#partNotice').val('');
		$('#noticeFrm').submit();
	});
				
	$('input[name="noticeStatus"]').click(function()
	{
		if($(this).val()==1)
		{
			$('#notice option[value=""]').prop('selected',true);
			$('#notice').rules('remove'); 
			$('#startMonth').addClass('required');	
			$('#startDay').addClass('required');	
			$('#startYear').addClass('required');	
		}
		else
		{
			$('#startDay').val('');
			$('#startMonth').val('');
			$('#startYear').val('');
			$('#startDay').removeClass('required');	
			$('#startMonth').removeClass('required');	
			$('#startYear').removeClass('required');	
			$('#notice').rules('add', 
			{ 
				required: true,
				messages:
				{
					required:globalRequire
				}
			});
			$('#notice').focus();
		}
		$('#partNotice').val('');
		$('#noticeFrm').submit();
	});	
	
	
	$('#noticeFrm').validate(
	{
		focusInvalid: false,
		errorPlacement: function(error,element) 
		{
		    return true;
		},
		submitHandler:function(form)
		{
			$('#noticeFrm').hide();
			$('#noticeFrm').fadeIn(100);
			$.ajax(
			{
				type: "POST",
	   			url: '/TargetJobServ',
	   			data: $('#noticeFrm').serialize(),
	   			async:true,
	   			success: function(data)
	   			{
	   				
	   				var obj = jQuery.parseJSON(data);
	   				if(obj.success==1)
	   				{
	   					$('#partNotice').val('1');
	   				}
	   			}
	 		});
	  		return false ;
		}		
	});		
	
	$('#mainFrm').validate(
	{
		ignore: [],
		errorContainer: container,
		errorLabelContainer: $("ol", container),
		wrapper: 'li',
		focusInvalid: false,
		invalidHandler: function(form, validator) 
		{
			$('html, body').animate({scrollTop: '0px'}, 300);      
		},		
		submitHandler:function(form)
		{
			if(backToView)
			{
				window.location.href='/SRIX?view='+backToView+'&idResume='+idResume;
				return false;
			}
			else
			{
				if(sequence==0)
				{
					$('#resumeListLayer').modal({
						show: 'true'
					});
					//window.location.href='/SRIX?view=resumeInfo&idResume='+idResume;
			 		return false;
				}
				else 
				{
					if(idResume==0)
					{
						var request = '{"idResume":"'+idResume+'","sequence":"'+sequence+'"}';
	   					$.ajax({
	   						type: "POST",
	   				  		url: '/MailRegisterServ',
	   				  		data: jQuery.parseJSON(request),
	   						async:true,
	   						success:function(data){
	   							
	   							var json = JSON.parse(data);
	   							
	   							var success = json && json.success ? json.success : 0;
	   							window.location.href = '/SRIX?view=personal&idResume='+idResume+'&sequence='+sequence+'&emailJobRegister='+success;
	   						}
	   					});
					}
					else
					{
						window.location.href='/SRIX?view=career&idResume='+idResume+'&sequence='+sequence;
					}
					return false;
				}
			}
		}		
	});
	
});