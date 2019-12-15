package com.tencent.common.plugin;

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
import com.tencent.basesupport.FLogger;
import com.tencent.common.utils.DataChangedListener;
import com.tencent.common.utils.FileUtilsF;
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
  public static final String TES_DB_NAME = "tes_db";
  public static final int TES_DB_VERSION = 1;
  public static final int USERDB_VER_CURRENT = 2;
  private int jdField_a_of_type_Int = 1;
  private SQLiteDatabase jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = null;
  private c jdField_a_of_type_ComTencentCommonPluginDBHelper$c;
  File jdField_a_of_type_JavaIoFile = null;
  private String jdField_a_of_type_JavaLangString;
  private HashMap<String, DataChangedListener> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  protected Context mContext;
  protected boolean mLoadCompleted = false;
  protected int mOldVersion;
  protected int newVersion;
  
  public DBHelper(Context paramContext, File paramFile, int paramInt)
  {
    this.mContext = paramContext.getApplicationContext();
    this.jdField_a_of_type_JavaIoFile = paramFile;
    this.jdField_a_of_type_Int = paramInt;
  }
  
  public DBHelper(Context paramContext, String paramString, int paramInt)
  {
    this.mContext = paramContext;
    this.jdField_a_of_type_JavaIoFile = null;
    this.jdField_a_of_type_JavaLangString = paramString;
    this.jdField_a_of_type_Int = paramInt;
  }
  
  private SQLiteOpenHelper a(String paramString)
    throws Exception
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("openConnection: ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(",mContext=");
    ((StringBuilder)localObject).append(this.mContext);
    FLogger.d("DBHelper", ((StringBuilder)localObject).toString());
    localObject = this.mContext;
    if ((localObject != null) && (this.jdField_a_of_type_JavaIoFile == null))
    {
      a((Context)localObject);
      int i = this.jdField_a_of_type_Int;
      this.jdField_a_of_type_ComTencentCommonPluginDBHelper$c = new c(this.mContext, paramString, null, i);
      paramString = new StringBuilder();
      paramString.append("openConnection..., OpenHelper: ");
      paramString.append(this.jdField_a_of_type_ComTencentCommonPluginDBHelper$c);
      FLogger.d("DBHelper", paramString.toString());
      return this.jdField_a_of_type_ComTencentCommonPluginDBHelper$c;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("openConnection: ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(",mContext null，mDatabaseFile=");
    ((StringBuilder)localObject).append(this.jdField_a_of_type_JavaIoFile);
    FLogger.d("DBHelper", ((StringBuilder)localObject).toString());
    return null;
  }
  
  private void a(Context paramContext)
  {
    FLogger.d("DBHelper", "fixDatabaseName...");
    paramContext = paramContext.getDatabasePath("database.db");
    if (paramContext != null)
    {
      if (!paramContext.exists()) {
        return;
      }
      try
      {
        FLogger.d("DBHelper", "fixDatabaseName, rename...");
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
  
  private static void a(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3, ContentValues paramContentValues)
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
  
  /* Error */
  private static void a(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3, String paramString4, ContentValues paramContentValues)
  {
    // Byte code:
    //   0: new 90	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   7: astore 6
    //   9: aload 6
    //   11: ldc -69
    //   13: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: pop
    //   17: aload 6
    //   19: aload_1
    //   20: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: pop
    //   24: ldc 104
    //   26: aload 6
    //   28: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   31: invokestatic 114	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   34: aload_0
    //   35: invokevirtual 164	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   38: aload_0
    //   39: aload_1
    //   40: invokestatic 191	com/tencent/common/plugin/DBHelper:existTable	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Z
    //   43: ifeq +15 -> 58
    //   46: ldc 104
    //   48: ldc -63
    //   50: invokestatic 114	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   53: aload_0
    //   54: aload_3
    //   55: invokevirtual 168	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   58: aload_0
    //   59: aload_2
    //   60: invokevirtual 168	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   63: aload 5
    //   65: ifnull +12 -> 77
    //   68: aload_0
    //   69: aload_1
    //   70: aconst_null
    //   71: aload 5
    //   73: invokevirtual 178	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   76: pop2
    //   77: aload 4
    //   79: invokestatic 174	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   82: ifne +9 -> 91
    //   85: aload_0
    //   86: aload 4
    //   88: invokevirtual 168	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   91: aload_0
    //   92: invokevirtual 181	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   95: goto +12 -> 107
    //   98: astore_1
    //   99: goto +13 -> 112
    //   102: astore_1
    //   103: aload_1
    //   104: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   107: aload_0
    //   108: invokevirtual 184	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   111: return
    //   112: aload_0
    //   113: invokevirtual 184	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   116: aload_1
    //   117: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	118	0	paramSQLiteDatabase	SQLiteDatabase
    //   0	118	1	paramString1	String
    //   0	118	2	paramString2	String
    //   0	118	3	paramString3	String
    //   0	118	4	paramString4	String
    //   0	118	5	paramContentValues	ContentValues
    //   7	20	6	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   38	58	98	finally
    //   58	63	98	finally
    //   68	77	98	finally
    //   77	91	98	finally
    //   91	95	98	finally
    //   103	107	98	finally
    //   38	58	102	java/lang/Exception
    //   58	63	102	java/lang/Exception
    //   68	77	102	java/lang/Exception
    //   77	91	102	java/lang/Exception
    //   91	95	102	java/lang/Exception
  }
  
  private void a(String paramString, int paramInt)
  {
    DataChangedListener localDataChangedListener = (DataChangedListener)this.jdField_a_of_type_JavaUtilHashMap.get(paramString);
    if (localDataChangedListener != null) {
      localDataChangedListener.onDataChanged(paramString);
    }
  }
  
  /* Error */
  private static boolean a(SQLiteDatabase paramSQLiteDatabase, String paramString1, String[] paramArrayOfString, String paramString2, String paramString3, String paramString4, String paramString5, ContentValues paramContentValues)
  {
    // Byte code:
    //   0: new 90	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   7: astore 11
    //   9: aload 11
    //   11: ldc -48
    //   13: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: pop
    //   17: aload 11
    //   19: aload_1
    //   20: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: pop
    //   24: ldc 104
    //   26: aload 11
    //   28: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   31: invokestatic 114	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   34: aload_0
    //   35: aload_1
    //   36: invokestatic 191	com/tencent/common/plugin/DBHelper:existTable	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Z
    //   39: istore 10
    //   41: goto +13 -> 54
    //   44: astore 11
    //   46: aload 11
    //   48: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   51: iconst_0
    //   52: istore 10
    //   54: iload 10
    //   56: ifeq +906 -> 962
    //   59: new 90	java/lang/StringBuilder
    //   62: dup
    //   63: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   66: astore 11
    //   68: aload 11
    //   70: ldc -46
    //   72: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: pop
    //   76: aload 11
    //   78: aload_1
    //   79: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: pop
    //   83: ldc 104
    //   85: aload 11
    //   87: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   90: invokestatic 114	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   93: new 90	java/lang/StringBuilder
    //   96: dup
    //   97: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   100: astore 11
    //   102: aload 11
    //   104: ldc -44
    //   106: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   109: pop
    //   110: aload 11
    //   112: aload_1
    //   113: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: pop
    //   117: aload 11
    //   119: ldc -42
    //   121: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: pop
    //   125: aload 11
    //   127: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   130: astore 12
    //   132: aconst_null
    //   133: astore 11
    //   135: aconst_null
    //   136: astore 13
    //   138: aload_0
    //   139: aload 12
    //   141: aconst_null
    //   142: invokevirtual 218	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   145: astore 12
    //   147: aload 12
    //   149: astore 13
    //   151: aload 12
    //   153: astore 11
    //   155: new 90	java/lang/StringBuilder
    //   158: dup
    //   159: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   162: astore 14
    //   164: aload 12
    //   166: astore 13
    //   168: aload 12
    //   170: astore 11
    //   172: aload 14
    //   174: ldc -36
    //   176: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   179: pop
    //   180: aload 12
    //   182: astore 13
    //   184: aload 12
    //   186: astore 11
    //   188: aload 14
    //   190: aload 12
    //   192: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   195: pop
    //   196: aload 12
    //   198: astore 13
    //   200: aload 12
    //   202: astore 11
    //   204: ldc 104
    //   206: aload 14
    //   208: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   211: invokestatic 114	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   214: aload 12
    //   216: ifnull +657 -> 873
    //   219: aload 12
    //   221: astore 13
    //   223: aload 12
    //   225: astore 11
    //   227: aload 12
    //   229: invokeinterface 225 1 0
    //   234: ifeq +639 -> 873
    //   237: aload 12
    //   239: astore 13
    //   241: aload 12
    //   243: astore 11
    //   245: ldc 104
    //   247: ldc -29
    //   249: invokestatic 114	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   252: aload 12
    //   254: astore 13
    //   256: aload 12
    //   258: astore 11
    //   260: new 90	java/lang/StringBuilder
    //   263: dup
    //   264: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   267: astore 4
    //   269: aload 12
    //   271: astore 13
    //   273: aload 12
    //   275: astore 11
    //   277: aload 4
    //   279: ldc -36
    //   281: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   284: pop
    //   285: aload 12
    //   287: astore 13
    //   289: aload 12
    //   291: astore 11
    //   293: aload 4
    //   295: aload 12
    //   297: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   300: pop
    //   301: aload 12
    //   303: astore 13
    //   305: aload 12
    //   307: astore 11
    //   309: ldc 104
    //   311: aload 4
    //   313: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   316: invokestatic 114	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   319: aload 12
    //   321: astore 13
    //   323: aload 12
    //   325: astore 11
    //   327: new 90	java/lang/StringBuilder
    //   330: dup
    //   331: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   334: astore 4
    //   336: aload 12
    //   338: astore 13
    //   340: aload 12
    //   342: astore 11
    //   344: aload_2
    //   345: arraylength
    //   346: istore 9
    //   348: iconst_0
    //   349: istore 8
    //   351: iload 8
    //   353: iload 9
    //   355: if_icmpge +132 -> 487
    //   358: aload_2
    //   359: iload 8
    //   361: aaload
    //   362: astore 7
    //   364: aload 12
    //   366: astore 13
    //   368: aload 12
    //   370: astore 11
    //   372: aload 12
    //   374: aload 7
    //   376: invokeinterface 231 2 0
    //   381: iconst_m1
    //   382: if_icmple +592 -> 974
    //   385: aload 12
    //   387: astore 13
    //   389: aload 12
    //   391: astore 11
    //   393: aload 4
    //   395: ldc -23
    //   397: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   400: pop
    //   401: aload 12
    //   403: astore 13
    //   405: aload 12
    //   407: astore 11
    //   409: aload 4
    //   411: aload 7
    //   413: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   416: pop
    //   417: aload 12
    //   419: astore 13
    //   421: aload 12
    //   423: astore 11
    //   425: new 90	java/lang/StringBuilder
    //   428: dup
    //   429: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   432: astore 14
    //   434: aload 12
    //   436: astore 13
    //   438: aload 12
    //   440: astore 11
    //   442: aload 14
    //   444: ldc -21
    //   446: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   449: pop
    //   450: aload 12
    //   452: astore 13
    //   454: aload 12
    //   456: astore 11
    //   458: aload 14
    //   460: aload 7
    //   462: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   465: pop
    //   466: aload 12
    //   468: astore 13
    //   470: aload 12
    //   472: astore 11
    //   474: ldc 104
    //   476: aload 14
    //   478: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   481: invokestatic 114	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   484: goto +490 -> 974
    //   487: aload 12
    //   489: astore 13
    //   491: aload 12
    //   493: astore 11
    //   495: aload 4
    //   497: invokevirtual 239	java/lang/StringBuilder:length	()I
    //   500: istore 8
    //   502: iload 8
    //   504: ifgt +17 -> 521
    //   507: aload 12
    //   509: ifnull +10 -> 519
    //   512: aload 12
    //   514: invokeinterface 242 1 0
    //   519: iconst_0
    //   520: ireturn
    //   521: aload 12
    //   523: astore 13
    //   525: aload 12
    //   527: astore 11
    //   529: aload 4
    //   531: iconst_0
    //   532: iconst_1
    //   533: invokevirtual 246	java/lang/StringBuilder:delete	(II)Ljava/lang/StringBuilder;
    //   536: pop
    //   537: aload 12
    //   539: astore 13
    //   541: aload 12
    //   543: astore 11
    //   545: aload_0
    //   546: invokevirtual 164	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   549: ldc 104
    //   551: ldc -8
    //   553: invokestatic 114	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   556: aload 6
    //   558: invokestatic 174	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   561: ifne +9 -> 570
    //   564: aload_0
    //   565: aload 6
    //   567: invokevirtual 168	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   570: new 90	java/lang/StringBuilder
    //   573: dup
    //   574: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   577: astore_2
    //   578: aload_2
    //   579: ldc -6
    //   581: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   584: pop
    //   585: aload_2
    //   586: aload_1
    //   587: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   590: pop
    //   591: aload_2
    //   592: ldc -4
    //   594: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   597: pop
    //   598: aload_2
    //   599: aload_1
    //   600: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   603: pop
    //   604: aload_2
    //   605: ldc -2
    //   607: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   610: pop
    //   611: aload_0
    //   612: aload_2
    //   613: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   616: invokevirtual 168	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   619: aload_0
    //   620: aload_3
    //   621: invokevirtual 168	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   624: new 90	java/lang/StringBuilder
    //   627: dup
    //   628: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   631: astore_2
    //   632: aload_2
    //   633: ldc_w 256
    //   636: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   639: pop
    //   640: aload_2
    //   641: aload_1
    //   642: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   645: pop
    //   646: aload_2
    //   647: ldc_w 258
    //   650: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   653: pop
    //   654: aload_2
    //   655: aload 4
    //   657: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   660: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   663: pop
    //   664: aload_2
    //   665: ldc_w 260
    //   668: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   671: pop
    //   672: aload_2
    //   673: aload 4
    //   675: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   678: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   681: pop
    //   682: aload_2
    //   683: ldc_w 262
    //   686: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   689: pop
    //   690: aload_2
    //   691: aload_1
    //   692: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   695: pop
    //   696: aload_2
    //   697: ldc -2
    //   699: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   702: pop
    //   703: aload_0
    //   704: aload_2
    //   705: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   708: invokevirtual 168	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   711: new 90	java/lang/StringBuilder
    //   714: dup
    //   715: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   718: astore_2
    //   719: aload_2
    //   720: ldc_w 264
    //   723: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   726: pop
    //   727: aload_2
    //   728: aload_1
    //   729: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   732: pop
    //   733: aload_2
    //   734: ldc -2
    //   736: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   739: pop
    //   740: aload_0
    //   741: aload_2
    //   742: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   745: invokevirtual 168	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   748: aload 5
    //   750: invokestatic 174	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   753: ifne +9 -> 762
    //   756: aload_0
    //   757: aload 5
    //   759: invokevirtual 168	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   762: ldc 104
    //   764: ldc_w 266
    //   767: invokestatic 114	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   770: aload_0
    //   771: invokevirtual 181	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   774: aload 12
    //   776: astore 13
    //   778: aload 12
    //   780: astore 11
    //   782: aload_0
    //   783: invokevirtual 184	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   786: goto +123 -> 909
    //   789: astore_1
    //   790: goto +61 -> 851
    //   793: astore_1
    //   794: new 90	java/lang/StringBuilder
    //   797: dup
    //   798: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   801: astore_2
    //   802: aload_2
    //   803: ldc_w 268
    //   806: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   809: pop
    //   810: aload_2
    //   811: aload_1
    //   812: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   815: pop
    //   816: ldc 104
    //   818: aload_2
    //   819: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   822: invokestatic 114	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   825: aload 12
    //   827: astore 13
    //   829: aload 12
    //   831: astore 11
    //   833: aload_0
    //   834: invokevirtual 184	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   837: aload 12
    //   839: ifnull +10 -> 849
    //   842: aload 12
    //   844: invokeinterface 242 1 0
    //   849: iconst_0
    //   850: ireturn
    //   851: aload 12
    //   853: astore 13
    //   855: aload 12
    //   857: astore 11
    //   859: aload_0
    //   860: invokevirtual 184	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   863: aload 12
    //   865: astore 13
    //   867: aload 12
    //   869: astore 11
    //   871: aload_1
    //   872: athrow
    //   873: aload 12
    //   875: astore 13
    //   877: aload 12
    //   879: astore 11
    //   881: ldc 104
    //   883: ldc_w 270
    //   886: invokestatic 114	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   889: aload 12
    //   891: astore 13
    //   893: aload 12
    //   895: astore 11
    //   897: aload_0
    //   898: aload_1
    //   899: aload_3
    //   900: aload 4
    //   902: aload 5
    //   904: aload 7
    //   906: invokestatic 272	com/tencent/common/plugin/DBHelper:a	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)V
    //   909: aload 12
    //   911: ifnull +61 -> 972
    //   914: aload 12
    //   916: astore 11
    //   918: goto +21 -> 939
    //   921: astore_0
    //   922: goto +26 -> 948
    //   925: astore_0
    //   926: aload 11
    //   928: astore 13
    //   930: aload_0
    //   931: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   934: aload 11
    //   936: ifnull +36 -> 972
    //   939: aload 11
    //   941: invokeinterface 242 1 0
    //   946: iconst_1
    //   947: ireturn
    //   948: aload 13
    //   950: ifnull +10 -> 960
    //   953: aload 13
    //   955: invokeinterface 242 1 0
    //   960: aload_0
    //   961: athrow
    //   962: aload_0
    //   963: aload_1
    //   964: aload_3
    //   965: aload 5
    //   967: aload 7
    //   969: invokestatic 274	com/tencent/common/plugin/DBHelper:a	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)V
    //   972: iconst_1
    //   973: ireturn
    //   974: iload 8
    //   976: iconst_1
    //   977: iadd
    //   978: istore 8
    //   980: goto -629 -> 351
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	983	0	paramSQLiteDatabase	SQLiteDatabase
    //   0	983	1	paramString1	String
    //   0	983	2	paramArrayOfString	String[]
    //   0	983	3	paramString2	String
    //   0	983	4	paramString3	String
    //   0	983	5	paramString4	String
    //   0	983	6	paramString5	String
    //   0	983	7	paramContentValues	ContentValues
    //   349	630	8	i	int
    //   346	10	9	j	int
    //   39	16	10	bool	boolean
    //   7	20	11	localStringBuilder1	StringBuilder
    //   44	3	11	localException	Exception
    //   66	874	11	localObject1	Object
    //   130	785	12	localObject2	Object
    //   136	818	13	localObject3	Object
    //   162	315	14	localStringBuilder2	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   34	41	44	java/lang/Exception
    //   549	570	789	finally
    //   570	762	789	finally
    //   762	774	789	finally
    //   794	825	789	finally
    //   549	570	793	android/database/SQLException
    //   570	762	793	android/database/SQLException
    //   762	774	793	android/database/SQLException
    //   138	147	921	finally
    //   155	164	921	finally
    //   172	180	921	finally
    //   188	196	921	finally
    //   204	214	921	finally
    //   227	237	921	finally
    //   245	252	921	finally
    //   260	269	921	finally
    //   277	285	921	finally
    //   293	301	921	finally
    //   309	319	921	finally
    //   327	336	921	finally
    //   344	348	921	finally
    //   372	385	921	finally
    //   393	401	921	finally
    //   409	417	921	finally
    //   425	434	921	finally
    //   442	450	921	finally
    //   458	466	921	finally
    //   474	484	921	finally
    //   495	502	921	finally
    //   529	537	921	finally
    //   545	549	921	finally
    //   782	786	921	finally
    //   833	837	921	finally
    //   859	863	921	finally
    //   871	873	921	finally
    //   881	889	921	finally
    //   897	909	921	finally
    //   930	934	921	finally
    //   138	147	925	java/lang/Exception
    //   155	164	925	java/lang/Exception
    //   172	180	925	java/lang/Exception
    //   188	196	925	java/lang/Exception
    //   204	214	925	java/lang/Exception
    //   227	237	925	java/lang/Exception
    //   245	252	925	java/lang/Exception
    //   260	269	925	java/lang/Exception
    //   277	285	925	java/lang/Exception
    //   293	301	925	java/lang/Exception
    //   309	319	925	java/lang/Exception
    //   327	336	925	java/lang/Exception
    //   344	348	925	java/lang/Exception
    //   372	385	925	java/lang/Exception
    //   393	401	925	java/lang/Exception
    //   409	417	925	java/lang/Exception
    //   425	434	925	java/lang/Exception
    //   442	450	925	java/lang/Exception
    //   458	466	925	java/lang/Exception
    //   474	484	925	java/lang/Exception
    //   495	502	925	java/lang/Exception
    //   529	537	925	java/lang/Exception
    //   545	549	925	java/lang/Exception
    //   782	786	925	java/lang/Exception
    //   833	837	925	java/lang/Exception
    //   859	863	925	java/lang/Exception
    //   871	873	925	java/lang/Exception
    //   881	889	925	java/lang/Exception
    //   897	909	925	java/lang/Exception
  }
  
  private boolean a(SQLiteException paramSQLiteException)
  {
    if (paramSQLiteException == null) {
      return false;
    }
    String str = paramSQLiteException.toString();
    paramSQLiteException = paramSQLiteException.getMessage();
    if (((str != null) && (str.contains("unable to open database file"))) || ((paramSQLiteException != null) && (paramSQLiteException.contains("unable to open database file"))))
    {
      FLogger.d("DBHelper", ">>>>> unable to open database file!!!!");
      FLogger.d("DBHelper", ">>>>> delete old database file!!!!");
      if (this.mContext == null)
      {
        FLogger.d("DBHelper", ">>>>> unable to delete database file for null mContext!!!!");
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
    FLogger.d("DBHelper", ((StringBuilder)localObject).toString());
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
    if (!a(paramSQLiteDatabase, paramString1, paramArrayOfString, paramString2, (String)localObject, paramString4, paramString5, paramContentValues)) {
      a(paramSQLiteDatabase, paramString1, paramString2, (String)localObject, paramString4, paramContentValues);
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
      FLogger.d("DBHelper", paramSQLiteDatabase.toString());
      return bool;
    }
    finally
    {
      if (localSQLiteDatabase != null) {
        localSQLiteDatabase.close();
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
    //   10: ifnull +220 -> 230
    //   13: aload_1
    //   14: invokestatic 174	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   17: ifne +213 -> 230
    //   20: aload_2
    //   21: invokestatic 174	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   24: ifeq +5 -> 29
    //   27: iconst_0
    //   28: ireturn
    //   29: new 90	java/lang/StringBuilder
    //   32: dup
    //   33: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   36: astore 9
    //   38: aload 9
    //   40: ldc -44
    //   42: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload 9
    //   48: aload_1
    //   49: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: aload 9
    //   55: ldc_w 347
    //   58: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: pop
    //   62: aload 9
    //   64: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   67: astore 10
    //   69: aconst_null
    //   70: astore_1
    //   71: aconst_null
    //   72: astore 9
    //   74: aload_0
    //   75: aload 10
    //   77: aconst_null
    //   78: invokevirtual 218	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   81: astore_0
    //   82: iload 6
    //   84: istore 5
    //   86: aload_0
    //   87: ifnull +80 -> 167
    //   90: aload_0
    //   91: astore 9
    //   93: aload_0
    //   94: astore_1
    //   95: aload_0
    //   96: invokeinterface 351 1 0
    //   101: astore 10
    //   103: iload 6
    //   105: istore 5
    //   107: aload 10
    //   109: ifnull +58 -> 167
    //   112: aload_0
    //   113: astore 9
    //   115: aload_0
    //   116: astore_1
    //   117: aload 10
    //   119: arraylength
    //   120: istore 4
    //   122: iconst_0
    //   123: istore_3
    //   124: iload 6
    //   126: istore 5
    //   128: iload_3
    //   129: iload 4
    //   131: if_icmpge +36 -> 167
    //   134: aload_0
    //   135: astore 9
    //   137: aload_0
    //   138: astore_1
    //   139: aload_2
    //   140: aload 10
    //   142: iload_3
    //   143: aaload
    //   144: invokevirtual 355	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   147: istore 5
    //   149: iload 5
    //   151: ifeq +9 -> 160
    //   154: iconst_1
    //   155: istore 5
    //   157: goto +10 -> 167
    //   160: iload_3
    //   161: iconst_1
    //   162: iadd
    //   163: istore_3
    //   164: goto -40 -> 124
    //   167: iload 5
    //   169: istore 6
    //   171: aload_0
    //   172: ifnull +41 -> 213
    //   175: aload_0
    //   176: invokeinterface 242 1 0
    //   181: iload 5
    //   183: ireturn
    //   184: astore_0
    //   185: goto +31 -> 216
    //   188: astore_0
    //   189: aload_1
    //   190: astore 9
    //   192: aload_0
    //   193: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   196: iload 8
    //   198: istore 6
    //   200: aload_1
    //   201: ifnull +12 -> 213
    //   204: iload 7
    //   206: istore 5
    //   208: aload_1
    //   209: astore_0
    //   210: goto -35 -> 175
    //   213: iload 6
    //   215: ireturn
    //   216: aload 9
    //   218: ifnull +10 -> 228
    //   221: aload 9
    //   223: invokeinterface 242 1 0
    //   228: aload_0
    //   229: athrow
    //   230: iconst_0
    //   231: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	232	0	paramSQLiteDatabase	SQLiteDatabase
    //   0	232	1	paramString1	String
    //   0	232	2	paramString2	String
    //   123	41	3	i	int
    //   120	12	4	j	int
    //   84	123	5	bool1	boolean
    //   7	207	6	bool2	boolean
    //   1	204	7	bool3	boolean
    //   4	193	8	bool4	boolean
    //   36	186	9	localObject1	Object
    //   67	74	10	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   74	82	184	finally
    //   95	103	184	finally
    //   117	122	184	finally
    //   139	149	184	finally
    //   192	196	184	finally
    //   74	82	188	java/lang/Exception
    //   95	103	188	java/lang/Exception
    //   117	122	188	java/lang/Exception
    //   139	149	188	java/lang/Exception
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
  
  public static SQLiteDatabase openDatabase(String paramString)
    throws Exception
  {
    return SQLiteDatabase.openDatabase(paramString, null, 268435456);
  }
  
  public void addTableListener(String paramString, DataChangedListener paramDataChangedListener)
  {
    if ((paramString != null) && (paramDataChangedListener != null)) {
      this.jdField_a_of_type_JavaUtilHashMap.put(paramString, paramDataChangedListener);
    }
  }
  
  /* Error */
  public int batchInsert(String paramString, java.util.List<ContentValues> paramList)
    throws Exception
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 378	com/tencent/common/plugin/DBHelper:getSQLiteDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   4: astore 6
    //   6: iconst_m1
    //   7: istore_3
    //   8: iload_3
    //   9: istore 4
    //   11: aload 6
    //   13: ifnull +141 -> 154
    //   16: aload 6
    //   18: invokevirtual 164	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   21: aload_2
    //   22: invokeinterface 384 1 0
    //   27: astore_2
    //   28: iconst_m1
    //   29: istore_3
    //   30: iload_3
    //   31: istore 4
    //   33: aload_2
    //   34: invokeinterface 389 1 0
    //   39: ifeq +52 -> 91
    //   42: iload_3
    //   43: istore 4
    //   45: aload 6
    //   47: aload_1
    //   48: ldc_w 391
    //   51: aload_2
    //   52: invokeinterface 395 1 0
    //   57: checkcast 397	android/content/ContentValues
    //   60: invokevirtual 178	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
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
    //   82: invokespecial 399	com/tencent/common/plugin/DBHelper:a	(Ljava/lang/String;I)V
    //   85: iload 5
    //   87: istore_3
    //   88: goto -58 -> 30
    //   91: iload_3
    //   92: istore 4
    //   94: aload 6
    //   96: invokevirtual 181	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   99: aload 6
    //   101: ifnull +8 -> 109
    //   104: aload 6
    //   106: invokevirtual 184	android/database/sqlite/SQLiteDatabase:endTransaction	()V
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
    //   124: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   127: iload_3
    //   128: istore 4
    //   130: aload 6
    //   132: ifnull +22 -> 154
    //   135: aload 6
    //   137: invokevirtual 184	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   140: iload_3
    //   141: ireturn
    //   142: aload 6
    //   144: ifnull +8 -> 152
    //   147: aload 6
    //   149: invokevirtual 184	android/database/sqlite/SQLiteDatabase:endTransaction	()V
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
    a(paramString, 0);
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
    a(paramString1, 0);
  }
  
  /* Error */
  public void closeConnection()
  {
    // Byte code:
    //   0: ldc 104
    //   2: ldc_w 411
    //   5: invokestatic 114	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   8: aload_0
    //   9: getfield 68	com/tencent/common/plugin/DBHelper:jdField_a_of_type_JavaIoFile	Ljava/io/File;
    //   12: ifnonnull +96 -> 108
    //   15: aload_0
    //   16: getfield 122	com/tencent/common/plugin/DBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper$c	Lcom/tencent/common/plugin/DBHelper$c;
    //   19: astore_2
    //   20: aload_2
    //   21: ifnull +205 -> 226
    //   24: aload_2
    //   25: invokevirtual 414	com/tencent/common/plugin/DBHelper$c:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   28: astore_2
    //   29: aload_2
    //   30: invokevirtual 417	android/database/sqlite/SQLiteDatabase:inTransaction	()Z
    //   33: ifeq +52 -> 85
    //   36: aload_2
    //   37: invokevirtual 181	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   40: aload_2
    //   41: invokevirtual 184	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   44: goto +41 -> 85
    //   47: astore_2
    //   48: aload_2
    //   49: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   52: goto +33 -> 85
    //   55: astore_3
    //   56: goto +15 -> 71
    //   59: astore_3
    //   60: aload_3
    //   61: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   64: aload_2
    //   65: invokevirtual 184	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   68: goto +17 -> 85
    //   71: aload_2
    //   72: invokevirtual 184	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   75: goto +8 -> 83
    //   78: astore_2
    //   79: aload_2
    //   80: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   83: aload_3
    //   84: athrow
    //   85: aload_0
    //   86: getfield 122	com/tencent/common/plugin/DBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper$c	Lcom/tencent/common/plugin/DBHelper$c;
    //   89: invokevirtual 418	com/tencent/common/plugin/DBHelper$c:close	()V
    //   92: goto +8 -> 100
    //   95: astore_2
    //   96: aload_2
    //   97: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   100: aload_0
    //   101: aconst_null
    //   102: putfield 122	com/tencent/common/plugin/DBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper$c	Lcom/tencent/common/plugin/DBHelper$c;
    //   105: goto +121 -> 226
    //   108: aload_0
    //   109: getfield 66	com/tencent/common/plugin/DBHelper:jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   112: astore_2
    //   113: aload_2
    //   114: ifnull +112 -> 226
    //   117: aload_2
    //   118: invokevirtual 417	android/database/sqlite/SQLiteDatabase:inTransaction	()Z
    //   121: istore_1
    //   122: iload_1
    //   123: ifeq +73 -> 196
    //   126: aload_0
    //   127: getfield 66	com/tencent/common/plugin/DBHelper:jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   130: invokevirtual 181	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   133: aload_0
    //   134: getfield 66	com/tencent/common/plugin/DBHelper:jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   137: invokevirtual 184	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   140: goto +56 -> 196
    //   143: astore_2
    //   144: aload_2
    //   145: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   148: goto +48 -> 196
    //   151: astore_2
    //   152: goto +22 -> 174
    //   155: astore_2
    //   156: aload_2
    //   157: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   160: aload_0
    //   161: getfield 66	com/tencent/common/plugin/DBHelper:jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   164: invokevirtual 184	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   167: goto +29 -> 196
    //   170: astore_2
    //   171: goto -27 -> 144
    //   174: aload_0
    //   175: getfield 66	com/tencent/common/plugin/DBHelper:jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   178: invokevirtual 184	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   181: goto +8 -> 189
    //   184: astore_3
    //   185: aload_3
    //   186: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   189: aload_2
    //   190: athrow
    //   191: astore_2
    //   192: aload_2
    //   193: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   196: aload_0
    //   197: getfield 66	com/tencent/common/plugin/DBHelper:jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   200: invokevirtual 421	android/database/sqlite/SQLiteDatabase:isOpen	()Z
    //   203: ifeq +18 -> 221
    //   206: aload_0
    //   207: getfield 66	com/tencent/common/plugin/DBHelper:jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   210: invokevirtual 422	android/database/sqlite/SQLiteDatabase:close	()V
    //   213: goto +8 -> 221
    //   216: astore_2
    //   217: aload_2
    //   218: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   221: aload_0
    //   222: aconst_null
    //   223: putfield 66	com/tencent/common/plugin/DBHelper:jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   226: ldc 104
    //   228: ldc_w 424
    //   231: invokestatic 114	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
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
    a(paramString1, 0);
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
  
  public boolean existNoLocalized(String paramString)
    throws Exception
  {
    return existTable(getSQLiteDatabaseNoLocalized(), paramString);
  }
  
  protected String getDBName()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public SQLiteOpenHelper getOpenHelper()
    throws Exception
  {
    if (this.jdField_a_of_type_ComTencentCommonPluginDBHelper$c == null) {
      a(getDBName());
    }
    return this.jdField_a_of_type_ComTencentCommonPluginDBHelper$c;
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
    Object localObject1 = this.jdField_a_of_type_JavaIoFile;
    c localc;
    if (localObject1 == null)
    {
      if (this.jdField_a_of_type_ComTencentCommonPluginDBHelper$c == null) {
        a(getDBName());
      }
      try
      {
        localObject1 = this.jdField_a_of_type_ComTencentCommonPluginDBHelper$c.getWritableDatabase();
        return (SQLiteDatabase)localObject1;
      }
      catch (SQLiteException localSQLiteException)
      {
        localSQLiteException.printStackTrace();
        if (!a(localSQLiteException)) {
          break label387;
        }
      }
      localc = this.jdField_a_of_type_ComTencentCommonPluginDBHelper$c;
      if (localc != null) {
        localc.close();
      }
      a(getDBName());
      return this.jdField_a_of_type_ComTencentCommonPluginDBHelper$c.getWritableDatabase();
    }
    else if (this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase == null)
    {
      localc.getParentFile().mkdirs();
      try
      {
        if (Build.VERSION.SDK_INT < 11) {
          this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(this.jdField_a_of_type_JavaIoFile, new a());
        } else {
          this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(this.jdField_a_of_type_JavaIoFile.getAbsolutePath(), new a(), new b());
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
        if (((str1 != null) && (str1.contains("unable to open database file"))) || ((str2 != null) && (str2.contains("unable to open database file")) && (FileUtilsF.deleteQuietly(this.jdField_a_of_type_JavaIoFile)))) {
          try
          {
            if (Build.VERSION.SDK_INT < 11) {
              this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(this.jdField_a_of_type_JavaIoFile, new a());
            } else {
              this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(this.jdField_a_of_type_JavaIoFile.getAbsolutePath(), new a(), new b());
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
        j = this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.getVersion();
        i = j;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
      int j = this.jdField_a_of_type_Int;
      if (i != j) {
        try
        {
          this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.beginTransaction();
          if (i < j) {
            onUpgrade(this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase, i, j);
          }
          this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.setVersion(j);
          this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.setTransactionSuccessful();
        }
        finally
        {
          this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.endTransaction();
        }
      }
    }
    label387:
    return this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase;
  }
  
  public SQLiteDatabase getSQLiteDatabaseNoLocalized()
    throws Exception
  {
    Object localObject1 = this.jdField_a_of_type_JavaIoFile;
    c localc;
    if (localObject1 == null)
    {
      if (this.jdField_a_of_type_ComTencentCommonPluginDBHelper$c == null) {
        a(getDBName());
      }
      try
      {
        localObject1 = this.jdField_a_of_type_ComTencentCommonPluginDBHelper$c.getWritableDatabase();
        return (SQLiteDatabase)localObject1;
      }
      catch (SQLiteException localSQLiteException)
      {
        localSQLiteException.printStackTrace();
        if (!a(localSQLiteException)) {
          break label393;
        }
      }
      localc = this.jdField_a_of_type_ComTencentCommonPluginDBHelper$c;
      if (localc != null) {
        localc.close();
      }
      a(getDBName());
      return this.jdField_a_of_type_ComTencentCommonPluginDBHelper$c.getWritableDatabase();
    }
    else if (this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase == null)
    {
      localc.getParentFile().mkdirs();
      try
      {
        if (Build.VERSION.SDK_INT < 11) {
          this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(this.jdField_a_of_type_JavaIoFile, new a());
        } else {
          this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = SQLiteDatabase.openDatabase(this.jdField_a_of_type_JavaIoFile.getAbsolutePath(), new a(), 268435472, new b());
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
        if (((str1 != null) && (str1.contains("unable to open database file"))) || ((str2 != null) && (str2.contains("unable to open database file")) && (FileUtilsF.deleteQuietly(this.jdField_a_of_type_JavaIoFile)))) {
          try
          {
            if (Build.VERSION.SDK_INT < 11) {
              this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(this.jdField_a_of_type_JavaIoFile, new a());
            } else {
              this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = SQLiteDatabase.openDatabase(this.jdField_a_of_type_JavaIoFile.getAbsolutePath(), new a(), 268435472, new b());
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
        j = this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.getVersion();
        i = j;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
      int j = this.jdField_a_of_type_Int;
      if (i != j) {
        try
        {
          this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.beginTransaction();
          if (i < j) {
            onUpgrade(this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase, i, j);
          }
          this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.setVersion(j);
          this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.setTransactionSuccessful();
        }
        finally
        {
          this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase.endTransaction();
        }
      }
    }
    label393:
    return this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase;
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
      a(paramString, 1);
    }
    return i;
  }
  
  /* Error */
  public boolean isColumnExist(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_2
    //   1: invokestatic 174	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
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
    //   22: new 90	java/lang/StringBuilder
    //   25: dup
    //   26: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   29: astore 9
    //   31: aload 9
    //   33: ldc -44
    //   35: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: pop
    //   39: aload 9
    //   41: aload_1
    //   42: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload 9
    //   48: ldc_w 347
    //   51: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: pop
    //   55: aload 9
    //   57: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   60: astore 10
    //   62: aconst_null
    //   63: astore_1
    //   64: aconst_null
    //   65: astore 9
    //   67: aload_0
    //   68: invokevirtual 378	com/tencent/common/plugin/DBHelper:getSQLiteDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   71: aload 10
    //   73: aconst_null
    //   74: invokevirtual 218	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   77: astore 10
    //   79: iload 6
    //   81: istore 5
    //   83: aload 10
    //   85: ifnull +87 -> 172
    //   88: aload 10
    //   90: astore 9
    //   92: aload 10
    //   94: astore_1
    //   95: aload 10
    //   97: invokeinterface 351 1 0
    //   102: astore 11
    //   104: iload 6
    //   106: istore 5
    //   108: aload 11
    //   110: ifnull +62 -> 172
    //   113: aload 10
    //   115: astore 9
    //   117: aload 10
    //   119: astore_1
    //   120: aload 11
    //   122: arraylength
    //   123: istore 4
    //   125: iconst_0
    //   126: istore_3
    //   127: iload 6
    //   129: istore 5
    //   131: iload_3
    //   132: iload 4
    //   134: if_icmpge +38 -> 172
    //   137: aload 10
    //   139: astore 9
    //   141: aload 10
    //   143: astore_1
    //   144: aload_2
    //   145: aload 11
    //   147: iload_3
    //   148: aaload
    //   149: invokevirtual 355	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   152: istore 5
    //   154: iload 5
    //   156: ifeq +9 -> 165
    //   159: iconst_1
    //   160: istore 5
    //   162: goto +10 -> 172
    //   165: iload_3
    //   166: iconst_1
    //   167: iadd
    //   168: istore_3
    //   169: goto -42 -> 127
    //   172: iload 5
    //   174: istore 6
    //   176: aload 10
    //   178: ifnull +42 -> 220
    //   181: aload 10
    //   183: astore_1
    //   184: aload_1
    //   185: invokeinterface 242 1 0
    //   190: iload 5
    //   192: ireturn
    //   193: astore_1
    //   194: goto +29 -> 223
    //   197: astore_2
    //   198: aload_1
    //   199: astore 9
    //   201: aload_2
    //   202: invokevirtual 158	java/lang/Exception:printStackTrace	()V
    //   205: iload 8
    //   207: istore 6
    //   209: aload_1
    //   210: ifnull +10 -> 220
    //   213: iload 7
    //   215: istore 5
    //   217: goto -33 -> 184
    //   220: iload 6
    //   222: ireturn
    //   223: aload 9
    //   225: ifnull +10 -> 235
    //   228: aload 9
    //   230: invokeinterface 242 1 0
    //   235: aload_1
    //   236: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	237	0	this	DBHelper
    //   0	237	1	paramString1	String
    //   0	237	2	paramString2	String
    //   126	43	3	i	int
    //   123	12	4	j	int
    //   4	212	5	bool1	boolean
    //   13	208	6	bool2	boolean
    //   7	207	7	bool3	boolean
    //   10	196	8	bool4	boolean
    //   29	200	9	localObject1	Object
    //   60	122	10	localObject2	Object
    //   102	44	11	arrayOfString	String[]
    // Exception table:
    //   from	to	target	type
    //   67	79	193	finally
    //   95	104	193	finally
    //   120	125	193	finally
    //   144	154	193	finally
    //   201	205	193	finally
    //   67	79	197	java/lang/Exception
    //   95	104	197	java/lang/Exception
    //   120	125	197	java/lang/Exception
    //   144	154	197	java/lang/Exception
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
    //   23: new 90	java/lang/StringBuilder
    //   26: dup
    //   27: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   30: astore 13
    //   32: aload 11
    //   34: astore 10
    //   36: aload 12
    //   38: astore 9
    //   40: aload 13
    //   42: ldc_w 508
    //   45: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   48: pop
    //   49: aload 11
    //   51: astore 10
    //   53: aload 12
    //   55: astore 9
    //   57: aload 13
    //   59: lload_2
    //   60: invokevirtual 330	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   63: pop
    //   64: aload 11
    //   66: astore 10
    //   68: aload 12
    //   70: astore 9
    //   72: aload 13
    //   74: ldc_w 510
    //   77: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: pop
    //   81: aload 11
    //   83: astore 10
    //   85: aload 12
    //   87: astore 9
    //   89: aload_0
    //   90: aload_1
    //   91: aload 13
    //   93: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   96: aconst_null
    //   97: invokevirtual 513	com/tencent/common/plugin/DBHelper:query	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
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
    //   116: invokeinterface 516 1 0
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
    //   144: invokeinterface 242 1 0
    //   149: iload 5
    //   151: ireturn
    //   152: astore_1
    //   153: aload 10
    //   155: ifnull +10 -> 165
    //   158: aload 10
    //   160: invokeinterface 242 1 0
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
    FLogger.d("DBHelper", "load...");
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
    if (this.jdField_a_of_type_JavaUtilHashMap.containsKey(paramString)) {
      localDataChangedListener = (DataChangedListener)this.jdField_a_of_type_JavaUtilHashMap.remove(paramString);
    }
    return localDataChangedListener;
  }
  
  public void replace(String paramString, ContentValues paramContentValues)
    throws Exception
  {
    getSQLiteDatabase().replace(paramString, "Null", paramContentValues);
    a(paramString, 2);
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
    a(paramString1, 2);
    return i;
  }
  
  public void updateConnection()
    throws Exception
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("updateConnection..., dbName: ");
    localStringBuilder.append(getDBName());
    FLogger.d("DBHelper", localStringBuilder.toString());
    closeConnection();
    a(getDBName());
  }
  
  public static abstract interface SQLiteOpenHelperListener
  {
    public abstract void onCreate(SQLiteDatabase paramSQLiteDatabase);
    
    public abstract void onDowngrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2);
    
    public abstract void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2);
  }
  
  class a
    implements SQLiteDatabase.CursorFactory
  {
    a() {}
    
    public Cursor newCursor(SQLiteDatabase paramSQLiteDatabase, SQLiteCursorDriver paramSQLiteCursorDriver, String paramString, SQLiteQuery paramSQLiteQuery)
    {
      if (Build.VERSION.SDK_INT < 11) {
        return new SQLiteCursor(paramSQLiteDatabase, paramSQLiteCursorDriver, paramString, paramSQLiteQuery);
      }
      return new SQLiteCursor(paramSQLiteCursorDriver, paramString, paramSQLiteQuery);
    }
  }
  
  class b
    implements DatabaseErrorHandler
  {
    DefaultDatabaseErrorHandler jdField_a_of_type_AndroidDatabaseDefaultDatabaseErrorHandler = new DefaultDatabaseErrorHandler();
    boolean jdField_a_of_type_Boolean = false;
    
    b() {}
    
    public void onCorruption(SQLiteDatabase paramSQLiteDatabase)
    {
      if (!this.jdField_a_of_type_Boolean)
      {
        this.jdField_a_of_type_Boolean = true;
        this.jdField_a_of_type_AndroidDatabaseDefaultDatabaseErrorHandler.onCorruption(paramSQLiteDatabase);
        FileUtilsF.deleteQuietly(DBHelper.this.a);
        return;
      }
      throw new SQLiteDatabaseCorruptException("db corrupted and cannot be recovered");
    }
  }
  
  private class c
    extends SQLiteOpenHelper
  {
    public c(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt)
    {
      super(paramString, paramCursorFactory, paramInt);
      FLogger.d("*************************", "OpenHelper");
    }
    
    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      FLogger.d("*************************", "onCreate");
      DBHelper.this.onCreate(paramSQLiteDatabase);
    }
    
    public void onDowngrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      FLogger.d("*************************", "onDowngrade");
      DBHelper localDBHelper = DBHelper.this;
      localDBHelper.mOldVersion = paramInt1;
      localDBHelper.newVersion = paramInt2;
      localDBHelper.onUpgrade(paramSQLiteDatabase, paramInt1, paramInt2);
    }
    
    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      FLogger.d("*************************", "onUpgrade");
      DBHelper localDBHelper = DBHelper.this;
      localDBHelper.mOldVersion = paramInt1;
      localDBHelper.newVersion = paramInt2;
      localDBHelper.onUpgrade(paramSQLiteDatabase, paramInt1, paramInt2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\DBHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */