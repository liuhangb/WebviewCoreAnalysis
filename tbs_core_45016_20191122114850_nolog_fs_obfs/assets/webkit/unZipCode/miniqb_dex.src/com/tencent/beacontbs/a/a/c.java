package com.tencent.beacontbs.a.a;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tencent.beacontbs.a.e;
import com.tencent.beacontbs.c.a;
import java.io.File;

public final class c
  extends SQLiteOpenHelper
{
  private static SQLiteDatabase jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = null;
  private static c jdField_a_of_type_ComTencentBeacontbsAAC;
  public static Object a;
  private Context jdField_a_of_type_AndroidContentContext;
  
  static
  {
    jdField_a_of_type_JavaLangObject = new Object();
    jdField_a_of_type_ComTencentBeacontbsAAC = null;
  }
  
  private c(Context paramContext)
  {
    super(paramContext, "beacon_tbs_db", null, 23);
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  public static c a(Context paramContext)
  {
    try
    {
      if (jdField_a_of_type_ComTencentBeacontbsAAC == null) {
        jdField_a_of_type_ComTencentBeacontbsAAC = new c(paramContext);
      }
      paramContext = jdField_a_of_type_ComTencentBeacontbsAAC;
      return paramContext;
    }
    finally {}
  }
  
  /* Error */
  private boolean a(SQLiteDatabase paramSQLiteDatabase)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aconst_null
    //   3: astore 5
    //   5: aconst_null
    //   6: astore 4
    //   8: aload 4
    //   10: astore_2
    //   11: aload 5
    //   13: astore_3
    //   14: new 39	java/util/ArrayList
    //   17: dup
    //   18: invokespecial 40	java/util/ArrayList:<init>	()V
    //   21: astore 6
    //   23: aload 4
    //   25: astore_2
    //   26: aload 5
    //   28: astore_3
    //   29: aload_1
    //   30: ldc 42
    //   32: iconst_1
    //   33: anewarray 44	java/lang/String
    //   36: dup
    //   37: iconst_0
    //   38: ldc 46
    //   40: aastore
    //   41: ldc 48
    //   43: aconst_null
    //   44: aconst_null
    //   45: aconst_null
    //   46: aconst_null
    //   47: invokevirtual 54	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   50: astore 4
    //   52: aload 4
    //   54: ifnull +44 -> 98
    //   57: aload 4
    //   59: astore_2
    //   60: aload 4
    //   62: astore_3
    //   63: aload 4
    //   65: invokeinterface 60 1 0
    //   70: ifeq +28 -> 98
    //   73: aload 4
    //   75: astore_2
    //   76: aload 4
    //   78: astore_3
    //   79: aload 6
    //   81: aload 4
    //   83: iconst_0
    //   84: invokeinterface 64 2 0
    //   89: invokeinterface 70 2 0
    //   94: pop
    //   95: goto -38 -> 57
    //   98: aload 4
    //   100: astore_2
    //   101: aload 4
    //   103: astore_3
    //   104: aload 6
    //   106: invokeinterface 74 1 0
    //   111: ifle +134 -> 245
    //   114: aload 4
    //   116: astore_2
    //   117: aload 4
    //   119: astore_3
    //   120: aload 6
    //   122: invokeinterface 78 1 0
    //   127: astore 5
    //   129: aload 4
    //   131: astore_2
    //   132: aload 4
    //   134: astore_3
    //   135: aload 5
    //   137: invokeinterface 83 1 0
    //   142: ifeq +103 -> 245
    //   145: aload 4
    //   147: astore_2
    //   148: aload 4
    //   150: astore_3
    //   151: aload 5
    //   153: invokeinterface 87 1 0
    //   158: checkcast 44	java/lang/String
    //   161: astore 6
    //   163: aload 4
    //   165: astore_2
    //   166: aload 4
    //   168: astore_3
    //   169: aload 6
    //   171: ldc 89
    //   173: invokevirtual 92	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   176: ifne -47 -> 129
    //   179: aload 4
    //   181: astore_2
    //   182: aload 4
    //   184: astore_3
    //   185: aload 6
    //   187: ldc 94
    //   189: invokevirtual 92	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   192: ifne -63 -> 129
    //   195: aload 4
    //   197: astore_2
    //   198: aload 4
    //   200: astore_3
    //   201: aload_1
    //   202: getstatic 100	java/util/Locale:US	Ljava/util/Locale;
    //   205: ldc 102
    //   207: iconst_1
    //   208: anewarray 13	java/lang/Object
    //   211: dup
    //   212: iconst_0
    //   213: aload 6
    //   215: aastore
    //   216: invokestatic 106	java/lang/String:format	(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   219: invokevirtual 110	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   222: aload 4
    //   224: astore_2
    //   225: aload 4
    //   227: astore_3
    //   228: ldc 112
    //   230: iconst_1
    //   231: anewarray 13	java/lang/Object
    //   234: dup
    //   235: iconst_0
    //   236: aload 6
    //   238: aastore
    //   239: invokestatic 118	com/tencent/beacontbs/c/a:g	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   242: goto -113 -> 129
    //   245: aload 4
    //   247: ifnull +20 -> 267
    //   250: aload 4
    //   252: invokeinterface 121 1 0
    //   257: ifne +10 -> 267
    //   260: aload 4
    //   262: invokeinterface 124 1 0
    //   267: aload_0
    //   268: monitorexit
    //   269: iconst_1
    //   270: ireturn
    //   271: astore_1
    //   272: goto +33 -> 305
    //   275: astore_1
    //   276: aload_3
    //   277: astore_2
    //   278: aload_1
    //   279: invokestatic 127	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   282: aload_3
    //   283: ifnull +18 -> 301
    //   286: aload_3
    //   287: invokeinterface 121 1 0
    //   292: ifne +9 -> 301
    //   295: aload_3
    //   296: invokeinterface 124 1 0
    //   301: aload_0
    //   302: monitorexit
    //   303: iconst_0
    //   304: ireturn
    //   305: aload_2
    //   306: ifnull +18 -> 324
    //   309: aload_2
    //   310: invokeinterface 121 1 0
    //   315: ifne +9 -> 324
    //   318: aload_2
    //   319: invokeinterface 124 1 0
    //   324: aload_1
    //   325: athrow
    //   326: astore_1
    //   327: aload_0
    //   328: monitorexit
    //   329: aload_1
    //   330: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	331	0	this	c
    //   0	331	1	paramSQLiteDatabase	SQLiteDatabase
    //   10	309	2	localObject1	Object
    //   13	283	3	localObject2	Object
    //   6	255	4	localCursor	android.database.Cursor
    //   3	149	5	localIterator	java.util.Iterator
    //   21	216	6	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   14	23	271	finally
    //   29	52	271	finally
    //   63	73	271	finally
    //   79	95	271	finally
    //   104	114	271	finally
    //   120	129	271	finally
    //   135	145	271	finally
    //   151	163	271	finally
    //   169	179	271	finally
    //   185	195	271	finally
    //   201	222	271	finally
    //   228	242	271	finally
    //   278	282	271	finally
    //   14	23	275	java/lang/Throwable
    //   29	52	275	java/lang/Throwable
    //   63	73	275	java/lang/Throwable
    //   79	95	275	java/lang/Throwable
    //   104	114	275	java/lang/Throwable
    //   120	129	275	java/lang/Throwable
    //   135	145	275	java/lang/Throwable
    //   151	163	275	java/lang/Throwable
    //   169	179	275	java/lang/Throwable
    //   185	195	275	java/lang/Throwable
    //   201	222	275	java/lang/Throwable
    //   228	242	275	java/lang/Throwable
    //   250	267	326	finally
    //   286	301	326	finally
    //   309	324	326	finally
    //   324	326	326	finally
  }
  
  public final SQLiteDatabase getWritableDatabase()
  {
    int i = 0;
    for (;;)
    {
      try
      {
        SQLiteDatabase localSQLiteDatabase1 = jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase;
        if ((localSQLiteDatabase1 == null) && (i < 5)) {
          i += 1;
        }
      }
      finally {}
      try
      {
        jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = super.getWritableDatabase();
      }
      catch (Exception localException)
      {
        continue;
      }
      a.c("getWritableDatabase error count %d", new Object[] { Integer.valueOf(i) });
      if (i == 5) {
        a.d("error get DB failed", new Object[0]);
      }
      try
      {
        Thread.sleep(200L);
      }
      catch (InterruptedException localInterruptedException)
      {
        a.a(localInterruptedException);
      }
    }
    SQLiteDatabase localSQLiteDatabase2 = jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase;
    return localSQLiteDatabase2;
  }
  
  public final void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    if (paramSQLiteDatabase != null) {
      try
      {
        String[][] arrayOfString = b.a;
        arrayOfString = b.a;
        int i = 0;
        while (i < 4)
        {
          String[] arrayOfString1 = arrayOfString[i];
          a.g("table:%s", new Object[] { arrayOfString1[0] });
          paramSQLiteDatabase.execSQL(arrayOfString1[1]);
          i += 1;
        }
      }
      finally {}
    }
  }
  
  @TargetApi(11)
  public final void onDowngrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    try
    {
      e.a(this.jdField_a_of_type_AndroidContentContext);
      if (Integer.parseInt(e.c()) >= 11)
      {
        a.g("downgrade a db  [%s] from v %d to  v%d , deleted all tables!", new Object[] { "beacon_tbs_db", Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) });
        if (a(paramSQLiteDatabase))
        {
          a.g("drop all success recreate!", new Object[0]);
          onCreate(paramSQLiteDatabase);
          return;
        }
        a.d("drop all fail try deleted file,may next time will success!", new Object[0]);
        paramSQLiteDatabase = this.jdField_a_of_type_AndroidContentContext.getDatabasePath("beacon_tbs_db");
        if ((paramSQLiteDatabase != null) && (paramSQLiteDatabase.canWrite())) {
          paramSQLiteDatabase.delete();
        }
      }
      return;
    }
    finally {}
  }
  
  public final void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    try
    {
      a.g("upgrade a db  [%s] from v %d to v %d , deleted all tables!", new Object[] { "beacon_tbs_db", Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) });
      if (a(paramSQLiteDatabase))
      {
        a.g("drop all success recreate!", new Object[0]);
        onCreate(paramSQLiteDatabase);
        return;
      }
      a.d("drop all fail try deleted file,may next time will success!", new Object[0]);
      paramSQLiteDatabase = this.jdField_a_of_type_AndroidContentContext.getDatabasePath("beacon_tbs_db");
      if ((paramSQLiteDatabase != null) && (paramSQLiteDatabase.canWrite())) {
        paramSQLiteDatabase.delete();
      }
      return;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\a\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */