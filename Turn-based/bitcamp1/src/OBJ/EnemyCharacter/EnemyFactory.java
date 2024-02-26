package OBJ.EnemyCharacter;

import OBJ.Enemy;
import OBJ.Player;
import OBJ.PlayerCharacter.Warrior;
import OBJ.Statistics.Stat;
import Repository.EnemyRepository;
import java.util.Random;

public class EnemyFactory {

    EnemyRepository enemyRepository = new EnemyRepository();


    public Enemy createEnemy() {
        Random random = new Random();

        Enemy enemy = null;
        int randomVal = random.nextInt(4)+1;

        Stat stat = enemyRepository.createEnemyStat( randomVal );

        if(stat.getName().equals("Devil")) {
            enemy = new Devil(stat);
        }
        else {
            enemy = new Devil(stat);
        }

        return enemy;
    }

}
