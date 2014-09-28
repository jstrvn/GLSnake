import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;
import javax.media.opengl.glu.*;
import javax.swing.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.gl2.*;


public class TableroGL extends GLCanvas implements AbstractTablero, GLEventListener, KeyListener{
   private JLabel puntuacion;
   private JDialog dia;
   public static int col, fil;
   private int width, height;
   public static double  ratioX, ratioY;
   Serpiente serp;
   Alimento raton;
   private int fps;
   public static GLCanvas canv;
   private GLU glu = new GLU();
   private FPSAnimator animator;
   private long inicio;
   private long fin;
   public long tiempo;
   public void setEstado(String estado){
      if ( serp!= null )
		serp.estado = estado;
   }
   
   
   public void setGame(SGSnake snake){
     serp = new Serpiente(this, snake.serpiente);
	 raton = new Alimento(this, snake.alimento.get(0));
	 tiempo = snake.tiempo;
   }
   
   public SGSnake getGame(){
       return new SGSnake(this);
   }
   
    public int	getFil(){
	   return fil;
	}
	public  Alimento getRaton(){
	   return raton;
	}
	
	public  double getRatioX(){
	   return ratioX;
	}
    public  double getRatioY(){
		return ratioY;
	}
	public  int getCol(){
		return col;
	}
    
    public void setAnimator(FPSAnimator animator){
		this.animator = animator;
	}
	
	
	public void reiniciar(){
	  
		int x = col/2;
		int y = fil/2;
	    serp = new Serpiente(this, new Par(x, y) );
		for(int i=0; i<2; i++) serp.aumentar();
		raton = new Alimento(this, getPosicionAlimento());
		tiempo = 0;
	    inicio = System.currentTimeMillis();
	}
   
   public TableroGL(GLCapabilities cap, int width, int height, int col, int fil, JLabel label){
     
	   super(cap);
	   tiempo = 0L;
	   puntuacion = label;
	   inicio = System.currentTimeMillis();
	   fps = 1;
	   setPreferredSize(new Dimension(width, height));
	   addGLEventListener(this);
	   addKeyListener(this); 
	   this.width = width;
	   this.height = height;
	   int x = ((int)(Math.random()*col));
	   int y = ((int)(Math.random()*fil));
     
	   serp = new Serpiente(this, new Par(x,y));
	   raton = new Alimento(this, getPosicionAlimento());
	   ratioX = (double)(width)/col;
	   ratioY = (double)(height)/fil;
	   this.col = col;
	   this.fil = fil;
	   puntuacion.setText( "Estado: " + serp.estado + " Puntuacion: " + String.valueOf(serp.size - 3) + " Tiempo: " + String.valueOf(tiempo + (System.currentTimeMillis()-inicio)/1000));
	   animator = new FPSAnimator(this, fps);
	   animator.start();
	}
   
   
public void display(GLAutoDrawable drawable) {                
	GL2 gl = drawable.getGL().getGL2();
	gl.glClear(GL2.GL_COLOR_BUFFER_BIT);  
    gl.glColor3f(0f, 0f, 0f);
    paint(gl);  
	 
	gl.glFlush();	
}

public void dispose(GLAutoDrawable drawable) {
}

public void init(GLAutoDrawable drawable) {

	GL2 gl = drawable.getGL().getGL2();
	 
	gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f); 
	gl.glMatrixMode(GL2.GL_PROJECTION);    
	gl.glLoadIdentity();                 
	glu.gluOrtho2D( -20, 900, -20, 800);   
	gl.glMatrixMode(GL2.GL_MODELVIEW);       
	gl.glLoadIdentity();    
	reiniciar();  
}

