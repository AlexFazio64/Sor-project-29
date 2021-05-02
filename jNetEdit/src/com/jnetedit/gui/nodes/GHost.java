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

import com.jnetedit.common.ItemType;
import com.jnetedit.core.nodes.AbstractHost;
import com.jnetedit.gui.GuiManager;
import com.jnetedit.gui.Lab;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PImage;

public class GHost extends GNode {
	private static final long serialVersionUID = 1L;

	protected static final String serverImage = "data/images/big/server.png";
	protected static final String nattedServerImage = "data/images/big/nattedserver.png";
	protected static final String pcImage = "data/images/big/pc.png";
	protected static final String routerImage = "data/images/big/router.png";
	protected static final String firewallImage = "data/images/big/firewall.png";
	protected static final String tapImage = "data/images/big/tap.png";
	protected static final String collisionDomainImage = "data/images/big/collisionDomain.png";
	
	protected PImage defaultImage;
	protected PImage selectedImage;
	protected PImage mouseOverImage;
	protected PImage connectImage;
	
	protected PImage currentImage;
	
	protected ArrayList<GLink> links;
	
	AbstractHost absHost;
	
	public GHost( double x, double y, AbstractHost host, PLayer layer ) {
		super( GNode.host, layer );
		
		this.links = new ArrayList<GLink>();
		this.absHost = host;
		
		String imagePath = "";
		switch (host.getType()) {
		case PC:
			imagePath = pcImage;
			break;
		case ROUTER:
			imagePath = routerImage;
			break;
		case FIREWALL:
			imagePath = firewallImage;
			break;
		case SERVER:
			imagePath = serverImage;
			break;
		case NATTEDSERVER:
			imagePath = nattedServerImage;
			break;
		}
		
		/* get the images needed */
		int index = imagePath.lastIndexOf(".");
		String extension = imagePath.substring( index );
		String imageName = imagePath.substring( 0, index );
		
		String selectedImagePath = imageName + "_selected" + extension;
		String mouseOverImagePath = imageName + "_mouseover" + extension;
		String connectImagePath = imageName + "_connecting" + extension;
		
		defaultImage = new PImage(imagePath);
		selectedImage = new PImage(selectedImagePath);
		mouseOverImage = new PImage(mouseOverImagePath);
		connectImage = new PImage(connectImagePath);
		
		setImage( defaultImage );
		
		setText(host.getLabel());
		
		centerFullBoundsOnPoint(x, y);
		
		createPopupMenu();
		
		update();
		
		Lab.getInstance().addNode(getLabNode());
	}
	
	public LabNode getLabNode() {
		if( labNode == null )
			labNode = new LabNode(getFullBoundsReference(), GNode.host, absHost);
		return labNode;
	}
	
	private void createPopupMenu() {
		menu = new JPopupMenu();
		
		JMenuItem rename = new JMenuItem("Rename", new ImageIcon("data/images/16x16/text_icon.png"));
		rename.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog("New name");
				while( GuiManager.getInstance().getProject().existsHost(name) ) {
					name = JOptionPane.showInputDialog("Another host with this name exists. New name");
				}
				if( name != null && !name.isEmpty() ) {
					absHost.setName(name);
					absHost.setLabel(name);
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
	
	private void setImage( PImage newImage ) {
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
		if( selected )
			setImage(selectedImage);
		else 
			setImage(defaultImage);
	}
	
	public void setMouseOver( boolean mouseOver ) {
		if( !selected ) {
			if( mouseOver ) 
				setImage(mouseOverImage);
			else 
				setImage(defaultImage);
		}
	}
	
	public void setConnecting( boolean connecting ) {
		if( connecting ) 
			setImage(connectImage);
		else 
			setImage(defaultImage);
	}

	public void update() {
		super.update();
		
		for( GLink gl : links ) {
			gl.update();
		}
	}
	
	public void addLink( GLink link ) {
		links.add(link);
	}
	
	public ArrayList<GLink> getLinks() {
		return links;
	}

	public ItemType getHostType() {
		return absHost.getType();
	}
	
	public AbstractHost getLogic() {
		return absHost;
	}

	public void delete() {
		while( !links.isEmpty() ) {
			links.get(0).delete();
		}
		
		super.delete();
		GuiManager.getInstance().getProject().removeHost( absHost );
		GuiManager.getInstance().update();
	}

	public void removeLink(GLink gLink) {
		links.remove(gLink);
	}
}
