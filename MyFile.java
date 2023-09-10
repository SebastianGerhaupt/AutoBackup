import java.nio.file.Path;

public class MyFile{
	private Path source, target;
	
	public MyFile(Path source, Path target){
		this.source=source;
		this.target=target;
	}

	public Path getSource(){
		return source;
	}

	public Path getTarget(){
		return target;
	}
}