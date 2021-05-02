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

package com.jnetedit.gui.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import com.jnetedit.gui.GuiManager;
import com.jnetedit.gui.nodes.GCollisionDomain;
import com.jnetedit.gui.nodes.GHost;
import com.jnetedit.gui.nodes.GNode;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PPath;

public class AddLinkInputHandler extends PBasicInputEventHandler {
	
	GHost host;
	GCollisionDomain collisionDomain;
	
	PPath link;	
	
	@Override
	public void mousePressed( PInputEvent event ) {
		super.mousePressed(event);
		
		if( event.getButton() == MouseEvent.BUTTON1 ) {
			if( !( event.getPickedNode() instanceof GNode) )
				return;
			
			GNode node = (GNode) event.getPickedNode();
			
			if( node.getType() == GNode.host ) {
				host = (GHost) node;
				createLink();
			} else if( node.getType() == GNode.domain ) {
				collisionDomain = (GCollisionDomain) node;
				createLink();
			} 
			
			if( host != null && collisionDomain != null ) {
				GuiManager.getInstance().getCanvas().addLink( host, collisionDomain );
				GuiManager.getInstance().update();
				reset();
			} else {
				update(event);
			}
		} else if( event.getButton() == MouseEvent.BUTTON3 ) {
			reset();
			GuiManager.getInstance().getHandler().switchToDefaultHandler();
		}
	}
	
	@Override
	public void mouseDragged(PInputEvent event) {
		super.mouseDragged(event);
		update(event);
	}
	
	@Override
	public void keyPressed(PInputEvent event) {
		if( event.getKeyCode() == KeyEvent.VK_DELETE ||
			event.getKeyCode() == KeyEvent.VK_CANCEL ) {
			reset();
			GuiManager.getInstance().getHandler().switchToDefaultHandler();
		}
	}
	
	private void createLink() {
		if( link == null ) {
			link = PPath.createLine(0, 0, 0, 0);
			GuiManager.getInstance().getCanvas().getLinkLayer().addChild(link);
		}
	}

	public void reset() {
		host = null;
		collisionDomain = null;
		if( link != null )
			link.removeFromParent();
		link = null;
	}
	
	@Override
	public void mouseMoved(PInputEvent event) {
		update( event );
	}
	
	private void update( PInputEvent e ) {
		Point2D start = null;
		Point2D end = e.getPosition();
		
		if( host != null ) {
			start = host.getFullBoundsReference().getCenter2D();
			if( e.getPickedNode() instanceof GNode && ((GNode) e.getPickedNode()).getType() == GNode.domain )
				end = e.getPickedNode().getGlobalBounds().getCenter2D();
			
		} else if( collisionDomain != null ) {
			start = collisionDomain.getFullBoundsReference().getCenter2D();
			if( e.getPickedNode() instanceof GNode && ((GNode) e.getPickedNode()).getType() == GNode.host )
				end = e.getPickedNode().getGlobalBounds().getCenter2D();
		}
		if( start != null ) {
			link.reset();
			link.moveTo((float)start.getX(), (float)start.getY());
			link.lineTo((float)end.getX(), (float)end.getY());
		}
	}
	
	@Override
	public void mouseEntered(PInputEvent event) {
		super.mouseEntered(event);
		
		if( event.getPickedNode() instanceof GNode ) {
			GNode node = (GNode) event.getPickedNode();
			node.setConnecting(true);
		}
	}
	
	@Override
	public void mouseExited(PInputEvent event) {
		super.mouseExited(event);
		
		if( event.getPickedNode() instanceof GNode ) {
			GNode node = (GNode) event.getPickedNode();
			node.setConnecting(false);
		}
	}
}
