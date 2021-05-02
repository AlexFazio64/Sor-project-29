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
import java.util.ArrayList;

import com.jnetedit.core.nodes.AbstractHost;


public class Chain implements AbstractChain, Serializable {
	private static final long serialVersionUID = -1472226693901733359L;

	AbstractHost host;
	
	String name;
	
	String policy;
	
	ArrayList<AbstractRule> rules;
	
	public Chain( AbstractHost host ) {
		this.host = host;
		name = "";
		policy = "ACCEPT";
		rules = new ArrayList<AbstractRule>();
	}
	
	public Chain( AbstractHost host, String name ) {
		this.host = host;
		this.name = name;
		policy = "ACCEPT";
		rules = new ArrayList<AbstractRule>();
	}
	
	public AbstractHost getHost() {
		return host;
	}
	
	@Override
	public AbstractRule addRule() {
		AbstractRule rule = new Rule(this, "rule" + rules.size());
		rules.add(rule);
		return rule;
	}

	@Override
	public void deleteRule(AbstractRule rule) {
		rules.remove(rule);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public AbstractRule getRule( String rule ) {
		for( AbstractRule r : rules ) {
			if( r.getName().equals( rule ) )
				return r;
		}
		return null;
	}

	@Override
	public ArrayList<AbstractRule> getRules() {
		return rules;
	}

	@Override
	public void setName( String name ) {
		this.name = name;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy( String policy ) {
		this.policy = policy;
	}

	@Override
	public void delete() {
		host.deleteChain(this);
	}
	
	public String getConfCommand() {
		String command = "";
		if( !name.matches("(INPUT|OUTPUT|FORWARD)") )
			command += "iptables -N " + name + "\n";
		command += "iptables -P " + name + " " + policy + "\n";
		return command;
	}

	@Override
	public void ruleDown(AbstractRule rule) {
		int index = rules.indexOf(rule);
		AbstractRule nextRule = rules.get(index + 1);
		rules.remove(index);
		rules.remove(index);
		rules.add(index, rule);
		rules.add(index, nextRule);
	}

	@Override
	public void ruleUp(AbstractRule rule) {
		int index = rules.indexOf(rule) - 1;
		AbstractRule previousRule = rules.get(index);
		rules.remove(index);
		rules.remove(index);
		rules.add(index, previousRule);
		rules.add(index, rule);
	}

}
