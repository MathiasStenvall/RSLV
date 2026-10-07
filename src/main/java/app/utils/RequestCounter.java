package app.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class RequestCounter {

    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void increment(){
        counter.incrementAndGet();
    }

    public static int getCounter(){
        return counter.get();
    }

}
