package com.tencent.tbs.common.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.tencent.common.utils.DataChangedListener;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LogUtils;
import java.io.File;
import java.util.HashMap;

public class DBHelper
{
  public static final int DATA_ADD = 1;
  public static final int DATA_CHANGED = 2;
  public static final int DATA_DEL = 0;
  public static final int DBVERSION_NOCONTROL = 1;
  public static final int DB_INSERT_ERROR = -1;
  public static final int DB_NO_SPACE = -2;
  public static final int PUBLICDB_VER_CURRENT = 37;
  public static final String PUBLIC_DB_NAME = "database";
  public static final String TABLE_DOWNLOAD = "download";
  private static final String TAG = "DBHelper";
  public static final String TES_DB_NAME = "tes_db";
  public static final int TES_DB_VERSION = 1;
  public static final int USERDB_VER_CURRENT = 2;
  private HashMap<String, DataChangedListener> listeners = new HashMap();
  protected Context mContext;
  private int mCurrent_DbVersion = 1;
  private SQLiteDatabase mDB = null;
  private String mDBName;
  File mDatabaseFile = null;
  protected boolean mLoadCompleted = false;
  protected int mOldVersion;
  protected int newVersion;
  private OpenHelper openHelper;
  
  public DBHelper(Context paramContext, File paramFile, int paramInt)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mDatabaseFile = paramFile;
    this.mCurrent_DbVersion = paramInt;
  }
  
  public DBHelper(Context paramContext, String paramString, int paramInt)
  {
    this.mContext = paramContext;
    this.mDatabaseFile = null;
    this.mDBName = paramString;
    this.mCurrent_DbVersion = paramInt;
  }
  
  private boolean checkException(SQLiteException paramSQLiteException)
  {
    if (paramSQLiteException == null) {
      return false;
    }
    String str = paramSQLiteException.toString();
    paramSQLiteException = paramSQLiteException.getMessage();
    if (((str != null) && (str.contains("unable to open database file"))) || ((paramSQLiteException != null) && (paramSQLiteException.contains("unable to open database file"))))
    {
      LogUtils.d("DBHelper", ">>>>> unable to open database file!!!!");
      LogUtils.d("DBHelper", ">>>>> delete old database file!!!!");
      if (this.mContext == null)
      {
        LogUtils.d("DBHelper", ">>>>> unable to delete database file for null mContext!!!!");
        return false;
      }
      paramSQLiteException = getDBName();
      paramSQLiteException = this.mContext.getDatabasePath(paramSQLiteException);
      if ((paramSQLiteException != null) && (paramSQLiteException.exists()))
      {
        paramSQLiteException.delete();
        return true;
      }
    }
    return false;
  }
  
  public static void checkUpgrade(SQLiteDatabase paramSQLiteDatabase, String paramString1, String[] paramArrayOfString, String paramString2, String paramString3, String paramString4, String paramString5, ContentValues paramContentValues)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("checkUpgrade: ");
    ((StringBuilder)localObject).append(paramString1);
    LogUtils.d("DBHelper", ((StringBuilder)localObject).toString());
    if (paramSQLiteDatabase == null) {
      return;
    }
    localObject = paramString3;
    if (TextUtils.isEmpty(paramString3))
    {
      paramString3 = new StringBuilder();
      paramString3.append("DROP TABLE ");
      paramString3.append(paramString1);
      paramString3.append(";");
      localObject = paramString3.toString();
    }
    if (!upgrade(paramSQLiteDatabase, paramString1, paramArrayOfString, paramString2, (String)localObject, paramString4, paramString5, paramContentValues)) {
      recreateTable(paramSQLiteDatabase, paramString1, paramString2, (String)localObject, paramString4, paramContentValues);
    }
  }
  
  public static void clearTable(SQLiteDatabase paramSQLiteDatabase, String paramString)
    throws Exception
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DELETE FROM ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(";");
    paramSQLiteDatabase.execSQL(localStringBuilder.toString());
  }
  
  private static void createTable(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3, ContentValues paramContentValues)
  {
    paramSQLiteDatabase.beginTransaction();
    try
    {
      paramSQLiteDatabase.execSQL(paramString2);
      if (!TextUtils.isEmpty(paramString3)) {
        paramSQLiteDatabase.execSQL(paramString3);
      }
      if (paramContentValues != null) {
        paramSQLiteDatabase.insert(paramString1, null, paramContentValues);
      }
      paramSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      paramSQLiteDatabase.endTransaction();
    }
  }
  
  public static boolean existTable(SQLiteDatabase paramSQLiteDatabase, String paramString)
    throws Exception
  {
    long l = System.currentTimeMillis();
    StringBuilder localStringBuilder = new StringBuilder("select 1 from sqlite_master where type='table' and name='");
    localStringBuilder.append(paramString);
    localStringBuilder.append("';");
    SQLiteDatabase localSQLiteDatabase = null;
    try
    {
      paramSQLiteDatabase = paramSQLiteDatabase.rawQuery(localStringBuilder.toString(), null);
      localSQLiteDatabase = paramSQLiteDatabase;
      boolean bool = paramSQLiteDatabase.moveToNext();
      if (paramSQLiteDatabase != null) {
        paramSQLiteDatabase.close();
      }
      paramSQLiteDatabase = new StringBuilder();
      paramSQLiteDatabase.append(paramString);
      paramSQLiteDatabase.append(" exist: ");
      paramSQLiteDatabase.append(bool);
      paramSQLiteDatabase.append(", used time: ");
      paramSQLiteDatabase.append(System.currentTimeMillis() - l);
      LogUtils.d("DBHelper", paramSQLiteDatabase.toString());
      return bool;
    }
    finally
    {
      if (localSQLiteDatabase != null) {
        localSQLiteDatabase.close();
      }
    }
  }
  
  private void fireDataChangedListener(String paramString, int paramInt)
  {
    DataChangedListener localDataChangedListener = (DataChangedListener)this.listeners.get(paramString);
    if (localDataChangedListener != null) {
      localDataChangedListener.onDataChanged(paramString);
    }
  }
  
  private void fixAppDatabaseName(Context paramContext)
  {
    LogUtils.d("DBHelper", "fixDatabaseName...");
    paramContext = paramContext.getDatabasePath("database.db");
    if (paramContext != null)
    {
      if (!paramContext.exists()) {
        return;
      }
      try
      {
        LogUtils.d("DBHelper", "fixDatabaseName, rename...");
        paramContext.renameTo(new File(paramContext.getParent(), getDBName()));
        return;
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
        return;
      }
    }
  }
  
  public static int getRowCount(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    try
    {
      Object localObject3 = new StringBuilder();
      localObject1 = localObject2;
      ((StringBuilder)localObject3).append("select count(1) from ");
      localObject1 = localObject2;
      ((StringBuilder)localObject3).append(paramString1);
      localObject1 = localObject2;
      localObject3 = ((StringBuilder)localObject3).toString();
      paramString1 = (String)localObject3;
      if (paramString2 != null)
      {
        localObject1 = localObject2;
        paramString1 = new StringBuilder();
        localObject1 = localObject2;
        paramString1.append((String)localObject3);
        localObject1 = localObject2;
        paramString1.append(" where ");
        localObject1 = localObject2;
        paramString1.append(paramString2);
        localObject1 = localObject2;
        paramString1 = paramString1.toString();
      }
      localObject1 = localObject2;
      paramSQLiteDatabase = paramSQLiteDatabase.rawQuery(paramString1, null);
      localObject1 = paramSQLiteDatabase;
      boolean bool = paramSQLiteDatabase.moveToFirst();
      int i = 0;
      if (bool)
      {
        localObject1 = paramSQLiteDatabase;
        i = paramSQLiteDatabase.getInt(0);
      }
      if (paramSQLiteDatabase != null) {
        paramSQLiteDatabase.close();
      }
      return i;
    }
    finally
    {
      if (localObject1 != null) {
        ((Cursor)localObject1).close();
      }
    }
  }
  
  /* Error */
  public static boolean isColumnExist(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 7
    //   3: iconst_0
    //   4: istore 8
    //   6: iconst_0
    //   7: istore 6
    //   9: aload_0
    //   10: ifnull +221 -> 231
    //   13: aload_1
    //   14: invokestatic 158	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   17: ifne +214 -> 231
    //   20: aload_2
    //   21: invokestatic 158	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   24: ifeq +5 -> 29
    //   27: iconst_0
    //   28: ireturn
    //   29: new 145	java/lang/StringBuilder
    //   32: dup
    //   33: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   36: astore 9
    //   38: aload 9
    //   40: ldc_w 282
    //   43: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: pop
    //   47: aload 9
    //   49: aload_1
    //   50: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   53: pop
    //   54: aload 9
    //   56: ldc_w 284
    //   59: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: pop
    //   63: aload 9
    //   65: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   68: astore 10
    //   70: aconst_null
    //   71: astore_1
    //   72: aconst_null
    //   73: astore 9
    //   75: aload_0
    //   76: aload 10
    //   78: aconst_null
    //   79: invokevirtual 216	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   82: astore_0
    //   83: iload 6
    //   85: istore 5
    //   87: aload_0
    //   88: ifnull +80 -> 168
    //   91: aload_0
    //   92: astore 9
    //   94: aload_0
    //   95: astore_1
    //   96: aload_0
    //   97: invokeinterface 288 1 0
    //   102: astore 10
    //   104: iload 6
    //   106: istore 5
    //   108: aload 10
    //   110: ifnull +58 -> 168
    //   113: aload_0
    //   114: astore 9
    //   116: aload_0
    //   117: astore_1
    //   118: aload 10
    //   120: arraylength
    //   121: istore 4
    //   123: iconst_0
    //   124: istore_3
    //   125: iload 6
    //   127: istore 5
    //   129: iload_3
    //   130: iload 4
    //   132: if_icmpge +36 -> 168
    //   135: aload_0
    //   136: astore 9
    //   138: aload_0
    //   139: astore_1
    //   140: aload_2
    //   141: aload 10
    //   143: iload_3
    //   144: aaload
    //   145: invokevirtual 292	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   148: istore 5
    //   150: iload 5
    //   152: ifeq +9 -> 161
    //   155: iconst_1
    //   156: istore 5
    //   158: goto +10 -> 168
    //   161: iload_3
    //   162: iconst_1
    //   163: iadd
    //   164: istore_3
    //   165: goto -40 -> 125
    //   168: iload 5
    //   170: istore 6
    //   172: aload_0
    //   173: ifnull +41 -> 214
    //   176: aload_0
    //   177: invokeinterface 224 1 0
    //   182: iload 5
    //   184: ireturn
    //   185: astore_0
    //   186: goto +31 -> 217
    //   189: astore_0
    //   190: aload_1
    //   191: astore 9
    //   193: aload_0
    //   194: invokevirtual 265	java/lang/Exception:printStackTrace	()V
    //   197: iload 8
    //   199: istore 6
    //   201: aload_1
    //   202: ifnull +12 -> 214
    //   205: iload 7
    //   207: istore 5
    //   209: aload_1
    //   210: astore_0
    //   211: goto -35 -> 176
    //   214: iload 6
    //   216: ireturn
    //   217: aload 9
    //   219: ifnull +10 -> 229
    //   222: aload 9
    //   224: invokeinterface 224 1 0
    //   229: aload_0
    //   230: athrow
    //   231: iconst_0
    //   232: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	233	0	paramSQLiteDatabase	SQLiteDatabase
    //   0	233	1	paramString1	String
    //   0	233	2	paramString2	String
    //   124	41	3	i	int
    //   121	12	4	j	int
    //   85	123	5	bool1	boolean
    //   7	208	6	bool2	boolean
    //   1	205	7	bool3	boolean
    //   4	194	8	bool4	boolean
    //   36	187	9	localObject1	Object
    //   68	74	10	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   75	83	185	finally
    //   96	104	185	finally
    //   118	123	185	finally
    //   140	150	185	finally
    //   193	197	185	finally
    //   75	83	189	java/lang/Exception
    //   96	104	189	java/lang/Exception
    //   118	123	189	java/lang/Exception
    //   140	150	189	java/lang/Exception
  }
  
  public static SQLiteOpenHelper openConnection(Context paramContext, String paramString, int paramInt, final SQLiteOpenHelperListener paramSQLiteOpenHelperListener)
    throws Exception
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    new SQLiteOpenHelper(paramContext, paramString, null, paramInt)
    {
      public void onCreate(SQLiteDatabase paramAnonymousSQLiteDatabase)
      {
        DBHelper.SQLiteOpenHelperListener localSQLiteOpenHelperListener = paramSQLiteOpenHelperListener;
        if (localSQLiteOpenHelperListener != null) {
          localSQLiteOpenHelperListener.onCreate(paramAnonymousSQLiteDatabase);
        }
      }
      
      public void onDowngrade(SQLiteDatabase paramAnonymousSQLiteDatabase, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        DBHelper.SQLiteOpenHelperListener localSQLiteOpenHelperListener = paramSQLiteOpenHelperListener;
        if (localSQLiteOpenHelperListener != null) {
          localSQLiteOpenHelperListener.onDowngrade(paramAnonymousSQLiteDatabase, paramAnonymousInt1, paramAnonymousInt2);
        }
      }
      
      public void onUpgrade(SQLiteDatabase paramAnonymousSQLiteDatabase, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        DBHelper.SQLiteOpenHelperListener localSQLiteOpenHelperListener = paramSQLiteOpenHelperListener;
        if (localSQLiteOpenHelperListener != null) {
          localSQLiteOpenHelperListener.onUpgrade(paramAnonymousSQLiteDatabase, paramAnonymousInt1, paramAnonymousInt2);
        }
      }
    };
  }
  
  private SQLiteOpenHelper openConnection(String paramString)
    throws Exception
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("openConnection: ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(",mContext=");
    ((StringBuilder)localObject).append(this.mContext);
    LogUtils.d("DBHelper", ((StringBuilder)localObject).toString());
    localObject = this.mContext;
    if ((localObject != null) && (this.mDatabaseFile == null))
    {
      fixAppDatabaseName((Context)localObject);
      int i = this.mCurrent_DbVersion;
      this.openHelper = new OpenHelper(this.mContext, paramString, null, i);
      paramString = new StringBuilder();
      paramString.append("openConnection..., OpenHelper: ");
      paramString.append(this.openHelper);
      LogUtils.d("DBHelper", paramString.toString());
      return this.openHelper;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("openConnection: ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(",mContext null，mDatabaseFile=");
    ((StringBuilder)localObject).append(this.mDatabaseFile);
    LogUtils.d("DBHelper", ((StringBuilder)localObject).toString());
    return null;
  }
  
  public static SQLiteDatabase openDatabase(String paramString)
    throws Exception
  {
    return SQLiteDatabase.openDatabase(paramString, null, 268435456);
  }
  
  /* Error */
  private static void recreateTable(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3, String paramString4, ContentValues paramContentValues)
  {
    // Byte code:
    //   0: new 145	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   7: astore 6
    //   9: aload 6
    //   11: ldc_w 324
    //   14: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   17: pop
    //   18: aload 6
    //   20: aload_1
    //   21: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: pop
    //   25: ldc 42
    //   27: aload 6
    //   29: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   32: invokestatic 121	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   35: aload_0
    //   36: invokevirtual 188	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   39: aload_0
    //   40: aload_1
    //   41: invokestatic 326	com/tencent/tbs/common/utils/DBHelper:existTable	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Z
    //   44: ifeq +16 -> 60
    //   47: ldc 42
    //   49: ldc_w 328
    //   52: invokestatic 121	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   55: aload_0
    //   56: aload_3
    //   57: invokevirtual 182	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   60: aload_0
    //   61: aload_2
    //   62: invokevirtual 182	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   65: aload 5
    //   67: ifnull +12 -> 79
    //   70: aload_0
    //   71: aload_1
    //   72: aconst_null
    //   73: aload 5
    //   75: invokevirtual 192	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   78: pop2
    //   79: aload 4
    //   81: invokestatic 158	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   84: ifne +9 -> 93
    //   87: aload_0
    //   88: aload 4
    //   90: invokevirtual 182	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   93: aload_0
    //   94: invokevirtual 195	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   97: goto +12 -> 109
    //   100: astore_1
    //   101: goto +13 -> 114
    //   104: astore_1
    //   105: aload_1
    //   106: invokevirtual 265	java/lang/Exception:printStackTrace	()V
    //   109: aload_0
    //   110: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   113: return
    //   114: aload_0
    //   115: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   118: aload_1
    //   119: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	120	0	paramSQLiteDatabase	SQLiteDatabase
    //   0	120	1	paramString1	String
    //   0	120	2	paramString2	String
    //   0	120	3	paramString3	String
    //   0	120	4	paramString4	String
    //   0	120	5	paramContentValues	ContentValues
    //   7	21	6	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   39	60	100	finally
    //   60	65	100	finally
    //   70	79	100	finally
    //   79	93	100	finally
    //   93	97	100	finally
    //   105	109	100	finally
    //   39	60	104	java/lang/Exception
    //   60	65	104	java/lang/Exception
    //   70	79	104	java/lang/Exception
    //   79	93	104	java/lang/Exception
    //   93	97	104	java/lang/Exception
  }
  
  /* Error */
  private static boolean upgrade(SQLiteDatabase paramSQLiteDatabase, String paramString1, String[] paramArrayOfString, String paramString2, String paramString3, String paramString4, String paramString5, ContentValues paramContentValues)
  {
    // Byte code:
    //   0: new 145	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   7: astore 11
    //   9: aload 11
    //   11: ldc_w 332
    //   14: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   17: pop
    //   18: aload 11
    //   20: aload_1
    //   21: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: pop
    //   25: ldc 42
    //   27: aload 11
    //   29: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   32: invokestatic 121	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   35: aload_0
    //   36: aload_1
    //   37: invokestatic 326	com/tencent/tbs/common/utils/DBHelper:existTable	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Z
    //   40: istore 10
    //   42: goto +13 -> 55
    //   45: astore 11
    //   47: aload 11
    //   49: invokevirtual 265	java/lang/Exception:printStackTrace	()V
    //   52: iconst_0
    //   53: istore 10
    //   55: iload 10
    //   57: ifeq +919 -> 976
    //   60: new 145	java/lang/StringBuilder
    //   63: dup
    //   64: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   67: astore 11
    //   69: aload 11
    //   71: ldc_w 334
    //   74: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: pop
    //   78: aload 11
    //   80: aload_1
    //   81: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   84: pop
    //   85: ldc 42
    //   87: aload 11
    //   89: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   92: invokestatic 121	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   95: new 145	java/lang/StringBuilder
    //   98: dup
    //   99: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   102: astore 11
    //   104: aload 11
    //   106: ldc_w 282
    //   109: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: pop
    //   113: aload 11
    //   115: aload_1
    //   116: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   119: pop
    //   120: aload 11
    //   122: ldc_w 336
    //   125: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: pop
    //   129: aload 11
    //   131: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   134: astore 12
    //   136: aconst_null
    //   137: astore 11
    //   139: aconst_null
    //   140: astore 13
    //   142: aload_0
    //   143: aload 12
    //   145: aconst_null
    //   146: invokevirtual 216	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   149: astore 12
    //   151: aload 12
    //   153: astore 13
    //   155: aload 12
    //   157: astore 11
    //   159: new 145	java/lang/StringBuilder
    //   162: dup
    //   163: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   166: astore 14
    //   168: aload 12
    //   170: astore 13
    //   172: aload 12
    //   174: astore 11
    //   176: aload 14
    //   178: ldc_w 338
    //   181: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   184: pop
    //   185: aload 12
    //   187: astore 13
    //   189: aload 12
    //   191: astore 11
    //   193: aload 14
    //   195: aload 12
    //   197: invokevirtual 305	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   200: pop
    //   201: aload 12
    //   203: astore 13
    //   205: aload 12
    //   207: astore 11
    //   209: ldc 42
    //   211: aload 14
    //   213: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   216: invokestatic 121	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   219: aload 12
    //   221: ifnull +666 -> 887
    //   224: aload 12
    //   226: astore 13
    //   228: aload 12
    //   230: astore 11
    //   232: aload 12
    //   234: invokeinterface 221 1 0
    //   239: ifeq +648 -> 887
    //   242: aload 12
    //   244: astore 13
    //   246: aload 12
    //   248: astore 11
    //   250: ldc 42
    //   252: ldc_w 340
    //   255: invokestatic 121	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   258: aload 12
    //   260: astore 13
    //   262: aload 12
    //   264: astore 11
    //   266: new 145	java/lang/StringBuilder
    //   269: dup
    //   270: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   273: astore 4
    //   275: aload 12
    //   277: astore 13
    //   279: aload 12
    //   281: astore 11
    //   283: aload 4
    //   285: ldc_w 338
    //   288: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   291: pop
    //   292: aload 12
    //   294: astore 13
    //   296: aload 12
    //   298: astore 11
    //   300: aload 4
    //   302: aload 12
    //   304: invokevirtual 305	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   307: pop
    //   308: aload 12
    //   310: astore 13
    //   312: aload 12
    //   314: astore 11
    //   316: ldc 42
    //   318: aload 4
    //   320: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   323: invokestatic 121	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   326: aload 12
    //   328: astore 13
    //   330: aload 12
    //   332: astore 11
    //   334: new 145	java/lang/StringBuilder
    //   337: dup
    //   338: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   341: astore 4
    //   343: aload 12
    //   345: astore 13
    //   347: aload 12
    //   349: astore 11
    //   351: aload_2
    //   352: arraylength
    //   353: istore 9
    //   355: iconst_0
    //   356: istore 8
    //   358: iload 8
    //   360: iload 9
    //   362: if_icmpge +134 -> 496
    //   365: aload_2
    //   366: iload 8
    //   368: aaload
    //   369: astore 7
    //   371: aload 12
    //   373: astore 13
    //   375: aload 12
    //   377: astore 11
    //   379: aload 12
    //   381: aload 7
    //   383: invokeinterface 344 2 0
    //   388: iconst_m1
    //   389: if_icmple +599 -> 988
    //   392: aload 12
    //   394: astore 13
    //   396: aload 12
    //   398: astore 11
    //   400: aload 4
    //   402: ldc_w 346
    //   405: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   408: pop
    //   409: aload 12
    //   411: astore 13
    //   413: aload 12
    //   415: astore 11
    //   417: aload 4
    //   419: aload 7
    //   421: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   424: pop
    //   425: aload 12
    //   427: astore 13
    //   429: aload 12
    //   431: astore 11
    //   433: new 145	java/lang/StringBuilder
    //   436: dup
    //   437: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   440: astore 14
    //   442: aload 12
    //   444: astore 13
    //   446: aload 12
    //   448: astore 11
    //   450: aload 14
    //   452: ldc_w 348
    //   455: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   458: pop
    //   459: aload 12
    //   461: astore 13
    //   463: aload 12
    //   465: astore 11
    //   467: aload 14
    //   469: aload 7
    //   471: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   474: pop
    //   475: aload 12
    //   477: astore 13
    //   479: aload 12
    //   481: astore 11
    //   483: ldc 42
    //   485: aload 14
    //   487: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   490: invokestatic 121	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   493: goto +495 -> 988
    //   496: aload 12
    //   498: astore 13
    //   500: aload 12
    //   502: astore 11
    //   504: aload 4
    //   506: invokevirtual 352	java/lang/StringBuilder:length	()I
    //   509: istore 8
    //   511: iload 8
    //   513: ifgt +17 -> 530
    //   516: aload 12
    //   518: ifnull +10 -> 528
    //   521: aload 12
    //   523: invokeinterface 224 1 0
    //   528: iconst_0
    //   529: ireturn
    //   530: aload 12
    //   532: astore 13
    //   534: aload 12
    //   536: astore 11
    //   538: aload 4
    //   540: iconst_0
    //   541: iconst_1
    //   542: invokevirtual 355	java/lang/StringBuilder:delete	(II)Ljava/lang/StringBuilder;
    //   545: pop
    //   546: aload 12
    //   548: astore 13
    //   550: aload 12
    //   552: astore 11
    //   554: aload_0
    //   555: invokevirtual 188	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   558: ldc 42
    //   560: ldc_w 357
    //   563: invokestatic 121	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   566: aload 6
    //   568: invokestatic 158	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   571: ifne +9 -> 580
    //   574: aload_0
    //   575: aload 6
    //   577: invokevirtual 182	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   580: new 145	java/lang/StringBuilder
    //   583: dup
    //   584: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   587: astore_2
    //   588: aload_2
    //   589: ldc_w 359
    //   592: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   595: pop
    //   596: aload_2
    //   597: aload_1
    //   598: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   601: pop
    //   602: aload_2
    //   603: ldc_w 361
    //   606: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   609: pop
    //   610: aload_2
    //   611: aload_1
    //   612: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   615: pop
    //   616: aload_2
    //   617: ldc_w 363
    //   620: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   623: pop
    //   624: aload_0
    //   625: aload_2
    //   626: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   629: invokevirtual 182	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   632: aload_0
    //   633: aload_3
    //   634: invokevirtual 182	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   637: new 145	java/lang/StringBuilder
    //   640: dup
    //   641: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   644: astore_2
    //   645: aload_2
    //   646: ldc_w 365
    //   649: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   652: pop
    //   653: aload_2
    //   654: aload_1
    //   655: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   658: pop
    //   659: aload_2
    //   660: ldc_w 367
    //   663: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   666: pop
    //   667: aload_2
    //   668: aload 4
    //   670: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   673: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   676: pop
    //   677: aload_2
    //   678: ldc_w 369
    //   681: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   684: pop
    //   685: aload_2
    //   686: aload 4
    //   688: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   691: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   694: pop
    //   695: aload_2
    //   696: ldc_w 371
    //   699: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   702: pop
    //   703: aload_2
    //   704: aload_1
    //   705: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   708: pop
    //   709: aload_2
    //   710: ldc_w 363
    //   713: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   716: pop
    //   717: aload_0
    //   718: aload_2
    //   719: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   722: invokevirtual 182	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   725: new 145	java/lang/StringBuilder
    //   728: dup
    //   729: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   732: astore_2
    //   733: aload_2
    //   734: ldc -96
    //   736: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   739: pop
    //   740: aload_2
    //   741: aload_1
    //   742: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   745: pop
    //   746: aload_2
    //   747: ldc_w 363
    //   750: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   753: pop
    //   754: aload_0
    //   755: aload_2
    //   756: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   759: invokevirtual 182	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   762: aload 5
    //   764: invokestatic 158	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   767: ifne +9 -> 776
    //   770: aload_0
    //   771: aload 5
    //   773: invokevirtual 182	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   776: ldc 42
    //   778: ldc_w 373
    //   781: invokestatic 121	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   784: aload_0
    //   785: invokevirtual 195	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   788: aload 12
    //   790: astore 13
    //   792: aload 12
    //   794: astore 11
    //   796: aload_0
    //   797: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   800: goto +123 -> 923
    //   803: astore_1
    //   804: goto +61 -> 865
    //   807: astore_1
    //   808: new 145	java/lang/StringBuilder
    //   811: dup
    //   812: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   815: astore_2
    //   816: aload_2
    //   817: ldc_w 375
    //   820: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   823: pop
    //   824: aload_2
    //   825: aload_1
    //   826: invokevirtual 305	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   829: pop
    //   830: ldc 42
    //   832: aload_2
    //   833: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   836: invokestatic 121	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   839: aload 12
    //   841: astore 13
    //   843: aload 12
    //   845: astore 11
    //   847: aload_0
    //   848: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   851: aload 12
    //   853: ifnull +10 -> 863
    //   856: aload 12
    //   858: invokeinterface 224 1 0
    //   863: iconst_0
    //   864: ireturn
    //   865: aload 12
    //   867: astore 13
    //   869: aload 12
    //   871: astore 11
    //   873: aload_0
    //   874: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   877: aload 12
    //   879: astore 13
    //   881: aload 12
    //   883: astore 11
    //   885: aload_1
    //   886: athrow
    //   887: aload 12
    //   889: astore 13
    //   891: aload 12
    //   893: astore 11
    //   895: ldc 42
    //   897: ldc_w 377
    //   900: invokestatic 121	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   903: aload 12
    //   905: astore 13
    //   907: aload 12
    //   909: astore 11
    //   911: aload_0
    //   912: aload_1
    //   913: aload_3
    //   914: aload 4
    //   916: aload 5
    //   918: aload 7
    //   920: invokestatic 170	com/tencent/tbs/common/utils/DBHelper:recreateTable	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)V
    //   923: aload 12
    //   925: ifnull +61 -> 986
    //   928: aload 12
    //   930: astore 11
    //   932: goto +21 -> 953
    //   935: astore_0
    //   936: goto +26 -> 962
    //   939: astore_0
    //   940: aload 11
    //   942: astore 13
    //   944: aload_0
    //   945: invokevirtual 265	java/lang/Exception:printStackTrace	()V
    //   948: aload 11
    //   950: ifnull +36 -> 986
    //   953: aload 11
    //   955: invokeinterface 224 1 0
    //   960: iconst_1
    //   961: ireturn
    //   962: aload 13
    //   964: ifnull +10 -> 974
    //   967: aload 13
    //   969: invokeinterface 224 1 0
    //   974: aload_0
    //   975: athrow
    //   976: aload_0
    //   977: aload_1
    //   978: aload_3
    //   979: aload 5
    //   981: aload 7
    //   983: invokestatic 379	com/tencent/tbs/common/utils/DBHelper:createTable	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)V
    //   986: iconst_1
    //   987: ireturn
    //   988: iload 8
    //   990: iconst_1
    //   991: iadd
    //   992: istore 8
    //   994: goto -636 -> 358
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	997	0	paramSQLiteDatabase	SQLiteDatabase
    //   0	997	1	paramString1	String
    //   0	997	2	paramArrayOfString	String[]
    //   0	997	3	paramString2	String
    //   0	997	4	paramString3	String
    //   0	997	5	paramString4	String
    //   0	997	6	paramString5	String
    //   0	997	7	paramContentValues	ContentValues
    //   356	637	8	i	int
    //   353	10	9	j	int
    //   40	16	10	bool	boolean
    //   7	21	11	localStringBuilder1	StringBuilder
    //   45	3	11	localException	Exception
    //   67	887	11	localObject1	Object
    //   134	795	12	localObject2	Object
    //   140	828	13	localObject3	Object
    //   166	320	14	localStringBuilder2	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   35	42	45	java/lang/Exception
    //   558	580	803	finally
    //   580	776	803	finally
    //   776	788	803	finally
    //   808	839	803	finally
    //   558	580	807	android/database/SQLException
    //   580	776	807	android/database/SQLException
    //   776	788	807	android/database/SQLException
    //   142	151	935	finally
    //   159	168	935	finally
    //   176	185	935	finally
    //   193	201	935	finally
    //   209	219	935	finally
    //   232	242	935	finally
    //   250	258	935	finally
    //   266	275	935	finally
    //   283	292	935	finally
    //   300	308	935	finally
    //   316	326	935	finally
    //   334	343	935	finally
    //   351	355	935	finally
    //   379	392	935	finally
    //   400	409	935	finally
    //   417	425	935	finally
    //   433	442	935	finally
    //   450	459	935	finally
    //   467	475	935	finally
    //   483	493	935	finally
    //   504	511	935	finally
    //   538	546	935	finally
    //   554	558	935	finally
    //   796	800	935	finally
    //   847	851	935	finally
    //   873	877	935	finally
    //   885	887	935	finally
    //   895	903	935	finally
    //   911	923	935	finally
    //   944	948	935	finally
    //   142	151	939	java/lang/Exception
    //   159	168	939	java/lang/Exception
    //   176	185	939	java/lang/Exception
    //   193	201	939	java/lang/Exception
    //   209	219	939	java/lang/Exception
    //   232	242	939	java/lang/Exception
    //   250	258	939	java/lang/Exception
    //   266	275	939	java/lang/Exception
    //   283	292	939	java/lang/Exception
    //   300	308	939	java/lang/Exception
    //   316	326	939	java/lang/Exception
    //   334	343	939	java/lang/Exception
    //   351	355	939	java/lang/Exception
    //   379	392	939	java/lang/Exception
    //   400	409	939	java/lang/Exception
    //   417	425	939	java/lang/Exception
    //   433	442	939	java/lang/Exception
    //   450	459	939	java/lang/Exception
    //   467	475	939	java/lang/Exception
    //   483	493	939	java/lang/Exception
    //   504	511	939	java/lang/Exception
    //   538	546	939	java/lang/Exception
    //   554	558	939	java/lang/Exception
    //   796	800	939	java/lang/Exception
    //   847	851	939	java/lang/Exception
    //   873	877	939	java/lang/Exception
    //   885	887	939	java/lang/Exception
    //   895	903	939	java/lang/Exception
    //   911	923	939	java/lang/Exception
  }
  
  public void addTableListener(String paramString, DataChangedListener paramDataChangedListener)
  {
    if ((paramString != null) && (paramDataChangedListener != null)) {
      this.listeners.put(paramString, paramDataChangedListener);
    }
  }
  
  /* Error */
  public int batchInsert(String paramString, java.util.List<ContentValues> paramList)
    throws Exception
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 391	com/tencent/tbs/common/utils/DBHelper:getSQLiteDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   4: astore 6
    //   6: iconst_m1
    //   7: istore_3
    //   8: iload_3
    //   9: istore 4
    //   11: aload 6
    //   13: ifnull +141 -> 154
    //   16: aload 6
    //   18: invokevirtual 188	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   21: aload_2
    //   22: invokeinterface 397 1 0
    //   27: astore_2
    //   28: iconst_m1
    //   29: istore_3
    //   30: iload_3
    //   31: istore 4
    //   33: aload_2
    //   34: invokeinterface 402 1 0
    //   39: ifeq +52 -> 91
    //   42: iload_3
    //   43: istore 4
    //   45: aload 6
    //   47: aload_1
    //   48: ldc_w 404
    //   51: aload_2
    //   52: invokeinterface 408 1 0
    //   57: checkcast 410	android/content/ContentValues
    //   60: invokevirtual 192	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   63: l2i
    //   64: istore 5
    //   66: iload 5
    //   68: istore_3
    //   69: iload 5
    //   71: iconst_m1
    //   72: if_icmpeq -42 -> 30
    //   75: iload 5
    //   77: istore 4
    //   79: aload_0
    //   80: aload_1
    //   81: iconst_1
    //   82: invokespecial 412	com/tencent/tbs/common/utils/DBHelper:fireDataChangedListener	(Ljava/lang/String;I)V
    //   85: iload 5
    //   87: istore_3
    //   88: goto -58 -> 30
    //   91: iload_3
    //   92: istore 4
    //   94: aload 6
    //   96: invokevirtual 195	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   99: aload 6
    //   101: ifnull +8 -> 109
    //   104: aload 6
    //   106: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   109: iload_3
    //   110: ireturn
    //   111: astore_1
    //   112: iload 4
    //   114: istore_3
    //   115: goto +8 -> 123
    //   118: astore_1
    //   119: goto +23 -> 142
    //   122: astore_1
    //   123: aload_1
    //   124: invokevirtual 265	java/lang/Exception:printStackTrace	()V
    //   127: iload_3
    //   128: istore 4
    //   130: aload 6
    //   132: ifnull +22 -> 154
    //   135: aload 6
    //   137: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   140: iload_3
    //   141: ireturn
    //   142: aload 6
    //   144: ifnull +8 -> 152
    //   147: aload 6
    //   149: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   152: aload_1
    //   153: athrow
    //   154: iload 4
    //   156: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	157	0	this	DBHelper
    //   0	157	1	paramString	String
    //   0	157	2	paramList	java.util.List<ContentValues>
    //   7	134	3	i	int
    //   9	146	4	j	int
    //   64	22	5	k	int
    //   4	144	6	localSQLiteDatabase	SQLiteDatabase
    // Exception table:
    //   from	to	target	type
    //   33	42	111	java/lang/Exception
    //   45	66	111	java/lang/Exception
    //   79	85	111	java/lang/Exception
    //   94	99	111	java/lang/Exception
    //   16	28	118	finally
    //   33	42	118	finally
    //   45	66	118	finally
    //   79	85	118	finally
    //   94	99	118	finally
    //   123	127	118	finally
    //   16	28	122	java/lang/Exception
  }
  
  public void beginTransaction()
    throws Exception
  {
    getSQLiteDatabase().beginTransaction();
  }
  
  public int clearTable(String paramString1, String paramString2, String[] paramArrayOfString)
    throws Exception
  {
    return getSQLiteDatabase().delete(paramString1, paramString2, paramArrayOfString);
  }
  
  public void clearTable(String paramString)
    throws Exception
  {
    SQLiteDatabase localSQLiteDatabase = getSQLiteDatabase();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DELETE FROM ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(";");
    localSQLiteDatabase.execSQL(localStringBuilder.toString());
    fireDataChangedListener(paramString, 0);
  }
  
  public void clearTable(String paramString1, String paramString2)
    throws Exception
  {
    if (TextUtils.isEmpty(paramString2))
    {
      clearTable(paramString1);
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DELETE FROM ");
    localStringBuilder.append(paramString1);
    localStringBuilder.append(" WHERE ");
    localStringBuilder.append(paramString2);
    localStringBuilder.append(";");
    paramString2 = localStringBuilder.toString();
    getSQLiteDatabase().execSQL(paramString2);
    fireDataChangedListener(paramString1, 0);
  }
  
  /* Error */
  public void closeConnection()
  {
    // Byte code:
    //   0: ldc 42
    //   2: ldc_w 424
    //   5: invokestatic 121	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   8: aload_0
    //   9: getfield 77	com/tencent/tbs/common/utils/DBHelper:mDatabaseFile	Ljava/io/File;
    //   12: ifnonnull +96 -> 108
    //   15: aload_0
    //   16: getfield 312	com/tencent/tbs/common/utils/DBHelper:openHelper	Lcom/tencent/tbs/common/utils/DBHelper$OpenHelper;
    //   19: astore_2
    //   20: aload_2
    //   21: ifnull +205 -> 226
    //   24: aload_2
    //   25: invokevirtual 427	com/tencent/tbs/common/utils/DBHelper$OpenHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   28: astore_2
    //   29: aload_2
    //   30: invokevirtual 430	android/database/sqlite/SQLiteDatabase:inTransaction	()Z
    //   33: ifeq +52 -> 85
    //   36: aload_2
    //   37: invokevirtual 195	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   40: aload_2
    //   41: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   44: goto +41 -> 85
    //   47: astore_2
    //   48: aload_2
    //   49: invokevirtual 265	java/lang/Exception:printStackTrace	()V
    //   52: goto +33 -> 85
    //   55: astore_3
    //   56: goto +15 -> 71
    //   59: astore_3
    //   60: aload_3
    //   61: invokevirtual 265	java/lang/Exception:printStackTrace	()V
    //   64: aload_2
    //   65: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   68: goto +17 -> 85
    //   71: aload_2
    //   72: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   75: goto +8 -> 83
    //   78: astore_2
    //   79: aload_2
    //   80: invokevirtual 265	java/lang/Exception:printStackTrace	()V
    //   83: aload_3
    //   84: athrow
    //   85: aload_0
    //   86: getfield 312	com/tencent/tbs/common/utils/DBHelper:openHelper	Lcom/tencent/tbs/common/utils/DBHelper$OpenHelper;
    //   89: invokevirtual 431	com/tencent/tbs/common/utils/DBHelper$OpenHelper:close	()V
    //   92: goto +8 -> 100
    //   95: astore_2
    //   96: aload_2
    //   97: invokevirtual 265	java/lang/Exception:printStackTrace	()V
    //   100: aload_0
    //   101: aconst_null
    //   102: putfield 312	com/tencent/tbs/common/utils/DBHelper:openHelper	Lcom/tencent/tbs/common/utils/DBHelper$OpenHelper;
    //   105: goto +121 -> 226
    //   108: aload_0
    //   109: getfield 75	com/tencent/tbs/common/utils/DBHelper:mDB	Landroid/database/sqlite/SQLiteDatabase;
    //   112: astore_2
    //   113: aload_2
    //   114: ifnull +112 -> 226
    //   117: aload_2
    //   118: invokevirtual 430	android/database/sqlite/SQLiteDatabase:inTransaction	()Z
    //   121: istore_1
    //   122: iload_1
    //   123: ifeq +73 -> 196
    //   126: aload_0
    //   127: getfield 75	com/tencent/tbs/common/utils/DBHelper:mDB	Landroid/database/sqlite/SQLiteDatabase;
    //   130: invokevirtual 195	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   133: aload_0
    //   134: getfield 75	com/tencent/tbs/common/utils/DBHelper:mDB	Landroid/database/sqlite/SQLiteDatabase;
    //   137: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   140: goto +56 -> 196
    //   143: astore_2
    //   144: aload_2
    //   145: invokevirtual 265	java/lang/Exception:printStackTrace	()V
    //   148: goto +48 -> 196
    //   151: astore_2
    //   152: goto +22 -> 174
    //   155: astore_2
    //   156: aload_2
    //   157: invokevirtual 265	java/lang/Exception:printStackTrace	()V
    //   160: aload_0
    //   161: getfield 75	com/tencent/tbs/common/utils/DBHelper:mDB	Landroid/database/sqlite/SQLiteDatabase;
    //   164: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   167: goto +29 -> 196
    //   170: astore_2
    //   171: goto -27 -> 144
    //   174: aload_0
    //   175: getfield 75	com/tencent/tbs/common/utils/DBHelper:mDB	Landroid/database/sqlite/SQLiteDatabase;
    //   178: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   181: goto +8 -> 189
    //   184: astore_3
    //   185: aload_3
    //   186: invokevirtual 265	java/lang/Exception:printStackTrace	()V
    //   189: aload_2
    //   190: athrow
    //   191: astore_2
    //   192: aload_2
    //   193: invokevirtual 265	java/lang/Exception:printStackTrace	()V
    //   196: aload_0
    //   197: getfield 75	com/tencent/tbs/common/utils/DBHelper:mDB	Landroid/database/sqlite/SQLiteDatabase;
    //   200: invokevirtual 434	android/database/sqlite/SQLiteDatabase:isOpen	()Z
    //   203: ifeq +18 -> 221
    //   206: aload_0
    //   207: getfield 75	com/tencent/tbs/common/utils/DBHelper:mDB	Landroid/database/sqlite/SQLiteDatabase;
    //   210: invokevirtual 435	android/database/sqlite/SQLiteDatabase:close	()V
    //   213: goto +8 -> 221
    //   216: astore_2
    //   217: aload_2
    //   218: invokevirtual 265	java/lang/Exception:printStackTrace	()V
    //   221: aload_0
    //   222: aconst_null
    //   223: putfield 75	com/tencent/tbs/common/utils/DBHelper:mDB	Landroid/database/sqlite/SQLiteDatabase;
    //   226: ldc 42
    //   228: ldc_w 437
    //   231: invokestatic 121	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   234: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	235	0	this	DBHelper
    //   121	2	1	bool	boolean
    //   19	22	2	localObject1	Object
    //   47	25	2	localException1	Exception
    //   78	2	2	localException2	Exception
    //   95	2	2	localException3	Exception
    //   112	6	2	localSQLiteDatabase	SQLiteDatabase
    //   143	2	2	localException4	Exception
    //   151	1	2	localObject2	Object
    //   155	2	2	localException5	Exception
    //   170	20	2	localException6	Exception
    //   191	2	2	localException7	Exception
    //   216	2	2	localException8	Exception
    //   55	1	3	localObject3	Object
    //   59	25	3	localException9	Exception
    //   184	2	3	localException10	Exception
    // Exception table:
    //   from	to	target	type
    //   40	44	47	java/lang/Exception
    //   64	68	47	java/lang/Exception
    //   36	40	55	finally
    //   60	64	55	finally
    //   36	40	59	java/lang/Exception
    //   71	75	78	java/lang/Exception
    //   85	92	95	java/lang/Exception
    //   133	140	143	java/lang/Exception
    //   126	133	151	finally
    //   156	160	151	finally
    //   126	133	155	java/lang/Exception
    //   160	167	170	java/lang/Exception
    //   174	181	184	java/lang/Exception
    //   117	122	191	java/lang/Exception
    //   144	148	191	java/lang/Exception
    //   185	189	191	java/lang/Exception
    //   189	191	191	java/lang/Exception
    //   196	213	216	java/lang/Exception
  }
  
  public int delete(String paramString1, String paramString2)
    throws Exception
  {
    return delete(paramString1, paramString2, null);
  }
  
  public int delete(String paramString1, String paramString2, String[] paramArrayOfString)
    throws Exception
  {
    int i = getSQLiteDatabase().delete(paramString1, paramString2, paramArrayOfString);
    fireDataChangedListener(paramString1, 0);
    return i;
  }
  
  public void deleteTable(SQLiteDatabase paramSQLiteDatabase, String paramString)
    throws Exception
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DROP TABLE ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(";");
    paramSQLiteDatabase.execSQL(localStringBuilder.toString());
  }
  
  public void deleteTable(String paramString)
    throws Exception
  {
    SQLiteDatabase localSQLiteDatabase = getSQLiteDatabase();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DROP TABLE ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(";");
    localSQLiteDatabase.execSQL(localStringBuilder.toString());
  }
  
  public void endTransaction()
    throws Exception
  {
    try
    {
      getSQLiteDatabase().setTransactionSuccessful();
      return;
    }
    finally
    {
      getSQLiteDatabase().endTransaction();
    }
  }
  
  public void endTransactionOnly()
  {
    try
    {
      getSQLiteDatabase().endTransaction();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void execSQL(String paramString)
    throws Exception
  {
    getSQLiteDatabase().execSQL(paramString);
  }
  
  public boolean exist(String paramString)
    throws Exception
  {
    return existTable(getSQLiteDatabase(), paramString);
  }
  
  protected String getDBName()
  {
    return this.mDBName;
  }
  
  public SQLiteOpenHelper getOpenHelper()
    throws Exception
  {
    if (this.openHelper == null) {
      openConnection(getDBName());
    }
    return this.openHelper;
  }
  
  public int getRowCount(String paramString)
    throws Exception
  {
    return getRowCount(paramString, null);
  }
  
  public int getRowCount(String paramString1, String paramString2)
    throws Exception
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    try
    {
      StringBuilder localStringBuilder = new StringBuilder("select count(1) from ");
      localObject1 = localObject2;
      localStringBuilder.append(paramString1);
      localObject1 = localObject2;
      localStringBuilder.append(";");
      if (paramString2 != null)
      {
        localObject1 = localObject2;
        paramString1 = new StringBuilder();
        localObject1 = localObject2;
        paramString1.append(" where ");
        localObject1 = localObject2;
        paramString1.append(paramString2);
        localObject1 = localObject2;
        localStringBuilder.append(paramString1.toString());
      }
      localObject1 = localObject2;
      paramString1 = query(localStringBuilder.toString());
      localObject1 = paramString1;
      boolean bool = paramString1.moveToFirst();
      int i = 0;
      if (bool)
      {
        localObject1 = paramString1;
        i = paramString1.getInt(0);
      }
      if (paramString1 != null) {
        paramString1.close();
      }
      return i;
    }
    finally
    {
      if (localObject1 != null) {
        ((Cursor)localObject1).close();
      }
    }
  }
  
  public SQLiteDatabase getSQLiteDatabase()
    throws Exception
  {
    Object localObject1 = this.mDatabaseFile;
    OpenHelper localOpenHelper;
    if (localObject1 == null)
    {
      if (this.openHelper == null) {
        openConnection(getDBName());
      }
      try
      {
        localObject1 = this.openHelper.getWritableDatabase();
        return (SQLiteDatabase)localObject1;
      }
      catch (SQLiteException localSQLiteException)
      {
        localSQLiteException.printStackTrace();
        if (!checkException(localSQLiteException)) {
          break label385;
        }
      }
      localOpenHelper = this.openHelper;
      if (localOpenHelper != null) {
        localOpenHelper.close();
      }
      openConnection(getDBName());
      return this.openHelper.getWritableDatabase();
    }
    else if (this.mDB == null)
    {
      localOpenHelper.getParentFile().mkdirs();
      try
      {
        if (Build.VERSION.SDK_INT < 11) {
          this.mDB = SQLiteDatabase.openOrCreateDatabase(this.mDatabaseFile, new CustomCursorFactory());
        } else {
          this.mDB = SQLiteDatabase.openOrCreateDatabase(this.mDatabaseFile.getAbsolutePath(), new CustomCursorFactory(), new CustomDbErrorHandler());
        }
      }
      catch (Error localError1)
      {
        localError1.printStackTrace();
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
        String str1 = localException3.toString();
        String str2 = localException3.getMessage();
        if (((str1 != null) && (str1.contains("unable to open database file"))) || ((str2 != null) && (str2.contains("unable to open database file")) && (FileUtils.deleteQuietly(this.mDatabaseFile)))) {
          try
          {
            if (Build.VERSION.SDK_INT < 11) {
              this.mDB = SQLiteDatabase.openOrCreateDatabase(this.mDatabaseFile, new CustomCursorFactory());
            } else {
              this.mDB = SQLiteDatabase.openOrCreateDatabase(this.mDatabaseFile.getAbsolutePath(), new CustomCursorFactory(), new CustomDbErrorHandler());
            }
          }
          catch (Error localError2)
          {
            localError2.printStackTrace();
          }
          catch (Exception localException1)
          {
            localException1.printStackTrace();
          }
        }
      }
      int i = 0;
      try
      {
        j = this.mDB.getVersion();
        i = j;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
      int j = this.mCurrent_DbVersion;
      if (i != j) {
        try
        {
          this.mDB.beginTransaction();
          if (i < j) {
            onUpgrade(this.mDB, i, j);
          }
          this.mDB.setVersion(j);
          this.mDB.setTransactionSuccessful();
        }
        finally
        {
          this.mDB.endTransaction();
        }
      }
    }
    label385:
    return this.mDB;
  }
  
  public int getVersion()
    throws Exception
  {
    return getSQLiteDatabase().getVersion();
  }
  
  public boolean inTransaction()
    throws Exception
  {
    return getSQLiteDatabase().inTransaction();
  }
  
  public void init(Context paramContext)
  {
    Context localContext = this.mContext;
    this.mContext = paramContext.getApplicationContext();
    if (localContext == null) {
      try
      {
        updateConnection();
        return;
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
      }
    }
  }
  
  public int insert(String paramString, ContentValues paramContentValues)
    throws Exception
  {
    int i = (int)getSQLiteDatabase().insert(paramString, "Null", paramContentValues);
    if (i != -1) {
      fireDataChangedListener(paramString, 1);
    }
    return i;
  }
  
  /* Error */
  public boolean isColumnExist(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_2
    //   1: invokestatic 158	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: istore 5
    //   6: iconst_0
    //   7: istore 7
    //   9: iconst_0
    //   10: istore 8
    //   12: iconst_0
    //   13: istore 6
    //   15: iload 5
    //   17: ifeq +5 -> 22
    //   20: iconst_0
    //   21: ireturn
    //   22: new 145	java/lang/StringBuilder
    //   25: dup
    //   26: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   29: astore 9
    //   31: aload 9
    //   33: ldc_w 282
    //   36: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   39: pop
    //   40: aload 9
    //   42: aload_1
    //   43: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: pop
    //   47: aload 9
    //   49: ldc_w 284
    //   52: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   55: pop
    //   56: aload 9
    //   58: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   61: astore 10
    //   63: aconst_null
    //   64: astore_1
    //   65: aconst_null
    //   66: astore 9
    //   68: aload_0
    //   69: invokevirtual 391	com/tencent/tbs/common/utils/DBHelper:getSQLiteDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   72: aload 10
    //   74: aconst_null
    //   75: invokevirtual 216	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   78: astore 10
    //   80: iload 6
    //   82: istore 5
    //   84: aload 10
    //   86: ifnull +87 -> 173
    //   89: aload 10
    //   91: astore 9
    //   93: aload 10
    //   95: astore_1
    //   96: aload 10
    //   98: invokeinterface 288 1 0
    //   103: astore 11
    //   105: iload 6
    //   107: istore 5
    //   109: aload 11
    //   111: ifnull +62 -> 173
    //   114: aload 10
    //   116: astore 9
    //   118: aload 10
    //   120: astore_1
    //   121: aload 11
    //   123: arraylength
    //   124: istore 4
    //   126: iconst_0
    //   127: istore_3
    //   128: iload 6
    //   130: istore 5
    //   132: iload_3
    //   133: iload 4
    //   135: if_icmpge +38 -> 173
    //   138: aload 10
    //   140: astore 9
    //   142: aload 10
    //   144: astore_1
    //   145: aload_2
    //   146: aload 11
    //   148: iload_3
    //   149: aaload
    //   150: invokevirtual 292	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   153: istore 5
    //   155: iload 5
    //   157: ifeq +9 -> 166
    //   160: iconst_1
    //   161: istore 5
    //   163: goto +10 -> 173
    //   166: iload_3
    //   167: iconst_1
    //   168: iadd
    //   169: istore_3
    //   170: goto -42 -> 128
    //   173: iload 5
    //   175: istore 6
    //   177: aload 10
    //   179: ifnull +42 -> 221
    //   182: aload 10
    //   184: astore_1
    //   185: aload_1
    //   186: invokeinterface 224 1 0
    //   191: iload 5
    //   193: ireturn
    //   194: astore_1
    //   195: goto +29 -> 224
    //   198: astore_2
    //   199: aload_1
    //   200: astore 9
    //   202: aload_2
    //   203: invokevirtual 265	java/lang/Exception:printStackTrace	()V
    //   206: iload 8
    //   208: istore 6
    //   210: aload_1
    //   211: ifnull +10 -> 221
    //   214: iload 7
    //   216: istore 5
    //   218: goto -33 -> 185
    //   221: iload 6
    //   223: ireturn
    //   224: aload 9
    //   226: ifnull +10 -> 236
    //   229: aload 9
    //   231: invokeinterface 224 1 0
    //   236: aload_1
    //   237: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	238	0	this	DBHelper
    //   0	238	1	paramString1	String
    //   0	238	2	paramString2	String
    //   127	43	3	i	int
    //   124	12	4	j	int
    //   4	213	5	bool1	boolean
    //   13	209	6	bool2	boolean
    //   7	208	7	bool3	boolean
    //   10	197	8	bool4	boolean
    //   29	201	9	localObject1	Object
    //   61	122	10	localObject2	Object
    //   103	44	11	arrayOfString	String[]
    // Exception table:
    //   from	to	target	type
    //   68	80	194	finally
    //   96	105	194	finally
    //   121	126	194	finally
    //   145	155	194	finally
    //   202	206	194	finally
    //   68	80	198	java/lang/Exception
    //   96	105	198	java/lang/Exception
    //   121	126	198	java/lang/Exception
    //   145	155	198	java/lang/Exception
  }
  
  /* Error */
  public boolean isUUIDExist(String paramString, long paramLong)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 12
    //   3: aconst_null
    //   4: astore 11
    //   6: iconst_0
    //   7: istore 7
    //   9: iconst_0
    //   10: istore 8
    //   12: iconst_0
    //   13: istore 6
    //   15: aload 11
    //   17: astore 10
    //   19: aload 12
    //   21: astore 9
    //   23: new 145	java/lang/StringBuilder
    //   26: dup
    //   27: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   30: astore 13
    //   32: aload 11
    //   34: astore 10
    //   36: aload 12
    //   38: astore 9
    //   40: aload 13
    //   42: ldc_w 513
    //   45: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   48: pop
    //   49: aload 11
    //   51: astore 10
    //   53: aload 12
    //   55: astore 9
    //   57: aload 13
    //   59: lload_2
    //   60: invokevirtual 234	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   63: pop
    //   64: aload 11
    //   66: astore 10
    //   68: aload 12
    //   70: astore 9
    //   72: aload 13
    //   74: ldc_w 515
    //   77: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: pop
    //   81: aload 11
    //   83: astore 10
    //   85: aload 12
    //   87: astore 9
    //   89: aload_0
    //   90: aload_1
    //   91: aload 13
    //   93: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   96: aconst_null
    //   97: invokevirtual 518	com/tencent/tbs/common/utils/DBHelper:query	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   100: astore_1
    //   101: iload 6
    //   103: istore 5
    //   105: aload_1
    //   106: ifnull +29 -> 135
    //   109: aload_1
    //   110: astore 10
    //   112: aload_1
    //   113: astore 9
    //   115: aload_1
    //   116: invokeinterface 521 1 0
    //   121: istore 4
    //   123: iload 6
    //   125: istore 5
    //   127: iload 4
    //   129: ifle +6 -> 135
    //   132: iconst_1
    //   133: istore 5
    //   135: iload 5
    //   137: istore 6
    //   139: aload_1
    //   140: ifnull +46 -> 186
    //   143: aload_1
    //   144: invokeinterface 224 1 0
    //   149: iload 5
    //   151: ireturn
    //   152: astore_1
    //   153: aload 10
    //   155: ifnull +10 -> 165
    //   158: aload 10
    //   160: invokeinterface 224 1 0
    //   165: aload_1
    //   166: athrow
    //   167: iload 8
    //   169: istore 6
    //   171: aload 9
    //   173: ifnull +13 -> 186
    //   176: aload 9
    //   178: astore_1
    //   179: iload 7
    //   181: istore 5
    //   183: goto -40 -> 143
    //   186: iload 6
    //   188: ireturn
    //   189: astore_1
    //   190: goto -23 -> 167
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	193	0	this	DBHelper
    //   0	193	1	paramString	String
    //   0	193	2	paramLong	long
    //   121	7	4	i	int
    //   103	79	5	bool1	boolean
    //   13	174	6	bool2	boolean
    //   7	173	7	bool3	boolean
    //   10	158	8	bool4	boolean
    //   21	156	9	localObject1	Object
    //   17	142	10	localObject2	Object
    //   4	78	11	localObject3	Object
    //   1	85	12	localObject4	Object
    //   30	62	13	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   23	32	152	finally
    //   40	49	152	finally
    //   57	64	152	finally
    //   72	81	152	finally
    //   89	101	152	finally
    //   115	123	152	finally
    //   23	32	189	java/lang/Exception
    //   40	49	189	java/lang/Exception
    //   57	64	189	java/lang/Exception
    //   72	81	189	java/lang/Exception
    //   89	101	189	java/lang/Exception
    //   115	123	189	java/lang/Exception
  }
  
  public Cursor listAll(String paramString1, String paramString2)
    throws Exception
  {
    return query(false, paramString1, null, null, null, paramString2, null);
  }
  
  public void load()
  {
    LogUtils.d("DBHelper", "load...");
    this.mLoadCompleted = true;
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase) {}
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {}
  
  public Cursor query(String paramString)
    throws Exception
  {
    return getSQLiteDatabase().rawQuery(paramString, null);
  }
  
  public Cursor query(String paramString1, String paramString2)
    throws Exception
  {
    return query(false, paramString1, paramString2, null, null, null, null);
  }
  
  public Cursor query(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    return query(false, paramString1, paramString2, null, null, paramString3, null);
  }
  
  public Cursor query(String paramString1, String[] paramArrayOfString1, String paramString2, String[] paramArrayOfString2, String paramString3)
    throws Exception
  {
    return getSQLiteDatabase().query(paramString1, paramArrayOfString1, paramString2, paramArrayOfString2, null, null, paramString3);
  }
  
  public Cursor query(boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
    throws Exception
  {
    return getSQLiteDatabase().query(paramBoolean, paramString1, new String[] { "*" }, paramString2, null, paramString3, paramString4, paramString5, paramString6);
  }
  
  public Cursor query(boolean paramBoolean, String paramString1, String paramString2, String[] paramArrayOfString, String paramString3, String paramString4, String paramString5, String paramString6)
    throws Exception
  {
    return getSQLiteDatabase().query(paramBoolean, paramString1, new String[] { "*" }, paramString2, paramArrayOfString, paramString3, paramString4, paramString5, paramString6);
  }
  
  public Cursor rawQuery(String paramString, String[] paramArrayOfString)
    throws Exception
  {
    return getSQLiteDatabase().rawQuery(paramString, paramArrayOfString);
  }
  
  public DataChangedListener removeTableListener(String paramString)
  {
    boolean bool = TextUtils.isEmpty(paramString);
    DataChangedListener localDataChangedListener = null;
    if (bool) {
      return null;
    }
    if (this.listeners.containsKey(paramString)) {
      localDataChangedListener = (DataChangedListener)this.listeners.remove(paramString);
    }
    return localDataChangedListener;
  }
  
  public void replace(String paramString, ContentValues paramContentValues)
    throws Exception
  {
    getSQLiteDatabase().replace(paramString, "Null", paramContentValues);
    fireDataChangedListener(paramString, 2);
  }
  
  public int update(String paramString1, ContentValues paramContentValues, String paramString2)
    throws Exception
  {
    return update(paramString1, paramContentValues, paramString2, null);
  }
  
  public int update(String paramString1, ContentValues paramContentValues, String paramString2, String[] paramArrayOfString)
    throws Exception
  {
    int i = getSQLiteDatabase().update(paramString1, paramContentValues, paramString2, paramArrayOfString);
    fireDataChangedListener(paramString1, 2);
    return i;
  }
  
  public void updateConnection()
    throws Exception
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("updateConnection..., dbName: ");
    localStringBuilder.append(getDBName());
    LogUtils.d("DBHelper", localStringBuilder.toString());
    closeConnection();
    openConnection(getDBName());
  }
  
  class CustomCursorFactory
    implements SQLiteDatabase.CursorFactory
  {
    CustomCursorFactory() {}
    
    public Cursor newCursor(SQLiteDatabase paramSQLiteDatabase, SQLiteCursorDriver paramSQLiteCursorDriver, String paramString, SQLiteQuery paramSQLiteQuery)
    {
      if (Build.VERSION.SDK_INT < 11) {
        return new SQLiteCursor(paramSQLiteDatabase, paramSQLiteCursorDriver, paramString, paramSQLiteQuery);
      }
      return new SQLiteCursor(paramSQLiteCursorDriver, paramString, paramSQLiteQuery);
    }
  }
  
  class CustomDbErrorHandler
    implements DatabaseErrorHandler
  {
    DefaultDatabaseErrorHandler __d = new DefaultDatabaseErrorHandler();
    boolean __once = false;
    
    CustomDbErrorHandler() {}
    
    public void onCorruption(SQLiteDatabase paramSQLiteDatabase)
    {
      if (!this.__once)
      {
        this.__once = true;
        this.__d.onCorruption(paramSQLiteDatabase);
        FileUtils.deleteQuietly(DBHelper.this.mDatabaseFile);
        return;
      }
      throw new SQLiteDatabaseCorruptException("db corrupted and cannot be recovered");
    }
  }
  
  private class OpenHelper
    extends SQLiteOpenHelper
  {
    public OpenHelper(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt)
    {
      super(paramString, paramCursorFactory, paramInt);
      LogUtils.d("*************************", "OpenHelper");
    }
    
    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      LogUtils.d("*************************", "onCreate");
      DBHelper.this.onCreate(paramSQLiteDatabase);
    }
    
    public void onDowngrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      LogUtils.d("*************************", "onDowngrade");
      DBHelper localDBHelper = DBHelper.this;
      localDBHelper.mOldVersion = paramInt1;
      localDBHelper.newVersion = paramInt2;
      localDBHelper.onUpgrade(paramSQLiteDatabase, paramInt1, paramInt2);
    }
    
    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      LogUtils.d("*************************", "onUpgrade");
      DBHelper localDBHelper = DBHelper.this;
      localDBHelper.mOldVersion = paramInt1;
      localDBHelper.newVersion = paramInt2;
      localDBHelper.onUpgrade(paramSQLiteDatabase, paramInt1, paramInt2);
    }
  }
  
  public static abstract interface SQLiteOpenHelperListener
  {
    public abstract void onCreate(SQLiteDatabase paramSQLiteDatabase);
    
    public abstract void onDowngrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2);
    
    public abstract void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\DBHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */