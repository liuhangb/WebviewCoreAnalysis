package com.tencent.mtt.video.browser.export.db;

import android.os.Environment;
import android.text.TextUtils;
import com.tencent.mtt.video.export.basemoduleforexternal.Md5Utils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class VideoFileUtils
{
  public static final String DIR_EXT_MAIN = "QQBrowser";
  
  public static int copy(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException, OutOfMemoryError
  {
    long l = copyLarge(paramInputStream, paramOutputStream);
    if (l > 2147483647L) {
      return -1;
    }
    return (int)l;
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
  
  public static File createDir(File paramFile, String paramString)
  {
    if ((paramFile != null) && (paramString != null) && (paramString.length() != 0))
    {
      paramFile = new File(paramFile, paramString);
      if (!paramFile.exists()) {
        paramFile.mkdirs();
      }
      return paramFile;
    }
    return null;
  }
  
  public static void deleteVideoPicFile(final String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    new Thread("deleteVideoFrame")
    {
      public void run()
      {
        File localFile = new File(VideoFileUtils.getVideoPicRootDir(), Md5Utils.getMD5(paramString));
        try
        {
          if (localFile.exists()) {
            localFile.delete();
          }
          return;
        }
        catch (Exception localException) {}
      }
    }.start();
  }
  
  public static void deleteVideoPicFile(final ArrayList<String> paramArrayList)
  {
    if (paramArrayList != null)
    {
      if (paramArrayList.isEmpty()) {
        return;
      }
      new Thread("deleteVideoPicFile")
      {
        public void run()
        {
          Iterator localIterator = paramArrayList.iterator();
          while (localIterator.hasNext())
          {
            Object localObject = (String)localIterator.next();
            localObject = new File(VideoFileUtils.getVideoPicRootDir(), Md5Utils.getMD5((String)localObject));
            if (((File)localObject).exists()) {
              ((File)localObject).delete();
            }
          }
        }
      }.start();
      return;
    }
  }
  
  public static File getQQBrowserDir()
  {
    return createDir(Environment.getExternalStorageDirectory(), "QQBrowser");
  }
  
  public static File getVideoPicRootDir()
  {
    return createDir(getQQBrowserDir(), ".VideoPics");
  }
  
  /* Error */
  public static byte[] loadVideoPicFromDisk(String paramString, boolean paramBoolean)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore 4
    //   5: iload_1
    //   6: ifeq +15 -> 21
    //   9: new 54	java/io/File
    //   12: dup
    //   13: aload_0
    //   14: invokespecial 112	java/io/File:<init>	(Ljava/lang/String;)V
    //   17: astore_0
    //   18: goto +20 -> 38
    //   21: aload_0
    //   22: invokestatic 118	com/tencent/mtt/video/export/basemoduleforexternal/Md5Utils:getMD5	(Ljava/lang/String;)Ljava/lang/String;
    //   25: astore_0
    //   26: new 54	java/io/File
    //   29: dup
    //   30: invokestatic 120	com/tencent/mtt/video/browser/export/db/VideoFileUtils:getVideoPicRootDir	()Ljava/io/File;
    //   33: aload_0
    //   34: invokespecial 57	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   37: astore_0
    //   38: aload_0
    //   39: invokevirtual 61	java/io/File:exists	()Z
    //   42: ifeq +42 -> 84
    //   45: new 122	java/io/BufferedInputStream
    //   48: dup
    //   49: new 124	java/io/FileInputStream
    //   52: dup
    //   53: aload_0
    //   54: invokespecial 127	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   57: invokespecial 130	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   60: astore_0
    //   61: aload_0
    //   62: astore_2
    //   63: aload_0
    //   64: invokestatic 134	com/tencent/mtt/video/browser/export/db/VideoFileUtils:toByteArrayOutputStream	(Ljava/io/InputStream;)Ljava/io/ByteArrayOutputStream;
    //   67: invokevirtual 140	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   70: astore_3
    //   71: aload_3
    //   72: astore_2
    //   73: goto +16 -> 89
    //   76: astore_3
    //   77: goto +25 -> 102
    //   80: astore_3
    //   81: goto +33 -> 114
    //   84: aconst_null
    //   85: astore_0
    //   86: aload 4
    //   88: astore_2
    //   89: aload_0
    //   90: invokestatic 146	com/tencent/common/utils/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   93: aload_2
    //   94: areturn
    //   95: astore_0
    //   96: goto +31 -> 127
    //   99: astore_3
    //   100: aconst_null
    //   101: astore_0
    //   102: aload_0
    //   103: astore_2
    //   104: aload_3
    //   105: invokevirtual 149	java/lang/OutOfMemoryError:printStackTrace	()V
    //   108: aload 4
    //   110: astore_2
    //   111: goto -22 -> 89
    //   114: aload_0
    //   115: astore_2
    //   116: aload_3
    //   117: invokevirtual 150	java/lang/Exception:printStackTrace	()V
    //   120: aload 4
    //   122: astore_2
    //   123: goto -34 -> 89
    //   126: astore_0
    //   127: aload_2
    //   128: invokestatic 146	com/tencent/common/utils/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   131: aload_0
    //   132: athrow
    //   133: astore_3
    //   134: aconst_null
    //   135: astore_0
    //   136: goto -22 -> 114
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	139	0	paramString	String
    //   0	139	1	paramBoolean	boolean
    //   1	127	2	localObject1	Object
    //   70	2	3	arrayOfByte	byte[]
    //   76	1	3	localOutOfMemoryError1	OutOfMemoryError
    //   80	1	3	localException1	Exception
    //   99	18	3	localOutOfMemoryError2	OutOfMemoryError
    //   133	1	3	localException2	Exception
    //   3	118	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   63	71	76	java/lang/OutOfMemoryError
    //   63	71	80	java/lang/Exception
    //   9	18	95	finally
    //   21	38	95	finally
    //   38	61	95	finally
    //   9	18	99	java/lang/OutOfMemoryError
    //   21	38	99	java/lang/OutOfMemoryError
    //   38	61	99	java/lang/OutOfMemoryError
    //   63	71	126	finally
    //   104	108	126	finally
    //   116	120	126	finally
    //   9	18	133	java/lang/Exception
    //   21	38	133	java/lang/Exception
    //   38	61	133	java/lang/Exception
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
  
  /* Error */
  public static boolean save(File paramFile, byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore_2
    //   4: new 191	java/io/BufferedOutputStream
    //   7: dup
    //   8: aload_0
    //   9: invokestatic 193	com/tencent/mtt/video/browser/export/db/VideoFileUtils:openOutputStream	(Ljava/io/File;)Ljava/io/FileOutputStream;
    //   12: invokespecial 196	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   15: astore_0
    //   16: aload_0
    //   17: aload_1
    //   18: iconst_0
    //   19: aload_1
    //   20: arraylength
    //   21: invokevirtual 197	java/io/BufferedOutputStream:write	([BII)V
    //   24: aload_0
    //   25: invokestatic 146	com/tencent/common/utils/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   28: iconst_1
    //   29: ireturn
    //   30: astore_1
    //   31: goto +9 -> 40
    //   34: goto +12 -> 46
    //   37: astore_1
    //   38: aload_2
    //   39: astore_0
    //   40: aload_0
    //   41: invokestatic 146	com/tencent/common/utils/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   44: aload_1
    //   45: athrow
    //   46: aload_0
    //   47: invokestatic 146	com/tencent/common/utils/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   50: iconst_0
    //   51: ireturn
    //   52: astore_0
    //   53: aload_3
    //   54: astore_0
    //   55: goto -9 -> 46
    //   58: astore_1
    //   59: goto -25 -> 34
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	62	0	paramFile	File
    //   0	62	1	paramArrayOfByte	byte[]
    //   3	36	2	localObject1	Object
    //   1	53	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   16	24	30	finally
    //   4	16	37	finally
    //   4	16	52	java/lang/Exception
    //   16	24	58	java/lang/Exception
  }
  
  public static void saveVideoPicFile(String paramString, byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length != 0))
    {
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
      save(new File(getVideoPicRootDir(), Md5Utils.getMD5(paramString)), paramArrayOfByte);
      return;
    }
  }
  
  public static ByteArrayOutputStream toByteArrayOutputStream(InputStream paramInputStream)
    throws IOException, OutOfMemoryError
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    copy(paramInputStream, localByteArrayOutputStream);
    return localByteArrayOutputStream;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\db\VideoFileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */