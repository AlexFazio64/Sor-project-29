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

package com.jnetedit.gui.gcomponents.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jnetedit.common.IpAddress;
import com.jnetedit.core.nodes.components.AbstractInterface;
import com.jnetedit.gui.GuiManager;


public class InterfaceDialog extends JDialog {
	private static final long serialVersionUID = -9193720585561385320L;

	AbstractInterface iface;
	
	public InterfaceDialog( final AbstractInterface iface ) {
		super( (Frame) GuiManager.getInstance().getFrame(), "Interface configuration" );
		this.iface = iface;
		
		createDialog( null );
	}	
	
	public InterfaceDialog( final AbstractInterface iface, String message ) {
		super( (Frame) GuiManager.getInstance().getFrame(), "Interface configuration" );
		this.iface = iface;
		
		createDialog( message );
	}

	private void createDialog( String message ) {
		setLayout(new BorderLayout());
		
		String name = iface.getName();
		String ip = iface.getIp();
		String net = iface.getNet();
		String mask = iface.getMask();
		String bcast = iface.getBCast();
		String host = iface.getHost().getName();
		
		if( ip == null || mask == null || bcast == null ) {
			ip = "";
			net = "";
			mask = "";
			bcast = "";
		}
		
		final JLabel label;
		if( message == null ) 
			label = new JLabel("Set the interface " + name + " of " + host );
		else 
			label = new JLabel(message + "\nSet the interface " + name + " of " + host );
		
		label.setFont( new Font("Serif", Font.BOLD, 14) );
		label.setBorder( BorderFactory.createEmptyBorder(5, 5, 5, 5) );
		add( label, BorderLayout.NORTH );
		
		JPanel panel = new JPanel( new GridBagLayout() );
		panel.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10) );
		
		GridBagConstraints labelConstraint = new GridBagConstraints();
		labelConstraint.anchor = GridBagConstraints.EAST;
		labelConstraint.ipadx = 10;
		labelConstraint.ipady = 5;
		
		GridBagConstraints textFieldConstraint = new GridBagConstraints();
		textFieldConstraint.gridwidth = 3;
		textFieldConstraint.anchor = GridBagConstraints.EAST;

		JLabel icon = new JLabel(new ImageIcon("data/images/big/nic.png"));
		labelConstraint.gridy = 0;
		labelConstraint.gridx = 0;
		labelConstraint.gridheight = 5;
		panel.add( icon, labelConstraint );
		labelConstraint.gridheight = 1;
		
		labelConstraint.gridy = 0;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("IP:"), labelConstraint );
		final JTextField ipField = new JTextField(ip, 15);
		textFieldConstraint.gridy = 0;
		textFieldConstraint.gridx = 2; //fino a 5
		panel.add( ipField, textFieldConstraint );
		
		labelConstraint.gridy = 1;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Network:"), labelConstraint );
		final JTextField netField = new JTextField(net, 15);
		textFieldConstraint.gridy = 1;
		textFieldConstraint.gridx = 2;
		panel.add( netField, textFieldConstraint );
		
		labelConstraint.gridy = 2;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("NetMask:"), labelConstraint );
		final JTextField maskField = new JTextField(mask, 15);
		textFieldConstraint.gridy = 2;
		textFieldConstraint.gridx = 2;
		panel.add( maskField, textFieldConstraint );
		
		labelConstraint.gridy = 3;
		labelConstraint.gridx = 1;
		panel.add(new JLabel("BroadCast:"), labelConstraint );
		final JTextField bcastField = new JTextField(bcast, 15);
		textFieldConstraint.gridy = 3;
		textFieldConstraint.gridx = 2;
		panel.add( bcastField, textFieldConstraint );
		
		Dimension size = new Dimension(60,30);
		
		labelConstraint.gridy = 4;
		labelConstraint.gridx = 4;
		
		JPanel buttonPanel = new JPanel();
		
		JButton set = new JButton("Set");
		set.setPreferredSize(size);
		buttonPanel.add( set );
		
		JButton cancel = new JButton("Cancel");
		cancel.setPreferredSize(size);
		buttonPanel.add( cancel );
		
		panel.add( buttonPanel, labelConstraint );
		
		add(panel, BorderLayout.CENTER);
		
		
		set.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = ipField.getText();
				String net = netField.getText();
				String mask = maskField.getText();
				String bcast = bcastField.getText();
				
				if( !ip.matches(IpAddress.ipRx) ) {
					label.setText("Not valid IP Address.");
					repaint();
				} else if ( !mask.matches(IpAddress.maskRx) ) {
					label.setText("Not valid subnet mask.");
					repaint();
				} else if ( !net.matches(IpAddress.ipRx) ) {
					label.setText("Not valid network.");
					repaint();
				} else if ( !bcast.matches(IpAddress.ipRx) ) {
					label.setText("Not valid broadcast IP Address.");
					repaint();
				} else {
					iface.setIp(ip);
					iface.setNet(net);
					iface.setMask(mask);
					iface.setBCast(bcast);
					GuiManager.getInstance().update();
					dispose();
				}
			}
		});
		
		cancel.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		pack();
		// center the dialog
		setLocationRelativeTo(GuiManager.getInstance().getFrame());
		setVisible(true);
	}
}
