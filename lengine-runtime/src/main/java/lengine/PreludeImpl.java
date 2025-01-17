package lengine;

import lengine.types.collections.traits.AddFront;
import lengine.types.collections.traits.AddRear;
import lengine.concurrency.LengineChannel;
import lengine.concurrency.LengineFuture;
import lengine.types.functions.LengineLambda0;
import lengine.types.functions.LengineLambda1;
import lengine.types.functions.LengineLambda2;
import lengine.types.functions.LengineLambda3;
import lengine.https.HttpServerBuilder;
import lengine.types.ComplexNumber;
import lengine.types.collections.traits.LengineIterable;
import lengine.types.collections.traits.LengineIterator;
import lengine.types.LengineLazyValue;
import lengine.types.collections.traits.LengineObjectHasHelp;
import lengine.types.collections.traits.LengineObjectType;
import lengine.types.collections.traits.LengineSequenceIterator;
import lengine.types.collections.LengineStreamIterator;
import lengine.types.LengineString;
import lengine.types.LengineStringIterator;
import lengine.types.LengineUnit;
import lengine.types.collections.RangeSequence;
import lengine.types.RatioNumber;
import lengine.runtime.exceptions.LengineRuntimeException;
import lengine.runtime.exceptions.LengineTypeMismatchException;
import lengine.sqls.DBConn;
import lengine.types.collections.traits.Addable;
import lengine.types.collections.traits.Buildable;
import lengine.types.collections.traits.CollectionBuilder;
import lengine.types.collections.Cons;
import lengine.types.collections.LengineFileReader;
import lengine.types.collections.LengineList;
import lengine.types.collections.LengineListIterator;
import lengine.types.collections.LengineMap;
import lengine.types.collections.LengineMapEntry;
import lengine.types.collections.LengineMapKey;
import lengine.types.collections.LengineSequence;
import lengine.types.collections.LengineSet;
import lengine.types.collections.LengineStream;
import lengine.types.collections.traits.Nillable;
import lengine.types.collections.StreamCons;
import lengine.types.collections.StreamNil;
import lengine.types.collections.UnresolvedStream;
import lengine.types.collections.traits.Wrap;

import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;

import static java.lang.String.format;
import static lengine.util.Utils.UNSAFE_cast;

public class PreludeImpl {
    private final static LengineUnit UNIT = LengineUnit.create();

