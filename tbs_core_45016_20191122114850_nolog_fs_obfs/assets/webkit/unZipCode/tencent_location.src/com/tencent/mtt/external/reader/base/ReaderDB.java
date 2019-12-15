package com.tencent.mtt.external.reader.base;

import android.os.Bundle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Properties;

public class ReaderDB
{
  String a = "";
  
  public static String byteToHexString(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0))
    {
      StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length * 2);
      int i = 0;
      while (i < paramArrayOfByte.length)
      {
        if ((paramArrayOfByte[i] & 0xFF) < 16) {
          localStringBuffer.append("0");
        }
        localStringBuffer.append(Long.toString(paramArrayOfByte[i] & 0xFF, 16));
        i += 1;
      }
      return localStringBuffer.toString();
    }
    return null;
  }
  
  public static String getMD5(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    try
    {
      paramString = paramString.getBytes();
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString);
      paramString = byteToHexString(localMessageDigest.digest());
      return paramString;
    }
    catch (Exception paramString) {}
    return null;
  }
  
  /* Error */
  public Bundle getPosition(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 76	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: istore 4
    //   6: aconst_null
    //   7: astore 6
    //   9: aconst_null
    //   10: astore 5
    //   12: iload 4
    //   14: ifeq +5 -> 19
    //   17: aconst_null
    //   18: areturn
    //   19: aload_1
    //   20: invokestatic 78	com/tencent/mtt/external/reader/base/ReaderDB:getMD5	(Ljava/lang/String;)Ljava/lang/String;
    //   23: astore_1
    //   24: new 80	java/lang/StringBuilder
    //   27: dup
    //   28: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   31: astore 7
    //   33: aload 7
    //   35: aload_1
    //   36: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   39: pop
    //   40: aload 7
    //   42: ldc 86
    //   44: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: pop
    //   48: aload 7
    //   50: invokevirtual 87	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   53: astore_1
    //   54: new 89	java/io/File
    //   57: dup
    //   58: aload_0
    //   59: getfield 14	com/tencent/mtt/external/reader/base/ReaderDB:a	Ljava/lang/String;
    //   62: aload_1
    //   63: invokespecial 92	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   66: astore 9
    //   68: aload 9
    //   70: invokevirtual 96	java/io/File:exists	()Z
    //   73: ifne +5 -> 78
    //   76: aconst_null
    //   77: areturn
    //   78: new 98	android/os/Bundle
    //   81: dup
    //   82: invokespecial 99	android/os/Bundle:<init>	()V
    //   85: astore 7
    //   87: new 101	java/util/Properties
    //   90: dup
    //   91: invokespecial 102	java/util/Properties:<init>	()V
    //   94: astore 8
    //   96: aload 5
    //   98: astore_1
    //   99: new 104	java/io/FileInputStream
    //   102: dup
    //   103: aload 9
    //   105: invokevirtual 107	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   108: invokespecial 110	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   111: astore 5
    //   113: aload 8
    //   115: aload 5
    //   117: invokevirtual 114	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   120: aload 8
    //   122: ldc 116
    //   124: invokevirtual 119	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   127: astore_1
    //   128: aload 8
    //   130: ldc 121
    //   132: invokevirtual 119	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   135: astore 6
    //   137: aload_1
    //   138: invokestatic 127	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   141: istore_2
    //   142: aload 6
    //   144: invokestatic 127	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   147: istore_3
    //   148: aload 7
    //   150: ldc -127
    //   152: iload_2
    //   153: invokevirtual 133	android/os/Bundle:putInt	(Ljava/lang/String;I)V
    //   156: aload 7
    //   158: ldc -121
    //   160: iload_3
    //   161: invokevirtual 133	android/os/Bundle:putInt	(Ljava/lang/String;I)V
    //   164: goto +8 -> 172
    //   167: astore_1
    //   168: aload_1
    //   169: invokevirtual 138	java/lang/NumberFormatException:printStackTrace	()V
    //   172: aload 5
    //   174: invokevirtual 143	java/io/InputStream:close	()V
    //   177: aload 7
    //   179: areturn
    //   180: astore_1
    //   181: goto +56 -> 237
    //   184: astore 6
    //   186: goto +22 -> 208
    //   189: astore 6
    //   191: aload_1
    //   192: astore 5
    //   194: aload 6
    //   196: astore_1
    //   197: goto +40 -> 237
    //   200: astore_1
    //   201: aload 6
    //   203: astore 5
    //   205: aload_1
    //   206: astore 6
    //   208: aload 5
    //   210: astore_1
    //   211: aload 6
    //   213: invokevirtual 144	java/io/IOException:printStackTrace	()V
    //   216: aload 5
    //   218: ifnull +16 -> 234
    //   221: aload 5
    //   223: invokevirtual 143	java/io/InputStream:close	()V
    //   226: aload 7
    //   228: areturn
    //   229: astore_1
    //   230: aload_1
    //   231: invokevirtual 145	java/lang/Exception:printStackTrace	()V
    //   234: aload 7
    //   236: areturn
    //   237: aload 5
    //   239: ifnull +18 -> 257
    //   242: aload 5
    //   244: invokevirtual 143	java/io/InputStream:close	()V
    //   247: goto +10 -> 257
    //   250: astore 5
    //   252: aload 5
    //   254: invokevirtual 145	java/lang/Exception:printStackTrace	()V
    //   257: aload_1
    //   258: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	259	0	this	ReaderDB
    //   0	259	1	paramString	String
    //   141	12	2	i	int
    //   147	14	3	j	int
    //   4	9	4	bool	boolean
    //   10	233	5	localObject1	Object
    //   250	3	5	localException	Exception
    //   7	136	6	str1	String
    //   184	1	6	localIOException	IOException
    //   189	13	6	localObject2	Object
    //   206	6	6	str2	String
    //   31	204	7	localObject3	Object
    //   94	35	8	localProperties	Properties
    //   66	38	9	localFile	File
    // Exception table:
    //   from	to	target	type
    //   137	164	167	java/lang/NumberFormatException
    //   113	137	180	finally
    //   137	164	180	finally
    //   168	172	180	finally
    //   113	137	184	java/io/IOException
    //   137	164	184	java/io/IOException
    //   168	172	184	java/io/IOException
    //   99	113	189	finally
    //   211	216	189	finally
    //   99	113	200	java/io/IOException
    //   172	177	229	java/lang/Exception
    //   221	226	229	java/lang/Exception
    //   242	247	250	java/lang/Exception
  }
  
  public void savePosition(String paramString, Bundle paramBundle)
  {
    if (paramBundle == null) {
      return;
    }
    int i = paramBundle.getInt("Position", -1);
    int j = paramBundle.getInt("Mode", 0);
    if (i < 0) {
      return;
    }
    paramString = getMD5(paramString);
    paramBundle = new StringBuilder();
    paramBundle.append(paramString);
    paramBundle.append(".dat");
    paramString = paramBundle.toString();
    paramBundle = new File(this.a, paramString);
    if (!paramBundle.exists()) {
      try
      {
        paramBundle.createNewFile();
      }
      catch (IOException paramString)
      {
        paramString.printStackTrace();
        return;
      }
    }
    try
    {
      paramString = new Properties();
      paramBundle = new FileOutputStream(paramBundle.getAbsolutePath());
      paramString.setProperty("READER_POSITION", String.valueOf(i));
      paramString.setProperty("READER_MODE", String.valueOf(j));
      paramString.save(paramBundle, "readerIdx");
      paramBundle.close();
      return;
    }
    catch (IOException paramString)
    {
      paramString.printStackTrace();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderDB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */