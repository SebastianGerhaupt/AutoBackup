import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;

public class App
{
	public static void main(String[] args) throws Exception
	{
		final boolean log = true;
		final boolean overwriteLog = true;
		Logger logger;
		final Path configPath = Paths.get("C:", "Users", "gerha", "Java-Projekte", "AutoBackup", "src");
		if (log)
			logger = new Logger(configPath, "log.txt", overwriteLog);
		DirectoryList directoryList = new DirectoryList(Paths.get("E:"));
		directoryList.addSourceDirectories(configPath, "sources.txt");
		directoryList.addRecursiveDirectories();
		directoryList.filterEmptyDirectories();
		directoryList.filterDuplicateDirectories();
		TreeSet<Directory> filteredDirectorySet = directoryList.getFilteredDirectorySet();
		if (log)
		{
			logger.logDirectoriesToCopy(filteredDirectorySet, overwriteLog);
			logger.logFilteredDirectories(directoryList.getEmptyDirectoryList(), "Empty directories that will be ignored:");
			logger.logFilteredDirectories(directoryList.getDuplicateDirectoryList(), "Duplicate directories that will be copied just once:");
		}
		FilesList fileList = new FilesList(filteredDirectorySet);
		fileList.addSourceFiles();
		fileList.concatinateTargetFiles();
		if (log)
			logger.logFilesToCopy(fileList.getConcatinatedFileList());
		fileList.copyFiles();
		if (log)
		{
			logger.logCreatedDirectories(fileList.getCreatedDirectorySet());
			logger.logCopiedFiles(fileList.getCopiedFileList());
		}
	}
}