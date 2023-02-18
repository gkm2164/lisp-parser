package lengine.util;

import lengine.runtime.LengineIterable;
import lengine.runtime.LengineIterator;
import lengine.runtime.LengineLazyValue;
import lengine.runtime.LengineStreamIterator;

import java.util.List;

public abstract class LengineStream implements LengineIterable, Nillable<LengineStream>, Addable<LengineStream>, Buildable<LengineStream, LengineStreamBuilder> {
  public static LengineStream cons(Object o, LengineStream lengineStream) {
    return new StreamCons(o, lengineStream);
  }

  @Override
  public LengineIterator iterator() {
    return new LengineStreamIterator(this);
  }

  public static LengineStream createFromList(List<Object> stream) {
    LengineStream current = StreamNil.get();

    for (Object item : stream) {
      current = current.ADD(item);
    }

    return current;
  }

  public static LengineStream create(Object obj) {
    if (obj instanceof LengineLazyValue) {
      return new UnresolvedStream((LengineLazyValue) obj);
    }

    return cons(obj, StreamNil.get());
  }

  @Override
  public LengineStream NIL() {
    return StreamNil.get();
  }

  @Override
  public LengineStream ADD(Object item) {
    if (this instanceof StreamNil) {
      return cons(item, NIL());
    } else if (this instanceof StreamCons) {
      StreamCons _cons = (StreamCons)this;
      return cons(_cons.getValue(), _cons.getNext().ADD(item));
    } else {
      return ((UnresolvedStream) this).force().ADD(item);
    }
  }

  @Override
  public LengineStreamBuilder BUILDER() {
    return new LengineStreamBuilder();
  }
}
