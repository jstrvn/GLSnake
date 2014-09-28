import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;


public class Personaje{

	public Cola<Par> cuerpo;
	protected AbstractTablero tab;
  
	public String toString(){
		StringBuffer buf = new StringBuffer();
		for(int i=0; i<cuerpo.size(); i++){
			buf.append( i +"::: " + "X:" + cuerpo.get(i).getX()  + " Y:" + cuerpo.get(i).getY() + "\n");
		}
		return buf.toString();
	}
  
	public Personaje(AbstractTablero tab, Cola<Par> par){
		 this.tab = tab;
		 cuerpo = par;
	}
  
	public Personaje(AbstractTablero tab, Par pos){
		this.tab = tab;
		cuerpo = new Cola<>();
		cuerpo.enqueue( pos );
	}
	
	public void dibujar(GL2 gl){
		int n = cuerpo.size();
	    gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
	
		for(int i=0; i<n; i++){
	    	TableroGL.drawRect(gl, (int)(cuerpo.get(i).getX()*tab.getRatioX()), (int)(cuerpo.get(i).getY()*tab.getRatioY()), (int)tab.getRatioX(), (int)tab.getRatioY());   		
	    }
	
	}
	
	public void dibujar(Graphics g){
		int n = cuerpo.size();
		
		for(int i=0; i<n; i++){
			 g.fillRect( (int)(cuerpo.get(i).getX()*tab.getRatioX()), (int)(cuerpo.get(i).getY()*tab.getRatioY()), (int)tab.getRatioX(), (int)tab.getRatioY());
		}
	}
  
}