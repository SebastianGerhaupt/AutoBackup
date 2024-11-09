import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;

public class App
{
	public static void main(String[] args) throws Exception
	{
		final boolean log = true, newLog = true;
		Logger logger;
		Path path = Paths.get("C:", "Users", "gerha", "Java-Projekte", "AutoBackup", "src");
		if (log)
			logger = new Logger(path, "log.txt", newLog);
		DirectoriesList directories = new DirectoriesList(Paths.get("E:"));
		directories.addSourceDirectories(path, "sources.txt");
		directories.addRecursiveDirectories();
		directories.filterEmptyDirectories();
		directories.filterDuplicateDirectories();
		TreeSet<Directory> filteredDirectories = directories.getFilteredDirectories();
		if (log)
		{
			logger.logDirectoriesToCopy(filteredDirectories, !newLog);
			logger.logFilteredDirectories(directories.getEmptyDirectories(), "Empty directories that will be ignored:");
			logger.logFilteredDirectories(directories.getDuplicateDirectories(), "Duplicate directories that will be copied just once:");
		}
		FilesList files = new FilesList(filteredDirectories);
		files.addSourceFiles();
		files.concatinateTargetFiles();
		if (log)
			logger.logFilesToCopy(files.getConcatinatedFiles());
		files.copyFiles();
		if (log)
		{
			logger.logCreatedDirectories(files.getCreatedDirectories());
			logger.logCopiedFiles(files.getCopiedFiles());
		}
	}
}