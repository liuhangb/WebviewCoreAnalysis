package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import android.content.Context;
import com.tencent.common.utils.LinuxToolsJni;
import java.io.File;

class MiniQBShareManager
{
  static final String APP_DEMO = "com.tencent.tbs";
  static final String APP_DEMO_TEST = "com.tencent.mtt.sdk.test";
  static final String APP_QB = "com.tencent.mtt";
  static final String APP_QQ = "com.tencent.mobileqq";
  static final String APP_QQPIMSECURE = "com.tencent.qqpimsecure";
  static final String APP_QZONE = "com.qzone";
  static final String APP_WX = "com.tencent.mm";
  private static final String CORE_VERSION = "core_version";
  private static final String MINIQB_SHARE_FILENAME = "miniqb_info";
  private static final String MINIQB_TBSCORE_VERION = "miniqb_tbscore_version";
  protected static boolean mAllowThirdAppUpgradeMiniqb = true;
  private Context mAppContext;
  private boolean mIsThirdPartyApp;
  private int mMiniqbTbsCoreVersion = 0;
  private int mShareCoreVersion = 0;
  
  protected MiniQBShareManager(Context paramContext)
  {
    this.mAppContext = paramContext;
    try
    {
      paramContext = this.mAppContext.getPackageName();
      this.mIsThirdPartyApp = true;
      String[] arrayOfString = getCoreProviderAppList();
      int j = arrayOfString.length;
      int i = 0;
      while (i < j)
      {
        if (arrayOfString[i].equals(paramContext))
        {
          this.mIsThirdPartyApp = false;
          break;
        }
        i += 1;
      }
      return;
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
      if (this.mIsThirdPartyApp) {
        loadProperties();
      }
    }
  }
  
  public static String[] getCoreProviderAppList()
  {
    return new String[] { "com.tencent.mm", "com.tencent.mtt", "com.tencent.mobileqq", "com.tencent.tbs", "com.tencent.mtt.sdk.test", "com.qzone", "com.tencent.qqpimsecure" };
  }
  
