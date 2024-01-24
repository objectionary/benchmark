package org.eolang.benchmark;

public final class BA {
  private final int d;

  public BA(int paramInt) {
    this.d = paramInt;
  }

  public int foo() {
    return this.d + 1;
  }

  public int bar() {
    return foo() + 2;
  }
}
