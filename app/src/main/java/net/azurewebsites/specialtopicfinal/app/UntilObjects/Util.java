package net.azurewebsites.specialtopicfinal.app.UntilObjects;


import java.util.Calendar;
import java.util.Date;


/**
 * Created by Mike on 01/06/2014.
 */
public class Util {
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public static int numOfDays(Date startDate, Date endDate) {
        return (int) ((startDate.getTime() - endDate.getTime()) / (1000 * 60 * 60 * 24));
    }

}
