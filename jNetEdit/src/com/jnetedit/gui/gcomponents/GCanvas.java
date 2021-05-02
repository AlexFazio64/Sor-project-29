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

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.jnetedit.common.ItemType;
import com.jnetedit.common.NameGenerator;
import com.jnetedit.core.nodes.AbstractCollisionDomain;
import com.jnetedit.core.nodes.AbstractHost;
import com.jnetedit.core.nodes.AbstractLink;
import com.jnetedit.gui.GFactory;
import com.jnetedit.gui.GuiManager;
import com.jnetedit.gui.Lab;
import com.jnetedit.gui.input.HandlerManager;
import com.jnetedit.gui.input.MouseWheelZoomEventHandler;
import com.jnetedit.gui.nodes.GArea;
import com.jnetedit.gui.nodes.GCollisionDomain;
import com.jnetedit.gui.nodes.GHost;
import com.jnetedit.gui.nodes.GLink;
import com.jnetedit.gui.nodes.GNode;
import com.jnetedit.gui.nodes.LabNode;
import com.jnetedit.gui.util.ImgFileFilter;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PInputEventFilter;

public class GCanvas extends PCanvas {
	private static final long serialVersionUID = 1L;
	
	PLayer nodeLayer, linkLayer, areaLayer;
	
	GFrame frame;
	HandlerManager handler;
	ConfigurationPanel confPanel;

	MouseWheelZoomEventHandler zoomEventHandler;
	
	double originalScale;
	
	/** 
	 * Create the canvas
	 */
	public GCanvas() {
		frame = GuiManager.getInstance().getFrame();
		confPanel = GuiManager.getInstance().getConfPanel();
		handler = GuiManager.getInstance().getHandler();
		
		// init layers
		nodeLayer = getLayer();
		linkLayer = new PLayer();
		areaLayer = new PLayer();
		nodeLayer.addChild(linkLayer);
		linkLayer.addChild(areaLayer);
		
		// load the lab's topology if opening
		ArrayList<LabNode> nodes = Lab.getInstance().getNodes();
		int size = nodes.size();
		for( int i = 0; i < size; i++ ) {
			LabNode labNode = nodes.get(i);
			switch (labNode.getType()) {
			case GNode.host:
				new GHost(labNode.getX(), labNode.getY(), (AbstractHost) labNode.getAbsNode(), nodeLayer);
				break;
			case GNode.link:
				GHost host = searchHost(((AbstractLink) labNode.getAbsNode()).getInterface().getHost());
				GCollisionDomain domain = searchDomain(((AbstractLink) labNode.getAbsNode()).getInterface().getCollisionDomain());
				GLink newLink = new GLink(host, domain, (AbstractLink) labNode.getAbsNode(), linkLayer);
				host.addLink(newLink);
				domain.addLink(newLink);
				break;
			case GNode.area:
				GArea area = new GArea(labNode.getX(), labNode.getY(), areaLayer);
				area.setBounds(labNode.getBounds());
				area.setColor(labNode.getColor());
				area.setText(labNode.getText());
				break;
			case GNode.domain:
				new GCollisionDomain(labNode.getX(), labNode.getY(), (AbstractCollisionDomain) labNode.getAbsNode(), nodeLayer);
				break;
			}
		}
		
		/* replace the default zoom event handler with the mouse wheel zoom event handler */
		removeInputEventListener(getZoomEventHandler());
		zoomEventHandler = new MouseWheelZoomEventHandler();
		addInputEventListener(zoomEventHandler);

		/* use the right mouse button for the pan event handler, instead of the left button */
		getPanEventHandler().setEventFilter(new PInputEventFilter(InputEvent.BUTTON3_MASK));
		
		originalScale = getCamera().getViewScale();
	}
	
