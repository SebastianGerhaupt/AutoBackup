// TODO Filter for links (.lnk)

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FilesList
{
	private ArrayList<MyFile> concatinatedFiles = new ArrayList<MyFile>(0);
	private ArrayList<Path> copiedFiles = new ArrayList<Path>(0), sourceFiles = new ArrayList<Path>(0);
	private TreeSet<Directory> filteredDirectories;

	public FilesList(TreeSet<Directory> directories)
	{
		filteredDirectories = directories;
	}

	public void addSourceFiles()
	{
		filteredDirectories.forEach(directory -> {
			try
			{
				sourceFiles.addAll(Files.list(Paths.get(directory.getSourcePath().toString())).filter(Files::isRegularFile).sorted().collect(Collectors.toList()));
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	public void concatinateTargetFiles()
	{
		sourceFiles.forEach(file -> {
			filteredDirectories.forEach(directory -> {
				if (file.getParent().equals(directory.getSourcePath()))
					concatinatedFiles.add(new MyFile(file, Paths.get(directory.getTargetPath(), file.toString())));
			});
		});
	}

	public void copyFiles()
	{
		final int total = concatinatedFiles.size();
		System.out.println();
		concatinatedFiles.forEach(file -> {
			try
			{
				copiedFiles.add(Files.copy(file.getSourceFile(), file.getTargetFile()));
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print(copiedFiles.size() + " von " + total);
		});
	}

	public ArrayList<MyFile> getConcatinatedFiles()
	{
		return concatinatedFiles;
	}

	public ArrayList<Path> getCopiedFiles()
	{
		return copiedFiles;
	}
}