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

import com.jnetedit.core.nodes.components.AbstractInterface;
import com.jnetedit.core.nodes.components.Interface;
import com.jnetedit.core.util.IpGenerator;

public class Link implements AbstractLink, Serializable {
	private static final long serialVersionUID = 6811160844126481939L;

	/** link's id */
	protected String id;
	
	/** Host's interface */
	protected AbstractInterface hostInterface;
	
	/** collision domain */
	protected AbstractCollisionDomain collisionDomain;
	
	/** Connect an host to a collision domain
	 * 
	 * @param hostInterface
	 * @param collisionDomain
	 */
	public Link( AbstractInterface hostInterface, AbstractCollisionDomain collisionDomain ) {
		this.hostInterface = hostInterface;
		this.collisionDomain = collisionDomain;
		if( this.collisionDomain.isTap() ) {
			this.hostInterface.setIp(IpGenerator.getNextTapIp());
			this.hostInterface.setConnectedToTap(true);
		}
	}
	
	
	/****************************
	 * Getter and Setter methods
	 ****************************/

	public AbstractCollisionDomain getCollisionDomain() {
		return collisionDomain;
	}

	public void setCollisionDomain(CollisionDomain collisionDomain) {
		this.collisionDomain = collisionDomain;
	}
	
	public AbstractInterface getHostInterface() {
		return hostInterface;
	}

	public void setHostInterface(Interface hostInterface) {
		this.hostInterface = hostInterface;
	}
	
	public AbstractHost getHost() {
		return hostInterface.getHost();
	}


	@Override
	public void delete() {
		collisionDomain.removeConnection( hostInterface );
		hostInterface.delete();
	}


	@Override
	public AbstractInterface getInterface() {
		return hostInterface;
	}
}
