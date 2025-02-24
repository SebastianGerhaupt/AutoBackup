import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.TreeSet;

public class Logger
{
	private String lineSeparator = System.lineSeparator();
	private Path logFile;
	private String paragraph = lineSeparator + lineSeparator;

	public Logger(Path configPath, String fileName, boolean overwriteLog)
	{
		logFile = Paths.get(configPath.toString(), fileName);
		if (!Files.exists(logFile) || overwriteLog)
		{
			try
			{
				Files.deleteIfExists(logFile);
				Files.createFile(logFile);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void logCopiedFiles(ArrayList<Path> copiedFileList)
	{
		StringBuilder content = new StringBuilder(paragraph + "Copied files:");
		copiedFileList.forEach(path -> {
			content.append(lineSeparator + path);
		});
		writeToFile(content);
	}

	public void logCreatedDirectories(TreeSet<Path> createdDirectorySet)
	{
		StringBuilder content = new StringBuilder(paragraph + "Created directories:");
		createdDirectorySet.forEach(path -> {
			content.append(lineSeparator + path);
		});
		writeToFile(content);
	}
	
	public void logDirectoriesToCopy(TreeSet<Directory> filteredDirectorySet, boolean overwriteLog)
	{
		final String description = "Directories to copy:";
		StringBuilder content = new StringBuilder(overwriteLog ? description : paragraph + description);
		filteredDirectorySet.forEach(directory -> {
			content.append(lineSeparator + directory.getSourcePath() + "," + directory.getIsRecursive() + "->" + directory.getTargetPath());
		});
		writeToFile(content);
	}

	public void logFilesToCopy(ArrayList<MyFile> concatinatedFileList)
	{
		StringBuilder content = new StringBuilder(paragraph + "Files to copy:");
		concatinatedFileList.forEach(file -> {
			content.append(lineSeparator + file.getSourceFile() + "->" + file.getTargetFile());
		});
		writeToFile(content);
	}

	public void logFilteredDirectories(ArrayList<Directory> filteredDirectoryList, String description)
	{
		StringBuilder content = new StringBuilder(paragraph + description);
		filteredDirectoryList.forEach(directory -> {
			content.append(lineSeparator + directory.getSourcePath());
		});
		writeToFile(content);
	}
	
	private void writeToFile(StringBuilder content)
	{
		try
		{
			Files.write(logFile, content.toString().getBytes(), StandardOpenOption.APPEND);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}