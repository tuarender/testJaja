<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.topgun.util.*" %> 
<%@ page import="com.topgun.shris.masterdata.State" %>
<%@ page import="com.topgun.shris.masterdata.MasterDataManager" %> 
<%@ page import="com.topgun.shris.masterdata.Industry" %>
<%
	int idLanguage = 38 ;
	String bgColor = "#c7c9cb";
	String topBgDesktop = "#4b3c36";
	String kvDesktop = "";
	String p1Desktop = "";
	String p2Desktop = "";
	String logoJTGDesktop = "";
	String logoBannerDesktop = "";
	String preLink="";
	String topBgMobile = "";
	String css="";
	String kvMobile = "";
	String p1Mobile = "";
	String p2Mobile = "";
	String logoJTGMobile = "";
	String logoBannerMobile = "";
	String title="หางานที่ JOBTOPGUN.com";
	int idIndustry = Util.getInt(request.getParameter("idJobfield"),3);
	Industry industry = MasterDataManager.getIndustry(idIndustry,idLanguage);
	String metaKeyword="";
	String industryName = "";
	if(idIndustry==3)
	{
		preLink="/Finance";
		css="css20/Finance/custom.css";
		title="การเงิน";
		topBgDesktop = "20150224/images/industry/bank/desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/bank/desktop/Logo.jpg";
		
		industryName="ธนาคาร";
		metaKeyword="งานธนาคาร, หางานธนาคาร, สมัครงานธนาคาร, งานการเงิน,หางานการเงิน,สมัครงานการเงิน,งานประกันชีวิต,ธนาคาร,การเงิน,หางานประกันชีวิต";
		
		topBgMobile = "20150224/images/industry/bank/mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/bank/mobile/Logo.jpg";
	}
	//accountant
	else if(idIndustry==4)
	{
		preLink="/Accountant";
		css="css20/Accountant/custom.css";
		title="บัญชี";
		topBgDesktop = "20150224/images/industry/accountant/desktop/bg.png";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/accountant/desktop/Logo.jpg";
		
		industryName="บัญชี";
		metaKeyword="งานธนาคาร, หางานธนาคาร, สมัครงานธนาคาร, งานการเงิน,หางานการเงิน,สมัครงานการเงิน,งานประกันชีวิต,ธนาคาร,การเงิน,หางานประกันชีวิต";
		
		topBgMobile = "20150224/images/industry/bank/mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/bank/mobile/Logo.jpg";
	}
	else if(idIndustry==35)
	{
		preLink="/Agricultural";
		css="css20/Agricultural/custom.css";
		title="เกษตรกรรม";
		topBgDesktop = "20150224/images/industry/agriculture/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/agriculture/Desktop/Logo.jpg";
		industryName="การเกษตร";
		metaKeyword="หางานการเกษตร,สมัครงานการเกษตร,สมัครงานด้านเกษตร,งานเกี่ยวกับการเกษตร,สมัครงานเกษตรป่าไม้,งานประมง,การประมง,ป่าไม้,งานเกษตร,งานป่าไม้";
		
		topBgMobile = "20150224/images/industry/agriculture/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/agriculture/Mobile/Logo.jpg";
	}
	
	else if(idIndustry==17)
	{
		title="หางานอุตสาหกรรมก่อสร้าง ที่ JOBTOPGUN.com";
		topBgDesktop = "20150224/images/industry/construction/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/construction/Desktop/Logo.jpg";
		industryName="อุตสาหกรรมก่อสร้าง";
		metaKeyword="งานก่อสร้าง, หางานก่อสร้าง, สมัครงานก่อสร้าง, งานควบคุมอาคาร, หางานควบคุมอาคาร, สมัครงานควบคุมอาคาร,งานอุตสาหกรรม,หางานโฟร์แมนก่อสร้าง,งานโฟร์แมนก่อสร้าง,สมัครงานโฟร์แมนก่อสร้าง";
		
		topBgMobile = "20150224/images/industry/construction/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/construction/Mobile/Logo.jpg";
	}
	else if(idIndustry==11)
	{
		title="หางานผลิตภัณฑ์ ที่ JOBTOPGUN.com";
		topBgDesktop = "20150224/images/industry/food/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/food/Desktop/Logo.jpg";
		industryName="อุปโภค บริโภค";
		metaKeyword="งานเนสเล่,หางานเนสเล่,สมัครงานเนสเล่,สมัครงานบริษัทยูนิลิเวอร์,สมัครงานบริษัทไทยเบฟ,หางาน,งาน,สมัครงานบริษัท,หางานบริษัท,งานผลิตภัณฑ์";
		
		topBgMobile = "20150224/images/industry/food/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/food/Mobile/Logo.jpg";
	}
	else if(idIndustry==12)
	{
		title="หางานอิเล็กทรอนิกส์ ที่ JOBTOPGUN.com";
		topBgDesktop = "20150224/images/industry/electronic/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/electronic/Desktop/Logo.jpg";
		industryName="อิเล็กทรอนิกส์";
		metaKeyword="อิเล็กทรอนิกส์,หางานอิเล็กทรอนิกส์,งานอิเล็กทรอนิกส์,สมัครงานอิเล็กทรอนิกส์,รับสมัครงานอิเล็กทรอนิกส์,งานว่างอิเล็กทรอนิกส์,ช่างอิเล็กทรอนิกส์,สมัครงานช่างอิเล็กทรอนิกส์,ประกาศรับสมัครงานอิเล็กทรอนิกส์,บริษัทอิเล็กทรอนิกส์";
		
		topBgMobile = "20150224/images/industry/electronic/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/electronic/Mobile/Logo.jpg";
	}
	else if(idIndustry==46)
	{
		preLink="/Food";
		css="css20/Food/custom.css";
		title="อาหารและเครื่องดื่ม";
		topBgDesktop = "20150224/images/industry/food/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/food/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/food/Desktop/Logo.jpg";
		industryName="อาหารและเครื่องดื่ม";
		metaKeyword="งานอาหารและเครื่องดื่ม, หางานอาหารและเครื่องดื่ม, สมัครงานอาหารและเครื่องดื่ม,งานF&B,หางานF&B,สมัครงานF&B,อาหารและเครื่องดื่ม,งานอาหาร,สมัครงานอาหาร,หางานอาหาร";
		
		topBgMobile = "20150224/images/industry/travel/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/travel/Mobile/Logo.jpg";
	}
	else if(idIndustry==23)
	{	
		preLink="/International_trade";
		css="css20/International_trade/custom.css";
		title="การค้าระหว่างประเทศ";
		topBgDesktop = "20150224/images/industry/import/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/import/Desktop/Logo.jpg";
		industryName="นำเข้า ส่งออก";
		metaKeyword="งานนำเข้า, หางานนำเข้า, สมัครงานนำเข้า, งานนำเข้า ส่งออก, หางานนำเข้า ส่งออก, สมัครงานนำเข้า ส่งออก, งานส่งออก, หางานส่งออก, สมัครงานส่งออก, งาน Import, หางานงาน Import";
		
		topBgMobile = "20150224/images/industry/import/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/import/Mobile/Logo.jpg";
	}
	else if(idIndustry==36)
	{
		title="หางานประกัน ที่ JOBTOPGUN.com";
		topBgDesktop = "20150224/images/industry/insurance/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/insurance/Desktop/Logo.jpg";
		industryName="ประกัน";
		metaKeyword="งานประกันภัย, หางานประกันภัย, สมัครงานประกันภัย, งานประกัน, หางานประกัน, สมัครงานประกัน,ธุรกิจประกันชีวิต, ประกันภัย,งานประกันชีวิต,บริษัทประกัน";
		
		topBgMobile = "20150224/images/industry/insurance/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/insurance/Mobile/Logo.jpg";
	}
	else if(idIndustry==6)
	{
		preLink="/IT";
		css="css20/IT/custom.css";
		title="ไอที";
		topBgDesktop = "20150224/images/industry/it/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/it/Desktop/Logo.jpg";
		industryName="ไอที";
		metaKeyword="งาน IT, หางาน IT, งานไอที, หางานไอที, สมัครงาน IT, สมัครงานไอที,หางานสารสนเทศ,สมัครงานสารสนเทศ,งานสารสนเทศ,งานโปรมแกรมเมอร์";
		
		topBgMobile = "20150224/images/industry/it/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/it/Mobile/Logo.jpg";
	}
	else if(idIndustry==10)
	{
		title="หางานกฎหมาย ที่ JOBTOPGUN.com";
		topBgDesktop = "20150224/images/industry/law/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/law/Desktop/Logo.jpg";
		industryName="กฎหมาย";
		metaKeyword="หางานนิติการ,งานทนายความ,บริษัทงานกฎหมาย,สมัครงานกฎหมาย,สมัครงานทนายความ,งานเกี่ยวกับกฎหมาย,สมัครงานทนายความ,หางานทนายความ,งานนิติกร,หางานกฎหมาย";
		
		topBgMobile = "20150224/images/industry/law/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/law/Mobile/Logo.jpg";
	}
	else if(idIndustry==28)
	{
		
		preLink="/Logistic";
		css="css20/Logistic/custom.css";
		title="คมนาคมขนส่ง";
		topBgDesktop = "20150224/images/industry/logistics/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/logistics/Desktop/Logo.jpg";
		industryName="ขนส่ง";
		metaKeyword="หางานขนส่งสินค้า,งานขนส่ง, หางานขนส่ง, สมัครงานขนส่ง,งานขนส่งสินค้า,สมัครงานบริษัทขนส่ง,หางานด้านโลจิสติกส์,งาน ขนส่ง ทาง บก,ธุรกิจขนส่ง,งานธุรกิจขนส่ง";
		
		topBgMobile = "20150224/images/industry/logistics/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/logistics/Mobile/Logo.jpg";
	}
	else if(idIndustry==1)
	{	
		preLink="/Marketing";
		css="css20/Marketing/custom.css";
		title="การตลาด";
		topBgDesktop = "20150224/images/industry/marketing/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/marketing/Desktop/Logo.jpg";
		industryName="การตลาด";
		metaKeyword="งานการตลาด, หางานการตลาด, สมัครงานการตลาด, งานประชาสัมพันธ์, หางานประชาสัมพันธ์, สมัครงานประชาสัมพันธ์, งาน PR, หางาน PR, สมัครงาน PR,ตำแหน่ง การ ตลาด,งาน ด้าน การ ตลาด";
		
		topBgMobile = "20150224/images/industry/marketing/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/marketing/Mobile/Logo.jpg";
	}
	else if(idIndustry==20)
	{
		title="หางานสื่อบันเทิง JOBTOPGUN.com";
		topBgDesktop = "20150224/images/industry/media/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/media/Desktop/Logo.jpg";
		industryName="สื่อบันเทิง";
		metaKeyword="งานสื่อสารมวลชน,หางานสื่อสารมวลชน,สมัครงานสื่อสารมวลชน,นักข่าว,งานวิทยุ,งานโทรทัน์,งานหนังสือพิมพ์,งานบันเทิง,งานโฆษณา,งานนิตยสาร";
		
		topBgMobile = "20150224/images/industry/media/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/media/Mobile/Logo.jpg";
	}
	else if(idIndustry==18)
	{
		title="หางานเคมีภัณฑ์ JOBTOPGUN.com";
		topBgDesktop = "20150224/images/industry/marketing/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/marketing/Desktop/Logo.jpg";
		industryName="เคมีภัณฑ์";
		metaKeyword="งานเคมี,สมัครงาน ปตท,สมัครงานTOA,งานบริษัทเคมี.หางานบริษัทเคมี,สมัครงานบริษัทเคมี,งานเคมีภัณฑ์,สมัครงานเคมีภัณฑ์,งานปตท";
		
		topBgMobile = "20150224/images/industry/marketing/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/marketing/Mobile/Logo.jpg";
	}
	else if(idIndustry==16)
	{
		title="หางานปิโตรเลียม JOBTOPGUN.com";
		topBgDesktop = "20150224/images/industry/petroleum/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/petroleum/Desktop/Logo.jpg";
		industryName="ปิโตรเลียม";
		metaKeyword="งานพลังงาน, หางานพลังงาน, สมัครงานพลังงาน,หางานปิโตรเลียม ,งานปิโตรเลียม ,สมัครงานปิโตรเลียม, งานน้ำมัน, หางานน้ำมัน, สมัครงานน้ำมัน,บริษัทน้ำมัน";
		
		topBgMobile = "20150224/images/industry/petroleum/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/petroleum/Mobile/Logo.jpg";
	}
	else if(idIndustry==15)
	{
		title="หางานเภสัชกรรม JOBTOPGUN.com";
		topBgDesktop = "20150224/images/industry/pharma/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/pharma/Desktop/Logo.jpg";
		industryName="เภสัชกรรม";
		metaKeyword="งานเภสัช, หางานเภสัช, สมัครงานเภสัช, สมัคงานเภสัช, งานเภสัชกรรม, หางานเภสัชกรรม, สมัครงานเภสัชกรรม, สมัคงานเภสัชกรรม, งานเภสัชกร, หางานเภสัชกร";
		
		topBgMobile = "20150224/images/industry/pharma/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/pharma/Mobile/Logo.jpg";
	}
	else if(idIndustry==19)
	{
		preLink="/PR";
		css="css20/PR/custom.css";
		title="โฆษณา";
		topBgDesktop = "20150224/images/industry/pr/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/pr/Desktop/Logo.jpg";
		industryName="โฆษณา";
		metaKeyword="งานโฆษณา, หางานโฆษณา, สมัครงานโฆษณา, หางานสื่อ, สมัครงานสื่อ,งานประชาสัมพันธ์,หางานสื่อและการโฆษณา ,สมัครงานสื่อและการโฆษณา,งานสื่อและการโฆษณา,งานพีอาร์";
		
		topBgMobile = "20150224/images/industry/pr/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/pr/Mobile/Logo.jpg";
	}
	else if(idIndustry==24)
	{
		title="หางานอสังหาริมทรัพย์่ JOBTOPGUN.com";
		topBgDesktop = "20150224/images/industry/construction/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/construction/Desktop/Logo.jpg";
		industryName="อสังหาริมทรัพย์";
		metaKeyword="งานอสังหาริมทรัพย์, หางานอสังหาริมทรัพย์, สมัครงานอสังหาริมทรัพย์,รับสมัครงานอสังหาริมทรัพย์,ธุรกิจอสังหาริมทรัพย์,สมัครงาน แลนด์แอนด์เฮาส์,งานธุรกิจ,หางานบริษัทอสังหาริมทรัพย์,งานบริษัทอสังหาริมทรัพย์,บริษัทอสังหาริมทรัพย์";
		
		topBgMobile = "20150224/images/industry/construction/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/construction/Mobile/Logo.jpg";
	}
	else if(idIndustry==26)
	{
		title="หางานห้างสรรพสินค้า JOBTOPGUN.com";
		topBgDesktop = "20150224/images/industry/retail/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/retail/Desktop/Logo.jpg";
		industryName="ห้างสรรพสินค้า";
		metaKeyword="งานห้างสรรพสินค้า,หางานห้าง,งานในห้าง,งานขายของในห้าง,งานศูนย์สรรพสินค้า,สมัครงาน ศูนย์สรรพสินค้า,หางานร้านค้า,พนักงานประจำร้าน,พนักงานขาย,พนักงานในห้าง";
		
		topBgMobile = "20150224/images/industry/retail/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/retail/Mobile/Logo.jpg";
	}
	else if(idIndustry==38)
	{
		title="หางานท่องเที่ยว JOBTOPGUN.com";
		topBgDesktop = "20150224/images/industry/travel/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/travel/Desktop/Logo.jpg";
		industryName="ท่องเที่ยว";
		metaKeyword="งานทัวร์, หางานทัวร์, สมัครงานทัวร์, หางานบริษัททัวร์, สมัครงานบริษัททัวร์,การโรงแรม, ล่าม, ไกด์, มัคคุเทศก์, งานท่องเที่ยว";
		
		topBgMobile = "20150224/images/industry/travel/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/travel/Mobile/Logo.jpg";
	}else if(idIndustry==9)
	{	
		preLink="/Architect";
		css="css20/Architect/custom.css";
		title="สถาปนิก";
		topBgDesktop = "20150224/images/industry/travel/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/travel/Desktop/Logo.jpg";
		industryName="สถาปนิก";
		metaKeyword="งานสถาปัตยกรรม,งานสถาปัตย์,งานสถาปนิก,หางานสถาปนิก,สมัครงานสถาปนิก,สมัครงานสถาปัตย์,งานมัณฑนากร,งานโยธา,หางานสถาปัตยกรรม,หางานมัณฑนากร";
		
		topBgMobile = "20150224/images/industry/travel/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/travel/Mobile/Logo.jpg";
	}else if(idIndustry==37)
	{
		preLink="/Economist";
		css="css20/Economist/custom.css";
		title="เศรษฐศาสตร์";
		topBgDesktop = "20150224/images/industry/travel/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/travel/Desktop/Logo.jpg";
		industryName="เศรษฐศาสตร์";
		metaKeyword="งานเศรษฐศาสตร์,หางานเศรษฐศาสตร๋,สมัครงานเศรษฐศาสตร์,งานสถิติ,งานวิจัย,งานวิเคราห์,งานคณิตศาสตร์,หางานวิจัย,สมัครงานสถิติ";

		
		topBgMobile = "20150224/images/industry/travel/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/travel/Mobile/Logo.jpg";
	}else if(idIndustry==8)
	{
		preLink="/Engineer";
		css="css20/Engineer/custom.css";
		title="วิศวกร/ช่างเทคนิค";
		topBgDesktop = "20150224/images/industry/travel/Desktop/BG.jpg";
		kvDesktop = "20150224/images/industry/desktop/KV.png";
		p1Desktop = "20150224/images/industry/desktop/P1.png";
		p2Desktop = "20150224/images/industry/desktop/P2.png";
		logoJTGDesktop = "20150224/images/industry/desktop/Logo.png";
		logoBannerDesktop = "20150224/images/industry/travel/Desktop/Logo.jpg";
		industryName="วิศวกร/ช่างเทคนิค";
		metaKeyword="งานวิศวกร, หางานวิศวกร, สมัครงานวิศวกร, งานวิศวะ, หางานวิศวะ, สมัครงานวิศวะ, งานวิศวกรรม, หางานช่างเทคนิค, สมัครงานวิศวกรรม,งานช่าง";
		
		topBgMobile = "20150224/images/industry/travel/Mobile/BG.jpg";
		kvMobile = "20150224/images/industry/mobile/KV.png";
		p1Mobile = "20150224/images/industry/mobile/P1.png";
		p2Mobile = "20150224/images/industry/mobile/P2.png";
		logoJTGMobile = "20150224/images/industry/mobile/Logo.png";
		logoBannerMobile = "20150224/images/industry/travel/Mobile/Logo.jpg";
	}

