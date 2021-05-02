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

package com.jnetedit.gui.gcomponents.gtree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class GTree extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private GTreeNode rootNode;
	private DefaultTreeModel treeModel;
	private JTree tree;
    
    public GTree( String treeLabel, String projectName ) {
        super(new BorderLayout());
        
		JPanel northPanel = new JPanel(new BorderLayout());
		northPanel.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 0));
		northPanel.add( new JLabel( treeLabel ), BorderLayout.WEST );
		
		Dimension size = new Dimension(20, 20);
		
		JButton up = new JButton(new ImageIcon("data/images/16x16/up_icon.png"));
		up.setPreferredSize(size);
		up.setToolTipText("Bring the selected node up");
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GTreeNode node = getSelectedNode();
				if( node != null ) {
					node.goUp();
				}
			}
		});
		
		JButton down = new JButton(new ImageIcon("data/images/16x16/down_icon.png"));
		down.setPreferredSize(size);
		down.setToolTipText("Bring the selected node down");
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GTreeNode node = getSelectedNode();
				if( node != null ) {
					node.goDown();
				}
			}
		});
		
		JButton coll = new JButton(new ImageIcon("data/images/16x16/collapse_icon.png"));
		coll.setPreferredSize(size);
		coll.setToolTipText("Collapse All");
		coll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				collapseAll();
			}
		});
		
		JButton exp = new JButton(new ImageIcon("data/images/16x16/expand_icon.png"));
		exp.setPreferredSize(size);
		exp.setToolTipText("Expand All");
		exp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expandAll();
			}
		});
		
		JToolBar buttonsBar = new JToolBar();
		buttonsBar.setFloatable(false);
		buttonsBar.add(up);
		buttonsBar.add(down);
		buttonsBar.addSeparator();
		buttonsBar.add(coll);
		buttonsBar.add(exp);
		
		northPanel.add(buttonsBar, BorderLayout.EAST);
		
		add( northPanel, BorderLayout.NORTH );
        
		// creating the tree and his tree model
        rootNode = new GTreeNode(projectName, GTreeNode.PROJECTFOLDER, this);
        treeModel = new DefaultTreeModel(rootNode);
        tree = new JTree(treeModel);
        tree.setEditable(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addMouseListener( new TreeListener() );
        tree.setCellRenderer( new TreeNodeRenderer() );

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane, BorderLayout.CENTER);
        
        ToolTipManager.sharedInstance().registerComponent(tree);
    }
    
    public void collapseAll() {
    	for( int row = tree.getRowCount(); row >= 0; row-- ) {
    		tree.collapseRow(row);
    	}
    }

    public void expandAll() {
    	for( int row = 0; row < tree.getRowCount(); row++ ) {
    		tree.expandRow(row);
    	}
    }
    
    /** expand the node with this name */
    public void expandNode( String nodeName ) {
    	collapseAll();
    	
    	for( int i = 0; i < rootNode.getChildCount(); i++ ) {
    		GTreeNode node = ((GTreeNode) rootNode.getChildAt(i));
    		if( node.getUserObject().equals( nodeName ) ) {
    			TreePath path = new TreePath(node.getPath());
    			tree.setSelectionPath(path);
    			int row = tree.getRowForPath(path);
    			tree.expandRow(row);
    			return;
    		}
    	}
    }
    
    /** set the name of this tree's root node
     */
    public void setName( String name ) {
		rootNode.setUserObject( name );
	}

    /** Remove all nodes except the root node.
     */
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }
    
    /**
     * Return the current selected node, or null if nothing is selected
     * 
     * @return GTreeNode
     */
    public GTreeNode getSelectedNode() {
    	TreePath currentSelection = tree.getSelectionPath();
    	GTreeNode selectedNode = null;
    	if( currentSelection != null )
    		selectedNode = (GTreeNode) currentSelection.getLastPathComponent();
    	return selectedNode;
    }
    
    /**
     * Select this node
     * 
     * @param node 
     */
    public void selectNode( GTreeNode node ) {
    	TreePath path = new TreePath(node.getPath());
    	tree.setSelectionPath(path);
    }

    /** Remove the currently selected node.
     */
    public void removeCurrentNode() {
   		treeModel.removeNodeFromParent(getSelectedNode());
    }

    /** Add child to the root node.
     * 
     * @param child
     * @param type
     * @return GTreeNode - the added node
     */
    public GTreeNode addNode( Object child, int type ) {
        return addNode( rootNode, child, type );
    }

    /** Add child to the parent node.
     * 
     * @param parent
     * @param obj
     * @param type
     * @return GTreeNode - the added node
     */
    public GTreeNode addNode( GTreeNode parent, Object obj, int type ) {
    	GTreeNode node = new GTreeNode(obj, type, this);
    	if (parent == null) 
    		parent = rootNode;
        treeModel.insertNodeInto(node, parent, parent.getChildCount());
        
        collapseAll();
        tree.scrollPathToVisible(new TreePath(node.getPath()));
        
    	return node;
    }
    
    public DefaultTreeModel getTreeModel() {
		return treeModel;
	}
    
    

    /** the mouse listeners for the tree nodes
     */
    private class TreeListener extends MouseAdapter {
    	@Override
    	public void mousePressed(MouseEvent e) {
    		TreePath path = tree.getPathForLocation(e.getX(), e.getY());
			tree.setSelectionPath(path);
			GTreeNode node = getSelectedNode();
			if( node != null ) {
				if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3)
					node.showMenu(e);
				
				if (e.getClickCount() >= 2)
					node.viewNode();
			}
		}
    }

    /** Renderer for the tree */
    private class TreeNodeRenderer extends DefaultTreeCellRenderer {
    	private static final long serialVersionUID = 1L;

    	public TreeNodeRenderer() {
    		super();
    	}

    	public Component getTreeCellRendererComponent( JTree tree, Object value,
    			boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

    		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

    		GTreeNode node = (GTreeNode) value;
    		setIcon( node.getIcon() );
    		setToolTipText( node.getInfo() );

    		return this;
    	}
    }
}