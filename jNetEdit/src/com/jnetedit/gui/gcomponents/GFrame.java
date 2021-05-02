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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import com.jnetedit.generator.TopologyGenerator;
import com.jnetedit.gui.ProjectHandler;
import com.jnetedit.gui.input.GActionListener;
import com.jnetedit.gui.input.GActionListener.ActionType;


public class GFrame extends JFrame {
	private static final long serialVersionUID = 3661490807594270819L;

	/** menu bar */
	JMenuBar menuBar;
	
	/** file menu */
	JMenu fileMenu;
	/** file menu item */
	GMenuItem newItem, openItem, saveItem, saveAsItem, exitItem;
	
	/** lab menu */
	JMenu labMenu;
	/** lab menu item */
	JMenuItem 	randomTopology, addRouterItem, addFirewallItem,
				addLinkItem, addPcItem, addServerItem, addNattedServerItem, 
				addCollisionDomainItem, addAreaItem, addTapItem;
	JMenu startLabMenu, stopLabMenu;
	JMenuItem startsItem, startpItem, haltItem, crashItem;
		
	/** view menu */
	JMenu viewMenu;
	/** view menu items */
	GMenuItem zoomInItem, zoomOutItem, zoomOriginalItem;
	
	/** help menu */
	JMenu helpMenu;
	/** help menu item */
	GMenuItem aboutItem, licenceItem;
	
	/** horizontal tool bar */
	JToolBar horizontalToolbar;
	/** horizontal tool bar's button */
	JButton newFile, open, save, saveAs, zoomIn, zoomOut, zoomOriginal;

	DropDownButton start;
	JMenuItem startp, starts;
	
	DropDownButton stop;
	JMenuItem lhalt, lcrash;
	
	/** vertical tool bar */
	JToolBar verticalToolbar;
	/** vertical tool bar button */
	JButton router, firewall, pc, collisionDomain, server, 
			nattedServer, area, tap, link, delete, clear;
	
	/** state bar's components */
	JLabel stateLabel;
	JPanel statePanel;

	/** the canvas */
	GCanvas canvas;

	/**
	 * Create the NetKit GUI window
	 */
	public GFrame() {
		super("jNetEdit");
		
		createFrame();
	}
	
