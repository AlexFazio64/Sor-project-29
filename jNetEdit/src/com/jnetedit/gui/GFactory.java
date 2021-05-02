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

package com.jnetedit.gui;

import com.jnetedit.common.ItemType;
import com.jnetedit.core.AbstractFactory;
import com.jnetedit.core.nodes.AbstractCollisionDomain;
import com.jnetedit.core.nodes.AbstractHost;
import com.jnetedit.core.nodes.AbstractLink;
import com.jnetedit.core.project.AbstractProject;
import com.jnetedit.gui.nodes.GArea;
import com.jnetedit.gui.nodes.GCollisionDomain;
import com.jnetedit.gui.nodes.GHost;
import com.jnetedit.gui.nodes.GLink;

import edu.umd.cs.piccolo.PLayer;

public class GFactory {

	protected static int serverCounter = 0;
	protected static int nattedServerCounter = 0;
	protected static int pcCounter = 0;
	protected static int routerCounter = 0;
	protected static int firewallCounter = 0;
	protected static int tapCounter = 0;
	protected static int collisionDomainCounter = 0;
	
	protected AbstractFactory absFactory;
	
	protected static GFactory gFactory;
	
	private GFactory( AbstractFactory factory ) {
		this.absFactory = factory;
	}
	
	public static void init( AbstractFactory absFactory ) {
		gFactory = new GFactory(absFactory);
	}
	
	public static GFactory getInstance() {
		return gFactory;
	}
	
	public AbstractProject createProject( String name, String directory ) {
		return absFactory.createProject(name, directory);
	}
	
	public GHost createGHost( ItemType type, double x, double y, PLayer layer ) {
		AbstractHost absHost = absFactory.createHost(type);
		
		return new GHost( x, y, absHost, layer );
	}
	
	public GCollisionDomain createCollisionDomain( double x, double y, PLayer layer ) {
		return new GCollisionDomain(x, y, absFactory.createCollisionDomain(false), layer);
	}
	
	public GCollisionDomain createTap( double x, double y, PLayer layer ) {
		AbstractCollisionDomain tap = absFactory.createCollisionDomain(true);
		if( tap != null )
			return new GCollisionDomain(x, y, tap, layer);
		return null;
	}
	
	public GLink createLink( GHost host, GCollisionDomain collisionDomain, PLayer layer ) {
		AbstractLink link = absFactory.createLink(host.getLogic(), collisionDomain.getLogic());
		if( link != null ) 
			return new GLink(host, collisionDomain, link, layer );
		return null;
	}

	public GArea createArea(double x, double y, PLayer layer) {
		GArea area = new GArea( (int) x, (int) y, layer );
		return area;
	}
}
