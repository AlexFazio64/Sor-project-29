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

#accept ssh only debug
#to me
iptables -A INPUT -i eth0 -p tcp --dport 22 -j ACCEPT
iptables -A OUTPUT -o eth0 -p tcp --sport 22 -m state --state ESTABLISHED,RELATED -j ACCEPT
#to others
iptables -A FORWARD -i eth0 -p tcp --dport 22 -j ACCEPT
iptables -A FORWARD -o eth0 -p tcp --sport 22 -m state --state ESTABLISHED,RELATED -j ACCEPT

#Green can start communications with all areas
#Creation of new chains
iptables -N greenAll
iptables -N allGreen

#Mapping the chains
#GREEN -> ALL
iptables -A FORWARD -i eth1 -j greenAll
iptables -A greenAll -j ACCEPT

#ALL -> GREEN
iptables -A FORWARD -o eth1 -j allGreen
iptables -A allGreen -m state --state ESTABLISHED,RELATED -j ACCEPT

#Anyone can start communications with DMZ, but only on ports (53,25,21)
#Creation of new chains
iptables -N allDmz
iptables -N dmzAll

#Mapping the chains
#ALL -> DMZ
iptables -A FORWARD -o eth2 -d 10.0.2.0/23 -j allDmz
iptables -A allDmz -p tcp --match multiport --dports 53,25,21 -j ACCEPT
iptables -A allDmz -p udp --match multiport --dports 53,25,21 -j ACCEPT

#DMZ -> ALL
iptables -A FORWARD -i eth2 -s 10.0.2.0/23 -j dmzAll
iptables -A dmzAll -p tcp --match multiport --sports 53,25,21 -m state --state ESTABLISHED,RELATED -j ACCEPT
iptables -A dmzAll -p udp --match multiport --sports 53,25,21 -m state --state ESTABLISHED,RELATED -j ACCEPT
