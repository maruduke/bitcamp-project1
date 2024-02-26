import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import org.json.JSONObject;

public class main {

    public static void main(String[] args) {
        final String IP = "192.168.0.66";
        final int PORT = 9000;
        Socket socket = null;


        Scanner sc = new Scanner(System.in);
        PrintWriter pw = null;
        BufferedReader br = null;
        String playerInfo = creatPlayer(sc, pw);

        try {

            socket = new Socket(IP, PORT); // 서버에 연결
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sc = new Scanner(System.in);
            // worker thread 생성, 서버 수신 담당
            ReceiveThread rt = new ReceiveThread(br);
            rt.start();
            // -- 커넥션
            send(playerInfo, pw);

            boolean isRun = true;
            while (rt.isAlive()) {
                String command = sc.nextLine();
                pw.println(command);
                pw.flush();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage() + "test");
        } finally {
            if (socket != null)
                try {
                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
    }















    public static String creatPlayer(Scanner sc, PrintWriter pw) {
        System.out.print("닉네임 설정 >> ");
        String name = sc.next();
        System.out.print("직업 선택 >> ");
        String position = sc.next();
        JSONObject packetObj = new JSONObject();
        packetObj.put("cmd", "Data");
        packetObj.put("name", name);
        packetObj.put("position", position);
        String packet = packetObj.toString();

        return packet;
    }

    public static void send(String str,PrintWriter pw){
        pw.println(str);
        pw.flush();
    }

    public static String getSelectMenu(Scanner sc) {
        System.out.println();
        System.out.println("------------------------");
        System.out.println("1. 공격");
        System.out.println("2. 방어");
        System.out.println("3. 스킬1");
        System.out.println("4. 스킬2");
        System.out.println("4. 스킬3");
        System.out.print("번호 입력 >> ");
        String sel = sc.nextLine();

        return sel;
    }

//    public void sendCommand(PrintWriter pw, String sel) {
//
//        JSONObject packetObj = new JSONObject();
//        packetObj.put("cmd");
//        String packet = packetObj.toString();
//
//        pw.println(packet);
//        pw.flush();
//    }


    public void processPacket(JSONObject packetObj) throws Exception {
        String preCmd = packetObj.getString("cmd");
        String cmd = new String(preCmd.getBytes("utf-8"), "utf-8");
        if (cmd.equals("creatPlayer")) {
            String ack = packetObj.getString("ack");
            String data = packetObj.getString("data");
            if (ack.equals("ok")) {
                System.out.println(data);
            } else if (ack.equals("fail")) {
                System.out.println("플레이어 생성 실패 실패");
            }

//            JsonChatClient.mainState = ClientState.NONE;
        }
    }

    public static void processServerResponse(BufferedReader input, PrintWriter pw) {
        try {
            Scanner sc = new Scanner(System.in);
            // 서버에 요청 보내기
            pw.println(sc);
            pw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class ReceiveThread extends Thread {

    BufferedReader br;

    ReceiveThread(BufferedReader br) {
        this.br = br;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String packet = br.readLine();
                if (packet == null || packet.equals("Players defeat") || packet.equals("Players Win")) {
                    System.out.println(packet);
                    break;
                }
                System.out.println(packet);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("엔터키를 눌러 종료하세요.");

    }
}



class Packet {
    private String cmd;
    private String ack;
    private String data;
}
