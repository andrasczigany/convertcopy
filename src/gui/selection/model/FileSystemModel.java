package gui.selection.model; 

import gui.util.treetable.AbstractTreeTableModel;
import gui.util.treetable.TreeTableModel;

import java.io.File;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.tree.TreePath;

import org.apache.commons.io.FileUtils;

/**
 * FileSystemModel is a TreeTableModel representing a hierarchical file 
 * system. Nodes in the FileSystemModel are FileNodes which, when they 
 * are directory nodes, cache their children to avoid repeatedly querying 
 * the real file system. 
 * 
 * @version %I% %G%
 *
 * @author Philip Milne
 * @author Scott Violet
 */

public class FileSystemModel extends AbstractTreeTableModel 
                             implements TreeTableModel {

    // Names of the columns.
    static protected String[]  cNames = {"Name", "Size" /*, "Type", "Modified"*/};

    // Types of the columns.
    static protected Class[]  cTypes = {TreeTableModel.class, Integer.class, String.class, Date.class};

    // The the returned file length for directories. 
    public static final Integer ZERO = new Integer(0); 

    public FileSystemModel(FileNode root) { 
        super(root);
    }
    
    public void addToRoot(TreePath path) {
        FileNode node = (FileNode)path.getLastPathComponent();
        Object[] ch = ((FileNode)getRoot()).getChildren();
        Object[] ch2 = new Object[ch.length+1];
        for(int c = 0; c < ch.length; c++)
            ch2[c] = ch[c];
        ch2[ch2.length-1] = node;
        ((FileNode)getRoot()).children = ch2;
        fireTreeNodesInserted(getRoot(), 
                new Object[] {path}, 
                new int[] {ch.length}, 
                new Object[] {node});
    }
    
    //
    // Some convenience methods. 
    //

    protected File getFile(Object node) {
	FileNode fileNode = ((FileNode)node); 
	return fileNode.getFile();       
    }

    protected Object[] getChildren(Object node) {
	FileNode fileNode = ((FileNode)node); 
	return fileNode.getChildren(); 
    }

    //
    // The TreeModel interface
    //

    public int getChildCount(Object node) { 
	Object[] children = getChildren(node); 
	return (children == null) ? 0 : children.length;
    }

    public Object getChild(Object node, int i) { 
	return getChildren(node)[i]; 
    }

    // The superclass's implementation would work, but this is more efficient. 
    public boolean isLeaf(Object node) { return getFile(node).isFile(); }

    //
    //  The TreeTableNode interface. 
    //

    public int getColumnCount() {
	return cNames.length;
    }

    public String getColumnName(int column) {
	return cNames[column];
    }

    public Class getColumnClass(int column) {
	return cTypes[column];
    }
 
    public Object getValueAt(Object node, int column) {
	File file = getFile(node); 
	try {
	    switch(column) {
	    case 0:
		return file.getName();
	    case 1:
		return file.isFile() ? NumberFormat.getInstance().format(file.length()) : NumberFormat.getInstance().format(FileUtils.sizeOfDirectory(file) / 1024 / 1024) + " MB";
	    case 2:
		return file.isFile() ?  "File" : "Directory";
	    case 3:
		return new Date(file.lastModified());
	    }
	}
	catch  (SecurityException se) { }
   
	return null; 
    }
    
}



