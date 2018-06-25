import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import java.util.ArrayList;
import java.util.List;

public class Triangles implements GLEventListener {
    private static List<float[]> topCoords;
    private static List<float[]> bottomLeftCoords;
    private static List<float[]> bottomRightCoords;
    private static List<float[]> colors;


    private static final float[] RED = {1.0f, 0.0f, 0.0f};
    private static final float[] GREEN = {0.0f,1.0f,0.0f};
    private static final float[] BLUE = { 0.0f,0.0f,1.0f };
    private static final float[] ORANGE = {1.0f, 0.5f, 0};
    private static final float[] PURPLE = {1.0f, 0.0f, 1.0f};
    private static final float[] YELLOW = {1.0f, 1.0f, 0.0f};

    Triangles(){
        topCoords = new ArrayList<>();
        bottomLeftCoords = new ArrayList<float[]>();
        bottomRightCoords = new ArrayList<>();
        colors = new ArrayList<>();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glBegin (GL2.GL_TRIANGLES);
        defineCoords();
        for(int index = 0; index < topCoords.size(); index++){
            gl.glColor3f( colors.get(index)[0], colors.get(index)[1], colors.get(index)[2]);
            gl.glVertex3f(topCoords.get(index)[0], topCoords.get(index)[1],0);       // Top
            gl.glVertex3f(bottomLeftCoords.get(index)[0], bottomLeftCoords.get(index)[1],0 );    // Bottom Left
            gl.glVertex3f(bottomRightCoords.get(index)[0], bottomRightCoords.get(index)[1],0);      // Bottom Right
        }
        gl.glEnd();
    }

    private void defineCoords() {
        //inner circle
        topCoords.add(new float[]{0, 0.2f});
        bottomLeftCoords.add(new float[]{0.2f, 0});
        bottomRightCoords.add(new float[]{0,0});
        colors.add(BLUE);

        topCoords.add(new float[]{0, -0.2f});
        bottomLeftCoords.add(new float[]{0.2f, 0});
        bottomRightCoords.add(new float[]{0,0});
        colors.add(BLUE);

        topCoords.add(new float[]{0, -0.2f});
        bottomLeftCoords.add(new float[]{-0.2f, 0});
        bottomRightCoords.add(new float[]{0,0});
        colors.add(BLUE);

        topCoords.add(new float[]{0, 0.2f});
        bottomLeftCoords.add(new float[]{-0.2f, 0});
        bottomRightCoords.add(new float[]{0,0});
        colors.add(BLUE);

        // medium circle
        topCoords.add(new float[]{0, 0.4f});
        bottomLeftCoords.add(new float[]{0f, 0.2f});
        bottomRightCoords.add(new float[]{0.2f,0.2f});
        colors.add(GREEN);

        topCoords.add(new float[]{0.2f, 0.2f});
        bottomLeftCoords.add(new float[]{0.2f, 0f});
        bottomRightCoords.add(new float[]{0.4f,0f});
        colors.add(ORANGE);

        topCoords.add(new float[]{0.2f, -0.2f});
        bottomLeftCoords.add(new float[]{0.2f, 0f});
        bottomRightCoords.add(new float[]{0.4f,0f});
        colors.add(GREEN);

        topCoords.add(new float[]{0f, -0.4f});
        bottomLeftCoords.add(new float[]{0f, -0.2f});
        bottomRightCoords.add(new float[]{0.2f,-0.2f});
        colors.add(ORANGE);

        topCoords.add(new float[]{0f, -0.4f});
        bottomLeftCoords.add(new float[]{0f, -0.2f});
        bottomRightCoords.add(new float[]{-0.2f,-0.2f});
        colors.add(GREEN);

        topCoords.add(new float[]{-0.2f, -0.2f});
        bottomLeftCoords.add(new float[]{-0.2f, 0});
        bottomRightCoords.add(new float[]{-0.4f,0});
        colors.add(ORANGE);

        topCoords.add(new float[]{-0.2f, 0.2f});
        bottomLeftCoords.add(new float[]{-0.2f, 0});
        bottomRightCoords.add(new float[]{-0.4f,0});
        colors.add(GREEN);

        topCoords.add(new float[]{0, 0.4f});
        bottomLeftCoords.add(new float[]{-0f, 0.2f});
        bottomRightCoords.add(new float[]{-0.2f,0.2f});
        colors.add(ORANGE);

        //outer circle
        topCoords.add(new float[]{0, 0.6f});
        bottomLeftCoords.add(new float[]{0f, 0.4f});
        bottomRightCoords.add(new float[]{0.2f,0.4f});
        colors.add(RED);

        topCoords.add(new float[]{0.4f, -0.2f});
        bottomLeftCoords.add(new float[]{0.4f, 0f});
        bottomRightCoords.add(new float[]{0.6f,0f});
        colors.add(RED);

        topCoords.add(new float[]{0, -0.6f});
        bottomLeftCoords.add(new float[]{0f, -0.4f});
        bottomRightCoords.add(new float[]{-0.2f,-0.4f});
        colors.add(RED);

        topCoords.add(new float[]{-0.4f, 0.2f});
        bottomLeftCoords.add(new float[]{-0.4f, 0f});
        bottomRightCoords.add(new float[]{-0.6f,0f});
        colors.add(RED);

        topCoords.add(new float[]{0, 0.6f});
        bottomLeftCoords.add(new float[]{0f, 0.4f});
        bottomRightCoords.add(new float[]{0.2f,0.4f});
        colors.add(RED);

        topCoords.add(new float[]{0, 0.6f});
        bottomLeftCoords.add(new float[]{0f, 0.4f});
        bottomRightCoords.add(new float[]{-0.2f,0.4f});
        colors.add(PURPLE);

        topCoords.add(new float[]{0, -0.6f});
        bottomLeftCoords.add(new float[]{0f, -0.4f});
        bottomRightCoords.add(new float[]{0.2f,-0.4f});
        colors.add(PURPLE);

        topCoords.add(new float[]{0.4f, 0.2f});
        bottomLeftCoords.add(new float[]{0.4f, 0f});
        bottomRightCoords.add(new float[]{0.6f,0f});
        colors.add(PURPLE);

        topCoords.add(new float[]{-0.4f, -0.2f});
        bottomLeftCoords.add(new float[]{-0.4f, 0f});
        bottomRightCoords.add(new float[]{-0.6f,0f});
        colors.add(PURPLE);

        topCoords.add(new float[]{0.2f, 0.4f});
        bottomLeftCoords.add(new float[]{0.2f, 0.2f});
        bottomRightCoords.add(new float[]{0.4f,0.2f});
        colors.add(YELLOW);

        topCoords.add(new float[]{-0.2f, -0.4f});
        bottomLeftCoords.add(new float[]{-0.2f, -0.2f});
        bottomRightCoords.add(new float[]{-0.4f,-0.2f});
        colors.add(YELLOW);

        topCoords.add(new float[]{-0.2f, 0.4f});
        bottomLeftCoords.add(new float[]{-0.2f, 0.2f});
        bottomRightCoords.add(new float[]{-0.4f,0.2f});
        colors.add(YELLOW);

        topCoords.add(new float[]{0.2f, -0.4f});
        bottomLeftCoords.add(new float[]{0.2f, -0.2f});
        bottomRightCoords.add(new float[]{0.4f,-0.2f});
        colors.add(YELLOW);
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
