import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.TreeSet;

public class Logger{
	private String logFile;
	private boolean append;

	public Logger(String logFile, boolean append){
		this.logFile=logFile;
		this.append=append;
	}

	public void logDirectoriesToCopy(TreeSet<Directory> directories){
		try{
			FileWriter writer=new FileWriter(logFile, append);
			writer.write("Directories to copy:"+System.lineSeparator());
			directories.forEach(directory->{
				try{
					writer.write(directory.getSource()+","+directory.isRecursive()+"->"+directory.getTarget()+System.lineSeparator());
				}
				catch(Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			writer.close();
		}
		catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void logFilteredDirectories(ArrayList<String> directories, String description){
		try{
			FileWriter writer=new FileWriter(logFile, append); 
			writer.write(System.lineSeparator()+description+System.lineSeparator());
			directories.forEach(string->{
				try{
					writer.write(string+System.lineSeparator());
				}
				catch(Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			writer.close();
		}
		catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void logFilesToCopy(ArrayList<MyFile> files){
		try{
			FileWriter writer=new FileWriter(logFile, append); 
			writer.write(System.lineSeparator()+"Files to copy:"+System.lineSeparator());
			files.forEach(file->{
				try{
					writer.write(file.getSource().toString()+"->"+file.getTarget().toString()+System.lineSeparator());
				}
				catch(Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			writer.close();
		}
		catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void logCreatedDirectories(TreeSet<Path> createdList){
		try{
			FileWriter writer=new FileWriter(logFile, append); 
			writer.write(System.lineSeparator()+"Created directories:"+System.lineSeparator());
			createdList.forEach(path->{
				try{
					writer.write(path.toString()+System.lineSeparator());
				}
				catch(Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			writer.close();
		}
		catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void logCopiedFiles(ArrayList<Path> copiedList){
		try{
			FileWriter writer=new FileWriter(logFile, append); 
			writer.write(System.lineSeparator()+"Copied files:"+System.lineSeparator());
			copiedList.forEach(path->{
				try{
					writer.write(path.toString()+System.lineSeparator());
				}
				catch(Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			writer.close();
		}
		catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}