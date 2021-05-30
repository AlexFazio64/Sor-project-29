#!/bin/sh

#delete old rules
iptables -F
iptables -X
iptables -t nat -F
iptables -t nat -X

#set default policy
iptables -P INPUT DROP
iptables -P OUTPUT DROP
iptables -P FORWARD DROP

#accept ssh
iptables -A INPUT -i eth3 -p tcp --dport 22 -j ACCEPT
iptables -A OUTPUT -o eth3 -p tcp --sport 22 -m state --state ESTABLISHED,RELATED -j ACCEPT

#Green can start communications with all areas
#Creations of new chains
iptables -N greenAll
iptables -N allGreen

#Mapping the chains
#GREEN -> ALL
iptables -A FORWARD -i eth3 -s 10.0.4.0/23 -j greenAll
iptables -A greenAll -j ACCEPT

#ALL -> GREEN
iptables -A FORWARD -o eth3 -d 10.0.4.0/23 -j allGreen
iptables -A allGreen -m state --state ESTABLISHED,RELATED -j ACCEPT

#DMZ can start communications with RED
#Creation of new chains
iptables -N dmzRed
iptables -N redDmz

#Mapping the chains
#DMZ -> RED
iptables -A FORWARD -i eth0 -s 10.0.2.0/23 -j dmzRed
iptables -A dmzRed -j ACCEPT

#RED -> DMZ
iptables -A FORWARD -o eth0 -d 10.0.2.0/23 -j redDmz
iptables -A redDmz -m state --state ESTABLISHED,RELATED -j ACCEPT


#Anyone can contact DMZ, but only on ports (53,25,21)
#Creation of new chains
iptables -N allDmz
iptables -N dmzAll

#Mapping the chains
#ALL -> DMZ
iptables -A FORWARD -o eth0 -d 10.0.2.0/23 -j allDmz
iptables -A allDmz -p tcp --match multiport --dports 53,25,21 -j ACCEPT
iptables -A allDmz -p udp --match multiport --dports 53,25,21 -j ACCEPT

#DMZ -> ALL
iptables -A FORWARD -i eth0 -s 10.0.2.0/23 -j dmzAll
iptables -A dmzAll -p tcp --match multiport --sports 53,25,21 -m state --state ESTABLISHED,RELATED -j ACCEPT
iptables -A dmzAll -p udp --match multiport --sports 53,25,21 -m state --state ESTABLISHED,RELATED -j ACCEPT
