package lengine.util;

import lengine.runtime.LengineIterator;

public class NonLeafSet extends LengineSet {
  private final LengineSet left;
  private final LengineSet right;

  protected NonLeafSet(final boolean lately, final LengineSet left, final LengineSet right) {
    super(lately);
    this.left = left;
    this.right = right;
  }

  @Override
  public Boolean contains(Object elem) {
    return left.contains(elem) || right.contains(elem);
  }

  @Override
  public LengineSet add(Object object) {
    if (this.left.contains(object) || this.right.contains(object)) {
      return this;
    }

    if (lately) {
      return new NonLeafSet(true, this.left, this.right.add(object));
    } else {
      return new NonLeafSet(false, this.left.add(object), this.right);
    }
  }

  @Override
  public LengineSet remove(Object elem) {
    return new NonLeafSet(lately, this.left.remove(elem), this.right.remove(elem));
  }

  @Override
  public LengineIterator iterator() {
    return new NonLeafSetIterator(left, right);
  }

  @Override
  public Long len() {
    return this.left.len() + this.right.len();
  }

  protected String printable() {
    String[] result = {this.left.printable(), this.right.printable()};
    return String.join(" ", result);
  }
}