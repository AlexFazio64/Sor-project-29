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

package com.jnetedit.core.nodes.components;

import java.util.ArrayList;

import com.jnetedit.core.nodes.AbstractCollisionDomain;
import com.jnetedit.core.nodes.AbstractHost;

public interface AbstractInterface {
	
	public String getName();
	
	public String getIp();
	
	public String getNet();
	
	public String getMask();
	
	public String getBCast();
	
	public AbstractCollisionDomain getCollisionDomain();
	
	public AbstractHost getHost();
	
	public void setName( String name );
	
	public boolean setIp( String ip );
	
	public boolean setNet( String net );
	
	public boolean setMask( String mask );
	
	public boolean setBCast( String bcast );
	
	public void setCollisionDomain( AbstractCollisionDomain collisionDomain );
	
	public void setHost( AbstractHost host );
	
	public void reset();

	public void delete();
	
	public String getConfCommand();

	public String getDebianConf();
	
	public void setConnectedToTap(boolean locked);

	public boolean isConnectedToTap();

	void deleteRoute(AbstractRoute route);

	AbstractRoute addRoute();

	ArrayList<AbstractRoute> getRoutes();

	AbstractRoute getRoute(String net);
}
