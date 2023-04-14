/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.labassess;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author USER
 */
public class Time {
    
    //      START   FINAL
    //      day
    //      Seconds
    private double[][] duration;
    
    //      FINAL   START
    //      Date    Time
    private String[] dateAndTime;
    
    //CONSTRUCTORS
    public Time() {
        this.duration = new double[2][2];
        this.dateAndTime = new String[2];
    }
    
    //Initializers
    public void insertTime(String start,String finish){
    for(int repeat = 0; repeat <=1 ; repeat++){
        int ali = 0;
        //Read Parameter
        String test = repeat == 0 ?start:finish;
        LocalDateTime myDateObj = LocalDateTime.parse(test);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.nnn");
        String formattedDate = myDateObj.format(myFormatObj);
        this.dateAndTime[repeat] = formattedDate;
        
        //INSERT START AND FINAL, DURATION
        String[] DateAndTime    = formattedDate.split(" ");
        String[] DayMonthYear   = DateAndTime[0].split("-");
        String[] HourMinSec     = DateAndTime[1].split(":");
        
        //COMPUTE TO CHANGE IN DAYS AND SECONDS
        this.duration[ali][repeat] = totalDays
        (DayMonthYear[0],DayMonthYear[1],DayMonthYear[2]);
        
        ali++;
        
        this.duration[ali][repeat] = totalSecs
        (HourMinSec[0],HourMinSec[1],HourMinSec[2]);
        }
    }
    
    public double tempoh(){
        double day = this.duration[0][1] - this.duration[0][0];
        if(this.duration[1][0] > this.duration[1][1]){
            day = day - 1;
            this.duration[1][1] += 24 * 60 * 60;
        }
        day = day * 24 * 60 * 60;
        return day +  this.duration[1][1] - this.duration[1][0];
    }
    
    public static double totalSecs(String hours, String mins, String seconds){
        double hour = Double.parseDouble(hours) * 60 * 60;
        double min = Double.parseDouble(mins) * 60;
        double second = Double.parseDouble(seconds);
        return hour + min + second;
    }
    
    public static int totalDays(String days, String month, String year){
        int totalday = Integer.parseInt(days);
        for(int counter = 1;counter < Integer.parseInt(month); counter++){
        switch(Integer.parseInt(month))
            {
                case 4:
                case 6:
                case 11:
                case 9: 
                    totalday += 30;
                case 2:
                {
                    int k = Integer.parseInt(year);
                    if((k%4 == 0 && k%100 != 0) || k%400 == 0)
                        totalday += 29;
                    else
                        totalday += 28;
                }
                default:
                    totalday += 31;
            }
        }
        return totalday;
    }
    
}
