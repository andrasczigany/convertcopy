package convertcopy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AudioConverter {

	public String convertFlacToMp3(String sourceFolder, String sourceFileName, String targetFolder, int bitRate, int samplingRate) {

		StringBuilder sourceFileNameWithPath = new StringBuilder();
		sourceFileNameWithPath.append(sourceFolder);
		if (!targetFolder.endsWith("\\")) sourceFileNameWithPath.append("\\");
		sourceFileNameWithPath.append(sourceFileName);
		
		FileOperations fileOperations = new FileOperations();
		if (!fileOperations.existsAndReadable(sourceFileNameWithPath.toString()) || !"flac".equals(fileOperations.getFileType(sourceFileNameWithPath.toString()))) 
			return null;

		StringBuilder targetFileNameWithPath = new StringBuilder();
		targetFileNameWithPath.append(targetFolder);
		if (!targetFolder.endsWith("\\")) targetFileNameWithPath.append("\\");
		targetFileNameWithPath.append(sourceFileName.replace(".flac", ".mp3"));
		
		try {
			Files.deleteIfExists(Paths.get(targetFileNameWithPath.toString()));
		} catch (IOException e) {
			System.out.println("Error when removing already existing file.");
			e.printStackTrace();
		}

		StringBuilder commandString = new StringBuilder();
		commandString.append("D:\\prog\\convertcopy\\lib\\ffmpeg.exe -i ");
		commandString.append("\"" + sourceFileNameWithPath.toString() + "\"");
		commandString.append(" -vn -acodec libmp3lame -ab ");
		commandString.append(bitRate);
		commandString.append(" -ac 2 -ar ");
		commandString.append(samplingRate);
		commandString.append(" -vol 256 -f mp3 -y ");
		commandString.append("\"" + targetFileNameWithPath.toString() + "\"");
		
		
		if (executeCommand(commandString.toString()) == 0) {
			if (fileOperations.existsAndReadable(targetFileNameWithPath.toString())) {
				System.out.println("Conversion completed successfully, see result file: " + targetFileNameWithPath.toString());
				return targetFileNameWithPath.toString();
			}
		}			
		System.out.println("Error when executing convert command.");
		return null;
	}
	
	private int executeCommand(String command) {
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec(command);
			return process.waitFor();
		} catch (IOException | InterruptedException e) {
			System.out.println("Error when executing command: " + command);
			e.printStackTrace();
		}
		return -1;
	}

	public static void main (String[] args) {
		AudioConverter ac = new AudioConverter();
		ac.convertFlacToMp3("d:\\prog\\convertcopy\\testfiles\\", "test.flac", "d:\\", 320000, 48000);
	}
}
