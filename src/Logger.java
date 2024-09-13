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

	public void logCopiedFiles(ArrayList<Path> copiedList, String content)
	{
		copiedList.forEach(path -> {
			content.concat(lineSeparator + path);
		});
		writeToFile(content);
	}

	public void logDirectoriesToCopy(TreeSet<Directory> filteredDirectories, String content)
	{
		filteredDirectories.forEach(directory -> {
			content.concat(lineSeparator + directory.getSourcePath() + "," + directory.getIsRecursive() + "->" + directory.getTargetPath());
		});
		writeToFile(content);
	}

	public void logFilesToCopy(ArrayList<MyFile> files, String content)
	{
		files.forEach(file -> {
			content.concat(lineSeparator + file.getSourceFile() + "->" + file.getTargetFile());
		});
		writeToFile(content);
	}

	public void logFilteredDirectories(ArrayList<Directory> directories, String content)
	{
		directories.forEach(directory -> {
			content.concat(lineSeparator + directory);
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

	/*public void logCreatedDirectories(TreeSet<Path> createdList, String content/*"Created directories:"*)
	{
		createdList.forEach(path -> {
			content.concat(lineSeparator + path);
		});
		writeToFile(content);
	}*/
}