    public static final LengineLambda2<Object, Object, Object> _ADD = (a, b) -> {
        Class<?> largerType = getLargerType(a.getClass(), b.getClass());
        Object x = cast(a, largerType);
        Object y = cast(b, largerType);

        if (x instanceof Character) {
            return (Character) x + (Character) y;
        } else if (x instanceof Long) {
            return (Long) x + (Long) y;
        } else if (x instanceof RatioNumber) {
            return ((RatioNumber) x).add((RatioNumber) y);
        } else if (x instanceof Double) {
            return (Double) x + (Double) y;
        } else if (x instanceof ComplexNumber) {
            return ((ComplexNumber) x).add((ComplexNumber) y);
        } else if (x instanceof LengineString) {
            return ((LengineString)x).add(y);
        }

        throw new RuntimeException("Can't add " + a + ", " + b);
    };
    public static final LengineLambda2<Object, Object, Object> _SUB = (a, b) -> {
        Class<?> largerType = getLargerType(a.getClass(), b.getClass());
        Object x = cast(a, largerType);
        Object y = cast(b, largerType);

        if (x instanceof Character) {
            return (Character) x - (Character) y;
        } else if (x instanceof Long) {
            return (Long) x - (Long) y;
        } else if (x instanceof RatioNumber) {
            return ((RatioNumber) x).sub((RatioNumber) y);
        } else if (x instanceof Double) {
            return (Double) x - (Double) y;
        } else if (x instanceof ComplexNumber) {
            return ((ComplexNumber) x).sub((ComplexNumber) y);
        }

        throw new RuntimeException("Can't subtract " + a + ", " + b);
    };
    public static final LengineLambda2<Object, Object, Object> _MULT = (a, b) -> {
        Class<?> largerType = getLargerType(a.getClass(), b.getClass());
        Object x = cast(a, largerType);
        Object y = cast(b, largerType);

        if (x instanceof Character) {
            return (Character) x * (Character) y;
        } else if (x instanceof Long) {
            return (Long) x * (Long) y;
        } else if (x instanceof RatioNumber) {
            return ((RatioNumber) x).mult((RatioNumber) y);
        } else if (x instanceof Double) {
            return (Double) x * (Double) y;
        } else if (x instanceof ComplexNumber) {
            return ((ComplexNumber) x).mult((ComplexNumber) y);
        }

        throw new RuntimeException("Can't multiply " + a + ", " + b);
    };
    public static final LengineLambda2<Object, Object, Object> _DIV = (a, b) -> {
        Class<?> largerType = getLargerType(a.getClass(), b.getClass());
        Object x = cast(a, largerType);
        Object y = cast(b, largerType);

        if (x instanceof Character) {
            return (Character) x / (Character) y;
        } else if (x instanceof Long) {
            return (Long) x / (Long) y;
        } else if (x instanceof RatioNumber) {
            return ((RatioNumber) x).div((RatioNumber) y);
        } else if (x instanceof Double) {
            return (Double) x / (Double) y;
        } else if (x instanceof ComplexNumber) {
            return ((ComplexNumber) x).div((ComplexNumber) y);
        }

        throw new LengineRuntimeException("Can't divide " + a + ", " + b);
    };
    public static final LengineLambda2<Object, Object, Object> _REM = (a, b) -> {
        Class<?> largerType = getLargerType(a.getClass(), b.getClass());
        Object x = cast(a, largerType);
        Object y = cast(b, largerType);

        if (x instanceof Character) {
            return (Character) x % (Character) y;
        } else if (x instanceof Long) {
            return (Long) x % (Long) y;
        } else if (x instanceof Double) {
            return (Double) x % (Double) y;
        }

        throw new RuntimeException("Can't divide " + a + ", " + b);
    };

    public static final LengineLambda1<RatioNumber, RatioNumber> _NORM = RatioNumber::normalize;
    public static final LengineLambda1<Long, Object> _LEN = (obj) -> {
        if (obj instanceof LengineIterable) {
            return ((LengineIterable) obj).len();
        }

        throw new RuntimeException("unable to decide the size for " + obj);
    };
    public static final LengineLambda2<LengineIterable, Long, LengineIterable> _TAKE = (n, seq) -> {
        LengineIterator it = seq.iterator();

        if (!(seq instanceof Nillable<?>) || !(seq instanceof Addable<?>)) {
            throw new RuntimeException("can't run take operation");
        }

        AtomicReference<LengineIterable> ret = new AtomicReference<>(((Nillable<?>)seq).NIL());
        Buildable<?, ?> builder = (Buildable<?, ?>) ret.get();
        CollectionBuilder<?> collectionBuilder = builder.BUILDER();
        it.forEachN(collectionBuilder::ADD, n);

        return collectionBuilder.BUILD();
    };
    public static final LengineLambda2<LengineIterable, Long, LengineIterable> _DROP = (n, seq) -> {
        LengineIterator it = seq.iterator();
        it.forEachN(x -> {}, n);
        if (it instanceof LengineListIterator) {
            return ((LengineListIterator) it)._this();
        } else if (it instanceof LengineSequenceIterator) {
            return LengineSequence.create(it);
        } else if (it instanceof LengineStreamIterator) {
            return ((LengineStreamIterator) it)._this();
        } else if (it instanceof LengineStringIterator) {
            return ((LengineStringIterator)it)._remains();
        } else {
          LengineIterable current = ((Nillable<?>)seq).NIL();
          while (it.hasNext()) {
            current = ((Addable<?>)current).ADD(it.next());
            current = ((Wrap<?>)current).WRAP();
          }
          return current;
        }
    };

