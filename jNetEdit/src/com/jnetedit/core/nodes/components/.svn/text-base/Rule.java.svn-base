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



public class Rule implements AbstractRule, Serializable {
	private static final long serialVersionUID = 3778843695341065384L;

	String name;
	
	String inputIface;
	String outputIface;
	String source;
	String destination;
	int sourcePort;
	int destPort;
	String protocol;
	String target;
	
	AbstractChain chain;
	
	String rule;
	
	String userRule;
	
	public Rule( AbstractChain chain, String name ) {
		this.chain = chain;
		this.name = name;
		
		sourcePort = 0;
		destPort = 0;
		inputIface = "";
		outputIface = "";
		protocol = "";
		target = "";
		source = "";
		destination = "";
		
		userRule = null;
	}

	public String getInputIface() {
		return inputIface;
	}
	
	public AbstractChain getChain() {
		return chain;
	}

	public void setInputIface(String inputIface) {
		this.inputIface = inputIface;
	}

	public String getOutputIface() {
		return outputIface;
	}

	public void setOutputIface(String outputIface) {
		this.outputIface = outputIface;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
	}

	public int getDestPort() {
		return destPort;
	}

	public void setDestPort(int destPort) {
		this.destPort = destPort;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getRule() {
		if( userRule != null )
			return userRule + "\n";
		
		rule = "iptables -A " + chain.getName();
		if( !source.equals("") )
			rule += " -s " + source;
		if( !destination.equals("") )
			rule += " -d " + destination;
		if( !inputIface.equals("") ) 
			rule += " -i " + inputIface;
		if( !outputIface.equals("") )
			rule += " -o " + outputIface;
		if( !protocol.equals("") )
			rule += " -p " + protocol;
		if( sourcePort != 0 )
			rule += " --sport " + sourcePort;
		if( destPort != 0 )
			rule += " --dport " + destPort;
		if( !target.equals("") ) 
			rule += " -j " + target;
		return rule + "\n";
	}

	public void setRule(String rule) {
		this.userRule = rule;
	}

	@Override
	public void delete() {
		chain.deleteRule(this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}
