package org.eolang.benchmark;

class A {
    private int d;
    A(int d) {
        this.d = d;
    }
    public int get() {
        return xget(this.d);
    }
    public static int xget(int xd) {
        if (xd <= 0) {
            return xd;
        }
        return xget(xd - 1);
   }
}

