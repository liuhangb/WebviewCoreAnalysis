package org.chromium.android_webview;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

public class HttpAuthDatabase
{
  private static final String[] jdField_a_of_type_ArrayOfJavaLangString = { "_id" };
  private SQLiteDatabase jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase;
  private final Object jdField_a_of_type_JavaLangObject = new Object();
  private boolean jdField_a_of_type_Boolean;
  
  private void a()
  {
    this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.execSQL("DROP TABLE IF EXISTS httpauth");
    this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.execSQL("CREATE TABLE httpauth (_id INTEGER PRIMARY KEY, host TEXT, realm TEXT, username TEXT, password TEXT, UNIQUE (host, realm) ON CONFLICT REPLACE);");
    this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.setVersion(1);
  }
  
  private void a(Context paramContext, String paramString)
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (this.jdField_a_of_type_Boolean) {
        return;
      }
      try
      {
        b(paramContext, paramString);
      }
      catch (Exception paramContext)
      {
        paramString = new StringBuilder();
        paramString.append("initDatabase exception:");
        paramString.append(paramContext.toString());
        Log.e("HttpAuthDatabase", paramString.toString());
        this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = null;
      }
      this.jdField_a_of_type_Boolean = true;
      this.jdField_a_of_type_JavaLangObject.notifyAll();
      return;
    }
  }
  
  private boolean a()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      for (;;)
      {
        boolean bool = this.jdField_a_of_type_Boolean;
        if (bool) {
          break;
        }
        try
        {
          this.jdField_a_of_type_JavaLangObject.wait();
        }
        catch (InterruptedException localInterruptedException)
        {
          Log.e("HttpAuthDatabase", "Caught exception while checking initialization", localInterruptedException);
        }
      }
      return this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase != null;
    }
  }
  
  private void b(Context paramContext, String paramString)
  {
    try
    {
      this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = paramContext.openOrCreateDatabase(paramString, 0, null);
    }
    catch (SQLiteException localSQLiteException)
    {
      for (;;) {}
    }
    if (paramContext.deleteDatabase(paramString)) {}
    try
    {
      this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = paramContext.openOrCreateDatabase(paramString, 0, null);
    }
    catch (SQLiteException paramContext)
    {
      for (;;) {}
    }
    this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = null;
    paramContext = this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase;
    if (paramContext == null)
    {
      paramContext = new StringBuilder();
      paramContext.append("Unable to open or create ");
      paramContext.append(paramString);
      Log.e("HttpAuthDatabase", paramContext.toString());
      return;
    }
    if (paramContext.getVersion() != 1)
    {
      X5ApiCompatibilityUtils.beginTransactionNonExclusive(this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase);
      try
      {
        a();
        this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.setTransactionSuccessful();
        return;
      }
      finally
      {
        this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.endTransaction();
      }
    }
  }
  
  public static HttpAuthDatabase newInstance(final Context paramContext, final String paramString)
  {
    HttpAuthDatabase localHttpAuthDatabase = new HttpAuthDatabase();
    new Thread()
    {
      public void run()
      {
        HttpAuthDatabase.a(this.jdField_a_of_type_OrgChromiumAndroid_webviewHttpAuthDatabase, paramContext, paramString);
      }
    }.start();
    return localHttpAuthDatabase;
  }
  
  public void clearHttpAuthUsernamePassword()
  {
    if (!a()) {
      return;
    }
    this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.delete("httpauth", null, null);
  }
  
  /* Error */
  public String[] getHttpAuthUsernamePassword(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: aconst_null
    //   7: astore_3
    //   8: aload_1
    //   9: ifnull +195 -> 204
    //   12: aload_2
    //   13: ifnull +191 -> 204
    //   16: aload_0
    //   17: invokespecial 131	org/chromium/android_webview/HttpAuthDatabase:a	()Z
    //   20: ifne +5 -> 25
    //   23: aconst_null
    //   24: areturn
    //   25: aload_0
    //   26: getfield 27	org/chromium/android_webview/HttpAuthDatabase:jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   29: ldc -123
    //   31: iconst_2
    //   32: anewarray 15	java/lang/String
    //   35: dup
    //   36: iconst_0
    //   37: ldc -113
    //   39: aastore
    //   40: dup
    //   41: iconst_1
    //   42: ldc -111
    //   44: aastore
    //   45: ldc -109
    //   47: iconst_2
    //   48: anewarray 15	java/lang/String
    //   51: dup
    //   52: iconst_0
    //   53: aload_1
    //   54: aastore
    //   55: dup
    //   56: iconst_1
    //   57: aload_2
    //   58: aastore
    //   59: aconst_null
    //   60: aconst_null
    //   61: aconst_null
    //   62: invokevirtual 151	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   65: astore_2
    //   66: aload_3
    //   67: astore_1
    //   68: aload_2
    //   69: astore_3
    //   70: aload_2
    //   71: invokeinterface 156 1 0
    //   76: ifeq +52 -> 128
    //   79: aload_2
    //   80: astore_3
    //   81: aload_2
    //   82: aload_2
    //   83: ldc -113
    //   85: invokeinterface 160 2 0
    //   90: invokeinterface 164 2 0
    //   95: astore_1
    //   96: aload_2
    //   97: astore_3
    //   98: aload_2
    //   99: aload_2
    //   100: ldc -111
    //   102: invokeinterface 160 2 0
    //   107: invokeinterface 164 2 0
    //   112: astore 6
    //   114: iconst_2
    //   115: anewarray 15	java/lang/String
    //   118: dup
    //   119: iconst_0
    //   120: aload_1
    //   121: aastore
    //   122: dup
    //   123: iconst_1
    //   124: aload 6
    //   126: aastore
    //   127: astore_1
    //   128: aload_1
    //   129: astore_3
    //   130: aload_2
    //   131: ifnull +58 -> 189
    //   134: aload_2
    //   135: astore_3
    //   136: aload_1
    //   137: astore_2
    //   138: aload_3
    //   139: invokeinterface 167 1 0
    //   144: aload_2
    //   145: areturn
    //   146: astore_3
    //   147: aload_2
    //   148: astore_1
    //   149: aload_3
    //   150: astore_2
    //   151: goto +12 -> 163
    //   154: astore_1
    //   155: aconst_null
    //   156: astore_3
    //   157: goto +35 -> 192
    //   160: astore_2
    //   161: aconst_null
    //   162: astore_1
    //   163: aload_1
    //   164: astore_3
    //   165: ldc 64
    //   167: ldc -88
    //   169: aload_2
    //   170: invokestatic 88	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   173: pop
    //   174: aload 5
    //   176: astore_3
    //   177: aload_1
    //   178: ifnull +11 -> 189
    //   181: aload 4
    //   183: astore_2
    //   184: aload_1
    //   185: astore_3
    //   186: goto -48 -> 138
    //   189: aload_3
    //   190: areturn
    //   191: astore_1
    //   192: aload_3
    //   193: ifnull +9 -> 202
    //   196: aload_3
    //   197: invokeinterface 167 1 0
    //   202: aload_1
    //   203: athrow
    //   204: aconst_null
    //   205: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	206	0	this	HttpAuthDatabase
    //   0	206	1	paramString1	String
    //   0	206	2	paramString2	String
    //   7	132	3	str1	String
    //   146	4	3	localIllegalStateException	IllegalStateException
    //   156	41	3	localObject1	Object
    //   1	181	4	localObject2	Object
    //   4	171	5	localObject3	Object
    //   112	13	6	str2	String
    // Exception table:
    //   from	to	target	type
    //   70	79	146	java/lang/IllegalStateException
    //   81	96	146	java/lang/IllegalStateException
    //   98	114	146	java/lang/IllegalStateException
    //   25	66	154	finally
    //   25	66	160	java/lang/IllegalStateException
    //   70	79	191	finally
    //   81	96	191	finally
    //   98	114	191	finally
    //   165	174	191	finally
  }
  
  /* Error */
  public boolean hasHttpAuthUsernamePassword()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 131	org/chromium/android_webview/HttpAuthDatabase:a	()Z
    //   4: istore_1
    //   5: iconst_0
    //   6: istore_2
    //   7: iconst_0
    //   8: istore_3
    //   9: iload_1
    //   10: ifne +5 -> 15
    //   13: iconst_0
    //   14: ireturn
    //   15: aconst_null
    //   16: astore 4
    //   18: aconst_null
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 27	org/chromium/android_webview/HttpAuthDatabase:jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   25: ldc -123
    //   27: getstatic 19	org/chromium/android_webview/HttpAuthDatabase:jdField_a_of_type_ArrayOfJavaLangString	[Ljava/lang/String;
    //   30: aconst_null
    //   31: aconst_null
    //   32: aconst_null
    //   33: aconst_null
    //   34: aconst_null
    //   35: invokevirtual 151	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   38: astore 6
    //   40: aload 6
    //   42: astore 5
    //   44: aload 6
    //   46: astore 4
    //   48: aload 6
    //   50: invokeinterface 156 1 0
    //   55: istore_1
    //   56: iload_1
    //   57: istore_2
    //   58: aload 6
    //   60: ifnull +47 -> 107
    //   63: aload 6
    //   65: astore 4
    //   67: aload 4
    //   69: invokeinterface 167 1 0
    //   74: iload_1
    //   75: ireturn
    //   76: astore 4
    //   78: goto +31 -> 109
    //   81: astore 6
    //   83: aload 4
    //   85: astore 5
    //   87: ldc 64
    //   89: ldc -85
    //   91: aload 6
    //   93: invokestatic 88	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   96: pop
    //   97: aload 4
    //   99: ifnull +8 -> 107
    //   102: iload_3
    //   103: istore_1
    //   104: goto -37 -> 67
    //   107: iload_2
    //   108: ireturn
    //   109: aload 5
    //   111: ifnull +10 -> 121
    //   114: aload 5
    //   116: invokeinterface 167 1 0
    //   121: aload 4
    //   123: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	124	0	this	HttpAuthDatabase
    //   4	100	1	bool1	boolean
    //   6	102	2	bool2	boolean
    //   8	95	3	bool3	boolean
    //   16	52	4	localObject1	Object
    //   76	46	4	localObject2	Object
    //   19	96	5	localObject3	Object
    //   38	26	6	localCursor	android.database.Cursor
    //   81	11	6	localIllegalStateException	IllegalStateException
    // Exception table:
    //   from	to	target	type
    //   21	40	76	finally
    //   48	56	76	finally
    //   87	97	76	finally
    //   21	40	81	java/lang/IllegalStateException
    //   48	56	81	java/lang/IllegalStateException
  }
  
  public void setHttpAuthUsernamePassword(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if ((paramString1 != null) && (paramString2 != null))
    {
      if (!a()) {
        return;
      }
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("host", paramString1);
      localContentValues.put("realm", paramString2);
      localContentValues.put("username", paramString3);
      localContentValues.put("password", paramString4);
      this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.insert("httpauth", "host", localContentValues);
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\HttpAuthDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */