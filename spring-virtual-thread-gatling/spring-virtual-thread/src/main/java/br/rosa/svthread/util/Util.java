package br.rosa.svthread.util;

public class Util {
    private Util() {
    }

    public static void threadSleep(long timer) {
        try {
            Thread.sleep(timer);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
