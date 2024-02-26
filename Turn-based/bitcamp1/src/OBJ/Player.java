package OBJ;

import OBJ.Statistics.Stat;

import java.net.Socket;
import java.util.List;
import java.util.Vector;

public abstract class Player extends Character {

    String position;
    Socket socket;

    protected Player(Stat stat, Socket socket, String position) {
        super(stat);
        this.socket = socket;
        this.position = position;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public String activate(String command, Enemy enemy, List<Player> players) {
        String comment = null;

        if(command.equals("1"))
            comment = tech1(enemy, players);
        else if(command.equals("2") )
            comment = tech2(enemy, players);
        else if(command.equals("3"))
            comment = tech3(enemy, players);
        else if(command.equals("4"))
            comment = tech4(enemy,players);
        else
            comment = tech1(enemy, players);
        return comment;
    }
    public String getPosition() { return this.position;}

}
