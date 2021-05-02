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

package com.jnetedit.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import com.jnetedit.common.NameGenerator;
import com.jnetedit.core.project.AbstractProject;
import com.jnetedit.gui.gcomponents.ConfigurationPanel;
import com.jnetedit.gui.gcomponents.GCanvas;
import com.jnetedit.gui.gcomponents.GFrame;
import com.jnetedit.gui.input.HandlerManager;
import com.jnetedit.gui.nodes.GLink;

public class GuiManager {
	private static final long serialVersionUID = 7422535360161172027L;

	private static GuiManager gmanager;
	
	GFrame frame;
	ConfigurationPanel confPanel;
	JLabel emptyCanvas;
	GCanvas canvas;
	JSplitPane splitPane;
	AbstractProject project;
	HandlerManager handler;
	
	/** Singleton implementation */
	private GuiManager() {}
	
	public static GuiManager getInstance() {
		if( gmanager == null ) 
			gmanager = new GuiManager();
		
		return gmanager;
	}
	/*****************************/
	
	public void startGui() {
		frame = new GFrame();
		
		confPanel = new ConfigurationPanel();
		
		emptyCanvas = new JLabel("Create a new project or open an existing one.");
		emptyCanvas.setHorizontalAlignment(SwingConstants.CENTER);
		emptyCanvas.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, emptyCanvas, confPanel);
		splitPane.setBorder( BorderFactory.createLineBorder(Color.LIGHT_GRAY) );
	    splitPane.setResizeWeight(1D);
		frame.add(splitPane, BorderLayout.CENTER);
		
		frame.validate();
	}
	
	public void setProject( AbstractProject project ) {
		this.project = project;
		Lab.getInstance().setProject(project);
		confPanel.setProject(project);
		
		NameGenerator.reset();
		
		if( canvas != null ) {
			canvas.clearAll();
		}
		
		handler = new HandlerManager();
		canvas = new GCanvas();
		handler.start();
		canvas.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		canvas.setPreferredSize(new Dimension((int) (frame.getSize().getWidth()*0.8), (int) (frame.getSize().getHeight()*0.8)));
		
		splitPane.setLeftComponent(canvas);
		frame.setCanvas(canvas);
		confPanel.update();
		frame.validate();
	}
	
	public void update() {
		frame.validate();
		confPanel.validate();
		canvas.validate();
		
		if( canvas != null ) {
			for( Object o : canvas.getLinkLayer().getChildrenReference() ) {
				if( o instanceof GLink ) {
					((GLink) o).update();
				}
			}
			confPanel.update();
		}
	}

	public ConfigurationPanel getConfPanel() {
		return confPanel;
	}
	
	public GCanvas getCanvas() {
		return canvas;
	}
	
	public GFrame getFrame() {
		return frame;
	}

	public AbstractProject getProject() {
		return project;
	}
	
	public HandlerManager getHandler() {
		return handler;
	}
}