	/** Create and show the frame 
	 */
	private void createFrame() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		/* Operating system check */
		String os = System.getProperty("os.name");
		if( os.equals("Linux") ) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			} catch (Exception e) { 
				System.err.println("Cannot set the GTK Look and Feel. Setting the default"); 
			}
		} else {
			String message = "WARNING: some features are not available on your system.\n" +
							"Run jNetEdit on a GNU/Linux system with a netkit installation.";
			String title = "WARNING: " + os + " is not fully supported";
			
			JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
			
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
			} catch (Exception e) { 
				System.err.println("Cannot set the system Look and Feel. Setting the Java L&F"); 
			}
		} 
		
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		setSize( screenSize );
		setSize( 1280, 800 );
		
		createMenuBar();
		createToolBar();
		createVericalToolBar();
		createStateBar();
		setupListeners();
		
		setVisible(true);
	}

	/** Create the menu bar and add it to the frame
	 */
	private void createMenuBar() {
		menuBar = new JMenuBar();
		
		// creating the file menu
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		newItem = new GMenuItem("New Project", new ImageIcon("data/images/16x16/new_icon.png"));
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		openItem = new GMenuItem("Open", new ImageIcon("data/images/16x16/open_icon.png"));
		saveItem = new GMenuItem("Save", new ImageIcon("data/images/16x16/save_icon.png"));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		saveAsItem = new GMenuItem("Save As...", new ImageIcon("data/images/16x16/saveAs_icon.png"));
		exitItem = new GMenuItem("Exit", new ImageIcon("data/images/16x16/exit_icon.png"));
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		// creating the project menu
		labMenu = new JMenu("Laboratory");
		labMenu.setMnemonic(KeyEvent.VK_L);
		
		startLabMenu = new JMenu("Start Lab");
		startLabMenu.setIcon(new ImageIcon("data/images/16x16/start_icon.png"));
		startsItem = new GMenuItem("Sequential Startup", new ImageIcon("data/images/16x16/start_icon.png"));
		startpItem = new GMenuItem("Parallel Startup", new ImageIcon("data/images/16x16/start_icon.png"));
		startLabMenu.add(startsItem);
		startLabMenu.add(startpItem);
		
		stopLabMenu = new JMenu("Stop Lab");	
		stopLabMenu.setIcon(new ImageIcon("data/images/16x16/stop_icon.png"));
		haltItem = new GMenuItem("Halt Lab", new ImageIcon("data/images/16x16/stop_icon.png"));
		crashItem = new GMenuItem("Crash Lab", new ImageIcon("data/images/16x16/stop_icon.png"));
		stopLabMenu.add(haltItem);
		stopLabMenu.add(crashItem);
		
		randomTopology = new GMenuItem("Generate topology", new ImageIcon("data/images/16x16/area_icon.png"));
		
		addRouterItem = new GMenuItem("Add router", new ImageIcon("data/images/16x16/router_icon.png"));
		addCollisionDomainItem = new GMenuItem("Add collision domain", new ImageIcon("data/images/16x16/collisionDomain_icon.png"));
		addLinkItem = new GMenuItem("Add link", new ImageIcon("data/images/16x16/link_icon.png"));
		addPcItem = new GMenuItem("Add pc", new ImageIcon("data/images/16x16/pc_icon.png"));
		addServerItem = new GMenuItem("Add server", new ImageIcon("data/images/16x16/server_icon.png"));
		addNattedServerItem = new GMenuItem("Add natted server", new ImageIcon("data/images/16x16/nattedserver_icon.png"));
		addFirewallItem = new GMenuItem("Add firewall", new ImageIcon("data/images/16x16/firewall_icon.png"));
		addAreaItem = new GMenuItem("Add area", new ImageIcon("data/images/16x16/area_icon.png"));
		addTapItem = new GMenuItem("Add tap", new ImageIcon("data/images/16x16/tap_icon.png"));
		labMenu.add(startLabMenu);
		labMenu.add(stopLabMenu);
		labMenu.addSeparator();
		labMenu.add(randomTopology);
		labMenu.addSeparator();
		labMenu.add(addRouterItem);
		labMenu.add(addCollisionDomainItem);
		labMenu.add(addPcItem);
		labMenu.add(addServerItem);
		labMenu.add(addNattedServerItem);
		labMenu.add(addFirewallItem);
		labMenu.add(addAreaItem);
		labMenu.add(addTapItem);
		labMenu.addSeparator();
		labMenu.add(addLinkItem);
		menuBar.add(labMenu);
		// creating view menu
		viewMenu = new JMenu("View");
		viewMenu.setMnemonic(KeyEvent.VK_V);
		zoomInItem = new GMenuItem("Zoom In", new ImageIcon("data/images/16x16/zoom_in.png"));
		zoomOutItem = new GMenuItem("Zoom out", new ImageIcon("data/images/16x16/zoom_out.png"));
		zoomOriginalItem = new GMenuItem("Zoom original", new ImageIcon("data/images/16x16/zoom_original.png"));
		viewMenu.add(zoomInItem);
		viewMenu.add(zoomOutItem);
		viewMenu.add(zoomOriginalItem);
		menuBar.add(viewMenu);
		// creating help menu
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		aboutItem = new GMenuItem("About", new ImageIcon("data/images/16x16/about_icon.png"));
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		licenceItem = new GMenuItem("Licence", new ImageIcon("data/images/16x16/license_icon.png"));
		helpMenu.add(aboutItem);
		helpMenu.add(licenceItem);
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
	}
	
	/** Create the standard toolbar and add it to the north side of the frame
	 */
	private void createToolBar() {
		horizontalToolbar = new JToolBar();
		horizontalToolbar.setFloatable(false);
		horizontalToolbar.setPreferredSize(new Dimension(40, 40));
		horizontalToolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
		
		newFile = new GButton("New", "Create a new Project", "data/images/16x16/new_icon.png", GButton.standard);
		open = new GButton("Open", "Open a Project", "data/images/16x16/open_icon.png", GButton.standard);
		save = new GButton("Save", "Save the Project", "data/images/16x16/save_icon.png", GButton.standard);
		saveAs = new GButton("Save as...", "Save as..", "data/images/16x16/saveAs_icon.png", GButton.standard);
		
		start = new DropDownButton("Start", "Start the lab", "data/images/16x16/start_icon.png");
		starts = new JMenuItem("Sequetial startup", new ImageIcon("data/images/16x16/start_icon.png"));
		startp = new JMenuItem("Parallel startup", new ImageIcon("data/images/16x16/start_icon.png"));
		start.getMenu().add(starts);
		start.getMenu().add(startp);
		start.setSelectedItem(starts);
		
		stop = new DropDownButton("Stop", "Stop the lab", "data/images/16x16/stop_icon.png");
		lhalt = new JMenuItem("Halt the lab ", new ImageIcon("data/images/16x16/stop_icon.png"));
		lcrash = new JMenuItem("Crash the lab ", new ImageIcon("data/images/16x16/stop_icon.png"));
		stop.getMenu().add(lhalt);
		stop.getMenu().add(lcrash);
		stop.setSelectedItem(lhalt);
		
		zoomIn = new GButton("", "Zoom in", "data/images/22x22/zoom_in.png", GButton.standard);
		zoomOut = new GButton("", "Zoom out", "data/images/22x22/zoom_out.png", GButton.standard);
		zoomOriginal = new GButton("", "Default zoom", "data/images/22x22/zoom_original.png", GButton.standard);
		
		horizontalToolbar.add( newFile );
		horizontalToolbar.add( open );
		horizontalToolbar.add( save );
		horizontalToolbar.add( saveAs );
		horizontalToolbar.addSeparator();
		horizontalToolbar.add( start );
		horizontalToolbar.add( stop );
		horizontalToolbar.addSeparator();
		horizontalToolbar.add( zoomIn );
		horizontalToolbar.add( zoomOriginal );
		horizontalToolbar.add( zoomOut );
		 
		add( horizontalToolbar, BorderLayout.NORTH );
	}
	
	/** Create the vertical toolbar and add it to the west side of the frame
	 */
	private void createVericalToolBar() {
		verticalToolbar = new JToolBar();
		verticalToolbar.setOrientation(JToolBar.VERTICAL);
		verticalToolbar.setFloatable(false);
		verticalToolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.lightGray));
		
		router = new GButton("Router", "Add a router", "data/images/22x22/router_icon.png");
		server = new GButton("Server", "Add a server", "data/images/22x22/server_icon.png");
		firewall = new GButton("Firewall", "Add a firewall", "data/images/22x22/firewall_icon.png");
		nattedServer = new GButton("NatServer", "Add a natted server", "data/images/22x22/nattedserver_icon.png");
		pc = new GButton("PC", "Add a pc", "data/images/22x22/pc_icon.png");
		collisionDomain = new GButton("Domain", "Add a collision domain", "data/images/22x22/collisionDomain_icon.png");
		area = new GButton("Area", "Create a Network Area", "data/images/22x22/area_icon.png");
		tap = new GButton("Tap", "Add TAP interface", "data/images/22x22/tap_icon.png");
		link = new GButton("Link", "Add a link", "data/images/22x22/link_icon.png");
		delete = new GButton("Delete", "Delete a node", "data/images/22x22/delete_icon.png");
		clear = new GButton("Clear All", "Clear all", "data/images/22x22/clear_icon.png");
		
		verticalToolbar.add( pc );
		verticalToolbar.add( server );
		verticalToolbar.add( nattedServer );
		verticalToolbar.add( router );
		verticalToolbar.add( firewall );
		verticalToolbar.add( collisionDomain );
		verticalToolbar.add( area );
		verticalToolbar.add( tap );
		verticalToolbar.add( link );
		verticalToolbar.addSeparator();
		verticalToolbar.add( delete );
		verticalToolbar.add( clear );
		
		add( verticalToolbar, BorderLayout.WEST );
	}

	/** Create the stateBar and add it to the south side of the frame
	 */
	private void createStateBar() {
		stateLabel = new JLabel("Status: editing", JLabel.TRAILING);
		statePanel = new JPanel(new GridLayout(1, 1));
		
		statePanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.lightGray));
		statePanel.add(stateLabel);
		
		add( statePanel, BorderLayout.SOUTH );
	}
	
	public void setCanvas( GCanvas canvas ) {
		this.canvas = canvas;
	}
	
	private void setupListeners() {
		/** set the window's closing event 
		 */
		addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent e ) {
				closeApplication();
			}
		});
		
		/** Set the window's minimum size
		 */
		addComponentListener( new ComponentAdapter() {
			public void componentResized( ComponentEvent e ) {
				JFrame tmp = (JFrame) e.getSource();
				if ( tmp.getWidth() < 600 || tmp.getHeight() < 600 ) {
					tmp.setSize( 700, 630 );
				}
			}
		});
		
		/********************************
		 * Menu's items listeners
		 ********************************/
		
		newItem.addActionListener( new GActionListener(ActionType.newProject) );
		openItem.addActionListener( new GActionListener(ActionType.openProject) );
		saveItem.addActionListener( new GActionListener(ActionType.saveProject) );
		exitItem.addActionListener( new GActionListener(ActionType.exit) );
		
		aboutItem.addActionListener( new GActionListener(ActionType.showInfo) );
		licenceItem.addActionListener( new GActionListener(ActionType.showLicence) );
		
		zoomInItem.addActionListener( new GActionListener(ActionType.zoomIn) );
		zoomOutItem.addActionListener( new GActionListener(ActionType.zoomOut) );
		zoomOriginalItem.addActionListener( new GActionListener(ActionType.zoomOriginal) );
		
		startsItem.addActionListener( new GActionListener(ActionType.sequentialStartup) );
		startpItem.addActionListener( new GActionListener(ActionType.parallelStartup) );
		haltItem.addActionListener( new GActionListener(ActionType.lhaltStop) );
		crashItem.addActionListener( new GActionListener(ActionType.lcrashStop) );
		
		randomTopology.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TopologyGenerator.start();
			}
		});
		
		addPcItem.addActionListener( new GActionListener(ActionType.addPc) );
		addRouterItem.addActionListener( new GActionListener(ActionType.addRouter) );
		addFirewallItem.addActionListener( new GActionListener(ActionType.addFirewall) );
		addServerItem.addActionListener( new GActionListener(ActionType.addServer) );
		addNattedServerItem.addActionListener( new GActionListener(ActionType.addNattedServer) );
		addAreaItem.addActionListener( new GActionListener(ActionType.addArea) );
		addCollisionDomainItem.addActionListener( new GActionListener(ActionType.addCollisionDomain) );
		addLinkItem.addActionListener( new GActionListener(ActionType.addLink) );
		addTapItem.addActionListener( new GActionListener(ActionType.addTap) );
		
		/********************************
		 * Buttons listeners
		 ********************************/
		
		newFile.addActionListener( new GActionListener(ActionType.newProject) );
		open.addActionListener( new GActionListener(ActionType.openProject) );
		save.addActionListener( new GActionListener(ActionType.saveProject) );
		saveAs.addActionListener( new  GActionListener(ActionType.exportImage) );
		
		router.addActionListener( new GActionListener(ActionType.addRouter) );
		firewall.addActionListener( new GActionListener(ActionType.addFirewall) );
		server.addActionListener( new GActionListener(ActionType.addServer) );
		nattedServer.addActionListener( new GActionListener(ActionType.addNattedServer) );
		pc.addActionListener( new GActionListener(ActionType.addPc) );
		collisionDomain.addActionListener( new GActionListener(ActionType.addCollisionDomain) );
		link.addActionListener( new GActionListener(ActionType.addLink) );
		tap.addActionListener( new GActionListener(ActionType.addTap) );
		area.addActionListener( new GActionListener(ActionType.addArea) );
		delete.addActionListener( new GActionListener(ActionType.delete) );
		clear.addActionListener( new GActionListener(ActionType.clear) );
		
		starts.addActionListener( new GActionListener(ActionType.sequentialStartup) );
		startp.addActionListener( new GActionListener(ActionType.parallelStartup) );
		lcrash.addActionListener( new GActionListener(ActionType.lcrashStop) );
		lhalt.addActionListener( new GActionListener(ActionType.lhaltStop) );
		
		zoomIn.addActionListener( new GActionListener(ActionType.zoomIn) );
		zoomOut.addActionListener( new GActionListener(ActionType.zoomOut) );
		zoomOriginal.addActionListener( new GActionListener(ActionType.zoomOriginal) );
	}
	
	public void closeApplication() {
		
		if( ProjectHandler.getInstance().isSaved() == false ) {
			int close = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit without saving the project?", 
					"Project not saved", JOptionPane.YES_NO_OPTION);
			if( close == JOptionPane.YES_OPTION ) {
				this.dispose();
			}
		} else {
			this.dispose();
		}
	}
}
