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

package com.jnetedit.gui.gcomponents;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class DropDownButton extends GButton {
	private static final long serialVersionUID = 8106596726298999650L;
	
	JButton dropDownButton;
	GMenu menu;

	public DropDownButton(String text, String toolTip, String icon) { 
		super(text + " | v", toolTip, icon, GButton.standard); 
		
		Dimension size = new Dimension(80, 30);
		setPreferredSize(size);
		setSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		menu = new GMenu();

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				if( p.x > 60 )
					menu.show(e.getComponent(), 0, (int) e.getComponent().getBounds().getHeight());
				else if( menu.getSelectedItem() != null )
					menu.getSelectedItem().doClick(0);
			}
		});
	}

	public GMenu getMenu() { 
		return menu; 
	}

	public void setSelectedItem(JMenuItem item) {
		menu.setSelectedItem(item);
		this.setToolTipText(item.getText());
	}
	
	public class GMenu extends JPopupMenu {
		private static final long serialVersionUID = 7322259617999175980L;
		JMenuItem selectedItem;
		
		public GMenu() {
			super();
		}
		
		public void setSelectedItem(JMenuItem it) {
			selectedItem = it;
		}
		
		public JMenuItem getSelectedItem() {
			return selectedItem;
		}
	}
}
