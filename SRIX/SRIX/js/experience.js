var lastExp;
var subFields=[];
$(document).ready(function()
{
	setTypeAhead();
	if(idCountry==102)
	{
		$('#currency option[value="62"]').prop('selected',true);
	}
	else
	{
		$('#currency option[value="140"]').prop('selected',true);
	}
	
	
	$('#salaryPer option[value="2"]').prop('selected',true);
	setExperience();
	if($('#idCountry').val()=='')
	{
		$('#idCountry').val(idCountry);
		getStates(idCountry);
	}
	//exp status
	if(expStatus!='')
	{
		$('input[name="expStatus"][value="'+expStatus+'"]').prop('checked',true);
		if(expStatus=='TRUE')
		{
			$('#noExpButtonLayer').hide();
			$('#experienceLayer').show();
		}
		else
		{
			$('#noExpButtonLayer').show();
			$('#experienceLayer').hide();
		}
		$('#partExpStatus').val('1');
	}
	
	$('input[name="expStatus"]').click(function()
	{
		if($('input[type=radio][name="expStatus"]:checked').val()!="")
		{
			$('#noExpButtonLayer').hide();
			$('#expStatusFrm').submit();
		}
	});
	
	$('#noExpButton').click(function()
	{
		if($('input[type=radio][name="expStatus"]:checked').val()=="FALSE")
		{
			$.ajax(
			{
				type: "GET",
				url: '/ExperienceServ',
				data: {'service':'deleteExperience','idResume':idResume},
				async:false,
				success: function(data)
				{
					var obj = jQuery.parseJSON(data);
					if(obj.success==1)
					{
						var request = '{"idResume":"'+idResume+'","sequence":"'+sequence+'"}';
						$.ajax({
							type: "POST",
							url: '/MailRegisterServ',
							data: jQuery.parseJSON(request),
							async:true,
							success:function(data){
								window.location.href='/SRIX?view=targetJob&idResume=0&sequence=1';
							}
						  });
					}
				}
			});
		}
	});
	
	var container = $('div.errorContainer');
	$('#expStatusFrm').validate(
	{
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
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight: function(element) 
		{
	    	$(element).closest('.form-group').removeClass('has-error');
		},
		submitHandler:function(form)
		{
			$('#noExpButton').prop('disabled', true);
			$.ajax(
			{
				type: "POST",
       			url: '/ExperienceServ',
       			data: $('#expStatusFrm').serialize(),
       			async:false,
       			success: function(data)
       			{
       				var obj = jQuery.parseJSON(data);
       				if(obj.success==1)
       				{
       					if($('input[type=radio][name="expStatus"]:checked').val()=='TRUE')
       					{
       						$('#noExpButtonLayer').hide();
							$('#experienceLayer').show();
       					}
       					else
       					{
       						$('#noExpButtonLayer').show();
							$('#experienceLayer').hide();
       					}
       					$('#partExpStatus').val('1');
       				}
           			else
           			{
           				$('#partExpStatus').val('');
           				$('div.container li').remove();
           				for(var i=0; i<obj.errors.length; i++)
           				{
           					container.find("ol").append('<li>'+obj.errors[i]+'</li>');
           				}
           				container.css({'display':'block'});
           				container.find("ol").css({'display':'block'});
           				$('html, body').animate({scrollTop: '0px'}, 300); 
           			}
       			}
     		});
			$('#noExpButton').prop('disabled', false);
     		return false;
		}
	});
	
	
	//totalCompany
	if(expCompany>0)
	{
		$('#expCompany [value="'+expCompany+'"]').prop("selected",true);
		$('#partExpCompany').val('1');
	}
	
	$('#expCompany').change(function()
	{
		if($(this).val()>0)
		{
			$('#expCompany').closest('.form-group').removeClass('has-error');
			$('#expCompanyFrm').submit();
		}
		else
		{
			//$('#expCompany').addClass('error');
			$('#expCompany').closest('.form-group').addClass('has-error');
			$('#partExpCompany').val('');
		}
	});

	$('#expCompanyFrm').validate(
	{
		errorContainer: container,
		errorLabelContainer: $("ol", container),
		wrapper: 'li',
		focusInvalid: false,
		highlight: function(element) 
		{	
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight: function(element) 
		{
	    	$(element).closest('.form-group').removeClass('has-error');
		},
		invalidHandler: function(form, validator) 
		{
			$('html, body').animate({scrollTop: '0px'}, 300);      
		},
		rules:
		{	
			expStatus:
			{
				required:true
			}
		},
		messages:
		{
			expStatus:
			{
				required:'<fmt:message key="EXP_WORKEXP_NUMBER_COM"/>'
			}
		},
		submitHandler:function(form)
		{
			$.ajax(
			{
				type: "POST",
       			url: '/ExperienceServ',
       			data: $('#expCompanyFrm').serialize(),
       			async:true,
       			success: function(data)
       			{
       				var obj = jQuery.parseJSON(data);
       				if(obj.success==1)
       				{
       					$('#partExpCompany').val('1');
       					$('#expCompanyLayer').hide();
       					$('#expCompanyLayer').fadeIn(100);
       				}
           			else
           			{
           				$('#partExpCompany').val('');
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
     		
     		return false;
		}
	});	
	
	
	//jobField
	getExperienceJobField();
	$('#jobFieldFrm').validate(
	{
		focusInvalid: false,
		highlight: function(element) 
		{
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight: function(element) 
		{
	    	$(element).closest('.form-group').removeClass('has-error');
		},
		rules:
		{
			idJobField:
			{
				required:true
			},
			expYear:
			{
				required:true
			},
			expMonth:
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
			expYear:
			{
				required:globalRequire
			},	
			expMonth:
			{
				required:globalRequire
			}			
		},
		submitHandler:function(form)
		{
			$.ajax(
			{
				type: "POST",
	   			url: '/ExperienceServ',
	   			data: $('#jobFieldFrm').serialize(),
	   			async:false,
	   			success: function(data)
	   			{
	   				getExperienceJobField();
	   				$('#jf').modal('hide');
	   				$('#idJobField option[value=""]').prop('selected',true);
	   				$('#expYear option[value=""]').prop('selected',true);
	   				$('#expMonth option[value=""]').prop('selected',true);
	   				$('#otherJobFieldLayer').hide();
	   			}
	 		});
	 		return false ;
		}
	});

	
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
		}
	});
	
	$('#idIndustry').change(function() 
	{
		if($('#idIndustry').val()==-1)
		{
			$('#otherIndustryLayer').show();
			$('#otherIndustry').val('');
			$('#indLinkLayer').show();
		}
		else
		{
			$('#otherIndustryLayer').hide();
			$('#indLinkLayer').hide();
		}
		if($('#idIndustry').val()>0)
		{
			var result=saveWorkIndustry();
			if(result!=false)
			{
				$('#otherIndustry').removeClass('error');
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
				$('#idIndustry').removeClass('error');
				$('#otherIndustry').val('');
				$('#otherIndustryLayer').hide();
				$('#idIndustry option[value=""]').prop('selected',true);
			}
		}
	});

	$('#idCountry').change(function()
	{
		getStates($(this).val());
	});
	
	
	$('#experienceFrm').validate(
	{
		ignore: [],
		errorContainer: container,
		errorLabelContainer: $("ol", container),
		wrapper: 'li',
		focusInvalid: false,
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
		invalidHandler: function(form, validator) 
		{
			$('html, body').animate({scrollTop: '0px'}, 300);      
		},
		rules: {
    		idIndustry:{
    			required: function(element){
    				return $('#partIndustry').val() <= 0;
    			}   
    		},
    		partJobField:{
    			required: function(element)
    			{
    				if($(element).val() <= 0)
    				{
    					$('#jobFieldList').closest('.form-group').addClass("alert alert-danger");
    				}
    				else
    				{	
    					$('#jobFieldList').closest('.form-group').removeClass("alert alert-danger");
    				}
    				return $(element).val() <= 0;
    			}
    		}
    	},
		submitHandler:function(form)
		{
			$.blockUI(
					{ 
						message:'     ',
						css: 
						{	
							top:'40%', 
					        left:'45%', 
							color:'#ffffff', 
							width:'70px',
							height:'70px',
							border: 'none', 
							padding: '0px', 
							'-webkit-border-radius': '8px', 
							'-moz-border-radius': '8px', 
							opacity: 1,
							background:'#ffffff url("/images/spinner.gif") no-repeat center center'
						}
					});
			$.ajax(
			{
				type: "POST",
	   			url: '/ExperienceServ',
	   			data: $('#experienceFrm').serialize(),
	   			async:false,
	   			success: function(data)
	   			{
	   				var obj = jQuery.parseJSON(data);
	   				if(obj.success==1)
	   				{
	  					if(sequence==0)
	   					{
       						window.location.href = "/SRIX?view=resumeInfo&idResume="+idResume;
       					}
       					else
       					{
       						window.location.href='/SRIX?view=targetJob&idResume=0&sequence=1';
       					}	
	   				}
	       			else
	       			{
	       				$('div.container li').remove();
	       				for(var i=0; i<obj.errors.length; i++)
	       				{
	       					container.append('<li><label class="error">'+obj.errors[i]+'</label></li>');
	       				}
	       				container.css({'display':'block'});
	       				$('html, body').animate({scrollTop: '0px'}, 300); 
	       			}
	   				$.unblockUI;
	   			}
	 		});
	 		return false;
		}
		
	});
	
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
			$("label[for='salaryLast']").html('<font color="red">*</font>'+globalSalaryLast);
		}
		else
		{
			$("#salaryLast").rules( "remove" );
			$("#salaryLast").removeClass("required");
			$("label[for='salaryLast']").html(globalSalaryLast);
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
	
	$(document).on('change', '.relateWorkSubField', function(){
		$('#workSubField').val($(this).val())
	})
	
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
});


function deleteIndustry(idIndustry)
{
	$.ajax(
	{
		type: "GET",
		url: '/ExperienceServ',
		data: {'service':'delIndustry','idIndustry':idIndustry,'idResume':idResume},
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

//get Experience JobField
function getExperienceJobField()
{
	$.ajax(
	{
		type: "GET",
		url: '/ExperienceServ',
		data: {'service':'viewJobFields','idResume':idResume},
		async:false,
		success: function(data)
		{
			var obj = jQuery.parseJSON(data);
			if(obj.success==1)
			{
				if(obj.jobFields.length>0)
				{
					var html='<table style="border-collapse:collapse; width:100%;">';
					for(var i=0; i<obj.jobFields.length; i++)
					{
						html+='<tr>';
						html+='<td class="answer"><b>'+(i+1)+'</b>. '+obj.jobFieldNames[i];
						html+=' (<i>'+obj.jobFields[i].sumYear+' '+globalYear+', '+obj.jobFields[i].sumMonth+' '+globalMonth+'</i>)</td>';
						html+='<td align="right" valign="top"><a href="javascript:void(0);" onclick="deleteExperienceJobField('+idResume+','+obj.jobFields[i].jobField+');">'+globalDelete+'</a></td>';
						html+='</tr>';
						html+='<tr style="height:6px;" colspan="2"><td></td></tr>';
					}
					html+='<tr>';
					html+='<td class="caption_bold" cospan="2">'+globalTotalExp+':'+obj.expYear+' '+globalYear+', '+obj.expMonth+' '+globalMonth+'</td>';
					html+='</tr>';
					html+='</table>'; 	
					$('#jobFieldList').html(html);
					$('#partJobField').val('1');
					
					$('#jobFieldList').closest('.form-group').removeClass("alert alert-danger");
					$('label[for=partJobField]').parent().remove();
					if($('.errorContainer').children('ol').children('li').length == 0)
					{
						$('.errorContainer').hide();
					}
					
				}
				else
				{
					$('#jobFieldList').html('');
					$('#partJobField').val('');
				}
			}
		}
	});
}

function callMaxlengthEvent(id){
	$('#'+id).keyup(function(){  
        var limit = parseInt($(this).attr('maxlength'));  
        var text = $(this).val(); 
        $('#countChar_'+id).html(text.length);
    });
}


function deleteExperienceJobField(idResume, idJobField)
{
	$.ajax(
	{
		type: "GET",
		url: '/ExperienceServ',
		data: {'service':'deleteJobFields','idResume':idResume,'idJobField':idJobField},
		async:false,
		success: function(data)
		{
			var obj = jQuery.parseJSON(data);
			if(obj.success==1)
			{
				getExperienceJobField();
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
						if(lastExp && lastExp.idCountry==idCountry && lastExp.idState==states[i].idState)
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
				$('#countryLayer').hide();
				$('#countryLayer').fadeIn(100);
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


function setExperience()
{
	$.ajax(
	{
		type: "GET",
		url: '/ExperienceServ',
		data: {'service':'getLatest','idResume':idResume},
		async:false,
		success: function(data)
		{
			var obj = jQuery.parseJSON(data);
			if(obj.success==1)
			{
				
				if(obj.experience)
				{
					lastExp=obj.experience;
					//setCountry
					if(lastExp.idCountry>0)
					{
						$('#idCountry option[value="'+lastExp.idCountry+'"]').prop("selected",true);
						getStates(lastExp.idCountry);
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
					if(lastExp.otherState)
					{
						$('#otherState').val(lastExp.otherState);
					}
					
					
					//set Company Name
					$('#company').val(lastExp.companyName);
					
					//set Job Field
					if(lastExp.workJobField)
					{
						$('#workJobField option[value="'+lastExp.workJobField+'"]').prop('selected',true);
						if(lastExp.workJobField>0)
						{
							$('#workJobFieldOther').removeClass('required');
							$('#workJobFieldOther').val('');
							$('#otherJobFieldLayer').hide();
						}
						else
						{
							$('#workJobFieldOther').addClass('required');
							$('#workJobFieldOther').val(lastExp.workJobFieldOth);
							$('#otherJobFieldLayer').show();
						}
					}
					
					//set Job Type
					if(lastExp.workJobType)
					{
						$('#jobType option[value="'+lastExp.workJobType+'"]').prop('selected',true);
						if(lastExp.workJobType==7)
						{
							$("label[for='salaryLast']").html(globalSalaryLast);
						}
					}
					
					//set Salary
					if(lastExp.salaryLast && lastExp.salaryLast>0)
					{
						$('#salaryLast').val(lastExp.salaryLast);
						$('#salaryPer option[value="'+lastExp.salaryPer+'"]').prop('selected',true);
						$('#currency option[value="'+lastExp.idCurrency+'"]').prop('selected',true);
					}
				}
			}
		}
	});		
	//set Industry
	var idWorkLast=1;
	if(lastExp) {idWorkLast=lastExp.id;}
	$.ajax(
	{
		type: "GET",
		url: '/ExperienceServ',
		data: {'service':'getExperienceIndustry','idResume':idResume,'idWork':idWorkLast},
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
		 			if(idIndustry>0)
		 			{
		 				$('#idIndustry option[value="'+idIndustry+'"]').prop('disabled',true);
		 			}

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
			}
		}
	});
	if(lastExp)
	{
		//set idWork
		$('#idWork').val(lastExp.id);
		//set Sub Field
		if(lastExp.workJobField>0 && lastExp.workSubField>0)
		{
			$.ajax(
			{
				type: "GET",
				url:'/AjaxServ?service=SubField&idJobField='+lastExp.workJobField+'&idLanguage='+idLanguage+'&idSubField='+lastExp.workSubField,
				success:function(result)
				{
					var subField=jQuery.parseJSON(result);
					if(subField)
					{
						$('#workSubFieldName').val(subField.subfieldName);
						$('#workSubField').val(lastExp.workSubField);
					}
					else
					{
						$('#workSubFieldName').prop('disabled',true);
					}
				}
			});
			getSubFields(lastExp.workJobField);
		}
		else
		{
			$('#workSubFieldName').val(lastExp.workSubFieldOth);
			$('#workSubField').val('-1');
		}
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
					url: '/ExperienceServ',
					data: {'service':'saveWorkIndustry','idIndustry':idIndustry,'otherIndustry':otherIndustry,'idResume':idResume,'idWorkLastest':idWorkLastest,'industryName':industryName},
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
