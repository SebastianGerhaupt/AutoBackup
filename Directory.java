public class Directory implements Comparable<Directory>{
	private String source, target;
	private boolean recursive;

	public Directory(String source, boolean recursive, String target){
		this.source=source;
		this.recursive=recursive;
		this.target=target;
	}

	@Override
	public int compareTo(Directory directory){
		return this.source.compareTo(directory.source);
	}

	public String getSource(){
		return source;
	}

	public boolean isRecursive(){
		return recursive;
	}

	public String getTarget(){
		return target;
	}
}