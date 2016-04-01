package in.divvela.legoapp;

import android.util.Log;

/**
 * Created by KH121 on 4/1/2016.
 */
public class Randomizer {

    private static Randomizer randomizer;

    private Randomizer(){

    }

    public static synchronized Randomizer getInstance() {
        if (randomizer == null) {
            randomizer = new Randomizer();
        }
        return  randomizer;
    }

    public boolean trail(double prob) {
        double rand = Math.random();
        if (((rand / prob)) > 1)
            return false;
        return true;
    }

    /**
     *
     *
     * @param size - Size of the return int array.
     * @param seed - To create randomness.
     * @param scale - output int will be less then this value.
     * @return Random integers of given size with values less then scale with given seed for randomness.
     */
    public int[] getRandomInts(int size, int seed, int scale) {
        int[] randList = new int[size];
        int i = 0, j=0;
        while (j < size) {
            if (trail( (double) 1 / seed)) {
                randList[j]= (i % scale);
                j++;
            }
            i++;
        }
        Log.d("Randomizer:" , randList.toString());
        return randList;
    }

}
