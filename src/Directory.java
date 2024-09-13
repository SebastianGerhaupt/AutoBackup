import java.nio.file.Path;

public class Directory implements Comparable<Directory>
{
	private boolean isRecursive;
	private Path sourcePath;
	private String targetPath;
	
	public Directory(Path sourcePath, boolean isRecursive, Path targetPath)
	{
		this.sourcePath = sourcePath;
		this.isRecursive = isRecursive;
		this.targetPath = targetPath.toString();
	}

	@Override
	public int compareTo(Directory directory)
	{
		return sourcePath.compareTo(directory.getSourcePath());
	}

	public boolean getIsRecursive()
	{
		return isRecursive;
	}

	public Path getSourcePath()
	{
		return sourcePath;
	}

	public String getTargetPath()
	{
		return targetPath;
	}
}