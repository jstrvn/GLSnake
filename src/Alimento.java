
import java.awt.*;
import javax.swing.*;

public class Alimento extends Personaje{

   
	
	public Alimento(AbstractTablero tab, Par par){
	 
	   super(tab, par);
	}

	public void consumir(){
	   cuerpo.dequeue();
	}
	
	public void reCrear(Par par){
	   cuerpo.enqueue(par);
	}
	
	
	
}