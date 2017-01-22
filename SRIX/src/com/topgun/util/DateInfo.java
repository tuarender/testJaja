package com.topgun.util;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.text.*;
import java.util.Calendar;

public class DateInfo {

  private String sDate;

  public String getSDate() {
    return sDate;
  }
  public void setSDate(String sDate) {
    this.sDate = sDate;
  }

  public String nowDateFormat (String strFormat) {
    Calendar c = Calendar.getInstance(new Locale("en"));
      Date today = c.getTime();
      SimpleDateFormat dataformat = new SimpleDateFormat(strFormat,new Locale("en")); //Create format Date
    sDate = dataformat.format( today ); //Convert Date to String (format(Date))
    return sDate;
 }

  public String convertFormat (String seperate,String dbDate) { //Convert dbFormat date(yyyyMMdd) to dd/MM/yyyy

    if (dbDate.length()>= 8){ //Format yyyymmdd
      String year = dbDate.substring(0, 4);
      String mo = dbDate.substring(4, 6);
      String day = dbDate.substring(6, 8);
      sDate = day + seperate + mo + seperate + year; //Example result if seperate = "/" = dd/mm/yyyy
    }
    else {
      sDate = dbDate;
    }
    return sDate;
}

  public String dateFormat (String strFormat,String date,String mo,String year) {
    //String strDate = "November 1, 2000"; Example Format
    String strDate = mo+" "+date+", "+year;
    DateFormat df = DateFormat.getDateInstance();
    try {
      Date myDate = df.parse(strDate);  //Convert String to Date (parse(string) is method of DateFormat)
      SimpleDateFormat dataformat = new SimpleDateFormat(strFormat); //Create format Date
      sDate = dataformat.format(myDate); //Convert Date to String (format(Date))

      //System.out.println("sdate="+sDate+" my date= "+myDate);
    }
    catch(ParseException e) {

    }
    return sDate;
 }


 public String addDate(int number) { // **----by TUI----**  find date after/before today by +- with number
                                          // and return with format dd/MM/yy
        Date now = new Date();
        Calendar car = new GregorianCalendar();
        car.setTime(now);
        car.add(Calendar.DATE, number);
        SimpleDateFormat dataformat = new SimpleDateFormat("dd/MM/yy"); //Create format Date
        String day = dataformat.format(car.getTime());
        /*
        String yy1 = String.valueOf(car.get(Calendar.YEAR));
        String mm1 = String.valueOf(car.get(Calendar.MONTH) + 1);
       //Make sure that format of month is correct
       if (Integer.parseInt(mm1) < 10)
         mm1 = "0" + mm1;
       String dd1 = String.valueOf(car.get(Calendar.DATE));
       //Make sure that format of date is correct
       if (Integer.parseInt(dd1) < 10)
         dd1 = "0" + dd1;
       String day = dd1+"/"+mm1+"/"+yy1;*/
        return day;
      }

      public String addDate2(int number,Date date) {
Calendar car = new GregorianCalendar();
car.setTime(date);
car.add(Calendar.DATE, number);
SimpleDateFormat dataformat = new SimpleDateFormat("dd/MM/yy"); //Create format Date
String day = dataformat.format(car.getTime());
return day;
}

 public String dateFormat (String strFormat,Date tmpDate) {
        try {
          SimpleDateFormat dataformat = new SimpleDateFormat(strFormat); //Create format Date
          sDate = dataformat.format(tmpDate);
        }
        catch(Exception ex){
         ex.printStackTrace();
        }
        return sDate;
     }
     public String [] findDate(String con,String date1,String date2) { //find Strat Date and End Date by condition
           Date day = new Date();
           String [] conDate = new String [2];
           String nowDate = nowDateFormat("dd/MM/yy");
           String endDate = nowDate;
           String strDate = "";
           int condition = Integer.parseInt(con);
           int dayInWeek = day.getDay();
           if (dayInWeek == 0) dayInWeek=6;
           else dayInWeek = dayInWeek-1;

           switch (condition) {
             case 1: // today
               strDate = nowDate;
             break;
             case 2: //from yesterday to now
               strDate = addDate(-1);
             break;
             case 3: //from last 3 day to now
               strDate = addDate(-2);
             break;
             case 4: //from last 4 day to now
               strDate = addDate(-3);
             break;
             case 5: //from last 5 day to now
               strDate = addDate(-4);
             break;
             case 6: //from last 6 day to now
               strDate = addDate(-5);
             break;
             case 7: //from last 7 day to now
               strDate = addDate(-6);
             break;
             case 8: //from monday to sunday (this week)
               strDate = addDate(-dayInWeek);
             break;
             case 9: //from last monday to last sunday (last week)
               strDate = addDate(+dayInWeek);
             break;
             case 10: //this month
               Calendar  c10= Calendar.getInstance(new Locale("en"));
               String y10 = String.valueOf(c10.get(Calendar.YEAR));
               int m10 = c10.get(Calendar.MONTH) + 1;
               String m101 = String.valueOf(m10 < 10 ? "0" + String.valueOf(m10) : String.valueOf(m10));
               strDate = "01"+"/"+m101+"/"+y10.substring(2,4);
             break;
             case 11: //from last month to now
               Calendar  c11= Calendar.getInstance(new Locale("en"));
               String y11 = String.valueOf(c11.get(Calendar.YEAR));
               int m11 = c11.get(Calendar.MONTH);
               if (m11 == 0)
               {
                 m11 = 12;
                 y11 = String.valueOf((Integer.parseInt(y11))-1);
               }
               String m111 = String.valueOf(m11 < 10 ? "0" + String.valueOf(m11) : String.valueOf(m11));
               strDate = "01"+"/"+m111+"/"+y11.substring(2,4);
             break;
           case 12: // all
             strDate="0";
             endDate="0";
             break;
           case 13: //from d1/m1/y1 to d2/m2/y2
             if (Integer.parseInt(date1) > Integer.parseInt(date2)) {
               strDate = convertFormat("/",date2);
               endDate = convertFormat("/",date1);
             }
             else {
               strDate = convertFormat("/",date1);
               endDate = convertFormat("/",date2);
             }
             break;
           }
           conDate[0] = strDate;
           conDate[1] = endDate;
           return conDate;
         }

         public static int compareDate(Date date1, Date date2){


                        int count = 0;
                        boolean equal = false;
                        int ans = 0;
                        Date d1=(Date)date1.clone();
                        Date d2=(Date)date2.clone();

                        ans = d1.compareTo(d2);

                        if(ans == 0){
                                equal = true;
                        }else if(ans == -1){
                                while(!equal){
                                        ans = d1.compareTo(d2);
                                        if(ans != 0){
                                                d1.setDate(d1.getDate()+1);
                                                count++;
                                        }else{
                                                equal = true;
                                        }
                                }
                        }else if(ans == 1){
                                while(!equal){
                                        ans = d1.compareTo(d2);
                                        if(ans != 0){
                                                d1.setDate(d1.getDate()-1);
                                                count++;
                                        }else{
                                                equal = true;
                                        }
                                }
                        }
                        return (count+1);
  }
 public static void main(String[] args) {
      DateInfo dateinfo = new DateInfo();
      String dateTmp = dateinfo.nowDateFormat("yyyy");

      Date date = new Date();


      String email = "patchara.v@g-able.com";
      String test="";
     String emailTmp = email.replace('.',' ');
     String emailTmp2 = emailTmp.replaceAll(" ","");

      }
 }
