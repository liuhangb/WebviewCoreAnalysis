package com.tencent.smtt.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import com.tencent.smtt.webkit.ContextHolder;

public class e
{
  private static int jdField_a_of_type_Int = 0;
  private static e jdField_a_of_type_ComTencentSmttUtilE;
  private static String jdField_a_of_type_JavaLangString = "smtt_webviewPrivate_x5.db";
  private SQLiteDatabase jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase;
  private a jdField_a_of_type_ComTencentSmttUtilE$a;
  
  private SQLiteDatabase a()
  {
    if (this.jdField_a_of_type_ComTencentSmttUtilE$a == null) {
      a(jdField_a_of_type_JavaLangString);
    }
    a locala = this.jdField_a_of_type_ComTencentSmttUtilE$a;
    if ((locala != null) && (this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase == null)) {
      this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase = locala.getWritableDatabase();
    }
    return this.jdField_a_of_type_AndroidDatabaseSqliteSQLiteDatabase;
  }
  
  public static e a()
  {
    if (jdField_a_of_type_ComTencentSmttUtilE == null) {
      jdField_a_of_type_ComTencentSmttUtilE = new e();
    }
    return jdField_a_of_type_ComTencentSmttUtilE;
  }
  
  /* Error */
  public static boolean a(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    // Byte code:
    //   0: new 43	java/lang/StringBuilder
    //   3: dup
    //   4: ldc 45
    //   6: invokespecial 47	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   9: astore 4
    //   11: aload 4
    //   13: aload_1
    //   14: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   17: pop
    //   18: aload 4
    //   20: ldc 53
    //   22: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   25: pop
    //   26: aconst_null
    //   27: astore_3
    //   28: aconst_null
    //   29: astore_1
    //   30: aload_0
    //   31: aload 4
    //   33: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   36: aconst_null
    //   37: invokevirtual 63	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   40: astore_0
    //   41: aload_0
    //   42: astore_1
    //   43: aload_0
    //   44: astore_3
    //   45: aload_0
    //   46: invokeinterface 69 1 0
    //   51: istore_2
    //   52: iload_2
    //   53: ifeq +15 -> 68
    //   56: aload_0
    //   57: ifnull +9 -> 66
    //   60: aload_0
    //   61: invokeinterface 72 1 0
    //   66: iconst_1
    //   67: ireturn
    //   68: aload_0
    //   69: ifnull +9 -> 78
    //   72: aload_0
    //   73: invokeinterface 72 1 0
    //   78: iconst_0
    //   79: ireturn
    //   80: astore_0
    //   81: aload_1
    //   82: ifnull +9 -> 91
    //   85: aload_1
    //   86: invokeinterface 72 1 0
    //   91: aload_0
    //   92: athrow
    //   93: aload_3
    //   94: ifnull +9 -> 103
    //   97: aload_3
    //   98: invokeinterface 72 1 0
    //   103: iconst_0
    //   104: ireturn
    //   105: astore_0
    //   106: goto -13 -> 93
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	109	0	paramSQLiteDatabase	SQLiteDatabase
    //   0	109	1	paramString	String
    //   51	2	2	bool	boolean
    //   27	71	3	localSQLiteDatabase	SQLiteDatabase
    //   9	23	4	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   30	41	80	finally
    //   45	52	80	finally
    //   30	41	105	java/lang/Exception
    //   45	52	105	java/lang/Exception
  }
  
  public int a()
  {
    return a().getVersion();
  }
  
  public int a(String paramString)
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    try
    {
      StringBuilder localStringBuilder = new StringBuilder("select count(1) from ");
      localObject1 = localObject2;
      localStringBuilder.append(paramString);
      localObject1 = localObject2;
      localStringBuilder.append(";");
      localObject1 = localObject2;
      paramString = a(localStringBuilder.toString());
      localObject1 = paramString;
      boolean bool = paramString.moveToFirst();
      int i = 0;
      if (bool)
      {
        localObject1 = paramString;
        i = paramString.getInt(0);
      }
      if (paramString != null) {
        paramString.close();
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
  
  public int a(String paramString, ContentValues paramContentValues)
  {
    try
    {
      long l = a().insert(paramString, "Null", paramContentValues);
      return (int)l;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return -1;
  }
  
  public int a(String paramString1, ContentValues paramContentValues, String paramString2)
  {
    return a(paramString1, paramContentValues, paramString2, null);
  }
  
  public int a(String paramString1, ContentValues paramContentValues, String paramString2, String[] paramArrayOfString)
  {
    return a().update(paramString1, paramContentValues, paramString2, paramArrayOfString);
  }
  
  public Cursor a(String paramString)
  {
    try
    {
      paramString = a().rawQuery(paramString, null);
      return paramString;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public Cursor a(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = a(false, paramString1, paramString2, null, null, null, null);
      return paramString1;
    }
    finally
    {
      paramString1 = finally;
      throw paramString1;
    }
  }
  
  public Cursor a(boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    try
    {
      paramString1 = a().query(paramBoolean, paramString1, new String[] { "*" }, paramString2, null, paramString3, paramString4, paramString5, paramString6);
      return paramString1;
    }
    finally
    {
      paramString1 = finally;
      throw paramString1;
    }
  }
  
  public void a(String paramString)
  {
    try
    {
      this.jdField_a_of_type_ComTencentSmttUtilE$a = new a(ContextHolder.getInstance().getContext().getApplicationContext(), paramString, null, 11);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public boolean a(String paramString)
  {
    return a(a(), paramString);
  }
  
  public void b(String paramString)
  {
    try
    {
      a().execSQL(paramString);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void c(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = a();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DELETE FROM ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(";");
    localSQLiteDatabase.execSQL(localStringBuilder.toString());
  }
  
  private class a
    extends SQLiteOpenHelper
  {
    public a(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt)
    {
      super(paramString, paramCursorFactory, paramInt);
    }
    
    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {}
    
    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */