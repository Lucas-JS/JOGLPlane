package luzambiente;

import java.util.concurrent.TimeUnit;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT; //primitivas 3D
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author siabr
 */
public class PlaneAps implements GLEventListener, KeyListener {

    private float x = 0, y = 0, z = 0;
    float rx = 0, ry = 0, rz = 0;
    float kx = 0, ky = 0, kz = 0;
    char cena = 'c';
    private float anguloX = 0, anguloY = 0, anguloZ = 0;
    private GL2 gl;
    private GLU glu;
    private GLUT glut;
    private int tonalizacao = GL2.GL_SMOOTH;
    private boolean ligaEspecular = false;
    private boolean ligaDifusa = false;

    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        GL2 gl = drawable.getGL().getGL2();
        //habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        //obtem o contexto Opengl
        gl = drawable.getGL().getGL2();
        glut = new GLUT(); //objeto da biblioteca glut

        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);
        //limpa a janela com a cor especificada
        //limpa o buffer de profundidade
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity(); //lê a matriz identidade

        /*
            desenho da cena        
        *
         */
        // criar a cena aqui....
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glShadeModel(GL2.GL_FLAT);
        float luzAmbiente[] = {0f, 0f, .5f, 1.0f}; //cor
        float posicaoLuz[] = {-50.0f, 0.0f, 100.0f, 1.0f}; //1.0 pontual

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);

        gl.glEnable(GL2.GL_LIGHT0);
        switch(cena){
            case 'c':
                anguloX = 0;
                anguloY = 0; 
                anguloZ = 0;
         gl.glPushMatrix();
        
        gl.glRotatef(anguloX, 1, 0, 0);
        gl.glRotatef(anguloY, 0, 1, 0);
        gl.glRotatef(anguloZ, 0, 0, 1);

        gl.glScalef(1.5f, 1.5f, 1.5f);
        planeTakeoff();
        gl.glPopMatrix();
        break;
        
            case 'C':
                anguloX = 0;
                anguloY = 0; 
                anguloZ = 0;
                gl.glPushMatrix();
        
        gl.glRotatef(anguloX, 1, 0, 0);
        gl.glRotatef(anguloY, 0, 1, 0);
        gl.glRotatef(anguloZ, 0, 0, 1);

        gl.glScalef(1.5f, 1.5f, 1.5f);
        spinningPlane();
        gl.glPopMatrix();
                break;
            default:
                break;
        }
        gl.glFlush();
    }

    public void planeTakeoff() {
        gl.glRotatef(-90, 0, 1, 0);
        gl.glRotatef(kx, 1, 0, 0);

        gl.glTranslatef(rx, ry, rz);

        drawPlane();

        risingPlane();

    }
    
    public void spinningPlane(){
                gl.glRotatef(-90, 0, 1, 0);
                rotatePlane('1');
        drawPlane();

    }



    public void risingPlane() {

        for (int i = 0; i < 60; i++) {

            rz = rz - 0.01f;

        }

        if (kx < 30) {
            kx++;
        }
        if (anguloX < 30){
            anguloX++;
        }
    }

    public void drawPlane() {
        planeCockpit();
        planeBody();
        planeBack();
        planeWings();
    }

    public void planeCockpit() {
        gl.glPushMatrix();
        glut.glutSolidSphere(5f, 100, 100);
        gl.glPopMatrix();
    }

    public void planeBody() {
        glut.glutSolidCylinder(5f, 25f, 100, 100);

    }

    public void planeBack() {
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 25);
        glut.glutSolidCone(5f, 5f, 100, 100);

        gl.glPopMatrix();
    }

    public void planeWings() {

        gl.glRotatef(90, 1, 0, 0);
        gl.glTranslatef(0, 5, 0);

        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 1);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(-20f, -2f);
        gl.glVertex2f(20f, -2f);
        gl.glVertex2f(20f, 10f);
        gl.glVertex2f(-20f, 10f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, -1);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(-20f, -2f);
        gl.glVertex2f(20f, -2f);
        gl.glVertex2f(20f, 10f);
        gl.glVertex2f(-20f, 10f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glRotatef(-90, 1, 0, 0);
        gl.glPushMatrix();

        gl.glTranslatef(0, 0, -2);
//                                    gl.glTranslatef(x, y, z);

        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(-20f, -2f);
        gl.glVertex2f(20f, -2f);
        gl.glVertex2f(20f, 1f);
        gl.glVertex2f(-20f, 1f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 10);

        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(-20f, -2f);
        gl.glVertex2f(20f, -2f);
        gl.glVertex2f(20f, 1f);
        gl.glVertex2f(-20f, 1f);

        gl.glEnd();
        gl.glPopMatrix();
        gl.glRotatef(90, 0, 1, 0);
        gl.glTranslatef(10, 0, 0);
        gl.glPushMatrix();
        gl.glTranslatef(-10, 0, -20);
//                            gl.glTranslatef(x, y, z);
        gl.glBegin(GL2.GL_QUADS);

        gl.glVertex2f(-10f, -2f);
        gl.glVertex2f(1f, -2f);
        gl.glVertex2f(1f, 1f);
        gl.glVertex2f(-10f, 1f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glPushMatrix();

        gl.glTranslatef(-10, 0, 20);
//                            gl.glTranslatef(x, y, z);
        gl.glBegin(GL2.GL_QUADS);

        gl.glVertex2f(-10f, -2f);
        gl.glVertex2f(1f, -2f);
        gl.glVertex2f(1f, 1f);
        gl.glVertex2f(-10f, 1f);
        gl.glEnd();
        gl.glPopMatrix();
    }

    public void rotatePlane(char c) {
        for (int i = 0; i < 360; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cena2.class.getName()).log(Level.SEVERE, null, ex);
            }
            switch (c) {
                case '1':
                    anguloX++;
                    break;
                case '2':
                    anguloY++;
                    break;
                case '3':
                    anguloZ++;
                    break;
            }

        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //obtem o contexto grafico Opengl
        gl = drawable.getGL().getGL2();
        //ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); //lê a matriz identidade
        //projeção ortogonal (xMin, xMax, yMin, yMax, zMin, zMax)
        gl.glOrtho(-100, 100, -100, 100, -100, 100);
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        System.out.println("Reshape: " + width + ", " + height);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            //........
        }
        switch (e.getKeyChar()) {
            case '1':
                System.out.println(e.getKeyChar());
                rotatePlane('1');
                break;
            case '2':
                System.out.println(e.getKeyChar());
                rotatePlane('2');
                break;
            case '3':
                System.out.println(e.getKeyChar());
                rotatePlane('3');
                break;
            case 'x':
                System.out.println(e.getKeyChar());
                System.out.println(x);
                x++;
                break;
            case 'X':
                System.out.println(e.getKeyChar());
                System.out.println(x);
                x--;
                break;
            case 'y':
                System.out.println(e.getKeyChar());
                System.out.println(y);
                y++;
                break;
            case 'Y':
                System.out.println(e.getKeyChar());
                System.out.println(y);
                y--;
                break;
            case 'z':
                System.out.println(e.getKeyChar());
                System.out.println(z);
                z++;
                break;
            case 'Z':
                System.out.println(e.getKeyChar());
                System.out.println(z);
                z--;
                break;
case 'c':
                System.out.println(e.getKeyChar());
                cena = 'c';
                break;
            case 'C':
                System.out.println(e.getKeyChar());
                cena = 'C';
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
