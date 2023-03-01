package lengine.runtime;

import lengine.Prelude;

import java.lang.reflect.Field;

public class ExportSymbols {
  public static final Field ADD_FIELD;
  public static final Field SUB_FIELD;
  public static final Field MULT_FIELD;
  public static final Field DIV_FIELD;
  public static final Field REM_FIELD;
  public static final Field NORM_FIELD;
  public static final Field LEN_FIELD;
  public static final Field TAKE_FIELD;
  public static final Field DROP_FIELD;
  public static final Field HEAD_FIELD;
  public static final Field TAIL_FIELD;
  public static final Field FOLD_FIELD;
  public static final Field LESS_THAN_FIELD;
  public static final Field LESS_EQUALS_FIELD;
  public static final Field GREATER_THAN_FIELD;
  public static final Field GREATER_EQUALS_FIELD;
  public static final Field EQUALS_FIELD;
  public static final Field NOT_EQUALS_FIELD;
  public static final Field AND_FIELD;
  public static final Field OR_FIELD;
  public static final Field NOT_FIELD;
  public static final Field PRINTLN_FIELD;
  public static final Field PRINT_FIELD;
  public static final Field PRINTF_FIELD;
  public static final Field FORMAT_FIELD;
  public static final Field RANGE_FIELD;
  public static final Field INCLUSIVE_RANGE_FIELD;
  public static final Field ASSERT_FIELD;
  public static final Field ASSERT_TRUE_FIELD;
  public static final Field ASSERT_FALSE_FIELD;
  public static final Field ASSERT_EQUALS_FIELD;
  public static final Field ASSERT_NOT_EQUALS_FIELD;
  public static final Field CAST_BOOLEAN_FIELD;
  public static final Field CAST_STR_FIELD;
  public static final Field CAST_INT_FIELD;
  public static final Field CAST_DOUBLE_FIELD;
  public static final Field NAN_FIELD;
  public static final Field CAST_CHARACTER_FIELD;
  public static final Field CAST_LIST_FIELD;
  public static final Field CAST_SEQ_FIELD;
  public static final Field CAST_SET_FIELD;
  public static final Field CAST_STREAM_FIELD;
  public static final Field IS_BOOL_FIELD;
  public static final Field IS_CHAR_FIELD;
  public static final Field IS_INT_FIELD;
  public static final Field IS_DOUBLE_FIELD;
  public static final Field IS_NAN_FIELD;
  public static final Field IS_STR_FIELD;
  public static final Field IS_LIST_FIELD;
  public static final Field IS_SEQ_FIELD;
  public static final Field IS_OBJECT_FIELD;
  public static final Field IS_CONS_FIELD;
  public static final Field IS_NIL_FIELD;
  public static final Field IS_SET_FIELD;
  public static final Field GET_NIL_FIELD;
  public static final Field DOES_HAVE_FIELD;
  public static final Field NOW_FIELD;
  public static final Field CONS_FIELD;
  public static final Field STREAM_CONS_FIELD;
  public static final Field KEY_FIELD;
  public static final Field KEYS_FIELD;
  public static final Field ENTRY_FIELD;
  public static final Field ENTRIES_FIELD;
  public static final Field OBJECT_GET_FIELD;
  public static final Field READ_CHAR_FIELD;
  public static final Field READ_FILE_FIELD;
  public static final Field NIL_FIELD;
  public static final Field STREAM_NIL_FIELD;
  public static final Field IS_STREAM_CONS_FIELD;
  public static final Field IS_STREAM_NIL_FIELD;
  public static final Field IS_STREAM_FIELD;
  public static final Field IS_STREAM_UNRESOLVED_FIELD;
  public static final Field APPEND_ITEM_FIELD;
  public static final Field MERGE_FIELD;
  public static final Field ASYNC_FIELD;
  public static final Field AWAIT_FIELD;
  public static final Field WAIT_FIELD;
  public static final Field CHANNEL_FIELD;
  public static final Field SEND_FIELD;
  public static final Field RECEIVE_FIELD;
  public static final Field CLOSE_FIELD;
  public static final Field LISTEN_FIELD;
  public static final Field HELP_FIELD;
  public static final Field HELP_KEYWORD_FIELD;
  public static final Field DB_CONN_FIELD;
  public static final Field BYTE_AND_FIELD;
  public static final Field BYTE_OR_FIELD;
  public static final Field BYTE_NEG_FIELD;

