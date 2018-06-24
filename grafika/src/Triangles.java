import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class Triangles implements GLEventListener {
    private static float[][] topCoords;
    private static float[][] bottomLeftCoords;
    private static float[][] bottomRightCoords;
    private static float[][] colors;

    private static final float[] RED = {1.0f, 0.0f, 0.0f};
    private static final float[] GREEN = {0.0f,1.0f,0.0f};
    private static final float[] BLUE = { 0.0f,0.0f,1.0f };

    Triangles(){
        this.topCoords = new float[][]{{0,0.2f}, {0,-0.2f}};
        this.bottomLeftCoords = new float[][] {{0.2f,0}, {-0.2f,0}};
        this.bottomRightCoords = new float[][] {{0,0}, {0,0}};
        this.colors = new float[][] {GREEN, RED};
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glBegin (GL2.GL_TRIANGLES);
        for(int index = 0; index < topCoords.length; index++){
            gl.glColor3f( colors[index][0], colors[index][1], colors[index][2]);
            gl.glVertex3f(topCoords[index][0], topCoords[index][1],0);       // Top
            gl.glVertex3f(bottomLeftCoords[index][0], bottomLeftCoords[index][1],0 );    // Bottom Left
            gl.glVertex3f(bottomRightCoords[index][0], bottomRightCoords[index][1],0);      // Bottom Right
        }
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        //method body
    }

    @Override
    public void init(GLAutoDrawable arg0) {
        // method body
    }
}
