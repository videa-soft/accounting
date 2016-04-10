package ir.visoft.accounting.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Amir
 */
public class DateUtil {


    public static Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    public static String getDateAsString(Date date, String separator) {
        String dateAsString = "";
        Calendar calendar = dateToCalendar(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        dateAsString = calendar.get(Calendar.DAY_OF_MONTH) + separator + month + separator + calendar.get(Calendar.YEAR);
        return dateAsString;
    }

    public static String getDateAsString(Date date) {
        return getDateAsString(date, "/");
    }



    public static Calendar jalaliToGregorianDate(JalaliDate jalaliDate) {
        Calendar gregorianCalendar = Calendar.getInstance();
        CalendarTool calendarTool = new CalendarTool();
        calendarTool.setIranianDate(jalaliDate.getYear(), jalaliDate.getMonth(), jalaliDate.getDay());
        gregorianCalendar.set(calendarTool.getGregorianYear(), calendarTool.getGregorianMonth(), calendarTool.getGregorianDay());
        return gregorianCalendar;
    }


    public static JalaliDate gregorianToJalali(Date date) {
        Calendar calendar = dateToCalendar(date);
        CalendarTool calendarTool = new CalendarTool(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1,calendar.get(Calendar.DAY_OF_MONTH));
        calendarTool.getIranianDate();
        return new JalaliDate(calendarTool.getIranianYear(), calendarTool.getIranianMonth(), calendarTool.getIranianDay());
    }

}
