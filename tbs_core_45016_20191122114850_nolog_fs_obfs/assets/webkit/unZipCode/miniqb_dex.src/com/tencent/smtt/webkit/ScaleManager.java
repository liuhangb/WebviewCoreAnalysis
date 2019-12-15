package com.tencent.smtt.webkit;

import android.content.ContentValues;
import android.database.Cursor;
import com.tencent.smtt.util.e;
import org.chromium.base.annotations.UsedByReflection;

public class ScaleManager
{
  private static final String COLUMN_SCALE = "SCALE";
  private static final String COLUMN_URL = "URL";
  private static final String INDEX_SCALE = "CREATE INDEX COLUMN_URL_INDEX ON scale (URL);";
  public static final int SCALE_CAPACITY = 1000;
  public static final int SCALE_DEL_COUNT = 50;
  private static final String SQL_CREATE_SCALE = "CREATE TABLE scale ( URL TEXT PRIMARY KEY, SCALE DOUBLE);";
  private static final String TABLE_SCALE = "scale";
  private static boolean mIsFirstSavedScale = true;
  private static ScaleManager scaleManager;
  
  public static void alreadySaved()
  {
    mIsFirstSavedScale = false;
  }
  
  private double cursor2Scale(Cursor paramCursor, int paramInt)
  {
    if ((paramCursor != null) && (paramInt < paramCursor.getCount()))
    {
      paramCursor.moveToPosition(paramInt);
      return paramCursor.getDouble(paramCursor.getColumnIndexOrThrow("SCALE"));
    }
    return 0.0D;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static ScaleManager getInstance()
  {
    if (scaleManager == null)
    {
      scaleManager = new ScaleManager();
      scaleManager.init();
    }
    return scaleManager;
  }
  
  public static boolean isFirstSaved()
  {
    return mIsFirstSavedScale;
  }
  
  private ContentValues scale2Values(String paramString, double paramDouble)
  {
    if ((paramString != null) && (paramDouble != 0.0D))
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("URL", paramString);
      localContentValues.put("SCALE", Double.valueOf(paramDouble));
      return localContentValues;
    }
    return null;
  }
  
