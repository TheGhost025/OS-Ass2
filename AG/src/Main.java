import java.util.LinkedList;
import java.util.Queue;
import java.lang.Math;
import java.util.*;

public class Main {

    static ArrayList<process> sort(ArrayList<process> array, String sortType) {


        if (sortType == "FCFS") {
            for (int i = 0; i < array.size() - 1; i++) {
                for (int j = 0; j < array.size() - 1; j++) {
                    if (array.get(j).getArrival_time() > array.get(j + 1).getArrival_time()) {
                        process temp = array.get(j);
                        array.set(j, array.get(j + 1));
                        array.set(j + 1, temp);
                    }
                }
            }
        } else if (sortType == "NPP") {
            for (int i = 0; i < array.size() - 1; i++) {
                for (int j = 0; j < array.size() - 1; j++) {
                    if (array.get(j).get_priority() > array.get(j + 1).get_priority()) {
                        process temp = array.get(j);
                        array.set(j, array.get(j + 1));
                        array.set(j + 1, temp);
                    }
                }
            }
        } else if (sortType == "SJF") {
            for (int i = 0; i < array.size() - 1; i++) {
                for (int j = 0; j < array.size() - 1; j++) {
                    if (array.get(j).getRemaining_time() > array.get(j + 1).getRemaining_time()) {
                        process temp = array.get(j);
                        array.set(j, array.get(j + 1));
                        array.set(j + 1, temp);
                    }
                }
            }
        }
        return array;
    }


    public static void main(String[] args) {
        Queue<process> pQueue = new LinkedList<process>();
        ArrayList<process> arr = new ArrayList<process>();
        ArrayList<process> arr1 = new ArrayList<process>();
        temp t = new temp();
        arr = t.usage();
        AG_Scheduler(arr);
    }

    //function to get the 
    static int delta(int q, double percentage) {
        double result = (percentage / 100.0) * q;
        double result2 = Math.ceil(result);
        return (int) result2;
    }

//get till now 

    static ArrayList<process> get_tillnow(ArrayList<process> arr, int currentTime) {

        ArrayList<process> developed_Arr = new ArrayList<process>();

        for (int i = 0; i < arr.size(); i++) {
            if ((arr.get(i).getArrival_time() < currentTime)) {
                developed_Arr.add(arr.get(i));
            }
        }
        return developed_Arr;
    }



