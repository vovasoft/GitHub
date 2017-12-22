package com.xcloudeye.stats.util;

import java.util.Random;

public class RandomUtil {
	public int random(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }
	
	public int randomInt(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }
	
	public double doubleRandome(){
		double s = ((double)random(0, 100))/100.00 ;
		return s;
	}
	
	public double randomBigZero(int min, int max) {
        double s = random(min,max) + ((double)random(0, 100))/100.00 ;
        return s;
    }

}