  private File getMiniQBShareFile()
  {
    File localFile = MiniQBUpgradeManager.getInstance().getTbsShareDir(this.mAppContext);
    if (localFile == null) {
      return null;
    }
    localFile = new File(localFile, "miniqb_info");
    if (localFile.exists()) {
      return localFile;
    }
    try
    {
      localFile.createNewFile();
      new LinuxToolsJni().Chmod(localFile.getAbsolutePath(), "644");
      return localFile;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    return null;
  }
  
  /* Error */
  private void loadProperties()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 125	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBShareManager:getMiniQBShareFile	()Ljava/io/File;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnonnull +4 -> 10
    //   9: return
    //   10: new 127	java/io/FileInputStream
    //   13: dup
    //   14: aload_1
    //   15: invokespecial 130	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   18: astore_2
    //   19: new 132	java/io/BufferedInputStream
    //   22: dup
    //   23: aload_2
    //   24: invokespecial 135	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   27: astore_1
    //   28: aload_1
    //   29: astore_3
    //   30: aload_2
    //   31: astore 4
    //   33: new 137	java/util/Properties
    //   36: dup
    //   37: invokespecial 138	java/util/Properties:<init>	()V
    //   40: astore 5
    //   42: aload_1
    //   43: astore_3
    //   44: aload_2
    //   45: astore 4
    //   47: aload 5
    //   49: aload_1
    //   50: invokevirtual 141	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   53: aload_1
    //   54: astore_3
    //   55: aload_2
    //   56: astore 4
    //   58: aload 5
    //   60: ldc 29
    //   62: ldc -113
    //   64: invokevirtual 147	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   67: astore 6
    //   69: aload_1
    //   70: astore_3
    //   71: aload_2
    //   72: astore 4
    //   74: ldc -113
    //   76: aload 6
    //   78: invokevirtual 77	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   81: ifne +21 -> 102
    //   84: aload_1
    //   85: astore_3
    //   86: aload_2
    //   87: astore 4
    //   89: aload_0
    //   90: aload 6
    //   92: invokestatic 153	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   95: iconst_0
    //   96: invokestatic 159	java/lang/Math:max	(II)I
    //   99: putfield 55	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBShareManager:mShareCoreVersion	I
    //   102: aload_1
    //   103: astore_3
    //   104: aload_2
    //   105: astore 4
    //   107: aload 5
    //   109: ldc 35
    //   111: ldc -113
    //   113: invokevirtual 147	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   116: astore 5
    //   118: aload_1
    //   119: astore_3
    //   120: aload_2
    //   121: astore 4
    //   123: ldc -113
    //   125: aload 5
    //   127: invokevirtual 77	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   130: ifne +21 -> 151
    //   133: aload_1
    //   134: astore_3
    //   135: aload_2
    //   136: astore 4
    //   138: aload_0
    //   139: aload 5
    //   141: invokestatic 153	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   144: iconst_0
    //   145: invokestatic 159	java/lang/Math:max	(II)I
    //   148: putfield 57	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBShareManager:mMiniqbTbsCoreVersion	I
    //   151: aload_1
    //   152: invokevirtual 162	java/io/BufferedInputStream:close	()V
    //   155: aload_2
    //   156: invokevirtual 163	java/io/FileInputStream:close	()V
    //   159: return
    //   160: astore 5
    //   162: goto +30 -> 192
    //   165: astore_1
    //   166: aconst_null
    //   167: astore_3
    //   168: goto +61 -> 229
    //   171: astore 5
    //   173: aconst_null
    //   174: astore_1
    //   175: goto +17 -> 192
    //   178: astore_1
    //   179: aconst_null
    //   180: astore_2
    //   181: aload_2
    //   182: astore_3
    //   183: goto +46 -> 229
    //   186: astore 5
    //   188: aconst_null
    //   189: astore_2
    //   190: aload_2
    //   191: astore_1
    //   192: aload_1
    //   193: astore_3
    //   194: aload_2
    //   195: astore 4
    //   197: aload 5
    //   199: invokevirtual 80	java/lang/Throwable:printStackTrace	()V
    //   202: aload_1
    //   203: ifnull +7 -> 210
    //   206: aload_1
    //   207: invokevirtual 162	java/io/BufferedInputStream:close	()V
    //   210: aload_2
    //   211: ifnull +13 -> 224
    //   214: aload_2
    //   215: invokevirtual 163	java/io/FileInputStream:close	()V
    //   218: return
    //   219: astore_1
    //   220: aload_1
    //   221: invokevirtual 164	java/io/IOException:printStackTrace	()V
    //   224: return
    //   225: astore_1
    //   226: aload 4
    //   228: astore_2
    //   229: aload_3
    //   230: ifnull +7 -> 237
    //   233: aload_3
    //   234: invokevirtual 162	java/io/BufferedInputStream:close	()V
    //   237: aload_2
    //   238: ifnull +15 -> 253
    //   241: aload_2
    //   242: invokevirtual 163	java/io/FileInputStream:close	()V
    //   245: goto +8 -> 253
    //   248: astore_2
    //   249: aload_2
    //   250: invokevirtual 164	java/io/IOException:printStackTrace	()V
    //   253: aload_1
    //   254: athrow
    //   255: astore_1
    //   256: goto -101 -> 155
    //   259: astore_1
    //   260: goto -50 -> 210
    //   263: astore_3
    //   264: goto -27 -> 237
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	267	0	this	MiniQBShareManager
    //   4	148	1	localObject1	Object
    //   165	1	1	localObject2	Object
    //   174	1	1	localObject3	Object
    //   178	1	1	localObject4	Object
    //   191	16	1	localObject5	Object
    //   219	2	1	localIOException1	java.io.IOException
    //   225	29	1	localObject6	Object
    //   255	1	1	localException1	Exception
    //   259	1	1	localException2	Exception
    //   18	224	2	localObject7	Object
    //   248	2	2	localIOException2	java.io.IOException
    //   29	205	3	localObject8	Object
    //   263	1	3	localException3	Exception
    //   31	196	4	localObject9	Object
    //   40	100	5	localObject10	Object
    //   160	1	5	localThrowable1	Throwable
    //   171	1	5	localThrowable2	Throwable
    //   186	12	5	localThrowable3	Throwable
    //   67	24	6	str	String
    // Exception table:
    //   from	to	target	type
    //   33	42	160	java/lang/Throwable
    //   47	53	160	java/lang/Throwable
    //   58	69	160	java/lang/Throwable
    //   74	84	160	java/lang/Throwable
    //   89	102	160	java/lang/Throwable
    //   107	118	160	java/lang/Throwable
    //   123	133	160	java/lang/Throwable
    //   138	151	160	java/lang/Throwable
    //   19	28	165	finally
    //   19	28	171	java/lang/Throwable
    //   0	5	178	finally
    //   10	19	178	finally
    //   0	5	186	java/lang/Throwable
    //   10	19	186	java/lang/Throwable
    //   155	159	219	java/io/IOException
    //   214	218	219	java/io/IOException
    //   33	42	225	finally
    //   47	53	225	finally
    //   58	69	225	finally
    //   74	84	225	finally
    //   89	102	225	finally
    //   107	118	225	finally
    //   123	133	225	finally
    //   138	151	225	finally
    //   197	202	225	finally
    //   241	245	248	java/io/IOException
    //   151	155	255	java/lang/Exception
    //   206	210	259	java/lang/Exception
    //   233	237	263	java/lang/Exception
  }
  
