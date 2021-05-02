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

package com.jnetedit.generator;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.jnetedit.core.nodes.AbstractCollisionDomain;
import com.jnetedit.core.nodes.AbstractHost;

public class VeryBasicLayouting implements AbstractLayout {

	boolean[][] matrix;
	
	public VeryBasicLayouting() {
		super();
		initMatrix();
	}
	
	@Override
	public Rectangle2D getBounds(AbstractHost node) {
		for( int i = 0; i < 8; i++ ) {
			for( int j = 0; j < 8; j++ ) {
				if( matrix[i][j] == false ) {
					matrix[i][j] = true;
					return new Rectangle(j*120, i*180, 64, 64);
				}
			}
		}
		return new Rectangle(0, 0, 64, 64);
	}
	
	@Override
	public Rectangle2D getBounds(AbstractCollisionDomain domain) {
		for( int i = 0; i < 8; i++ ) {
			for( int j = 0; j < 8; j++ ) {
				if( matrix[i][j] == false ) {
					matrix[i][j] = true;
					return new Rectangle(j*120, i*180, 64, 64);
				}
			}
		}
		return new Rectangle(0, 0, 64, 64);
	}

	private void initMatrix() {
		matrix = new boolean[8][8];
		for( int i = 0; i < 8; i++ ) {
			for( int j = 0; j < 8; j++ ) {
				matrix[i][j] = false;
			}
		}
	}
}
