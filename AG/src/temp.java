import java.util.ArrayList;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;
import java.util.*;

public class temp {

    ArrayList<process> usage() {
        Scanner scanner=new Scanner(System.in);
        ArrayList<process>operations =new ArrayList<process>();
        System.out.println("please enter the number of processe"); 
        int num =scanner.nextInt();
        for(int i=0;i<num;i++){
            System.out.println("enter the data of process number "+i+" :-");
            System.out.println("enter the Quantum time :");
            int Quantum_time=scanner.nextInt();
            System.out.println("enter process name: ");
            String name=scanner.next();
            System.out.println("enter arrival time: ");
            int arr_time =scanner.nextInt();
            System.out.println("enter Burst time:" );
            int burs_time=scanner.nextInt();
            System.out.println("enter priority of process: " );
            int priority=scanner.nextInt();
            process operation=new process(name,arr_time,burs_time,Quantum_time,priority);
            operations.add(operation);


        }
        return operations;
    }
}