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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.border.MatteBorder;

import com.jnetedit.common.ItemType;
import com.jnetedit.core.nodes.AbstractHost;
import com.jnetedit.core.nodes.components.AbstractChain;
import com.jnetedit.core.nodes.components.AbstractInterface;
import com.jnetedit.core.nodes.components.AbstractRoute;
import com.jnetedit.core.nodes.components.AbstractRule;
import com.jnetedit.core.project.AbstractProject;
import com.jnetedit.gui.gcomponents.gtree.GTree;
import com.jnetedit.gui.gcomponents.gtree.GTreeNode;


public class ConfigurationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	GTree interfacesTree;
	GTree routingTree;
	GTree firewallingTree;
	
	AbstractProject project;
	
	JTabbedPane tab;
	
	GTree fileSystemView;
	
	public ConfigurationPanel() {
		super(new GridLayout(1,0));
		
		setBorder( new MatteBorder(0, 1, 0, 0, Color.lightGray) );
		
		tab = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		
		String projName = "";
		if( project == null ) 
			projName = "noProject";
		else
			projName = project.getName();
		
		interfacesTree = new GTree("Hosts interfaces", projName);
		routingTree = new GTree("Hosts routing tables", projName);
		firewallingTree = new GTree("Firewalls", projName);
	
		tab.addTab("Interfaces", interfacesTree);
		tab.addTab("Routing", routingTree);
		tab.addTab("Firewalling", firewallingTree);
		
		fileSystemView = new GTree("Filesystem View", projName);
		fileSystemView.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		
		JSplitPane splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, tab, fileSystemView);
		splitpane.setResizeWeight(0.5D);
		add(splitpane);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(250, 400);
	}
	
	public void update() {
		if( project == null ) 
			return;
		
		interfacesTree.clear();
		routingTree.clear();
		firewallingTree.clear();
		fileSystemView.clear();
		
		File projDir = new File(project.getDirectory());
		addFiles (projDir, null);
		
		for( AbstractHost host : project.getHosts() ) {
			// add a folder for each host in the interfaces tree
			GTreeNode node = interfacesTree.addNode( host, GTreeNode.FOLDER );
			// add his interfaces to each host
			for( AbstractInterface iface : host.getInterfaces() ) {
				interfacesTree.addNode( node, iface, GTreeNode.IFACE );
			}
			
			// add a folder for each host in the routing tree
			node = routingTree.addNode( host, GTreeNode.ROUTER );
			// add the routes
			for( AbstractInterface iface : host.getInterfaces() ) {
				for( AbstractRoute route : iface.getRoutes() ) {
					routingTree.addNode( node, route, GTreeNode.ROUTE );
				}
			}
			
			// add a folder for each firewall in the firewalling tree
			if( host.getType() == ItemType.FIREWALL ) {
				node = firewallingTree.addNode( host, GTreeNode.FIREWALL );
				// add his fw rules to each firewall
				for( AbstractChain chain : host.getChains() ) {
					GTreeNode child = firewallingTree.addNode( node, chain, GTreeNode.CHAIN );
					for( AbstractRule rule : chain.getRules() ) {
						firewallingTree.addNode( child, rule, GTreeNode.RULE );
					}
				}
			}
		}
		
		this.repaint();
	}
	
	private void addFiles (File f, GTreeNode t) {
		if (!f.isDirectory()) 
			return;
		
		File files[] = f.listFiles();
		for (File file : files) {
			int type = file.isDirectory() ? GTreeNode.FOLDER : GTreeNode.FILE;
			GTreeNode tnode = fileSystemView.addNode( t, file.getPath(), type );
			addFiles (file, tnode);
		}
	}

	public void setProject( AbstractProject project ) {
		this.project = project;
		interfacesTree.setName(project.getName());
		routingTree.setName(project.getName());
		firewallingTree.setName(project.getName());
		fileSystemView.setName(project.getName());
	}
	
	public void selectHost( String hostName ) {
		interfacesTree.expandNode(hostName);
		routingTree.expandNode(hostName);
		firewallingTree.expandNode(hostName);
	}
}

