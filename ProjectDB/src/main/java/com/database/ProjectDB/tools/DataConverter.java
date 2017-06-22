package com.database.ProjectDB.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by seba on 2017-05-11.
 */
public class DataConverter {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyy-mm-dd");


    public static Date getTodayDate(){
        Date today = new Date();
        String data = new SimpleDateFormat("MM-dd-yyyy").format(today);
        LocalDate localDate = java.sql.Date.valueOf(LocalDate.parse(
                data ,
                DateTimeFormatter.ofPattern( "MM-dd-uuuu" )
        )).toLocalDate();
        Date dateToReturn = java.sql.Date.valueOf(localDate);
        return dateToReturn;
    }


    public static String getTime(){
        String time = new SimpleDateFormat("HH:MM:s").format(Calendar.getInstance().getTime());
        return "\n" + time + ": ";
    }

    public static Date convertDateFromDataPicker(LocalDate localDate){
        Date data = java.sql.Date.valueOf(localDate);
        return data;
    }

    public static Date convertStringToDate(String date) throws ParseException {
        Date dateToReturn = java.sql.Date.valueOf(date);
        return dateToReturn;
    }
}
