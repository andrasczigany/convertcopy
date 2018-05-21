package gui.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

import gui.Settings;

public class Tools {

    public static boolean isFileMatching(String fileName, String[] filter) {
        if (fileName == null || filter == null)
            return false;
        if (filter.length == 0)
            return true;
        for (int f = 0; f < filter.length; f++)
            if (fileName.toLowerCase().indexOf(filter[f].toLowerCase()) > -1)
                return true;
        return false;
    }
    
    public static String getEstimatedSizeString(File item) {
    	if (item == null) return "";
    	return item.isFile() ? getSizeStringForFile(item) : getSizeStringForFolder(item);
    }
    
    public static long getEstimatedSize(File item) {
    	if (item == null) return 0;
    	return item.isFile() ? getSizeForFile(item) : getSizeForFolder(item);
    }
    
    private static long getSizeForFile(File file) {
    	if (file == null) return 0;
    	if (!file.isFile()) return 0;
    	if (file.getName().endsWith("mp3")) return file.length() / 1024 / 1024;
    	if (file.getName().endsWith("flac") || file.getName().endsWith("wav")) return (long)(file.length() * Settings.FLAC_MP3_SIZE_CONVERTER / 1024 / 1024);
    	return 0;
    }
    
    private static long getSizeForFolder(File folder) {
    	if (folder == null) return 0;
    	long size = FileUtils.sizeOfDirectory(folder);
    	return containsFlac(folder) ? ((long)(size * Settings.FLAC_MP3_SIZE_CONVERTER / 1024 / 1024)) : (long)(size / 1024 / 1024);
    }
    
    private static String getSizeStringForFile(File file) {
    	if (file == null) return "";
    	if (!file.isFile()) return "";
    	if (file.getName().endsWith("mp3")) return " [" + NumberFormat.getInstance().format(file.length() / 1024 / 1024) + " MB]";
    	if (file.getName().endsWith("flac") || file.getName().endsWith("wav")) return " [~" + NumberFormat.getInstance().format((long)(file.length() * Settings.FLAC_MP3_SIZE_CONVERTER / 1024 / 1024)) + " MB]";
    	return "";
    }
    
    private static String getSizeStringForFolder(File folder) {
    	if (folder == null) return "";
    	long size = FileUtils.sizeOfDirectory(folder);
    	return containsFlac(folder) ? " [~" + NumberFormat.getInstance().format((long)(size * Settings.FLAC_MP3_SIZE_CONVERTER / 1024 / 1024)) + " MB]" : " [" + NumberFormat.getInstance().format((long)(size / 1024 / 1024)) + " MB]";
    }
    
    public static boolean containsFlac(File folder) {
    	if (folder == null) return false;
    	try {
    		Stream<Path> stream = Files.find(folder.toPath(), 3, (path, attr) -> String.valueOf(path).endsWith(".flac"));
    		return stream.count() > 0;
		} catch (IOException e) {
			e.printStackTrace();
		}    		
    	return false;
    }
}
