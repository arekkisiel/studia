import org.eclipse.jetty.servlet.ServletContextHandler;
import server.HandlerImpl;

public class ChatServer {

    public static void main(String[] args) throws Exception {
            org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(10000);
            ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);

            handler.setContextPath("/");
            handler.addServlet(HandlerImpl.class, "/chat");

            server.setHandler(handler);
            server.start();
    }
}