	@SuppressWarnings("rawtypes")
	private GHost searchHost(AbstractHost logic) {
		for( Iterator it = nodeLayer.getAllNodes().iterator(); it.hasNext(); ) {
			Object node = it.next();
			if( node instanceof GHost ) {
				GHost host = (GHost) node;
				if( host.getLogic().equals(logic) ) 
					return host;
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private GCollisionDomain searchDomain(AbstractCollisionDomain logic) {
		for( Iterator it = nodeLayer.getAllNodes().iterator(); it.hasNext(); ) {
			Object node = it.next();
			if( node instanceof GCollisionDomain ) {
				GCollisionDomain domain = (GCollisionDomain) node;
				if( domain.getLogic().equals(logic) ) 
					return domain;
			}
		}
		return null;
	}
	
	/** 
	 * Clear the canvas deleting everything
	 */
	public void clearAll() {
		for( Object node : nodeLayer.getAllNodes() )
			if( node instanceof GNode ) 
				((GNode) node).delete();
		for( Object node : linkLayer.getAllNodes() )
			if( node instanceof GNode ) 
				((GNode) node).delete();
		for( Object node : areaLayer.getAllNodes() )
			if( node instanceof GNode ) 
				((GNode) node).delete();

		GuiManager.getInstance().update();
		NameGenerator.reset();
	}
	
	public void zoomIn() {
		zoom( 1.2 );
	}
	
	public void zoomOut() {
		zoom( 0.8 );
	}
	
	public void zoomOriginal() {
		double delta = originalScale / getCamera().getViewScale();
		zoom(delta);
	}
	
	private void zoom( double delta ) {
		PCamera camera = getCamera();
		
		double currentScale = camera.getViewScale();
		double newScale = currentScale * delta;
		
		double minScale = zoomEventHandler.getMinScale();
		double maxScale = zoomEventHandler.getMaxScale();

		if ( newScale < minScale ) {
			delta = minScale / currentScale;
		}
		if ( (maxScale > 0) && (newScale > maxScale) ) {
			delta = maxScale / currentScale;
		}
		Point p = new Point( getBounds().width / 2, getBounds().height / 2 );
		camera.scaleViewAboutPoint(delta, p.x, p.y);
	}
	
	/**
	 * Export the canvas as an image
	 */
	public void export() {
		int width = getWidth();
		int height = getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		this.paint(g2);
		g2.dispose();
		
		String[] options = {"png", "gif", "jpg"};
		String extension = (String) JOptionPane.showInputDialog(frame, "Choose the image type", "Export as..", JOptionPane.QUESTION_MESSAGE, null, options, ".png");
		
		if( extension == null ) 
			return;
		
		JFileChooser saveImg = new JFileChooser();
		saveImg.setMultiSelectionEnabled(false);
		saveImg.setFileFilter(new ImgFileFilter(extension));
		saveImg.showSaveDialog(frame);
		
		File f = saveImg.getSelectedFile();
		if( f != null ) {
			while( f.exists() ) {
				int choose = JOptionPane.showConfirmDialog(frame, "Are you sure you want to overwrite this file?");
				if( choose == JOptionPane.NO_OPTION ) 
					saveImg.showSaveDialog(frame);
				else if( choose == JOptionPane.CANCEL_OPTION )
					return;
				else
					break;
			}
			try {
				if( f.getPath().endsWith("." + extension) ) 
					ImageIO.write(image, extension, f);
				else
					ImageIO.write(image, extension, new File(f.getPath() + "." + extension));
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void addNode( ItemType nodeType, Point2D pos ) {
		switch( nodeType ) {
		case COLLISIONDOMAIN:
			GCollisionDomain cd = GFactory.getInstance().createCollisionDomain( pos.getX(), pos.getY(), nodeLayer );
			GuiManager.getInstance().getProject().addCollisionDomain(cd.getLogic());
			break;
			
		case TAP:
			GCollisionDomain tap = GFactory.getInstance().createTap( pos.getX(), pos.getY(), nodeLayer );
			if( tap == null ) {
				JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), 
						"Can't add more than one tap", "", JOptionPane.ERROR_MESSAGE);
				return;
			}
			GuiManager.getInstance().getProject().addCollisionDomain(tap.getLogic());
			break;
			
		case AREA:
			GFactory.getInstance().createArea( pos.getX(), pos.getY(), areaLayer );
			handler.switchToDefaultHandler();
			break;
			
		default:
			GHost host = GFactory.getInstance().createGHost( nodeType, pos.getX(), pos.getY(), nodeLayer );
			GuiManager.getInstance().getProject().addHost(host.getLogic());
			break;
		}
	}
	
	public void addLink( GHost host, GCollisionDomain collisionDomain ) {
		GLink link = GFactory.getInstance().createLink( host, collisionDomain, linkLayer );
		
		if( link == null ) {
			JOptionPane.showMessageDialog(this, "Can't add another link");
		} else {
			host.addLink(link);
			collisionDomain.addLink(link);
		}
	}

	public PLayer getNodeLayer() {
		return nodeLayer;
	}

	public PLayer getAreaLayer() {
		return areaLayer;
	}

	public PLayer getLinkLayer() {
		return linkLayer;
	}
}
