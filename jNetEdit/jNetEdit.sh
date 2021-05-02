#! /bin/bash

VER=$(cat version) 

echo "jNetEdit - version $VER"
echo "Copyright 2010 Salvatore Loria"
echo ""
echo "Starting jNetEdit..."

if test "${NETKIT_HOME+set}" != set ; then
	if [ -d $1 ] && [ -f "$1/netkit-version" ] ; then
		NETKIT_HOME=$1
		echo "NETKIT_HOME=$NETKIT_HOME"
	else
		echo "WARNING: No Netkit installation founded, some features wont be available."
		echo "Download a free copy of Netkit from here: <http://wiki.netkit.org>"
		echo "or tell me the path of your nekit installation: "
		echo "	sh jNetEdit.sh /path/to/netkit"
	fi
else
 	echo "NETKIT_HOME=$NETKIT_HOME"
fi

java -cp ./bin:./lib/piccolo.jar:./lib/piccolox.jar com.jnetedit.Main $NETKIT_HOME
