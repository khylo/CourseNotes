#
# Run docker nagios
#

FROM redhat/ubi8-init

#wget https://sourceforge.net/projects/nagios/files/nagios-4.x/nagios-4.4.6/nagios-4.4.6.tar.gz/download --no-check-certificate
RUN wget https://sourceforge.net/projects/nagios/files/nagios-4.x/nagios-4.3.4/nagios-4.3.4.tar.gz/download --no-check-certificate && tar zxf download && rm -rf download

#NRPE
#wget https://sourceforge.net/projects/nagios/files/nrpe-4.x/nrpe-4.0.3/nrpe-4.0.3.tar.gz/download --no-check-certificate

# Plugins
RUN wget https://nagios-plugins.org/download/nagios-plugins-2.3.3.tar.gz --no-check-certificate && tar zxf nagios-plugins-2.3.3.tar.gz && rm -rf nagios-plugins-2.3.3.tar.gz


run yum clear metadata
run yum install ... 
