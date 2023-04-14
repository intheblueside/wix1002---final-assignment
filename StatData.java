/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.labassess;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class StatData extends Application{
    
    static int countD=0;
    static int countE=0;
    static int countF=0;
    static int countG=0;
    static int countH=0;
    
    public static void otherData(){
        String line4="";
        try{
            BufferedReader read = new BufferedReader(new FileReader("extracted_log.txt"));
            Pattern pD = Pattern.compile("REQUEST_KILL_JOB");
            Pattern pE = Pattern.compile("cleanup_completing");
            Pattern pF = Pattern.compile("Recovered JobId");
            Pattern pG = Pattern.compile("Time limit exhausted");
            Pattern pH = Pattern.compile("Requeuing ");
            
            while ((line4 = read.readLine()) != null) {
                Matcher m1 = pD.matcher(line4);
                Matcher m2 = pE.matcher(line4);
                Matcher m3 = pF.matcher(line4);
                Matcher m4 = pG.matcher(line4);
                Matcher m5 = pH.matcher(line4);
                
                if (m1.find()) {
                    countD++;   
                }
                if(m2.find()){
                    countE++;
                }
                if(m3.find()){
                    countF++;
                }
                if(m4.find()){
                    countG++;
                }
                if(m5.find()){
                    countH++;
                }
            }
            read.close();
        }catch(IOException e){
            System.out.println("Error parsing file");
        }
    }
    
    
    @Override
    public void start(Stage stage){
        
        Scene scene = new Scene(new Group());
        stage.setTitle("Other Statistical Data");
        stage.setWidth(500);
        stage.setHeight(500);
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        new PieChart.Data("Jobs: Request for job kill", countD),
        new PieChart.Data("Jobs: Completion", countE),
        new PieChart.Data("Jobs: Recovered", countF),
        new PieChart.Data("Jobs: Time Limit Exhausted", countG),
        new PieChart.Data("Jobs: Requeuing", countH));
        
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Statistical data");
        
        ((Group) scene.getRoot()).getChildren().add(chart);
        
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) throws Exception {
        StatData s = new StatData();
        s.otherData();
        launch(args);
    }
    
    
    
}
