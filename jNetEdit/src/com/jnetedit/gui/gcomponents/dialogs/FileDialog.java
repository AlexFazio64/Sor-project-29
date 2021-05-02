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

package com.jnetedit.gui.gcomponents.dialogs;

import java.awt.Color;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jnetedit.gui.GuiManager;


public class FileDialog extends JDialog {
	private static final long serialVersionUID = 2855449568360456412L;

	String filePath;
	String text;
	
	public FileDialog( String filePath ) {
		super( (Frame) GuiManager.getInstance().getFrame(), filePath );
		
		this.filePath = filePath;
		this.text = "";
	}
	
	public void showDialog () {
		try {
			BufferedReader b = new BufferedReader(new FileReader(filePath));
			String line;
			while ((line = b.readLine()) != null) {
				text += line + "\n";
			}
		} catch (Exception e) {}
		
		JTextArea pane = new JTextArea(text);
		pane.setEditable(false);
		pane.setBackground(Color.white);

		JScrollPane scrollPane = new JScrollPane(pane);
		add(scrollPane);
		
		setSize(550, 700);
		
		setLocationRelativeTo(GuiManager.getInstance().getFrame());
		setVisible(true);
	}
}
