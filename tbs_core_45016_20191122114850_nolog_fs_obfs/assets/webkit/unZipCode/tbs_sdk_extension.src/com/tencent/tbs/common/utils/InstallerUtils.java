package com.tencent.tbs.common.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Properties;

public class InstallerUtils
{
  private static HashMap<String, Properties> sPropMap = new HashMap();
  
  public static Properties getConfigFromAssets(Context arg0, String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    synchronized (sPropMap)
    {
      Properties localProperties = (Properties)sPropMap.get(paramString);
      if (localProperties != null) {
        return localProperties;
      }
      ??? = new Properties();
      try
      {
        ??? = ???.getAssets().open(paramString);
        ((Properties)???).load(???);
        ???.close();
        synchronized (sPropMap)
        {
          sPropMap.put(paramString, ???);
          return (Properties)???;
        }
        ??? = finally;
      }
      catch (Exception ???)
      {
        ???.printStackTrace();
        return null;
      }
      catch (UnsupportedEncodingException ???)
      {
        ???.printStackTrace();
        return null;
      }
    }
  }
  
  /* Error */
  public static Properties getConfigFromInstalled(java.io.File paramFile)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: getstatic 16	com/tencent/tbs/common/utils/InstallerUtils:sPropMap	Ljava/util/HashMap;
    //   9: astore_1
    //   10: aload_1
    //   11: monitorenter
    //   12: getstatic 16	com/tencent/tbs/common/utils/InstallerUtils:sPropMap	Ljava/util/HashMap;
    //   15: aload_0
    //   16: invokevirtual 76	java/io/File:getAbsoluteFile	()Ljava/io/File;
    //   19: invokevirtual 34	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   22: checkcast 36	java/util/Properties
    //   25: astore_2
    //   26: aload_2
    //   27: ifnull +7 -> 34
    //   30: aload_1
    //   31: monitorexit
    //   32: aload_2
    //   33: areturn
    //   34: aload_1
    //   35: monitorexit
    //   36: new 36	java/util/Properties
    //   39: dup
    //   40: invokespecial 37	java/util/Properties:<init>	()V
    //   43: astore_3
    //   44: aload_0
    //   45: invokevirtual 80	java/io/File:exists	()Z
    //   48: ifne +5 -> 53
    //   51: aconst_null
    //   52: areturn
    //   53: new 82	java/io/BufferedInputStream
    //   56: dup
    //   57: new 84	java/io/FileInputStream
    //   60: dup
    //   61: aload_0
    //   62: invokespecial 87	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   65: invokespecial 89	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   68: astore_2
    //   69: aload_2
    //   70: astore_1
    //   71: aload_3
    //   72: aload_2
    //   73: invokevirtual 53	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   76: aload_2
    //   77: astore_1
    //   78: getstatic 16	com/tencent/tbs/common/utils/InstallerUtils:sPropMap	Ljava/util/HashMap;
    //   81: astore 4
    //   83: aload_2
    //   84: astore_1
    //   85: aload 4
    //   87: monitorenter
    //   88: getstatic 16	com/tencent/tbs/common/utils/InstallerUtils:sPropMap	Ljava/util/HashMap;
    //   91: aload_0
    //   92: invokevirtual 93	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   95: aload_3
    //   96: invokevirtual 62	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   99: pop
    //   100: aload 4
    //   102: monitorexit
    //   103: aload_2
    //   104: invokevirtual 94	java/io/BufferedInputStream:close	()V
    //   107: aload_3
    //   108: areturn
    //   109: astore_0
    //   110: aload_0
    //   111: invokevirtual 95	java/io/IOException:printStackTrace	()V
    //   114: aload_3
    //   115: areturn
    //   116: astore_0
    //   117: aload 4
    //   119: monitorexit
    //   120: aload_2
    //   121: astore_1
    //   122: aload_0
    //   123: athrow
    //   124: astore_0
    //   125: goto +16 -> 141
    //   128: astore_0
    //   129: goto +31 -> 160
    //   132: astore_0
    //   133: aconst_null
    //   134: astore_1
    //   135: goto +49 -> 184
    //   138: astore_0
    //   139: aconst_null
    //   140: astore_2
    //   141: aload_2
    //   142: astore_1
    //   143: aload_0
    //   144: invokevirtual 95	java/io/IOException:printStackTrace	()V
    //   147: aload_2
    //   148: ifnull +33 -> 181
    //   151: aload_2
    //   152: invokevirtual 94	java/io/BufferedInputStream:close	()V
    //   155: aconst_null
    //   156: areturn
    //   157: astore_0
    //   158: aconst_null
    //   159: astore_2
    //   160: aload_2
    //   161: astore_1
    //   162: aload_0
    //   163: invokevirtual 66	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   166: aload_2
    //   167: ifnull +14 -> 181
    //   170: aload_2
    //   171: invokevirtual 94	java/io/BufferedInputStream:close	()V
    //   174: aconst_null
    //   175: areturn
    //   176: astore_0
    //   177: aload_0
    //   178: invokevirtual 95	java/io/IOException:printStackTrace	()V
    //   181: aconst_null
    //   182: areturn
    //   183: astore_0
    //   184: aload_1
    //   185: ifnull +15 -> 200
    //   188: aload_1
    //   189: invokevirtual 94	java/io/BufferedInputStream:close	()V
    //   192: goto +8 -> 200
    //   195: astore_1
    //   196: aload_1
    //   197: invokevirtual 95	java/io/IOException:printStackTrace	()V
    //   200: aload_0
    //   201: athrow
    //   202: astore_0
    //   203: aload_1
    //   204: monitorexit
    //   205: aload_0
    //   206: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	207	0	paramFile	java.io.File
    //   9	180	1	localObject1	Object
    //   195	9	1	localIOException	java.io.IOException
    //   25	146	2	localObject2	Object
    //   43	72	3	localProperties	Properties
    //   81	37	4	localHashMap	HashMap
    // Exception table:
    //   from	to	target	type
    //   103	107	109	java/io/IOException
    //   88	103	116	finally
    //   117	120	116	finally
    //   71	76	124	java/io/IOException
    //   78	83	124	java/io/IOException
    //   85	88	124	java/io/IOException
    //   122	124	124	java/io/IOException
    //   71	76	128	java/io/UnsupportedEncodingException
    //   78	83	128	java/io/UnsupportedEncodingException
    //   85	88	128	java/io/UnsupportedEncodingException
    //   122	124	128	java/io/UnsupportedEncodingException
    //   44	51	132	finally
    //   53	69	132	finally
    //   44	51	138	java/io/IOException
    //   53	69	138	java/io/IOException
    //   44	51	157	java/io/UnsupportedEncodingException
    //   53	69	157	java/io/UnsupportedEncodingException
    //   151	155	176	java/io/IOException
    //   170	174	176	java/io/IOException
    //   71	76	183	finally
    //   78	83	183	finally
    //   85	88	183	finally
    //   122	124	183	finally
    //   143	147	183	finally
    //   162	166	183	finally
    //   188	192	195	java/io/IOException
    //   12	26	202	finally
    //   30	32	202	finally
    //   34	36	202	finally
    //   203	205	202	finally
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\InstallerUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */