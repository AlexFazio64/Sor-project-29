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

package com.jnetedit.generator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.jnetedit.common.ItemType;
import com.jnetedit.common.NameGenerator;
import com.jnetedit.core.AbstractFactory;
import com.jnetedit.core.nodes.AbstractCollisionDomain;
import com.jnetedit.core.nodes.AbstractHost;
import com.jnetedit.core.nodes.AbstractLink;
import com.jnetedit.core.project.AbstractProject;
import com.jnetedit.core.util.IpGenerator;
import com.jnetedit.gui.Lab;
import com.jnetedit.gui.ProjectHandler;
import com.jnetedit.gui.nodes.GNode;
import com.jnetedit.gui.nodes.LabNode;

public class Topology {
	int maxFirewalls = 2;
	int maxRouters = 4;
	
	LinkedList<String> areas;
	
	ArrayList<AbstractHost> hostsToRemove = new ArrayList<AbstractHost>();
	ArrayList<AbstractCollisionDomain> domainToRemove = new ArrayList<AbstractCollisionDomain>();
	
	AbstractLayout layout;
	
	ArrayList<String> services = new ArrayList<String>();
	{
		services.add("FTP");
		services.add("sFTP");
		services.add("Http");
		services.add("DNS");
		services.add("Telnet");
		services.add("SSH");
		services.add("SMTP");
		services.add("Https");
	}
	
	Random r;
	
	AbstractFactory factory;
	
	AbstractProject project;
	
	Lab lab;
	
	AbstractHost mainGateway; 
	
	AbstractCollisionDomain TAP;

	int firewallsNumber;
	int routersNumber;
	
	public Topology(String name, String directory, LinkedList<String> areas, 
			int maxRouters, AbstractFactory factory, AbstractLayout layout) {
		this.maxRouters = maxRouters;
		this.factory = factory;
		this.layout = layout;
		this.areas = new LinkedList<String>();
		this.areas.addAll(areas);
		this.r = new Random();
		this.firewallsNumber = 0;
		this.routersNumber = 0;
		
		NameGenerator.reset();
		IpGenerator.reset();
		
		project = factory.createProject(name, directory+"/"+name);
		lab = Lab.getInstance();
		lab.clear();
		lab.setProject(project);
	}
	
	public Topology createTopology() {
		System.out.println(project.getName() + " generation started");
		
		// create the tap
		addTap();
		// create main gateway (connected to tap)
		AbstractHost mainGateway = addFirewall();
		setMainGateway(mainGateway);
		// connect the main gateway to the tap (eth0)
		connect(mainGateway, TAP);
		
		// eth1 - cd1 (random area, random min ip)
		AbstractCollisionDomain cd1 = addDomain(true);
		if( cd1 != null ) {
			connect(mainGateway, cd1);
			populate(cd1);
		}
		
		// eth2 - cd2 (subnet with a firewall or a router as gateway)
		AbstractCollisionDomain cd2 = addDomain(false);
		connect(mainGateway, cd2);
		AbstractHost gateway = r.nextBoolean() ? addFirewall() : addRouter();
		
		// eth3 - cd3 (subnet with a router as gateway)
		AbstractCollisionDomain cd3 = addDomain(false);
		connect(mainGateway, cd3);
		AbstractHost router = addRouter();
		
		// expand and populate the subnets
		if( gateway != null ) {
			if( gateway.getType() == ItemType.FIREWALL ) {
				expandFirewall(gateway);
			} else {
				expandRouter(gateway);
			}
			connect(gateway, cd2);
		} else {
			String area = getRandomArea();
			if( area != null ) {
				cd2.setArea(area);
				populate(cd2);
			}
		}
		if( router != null ) {
			connect(router, cd3);
			expandRouter(router);
		}
		
		// remove unused hosts and domains 
		while( !hostsToRemove.isEmpty() || !domainToRemove.isEmpty() ) {
			while( !hostsToRemove.isEmpty() ) {
				removeHost(hostsToRemove.remove(0));
			}
			while( !domainToRemove.isEmpty() ) {
				removeDomain(domainToRemove.remove(0));
			}
		}
		
		// save the project
		ProjectHandler.getInstance().saveProjectSilent();
		System.out.println(project.getName() + " generated");
		
		return this;
	} 
	
