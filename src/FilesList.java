// TODO Filter for links (.lnk)

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FilesList
{
	private ArrayList<MyFile> concatinatedFileList = new ArrayList<MyFile>(0);
	private ArrayList<Path> copiedFileList = new ArrayList<Path>(0), sourceFileList = new ArrayList<Path>(0);
	private TreeSet<Path> createdDirectorySet = new TreeSet<Path>();
	private TreeSet<Directory> filteredDirectorySet;

	public FilesList(TreeSet<Directory> filteredDirectorySet)
	{
		this.filteredDirectorySet = filteredDirectorySet;
	}
	
	public void addSourceFiles()
	{
		filteredDirectorySet.forEach(directory -> {
			try
			{
				sourceFileList.addAll(Files.list(Paths.get(directory.getSourcePath().toString())).filter(Files::isRegularFile).sorted().collect(Collectors.toList()));
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
		sourceFileList.forEach(file -> {
			filteredDirectorySet.forEach(directory -> {
				if (file.getParent().equals(directory.getSourcePath()))
					concatinatedFileList.add(new MyFile(file, Paths.get(directory.getTargetPath(), file.getFileName().toString())));
			});
		});
	}

	public void copyFiles()
	{
		final int total = concatinatedFileList.size();
		concatinatedFileList.forEach(file -> {
			try
			{
				createdDirectorySet.add(Files.createDirectories(file.getTargetFile().getParent()));
				copiedFileList.add(Files.copy(file.getSourceFile(), file.getTargetFile(), StandardCopyOption.REPLACE_EXISTING));
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print("\r" + copiedFileList.size() + " von " + total);
		});
		System.out.print(System.lineSeparator());
	}
	
	public ArrayList<MyFile> getConcatinatedFileList()
	{
		return concatinatedFileList;
	}

	public ArrayList<Path> getCopiedFileList()
	{
		return copiedFileList;
	}
	
	public TreeSet<Path> getCreatedDirectorySet()
	{
		return createdDirectorySet;
	}
}