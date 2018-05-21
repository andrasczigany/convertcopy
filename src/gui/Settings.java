package gui;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeSelectionModel;

public interface Settings {

    // main window
    public final String TITLE = "MyCopy";
    public final Dimension WINDOW_SIZE = new Dimension(1000, 600);
    public final int WINDOW_POS_X = 50;
    public final int WINDOW_POS_Y = 100;
    public final Dimension RESULT_FIELD_SIZE = new Dimension(80,20);
    
    
    // lists
    public final Dimension BROWSER_SIZE = new Dimension(500,500);
    public final Insets CONTROL_BUTTON_INSETS = new Insets(2,2,2,2);
    public final Dimension CONTROL_BUTTON_SIZE = new Dimension(80,20);
    public final ImageIcon ICON_FILE = new ImageIcon("images/default2.jpg");
    public final ImageIcon ICON_MP3 = new ImageIcon("images/myIcon1.jpg");
    
    // browser tree
    public final String BROWSER_ROOT_PATH = "e:/"; // def: File.SEPARATOR
    public final int BROWSER_COLUMN_WIDTH_NAME = 75;
    public final String BROWSER_LINE_STYLE = "Angled";
    public final int BROWSER_SELECTION_MODE = TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION;
    public final boolean BROWSER_SHOW_ROOT = false;
    public final boolean BROWSER_SHOW_HANDLES = true;
    public final int BROWSER_TOGGLE_CLICKS = 2;
    
    // selected tree
    public final String SELECTED_LINE_STYLE = "Angled";
    public final int SELECTED_SELECTION_MODE = TreeSelectionModel.CONTIGUOUS_TREE_SELECTION;
    public final boolean SELECTED_SHOW_ROOT = false;
    public final boolean SELECTED_SHOW_HANDLES = true;
    public final int SELECTED_TOGGLE_CLICKS = 2;

    
    // file filters
    public final String[] FILTER_MUSIC = new String[] {"mp3", "wav", "flac"};
    public final double FLAC_MP3_SIZE_CONVERTER = 0.3;
	public final String DEFAULT_TARGET_FOLDER = "d:\\";
}
