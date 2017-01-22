$(document).ready(function()
{	
	$(document).ajaxStart(function()
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
	});
	
	$(document).ajaxStop($.unblockUI);
	
	$(window).load(function(){
		window.scrollTo(0, 1);
	});
});

if(typeof String.prototype.trim !== 'function')
{
	String.prototype.trim = function() 
	{
			return this.replace(/^\s+|\s+$/g, ''); 
	};
}

function isIE () {
	var myNav = navigator.userAgent.toLowerCase();
	return (myNav.indexOf('msie') != -1) ? parseInt(myNav.split('msie')[1]) : false;
}