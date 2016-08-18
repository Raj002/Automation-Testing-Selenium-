package Practice;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Delete_File 
{
	public static void main(String[] args) 
	{
		try
		{
			DeleteFiles();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	static void DeleteFiles() throws IOException 
	{
        
        File file = new File("C:\\Users\\Rajkumar\\Desktop\\New folder\\");
        FileUtils.deleteDirectory(file);
        
        // FileUtils.cleanDirectory(file);

       /* String[] myFiles;		        
        if (file.isDirectory())
        {	        	
            myFiles = file.list();
            System.out.println(file.getPath());
            for (int i = 0; i < myFiles.length; i++)
            {
                File myFile = new File(file, myFiles[i]);

                myFile.delete();
            }
            System.out.println("Files deleted successfully");
        }
        else if(!file.isDirectory())
        {
        	System.out.println("Folder is empty");
        }*/
	}
}
