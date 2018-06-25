import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;


import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        final GLCanvas glcanvas = new GLCanvas(capabilities);
        glcanvas.addGLEventListener(new Triangles());
        glcanvas.setSize(1000, 1000);

        final JFrame frame = new JFrame ();

        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);

    }

}