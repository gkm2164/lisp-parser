package lengine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.function.BiPredicate;

import lengine.runtime.Sequence;

public class Prelude {

  public static Object cast(Object from, Class to) {
    if (to.equals(Character.class)) {
      return castChar(from);
    } else if (to.equals(Long.class)) {
      return castLong(from);
    } else if (to.equals(Double.class)) {
      return castDouble(from);
    } else if (to.equals(String.class)) {
      return castString(from);
    } else if (to.equals(Sequence.class)) {
      return castSequence(from);
    }

    throw new RuntimeException("unable to cast");
  }


  public static Object cast_str(Object from) {
    return cast(from, String.class);
  }

  public static Object cast_int(Object from) {
    return cast(from, Long.class);
  }

  public static Object cast_double(Object from) {
    return cast(from, Double.class);
  }

  public static Object cast_char(Object from) {
    return cast(from, Character.class);
  }

  private static Sequence castSequence(Object from) {
    if (from instanceof Sequence) {
      return (Sequence) from;
    }
    Sequence newSeq = new Sequence();
    newSeq.add(from);
    return newSeq;
  }

  private static Character castChar(Object from) {
    if (from instanceof Character) {
      return (Character) from;
    } else if (from instanceof Long) {
      return (char) ((Long) from).intValue();
    } else if (from instanceof Double) {
      return (char) ((Double) from).intValue();
    }

    throw new RuntimeException(String.format("unable to cast from %s to Character", from.getClass()));
  }

  private static Long castLong(Object from) {
    if (from instanceof Character) {
      return (long) (Character) from;
    } else if (from instanceof Long) {
      return (Long) from;
    } else if (from instanceof Double) {
      return ((Double) from).longValue();
    } else if (from instanceof String) {
      return Long.parseLong((String) from);
    }

    throw new RuntimeException(String.format("unable to cast from %s to Character", from.getClass()));
  }

  private static Double castDouble(Object from) {
    if (from instanceof Character) {
      return (double) (Character) from;
    } else if (from instanceof Long) {
      return ((Long) from).doubleValue();
    } else if (from instanceof Double) {
      return (Double) from;
    } else if (from instanceof String) {
      return Double.parseDouble((String) from);
    }

    throw new RuntimeException(String.format("unable to cast from %s to Character", from.getClass()));
  }

  private static String castString(Object from) {
    return from.toString();
  }

  public static Object add(Object a, Object b) {
    Class<?> largerType = getLargerType(a.getClass(), b.getClass());
    Object x = cast(a, largerType);
    Object y = cast(b, largerType);

    if (x instanceof Character) {
      return (Character) x + (Character) y;
    } else if (x instanceof Long) {
      return (Long) x + (Long) y;
    } else if (x instanceof Double) {
      return (Double) x + (Double) y;
    } else if (x instanceof String) {
      return x + (String) y;
    } else if (x instanceof Sequence) {
      Sequence newSeq = new Sequence();
      newSeq.append((Sequence) x);
      newSeq.append((Sequence) y);
      return newSeq;
    }

    throw new RuntimeException("Can't add");
  }

  public static Object sub(Object a, Object b) {
    Class<?> largerType = getLargerType(a.getClass(), b.getClass());
    Object x = cast(a, largerType);
    Object y = cast(b, largerType);

    if (x instanceof Character) {
      return (Character) x - (Character) y;
    } else if (x instanceof Long) {
      return (Long) x - (Long) y;
    } else if (x instanceof Double) {
      return (Double) x - (Double) y;
    }

    throw new RuntimeException("Can't subtract");
  }

  public static Object mult(Object a, Object b) {
    Class<?> largerType = getLargerType(a.getClass(), b.getClass());
    Object x = cast(a, largerType);
    Object y = cast(b, largerType);

    if (x instanceof Character) {
      return (Character) x * (Character) y;
    } else if (x instanceof Long) {
      return (Long) x * (Long) y;
    } else if (x instanceof Double) {
      return (Double) x * (Double) y;
    }

    throw new RuntimeException("Can't multiply");
  }

  public static Object div(Object a, Object b) {
    Class<?> largerType = getLargerType(a.getClass(), b.getClass());
    Object x = cast(a, largerType);
    Object y = cast(b, largerType);

    if (x instanceof Character) {
      return (Character) x / (Character) y;
    } else if (x instanceof Long) {
      return (Long) x / (Long) y;
    } else if (x instanceof Double) {
      return (Double) x / (Double) y;
    }

    throw new RuntimeException("Can't divide");
  }


  private static Class<?> getLargerType(Class<?> a, Class<?> b) {
    return getRank(a) > getRank(b) ? a : b;
  }

  private static int getRank(Class<?> a) {
    if (a.equals(Character.class)) {
      return 0;
    } else if (a.equals(Long.class)) {
      return 1;
    } else if (a.equals(Double.class)) {
      return 2;
    } else if (a.equals(String.class)) {
      return 3;
    } else if (a.equals(Sequence.class)) {
      return 4;
    }

    throw new RuntimeException("Unable to decide rank for type: " + a.getName());
  }

  public static Object take(Long n, Sequence seq) {
    return seq.take(n.intValue());
  }

  public static Object drop(Long n, Sequence seq) {
    return seq.drop(n.intValue());
  }

  public static Object flatten(Sequence seq) {
    return seq.flatten();
  }

  public static String readLine() throws IOException {
    return new BufferedReader(new InputStreamReader(System.in)).readLine();
  }

  private static Boolean compareFunction(Object a, Object b, BiPredicate<Comparable, Comparable> predicate) {
    Class<?> largerType = getLargerType(a.getClass(), b.getClass());

    Object aobj = cast(a, largerType);
    Object bobj = cast(b, largerType);

    if (!(aobj instanceof Comparable) || !(bobj instanceof Comparable)) {
      throw new RuntimeException("Unable to compare the given object: " + a + ", " + b);
    }

    return predicate.test((Comparable) a, (Comparable) b);
  }

  public static Boolean lt(Object a, Object b) {
    return compareFunction(a, b, (x, y) -> x.compareTo(y) < 0);
  }

  public static Boolean le(Object a, Object b) {
    return compareFunction(a, b, (x, y) -> x.compareTo(y) <= 0);
  }

  public static Boolean gt(Object a, Object b) {
    return compareFunction(a, b, (x, y) -> x.compareTo(y) > 0);
  }

  public static Boolean ge(Object a, Object b) {
    return compareFunction(a, b, (x, y) -> x.compareTo(y) >= 0);
  }

  public static Boolean eq(Object a, Object b) {
    return Objects.equals(a, b);
  }

  public static Boolean neq(Object a, Object b) {
    return !Objects.equals(a, b);
  }

  public static Boolean and(Object a, Object b) {
    if (!(a instanceof Boolean)) {
      throw new RuntimeException("first parameter is not boolean");
    }
    if (!(b instanceof Boolean)) {
      throw new RuntimeException("second parameter is not boolean");
    }

    return ((Boolean) a) && ((Boolean) b);
  }

  public static Boolean or(Object a, Object b) {
    if (!(a instanceof Boolean)) {
      throw new RuntimeException("first parameter is not boolean");
    }
    if (!(b instanceof Boolean)) {
      throw new RuntimeException("second parameter is not boolean");
    }

    return ((Boolean) a) || ((Boolean) b);
  }

  public static Boolean not(Object a) {
    if (!(a instanceof Boolean)) {
      throw new RuntimeException("first parameter is not boolean");
    }

    return !((Boolean) a);
  }
}