import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Tablero extends JPanel implements KeyListener, AbstractTablero{
   
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
      
   
   JDialog dia;
   private boolean stop;
   Thread thread;
   public int col, fil;
   private int width, height;
   public double ratioX, ratioY;
   Serpiente serp;
   Alimento raton;
   public int ms;
   private static final int tiempo = 500; 
   private Random rand;
   
   public Tablero(int width, int height, int col, int fil){
     rand = new Random(System.currentTimeMillis() );
	 this.width = width;
	 stop = false;
	 dia = new JDialog((Window)getParent(), "Has perdido");
	 this.height = height;
	 setSize(width, height);
   	 int x = ((int)(Math.random()*col));
	 int y = ((int)(Math.random()*fil));
	 serp = new Serpiente(this, new Par(x,y));
	 ms = tiempo;
	 raton = new Alimento(this, getPosicionAlimento() );
	 ratioX = (double)(width)/col;
	 ratioY = (double)(height)/fil;
	 setFocusable(true);
     requestFocusInWindow();
     addKeyListener(this);
      this.col = col;
	  this.fil = fil;
	  Runnable run = new Runnable(){
	    public void run(){
			anima();
		}
	  };
	  Thread thread = new Thread(run);
	  thread.start();
   }
   
     
	public void anima(){
	 while(true){ 
  	 try{
	    Thread.sleep(ms);		
		repaint();
		if ( stop ){
		  break;
		}
	  }
	  catch(InterruptedException e){
		
	  }
	 }
	}
   
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      g.setColor( new Color(100, 120, 200) );
	  for(int i=0; i<col; i++)
	   for(int j=0; j<fil; j++){
		g.drawRect((int)(i*ratioX), (int)(j*ratioY), (int)ratioX, (int)ratioY);
		}
		g.setColor( new Color(255, 0, 0) );
	  
		serp.dibujar(g);
		ms = 500/serp.size;
		
		if ( !serp.avanzar() ){
		  
		  SwingUtilities.invokeLater(new Runnable(){
	         public void run(){
			  if ( dia.getComponent(0) == null )
				dia.setLayout( new BorderLayout() );
				JButton but = new JButton("Puntuacion: " + serp.size);
				dia.add( but, BorderLayout.NORTH);
				but.addActionListener(new ActionListener(){
						 public void actionPerformed(ActionEvent ae) {      
							dia.dispose();
							new Thread( new Runnable(){
							    public void run(){
									reiniciar();
								}
						   }).start();
						 }    
				});
				dia.pack();
				dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dia.setFocusableWindowState(true);
				dia.addWindowListener(new WindowAdapter(){
				    public void windowClosing(WindowEvent e){
					   new Thread( new Runnable(){
							    public void run(){
									reiniciar();
								}
						   }).start();
					}
				});
				dia.show();
				 }
			});
			
			
			stop = true;
		}		
		g.setColor( new Color(0, 0, 255) );
		raton.dibujar(g);
	
	}
	
	public void reiniciar(){
	    int x = ((int)(Math.random()*col));
		int y = ((int)(Math.random()*fil));
		serp = new Serpiente(this, new Par(x,y));
		ms = tiempo;
		x = ((int)(Math.random()*col));
		y = ((int)(Math.random()*fil));
		raton = new Alimento(this, new Par(x, y));
	    stop = false;
		anima();
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
	
   public void keyPressed(KeyEvent e){
     
	 switch( e.getKeyCode() ){
	    case KeyEvent.VK_UP:
		serp.setDireccion( new Par(0, -1));
		break;
		
		case KeyEvent.VK_DOWN:
		serp.setDireccion( new Par(0, 1));
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
		
	 }
	}

   public void	keyReleased(KeyEvent e){
   
   }
   
   public void	keyTyped(KeyEvent e){
   
   }
   
}