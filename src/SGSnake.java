import java.io.*;
import javax.swing.*;
public class SGSnake implements Serializable{

   private static final long serialVersionUID = 42386326223537L;
   
   public Cola<Par> serpiente;
   public Cola<Par> alimento;
   public long tiempo;
   
   public SGSnake(TableroGL tab){
   
		serpiente = tab.serp.cuerpo;
		alimento = tab.raton.cuerpo;
		tiempo = tab.tiempo;	
   }
   
}