
function confirmDelete(url,msg)
{
	var ok=confirm(msg);
	if(ok===true)
	{
		this.location.href=url;
	}
}

function createXMLHttpRequest()
{
	var xmlHttp;
	if (window.XMLHttpRequest) // Mozilla, Safari,...
	{ 
		xmlHttp = new XMLHttpRequest();
	}
	else if (window.ActiveXObject) // IE
	{ 
		try 
		{
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch(e) 
		{
			try 
			{
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			} 
			catch (e) 
			{
			}
		}
	}
	
	if (!xmlHttp) 
	{
		alert('Cannot create XMLHTTP instance');
		return false;
	}
	else
	{
		return xmlHttp;
	}
} 		

function getAjax(url,display)
{		
	var xmlHttp=createXMLHttpRequest();
	xmlHttp.onreadystatechange=function()
	{
		viewAjax(xmlHttp,display);
	}
	xmlHttp.open("post",url,true);
	xmlHttp.send(null);	
}

function viewAjax(xml,display)
{	
	if(xml.readyState==4)
	{	
		if(xml.status==200)
		{			
			document.getElementById(display).innerHTML=xml.responseText;	
		}
	}
	else
	{
		document.getElementById(display).innerHTML='<img src="images/progressbar.gif">';
	}
}	

function getHighestEducation(idJsk,idResume,idLanguage)
{
	var xmlHttp=createXMLHttpRequest();
	xmlHttp.onreadystatechange=function()
	{
		viewHighestEducation(xmlHttp,idJsk,idResume);
	}
	xmlHttp.open("post","view/AjaxHighestEducation.php?idJsk="+idJsk+"&idResume="+idResume+"&idLanguage="+idLanguage,true);
	xmlHttp.send(null);	
}

function viewHighestEducation(xml,idJsk,idResume)
{	
	if(xml.readyState==4)
	{	
		if(xml.status==200)
		{			
			var json = eval('('+ xml.responseText +')');
			if(json[0])
			{
				document.getElementById('edu_'+idJsk+'_'+idResume+'_1').innerHTML=json[0];
			}
			else
			{
				document.getElementById('edu_'+idJsk+'_'+idResume+'_1').innerHTML='';
			}
			if(json[1])
			{
				document.getElementById('edu_'+idJsk+'_'+idResume+'_2').innerHTML=json[1];
			}
			else
			{
				document.getElementById('edu_'+idJsk+'_'+idResume+'_2').innerHTML='';
			}
		}
	}
	else
	{
		document.getElementById('edu_'+idJsk+'_'+idResume+'_1').innerHTML='<img src="images/progressbar.gif">';
		document.getElementById('edu_'+idJsk+'_'+idResume+'_2').innerHTML='<img src="images/progressbar.gif">';
	}
}	
