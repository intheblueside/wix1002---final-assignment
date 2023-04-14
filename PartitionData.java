/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.labassess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author USER
 */
public class PartitionData extends Application{

    static int allocateGpu=0,allocateOpteron=0,allocateEpyc=0,allocateNull=0;
    static int startedGpu=0,startedOpteron=0,startedEpyc=0,startedNull=0;
    static int errorGpu=0,errorOpteron=0,errorEpyc=0,errorNull=0;
    
    static ArrayList<String> gpu = new ArrayList<>();
    static ArrayList<String> opetron = new ArrayList<>();
    static ArrayList<String> opyc = new ArrayList<>();
    static ArrayList<String> partitionNull = new ArrayList<>();
     
    static String sGpu,sAllocateGpu,sStartedGpu,sErrorGpu;
    static String sOpteron,sAllocateOpteron,sStartedOpteron,sErrorOpteron;
    static String sEpyc,sAllocateEpyc,sStartedEpyc,sErrorEpyc;
    static String sNull,sAllocateNull,sStartedNull,sErrorNull;
     
    public static void findPartition(){

        try (BufferedReader reader = new BufferedReader(new FileReader("extracted_log.txt"))) {
             // Set the pattern to match and the metric to count
            int count1 = 0;
            int count2 = 0;
            int count3 = 0;

            // Read each line of the log file
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("gpu")) {
                    // If it does, increment the count
                    count1++;
                    gpu.add(line);
                }else if (line.contains("opteron")) {
                    // If it does, increment the count
                    count2++;
                    opetron.add(line);
                }else if (line.contains("epyc")) {
                    // If it does, increment the count
                    count3++;
                    opyc.add(line);
                }else if(line.contains("partition='(null)'")){
                    partitionNull.add(line);
                    //System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        arrayTestGpu(gpu);
        arrayTestOpteron(opetron);
        arrayTestEpyc(opyc);
        arrayTestNull(partitionNull);
        convertString();
        partitionTable();
    }
     public static void arrayTestGpu(ArrayList<String> a){

         for (String i : a) {

            if(i.contains("error")){
                errorGpu++;
            }
            if(i.contains("Allocate")){
                allocateGpu++;
            }
            if(i.contains("_start_job")){
                startedGpu++;
            }
        }
    }
     public static void arrayTestOpteron(ArrayList<String> a){

         for (String i : a) {

            if(i.contains("error")){
                errorOpteron++;
            }
            if(i.contains("Allocate")){
                allocateOpteron++;
            }
            if(i.contains("_start_job")){
                startedOpteron++;
            }
        }
    }
     public static void arrayTestEpyc(ArrayList<String> a){

         for (String i : a) {

            if(i.contains("error")){
                errorEpyc++;
            }
            if(i.contains("Allocate")){
                allocateEpyc++;
            }
            if(i.contains("_start_job")){
                startedEpyc++;
            }
        }
    }
     public static void arrayTestNull(ArrayList<String> a){

         for (String i : a) {

            if(i.contains("error")){
                errorNull++;
            }
            if(i.contains("Allocate")){
                allocateNull++;
            }
            if(i.contains("_start_job")){
                startedNull++;
            }
        }
    }
     public static void convertString(){
        sGpu=Integer.toString(gpu.size());
        sAllocateGpu=Integer.toString(allocateGpu);
        sStartedGpu=Integer.toString(startedGpu);
        sErrorGpu=Integer.toString(errorGpu);
         
        sOpteron=Integer.toString(opetron.size());
        sAllocateOpteron=Integer.toString(allocateOpteron);
        sStartedOpteron=Integer.toString(startedOpteron);
        sErrorOpteron=Integer.toString(errorOpteron);
         
        sEpyc=Integer.toString(opyc.size());
        sAllocateEpyc=Integer.toString(allocateEpyc);
        sStartedEpyc=Integer.toString(startedEpyc);
        sErrorEpyc=Integer.toString(errorEpyc);
         
        sNull=Integer.toString(partitionNull.size());
        sAllocateNull=Integer.toString(allocateNull);
        sStartedNull=Integer.toString(startedNull);
        sErrorNull=Integer.toString(errorNull);
     }
     
     public static void partitionTable(){
        JFrame f;
        f=new JFrame();    
        String data[][]={ {"GPU",sGpu,sAllocateGpu,sStartedGpu,sErrorGpu},    
                              {"OPTERON",sOpteron,sAllocateOpteron,sStartedOpteron,sErrorOpteron},    
                              {"EPYC",sEpyc,sAllocateEpyc,sStartedEpyc,sErrorEpyc},
                              {"NULL",sNull,sAllocateNull,sStartedNull,sErrorNull}};    
        String column[]={"PARTITION","TOTAL","ALLOCATE","JOBSTARTED","ERROR"};         
        JTable jt=new JTable(data,column);    
        jt.setBounds(30,40,200,300);          
        JScrollPane sp=new JScrollPane(jt);    
        f.add(sp);          
        f.setSize(600,400);    
        f.setVisible(true);  
    
    }
    @Override
    public void start(Stage stage) throws Exception {
        CategoryAxis xAxis = new CategoryAxis();  
        xAxis.setCategories(FXCollections.<String>
        observableArrayList(Arrays.asList("Job Allocate", "Job Started", "Error", "Total")));
        xAxis.setLabel("category");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Partition");

        //Creating the Bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis); 
        barChart.setTitle("Comparison between partition");

        //Prepare XYChart.Series objects by setting data       
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("gpu");
        series1.getData().add(new XYChart.Data<>("Job Allocate", allocateGpu));
        series1.getData().add(new XYChart.Data<>("Job Started", startedGpu));
        series1.getData().add(new XYChart.Data<>("Error", errorGpu));
        series1.getData().add(new XYChart.Data<>("Total", gpu.size()));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Opteron");
        series2.getData().add(new XYChart.Data<>("Job Allocate", allocateOpteron));
        series2.getData().add(new XYChart.Data<>("Job Started", startedOpteron));
        series2.getData().add(new XYChart.Data<>("Error", errorOpteron));
        series2.getData().add(new XYChart.Data<>("Total", opetron.size()));

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("epyc");
        series3.getData().add(new XYChart.Data<>("Job Allocate", allocateEpyc));
        series3.getData().add(new XYChart.Data<>("Job Started", startedEpyc));
        series3.getData().add(new XYChart.Data<>("Error", startedEpyc));
        series3.getData().add(new XYChart.Data<>("Total", opyc.size()));

        XYChart.Series<String, Number> series4 = new XYChart.Series<>();
        series4.setName("epyc");
        series4.getData().add(new XYChart.Data<>("Job Allocate", allocateNull));
        series4.getData().add(new XYChart.Data<>("Job Started", startedNull));
        series4.getData().add(new XYChart.Data<>("Error", errorNull));
        series4.getData().add(new XYChart.Data<>("Total", partitionNull.size()));
        //Setting the data to bar chart       
        barChart.getData().addAll(series1, series2, series3);

        //Creating a Group object 
        Group root = new Group(barChart);

        //Creating a scene object
        Scene scene = new Scene(root, 600, 400);

        //Setting title to the Stage
        stage.setTitle("Bar Chart");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();        
    }
    
    
    public static void main(String[] args) {
       PartitionData partition = new PartitionData();
       partition.findPartition();
       launch(args);
    }
    
}
