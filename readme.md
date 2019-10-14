# Projet scientifique collectif(PSC) 

Sep. 2018 - May. 2019

This project is under the supervision of Renault and Departement of mathematics and Management in École Polytechnique, 
concerns the optimisation of robot-taxi service(driveless, electrical, sharing, dynamic dispatching). According to the ambition of Renault, 
we may be able to see robot-taxi serve in Paris in 2022.

Our works focused on the fleet-sizing and pricing, composed of two parts: Mathematical modeling and JAVA simulation. 
What you see here is the Java simulation.  

----
### The model:
<center class="half">
<img src="./demo/model.png" width="300" />
</center>

The city is segmented into a square grid, clients appear randmoly according to a Poisson Process at each node. A dispatching algorithme is in charge of manoeuvring taxis and deciding how to share a ride between clients.


### Some demonstrations of our results:

1. <center class="half">
<img src="./demo/percentage_of_utilisation.png"  title="Logo" width="300" />
<center>rate of utilisation</center>

2. 
<img src="./demo/average_waiting_time.png"  title="Logo" width="300"/>
<center>waiting time of client</center>
</center>

3. 
![distribution fluctuation in time](./demo/distribution_selon_le_temps.png)
<center>spatial client distribution fluctuation in time</center>

---------

Combining the result of mathematical modeling and this simulation, it turns out that we need 8600 robot-taxis to satisfy all clients instead of the current number 16000!