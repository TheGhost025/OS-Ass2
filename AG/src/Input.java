import java.util.ArrayList;
import java.util.Scanner;

public class Input {

    ArrayList<process> input() {
        Scanner scanner=new Scanner(System.in);
        ArrayList<process>processes =new ArrayList<process>();
        System.out.println("Enter the max Number of processes:");
        int num =scanner.nextInt();
        for(int i=0;i<num;i++){
            System.out.println("Enter process name Process "+i+" :");
            String name=scanner.next();
            System.out.println("Enter Burst time Process "+i+" :");
            int burs_time=scanner.nextInt();
            System.out.println("Enter arrival time Process "+i+" :");
            int arr_time =scanner.nextInt();
            System.out.println("Enter priority Process "+i+" :");
            int priority=scanner.nextInt();
            System.out.println("Enter the Quantum time Process "+i+" :");
            int Quantum_time=scanner.nextInt();
            process operation=new process(name,arr_time,burs_time,Quantum_time,priority);
            processes.add(operation);


        }
        return processes;
    }
}