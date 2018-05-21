package gui.selection.view;

import gui.selection.controller.ListsPanelController;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewFolderDialog extends JDialog {

    private JTextField tf = null;
    private JButton okButton = null;
    private JButton cancelButton = null;
    private ListsPanelController controller = null;
    
    public NewFolderDialog(int x, int y, ListsPanelController c) {
        super();
        this.setTitle("Add name");
        this.setLocation(x+15,y+5);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.controller = c;
        this.init();
        this.pack();
        this.show();
    }
    
    private void init() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        p.add(getTextField());
        p.add(getOkButton());
        p.add(getCancelButton());
        this.setContentPane(p);
    }
    
    private JTextField getTextField() {
        if (tf == null) {
            tf = new JTextField();
            tf.setColumns(12);
        }
        return tf;
    }
    
    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton("Create");
            okButton.setMargin(new Insets(2,2,2,2));
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String name = getTextField().getText();
                    if (name != null && !"".equals(name)) {
                        controller.createActionPerformed(name);
                        dispose();
                    }
                }
            });
        }
        return okButton;
    }
    
    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton("Cancel");
            cancelButton.setMargin(new Insets(2,4,2,4));
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return cancelButton;
    }
    
}
