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

import java.awt.event.MouseEvent;

import com.jnetedit.gui.GuiManager;
import com.jnetedit.gui.nodes.GHost;
import com.jnetedit.gui.nodes.GNode;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.util.PDimension;

public class DefaultInputHandler extends PBasicInputEventHandler {
	
	private int pressedButton;

	private GNode selectedNode;
	
	@Override
	public void mouseClicked(PInputEvent event) {
		super.mouseClicked(event);
		
		if( event.getPickedNode() instanceof GNode ) {
			GNode node = (GNode) event.getPickedNode();
			
			if( selectedNode != null ) {
				selectedNode.setSelected(false);
			}
			selectedNode = node;
			selectedNode.setSelected(true);	
			
			if( node.getType() == GNode.host ) {
				// expand this host's configuration in the trees
				GuiManager.getInstance().getConfPanel().selectHost( ((GHost) node).getLogic().getName() );
			}
			
			if( event.getButton() == MouseEvent.BUTTON3 ) {
				node.showMenu(event);
			}
		} else {
			if( selectedNode != null ) {
				selectedNode.setSelected(false);
			}
		}
	}
	
	@Override
	public void mouseEntered(PInputEvent event) {
		super.mouseEntered(event);
		
		if( event.getPickedNode() instanceof GNode ) {
			GNode node = (GNode) event.getPickedNode();
			node.setMouseOver(true);
		}
	}
	
	@Override
	public void mouseExited(PInputEvent event) {
		super.mouseExited(event);
		
		if( event.getPickedNode() instanceof GNode ) {
			GNode node = (GNode) event.getPickedNode();
			node.setMouseOver(false);
		}
	}
	
	@Override
	public void mousePressed(PInputEvent event) {
		if( event.getPickedNode() instanceof GNode ) {
			GNode node = (GNode) event.getPickedNode();
			node.setMouseOver(true);
		}
		pressedButton = event.getButton();
	}
	
	@Override
	public void mouseReleased(PInputEvent event) {
		pressedButton = -1;
	}
	
	@Override
	public void mouseDragged(PInputEvent event) {
		if( !(event.getPickedNode() instanceof GNode) ) 
			return;
		
		if( pressedButton == MouseEvent.BUTTON1 ) {
			GNode node = (GNode) event.getPickedNode();
			
			if( node.getType() == GNode.link )
				return;
			
			PDimension d = event.getDeltaRelativeTo(node);		
			node.localToParent(d);
			node.offset(d.getWidth(), d.getHeight());
			
			node.setSelected(false);
			node.setMouseOver(true);
			node.update();
		}
	}
}
