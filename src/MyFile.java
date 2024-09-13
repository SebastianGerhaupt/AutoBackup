import java.nio.file.Path;

public class MyFile
{
	private Path sourceFile, targetFile;
	
	public MyFile(Path sourceFile, Path targetFile)
	{
		this.sourceFile = sourceFile;
		this.targetFile = targetFile;
	}

	public Path getSourceFile()
	{
		return sourceFile;
	}

	public Path getTargetFile()
	{
		return targetFile;
	}
}