    public static <T> Boolean isInstanceOf(Class<T> cls, Object value) {
        return cls.isInstance(value);
    }

    public static final LengineLambda1<Object, LengineIterable> _HEAD = LengineIterable::head;
    public static final LengineLambda1<LengineIterable, LengineIterable> _TAIL = LengineIterable::tail;
    public static final LengineLambda3<Object, LengineIterable, Object, LengineLambda2<Object, Object, Object>> _FOLD = (seq, acc, fn) -> {
        LengineIterator it = seq.iterator();
        while (it.hasNext()) {
            acc = fn.invoke(acc, it.next());
        }
        return acc;
    };
    public static final LengineLambda2<Boolean, Object, Object> _LESS_THAN = (a, b) -> compareFunction(a, b, (x, y) -> x.compareTo(y) < 0);
    public static final LengineLambda2<Boolean, Object, Object> _LESS_EQUALS = (a, b) -> compareFunction(a, b, (x, y) -> x.compareTo(y) <= 0);
    public static final LengineLambda2<Boolean, Object, Object> _GREATER_THAN = (a, b) -> compareFunction(a, b, (x, y) -> x.compareTo(y) > 0);
    public static final LengineLambda2<Boolean, Object, Object> _GREATER_EQUALS = (a, b) -> compareFunction(a, b, (x, y) -> x.compareTo(y) >= 0);
    public static final LengineLambda2<Boolean, Object, Object> _EQUALS = Objects::equals;
    public static final LengineLambda2<Boolean, Object, Object> _NOT_EQUALS = (a, b) -> !Objects.equals(a, b);
    public static final LengineLambda2<Boolean, Boolean, Boolean> _AND = (a, b) -> a && b;
    public static final LengineLambda2<Boolean, Boolean, Boolean> _OR = (a, b) -> a || b;
    public static final LengineLambda1<Boolean, Boolean> _NOT = (a) -> !a;
    public static final LengineLambda1<LengineUnit, Object> _PRINTLN = (str) -> {
        System.out.println(str);
        return UNIT;
    };
    public static final LengineLambda1<LengineUnit, Object> _PRINT = (str) -> {
        System.out.print(str);
        return UNIT;
    };
    public static final LengineLambda2<LengineUnit, LengineString, LengineIterable> _PRINTF = (fmt, args) -> {
        final LinkedList<Object> items = new LinkedList<>();
        LengineIterator argsIt = args.iterator();
        while (argsIt.hasNext()) {
            items.add(argsIt.next());
        }

        System.out.printf(fmt.toString(), items.toArray());
        return UNIT;
    };
    public static final LengineLambda2<LengineString, LengineString, LengineIterable> _FORMAT = (fmt, args) -> {
        final LinkedList<Object> items = new LinkedList<>();
        LengineIterator argsIt = args.iterator();
        while (argsIt.hasNext()) {
            items.add(argsIt.next());
        }

        return LengineString.create(format(fmt.toString(), items.toArray()));
    };
    public static final LengineLambda2<RangeSequence, Long, Long> _RANGE = RangeSequence::createRange;
    public static final LengineLambda2<RangeSequence, Long, Long> _INCLUSIVE_RANGE = RangeSequence::createInclusiveRange;
    public static final LengineLambda2<LengineUnit, LengineString, Boolean> _ASSERT_TRUE = PreludeImpl::assertTrue;
    public static final LengineLambda2<LengineUnit, LengineString, Boolean> _ASSERT_FALSE = (message, value) -> assertTrue(message, !value);
    public static final LengineLambda3<LengineUnit, LengineString, Object, Object> _ASSERT_EQUALS = (message, a, b) -> assertTrue(message, _EQUALS.invoke(a, b));
    public static final LengineLambda3<LengineUnit, LengineString, Object, Object> _ASSERT_NOT_EQUALS = (message, a, b) -> assertTrue(message, _NOT_EQUALS.invoke(a, b));
    public static final LengineLambda1<Boolean, Object> _CAST_BOOLEAN = PreludeImpl::castBoolean;
    public static final LengineLambda1<LengineString, Object> _CAST_STR = PreludeImpl::castString;
    public static final LengineLambda1<Long, Object> _CAST_INT = PreludeImpl::castLong;
    public static final LengineLambda1<Double, Object> _CAST_DOUBLE = PreludeImpl::castDouble;
    public static final LengineLambda1<Character, Object> _CAST_CHARACTER = PreludeImpl::castChar;
    public static final LengineLambda1<LengineList, Object> _CAST_LIST = PreludeImpl::castList;
    public static final LengineLambda1<LengineSequence, Object> _CAST_SEQ = PreludeImpl::castSeq;
    public static final LengineLambda1<LengineSet, Object> _CAST_SET = PreludeImpl::castSet;
    public static final LengineLambda1<LengineStream, Object> _CAST_STREAM = LengineStream::create;
    public static final LengineLambda1<Boolean, Object> _IS_BOOL = (obj) -> isInstanceOf(Boolean.class, obj);
    public static final LengineLambda1<Boolean, Object> _IS_CHAR = (obj) -> isInstanceOf(Character.class, obj);
    public static final LengineLambda1<Boolean, Object> _IS_INT = (obj) -> isInstanceOf(Long.class, obj);
    public static final LengineLambda1<Boolean, Object> _IS_DOUBLE = (obj) -> isInstanceOf(Double.class, obj);
    public static final LengineLambda1<Boolean, Double> _IS_NAN = (dbl) -> Double.isNaN(dbl);
    public static final LengineLambda1<Boolean, Object> _IS_STR = (obj) -> isInstanceOf(LengineString.class, obj);
    public static final LengineLambda1<Boolean, Object> _IS_LIST = (obj) -> isInstanceOf(LengineList.class, obj);
    public static final LengineLambda1<Boolean, Object> _IS_SEQ = (obj) -> isInstanceOf(LengineSequence.class, obj);
    public static final LengineLambda1<Boolean, Object> _IS_OBJECT = (obj) -> isInstanceOf(LengineMap.class, obj);
    public static final LengineLambda1<Boolean, Object> _IS_CONS = (obj) -> isInstanceOf(Cons.class, obj);
    public static final LengineLambda1<Boolean, LengineIterable> _IS_NIL = (obj) -> obj == null || obj.IS_NIL();
    public static final LengineLambda1<Boolean, Object> _IS_STREAM_NIL = (obj) -> isInstanceOf(StreamNil.class, obj);
    public static final LengineLambda1<Boolean, Object> _IS_STREAM_CONS = (obj) -> isInstanceOf(StreamCons.class, obj);
    public static final LengineLambda1<Boolean, Object> _IS_STREAM_UNRESOLVED = (obj) -> isInstanceOf(UnresolvedStream.class, obj) && !((UnresolvedStream)obj).isResolved();
    public static final LengineLambda1<Boolean, Object> _IS_STREAM = (obj) -> isInstanceOf(LengineStream.class, obj);
    public static final LengineLambda1<Boolean, Object> _IS_SET = (obj) -> isInstanceOf(LengineSet.class, obj);
    public static final LengineLambda1<LengineIterable, Nillable<?>> _GET_NIL = Nillable::NIL;
    public static final LengineLambda2<Boolean, Object, Object> _DOES_HAVE = (set, obj) -> {
        if (set instanceof LengineSet) {
            return ((LengineSet) set).contains(obj);
        } else if (set instanceof LengineMap && obj instanceof LengineMapKey) {
            return ((LengineMap) set).contains((LengineMapKey) obj);
        }

        return false;
    };
    public static final LengineLambda0<Long> _NOW = System::currentTimeMillis;
    public static final LengineLambda1<LengineUnit, Long> _EXIT = (code) -> {
        System.exit(code.intValue());
        return UNIT;
    };
    public static final LengineLambda2<LengineList, Object, LengineList> _CONS = LengineList::cons;
    public static final LengineLambda2<LengineStream, Object, Object> _STREAM_CONS = (value, tail) -> {
        if (tail instanceof LengineLazyValue) {
            return LengineStream.cons(value, UnresolvedStream.create((LengineLazyValue) tail));
        } else if (tail instanceof StreamCons) {
            return LengineStream.cons(value, (StreamCons) tail);
        } else if (tail instanceof StreamNil) {
            return LengineStream.cons(value, (StreamNil) tail);
        } else if (tail instanceof UnresolvedStream) {
            return LengineStream.cons(value, (UnresolvedStream) tail);
        }

        throw new RuntimeException("Unable to concatenate to stream:" + tail);
    };
    public static final LengineLambda1<LengineMapKey, LengineString> _KEY = LengineMapKey::create;
    public static final LengineLambda1<LengineSet, LengineMap> _KEYS = LengineMap::keys;
    public static final LengineLambda2<LengineMapEntry, LengineMapKey, Object> _ENTRY = LengineMapEntry::create;
    public static final LengineLambda1<LengineSequence, LengineMap> _ENTRIES = LengineMap::entries;
    public static final LengineLambda1<LengineString, LengineMapKey> _OBJECT_GET = LengineMapKey::getKey;

