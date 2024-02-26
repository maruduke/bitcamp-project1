package pro01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 9797;

    public static void main(String[] args) throws InterruptedException {
        try {
            // 서버에 연결
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("서버에 연결되었습니다.");

            // 게임 설명 메뉴 출력
            String pr_menu = "|+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n"
                          + "+        ===========지하철 게임===========        +\n"
                          + "+-----------------------------------------------------+\n"
                          + "|              게임 이용 방법                     \n"
                          + "| 1) 1번 플레이어가 게임을 원하는 호선을 입력           \n"
                          + "| 2) 숫자 0 입력시 게임이 시작됩니다.                 \n"
                          + "| 3) 제한시간 5초안에 해당 호선의 역을 입력            \n"
                          + "|   !! ===========!!!주의사항!!!=========== !!   \n"
                          + "|        !!답 입력시 '역'은 생략해주세요!!          \n"
                          + "|Space bar나 .을 사용하면 오답 처리될 수 있습니다!!   \n"
                          + "+-----------------------------------------------------+\n"
                          + "|            1~9호선 중에서 선택해주세요.           \n"
                          + "|+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n"
                          + "호선을 선택하세요 >> ";
            System.out.println(pr_menu);

            // 사용자 입력을 받기 위한 BufferedReader 생성
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            // 소켓으로부터 입력을 받기 위한 BufferedReader 생성
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 소켓을 출력 하기 위한 PrintWriter 생성
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 호선 선택
            int lineNumber = Integer.parseInt(keyboard.readLine());
            pw.println(lineNumber);
            pw.flush();

            // 게임 시작 메시지 출력
            String startMessage = br.readLine();
            System.out.println(startMessage);
            
            while (true) {
                String message = br.readLine();

                if (message.equals("제한 시간 5초 내에 역 이름을 입력하세요 >> ")) {
                    System.out.println(message);

                    Thread userInputThread = new Thread(() -> {
                        try {
                            String userInput = getUserInputTimer(keyboard);
                            pw.println(userInput);
                            pw.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    userInputThread.start();

                    try {
                        userInputThread.join(5000); 	 // 5초 동안 대기
                        if (userInputThread.isAlive()) { // 스레드가 아직 실행 중인 경우
                            userInputThread.interrupt(); // 인터럽트하여 작업 중단
                            System.out.println();
                            System.out.println("시간이 초과되었습니다.");
                            System.out.println("Enter 입력 시 프로그램이 종료됩니다.");
                            pw.flush();
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (message.equals("게임 종료! 플레이어 ")) {
                    System.out.println(message);
                    
                } else if (message.equals("종료")) {
                    System.out.println(message);
                    break;
                } else {
                    System.out.println(message);
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 제한 시간 내에 사용자 입력을 받기 위한 메소드
    private static String getUserInputTimer(BufferedReader keyboard) throws IOException {
        return keyboard.readLine(); // 사용자가 입력한 걸 1줄씩 받아온다.
    }
}
