/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.labassess;
import com.mycompany.labassess.MainPage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class JobGraph extends Application{
    
    final static String june = "JUNE";
    final static String july = "JULY";
    final static String aug = "AUGUST";
    final static String sept = "SEPTEMBER";
    final static String oct = "OCTOBER";
    final static String nov = "NOVEMBER";
    final static String dec = "DECEMBER";
    
    static int allocateMonth6=0;
    static int allocateMonth7=0;
    static int allocateMonth8=0;
    static int allocateMonth9=0;
    static int allocateMonth10=0;
    static int allocateMonth11=0;
    static int allocateMonth12=0;
    
    static int compMonth6=0; 
    static int compMonth7=0;
    static int compMonth8=0;
    static int compMonth9=0;
    static int compMonth10=0;
    static int compMonth11=0; 
    static int compMonth12=0;
    
    static int countA=0;
    static int countC=0;
    
    
    public void jobCreatebyMonth() throws IOException{
        //find number of jobs by month 
        //allocated jobs
        
        String[] monthName = {"June", "July", "August", "September","October", "November","December"} ;
        String line1;
        String enter="";
        int counter=0;
        
        try{
            BufferedReader read = new BufferedReader(new FileReader("extracted_log.txt"));
            PrintWriter pwA = new PrintWriter(new FileOutputStream("allocate.txt"));
            Pattern pA = Pattern.compile("Allocate JobId");
            while ((line1 = read.readLine()) != null) {
                
                Matcher m1 = pA.matcher(line1);
                
                if (m1.find()) {
                    
                    countA++;
                    //System.out.println(line.substring(48,53));
                    enter = line1.substring(48,53);

                    pwA.println(enter);

                    if(line1.contains("2022-06")){
                        allocateMonth6++;
                    }else if(line1.contains("2022-07")){
                        allocateMonth7++;
                    }else if(line1.contains("2022-08")){
                        allocateMonth8++;
                    }else if(line1.contains("2022-09")){
                        allocateMonth9++;
                    }else if(line1.contains("2022-10")){
                        allocateMonth10++;
                    }else if(line1.contains("2022-11")){
                        allocateMonth11++;
                    }else if(line1.contains("2022-12")){
                        allocateMonth12++;
                    }
                }
                
            }
            read.close();
            pwA.close();
            
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }catch(IOException e){
            System.out.println("Error parsing file");
        }
        
        System.out.println("Total Jobs Allocated: " + countA);
        
    }
    
    public void jobCompletebyMonth() throws IOException{
        //find number of jobs by month 
        //complete jobs // with done only
        
        String line2;
        String enter="";
        int countB=0;
        
        try{
            BufferedReader read = new BufferedReader(new FileReader("extracted_log.txt"));
            PrintWriter pwB = new PrintWriter(new FileOutputStream("complete.txt"));
            Pattern pB = Pattern.compile("_job_complete");
            Pattern pC = Pattern.compile("done");
            
            while ((line2 = read.readLine()) != null) {
                
                Matcher m1 = pB.matcher(line2);
                Matcher m2 = pC.matcher(line2);
                
                if (m1.find()) {
                    countB++;
                    
                    if(m2.find()){
                        
                       countC++; 
                       enter = line2.substring(47,52); 
                       pwB.println(enter);
 

                        if(line2.contains("2022-06")){
                            compMonth6++;
                        }else if(line2.contains("2022-07")){
                            compMonth7++;
                        }else if(line2.contains("2022-08")){
                            compMonth8++;
                        }else if(line2.contains("2022-09")){
                            compMonth9++;
                        }else if(line2.contains("2022-10")){
                            compMonth10++;
                        }else if(line2.contains("2022-11")){
                            compMonth11++;
                        }else if(line2.contains("2022-12")){
                            compMonth12++;
                        }
                    }
                }
                
            }
            read.close();
            pwB.close();
            
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }catch(IOException e){
            System.out.println("Error parsing file");
        }
        
        System.out.println("\nTotal Jobs Completed: "+countC);
        
    }
    

    @Override
    public void start(Stage stage){
        
        stage.setTitle("Bar graph of Jobs Created/ Completed");
        
        //number of axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        //creating graph
        final BarChart<String,Number> bc = new BarChart<String, Number>(xAxis,yAxis);
        bc.setTitle("Jobs Created and Jobs Completed \n Total Jobs Allocated " + countA + 
                "\tTotal Jobs Complete " +countC);
       
        xAxis.setLabel("Months");
        yAxis.setLabel("Number of jobs");
        
        XYChart.Series s1 = new XYChart.Series();
        s1.setName("jobs created");
        s1.getData().add(new XYChart.Data(june, allocateMonth6));
        s1.getData().add(new XYChart.Data(july, allocateMonth7));
        s1.getData().add(new XYChart.Data(aug, allocateMonth8));
        s1.getData().add(new XYChart.Data(sept, allocateMonth9));
        s1.getData().add(new XYChart.Data(oct, allocateMonth10));
        s1.getData().add(new XYChart.Data(nov, allocateMonth11));
        s1.getData().add(new XYChart.Data(dec, allocateMonth12));
        
        XYChart.Series s2 = new XYChart.Series();
        s2.setName("jobs completed");
        s2.getData().add(new XYChart.Data(june, compMonth6));
        s2.getData().add(new XYChart.Data(july, compMonth7));
        s2.getData().add(new XYChart.Data(aug, compMonth8));
        s2.getData().add(new XYChart.Data(sept, compMonth9));
        s2.getData().add(new XYChart.Data(oct, compMonth10));
        s2.getData().add(new XYChart.Data(nov, compMonth11));
        s2.getData().add(new XYChart.Data(dec, compMonth12));
        
        Scene scene = new Scene(bc,800,600);
        bc.getData().addAll(s1,s2);
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) throws Exception {

        JobGraph j = new JobGraph();
        j.jobCreatebyMonth();
        j.jobCompletebyMonth();
        
        launch(args);
    }
    
}
