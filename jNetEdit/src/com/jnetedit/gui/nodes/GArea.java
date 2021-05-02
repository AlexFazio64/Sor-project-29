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
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import com.jnetedit.gui.GuiManager;
import com.jnetedit.gui.Lab;
import com.jnetedit.gui.ProjectHandler;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolox.handles.PBoundsHandle;

public class GArea extends GNode {
	private static final long serialVersionUID = 3492362844970509196L;
	
	PPath shape;
	
	public GArea( double x, double y, PLayer layer ) {
		super(GNode.area, layer);
		
		setShape(x, y);
		
		createPopupMenu();
		
		Lab.getInstance().addNode(getLabNode());
	}
	
	public LabNode getLabNode() {
		if( labNode == null )
			labNode = new LabNode(shape.getFullBoundsReference(), GNode.area, null);
		return labNode;
	}

	private void createPopupMenu() {
		menu = new JPopupMenu();
		
		JMenuItem setText = new JMenuItem("Set Name", new ImageIcon("data/images/16x16/text_icon.png"));
		setText.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setText();
			}
		});
		menu.add(setText);
		
		JMenuItem selectcolor = new JMenuItem("Select Color", new ImageIcon("data/images/16x16/color_icon.png"));
		selectcolor.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(GuiManager.getInstance().getFrame(), "Area Color", Color.cyan);
				if( color != null )
					setColor( color );
			}
		});
		menu.add(selectcolor);
		
		JMenuItem delete = new JMenuItem("Delete", new ImageIcon("data/images/16x16/delete_icon.png"));
		delete.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		menu.add(delete);
	}

	private void setShape( double x, double y ) {
		if( shape != null ) {
			removeChild(shape);
		}
		shape = new PPath(new Rectangle((int)x, (int)y, 100, 100));
		addChild(shape);
//		shape.centerFullBoundsOnPoint(x, y/*getBounds().getCenterX(), getBounds().getCenterY()*/);
		shape.setPickable(false);
		getLabNode();
		setBounds(shape.getBounds());
		setColor(Color.cyan);
	}

	public void setText() {
		String name = JOptionPane.showInputDialog("Insert the name:");
		if( name != null && !name.equals("") ) {
			if( text == null )
				text = new PText();
			text.setText(name);
			text.setPickable(false);
			text.setFont(new Font("SansSerif", Font.BOLD, 16));
			addChild(text);
			labNode.setText(name);
			update();
		}
		ProjectHandler.getInstance().setSaved(false);
	}
	
	public void setText(String name) {
		if( name != null && !name.equals("") ) {
			if( text == null )
				text = new PText();
			text.setText(name);
			text.setPickable(false);
			text.setFont(new Font("SansSerif", Font.BOLD, 16));
			addChild(text);
			labNode.setText(name);
			update();
		}
		ProjectHandler.getInstance().setSaved(false);
	}
	
	@Override
	public void update() {
		if( text != null )
			text.centerFullBoundsOnPoint( getX() + getWidth() - text.getWidth(), getY() + text.getHeight() );
		labNode.setBounds(getFullBoundsReference());
		ProjectHandler.getInstance().setSaved(false);
	}

	@Override
	public boolean setBounds(Rectangle2D newBounds) {
		boolean result = super.setBounds(newBounds);
		shape.setBounds(newBounds);
		update();
		ProjectHandler.getInstance().setSaved(false);
		return result;
	}
	
	@Override
	public void setSelected( boolean selected ) {
		super.setSelected(selected);
		if( selected )
			PBoundsHandle.addBoundsHandlesTo(this);
		else 
			PBoundsHandle.removeBoundsHandlesFrom(this);
	}
	
	@Override
	public void setMouseOver( boolean mouseOver ) {
		if( !selected ) {
			if( mouseOver )
				setTransparency(0.8f);
			else 
				setTransparency(1);
		}
	}

	@Override
	public void showMenu( PInputEvent e ) {
		menu.show((Component) e.getComponent(), (int) e.getCanvasPosition().getX(), (int) e.getCanvasPosition().getY());
	}
	
	public void setColor(Color newColor) {
		labNode.setColor(newColor);
		shape.setPaint(newColor);
		ProjectHandler.getInstance().setSaved(false);
	}
}
