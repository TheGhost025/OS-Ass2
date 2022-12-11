package controller;

import model.GanttRecord;
import model.Process;
import model.ReadyQueue;

import java.util.ArrayList;

public class PreemptiveScheduling {
    //Gantt
    private ArrayList<GanttRecord> gantt;
    private int currentTime;
    private int exeTime;
    private ReadyQueue readyQueue;

    public PreemptiveScheduling(){
        gantt = new ArrayList<>();
        currentTime = 0;
        exeTime = 0;
        readyQueue = new ReadyQueue();
    }

    public ArrayList<GanttRecord> getGantt(ArrayList<Process> processes){
        //initialization of initial times
        currentTime = this.getFirstArrivingTime(processes);
        int in = currentTime ,out = currentTime;

        //the first processes in the ready queue are added
        ArrayList<Process> processes1 = this.getFirstArrivingProcesses(processes);

        for(Process process : processes1){
            //addition to the ready queue is based on priority
            readyQueue.enqueue(process);
            processes.remove(process);
        }

        //the remaining processes are listed depending on the maturity time
        ArrayList<Process> orderedByArrivingTime = this.orderProcessesByArrivingTime(processes);

        //as long as the queue is not empty
        while(!readyQueue.isEmpty()){
            //the process with the highest priority from the ready queue is taken
            Process process = readyQueue.dequeue();


            //Two cases that must be taken into account, the first is when we have new processes coming and the other case
            //it is when no new processes come, but those that are in the ready queue are handled
            if(orderedByArrivingTime.size() > 0) {
                //Handle of all arriving processes while one process has the control of CPU
                for (int i = 0; i < orderedByArrivingTime.size(); i++) {
                    Process p = orderedByArrivingTime.get(i);
                    //the new process that comes when the CPU is busy is compared to its priority and if the priority is more
                    //less than the priority of the process that has control, then the new process is added to the ready queue
                    if (p.getArrivingTime() >= process.getArrivingTime()
                            && p.getArrivingTime() < (process.getBurstTime() + currentTime)
                            && p.getPriority() >= process.getPriority()) {
                        readyQueue.enqueue(p);
                        orderedByArrivingTime.remove(p);
                        i--;
                    }
                    //the new process that comes when the CPU is busy is compared to its priority and if the priority is more
                    //greater than the priority of the controlling process, then the old process is added to the ready queue
                    //with reduced burst time, the new process takes control of the CPU
                    else if (p.getArrivingTime() >= process.getArrivingTime()
                            && p.getArrivingTime() < (process.getBurstTime() + currentTime)
                            && p.getPriority() < process.getPriority()) {
                        in = currentTime;
                        currentTime = p.getArrivingTime();
                        process.reduceTime(currentTime - in);
                        out = currentTime;
                        readyQueue.enqueue(process);
                        GanttRecord gR = new GanttRecord(in, out, process.getProcessID());
                        gantt.add(gR);

                        readyQueue.enqueue(p);
                        orderedByArrivingTime.remove(p);
                        i--;

                        break;
                    }
                    //if the entire list of incoming new processes is checked and none of them are valid
                    //to take control of the CPU, the process that has the control continues with the time it needs
                    if (i == orderedByArrivingTime.size() - 1) {
                        in = currentTime;
                        currentTime += process.getBurstTime();
                        out = currentTime;
                        gantt.add(new GanttRecord(in, out, process.getProcessID()));
                        //it is checked if at the end of the uninterrupted execution of a process we have a new process that
                        //comes and is added to the ready queue
                        if(orderedByArrivingTime.size() > 0
                                && readyQueue.isEmpty()) {
                            readyQueue.enqueue(orderedByArrivingTime.get(0));
                        }
                    }
                }
            }
            // the other case when we don't have new processes that follow, but only those that are in the ready queue are handled
            else{
                in = currentTime;
                currentTime += process.getBurstTime();
                out = currentTime;
                gantt.add(new GanttRecord(in, out, process.getProcessID()));
            }
        }
        return gantt;
    }

    public static int getCompletionTime(Process p, ArrayList<GanttRecord> gantt) {
        int completionTime = 0;
        for(GanttRecord gR : gantt){
            if(gR.getProcessId() == p.getProcessID())
                completionTime = gR.getOutTime();
        }
        return completionTime;
    }

    public static int getTurnAroundTime(Process p, ArrayList<GanttRecord> gantt) {
        int completionTime = PreemptiveScheduling.getCompletionTime(p,gantt);
        return completionTime-p.getArrivingTime();
    }

    public static int getWaitingTime(Process p, ArrayList<GanttRecord> gantt) {
        int turnAroundTime = PreemptiveScheduling.getTurnAroundTime(p,gantt);
        return turnAroundTime-p.getBurstTime();
    }

    private ArrayList<Process> orderProcessesByArrivingTime(ArrayList<Process> processes){
        ArrayList<Process> newProcesses = new ArrayList<>();
        while(processes.size() != 0) {
            Process p = this.getFirstArrivingProcess(processes);
            processes.remove(p);
            newProcesses.add(p);
        }
        return newProcesses;
    }

    private Process getFirstArrivingProcess(ArrayList<Process> processes){
        int min = Integer.MAX_VALUE;
        Process process = null;
        for(Process p : processes){
            if(p.getArrivingTime() < min){
                min = p.getArrivingTime();
                process = p;
            }
        }
        return process;
    }

    private ArrayList<Process> getFirstArrivingProcesses(ArrayList<Process> processes){
        int min = this.getFirstArrivingTime(processes);
        ArrayList<Process> processes1 = new ArrayList<>();
        for(Process p : processes){
            if(p.getArrivingTime() == min){
                processes1.add(p);
            }
        }
        return processes1;
    }

    private int getFirstArrivingTime(ArrayList<Process> processes){
        int min = Integer.MAX_VALUE;
        for(Process p : processes){
            if(p.getArrivingTime() < min){
                min = p.getArrivingTime();
            }
        }
        return min;
    }

}