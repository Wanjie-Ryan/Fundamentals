# CONCURRENCY VS PARALLELISM
- Concurrency          Parallelism
─────────────────────────────────────────────────
- Definition      managing multiple    executing multiple
                tasks, switching     tasks simultaneously
                between them

- Requires        one or more cores    multiple cores

- Tasks run at    not necessarily      yes — same instant
- same instant?   same time

- Analogy         one chef, multiple   multiple chefs,
                dishes               multiple dishes

- Java tool       Thread, switching    parallelStream(),
                context              ForkJoinPool

- Goal            responsiveness,      raw speed,
                don't block          throughput


- Concurrency means dealing with multiple things at once where CPU switches btn tasks rapidly

- Parallelism means actually doing multiple things at the exact same instant, requires multiple CPU cores.

