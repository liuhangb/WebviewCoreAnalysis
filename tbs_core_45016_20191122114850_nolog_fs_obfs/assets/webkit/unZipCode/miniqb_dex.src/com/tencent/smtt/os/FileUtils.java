package com.tencent.smtt.os;

import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class FileUtils
{
  private static final Pattern jdField_a_of_type_JavaUtilRegexPattern = Pattern.compile("[\\w%+,./=_-]+");
  private static Pattern b = Pattern.compile("[\\\\\\/\\:\\*\\?\\\"\\|\\<\\>]", 2);
  
  private static File a()
  {
    try
    {
      File localFile = com.tencent.common.utils.FileUtils.getSDcardDir(ContextHolder.getInstance().getContext());
      return localFile;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
    }
    return null;
  }
  
  private static File a(File paramFile, String paramString)
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
  
  public static String a()
  {
    File localFile = b();
    if (localFile != null) {
      return localFile.getAbsolutePath();
    }
    return null;
  }
  
  public static void a(String paramString)
  {
    try
    {
      b(paramString);
      new File(paramString.toString()).delete();
      return;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_file_delete_file_error", "string"), 0);
  }
  
  public static boolean a(File paramFile1, File paramFile2)
  {
    try
    {
      paramFile1 = new FileInputStream(paramFile1);
      try
      {
        boolean bool = a(paramFile1, paramFile2);
        return bool;
      }
      finally
      {
        paramFile1.close();
      }
    }
    catch (IOException paramFile1)
    {
      for (;;) {}
    }
    return false;
  }
  
  /* Error */
  public static boolean a(InputStream paramInputStream, File paramFile)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 78	java/io/File:exists	()Z
    //   4: ifeq +8 -> 12
    //   7: aload_1
    //   8: invokevirtual 100	java/io/File:delete	()Z
    //   11: pop
    //   12: aconst_null
    //   13: astore 4
    //   15: aconst_null
    //   16: astore_3
    //   17: new 137	java/io/FileOutputStream
    //   20: dup
    //   21: aload_1
    //   22: invokespecial 138	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   25: astore_1
    //   26: sipush 4096
    //   29: newarray <illegal type>
    //   31: astore_3
    //   32: aload_0
    //   33: aload_3
    //   34: invokevirtual 142	java/io/InputStream:read	([B)I
    //   37: istore_2
    //   38: iload_2
    //   39: iflt +13 -> 52
    //   42: aload_1
    //   43: aload_3
    //   44: iconst_0
    //   45: iload_2
    //   46: invokevirtual 146	java/io/FileOutputStream:write	([BII)V
    //   49: goto -17 -> 32
    //   52: aload_1
    //   53: invokevirtual 149	java/io/FileOutputStream:flush	()V
    //   56: aload_1
    //   57: invokevirtual 153	java/io/FileOutputStream:getFD	()Ljava/io/FileDescriptor;
    //   60: invokevirtual 158	java/io/FileDescriptor:sync	()V
    //   63: aload_1
    //   64: invokevirtual 159	java/io/FileOutputStream:close	()V
    //   67: goto +89 -> 156
    //   70: astore_0
    //   71: aload_1
    //   72: astore_3
    //   73: goto +47 -> 120
    //   76: astore_3
    //   77: aload_1
    //   78: astore_0
    //   79: aload_3
    //   80: astore_1
    //   81: goto +11 -> 92
    //   84: astore_0
    //   85: goto +35 -> 120
    //   88: astore_1
    //   89: aload 4
    //   91: astore_0
    //   92: aload_0
    //   93: astore_3
    //   94: aload_1
    //   95: invokevirtual 160	java/lang/Exception:printStackTrace	()V
    //   98: aload_0
    //   99: ifnull +57 -> 156
    //   102: aload_0
    //   103: invokevirtual 149	java/io/FileOutputStream:flush	()V
    //   106: aload_0
    //   107: invokevirtual 153	java/io/FileOutputStream:getFD	()Ljava/io/FileDescriptor;
    //   110: invokevirtual 158	java/io/FileDescriptor:sync	()V
    //   113: aload_0
    //   114: invokevirtual 159	java/io/FileOutputStream:close	()V
    //   117: goto +39 -> 156
    //   120: aload_3
    //   121: ifnull +18 -> 139
    //   124: aload_3
    //   125: invokevirtual 149	java/io/FileOutputStream:flush	()V
    //   128: aload_3
    //   129: invokevirtual 153	java/io/FileOutputStream:getFD	()Ljava/io/FileDescriptor;
    //   132: invokevirtual 158	java/io/FileDescriptor:sync	()V
    //   135: aload_3
    //   136: invokevirtual 159	java/io/FileOutputStream:close	()V
    //   139: aload_0
    //   140: athrow
    //   141: astore_0
    //   142: iconst_0
    //   143: ireturn
    //   144: astore_0
    //   145: goto -82 -> 63
    //   148: astore_1
    //   149: goto -36 -> 113
    //   152: astore_1
    //   153: goto -18 -> 135
    //   156: iconst_1
    //   157: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	158	0	paramInputStream	InputStream
    //   0	158	1	paramFile	File
    //   37	9	2	i	int
    //   16	57	3	localObject1	Object
    //   76	4	3	localException	Exception
    //   93	43	3	localInputStream	InputStream
    //   13	77	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   26	32	70	finally
    //   32	38	70	finally
    //   42	49	70	finally
    //   26	32	76	java/lang/Exception
    //   32	38	76	java/lang/Exception
    //   42	49	76	java/lang/Exception
    //   17	26	84	finally
    //   94	98	84	finally
    //   17	26	88	java/lang/Exception
    //   0	12	141	java/io/IOException
    //   52	56	141	java/io/IOException
    //   63	67	141	java/io/IOException
    //   102	106	141	java/io/IOException
    //   113	117	141	java/io/IOException
    //   124	128	141	java/io/IOException
    //   135	139	141	java/io/IOException
    //   139	141	141	java/io/IOException
    //   56	63	144	java/io/IOException
    //   106	113	148	java/io/IOException
    //   128	135	152	java/io/IOException
  }
  
  private static File b()
  {
    try
    {
      File localFile = a(a(), "QQBrowser");
      return localFile;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
    }
    return null;
  }
  
  public static void b(String paramString)
  {
    Object localObject = new File(paramString);
    if (!((File)localObject).exists()) {
      return;
    }
    if (!((File)localObject).isDirectory()) {
      return;
    }
    String[] arrayOfString = ((File)localObject).list();
    int i = 0;
    for (;;)
    {
      if (i >= arrayOfString.length) {
        break label240;
      }
      if (paramString.endsWith(File.separator))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append(arrayOfString[i]);
        localObject = new File(((StringBuilder)localObject).toString());
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append(File.separator);
        ((StringBuilder)localObject).append(arrayOfString[i]);
        localObject = new File(((StringBuilder)localObject).toString());
      }
      try
      {
        if (((File)localObject).isFile()) {
          ((File)localObject).delete();
        }
        if (((File)localObject).isDirectory())
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append(paramString);
          ((StringBuilder)localObject).append("/");
          ((StringBuilder)localObject).append(arrayOfString[i]);
          b(((StringBuilder)localObject).toString());
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append(paramString);
          ((StringBuilder)localObject).append("/");
          ((StringBuilder)localObject).append(arrayOfString[i]);
          a(((StringBuilder)localObject).toString());
        }
        i += 1;
      }
      catch (Exception paramString)
      {
        for (;;) {}
      }
    }
    SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_file_delete_file_error", "string"), 0);
    return;
    label240:
  }
  
  public static native int getFatVolumeId(String paramString);
  
  public static native boolean getFileStatus(String paramString, a parama);
  
  public static native int getPermissions(String paramString, int[] paramArrayOfInt);
  
  public static native int setPermissions(String paramString, int paramInt1, int paramInt2, int paramInt3);
  
  public static final class a {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\os\FileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */