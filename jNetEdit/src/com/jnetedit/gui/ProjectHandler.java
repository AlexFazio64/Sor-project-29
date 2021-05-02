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

package com.jnetedit.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.jnetedit.core.nodes.AbstractHost;
import com.jnetedit.core.project.AbstractProject;

import edu.umd.cs.piccolo.util.PObjectOutputStream;


public class ProjectHandler {

	private AbstractProject project;
	
	private static ProjectHandler projectHandler;
	
	private static boolean saved = true;
	
	/** Singleton implementation for the ProjectHandler */
	private ProjectHandler() {
	}

	public static ProjectHandler getInstance() {
		if( projectHandler == null )  
			projectHandler = new ProjectHandler();
		
		return projectHandler;
	}
	/****************************************************/
	
	public boolean newProject() {
		if( !saved ) {
			int choose = JOptionPane.showConfirmDialog(GuiManager.getInstance().getFrame(), 
					"Are you sure you want to exit without save the project?", 
					"Project not saved", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if( choose == JOptionPane.NO_OPTION ) {
				return false;
			}
		}
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select the project's directory");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		int choose = fc.showOpenDialog( GuiManager.getInstance().getFrame() );
		if( choose == JFileChooser.APPROVE_OPTION ) {
			String dir = fc.getSelectedFile().getAbsolutePath();
			String projectName = dir.substring(dir.lastIndexOf("/")+1);
			project = GFactory.getInstance().createProject(projectName, dir);
			GuiManager.getInstance().setProject(project);
			saved = false;
			return true;
		} 
		return false;
	}
	
	public void saveProject() {
		File f = saveProjectSilent();
		if( f != null ) 
			JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), "Project Saved");
		else 
			JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), "Unable to save the project", 
					"Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public File saveProjectSilent() {
		project = Lab.getInstance().getProject();
		if( project != null ) {
			String projDir = project.getDirectory();
			
			createDirectory(projDir);
			
			String labConfcontent = project.getLabConfFile();
			createFile( "lab.conf", projDir, labConfcontent );
			
			for( AbstractHost host : project.getHosts() ) {
				boolean startup = false;
				if (startup) {
					String hostName = host.getName();
					createDirectory( projDir + "/" + hostName );
					String startupContent = host.getStartupFile();
					createFile( hostName + ".startup", projDir, startupContent );
				} else {
					String hostName = host.getName();
					createDirectory( projDir + "/" + hostName + "/etc/network/myscript" );
					String interfacesContent = host.getInterfacesFile();
					createFile( hostName+"/etc/network/interfaces", projDir, interfacesContent );
					String firewallScript = host.getScript();
					createFile( hostName+"/etc/network/myscript/firewall", projDir, firewallScript );
				}
			}
			
			saved = true;
			
			try {
				PObjectOutputStream out = new PObjectOutputStream( 
						new FileOutputStream(projDir + "/" + project.getName() + ".jne", false)); 
				out.writeObjectTree(Lab.getInstance());
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			GuiManager.getInstance().getConfPanel().update();
			return new File(projDir + "/" + project.getName() + ".jne");
		} else {
			JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), "Unable to save the project", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
//	private void rmDirContent(File dir) {
//		if( !dir.isDirectory() ) 
//			return;
//		File[] files = dir.listFiles();
//		for( File f : files ) {
//			if( f.isDirectory() ) {
//				rmDirContent(f);
//			}
//			f.delete();
//		}
//	}

	private void createFile( String fileName, String projDir, String content ) {
		File f = new File( projDir + "/" + fileName );
		if( f.exists() ) {
			f.delete();
			try {
				f.createNewFile();
				f.setExecutable(true);
				f.setReadable(true);
				f.setWritable(true);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"Error creating "+fileName, "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		try {
			PrintWriter out = new PrintWriter(f);
			
			out.println( content );
			out.flush();
			
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String createDirectory( String directory ) {
		File proj = new File( directory );
		if( !proj.exists() ) 
			proj.mkdirs();
		
		String dir = proj.getAbsolutePath();
		return dir;
	}

	public void setSaved(boolean b) {
		saved = b;
	}

	public boolean isSaved() {
		return saved;
	}

	public void openProject() {
		if( !saved ) {
			int choose = JOptionPane.showConfirmDialog(GuiManager.getInstance().getFrame(), 
					"Are you sure you want to exit without save the project?", 
					"Project not saved", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if( choose == JOptionPane.NO_OPTION ) {
				return;
			}
		}
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return "jNetEdit file - .jne";
			}
			
			@Override
			public boolean accept(File f) {
				if( f.isDirectory() ) 
					return true;
				return f.getName().endsWith(".jne");
			}
		});
		int choose = fileChooser.showOpenDialog(GuiManager.getInstance().getFrame());
		if( choose == JFileChooser.APPROVE_OPTION ) {
			File file = fileChooser.getSelectedFile();
			try {
				ObjectInputStream in = new ObjectInputStream( 
						new FileInputStream(file)); 
				Lab lab = (Lab) in.readObject();
				Lab.setInstance(lab);
				final AbstractProject project = Lab.getInstance().getProject();
				project.setDirectory(file.getParent());
				GuiManager.getInstance().setProject(project);
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void openProject(File projectFile) {
		if( !saved ) {
			int choose = JOptionPane.showConfirmDialog(GuiManager.getInstance().getFrame(), 
					"Are you sure you want to exit without save the project?", 
					"Project not saved", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if( choose == JOptionPane.NO_OPTION ) {
				return;
			}
		}
		try {
			ObjectInputStream in = new ObjectInputStream( 
					new FileInputStream(projectFile)); 
			Lab lab = (Lab) in.readObject();
			Lab.setInstance(lab);
			GuiManager.getInstance().setProject(Lab.getInstance().getProject());
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

