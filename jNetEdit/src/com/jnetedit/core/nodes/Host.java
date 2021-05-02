/**
 * jNetEdit - Copyright (c) 2010 Salvatore Loria
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.jnetedit.core.nodes;

import java.io.Serializable;
import java.util.ArrayList;

import com.jnetedit.common.IpAddress;
import com.jnetedit.common.ItemType;
import com.jnetedit.core.nodes.components.AbstractChain;
import com.jnetedit.core.nodes.components.AbstractInterface;
import com.jnetedit.core.nodes.components.AbstractRoute;
import com.jnetedit.core.nodes.components.AbstractRule;
import com.jnetedit.core.nodes.components.Chain;
import com.jnetedit.core.nodes.components.Interface;



/**
 * Class representing a generic Host (e.g. router, firewall, pc etc)
 * 
 * @author sal
 */
public class Host implements AbstractHost, Serializable {
	private static final long serialVersionUID = -8160550272638451472L;

	protected String name;
	
	protected String label;
	
	/** host's network interfaces */
	protected ArrayList<AbstractInterface> interfaces;

	/** host's iptables chains */
	protected ArrayList<AbstractChain> chains;

	protected ItemType type;

	private int ifaceNumber = 0;

	/** Create a Host
	 * 
	 * @param name
	 * @param type
	 */
	public Host(String name, ItemType type) {
		this.name = name;
		this.type = type;
		this.label = "" + name;
		
		interfaces = new ArrayList<AbstractInterface>();
		chains = new ArrayList<AbstractChain>();
		
		if( type == ItemType.FIREWALL ) {
			chains.add(new Chain(this, "INPUT"));
			chains.add(new Chain(this, "OUTPUT"));
			chains.add(new Chain(this, "FORWARD"));
		}
	}
	
	/**
	 * Add this interface to the host's interfaces list (returns true) <br>
	 * if there is an existing interface with the same id it returns false
	 * 
	 * @param i - (Interface) the interface to add
	 * @return boolean
	 */
	public void addInterface( Interface i ) {
		interfaces.add( i );
	}
	
	public ArrayList<AbstractInterface> getInterfaces() {
		return interfaces;
	}

	@Override
	public AbstractInterface addInterface( AbstractCollisionDomain cd ) {
		if( ifaceNumber < 8 ) {
			String ifaceName = "eth" + ifaceNumber++;
			Interface iface = new Interface( ifaceName, cd, this );
			interfaces.add(iface);
			return iface;
		} else {
			return null;
		}
	}

	@Override
	public boolean delete() {
		while( !interfaces.isEmpty() ) {
			interfaces.get(0).delete();
		}
		interfaces.clear();
		return true;
	}

	@Override
	public boolean deleteInterface( AbstractInterface iface ) {
		return interfaces.remove( iface );
	}

	@Override
	public AbstractInterface getInterface(String ifaceName) {
		String name = ifaceName.split(" :")[0];
		for( AbstractInterface i : interfaces ) {
			if( i.getName().equals(name) ) 
				return i;
		}
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public ItemType getType() {
		return type;
	}

	@Override
	public boolean isConnectedTo(AbstractCollisionDomain collisionDomain) {
		for( AbstractInterface iface : interfaces ) {
			if( collisionDomain.equals(iface.getCollisionDomain())) 
				return true;
		}
		return false;
	}

	@Override
	public AbstractChain addChain() {
		AbstractChain chain = new Chain(this);
		chains.add(chain);
		return chain;
	}

	@Override
	public void deleteChain(AbstractChain chain) {
		chains.remove(chain);
	}

	@Override
	public AbstractChain getChain(String chain) {
		for( AbstractChain c : chains ) {
			if( c.getName().equals(chain) )
				return c;
		}
		return null;
	}

	@Override
	public ArrayList<AbstractChain> getChains() {
		return chains;
	}
	
	public String getStartupFile() {
		String text = "";
		text = "# '" + name + ".startup' created by jNetEdit\n\n";
		
		text += "# Interfaces configuration\n";
		for( AbstractInterface iface : interfaces ) {
			text += iface.getConfCommand();
			text += "# Routing configuration\n";
			for( AbstractRoute route : iface.getRoutes() ) {
				text += route.getConfCommand();
			}
		}
		text += "\n\n";
		
		text += "# Firewalling configuration\n";
		for( AbstractChain chain : chains ) {
			text += chain.getConfCommand();
			for( AbstractRule rule : chain.getRules() ) {
				text += rule.getRule();
			}
			text += "\n\n";
		}
		
		return text;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel( String label ) {
		this.label = label;
	}

	@Override
	public String getInterfacesFile() {
		String text = "";
		text = "# '/etc/network/interfaces' created by jNetEdit\n\n";
		
		text += "# Interfaces configuration\n";
		for( AbstractInterface iface : interfaces ) {
			text += iface.getDebianConf();
		}
		text += "\n\n";
		
		text += "# exec firewall config script\n";
		text += "post-up /etc/network/myscript/firewall\n\n";
		
		return text;
	}

	@Override
	public String getScript() {
		String text = "";
		text += "# Firewalling configuration\n";
		for( AbstractChain chain : chains ) {
			text += chain.getConfCommand();
			for( AbstractRule rule : chain.getRules() ) {
				text += rule.getRule();
			}
			text += "\n\n";
		}
		return text;
	}

	@Override
	public AbstractRoute addDefaultGateway(String gw) {
		AbstractRoute route = null;
		for( AbstractInterface iface : interfaces ) {
			String net = iface.getNet();
			String mask = iface.getMask();
			if( IpAddress.ipInNetwork(gw, net, mask)) {
				route = iface.addRoute();
				route.setGw(gw);
				route.setNet("0.0.0.0/0");
				route.setDev(iface.getName());
			}
		}
		return route;
	}
	
	@Override
	public AbstractRoute addRoute (String net, String gw) {
		AbstractRoute route = null;
		for( AbstractInterface iface : interfaces ) {
			String inet = iface.getNet();
			String mask = iface.getMask();
			if( IpAddress.ipInNetwork(gw, inet, mask)) {
				route = iface.addRoute();
				route.setGw(gw);
				route.setNet(net);
				route.setDev(iface.getName());
				return route;
			}
		}
		return route;
	}
}
