package org.eolang.benchmark;

public class Main {
    public static void main(String... args) {
        long total = Long.parseLong(args[0]);
        long sum = 0L;
        long start = System.currentTimeMillis();
        for (long i = 0; i < total; ++i) {
            sum += new Factorial(42).get();
        }
        System.out.printf("sum=%d time=%d\n", sum, System.currentTimeMillis() - start);
    }
}
