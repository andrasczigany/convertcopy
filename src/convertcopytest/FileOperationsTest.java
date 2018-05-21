package convertcopytest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import convertcopy.FileOperations;

public class FileOperationsTest {

	@Test
	public void checkFileWithNull() {
		FileOperations fileOperations = new FileOperations();
		assertFalse(fileOperations.existsAndReadable(null));			
	}

	@Test
	public void checkFileWithEmptyString() {
		FileOperations fileOperations = new FileOperations();
		String fileNameWithPath = "";
		assertFalse(fileOperations.existsAndReadable(fileNameWithPath));			
	}

	@Test
	public void checkFileWithNonExistingFile() {
		FileOperations fileOperations = new FileOperations();
		String fileNameWithPath = "X:\\nosuchfile.txt";
		assertFalse(fileOperations.existsAndReadable(fileNameWithPath));			
	}

	@Test
	public void checkFileWithRootAccessRight() {
		FileOperations fileOperations = new FileOperations();
		String fileNameWithPath = "D:\\prog\\convertcopy\\testfiles\\unreadable.txt";
		assertFalse(fileOperations.existsAndReadable(fileNameWithPath));			
	}

	@Test
	public void fileLengthWithEmptyFile() {
		FileOperations fileOperations = new FileOperations();
		String fileNameWithPath = "D:\\prog\\convertcopy\\testfiles\\empty.flac";
		assertEquals(fileOperations.getFileSize(fileNameWithPath), 0);			
	}

	@Test
	public void checkFileWithReadableFile() {
		FileOperations fileOperations = new FileOperations();
		String fileNameWithPath = "D:\\prog\\convertcopy\\testfiles\\readable.txt";
		assertTrue(fileOperations.existsAndReadable(fileNameWithPath));			
	}

	@Test
	public void checkFileTypeForFlac() {
		FileOperations fileOperations = new FileOperations();
		String fileNameWithPath = "d:\\prog\\convertcopy\\testfiles\\testFormat.flac";
		assertEquals("flac", fileOperations.getFileType(fileNameWithPath));			
	}

	@Test
	public void checkFileTypeWithEmptyFile() {
		FileOperations fileOperations = new FileOperations();
		String fileNameWithPath = "d:\\prog\\convertcopy\\testfiles\\empty.flac";
		assertNull(fileOperations.getFileType(fileNameWithPath));			
	}

	@Test
	public void checkFileTypeForMp3() {
		FileOperations fileOperations = new FileOperations();
		String fileNameWithPath = "d:\\prog\\convertcopy\\testfiles\\test.mp3";
		assertEquals("mp3", fileOperations.getFileType(fileNameWithPath));			
	}

	@Test
	public void copyFileToExistingFolderWithoutError() {
		FileOperations fileOperations = new FileOperations();
		String sourceFileFolder = "d:\\prog\\convertcopy\\testfiles\\";
		String sourceFileName = "copy.txt";
		String targetFolder = "d:\\prog\\convertcopy\\testfiles\\results";
		boolean overwrite = true;
		
		if (fileOperations.copyFile(sourceFileFolder, sourceFileName, targetFolder, overwrite))
			assertTrue(fileOperations.existsAndReadable("d:\\prog\\convertcopy\\testfiles\\results\\copy.txt"));
		else
			throw new AssertionError();
	}

	@Test
	public void copyFileToNonExistingFolderWithoutError() {
		FileOperations fileOperations = new FileOperations();
		String sourceFileFolder = "d:\\prog\\convertcopy\\testfiles\\";
		String sourceFileName = "copy.txt";
		String targetFolder = "d:\\prog\\convertcopy\\testfiles\\results\\newfolder";
		boolean overwrite = true;
		
		if (fileOperations.copyFile(sourceFileFolder, sourceFileName, targetFolder, overwrite)) {
			boolean targetFileOkay = fileOperations.existsAndReadable("d:\\prog\\convertcopy\\testfiles\\results\\newfolder\\copy.txt");
			try {
				Files.delete(Paths.get("d:\\prog\\convertcopy\\testfiles\\results\\newfolder\\copy.txt"));
				Files.delete(Paths.get("d:\\prog\\convertcopy\\testfiles\\results\\newfolder"));
			} catch (IOException e) {
				e.printStackTrace();
				throw new AssertionError();
			}
			assertTrue(targetFileOkay);
		} else
			throw new AssertionError();
	}

