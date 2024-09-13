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
	private StandardOpenOption option;

	public Logger(Path path, String fileName, boolean append)
	{
		logFile = Paths.get(path.toString(), fileName);
		if (append)
			option = StandardOpenOption.APPEND;
	}

	public void logCopiedFiles(ArrayList<Path> copiedList)
	{
		String content = lineSeparator + "Copied files:" + lineSeparator;
		copiedList.forEach(path -> {
			content.concat(path + lineSeparator);
		});
		writeToFile(content);
	}

	public void logDirectoriesToCopy(TreeSet<Directory> filteredDirectories)
	{
		String content = "Directories to copy:" + lineSeparator;
		filteredDirectories.forEach(directory -> {
			content.concat(directory.getSourcePath() + "," + directory.getIsRecursive() + "->" + directory.getTargetPath() + lineSeparator);
		});
		writeToFile(content);
	}

	public void logFilesToCopy(ArrayList<MyFile> files)
	{
		String content = lineSeparator + "Files to copy:" + lineSeparator;
		files.forEach(file -> {
			content.concat(file.getSourceFile() + "->" + file.getTargetFile() + lineSeparator);
		});
		writeToFile(content);
	}

	public void logFilteredDirectories(ArrayList<Directory> directories, String description)
	{
		String content = lineSeparator + description + lineSeparator;
		directories.forEach(directory -> {
			content.concat(directory + lineSeparator);
		});
		writeToFile(content);
	}

	private void writeToFile(String content)
	{
		try
		{
			Files.write(logFile, content.getBytes(), option);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*public void logCreatedDirectories(TreeSet<Path> createdList)
	{
		String content = lineSeparator + "Created directories:" + lineSeparator;
		createdList.forEach(path -> {
			content.concat(path + lineSeparator);
		});
		writeToFile(content);
	}*/
}