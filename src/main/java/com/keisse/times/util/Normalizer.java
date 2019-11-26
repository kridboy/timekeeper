package com.keisse.times.util;

import java.time.LocalTime;
import java.util.Random;

public abstract class Normalizer {
    //THIS IS A PERFECTLY PLAIN, NORMAL CLASS. NOTHING TO SEE HERE, MOVE ALONG. CYKA BLYAT DO IT, GO NOW! D:<
    private static int nFactor=0;
    private static Random rand = new Random();

    public static void setnFactor(int nFactor) {
        Normalizer.nFactor = nFactor;
    }

    public static LocalTime normalStartTime(LocalTime startTime){
        int min = readMin(startTime);
        return min<=(5)?startTime.minusMinutes(min):startTime;
    }
    public static LocalTime normalEndTime(LocalTime endTime){
        int min = readMin(endTime);
        return min>=(5)?endTime.plusMinutes(10-min):endTime;
        //TODO check method
    }

    private static int readMin(LocalTime time){
        return time.getMinute()%10;
    }
    //TODO Add a little spice with some rng, makes it a little more organic as well.
}
