package org.chromium.tencent.utils;

import android.os.Build;
import android.os.Build.VERSION;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public class TencentDeviceUtils
{
  public static final int DEVICEINFO_UNKNOWN = -1;
  private static int sTotalRAMMemory = -1;
  
  public static String OsName()
  {
    Object localObject = Build.VERSION.RELEASE;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Android ");
    try
    {
      String str = new String(((String)localObject).getBytes("UTF-8"), "ISO8859-1");
      localObject = str;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    if (((String)localObject).length() > 0) {
      localStringBuilder.append((String)localObject);
    } else {
      localStringBuilder.append("1.0");
    }
    return localStringBuilder.toString();
  }
  
  private static int extractValue(byte[] paramArrayOfByte, int paramInt)
  {
    while ((paramInt < paramArrayOfByte.length) && (paramArrayOfByte[paramInt] != 10))
    {
      if ((paramArrayOfByte[paramInt] >= 48) && (paramArrayOfByte[paramInt] <= 57))
      {
        int i = paramInt + 1;
        while ((i < paramArrayOfByte.length) && (paramArrayOfByte[i] >= 48) && (paramArrayOfByte[i] <= 57)) {
          i += 1;
        }
        return Integer.parseInt(new String(paramArrayOfByte, 0, paramInt, i - paramInt));
      }
      paramInt += 1;
    }
    return -1;
  }
  
  /* Error */
  public static int getCPUMaxFreqKHz()
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: iconst_m1
    //   3: istore_0
    //   4: iload_2
    //   5: invokestatic 77	org/chromium/tencent/utils/TencentDeviceUtils:getNumberOfCPUCores	()I
    //   8: if_icmpge +161 -> 169
    //   11: new 29	java/lang/StringBuilder
    //   14: dup
    //   15: invokespecial 30	java/lang/StringBuilder:<init>	()V
    //   18: astore_3
    //   19: aload_3
    //   20: ldc 79
    //   22: invokevirtual 36	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   25: pop
    //   26: aload_3
    //   27: iload_2
    //   28: invokevirtual 82	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   31: pop
    //   32: aload_3
    //   33: ldc 84
    //   35: invokevirtual 36	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: pop
    //   39: new 86	java/io/File
    //   42: dup
    //   43: aload_3
    //   44: invokevirtual 58	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   47: invokespecial 89	java/io/File:<init>	(Ljava/lang/String;)V
    //   50: astore_3
    //   51: iload_0
    //   52: istore_1
    //   53: aload_3
    //   54: invokevirtual 93	java/io/File:exists	()Z
    //   57: ifeq +177 -> 234
    //   60: sipush 128
    //   63: newarray <illegal type>
    //   65: astore 4
    //   67: new 95	java/io/FileInputStream
    //   70: dup
    //   71: aload_3
    //   72: invokespecial 98	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   75: astore_3
    //   76: aload_3
    //   77: aload 4
    //   79: invokevirtual 102	java/io/FileInputStream:read	([B)I
    //   82: pop
    //   83: iconst_0
    //   84: istore_1
    //   85: aload 4
    //   87: iload_1
    //   88: baload
    //   89: bipush 48
    //   91: if_icmplt +26 -> 117
    //   94: aload 4
    //   96: iload_1
    //   97: baload
    //   98: bipush 57
    //   100: if_icmpgt +17 -> 117
    //   103: iload_1
    //   104: aload 4
    //   106: arraylength
    //   107: if_icmpge +10 -> 117
    //   110: iload_1
    //   111: iconst_1
    //   112: iadd
    //   113: istore_1
    //   114: goto -29 -> 85
    //   117: new 38	java/lang/String
    //   120: dup
    //   121: aload 4
    //   123: iconst_0
    //   124: iload_1
    //   125: invokespecial 105	java/lang/String:<init>	([BII)V
    //   128: invokestatic 69	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   131: invokestatic 109	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   134: astore 4
    //   136: iload_0
    //   137: istore_1
    //   138: aload 4
    //   140: invokevirtual 112	java/lang/Integer:intValue	()I
    //   143: iload_0
    //   144: if_icmple +9 -> 153
    //   147: aload 4
    //   149: invokevirtual 112	java/lang/Integer:intValue	()I
    //   152: istore_1
    //   153: aload_3
    //   154: invokevirtual 115	java/io/FileInputStream:close	()V
    //   157: goto +77 -> 234
    //   160: astore 4
    //   162: aload_3
    //   163: invokevirtual 115	java/io/FileInputStream:close	()V
    //   166: aload 4
    //   168: athrow
    //   169: iload_0
    //   170: iconst_m1
    //   171: if_icmpne +51 -> 222
    //   174: new 95	java/io/FileInputStream
    //   177: dup
    //   178: ldc 117
    //   180: invokespecial 118	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   183: astore_3
    //   184: ldc 120
    //   186: aload_3
    //   187: invokestatic 124	org/chromium/tencent/utils/TencentDeviceUtils:parseFileForValue	(Ljava/lang/String;Ljava/io/FileInputStream;)I
    //   190: istore_1
    //   191: iload_1
    //   192: sipush 1000
    //   195: imul
    //   196: istore_1
    //   197: iload_1
    //   198: iload_0
    //   199: if_icmple +8 -> 207
    //   202: iload_1
    //   203: istore_0
    //   204: goto +3 -> 207
    //   207: aload_3
    //   208: invokevirtual 115	java/io/FileInputStream:close	()V
    //   211: iload_0
    //   212: ireturn
    //   213: astore 4
    //   215: aload_3
    //   216: invokevirtual 115	java/io/FileInputStream:close	()V
    //   219: aload 4
    //   221: athrow
    //   222: iload_0
    //   223: ireturn
    //   224: astore_3
    //   225: iconst_m1
    //   226: ireturn
    //   227: astore 4
    //   229: iload_0
    //   230: istore_1
    //   231: goto -78 -> 153
    //   234: iload_2
    //   235: iconst_1
    //   236: iadd
    //   237: istore_2
    //   238: iload_1
    //   239: istore_0
    //   240: goto -236 -> 4
    // Local variable table:
    //   start	length	slot	name	signature
    //   3	237	0	i	int
    //   52	187	1	j	int
    //   1	237	2	k	int
    //   18	198	3	localObject1	Object
    //   224	1	3	localIOException	IOException
    //   65	83	4	localObject2	Object
    //   160	7	4	localObject3	Object
    //   213	7	4	localObject4	Object
    //   227	1	4	localNumberFormatException	NumberFormatException
    // Exception table:
    //   from	to	target	type
    //   76	83	160	finally
    //   103	110	160	finally
    //   117	136	160	finally
    //   138	153	160	finally
    //   184	191	213	finally
    //   4	51	224	java/io/IOException
    //   53	76	224	java/io/IOException
    //   153	157	224	java/io/IOException
    //   162	169	224	java/io/IOException
    //   174	184	224	java/io/IOException
    //   207	211	224	java/io/IOException
    //   215	222	224	java/io/IOException
    //   76	83	227	java/lang/NumberFormatException
    //   103	110	227	java/lang/NumberFormatException
    //   117	136	227	java/lang/NumberFormatException
    //   138	153	227	java/lang/NumberFormatException
  }
  
  public static String getDeviceSet()
  {
    int i = 0;
    try
    {
      if (Build.VERSION.SDK_INT >= 21)
      {
        localObject = Build.SUPPORTED_ABIS;
      }
      else
      {
        localObject = new String[2];
        localObject[0] = Build.CPU_ABI;
        localObject[1] = Build.CPU_ABI2;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      int j = localObject.length;
      while (i < j)
      {
        localStringBuilder.append(localObject[i]);
        localStringBuilder.append(',');
        i += 1;
      }
      Object localObject = localStringBuilder.toString();
      return (String)localObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  public static int getNumberOfCPUCores()
  {
    try
    {
      int i = new File("/sys/devices/system/cpu/").listFiles(new FileFilter()
      {
        public boolean accept(File paramAnonymousFile)
        {
          return Pattern.matches("cpu[0-9]", paramAnonymousFile.getName());
        }
      }).length;
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 1;
  }
  
  /* Error */
  public static int getTotalRAMMemory()
  {
    // Byte code:
    //   0: getstatic 160	org/chromium/tencent/utils/TencentDeviceUtils:sTotalRAMMemory	I
    //   3: istore_0
    //   4: iload_0
    //   5: ifle +5 -> 10
    //   8: iload_0
    //   9: ireturn
    //   10: iconst_0
    //   11: istore_1
    //   12: iconst_0
    //   13: istore_2
    //   14: iconst_0
    //   15: istore_3
    //   16: new 162	java/io/FileReader
    //   19: dup
    //   20: ldc -92
    //   22: invokespecial 165	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   25: astore 4
    //   27: new 167	java/io/BufferedReader
    //   30: dup
    //   31: aload 4
    //   33: sipush 8192
    //   36: invokespecial 170	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   39: astore 7
    //   41: aload 7
    //   43: astore 5
    //   45: aload 4
    //   47: astore 6
    //   49: aload 7
    //   51: invokevirtual 173	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   54: astore 8
    //   56: iload_3
    //   57: istore_0
    //   58: aload 8
    //   60: ifnull +128 -> 188
    //   63: aload 7
    //   65: astore 5
    //   67: aload 4
    //   69: astore 6
    //   71: aload 8
    //   73: ldc -81
    //   75: invokevirtual 178	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   78: istore_0
    //   79: iconst_m1
    //   80: iload_0
    //   81: if_icmpeq -40 -> 41
    //   84: aload 7
    //   86: astore 5
    //   88: aload 4
    //   90: astore 6
    //   92: aload 8
    //   94: iload_0
    //   95: bipush 9
    //   97: iadd
    //   98: invokevirtual 182	java/lang/String:substring	(I)Ljava/lang/String;
    //   101: invokevirtual 185	java/lang/String:trim	()Ljava/lang/String;
    //   104: astore 8
    //   106: iload_3
    //   107: istore_0
    //   108: aload 8
    //   110: ifnull +78 -> 188
    //   113: iload_3
    //   114: istore_0
    //   115: aload 7
    //   117: astore 5
    //   119: aload 4
    //   121: astore 6
    //   123: ldc -108
    //   125: aload 8
    //   127: invokevirtual 185	java/lang/String:trim	()Ljava/lang/String;
    //   130: invokevirtual 189	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   133: ifne +55 -> 188
    //   136: iload_3
    //   137: istore_0
    //   138: aload 7
    //   140: astore 5
    //   142: aload 4
    //   144: astore 6
    //   146: aload 8
    //   148: ldc -65
    //   150: invokevirtual 195	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   153: ifeq +35 -> 188
    //   156: aload 7
    //   158: astore 5
    //   160: aload 4
    //   162: astore 6
    //   164: aload 8
    //   166: iconst_0
    //   167: aload 8
    //   169: ldc -65
    //   171: invokevirtual 178	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   174: invokevirtual 198	java/lang/String:substring	(II)Ljava/lang/String;
    //   177: invokevirtual 185	java/lang/String:trim	()Ljava/lang/String;
    //   180: invokestatic 69	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   183: sipush 1024
    //   186: idiv
    //   187: istore_0
    //   188: aload 7
    //   190: invokevirtual 199	java/io/BufferedReader:close	()V
    //   193: goto +10 -> 203
    //   196: astore 5
    //   198: aload 5
    //   200: invokevirtual 200	java/io/IOException:printStackTrace	()V
    //   203: iload_0
    //   204: istore_1
    //   205: aload 4
    //   207: invokevirtual 201	java/io/FileReader:close	()V
    //   210: goto +176 -> 386
    //   213: astore 4
    //   215: aload 4
    //   217: invokevirtual 200	java/io/IOException:printStackTrace	()V
    //   220: iload_1
    //   221: istore_0
    //   222: goto +164 -> 386
    //   225: astore 8
    //   227: goto +53 -> 280
    //   230: astore 8
    //   232: goto +107 -> 339
    //   235: astore 6
    //   237: aconst_null
    //   238: astore 5
    //   240: goto +162 -> 402
    //   243: astore 8
    //   245: aconst_null
    //   246: astore 7
    //   248: goto +32 -> 280
    //   251: astore 8
    //   253: aconst_null
    //   254: astore 7
    //   256: goto +83 -> 339
    //   259: astore 6
    //   261: aconst_null
    //   262: astore 4
    //   264: aload 4
    //   266: astore 5
    //   268: goto +134 -> 402
    //   271: astore 8
    //   273: aconst_null
    //   274: astore 4
    //   276: aload 4
    //   278: astore 7
    //   280: aload 7
    //   282: astore 5
    //   284: aload 4
    //   286: astore 6
    //   288: aload 8
    //   290: invokevirtual 202	java/lang/Throwable:printStackTrace	()V
    //   293: aload 7
    //   295: ifnull +18 -> 313
    //   298: aload 7
    //   300: invokevirtual 199	java/io/BufferedReader:close	()V
    //   303: goto +10 -> 313
    //   306: astore 5
    //   308: aload 5
    //   310: invokevirtual 200	java/io/IOException:printStackTrace	()V
    //   313: iload_2
    //   314: istore_0
    //   315: aload 4
    //   317: ifnull +69 -> 386
    //   320: aload 4
    //   322: invokevirtual 201	java/io/FileReader:close	()V
    //   325: iload_2
    //   326: istore_0
    //   327: goto +59 -> 386
    //   330: astore 8
    //   332: aconst_null
    //   333: astore 4
    //   335: aload 4
    //   337: astore 7
    //   339: aload 7
    //   341: astore 5
    //   343: aload 4
    //   345: astore 6
    //   347: aload 8
    //   349: invokevirtual 200	java/io/IOException:printStackTrace	()V
    //   352: aload 7
    //   354: ifnull +18 -> 372
    //   357: aload 7
    //   359: invokevirtual 199	java/io/BufferedReader:close	()V
    //   362: goto +10 -> 372
    //   365: astore 5
    //   367: aload 5
    //   369: invokevirtual 200	java/io/IOException:printStackTrace	()V
    //   372: iload_2
    //   373: istore_0
    //   374: aload 4
    //   376: ifnull +10 -> 386
    //   379: aload 4
    //   381: invokevirtual 201	java/io/FileReader:close	()V
    //   384: iload_2
    //   385: istore_0
    //   386: iload_0
    //   387: putstatic 160	org/chromium/tencent/utils/TencentDeviceUtils:sTotalRAMMemory	I
    //   390: iload_0
    //   391: ireturn
    //   392: astore 7
    //   394: aload 6
    //   396: astore 4
    //   398: aload 7
    //   400: astore 6
    //   402: aload 5
    //   404: ifnull +18 -> 422
    //   407: aload 5
    //   409: invokevirtual 199	java/io/BufferedReader:close	()V
    //   412: goto +10 -> 422
    //   415: astore 5
    //   417: aload 5
    //   419: invokevirtual 200	java/io/IOException:printStackTrace	()V
    //   422: aload 4
    //   424: ifnull +18 -> 442
    //   427: aload 4
    //   429: invokevirtual 201	java/io/FileReader:close	()V
    //   432: goto +10 -> 442
    //   435: astore 4
    //   437: aload 4
    //   439: invokevirtual 200	java/io/IOException:printStackTrace	()V
    //   442: aload 6
    //   444: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   3	388	0	i	int
    //   11	210	1	j	int
    //   13	372	2	k	int
    //   15	122	3	m	int
    //   25	181	4	localFileReader1	java.io.FileReader
    //   213	3	4	localIOException1	IOException
    //   262	166	4	localObject1	Object
    //   435	3	4	localIOException2	IOException
    //   43	116	5	localObject2	Object
    //   196	3	5	localIOException3	IOException
    //   238	45	5	localObject3	Object
    //   306	3	5	localIOException4	IOException
    //   341	1	5	localObject4	Object
    //   365	43	5	localIOException5	IOException
    //   415	3	5	localIOException6	IOException
    //   47	116	6	localFileReader2	java.io.FileReader
    //   235	1	6	localObject5	Object
    //   259	1	6	localObject6	Object
    //   286	157	6	localObject7	Object
    //   39	319	7	localObject8	Object
    //   392	7	7	localObject9	Object
    //   54	114	8	str	String
    //   225	1	8	localThrowable1	Throwable
    //   230	1	8	localIOException7	IOException
    //   243	1	8	localThrowable2	Throwable
    //   251	1	8	localIOException8	IOException
    //   271	18	8	localThrowable3	Throwable
    //   330	18	8	localIOException9	IOException
    // Exception table:
    //   from	to	target	type
    //   188	193	196	java/io/IOException
    //   205	210	213	java/io/IOException
    //   320	325	213	java/io/IOException
    //   379	384	213	java/io/IOException
    //   49	56	225	java/lang/Throwable
    //   71	79	225	java/lang/Throwable
    //   92	106	225	java/lang/Throwable
    //   123	136	225	java/lang/Throwable
    //   146	156	225	java/lang/Throwable
    //   164	188	225	java/lang/Throwable
    //   49	56	230	java/io/IOException
    //   71	79	230	java/io/IOException
    //   92	106	230	java/io/IOException
    //   123	136	230	java/io/IOException
    //   146	156	230	java/io/IOException
    //   164	188	230	java/io/IOException
    //   27	41	235	finally
    //   27	41	243	java/lang/Throwable
    //   27	41	251	java/io/IOException
    //   16	27	259	finally
    //   16	27	271	java/lang/Throwable
    //   298	303	306	java/io/IOException
    //   16	27	330	java/io/IOException
    //   357	362	365	java/io/IOException
    //   49	56	392	finally
    //   71	79	392	finally
    //   92	106	392	finally
    //   123	136	392	finally
    //   146	156	392	finally
    //   164	188	392	finally
    //   288	293	392	finally
    //   347	352	392	finally
    //   407	412	415	java/io/IOException
    //   427	432	435	java/io/IOException
  }
  
  private static int parseFileForValue(String paramString, FileInputStream paramFileInputStream)
  {
    arrayOfByte = new byte['Ѐ'];
    for (;;)
    {
      try
      {
        m = paramFileInputStream.read(arrayOfByte);
        j = 0;
      }
      catch (IOException|NumberFormatException paramString)
      {
        int m;
        continue;
        if (j >= m) {
          continue;
        }
        if (arrayOfByte[j] == 10) {
          continue;
        }
        int k = j;
        if (j != 0) {
          continue;
        }
        int i = j;
        if (arrayOfByte[j] != 10) {
          continue;
        }
        i = j + 1;
        int j = i;
        continue;
      }
      k = i;
      if (j >= m) {
        continue;
      }
      k = j - i;
      if (arrayOfByte[j] != paramString.charAt(k))
      {
        k = i;
        continue;
      }
      if (k == paramString.length() - 1)
      {
        i = extractValue(arrayOfByte, j);
        return i;
      }
      j += 1;
    }
    j = k + 1;
    break label94;
    return -1;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\utils\TencentDeviceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */