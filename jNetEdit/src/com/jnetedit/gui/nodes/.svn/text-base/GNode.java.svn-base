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

import javax.swing.JPopupMenu;

import com.jnetedit.gui.Lab;
import com.jnetedit.gui.ProjectHandler;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PText;

public class GNode extends PNode {
	private static final long serialVersionUID = 3165028185079487978L;

	public static final int host = 0;
	public static final int domain = 1;
	public static final int link = 2;
	public static final int area = 3;
	
	int type;
	
	PLayer layer;
	
	PText text;
	
	JPopupMenu menu;
	
	boolean selected;
	
	LabNode labNode;
	
	public GNode( int type, PLayer layer ) {
		this.type = type;
		this.layer = layer;
		
		selected = false;
		
		layer.addChild(this);
		
		ProjectHandler.getInstance().setSaved(false);
	}
	
	public void setText( String aText ) {
		text = new PText( aText );
		text.setPickable(false);
		layer.addChild(text);
		ProjectHandler.getInstance().setSaved(false);
	}
	
	public void update() {
		double x = getGlobalTranslation().getX();
		double y = getGlobalTranslation().getY();
		text.centerFullBoundsOnPoint(x + getWidth()/2, y + getHeight() + 10);
		ProjectHandler.getInstance().setSaved(false);
	}
	
	public void delete() {
		removeFromParent();
		if( text != null )
			text.removeFromParent();
		if( labNode != null )
			Lab.getInstance().removeNode(labNode);
		ProjectHandler.getInstance().setSaved(false);
	}
	
	public LabNode getLabNode() {
		return labNode;
	}
	
	public int getType() {
		return type;
	}

	public PLayer getLayer() {
		return layer;
	}

	public PText getText() {
		return text;
	}

	public JPopupMenu getMenu() {
		return menu;
	}
	
	public void setSelected( boolean selected ) {
		this.selected = selected;
	}
	
	public void showMenu( PInputEvent e ) {
		// to override
	}
	
	public void setMouseOver( boolean mouseOver ) {
		// to override
	}
	
	public void setConnecting( boolean connecting ) {
		// to override
	}
}
