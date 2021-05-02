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

package com.jnetedit.core;

import java.io.Serializable;

import com.jnetedit.common.ItemType;
import com.jnetedit.common.NameGenerator;
import com.jnetedit.core.nodes.AbstractCollisionDomain;
import com.jnetedit.core.nodes.AbstractHost;
import com.jnetedit.core.nodes.AbstractLink;
import com.jnetedit.core.nodes.CollisionDomain;
import com.jnetedit.core.nodes.Host;
import com.jnetedit.core.nodes.Link;
import com.jnetedit.core.nodes.components.AbstractInterface;
import com.jnetedit.core.project.AbstractProject;
import com.jnetedit.core.project.Project;


public class Factory implements AbstractFactory, Serializable {
	private static final long serialVersionUID = -4123193508601850436L;
	
	public Factory() {
	}
	
	@Override
	public AbstractCollisionDomain createCollisionDomain( boolean isTap ) {
		if( isTap ) {
			String name = "TAP";
			AbstractCollisionDomain cd = new CollisionDomain(name);
			cd.setIsTap(true);
			return cd;
		} else {
			return new CollisionDomain( NameGenerator.getNextName( ItemType.COLLISIONDOMAIN ) );
		}
	}

	@Override
	public AbstractHost createHost(ItemType type) {
		return new Host( NameGenerator.getNextName(type), type );
	}

	@Override
	public AbstractLink createLink(AbstractHost host, AbstractCollisionDomain collisionDomain) {
		if( host.isConnectedTo( collisionDomain ) ) 
			return null;
		
		AbstractInterface iface = host.addInterface(collisionDomain);
		collisionDomain.addConnection(iface);
		
		if( iface != null ) 	
			return new Link(iface, collisionDomain);
		else return null;
	}

	@Override
	public AbstractProject createProject(String name, String directory) {
		return new Project(directory, name);
	}
}
