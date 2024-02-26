package Server;

import Game.Game;
import Game.Poketmon;

import OBJ.Enemy;
import OBJ.EnemyCharacter.EnemyFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main(String[] args) {

        final int port = 9000;
        EnemyFactory enemyFactory = new EnemyFactory();
        System.out.println("server start...");

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            String mainThreadName = Thread.currentThread().getName();
            List<Socket> sockets = new ArrayList<>();

//            Game poketmon = null;

            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("socket connect successful");

                sockets.add(socket);

                // 클라이언트에게 기다리라는 데이터 전송
                OutputStream out = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out) );
                pw.println("참가자를 기다리고 있습니다.");
                pw.flush();

                if(sockets.size() == 2) {
                    Enemy enemy = enemyFactory.createEnemy();
                    Thread poketmon = new Thread(new GameStart(new ArrayList<>(sockets), new Poketmon(enemy)));
                    poketmon.start();
                    sockets.clear();
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}

class GameStart implements Runnable {
    List<Socket> sockets;
    Game poketmon;
    public GameStart(List<Socket> sockets, Poketmon poketmon) {
        this.sockets = sockets;
        this.poketmon = poketmon;
    }

    @Override
    public void run() {
        try {
            poketmon.enterRoom(sockets);
            System.out.println("---------------- game starting ----------------");
            poketmon.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