%>	
<!doctype html>
<!--[if IE 9]><html class="ie9" lang="en"><![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--><html lang="en"><!--<![endif]-->
<head>
	<title>JOBTOPGUN</title>
	<!--meta info-->
	<meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<meta name="author" content="">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<link rel="shortcut icon" type="image/x-icon" href="images20/fav.ico">
	<!--web fonts-->
	<link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900,100italic,300italic,400italic' rel='stylesheet' type='text/css'>
	<!--libs css-->
	<link rel="stylesheet" type="text/css" media="all" href="plugins/owl-carousel/owl.carousel.css">
	<link rel="stylesheet" type="text/css" media="all" href="plugins/owl-carousel/owl.transitions.css">
	<link rel="stylesheet" type="text/css" media="all" href="plugins/jackbox/css/jackbox.min.css">
	<link rel="stylesheet" type="text/css" media="screen" href="plugins/rs-plugin/css/settings.css">
	 
	<!--theme css-->
	<link rel="stylesheet" type="text/css" media="all" href="css20/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" media="all" href="css20/theme-animate.css">
	<link rel="stylesheet" type="text/css" media="all" href="css20/style.css">
	<link rel="stylesheet" type="text/css" media="all" href="<%=css%>">
	
	<!--head libs-->
	<script src="js/jquery-2.1.0.min.js"></script>
	<script src="plugins/jquery.queryloader2.min.js"></script>
	<script src="plugins/modernizr.js"></script>
	<script>
		$('html').addClass('d_none');
		$(document).ready(function(){
			$('html').show();
			$("body").queryLoader2({
				backgroundColor: '#fff',
				barColor : '#35eef6',
				barHeight: 4,
				percentage:true,
				deepSearch:true,
				minimumTime:1000
			});
		});
	</script>
</head>
<body>

		
	<!--Header-->
		<section class="section-landing">
			<div class="section-header container-fluid">
				<div class="human"></div>
				<div class="header-top">
					<img src="images20/logo_superresume.png" alt="">
					<img src="images20/logo_jobtopgun.png" alt="">
				</div>
				<div class="header-title ">
					<h1>&nbsp;หางาน<%=title%>ที่&nbsp;JOBTOPGUN&nbsp;</h1>
				</div>

				<div class="feature">
					<div class="container-feature">
						<!-- <div class="row"> -->
							<!-- <div class="col-md-4 hidden-sm hidden-xs">&nbsp;</div> -->
							<!-- <div class="col-md-8"> -->
								<h1>
									หางานได้งานด้วย <b>Super 5</b><br>
									ฮีโร่ที่ช่วยคุณหางาน
								</h1>
								<div class="list">
									<div class="icon01"></div>
									<div class="textbox">
										<h2>Super Match</h2>
										<p>มีฮีโร่ช่วยหางานให้คุณ</p>
									</div>
								</div>
								<div class="list">
									<div class="icon02"></div>
									<div class="textbox">
										<h2>Super Resume</h2>
										<p>ทำให้คุณเด่่นเหนือใคร</p>
									</div>
								</div>
								<div class="list">
									<div class="icon03"></div>
									<div class="textbox" >
										<h2>Super Apply</h2>
										<p >สมัครงานเป็น 100 ไม่อั้นในครั้งเดียว</p>
									</div>
								</div>
								<div class="list">
									<div class="icon04"></div>
									<div class="textbox">
										<h2>Super Tracking</h2>
										<p>ติดตามผลรู้ว่า HR อ่านเรซูเม่คุณ</p>
									</div>
								</div>
								<div class="list">
									<div class="icon05"></div>
									<div class="textbox">
										<h2>Super Notifications</h2>
										<p>ทันใจเมื่อ HR เชิญคุณสัมภาษณ์</p>
									</div>
								</div>
							<!-- </div> -->
						<!-- </div> -->
					</div>
				</div>
				<a href="http://www.jobtopgun.com" class="readmore">อ่านต่อ<span><img src="images20<%=preLink%>/read_icon.png" alt=""></span></a>
			</div>
			<div class='section-hr' >
				<center><hr style=' border-top: 1px solid #BEBEBE;'>
			</div>
			<div id="why" class="section-content tac">
				<h2>หากคุณกำลังหางาน<%=title%>อยู่ คุณมี 2 ทางเลือก</h2>
				<div class="content-box">
					<h3>1. หางานก่อน</h3>
					<div class="link"><a href="http://www.jobtopgun.com/?view=jobFieldList&idJobField=<%=idIndustry%>"><img src="images20<%=preLink%>/button1.png" alt=""></a></div>
					<p>เจองานเมื่อไหร่ ค่อยทำ ซูเปอร์ เรซูเม่ ภายหลัง</p>
				</div>
				<div class="content-box">
					<h3 class="h3-xs">2. ทำซูเปอร์ เรซูเม่ (ใบสมัครงาน) เตรียมพร้อมไว้เลย</h3>
					<p>เมื่อคุณเจองานที่คุณสนใจก็สมัครได้ทันที ไม่เสียเวลา</p>
					<div class="link"><a href="http://www.superresume.com/SRIX?view=register"><img src="images20<%=preLink%>/button2.png" alt=""></a></div>
					<p class="p-xs">* ทุกบริษัทชั้นนำ ต้องการใบสมัครงานที่เป็น ซูเปอร์ เรซูเม่ เท่านั้น</p>
				</div>
			</div>
			<a href="http://www.jobtopgun.com/?view=jobFieldList&idJobField=<%=idIndustry%>" class="readmore">
			<div class="section-client">
				
			</div>
			</a>
			<div class="section-faq">
				<div class="container-faq">
					<div class="faq-list">
						<h3>ทำไมต้องใช้ Super Resume ?</h3>
						<ul>
							<li>
								Resume รูปแบบอื่นสร้างปัญหาให้  HR<br>
								Super Resume เป็นรูปแบบเรซูเม่ที่บริษัทชั้นนำเจาะจงเลือกใช้ในการรับสมัครงาน เพราะให้ข้อมูลผู้ สมัครได้ดีกว่าเรซูเม่ที่ทั่วไปที่ทำกันเอง ซึ่งมีหลายร้อยรูปแบบ ทำให้ HR ปวดหัวมากในการรับคนเพราะ ข้อมูลที่อยู่ในแต่ละแบบของเรซูเม่จะไม่เหมือนกัน ทำให้เวลา HR  ทำงานเลือกอ่านได้ลำบาก บางแบบ ข้อมูลการศึกษาก็อยู่หน้าแรก บางทีก็หน้า 2 บางทีก็หน้า 3 เพราะฉะนั้น เจาะจงเลือก SR ในการสมัคร งานก็จะมีโอกาสในการได้รับการพิจารณามากกว่า
							</li>
							<li>
								นำเสนอตัวตนได้ดีกว่า<br>
								สมัยนี้รับคนที่ Competency เรียกว่า เป็น Super Resume เดียวที่มีตัวช่วยให้นำเสนอจุดเด่นของ ผู้สมัครและ Lifestyle เรื่องงานอดิเรก
							</li>
							<li>
								Resume นี้ใช้มาแล้ว 16 ปี มีคนกว่า 2 ล้านที่เป็นสมาชิก และเป็นเรซูเม่เดียวที่มีลิขสิทธิ์ แต่เราให้ ผู้สมัครใช้ฟรี
							</li>
						</ul>
					</div>
					<div class="faq-list">
						<h3>ทำไมต้องใช้ JOBTOPGUN หางาน ?</h3>
						<ul>
							<li>
								ทุกตำแหน่งเปิดรับจริง ซึ่งต่างจากหลายเว็บไซต์ที่อาจเอาตำแหน่งงานมาลงเองโดยไม่ได้รับอนุญาต
							</li>
							<li>
								HR บางท่านเช็คใบสมัครจากระบบ Apply Online ไม่ได้เช็คจากอีเมล
							</li>
							<li>
								มีตัวช่วยคือ Super Resume ที่แสดงตัวตนได้ดีกว่าเรซูเม่แบบอื่นๆ
							</li>
						</ul>
					</div>
					<div class="faq-list">
						<h3>ขั้นตอนการหาและสมัครงาน</h3>
						<ul>
							<li>
								สร้าง Super Resume ให้เสร็จพร้อมใช้ในการสมัครงาน
							</li>
							<li>
								หางานที่ต้องการด้วย Search Engine ตามนี้<br>
								ค้นหาจากชื่อบริษัทดัง<br>
								งานตามรายชื่อบริษัท<br>
								งานตามสายอาชีพ<br>
								งานตามวันที่<br>
								งานตามประเภทธุรกิจ<br>
								งานตามประสบการณ์<br>
								งานตามจังหวัด<br>
								งานตามเงื่อนไข<br>
								งานนิคมอุตสาหกรรม<br>
							</li>
							<li>
								เมื่อเจอตำแหน่งงานที่คุณสนใจ คุณสามารถสมัครงานได้โดยกด “สมัครทันที”
							</li>
							<li>
								ระบบจะนำคุณเข้าสู่หน้าล็อกอินเพื่อส่ง Super Resume ไปยัง HR บริษัทที่คุณสนใจ
							</li>
							<li>
								หลังจาก Log in ระบบจะนำคุณไปที่หน้าส่ง Resume ให้คุณเลือกเรซูเม่และเอกสารที่คุณต้องการส่ง และตรวจสอบความเรียบร้อย
							</li>
							<li>
								กด “ส่งเรซูเม่” การสมัครงานของคุณก็เสร็จสมบูรณ์ มั่นใจว่าเรซูเม่ส่งถึง HR แน่นอน
							</li>
						</ul>
					</div>
				</div>
			</div>
		</section>

	<!--Script-->
		<script src="plugins/rs-plugin/js/jquery.themepunch.plugins.min.js"></script>
		<script src="plugins/rs-plugin/js/jquery.themepunch.revolution.min.js"></script>
		<script src="plugins/isotope.pkgd.min.js"></script>
		<script src="plugins/jquery.appear.js"></script>
		<script src="plugins/afterresize.min.js"></script>
		<script src="plugins/jquery.easing.1.3.js"></script>
		<script src="plugins/jquery.easytabs.min.js"></script>
		<script src="plugins/jackbox/js/jackbox-packed.min.js"></script>
		<script src="plugins/twitter/jquery.tweet.min.js"></script>
		<script src="plugins/flickr.js"></script>
		<script src="plugins/owl-carousel/owl.carousel.min.js"></script>
		<script src="plugins/flickr.js"></script>
		<script src="js/theme.plugins.js"></script>
		<script src="js/theme.js"></script>
		<script type="text/javascript" src="js/retina.js"></script>
		<script>
			$(document).ready(function(){

			    if (window.devicePixelRatio > 1) {

			        var lowresimages18 = $('img');

			 

			        images18.each(function(i) {

			            var lowres = $(this).attr('src');

			            var highres = lowres.replace(".", "@2x.");

			            $(this).attr('src', highres);

			        });

			    }

			});
			
		</script>
	    <script>
	        $(function() {
	          $('a[href*=#]:not([href=#])').click(function() {
	            if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
	              var target = $(this.hash);
	              target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
	              if (target.length) {
	                $('html,body').animate({
	                  scrollTop: target.offset().top
	                }, 1000);
	                return false;
	              }
	            }
	          });
	        });
	    </script>
</body>
</html>