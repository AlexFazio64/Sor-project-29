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
import java.awt.Font;
import java.awt.geom.Point2D;

import com.jnetedit.core.nodes.AbstractLink;
import com.jnetedit.gui.GuiManager;
import com.jnetedit.gui.Lab;


import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;

public class GLink extends GNode {
	private static final long serialVersionUID = 1L;
	
	GHost host;
	GCollisionDomain collisionDomain;
	
	AbstractLink absLink;
	
	PPath link;
	
	/** 
	 * Create a graph edge between two node
	 * 
	 * @param host
	 * @param abstractLink 
	 * @param collisionDomain2
	 */
	public GLink( GHost host, GCollisionDomain collisionDomain, AbstractLink abstractLink, PLayer layer ) {
		super( GNode.link, layer );
		
		this.host = host;
		this.collisionDomain = collisionDomain;
		this.absLink = abstractLink;
		this.layer = layer;
		
		link = PPath.createLine(0, 0, 0, 0);
		addChild(link);
		setBounds(link.getBounds());
		link.setPickable(false);
		
		setText(absLink.getInterface().getName());
		
		update();
		
		Lab.getInstance().addNode(getLabNode());
	}

	public void update() {
		Point2D start = host.getFullBoundsReference().getCenter2D();
		Point2D end = collisionDomain.getFullBoundsReference().getCenter2D();
		link.reset();
		link.moveTo((float)start.getX(), (float)start.getY());
		link.lineTo((float)end.getX(), (float)end.getY());
		setBounds(link.getBounds());
		if( text != null ) {
			if( absLink.getInterface().getIp() != null ) {
				text.setText(absLink.getInterface().getName() + "\n" + absLink.getInterface().getIp());
			}
			text.centerFullBoundsOnPoint( link.getBounds().getCenterX(), link.getBounds().getCenterY() + 10 );
		}
	}
	
	public void setText( String aText ) {
		if( text != null ) {
			text.removeFromParent();
		}
		text = new PText(aText);
		text.setPickable(false);
		text.setFont(new Font("", Font.BOLD, 14));
		text.setJustification(0.5f);
		layer.addChild(text);
		update();
	}
	
	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		if( selected )
			link.setPaint(Color.red);
		else
			link.setPaint(Color.black);
	}
	
	@Override
	public void setMouseOver(boolean mouseOver) {
		if( !selected ) {
			if( mouseOver )
				link.setTransparency(0.8f);
			else
				link.setTransparency(1.0f);
		}
	}
	
	public LabNode getLabNode() {
		if( labNode == null )
			labNode = new LabNode(getFullBoundsReference(), GNode.link, absLink);
		return labNode;
	}
	
	public GHost getHost() {
		return host;
	}
	
	public GCollisionDomain getCollisionDomain() {
		return collisionDomain;
	}
	
	public AbstractLink getLogic() {
		return absLink;
	}

	public void delete() {
		super.delete();
		collisionDomain.removeLink( this );
		host.removeLink( this );
		GuiManager.getInstance().getProject().removeLink( absLink );
		GuiManager.getInstance().update();
	}
	
	@Override
	public String toString() {
		return "GLink - from " + host + " to " + collisionDomain.getLogic().getName();
	}
}
