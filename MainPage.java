/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.labassess;

import com.mycompany.labassess.StatData;
import com.mycompany.labassess.JobGraph;
import com.mycompany.labassess.ErrorData;
import com.mycompany.labassess.PartitionData;
import com.mycompany.labassess.AverageTime;
import java.util.Scanner;
import javafx.application.Application;


public class MainPage {

    
    public static void main(String[] args) throws Exception{
        
        System.out.println("------------------WELCOME TO SLURM------------------------");
        
        System.out.println("----------------------------------------------------------");  
        System.out.printf("%5s %20s ", "INPUT NUMBER", "PROCESS TO DISPLAY");  
        System.out.println();  
        System.out.println("----------------------------------------------------------");  
        System.out.format("%7s %36s", "1", " Jobs In Created and Completed");  
        System.out.format("\n%7s %25s", "2", " Jobs In Partitions");  
        System.out.format("\n%7s %20s", "3", " Jobs In Error");  
        System.out.format("\n%7s %40s", "4", " Jobs In Average Time of Execution");  
        System.out.format("\n%7s %25s", "5", " Jobs In Other Data");  
        System.out.println();
        System.out.println("----------------------------------------------------------");
        
        System.out.println("\n[Note*** Only one process can be displayed at one time***]");
        System.out.println("ENTER NUMBER OF PROCESS TO DISPLAY: ");
        
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        
        switch(input){
            case 1:
                //job create and job complete, go to class jobgraph 

                JobGraph j = new JobGraph();
                j.jobCreatebyMonth();
                j.jobCompletebyMonth();
                Application.launch(JobGraph.class,args);
                break;
            case 2:
                //find partitions in jobs, go to class PartitionData
                PartitionData p = new PartitionData();
                p.findPartition();
                Application.launch(PartitionData.class,args);
                break;
            case 3:
                //find number of errors, go to class errordata
                ErrorData e = new ErrorData();
                e.findError();
                e.UserError();
                Application.launch(ErrorData.class,args);
                break;
            case 4:
                //find average exe time, go to class averagetime
                AverageTime a = new AverageTime();
                a.findTime();
                Application.launch(AverageTime.class,args);
                break;
            case 5:
                //find other statistical data, go to class statdata
                StatData s = new StatData();
                s.otherData();
                Application.launch(StatData.class,args);
                break;
            default:
                System.out.println("*** Not A Valid Input ***");  
                
            }
        
            
        System.out.println("------------------------CLOSING SLURM-----------------------"); 
        System.out.println("\n*** To Display New Process Please Re-Run Program ***");  
    }      
}
