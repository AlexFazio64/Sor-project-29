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

package com.jnetedit;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JOptionPane;

import com.jnetedit.core.Factory;
import com.jnetedit.gui.GFactory;
import com.jnetedit.gui.GuiManager;
import com.jnetedit.netkit.Shell;

public class Main {
	
	public static String version;
	
	public static void main(String[] args) {
	    try {
			version = new BufferedReader(new FileReader("version")).readLine();
		} catch (Exception e) {
			version = "0.1";
		}
		if( args.length == 0 || args[0] == null || args[0].isEmpty() || args[0].equals("NULL") ) {
			JOptionPane.showMessageDialog(null, 
					"jNetkit - version " + version + "\n" +
					"Copyright 2010 Salvatore Loria\n\n" +
					"WARNING: No Netkit installation founded, some features wont be available.\n" +
					"You can get a free copy of Netkit here: <http://wiki.netkit.org>\n", 
					"No Netkit installation", JOptionPane.WARNING_MESSAGE);
			Shell.setNetkitHome(null);
		} else {
			Shell.setNetkitHome(args[0]);
		}
		GFactory.init( new Factory() );
		GuiManager.getInstance().startGui();
	}
}