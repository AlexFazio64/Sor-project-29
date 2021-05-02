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
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class GButton extends JButton {
	private static final long serialVersionUID = -635029680100083147L;
	
	public static final int standard = 0;
	public static final int toolbar = 1;
	
	private boolean selected = false;
	
	public GButton( String text, String toolTip, String icon ) {
		this( text, toolTip, icon, toolbar );
	}
	
	public GButton( String text, String toolTip, String icon, int type ) {
		super( text, new ImageIcon(icon) );
		setToolTipText( toolTip );
		
		setFont( new Font("", Font.PLAIN, 10));
		
		if( type == toolbar ) {
			setVerticalTextPosition(SwingConstants.BOTTOM);
			setHorizontalTextPosition(SwingConstants.CENTER);
			setPreferredSize(new Dimension(50, 50));
		} else {
			setVerticalTextPosition(SwingConstants.CENTER);
			
			setFont( new Font("", Font.PLAIN, 11));

			setIconTextGap(5);
		}
		
		setAlignmentX(JToolBar.CENTER_ALIGNMENT);
		setAlignmentY(JToolBar.CENTER_ALIGNMENT);
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean b) {
		selected = b;
	}
	
	public void toggleSelected() {
		selected = !selected;
	}
}
