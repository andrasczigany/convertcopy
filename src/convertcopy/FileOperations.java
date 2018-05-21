package convertcopy;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import it.sauronsoftware.jave.AudioInfo;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;

public class FileOperations {

	public boolean existsAndReadable(String fileNameWithPath) {
		if (fileNameWithPath == null || fileNameWithPath == "")
			return false;
		if (Files.isReadable(Paths.get(fileNameWithPath))) {
			return true;
		}
		else {
			System.out.println("File/folder not readable: " + fileNameWithPath);
			return false;
		}
	}
	
	public boolean existsAndWritable(String folderNameWithPath) {
		if (folderNameWithPath == null || folderNameWithPath == "")
			return false;
		if (Files.isWritable(Paths.get(folderNameWithPath))) {
			return true;
		}
		else {
			System.out.println("Folder not writable: " + folderNameWithPath);
			return false;
		}
	}
	
	public String getFileType(String fileNameWithPath) {
		
		if (!existsAndReadable(fileNameWithPath)) return null;
		
		if (getFileSize(fileNameWithPath) == 0) return null;
		
		Encoder encoder = new Encoder();
		File inputFile = new File(fileNameWithPath);
		try {
			return encoder.getInfo(inputFile).getFormat();
		} catch (EncoderException ee) {
		} 
		
		return null;
	}

	public long getFileSize(String fileNameWithPath) {
		if (fileNameWithPath == null || fileNameWithPath.equals(""))
			return -1;
		File file = new File(fileNameWithPath);
		return file.length();
	}
	
	public Integer getBitRate(String fileNameWithPath) {

		if (!existsAndReadable(fileNameWithPath)) return null;
		
		Encoder encoder = new Encoder();
		File inputFile = new File(fileNameWithPath);
		
		try {
			AudioInfo audioInfo = encoder.getInfo(inputFile).getAudio();
			if (audioInfo == null) return null;
			return audioInfo.getBitRate();
		} catch (EncoderException ee) {
		} 

		return null;
	}

	public Integer getSampleRate(String fileNameWithPath) {

		if (!existsAndReadable(fileNameWithPath)) return null;
		
		Encoder encoder = new Encoder();
		File inputFile = new File(fileNameWithPath);
		
		try {
			AudioInfo audioInfo = encoder.getInfo(inputFile).getAudio();
			if (audioInfo == null) return null;
			return audioInfo.getSamplingRate();
		} catch (EncoderException ee) {
		} 

		return null;
	}

	public boolean copyFile(String sourceFileFolder, String sourceFileName, String targetFolder, boolean overwrite) {

        if (sourceFileFolder == null || "".equals(sourceFileFolder)) return false;
        if (sourceFileName == null || "".equals(sourceFileName)) return false;
        
        if (!sourceFileFolder.endsWith("\\")) sourceFileFolder = sourceFileFolder + "\\";
        if (!targetFolder.endsWith("\\")) targetFolder = targetFolder + "\\";

        try {
			Files.createDirectories(Paths.get(targetFolder));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        try {
        	if (overwrite)
        		Files.copy(Paths.get(sourceFileFolder + sourceFileName), Paths.get(targetFolder + sourceFileName), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        	else 
        		Files.copy(Paths.get(sourceFileFolder + sourceFileName), Paths.get(targetFolder + sourceFileName));
        	return existsAndReadable(targetFolder + sourceFileName);
        } catch (FileAlreadyExistsException aee) {
        	return false;
        } catch (DirectoryNotEmptyException dne) {
        	return existsAndReadable(targetFolder + sourceFileName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public boolean copyFolder(String sourceFolder, String targetFolder, boolean overwrite) {
		if (existsAndReadable(targetFolder) && !overwrite) return false;

		File srcDir = new File(sourceFolder);
		File destDir = new File(targetFolder);

		if (srcDir == null || destDir == null) return false;
		
		try {
			FileUtils.copyDirectoryToDirectory(srcDir, destDir);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }

	public String createFolder(String parentFolder, String folderName) {
		String separator = "";
		if (!parentFolder.endsWith("\\")) separator = "\\";
		try {
			Path createdFolder = Files.createDirectory(Paths.get(parentFolder + separator + folderName));
			if (createdFolder == null) return null;
			if (existsAndReadable(createdFolder.toString())) return createdFolder.toString();
			else return null;
		} catch (FileAlreadyExistsException fae) {
			return parentFolder + separator + folderName;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean deleteFolder(String folderNameWithPath) {
		try {
			FileUtils.deleteDirectory(new File(folderNameWithPath));
			return !existsAndReadable(folderNameWithPath);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