	/** Popolate a collision domain with some hosts
	 * @param cd the domain to popolate with some hosts
	 */
	private void populate(AbstractCollisionDomain cd) {
		String area = cd.getArea();
		int hosts = r.nextInt(2) + 1;
		// connect one or two host to this domain
		for( int i = 0; i < hosts; i++ ) {
			AbstractHost host;
			int random = r.nextInt(100);
			if( area.matches(".*(DMZ|dmz).*") ) {
				if( random < 70 ) {
					host = addHost(ItemType.SERVER);
				} else {
					host = addHost(ItemType.NATTEDSERVER);
				}
				host.setLabel(host.getLabel() + " ("+getRandomService()+")");
			} else {
				host = addHost(ItemType.PC);
			}
			connect(host, cd);
		}
	}

	/** Create a subnet with this fw as gateway
	 * @param fw the fw from witch to create a subnet
	 */
	private void expandFirewall(AbstractHost fw) {
		// try to connect a router to the fw
		AbstractHost router = addRouter();
		if( router != null ) {
			AbstractCollisionDomain cd = addDomain(false);
			connect(fw, cd);
			connect(router, cd);
			expandRouter(router);
		}
		
		String area = getRandomArea();
		boolean areaUsed = false;
		int fwIfaces = r.nextInt(2) + 1;
		for( int i = 0; i < fwIfaces; i++ ) { 
			// 40% of probabilty to connect another router to the fw
			if( r.nextInt(100) < 40 ) {
				AbstractHost anotherRouter = addRouter();
				if( anotherRouter != null ) {
					AbstractCollisionDomain domain = addDomain(false);
					connect(fw, domain);
					connect(anotherRouter, domain);
					expandRouter(anotherRouter);
				}
			} else {
				AbstractCollisionDomain domainInArea = addDomain(area);
				if( domainInArea != null ) {
					areaUsed = true;
					connect(fw, domainInArea);
					populate(domainInArea);
				}
			}
		}
		if( !areaUsed )
			areas.add(area);
	}

	/** Create a subnet with this router as gateway
	 * @param router the router from witch to create a subnet
	 */
	private void expandRouter(AbstractHost router) {
		String area = getRandomArea();
		boolean areaUsed = false;
		// create a subnet with a firewall if possible
		AbstractHost fw = addFirewall();
		if( fw != null ) {
			AbstractCollisionDomain routerToFw = addDomain(false);
			connect(router, routerToFw);
			connect(fw, routerToFw);
			expandFirewall(fw);
		}
		
		if( area != null ) {
			int routerIfaces = 2;
			for( int i = 0; i < routerIfaces; i++ ) {
				AbstractCollisionDomain domain = addDomain(area);
				if( domain != null ) {
					areaUsed = true;
					connect(router, domain);
					populate(domain);
				}
			}
		}
		
		if( router.getInterfaces().size() == 1 ) {
			removeHost(router);
		}
		if( !areaUsed )
			areas.add(area);
	}

	/** return an int between 4 and 512 */
	private int getMinIp() {
		return r.nextInt(508) + 4;
	}

	/**
	 * @return String a service (eg. http,ssh,ftp) or a port number >1024
	 */
	private String getRandomService() {
		int nServices = services.size();
		if( nServices == 0 ) 
			return "port: " + (r.nextInt(6000) + 1024);
		return services.remove(r.nextInt(nServices));
	}

	/**
	 * @return String one (random) of the areas or null if there aren't
	 */
	private String getRandomArea() {
		int nAreas = areas.size();
		if( nAreas == 0 ) 
			return null;
		return areas.remove(r.nextInt(nAreas));
	}
	
	/**
	 * @return the added firewall or null if it's not possibile to add another one
	 */
	private AbstractHost addFirewall() {
		if( firewallsNumber >= maxFirewalls ) 
			return null;
		AbstractHost fw = factory.createHost(ItemType.FIREWALL);
		project.addHost(fw);
		lab.addNode(new LabNode(layout.getBounds(fw), GNode.host, fw));
		firewallsNumber++;
		return fw;
	}

	/**
	 * @return the added router or null if it's not possibile to add another one
	 */
	private AbstractHost addRouter() {
		if( routersNumber >= maxRouters ) 
			return null;
		AbstractHost router = factory.createHost(ItemType.ROUTER);
		project.addHost(router);
		lab.addNode(new LabNode(layout.getBounds(router), GNode.host, router));
		routersNumber++;
		return router;
	}
	
	/**
	 * @param type the type of the host to add (ItemType)
	 * @return the added host
	 */
	private AbstractHost addHost(ItemType type) {
		AbstractHost host = factory.createHost(type);
		project.addHost(host);
		lab.addNode(new LabNode(layout.getBounds(host), GNode.host, host));
		return host;
	}
	
	/**
	 * add the tap to this lab
	 */
	private void addTap() {
		TAP = factory.createCollisionDomain(true);
		lab.addNode(new LabNode(layout.getBounds(TAP), GNode.domain, TAP));
		project.addCollisionDomain(TAP);
	}
	
	/**
	 * @param withArea true if this domain is into an area
	 * @return the added domain or null if is not possibile to add another domain (no more area) 
	 */
	private AbstractCollisionDomain addDomain(boolean withArea) {
		AbstractCollisionDomain cd;
		if( withArea ) {
			String area = getRandomArea();
			if( area == null ) 
				return null;
			cd = factory.createCollisionDomain(false);
			lab.addNode(new LabNode(layout.getBounds(cd), GNode.domain, cd));
			cd.setArea(area);
			cd.setMinimumIp(getMinIp());
			project.addCollisionDomain(cd);
		} else {
			cd = factory.createCollisionDomain(false);
			project.addCollisionDomain(cd);
			lab.addNode(new LabNode(layout.getBounds(cd), GNode.domain, cd));
		}
		return cd;
	}
	
	/**
	 * @param area the area 
	 * @return the added domain or null if this area is null
	 */
	private AbstractCollisionDomain addDomain(String area) {
		if( area == null ) 
			return null;
		AbstractCollisionDomain cd = factory.createCollisionDomain(false);
		lab.addNode(new LabNode(layout.getBounds(cd), GNode.domain, cd));
		cd.setArea(area);
		cd.setMinimumIp(getMinIp());
		project.addCollisionDomain(cd);
		return cd;
	}
	
	/** Connect this host to this collision domain, adding an interface 
	 * to the host and a link from it to the domain
	 * @param host the host to connect to the collision domain
	 * @param cd the collision domain
	 */
	private void connect( AbstractHost host, AbstractCollisionDomain cd ) {
		AbstractLink link = factory.createLink(host, cd);
		if( link != null ) 
			project.addLink(link);
		lab.addNode(new LabNode(null, GNode.link, link));
	}
	
	/**
	 * Remove this host, all links started from it and eventually
	 * all the collision domains become empty
	 * @param host the host to remove 
	 */
	private void removeHost( AbstractHost host ) {
		for( int i = 0; i < project.getLinks().size(); i++ ) {
			AbstractLink link = project.getLinks().get(i);
			if( link.getInterface().getHost().getName().equals(host.getName()) ) {
				AbstractCollisionDomain cd = link.getInterface().getCollisionDomain();
				domainToRemove.add(cd);
				lab.removeNode(link);
				project.getLinks().remove(i);
				link.delete();
				i--;
			}
		}
		lab.removeNode(host);
		project.removeHost(host);
		host.delete();
	}
	
	/**
	 * Remove this domain, all links ended in it and all hosts previusly 
	 * connected to it with no more interfaces
	 * @param cd the domain to remove
	 */
	private void removeDomain( AbstractCollisionDomain cd ) {
		for( int i = 0; i < project.getLinks().size(); i++ ) {
			AbstractLink link = project.getLinks().get(i);
			if( link.getInterface().getCollisionDomain().getName().equals(cd.getName()) ) {
				AbstractHost host = link.getInterface().getHost();
				link.getInterface().delete();
				lab.removeNode(link);
				project.getLinks().remove(i);
				link.delete();
				i--;
				if( host.getInterfaces().size() == 0 ) 
					hostsToRemove.add(host);
			}
		}
		lab.removeNode(cd);
		project.removeCollisionDomain(cd);
		cd.delete();
	}
	
	/** GETTER AND SETTER METHODS */
	
	public AbstractCollisionDomain getTAP() {
		return TAP;
	}

	public void setTAP(AbstractCollisionDomain tAP) {
		TAP = tAP;
	}
	
	public int getFirewallsNumber() {
		return firewallsNumber;
	}


	public void setFirewallsNumber(int firewallsNumber) {
		this.firewallsNumber = firewallsNumber;
	}

	public void setMainGateway(AbstractHost mainGateway) {
		this.mainGateway = mainGateway;
	}


	public AbstractHost getMainGateway() {
		return mainGateway;
	}
	
	public AbstractProject getProject() {
		return project;
	}

	public int getMaxRouters() {
		return maxRouters;
	}

	public int getRoutersNumer() {
		return routersNumber;
	}
}
