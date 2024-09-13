import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DirectoriesList
{
	private ArrayList<Directory> duplicateDirectories = new ArrayList<Directory>(0),  emptyDirectories = new ArrayList<Directory>(0), sourceDirectories = new ArrayList<Directory>(0);
	private TreeSet<Directory> filteredDirectories = new TreeSet<Directory>();
	private String targetPath;
	
	public DirectoriesList(Path path)
	{
		targetPath = path.toString();
	}

	public void addRecursiveDirectories()
	{
		ArrayList<Path> recursiveDirectories = new ArrayList<Path>(0);
		sourceDirectories.forEach(directory -> {
			if (directory.getIsRecursive())
			{
				try
				{
					recursiveDirectories.addAll(Files.walk(directory.getSourcePath(), Integer.MAX_VALUE).filter(Files::isDirectory).sorted().collect(Collectors.toList()));
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				recursiveDirectories.remove(directory.getSourcePath());
			}
		});
		recursiveDirectories.forEach(directory -> {
			sourceDirectories.add(new Directory(directory, true, Paths.get(targetPath, directory.toString().replace(":", ""))));
		});
	}

	public void addSourceDirectories(Path path, String fileName)
	{
			try (Scanner scanner = new Scanner(Paths.get(path.toString(), fileName)).useDelimiter(",|\\R"))
			{
				while (scanner.hasNextLine())
				{
					final String directory = scanner.next().replace(":", "");
					final boolean isRecursive = Boolean.parseBoolean(scanner.next());
					sourceDirectories.add(new Directory(Paths.get(directory), isRecursive, Paths.get(targetPath, directory)));
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
		sourceDirectories.forEach(directory -> {
			if (!filteredDirectories.add(directory))
				duplicateDirectories.add(directory);
		});
	}

	public void filterEmptyDirectories()
	{
		Iterator<Directory> iterator = sourceDirectories.iterator();
		while (iterator.hasNext())
		{
			final Directory directory = iterator.next();
			try
			{
				if (Files.list(directory.getSourcePath()).count() == 0)
				{
					emptyDirectories.add(directory);
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

	public ArrayList<Directory> getDuplicateDirectories()
	{
		return duplicateDirectories;
	}

	public ArrayList<Directory> getEmptyDirectories()
	{
		return emptyDirectories;
	}

	public TreeSet<Directory> getFilteredDirectories()
	{
		return filteredDirectories;
	}
}