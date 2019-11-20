package aula3.ex2;


import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

/**
 *
 * @author siabr
 */
public class Cena implements GLEventListener, KeyListener{
    private float tx = 0;
    private float sx = 1;
    private float angulo = 0;
    
    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
    }
    
    //Função resposável por desenhar o céu...
    public void desenhaCeu(GL2 gl){        
    gl.glColor3f(0.529f, 0.808f, 0.980f);    
            
    gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glVertex2f(1.0f, 1.0f);
        gl.glVertex2f(1.0f, 0);
        gl.glVertex2f(-1.0f, 0);
    gl.glEnd();
    }
    
    //Função responsável por desenhar a grama...
    public void desenhaGrama(GL2 gl){
        gl.glColor3f(0, .5f, 0);       
       gl.glBegin(GL2.GL_QUADS);            
            gl.glVertex2f(-1.0f, 0);
            gl.glVertex2f(1.0f, 0);
            gl.glVertex2f(1.0f, -1.0f);
            gl.glVertex2f(-1.0f, -1.0f);
        gl.glEnd();
    }
    
    public void desenhaParede(GL2 gl){
        gl.glColor3f(0.698f, 0.133f, 0.133f);       
       gl.glBegin(GL2.GL_QUADS);            
            gl.glVertex2f(-0.5f, 0.4f);
            gl.glVertex2f(0.5f, 0.4f);
            gl.glVertex2f(0.5f, -0.6f);
            gl.glVertex2f(-0.5f, -0.6f);
        gl.glEnd();
    }
    
    public void desenhaTelhado(GL2 gl){
        gl.glColor3f(1.000f, 0.498f, 0.314f);
       
       gl.glPushMatrix();
            gl.glTranslatef(.3f, .1f, 0f);
            
            gl.glBegin(GL2.GL_TRIANGLES);               
                gl.glVertex2f(-0.5f, 0.4f);
                gl.glVertex2f(0.5f, 0.4f);
                gl.glVertex2f(0, 0.9f);
            gl.glEnd();
        gl.glPopMatrix();
    }
    
    public void desenhaPorta(GL2 gl){
        gl.glColor3f(0.545f, 0.271f, 0.075f);       
       gl.glBegin(GL2.GL_QUADS);      
           gl.glVertex2f(-0.4f, -0.6f);
           gl.glVertex2f(-0.4f, 0.2f);
           gl.glVertex2f(-0.1f, 0.2f);
           gl.glVertex2f(-0.1f, -0.6f);         
            
        gl.glEnd();
        
        gl.glColor3f(0,0,0);
        gl.glPointSize(8f);
        gl.glBegin(GL2.GL_POINTS);
            gl.glVertex2f(-.35f, -.2f);
        gl.glEnd();
    }
    
    public void desenhaEntrada(GL2 gl){
        
        float k = -.4f;
        float j = -.6f;
        
        gl.glColor3f(1, 1, 0);
        
        gl.glBegin(GL2.GL_QUADS);
        
            gl.glVertex2f(k, j);
            gl.glVertex2f((k + .3f), j);
            gl.glVertex2f((k + .6f), -1f);
            gl.glVertex2f((k - .3f), -1f);
            
        gl.glEnd();
        
    }
    
    public void desenhaCasa(GL2 gl){
        desenhaParede(gl);
        desenhaTelhado(gl);
        desenhaPorta(gl);
    }
    
    public void desenhaJanela(GL2 gl){
        float xo = 0f;
        float yo = 0f;
        
        gl.glLineWidth(5f);
        gl.glColor3f(0,0,0);
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(xo, yo);
            gl.glVertex2f((xo + .4f), yo);
            gl.glVertex2f((xo + .4f), (yo + .2f));
            gl.glVertex2f((xo), (yo + .2f));
        gl.glEnd();
        gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(xo, (yo + .1f));
            gl.glVertex2f((xo+.4f), (yo+.1f));
        gl.glEnd();
        gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f((xo +.2f), (yo + .2f));
            gl.glVertex2f((xo+.2f), (yo));
        gl.glEnd();
    }
    
    @Override
    public void display(GLAutoDrawable drawable) {  
        //obtem o contexto Opengl
        GL2 gl = drawable.getGL().getGL2();                
        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);        
        //limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);       
        gl.glLoadIdentity(); //lê a matriz identidade
            
        
        
        /*
            desenho da cena      
        */
        
        gl.glRotatef(angulo, 0, 0, 1);
       
        gl.glScalef(sx, sx, 1);

        desenhaCeu(gl);
        desenhaGrama(gl);
        desenhaCasa(gl);
        desenhaEntrada(gl);
        desenhaJanela(gl);
        gl.glFlush();      
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {    
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();        
        //ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);      
        gl.glLoadIdentity(); //lê a matriz identidade
        //projeção ortogonal (xMin, xMax, yMin, yMax, zMin, zMax)
        gl.glOrtho(-1,1,-1,1,-1,1);
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    @Override
    public void dispose(GLAutoDrawable drawable) {}        

    @Override
    public void keyPressed(KeyEvent e) {         
        switch(e.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            //........
        }
        switch(e.getKeyChar()){
            case 'T':
                tx += 0.1f;
                break;
            case 't':
                tx -= 0.1f;
                break;
            case 'r':
                angulo += 45.0f;
                break;
            case 'E':
                sx += 0.1f;
                break;
            case 'e':
                sx -= 0.1f;
                break;
            //........
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
