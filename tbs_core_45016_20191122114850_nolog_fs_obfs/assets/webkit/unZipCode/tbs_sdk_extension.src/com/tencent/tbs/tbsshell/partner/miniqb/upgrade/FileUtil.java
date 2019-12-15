package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;

class FileUtil
{
  private static final String APK_ASSETS = "assets/";
  private static final String APK_LIB = "lib/";
  private static final int APK_LIB_LEN = 4;
  private static final int DEFAULT_BUFFER_SIZE = 4096;
  public static final int ENU_NEW_TBS_BACKUP_PATH = 4;
  public static final int ENU_OLD_TBS_BACKUP_PATH3 = 3;
  public static final int ENU_TBS_LOCALINSTALL = 5;
  private static final String LIB_SUFFIX = ".so";
  public static final FileComparator SIMPLE_COMPARATOR = new FileComparator()
  {
    public boolean equals(File paramAnonymousFile1, File paramAnonymousFile2)
    {
      return (paramAnonymousFile1.length() == paramAnonymousFile2.length()) && (paramAnonymousFile1.lastModified() == paramAnonymousFile2.lastModified());
    }
  };
  private static final String TAG = "FileHelper";
  public static final String TBS_CORE_PRIVATE = "core_private";
  public static final String TBS_FILE_CORE_SHARE = "core_share";
  public static final String TBS_FILE_SHARE = "share";
  private static final String TBS_SDCARD_DIR = "tbs";
  private static final String TBS_SDCARD_SHARE_DIR = ".tbs";
  public static final int ZIP_BUFFER_SIZE = 4096;
  
  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable == null) {
      return;
    }
    try
    {
      paramCloseable.close();
      return;
    }
    catch (Exception paramCloseable)
    {
      paramCloseable.printStackTrace();
    }
  }
  
  public static int copy(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException, OutOfMemoryError
  {
    long l = copyLarge(paramInputStream, paramOutputStream);
    if (l > 2147483647L) {
      return -1;
    }
    return (int)l;
  }
  
  /* Error */
  private static boolean copyFileIfChanged(InputStream paramInputStream, ZipEntry paramZipEntry, String paramString1, String paramString2)
    throws Exception
  {
    // Byte code:
    //   0: new 96	java/io/File
    //   3: dup
    //   4: aload_2
    //   5: invokespecial 99	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: invokestatic 103	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:ensureDirectory	(Ljava/io/File;)Z
    //   11: pop
    //   12: new 105	java/lang/StringBuilder
    //   15: dup
    //   16: invokespecial 106	java/lang/StringBuilder:<init>	()V
    //   19: astore 5
    //   21: aload 5
    //   23: aload_2
    //   24: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   27: pop
    //   28: aload 5
    //   30: getstatic 113	java/io/File:separator	Ljava/lang/String;
    //   33: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: pop
    //   37: aload 5
    //   39: aload_3
    //   40: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: pop
    //   44: aload 5
    //   46: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   49: astore 7
    //   51: new 96	java/io/File
    //   54: dup
    //   55: aload 7
    //   57: invokespecial 99	java/io/File:<init>	(Ljava/lang/String;)V
    //   60: astore 6
    //   62: aconst_null
    //   63: astore 5
    //   65: aconst_null
    //   66: astore_2
    //   67: new 119	java/io/FileOutputStream
    //   70: dup
    //   71: aload 6
    //   73: invokespecial 122	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   76: astore_3
    //   77: sipush 8192
    //   80: newarray <illegal type>
    //   82: astore_2
    //   83: aload_0
    //   84: aload_2
    //   85: invokevirtual 128	java/io/InputStream:read	([B)I
    //   88: istore 4
    //   90: iload 4
    //   92: ifle +14 -> 106
    //   95: aload_3
    //   96: aload_2
    //   97: iconst_0
    //   98: iload 4
    //   100: invokevirtual 132	java/io/FileOutputStream:write	([BII)V
    //   103: goto -20 -> 83
    //   106: aload_3
    //   107: invokevirtual 133	java/io/FileOutputStream:close	()V
    //   110: aload 7
    //   112: aload_1
    //   113: invokevirtual 139	java/util/zip/ZipEntry:getSize	()J
    //   116: aload_1
    //   117: invokevirtual 142	java/util/zip/ZipEntry:getTime	()J
    //   120: aload_1
    //   121: invokevirtual 145	java/util/zip/ZipEntry:getCrc	()J
    //   124: invokestatic 149	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:isFileDifferent	(Ljava/lang/String;JJJ)Z
    //   127: ifeq +37 -> 164
    //   130: new 105	java/lang/StringBuilder
    //   133: dup
    //   134: invokespecial 106	java/lang/StringBuilder:<init>	()V
    //   137: astore_0
    //   138: aload_0
    //   139: ldc -105
    //   141: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   144: pop
    //   145: aload_0
    //   146: aload 7
    //   148: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   151: pop
    //   152: ldc 39
    //   154: aload_0
    //   155: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   158: invokestatic 157	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   161: pop
    //   162: iconst_0
    //   163: ireturn
    //   164: aload 6
    //   166: aload_1
    //   167: invokevirtual 142	java/util/zip/ZipEntry:getTime	()J
    //   170: invokevirtual 161	java/io/File:setLastModified	(J)Z
    //   173: ifne +35 -> 208
    //   176: new 105	java/lang/StringBuilder
    //   179: dup
    //   180: invokespecial 106	java/lang/StringBuilder:<init>	()V
    //   183: astore_0
    //   184: aload_0
    //   185: ldc -93
    //   187: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   190: pop
    //   191: aload_0
    //   192: aload 6
    //   194: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   197: pop
    //   198: ldc 39
    //   200: aload_0
    //   201: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   204: invokestatic 157	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   207: pop
    //   208: iconst_1
    //   209: ireturn
    //   210: astore_0
    //   211: aload_3
    //   212: astore_2
    //   213: goto +108 -> 321
    //   216: astore_1
    //   217: aload_3
    //   218: astore_0
    //   219: goto +11 -> 230
    //   222: astore_0
    //   223: goto +98 -> 321
    //   226: astore_1
    //   227: aload 5
    //   229: astore_0
    //   230: aload_0
    //   231: astore_2
    //   232: new 105	java/lang/StringBuilder
    //   235: dup
    //   236: invokespecial 106	java/lang/StringBuilder:<init>	()V
    //   239: astore_3
    //   240: aload_0
    //   241: astore_2
    //   242: aload_3
    //   243: ldc -88
    //   245: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   248: pop
    //   249: aload_0
    //   250: astore_2
    //   251: aload_3
    //   252: aload 6
    //   254: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   257: pop
    //   258: aload_0
    //   259: astore_2
    //   260: ldc 39
    //   262: aload_3
    //   263: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   266: aload_1
    //   267: invokestatic 172	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   270: pop
    //   271: aload_0
    //   272: astore_2
    //   273: aload 6
    //   275: invokestatic 175	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:delete	(Ljava/io/File;)V
    //   278: aload_0
    //   279: astore_2
    //   280: new 105	java/lang/StringBuilder
    //   283: dup
    //   284: invokespecial 106	java/lang/StringBuilder:<init>	()V
    //   287: astore_3
    //   288: aload_0
    //   289: astore_2
    //   290: aload_3
    //   291: ldc -88
    //   293: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   296: pop
    //   297: aload_0
    //   298: astore_2
    //   299: aload_3
    //   300: aload 6
    //   302: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   305: pop
    //   306: aload_0
    //   307: astore_2
    //   308: new 86	java/io/IOException
    //   311: dup
    //   312: aload_3
    //   313: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   316: aload_1
    //   317: invokespecial 178	java/io/IOException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   320: athrow
    //   321: aload_2
    //   322: ifnull +7 -> 329
    //   325: aload_2
    //   326: invokevirtual 133	java/io/FileOutputStream:close	()V
    //   329: aload_0
    //   330: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	331	0	paramInputStream	InputStream
    //   0	331	1	paramZipEntry	ZipEntry
    //   0	331	2	paramString1	String
    //   0	331	3	paramString2	String
    //   88	11	4	i	int
    //   19	209	5	localStringBuilder	StringBuilder
    //   60	241	6	localFile	File
    //   49	98	7	str	String
    // Exception table:
    //   from	to	target	type
    //   77	83	210	finally
    //   83	90	210	finally
    //   95	103	210	finally
    //   77	83	216	java/io/IOException
    //   83	90	216	java/io/IOException
    //   95	103	216	java/io/IOException
    //   67	77	222	finally
    //   232	240	222	finally
    //   242	249	222	finally
    //   251	258	222	finally
    //   260	271	222	finally
    //   273	278	222	finally
    //   280	288	222	finally
    //   290	297	222	finally
    //   299	306	222	finally
    //   308	321	222	finally
    //   67	77	226	java/io/IOException
  }
  
  public static boolean copyFiles(File paramFile1, File paramFile2)
    throws Exception
  {
    return copyFiles(paramFile1, paramFile2, null);
  }
  
  public static boolean copyFiles(File paramFile1, File paramFile2, FileFilter paramFileFilter)
    throws Exception
  {
    return copyFiles(paramFile1, paramFile2, paramFileFilter, SIMPLE_COMPARATOR);
  }
  
  public static boolean copyFiles(File paramFile1, File paramFile2, FileFilter paramFileFilter, FileComparator paramFileComparator)
    throws Exception
  {
    if (paramFile1 != null)
    {
      if (paramFile2 == null) {
        return false;
      }
      if (!paramFile1.exists()) {
        return false;
      }
      if (paramFile1.isFile()) {
        return performCopyFile(paramFile1, paramFile2, paramFileFilter, paramFileComparator);
      }
      paramFile1 = paramFile1.listFiles();
      if (paramFile1 == null) {
        return false;
      }
      int j = paramFile1.length;
      int i = 0;
      boolean bool = true;
      while (i < j)
      {
        paramFileComparator = paramFile1[i];
        if (!copyFiles(paramFileComparator, new File(paramFile2, paramFileComparator.getName()), paramFileFilter)) {
          bool = false;
        }
        i += 1;
      }
      return bool;
    }
    return false;
  }
  
  public static long copyLarge(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException, OutOfMemoryError
  {
    if (paramInputStream == null) {
      return -1L;
    }
    byte[] arrayOfByte = new byte['က'];
    int i;
    for (long l = 0L;; l += i)
    {
      i = paramInputStream.read(arrayOfByte);
      if (-1 == i) {
        break;
      }
      paramOutputStream.write(arrayOfByte, 0, i);
    }
    return l;
  }
  
  public static boolean copyTbsFilesIfNeeded(File paramFile1, File paramFile2)
    throws Exception
  {
    return copyTbsFilesIfNeeded(paramFile1.getPath(), paramFile2.getPath());
  }
  
  @SuppressLint({"InlinedApi"})
  public static boolean copyTbsFilesIfNeeded(String paramString1, String paramString2)
    throws Exception
  {
    String str2 = Build.CPU_ABI;
    String str1;
    if (Build.VERSION.SDK_INT >= 8) {
      str1 = Build.CPU_ABI2;
    } else {
      str1 = null;
    }
    return copyTbsFilesIfNeeded(paramString1, paramString2, str2, str1, PropertyUtils.getQuickly("ro.product.cpu.upgradeabi", "armeabi"));
  }
  
  private static boolean copyTbsFilesIfNeeded(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws Exception
  {
    iterateOverTbsFiles(paramString1, paramString3, paramString4, paramString5, new IterateHandler()
    {
      public boolean handleEntry(InputStream paramAnonymousInputStream, ZipEntry paramAnonymousZipEntry, String paramAnonymousString)
        throws Exception
      {
        try
        {
          boolean bool = FileUtil.copyFileIfChanged(paramAnonymousInputStream, paramAnonymousZipEntry, this.val$dstDir, paramAnonymousString);
          return bool;
        }
        catch (Exception paramAnonymousInputStream)
        {
          throw new Exception("copyFileIfChanged Exception", paramAnonymousInputStream);
        }
      }
    });
  }
  
  public static void delete(File paramFile)
  {
    delete(paramFile, false);
  }
  
  public static void delete(File paramFile, boolean paramBoolean)
  {
    if (paramFile != null)
    {
      if (!paramFile.exists()) {
        return;
      }
      if (paramFile.isFile())
      {
        paramFile.delete();
        return;
      }
      File[] arrayOfFile = paramFile.listFiles();
      if (arrayOfFile == null) {
        return;
      }
      int j = arrayOfFile.length;
      int i = 0;
      while (i < j)
      {
        delete(arrayOfFile[i], paramBoolean);
        i += 1;
      }
      if (!paramBoolean) {
        paramFile.delete();
      }
      return;
    }
  }
  
  public static boolean ensureDirectory(File paramFile)
  {
    if (paramFile == null) {
      return false;
    }
    if ((paramFile.exists()) && (paramFile.isDirectory())) {
      return true;
    }
    delete(paramFile);
    return paramFile.mkdirs();
  }
  
  public static long getFreeSpace(String paramString)
  {
    try
    {
      StatFs localStatFs = new StatFs(paramString);
      localStatFs.restat(paramString);
      long l = localStatFs.getBlockSize();
      int i = localStatFs.getAvailableBlocks();
      return l * i;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    return 0L;
  }
  
  public static File getSDcardDir()
  {
    try
    {
      File localFile = Environment.getExternalStorageDirectory();
      return localFile;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
    }
    return null;
  }
  
  private static String getStandardExternalFilesDir(Context paramContext, String paramString)
  {
    if (paramContext == null) {
      return "";
    }
    paramContext = paramContext.getApplicationContext();
    try
    {
      String str = paramContext.getExternalFilesDir(paramString).getAbsolutePath();
      return str;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      try
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(Environment.getExternalStorageDirectory());
        localStringBuilder.append(File.separator);
        localStringBuilder.append("Android");
        localStringBuilder.append(File.separator);
        localStringBuilder.append("data");
        localStringBuilder.append(File.separator);
        localStringBuilder.append(paramContext.getApplicationInfo().packageName);
        localStringBuilder.append(File.separator);
        localStringBuilder.append("files");
        localStringBuilder.append(File.separator);
        localStringBuilder.append(paramString);
        paramContext = localStringBuilder.toString();
        return paramContext;
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return "";
  }
  
  public static String getTBSSdcardFilePath(Context paramContext, int paramInt)
  {
    if (paramContext == null) {
      return "";
    }
    Object localObject1 = "";
    try
    {
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(Environment.getExternalStorageDirectory());
      ((StringBuilder)localObject2).append(File.separator);
      localObject2 = ((StringBuilder)localObject2).toString();
      localObject1 = localObject2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    switch (paramInt)
    {
    default: 
      return "";
    case 5: 
      if (((String)localObject1).equals("")) {
        return (String)localObject1;
      }
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append((String)localObject1);
      ((StringBuilder)localObject3).append("tencent");
      ((StringBuilder)localObject3).append(File.separator);
      ((StringBuilder)localObject3).append("tbs");
      ((StringBuilder)localObject3).append(File.separator);
      ((StringBuilder)localObject3).append(paramContext.getApplicationInfo().packageName);
      return ((StringBuilder)localObject3).toString();
    case 4: 
      if (((String)localObject1).equals("")) {
        return getStandardExternalFilesDir(paramContext, "backup");
      }
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append((String)localObject1);
      ((StringBuilder)localObject3).append("backup");
      ((StringBuilder)localObject3).append(File.separator);
      ((StringBuilder)localObject3).append(paramContext.getApplicationInfo().packageName);
      localObject3 = ((StringBuilder)localObject3).toString();
      File localFile = new File((String)localObject3);
      if (localFile.exists())
      {
        localObject1 = localObject3;
        if (localFile.canWrite()) {}
      }
      else if (!localFile.exists())
      {
        localFile.mkdirs();
        localObject1 = localObject3;
        if (!localFile.canWrite()) {
          return getStandardExternalFilesDir(paramContext, "backup");
        }
      }
      else
      {
        localObject1 = getStandardExternalFilesDir(paramContext, "backup");
      }
      return (String)localObject1;
    }
    if (((String)localObject1).equals("")) {
      return (String)localObject1;
    }
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append((String)localObject1);
    ((StringBuilder)localObject3).append("backup");
    ((StringBuilder)localObject3).append(File.separator);
    ((StringBuilder)localObject3).append(paramContext.getApplicationInfo().packageName);
    return ((StringBuilder)localObject3).toString();
  }
  
  public static File getTbsSdcardShareDir()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(getSDcardDir().getAbsolutePath());
    ((StringBuilder)localObject).append("/");
    ((StringBuilder)localObject).append(".tbs");
    localObject = new File(((StringBuilder)localObject).toString());
    if (!((File)localObject).exists()) {
      ((File)localObject).mkdirs();
    }
    return (File)localObject;
  }
  
  public static boolean hasSDcard()
  {
    try
    {
      boolean bool = "mounted".equals(Environment.getExternalStorageState());
      return bool;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
    }
    return false;
  }
  
  /* Error */
  private static boolean isFileDifferent(String paramString, long paramLong1, long paramLong2, long paramLong3)
    throws Exception
  {
    // Byte code:
    //   0: new 96	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 99	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_0
    //   9: aload_0
    //   10: invokevirtual 355	java/io/File:length	()J
    //   13: lload_1
    //   14: lcmp
    //   15: ifeq +60 -> 75
    //   18: new 105	java/lang/StringBuilder
    //   21: dup
    //   22: invokespecial 106	java/lang/StringBuilder:<init>	()V
    //   25: astore 8
    //   27: aload 8
    //   29: ldc_w 357
    //   32: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: pop
    //   36: aload 8
    //   38: aload_0
    //   39: invokevirtual 355	java/io/File:length	()J
    //   42: invokevirtual 360	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload 8
    //   48: ldc_w 362
    //   51: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: pop
    //   55: aload 8
    //   57: lload_1
    //   58: invokevirtual 360	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   61: pop
    //   62: ldc 39
    //   64: aload 8
    //   66: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   69: invokestatic 157	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   72: pop
    //   73: iconst_1
    //   74: ireturn
    //   75: new 364	java/io/FileInputStream
    //   78: dup
    //   79: aload_0
    //   80: invokespecial 365	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   83: astore 8
    //   85: new 367	java/util/zip/CRC32
    //   88: dup
    //   89: invokespecial 368	java/util/zip/CRC32:<init>	()V
    //   92: astore 9
    //   94: sipush 8192
    //   97: newarray <illegal type>
    //   99: astore 10
    //   101: aload 8
    //   103: aload 10
    //   105: invokevirtual 369	java/io/FileInputStream:read	([B)I
    //   108: istore 7
    //   110: iload 7
    //   112: ifle +16 -> 128
    //   115: aload 9
    //   117: aload 10
    //   119: iconst_0
    //   120: iload 7
    //   122: invokevirtual 372	java/util/zip/CRC32:update	([BII)V
    //   125: goto -24 -> 101
    //   128: aload 9
    //   130: invokevirtual 375	java/util/zip/CRC32:getValue	()J
    //   133: lstore_1
    //   134: new 105	java/lang/StringBuilder
    //   137: dup
    //   138: invokespecial 106	java/lang/StringBuilder:<init>	()V
    //   141: astore 9
    //   143: aload 9
    //   145: ldc_w 295
    //   148: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   151: pop
    //   152: aload 9
    //   154: aload_0
    //   155: invokevirtual 203	java/io/File:getName	()Ljava/lang/String;
    //   158: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   161: pop
    //   162: aload 9
    //   164: ldc_w 377
    //   167: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   170: pop
    //   171: aload 9
    //   173: lload_1
    //   174: invokevirtual 360	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   177: pop
    //   178: aload 9
    //   180: ldc_w 379
    //   183: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   186: pop
    //   187: aload 9
    //   189: lload 5
    //   191: invokevirtual 360	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   194: pop
    //   195: aload 9
    //   197: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   200: pop
    //   201: lload_1
    //   202: lload 5
    //   204: lcmp
    //   205: ifeq +10 -> 215
    //   208: aload 8
    //   210: invokevirtual 380	java/io/FileInputStream:close	()V
    //   213: iconst_1
    //   214: ireturn
    //   215: aload 8
    //   217: invokevirtual 380	java/io/FileInputStream:close	()V
    //   220: iconst_0
    //   221: ireturn
    //   222: astore_0
    //   223: goto +7 -> 230
    //   226: astore_0
    //   227: aconst_null
    //   228: astore 8
    //   230: aload 8
    //   232: ifnull +8 -> 240
    //   235: aload 8
    //   237: invokevirtual 380	java/io/FileInputStream:close	()V
    //   240: aload_0
    //   241: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	242	0	paramString	String
    //   0	242	1	paramLong1	long
    //   0	242	3	paramLong2	long
    //   0	242	5	paramLong3	long
    //   108	13	7	i	int
    //   25	211	8	localObject1	Object
    //   92	104	9	localObject2	Object
    //   99	19	10	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   85	101	222	finally
    //   101	110	222	finally
    //   115	125	222	finally
    //   128	201	222	finally
    //   75	85	226	finally
  }
  
  public static boolean isFileValid(File paramFile)
  {
    return (paramFile != null) && (paramFile.exists()) && (paramFile.isFile()) && (paramFile.length() > 0L);
  }
  
  /* Error */
  private static boolean iterateOverTbsFiles(String paramString1, String paramString2, String paramString3, String paramString4, IterateHandler paramIterateHandler)
    throws Exception
  {
    // Byte code:
    //   0: new 105	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 106	java/lang/StringBuilder:<init>	()V
    //   7: astore 9
    //   9: aload 9
    //   11: ldc_w 383
    //   14: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   17: pop
    //   18: aload 9
    //   20: aload_0
    //   21: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: pop
    //   25: aload 9
    //   27: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   30: pop
    //   31: new 385	java/util/zip/ZipFile
    //   34: dup
    //   35: aload_0
    //   36: invokespecial 386	java/util/zip/ZipFile:<init>	(Ljava/lang/String;)V
    //   39: astore_0
    //   40: aload_0
    //   41: invokevirtual 390	java/util/zip/ZipFile:entries	()Ljava/util/Enumeration;
    //   44: astore 9
    //   46: iconst_0
    //   47: istore 6
    //   49: iconst_0
    //   50: istore 5
    //   52: aload 9
    //   54: invokeinterface 395 1 0
    //   59: ifeq +593 -> 652
    //   62: aload 9
    //   64: invokeinterface 399 1 0
    //   69: checkcast 135	java/util/zip/ZipEntry
    //   72: astore 11
    //   74: aload 11
    //   76: invokevirtual 400	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   79: astore 10
    //   81: aload 10
    //   83: ifnull -31 -> 52
    //   86: aload 10
    //   88: ldc_w 402
    //   91: invokevirtual 406	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   94: ifeq +6 -> 100
    //   97: goto -45 -> 52
    //   100: aload 10
    //   102: ldc 21
    //   104: invokevirtual 410	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   107: ifne +16 -> 123
    //   110: aload 10
    //   112: ldc 18
    //   114: invokevirtual 410	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   117: ifne +6 -> 123
    //   120: goto -68 -> 52
    //   123: aload 10
    //   125: aload 10
    //   127: bipush 47
    //   129: invokevirtual 414	java/lang/String:lastIndexOf	(I)I
    //   132: invokevirtual 418	java/lang/String:substring	(I)Ljava/lang/String;
    //   135: astore 12
    //   137: iload 6
    //   139: istore 7
    //   141: iload 5
    //   143: istore 8
    //   145: aload 12
    //   147: ldc 34
    //   149: invokevirtual 421	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   152: ifeq +381 -> 533
    //   155: aload 10
    //   157: getstatic 423	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:APK_LIB_LEN	I
    //   160: aload_1
    //   161: iconst_0
    //   162: aload_1
    //   163: invokevirtual 425	java/lang/String:length	()I
    //   166: invokevirtual 429	java/lang/String:regionMatches	(ILjava/lang/String;II)Z
    //   169: ifeq +31 -> 200
    //   172: aload 10
    //   174: getstatic 423	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:APK_LIB_LEN	I
    //   177: aload_1
    //   178: invokevirtual 425	java/lang/String:length	()I
    //   181: iadd
    //   182: invokevirtual 433	java/lang/String:charAt	(I)C
    //   185: bipush 47
    //   187: if_icmpne +13 -> 200
    //   190: iconst_1
    //   191: istore 7
    //   193: iload 5
    //   195: istore 8
    //   197: goto +336 -> 533
    //   200: aload_2
    //   201: ifnull +121 -> 322
    //   204: aload 10
    //   206: getstatic 423	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:APK_LIB_LEN	I
    //   209: aload_2
    //   210: iconst_0
    //   211: aload_2
    //   212: invokevirtual 425	java/lang/String:length	()I
    //   215: invokevirtual 429	java/lang/String:regionMatches	(ILjava/lang/String;II)Z
    //   218: ifeq +104 -> 322
    //   221: aload 10
    //   223: getstatic 423	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:APK_LIB_LEN	I
    //   226: aload_2
    //   227: invokevirtual 425	java/lang/String:length	()I
    //   230: iadd
    //   231: invokevirtual 433	java/lang/String:charAt	(I)C
    //   234: bipush 47
    //   236: if_icmpne +86 -> 322
    //   239: iload 6
    //   241: ifeq +40 -> 281
    //   244: new 105	java/lang/StringBuilder
    //   247: dup
    //   248: invokespecial 106	java/lang/StringBuilder:<init>	()V
    //   251: astore 10
    //   253: aload 10
    //   255: ldc_w 435
    //   258: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   261: pop
    //   262: aload 10
    //   264: aload_2
    //   265: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   268: pop
    //   269: aload 10
    //   271: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   274: pop
    //   275: iconst_1
    //   276: istore 5
    //   278: goto -226 -> 52
    //   281: new 105	java/lang/StringBuilder
    //   284: dup
    //   285: invokespecial 106	java/lang/StringBuilder:<init>	()V
    //   288: astore 13
    //   290: aload 13
    //   292: ldc_w 437
    //   295: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   298: pop
    //   299: aload 13
    //   301: aload_2
    //   302: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   305: pop
    //   306: aload 13
    //   308: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   311: pop
    //   312: iconst_1
    //   313: istore 8
    //   315: iload 6
    //   317: istore 7
    //   319: goto +214 -> 533
    //   322: aload_3
    //   323: ifnull +127 -> 450
    //   326: aload 10
    //   328: getstatic 423	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:APK_LIB_LEN	I
    //   331: aload_3
    //   332: iconst_0
    //   333: aload_3
    //   334: invokevirtual 425	java/lang/String:length	()I
    //   337: invokevirtual 429	java/lang/String:regionMatches	(ILjava/lang/String;II)Z
    //   340: ifeq +110 -> 450
    //   343: aload 10
    //   345: getstatic 423	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:APK_LIB_LEN	I
    //   348: aload_3
    //   349: invokevirtual 425	java/lang/String:length	()I
    //   352: iadd
    //   353: invokevirtual 433	java/lang/String:charAt	(I)C
    //   356: bipush 47
    //   358: if_icmpne +92 -> 450
    //   361: iload 6
    //   363: ifne +53 -> 416
    //   366: iload 5
    //   368: ifeq +6 -> 374
    //   371: goto +45 -> 416
    //   374: new 105	java/lang/StringBuilder
    //   377: dup
    //   378: invokespecial 106	java/lang/StringBuilder:<init>	()V
    //   381: astore 13
    //   383: aload 13
    //   385: ldc_w 439
    //   388: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   391: pop
    //   392: aload 13
    //   394: aload_3
    //   395: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   398: pop
    //   399: aload 13
    //   401: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   404: pop
    //   405: iload 6
    //   407: istore 7
    //   409: iload 5
    //   411: istore 8
    //   413: goto +120 -> 533
    //   416: new 105	java/lang/StringBuilder
    //   419: dup
    //   420: invokespecial 106	java/lang/StringBuilder:<init>	()V
    //   423: astore 10
    //   425: aload 10
    //   427: ldc_w 441
    //   430: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   433: pop
    //   434: aload 10
    //   436: aload_3
    //   437: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   440: pop
    //   441: aload 10
    //   443: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   446: pop
    //   447: goto -395 -> 52
    //   450: new 105	java/lang/StringBuilder
    //   453: dup
    //   454: invokespecial 106	java/lang/StringBuilder:<init>	()V
    //   457: astore 11
    //   459: aload 11
    //   461: ldc_w 443
    //   464: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   467: pop
    //   468: aload 11
    //   470: aload 10
    //   472: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   475: pop
    //   476: aload 11
    //   478: ldc_w 445
    //   481: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   484: pop
    //   485: aload 11
    //   487: aload_1
    //   488: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   491: pop
    //   492: aload 11
    //   494: ldc_w 447
    //   497: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   500: pop
    //   501: aload 11
    //   503: aload_2
    //   504: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   507: pop
    //   508: aload 11
    //   510: ldc_w 449
    //   513: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   516: pop
    //   517: aload 11
    //   519: aload_3
    //   520: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   523: pop
    //   524: aload 11
    //   526: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   529: pop
    //   530: goto -478 -> 52
    //   533: aload_0
    //   534: aload 11
    //   536: invokevirtual 453	java/util/zip/ZipFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   539: astore 13
    //   541: aload 4
    //   543: aload 13
    //   545: aload 11
    //   547: aload 12
    //   549: iconst_1
    //   550: invokevirtual 418	java/lang/String:substring	(I)Ljava/lang/String;
    //   553: invokeinterface 457 4 0
    //   558: ifne +52 -> 610
    //   561: new 105	java/lang/StringBuilder
    //   564: dup
    //   565: invokespecial 106	java/lang/StringBuilder:<init>	()V
    //   568: astore_1
    //   569: aload_1
    //   570: ldc_w 459
    //   573: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   576: pop
    //   577: aload_1
    //   578: aload 10
    //   580: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   583: pop
    //   584: ldc 39
    //   586: aload_1
    //   587: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   590: invokestatic 461	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   593: pop
    //   594: aload 13
    //   596: ifnull +8 -> 604
    //   599: aload 13
    //   601: invokevirtual 462	java/io/InputStream:close	()V
    //   604: aload_0
    //   605: invokevirtual 463	java/util/zip/ZipFile:close	()V
    //   608: iconst_0
    //   609: ireturn
    //   610: iload 7
    //   612: istore 6
    //   614: iload 8
    //   616: istore 5
    //   618: aload 13
    //   620: ifnull -568 -> 52
    //   623: aload 13
    //   625: invokevirtual 462	java/io/InputStream:close	()V
    //   628: iload 7
    //   630: istore 6
    //   632: iload 8
    //   634: istore 5
    //   636: goto -584 -> 52
    //   639: astore_1
    //   640: aload 13
    //   642: ifnull +8 -> 650
    //   645: aload 13
    //   647: invokevirtual 462	java/io/InputStream:close	()V
    //   650: aload_1
    //   651: athrow
    //   652: aload_0
    //   653: invokevirtual 463	java/util/zip/ZipFile:close	()V
    //   656: iconst_1
    //   657: ireturn
    //   658: astore_1
    //   659: goto +6 -> 665
    //   662: astore_1
    //   663: aconst_null
    //   664: astore_0
    //   665: aload_0
    //   666: ifnull +7 -> 673
    //   669: aload_0
    //   670: invokevirtual 463	java/util/zip/ZipFile:close	()V
    //   673: aload_1
    //   674: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	675	0	paramString1	String
    //   0	675	1	paramString2	String
    //   0	675	2	paramString3	String
    //   0	675	3	paramString4	String
    //   0	675	4	paramIterateHandler	IterateHandler
    //   50	585	5	i	int
    //   47	584	6	j	int
    //   139	490	7	k	int
    //   143	490	8	m	int
    //   7	56	9	localObject1	Object
    //   79	500	10	localObject2	Object
    //   72	474	11	localObject3	Object
    //   135	413	12	str	String
    //   288	358	13	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   541	594	639	finally
    //   40	46	658	finally
    //   52	81	658	finally
    //   86	97	658	finally
    //   100	120	658	finally
    //   123	137	658	finally
    //   145	190	658	finally
    //   204	239	658	finally
    //   244	275	658	finally
    //   281	312	658	finally
    //   326	361	658	finally
    //   374	405	658	finally
    //   416	447	658	finally
    //   450	530	658	finally
    //   533	541	658	finally
    //   599	604	658	finally
    //   623	628	658	finally
    //   645	650	658	finally
    //   650	652	658	finally
    //   31	40	662	finally
  }
  
  public static FileInputStream openInputStream(File paramFile)
    throws IOException
  {
    if (paramFile.exists())
    {
      if (!paramFile.isDirectory())
      {
        if (paramFile.canRead()) {
          return new FileInputStream(paramFile);
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("File '");
        localStringBuilder.append(paramFile);
        localStringBuilder.append("' cannot be read");
        throw new IOException(localStringBuilder.toString());
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("File '");
      localStringBuilder.append(paramFile);
      localStringBuilder.append("' exists but is a directory");
      throw new IOException(localStringBuilder.toString());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("File '");
    localStringBuilder.append(paramFile);
    localStringBuilder.append("' does not exist");
    throw new FileNotFoundException(localStringBuilder.toString());
  }
  
  public static FileOutputStream openOutputStream(File paramFile)
    throws IOException
  {
    Object localObject;
    if (paramFile.exists())
    {
      if (!paramFile.isDirectory())
      {
        if (!paramFile.canWrite())
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("File '");
          ((StringBuilder)localObject).append(paramFile);
          ((StringBuilder)localObject).append("' cannot be written to");
          throw new IOException(((StringBuilder)localObject).toString());
        }
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("File '");
        ((StringBuilder)localObject).append(paramFile);
        ((StringBuilder)localObject).append("' exists but is a directory");
        throw new IOException(((StringBuilder)localObject).toString());
      }
    }
    else
    {
      localObject = paramFile.getParentFile();
      if ((localObject != null) && (!((File)localObject).exists()) && (!((File)localObject).mkdirs()))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("File '");
        ((StringBuilder)localObject).append(paramFile);
        ((StringBuilder)localObject).append("' could not be created");
        throw new IOException(((StringBuilder)localObject).toString());
      }
    }
    return new FileOutputStream(paramFile);
  }
  
  private static boolean performCopyFile(File paramFile1, File paramFile2, FileFilter paramFileFilter, FileComparator paramFileComparator)
    throws Exception
  {
    if (paramFile1 != null)
    {
      if (paramFile2 == null) {
        return false;
      }
      if ((paramFileFilter != null) && (!paramFileFilter.accept(paramFile1))) {
        return false;
      }
      paramFileFilter = null;
      Object localObject = null;
      try
      {
        if (paramFile1.exists())
        {
          if (!paramFile1.isFile()) {
            return false;
          }
          if (paramFile2.exists())
          {
            if ((paramFileComparator != null) && (paramFileComparator.equals(paramFile1, paramFile2))) {
              return true;
            }
            delete(paramFile2);
          }
          paramFileComparator = paramFile2.getParentFile();
          if (paramFileComparator.isFile()) {
            delete(paramFileComparator);
          }
          if ((!paramFileComparator.exists()) && (!paramFileComparator.mkdirs())) {
            return false;
          }
          paramFileComparator = new FileInputStream(paramFile1).getChannel();
          paramFile1 = (File)localObject;
          try
          {
            paramFileFilter = new FileOutputStream(paramFile2).getChannel();
            paramFile1 = paramFileFilter;
            long l = paramFileComparator.size();
            paramFile1 = paramFileFilter;
            if (paramFileFilter.transferFrom(paramFileComparator, 0L, l) != l)
            {
              paramFile1 = paramFileFilter;
              delete(paramFile2);
              if (paramFileComparator != null) {
                paramFileComparator.close();
              }
              if (paramFileFilter != null) {
                paramFileFilter.close();
              }
              return false;
            }
            if (paramFileComparator != null) {
              paramFileComparator.close();
            }
            if (paramFileFilter != null) {
              paramFileFilter.close();
            }
            return true;
          }
          finally
          {
            paramFile2 = paramFileComparator;
            paramFileComparator = paramFile1;
            paramFile1 = paramFileFilter;
            paramFileFilter = paramFileComparator;
            break label221;
          }
        }
        return false;
      }
      finally
      {
        paramFileComparator = null;
        paramFile2 = paramFileFilter;
        paramFileFilter = paramFileComparator;
        label221:
        if (paramFile2 != null) {
          paramFile2.close();
        }
        if (paramFileFilter != null) {
          paramFileFilter.close();
        }
      }
    }
    return false;
  }
  
  /* Error */
  public static byte[] read(File paramFile)
    throws OutOfMemoryError
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: aload_0
    //   7: invokestatic 514	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:openInputStream	(Ljava/io/File;)Ljava/io/FileInputStream;
    //   10: astore_1
    //   11: aload_1
    //   12: astore_0
    //   13: aload_1
    //   14: invokestatic 518	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:toByteArrayOutputStream	(Ljava/io/InputStream;)Ljava/io/ByteArrayOutputStream;
    //   17: invokevirtual 524	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   20: astore_2
    //   21: aload_1
    //   22: ifnull +7 -> 29
    //   25: aload_1
    //   26: invokestatic 526	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   29: aload_2
    //   30: areturn
    //   31: astore_1
    //   32: goto +46 -> 78
    //   35: astore_2
    //   36: goto +16 -> 52
    //   39: astore_2
    //   40: goto +22 -> 62
    //   43: astore_1
    //   44: aconst_null
    //   45: astore_0
    //   46: goto +32 -> 78
    //   49: astore_2
    //   50: aconst_null
    //   51: astore_1
    //   52: aload_1
    //   53: astore_0
    //   54: aload_2
    //   55: invokevirtual 527	java/lang/OutOfMemoryError:printStackTrace	()V
    //   58: aload_1
    //   59: astore_0
    //   60: aload_2
    //   61: athrow
    //   62: aload_1
    //   63: astore_0
    //   64: aload_2
    //   65: invokevirtual 82	java/lang/Exception:printStackTrace	()V
    //   68: aload_1
    //   69: ifnull +7 -> 76
    //   72: aload_1
    //   73: invokestatic 526	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   76: aconst_null
    //   77: areturn
    //   78: aload_0
    //   79: ifnull +7 -> 86
    //   82: aload_0
    //   83: invokestatic 526	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   86: aload_1
    //   87: athrow
    //   88: astore_2
    //   89: aconst_null
    //   90: astore_1
    //   91: goto -29 -> 62
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	94	0	paramFile	File
    //   10	16	1	localFileInputStream	FileInputStream
    //   31	1	1	localObject1	Object
    //   43	1	1	localObject2	Object
    //   51	40	1	localCloseable	Closeable
    //   20	10	2	arrayOfByte	byte[]
    //   35	1	2	localOutOfMemoryError1	OutOfMemoryError
    //   39	1	2	localException1	Exception
    //   49	16	2	localOutOfMemoryError2	OutOfMemoryError
    //   88	1	2	localException2	Exception
    // Exception table:
    //   from	to	target	type
    //   13	21	31	finally
    //   54	58	31	finally
    //   60	62	31	finally
    //   64	68	31	finally
    //   13	21	35	java/lang/OutOfMemoryError
    //   13	21	39	java/lang/Exception
    //   6	11	43	finally
    //   6	11	49	java/lang/OutOfMemoryError
    //   6	11	88	java/lang/Exception
  }
  
  public static boolean removeTbsFiles(File paramFile)
  {
    boolean bool1 = paramFile.exists();
    int i = 0;
    if (bool1)
    {
      paramFile = paramFile.listFiles();
      if (paramFile != null)
      {
        bool1 = false;
        for (;;)
        {
          bool2 = bool1;
          if (i >= paramFile.length) {
            break;
          }
          if (!paramFile[i].delete())
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("Could not delete native binary: ");
            localStringBuilder.append(paramFile[i].getPath());
            Log.w("FileHelper", localStringBuilder.toString());
          }
          else
          {
            bool1 = true;
          }
          i += 1;
        }
      }
    }
    boolean bool2 = false;
    return bool2;
  }
  
  public static boolean removeTbsFiles(String paramString)
  {
    return removeTbsFiles(new File(paramString));
  }
  
  public static byte[] toByteArray(InputStream paramInputStream)
    throws IOException, OutOfMemoryError
  {
    return toByteArrayOutputStream(paramInputStream).toByteArray();
  }
  
  public static ByteArrayOutputStream toByteArrayOutputStream(InputStream paramInputStream)
    throws IOException, OutOfMemoryError
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    copy(paramInputStream, localByteArrayOutputStream);
    return localByteArrayOutputStream;
  }
  
  public static abstract interface FileComparator
  {
    public abstract boolean equals(File paramFile1, File paramFile2);
  }
  
  public static abstract interface IterateHandler
  {
    public abstract boolean handleEntry(InputStream paramInputStream, ZipEntry paramZipEntry, String paramString)
      throws Exception;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\FileUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */