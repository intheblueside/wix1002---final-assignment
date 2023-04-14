/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.labassess;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

public class AverageTime extends Application{
    
    static int MAX=100000;
    static String[] jobId;
    static String[] timeStart ;
    static String[] timeEnd ;
    static String[] finalTimeEnd;
    static String[] finalTimeStart;
    static double[] extTime;
    static double avrDuration;
    static int total =0;
    static double totalDuration = 0;
    static int lessTenSec=0 , less30Min=0;
    static int lessOneMin=0 , more30Min=0;
    
    public static void findTime() throws FileNotFoundException, IOException{
        
        String fileName = "extracted_log.txt";
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String line;
        jobId = new String[MAX];
        timeStart = new String[MAX];
        timeEnd = new String[MAX];
//        finalTimeEnd;
//        finalTimeStart;
        int counter = 0;
            
        
        while ((line = br.readLine()) != null){
            String checkStart = "_slurm_rpc_submit_batch_job: JobId=";
            String checkFinal1 = "_job_complete: JobId=";
            String checkFinal2 = "done";
            String time = line.substring(1,23);
            
            //check if the line has _slurm_rpc_submit_batch_job: JobId=
            if(line.contains(checkStart)){
                jobId[counter] = line.substring(61, 61+5);
                timeStart[counter] = time;
                counter++;
                }
                
            //check if the line has _job_complete: JobId=
            if(line.contains(checkFinal1)&&line.contains(checkFinal2)){
                String test = line.substring(47,47+5);
                int insert;
                //check jobId same
                for (insert = 0;insert < jobId.length;insert++) {
                    if (test.equals(jobId[insert])) 
                        timeEnd[insert] = time;
                }
            }
        }
        
        //count how many total using timeEnd
        for(counter = 0;counter < MAX;counter++){
            if(timeEnd[counter] != null)
                total++;
        }
        
        //Eliminate if there is any null in timeEnd
        finalTimeEnd = new String[total];
        int element = 0;
        finalTimeStart = new String[total];
        for(counter = 0;counter < MAX;counter++){
            if(timeEnd[counter] != null){
                finalTimeEnd[element] = timeEnd[counter];
                finalTimeStart[element++] = timeStart[counter];
            }
        }
        
        Time t = new Time();
        extTime = new double[total];
        for(counter = 0;counter < total;counter++){
            t.insertTime(finalTimeStart[counter], finalTimeEnd[counter]);
            extTime[counter] = t.tempoh();
        }
        
        
        for(counter = 0;counter < total;counter++){
            //System.out.println(extTime[counter]);
            if(extTime[counter]<10){
                lessTenSec++;
            }
            else if(extTime[counter]>=10&&extTime[counter]<60){
                lessOneMin++;
            }
            else if(extTime[counter]>=60&&extTime[counter]<1800){
                less30Min++;
            }
            else if(extTime[counter]>=1800){
                more30Min++;
            }
            totalDuration += extTime[counter];
        }
//        for(counter = 0;counter < total;counter++){
//            System.out.printf("%-15s | %-25s | %-25s | %f",jobId[counter],finalTimeStart[counter],finalTimeEnd[counter],extTime[counter]);
//            System.out.println();
//            
//        }
        
        avrDuration = totalDuration/(double)total;
        System.out.println("AVERAGE: " + avrDuration);
        //System.out.println(total);
    
    }
    
    
    @Override
   public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new Group());
        stage.setTitle("Average Execution Time");
        stage.setWidth(500);
        stage.setHeight(500);
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        new PieChart.Data("less than 30 second", lessTenSec),
        new PieChart.Data("30 second to 1 minute",lessOneMin),
        new PieChart.Data("1 minute to 30 minute", less30Min),
        new PieChart.Data("More than 30 minute",more30Min));
        
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Average Time Execution");
        
       ((Group) scene.getRoot()).getChildren().add(chart);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) throws Exception{
        AverageTime av = new AverageTime();
        av.findTime();
        launch(args);
        
    }
    
}

