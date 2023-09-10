import java.util.TreeSet;

public class App{
	public static void main(String[] args) throws Exception{
		final boolean log=true;
		Logger logger=new Logger("C:\\Users\\gerha\\Documents\\Java\\AutoBackup\\src\\log.txt", true);
		DirectoryList directoryList=new DirectoryList("E:");
		directoryList.addSourceDirectories("C:\\Users\\gerha\\Documents\\Java\\AutoBackup\\src\\sources.txt");
		directoryList.addRecursiveDirectories();
		directoryList.filterEmptyDirectories();
		directoryList.filterDoubledDirectories();
		TreeSet<Directory> filteredList=directoryList.getFilteredList();
		if(log){
			logger.logDirectoriesToCopy(filteredList);
			logger.logFilteredDirectories(directoryList.getEmptyList(), "Empty directories that will be ignored:");
			logger.logFilteredDirectories(directoryList.getDuplicateList(), "Duplicate directories that will be copied just once:");
		}
		FileList fileList=new FileList(filteredList);
		fileList.addSourceFiles();
		fileList.concatinateTargets();
		if(log) logger.logFilesToCopy(fileList.getConcatinatedList());
		fileList.copyFiles();
		if(log) logger.logCopiedFiles(fileList.getCopiedList());
	}
}