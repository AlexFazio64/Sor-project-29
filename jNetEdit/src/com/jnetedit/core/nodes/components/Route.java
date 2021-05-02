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

package com.jnetedit.core.nodes.components;

import java.io.Serializable;

public class Route implements AbstractRoute, Serializable {
	private static final long serialVersionUID = -7428572014762107691L;

	AbstractInterface iface;
	
	String net;
	String gw;
	String dev;
	
	public Route( Interface iface ) {
		this.iface = iface;
	}
	
	public AbstractInterface getInterface() {
		return iface;
	}

	public void setInterface (AbstractInterface iface) {
		this.iface = iface;
	}

	public String getNet() {
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	public String getGw() {
		return gw;
	}

	public void setGw(String gw) {
		this.gw = gw;
	}

	@Override
	public void delete() {
		iface.deleteRoute(this);
	}

	@Override
	public String getConfCommand() {
		String command = "";
		if( net != null && gw != null ) {
			if( net.equals("0.0.0.0/0") )
				command += "route add default gw " + gw + "\n";
			else
				command += "route add -net " + net + " gw " + gw
						+ (dev.isEmpty() ? "\n" : " dev " + dev + "\n");
		}
		return command;
	}

	@Override
	public String getDev() {
		return dev;
	}

	@Override
	public void setDev(String dev) {
		this.dev = dev;
	}
}
