package gui.selection.view;

import gui.Settings;
import gui.selection.controller.ListsPanelController;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class ListsPanel extends JPanel implements Settings {

    private JPanel fromPanel = null;
    private JPanel controlPanel = null;
    private JPanel toPanel = null;
    private JScrollPane fromPane = null;
    private JScrollPane toPane = null;
    private BrowserTreeTable fromTree = null;
    private SelectedTree toTree = null;
    private JPanel controlButtonsPanel = null;
    private JPanel upButtonPanel = null;
    private JPanel addButtonPanel = null;
    private JPanel removeButtonPanel = null;
    private JPanel downButtonPanel = null;
    private JButton upButton = null;
    private JButton addButton = null;
    private JButton removeButton = null;
    private JButton downButton = null;
    private JPanel addToButtonPanel = null;
    private JButton addToButton = null;
    
    private JPopupMenu contextMenu = null;
    private JMenuItem create = null;
    private JMenuItem rename = null;
    private JMenuItem delete = null;
    
    private ListsPanelController controller = null;
    
    private Point contextPoint = new Point(0, 0);
    
    public ListsPanel() {
        super();
        this.initialize();
    }

    /**
     * This method initializes this   
     *  
     */
    private void initialize() {
        this.controller = new ListsPanelController(this);
        this.initContextMenu();
        this.setLayout(new FlowLayout());
        this.add(getFromPanel(), null);
        this.add(getControlPanel(), null);
        this.add(getToPanel(), null);
    }
    
    private void initContextMenu() {
        contextMenu = new JPopupMenu();
        
        create = new JMenuItem("New folder...");
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new NewFolderDialog(contextPoint.x, contextPoint.y, controller);
            }
        });
        
        rename = new JMenuItem("Rename");
        rename.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Point p = new Point(contextPoint.x, contextPoint.y);
                SwingUtilities.convertPointFromScreen(p, getToTree());
                if (getToTree().getPathForLocation(p.x, p.y) == null)
                    return;
                new RenameDialog(contextPoint.x, contextPoint.y, controller);
            }
        });

        delete = new JMenuItem("Delete");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Point p = new Point(contextPoint.x, contextPoint.y);
                SwingUtilities.convertPointFromScreen(p, getToTree());
                if (getToTree().getPathForLocation(p.x, p.y) == null)
                    return;
                controller.deleteActionPerformed(e);
            }
        });
        
        contextMenu.add(create);
        contextMenu.add(rename);
        contextMenu.add(delete);
    }

    /**
     * This method initializes fromPanel    
     *  
     * @return javax.swing.JPanel   
     */
    private JPanel getFromPanel() {
        if (fromPanel == null) {
            fromPanel = new JPanel();
            fromPanel.add(getFromPane(), null);  // Generated
        }
        return fromPanel;
    }

    /**
     * This method initializes controlPanel 
     *  
     * @return javax.swing.JPanel   
     */
    private JPanel getControlPanel() {
        if (controlPanel == null) {
            controlPanel = new JPanel();
            controlPanel.setLayout(new BorderLayout());  // Generated
            controlPanel.add(getControlButtonsPanel(), java.awt.BorderLayout.CENTER);  // Generated
        }
        return controlPanel;
    }

    /**
     * This method initializes toPanel  
     *  
     * @return javax.swing.JPanel   
     */
    private JPanel getToPanel() {
        if (toPanel == null) {
            toPanel = new JPanel();
            toPanel.add(getToPane(), null);  // Generated
        }
        return toPanel;
    }

    /**
     * This method initializes fromPane 
     *  
     * @return javax.swing.JScrollPane  
     */
    private JScrollPane getFromPane() {
        if (fromPane == null) {
            fromPane = new JScrollPane();
            fromPane.setViewportView(getFromTree());  // Generated
            fromPane.setMinimumSize(BROWSER_SIZE);
            fromPane.setSize(BROWSER_SIZE);
            fromPane.setPreferredSize(BROWSER_SIZE);
        }
        return fromPane;
    }

    /**
     * This method initializes toPane   
     *  
     * @return javax.swing.JScrollPane  
     */
    private JScrollPane getToPane() {
        if (toPane == null) {
            toPane = new JScrollPane();
            toPane.setViewportView(getToTree());  // Generated
            toPane.setMinimumSize(BROWSER_SIZE);
            toPane.setSize(BROWSER_SIZE);
            toPane.setPreferredSize(BROWSER_SIZE);
        }
        return toPane;
    }

    /**
     * This method initializes fromTree 
     *  
     * @return javax.swing.JTree    
     */
    public BrowserTreeTable getFromTree() {
        if (fromTree == null) {
            fromTree = BrowserTreeTable.getInstance();
        }
        return fromTree;
    }

    /**
     * This method initializes toTree   
     *  
     * @return javax.swing.JTree    
     */
    public SelectedTree getToTree() {
        if (toTree == null) {
            toTree = SelectedTree.getInstance();
            toTree.addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        contextMenu.show(e.getComponent(),e.getX(), e.getY());
                        Point p = new Point(e.getX(), e.getY());
                        contextPoint.x = p.x;
                        contextPoint.y = p.y;
                        SwingUtilities.convertPointToScreen(contextPoint, e.getComponent());
                        controller.setContextPoint(p);
                    }
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }

                public void mousePressed(MouseEvent e) {
                }

                public void mouseReleased(MouseEvent e) {
                }
                
            });
        }
        return toTree;
    }

    /**
     * This method initializes controlButtonsPanel  
     *  
     * @return javax.swing.JPanel   
     */
    private JPanel getControlButtonsPanel() {
        if (controlButtonsPanel == null) {
            controlButtonsPanel = new JPanel();
            controlButtonsPanel.setLayout(new BoxLayout(getControlButtonsPanel(), BoxLayout.Y_AXIS));  // Generated
            controlButtonsPanel.add(getUpButtonPanel(), null);  // Generated
            controlButtonsPanel.add(getAddButtonPanel(), null);  // Generated
            controlButtonsPanel.add(getAddToButtonPanel(), null);  // Generated
            controlButtonsPanel.add(getRemoveButtonPanel(), null);  // Generated
            controlButtonsPanel.add(getDownButtonPanel(), null);  // Generated
        }
        return controlButtonsPanel;
    }

    /**
     * This method initializes upButtonPanel    
     *  
     * @return javax.swing.JPanel   
     */
    private JPanel getUpButtonPanel() {
        if (upButtonPanel == null) {
            upButtonPanel = new JPanel();
            upButtonPanel.add(getUpButton(), null);  // Generated
        }
        return upButtonPanel;
    }

    /**
     * This method initializes addButtonPanel   
     *  
     * @return javax.swing.JPanel   
     */
    private JPanel getAddButtonPanel() {
        if (addButtonPanel == null) {
            addButtonPanel = new JPanel();
            addButtonPanel.add(getAddButton(), null);  // Generated
        }
        return addButtonPanel;
    }

    /**
     * This method initializes addToButtonPanel 
     *  
     * @return javax.swing.JPanel   
     */
    private JPanel getAddToButtonPanel() {
        if (addToButtonPanel == null) {
            addToButtonPanel = new JPanel();
            addToButtonPanel.add(getAddToButton(), null);  // Generated
        }
        return addToButtonPanel;
    }

    /**
     * This method initializes removeButtonPanel    
     *  
     * @return javax.swing.JPanel   
     */
    private JPanel getRemoveButtonPanel() {
        if (removeButtonPanel == null) {
            removeButtonPanel = new JPanel();
            removeButtonPanel.add(getRemoveButton(), null);  // Generated
        }
        return removeButtonPanel;
    }

    /**
     * This method initializes downButtonPanel  
     *  
     * @return javax.swing.JPanel   
     */
    private JPanel getDownButtonPanel() {
        if (downButtonPanel == null) {
            downButtonPanel = new JPanel();
            downButtonPanel.add(getDownButton(), null);  // Generated
        }
        return downButtonPanel;
    }

    /**
     * This method initializes upButton 
     *  
     * @return javax.swing.JButton  
     */
    private JButton getUpButton() {
        if (upButton == null) {
            upButton = new JButton();
            upButton.setText("Move up");  // Generated
            upButton.setMargin(CONTROL_BUTTON_INSETS);
            upButton.setPreferredSize(CONTROL_BUTTON_SIZE);
            upButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    controller.upActionPerformed();
                }
            });
        }
        return upButton;
    }

    /**
     * This method initializes addButton    
     *  
     * @return javax.swing.JButton  
     */
    private JButton getAddButton() {
        if (addButton == null) {
            addButton = new JButton();
            addButton.setText("Add");  // Generated
            addButton.setMargin(CONTROL_BUTTON_INSETS);
            addButton.setPreferredSize(CONTROL_BUTTON_SIZE);
            addButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    controller.addActionPerformed();
                }
            });
        }
        return addButton;
    }

    /**
     * This method initializes addToButton  
     *  
     * @return javax.swing.JButton  
     */
    private JButton getAddToButton() {
        if (addToButton == null) {
            addToButton = new JButton();
            addToButton.setText("Add to folder");  // Generated
            addToButton.setMargin(CONTROL_BUTTON_INSETS);
            addToButton.setPreferredSize(CONTROL_BUTTON_SIZE);
            addToButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    controller.addToActionPerformed();
                }
            });
        }
        return addToButton;
    }

    /**
     * This method initializes removeButton 
     *  
     * @return javax.swing.JButton  
     */
    private JButton getRemoveButton() {
        if (removeButton == null) {
            removeButton = new JButton();
            removeButton.setText("Remove");  // Generated
            removeButton.setMargin(CONTROL_BUTTON_INSETS);
            removeButton.setPreferredSize(CONTROL_BUTTON_SIZE);
            removeButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    controller.removeActionPerformed();
                }
            });
        }
        return removeButton;
    }

    /**
     * This method initializes downButton   
     *  
     * @return javax.swing.JButton  
     */
    private JButton getDownButton() {
        if (downButton == null) {
            downButton = new JButton();
            downButton.setText("Move down");  // Generated
            downButton.setMargin(CONTROL_BUTTON_INSETS);
            downButton.setPreferredSize(CONTROL_BUTTON_SIZE);
            downButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    controller.downActionPerformed();
                }
            });
        }
        return downButton;
    }
    
}
