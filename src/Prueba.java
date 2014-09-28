import javax.swing.JOptionPane;
import java.io.*;
import java.lang.reflect.*;
public class Prueba{

   public  static void write(String file, SGSnake par){
      try{
			  FileOutputStream w = new FileOutputStream(file);
			  ObjectOutputStream o = new ObjectOutputStream(w);
			  o.writeObject(par);
			  o.close();
			  w.close();
		}
		catch(Exception e){
			 JOptionPane.showMessageDialog(null, e);
			 System.exit(0);
		} 
    }
   
   public static SGSnake read(String file){
	  SGSnake s = null;
	  try{
			  FileInputStream w = new FileInputStream(file);
			  ObjectInputStream o = new ObjectInputStream(w);
			  s = (SGSnake) o.readObject();
			  o.close();
			  w.close();
		}
	  catch(Exception e){
			   JOptionPane.showMessageDialog(null, e);
		} 
	  finally {
				return s;
		}
   }

}