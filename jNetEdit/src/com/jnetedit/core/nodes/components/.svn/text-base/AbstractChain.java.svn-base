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

import com.jnetedit.core.nodes.AbstractHost;


public interface AbstractChain {

	public String getName();
	
	public void setName( String name );
	
	public String getPolicy();
	
	public void setPolicy( String policy );
	
	public AbstractHost getHost();
	
	public ArrayList<AbstractRule> getRules();
	
	public AbstractRule getRule( String rule );
	
	public AbstractRule addRule();
	
	public void deleteRule( AbstractRule rule );

	public void delete();

	public String getConfCommand();

	public void ruleUp(AbstractRule rule);

	public void ruleDown(AbstractRule rule);
}
