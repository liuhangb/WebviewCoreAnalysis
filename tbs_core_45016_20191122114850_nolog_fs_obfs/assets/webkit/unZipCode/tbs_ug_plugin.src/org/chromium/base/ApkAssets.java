package org.chromium.base;

import org.chromium.base.annotations.JNINamespace;

@JNINamespace("base::android")
public class ApkAssets
{
  /* Error */
  @org.chromium.base.annotations.CalledByNative
  public static long[] open(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_1
    //   4: invokestatic 23	org/chromium/base/ContextUtils:getApplicationContext	()Landroid/content/Context;
    //   7: invokevirtual 29	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   10: aload_0
    //   11: invokevirtual 35	android/content/res/AssetManager:openNonAssetFd	(Ljava/lang/String;)Landroid/content/res/AssetFileDescriptor;
    //   14: astore_3
    //   15: aload_3
    //   16: astore_1
    //   17: aload_3
    //   18: astore_2
    //   19: iconst_3
    //   20: newarray <illegal type>
    //   22: astore 4
    //   24: aload_3
    //   25: astore_1
    //   26: aload_3
    //   27: astore_2
    //   28: aload 4
    //   30: iconst_0
    //   31: aload_3
    //   32: invokevirtual 41	android/content/res/AssetFileDescriptor:getParcelFileDescriptor	()Landroid/os/ParcelFileDescriptor;
    //   35: invokevirtual 47	android/os/ParcelFileDescriptor:detachFd	()I
    //   38: i2l
    //   39: lastore
    //   40: aload_3
    //   41: astore_1
    //   42: aload_3
    //   43: astore_2
    //   44: aload 4
    //   46: iconst_1
    //   47: aload_3
    //   48: invokevirtual 51	android/content/res/AssetFileDescriptor:getStartOffset	()J
    //   51: lastore
    //   52: aload_3
    //   53: astore_1
    //   54: aload_3
    //   55: astore_2
    //   56: aload 4
    //   58: iconst_2
    //   59: aload_3
    //   60: invokevirtual 54	android/content/res/AssetFileDescriptor:getLength	()J
    //   63: lastore
    //   64: aload_3
    //   65: ifnull +20 -> 85
    //   68: aload_3
    //   69: invokevirtual 57	android/content/res/AssetFileDescriptor:close	()V
    //   72: aload 4
    //   74: areturn
    //   75: astore_0
    //   76: ldc 59
    //   78: ldc 61
    //   80: aload_0
    //   81: invokestatic 67	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   84: pop
    //   85: aload 4
    //   87: areturn
    //   88: astore_0
    //   89: goto +139 -> 228
    //   92: astore_3
    //   93: aload_2
    //   94: astore_1
    //   95: aload_3
    //   96: invokevirtual 71	java/io/IOException:getMessage	()Ljava/lang/String;
    //   99: ldc 73
    //   101: invokevirtual 79	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   104: ifne +78 -> 182
    //   107: aload_2
    //   108: astore_1
    //   109: aload_3
    //   110: invokevirtual 71	java/io/IOException:getMessage	()Ljava/lang/String;
    //   113: aload_0
    //   114: invokevirtual 79	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   117: ifne +65 -> 182
    //   120: aload_2
    //   121: astore_1
    //   122: new 81	java/lang/StringBuilder
    //   125: dup
    //   126: invokespecial 82	java/lang/StringBuilder:<init>	()V
    //   129: astore 4
    //   131: aload_2
    //   132: astore_1
    //   133: aload 4
    //   135: ldc 84
    //   137: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   140: pop
    //   141: aload_2
    //   142: astore_1
    //   143: aload 4
    //   145: aload_0
    //   146: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: pop
    //   150: aload_2
    //   151: astore_1
    //   152: aload 4
    //   154: ldc 90
    //   156: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   159: pop
    //   160: aload_2
    //   161: astore_1
    //   162: aload 4
    //   164: aload_3
    //   165: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   168: pop
    //   169: aload_2
    //   170: astore_1
    //   171: ldc 59
    //   173: aload 4
    //   175: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   178: invokestatic 99	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   181: pop
    //   182: aload_2
    //   183: astore_1
    //   184: iconst_3
    //   185: newarray <illegal type>
    //   187: astore_0
    //   188: aload_0
    //   189: iconst_0
    //   190: ldc2_w 100
    //   193: lastore
    //   194: aload_0
    //   195: iconst_1
    //   196: ldc2_w 100
    //   199: lastore
    //   200: aload_0
    //   201: iconst_2
    //   202: ldc2_w 100
    //   205: lastore
    //   206: aload_2
    //   207: ifnull +19 -> 226
    //   210: aload_2
    //   211: invokevirtual 57	android/content/res/AssetFileDescriptor:close	()V
    //   214: aload_0
    //   215: areturn
    //   216: astore_1
    //   217: ldc 59
    //   219: ldc 61
    //   221: aload_1
    //   222: invokestatic 67	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   225: pop
    //   226: aload_0
    //   227: areturn
    //   228: aload_1
    //   229: ifnull +20 -> 249
    //   232: aload_1
    //   233: invokevirtual 57	android/content/res/AssetFileDescriptor:close	()V
    //   236: goto +13 -> 249
    //   239: astore_1
    //   240: ldc 59
    //   242: ldc 61
    //   244: aload_1
    //   245: invokestatic 67	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   248: pop
    //   249: aload_0
    //   250: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	251	0	paramString	String
    //   3	181	1	localObject1	Object
    //   216	17	1	localIOException1	java.io.IOException
    //   239	6	1	localIOException2	java.io.IOException
    //   1	210	2	localObject2	Object
    //   14	55	3	localAssetFileDescriptor	android.content.res.AssetFileDescriptor
    //   92	73	3	localIOException3	java.io.IOException
    //   22	152	4	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   68	72	75	java/io/IOException
    //   4	15	88	finally
    //   19	24	88	finally
    //   28	40	88	finally
    //   44	52	88	finally
    //   56	64	88	finally
    //   95	107	88	finally
    //   109	120	88	finally
    //   122	131	88	finally
    //   133	141	88	finally
    //   143	150	88	finally
    //   152	160	88	finally
    //   162	169	88	finally
    //   171	182	88	finally
    //   184	188	88	finally
    //   4	15	92	java/io/IOException
    //   19	24	92	java/io/IOException
    //   28	40	92	java/io/IOException
    //   44	52	92	java/io/IOException
    //   56	64	92	java/io/IOException
    //   210	214	216	java/io/IOException
    //   232	236	239	java/io/IOException
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\ApkAssets.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */