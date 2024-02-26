package OBJ.PlayerCharacter;

import OBJ.Enemy;
import OBJ.Player;
import OBJ.Statistics.Stat;

import java.net.Socket;
import java.util.List;

public class Warrior extends Player {
    public Warrior(Stat stat, Socket socket, String position) {
        super(stat, socket, position);
    }

    @Override
    public String tech1(Enemy enemy, List<Player> characters) { // 기본공격
        int damage = enemy.hit(this.stat.getAttackPoint());
//        String log = enemy.getStat().getName() + "에게" +enemy.getStat().getTech2() +"를 사용하였습니다. 피해량: " + damage;
        String log = this.getStat().getTech1() + "!!!\n" + enemy.getStat().getName() + "이 " + damage + "의 피해를 입었습니다.";
        return log;
    }

    @Override
    public String tech2(Enemy enemy, List<Player> characters) { // 버프
        this.strength(10);
        this.usePp(5);
        return this.getStat().getName() + "님의 공격력이 상승하였습니다.";
    }

    @Override
    public String tech3(Enemy enemy, List<Player> characters) { // 기술1
        int damage = enemy.hit(this.stat.getAttackPoint()*2);
        this.usePp(5);
        String log = this.getStat().getTech3() + "!!!\n" + enemy.getStat().getName() + "이 " + damage + "의 피해를 입었습니다.";
        return log;
    }

    @Override
    public String tech4(Enemy enemy, List<Player> characters) { // 필살기
        int damage = enemy.hit(this.stat.getAttackPoint()*3);
        this.usePp(5);
        String log = this.getStat().getTech4() + "!!!\n" + enemy.getStat().getName() + "이 " + damage + "의 피해를 입었습니다.";
        return log;
    }
}
