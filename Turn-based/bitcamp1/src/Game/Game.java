package Game;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public interface Game {

    public void enterRoom(List<Socket> socket) throws IOException;
    public void start() throws Exception;

}