    static void AG_Scheduler(ArrayList<process> pArray) {

        int currentTime = 0;
        ArrayList<process> fArray = new ArrayList<process>();
        ArrayList<process> tempArray = new ArrayList<process>();
        
        // the currentProcess = working process = running process 
        process currentProcess ;
        process previousProcess;

        currentProcess = sort(pArray, "FCFS").get(0);

        while (pArray.size() != 0) {
            int Q1 = delta(currentProcess.getQuantum_time(), 25);


            if (currentProcess.getRemaining_time() < Q1) {

                // Time update
                currentTime += currentProcess.getRemaining_time();

                // updating process state
                currentProcess.setQuantum_time(0);
                currentProcess.setRemaining_time(0);
                currentProcess.setEnd_time(currentTime);

                //  transfer the process from procceses arry to the finished processes array
                fArray.add(currentProcess);
                pArray.remove(currentProcess);



                // picking the Nxt process By FCFS
                tempArray = get_tillnow(pArray, currentTime);
                if(tempArray.size()!=0)
                    currentProcess = sort(tempArray, "FCFS").get(0);
                continue;

            } else {

                if (currentProcess.getRemaining_time() == Q1) {

                    // Time update
                    currentTime += currentProcess.getRemaining_time();

                    // updating process state
                    currentProcess.setQuantum_time(0);
                    currentProcess.setRemaining_time(0);
                    currentProcess.setEnd_time(currentTime);

                    //  transfer the process from procceses arry to the finished processes array
                    fArray.add(currentProcess);
                    pArray.remove(currentProcess);


                    // picking the Nxt process By NPP

                    tempArray = get_tillnow(pArray, currentTime);
                    currentProcess = sort(tempArray, "NPP").get(0);
                    continue;
                } else {
                    // update time
                    currentTime += Q1;

                    // updating process state
                    currentProcess.setRemaining_time(currentProcess.getRemaining_time() - Q1);
                    currentProcess.setQuantumRemaining(currentProcess.getQuantumRemaining() - Q1);


                    // picking the Nxt process By NPP
                    previousProcess = currentProcess;
                    tempArray = get_tillnow(pArray, currentTime);
                    currentProcess = sort(pArray,"NPP").get(0);

                        //if there's a context switching by PP or not at Q1
                        if(previousProcess == currentProcess){

                            int Q2 = delta(currentProcess.getQuantum_time(), 50);
                            if(currentProcess.getRemaining_time() > (Q2-Q1) ){

                                int Q4 = delta(currentProcess.getQuantum_time(),100);

                                // a process with a shorteset job came to existense int the intervel of Q2 to the end of the Quantum time
                                for(int i = Q2; i < (Q4-Q2); i++ ){

                                    currentTime++;
                                    tempArray= get_tillnow(pArray,currentTime);
                                    previousProcess=currentProcess;
                                    currentProcess=sort(tempArray,"SJF").get(0);

                                    if(previousProcess==currentProcess){
                                       currentProcess.setRemaining_time(currentProcess.getRemaining_time()-1);
                                       currentProcess.setQuantumRemaining(currentProcess.getQuantumRemaining()-1);
                                    }
                                    else{
                                        previousProcess.setQuantum_time(previousProcess.getQuantum_time()+previousProcess.getQuantumRemaining());
                                        continue;
                                    }
                                }

                               // the process took all it's quantum time and still have job to do
                                if(currentProcess.getRemaining_time()>0){

                                   currentProcess.setQuantum_time(currentProcess.getQuantum_time()+currentProcess.getQuantumRemaining()+2);

                                    // picking the Nxt process By FCFS
                                    tempArray = get_tillnow(pArray, currentTime);
                                    currentProcess = sort(tempArray, "FCFS").get(0);
                                    continue;
                                }else{
                                     // updating process state
                                    currentProcess.setQuantum_time(0);
                                    currentProcess.setRemaining_time(0);
                                    currentProcess.setEnd_time(currentTime);

                                    //  transfer the process from procceses arry to the finished processes array
                                    fArray.add(currentProcess);
                                    pArray.remove(currentProcess);

                                    // picking the Nxt process By SJF
                                    tempArray = get_tillnow(pArray, currentTime);
                                    currentProcess = sort(tempArray, "SJF").get(0);
                                    continue;

                                }
                                // else for ending
                            }
                            else{

                                if(currentProcess.getRemaining_time() == (Q2 - Q1)){
                                    //time update
                                    currentTime += (Q2-Q1);

                                    // updating process state
                                    currentProcess.setQuantum_time(0);
                                    currentProcess.setRemaining_time(0);
                                    currentProcess.setEnd_time(currentTime);

                                    //  transfer the process from procceses arry to the finished processes array
                                    fArray.add(currentProcess);
                                    pArray.remove(currentProcess);

                                    // picking the Nxt process By SJF
                                    tempArray = get_tillnow(pArray, currentTime);
                                    currentProcess = sort(tempArray, "SJF").get(0);
                                    continue;
                                }
                                else{

                                     //time update
                                    currentTime += currentProcess.getRemaining_time();

                                    // updating process state
                                    currentProcess.setQuantum_time(0);
                                    currentProcess.setRemaining_time(0);
                                    currentProcess.setEnd_time(currentTime);

                                    //  transfer the process from procceses arry to the finished processes array
                                    fArray.add(currentProcess);
                                    pArray.remove(currentProcess);

                                    // picking the Nxt process By SJF
                                    tempArray = get_tillnow(pArray, currentTime);
                                    currentProcess = sort(tempArray, "NPP").get(0);
                                    continue;

                                }

                            }



                        }

                        // in case of the's a process with a priority higher than the current process
                        // will makes t the current and put the previous in the end of the queue with increasing it's quantum time by half the remaining
                        else{
                            previousProcess.setRemaining_time(previousProcess.getRemaining_time()-Q1);
                            previousProcess.setQuantum_time(previousProcess.getQuantum_time()+(previousProcess.getQuantumRemaining()/2));
                            continue;
                        }



                }
            }


            

        }
      fArray=sort(fArray, "FCFS");
      for(int i=0;i<fArray.size();i++){
        System.out.println(fArray.get(i).getEnd_time());
      }  

    }

}
