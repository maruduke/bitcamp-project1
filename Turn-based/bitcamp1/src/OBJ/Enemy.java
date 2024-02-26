package OBJ;

import OBJ.Statistics.Stat;

import java.util.List;
import java.util.Random;
import java.util.Vector;

public abstract class Enemy extends Character{


    protected Enemy(Stat stat) {
        super(stat);
    }

    @Override
    public String activate(String command, Enemy enemy, List<Player> players) {
        Random random = new Random();
        String enemyTech = Integer.toString(random.nextInt(4) + 1);


        String comment = "";

        switch (enemyTech) {
            case "1":
                comment = tech1(enemy, players);
                break;
            case "2":
                comment = tech2(enemy, players);
                break;
            case "3":
                comment = tech3(enemy, players);
                break;
            case "4":
                comment = tech4(enemy, players);
                break;
        }
        return comment;
    }

}