  static {
    try {
      ADD_FIELD = Prelude.class.getField("ADD");
      SUB_FIELD = Prelude.class.getField("SUB");
      MULT_FIELD = Prelude.class.getField("MULT");
      DIV_FIELD = Prelude.class.getField("DIV");
      REM_FIELD = Prelude.class.getField("REM");
      NORM_FIELD = Prelude.class.getField("NORM");
      LEN_FIELD = Prelude.class.getField("LEN");
      TAKE_FIELD = Prelude.class.getField("TAKE");
      DROP_FIELD = Prelude.class.getField("DROP");
      HEAD_FIELD = Prelude.class.getField("HEAD");
      TAIL_FIELD = Prelude.class.getField("TAIL");
      FOLD_FIELD = Prelude.class.getField("FOLD");
      LESS_THAN_FIELD = Prelude.class.getField("LESS_THAN");
      LESS_EQUALS_FIELD = Prelude.class.getField("LESS_EQUALS");
      GREATER_THAN_FIELD = Prelude.class.getField("GREATER_THAN");
      GREATER_EQUALS_FIELD = Prelude.class.getField("GREATER_EQUALS");
      EQUALS_FIELD = Prelude.class.getField("EQUALS");
      NOT_EQUALS_FIELD = Prelude.class.getField("NOT_EQUALS");
      AND_FIELD = Prelude.class.getField("AND");
      OR_FIELD = Prelude.class.getField("OR");
      NOT_FIELD = Prelude.class.getField("NOT");
      PRINTLN_FIELD = Prelude.class.getField("PRINTLN");
      PRINT_FIELD = Prelude.class.getField("PRINT");
      PRINTF_FIELD = Prelude.class.getField("PRINTF");
      FORMAT_FIELD = Prelude.class.getField("FORMAT");
      RANGE_FIELD = Prelude.class.getField("RANGE");
      INCLUSIVE_RANGE_FIELD = Prelude.class.getField("INCLUSIVE_RANGE");
      ASSERT_FIELD = Prelude.class.getField("ASSERT");
      ASSERT_TRUE_FIELD = Prelude.class.getField("ASSERT_TRUE");
      ASSERT_FALSE_FIELD = Prelude.class.getField("ASSERT_FALSE");
      ASSERT_EQUALS_FIELD = Prelude.class.getField("ASSERT_EQUALS");
      ASSERT_NOT_EQUALS_FIELD = Prelude.class.getField("ASSERT_NOT_EQUALS");
      CAST_BOOLEAN_FIELD = Prelude.class.getField("CAST_BOOLEAN");
      CAST_STR_FIELD = Prelude.class.getField("CAST_STR");
      CAST_INT_FIELD = Prelude.class.getField("CAST_INT");
      CAST_DOUBLE_FIELD = Prelude.class.getField("CAST_DOUBLE");
      NAN_FIELD = Prelude.class.getField("DOUBLE_NAN");
      CAST_CHARACTER_FIELD = Prelude.class.getField("CAST_CHARACTER");
      CAST_LIST_FIELD = Prelude.class.getField("CAST_LIST");
      CAST_SEQ_FIELD = Prelude.class.getField("CAST_SEQ");
      IS_BOOL_FIELD = Prelude.class.getField("IS_BOOL");
      IS_CHAR_FIELD = Prelude.class.getField("IS_CHAR");
      IS_INT_FIELD = Prelude.class.getField("IS_INT");
      IS_DOUBLE_FIELD = Prelude.class.getField("IS_DOUBLE");
      IS_NAN_FIELD = Prelude.class.getField("IS_NAN");
      IS_STR_FIELD = Prelude.class.getField("IS_STR");
      IS_LIST_FIELD = Prelude.class.getField("IS_LIST");
      IS_SEQ_FIELD = Prelude.class.getField("IS_SEQ");
      IS_OBJECT_FIELD = Prelude.class.getField("IS_OBJECT");
      IS_CONS_FIELD = Prelude.class.getField("IS_CONS");
      IS_NIL_FIELD = Prelude.class.getField("IS_NIL");
      IS_STREAM_CONS_FIELD = Prelude.class.getField("IS_STREAM_CONS");
      IS_STREAM_NIL_FIELD = Prelude.class.getField("IS_STREAM_NIL");
      IS_STREAM_FIELD = Prelude.class.getField("IS_STREAM");
      IS_STREAM_UNRESOLVED_FIELD = Prelude.class.getField("IS_STREAM_UNRESOLVED");
      NOW_FIELD = Prelude.class.getField("NOW");
      CONS_FIELD = Prelude.class.getField("CONS");
      STREAM_CONS_FIELD = Prelude.class.getField("STREAM_CONS");
      KEY_FIELD = Prelude.class.getField("KEY");
      KEYS_FIELD = Prelude.class.getField("KEYS");
      ENTRY_FIELD = Prelude.class.getField("ENTRY");
      ENTRIES_FIELD = Prelude.class.getField("ENTRIES");
      OBJECT_GET_FIELD = Prelude.class.getField("OBJECT_GET");
      READ_CHAR_FIELD = Prelude.class.getField("READ_CHAR");
      READ_FILE_FIELD = Prelude.class.getField("READ_FILE");
      APPEND_ITEM_FIELD = Prelude.class.getField("APPEND_ITEM");
      MERGE_FIELD = Prelude.class.getField("MERGE");
      NIL_FIELD = Prelude.class.getField("NIL");
      STREAM_NIL_FIELD = Prelude.class.getField("STREAM_NIL");
      CAST_SET_FIELD = Prelude.class.getField("CAST_SET");
      CAST_STREAM_FIELD = Prelude.class.getField("CAST_STREAM");
      IS_SET_FIELD = Prelude.class.getField("IS_SET");
      GET_NIL_FIELD = Prelude.class.getField("GET_NIL");
      DOES_HAVE_FIELD = Prelude.class.getField("DOES_HAVE");
      ASYNC_FIELD = Prelude.class.getField("ASYNC");
      AWAIT_FIELD = Prelude.class.getField("AWAIT");
      WAIT_FIELD = Prelude.class.getField("WAIT");
      CHANNEL_FIELD = Prelude.class.getField("CHANNEL");
      SEND_FIELD = Prelude.class.getField("SEND");
      RECEIVE_FIELD = Prelude.class.getField("RECEIVE");
      CLOSE_FIELD = Prelude.class.getField("CLOSE");
      LISTEN_FIELD = Prelude.class.getField("LISTEN");
      HELP_FIELD = Prelude.class.getField("HELP");
      HELP_KEYWORD_FIELD = Prelude.class.getField("HELP_KEYWORD");
      DB_CONN_FIELD = Prelude.class.getField("DB_CONN");
      BYTE_AND_FIELD = Prelude.class.getField("BYTE_AND");
      BYTE_OR_FIELD = Prelude.class.getField("BYTE_OR");
      BYTE_NEG_FIELD = Prelude.class.getField("BYTE_NEG");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    } catch (Exception e) {
      System.out.println("Unknown error");
      throw new RuntimeException(e);
    }
  }
}
