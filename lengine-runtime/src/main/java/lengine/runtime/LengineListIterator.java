package lengine.runtime;

public class LengineListIterator implements LengineIterator {
  private LengineList current;

  LengineListIterator(LengineList from) {
    this.current = from;
  }

  public LengineList _this() {
    return current;
  }

  @Override
  public boolean hasNext() {
    return current instanceof Cons;
  }

  @Override
  public Object peek() {
    return current.head();
  }

  @Override
  public Object next() {
    LengineList ret = current;
    if (current instanceof Nil) {
      throw new RuntimeException("This is end of the list.");
    }

    current = ((Cons) current).next;
    return ((Cons)ret).item;
  }

}
