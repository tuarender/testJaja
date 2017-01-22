/*-------This script use in experience.jsp only------------*/
var exp;
var subFields=[];
var subFields2=[];

$(document).ready(function()
{
	setExperience();
	setTypeAhead();
	setTypeAheadWsStart();
	if($('#idCountry').val()=='')
	{
		$('#idCountry').val(idCountry);
		getStates(idCountry);
	}
	$('#indLink').click(function()
	{
		var result=saveWorkIndustry();
		if(result!=false)
		{
			$('#otherIndustry').removeClass('error');
			$('#idIndustry').removeClass('error');
			$('#otherIndustry').val('');
			$('#otherIndustryLayer').hide();
			$('#idIndustry option[value=""]').prop('selected',true);
			$('#otherIndustry').rules( "remove" );
			$('#otherIndustry').removeClass("required");
		}
	});
	
	$('#idIndustry').change(function() 
	{
		if($('#idIndustry').val()==-1)
		{
			$('#otherIndustryLayer').show();
			$('#otherIndustry').val('');
			$('#indLinkLayer').show();
			
			var countIns=$('#partIndustry').val();
			if(countIns==0)
			{
				$('#otherIndustry').rules("add", {
					required:true,
				    messages: {
				       required:globalIndustryReq
				    }
				})
			}
			else
			{
				$('#otherIndustry').removeClass('error');
				$('#otherIndustry').rules( "remove" );
				$('#otherIndustry').removeClass("required");
			}
		}
		else
		{
			$('#otherIndustryLayer').hide();
			$('#indLinkLayer').hide();
			$('#otherIndustry').removeClass('error');
			$('#otherIndustry').rules( "remove" );
			$('#otherIndustry').removeClass("required");
		}
		if($('#idIndustry').val()>0)
		{
			var result=saveWorkIndustry();
			if(result!=false)
			{
				$('#otherIndustry').removeClass('error');
				$('#otherIndustry').rules( "remove" );
				$('#otherIndustry').removeClass("required");
				$('#idIndustry').removeClass('error');
				$('#otherIndustry').val('');
				$('#otherIndustryLayer').hide();
				$('#idIndustry option[value=""]').prop('selected',true);
				$('#indLinkLayer').hide();
			}
		}
		
	});		
	
	$('#otherIndustry').change(function(){
		if($(this).val()!="")
		{
			var result=saveWorkIndustry();
			if(result!=false)
			{
				$('#otherIndustry').removeClass('error');
				$('#otherIndustry').rules( "remove" );
				$('#otherIndustry').removeClass("required");
				$('#idIndustry').removeClass('error');
				$('#otherIndustry').val('');
				$('#otherIndustryLayer').hide();
				$('#idIndustry option[value=""]').prop('selected',true);
				$('#indLinkLayer').hide();
			}
		}
	});

	$('#idCountry').change(function()
	{
		getStates($(this).val());
	});

	var container = $('div.errorContainer');
	$('#experienceFrm').validate(
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
		highlight: function(element) 
		{
			$(element).closest('.input-group-element').addClass('has-error');
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight: function(element) 
		{
			$(element).closest('.input-group-element').removeClass('has-error');
	    	$(element).closest('.form-group').removeClass('has-error');
		},
    	rules: {
    		idIndustry:{
    			required: function(element){
    				return $('#partIndustry').val() <= 0;
    			}   
    		}
    	},
		submitHandler:function(form)
		{
			$.ajax(
			{
				type: "POST",
	   			url: '/ExperienceEditServ',
	   			data: $('#experienceFrm').serialize(),
	   			async:false,
	   			success: function(data)
	   			{
	   				var obj = jQuery.parseJSON(data);
	   				if(obj.success==1)
	   				{
	   					if(backToView)
	   					{
	   						if(sequence==0)
	   						{
		   						$('#resumeListLayer').modal({
	   								show: 'true'
								}); 
	   						}
		   					else
		   					{
		   						window.location.href='/SRIX?view='+backToView+'&idResume='+idResume;
	   						}
	   					}
	   					else
	   					{
	   						if(sequence==0)
	   						{
	   							$('#resumeListLayer').modal({
       								show: 'true'
    							}); 
		   						//window.location.href='/SRIX?view=resumeInfo&idResume='+idResume;
	   						}
		   					else
		   					{
		   						window.location.href='/SRIX?view=experienceList&idResume='+idResume;
	   						}
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
	 		return false;
		}
		
	});
	
	$('#workJobField').change(function()
	{
		if($(this).val()==-1)
		{
			$('#workJobFieldOther').addClass('required');
			$('#otherJobFieldLayer').show();
			$('#workSubFieldName').prop('disabled',false);
		}
		else if($(this).val()=="")
		{
			$('#workSubFieldName').prop('disabled',true);
			$('#otherJobFieldLayer').hide();
			$('#otherJobFieldLayer').val('');
		}
		else
		{
			$('#workSubFieldName').prop('disabled',false);
			getSubFields($(this).val());
			$('#workJobFieldOther').removeClass('required');
			$('#otherJobFieldLayer').hide();
		}
		$('#relateSubfieldPosition').hide();
		$('#relateSubfieldPositionBody').html('');
		$('#workSubField').attr('disabled', false);
		$('#workSubField').val('-1');
		$('#workSubFieldName').val('');
		$('#workJobField').hide();
		$('#workJobField').fadeIn(100);
	});
	
	
	$('#workJobFieldStart').change(function()
	{
		if($(this).val()==-1)
		{
			$('#otherJobFieldStartLayer').show();
			$('#workSubFieldStartName').prop('disabled',false);
			$('#workJobFieldStart').addClass('required');
		}
		else if($(this).val()=="")
		{
			$('#workSubFieldStartName').prop('disabled',true);
			$('#workJobFieldStart').removeClass('required');
			$('#otherJobFieldStartLayer').hide();
			$('#otherJobFieldStartLayer').val('');
		}
		else
		{
			$('#workSubFieldStartName').prop('disabled',false);
			$('#workJobFieldStart').addClass('required');
			getSubFields2($(this).val());
			$('#otherJobFieldStartLayer').hide();
		}
		$('#relateSubfieldStartPosition').hide();
		$('#relateSubfieldStartPositionBody').html('');
		$('#workSubFieldStart').val('-1');
		$('#workSubFieldStartName').val('');
		$('#workJobFieldStart').hide();
		$('#workJobFieldStart').fadeIn(100);
	});
	
	$(document).on('change', '.relateWorkSubField', function(){
		$('#workSubField').val($(this).val())
	})
	
	$(document).on('change', '.relateWorkSubFieldStart', function(){
		$('#workSubFieldStart').val($(this).val())
	})
			
	
	$('input[name="present"]').click(function()
	{
		if($(this).val()=='FALSE')
		{
			$('#endMonth').addClass('required');
			$('#endYear').addClass('required');
		}
		else
		{
			$('#endMonth').removeClass('required');
			$('#endYear').removeClass('required');
			$('#endMonth').val('');
			$('#endYear').val('');			
		}
	});
	$('.workEnd').change(function()
	{
		$('input[name="present"][value="FALSE"]').prop('checked',true);
		$('#endMonth').addClass('required');
		$('#endYear').addClass('required');
	});
	
	if($('#currencyStart').val()=='' && idCountry==216)
	{
		$('#currencyStart').val(140);
	}
	if($('#salaryPerStart').val()=='' && idCountry==216)
	{
		$('#salaryPerStart').val(2);
	}
	
	if($('#currency').val()=='' && idCountry==216)
	{
		$('#currency').val(140);
	}
	if($('#salaryPer').val()=='' && idCountry==216)
	{
		$('#salaryPer').val(2);
	}
	
	var highlightFn = function (item) {
	        var html = $('<div></div>');
	        var query = this.query;
	        var i = item.toLowerCase().trim().indexOf(query.toLowerCase().trim());
	        var len, leftPart, middlePart, rightPart, strong;
	        len = query.length;
	        if(len === 0){
	            return html.text(item).html();
	        }
	        while (i > -1) {
	            leftPart = item.substr(0, i);
	            middlePart = item.substr(i, len);
	            rightPart = item.substr(i + len);
	            strong = $('<strong></strong>').text(middlePart);
	            html
	                .append(document.createTextNode(leftPart))
	                .append(strong);
	            item = rightPart;
	            i = item.toLowerCase().trim().indexOf(query.toLowerCase().trim());
	        }
	        return html.append(document.createTextNode(item)).html();
	  };
	
	function setTypeAhead(){
		$('#workSubFieldName').typeahead({
			minLength:3,
			items:12,
		  	source: function (query, process) {
		  		var idJobField=$('#workJobField').val();
		  		if(idJobField>0)
	  			{
		  			return $.ajax({
			        	url: '/AjaxServ?service=SubField&idJobField='+idJobField+'&idLanguage='+idLanguage,
			        	global: false,
			        	success: function( data, textStatus, jqXHR) {
			        		objects = [];
			       	 		map = {};
			       	 		if(data!=""){
				       	 		$.each(jQuery.parseJSON(data), function(i, object) {
						            map[object.subfieldName.toLowerCase()] = object;
						            objects.push(object.subfieldName);
						        });
					        }
					        $('#workSubField').val(-1);
				            return process(objects);
		                }
			        });
	  			}
	    	},
	    	matcher: function (item) {
	    		var it = this.displayText(item).trim();
	    		return ~it.toLowerCase().indexOf(this.query.toLowerCase().trim());
    	    },
    	    highlighter: highlightFn,
	    	updater: function(item) {
	    		$('#relateSubfieldPosition').hide();
				$('#relateSubfieldPositionBody').html('');
		        $('#workSubField').val(map[item.toLowerCase()].idSubfield);
		        $('#workSubFieldName').unbind('change');
		        $('#workSubField').attr('disabled', false);
		        return item;
		    },
		    whenNotMatch: function(){
		    	if($('#workSubField').val() == -1 && $('#workJobField').val() != -1)
		    	{
	    			var selectJobField = $('#workJobField').val();
	    				
					$('#relateSubfieldPosition').show();
					$('#workSubField').attr('disabled', true);
					getRelateSubfieldPosition(selectJobField);
		    	}		
		    	else
		    	{
		    		$('#workSubField').attr('disabled', false);
					$('#relateSubfieldPosition').hide();
					$('#relateSubfieldPositionBody').html('');
		    	}
		    },
		    afterBlur: function(){
		    	var selectJobField = $('#workJobField').val();
		    	var inputSubfieldName = $("#workSubFieldName").val().trim().toLowerCase();
		    	if(map[inputSubfieldName])	
		    	{
		    		$('#workSubField').val(map[inputSubfieldName].idSubfield);
		    		$('#workSubField').attr('disabled', false);
					$('#relateSubfieldPosition').hide();
					$('#relateSubfieldPositionBody').html('');
					$('#workSubFieldName').val(map[inputSubfieldName].subfieldName);
		    	}
		    	else
		    	{
		    		$('#workSubField').val('-1');
		    		$('#relateSubfieldPosition').show();
					$('#workSubField').attr('disabled', true);
					getRelateSubfieldPosition(selectJobField);
		    	}
		    }
		});
	}
	
	function setTypeAheadWsStart(){
		$('#workSubFieldStartName').typeahead({
			minLength:3,
			items:12,
		  	source: function (query, process) {
		  		var idJobField=$('#workJobFieldStart').val();
		  		if(idJobField>0)
	  			{
		  			return $.ajax({
			        	url: '/AjaxServ?service=SubField&idJobField='+idJobField+'&idLanguage='+idLanguage,
			        	global: false,
			        	success: function( data, textStatus, jqXHR) {
			        		objects = [];
			       	 		map = {};
			       	 		if(data!=""){
				       	 		$.each(jQuery.parseJSON(data), function(i, object) {
						            map[object.subfieldName.toLowerCase()] = object;
						            objects.push(object.subfieldName);
						        });
					        }
			       	 	$('#workSubFieldStart').val(-1);
			            return process(objects);
		                }
			        });
	  			}
	    	},
	    	matcher: function (item) {
	    		var it = this.displayText(item).trim();
	    		return ~it.toLowerCase().indexOf(this.query.toLowerCase().trim());
    	    },
		    updater: function(item) {
		        $('#workSubFieldStart').val(map[item.toLowerCase()].idSubfield);
		        $("#workSubFieldStartName").prop('maxLength', item.length);
		        $('#workSubFieldStartName').unbind('change');
		        return item;
		    },
    	    highlighter: highlightFn,
	    	updater: function(item) {
	    		$('#relateSubfieldStartPosition').hide();
				$('#relateSubfieldStartPositionBody').html('');
		        $('#workSubFieldStart').val(map[item.toLowerCase()].idSubfield);
		        $('#workSubFieldStartName').unbind('change');
		        $('#workSubFieldStart').attr('disabled', false);
		        return item;
		    },
		    whenNotMatch: function(){
		    	if($('#workSubFieldStart').val() == -1 && $('#workJobFieldStart').val() != -1)
		    	{
	    			var selectJobField = $('#workJobFieldStart').val();
	    				
					$('#relateSubfieldStartPosition').show();
					$('#workSubFieldStart').attr('disabled', true);
					getRelateSubfieldPosition2(selectJobField);
		    	}		
		    	else
		    	{
		    		$('#workSubFieldStart').attr('disabled', false);
					$('#relateSubfieldStartPosition').hide();
					$('#relateSubfieldStartPositionBody').html('');
		    	}
		    },
		    afterBlur: function(){
		    	var selectJobField = $('#workJobFieldStart').val();
		    	var inputSubfieldName = $("#workSubFieldStartName").val().trim().toLowerCase();
		    	if(map[inputSubfieldName])	
		    	{
		    		$('#workSubFieldStart').val(map[inputSubfieldName].idSubfield);
		    		$('#workSubFieldStart').attr('disabled', false);
					$('#relateSubfieldStartPosition').hide();
					$('#relateSubfieldStartPositionBody').html('');
					$('#workSubFieldStartName').val(map[inputSubfieldName].subfieldName);
		    	}
		    	else
		    	{
		    		$('#workSubFieldStart').val('-1');
		    		$('#relateSubfieldStartPosition').show();
					$('#workSubFieldStart').attr('disabled', true);
					getRelateSubfieldPosition2(selectJobField);
		    	}
		    }
		});
	}
	
	$('#salaryLast,#salaryStart').on('keydown', function(e){
		e = e || event;
    	var keyPress = e.which ? e.which :e.keyCode;
        var exceptKeyCode = [8,9,46,35,36,37,38,39,40];
		var numberKeyCode = [48,49,50,51,52,53,54,55,56,57,96,97,98,99,100,101,102,103,104,105];
        var oldValue = $(this).val();
        var newValue = oldValue;
        if(numberKeyCode.indexOf(keyPress) > -1)
        {
            if(e.key)
            {
        		newValue += e.key;
            }
        }
        return !isNaN(newValue) && numberKeyCode.indexOf(keyPress) > -1 || exceptKeyCode.indexOf(keyPress) > -1;
	});
	$('#jobType').change(function()
	{
		if($(this).val()!=7)
		{
			$( "#salaryLast" ).rules( "add", {
				required:true,
				digits:true,
				messages: {
					required:globalSalRequired,
					digits:globalSalNum
				}
			});
		}
		else
		{
			$("#salaryLast").rules( "remove" );
			$("#salaryLast").removeClass("required");
		}
	});
	
	function getRelateSubfieldPosition(idJobField)
	{
		$.ajax({
			type: "GET",
			url: 'AjaxServ',
			data: { 'service':'RelateSubFieldPosition', 'idJobField': idJobField, 'idLanguage': idLanguage},
			global : false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				var html = '<select name="workSubField" class="required form-control relateWorkSubField" title="'+globalEquivMarketPositionRequired+'">';
				for(i = 0 ; i < obj.length ; i++)
				{
					if(i == 0)
					{
						html += '<option value="">'+globalSelect+'</option>';
					}
					
					var subField = obj[i];
					html += '<option value="'+subField["idSubfield"]+'">'+subField["subfieldName"]+'</option>';
				}
				html += '</select>';
				$('#relateSubfieldPositionBody').html(html);
			}
		});
	}
	
	function getRelateSubfieldPosition2(idJobField)
	{
		$.ajax({
			type: "GET",
			url: 'AjaxServ',
			data: { 'service':'RelateSubFieldPosition', 'idJobField': idJobField, 'idLanguage': idLanguage},
			global: false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				var html = '<select name="workSubFieldStart" class="form-control relateWorkSubFieldStart" title="'+globalEquivMarketPositionRequired+'">';
				for(i = 0 ; i < obj.length ; i++)
				{
					if(i == 0)
					{
						html += '<option value="">'+globalSelect+'</option>';
					}
					var subField = obj[i];
					html += '<option value="'+subField["idSubfield"]+'">'+subField["subfieldName"]+'</option>';
				}
				html += '</select>';
				$('#relateSubfieldStartPositionBody').html(html);
			}
		});
	}
	
	function InitRelateSubfieldPosition(idJobField)
	{
		$.ajax({
			type: "GET",
			url: 'AjaxServ',
			data: { 'service':'RelateSubFieldPosition', 'idJobField': idJobField, 'idLanguage': idLanguage},
			global : false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				var html = '<select name="workSubField" class="required form-control relateWorkSubField" title="'+globalEquivMarketPositionRequired+'">';
				for(i = 0 ; i < obj.length ; i++)
				{
					if(i == 0)
					{
						html += '<option value="">'+globalSelect+'</option>';
					}
					
					var subField = obj[i];
					
					if(exp && exp.workSubField == subField["idSubfield"])
					{
						html += '<option value="'+subField["idSubfield"]+'" selected>'+subField["subfieldName"]+'</option>';
					}
					else
					{
						html += '<option value="'+subField["idSubfield"]+'">'+subField["subfieldName"]+'</option>';
					}
					
				}
				html += '</select>';
				$('#relateSubfieldPositionBody').html(html);
			}
		});
	}
	
	function InitRelateSubfieldStartPosition(idJobField)
	{
		$.ajax({
			type: "GET",
			url: 'AjaxServ',
			data: { 'service':'RelateSubFieldPosition', 'idJobField': idJobField, 'idLanguage': idLanguage},
			global: false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				var html = '<select name="workSubFieldStart" class="form-control relateWorkSubFieldStart" title="'+globalEquivMarketPositionRequired+'">';
				for(i = 0 ; i < obj.length ; i++)
				{
					if(i == 0)
					{
						html += '<option value="">'+globalSelect+'</option>';
					}
					var subField = obj[i];
					
					if(exp && exp.workSubFieldStart == subField["idSubfield"])
					{
						html += '<option value="'+subField["idSubfield"]+'" selected>'+subField["subfieldName"]+'</option>';
					}
					else
					{
						html += '<option value="'+subField["idSubfield"]+'">'+subField["subfieldName"]+'</option>';
					}
					
					html += '<option value="'+subField["idSubfield"]+'">'+subField["subfieldName"]+'</option>';
				}
				html += '</select>';
				$('#relateSubfieldStartPositionBody').html(html);
			}
		});
	}
	$('#workSubFieldName').on('blur', function(){

		if($(this).val().trim().length == 0)
		{
			$('#workSubField').val(-1);
		}
		if($(this).val().length < 3){
			if($('#workSubField').val() == -1)
			{
				var selectJobField = $('#workJobField').val();
				if(selectJobField != -1)
				{
					$('#relateSubfieldPosition').show();
					$('#workSubField').attr('disabled', true);
					getRelateSubfieldPosition(selectJobField);
				}
			}
		}
	});
	
	$('#workSubFieldStartName').on('blur', function(){
		if($(this).val().trim().length == 0)
		{
			$('#workSubFieldStart').val(-1);
		}
		if($(this).val().length < 3){
			if($('#workSubFieldStart').val() == -1)
			{
				var selectJobField = $('#workJobFieldStart').val();
				if(selectJobField != -1)
				{
					$('#relateSubfieldStartPosition').show();
					$('#workSubFieldStart').attr('disabled', true);
					getRelateSubfieldPosition2(selectJobField);
				}
			}
		}
	});
	
	function setExperience()
	{
		$.ajax(
		{
			type: "GET",
			url: '/ExperienceEditServ',
			data: {'service':'getExperience','idResume':idResume,'idWork':idWork},
			async:false,
			success: function(data)
			{
				var obj = jQuery.parseJSON(data);
				if(obj.success==1)
				{
					
					if(obj.experience)
					{
						exp=obj.experience;
						//setCountry
						if(exp.idCountry>0)
						{
							$('#idCountry option[value="'+exp.idCountry+'"]').prop("selected",true);
							getStates(exp.idCountry);
						}
						else if(idCountry>0)
						{
							$('#idCountry option[value="'+idCountry+'"]').prop("selected",true);
							getStates(idCountry);
						}
						else
						{
							$('#idCountry option[value="'+applyIdCountry+'"]').prop("selected",true);
							getStates(applyIdCountry);
						}	
						
						if(exp.otherState)
						{
							$('#otherState').val(exp.otherState);
						}
						
						//set Company Name
						$('#company').val(exp.companyName);
						
						//set Job Field
						if(exp.workJobField)
						{
							$('#workJobField option[value="'+exp.workJobField+'"]').prop('selected',true);
							if(exp.workJobField>0)
							{
								$('#workJobFieldOther').removeClass('required');
								$('#workJobFieldOther').val('');
								$('#otherJobFieldLayer').hide();
							}
							else
							{
								$('#workJobFieldOther').addClass('required');
								$('#workJobFieldOther').val(exp.workJobFieldOth);
								$('#otherJobFieldLayer').show();
							}
						}
						
						//set Job Field start
						if(exp.workJobFieldStart)
						{
							$('#workJobFieldStart option[value="'+exp.workJobFieldStart+'"]').prop('selected',true);
							if(exp.workJobFieldStart>0)
							{
								$('#workJobFieldStartOther').val('');
								$('#otherJobFieldStartLayer').hide();
							}
							else
							{
								$('#workJobFieldStartOther').val(exp.workJobFieldOthStart);
								$('#otherJobFieldStartLayer').show();
							}
						}
						//com business
						if(exp.comBusiness)
						{
							$('#comBusiness').val(exp.comBusiness);
					        $('#countChar_comBusiness').html($('#comBusiness').val().length);
						}
						
						//set JobDesc
						if(!exp.jobDesc)
						{
							exp.jobDesc="";
						}
						if(!exp.jobDesc2)
						{
							exp.jobDesc2="";
						}
						$('#jobDesc').val(exp.jobDesc+exp.jobDesc2);
						$('#countChar_jobDesc').html($('#jobDesc').val().length);
						//set achieve
						if(!exp.achieve)
						{
							exp.achieve="";
						}
						if(!exp.achieve2)
						{
							exp.achieve2="";
						}
						$('#achieve').val(exp.achieve+exp.achieve2);
						$('#countChar_achieve').html($('#achieve').val().length);
						//set reasonQuit
						if(exp.reasonQuit)
						{
							$('#reasonQuit').val(exp.reasonQuit);
							$('#countChar_reasonQuit').html($('#reasonQuit').val().length);
						}
						
						//company size
						if(exp.comSize)
						{
							$('#comSize option[value="'+exp.comSize+'"]').prop('selected',true);
						}
						
						//subordinate
						if(exp.subordinate && exp.subordinate>0)
						{
							$('#subordinate').val(exp.subordinate);
						}
						else
						{
							$('#subordinate').val('');
						}
						
						//set Job Type
						if(exp.workJobType)
						{
							$('#jobType option[value="'+exp.workJobType+'"]').prop('selected',true);
							if(exp.workJobType==7){
								$("#salaryLast").removeClass("required");
							}
						}
						
						//set Salary
						if(exp.salaryLast && exp.salaryLast>0)
						{
							$('#salaryLast').val(exp.salaryLast);
							$('#salaryPer option[value="'+exp.salaryPer+'"]').prop('selected',true);
							$('#currency option[value="'+exp.idCurrency+'"]').prop('selected',true);
						}
						
						//set Salary
						if(exp.salaryStart && exp.salaryStart>0)
						{
							$('#salaryStart').val(exp.salaryStart);
							$('#salaryPerStart option[value="'+exp.salaryPerStart+'"]').prop('selected',true);
							$('#currencyStart option[value="'+exp.idCurrencyStart+'"]').prop('selected',true);
						}
						
						//set startDate
						if(exp.workStart)
						{
							var startDate = Date.parse(exp.workStart);
							$('#startDateDisplay').val(startDate.toString('MM-yyyy'));
							$('#startDate').val(startDate.toString('yyyy-MM'));
						}
						
						//set endDate
						$('input[name="present"][value="'+exp.workingStatus+'"]').prop('checked',true);
						if(exp.workingStatus=='TRUE')
						{
							$('#endDate').val('');
						}
						else if(exp.workingStatus=='FALSE')
						{
							var endDate = Date.parse(exp.workEnd);
							$('#endDateDisplay').val(endDate.toString('MM-yyyy'));
							$('#endDate').val(endDate.toString('yyyy-MM'));
						}					
					}
				}
			}
		});		
		
		
		if(exp)
		{
			//set idWork
			$('#idWork').val(exp.id);
			//set Industry
			$.ajax(
			{
				type: "GET",
				url: '/ExperienceServ',
				data: {'service':'getExperienceIndustry','idResume':idResume,'idWork':exp.id},
				async:false,
				success: function(data)
				{
					var obj = jQuery.parseJSON(data);
					if(obj.success==1)
					{
						$('#industryList').empty();
						for(var i=0; i<obj.industries.length; i++)
						{
							var idIndustry=obj.industries[i].idIndustry;
							var industry=obj.names[i];
							$('#industryList').append('<li id="'+idIndustry+'">'+industry+'&nbsp;&nbsp;<a href="javascript:deleteIndustry('+idIndustry+');">'+globalDelete+'</a></li>');
						}
							
						if($('#industryList li').size()<3)
			 			{
			 				$('#addIndustryLayer').show();
			 			}
			 			else
			 			{
			 				$('#addIndustryLayer').hide();
			 			}
			 			if(idIndustry>0)
			 			{
			 				$('#idIndustry option[value="'+idIndustry+'"]').prop('disabled',true);
			 			}
			 			if($('#industryList li').size()>0)
			 			{
			 				$('#partIndustry').val(parseInt($('#industryList li').size()));
			 			}					
					}
				}
			});
			//set Sub Field
			if(exp.workJobField>0 && exp.workSubField>0)
			{
				$.ajax(
				{
					type: "GET",
					url:'/AjaxServ?service=SubField&idJobField='+exp.workJobField+'&idLanguage='+idLanguage+'&idSubField='+exp.workSubField,
					success:function(result)
					{
						var subField=jQuery.parseJSON(result);
						if(subField)
						{
							$('#workSubFieldName').val(exp.positionLast);
							$('#workSubField').val(exp.workSubField);
							$('#workSubFieldName').prop('disabled',false);
							if(subField.subfieldName != exp.positionLast)
							{
								$('#relateSubfieldPosition').show();
								InitRelateSubfieldPosition(exp.workJobField);
							}
						}
						else
						{
							$('#workSubFieldName').prop('disabled',true);
						}
					}
				});
				getSubFields(exp.workJobField);
			}
			else
			{
				$('#workSubFieldName').val(exp.workSubFieldOth);
				$('#workSubFieldName').prop('disabled',false);
				$('#workSubField').val(-1);
				if(exp.workJobField>0 &&  exp.workSubField <= 0)
				{
					$('#workSubField').prop('disabled',true);
					$('#relateSubfieldPosition').show();
					InitRelateSubfieldPosition(exp.workJobField);
				}
			}
			//set Sub Field start
			if(exp.workJobFieldStart>0 && exp.workSubFieldStart>0)
			{
				$.ajax(
				{
					type: "GET",
					url:'/AjaxServ?service=SubField&idJobField='+exp.workJobFieldStart+'&idLanguage='+idLanguage+'&idSubField='+exp.workSubFieldStart,
					success:function(result)
					{
						var subField=jQuery.parseJSON(result);
						if(subField)
						{	
							$('#workSubFieldStartName').val(exp.workSubFieldOthStart);
							//$('#workSubFieldStartName').val(subField.subfieldName);
							$('#workSubFieldStart').val(exp.workSubFieldStart);
							$('#workSubFieldStartName').prop('disabled',false);
							if(exp.workSubFieldOthStart != subField.subfieldName)
							{
								$('#relateSubfieldStartPosition').show();
								InitRelateSubfieldStartPosition(exp.workJobFieldStart);
							}
						}
						else
						{
							$('#workSubFieldStartName').prop('disabled',true);
						}
					}
				});
				getSubFields2(exp.workJobFieldStart);
			}
			else
			{
				$('#workSubFieldStartName').val(exp.workSubFieldOthStart);
				$('#workSubFieldStartName').prop('disabled',false);
				$('#workSubFieldStart').val('-1');
				if(exp.workJobFieldStart > 0 && exp.workSubFieldStart <= 0)
				{
					$('#workSubFieldStart').prop('disabled', true);
					$('#relateSubfieldStartPosition').show();
					InitRelateSubfieldStartPosition(exp.workJobFieldStart);
				}
			}
			if(!$('#workJobFieldStart').val())
			{
				$('#workSubFieldStartName').prop('disabled', true);
				$('#otherJobFieldStartLayer').hide();
			}
		}
	}
	
});
	function deleteIndustry(idIndustry)
	{
		$.ajax(
		{
			type: "GET",
			url: '/ExperienceEditServ',
			data: {'service':'delIndustry','idIndustry':idIndustry,'idResume':idResume,'idWork':idWork},
			async:false,
			success: function(data)
			{
				var obj = jQuery.parseJSON(data);
				if(obj.success==1)
				{
					$('#'+idIndustry).remove();
					if(idIndustry>0)
					{
						$('#idIndustry option[value="'+idIndustry+'"]').prop('disabled',false);
					}
					if($('#industryList li').size()<3)
					{
						$('#addIndustryLayer').show();
					}
					else
					{
						$('#addIndustryLayer').hide();
					}
	
					if($('#industryList li').size()>0)
					{
						$('#partIndustry').val($('#industryList li').size());
					}
					else
					{
						$('#partIndustry').val('');
					}
					$('#indLinkLayer').hide();
					return true;
				}
				else
				{
					return false;
				}
			}
		});
	}
	
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
						$('#stateLayer').show();
						$("#otherState").removeClass('required');
						$('#idState option[value!=""]').remove();
						for (var i=0; i<states.length; i++) 
						{
							if(exp && exp.idCountry==idCountry && exp.idState==states[i].idState)
							{
								$('#idState').append('<option value="'+states[i].idState+'" selected>'+states[i].stateName+'</option>');
							}
							else
							{
								$('#idState').append('<option value="'+states[i].idState+'">'+states[i].stateName+'</option>');
							}
		   		 		}	
						$('#otherState').val('');
						$('#otherStateLayer').hide();
						
					}
					else
					{
						$('#idState option[value!=""]').remove();
						$('#idState').append('<option value="-1" selected>'+globalOther+'</option>');
						$("#otherState").addClass('required');
						$('#otherStateLayer').show();
						$('#stateLayer').hide();
					}
					$('#idCountry').hide();
					$('#idCountry').fadeIn(100);
				}		
			});
		}
		else
		{
			$('#idState option[value!=""]').remove();
			$('#idState').append('<option value="-1" selected>'+globalOther+'</option>');
			$("#otherState").addClass('required');
			$('#otherStateLayer').show();
			$('#stateLayer').hide();
		}		
	}
	
	
	
	function getSubFields(idJobField)
	{
		subFields=[];
		if(idJobField>0)
		{
			$.ajax(
			{
				type: "GET",
				url:'/AjaxServ?service=SubField&idJobField='+idJobField+'&idLanguage='+idLanguage,
				success:function(result)
				{
					$.merge(subFields, jQuery.parseJSON(result));
					if(idLanguage==38)
					{
						$.ajax(
						{
							type: "GET",
							url:'/AjaxServ?service=SubField&idJobField='+idJobField+'&idLanguage=11',
							success:function(result)
							{
								$.merge(subFields, jQuery.parseJSON(result) );
							}
						});
					}
				}
			});
		}
	}
	
	function getSubFields2(idJobField)
	{
		subFields2=[];
		if(idJobField>0)
		{
			$.ajax(
			{
				type: "GET",
				url:'/AjaxServ?service=SubField&idJobField='+idJobField+'&idLanguage='+idLanguage,
				success:function(result)
				{
					$.merge(subFields2, jQuery.parseJSON(result));
					if(idLanguage==38)
					{
						$.ajax(
						{
							type: "GET",
							url:'/AjaxServ?service=SubField&idJobField='+idJobField+'&idLanguage=11',
							success:function(result)
							{
								$.merge(subFields2, jQuery.parseJSON(result) );
							}
						});
					}
				}
			});
		}
	}
	
	function callMaxlengthEvent(id){
		$('#'+id).keyup(function(){  
	        var limit = parseInt($(this).attr('maxlength'));  
	        var text = $(this).val(); 
	        $('#countChar_'+id).html(text.length);
	    });
	}
	
	function saveWorkIndustry()
	{
		var idIndustry=$('#idIndustry option:selected').val();
		var otherIndustry=$('#otherIndustry').val()+"";
		var countIns=$('#partIndustry').val();
		var industryName=$('#idIndustry option:selected').text();
		if(countIns<3)
		{
			if(idIndustry!="")
			{
				if(idIndustry==-1 && $('#otherIndustry').val()=="")
				{
					$('#otherIndustry').addClass('error');
					return false;
				}
				else
				{
					$.ajax(
					{
						type: "GET",
						url: '/ExperienceEditServ',
						data: {'service':'saveWorkIndustry','idIndustry':idIndustry,'otherIndustry':otherIndustry,'idResume':idResume,'idWork':idWork,'industryName':industryName},
						async:true,
						success: function(data)
						{
							var obj = jQuery.parseJSON(data);
							if(obj.success==1)
							{
								$('#industryList').append('<li id="'+obj.idIndustry+'">'+obj.industryName+'&nbsp;&nbsp;<a href="javascript:deleteIndustry('+obj.idIndustry+');">'+globalDelete+'</a></li>');
								if(obj.idIndustry>0)
					 			{
					 				$('#idIndustry option[value="'+obj.idIndustry+'"]').prop('disabled',true);
					 			}
					 			if($('#industryList li').size()>0)
					 			{
					 				$('#partIndustry').val(parseInt($('#industryList li').size()));
					 			}
					 			if($('#industryList li').size()<3)
					 			{
					 				$('#addIndustryLayer').show();
					 			}
					 			else
					 			{
					 				$('#addIndustryLayer').hide();
					 			}
								return true;
							}
							else
							{
								return false;
							}
						}
					});
				}
			}
			else 
			{
				$('#idIndustry').addClass('error');
				return false;
			}
		}
		else 
		{
			return false;
		}
	}
