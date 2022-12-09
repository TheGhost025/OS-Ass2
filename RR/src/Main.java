import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int maxProcess,Quantam,sQuantam=0,context;
        Scanner MyObj=new Scanner(System.in);
        System.out.println("Enter the max Number of processes: ");
        maxProcess=MyObj.nextInt();
        System.out.println("Enter the Quantum: ");
        Quantam=MyObj.nextInt();
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

        int current=0;
        while (true) {
            int count = 0;
            int temp;
            for (int i = 0; i < maxProcess; i++) {
                temp = Quantam;
                if (remain[i] == 0) {
                    count++;
                    continue;
                }
                int x=current-1;
                if (ArrivalTime[i] < x ||ArrivalTime[i]==0) {
                    System.out.println(Name[i]);
                    if (remain[i] > Quantam) {
                        remain[i] = remain[i] - Quantam;
                    } else if (remain[i] >= 0) {
                        temp = remain[i];
                        remain[i] = 0;
                    }
                    sQuantam = sQuantam + temp;
                    TurnAroundTime[i] = sQuantam + context;
                    context++;
                    current = TurnAroundTime[i];
                }
            }
            if (count == maxProcess) {
                break;
            }
        }

        for(int i=0;i<maxProcess;i++){
            TurnAroundTime[i]=TurnAroundTime[i]-ArrivalTime[i];
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
            System.out.println(Name[i]+"\t\t\t\t\t"+BurstTime[i]+"\t\t\t\t\t"+TurnAroundTime[i]+"\t\t\t\t\t\t"+WaitingTime[i]);
        }
        System.out.println("Average TurnAround Time: "+(AverageTurnAround/maxProcess));
        System.out.println("Average Waiting Time: "+(AverageWait/maxProcess));
    }
}