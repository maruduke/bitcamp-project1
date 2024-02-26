package OBJ.EnemyCharacter;

import OBJ.Character;
import OBJ.Enemy;
import OBJ.Player;
import OBJ.Statistics.Stat;

import java.util.List;
import java.util.Random;

public class Devil extends Enemy {

    Random random= new Random();
    public Devil(Stat stat) {
        super(stat);
    }

    @Override
    public String tech1(Enemy enemy, List<Player> characters) {

        int randomVal = random.nextInt(10); // 0 이상 10 미만의 랜덤 정수

        int random = (randomVal) % characters.size();
        System.out.println("random code:" + random);
        int damage = characters.get(random).hit(this.stat.getAttackPoint());

        String log = this.getStat().getName() + "의 " + this.getStat().getTech1() + "!!!\n" +
            characters.get(random).getStat().getName() + "님이 " + damage + "의 피해량을 입었습니다.";
        return log;
    }

    @Override
    public String tech2(Enemy enemy, List<Player> characters) {
        int random = ((int) Math.random()*10) % characters.size();
        int damage = characters.get(random).hit(this.getStat().getAttackPoint()*2);
        this.usePp(10);
        String log = this.getStat().getName() + "의 " + this.getStat().getTech2() + "!!!\n" +
           characters.get(random).getStat().getName() + "님이 " + damage + "의 피해량을 입었습니다.";
        return log;
    }

    @Override
    public String tech3(Enemy enemy, List<Player> characters) {
        this.usePp(10);
        this.defence(7);
        return this.getStat().getName() + "의 " + this.getStat().getTech3() + "!!!\n" +
            this.getStat().getName() + "의 방어력이 상승 했습니다.";
    }

    @Override
    public String tech4(Enemy enemy, List<Player> characters) {
        this.usePp(10);
        enemy.heal(10);
        return this.getStat().getName() + "의 " + this.getStat().getTech4() + "!!!\n" +
            this.getStat().getName() + "의 체력이 회복 되었습니다.";
    }

}
