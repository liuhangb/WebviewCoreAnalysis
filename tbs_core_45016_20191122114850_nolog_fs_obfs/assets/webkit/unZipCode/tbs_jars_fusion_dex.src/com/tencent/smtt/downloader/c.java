package com.tencent.smtt.downloader;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.HashMap;
import java.util.Map;

public class c
{
  private static c jdField_a_of_type_ComTencentSmttDownloaderC;
  private Context jdField_a_of_type_AndroidContentContext;
  public SharedPreferences a;
  public Map<String, Object> a;
  
  private c(Context paramContext)
  {
    this.jdField_a_of_type_JavaUtilMap = new HashMap();
    this.jdField_a_of_type_AndroidContentSharedPreferences = paramContext.getSharedPreferences("tbs_download_config_64", 4);
    this.jdField_a_of_type_AndroidContentContext = paramContext.getApplicationContext();
    if (this.jdField_a_of_type_AndroidContentContext == null) {
      this.jdField_a_of_type_AndroidContentContext = paramContext;
    }
  }
  
  public static c a()
  {
    try
    {
      c localc = jdField_a_of_type_ComTencentSmttDownloaderC;
      return localc;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static c a(Context paramContext)
  {
    try
    {
      if (jdField_a_of_type_ComTencentSmttDownloaderC == null) {
        jdField_a_of_type_ComTencentSmttDownloaderC = new c(paramContext);
      }
      paramContext = jdField_a_of_type_ComTencentSmttDownloaderC;
      return paramContext;
    }
    finally {}
  }
  
  public int a()
  {
    try
    {
      int j = this.jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_download_success_max_retrytimes", 0);
      int i = j;
      if (j == 0) {
        i = 3;
      }
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public long a()
  {
    try
    {
      int j = this.jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_download_maxflow", 0);
      int i = j;
      if (j == 0) {
        i = 20;
      }
      long l = i * 1024;
      return l * 1024L;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  public void a()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 30	com/tencent/smtt/downloader/c:jdField_a_of_type_AndroidContentSharedPreferences	Landroid/content/SharedPreferences;
    //   6: invokeinterface 63 1 0
    //   11: astore_1
    //   12: aload_0
    //   13: getfield 20	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   16: invokeinterface 69 1 0
    //   21: invokeinterface 75 1 0
    //   26: astore_2
    //   27: aload_2
    //   28: invokeinterface 81 1 0
    //   33: ifeq +157 -> 190
    //   36: aload_2
    //   37: invokeinterface 85 1 0
    //   42: checkcast 87	java/lang/String
    //   45: astore_3
    //   46: aload_0
    //   47: getfield 20	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   50: aload_3
    //   51: invokeinterface 91 2 0
    //   56: astore 4
    //   58: aload 4
    //   60: instanceof 87
    //   63: ifeq +19 -> 82
    //   66: aload_1
    //   67: aload_3
    //   68: aload 4
    //   70: checkcast 87	java/lang/String
    //   73: invokeinterface 97 3 0
    //   78: pop
    //   79: goto -52 -> 27
    //   82: aload 4
    //   84: instanceof 99
    //   87: ifeq +22 -> 109
    //   90: aload_1
    //   91: aload_3
    //   92: aload 4
    //   94: checkcast 99	java/lang/Boolean
    //   97: invokevirtual 102	java/lang/Boolean:booleanValue	()Z
    //   100: invokeinterface 106 3 0
    //   105: pop
    //   106: goto -79 -> 27
    //   109: aload 4
    //   111: instanceof 108
    //   114: ifeq +22 -> 136
    //   117: aload_1
    //   118: aload_3
    //   119: aload 4
    //   121: checkcast 108	java/lang/Long
    //   124: invokevirtual 111	java/lang/Long:longValue	()J
    //   127: invokeinterface 115 4 0
    //   132: pop
    //   133: goto -106 -> 27
    //   136: aload 4
    //   138: instanceof 117
    //   141: ifeq +22 -> 163
    //   144: aload_1
    //   145: aload_3
    //   146: aload 4
    //   148: checkcast 117	java/lang/Integer
    //   151: invokevirtual 120	java/lang/Integer:intValue	()I
    //   154: invokeinterface 124 3 0
    //   159: pop
    //   160: goto -133 -> 27
    //   163: aload 4
    //   165: instanceof 126
    //   168: ifeq -141 -> 27
    //   171: aload_1
    //   172: aload_3
    //   173: aload 4
    //   175: checkcast 126	java/lang/Float
    //   178: invokevirtual 130	java/lang/Float:floatValue	()F
    //   181: invokeinterface 134 3 0
    //   186: pop
    //   187: goto -160 -> 27
    //   190: aload_1
    //   191: invokeinterface 137 1 0
    //   196: pop
    //   197: aload_0
    //   198: getfield 20	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   201: invokeinterface 140 1 0
    //   206: goto +12 -> 218
    //   209: astore_1
    //   210: goto +11 -> 221
    //   213: astore_1
    //   214: aload_1
    //   215: invokevirtual 143	java/lang/Exception:printStackTrace	()V
    //   218: aload_0
    //   219: monitorexit
    //   220: return
    //   221: aload_0
    //   222: monitorexit
    //   223: aload_1
    //   224: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	225	0	this	c
    //   11	180	1	localEditor	SharedPreferences.Editor
    //   209	1	1	localObject1	Object
    //   213	11	1	localException	Exception
    //   26	11	2	localIterator	java.util.Iterator
    //   45	128	3	str	String
    //   56	118	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   2	27	209	finally
    //   27	79	209	finally
    //   82	106	209	finally
    //   109	133	209	finally
    //   136	160	209	finally
    //   163	187	209	finally
    //   190	206	209	finally
    //   214	218	209	finally
    //   2	27	213	java/lang/Exception
    //   27	79	213	java/lang/Exception
    //   82	106	213	java/lang/Exception
    //   109	133	213	java/lang/Exception
    //   136	160	213	java/lang/Exception
    //   163	187	213	java/lang/Exception
    //   190	206	213	java/lang/Exception
  }
  
  /* Error */
  public void a(int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 30	com/tencent/smtt/downloader/c:jdField_a_of_type_AndroidContentSharedPreferences	Landroid/content/SharedPreferences;
    //   6: invokeinterface 63 1 0
    //   11: astore_2
    //   12: aload_2
    //   13: ldc -110
    //   15: iload_1
    //   16: invokeinterface 124 3 0
    //   21: pop
    //   22: aload_2
    //   23: ldc -108
    //   25: invokestatic 153	java/lang/System:currentTimeMillis	()J
    //   28: invokeinterface 115 4 0
    //   33: pop
    //   34: aload_2
    //   35: invokeinterface 137 1 0
    //   40: pop
    //   41: goto +8 -> 49
    //   44: astore_2
    //   45: aload_0
    //   46: monitorexit
    //   47: aload_2
    //   48: athrow
    //   49: aload_0
    //   50: monitorexit
    //   51: return
    //   52: astore_2
    //   53: goto -4 -> 49
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	56	0	this	c
    //   0	56	1	paramInt	int
    //   11	24	2	localEditor	SharedPreferences.Editor
    //   44	4	2	localObject	Object
    //   52	1	2	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   2	41	44	finally
    //   2	41	52	java/lang/Exception
  }
  
  /* Error */
  public void a(boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 30	com/tencent/smtt/downloader/c:jdField_a_of_type_AndroidContentSharedPreferences	Landroid/content/SharedPreferences;
    //   6: invokeinterface 63 1 0
    //   11: astore_2
    //   12: aload_2
    //   13: ldc -100
    //   15: iload_1
    //   16: invokeinterface 106 3 0
    //   21: pop
    //   22: aload_2
    //   23: invokeinterface 137 1 0
    //   28: pop
    //   29: goto +8 -> 37
    //   32: astore_2
    //   33: aload_0
    //   34: monitorexit
    //   35: aload_2
    //   36: athrow
    //   37: aload_0
    //   38: monitorexit
    //   39: return
    //   40: astore_2
    //   41: goto -4 -> 37
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	44	0	this	c
    //   0	44	1	paramBoolean	boolean
    //   11	12	2	localEditor	SharedPreferences.Editor
    //   32	4	2	localObject	Object
    //   40	1	2	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   2	29	32	finally
    //   2	29	40	java/lang/Exception
  }
  
  public int b()
  {
    try
    {
      int j = this.jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_download_failed_max_retrytimes", 0);
      int i = j;
      if (j == 0) {
        i = 100;
      }
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public long b()
  {
    try
    {
      if (TbsDownloader.a() >= 0L)
      {
        l = TbsDownloader.a();
        return l;
      }
      long l = this.jdField_a_of_type_AndroidContentSharedPreferences.getLong("retry_interval", 86400L);
      return l;
    }
    finally {}
  }
  
  public void b(int paramInt)
  {
    try
    {
      SharedPreferences.Editor localEditor = this.jdField_a_of_type_AndroidContentSharedPreferences.edit();
      localEditor.putInt("tbs_install_interrupt_code", paramInt);
      localEditor.commit();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  public void b(boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 30	com/tencent/smtt/downloader/c:jdField_a_of_type_AndroidContentSharedPreferences	Landroid/content/SharedPreferences;
    //   6: invokeinterface 63 1 0
    //   11: astore_2
    //   12: aload_2
    //   13: ldc -81
    //   15: iload_1
    //   16: invokeinterface 106 3 0
    //   21: pop
    //   22: aload_2
    //   23: invokeinterface 137 1 0
    //   28: pop
    //   29: goto +8 -> 37
    //   32: astore_2
    //   33: aload_0
    //   34: monitorexit
    //   35: aload_2
    //   36: athrow
    //   37: aload_0
    //   38: monitorexit
    //   39: return
    //   40: astore_2
    //   41: goto -4 -> 37
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	44	0	this	c
    //   0	44	1	paramBoolean	boolean
    //   11	12	2	localEditor	SharedPreferences.Editor
    //   32	4	2	localObject	Object
    //   40	1	2	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   2	29	32	finally
    //   2	29	40	java/lang/Exception
  }
  
  public long c()
  {
    try
    {
      int j = this.jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_download_min_free_space", 0);
      int i = j;
      if (j == 0) {
        i = 0;
      }
      long l = i * 1024;
      return l * 1024L;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */