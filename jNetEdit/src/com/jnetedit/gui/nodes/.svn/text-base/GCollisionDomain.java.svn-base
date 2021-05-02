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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import com.jnetedit.core.nodes.AbstractCollisionDomain;
import com.jnetedit.gui.GuiManager;
import com.jnetedit.gui.Lab;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PImage;

public class GCollisionDomain extends GNode {
	private static final long serialVersionUID = -5648625660569308724L;
	
	protected PImage defaultImage;
	protected PImage selectedImage;
	protected PImage mouseOverImage;
	
	protected PImage currentImage;
	
	protected ArrayList<GLink> links;
	
	protected AbstractCollisionDomain absCollisionDomain;
	
	public GCollisionDomain( double x, double y, AbstractCollisionDomain collisionDomain, PLayer layer ) {
		super( GNode.domain, layer );
		
		this.links = new ArrayList<GLink>();
		this.absCollisionDomain = collisionDomain;
		
		if( absCollisionDomain.isTap() ) {
			defaultImage = new PImage("data/images/big/tap.png");
			selectedImage = new PImage("data/images/big/tap_selected.png");
			mouseOverImage = new PImage("data/images/big/tap_mouseover.png");
		} else {
			defaultImage = new PImage("data/images/big/collisionDomain.png");
			selectedImage = new PImage("data/images/big/collisionDomain_selected.png");
			mouseOverImage = new PImage("data/images/big/collisionDomain_mouseover.png");
		}
		
		setImage(defaultImage);
		
		setText(absCollisionDomain.getLabel());
		
		centerFullBoundsOnPoint(x, y);
		
		createPopupMenu();
		
		update();
		
		Lab.getInstance().addNode(getLabNode());
	}
	
	protected void createPopupMenu() {
		menu = new JPopupMenu();
		
		JMenuItem rename = new JMenuItem("Rename", new ImageIcon("data/images/16x16/text_icon.png"));
		rename.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog("New name");
				while( GuiManager.getInstance().getProject().existsCD(name) ) {
					name = JOptionPane.showInputDialog("Another domain with this name exists. New name");
				}
				if( name != null && !name.isEmpty() ) {
					absCollisionDomain.setName(name);
					absCollisionDomain.setLabel(name);
					text.setText(name);
					GuiManager.getInstance().getConfPanel().update();
				}
			}
		});
		menu.add(rename);
		
		JMenuItem delete = new JMenuItem("Delete", new ImageIcon("data/images/16x16/delete_icon.png"));
		delete.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		menu.add(delete);
	}
	
	@Override
	public void showMenu(PInputEvent e) {
		super.showMenu(e);
		menu.show((Component) e.getComponent(), (int) e.getCanvasPosition().getX(), (int) e.getCanvasPosition().getY());
	}
	
	protected void setImage( PImage newImage ) {
		if( currentImage != null ) {
			removeChild(currentImage);
		}
		currentImage = newImage;
		addChild(currentImage);
		setBounds(currentImage.getBounds());
		currentImage.centerFullBoundsOnPoint(getBounds().getCenterX(), getBounds().getCenterY());
		currentImage.setPickable(false);
	}
	
	public void setSelected( boolean selected ) {
		super.setSelected(selected);
		if( selected ) {
			setImage(selectedImage);
		} else {
			setImage(defaultImage);
		}
	}
	
	public void setMouseOver( boolean mouseOver ) {
		if( !selected ) {
			if( mouseOver ) 
				setImage(mouseOverImage);
			else 
				setImage(defaultImage);
		}
	}
	
	public void update() {
		super.update();
		
		for( GLink gl : links ) {
			gl.update();
		}
	}
		
	public LabNode getLabNode() {
		if( labNode == null )
			labNode = new LabNode(getFullBoundsReference(), GNode.domain, absCollisionDomain);
		return labNode;
	}
	
	public GLink getLink( int index ) {
		return links.get( index );
	}
	
	public void addLink( GLink link ) {
		links.add(link);
	}
	
	public ArrayList<GLink> getLinks() {
		return links;
	}
	
	public AbstractCollisionDomain getLogic() {
		return absCollisionDomain;
	}
	
	public void delete() {
		while( !links.isEmpty() ) {
			links.get(0).delete();
		}
		super.delete();
		GuiManager.getInstance().getProject().removeCollisionDomain( absCollisionDomain );
		GuiManager.getInstance().update();
	}

	public void removeLink(GLink gLink) {
		links.remove(gLink);
	}
}

