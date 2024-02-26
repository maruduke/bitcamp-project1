package pro01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {
	// ClientHandler 객체들을 저장. 클라이언트가 접속할 때마다 객체를 생성해서 리스트에 추가
	public static List<ClientHandler> clients = new ArrayList<>();
	public static final int PORT = 9797;

	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("서버가 열렸습니다. 플레이어를 기다려주세요...");

			while (clients.size() < 2) { // 클라이언트 접속을 2명으로 제한
				Socket socket = serverSocket.accept();
				System.out.println("플레이어가 연결되었습니다 : " + socket);

				ClientHandler clientHandler = new ClientHandler(socket);
				clients.add(clientHandler);
				clientHandler.start(); // 클라이언트 연결을 위한 스레드 실행
			}

			// 2명의 클라이언트가 모두 연결되면 게임 시작
			for (ClientHandler client : clients) {
				client.sendStartGameMessage();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class ClientHandler extends Thread {
	private static List<String> duplicateValue = new ArrayList<>(); // 중복 검사하는 컬렉션 생성
	private Socket socket;
	private Connection connection;
	private boolean isRun = true;
	private BufferedReader br;
	private PrintWriter pw;
	private int playerNumber;
	private int count = 0; // 맞춘 개수를 저장할 변수
	private static int player1CorrectCount = 0; // 플레이어 1의 맞춘 개수를 저장할 변수
	private static int player2CorrectCount = 0; // 플레이어 2의 맞춘 개수를 저장할 변수

	public ClientHandler(Socket socket) {
		this.socket = socket;
		try {
			// 클라이언트와 통신을 위한 출력 스트림을 생성
			this.pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getCorrectCount() {
		return count;
	}

	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 입력 스트림 설정
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream())); // 출력 스트림 설정

			// 플레이어 번호 할당
			playerNumber = Server.clients.indexOf(this) + 1; 

			// 호선 선택
			int lineNumber = selectLineNumber();
			System.out.println("플레이어 " + playerNumber + "가 선택한 호선은 " + lineNumber + "호선 입니다");
			pw.println("DB에서 지하철 역 정보를 가져옵니다...");
			pw.flush();
			List<String> dbSubwaylist = DBsubwaylist(lineNumber); // DB에 저장된 해당 호선의 역 이름을 저장
			pw.printf("DB로부터 지하철 데이터를 성공적으로 가져왔습니다!\n3초 뒤에 게임이 시작됩니다.\n");
			pw.flush();
			Thread.sleep(3000);
			pw.println("게임을 시작합니다!");
			pw.flush();

			// 게임 진행
			while (isRun) {
				String userInput = getUserInputTimer(); // 제한 시간을 설정한 로직을 userInput이라는 지역변수에 저장
				if (userInput == null) {
					pw.println("시간이 초과되었습니다.");
					pw.println("종료");
					pw.flush();
					isRun = false;
				} else {
					play(dbSubwaylist, userInput);
				}
			}

			// 게임 종료 후 맞춘 역 개수를 출력
			System.out.println(playerNumber + "번 플레이어 정답 개수 : " + count);
			if (playerNumber == 1) {
				player1CorrectCount = count;
			} else if (playerNumber == 2) {
				player2CorrectCount = count;
			}
			System.out.println("플레이어 " + playerNumber + "의 게임이 종료됩니다.");
		} catch (IOException | InterruptedException e) { // 입출력 오류 및 스레드가 중단될 경우
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private int selectLineNumber() throws IOException {  // 사용자가 호선 선택하는 메소드
		try {
			return Integer.parseInt(br.readLine());		 // 사용자가 입력한 것을 int형으로 형변환
		} catch (NumberFormatException e) { 			 // 문자열 입력 시 0을 반환
			return 0;
		}	
	}

	private String getUserInputTimer() throws IOException {
		pw.println("제한 시간 5초 내에 역 이름을 입력하세요 >> ");
		pw.flush();
		try {
			socket.setSoTimeout(5000); // 서버 측 5초 타임아웃 설정
			return br.readLine();
		} catch (IOException e) {
			return null;
		} finally {
			socket.setSoTimeout(0); // 타임아웃 설정 초기화(계속해서 5초라는 제한시간을 부여하기 위함)
		}
	}

	private void play(List<String> dbSubwaylist, String userInput) throws IOException {
		if (duplicateValue.contains(userInput)) { // 중복 값이 담긴 리스트와 사용자 입력 비교
			pw.println("중복 입력하였습니다. 게임을 종료합니다.");
			pw.println("종료");
			pw.flush();
			isRun = false;
		} else if (dbSubwaylist.contains(userInput)) { // DB와 사용자 입력 비교
			count++; 								   // 맞춘 개수 증가
			duplicateValue.add(userInput); 			   // 중복 검사에 활용하기 위해서 정답인 역을 리스트로 보관
			pw.println("정답!!");
			pw.flush();
		} else {
			pw.println("오답입니다! 게임을 종료합니다.");
			pw.println("종료");
			pw.flush();
			isRun = false;
		}
	}

	public void sendStartGameMessage() {
		pw.println("게임을 시작합니다!");
		pw.flush();
	}

	public List<String> DBsubwaylist(int lineNumber) {
		List<String> dbSubwaylist = new ArrayList<>();
		try {
			// DB에 접근해서 사용자가 입력한 호선에 대한 값을 가져올 수 있게 한다.
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SubwayInformation", "root",
					"12345678");
			PreparedStatement pstmt = connection
					.prepareStatement("SELECT station_name FROM station_info WHERE line_number = ?");
			pstmt.setInt(1, lineNumber); // 해당 DB에 있는 line_Number의 호선 번호를 첫 번째 매개변수에 담겠다.
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) { // 해당 호선에 대한 역 이름을 전부 넣는다.
				dbSubwaylist.add(rs.getString("station_name"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close(); // 연결 종료
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dbSubwaylist; // 해당 호선의 지하철 역 이름이 담긴 리스트를 반환
	}

	public static int getPlayer1CorrectCount() {
		return player1CorrectCount;
	}

	public static int getPlayer2CorrectCount() {
		return player2CorrectCount;
	}
}
