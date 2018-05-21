package gui.selection.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;

public class TransferredData implements Transferable {

    public static DataFlavor FILE_FLAVOR = 
        new DataFlavor(File[].class, "URL");
    DataFlavor flavors[] = { FILE_FLAVOR };
    File[] data;
    
    public TransferredData(File[] f) {
        data = f;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return flavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return (flavor.getRepresentationClass() == File[].class);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (isDataFlavorSupported(flavor)) {
//            ArrayList l = new ArrayList(data.length);
//            for (int d = 0; d < data.length; d++)
//                l.add(data[d]);
            return data;
        }
        throw new UnsupportedFlavorException(flavor);
    }

}
