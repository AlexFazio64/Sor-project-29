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

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import com.jnetedit.common.ItemType;
import com.jnetedit.gui.GuiManager;
import com.jnetedit.gui.ProjectHandler;
import com.jnetedit.gui.gcomponents.DropDownButton.GMenu;
import com.jnetedit.netkit.Shell;


public class GActionListener implements ActionListener {

	ActionType action;
	
	GuiManager manager;
	
	public GActionListener( ActionType action ) {
		super();
		this.action = action;
		
		manager = GuiManager.getInstance();
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		switch (action) {
		case newProject:
			ProjectHandler.getInstance().newProject();
			break;
		case openProject:
			ProjectHandler.getInstance().openProject();
			break;
		case saveProject:
			ProjectHandler.getInstance().saveProject();
			break;
		case parallelStartup:
			JMenuItem item = (JMenuItem) e.getSource();
			((GMenu) item.getParent()).setSelectedItem(item);
			Shell.startLab( GuiManager.getInstance().getProject(), true );
			break;
		case sequentialStartup:
			JMenuItem item1 = (JMenuItem) e.getSource();
			((GMenu) item1.getParent()).setSelectedItem(item1);
			Shell.startLab( GuiManager.getInstance().getProject(), false );
			break;
		case lcrashStop:
			JMenuItem item2 = (JMenuItem) e.getSource();
			((GMenu) item2.getParent()).setSelectedItem(item2);
			Shell.stopLab( GuiManager.getInstance().getProject(), true );
			break;
		case lhaltStop:
			JMenuItem item3 = (JMenuItem) e.getSource();
			((GMenu) item3.getParent()).setSelectedItem(item3);
			Shell.stopLab( GuiManager.getInstance().getProject(), false );
			break;
		case addPc:
			manager.getHandler().adding(ItemType.PC);
			break;
		case addRouter:
			manager.getHandler().adding(ItemType.ROUTER);
			break;
		case addFirewall:
			manager.getHandler().adding(ItemType.FIREWALL);
			break;
		case addArea:
			manager.getHandler().adding(ItemType.AREA);
			break;
		case addCollisionDomain:
			manager.getHandler().adding(ItemType.COLLISIONDOMAIN);
			break;
		case addTap:
			manager.getHandler().adding(ItemType.TAP);
			break;
		case addLink:
			manager.getHandler().adding(ItemType.LINK);
			break;
		case addNattedServer:
			manager.getHandler().adding(ItemType.NATTEDSERVER);
			break;
		case addServer: 
			manager.getHandler().adding(ItemType.SERVER);
			break;
		case delete:
			manager.getHandler().deleting();
			break;
		case clear:
			manager.getCanvas().clearAll();
			break;
		case showInfo:
			showInfo();
			break;
		case showLicence:
			showLicence();
			break;
		case exportImage:
			if( manager.getCanvas() != null ) 
				manager.getCanvas().export();
			break;
		case zoomIn:
			if( manager.getCanvas() != null ) 
				manager.getCanvas().zoomIn();
			break;
		case zoomOut:
			if( manager.getCanvas() != null ) 
				manager.getCanvas().zoomOut();
			break;
		case zoomOriginal:
			if( manager.getCanvas() != null ) 
				manager.getCanvas().zoomOriginal();
			break;
		case exit:
			manager.getFrame().closeApplication();
			break;
		}
	}
	
	public void showInfo() {
		String credits = "<html><center><b><font size=6>jNetEdit</font></b></center><br>" +
			"Released under GNU General Public License version 3. " +
			"<a href=http://www.gnu.org/licenses/gpl-3.0-standalone.html>GPLv3</a><br><br>" +
			"Copyright © 2010 <i>Loria Salvatore</i><br>" +
			"Visit <a href=http://slash17.googlecode.com>http://slash17.googlecode.com</a></html>";

		JEditorPane editorPane = new JEditorPane("text/html", credits);
		editorPane.setEditable(false);

		editorPane.addHyperlinkListener( new HyperlinkListener() {
			public void hyperlinkUpdate( HyperlinkEvent evt ) {
				if( evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED ) {
					try {
						Desktop.getDesktop().browse( new URI(evt.getDescription()) );
					} catch (Exception e) {
					}
				}
			}
		});

		JOptionPane.showMessageDialog( GuiManager.getInstance().getFrame(), editorPane, "About...", 
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon("data/images/big/GNU.png"));
	}
	
	public void showLicence() {
		File f = new File("licence.txt");
		String text = "";
		try {
			BufferedReader r = new BufferedReader(new FileReader(f));
			String s;
			while( (s = r.readLine() ) != null ) {
				text = text + s + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JDialog d = new JDialog(manager.getFrame());
		d.setTitle("Licence");
		d.setContentPane(new JPanel(new BorderLayout()));
		JLabel title = new JLabel("jNetEdit Licence", JLabel.CENTER);
		title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		title.setFont(new Font("Times New Roman", Font.BOLD, 24));
		d.add(title, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(new JTextArea(text));
		d.add(scrollPane);
		d.setSize(600, 600);
		d.setLocationRelativeTo(manager.getFrame());
		d.setVisible(true);
	}
	
	public enum ActionType {
		newProject,
		saveProject,
		openProject,
		addPc,
		addRouter,
		addFirewall,
		addServer,
		addNattedServer,
		addLink,
		addCollisionDomain,
		addArea,
		addTap,
		delete, 
		clear,
		showInfo,
		showLicence,
		exportImage,
		zoomIn,
		zoomOut,
		zoomOriginal,
		exit, 
		lhaltStop, 
		lcrashStop,
		sequentialStartup, 
		parallelStartup;
	}
}
