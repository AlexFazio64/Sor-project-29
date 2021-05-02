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

import java.util.ArrayList;

import com.jnetedit.common.ItemType;
import com.jnetedit.core.nodes.components.AbstractChain;
import com.jnetedit.core.nodes.components.AbstractInterface;
import com.jnetedit.core.nodes.components.AbstractRoute;


public interface AbstractHost {

	public String getName();
	
	public void setName( String name );
	
	
	public String getLabel();
	
	public void setLabel(String label);
	
	
	public AbstractInterface addInterface( AbstractCollisionDomain cd );
	
	public AbstractInterface getInterface( String name );
	
	public ArrayList<AbstractInterface> getInterfaces();
	
	public boolean deleteInterface( AbstractInterface iface );
	
	
	public AbstractChain addChain();
	
	public AbstractChain getChain( String chain );
	
	public ArrayList<AbstractChain> getChains();
	
	public void deleteChain( AbstractChain chain );
	
	public boolean delete();

	public ItemType getType();

	public boolean isConnectedTo( AbstractCollisionDomain collisionDomain );
	
	public String getStartupFile();

	public String getInterfacesFile();

	public String getScript();
	

	public AbstractRoute addDefaultGateway(String gw);

	public AbstractRoute addRoute(String net, String gw);
}