  /* Error */
  private void writeProperties(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 6
    //   3: aconst_null
    //   4: astore 5
    //   6: aload_0
    //   7: invokespecial 125	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBShareManager:getMiniQBShareFile	()Ljava/io/File;
    //   10: astore 8
    //   12: aload 8
    //   14: ifnonnull +4 -> 18
    //   17: return
    //   18: new 127	java/io/FileInputStream
    //   21: dup
    //   22: aload 8
    //   24: invokespecial 130	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   27: astore 4
    //   29: new 132	java/io/BufferedInputStream
    //   32: dup
    //   33: aload 4
    //   35: invokespecial 135	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   38: astore_3
    //   39: new 137	java/util/Properties
    //   42: dup
    //   43: invokespecial 138	java/util/Properties:<init>	()V
    //   46: astore 7
    //   48: aload 7
    //   50: aload_3
    //   51: invokevirtual 141	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   54: aload 7
    //   56: ldc 29
    //   58: aload_1
    //   59: invokevirtual 170	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   62: pop
    //   63: getstatic 172	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBShareManager:mAllowThirdAppUpgradeMiniqb	Z
    //   66: ifeq +12 -> 78
    //   69: aload 7
    //   71: ldc 35
    //   73: aload_2
    //   74: invokevirtual 170	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   77: pop
    //   78: new 174	java/io/FileOutputStream
    //   81: dup
    //   82: aload 8
    //   84: invokespecial 175	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   87: astore_1
    //   88: new 177	java/io/BufferedOutputStream
    //   91: dup
    //   92: aload_1
    //   93: invokespecial 180	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   96: astore_2
    //   97: aload 7
    //   99: aload_2
    //   100: aconst_null
    //   101: invokevirtual 184	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   104: aload_2
    //   105: invokevirtual 185	java/io/BufferedOutputStream:close	()V
    //   108: aload_1
    //   109: invokevirtual 186	java/io/FileOutputStream:close	()V
    //   112: goto +8 -> 120
    //   115: astore_1
    //   116: aload_1
    //   117: invokevirtual 164	java/io/IOException:printStackTrace	()V
    //   120: aload_3
    //   121: invokevirtual 162	java/io/BufferedInputStream:close	()V
    //   124: aload 4
    //   126: invokevirtual 163	java/io/FileInputStream:close	()V
    //   129: return
    //   130: astore 5
    //   132: aload_1
    //   133: astore 6
    //   135: aload 5
    //   137: astore_1
    //   138: aload_2
    //   139: astore 5
    //   141: aload 6
    //   143: astore_2
    //   144: goto +155 -> 299
    //   147: astore 6
    //   149: aload_2
    //   150: astore 5
    //   152: aload 6
    //   154: astore_2
    //   155: goto +82 -> 237
    //   158: astore 5
    //   160: aload_1
    //   161: astore_2
    //   162: aload 5
    //   164: astore_1
    //   165: aload 6
    //   167: astore 5
    //   169: goto +130 -> 299
    //   172: astore_2
    //   173: goto +64 -> 237
    //   176: astore_1
    //   177: aconst_null
    //   178: astore_2
    //   179: aload 6
    //   181: astore 5
    //   183: goto +116 -> 299
    //   186: astore_2
    //   187: aconst_null
    //   188: astore_1
    //   189: goto +48 -> 237
    //   192: astore_1
    //   193: aconst_null
    //   194: astore_2
    //   195: aload_2
    //   196: astore_3
    //   197: aload 6
    //   199: astore 5
    //   201: goto +98 -> 299
    //   204: astore_2
    //   205: aconst_null
    //   206: astore_1
    //   207: aload_1
    //   208: astore_3
    //   209: goto +28 -> 237
    //   212: astore_1
    //   213: aconst_null
    //   214: astore_2
    //   215: aload_2
    //   216: astore 4
    //   218: aload 4
    //   220: astore_3
    //   221: aload 6
    //   223: astore 5
    //   225: goto +74 -> 299
    //   228: astore_2
    //   229: aconst_null
    //   230: astore_1
    //   231: aload_1
    //   232: astore 4
    //   234: aload 4
    //   236: astore_3
    //   237: aload_2
    //   238: invokevirtual 80	java/lang/Throwable:printStackTrace	()V
    //   241: aload 5
    //   243: ifnull +8 -> 251
    //   246: aload 5
    //   248: invokevirtual 185	java/io/BufferedOutputStream:close	()V
    //   251: aload_1
    //   252: ifnull +15 -> 267
    //   255: aload_1
    //   256: invokevirtual 186	java/io/FileOutputStream:close	()V
    //   259: goto +8 -> 267
    //   262: astore_1
    //   263: aload_1
    //   264: invokevirtual 164	java/io/IOException:printStackTrace	()V
    //   267: aload_3
    //   268: ifnull +7 -> 275
    //   271: aload_3
    //   272: invokevirtual 162	java/io/BufferedInputStream:close	()V
    //   275: aload 4
    //   277: ifnull +14 -> 291
    //   280: aload 4
    //   282: invokevirtual 163	java/io/FileInputStream:close	()V
    //   285: return
    //   286: astore_1
    //   287: aload_1
    //   288: invokevirtual 164	java/io/IOException:printStackTrace	()V
    //   291: return
    //   292: astore 6
    //   294: aload_1
    //   295: astore_2
    //   296: aload 6
    //   298: astore_1
    //   299: aload 5
    //   301: ifnull +8 -> 309
    //   304: aload 5
    //   306: invokevirtual 185	java/io/BufferedOutputStream:close	()V
    //   309: aload_2
    //   310: ifnull +15 -> 325
    //   313: aload_2
    //   314: invokevirtual 186	java/io/FileOutputStream:close	()V
    //   317: goto +8 -> 325
    //   320: astore_2
    //   321: aload_2
    //   322: invokevirtual 164	java/io/IOException:printStackTrace	()V
    //   325: aload_3
    //   326: ifnull +7 -> 333
    //   329: aload_3
    //   330: invokevirtual 162	java/io/BufferedInputStream:close	()V
    //   333: aload 4
    //   335: ifnull +16 -> 351
    //   338: aload 4
    //   340: invokevirtual 163	java/io/FileInputStream:close	()V
    //   343: goto +8 -> 351
    //   346: astore_2
    //   347: aload_2
    //   348: invokevirtual 164	java/io/IOException:printStackTrace	()V
    //   351: aload_1
    //   352: athrow
    //   353: astore_2
    //   354: goto -246 -> 108
    //   357: astore_1
    //   358: goto -234 -> 124
    //   361: astore_2
    //   362: goto -111 -> 251
    //   365: astore_1
    //   366: goto -91 -> 275
    //   369: astore 5
    //   371: goto -62 -> 309
    //   374: astore_2
    //   375: goto -42 -> 333
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	378	0	this	MiniQBShareManager
    //   0	378	1	paramString1	String
    //   0	378	2	paramString2	String
    //   38	292	3	localObject1	Object
    //   27	312	4	localObject2	Object
    //   4	1	5	localObject3	Object
    //   130	6	5	localObject4	Object
    //   139	12	5	str1	String
    //   158	5	5	localObject5	Object
    //   167	138	5	localThrowable1	Throwable
    //   369	1	5	localException	Exception
    //   1	141	6	str2	String
    //   147	75	6	localThrowable2	Throwable
    //   292	5	6	localObject6	Object
    //   46	52	7	localProperties	java.util.Properties
    //   10	73	8	localFile	File
    // Exception table:
    //   from	to	target	type
    //   108	112	115	java/io/IOException
    //   97	104	130	finally
    //   97	104	147	java/lang/Throwable
    //   88	97	158	finally
    //   88	97	172	java/lang/Throwable
    //   39	78	176	finally
    //   78	88	176	finally
    //   39	78	186	java/lang/Throwable
    //   78	88	186	java/lang/Throwable
    //   29	39	192	finally
    //   29	39	204	java/lang/Throwable
    //   6	12	212	finally
    //   18	29	212	finally
    //   6	12	228	java/lang/Throwable
    //   18	29	228	java/lang/Throwable
    //   255	259	262	java/io/IOException
    //   124	129	286	java/io/IOException
    //   280	285	286	java/io/IOException
    //   237	241	292	finally
    //   313	317	320	java/io/IOException
    //   338	343	346	java/io/IOException
    //   104	108	353	java/lang/Exception
    //   120	124	357	java/lang/Exception
    //   246	251	361	java/lang/Exception
    //   271	275	365	java/lang/Exception
    //   304	309	369	java/lang/Exception
    //   329	333	374	java/lang/Exception
  }
  
  public int getShareVersion()
  {
    return this.mShareCoreVersion;
  }
  
  public int getmMiniqbTbsCoreVersion()
  {
    return this.mMiniqbTbsCoreVersion;
  }
  
  public boolean isThirdPartyApp()
  {
    return this.mIsThirdPartyApp;
  }
  
  public void updateShareCoreVersion(int paramInt1, int paramInt2)
  {
    int i = this.mShareCoreVersion;
    if ((i != 0) && (paramInt1 <= i)) {
      return;
    }
    writeProperties(String.valueOf(paramInt1), String.valueOf(paramInt2));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\MiniQBShareManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */