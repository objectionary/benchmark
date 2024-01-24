package org.eolang.benchmark;

class A implements F {
  private int d;

  A(int paramInt) {
    this.d = paramInt;
  }

  public int foo() {
    return this.d + hashCode();
  }
}
