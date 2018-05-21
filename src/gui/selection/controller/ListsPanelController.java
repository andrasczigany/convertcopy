package gui.selection.controller;

import gui.selection.model.FileNode;
import gui.selection.model.SelectionNode;
import gui.selection.view.ListsPanel;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.tree.TreePath;

public class ListsPanelController {
    
    public ListsPanel view = null;
    private Point cp = new Point(0,0);  // where right click happened

    public ListsPanelController(ListsPanel p) {
        this.view = p;
    }
    
    public void upActionPerformed() {
        TreePath[] toSelection = view.getToTree().getSelectionPaths();
        if (toSelection == null)
            return;
        view.getToTree().moveNodes(toSelection, -1);
        view.getToTree().reExpand();
    }
    
    public void addActionPerformed() {
        TreePath[] selection = view.getFromTree().getTree().getSelectionPaths();
        TreePath[] toSelection = view.getToTree().getSelectionPaths();
        if (selection == null)
            return;
        for (int s = 0; s < selection.length; s++) {
            SelectionNode node = new SelectionNode(
                    ((FileNode)selection[s].getLastPathComponent()).getFile());
            if (!hasNode(view.getToTree().getRoot(), node.getName()))
                view.getToTree().insertNode(node, null);  // null: add to root
        }
        view.getToTree().reExpand();
        view.getToTree().setSelectionPaths(toSelection);
    }
    
    public boolean hasNode(SelectionNode parent, String node) {
        if (parent.children() == null)
            return false;
        for (int c = 0; c < parent.getChildCount(); c++) {
            if (node.equals(((SelectionNode)parent.getChildAt(c)).getName()))
                return true;
        }
        return false;
    }
    
    public void addToActionPerformed() {
        TreePath[] selection = view.getFromTree().getTree().getSelectionPaths();
        TreePath[] toSelection = view.getToTree().getSelectionPaths();
        if (selection == null || toSelection == null
                || toSelection.length != 1
                || !(((SelectionNode)toSelection[0].getLastPathComponent()).getFile().isDirectory()) )
            return;

        SelectionNode selDir = (SelectionNode)toSelection[0].getLastPathComponent();
        for (int s = 0; s < selection.length; s++) {
            SelectionNode node = new SelectionNode(
                    ((FileNode)selection[s].getLastPathComponent()).getFile());
            if (!hasNode(selDir, node.getName()))
                view.getToTree().insertNode(node, selDir);
        }
        view.getToTree().reExpand();
        view.getToTree().setSelectionPaths(toSelection);
    }
    
    public void removeActionPerformed() {
        TreePath[] selection = view.getToTree().getSelectionPaths();
        if (selection == null)
            return;
        for (int s = 0; s < selection.length; s++)
            view.getToTree().removeNode((SelectionNode)selection[s].getLastPathComponent());
        view.getToTree().reExpand();
    }
    
    public void downActionPerformed() {
        TreePath[] toSelection = view.getToTree().getSelectionPaths();
        if (toSelection == null)
            return;
        view.getToTree().moveNodes(toSelection, 1);
        view.getToTree().reExpand();
    }
    
    public void createActionPerformed(String name) {
        // TODO
//        File dir = new File("");
//        dir.mkdir();
//        SelectionNode n = new SelectionNode(dir);
//        view.getToTree().insertNode(n, null);
//        view.getToTree().reExpand();
    }
    
    public void renameActionPerformed(String name) {
        TreePath path = view.getToTree().getPathForLocation(cp.x, cp.y);
        if (path == null)
            return;
        ((SelectionNode)path.getLastPathComponent()).setName(name);
        view.revalidate();
        view.repaint();
    }
    
    public void deleteActionPerformed(ActionEvent e) {
        TreePath path = view.getToTree().getPathForLocation(cp.x, cp.y);
        if (path == null)
            return;
        view.getToTree().removeNode((SelectionNode)path.getLastPathComponent());
        view.getToTree().reExpand();
        view.revalidate();
        view.repaint();
    }

    public void setContextPoint(Point p) {
        this.cp = p;
    }
}
