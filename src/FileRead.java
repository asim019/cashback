import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class FileRead {
	public static void main(String rgs[]){
		try {
			FileInputStream fis = new FileInputStream(new File("asim.txt"));
		//	fis.
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
