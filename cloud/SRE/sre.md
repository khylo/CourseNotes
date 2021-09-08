# SRE
Site Reliability Engineering funtion originated in Google in the early 2000s
At google they say SRE is what happens when you let engineers design an operations function.
Goal. "Make tomorrow better than today"

Sources: https://www.youtube.com/watch?v=c-w_GYvi0eA


## SRE Principles
Wiki definition
```
Site reliability engineering (SRE) is a set of principles and practices that incorporates aspects of software engineering and applies them to infrastructure and operations problems. 
The main goals are to create scalable and highly reliable software systems. 
Site reliability engineering is closely related to DevOps, a set of practices that combine software development and IT operations, and SRE has also been described as a specific implementation of DevOps.
```

from https://www.youtube.com/watch?v=uTEL8Ff1Zvk&ab_channel=GoogleCloudTech
- Shared Ownership... reduce silos of knowledge
- SLO's and blameless PMs
- REduce cost of falure. .Implement gradual change
- Leverage tooling and automation
- Measure everything

From https://www.youtube.com/watch?v=c-w_GYvi0eA

 - SRE needs Service Level Objectives (SLOs) with *consequences* ![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+) `partially`
 - SREs have time to *"Make tomorrow better than today"* ![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+) `improving`
 - SRE teams have the ability to regulate their workload.![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+) `improving`

###  Service Level Objectives (SLO)s
Set goal for how well the system should behave
Specifically tracking customer experience.
If customers are happy then SLO is been met.

Consequences: INCs / escations

### Error Budgets
- THe error budget is the gap between perfect relieability and our SLO
- Given updatime SLO of 99.9% (), after a 20 minute outage you still have 23 minutes of busget remaining for the month. 

43200 minutes per month..   0.1 % = 43 minutes of downtime per month.

If you exceed the error budget . You need to improce reliability.

```diff
- We are improving here. More/ larger teams
+ text in green
! text in orange
# text in gray
@@ text in purple (and bold)@@
```

- ![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+) `#f03c15`
- ![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+) `#c5f015`
- ![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `#1589F0`