    public static final LengineLambda0<Character> _READ_CHAR = () -> {
        LengineObjectType fr = LengineFileReader.createFileReader(System.in);
        LengineLambda0<Character> reader = UNSAFE_cast(fr.get(LengineMapKey.create(LengineString.create("read"))));
        return reader.invoke();
    };

    public static final LengineLambda1<LengineObjectType, LengineString> _READ_FILE = LengineFileReader::createFileReader;

    public static final LengineLambda2<LengineIterable, LengineIterable, Object> _APPEND_ITEM = (coll, elem) -> {
        if (coll instanceof Addable<?>) {
            return ((Addable<?>)coll).ADD(elem);
        }

        throw new RuntimeException("currently not supporting the operation");
    };

    public static final LengineLambda2<LengineIterable, Object, AddFront<?>> _ADD_FRONT = (item, coll) -> coll.ADD_FRONT(item);
  public static final LengineLambda2<LengineIterable, AddRear<?>, Object> _ADD_REAR = AddRear::ADD_REAR;

    public static final LengineLambda2<Object, Object, Object> _MERGE = (xs, ys) -> {
        if (xs instanceof LengineString) {
            if (ys instanceof LengineString) {
                return ((LengineString)xs).add(ys);
            }

            throw new RuntimeException("Unable to determine the 2nd parameter to be LengineString");
        } else if (xs instanceof LengineList) {
            if (ys instanceof LengineIterable) {
                return ((LengineList) xs).append((LengineIterable) ys);
            }
        } else if (xs instanceof LengineSequence) {
            if (ys instanceof LengineIterable) {
                return ((LengineSequence) xs).append((LengineIterable) ys);
            }
        } else if (xs instanceof LengineSet) {
            if (ys instanceof LengineSet) {
                return ((LengineSet) xs).append((LengineSet) ys);
            } else if (ys instanceof LengineIterable) {
                return ((LengineSet) xs).append(LengineSet.create((LengineIterable) ys));
            }
        }

        throw new RuntimeException("merge operation not supported.");
    };
    public static final LengineLambda1<LengineFuture, LengineLambda0<?>> _ASYNC = LengineFuture::new;
    public static final LengineLambda1<Object, LengineFuture> _AWAIT = LengineFuture::await;
    public static final LengineLambda1<LengineUnit, Long> _WAIT = (milliseconds) -> {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return UNIT;
    };
    public static final LengineLambda0<LengineChannel> _CHANNEL = LengineChannel::create;

