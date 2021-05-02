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

import java.util.ArrayList;
import java.util.Collection;

import com.jnetedit.core.nodes.AbstractCollisionDomain;
import com.jnetedit.core.nodes.AbstractHost;
import com.jnetedit.core.nodes.AbstractLink;


public interface AbstractProject {
	
	public String getName();
	
	public String getDirectory();
	
	public String getDescription();
	
	public String getAuthor();
	
	public String getEmail();
	
	public String getWeb();
	
	public String getVersion();
	
	public void setName(String projectName);

	public void setDirectory(String string);
	
	public void setDescription(String description);
	
	public void setAuthor(String author);
	
	public void setEmail(String email);
	
	public void setWeb(String web);
	
	public void setVersion(String version);
	
	
	public void addHost( AbstractHost host );
	
	public void addCollisionDomain( AbstractCollisionDomain cd );
	
	public void addLink( AbstractLink link );
	
	
	public void removeHost( AbstractHost abstractHost );
	
	public void removeCollisionDomain( AbstractCollisionDomain abstractCollisionDomain );
	
	public void removeLink( AbstractLink link );
	

	public Collection<AbstractHost> getHosts();
	
	public Collection<AbstractCollisionDomain> getCollisionDomains();

	public String getLabConfFile();

	public ArrayList<AbstractLink> getLinks();

	public boolean existsHost(String name);

	public boolean existsCD(String name);
}
