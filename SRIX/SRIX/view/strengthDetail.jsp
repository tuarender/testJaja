<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.util.Util"%>
<c:import url="/config.jsp" charEncoding="UTF-8"/>
<c:if test="${not empty resume}">
	<fmt:setLocale value="${resume.locale}"/>
</c:if>
<%
	Resume resume=(Resume)request.getAttribute("resume");
	if(resume != null)
	{
		int idLanguage=resume.getIdLanguage();
		int idCountry=resume.getIdCountry();
		int sequence = Util.StrToInt(request.getParameter("sequence"));
		int idJsk=Util.getInteger(session.getAttribute("SESSION_ID_JSK"));
		int idResume = Util.StrToInt(request.getParameter("idResume"));
		String view = Util.getStr(request.getParameter("view"));
		
		request.setAttribute("view", view);
		request.setAttribute("idResume",idResume);
		request.setAttribute("idLanguage",idLanguage);
		request.setAttribute("sequence",sequence);
	}
%>
<script>
var textSave = "<fmt:message key='GLOBAL_SAVE'/>";
var textEdit = "<fmt:message key='GLOBAL_EDIT'/>";
var countCharText = "<fmt:message key='STRENGTH_MAX_CHAR'/>";
var idResume = '<c:out value="${idResume}"/>';	
var sequence = '<c:out value="${sequence}"/>';
var container = null;
var countStrength = 0;
var idLanguage= '<c:out value="${idLanguage}"/>';

$(document).ready(function()
{
	container = $('div.container');
	//---------------------------- load strength ----------------------------
	getStrengthSelected();
	
	//validation here
	$('#buttonNext').click(function() 
	{
		//container.hide();
		var haveUnSubmitedStrength = false;
		for(var i=0;i<countStrength;i++)
		{
			if(!$("#order"+i).is(":disabled")){
				haveUnSubmitedStrength = true;
				var idOrder = i;
				var detail = addslashes($('#detail'+i).val());
				var idStrength = $('#order'+i).val();
				var service = 'updateDetail';
				var request = '{"service":"'+service+'","idResume":'+idResume+',"idStrength":"'+idStrength+'","detail":"'+detail+'"}';
				$.ajax({
					type: "POST",
			 		url: '/StrengthServ',
					data: jQuery.parseJSON(request),
					async:false,
					success: function(data)
					{
						var obj = jQuery.parseJSON(data);
						if(obj.success==1)
						{
							disableStrength(idOrder);
						}
			  			else
			  			{
			  				if(obj.urlError!="")
			  				{
			  					window.location.href = obj.urlError;
			  				}
			  				else
			  				{
			  					$('div.container li').remove();
			  					for(var i=0; i<obj.errors.length; i++)
			  					{
			   						container.append('<li><label class="error">'+obj.errors[i]+'</label></li>');
			    				}
			    				container.show();
			    				$('html, body').animate({scrollTop: '0px'}, 300); 
			  				
			  			}}			  			
					}
					
				});
				
			}
		}
		checkAllSubmited();
		return false ;
	});
});

function callMaxlengthEvent(id)
{
	$('#detail'+id).keyup(function(){  
        var limit = parseInt($(this).attr('maxlength'));  
        var text = $(this).val(); 
        $('#countChar'+id).html(text.length);
    });
}

function checkAllSubmited()
{
	var allDone = true;
	for(var i=0;i<countStrength;i++){
		if(!$("#order"+i).is(":disabled")){
			allDone = false;
		}
	}
	
	if(allDone){
		if(sequence==0)
		{
			$('#resumeListLayer').modal("show");
			//window.location.href = "?view=resumeInfo&idResume="+idResume;
		}
		else
		{
			window.location.href = "/SRIX?view=aptitude&idResume="+idResume+"&sequence="+sequence;
		}
	}
}

function getStrengthSelected()
{
	//container.hide();
	var service = 'getAllStrength';
	var request = '{"service":"'+service+'","idResume":"'+idResume+'","SESSION_ID_LANGUAGE":"'+idLanguage+'"}';
	$.ajax({
		type: "POST",
  		url: '/StrengthServ',
		data: jQuery.parseJSON(request),
		async:false,
		success: function(data){
			var obj = jQuery.parseJSON(data);
			if(obj.success==1)
			{
				//countStrength = obj.strengths.length;
				if(obj.strengths.length <= 5)
				{
					countStrength = obj.strengths.length;
				}
				else
				{
					countStrength = 5;
				}
				createOrderList(obj);
			}
   			else
   			{
   				$('div.container li').remove();
  				for(var i=0; i<obj.errors.length; i++){
   					container.append('<li><label class="error">'+obj.errors[i]+'</label></li>');
   				}
   				container.css({'display':'block'});
   				$('html, body').animate({scrollTop: '0px'}, 300); 
   			}
		}
	});
}