public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
}

       public  void paint(GL2 gl){
      
		
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
		gl.glColor3f(100.0f/255.0f, 120.0f/255.0f, 200.0f/255.0f);
		  for(int i=0; i<col; i++)
	      for(int j=0; j<fil; j++){
	     	drawRect(gl, (int)(i*ratioX), (int)(j*ratioY), (int)ratioX, (int)ratioY);
		}	
		
		gl.glColor3f(0, 0, 1);
		
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		serp.avanzar();
		if (  !serp.avanzar() ){
		    
		  SwingUtilities.invokeLater(new Runnable(){
	         public void run(){
			  if ( dia == null ){
	            dia = new JDialog( new JFrame() ,"Has perdido");
				dia.setLayout( new BorderLayout() );
				
				JTextArea but = new JTextArea("Puntuacion: " + serp.size );
				but.setEditable(false);
				dia.add( but, BorderLayout.NORTH);
				
				dia.pack();
				dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dia.setFocusableWindowState(true);
				dia.addWindowListener(new WindowAdapter(){
				    public void windowClosing(WindowEvent e){
						  dia = null;
					   new Thread( new Runnable(){
					            public void run(){
									reiniciar();
								}
						   }).start();
					}
				});
				dia.show();
				 }
				 }
			});
			
		}
		else{
			puntuacion.setText( "Estado: " + serp.estado + " Puntuacion: " + String.valueOf(serp.size - 3) +   space(8) +  " Tiempo: "  +  String.valueOf(tiempo +(System.currentTimeMillis()-inicio)/1000) );
		}
		serp.dibujar(gl);
		
	    if ( fps != serp.size ){
		    fps = serp.size;
	    	animator.stop();
			animator.remove(canv);
			animator = new FPSAnimator(canv, fps);
			animator.start();
	
		}
		gl.glColor3f(0, 0, 0);
		raton.dibujar(gl);
		gl.glFlush();
	
	}
	public String space(int n){
	   StringBuffer buf = new StringBuffer();
	   for(int i=0; i<n; i++)
	      buf.append(" ");
	    return buf.toString();
	}
	
	public static void drawRect(GL2 gl, int x1, int y1, int width, int height){
		
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex3i(x1, y1 ,0);
		gl.glVertex3i(x1 + width, y1, 0 );
		gl.glVertex3i(x1 + width, y1 + height, 0 );
		gl.glVertex3i(x1, y1+ height, 0 );
		gl.glEnd();
	}
	
	
	
	public Par getPosicionAlimento(){
		int x = ((int)(Math.random()*col));
		int y = ((int)(Math.random()*fil));
	    for(int i=0; i<serp.cuerpo.size(); i++){
		   if ( x == serp.cuerpo.get(i).getX() && y == serp.cuerpo.get(i).getY() ){
				return getPosicionAlimento();
		   }
		}
		return new Par(x, y);
	}
	
/*	
	public JLabel obtenerLPuntuacion(){
	  JLabel l = null;
	  int i=0;
	  try{
	        Component c = this;
			//while( true ){
		    JOptionPane.showMessageDialog(null, SwingUtilities.getWindowAncestor(this));
		//	frame.setTitle("HOLA");
				//if (  ( (JFrame)getParent() ).getContentPane().getComponent(i) instanceof JLabel ){
			//		l = ((JLabel)( (JFrame)getParent() ).getContentPane().getComponent(i));
				//	break;
				//}
				//JOptionPane.showMessageDialog(null, ( ( (JFrame)getParent() )) );
			//	i++;
		//	}
		}
		catch(Exception e){
		     
			JOptionPane.showMessageDialog(null,  e);
		}
		
		finally{
			return l;
		}
	}
*/	
   public void keyPressed(KeyEvent e){
     switch( e.getKeyCode() ){
	    case KeyEvent.VK_UP:
		serp.setDireccion( new Par(0, 1));
		break;
		
		case KeyEvent.VK_DOWN:
		serp.setDireccion( new Par(0, -1));
		break;
		
		case KeyEvent.VK_LEFT:
		serp.setDireccion( new Par(-1, 0));
		break;
		
		case KeyEvent.VK_RIGHT:
		serp.setDireccion( new Par(1, 0));
		break;
		
		
		case KeyEvent.VK_ESCAPE:
		System.exit(0);
		break;
		
		case KeyEvent.VK_P:
		if ( !serp.estado.equalsIgnoreCase("PAUSA")){
			serp.estado ="pausa";
			tiempo += (System.currentTimeMillis() - inicio)/1000;
		}
		else{
		    serp.estado = "on";
		}
		break;
	 }
	}

   public void	keyReleased(KeyEvent e){
   
   }
   
   public void	keyTyped(KeyEvent e){
   
   }


}