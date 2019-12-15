package com.tencent.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtils
{
  public static final String EXT = ".zip";
  public static final String TAG = "JarPluginManager";
  private static CRC32 jdField_a_of_type_JavaUtilZipCRC32 = new CRC32();
  private static ZipFile jdField_a_of_type_JavaUtilZipZipFile = null;
  
  /* Error */
  public static boolean UnZip(String paramString1, String paramString2, boolean paramBoolean, java.util.List<File> paramList)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 6
    //   3: aconst_null
    //   4: astore 7
    //   6: aload 7
    //   8: astore 5
    //   10: new 33	java/io/File
    //   13: dup
    //   14: aload_0
    //   15: invokespecial 36	java/io/File:<init>	(Ljava/lang/String;)V
    //   18: astore_0
    //   19: aload 7
    //   21: astore 5
    //   23: aload_0
    //   24: invokevirtual 40	java/io/File:exists	()Z
    //   27: ifne +16 -> 43
    //   30: aload 7
    //   32: astore 5
    //   34: ldc 11
    //   36: ldc 42
    //   38: invokestatic 48	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   41: iconst_0
    //   42: ireturn
    //   43: aload 7
    //   45: astore 5
    //   47: new 50	java/util/zip/ZipInputStream
    //   50: dup
    //   51: new 52	java/io/FileInputStream
    //   54: dup
    //   55: aload_0
    //   56: invokespecial 55	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   59: invokespecial 58	java/util/zip/ZipInputStream:<init>	(Ljava/io/InputStream;)V
    //   62: astore_0
    //   63: aload_0
    //   64: invokevirtual 62	java/util/zip/ZipInputStream:getNextEntry	()Ljava/util/zip/ZipEntry;
    //   67: astore 5
    //   69: aload 5
    //   71: ifnull +199 -> 270
    //   74: aload 5
    //   76: invokevirtual 67	java/util/zip/ZipEntry:isDirectory	()Z
    //   79: ifne -16 -> 63
    //   82: aload 5
    //   84: invokevirtual 71	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   87: astore 5
    //   89: ldc 73
    //   91: invokestatic 79	java/util/regex/Pattern:compile	(Ljava/lang/String;)Ljava/util/regex/Pattern;
    //   94: aload 5
    //   96: invokevirtual 83	java/util/regex/Pattern:matcher	(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   99: invokevirtual 88	java/util/regex/Matcher:matches	()Z
    //   102: ifne +7 -> 109
    //   105: iload_2
    //   106: ifeq -43 -> 63
    //   109: aload 5
    //   111: aload 5
    //   113: getstatic 92	java/io/File:separatorChar	C
    //   116: invokevirtual 98	java/lang/String:lastIndexOf	(I)I
    //   119: iconst_1
    //   120: iadd
    //   121: aload 5
    //   123: invokevirtual 102	java/lang/String:length	()I
    //   126: invokevirtual 106	java/lang/String:substring	(II)Ljava/lang/String;
    //   129: astore 5
    //   131: new 108	java/lang/StringBuilder
    //   134: dup
    //   135: invokespecial 109	java/lang/StringBuilder:<init>	()V
    //   138: astore 6
    //   140: aload 6
    //   142: aload_1
    //   143: invokevirtual 113	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   146: pop
    //   147: aload 6
    //   149: getstatic 92	java/io/File:separatorChar	C
    //   152: invokevirtual 116	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   155: pop
    //   156: aload 6
    //   158: aload 5
    //   160: invokevirtual 113	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   163: pop
    //   164: new 33	java/io/File
    //   167: dup
    //   168: aload 6
    //   170: invokevirtual 119	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   173: invokespecial 36	java/io/File:<init>	(Ljava/lang/String;)V
    //   176: astore 6
    //   178: aload 6
    //   180: invokevirtual 40	java/io/File:exists	()Z
    //   183: ifne +9 -> 192
    //   186: aload 6
    //   188: invokevirtual 122	java/io/File:createNewFile	()Z
    //   191: pop
    //   192: new 124	java/io/FileOutputStream
    //   195: dup
    //   196: aload 6
    //   198: invokespecial 125	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   201: astore 5
    //   203: ldc 126
    //   205: newarray <illegal type>
    //   207: astore 7
    //   209: aload_3
    //   210: ifnull +12 -> 222
    //   213: aload_3
    //   214: aload 6
    //   216: invokeinterface 132 2 0
    //   221: pop
    //   222: aload_0
    //   223: aload 7
    //   225: invokevirtual 136	java/util/zip/ZipInputStream:read	([B)I
    //   228: istore 4
    //   230: iload 4
    //   232: iconst_m1
    //   233: if_icmpeq +16 -> 249
    //   236: aload 5
    //   238: aload 7
    //   240: iconst_0
    //   241: iload 4
    //   243: invokevirtual 140	java/io/FileOutputStream:write	([BII)V
    //   246: goto -24 -> 222
    //   249: aload 5
    //   251: invokevirtual 143	java/io/FileOutputStream:flush	()V
    //   254: aload 5
    //   256: invokevirtual 146	java/io/FileOutputStream:close	()V
    //   259: goto -196 -> 63
    //   262: astore_1
    //   263: aload 5
    //   265: invokevirtual 146	java/io/FileOutputStream:close	()V
    //   268: aload_1
    //   269: athrow
    //   270: aload_0
    //   271: invokevirtual 147	java/util/zip/ZipInputStream:close	()V
    //   274: iconst_1
    //   275: ireturn
    //   276: astore_0
    //   277: aload_0
    //   278: invokevirtual 150	java/io/IOException:printStackTrace	()V
    //   281: iconst_1
    //   282: ireturn
    //   283: astore_3
    //   284: aload_0
    //   285: astore_1
    //   286: aload_3
    //   287: astore_0
    //   288: goto +42 -> 330
    //   291: astore_1
    //   292: goto +14 -> 306
    //   295: astore_0
    //   296: aload 5
    //   298: astore_1
    //   299: goto +31 -> 330
    //   302: astore_1
    //   303: aload 6
    //   305: astore_0
    //   306: aload_0
    //   307: astore 5
    //   309: aload_1
    //   310: invokevirtual 150	java/io/IOException:printStackTrace	()V
    //   313: aload_0
    //   314: ifnull +14 -> 328
    //   317: aload_0
    //   318: invokevirtual 147	java/util/zip/ZipInputStream:close	()V
    //   321: iconst_0
    //   322: ireturn
    //   323: astore_0
    //   324: aload_0
    //   325: invokevirtual 150	java/io/IOException:printStackTrace	()V
    //   328: iconst_0
    //   329: ireturn
    //   330: aload_1
    //   331: ifnull +15 -> 346
    //   334: aload_1
    //   335: invokevirtual 147	java/util/zip/ZipInputStream:close	()V
    //   338: goto +8 -> 346
    //   341: astore_1
    //   342: aload_1
    //   343: invokevirtual 150	java/io/IOException:printStackTrace	()V
    //   346: aload_0
    //   347: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	348	0	paramString1	String
    //   0	348	1	paramString2	String
    //   0	348	2	paramBoolean	boolean
    //   0	348	3	paramList	java.util.List<File>
    //   228	14	4	i	int
    //   8	300	5	localObject1	Object
    //   1	303	6	localObject2	Object
    //   4	235	7	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   222	230	262	finally
    //   236	246	262	finally
    //   249	254	262	finally
    //   270	274	276	java/io/IOException
    //   63	69	283	finally
    //   74	105	283	finally
    //   109	192	283	finally
    //   192	209	283	finally
    //   213	222	283	finally
    //   254	259	283	finally
    //   263	270	283	finally
    //   63	69	291	java/io/IOException
    //   74	105	291	java/io/IOException
    //   109	192	291	java/io/IOException
    //   192	209	291	java/io/IOException
    //   213	222	291	java/io/IOException
    //   254	259	291	java/io/IOException
    //   263	270	291	java/io/IOException
    //   10	19	295	finally
    //   23	30	295	finally
    //   34	41	295	finally
    //   47	63	295	finally
    //   309	313	295	finally
    //   10	19	302	java/io/IOException
    //   23	30	302	java/io/IOException
    //   34	41	302	java/io/IOException
    //   47	63	302	java/io/IOException
    //   317	321	323	java/io/IOException
    //   334	338	341	java/io/IOException
  }
  
  private static void a(File paramFile, ZipOutputStream paramZipOutputStream, String paramString)
    throws Exception
  {
    if (paramFile.isDirectory())
    {
      b(paramFile, paramZipOutputStream, paramString);
      return;
    }
    c(paramFile, paramZipOutputStream, paramString);
  }
  
  private static void b(File paramFile, ZipOutputStream paramZipOutputStream, String paramString)
    throws Exception
  {
    File[] arrayOfFile = paramFile.listFiles();
    Object localObject;
    if (arrayOfFile.length < 1)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(paramFile.getName());
      ((StringBuilder)localObject).append("/");
      paramZipOutputStream.putNextEntry(new ZipEntry(((StringBuilder)localObject).toString()));
      paramZipOutputStream.closeEntry();
    }
    int j = arrayOfFile.length;
    int i = 0;
    while (i < j)
    {
      localObject = arrayOfFile[i];
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append(paramFile.getName());
      localStringBuilder.append("/");
      a((File)localObject, paramZipOutputStream, localStringBuilder.toString());
      i += 1;
    }
  }
  
  private static void c(File paramFile, ZipOutputStream paramZipOutputStream, String paramString)
    throws Exception
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(paramFile.getName());
    paramZipOutputStream.putNextEntry(new ZipEntry(localStringBuilder.toString()));
    paramFile = new BufferedInputStream(new FileInputStream(paramFile));
    paramString = new byte['Ѐ'];
    for (;;)
    {
      int i = paramFile.read(paramString, 0, 1024);
      if (i == -1) {
        break;
      }
      paramZipOutputStream.write(paramString, 0, i);
    }
    paramFile.close();
    paramZipOutputStream.closeEntry();
  }
  
  public static void compress(File paramFile)
    throws Exception
  {
    String str1 = paramFile.getName();
    String str2 = paramFile.getParent();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str2);
    localStringBuilder.append(str1);
    localStringBuilder.append(".zip");
    compress(paramFile, localStringBuilder.toString());
  }
  
  public static void compress(File paramFile1, File paramFile2)
    throws Exception
  {
    paramFile2 = new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(paramFile2), new CRC32()));
    a(paramFile1, paramFile2, "");
    paramFile2.flush();
    paramFile2.close();
  }
  
  public static void compress(File paramFile, String paramString)
    throws Exception
  {
    compress(paramFile, new File(paramString));
  }
  
  public static void compress(String paramString)
    throws Exception
  {
    compress(new File(paramString));
  }
  
  public static void compress(String paramString1, String paramString2)
    throws Exception
  {
    compress(new File(paramString1), paramString2);
  }
  
  public static void compress(File[] paramArrayOfFile, String paramString)
    throws Exception
  {
    paramString = new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(paramString), new CRC32()));
    try
    {
      int j = paramArrayOfFile.length;
      int i = 0;
      while (i < j)
      {
        a(paramArrayOfFile[i], paramString, "");
        i += 1;
      }
      return;
    }
    finally
    {
      paramString.flush();
      paramString.close();
    }
  }
  
  public static void compressByte(byte[] paramArrayOfByte, String paramString1, ZipOutputStream paramZipOutputStream, String paramString2)
    throws Exception
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString2);
    localStringBuilder.append(paramString1);
    paramZipOutputStream.putNextEntry(new ZipEntry(localStringBuilder.toString()));
    paramZipOutputStream.write(paramArrayOfByte, 0, paramArrayOfByte.length);
    paramZipOutputStream.closeEntry();
  }
  
  /* Error */
  public static byte[] compressString(String paramString)
    throws Exception
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aload_0
    //   3: ifnonnull +5 -> 8
    //   6: aconst_null
    //   7: areturn
    //   8: new 222	java/io/ByteArrayOutputStream
    //   11: dup
    //   12: invokespecial 223	java/io/ByteArrayOutputStream:<init>	()V
    //   15: astore_1
    //   16: new 173	java/util/zip/ZipOutputStream
    //   19: dup
    //   20: aload_1
    //   21: invokespecial 206	java/util/zip/ZipOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   24: astore_2
    //   25: aload_2
    //   26: new 64	java/util/zip/ZipEntry
    //   29: dup
    //   30: ldc -31
    //   32: invokespecial 171	java/util/zip/ZipEntry:<init>	(Ljava/lang/String;)V
    //   35: invokevirtual 177	java/util/zip/ZipOutputStream:putNextEntry	(Ljava/util/zip/ZipEntry;)V
    //   38: aload_2
    //   39: aload_0
    //   40: invokevirtual 229	java/lang/String:getBytes	()[B
    //   43: invokevirtual 232	java/util/zip/ZipOutputStream:write	([B)V
    //   46: aload_2
    //   47: invokevirtual 180	java/util/zip/ZipOutputStream:closeEntry	()V
    //   50: aload_1
    //   51: invokevirtual 235	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   54: astore_0
    //   55: aload_2
    //   56: invokevirtual 210	java/util/zip/ZipOutputStream:close	()V
    //   59: aload_1
    //   60: invokevirtual 236	java/io/ByteArrayOutputStream:close	()V
    //   63: aload_0
    //   64: areturn
    //   65: astore_0
    //   66: aload_1
    //   67: astore_3
    //   68: aload_2
    //   69: astore_1
    //   70: goto +28 -> 98
    //   73: aload_2
    //   74: astore_0
    //   75: goto +48 -> 123
    //   78: astore_0
    //   79: aconst_null
    //   80: astore_2
    //   81: aload_1
    //   82: astore_3
    //   83: aload_2
    //   84: astore_1
    //   85: goto +13 -> 98
    //   88: aconst_null
    //   89: astore_0
    //   90: goto +33 -> 123
    //   93: astore_0
    //   94: aconst_null
    //   95: astore_3
    //   96: aload_3
    //   97: astore_1
    //   98: aload_1
    //   99: ifnull +10 -> 109
    //   102: aload_1
    //   103: invokevirtual 210	java/util/zip/ZipOutputStream:close	()V
    //   106: goto +3 -> 109
    //   109: aload_3
    //   110: ifnull +7 -> 117
    //   113: aload_3
    //   114: invokevirtual 236	java/io/ByteArrayOutputStream:close	()V
    //   117: aload_0
    //   118: athrow
    //   119: aconst_null
    //   120: astore_1
    //   121: aload_1
    //   122: astore_0
    //   123: aload_0
    //   124: ifnull +10 -> 134
    //   127: aload_0
    //   128: invokevirtual 210	java/util/zip/ZipOutputStream:close	()V
    //   131: goto +3 -> 134
    //   134: aload_1
    //   135: ifnull +8 -> 143
    //   138: aload_3
    //   139: astore_0
    //   140: goto -81 -> 59
    //   143: aconst_null
    //   144: areturn
    //   145: astore_0
    //   146: goto -27 -> 119
    //   149: astore_0
    //   150: goto -62 -> 88
    //   153: astore_0
    //   154: goto -81 -> 73
    //   157: astore_2
    //   158: goto -99 -> 59
    //   161: astore_1
    //   162: aload_0
    //   163: areturn
    //   164: astore_1
    //   165: goto -56 -> 109
    //   168: astore_1
    //   169: goto -52 -> 117
    //   172: astore_0
    //   173: goto -39 -> 134
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	176	0	paramString	String
    //   15	120	1	localObject1	Object
    //   161	1	1	localIOException1	java.io.IOException
    //   164	1	1	localIOException2	java.io.IOException
    //   168	1	1	localIOException3	java.io.IOException
    //   24	60	2	localZipOutputStream	ZipOutputStream
    //   157	1	2	localIOException4	java.io.IOException
    //   1	138	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   25	55	65	finally
    //   16	25	78	finally
    //   8	16	93	finally
    //   8	16	145	java/io/IOException
    //   16	25	149	java/io/IOException
    //   25	55	153	java/io/IOException
    //   55	59	157	java/io/IOException
    //   59	63	161	java/io/IOException
    //   102	106	164	java/io/IOException
    //   113	117	168	java/io/IOException
    //   127	131	172	java/io/IOException
  }
  
  /* Error */
  public static void unzip(ZipEntry paramZipEntry, java.io.InputStream paramInputStream, File paramFile)
    throws java.io.IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: aconst_null
    //   7: astore 6
    //   9: aload_0
    //   10: invokevirtual 67	java/util/zip/ZipEntry:isDirectory	()Z
    //   13: ifne +94 -> 107
    //   16: new 184	java/io/BufferedInputStream
    //   19: dup
    //   20: aload_1
    //   21: invokespecial 185	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   24: astore_1
    //   25: new 240	java/io/BufferedOutputStream
    //   28: dup
    //   29: new 124	java/io/FileOutputStream
    //   32: dup
    //   33: new 33	java/io/File
    //   36: dup
    //   37: aload_2
    //   38: aload_0
    //   39: invokevirtual 71	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   42: invokespecial 242	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   45: invokespecial 125	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   48: invokespecial 243	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   51: astore_0
    //   52: sipush 4096
    //   55: newarray <illegal type>
    //   57: astore_2
    //   58: aload_1
    //   59: aload_2
    //   60: invokevirtual 244	java/io/BufferedInputStream:read	([B)I
    //   63: istore_3
    //   64: iload_3
    //   65: iconst_m1
    //   66: if_icmpeq +13 -> 79
    //   69: aload_0
    //   70: aload_2
    //   71: iconst_0
    //   72: iload_3
    //   73: invokevirtual 245	java/io/BufferedOutputStream:write	([BII)V
    //   76: goto -18 -> 58
    //   79: aload_0
    //   80: invokevirtual 246	java/io/BufferedOutputStream:flush	()V
    //   83: goto +45 -> 128
    //   86: astore_2
    //   87: goto +68 -> 155
    //   90: astore_2
    //   91: goto +13 -> 104
    //   94: astore_2
    //   95: aload 5
    //   97: astore_0
    //   98: goto +57 -> 155
    //   101: astore_2
    //   102: aconst_null
    //   103: astore_0
    //   104: goto +48 -> 152
    //   107: new 33	java/io/File
    //   110: dup
    //   111: aload_2
    //   112: aload_0
    //   113: invokevirtual 71	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   116: invokespecial 242	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   119: invokevirtual 249	java/io/File:mkdirs	()Z
    //   122: pop
    //   123: aconst_null
    //   124: astore_0
    //   125: aload 6
    //   127: astore_1
    //   128: aload_1
    //   129: invokestatic 255	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   132: aload_0
    //   133: invokestatic 255	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   136: return
    //   137: astore_2
    //   138: aconst_null
    //   139: astore_1
    //   140: aload 5
    //   142: astore_0
    //   143: goto +12 -> 155
    //   146: astore_2
    //   147: aconst_null
    //   148: astore_0
    //   149: aload 4
    //   151: astore_1
    //   152: aload_2
    //   153: athrow
    //   154: astore_2
    //   155: aload_1
    //   156: invokestatic 255	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   159: aload_0
    //   160: invokestatic 255	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   163: aload_2
    //   164: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	165	0	paramZipEntry	ZipEntry
    //   0	165	1	paramInputStream	java.io.InputStream
    //   0	165	2	paramFile	File
    //   63	10	3	i	int
    //   1	149	4	localObject1	Object
    //   4	137	5	localObject2	Object
    //   7	119	6	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   52	58	86	finally
    //   58	64	86	finally
    //   69	76	86	finally
    //   79	83	86	finally
    //   52	58	90	java/io/IOException
    //   58	64	90	java/io/IOException
    //   69	76	90	java/io/IOException
    //   79	83	90	java/io/IOException
    //   25	52	94	finally
    //   25	52	101	java/io/IOException
    //   9	25	137	finally
    //   107	123	137	finally
    //   9	25	146	java/io/IOException
    //   107	123	146	java/io/IOException
    //   152	154	154	finally
  }
  
  /* Error */
  public static boolean unzip(File paramFile1, File paramFile2, String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 4
    //   6: new 52	java/io/FileInputStream
    //   9: dup
    //   10: aload_0
    //   11: invokespecial 55	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   14: astore_0
    //   15: aload_0
    //   16: aload_1
    //   17: aload_2
    //   18: invokestatic 261	com/tencent/common/utils/ZipUtils:unzip	(Ljava/io/InputStream;Ljava/io/File;Ljava/lang/String;)Z
    //   21: istore_3
    //   22: aload_0
    //   23: invokevirtual 262	java/io/FileInputStream:close	()V
    //   26: iload_3
    //   27: ireturn
    //   28: astore_0
    //   29: aload_0
    //   30: invokevirtual 150	java/io/IOException:printStackTrace	()V
    //   33: iload_3
    //   34: ireturn
    //   35: astore_1
    //   36: aload_0
    //   37: astore 4
    //   39: aload_1
    //   40: astore_0
    //   41: goto +40 -> 81
    //   44: astore_1
    //   45: goto +11 -> 56
    //   48: astore_0
    //   49: goto +32 -> 81
    //   52: astore_1
    //   53: aload 5
    //   55: astore_0
    //   56: aload_0
    //   57: astore 4
    //   59: aload_1
    //   60: invokevirtual 263	java/io/FileNotFoundException:printStackTrace	()V
    //   63: aload_0
    //   64: ifnull +15 -> 79
    //   67: aload_0
    //   68: invokevirtual 262	java/io/FileInputStream:close	()V
    //   71: goto +8 -> 79
    //   74: astore_0
    //   75: aload_0
    //   76: invokevirtual 150	java/io/IOException:printStackTrace	()V
    //   79: iconst_0
    //   80: ireturn
    //   81: aload 4
    //   83: ifnull +16 -> 99
    //   86: aload 4
    //   88: invokevirtual 262	java/io/FileInputStream:close	()V
    //   91: goto +8 -> 99
    //   94: astore_1
    //   95: aload_1
    //   96: invokevirtual 150	java/io/IOException:printStackTrace	()V
    //   99: aload_0
    //   100: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	101	0	paramFile1	File
    //   0	101	1	paramFile2	File
    //   0	101	2	paramString	String
    //   21	13	3	bool	boolean
    //   4	83	4	localFile	File
    //   1	53	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   22	26	28	java/io/IOException
    //   15	22	35	finally
    //   15	22	44	java/io/FileNotFoundException
    //   6	15	48	finally
    //   59	63	48	finally
    //   6	15	52	java/io/FileNotFoundException
    //   67	71	74	java/io/IOException
    //   86	91	94	java/io/IOException
  }
  
  /* Error */
  public static boolean unzip(java.io.InputStream paramInputStream, File paramFile, String paramString)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 4
    //   3: aconst_null
    //   4: astore 6
    //   6: aconst_null
    //   7: astore 5
    //   9: new 50	java/util/zip/ZipInputStream
    //   12: dup
    //   13: new 184	java/io/BufferedInputStream
    //   16: dup
    //   17: aload_0
    //   18: invokespecial 185	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   21: invokespecial 58	java/util/zip/ZipInputStream:<init>	(Ljava/io/InputStream;)V
    //   24: astore_0
    //   25: aload_0
    //   26: invokevirtual 62	java/util/zip/ZipInputStream:getNextEntry	()Ljava/util/zip/ZipEntry;
    //   29: astore 5
    //   31: aload 5
    //   33: ifnull +174 -> 207
    //   36: aload 5
    //   38: invokevirtual 71	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   41: ldc_w 265
    //   44: invokevirtual 269	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   47: ifeq +6 -> 53
    //   50: goto -25 -> 25
    //   53: aload 5
    //   55: invokevirtual 67	java/util/zip/ZipEntry:isDirectory	()Z
    //   58: ifne +129 -> 187
    //   61: aload_2
    //   62: ifnull +18 -> 80
    //   65: aload_2
    //   66: aload 5
    //   68: invokevirtual 71	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   71: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   74: ifne +6 -> 80
    //   77: goto -52 -> 25
    //   80: new 33	java/io/File
    //   83: dup
    //   84: aload_1
    //   85: aload 5
    //   87: invokevirtual 71	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   90: invokespecial 242	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   93: astore 5
    //   95: aload 5
    //   97: invokevirtual 276	java/io/File:getParentFile	()Ljava/io/File;
    //   100: invokevirtual 249	java/io/File:mkdirs	()Z
    //   103: pop
    //   104: new 124	java/io/FileOutputStream
    //   107: dup
    //   108: aload 5
    //   110: iconst_0
    //   111: invokespecial 279	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
    //   114: astore 5
    //   116: ldc_w 280
    //   119: newarray <illegal type>
    //   121: astore 6
    //   123: aload_0
    //   124: aload 6
    //   126: invokevirtual 136	java/util/zip/ZipInputStream:read	([B)I
    //   129: istore_3
    //   130: iload_3
    //   131: iconst_m1
    //   132: if_icmpeq +15 -> 147
    //   135: aload 5
    //   137: aload 6
    //   139: iconst_0
    //   140: iload_3
    //   141: invokevirtual 140	java/io/FileOutputStream:write	([BII)V
    //   144: goto -21 -> 123
    //   147: aload 5
    //   149: invokevirtual 146	java/io/FileOutputStream:close	()V
    //   152: goto -127 -> 25
    //   155: astore_1
    //   156: aload_1
    //   157: invokevirtual 281	java/lang/Exception:printStackTrace	()V
    //   160: aload_1
    //   161: athrow
    //   162: astore_1
    //   163: goto +10 -> 173
    //   166: astore_1
    //   167: aload_1
    //   168: invokevirtual 150	java/io/IOException:printStackTrace	()V
    //   171: aload_1
    //   172: athrow
    //   173: aload 5
    //   175: invokevirtual 146	java/io/FileOutputStream:close	()V
    //   178: aload_1
    //   179: athrow
    //   180: astore_1
    //   181: aload_1
    //   182: invokevirtual 281	java/lang/Exception:printStackTrace	()V
    //   185: aload_1
    //   186: athrow
    //   187: new 33	java/io/File
    //   190: dup
    //   191: aload_1
    //   192: aload 5
    //   194: invokevirtual 71	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   197: invokespecial 242	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   200: invokevirtual 249	java/io/File:mkdirs	()Z
    //   203: pop
    //   204: goto -179 -> 25
    //   207: iconst_1
    //   208: istore 4
    //   210: aload_0
    //   211: invokevirtual 147	java/util/zip/ZipInputStream:close	()V
    //   214: iconst_1
    //   215: ireturn
    //   216: astore_0
    //   217: aload_0
    //   218: invokevirtual 150	java/io/IOException:printStackTrace	()V
    //   221: iload 4
    //   223: ireturn
    //   224: astore_1
    //   225: goto +31 -> 256
    //   228: astore_1
    //   229: goto +14 -> 243
    //   232: astore_1
    //   233: aload 5
    //   235: astore_0
    //   236: goto +20 -> 256
    //   239: astore_1
    //   240: aload 6
    //   242: astore_0
    //   243: aload_0
    //   244: astore 5
    //   246: aload_1
    //   247: invokevirtual 281	java/lang/Exception:printStackTrace	()V
    //   250: aload_0
    //   251: invokevirtual 147	java/util/zip/ZipInputStream:close	()V
    //   254: iconst_0
    //   255: ireturn
    //   256: aload_0
    //   257: invokevirtual 147	java/util/zip/ZipInputStream:close	()V
    //   260: goto +8 -> 268
    //   263: astore_0
    //   264: aload_0
    //   265: invokevirtual 150	java/io/IOException:printStackTrace	()V
    //   268: aload_1
    //   269: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	270	0	paramInputStream	java.io.InputStream
    //   0	270	1	paramFile	File
    //   0	270	2	paramString	String
    //   129	12	3	i	int
    //   1	221	4	bool	boolean
    //   7	238	5	localObject	Object
    //   4	237	6	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   147	152	155	java/lang/Exception
    //   123	130	162	finally
    //   135	144	162	finally
    //   167	173	162	finally
    //   123	130	166	java/io/IOException
    //   135	144	166	java/io/IOException
    //   173	178	180	java/lang/Exception
    //   210	214	216	java/io/IOException
    //   250	254	216	java/io/IOException
    //   25	31	224	finally
    //   36	50	224	finally
    //   53	61	224	finally
    //   65	77	224	finally
    //   80	123	224	finally
    //   147	152	224	finally
    //   156	162	224	finally
    //   173	178	224	finally
    //   178	180	224	finally
    //   181	187	224	finally
    //   187	204	224	finally
    //   25	31	228	java/lang/Exception
    //   36	50	228	java/lang/Exception
    //   53	61	228	java/lang/Exception
    //   65	77	228	java/lang/Exception
    //   80	123	228	java/lang/Exception
    //   156	162	228	java/lang/Exception
    //   178	180	228	java/lang/Exception
    //   181	187	228	java/lang/Exception
    //   187	204	228	java/lang/Exception
    //   9	25	232	finally
    //   246	250	232	finally
    //   9	25	239	java/lang/Exception
    //   256	260	263	java/io/IOException
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\ZipUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */