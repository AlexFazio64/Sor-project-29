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

package com.jnetedit.core.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.jnetedit.Main;
import com.jnetedit.core.nodes.AbstractCollisionDomain;
import com.jnetedit.core.nodes.AbstractHost;
import com.jnetedit.core.nodes.AbstractLink;
import com.jnetedit.core.nodes.components.AbstractInterface;


/** Class representig a netkit project
 * 
 * @author sal
 */
public class Project implements AbstractProject, Serializable {
	private static final long serialVersionUID = 4304623857846861337L;

	/** project's name */
	protected String name;
	
	/** project's description */
	protected String description;
	
	/** project's author */
	protected String author;
	
	/** project's directory */
	protected String directory;
	
	/** project's version */
	protected String version;
	
	/** author's email */
	protected String email;
	
	/** web site */
	protected String web;
	
	/** Hosts */
	protected ArrayList<AbstractHost> hosts;
	
	/** Collision Domains */
	protected ArrayList<AbstractCollisionDomain> collisionDomains;
	
	/** links */
	protected ArrayList<AbstractLink> links;
	
	/** the tap */
	protected AbstractCollisionDomain tap;
	
	/**
	 * Create a new empty netkit project with this name in this directory
	 * 
	 * @param name (String) - the project's name
	 * @param directory (String) - the project's directory
	 */
	public Project( String directory, String name ) {
		hosts = new ArrayList<AbstractHost>();
		collisionDomains = new ArrayList<AbstractCollisionDomain>();
		links = new ArrayList<AbstractLink>();
		this.directory = directory;
		this.name = name;
		description = name + "; ";
		version = Main.version;
		author = "jNetEdit";
		email = "deadlyomen17@gmail.it";
		web = "http://slash17.googlecode.com/";
	}
	
	/** Add a new host to the project
	 * 
	 * @param host (Host) the new host to add
	 */
	public void addHost( AbstractHost host ) {
		hosts.add( host );
	}
	
	/** Add a new collision domain to the project
	 * 
	 * @param cd (CollisionDomain) the new collisiondomain
	 */
	public void addCollisionDomain( AbstractCollisionDomain cd ) {
		if( cd.isTap() ) {
			this.tap = cd;
		} else {
			collisionDomains.add( cd );
		}
	}
	
	public void addLink( AbstractLink link ) {
		links.add(link);
	}
	
	/** Remove the host with this name
	 * 
	 * @param host (Host) the host to remove
	 */
	public void removeHost( AbstractHost host ) {
		if( hosts.remove(host) )
			host.delete();
	}
	
	/** Remove the collision domain with this name
	 * 
	 * @param cd (CollisionDomain) the collision domain to remove
	 */
	public void removeCollisionDomain( AbstractCollisionDomain cd ) {
		if( collisionDomains.remove(cd) )
			cd.delete();
	}
	
	public void removeLink( AbstractLink link ) {
		if( links.remove(link) ) 
			link.delete();
	}
	
	/*******************************
	 * Getter and Setter methods
	 *******************************/
	
	public Collection<AbstractHost> getHosts() {
		return hosts;
	}
	
	public Collection<AbstractCollisionDomain> getCollisionDomains() {
		return collisionDomains;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}
	
	public String getLabConfFile() {
		String text = "# 'lab.conf' created by jNetEdit <http://slash17.googlecode.com/>\n\n";
		
		if( description != null )
			text += "LAB_DESCRIPTION=" + description + "\n";
		if( version != null ) 
			text += "LAB_VERSION=" + version + "\n";
		if( author != null )
			text += "LAB_AUTHOR=" + author + "\n";
		if( email != null ) 
			text += "LAB_EMAIL=" + email + "\n";
		if( web != null ) 
			text += "LAB_WEB=" + web + "\n";
		
		text += "\n";
		for( AbstractHost host : hosts ) {
			String hostName = host.getName();
			
			for( AbstractInterface iface : host.getInterfaces() ) {
				String ifaceName =  iface.getName();
				AbstractCollisionDomain cd = iface.getCollisionDomain();
				if( cd.isTap() ) {
					text += hostName + "[" + ifaceName + "]=tap,200.0.0.1," + iface.getIp() + "\n";
				} else {
					String cdName = iface.getCollisionDomain().getName();
					text += hostName + "[" + ifaceName + "]=\"" + cdName + "\"\n";
				}
			}
			text += "\n";
		}
		return text;
	}

	@Override
	public ArrayList<AbstractLink> getLinks() {
		return links;
	}

	@Override
	public boolean existsHost(String name) {
		if( name == null || name.isEmpty() ) 
			return false;
		for( AbstractHost host : hosts ) {
			if( host.getName().equalsIgnoreCase(name) ) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean existsCD(String name) {
		if( name == null || name.isEmpty() ) 
			return false;
		for( AbstractCollisionDomain cd : collisionDomains ) {
			if( cd.getName().equalsIgnoreCase(name) ) {
				return true;
			}
		}
		return false;
	}
}

