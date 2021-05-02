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

import java.awt.geom.Point2D;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;

/** Alternative Zoom event handler to the standard Piccolo PZoomEventHandler<br>
 * 	Use mouse wheel to zoom in/out 
 * 
 * @author sal
 */
public class MouseWheelZoomEventHandler extends PBasicInputEventHandler
{
	private Point2D viewZoomPoint;
	private double minScale = 0.4;
	private double maxScale = 2;
	
    @Override
    public void mouseMoved( PInputEvent event ) {
    	viewZoomPoint = event.getPosition();
    }
    
    public void mouseWheelRotated( PInputEvent event ) {
    	PCamera camera = event.getCamera();
    	double scaleDelta;
    	
    	if( event.getWheelRotation() == 1 ) {
    		scaleDelta = 0.8;
    	} else {
    		scaleDelta = 1.2;
    	}
		
		double currentScale = camera.getViewScale();
		double newScale = currentScale * scaleDelta;

		if ( newScale < minScale ) {
			scaleDelta = minScale / currentScale;
		}
		if ( (maxScale > 0) && (newScale > maxScale) ) {
			scaleDelta = maxScale / currentScale;
		}

		camera.scaleViewAboutPoint(scaleDelta, viewZoomPoint.getX(), viewZoomPoint.getY());
	}

	public double getMinScale() {
		return minScale;
	}

	public void setMinScale(double minScale) {
		this.minScale = minScale;
	}

	public double getMaxScale() {
		return maxScale;
	}

	public void setMaxScale(double maxScale) {
		this.maxScale = maxScale;
	}
}

