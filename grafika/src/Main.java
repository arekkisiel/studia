import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;


import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int BUFFER_ID = 55121;
    private static List<float[][]> coordsList = new ArrayList<>();
    private static List<Triangles> trianglesList = new ArrayList<>();



    //from openGL library
    private static int GL_TRIANGLES = 4;
    private static int GL_UNSIGNED_INT = 5125;

    public static void main(String[] args) {
        //getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        glcanvas.addGLEventListener(new Triangles());



//        GL.glDrawElements(GL_TRIANGLES, trianglesList.size(), GL_UNSIGNED_INT, BUFFER_ID);

        glcanvas.setSize(1400, 1400);

        //creating frame
        final JFrame frame = new JFrame ("straight Line");

        //adding canvas to frame
        frame.getContentPane().add(glcanvas);

        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);

    }

}