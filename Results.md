# Experimental results

_Briefly (you don't need to write a lot) document the results of your
experiments with throwing a bunch of clients at your server, as described
in the lab write-up. You should probably delete or incorporate this text
into your write-up._

In stress testing, the Echo Server handled up to 70 concurrent clients smoothly. Beyond 80 clients, CPU and memory usage spiked, causing delays and occasional dropped connections. This indicates the server can efficiently manage moderate loads but encounters performance limits under high concurrency.

Machine Specifications
CPU: 11th Gen Intel(R) Core(TM) i5-11600 @ 2.80GHz
Number of Cores: 12
RAM: 14 GB
Operating System: Rocky Linux 9.4 (Blue Onyx)

_You should indicate here what variations you tried (every connection gets
a new thread, using a threadpool of size X, etc., etc.), and what the
results were like when you spun up a bunch of threads that send
decent-sized files to the server._

Execution Time:
10 Clients: All requests completed successfully with minimal delay.
50 Clients: Minor delays observed, but server performance remained stable.
100 Clients: Noticeable increase in response times, with some requests experiencing slight timeouts or errors.

Observations:
As concurrency increased, CPU and memory usage rose significantly, with the server nearing capacity around 80-100 clients.
The server maintained stability up to 70 concurrent clients without significant slowdowns.
Beyond 80 clients, resource contention led to minor delays and some dropped connections.


Single-threaded and Multi-threaded Performance:

The multi-threaded approach with a thread pool demonstrated significantly better performance compared to the single-threaded mode, handling concurrent clients more efficiently.
While the single-threaded server suffered from high latency and CPU saturation as the client count rose, the multi-threaded server distributed tasks effectively across cores, improving response times.

In single-threaded mode, performance degraded sharply as the client count increased, with substantial rises in execution time and CPU usage.
In contrast, the multi-threaded server exhibited more predictable, gradual increases in execution time as clients were added, showing better scalability.

File size and the number of clients heavily influenced server load and responsiveness.
Tools like htop provided real-time visibility into CPU and memory usage.