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

import java.awt.Cursor;

import com.jnetedit.common.ItemType;
import com.jnetedit.gui.GuiManager;
import com.jnetedit.gui.gcomponents.GCanvas;
import com.jnetedit.gui.gcomponents.GFrame;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;


public class HandlerManager {
	
	DefaultInputHandler defaultHandler;
	DeleteInputHandler deleteHandler;
	AddNodeInputHandler addNodeHandler;
	AddLinkInputHandler addLinkHandler;
	
	PBasicInputEventHandler currentHandler;

	GCanvas canvas;
	GFrame frame;
	
	PLayer nodeLayer;
	PLayer linkLayer;
	PLayer areaLayer;
	
	public void start() {
		this.canvas = GuiManager.getInstance().getCanvas();
		this.frame = GuiManager.getInstance().getFrame();
		nodeLayer = canvas.getNodeLayer();
		linkLayer = canvas.getLinkLayer();
		areaLayer = canvas.getAreaLayer();
		
		defaultHandler = new DefaultInputHandler();
		deleteHandler = new DeleteInputHandler();
		addLinkHandler = new AddLinkInputHandler();
		
		switchToDefaultHandler();
	}
	
	public void switchToAddHandler( ItemType type ) {
		removeCurrentHandler();
		
		if( type == ItemType.LINK ) {
			canvas.addInputEventListener(addLinkHandler);
			currentHandler = addLinkHandler;
		} else {
			canvas.addInputEventListener(addNodeHandler);
			currentHandler = addNodeHandler;
		}
	}
	
	public void switchToDeleteHandler() {
		removeCurrentHandler();
		
		canvas.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		canvas.addInputEventListener(deleteHandler);
		currentHandler = deleteHandler;
	}
	
	public void switchToDefaultHandler() {
		removeCurrentHandler();
		
		canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		canvas.addInputEventListener(defaultHandler);
		currentHandler = defaultHandler;
	}
	
	private void removeCurrentHandler() {
		canvas.removeInputEventListener(currentHandler);
	}
	
	public void adding( ItemType type ) {
		if( (currentHandler == addNodeHandler && addNodeHandler.getNodeType() == type) ||
				(currentHandler == addLinkHandler && type == ItemType.LINK) ) {
			switchToDefaultHandler();
			return;
		}
		if( type != ItemType.LINK ) {
			// remove previously created addhandler
			if( currentHandler.equals(addNodeHandler) ) {
				canvas.removeInputEventListener(addNodeHandler);
			}
			// create a new addhandler for this node type
			addNodeHandler = new AddNodeInputHandler(type);
		}
		switchToAddHandler( type );
	}
	
	public void deleting() {
		if( currentHandler == deleteHandler )
			switchToDefaultHandler();
		else 
			switchToDeleteHandler();
	}
}
