package org.eolang.benchmark;

class B {
  private final F f;
  
  B(F paramF) {
    this.f = paramF;
  }
  
  int bar() {
    return this.f.foo() + 2;
  }
}
