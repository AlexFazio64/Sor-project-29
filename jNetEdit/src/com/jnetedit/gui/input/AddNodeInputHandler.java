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

import com.jnetedit.common.ItemType;
import com.jnetedit.gui.GuiManager;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;

public class AddNodeInputHandler extends PBasicInputEventHandler {
	
	ItemType nodeType;
	
	public ItemType getNodeType() {
		return nodeType;
	}

	public AddNodeInputHandler( ItemType nodeType ) {
		this.nodeType = nodeType;
	}
	
	@Override
	public void mouseClicked( PInputEvent event ) {
		super.mousePressed(event);
		
		if( event.getButton() == MouseEvent.BUTTON1 ) {
			GuiManager.getInstance().getCanvas().addNode( nodeType, event.getPosition() );
			GuiManager.getInstance().update();
		} else {
			GuiManager.getInstance().getHandler().switchToDefaultHandler();
		}
	}
}
