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

package com.jnetedit.gui.nodes;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import edu.umd.cs.piccolo.util.PBounds;

/** Utility class to store node's informations for saving
 */
public class LabNode implements Serializable {
	private static final long serialVersionUID = 2877077749467465200L;

	Object absNode;
	
	int type;
	
	Rectangle2D bounds;
	
	Color color;
	
	String text;
	
	public LabNode( Rectangle2D bounds, int type, Object absNode ) {
		this.absNode = absNode;
		this.type = type;
		this.bounds = bounds;
	}
	
	public double getX() {
		return bounds.getCenterX();
	}

	public double getY() {
		return bounds.getCenterY();
	}
	
	public int getType() {
		return type;
	}

	public Object getAbsNode() {
		return absNode;
	}
	
	public boolean equals( LabNode node ) {
		if( node.absNode == null ) 
			return false;
		return ( node.absNode == this.absNode );
	}

	public Rectangle2D getBounds() {
		return bounds;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public void setBounds(PBounds bounds) {
		this.bounds = bounds;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}