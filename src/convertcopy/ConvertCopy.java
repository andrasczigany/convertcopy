package convertcopy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConvertCopy {

	private FileOperations fileOperations = null;
	private AudioConverter converter = null;
	private String tempFolder = null;
	private HashMap<String, ArrayList<String>> removedItems = null;

	private FileOperations getFileOperations() {
		if (fileOperations == null)
			fileOperations = new FileOperations();
		return fileOperations;
	}

	private AudioConverter getConverter() {
		if (converter == null)
			converter = new AudioConverter();
		return converter;
	}

	public int[] convertCopy(List<File> sourceItems, String targetFolder, HashMap<String, ArrayList<String>> removedItems, boolean convert, boolean overwrite) {

		int convertErrors = 0;
		int copyErrors = 0;
		this.tempFolder = null;
		this.removedItems = removedItems;
		List<File> itemsToCopy = new ArrayList<>();
		
		if (!convert) {
			itemsToCopy = sourceItems;
		} else {
			File convertedItem = null;
			tempFolder = getFileOperations().createFolder("e:\\", "tmp-convertcopy");

			for (File item : sourceItems) {
				convertedItem = convertItem(item);
				if (convertedItem == null)
					convertErrors++;
				else
					itemsToCopy.add(convertedItem);
			}
			System.out.println("Conversion completed with " + convertErrors + " errors.");
		}

		for (File itemToCopy : itemsToCopy) {
			if (!copyItem(itemToCopy, targetFolder, overwrite))
				copyErrors++;
		}
		System.out.println("Copy completed with " + copyErrors + " errors.");

		if (!getFileOperations().deleteFolder(tempFolder))
			System.out.println("Error when deleting " + tempFolder);

		return new int[] { convertErrors, copyErrors };
	}

	private File convertFile(File fileToConvert, String targetFolder) {
		String convertedFile = getConverter().convertFlacToMp3(fileToConvert.getParent(), fileToConvert.getName(),
				targetFolder, 320000, 48000);
		if (convertedFile == null)
			return null;
		return new File(convertedFile);
	}

	//TODO skip removed:
	private File convertItem(File itemToConvert) {
		if (itemToConvert.isDirectory()) {

			String tempSubFolder = tempFolder + (tempFolder.endsWith("\\") ? "" : "\\") + itemToConvert.getName();

			// copy root to tmp
			getFileOperations().copyFolder(itemToConvert.getPath(), tempFolder, true);

			// walk tmp/root recursively
			try {
				Files.walk(Paths.get(tempSubFolder)).filter(f -> f.toString().endsWith("flac"))
						.forEach(f -> convertFile(f.toFile(), f.getParent().toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// delete all flac
			try {
				Files.walk(Paths.get(tempSubFolder)).filter(f -> f.toString().endsWith("flac")).forEach(f -> {
					try {
						Files.delete(f);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new File(tempSubFolder);

		} else
			return convertFile(itemToConvert, tempFolder);
	}

	private boolean copyItem(File itemToCopy, String targetFolder, boolean overwrite) {
		if (itemToCopy == null)
			return false;
		if (itemToCopy.isDirectory()) {
			return getFileOperations().copyFolder(itemToCopy.getPath(), targetFolder, overwrite);
		} else {
			return getFileOperations().copyFile(itemToCopy.getParent(), itemToCopy.getName(), targetFolder, overwrite);
		}
	}

}
