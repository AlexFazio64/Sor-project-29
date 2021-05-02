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

import java.awt.geom.Rectangle2D;

import com.jnetedit.core.nodes.AbstractCollisionDomain;
import com.jnetedit.core.nodes.AbstractHost;

/**
 * Interface which define an automatic layouting for jNetEdit nodes
 */
public interface AbstractLayout {

	/**
	 * Called to get the position of an host in the canvas 
	 * for a automatic layouting
	 * 
	 * @param host - (AbstractHost) the node to bound
	 * @return the bounds for this node
	 */
	public Rectangle2D getBounds(AbstractHost host);
	
	/**
	 * Called to get the position of a domain in the canvas 
	 * for a automatic layouting
	 * 
	 * @param domain - (AbstractCollisionDomain) the domain to bound
	 * @return the bounds for this node
	 */
	public Rectangle2D getBounds(AbstractCollisionDomain domain);
}
