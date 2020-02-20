See https://youtu.be/UZr8J9iU4Ko

# Intro
1.  Exam guide https://cloud.google.com/certification/cloud-devops-engineer
2. Training 
3. Hands-on practises on Qwiklabs
4. Docuemntation


## How to measure Reliability
SLO and SRE
100% is the wrong number

*Naive measure*
Availability = uptime/ total time
however this doens't work for distributed system (e.g.g 1 of 3 down.. in theory not down)

*Better*
Availability - good interactions/ total interactions

How does reliability sit with distributed error rate?
e.g. 4 9's 99.999 allows 4.32 minutes downtime per 30 days (basrely enough time to login).
but if part of distributed system with error rate of 1% then that increases to 36 hours.

## Error Budget (USe this instead of blanket 100% uptime)
* Common incentive for devs and SRE
* DEv team can manage the risk themselves
* Unrealistic reliability goals become unattrative
* Dev teams become self policing (in order to reduce time spend fixing issues)
* Shared responsibility for uptime. Infra errors eat into devs budget

## SLO and SRE
* SLI Service Level Indicator (Some measure which indicates success)
* SLO = Service Level Objective
* SLA SErvice level Agreement . Public commitment. SLA = SLO+margin + consequences = SLI + goal + concequences

SLO is Target for SLI's over time. It is measuered typically as sum(SLI's met)/ window >= Target percent.
Try not to exceed SLO by much. Choosing appropriate SLO is complex. Set priorities for SRE and dev work

## Demand forecasting and capacity planning
* Plan for organic growth
* In-organic growth e.g. marketing

Aim for maximum change velocity.

Try to minimize toil (repetitive manual tasks to < 50%)

# CI/ CD pipelines

* 70% outages due to changes
Remove humans to reduce erors/ fatigue/ improve velocity

e.g. Cloud Build + Spinnaker.. sample Google CI/CD .. 
git commit -> create tag -> push and voila, canary and or manual approval
 * Developper git commit -> create tag -> push 
 * Cloud Build .. Detect new tag -> Build docker image -> Run tests -> push docker image
 * Spinnaker: Detect new image -> Deploy to canary -> Functional tests -> Manual approval -> Prod Deploy
 
 ## Monitoring and Alerting
 
 Humans shouln't monitor logs etc.
 
 *Stackdriver service Momnitoring*
https://youtu.be/UZr8J9iU4Ko?t=1925


