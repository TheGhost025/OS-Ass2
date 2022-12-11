import java.util.ArrayList;

public class Main {

    static ArrayList<process> getBy(ArrayList<process> arr, String Type) {
        process temp = new process();

        if (Type == "FCFS") {
            for (int i = 0; i < arr.size() - 1; i++) {
                for (int j = 0; j < arr.size() - 1; j++) {
                    if (arr.get(j).get_Arrivaltime() > arr.get(j + 1).get_Arrivaltime()) {
                        temp = arr.get(j);
                        arr.set(j, arr.get(j + 1));
                        arr.set(j + 1, temp);
                    }
                }
            }
        } else if (Type == "NPP") {
            for (int i = 0; i < arr.size() - 1; i++) {
                for (int j = 0; j < arr.size() - 1; j++) {
                    if (arr.get(j).get_Priority() > arr.get(j + 1).get_Priority()) {
                        temp = arr.get(j);
                        arr.set(j, arr.get(j + 1));
                        arr.set(j + 1, temp);
                    }
                }
            }
        } else if (Type == "SJF") {
            for (int i = 0; i < arr.size() - 1; i++) {
                for (int j = 0; j < arr.size() - 1; j++) {
                    if (arr.get(j).get_Remainingtime() > arr.get(j + 1).get_Remainingtime()) {
                        temp = arr.get(j);
                        arr.set(j, arr.get(j + 1));
                        arr.set(j + 1, temp);
                    }
                }
            }
        }
        return arr;
    }
    static int ceil(int quantum, double percentage) {
        double result = (percentage / 100.0) * quantum;
        double result2 = Math.ceil(result);
        return (int) result2;
    }
    static process endProcess(process current, int currentTime, ArrayList<process> a, ArrayList<process> f, ArrayList<process> temp, String Type,ArrayList<String> name,ArrayList<Integer> history) {
        // updating process state
        current.set_Quantumtime(0);
        history.add(current.get_Quantumtime());
        name.add(current.get_Processname());
        current.set_Remainingtime(0);
        current.set_Endtime(currentTime);

        //  transfer the process from procceses arry to the finished processes array
        f.add(current);
        a.remove(current);

        // picking the Nxt process By FCFS
        temp = get_ActiceProcess(a, currentTime);
        if (temp.size() != 0)
            current = getBy(temp, Type).get(0);

        return current;
    }

    static ArrayList<process> get_ActiceProcess(ArrayList<process> arr, int currentTime) {

        ArrayList<process> ActiveProcess = new ArrayList<process>();

        for (int i = 0; i < arr.size(); i++) {
            if ((arr.get(i).get_Arrivaltime() <= currentTime)) {
                ActiveProcess.add(arr.get(i));
            }
        }
        return ActiveProcess;
    }

