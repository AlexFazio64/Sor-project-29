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

public interface AbstractRule {
	
	public String getRule();

	public String getTarget();
	
	public String getProtocol();
	
	public String getSource();
	
	public String getDestination();
	
	public String getInputIface();
	
	public String getOutputIface();
	
	public int getDestPort();
	
	public int getSourcePort();
	
	public AbstractChain getChain();
	
	public void setRule( String rule );
	
	public void setTarget( String target );
	
	public void setProtocol( String protocol );
	
	public void setSource( String source );
	
	public void setDestination( String destination );
	
	public void setInputIface( String iface );
	
	public void setOutputIface( String iface );
	
	public void setDestPort( int port );
	
	public void setSourcePort( int port );

	public void delete();

	public String getName();
	
	public void setName( String name );
}
