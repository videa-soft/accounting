package ir.visoft.accounting.util;

/**
 * @author Amir
 */
public class JalaliDate {

    private Integer day;
    private Integer month;
    private Integer year;

    public JalaliDate(Integer year, Integer month, Integer day) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