    static ArrayList<process> AG(ArrayList<process> proccesses, ArrayList<Integer> history,ArrayList<String> name,ArrayList<String> exec) {
        int currentTime = 0;
        ArrayList<process> FinshedProcesses = new ArrayList<process>();
        ArrayList<process> temp = new ArrayList<process>();

        process current;
        process previous;

        current = getBy(proccesses, "FCFS").get(0);

        while(proccesses.size() > 0) {
            exec.add(current.get_Processname());
            int Q1 = ceil(current.get_Quantumtime(), 25);

            if (current.get_Remainingtime() <= Q1) {

                currentTime += current.get_Remainingtime();
                current = endProcess(current, currentTime, proccesses, FinshedProcesses, temp,"FCFS",name,history);
                continue;
            }
            else {
                currentTime += Q1;

                // updating process state
                current.set_Remainingtime(current.get_Remainingtime() - Q1);
                current.set_QuantumRemaining(current.get_QuantumRemaining() - Q1);

                // picking the Nxt process By NPP
                previous = current;
                temp = get_ActiceProcess(proccesses, currentTime);
                current = getBy(temp, "NPP").get(0);

                if (previous == current) {
                    int Q2 = ceil(current.get_Quantumtime(), 50);

                    if (current.get_Remainingtime() <= (Q2 - Q1)) {
                        currentTime += current.get_Remainingtime();

                        current = endProcess(current, currentTime, proccesses, FinshedProcesses, temp,"FCFS",name,history);
                        continue;
                    }
                    else {
                        currentTime += (Q2 - Q1);
                        current.set_Remainingtime(current.get_Remainingtime() - (Q2 - Q1));
                        current.set_QuantumRemaining(current.get_QuantumRemaining() - (Q2 - Q1));

                        previous = current;
                        temp = get_ActiceProcess(proccesses, currentTime);
                        current = getBy(temp, "SJF").get(0);

                        if (current == previous) {
                            int Q4 = ceil(current.get_Quantumtime(), 100);
                            boolean state = false ;

                            for (int i = Q2; i < Q4; i++) {
                                if(current.get_Remainingtime() == 0){

                                    current = endProcess(current, currentTime, proccesses, FinshedProcesses, temp,"FCFS",name,history);
                                    state = true;
                                    break;
                                }
                                temp = get_ActiceProcess(proccesses, currentTime);
                                previous = current;
                                current = getBy(temp, "SJF").get(0);

                                if (previous == current) {
                                    current.set_Remainingtime(current.get_Remainingtime() - 1);
                                    current.set_QuantumRemaining(current.get_QuantumRemaining() - 1);

                                } else {
                                    // Context Switch
                                    previous.set_Quantumtime(previous.get_Quantumtime() + previous.get_QuantumRemaining());
                                    history.add(previous.get_Quantumtime());
                                    name.add(previous.get_Processname());
                                    previous.set_QuantumRemaining(previous.get_Quantumtime());
                                    state = true;
                                    break;
                                }
                                currentTime++;
                            }
                            if (state) continue;
                            if (current.get_Remainingtime()>0){

                                current.set_Quantumtime(current.get_Quantumtime()+2);
                                history.add(current.get_Quantumtime());
                                name.add(current.get_Processname());
                                current.set_QuantumRemaining(current.get_Quantumtime());

                                // picking the Nxt process By FCFS
                                temp = get_ActiceProcess(proccesses, currentTime);
                                current = getBy(temp, "FCFS").get(0);
                                continue;

                            }
                            else{
                                current = endProcess(current, currentTime, proccesses, FinshedProcesses, temp,"FCFS",name,history);
                                continue;
                            }

                        }
                        else {
                            // Context Switch
                            previous.set_Quantumtime(previous.get_Quantumtime() + previous.get_QuantumRemaining());
                            history.add(previous.get_Quantumtime());
                            name.add(previous.get_Processname());
                            previous.set_QuantumRemaining(previous.get_Quantumtime());
                            continue;
                        }
                    }

                }
                else {
                    // Context Switch
                    previous.set_Quantumtime(previous.get_Quantumtime() + (previous.get_QuantumRemaining()/2));
                    history.add(previous.get_Quantumtime());
                    name.add(previous.get_Processname());
                    previous.set_QuantumRemaining(previous.get_Quantumtime());
                    continue;
                }
            }
        }
        return FinshedProcesses;
    }

    public static void main(String[] args) {
        ArrayList<process> proccess = new ArrayList<process>();
        ArrayList<process> endProccess = new ArrayList<process>();
        ArrayList<Integer> quantumHistory = new ArrayList<Integer>();
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<String> execution = new ArrayList<String>();
        Input input = new Input();
        proccess=input.input();
        endProccess = AG(proccess, quantumHistory,name ,execution);
        endProccess = getBy(endProccess, "FCFS");
        double AverageTurnAround = 0;
        double AverageWait = 0;
        for(int i=0;i<endProccess.size();i++){
            endProccess.get(i).set_Turnarroundime(endProccess.get(i).get_Endtime()-endProccess.get(i).get_Arrivaltime());
            AverageTurnAround+=endProccess.get(i).get_Turnarroundime();
        }

        for(int i=0;i<endProccess.size();i++){
            endProccess.get(i).set_Waitingtime(endProccess.get(i).get_Arrivaltime()+endProccess.get(i).get_Bursttime());
            AverageWait+=endProccess.get(i).get_Waitingtime();
        }
        for (int i = 0; i < execution.size(); i++) {
            System.out.print(execution.get(i) + " ");
        }
        System.out.println("\n");
        for (int i = 0; i < quantumHistory.size(); i++) {
            System.out.print(quantumHistory.get(i) + " "+ name.get(i)+"  ");
        }
        System.out.println("\n");
        System.out.println("-------------------------------------------------------------------");
        System.out.print("\nProcess      Burst Time       Turnaround Time          Waiting Time\n");
        for(int i=0;i<endProccess.size();i++){
            System.out.println(endProccess.get(i).get_Processname()+"\t\t\t\t\t"+endProccess.get(i).get_Bursttime()+"\t\t\t\t\t"+endProccess.get(i).get_Turnarroundime()+"\t\t\t\t\t\t"+endProccess.get(i).get_Waitingtime());
        }
        System.out.println("Average TurnAround Time: "+(AverageTurnAround/ endProccess.size()));
        System.out.println("Average Waiting Time: "+(AverageWait/endProccess.size()));
    }
}
