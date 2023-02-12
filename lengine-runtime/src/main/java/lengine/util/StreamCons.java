package lengine.util;

public class StreamCons extends LengineStream {
  private final Object value;
  private final LengineStream next;

  public StreamCons(Object value, LengineStream next) {
    this.value = value;
    this.next = next;
  }

  @Override
  public Long len() {
    return 1 + next.len();
  }

  public Object head() {
    return value;
  }

  public LengineStream tail() {
    return next;
  }

  public static StreamCons create(Object value, LengineStream next) {
    return new StreamCons(value, next);
  }

  public Object getValue() {
    return this.value;
  }

  public LengineStream getNext() {
    return this.next;
  }
}
