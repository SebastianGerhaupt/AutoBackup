import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DirectoryList{
	private String targetDirectory;
	private ArrayList<Directory> sourceList=new ArrayList<Directory>(0);
	private ArrayList<String> emptyList=new ArrayList<String>(0), duplicateList=new ArrayList<String>(0);
	private TreeSet<Directory> filteredList=new TreeSet<Directory>();

	public DirectoryList(String targetDirectory){
		this.targetDirectory=targetDirectory;
	}

	public void addSourceDirectories(String filePath){
		File file=new File(filePath);
		try{
			Scanner scanner=new Scanner(file).useDelimiter(",|\\R");
			while(scanner.hasNextLine()){
				String source=scanner.next();
				boolean recursive=Boolean.parseBoolean(scanner.next());
				sourceList.add(new Directory(source, recursive, targetDirectory+"\\"+source.replace(":", "")));
			}
			scanner.close();
		}
		catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addRecursiveDirectories(){
		ArrayList<Path> recursiveList=new ArrayList<Path>(0);
		sourceList.forEach(directory->{
			if(directory.isRecursive()){
				try{
					recursiveList.addAll(Files.walk(Paths.get(directory.getSource()), Integer.MAX_VALUE).filter(Files::isDirectory).sorted().collect(Collectors.toList()));
				}
				catch(Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				recursiveList.remove(Paths.get(directory.getSource()));
			}
		});
		recursiveList.forEach(path->{
			String source=path.toString();
			sourceList.add(new Directory(source, true, targetDirectory+"\\"+source.replace(":", "")));
		});
	}

	public void filterEmptyDirectories(){
		Iterator<Directory> iterator=sourceList.iterator();
		while(iterator.hasNext()){
			final String source=iterator.next().getSource();
			try{
				if(Files.list(Paths.get(source)).count()==0){
					emptyList.add(source);
					iterator.remove();
				}
			}
			catch(Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void filterDoubledDirectories(){
		sourceList.forEach(directory->{
			if(!filteredList.add(directory)) duplicateList.add(directory.getSource());
		});
	}

	public ArrayList<Directory> getSourceList(){
		return sourceList;
	}

	public ArrayList<String> getEmptyList(){
		return emptyList;
	}

	public ArrayList<String> getDuplicateList(){
		return duplicateList;
	}

	public TreeSet<Directory> getFilteredList(){
		return filteredList;
	}
}