    public static final LengineLambda2<LengineUnit, LengineChannel, Object> _SEND = (channel, msg) -> {
        try {
            channel.sendMessage(msg);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return UNIT;
    };
    public static final LengineLambda1<Object, LengineChannel> _RECEIVE = (channel) -> {
        try {
            return channel.receiveMessage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    };
    public static final LengineLambda1<Object, LengineChannel> _CLOSE = (channel) -> {
        try {
            channel.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return UNIT;
    };

    static final LengineLambda1<LengineSequence, LengineObjectHasHelp> _HELP = LengineObjectHasHelp::help;
    static final LengineLambda2<LengineString, LengineObjectHasHelp, LengineMapKey> _HELP_KEYWORD = LengineObjectHasHelp::help;
    static final LengineLambda1<LengineLambda0<LengineUnit>, LengineMap> _LISTEN = HttpServerBuilder::listen;
    static final LengineLambda3<LengineMap, LengineString, LengineString, LengineString> _DB_CONN = DBConn::connect;

    static final LengineLambda2<Object, Object, Object> _BYTE_AND = (_a, _b) -> {
        Class<?> largerType = getLargerType(_a.getClass(), _b.getClass());
        Object a = cast(_a, largerType);
        Object b = cast(_b, largerType);

        if (a instanceof Boolean) {
            return _AND.invoke((Boolean) _a, (Boolean) _b);
        } else if (a instanceof Character) {
            return (Character)a & (Character) b;
        } else if (a instanceof Long) {
            return (Long)a & (Long) b;
        }

        throw new RuntimeException("operation & cannot be applied");
    };
    static final LengineLambda2<Object, Object, Object> _BYTE_OR = (_a, _b) -> {
        Class<?> largerType = getLargerType(_a.getClass(), _b.getClass());
        Object a = cast(_a, largerType);
        Object b = cast(_b, largerType);

        if (a instanceof Boolean) {
            return _OR.invoke((Boolean) _a, (Boolean) _b);
        } else if (a instanceof Character) {
            return (Character)a | (Character) b;
        } else if (a instanceof Long) {
            return (Long)a | (Long) b;
        }

        throw new RuntimeException("operation ~ cannot be applied");
    };
    static final LengineLambda1<Object, Object> _BYTE_NEG = (a) -> {
        if (a instanceof Boolean) {
            return _NOT.invoke((Boolean) a);
        } else if (a instanceof Character) {
            return ~(Character) a;
        } else if (a instanceof Long) {
            return ~(Long) a;
        }

        throw new RuntimeException("operation ~ cannot be applied");
    };



    public static Object cast(Object from, Class<?> to) {
        if (to.equals(Character.class)) {
            return castChar(from);
        } else if (to.equals(Long.class)) {
            return castLong(from);
        } else if (to.equals(RatioNumber.class)) {
            return castRatioNumber(from);
        } else if (to.equals(Double.class)) {
            return castDouble(from);
        } else if (to.equals(ComplexNumber.class)) {
            return castComplexNumber(from);
        } else if (to.equals(LengineString.class)) {
            return castString(from);
        }

        throw new LengineTypeMismatchException(from, to);
    }

    private static Character castChar(Object from) {
        if (from instanceof Character) {
            return (Character) from;
        } else if (from instanceof Long) {
            return (char) ((Long) from).intValue();
        } else if (from instanceof Double) {
            return (char) ((Double) from).intValue();
        }

        throw new LengineTypeMismatchException(from, Character.class);
    }

    private static Long castLong(Object from) {
        if (from instanceof Character) {
            return (long) (Character) from;
        } else if (from instanceof Long) {
            return (Long) from;
        } else if (from instanceof Double) {
            return ((Double) from).longValue();
        } else if (from instanceof LengineString) {
            return Long.parseLong(from.toString());
        }

        throw new LengineTypeMismatchException(from, Long.class);
    }

    private static Double castDouble(Object from) {
        if (from instanceof Character) {
            return (double) (Character) from;
        } else if (from instanceof Long) {
            return ((Long) from).doubleValue();
        } else if (from instanceof RatioNumber) {
            return ((RatioNumber) from).doubleValue();
        } else if (from instanceof Double) {
            return (Double) from;
        } else if (from instanceof LengineString) {
            return Double.parseDouble(from.toString());
        }

        throw new LengineTypeMismatchException(from, Double.class);
    }

    private static RatioNumber castRatioNumber(Object from) {
        if (from instanceof Character) {
            return RatioNumber.create((Character) from, 1L);
        } else if (from instanceof Long) {
            return RatioNumber.create((Long) from, 1L);
        } else if (from instanceof RatioNumber) {
            return (RatioNumber) from;
        }

        throw new LengineTypeMismatchException(from, RatioNumber.class);
    }

    private static ComplexNumber castComplexNumber(Object from) {
        if (from instanceof Character) {
            return ComplexNumber.create((long) (Character) from, 1L);
        } else if (from instanceof Long) {
            return ComplexNumber.create((Long) from, 1L);
        } else if (from instanceof RatioNumber) {
            return ComplexNumber.create((RatioNumber) from, 1L);
        } else if (from instanceof ComplexNumber) {
            return (ComplexNumber) from;
        }

        throw new LengineTypeMismatchException(from, ComplexNumber.class);
    }

    private static Boolean castBoolean(Object o) {
        switch (o.toString()) {
            case "true":
                return true;
            case "false":
                return false;
        }
        throw new RuntimeException("Can't convert " + o + " into boolean type");
    }

    private static LengineString castString(Object from) {
        return LengineString.create(from.toString());
    }

    private static LengineList castList(Object o) {
        if (o instanceof LengineString) {
            return LengineList.create(o.toString());
        } else if (o instanceof LengineList) {
            return (LengineList) o;
        }

        throw new LengineTypeMismatchException(o, LengineList.class);
    }

    private static LengineSequence castSeq(Object o) {
        if (o instanceof LengineString) {
            return LengineSequence.create(o.toString());
        } else if (o instanceof LengineSequence) {
            return (LengineSequence) o;
        } else if (o instanceof LengineIterable) {
            return LengineSequence.create((LengineIterable) o);
        }

        throw new LengineTypeMismatchException(o, LengineSequence.class);
    }

    private static LengineSet castSet(Object o) {
        if (o instanceof LengineString) {
            return LengineSet.create(o.toString());
        } else if (o instanceof LengineSet) {
            return (LengineSet) o;
        } else if (o instanceof LengineIterable) {
            return LengineSet.create((LengineIterable) o);
        }

        throw new LengineTypeMismatchException(o, LengineSet.class);
    }

    private static Class<?> getLargerType(Class<?> a, Class<?> b) {
        return getRank(a) > getRank(b) ? a : b;
    }

    private static int getRank(Class<?> a) {
        if (a.equals(Character.class)) {
            return 0;
        } else if (a.equals(Long.class)) {
            return 1;
        } else if (a.equals(RatioNumber.class)) {
            return 2;
        } else if (a.equals(Double.class)) {
            return 3;
        } else if (a.equals(ComplexNumber.class)) {
            return 4;
        } else if (a.equals(LengineString.class)) {
            return 5;
        } else if (a.equals(LengineIterable.class)) {
            return 6;
        }

        throw new RuntimeException("Unable to decide rank for type: " + a.getName());
    }

    private static LengineUnit assertTrue(LengineString message, Boolean value) {
        if (!value) {
            throw new RuntimeException("Failed to assert: " + message);
        }
        System.out.println("PASSED: " + message);
        return UNIT;
    }


    @SuppressWarnings("unchecked")
    private static Boolean compareFunction(Object a, Object b, BiPredicate<Comparable<Object>, Comparable<Object>> predicate) {
        Class<?> largerType = getLargerType(a.getClass(), b.getClass());

        Object _a = cast(a, largerType);
        Object _b = cast(b, largerType);

        if (!(_a instanceof Comparable)) {
            throw new RuntimeException("Unable to compare the given object: " + a + ", " + b);
        }

        if (!(_b instanceof Comparable)) {
            throw new RuntimeException("Unable to compare the given object: " + a + ", " + b);
        }

        Comparable<Object> _aComp = (Comparable<Object>) _a;
        Comparable<Object> _bComp = (Comparable<Object>) _b;

        return predicate.test(_aComp, _bComp);
    }
}
