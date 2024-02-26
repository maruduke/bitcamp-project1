package OBJ.PlayerCharacter;

import OBJ.Player;
import OBJ.Statistics.Stat;
import Repository.PlayerRepository;

import java.net.Socket;

public class PlayerFactory {
    // player status(DB 값), socket(socket 연결) 을 사용하여 PlayerData 생성 및 반환

    PlayerRepository repository = new PlayerRepository();

    public Player createPlayer(String name, String position, Socket socket) {

        Player player = null;
        if(position.toLowerCase().equals("warrior")||position.equals("1")) {
            Stat stat = repository.createPlayerStat(name, "warrior");
            // 전사 직업 생성
            player = new Warrior(stat,socket,"warrior");
        }
        else if(position.toLowerCase().equals("wizard")||position.equals("2")) {
            Stat stat = repository.createPlayerStat(name, "wizard");
            // 전사 직업 생성
            player = new Warrior(stat,socket,"wizard");
        }
        else {
            Stat stat = repository.createPlayerStat(name, "warrior");
            // 직업이 존재하지 않을 경우 warrior 생성
            player = new Warrior(stat, socket, "warrior");
        }

        return player;
    }
}
