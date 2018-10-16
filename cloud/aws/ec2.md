# EC2
    on demand   per hour or per second depending on 
        short term spike
    RI Reserved i=Instances reserved for 1 to 3 years
        Applications that require steady state predictable cpu resources
        upto 75% off if you pay in front for 3 years
        Scheduled RIs e.g. for planned bigger load days like month end  upcoming sales etc.
    Spot 
        Flexible start and end times.. Batch jobs
        if  aws terminate instance (cos price > threshold) then don't pay of fractino of hour youve used.
        If you terminate you Do pay for fraction of hour
    Dedicated hosts
        Useful for regulatory requirements that don't allow multi tenant
		
	## Sample of services
	..* EC2
	..* EC2 Container Service (ECS)
	..* Elastic Beanstalk (in developper associate exam)
	..* Lambda
	..* LightSail VPS service.. Easier th
	..* Batch  

## Host types
    Dr Mc GiftPx
    Density
    Ram
    Main choice
    Compute
    Graphics
    Iops
    FPGA
    T cheap main use e.g. t2
    Pix (Graphics)
    Xtreme Memory
now
    Fight Dr McPx
    High Disk Tru put

## EBS Elastic Block Storage
So EBS volume's placed in a specific availabilities zones where they are automatically replicated to protect
    Root volumne must be either SSD.. 2 types or magnetic standard
    	- General purpose ssd GP2 ... 3IOPs per gig => 3000 IOPS for volumes 3334GB and above
        -Provisioned IOPS SSd.. For high perf >10000 IOPS

    Magnetic types
        Magnetic Standard (previous generation )
        throughput optimized HDD.. big data, data warehouse. frequenctly acessed
        sc1.. cold HDD .. less frequent. lowest cost hdd

by default EBS deleted with Ec2 instance, but can change
Additional volumes can be encryptrd. By default root can't but can by either 3rd part apps or creating custom AMI can allow you to do it	