	@Test
	public void copyFileFailToExistingWithoutOverwrite() {
		FileOperations fileOperations = new FileOperations();
		String sourceFileFolder = "d:\\prog\\convertcopy\\testfiles\\";
		String sourceFileName = "copyExisting.txt";
		String targetFolder = "d:\\prog\\convertcopy\\testfiles\\results";
		boolean overwrite = false;
		
		assertFalse(fileOperations.copyFile(sourceFileFolder, sourceFileName, targetFolder, overwrite));
	}

	@Test
	public void copySimpleFolderWithoutError() {
		FileOperations fileOperations = new FileOperations();
		String sourceFileFolder = "d:\\prog\\convertcopy\\testfiles\\testfoldersimple";
		String targetFolder = "d:\\prog\\convertcopy\\testfiles\\results";
		boolean overwrite = true;
		
		assertTrue(fileOperations.copyFolder(sourceFileFolder, targetFolder, overwrite));
	}

	@Test
	public void copyHierarchicFolderWithoutError() {
		FileOperations fileOperations = new FileOperations();
		String sourceFileFolder = "d:\\prog\\convertcopy\\testfiles\\testfolder";
		String targetFolder = "d:\\prog\\convertcopy\\testfiles\\results";
		boolean overwrite = true;
		
		assertTrue(fileOperations.copyFolder(sourceFileFolder, targetFolder, overwrite));
	}

	@Test
	public void copyFolderFailWithoutOverWrite() {
		FileOperations fileOperations = new FileOperations();
		String sourceFileFolder = "d:\\prog\\convertcopy\\testfiles\\testfolderexisting";
		String targetFolder = "d:\\prog\\convertcopy\\testfiles\\results";
		boolean overwrite = false;
		
		assertFalse(fileOperations.copyFolder(sourceFileFolder, targetFolder, overwrite));
	}

	@Test
	public void copySimpleFolderToOtherDriveWithoutError() {
		FileOperations fileOperations = new FileOperations();
		String sourceFileFolder = "d:\\prog\\convertcopy\\testfiles\\testfoldersimple";
		String targetFolder = "c:\\tmp";
		boolean overwrite = true;
		
		assertTrue(fileOperations.copyFolder(sourceFileFolder, targetFolder, overwrite));
	}

	@Test
	public void copyHierarchicFolderToOtherDriveWithoutError() {
		FileOperations fileOperations = new FileOperations();
		String sourceFileFolder = "d:\\prog\\convertcopy\\testfiles\\testfolder";
		String targetFolder = "c:\\tmp";
		boolean overwrite = true;
		
		assertTrue(fileOperations.copyFolder(sourceFileFolder, targetFolder, overwrite));
	}

	@Test
	public void copyFolderShouldFailWhenSourceFolderNotFound() {
		FileOperations fileOperations = new FileOperations();
		String sourceFileFolder = "x:\\notexisting";
		String targetFolder = "c:\\tmp";
		boolean overwrite = true;
		
		assertFalse(fileOperations.copyFolder(sourceFileFolder, targetFolder, overwrite));
	}

	@Test
	public void copyFolderShouldFailWhenTargetFolderNotFound() {
		FileOperations fileOperations = new FileOperations();
		String sourceFileFolder = "c:\\java";
		String targetFolder = "y:\\\\notexisting";
		boolean overwrite = true;
		
		assertFalse(fileOperations.copyFolder(sourceFileFolder, targetFolder, overwrite));
	}

}
