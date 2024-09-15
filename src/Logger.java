import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.TreeSet;

public class Logger
{
	private String lineSeparator = System.lineSeparator(), paragraph = lineSeparator + lineSeparator;
	private Path logFile;

	public Logger(Path path, String fileName, boolean newLog)
	{
		logFile = Paths.get(path.toString(), fileName);
		if (newLog || !newLog && !Files.exists(logFile))
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

	public void logCopiedFiles(ArrayList<Path> copiedList, boolean isNewParagraph)
	{
		final String description = "Copied files:";
		StringBuilder content = new StringBuilder(isNewParagraph ? paragraph + description : description);
		copiedList.forEach(path -> {
			content.append(lineSeparator + path);
		});
		writeToFile(content);
	}

	public void logCreatedDirectories(TreeSet<Path> createdList, boolean isNewParagraph)
	{
		final String description = "Created directories:";
		StringBuilder content = new StringBuilder(isNewParagraph ? paragraph + description : description);
		createdList.forEach(path -> {
			content.append(lineSeparator + path);
		});
		writeToFile(content);
	}

	public void logDirectoriesToCopy(TreeSet<Directory> filteredDirectories, boolean isNewParagraph)
	{
		final String description = "Directories to copy:";
		StringBuilder content = new StringBuilder(isNewParagraph ? paragraph + description : description);
		filteredDirectories.forEach(directory -> {
			content.append(lineSeparator + directory.getSourcePath() + "," + directory.getIsRecursive() + "->" + directory.getTargetPath());
		});
		writeToFile(content);
	}

	public void logFilesToCopy(ArrayList<MyFile> files, boolean isNewParagraph)
	{
		final String description = "Files to copy:";
		StringBuilder content = new StringBuilder(isNewParagraph ? paragraph + description : description);
		files.forEach(file -> {
			content.append(lineSeparator + file.getSourceFile() + "->" + file.getTargetFile());
		});
		writeToFile(content);
	}

	public void logFilteredDirectories(ArrayList<Directory> directories, boolean isNewParagraph, String description)
	{
		StringBuilder content = new StringBuilder(isNewParagraph ? paragraph + description : description);
		directories.forEach(directory -> {
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