//create select box for list strength
function createOrderList(obj)
{
	var html = "";
	var totalStr=0;
	for(var i=0;i<obj.strengths.length; i++)
	{
		var idStrengthSelected = 0;
		if(obj.strengths.length <=5)
		{
			totalStr= obj.strengths.length;
		}
		else
		{
			totalStr=5;
		}
		//Get idStrength for example
		for(var j=0;j<totalStr; j++)
		{
			if((i+1)==obj.strengths[j][2])
			{
				idStrengthSelected = obj.strengths[j][0];
			}
		}
		html += "<div>";
		html += "<div align='right'><a align='right' id='strengthLink"+i+"' href='SRIX?view=strengthExample&id="+idStrengthSelected+"&ResumeId="+idResume+"' target='_blank'><fmt:message key='STRENGTH_EXAMPLE'/></a></div>";
		html += "<select id='order"+i+"' name='order"+i+"' onchange='swapStrength("+i+")' class='form-control'>";
		//option in select
		for(var j=0;j<totalStr; j++)
		{
			html+="<option value='"+obj.strengths[j][0]+"'";
			if((i+1)==obj.strengths[j][2])
			{
				idStrengthSelected = obj.strengths[j][0];
				html+=" selected";
			}
			html+=">"+obj.strengths[j][1]+"</option>";
		}
		html += "</select>";
		html += "<input type='hidden' id='idStrengthSelected"+i+"' value='"+idStrengthSelected+"'>";
		html += "<textarea id='detail"+i+"' maxlength='1000' name='detail"+i+"' rows='3' class='form-control'>"+obj.strengths[i][3]+"</textarea>";
		html += "<span class='caption' id='countChar"+i+"'>0</span>&nbsp;<font class='caption'>"+countCharText+"</font><div align='right'><input id='submitDetail"+i+"' class='btn btn-lg btn-success' onclick='submitDetail("+i+")' type='button' value='"+textSave+"'></div>";
		html += "</div><br><br>";
		
		if(i == 4)
		{
			break;
		}
	}
	$('#orderDiv').html(html);
	//register event
	for(var i=0;i<obj.strengths.length;i++){
		callMaxlengthEvent(i);
	}
}

	function submitDetail(idOrder)
	{
		if($("#order"+idOrder).is(":disabled")){
			enableStrength(idOrder);
		}
		else
		{
			//container.hide();
			var detail = addslashes($('#detail'+idOrder).val());
			var idStrength = $('#order'+idOrder).val();
			var service = 'updateDetail';
			var request = '{"service":"'+service+'","idResume":'+idResume+',"idStrength":"'+idStrength+'","detail":"'+detail+'"}';
			$.ajax({
				type: "POST",
		 		url: '/StrengthServ',
				data: jQuery.parseJSON(request),
				async:false,
				success: function(data)
				{
					var obj = jQuery.parseJSON(data);
					if(obj.success==1)
					{
						disableStrength(idOrder);
					}
		  			else
		  			{
		  				if(obj.urlError!="")
		  				{
		  					window.location.href = obj.urlError;
		  				}
		  				else
		  				{
		  					$('div.container li').remove();
		  					for(var i=0; i<obj.errors.length; i++)
		  					{
		   						container.append('<li><label class="error">'+obj.errors[i]+'</label></li>');
		    				}
		    				container.show();
		    				$('html, body').animate({scrollTop: '0px'}, 300); 
		  				}
		  			}
				}
			});
		}
	}

	function swapStrength(idOrder)
	{
		//container.hide();
		var newId = $('#order'+idOrder).val();
		var oldId = $('#idStrengthSelected'+idOrder).val();
		var swaped = false;
		var idSwaped = -1;
		//swap position
		for(var i=0;i<countStrength&!swaped;i++)
		{
			if(parseInt($('#idStrengthSelected'+i).val())==parseInt(newId))
			{
				idSwaped = i;
				swaped = true;
			}
		}
		if(idSwaped>=0)
		{
			var service = 'orderDetail';
			var request = '{"service":"'+service+'","idResume":'+idResume+',"countStrength":"2","strength":[{"id":"'+newId+'","idOrder":"'+(idOrder+1)+'"},{"id":"'+oldId+'","idOrder":"'+(idSwaped+1)+'"}]}';
			$.ajax({
				type: "POST",
		 		url: '/StrengthServ',
				data: jQuery.parseJSON(request),
				async:false,
				success: function(data){
					var obj = jQuery.parseJSON(data);
					if(obj.success==1)
					{
						$('#idStrengthSelected'+idSwaped).val(oldId);
						$('#order'+idSwaped+' option[value="'+parseInt(oldId)+'"]').prop('selected', true);
						$('#idStrengthSelected'+idOrder).val(newId);
						var temp=$("#detail"+idOrder).val();
						$("#detail"+idOrder).val($("#detail"+idSwaped).val());
						$("#detail"+idSwaped).val(temp);
						$("#strengthLink"+idOrder).attr({
						    "href" : "SRIX?view=strengthExample&id="+newId+"&ResumeId="+idResume,
						    "target":"_blank"
						});
						$("#strengthLink"+idSwaped).attr({
						    "href" : "SRIX?view=strengthExample&id="+oldId+"&ResumeId="+idResume,
						    "target":"_blank"
						});
					}
		  			else
		  			{
		  				if(obj.urlError!="")
		  				{
		  					window.location.href = obj.urlError;
		  				}
		  				else
		  				{
		  					$('div.container li').remove();
		  					for(var i=0; i<obj.errors.length; i++){
		   						container.append('<li><label class="error">'+obj.errors[i]+'</label></li>');
		    				}
		    				container.show();
		    				$('html, body').animate({scrollTop: '0px'}, 300); 
		  				}
		  			}
				}
			});
		}
	}

	function disableStrength(idOrder)
	{
		var idStrength = $('#order'+idOrder).val();
		$('#order'+idOrder).prop('disabled', true);
		$('#detail'+idOrder).prop('disabled',true);
		$('#submitDetail'+idOrder).val(textEdit);
		for(var j=0;j<countStrength;j++){
			$("#order"+j+" option[value='"+idStrength+"']").prop('disabled', true);
		}
	}
	
	function enableStrength(idOrder){
		var idStrength = $('#idStrengthSelected'+idOrder).val();
		$('#order'+idOrder).prop('disabled', false);
		$('#detail'+idOrder).prop('disabled',false);
		$('#submitDetail'+idOrder).val(textSave);
		for(var j=0;j<countStrength;j++){
			$("#order"+j+" option[value='"+idStrength+"']").prop('disabled', false);
		}
	}
	
	function addslashes(string) 
	{
	    return string.replace(/\\/g, '\\\\').
		replace(/\u0008/g, '\\b').
		replace(/\t/g, '\\t').
		replace(/\n/g, '\\n').
		replace(/\f/g, '\\f').
		replace(/\r/g, '\\r').
		replace(/"/g, '\\"');
	}
</script>


<form action="">
	<div class="seperator"></div>
	<div align="right">
	  <img alt="งาน หางาน" src="../images/icon_question.png" width="30px"  data-toggle="collapse" data-target="#demo">
	  <img alt="งาน หางาน" src="../images/icon_help.png" width="30px"  data-toggle="collapse" data-target="#hotline">
	</div>
	<div>
	  <div id="demo" class="collapse">
		  <div style="height:10px;"></div>
			  <p style="text-align:justify !important; text-indent:0.5cm;" class="color59595c font_14"><fmt:message key="COLLAPSE_STRENGTHS"/></p>
	  </div>
	</div>
	<div>
	  <div id="hotline" class="collapse">
		  <div style="height:10px;"></div>
		 	 <p style="text-align:center" class="color59595c font_14"><fmt:message key="COLLAPSE_HOTLINE"/><br><a href='http://www.jobtopgun.com/?view=comment' target='_blank'><fmt:message key="COLLAPSE_HOTLINE_DISPUTE"/></a><br><br></p>
	  </div>
	</div>
		<h3 align="center" class="section_header"><fmt:message key="STRENGTH_HEADER"/></h3>
		<div class="suggestion">
			<fmt:message key="DIRECTION_STRENGTH_DETAIL" />
		</div>
		<div id="orderDiv"></div>
		<div align="center">
			<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png" id="buttonNext"/>
		</div>
</form>
<c:set var="section" value="5" scope="request"/>
<c:import url="/view/subview/syncResumeList.jsp" charEncoding="UTF-8"/>