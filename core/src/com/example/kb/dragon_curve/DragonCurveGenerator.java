package com.example.kb.dragon_curve;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Karlo on 2017-04-19.
 */

public class DragonCurveGenerator {

    /*
    * TURN LEFT is represented by 1.
    * TURN RIGHT is represented by 0.
    * */

    public static List<Integer> getSequence(int iterations) {
        List<Integer> turnSequence = new ArrayList<Integer>();
        List<Integer> previous;
        turnSequence.add(1); // that is base
        for (int i = 0; i < iterations; i++) {
            previous = new ArrayList<Integer>(turnSequence);
            turnSequence.add(1);
            int indexToChange = previous.size() / 2;
            previous.set(indexToChange, Math.abs(previous.get(indexToChange) - 1));
            turnSequence.addAll(previous);
        }
        return turnSequence;
    }

}
