import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Window extends JFrame implements KeyListener{
	
	//Immagine usata come buffer dei pixel calcolati
	private BufferedImage immagine;
	//Numero di iterazioni di preferenza
	int ITERATIONI = 100;
	//Valore usato per controllare l'ingrandimento
	int scala = 250;
	//Valore usato per gestire la tonalità della rappresentazione
	float colore_offset = 0f;
	//Valori usati per spostare la rappresentazione dell'insieme di una quantità precisa
	float x_off = 0, y_off = 0;
	//Valore usato per limitare il movimento dell'insieme
	float mv = 1000;
	//Valore booleano: se vero l'immagine sarà in bianco e nero, se no a colori
	boolean bianco_e_nero = true;
	
	public Window()
	{
		immagine = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		this.setTitle("Insieme di Mandelbrot - by Frank01001");
		this.setSize(800, 600);
		this.setVisible(true);
		repaint();
		this.setResizable(false);
		this.addKeyListener(this);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void disegnaInsieme()
	{
		for(int x = 0; x < 800; x++)
		{
			
			for(int y = 0; y < 600; y++)
			{
				int color = calcolaColore(((x + x_off) - 800/2f)/scala, ((y + y_off) - 600/2f)/scala);
				immagine.setRGB(x, y, color);
			}
			
		}
	}
	
	public int calcolaColore(float x, float y)
	{
		float cx = x;
		float cy = y;
		
		int i = 0;
		
		for(;i < ITERATIONI; i++)
		{
		   /* Calcola le componenti reale e immaginaria del numero complesso riferito alle coordinate
			* tramite la formula di moltiplicazione dei numeri complessi
			*/
			float nx = x*x-y*y+cx;
			float ny = 2*x*y+cy;
			// assegna a x e y i nuovi valori
			x = nx;
			y = ny;
			
			/* Se la distanza al quadrato tra il numero complesso e il centro sul piano di Gauss
			 * è maggiore di 4 (valore sperimentale), per approssimazione si considera come
			 * prossimo a infinito al termine delle iterazioni.
			 */
			if(x*x + y*y > 4) break;
		}
		
		/* Se il calcolo ha raggiunto la fine delle iterazioni
		 * senza diventare prossimo a infinito, il punto appartiene
		 * all'insieme, e deve dunque essere colorato di nero
		 */
		if(i == ITERATIONI) return 0x00000000;
		
		/* Per quanto riguarda gli altri punti, se l'opzione "bianco e nero" è attiva,
		 * i punti esterni saranno semplicemente colorati di bianco, altrimenti esso
		 * verrà colorato in base al numero di iterazioni
		 */
		if(!bianco_e_nero)
				return Color.HSBtoRGB(((float) i / ITERATIONI + colore_offset)%1, 0.6f, 1);
		else
			return 0xffffff;
		
	}
	
	@Override
	public void paint(Graphics g)
	{
		disegnaInsieme();
		g.drawImage(immagine, 0, 0, null);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_Q:
		{
			scala *= 1.2f;
			break;
		}
		case KeyEvent.VK_E:
		{
			scala /=1.2f;
			break;
		}
		case KeyEvent.VK_W:
		{
			y_off -= 10 * mv/scala;
			break;
		}
		case KeyEvent.VK_S:
		{
			y_off += 10 * mv/scala;
			break;
		}
		case KeyEvent.VK_A:
		{
			x_off -= 10 * mv/scala;
			break;
		}
		case KeyEvent.VK_D:
		{
			x_off += 10 * mv/scala;
			break;
		}
		case KeyEvent.VK_SPACE:
		{
			bianco_e_nero = !bianco_e_nero;
			break;
		}
		default:
				break;
		}
		
		repaint();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}