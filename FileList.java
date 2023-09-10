// TODO Filter for links (.lnk)

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FileList{
	private TreeSet<Directory> directories;
	private ArrayList<Path> sourceList=new ArrayList<Path>(0), copiedList=new ArrayList<Path>(0);
	private ArrayList<MyFile> concatinatedList=new ArrayList<MyFile>(0);

	public FileList(TreeSet<Directory> directories){
		this.directories=directories;
	}

	public void addSourceFiles(){
		directories.forEach(directory->{
			try{
				sourceList.addAll(Files.list(Paths.get(directory.getSource())).filter(Files::isRegularFile).sorted().collect(Collectors.toList()));
			}
			catch(Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	public void concatinateTargets(){
		sourceList.forEach(path->{
			directories.forEach(directory->{
				if(path.getParent().toString().equals(directory.getSource())) concatinatedList.add(new MyFile(path, Paths.get(directory.getTarget()+"\\"+path.getFileName().toString())));
			});
		});
	}

	public void copyFiles(){
		final int total=concatinatedList.size();
		concatinatedList.forEach(file->{
			Path target=file.getTarget();
			try{
				copiedList.add(Files.copy(file.getSource(), target));
			}
			catch(Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print("\r"+copiedList.size()+" von "+total);
		});
	}

	public ArrayList<MyFile> getConcatinatedList(){
		return concatinatedList;
	}

	public ArrayList<Path> getCopiedList(){
		return copiedList;
	}
}