public class process {
    private String process_name;
    private int Arrival_time;
    private int Burst_time;
    private int turn_arroundime;
    private int waiting_time;
    private int Quantum_time;
    private int remaining_Quantum_time;
    private int remaining_time ;
    private int priority;
    private int end_time;

    public process(String process_name, int arrival_time, int burst_time,int Quantum_time,int priority) {
        this.process_name = process_name;
        this.Quantum_time = Quantum_time;
        this.priority = priority;
        this.Arrival_time = arrival_time;
        this.Burst_time = burst_time;
        this.remaining_time = burst_time;
        this.remaining_Quantum_time=Quantum_time;
    }

    public process(){
        this.process_name = null;
        this.Quantum_time = 0;
        this.priority = 0;
        this.Arrival_time = 0;
        this.Burst_time = 0;
        this.remaining_time = 0;
    }

    public int get_Priority(){
        return this.priority;
    }

    public int get_Quantumtime(){
        return this.Quantum_time;
    }

    public void set_Quantumtime(int quantumTime){
        this.Quantum_time = quantumTime;
    }

    public int get_Endtime() {
        return this.end_time;
    }

    public void set_Endtime(int end_time) {
        this.end_time = end_time;
    }


    public int get_Turnarroundime() {
        return turn_arroundime;
    }

    public void set_Turnarroundime(int turn_arroundime) {
        this.turn_arroundime = turn_arroundime;
    }

    public int get_Waitingtime() {
        return waiting_time;
    }

    public void set_Waitingtime(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    public String get_Processname() {
        return process_name;
    }

    public int get_Arrivaltime() {
        return Arrival_time;
    }

    public int get_Bursttime() {
        return Burst_time;
    }


    public int get_Remainingtime() {
        return remaining_time;
    }
    public int get_QuantumRemaining() {
        return remaining_Quantum_time;
    }
    public void set_QuantumRemaining(int QuantumRemaining) {
        this.remaining_Quantum_time = QuantumRemaining;
    }

    public void set_Remainingtime(int remaining_time) {
        this.remaining_time = remaining_time;
    }
}
