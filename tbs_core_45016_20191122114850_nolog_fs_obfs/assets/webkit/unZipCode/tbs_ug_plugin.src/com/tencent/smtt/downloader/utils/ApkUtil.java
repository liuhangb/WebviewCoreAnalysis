package com.tencent.smtt.downloader.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Build.VERSION;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApkUtil
{
  public static int a(Context paramContext, File paramFile)
  {
    try
    {
      if (Build.VERSION.SDK_INT < 20) {
        break label35;
      }
      bool = true;
    }
    catch (Exception paramContext)
    {
      for (;;)
      {
        int i;
        continue;
        label35:
        boolean bool = false;
      }
    }
    i = a(paramContext, paramFile, bool);
    return i;
    h.a("ApkUtil", "getApkVersion Failed");
    return 0;
  }
  
  public static int a(Context paramContext, File paramFile, boolean paramBoolean)
  {
    if (paramFile != null) {}
    for (;;)
    {
      try
      {
        if (!paramFile.exists()) {
          break label128;
        }
        boolean bool1 = paramFile.getName().contains("tbs.org");
        boolean bool2 = paramFile.getName().contains("x5.tbs.decouple");
        if ((bool1) || (bool2))
        {
          i = a(bool2, paramFile);
          if (i > 0) {
            return i;
          }
          if (!paramFile.getAbsolutePath().contains(paramContext.getApplicationInfo().packageName)) {
            return 0;
          }
        }
        if (((Build.VERSION.SDK_INT != 23) && (Build.VERSION.SDK_INT != 25)) || (!Build.MANUFACTURER.toLowerCase().contains("mi"))) {
          break label172;
        }
        i = 1;
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
      int i = a(paramFile);
      if (i > 0) {
        return i;
      }
      label128:
      label172:
      do
      {
        if ((paramFile != null) && (paramFile.exists())) {
          try
          {
            paramContext = paramContext.getPackageManager().getPackageArchiveInfo(paramFile.getAbsolutePath(), 1);
            if (paramContext != null)
            {
              i = paramContext.versionCode;
              return i;
            }
          }
          catch (Throwable paramContext)
          {
            paramContext.printStackTrace();
            return -1;
          }
        }
        return 0;
        i = 0;
        if (paramBoolean) {
          break;
        }
      } while (i == 0);
    }
  }
  
  /* Error */
  public static int a(File paramFile)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: aconst_null
    //   4: astore_3
    //   5: aconst_null
    //   6: astore_2
    //   7: aconst_null
    //   8: astore 6
    //   10: new 104	java/util/jar/JarFile
    //   13: dup
    //   14: aload_0
    //   15: invokespecial 107	java/util/jar/JarFile:<init>	(Ljava/io/File;)V
    //   18: astore_0
    //   19: aload_3
    //   20: astore_2
    //   21: aload_0
    //   22: astore_3
    //   23: new 109	java/io/BufferedReader
    //   26: dup
    //   27: new 111	java/io/InputStreamReader
    //   30: dup
    //   31: aload_0
    //   32: aload_0
    //   33: ldc 113
    //   35: invokevirtual 117	java/util/jar/JarFile:getJarEntry	(Ljava/lang/String;)Ljava/util/jar/JarEntry;
    //   38: invokevirtual 121	java/util/jar/JarFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   41: invokespecial 124	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   44: invokespecial 127	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   47: astore 4
    //   49: aload 4
    //   51: invokevirtual 130	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   54: astore_2
    //   55: aload_2
    //   56: ifnull +53 -> 109
    //   59: aload_2
    //   60: ldc -124
    //   62: invokevirtual 51	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   65: ifeq -16 -> 49
    //   68: aload_2
    //   69: ldc -122
    //   71: invokevirtual 138	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   74: astore_2
    //   75: aload_2
    //   76: ifnull -27 -> 49
    //   79: aload_2
    //   80: arraylength
    //   81: iconst_2
    //   82: if_icmpne -33 -> 49
    //   85: aload_2
    //   86: iconst_1
    //   87: aaload
    //   88: invokevirtual 141	java/lang/String:trim	()Ljava/lang/String;
    //   91: invokestatic 147	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   94: istore_1
    //   95: aload 4
    //   97: invokevirtual 150	java/io/BufferedReader:close	()V
    //   100: aload_0
    //   101: invokevirtual 151	java/util/jar/JarFile:close	()V
    //   104: ldc 2
    //   106: monitorexit
    //   107: iload_1
    //   108: ireturn
    //   109: aload 4
    //   111: invokevirtual 150	java/io/BufferedReader:close	()V
    //   114: aload_0
    //   115: invokevirtual 151	java/util/jar/JarFile:close	()V
    //   118: goto +76 -> 194
    //   121: astore_3
    //   122: aload 4
    //   124: astore_2
    //   125: aload_3
    //   126: astore 4
    //   128: goto +75 -> 203
    //   131: astore 5
    //   133: goto +27 -> 160
    //   136: astore 5
    //   138: aload 6
    //   140: astore 4
    //   142: goto +18 -> 160
    //   145: astore 4
    //   147: aconst_null
    //   148: astore_0
    //   149: goto +54 -> 203
    //   152: astore 5
    //   154: aconst_null
    //   155: astore_0
    //   156: aload 6
    //   158: astore 4
    //   160: aload 4
    //   162: astore_2
    //   163: aload_0
    //   164: astore_3
    //   165: aload 5
    //   167: invokevirtual 152	java/lang/Exception:printStackTrace	()V
    //   170: aload 4
    //   172: ifnull +15 -> 187
    //   175: aload 4
    //   177: invokevirtual 150	java/io/BufferedReader:close	()V
    //   180: goto +7 -> 187
    //   183: astore_0
    //   184: goto +38 -> 222
    //   187: aload_0
    //   188: ifnull +6 -> 194
    //   191: goto -77 -> 114
    //   194: ldc 2
    //   196: monitorexit
    //   197: iconst_m1
    //   198: ireturn
    //   199: astore 4
    //   201: aload_3
    //   202: astore_0
    //   203: aload_2
    //   204: ifnull +7 -> 211
    //   207: aload_2
    //   208: invokevirtual 150	java/io/BufferedReader:close	()V
    //   211: aload_0
    //   212: ifnull +7 -> 219
    //   215: aload_0
    //   216: invokevirtual 151	java/util/jar/JarFile:close	()V
    //   219: aload 4
    //   221: athrow
    //   222: ldc 2
    //   224: monitorexit
    //   225: aload_0
    //   226: athrow
    //   227: astore_0
    //   228: goto -124 -> 104
    //   231: astore_0
    //   232: goto -38 -> 194
    //   235: astore_0
    //   236: goto -17 -> 219
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	239	0	paramFile	File
    //   94	14	1	i	int
    //   6	202	2	localObject1	Object
    //   4	19	3	localFile1	File
    //   121	5	3	localObject2	Object
    //   164	38	3	localFile2	File
    //   47	94	4	localObject3	Object
    //   145	1	4	localObject4	Object
    //   158	18	4	localObject5	Object
    //   199	21	4	localObject6	Object
    //   131	1	5	localException1	Exception
    //   136	1	5	localException2	Exception
    //   152	14	5	localException3	Exception
    //   8	149	6	localObject7	Object
    // Exception table:
    //   from	to	target	type
    //   49	55	121	finally
    //   59	75	121	finally
    //   79	95	121	finally
    //   49	55	131	java/lang/Exception
    //   59	75	131	java/lang/Exception
    //   79	95	131	java/lang/Exception
    //   23	49	136	java/lang/Exception
    //   10	19	145	finally
    //   10	19	152	java/lang/Exception
    //   95	104	183	finally
    //   104	107	183	finally
    //   109	114	183	finally
    //   114	118	183	finally
    //   175	180	183	finally
    //   194	197	183	finally
    //   207	211	183	finally
    //   215	219	183	finally
    //   219	222	183	finally
    //   222	225	183	finally
    //   23	49	199	finally
    //   165	170	199	finally
    //   95	104	227	java/lang/Exception
    //   109	114	231	java/lang/Exception
    //   114	118	231	java/lang/Exception
    //   175	180	231	java/lang/Exception
    //   207	211	235	java/lang/Exception
    //   215	219	235	java/lang/Exception
  }
  
  private static int a(boolean paramBoolean, File paramFile)
  {
    try
    {
      paramFile = paramFile.getParentFile();
      if (paramFile != null)
      {
        paramFile = paramFile.listFiles();
        Object localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(getCoreVersionCfgFile(paramBoolean));
        ((StringBuilder)localObject1).append("(.*)");
        localObject1 = Pattern.compile(((StringBuilder)localObject1).toString());
        int j = paramFile.length;
        int i = 0;
        while (i < j)
        {
          Object localObject2 = paramFile[i];
          if ((((Pattern)localObject1).matcher(((File)localObject2).getName()).find()) && (((File)localObject2).isFile()) && (((File)localObject2).exists()))
          {
            i = Integer.parseInt(((File)localObject2).getName().substring(((File)localObject2).getName().lastIndexOf(".") + 1));
            return i;
          }
          i += 1;
        }
      }
    }
    catch (Exception paramFile)
    {
      for (;;) {}
    }
    return -1;
  }
  
  /* Error */
  public static String a(File paramFile)
  {
    // Byte code:
    //   0: bipush 16
    //   2: newarray <illegal type>
    //   4: astore 6
    //   6: aload 6
    //   8: dup
    //   9: iconst_0
    //   10: ldc -49
    //   12: castore
    //   13: dup
    //   14: iconst_1
    //   15: ldc -48
    //   17: castore
    //   18: dup
    //   19: iconst_2
    //   20: ldc -47
    //   22: castore
    //   23: dup
    //   24: iconst_3
    //   25: ldc -46
    //   27: castore
    //   28: dup
    //   29: iconst_4
    //   30: ldc -45
    //   32: castore
    //   33: dup
    //   34: iconst_5
    //   35: ldc -44
    //   37: castore
    //   38: dup
    //   39: bipush 6
    //   41: ldc -43
    //   43: castore
    //   44: dup
    //   45: bipush 7
    //   47: ldc -42
    //   49: castore
    //   50: dup
    //   51: bipush 8
    //   53: ldc -41
    //   55: castore
    //   56: dup
    //   57: bipush 9
    //   59: ldc -40
    //   61: castore
    //   62: dup
    //   63: bipush 10
    //   65: ldc -39
    //   67: castore
    //   68: dup
    //   69: bipush 11
    //   71: ldc -38
    //   73: castore
    //   74: dup
    //   75: bipush 12
    //   77: ldc -37
    //   79: castore
    //   80: dup
    //   81: bipush 13
    //   83: ldc -36
    //   85: castore
    //   86: dup
    //   87: bipush 14
    //   89: ldc -35
    //   91: castore
    //   92: dup
    //   93: bipush 15
    //   95: ldc -34
    //   97: castore
    //   98: pop
    //   99: bipush 32
    //   101: newarray <illegal type>
    //   103: astore 7
    //   105: ldc -32
    //   107: invokestatic 230	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   110: astore 8
    //   112: new 232	java/io/FileInputStream
    //   115: dup
    //   116: aload_0
    //   117: invokespecial 233	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   120: astore 5
    //   122: aload 5
    //   124: astore_0
    //   125: sipush 8192
    //   128: newarray <illegal type>
    //   130: astore 9
    //   132: aload 5
    //   134: astore_0
    //   135: aload 5
    //   137: aload 9
    //   139: invokevirtual 237	java/io/FileInputStream:read	([B)I
    //   142: istore_1
    //   143: iconst_0
    //   144: istore_2
    //   145: iload_1
    //   146: iconst_m1
    //   147: if_icmpeq +18 -> 165
    //   150: aload 5
    //   152: astore_0
    //   153: aload 8
    //   155: aload 9
    //   157: iconst_0
    //   158: iload_1
    //   159: invokevirtual 241	java/security/MessageDigest:update	([BII)V
    //   162: goto -30 -> 132
    //   165: aload 5
    //   167: astore_0
    //   168: aload 8
    //   170: invokevirtual 245	java/security/MessageDigest:digest	()[B
    //   173: astore 8
    //   175: iconst_0
    //   176: istore_1
    //   177: goto +98 -> 275
    //   180: aload 5
    //   182: astore_0
    //   183: new 47	java/lang/String
    //   186: dup
    //   187: aload 7
    //   189: invokespecial 248	java/lang/String:<init>	([C)V
    //   192: astore 6
    //   194: aload 5
    //   196: invokevirtual 249	java/io/FileInputStream:close	()V
    //   199: aload 6
    //   201: areturn
    //   202: astore_0
    //   203: aload_0
    //   204: invokevirtual 250	java/io/IOException:printStackTrace	()V
    //   207: aload 6
    //   209: areturn
    //   210: astore 6
    //   212: goto +15 -> 227
    //   215: astore 5
    //   217: aconst_null
    //   218: astore_0
    //   219: goto +37 -> 256
    //   222: astore 6
    //   224: aconst_null
    //   225: astore 5
    //   227: aload 5
    //   229: astore_0
    //   230: aload 6
    //   232: invokevirtual 152	java/lang/Exception:printStackTrace	()V
    //   235: aload 5
    //   237: ifnull +15 -> 252
    //   240: aload 5
    //   242: invokevirtual 249	java/io/FileInputStream:close	()V
    //   245: aconst_null
    //   246: areturn
    //   247: astore_0
    //   248: aload_0
    //   249: invokevirtual 250	java/io/IOException:printStackTrace	()V
    //   252: aconst_null
    //   253: areturn
    //   254: astore 5
    //   256: aload_0
    //   257: ifnull +15 -> 272
    //   260: aload_0
    //   261: invokevirtual 249	java/io/FileInputStream:close	()V
    //   264: goto +8 -> 272
    //   267: astore_0
    //   268: aload_0
    //   269: invokevirtual 250	java/io/IOException:printStackTrace	()V
    //   272: aload 5
    //   274: athrow
    //   275: iload_2
    //   276: bipush 16
    //   278: if_icmpge -98 -> 180
    //   281: aload 8
    //   283: iload_2
    //   284: baload
    //   285: istore_3
    //   286: iload_1
    //   287: iconst_1
    //   288: iadd
    //   289: istore 4
    //   291: aload 7
    //   293: iload_1
    //   294: aload 6
    //   296: iload_3
    //   297: iconst_4
    //   298: iushr
    //   299: bipush 15
    //   301: iand
    //   302: caload
    //   303: castore
    //   304: iload 4
    //   306: iconst_1
    //   307: iadd
    //   308: istore_1
    //   309: aload 7
    //   311: iload 4
    //   313: aload 6
    //   315: iload_3
    //   316: bipush 15
    //   318: iand
    //   319: caload
    //   320: castore
    //   321: iload_2
    //   322: iconst_1
    //   323: iadd
    //   324: istore_2
    //   325: goto -50 -> 275
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	328	0	paramFile	File
    //   142	167	1	i	int
    //   144	181	2	j	int
    //   285	34	3	k	int
    //   289	23	4	m	int
    //   120	75	5	localFileInputStream	java.io.FileInputStream
    //   215	1	5	localObject1	Object
    //   225	16	5	localObject2	Object
    //   254	19	5	localObject3	Object
    //   4	204	6	localObject4	Object
    //   210	1	6	localException1	Exception
    //   222	92	6	localException2	Exception
    //   103	207	7	arrayOfChar	char[]
    //   110	172	8	localObject5	Object
    //   130	26	9	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   194	199	202	java/io/IOException
    //   125	132	210	java/lang/Exception
    //   135	143	210	java/lang/Exception
    //   153	162	210	java/lang/Exception
    //   168	175	210	java/lang/Exception
    //   183	194	210	java/lang/Exception
    //   105	122	215	finally
    //   105	122	222	java/lang/Exception
    //   240	245	247	java/io/IOException
    //   125	132	254	finally
    //   135	143	254	finally
    //   153	162	254	finally
    //   168	175	254	finally
    //   183	194	254	finally
    //   230	235	254	finally
    //   260	264	267	java/io/IOException
  }
  
  public static final String getCoreVersionCfgFile(boolean paramBoolean)
  {
    if (b.a())
    {
      if (paramBoolean) {
        return "x5.64.decouple.backup";
      }
      return "x5.64.backup";
    }
    if (paramBoolean) {
      return "x5.decouple.backup";
    }
    return "x5.backup";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\utils\ApkUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */