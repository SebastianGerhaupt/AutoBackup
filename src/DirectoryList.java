import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DirectoryList
{
	private ArrayList<Directory> duplicateDirectoryList = new ArrayList<Directory>(0);
	private ArrayList<Directory> emptyDirectoryList = new ArrayList<Directory>(0);
	private TreeSet<Directory> filteredDirectorySet = new TreeSet<Directory>();
	private ArrayList<Directory> sourceDirectoryList = new ArrayList<Directory>(0);
	private String targetPath;
	
	public DirectoryList(Path targetPath)
	{
		this.targetPath = targetPath.toString();
	}

	public void addRecursiveDirectories()
	{
		ArrayList<Path> recursiveDirectoryList = new ArrayList<Path>(0);
		sourceDirectoryList.forEach(directory -> {
			if (directory.getIsRecursive())
			{
				try
				{
					recursiveDirectoryList.addAll(Files.walk(directory.getSourcePath(), Integer.MAX_VALUE).filter(Files::isDirectory).sorted().collect(Collectors.toList()));
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				recursiveDirectoryList.remove(directory.getSourcePath());
			}
		});
		recursiveDirectoryList.forEach(directory -> {
			sourceDirectoryList.add(new Directory(directory, true, Paths.get(targetPath, directory.toString().replace(":", ""))));
		});
	}
	
	public void addSourceDirectories(Path configPath, String fileName)
	{
		try (Scanner scanner = new Scanner(Paths.get(configPath.toString(), fileName)).useDelimiter(",|\\R"))
		{
			while (scanner.hasNextLine())
			{
				final String directory = scanner.next();
				sourceDirectoryList.add(new Directory(Paths.get(directory), Boolean.parseBoolean(scanner.next()), Paths.get(targetPath, directory.replace(":", ""))));
			}
			scanner.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void filterDuplicateDirectories()
	{
		sourceDirectoryList.forEach(directory -> {
			if (!filteredDirectorySet.add(directory))
				duplicateDirectoryList.add(directory);
		});
	}
	
	public void filterEmptyDirectories()
	{
		Iterator<Directory> iterator = sourceDirectoryList.iterator();
		while (iterator.hasNext())
		{
			final Directory directory = iterator.next();
			try
			{
				if (Files.list(directory.getSourcePath()).count() == 0)
				{
					emptyDirectoryList.add(directory);
					iterator.remove();
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Directory> getDuplicateDirectoryList()
	{
		return duplicateDirectoryList;
	}

	public ArrayList<Directory> getEmptyDirectoryList()
	{
		return emptyDirectoryList;
	}
	
	public TreeSet<Directory> getFilteredDirectorySet()
	{
		return filteredDirectorySet;
	}
}