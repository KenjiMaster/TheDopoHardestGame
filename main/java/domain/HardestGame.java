package domain;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HardestGame implements Serializable {
    private List<Level> levels;
    private int numLevel = 0;
    public HardestGame(){
        levels = new ArrayList<>();
        try{
            LevelLoader loader = new LevelLoader();
            levels.add(loader.load("level1.txt"));
        }catch (Exception e){
            System.out.println("Error al cargar nivel");
        }
    }

    public Level getLevel(){
        return levels.get(numLevel);
    }

}
