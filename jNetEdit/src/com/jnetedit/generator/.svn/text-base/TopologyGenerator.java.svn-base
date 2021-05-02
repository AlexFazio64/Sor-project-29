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

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jnetedit.core.Factory;
import com.jnetedit.gui.GuiManager;

public class TopologyGenerator {

	String number = "";
	String areasString = "";
	String maxRouterString = "4";
	
	public static void start() {
		new TopologyGenerator();
	}
	
	public TopologyGenerator() {
		createDialog();
		
		final LinkedList<String> areas = new LinkedList<String>();
		String[] areasStrings = areasString.split(" ");
		for( String area : areasStrings ) {
			areas.add(area);
		}
		
		if( number.equals("") || areasStrings.equals("") || maxRouterString.equals("") ) {
			return;
		}
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select the projects directory");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		int choose = fc.showDialog( GuiManager.getInstance().getFrame(), "Select" );
		
		if( choose == JFileChooser.APPROVE_OPTION ) {
			File dir = fc.getSelectedFile();
			String date = DateFormat.getDateInstance().format(new Date());
			final String directory = dir.getPath()+System.getProperty("file.separator")+"topologies-"+date;
			final int n = Integer.parseInt(number);
			final int routers = Integer.parseInt(maxRouterString);
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					for( int i = 0; i < n; i++ ) {
						Topology t = new Topology("proj" + i, directory, areas, routers, 
								new Factory(), new VeryBasicLayouting() );
						t.createTopology();
						try {
							Thread.sleep(10);
						}catch (Exception e) {
						}
					}
					JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(),
							number + " topologies generated in " + directory);
				}
			});
			t.start();
		} 
	}
	
	private void createDialog() {
		final JDialog dialog = new JDialog(GuiManager.getInstance().getFrame());
		dialog.setTitle("Topologies generation");
		dialog.setLayout(new BorderLayout());

		final JLabel label;
		label = new JLabel("Insert data: number of topologies and areas (at least 3)");
		
		label.setFont( new Font("Serif", Font.BOLD, 14) );
		label.setBorder( BorderFactory.createEmptyBorder(5, 5, 5, 5) );
		dialog.add( label, BorderLayout.NORTH );
		
		JPanel panel = new JPanel( new GridBagLayout() );
		panel.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10) );
		
		GridBagConstraints labelConstraint = new GridBagConstraints();
		labelConstraint.anchor = GridBagConstraints.EAST;
		labelConstraint.ipadx = 10;
		labelConstraint.ipady = 5;
		
		GridBagConstraints textFieldConstraint = new GridBagConstraints();
		textFieldConstraint.gridwidth = 3;
		textFieldConstraint.anchor = GridBagConstraints.EAST;

		JLabel icon = new JLabel(new ImageIcon("data/images/big/route.png"));
		labelConstraint.gridy = 0;
		labelConstraint.gridx = 0;
		labelConstraint.gridheight = 4;
		panel.add( icon, labelConstraint );
		labelConstraint.gridheight = 1;
		
		labelConstraint.gridy = 0;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Number:"), labelConstraint );
		
		final JTextField numberField = new JTextField(number, 15);
		textFieldConstraint.gridy = 0;
		textFieldConstraint.gridx = 2; 
		panel.add( numberField, textFieldConstraint );
		
		labelConstraint.gridy = 1;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Areas:"), labelConstraint );
		
		final JTextField areasField = new JTextField(areasString, 15);
		textFieldConstraint.gridy = 1;
		textFieldConstraint.gridx = 2;
		panel.add( areasField, textFieldConstraint );
		
		labelConstraint.gridy = 2;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Max routers:"), labelConstraint );
		
		final JTextField maxRoutersField = new JTextField(maxRouterString, 15);
		textFieldConstraint.gridy = 2;
		textFieldConstraint.gridx = 2;
		panel.add( maxRoutersField, textFieldConstraint );
		
		Dimension size = new Dimension(65,30);
		
		labelConstraint.gridy = 3;
		labelConstraint.gridx = 4;
		
		JPanel buttonPanel = new JPanel();
		
		final JButton go = new JButton("Go");
		go.setPreferredSize(size);
		buttonPanel.add( go );
		
		JButton cancel = new JButton("Cancel");
		cancel.setPreferredSize(size);
		buttonPanel.add( cancel );
		
		panel.add( buttonPanel, labelConstraint );
		
		dialog.add(panel, BorderLayout.CENTER);
		
		go.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String n = numberField.getText();
				String areas = areasField.getText();
				
				if( !areas.matches("^((\\w|\\d)+ ){2}((\\w|\\d)+ )*(\\w|\\d)+") ||
					!n.matches("\\d+") ) {
					label.setText("Error: e.g. Number: 3 Areas: DMZ Green Red");
					dialog.repaint();
				} else {
					System.out.println("Generate " + n + " topologies. Areas: " + areas);
					number = n;
					areasString = areas;
					dialog.dispose();
				}
			}
		});
		
		cancel.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		
		dialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		dialog.pack();
		dialog.setLocationRelativeTo(GuiManager.getInstance().getFrame());
		dialog.setVisible(true);
	}
}
