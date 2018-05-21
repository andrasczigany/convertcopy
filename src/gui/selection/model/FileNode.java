package gui.selection.model;

import gui.util.treetable.MergeSort;

import java.io.File;
import java.util.ArrayList;

/* A FileNode is a derivative of the File class - though we delegate to 
 * the File object rather than subclassing it. It is used to maintain a 
 * cache of a directory's children and therefore avoid repeated access 
 * to the underlying file system during rendering. 
 */
public class FileNode { 
    File     file; 
    Object[] children; 
    
    public FileNode(File file) { 
        this.file = file; 
    }
    
    // Used to sort the file names.
    static private MergeSort  fileMS = new MergeSort() {
        public int compareElementsAt(int a, int b) {
            return ((String)toSort[a]).compareToIgnoreCase((String)toSort[b]);
        }
    };
    
    /**
     * Returns the the string to be used to display this leaf in the JTree.
     */
    public String toString() { 
        return file.getName();
    }
    
    public File getFile() {
        return file; 
    }
    
    /**
     * Loads the children, caching the results in the children ivar.
     */
    protected Object[] getChildren() {
        if (children != null) {
            return children; 
        }
        try {
            String[] files = getSorted(file);
            if(files != null) {
                children = new FileNode[files.length]; 
                String path = file.getPath();
                for(int i = 0; i < files.length; i++) {
                    File childFile = new File(path, files[i]); 
                    children[i] = new FileNode(childFile);
                }
            }
        } catch (SecurityException se) {}
        return children; 
    }
    
    private String[] getSorted(File file) {
        if (file == null)
            return null;
        File[] all = file.listFiles();
        if (all == null)
            return null;
        ArrayList dirs = new ArrayList(all.length);
        ArrayList fils = new ArrayList(all.length);
        for (int i=0; i<all.length; i++) {
            if (all[i].isDirectory())
                dirs.add(all[i].getName());
            else fils.add(all[i].getName());
        }
        String[] files = new String[fils.size()];
        fils.toArray(files);
        fileMS.sort(files);
        
        String[] directories = new String[dirs.size()];
        dirs.toArray(directories);
        fileMS.sort(directories);
        
        String[] result = new String[files.length+directories.length];
        for (int k=0; k<files.length+directories.length; k++) { 
            result[k] = k<directories.length ? directories[k] : files[k-directories.length];
        }        
        
        return result;
    }
}
