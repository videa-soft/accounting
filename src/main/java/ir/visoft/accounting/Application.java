package ir.visoft.accounting;

import ir.visoft.accounting.util.DateUtil;
import ir.visoft.accounting.util.JalaliDate;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Amir
 */
public class Application {

    public static void main(String[] args) {
        System.out.println("Application started...!");

        //Gregorian to Jalali
        Date date = new Date();
        JalaliDate currentJalaliDate = DateUtil.gregorianToJalali(date);
        System.out.println("current jalali date: " + currentJalaliDate.getYear() + "/" + currentJalaliDate.getMonth() + "/" + currentJalaliDate.getDay());

        //Jalali to Gregorian
        JalaliDate jalaliDate = new JalaliDate(1394, 10 ,10);
        Calendar gregorianCalendar = DateUtil.jalaliToGregorianDate(jalaliDate);
        System.out.println("gregorian date for 1394/10/10 is: " + gregorianCalendar.get(Calendar.YEAR) + "/" + (gregorianCalendar.get(Calendar.MONTH) + 1) + "/" + gregorianCalendar.get(Calendar.DAY_OF_MONTH));

    }

}
