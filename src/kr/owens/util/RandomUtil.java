package kr.owens.util;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class RandomUtil {
    private static final Logger logger = Logger.getLogger(RandomUtil.class.getName());

    private RandomUtil() {
    }

    public static int[] getRandomIntegerArray(int length) {
        int[] resultArray = new int[length];
        int current = 0, random, tmp;

        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = i;
        }

        while (current < length) {
            random = ThreadLocalRandom.current().nextInt(0, length - 1);

            if (current != random) {
                tmp = resultArray[current];
                resultArray[current] = resultArray[random];
                resultArray[random] = tmp;
                current++;
            }
        }

        return resultArray;
    }
}