  public int addScale(String paramString, double paramDouble)
  {
    try
    {
      if (!hasCapacity())
      {
        StringBuffer localStringBuffer = new StringBuffer(200);
        localStringBuffer.append("delete from ");
        localStringBuffer.append("scale");
        localStringBuffer.append(" where ");
        localStringBuffer.append("URL");
        localStringBuffer.append(" in ( select ");
        localStringBuffer.append("URL");
        localStringBuffer.append(" from ");
        localStringBuffer.append("scale");
        localStringBuffer.append(" limit ");
        localStringBuffer.append(50);
        localStringBuffer.append(" )");
        e.a().b(localStringBuffer.toString());
      }
      if ((paramString != null) && (paramDouble != 0.0D))
      {
        int i = e.a().a("scale", scale2Values(paramString, paramDouble));
        return i;
      }
      return -1;
    }
    finally {}
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void destroy()
  {
    if (e.a().a("scale")) {
      e.a().c("scale");
    }
  }
  
  /* Error */
  public double findScale(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +5 -> 6
    //   4: dconst_0
    //   5: dreturn
    //   6: aconst_null
    //   7: astore 7
    //   9: aconst_null
    //   10: astore 6
    //   12: aload 6
    //   14: astore 4
    //   16: aload 7
    //   18: astore 5
    //   20: new 152	java/lang/StringBuilder
    //   23: dup
    //   24: invokespecial 153	java/lang/StringBuilder:<init>	()V
    //   27: astore 8
    //   29: aload 6
    //   31: astore 4
    //   33: aload 7
    //   35: astore 5
    //   37: aload 8
    //   39: ldc -101
    //   41: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: pop
    //   45: aload 6
    //   47: astore 4
    //   49: aload 7
    //   51: astore 5
    //   53: aload 8
    //   55: aload_1
    //   56: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: pop
    //   60: aload 6
    //   62: astore 4
    //   64: aload 7
    //   66: astore 5
    //   68: aload 8
    //   70: ldc -96
    //   72: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: pop
    //   76: aload 6
    //   78: astore 4
    //   80: aload 7
    //   82: astore 5
    //   84: aload 8
    //   86: invokevirtual 161	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   89: astore_1
    //   90: aload 6
    //   92: astore 4
    //   94: aload 7
    //   96: astore 5
    //   98: invokestatic 126	com/tencent/smtt/util/e:a	()Lcom/tencent/smtt/util/e;
    //   101: ldc 25
    //   103: aload_1
    //   104: invokevirtual 164	com/tencent/smtt/util/e:a	(Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   107: astore_1
    //   108: aload_1
    //   109: astore 4
    //   111: aload_1
    //   112: astore 5
    //   114: aload_0
    //   115: aload_1
    //   116: iconst_0
    //   117: invokespecial 166	com/tencent/smtt/webkit/ScaleManager:cursor2Scale	(Landroid/database/Cursor;I)D
    //   120: dstore_2
    //   121: aload_1
    //   122: astore 4
    //   124: aload_1
    //   125: astore 5
    //   127: aload_1
    //   128: invokeinterface 169 1 0
    //   133: dload_2
    //   134: dreturn
    //   135: astore_1
    //   136: aload 4
    //   138: ifnull +10 -> 148
    //   141: aload 4
    //   143: invokeinterface 169 1 0
    //   148: aload_1
    //   149: athrow
    //   150: aload 5
    //   152: ifnull +10 -> 162
    //   155: aload 5
    //   157: invokeinterface 169 1 0
    //   162: dconst_0
    //   163: dreturn
    //   164: astore_1
    //   165: goto -15 -> 150
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	168	0	this	ScaleManager
    //   0	168	1	paramString	String
    //   120	14	2	d	double
    //   14	128	4	localObject1	Object
    //   18	138	5	localObject2	Object
    //   10	81	6	localObject3	Object
    //   7	88	7	localObject4	Object
    //   27	58	8	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   20	29	135	finally
    //   37	45	135	finally
    //   53	60	135	finally
    //   68	76	135	finally
    //   84	90	135	finally
    //   98	108	135	finally
    //   114	121	135	finally
    //   127	133	135	finally
    //   20	29	164	java/lang/Exception
    //   37	45	164	java/lang/Exception
    //   53	60	164	java/lang/Exception
    //   68	76	164	java/lang/Exception
    //   84	90	164	java/lang/Exception
    //   98	108	164	java/lang/Exception
    //   114	121	164	java/lang/Exception
    //   127	133	164	java/lang/Exception
  }
  
  public int getScaleCount()
  {
    try
    {
      int i = e.a().a("scale");
      return i;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return -1;
  }
  
  public boolean hasCapacity()
  {
    return getScaleCount() < 1000;
  }
  
  public void init()
  {
    try
    {
      if (!e.a().a("scale"))
      {
        e.a().b("CREATE TABLE scale ( URL TEXT PRIMARY KEY, SCALE DOUBLE);");
        e.a().b("CREATE INDEX COLUMN_URL_INDEX ON scale (URL);");
      }
      else
      {
        e.a().a();
      }
      mIsFirstSavedScale = true;
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  /* Error */
  public boolean isUrlExist(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +5 -> 6
    //   4: iconst_0
    //   5: ireturn
    //   6: aconst_null
    //   7: astore 7
    //   9: aconst_null
    //   10: astore 6
    //   12: aload 6
    //   14: astore 4
    //   16: aload 7
    //   18: astore 5
    //   20: new 152	java/lang/StringBuilder
    //   23: dup
    //   24: invokespecial 153	java/lang/StringBuilder:<init>	()V
    //   27: astore 8
    //   29: aload 6
    //   31: astore 4
    //   33: aload 7
    //   35: astore 5
    //   37: aload 8
    //   39: ldc -101
    //   41: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: pop
    //   45: aload 6
    //   47: astore 4
    //   49: aload 7
    //   51: astore 5
    //   53: aload 8
    //   55: aload_1
    //   56: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: pop
    //   60: aload 6
    //   62: astore 4
    //   64: aload 7
    //   66: astore 5
    //   68: aload 8
    //   70: ldc -96
    //   72: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: pop
    //   76: aload 6
    //   78: astore 4
    //   80: aload 7
    //   82: astore 5
    //   84: aload 8
    //   86: invokevirtual 161	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   89: astore_1
    //   90: aload 6
    //   92: astore 4
    //   94: aload 7
    //   96: astore 5
    //   98: invokestatic 126	com/tencent/smtt/util/e:a	()Lcom/tencent/smtt/util/e;
    //   101: ldc 25
    //   103: aload_1
    //   104: invokevirtual 164	com/tencent/smtt/util/e:a	(Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   107: astore_1
    //   108: aload_1
    //   109: astore 4
    //   111: aload_1
    //   112: astore 5
    //   114: aload_0
    //   115: aload_1
    //   116: iconst_0
    //   117: invokespecial 166	com/tencent/smtt/webkit/ScaleManager:cursor2Scale	(Landroid/database/Cursor;I)D
    //   120: dstore_2
    //   121: aload_1
    //   122: astore 4
    //   124: aload_1
    //   125: astore 5
    //   127: aload_1
    //   128: invokeinterface 169 1 0
    //   133: dload_2
    //   134: dconst_0
    //   135: dcmpl
    //   136: ifeq +5 -> 141
    //   139: iconst_1
    //   140: ireturn
    //   141: iconst_0
    //   142: ireturn
    //   143: astore_1
    //   144: aload 4
    //   146: ifnull +10 -> 156
    //   149: aload 4
    //   151: invokeinterface 169 1 0
    //   156: aload_1
    //   157: athrow
    //   158: aload 5
    //   160: ifnull +10 -> 170
    //   163: aload 5
    //   165: invokeinterface 169 1 0
    //   170: iconst_0
    //   171: ireturn
    //   172: astore_1
    //   173: goto -15 -> 158
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	176	0	this	ScaleManager
    //   0	176	1	paramString	String
    //   120	14	2	d	double
    //   14	136	4	localObject1	Object
    //   18	146	5	localObject2	Object
    //   10	81	6	localObject3	Object
    //   7	88	7	localObject4	Object
    //   27	58	8	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   20	29	143	finally
    //   37	45	143	finally
    //   53	60	143	finally
    //   68	76	143	finally
    //   84	90	143	finally
    //   98	108	143	finally
    //   114	121	143	finally
    //   127	133	143	finally
    //   20	29	172	java/lang/Exception
    //   37	45	172	java/lang/Exception
    //   53	60	172	java/lang/Exception
    //   68	76	172	java/lang/Exception
    //   84	90	172	java/lang/Exception
    //   98	108	172	java/lang/Exception
    //   114	121	172	java/lang/Exception
    //   127	133	172	java/lang/Exception
  }
  
  /* Error */
  public boolean updateScale(String paramString, double paramDouble)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnull +93 -> 96
    //   6: dload_2
    //   7: dconst_0
    //   8: dcmpl
    //   9: ifeq +87 -> 96
    //   12: new 152	java/lang/StringBuilder
    //   15: dup
    //   16: invokespecial 153	java/lang/StringBuilder:<init>	()V
    //   19: astore 5
    //   21: aload 5
    //   23: ldc -101
    //   25: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: pop
    //   29: aload 5
    //   31: aload_1
    //   32: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: pop
    //   36: aload 5
    //   38: ldc -96
    //   40: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: pop
    //   44: aload 5
    //   46: invokevirtual 161	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   49: astore 5
    //   51: invokestatic 126	com/tencent/smtt/util/e:a	()Lcom/tencent/smtt/util/e;
    //   54: ldc 25
    //   56: aload_0
    //   57: aload_1
    //   58: dload_2
    //   59: invokespecial 136	com/tencent/smtt/webkit/ScaleManager:scale2Values	(Ljava/lang/String;D)Landroid/content/ContentValues;
    //   62: aload 5
    //   64: invokevirtual 185	com/tencent/smtt/util/e:a	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;)I
    //   67: ifne +20 -> 87
    //   70: aload_0
    //   71: aload_1
    //   72: dload_2
    //   73: invokevirtual 187	com/tencent/smtt/webkit/ScaleManager:addScale	(Ljava/lang/String;D)I
    //   76: istore 4
    //   78: iload 4
    //   80: ifle +7 -> 87
    //   83: aload_0
    //   84: monitorexit
    //   85: iconst_1
    //   86: ireturn
    //   87: aload_0
    //   88: monitorexit
    //   89: iconst_1
    //   90: ireturn
    //   91: astore_1
    //   92: aload_0
    //   93: monitorexit
    //   94: aload_1
    //   95: athrow
    //   96: aload_0
    //   97: monitorexit
    //   98: iconst_0
    //   99: ireturn
    //   100: astore_1
    //   101: goto -5 -> 96
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	104	0	this	ScaleManager
    //   0	104	1	paramString	String
    //   0	104	2	paramDouble	double
    //   76	3	4	i	int
    //   19	44	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   12	78	91	finally
    //   12	78	100	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ScaleManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */