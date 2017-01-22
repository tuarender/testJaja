package com.topgun.supermatch;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Location 
{
	public static Set<Integer> getListStateInRegions(int idState)
	{
		Set<Integer> result = new HashSet<Integer>();
		
		if(idState==1){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//	อำนาจเจริญ
		else if(idState==14){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//กาฬสินธุ์
		else if(idState==17){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//ขอนแก่น
		else if(idState==24){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//มหาสารคาม
		else if(idState==25){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//มุกดาหาร
		else if(idState==27){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//นครพนม
		else if(idState==33){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//หนองบัวลำภู
		else if(idState==34){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//หนองคาย
		else if(idState==52){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//ร้อยเอ็ด
		else if(idState==59){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//ศรีสะเกษ
		else if(idState==65){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//สุรินทร์
		else if(idState==68){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//อุบลราชธานี
		else if(idState==69){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//อุดรธานี
		else if(idState==73){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//ยโสธร
		else if(idState==75){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//สกลนคร
		else if(idState==78){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78));}//บึงกาฬ
		else if(idState==28){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78,22,56,74,47,61));}//นครราชสีมา
		else if(idState==8){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78,38,22));}//ชัยภูมิ
		else if(idState==21){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78,44,38));}//เลย
		else if(idState==5){result=new HashSet<Integer>(Arrays.asList(1,5,8,14,17,21,24,25,27,28,33,34,52,59,65,68,69,73,75,78,61));}//บุรีรัมย์
		else if(idState==10){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76));}//เชียงใหม่
		else if(idState==11){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76));}//เชียงราย
		else if(idState==15){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76));}//กำแพงเพชร
		else if(idState==19){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76));}//ลำปาง
		else if(idState==20){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76));}//ลำพูน
		else if(idState==23){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76));}//แม่ฮ่องสอน
		else if(idState==31){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76));}//น่าน
		else if(idState==41){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76));}//พะเยา
		else if(idState==43){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76));}//พิจิตร
		else if(idState==45){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76));}//แพร่
		else if(idState==62){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76));}//สุโขทัย
		else if(idState==71){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76));}//อุตรดิตถ์
		else if(idState==76){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76,16));}//ตาก
		else if(idState==70){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76,16,63,7));}//อุทัยธานี
		else if(idState==44){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76,21));}//พิษณุโลก
		else if(idState==29){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76,22,58,7));}//นครสวรรค์
		else if(idState==38){result=new HashSet<Integer>(Arrays.asList(10,11,15,19,20,23,29,31,38,41,43,44,45,62,70,71,76,8,22));}//เพชรบูรณ์
		else if(idState==18){result=new HashSet<Integer>(Arrays.asList(13,18,30,32,37,39,40,46,49,57,60,64,66,72));}//กระบี่
		else if(idState==30){result=new HashSet<Integer>(Arrays.asList(13,18,30,32,37,39,40,46,49,57,60,64,66,72));}//นครศรีธรรมราช
		else if(idState==32){result=new HashSet<Integer>(Arrays.asList(13,18,30,32,37,39,40,46,49,57,60,64,66,72));}//นราธิวาส
		else if(idState==37){result=new HashSet<Integer>(Arrays.asList(13,18,30,32,37,39,40,46,49,57,60,64,66,72));}//ปัตตานี
		else if(idState==39){result=new HashSet<Integer>(Arrays.asList(13,18,30,32,37,39,40,46,49,57,60,64,66,72));}//พังงา
		else if(idState==40){result=new HashSet<Integer>(Arrays.asList(13,18,30,32,37,39,40,46,49,57,60,64,66,72));}//พัทลุง
		else if(idState==46){result=new HashSet<Integer>(Arrays.asList(13,18,30,32,37,39,40,46,49,57,60,64,66,72));}//ภูเก็ต
		else if(idState==49){result=new HashSet<Integer>(Arrays.asList(13,18,30,32,37,39,40,46,49,57,60,64,66,72));}//ระนอง
		else if(idState==57){result=new HashSet<Integer>(Arrays.asList(13,18,30,32,37,39,40,46,49,57,60,64,66,72));}//สตูล
		else if(idState==60){result=new HashSet<Integer>(Arrays.asList(13,18,30,32,37,39,40,46,49,57,60,64,66,72));}//สงขลา
		else if(idState==64){result=new HashSet<Integer>(Arrays.asList(13,18,30,32,37,39,40,46,49,57,60,64,66,72));}//สุราษฎร์ธานี
		else if(idState==66){result=new HashSet<Integer>(Arrays.asList(13,18,30,32,37,39,40,46,49,57,60,64,66,72));}//ตรัง
		else if(idState==72){result=new HashSet<Integer>(Arrays.asList(13,18,30,32,37,39,40,46,49,57,60,64,66,72));}//ยะลา
		else if(idState==13){result=new HashSet<Integer>(Arrays.asList(13,18,30,32,37,39,40,46,49,57,60,64,66,72,48));}//ชุมพร
		else if(idState==2){result=new HashSet<Integer>(Arrays.asList(2,4,7,16,22,42,48,50,55,56,58,63,74));}//อ่างทอง
		else if(idState==42){result=new HashSet<Integer>(Arrays.asList(2,4,7,16,22,42,48,50,55,56,58,63,74));}//เพชรบุรี
		else if(idState==48){result=new HashSet<Integer>(Arrays.asList(2,4,7,16,22,42,48,50,55,56,58,63,74,13));}//ประจวบคีรีขันธ์
		else if(idState==50){result=new HashSet<Integer>(Arrays.asList(2,4,7,16,22,42,48,50,55,56,58,63,74,26,54));}//ราชบุรี
		else if(idState==56){result=new HashSet<Integer>(Arrays.asList(2,4,7,16,22,42,48,50,55,56,58,63,74,28));}//สระบุรี
		else if(idState==58){result=new HashSet<Integer>(Arrays.asList(2,4,7,16,22,42,48,50,55,56,58,63,74,29));}//สิงห์บุรี
		else if(idState==22){result=new HashSet<Integer>(Arrays.asList(2,4,7,16,22,42,48,50,55,56,58,63,74,29,38,8,28));}//ลพบุรี
		else if(idState==4){result=new HashSet<Integer>(Arrays.asList(2,4,7,16,22,42,48,50,55,56,58,63,74,36,35));}//พระนครศรีอยุธยา
		else if(idState==74){result=new HashSet<Integer>(Arrays.asList(2,4,7,16,22,42,48,50,55,56,58,63,74,47,6,28,36));}//นครนายก
		else if(idState==55){result=new HashSet<Integer>(Arrays.asList(2,4,7,16,22,42,48,50,55,56,58,63,74,54));}//สมุทรสงคราม
		else if(idState==63){result=new HashSet<Integer>(Arrays.asList(2,4,7,16,22,42,48,50,55,56,58,63,74,70,26));}//สุพรรณบุรี
		else if(idState==7){result=new HashSet<Integer>(Arrays.asList(2,4,7,16,22,42,48,50,55,56,58,63,74,70,29));}//ชัยนาท
		else if(idState==16){result=new HashSet<Integer>(Arrays.asList(2,4,7,16,22,42,48,50,55,56,58,63,74,70,76,26));}//กาญจนบุรี
		else if(idState==77){result=new HashSet<Integer>(Arrays.asList(26,35,36,53,54,77));}//กรุงเทพมหานคร
		else if(idState==26){result=new HashSet<Integer>(Arrays.asList(35,54,50,16,63,4,77));}//นครปฐม
		else if(idState==9){result=new HashSet<Integer>(Arrays.asList(6,9,12,47,51,61,67));}//จันทบุรี
		else if(idState==12){result=new HashSet<Integer>(Arrays.asList(6,9,12,47,51,61,67));}//ชลบุรี
		else if(idState==51){result=new HashSet<Integer>(Arrays.asList(6,9,12,47,51,61,67));}//ระยอง
		else if(idState==67){result=new HashSet<Integer>(Arrays.asList(6,9,12,47,51,61,67));}//ตราด
		else if(idState==61){result=new HashSet<Integer>(Arrays.asList(6,9,12,47,51,61,67,5,28));}//สระแก้ว
		else if(idState==6){result=new HashSet<Integer>(Arrays.asList(6,9,12,47,51,61,67,74));}//ฉะเชิงเทรา
		else if(idState==47){result=new HashSet<Integer>(Arrays.asList(6,9,12,47,51,61,67,74,28));}//ปราจีนบุรี
		else if(idState==54){result=new HashSet<Integer>(Arrays.asList(77,26,50,55));}//สมุทรสาคร
		else if(idState==36){result=new HashSet<Integer>(Arrays.asList(77,35,74,4));}//ปทุมธานี
		else if(idState==35){result=new HashSet<Integer>(Arrays.asList(77,36,26));}//นนทบุรี
		else if(idState==53){result=new HashSet<Integer>(Arrays.asList(77,6));}//สมุทรปราการ 
		return result;
	}

}
