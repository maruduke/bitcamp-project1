package Server;

import OBJ.Player;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class SendAndReceive {
    List<Player> players; // 플레이어를 저장할 List<Player>
    String ThreadName = Thread.currentThread().getName(); // 실행중인 스레드의 이름을 저장

    public SendAndReceive(List<Player> players) {
        this.players = players;
    } // 플레이어 생성

    public void broadcast(String GameLog) throws Exception { // 플레이어의 게임 진행 로그 출력
        for (Player p : players) {
            Socket s = p.getSocket();

            OutputStream out = s.getOutputStream();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out) );
            System.out.println(ThreadName + ":" + GameLog);
            pw.println(GameLog);
            pw.flush();
        }
        Thread.sleep(1500);
    }

    public String turn(Player player, Socket socket) throws IOException {

        InputStream in = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String command = null;
//        player.turn = true;
        for (Player p : players) {
            Socket s = p.getSocket();
            OutputStream out = s.getOutputStream();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));

            if (s.equals(socket)) {
                pw.println(String.format("[1] %s\n",p.getStat().getTech1())
                    + String.format("[2] %s\n",p.getStat().getTech2())
                    + String.format("[3] %s\n",p.getStat().getTech3())
                    + String.format("[4] %s\n",p.getStat().getTech4())
                    + "+----------------------------------------------------------------------+\n"
//                    + String.format(">>  %s(%s)의 턴입니다. >>\n", p.getStat().getName(), p.getPosition()));
                    + ">> 나의 턴입니다! 원하는 커맨드를 두번 입력하세요. \n");


            } else {
                pw.println(String.format("[1] %s\n",p.getStat().getTech1())
                    + String.format("[2] %s\n",p.getStat().getTech2())
                    + String.format("[3] %s\n",p.getStat().getTech3())
                    + String.format("[4] %s\n",p.getStat().getTech4())
                    + "+----------------------------------------------------------------------+\n"
//                    + String.format(">>  %s(%s)의 턴입니다. >>\n", p.getStat().getName(), p.getPosition()));
                    + ">> 다른 플레이어의 턴입니다. \n");
            }
            pw.flush();
        }


        while(br.ready()) {
            br.readLine();
        }

        while(command == null) {
            command = br.readLine();
        }
        System.out.println("입력 출력 중" + command);
        return command;
    }



}

