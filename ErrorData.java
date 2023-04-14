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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
/**
 *
 * @author USER
 */
public class ErrorData extends Application{

    static int numGpu=0 , notrespon=0, pending=0;
    static int slurmInva=0, security=0 , other=0;
    static int slurmComp=0, epilogError=0;
    static int partitionNull=0,notFound=0;
    static int slurmOpe=0, node=0;
    
    public static void findError(){

        ArrayList<String> error = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("extracted_log.txt"))) {
             // Set the pattern to match and the metric to count
        int count4=0;
        
        // Read each line of the log file
        String line;
            while ((line = reader.readLine()) != null) {

            if(line.contains("error")){
               count4++;
               error.add(line);
                //System.out.println(line);
            }
             
            }
            System.out.println(" "+count4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        arrayTest(error);
    }
    public static void arrayTest(ArrayList<String> a){
        
        for(String i: a){
            if(i.contains("gpu")){
                numGpu++;
            }else if(i.contains("Invalid")&&i.contains("job id ")){
                slurmInva++;
            }else if(i.contains("already completing")&&i.contains("completed")){
                slurmComp++;
            }else if(i.contains("partition='(null)'")){
                partitionNull++;
            }else if(i.contains("slurm")&&i.contains("recv")){
                slurmOpe++;
            }else if(i.contains("Nodes")&&i.contains("not responding")){
                notrespon++;
            }else if(i.contains("not found")){
                notFound++;
            }else if(i.contains("Security")&&i.contains("violation")){
                security++;
            }else if(i.contains("epilog error")){
                epilogError++;
            }else if(i.contains("Node")){
                node++;
            }else if(i.contains("Registered PENDING")){
                pending++;
            }else{
              other++;  
            }
            //System.out.println(i);       
            
        }
    }
    
    public void UserError(){
        ArrayList<String> error = new ArrayList<>();


        try (BufferedReader reader = new BufferedReader(new FileReader("extracted_log.txt"))) {
             // Set the pattern to match and the metric to count
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        
        // Read each line of the log file
        String line;
            while ((line = reader.readLine()) != null) {

            if (line.contains("error")) {
                if(line.contains("account='free'")&&line.contains("partition='(null)'")){
                error.add(line);
                //System.out.println(line);
                }
            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int count1=0;
        String[] user=new String[143];
        for(String i : error){ 
            String checkUser = userError(i);
           user[count1]=checkUser;
           count1++;
            //System.out.println(i); 
        }
        Arrays.sort(user);

        Map<String, Integer> duplicateCountMap = Arrays
                .stream(user)
                .collect(
                        Collectors.toMap(Function.identity(), company -> 1, Math::addExact)
                        );
 

        duplicateCountMap
        .entrySet()
        .stream();
        //.forEach(entry -> System.out.println(entry.getKey()));

        duplicateCountMap
        .entrySet()
        .stream()
        .filter(entry -> entry.getValue() > 1);
        //.forEach(entry -> System.out.println(entry.getKey()));

        // 2.3 print Map with duplicate count
        System.out.printf("%-25s | %10s \n","User","CountError");
        System.out.printf("--------------------------------------\n");
        duplicateCountMap.forEach(
                (key, value) -> System.out.printf("%-25s | %3s \n",key, value)
                );

         System.out.printf("%-25s | %5d \n","TOTAL",error.size());
    }
    
    public static String userError(String line){
        String[]parts = line.split(" ");
        for(String i : parts){
            if(i.contains("user='")){
                return i;  
            }
            
        }
       return null;
    }
    
   @Override
   public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new Group());
        stage.setTitle("Other Statistical Data");
        stage.setWidth(500);
        stage.setHeight(500);
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        new PieChart.Data("Error:Number of gpu", numGpu),
        new PieChart.Data("Error:Invalid JobID",slurmInva),
        new PieChart.Data("Error:Job already complete", slurmComp),
        new PieChart.Data("Error:Partition Null",partitionNull),
        new PieChart.Data("Error:Nodes Not responding ",notrespon),
        new PieChart.Data("Error:Not found ",notFound),
        new PieChart.Data("Error:Security Violation ",security),
        new PieChart.Data("Error:slurm recv ",slurmOpe),
        new PieChart.Data("Error:Epilog Error ",epilogError),
        new PieChart.Data("Error:Node appears ",node),
        new PieChart.Data("Error:Registered Pending ",pending),
        new PieChart.Data("Error:Other error ",other));
        
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Cause of Error");
        
       ((Group) scene.getRoot()).getChildren().add(chart);
        
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        ErrorData se = new ErrorData();
        se.findError();
        se.UserError();
        launch(args);
    }
}
