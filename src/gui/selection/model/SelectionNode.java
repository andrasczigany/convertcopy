package gui.selection.model;

import java.io.File;
import java.text.NumberFormat;

import javax.swing.tree.DefaultMutableTreeNode;

import gui.util.Tools;

public class SelectionNode extends DefaultMutableTreeNode {

    private File file = null;
    private String name = "";
    private long estimatedSize = 0;
    private boolean isLossless = false;
    
    public SelectionNode(File f) {
        this.file = f;
        if (f != null && f.getName() != null)
            this.name = f.getName();
        calculateEstimatedSize();
        if (isLeaf())  
       		isLossless = file.getName().endsWith("flac") || file.getName().endsWith("wav");
    	else isLossless = Tools.containsFlac(file);
    }
    
    public File getFile() {
        return this.file;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String n) {
        this.name = n;
    }
    
    public long getEstimatedSize() {
    	return estimatedSize;
    }
    
    public void calculateEstimatedSize() {
    	if (file == null) return;
    	if (isLeaf()) {
    		estimatedSize = Tools.getEstimatedSize(file);
    		return;
    	}
    	
    	long total = 0;
    	for (File f : file.listFiles()) {
    		total += Tools.getEstimatedSize(f);
    	}
    	estimatedSize = total;
    }
    
    public String toString() {
        return this.name + " [" + (isLossless ? "~" : "") + NumberFormat.getInstance().format(estimatedSize) + " MB]"; //Tools.getEstimatedSizeString(file);
    }
    
    public boolean isLeaf() {
        if (file == null)
            return false;
        return file.isFile();
    }
}
