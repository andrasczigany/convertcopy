package gui.selection.view;

import gui.Settings;
import gui.selection.model.FileNode;
import gui.selection.model.FileSystemModel;
import gui.selection.model.TransferredData;
import gui.util.Tools;
import gui.util.treetable.JTreeTable;
import gui.util.treetable.TreeTableModel;

import java.awt.Component;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.io.File;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

public class BrowserTreeTable extends JTreeTable implements 
Settings, DragSourceListener, DragGestureListener {
    
    private static BrowserTreeTable instance = null;
    DragSource source;
    DragGestureRecognizer recognizer;
    TransferredData transferable;
    
    private BrowserTreeTable() {
        super(new FileSystemModel(new FileNode(new File(BROWSER_ROOT_PATH))));
        this.initialize();
    }
    
    public static BrowserTreeTable getInstance() {
        if (instance == null)
            instance = new BrowserTreeTable();
        return instance;
    }
    
    private void initialize() {
        this.getColumnModel().getColumn(1).setPreferredWidth(BROWSER_COLUMN_WIDTH_NAME);
        this.getColumnModel().getColumn(1).setMaxWidth(BROWSER_COLUMN_WIDTH_NAME);
        this.getTree().setRootVisible(BROWSER_SHOW_ROOT);
        this.getTree().setShowsRootHandles(BROWSER_SHOW_HANDLES);
        //this.getTree().getSelectionModel().setSelectionMode(BROWSER_SELECTION_MODE);
        this.putClientProperty("JTree.lineStyle", BROWSER_LINE_STYLE);
        this.getTree().setToggleClickCount(BROWSER_TOGGLE_CLICKS);

        DefaultTreeCellRenderer rend = new DefaultTreeCellRenderer() {
            public Component getTreeCellRendererComponent(
                    JTree tree,
                    Object value,
                    boolean sel,
                    boolean expanded,
                    boolean leaf,
                    int row,
                    boolean hasFocus) {
                
                super.getTreeCellRendererComponent(tree, value, sel,
                        expanded, leaf, row, hasFocus);
                String n = (String)((TreeTableModel)getTree().getModel()).getValueAt(value,0);
                if (leaf)
                    setIcon(Tools.isFileMatching(n, FILTER_MUSIC)
                            ? ICON_MP3 : ICON_FILE);
                setToolTipText(n);
                return this;
            }
        };
        this.getTree().setCellRenderer(rend);
        
//        this.setDragEnabled(true);
//        this.getTree().setUI(new MultiDragTreeUI());
        source = new DragSource();
        recognizer = source.createDefaultDragGestureRecognizer(
                this, DnDConstants.ACTION_COPY, this);
    }
    
    /*
     * Drag Gesture Handler
     */
    public void dragGestureRecognized(DragGestureEvent dge) {
        TreePath[] path = getTree().getSelectionPaths();
        if (path == null)
            return;
        File[] f = new File[path.length];
        for (int p = 0; p < path.length; p++)
            f[p] = (((FileNode)path[p].getLastPathComponent()).getFile());
        transferable = new TransferredData(f);
        source.startDrag(dge, DragSource.DefaultCopyDrop, transferable, this);
    }
    
    /*
     * Drag Event Handlers
     */
    public void dragEnter(DragSourceDragEvent dsde) {}
    public void dragExit(DragSourceEvent dse) { }
    public void dragOver(DragSourceDragEvent dsde) {}
    public void dropActionChanged(DragSourceDragEvent dsde) {}
    public void dragDropEnd(DragSourceDropEvent dsde) {}

}
