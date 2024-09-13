import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;

public class App
{
	public static void main(String[] args) throws Exception
	{
		final boolean log = true;
		Logger logger;
		Path path = Paths.get("C:", "Users", "gerha", "Java-Projekte", "AutoBackup", "src");
		if (log)
			logger = new Logger(path, "log.txt", true);
		DirectoriesList directories = new DirectoriesList(Paths.get("E:"));
		directories.addSourceDirectories(path, "sources.txt");
		directories.addRecursiveDirectories();
		directories.filterEmptyDirectories();
		directories.filterDuplicateDirectories();
		TreeSet<Directory> filteredDirectories = directories.getFilteredDirectories();
		final String lineSeparator = System.lineSeparator();
		final String paragraph = lineSeparator + lineSeparator;
		if (log)
		{
			logger.logDirectoriesToCopy(filteredDirectories, "Directories to copy:");
			logger.logFilteredDirectories(directories.getEmptyDirectories(), paragraph + "Empty directories that will be ignored:");
			logger.logFilteredDirectories(directories.getDuplicateDirectories(), paragraph + "Duplicate directories that will be copied just once:");
		}
		FilesList files = new FilesList(filteredDirectories);
		files.addSourceFiles();
		files.concatinateTargetFiles();
		if (log)
			logger.logFilesToCopy(files.getConcatinatedFiles(), paragraph + "Files to copy:");
		files.copyFiles();
		if (log)
			logger.logCopiedFiles(files.getCopiedFiles(), paragraph + "Copied files:");
	}
}