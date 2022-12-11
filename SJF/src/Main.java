import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int maxProcess,context;
        Scanner MyObj=new Scanner(System.in);
        System.out.println("Enter the max Number of processes: ");
        maxProcess=MyObj.nextInt();
        System.out.println("Enter the Context Switching: ");
        context=MyObj.nextInt();

        String Name[]=new String[maxProcess];
        int ArrivalTime[]=new int[maxProcess];
        int BurstTime[]=new int[maxProcess];
        int remain[]=new int[maxProcess];
        int TurnAroundTime[]=new int[maxProcess];
        int WaitingTime[]=new int[maxProcess];

        for(int i=0;i<maxProcess;i++){
            System.out.println("Enter the Name of Process"+(i+1)+": ");
            Name[i]=MyObj.next();
            System.out.println("Enter the Arrival Time of Process"+(i+1)+": ");
            ArrivalTime[i]=MyObj.nextInt();
            System.out.println("Enter the Burst Time of Process"+(i+1)+": ");
            BurstTime[i]=MyObj.nextInt();
            remain[i]=BurstTime[i];
        }




        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finish_time;
        boolean check = false;
        System.out.println("Processes execution order\r\n");

        // Process until all processes gets  completed
        while (complete != maxProcess) {

            for (int j = 0; j < maxProcess; j++)
            {
                if ((ArrivalTime[j] <= t) &&
                        (remain[j] < minm) && remain[j] > 0) {

                    System.out.println(Name[j]);
                    minm = remain[j];

                    shortest = j;
                    check = true;
                }
            }

            if (check == false) {
                t++;
                continue;
            }

            // Reduce remaining time by one
            remain[shortest]--;

            // Update minimum
            minm = remain[shortest];
            if (minm == 0)
                minm = Integer.MAX_VALUE;

            // If a process gets completely executed
            if (remain[shortest] == 0) {

                // Increment complete
                complete++;
                check = false;

                // Find finish time of current process

                finish_time = t + 1;
                t=t+context;



                TurnAroundTime[shortest]=finish_time-ArrivalTime[shortest];

            }

            // Increment time
            t++;

        }


        for(int i=0;i<maxProcess;i++){
            WaitingTime[i]=(TurnAroundTime[i]-BurstTime[i]);
        }


        float AverageWait=0;
        float AverageTurnAround=0;

        for(int i=0;i<maxProcess;i++){
            AverageWait=AverageWait+WaitingTime[i];
            AverageTurnAround=AverageTurnAround+TurnAroundTime[i];
        }

        System.out.print("-------------------------------------------------------------------");
        System.out.print("\nProcess      Burst Time       Turnaround Time          Waiting Time\n");
        for(int i=0;i<maxProcess;i++){
            System.out.println(Name[i]+"\t\t"+BurstTime[i]+"\t\t"+TurnAroundTime[i]+"\t\t\t"+WaitingTime[i]);
        }
        System.out.println("Average TurnAround Time: "+(AverageTurnAround/maxProcess));
        System.out.println("Average Waiting Time: "+(AverageWait/maxProcess));
    }
    }
