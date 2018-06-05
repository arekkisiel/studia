import java.net.MalformedURLException;

public class Application {

    public static void main(String[] args) throws MalformedURLException {
        Game game = new Game();
        while(!game.isFinished())
            game.play();
    }
}
