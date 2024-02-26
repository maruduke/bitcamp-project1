package OBJ;

import OBJ.Statistics.Stat;

import java.util.List;
import java.util.Vector;

public abstract class Character {

    public Stat stat;
    public boolean turn = false;

    Character(Stat stat) {
        this.stat = stat;
    }


    public boolean getTurn () { this.turn = !turn; return this.turn;}
    public int hit(int damage) {
        // 공격 받음
        return this.stat.hit(damage);
    }
    public int heal(int heal) {
        // 치료 받음
        return this.stat.heal(heal);
    }

    public int strength(int up) {
        return this.stat.strength(up);
    }
    public int defence(int up) {
        return this.stat.defence(up);
    }
    public int usePp(int usePp){
        return this.stat.usePp(usePp);
    }

    public boolean isDead() {
        return stat.getHp() <= 0 ? true : false;
    }

    public abstract String tech1(Enemy enemy, List<Player> characters);
    public abstract String tech2(Enemy enemy, List<Player> characters);
    public abstract String tech3(Enemy enemy, List<Player> characters);
    public abstract String tech4(Enemy enemy, List<Player> characters);



    public abstract String activate(String command, Enemy enemy, List<Player> players);

    public Stat getStat() {
        return this.stat;
    }


}
