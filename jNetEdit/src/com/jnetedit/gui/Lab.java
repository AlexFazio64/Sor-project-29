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

import java.io.Serializable;
import java.util.ArrayList;

import com.jnetedit.core.project.AbstractProject;
import com.jnetedit.gui.nodes.LabNode;

public class Lab implements Serializable {
	private static final long serialVersionUID = -3935966292120774295L;

	private AbstractProject project;
	
	private ArrayList<LabNode> nodes;
	
	private static Lab lab;
	
	private Lab() {
		nodes = new ArrayList<LabNode>();
	}
	
	public static Lab getInstance() {
		if( lab == null ) {
			lab = new Lab();
		}
		return lab;
	}
	
	public static void setInstance( Lab newLab ) {
		lab = newLab;
	}
	
	public void clear() {
		nodes.clear();
	}
	
	public void setProject( AbstractProject project ) {
		this.project = project;
	}
	
	public AbstractProject getProject() {
		return project;
	}
	
	public ArrayList<LabNode> getNodes() {
		return nodes;
	}
	
	public void addNode( LabNode node ) {
		int size = nodes.size();
		int i;
		for( i = 0; i < size; i++ ) {
			if( nodes.get(i).equals(node) ) {
				nodes.remove(i);
				break;
			}
		}
		nodes.add(i, node);
	}
	
	public void removeNode( LabNode node ) {
		nodes.remove(node);
	}
	
	public void removeNode( Object absNode ) {
		for( LabNode node : nodes ) {
			if( node.getAbsNode() == absNode ) {
				nodes.remove(node);
				return;
			}
		}
	}
}
