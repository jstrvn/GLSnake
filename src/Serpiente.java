import java.util.*;
import java.awt.*;
import javax.swing.*;


public class Serpiente extends Personaje{

	private Par direccion;
	public int size;
	public String estado;
	  
	public void setDireccion(Par par){
   
		 if ( validarDireccion(par, direccion) ){
			direccion = par;
		 }
	}
  
	public boolean validarDireccion(Par parA, Par parB){
		if ( parA.getX() * parB.getX()  == -1  || parA.getY() * parB.getY()  == -1 ){
		    return false;
	    }
			return true;  
	}
  
  
	public Par getDireccion(){
		return direccion;
	}
  
	public Serpiente(AbstractTablero tab, Cola<Par> par){
		super(tab, par);
		estado = "Juego";
		direccion = generarDireccionAleatoria() ;
		size = cuerpo.size();
	}
  
	public Serpiente(AbstractTablero tab, Par par){
		 super(tab, par);
		 estado = "Juego";
		 direccion = generarDireccionAleatoria() ;
		 size = cuerpo.size();
	}
  
  
	public Par generarDireccionAleatoria(){
		 Par[] par = new Par[4]; 
		 par[0] = new Par(0, -1);
		 par[1] = new Par(1, 0);
		 par[2] = new Par(0, 1);
		 par[3] = new Par(-1, 0);	 
		 return par[(int)(Math.random()*4)];
	}

  
	public void aumentar(){
		Par par = new Par(cuerpo.getLast().getX() + direccion.getX(),
									 cuerpo.getLast().getY() + direccion.getY());
		if ( validarMovimiento() ){
			cuerpo.enqueue( par );
		}
		size = cuerpo.size();
	}
  
  public boolean avanzar(){
    if ( validarMovimiento() && !estado.equalsIgnoreCase("PAUSA") ){
		cuerpo.enqueue( new Par(cuerpo.getLast().getX() + direccion.getX(),
                                 cuerpo.getLast().getY() + direccion.getY()));
		cuerpo.dequeue();
		
		if ( cuerpo.getLast().getX() == tab.getRaton().cuerpo.getLast().getX() &&
             cuerpo.getLast().getY() == tab.getRaton().cuerpo.getLast().getY() ){
				tab.getRaton().consumir();
				aumentar();
				tab.getRaton().reCrear(tab.getPosicionAlimento() );
		}	
		return true;
	}	
    if ( estado.equalsIgnoreCase("PAUSA") )
	    return true;
	return false;
	
  }
  
  public boolean validarMovimiento(){
      
      int x = cuerpo.getLast().getX() + direccion.getX();
      int y = cuerpo.getLast().getY() + direccion.getY();
	  if ( x < tab.getCol() && x >= 0)	 
		if ( y < tab.getFil() && y >= 0){	
            for(int i=0; i<cuerpo.size()-1; i++){
				if ( cuerpo.get(i).getX() == x && cuerpo.get(i).getY() == y ){
					return false;
				}
				
			}	
          			
			return true;
		}
		
		return false;
	}

}