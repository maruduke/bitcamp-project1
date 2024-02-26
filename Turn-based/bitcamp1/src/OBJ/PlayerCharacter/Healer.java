package OBJ.PlayerCharacter;

import OBJ.Enemy;
import OBJ.Player;
import OBJ.Statistics.Stat;

import java.net.Socket;
import java.util.List;

public class Healer extends Player {
    public Healer(Stat stat, Socket socket, String position) {
        super(stat, socket, position);
    }

    @Override
    public String tech1(Enemy enemy, List<Player> characters) {
        int damage = enemy.hit(this.stat.getAttackPoint());
        String log = enemy.getStat().getName() + "을 공격하였습니다. 데미지: " + damage;
        return log;
    }

    @Override
    public String tech2(Enemy enemy, List<Player> characters) {

        int damage = enemy.hit(this.stat.getAttackPoint() + 10);
        String log = enemy.getStat().getName() + "에게" +enemy.getStat().getTech2() +"를 사용하였습니다. 피해량: " + damage;
        return log;
    }

    @Override
    public String tech3(Enemy enemy, List<Player> characters) {
        this.strength(10);
        return this.getStat().getName() + "의 공격력이 상승하였습니다.";
    }

    @Override
    public String tech4(Enemy enemy, List<Player> characters) {
        this.heal(10);
        return this.getStat().getName() + "이 회복을 사용하였습니다.";
    }

}
