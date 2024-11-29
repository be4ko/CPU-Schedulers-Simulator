# CPU Schedulers Simulator

### Overview  
This project is a Java-based simulation of various CPU scheduling algorithms. Scheduling is a fundamental aspect of operating systems that determines how processes are allocated to the CPU. The simulator implements traditional scheduling techniques and introduces a novel FCAI scheduling algorithm to address common issues like starvation and inefficiency.

---

### Implemented Scheduling Algorithms  

1. **Non-preemptive Priority Scheduling**  
   - Simulates context switching.
   - Resolves any starvation issues.

2. **Non-Preemptive Shortest Job First (SJF)**  
   - Implements SJF scheduling.
   - Includes logic to solve starvation problems.

3. **Shortest Remaining Time First (SRTF)**  
   - Simulates preemption with context switching.
   - Resolves starvation issues.

4. **FCAI Scheduling** (Novel Approach)  
   - Combines priority, arrival time, and remaining burst time into an adaptive **FCAI Factor**.  
   - Features:
     - **Dynamic FCAI Factor Calculation:**  
       \[
       \text{FCAI Factor} = (10 - \text{Priority}) + \left(\frac{\text{Arrival Time}}{V_1}\right) + \left(\frac{\text{Remaining Burst Time}}{V_2}\right)
       \]
       where:
       - \(V_1 = \frac{\text{last arrival time of all processes}}{10}\)
       - \(V_2 = \frac{\text{max burst time of all processes}}{10}\)
     - **Dynamic Quantum Allocation Rules:**  
       - \(Q = Q + 2\) if the process completes its quantum but still has work left.  
       - \(Q = Q + \text{unused quantum}\) if the process is preempted.  
     - **Preemptive and Non-Preemptive Execution:**  
       - Processes execute non-preemptively for 40% of their quantum.  
       - Preemption allowed after 40% execution if needed.

---

### Input Parameters  

The program requires the following input from the user:  
- **Number of Processes**  
- **Round Robin Time Quantum**  
- **Context Switching Time**  
- For each process:  
  - Name  
  - Color (for graphical representation)  
  - Arrival Time  
  - Burst Time  
  - Priority Number  

---

### Outputs  

For each scheduling algorithm, the program provides:  
- **Execution Order of Processes**  
- **Waiting Time for Each Process**  
- **Turnaround Time for Each Process**  
- **Average Waiting Time**  
- **Average Turnaround Time**  
- **Quantum Time History for FCAI Scheduling**  
- **Graphical Representation** (Bonus)  
  - Visualizes the execution timeline of processes.

---

### Example Execution  

#### FCAI Scheduling:  

**Input:**  
| Process | Burst Time | Arrival Time | Priority | Quantum |  
|---------|------------|--------------|----------|---------|  
| P1      | 17         | 0            | 4        | 4       |  
| P2      | 6          | 3            | 9        | 3       |  
| P3      | 10         | 4            | 3        | 5       |  
| P4      | 4          | 29           | 8        | 2       |  

**Output:**  
- **Execution Timeline:**  
  - Process \(P1\) executes for 3 units. Remaining burst time: 14.  
  - Process \(P2\) preempts \(P1\) and executes for 3 units. Remaining burst time: 3.  
  - \(P1\) resumes, executes for 2 units. Remaining burst time: 12.  
  - ... (full timeline provided in program output).  

- **Average Waiting Time:** \(X\) ms  
- **Average Turnaround Time:** \(Y\) ms  

---

### How to Run  
