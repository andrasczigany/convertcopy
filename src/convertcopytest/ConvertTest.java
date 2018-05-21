package convertcopytest;

import static org.junit.Assert.*;

import org.junit.Test;

import convertcopy.AudioConverter;
import convertcopy.FileOperations;

public class ConvertTest {

	@Test
	public void testConvertFlacToMp3WithoutErrorCreatesMp3() {
		AudioConverter audioConverter = new AudioConverter();
		String sourceFolder = "d:\\prog\\convertcopy\\testfiles\\";
		String sourceFile = "testFormat.flac";
		String targetFolder = "d:\\prog\\convertcopy\\testfiles\\results";
		String resultFileNameWithPath = audioConverter.convertFlacToMp3(sourceFolder, sourceFile, targetFolder, 320000, 48000);

		FileOperations fileOperations = new FileOperations();
		assertEquals("mp3", fileOperations.getFileType(resultFileNameWithPath));			
	}
	
	@Test
	public void testConvertFlacToMp3WithoutErrorCreatesMp3WithCorrectBitRate() {
		AudioConverter audioConverter = new AudioConverter();
		String sourceFolder = "d:\\prog\\convertcopy\\testfiles\\";
		String sourceFile = "testFormat.flac";
		String targetFolder = "d:\\prog\\convertcopy\\testfiles\\results";
		String resultFileNameWithPath = audioConverter.convertFlacToMp3(sourceFolder, sourceFile, targetFolder, 320000, 48000);

		FileOperations fileOperations = new FileOperations();
		assertEquals(fileOperations.getBitRate(resultFileNameWithPath).intValue(), 320);			
	}
	
	@Test
	public void testConvertFlacToMp3WithoutErrorCreatesMp3WithCorrectSampleRate() {
		AudioConverter audioConverter = new AudioConverter();
		String sourceFolder = "d:\\prog\\convertcopy\\testfiles\\";
		String sourceFile = "testFormat.flac";
		String targetFolder = "d:\\prog\\convertcopy\\testfiles\\results";
		String resultFileNameWithPath = audioConverter.convertFlacToMp3(sourceFolder, sourceFile, targetFolder, 320000, 48000);

		FileOperations fileOperations = new FileOperations();
		assertEquals(fileOperations.getSampleRate(resultFileNameWithPath).intValue(), 48000);			
	}
	
	@Test
	public void testConvertFlacToMp3WithoutErrorCreatesMp3WithCorrectPath() {
		AudioConverter audioConverter = new AudioConverter();
		String sourceFolder = "d:\\prog\\convertcopy\\testfiles\\";
		String sourceFile = "testPath.flac";
		String targetFolder = "d:\\prog\\convertcopy\\testfiles\\results";
		String resultFileNameWithPath = audioConverter.convertFlacToMp3(sourceFolder, sourceFile, targetFolder, 320000, 48000);

		assertEquals(resultFileNameWithPath, targetFolder + "\\" + "testPath.mp3");			
	}
	
	@Test
	public void testConvertFlacToMp3WithSpaceInPathWithoutErrorCreatesMp3WithCorrectPath() {
		AudioConverter audioConverter = new AudioConverter();
		String sourceFolder = "d:\\prog\\convertcopy\\testfiles\\test flac";
		String sourceFile = "test 1.flac";
		String targetFolder = "d:\\prog\\convertcopy\\testfiles\\results\\result flac";
		String resultFileNameWithPath = audioConverter.convertFlacToMp3(sourceFolder, sourceFile, targetFolder, 320000, 48000);

		assertEquals(resultFileNameWithPath, targetFolder + "\\" + "test 1.mp3");			
	}
	
	@Test
	public void testConvertFlacToMp3WithNotFlac() {
		AudioConverter audioConverter = new AudioConverter();
		String sourceFolder = "d:\\prog\\convertcopy\\testfiles\\";
		String sourceFile = "test.mp3";
		String targetFolder = "d:\\prog\\convertcopy\\testfiles\\results";
		assertNull(audioConverter.convertFlacToMp3(sourceFolder, sourceFile, targetFolder, 320000, 48000));
	}
	
}
