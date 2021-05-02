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

package com.jnetedit.common;

import java.io.Serializable;




public class NameGenerator implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static int cdNumber = 0;
	private static int pcNumber = 0;
	private static int routerNumber = 0;
	private static int serverNumber = 0;
	private static int nattedServerNumber = 0;
	private static int firewallNumber = 0;
	private static int tapNumber = 0;
	
	public static String getNextName( ItemType type ) {
		String name = null;
		
		switch (type) {
		case SERVER:
			name = "S" + ++serverNumber;
			break;
		case FIREWALL:
			name = "F" + ++firewallNumber;
			break;
		case NATTEDSERVER:
			name = "SNatted" + ++nattedServerNumber;
			break;
		case PC:
			name = "PC" + ++pcNumber;
			break;
		case ROUTER:
			name = "R" + ++routerNumber;
			break;
		case TAP:
			if( tapNumber < 1 ) {
				name = "TAP";
				tapNumber++;
			}
			break;
		case COLLISIONDOMAIN:
			name = "CD" + ++cdNumber;
			break;
		}
		
		return name;
	}

	public static void reset() {
		cdNumber = 0;
		pcNumber = 0;
		routerNumber = 0;
		serverNumber = 0;
		nattedServerNumber = 0;
		firewallNumber = 0;
		tapNumber = 0;
	}
}
