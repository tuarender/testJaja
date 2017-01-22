<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topgun.util.Util"%>
<%@page import="com.topgun.resume.Resume"%>
<%@page import="com.topgun.shris.masterdata.MasterDataManager"%>
<%@page import="com.topgun.shris.masterdata.Strength"%>
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
		int idJsk=Util.getInt(session.getAttribute("SESSION_ID_JSK"));
		int idResume = Util.getInt(request.getParameter("idResume"));
		int sequence = Util.getInt(request.getParameter("sequence"),1);
		String view = Util.getStr(request.getParameter("view"));
		
		request.setAttribute("view", view);
		request.setAttribute("idResume",idResume);
		request.setAttribute("sequence",sequence);
		request.setAttribute("idLanguage",idLanguage);
	}
%>
<script>
	var idResume = '<c:out value="${idResume}"/>';
	var idLanguage= '<c:out value="${idLanguage}"/>';
	var sequence = '<c:out value="${sequence}"/>';
	var container = null;
	var countStrength = 0;
	
	$(document).ready(function()
	{
		//---------------------------- load strength ----------------------------
		getStrengthSelected();
		
		//validation here
		container = $('div.errorContainer');
		$('#buttonNext').click(function() 
		{
			var container = $('div.errorContainer');
			var allOrdered = true;
			var allRepeat= true;
			$('div.container li').remove();
			
			//releaseAllLock();
			if(countStrength <= 5)
			{
				for(var i=0;i<countStrength;i++)
				{
					if($('#order'+i).val()==null||$('#order'+i).val()=="")
					{
						allOrdered = false;
					}
				}
			}
			else
			{
				for(var i=0;i<5;i++)
				{
					if($('#order'+i).val()==null||$('#order'+i).val()=="")
					{
						allOrdered = false;
					}
				}
			}
			
			//check repeat
			if(countStrength <= 5)
			{
				for(var i=0;i<countStrength;i++)
				{
					for(var j=0; j<countStrength;j++)
					{
						if(i!=j)
						{
							if($('#order'+i).val()==$('#order'+j).val())
							{
								allRepeat = false;
							}
						}
					}
				}
			}
			else
			{
				for(var i=0;i<5;i++)
				{
					for(var j=0; j<countStrength;j++)
					{
						if(i!=j)
						{
							if($('#order'+i).val()==$('#order'+j).val())
							{
								allRepeat = false;
							}
						}
					}
				}
			}
			if(!allOrdered)
			{
				container.find("ol").append("<li><label class='error'><fmt:message key='STRENGTH_ORDER_REQUIRE'/></label></li>");
				container.show();
				container.find("ol").css({'display':'block'});
	        	$('html, body').animate({scrollTop: '0px'}, 300);
			}
			else if(!allRepeat)
			{
				container.find("ol").append("<li><label class='error'><fmt:message key='STRENGTH_REPEAT'/></label></li>");
				container.show();
				container.find("ol").css({'display':'block'});
	        	$('html, body').animate({scrollTop: '0px'}, 300);
			}
			else
			{
				container.hide();
				var service = 'orderStrength';
				var request = '{"service":"'+service+'","idResume":'+idResume+',"countStrength":"'+countStrength+'","strength":[';
				for(var i=0;i<countStrength;i++)
				{
					if(i>0)
					{
						request+=",";
					}
					request+='{ "id":"'+$('#order'+i).val()+'" , "idOrder":"'+(i+1)+'" }';
				}
				request += "]}";
				$.ajax({
					type: "POST",
			  		url: '/StrengthServ',
					data: jQuery.parseJSON(request),
					async:false,
					success: function(data){
	      				var obj = jQuery.parseJSON(data);
	      				if(obj.success==1)
	      				{
	      					if(sequence==1&&idResume==0)
	      					{
	      						window.location.href = "?view=aptitude&idResume="+idResume+"&sequence="+sequence;
	      					}
	      					else
	      					{
	      						if(sequence==0)
								{
									$('#resumeListLayer').modal({
       									show: 'true'
    								});
   								}
   								else
   								{
   									window.location.href = "?view=strengthDetail&idResume="+idResume+"&sequence="+sequence;
   								}
	      					}
	      				}
	          			else
	          			{
	          				if(obj.urlError!="")
	          				{
	          					window.location.href = obj.urlError;
	          				}
	          				else
	          				{
	          					for(var i=0; i<obj.errors.length; i++)
	          					{
	           						container.find("ol").append('<li>'+obj.errors[i]+'</li>');
		           				}
		           				container.show();
		           				container.find("ol").css({'display':'block'});
		           				$('html, body').animate({scrollTop: '0px'}, 300); 
	          				}
	          			}
	      			}
	      		});
			}
			//lockSelected();
			return false ;
		});
	});
	
	function getStrengthSelected(){
		var service = 'getAllStrength';
		var request = '{"service":"'+service+'","idResume":"'+idResume+'","SESSION_ID_LANGUAGE":"'+idLanguage+'"}';
		$.ajax({
			type: "POST",
	  		url: '/StrengthServ',
			data: jQuery.parseJSON(request),
			async:false,
			success: function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.success==1){
					var html = "<ol>";
					for(var i=0;i<obj.strengths.length; i++){
						html +="<li>"+obj.strengths[i][1]+"</li>";
					}
					html+="</ol>";
					countStrength = obj.strengths.length;
					$("#numStr").html(countStrength);
					$("#strengthSelected").html(html);
					createOrderList(obj);
				}
	   			else{
	   				$('div.container li').remove();
	  				for(var i=0; i<obj.errors.length; i++){
	   					container.find("ol").append('<li>'+obj.errors[i]+'</li>');
	   				}
	   				container.css({'display':'block'});
	   				container.find("ol").css({'display':'block'});
	   				$('html, body').animate({scrollTop: '0px'}, 300); 
	   			}
			}
		});
	}
	
	//create select box for list strength
	function createOrderList(obj)
	{
		var html = "";
		if(obj.strengths.length <= 5)
		{
			for(var i=0;i<obj.strengths.length; i++)
			{
				html += "<div class='row'>";
				html += "<div class='col-lg-1 col-md-1 col-sm-2 col-xs-2' align='center'>"+(i+1)+".</div>";
				html += "<div class='col-lg-11 col-md-11 col-sm-10 col-xs-10'>";
				html += "<select id='order"+i+"' name='order"+i+"' onchange='swapStrength("+i+")' class='form-control' onClick=\"ga('send', 'event', { eventCategory: 'Engage-StrengthOrder', eventAction: 'Click-dropdown', eventLabel: 'StrengthOrder'});\">";
				html += "<option value=''><fmt:message key='GLOBAL_SELECT'/></option>";
				var idStrengthSelected = 0;
				for(var j=0;j<obj.strengths.length; j++)
				{
					html+="<option value='"+obj.strengths[j][0]+"'";
					if((i+1)==obj.strengths[j][2])
					{
						if(idResume!=0||sequence!=1)
						{
							idStrengthSelected = obj.strengths[j][0];
							html+=" selected";
						}
					}
					html+=">"+obj.strengths[j][1]+"</option>";
				}
				html += "</select>";
				html += "</div>";
				html += "<input type='hidden' id='idStrengthSelected"+i+"' value='"+idStrengthSelected+"'>";
				html += "</div><br>";
				
			}
		}
		else
		{
			for(var i=0;i<5; i++)
			{
				html += "<div class='row'>";
				html += "<div class='col-lg-1 col-md-1 col-sm-2 col-xs-2' align='center'>"+(i+1)+".</div>";
				html += "<div class='col-lg-11 col-md-11 col-sm-10 col-xs-10'>";
				html += "<select id='order"+i+"' name='order"+i+"' onchange='swapStrength("+i+")' class='form-control'>";
				html += "<option value=''><fmt:message key='GLOBAL_SELECT'/></option>";
				var idStrengthSelected = 0;
				for(var j=0;j<obj.strengths.length; j++)
				{
					html+="<option value='"+obj.strengths[j][0]+"'";
					if((i+1)==obj.strengths[j][2])
					{
						if(idResume!=0||sequence!=1)
						{
							idStrengthSelected = obj.strengths[j][0];
							html+=" selected";
						}
					}
					html+=">"+obj.strengths[j][1]+"</option>";
				}
				html += "</select>";
				html += "</div>";
				html += "<input type='hidden' id='idStrengthSelected"+i+"' value='"+idStrengthSelected+"'>";
				html += "</div><br>";
				
			}
		}
		$('#orderDiv').html(html);
	}
	
	function swapStrength(idOrder){
		container.hide();
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
		
		if(idSwaped>=0&&oldId>0)
		{
			var service = 'orderStrength';
			var request = '{"service":"'+service+'","idResume":'+idResume+',"countStrength":"2","strength":[{"id":"'+newId+'","idOrder":"'+(idOrder+1)+'"},{"id":"'+oldId+'","idOrder":"'+(idSwaped+1)+'"}]}';
			$.ajax({
				type: "POST",
		 		url: '/StrengthServ',
				data: jQuery.parseJSON(request),
				async:false,
				success: function(data){
					var obj = jQuery.parseJSON(data);
					if(obj.success==1){
						$('#idStrengthSelected'+idSwaped).val(oldId);
						$('#order'+idSwaped+' option[value="'+parseInt(oldId)+'"]').prop('selected', true);
						$('#idStrengthSelected'+idOrder).val(newId);
					}
		  			else{
		  				if(obj.urlError!=""){
		  					window.location.href = obj.urlError;
		  				}
		  				else{
		  					$('div.container li').remove();
		  					for(var i=0; i<obj.errors.length; i++){
		   						container.find("ol").append('<li>'+obj.errors[i]+'</li>');
		    				}
		    				container.show();
		    				container.find("ol").css({'display':'block'});
		    				$('html, body').animate({scrollTop: '0px'}, 300); 
		  				}
		  			}
				}
			});
		}
	}

</script>
<form>
	<input type="hidden" hidden="SESSION_ID_LANGUAGE" value="<c:out value="${idLanguage}"/>">
	<div class="seperator"></div>
	<c:import url="register_suggestion.jsp" charEncoding="UTF-8"/>
	<div class="suggestion alignCenter">
		<h5><fmt:message key="STRENGTH_ORDER_DETAIL"/></h5>
	</div>
	<div class="errorContainer" style="padding:5px" align="left">
		<b><fmt:message key="GLOBAL_PLEASE_READ"/></b>
		<ol></ol>
	</div>
	<div align="center" class="caption">
		<div id="orderDiv"></div>
	</div>
	<br>
	<div align="center">
		<input type="image" src="/images/<c:out value='${sessionScope.SESSION_LANGUAGE}'/>/next.png" id="buttonNext" onClick="ga('send', 'event', { eventCategory: 'Engage-StrengthOrder', eventAction: 'Click', eventLabel: 'ถัดไป'});"/>
	</div>
	<br>
</form>
<c:set var="section" value="5" scope="request"/>
<c:import url="/view/subview/syncResumeList.jsp" charEncoding="UTF-8"/>