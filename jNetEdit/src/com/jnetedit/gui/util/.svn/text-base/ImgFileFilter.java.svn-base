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

package com.jnetedit.gui.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ImgFileFilter extends FileFilter {

	String extension;
	
	public ImgFileFilter( String extension ) {
		this.extension = extension;
	}
	
	@Override
	public boolean accept(File f) {
		if( f.isDirectory() ) {
			return true;
		}
		String fileName = f.getPath();
		if( fileName.endsWith( extension ) )
			return true;
		else 
			return false;
	}

	@Override
	public String getDescription() {
		return extension;
	}
	
	public String getExtension() {
		return extension.substring(1);
	}
}
