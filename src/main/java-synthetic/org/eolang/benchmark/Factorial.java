package org.eolang.benchmark;

class Factorial {
    private int d;
    Factorial(int d) {
        this.d = d;
    }
    public int get() {
        return Factorial.xget(this.d);
    }
    public static int xget(int xd) {
        if (xd <= 1) {
            return 1;
        }
        return xget(xd - 1) * xd;
   }
}

