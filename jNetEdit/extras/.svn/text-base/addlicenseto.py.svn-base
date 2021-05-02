#!/usr/bin/python
"""
 addlicenseto. Copyright (C) 2010 Giuseppe Leone
 This program is free software; you can redistribute it and/or modify it under
 the terms of the GNU General Public License as published by the Free Software
 Foundation; either version 2 of the License, or (at your option) any later
 version.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 details.

 You should have received a copy of the GNU General Public License along with
 this program; if not, write to the Free Software Foundation, Inc., 51
 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 
 Utility script that add License Disclaimer at the beginning of a single or each
 file into a specified Directory.
 
"""
__author__  = "Giuseppe Leone"
__program__ = "addlicenseto"
__version__ = "0.2.0"
__date__    = "10-02-2010 23:15"

import os
import glob
import sys

# Global variables
text_to_inject = ""
be_verbose = False
be_recursive = False
extensions = []

def usage( message = '' ):    
    if ( len(message) == 0 ):
        print """addlicenseto 
Utility script that add "License Disclaimer" at the
beginning of a single or each files inside specified Directory.

Usage:
  addlicenseto [OPTION] license_file [directory | file]

General options:
  -t, --type            Specify which type of files inject
                        with the License Disclaimer
                        e.g.: --type cpp,java (without spaces)
  -h, --help            Show this screen and exit
  -r, --recursive       Apply the License Disclaimer to all files recursively
  -v, --verbose         Display what's going on

Usage Example:
  Q) How can I apply the license "mylicense.txt" to all python script
     inside a specified directory?
  
  A) addlicenseto --type php,java --recursive /path/to/mylicense.txt /path/to/apply/license/
  
  Q) How can I apply the license "mylicense.txt" to multiple file
     (e.g. cpp and java) into a specified directory?
     
  A) addlicenseto --type cpp,java --recursive /path/to/mylicense.txt /path/to/apply/license/

Please report bugs to <joseph at masterdrive.it>"""
    else:
        print message

def is_valid_extension( filename ):
    for i in extensions:
        if filename[-len(i):] == i:
            return 1
    return 0
    
def inject_files( directory ):
    try:
        os.chdir( directory )
    except:
        usage( "Unable to change location: " + directory )
    finally:
        files = glob.glob('*')

        for f in files:
            # Check extension
            if os.path.isfile( directory + "/" + f ):
                if is_valid_extension( f ):
                    # open file
                    fh = open( directory + "/" + f , 'r' )
                    new_content = text_to_inject + "\n" + fh.read()
                    fh = open( directory + "/" + f , 'w' )
                    fh.write( new_content )
                    
                    if be_verbose:
                        print "Injected file: " + directory + "/" + f
            else:
                if be_recursive:
                    inject_files( directory + "/" + f )

# MAIN
if __name__ == "__main__":
    # Process command line
    if ( len(sys.argv) < 3 or ( "-h" in sys.argv or "--help" in sys.argv ) ):
        usage()
    else:
        # Check the for the Directory or File to inject
        if ( os.path.isfile( sys.argv[len(sys.argv)-1] ) or os.path.isdir( sys.argv[len(sys.argv)-1] ) ):
            target = sys.argv[len(sys.argv)-1]
            
            # Check for the License File
            if os.path.isfile( sys.argv[len(sys.argv)-2] ):
                # Check file type to inject
                if ( "-t" in sys.argv or "--type" in sys.argv ):
                    # Extract type of files
                    if "-t" in sys.argv:
                        indexof = sys.argv.index( "-t" )
                    elif "--type" in sys.argv:
                        indexof = sys.argv.index( "--type" )
                    extensions = sys.argv[indexof+1].split(",")
                    
                    # Check for recursive or verbose flags
                    if ( "-r" in sys.argv or "--recursive" in sys.argv ):
                        be_recursive = True
                        
                    if ( "-v" in sys.argv or "--verbose" in sys.argv ):
                        be_verbose = True
                    
                    # Read the license
                    flicense = open( sys.argv[len(sys.argv)-2], 'r' )
                    text_to_inject = flicense.read()
                    if be_verbose:
                        print "LICENSE DICLAIMER THAT WILL BE INJECTED:\n\n" + text_to_inject
                    
                    # Retrieve the absolute path of target
                    target = os.path.abspath( target )
                    
                    # Inject
                    inject_files( target )
                else:
                    usage( "You must specify which type of file inject (E.g. --type java,cpp)" )
            else:
                usage()
        else:
            usage( "The destination is not a valid file or directory" )
