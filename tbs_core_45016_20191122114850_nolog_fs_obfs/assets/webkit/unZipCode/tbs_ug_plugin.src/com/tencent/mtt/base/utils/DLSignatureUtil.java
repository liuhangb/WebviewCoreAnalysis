package com.tencent.mtt.base.utils;

import android.content.pm.Signature;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DLSignatureUtil
{
  /* Error */
  private static Certificate[] a(JarFile paramJarFile, JarEntry paramJarEntry, byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aload_0
    //   4: aload_1
    //   5: invokevirtual 21	java/util/jar/JarFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   8: astore_3
    //   9: aload_3
    //   10: astore_0
    //   11: aload_3
    //   12: aload_2
    //   13: iconst_0
    //   14: aload_2
    //   15: arraylength
    //   16: invokevirtual 27	java/io/InputStream:read	([BII)I
    //   19: iconst_m1
    //   20: if_icmpeq +6 -> 26
    //   23: goto -14 -> 9
    //   26: aload_3
    //   27: astore_0
    //   28: aload_3
    //   29: invokevirtual 30	java/io/InputStream:close	()V
    //   32: aload 4
    //   34: astore_0
    //   35: aload_1
    //   36: ifnull +12 -> 48
    //   39: aload_3
    //   40: astore_0
    //   41: aload_1
    //   42: invokevirtual 36	java/util/jar/JarEntry:getCertificates	()[Ljava/security/cert/Certificate;
    //   45: astore_1
    //   46: aload_1
    //   47: astore_0
    //   48: aload_3
    //   49: ifnull +14 -> 63
    //   52: aload_3
    //   53: invokevirtual 30	java/io/InputStream:close	()V
    //   56: aload_0
    //   57: areturn
    //   58: astore_1
    //   59: aload_1
    //   60: invokevirtual 39	java/io/IOException:printStackTrace	()V
    //   63: aload_0
    //   64: areturn
    //   65: astore_2
    //   66: aload_3
    //   67: astore_1
    //   68: goto +12 -> 80
    //   71: astore_1
    //   72: aconst_null
    //   73: astore_0
    //   74: goto +30 -> 104
    //   77: astore_2
    //   78: aconst_null
    //   79: astore_1
    //   80: aload_1
    //   81: astore_0
    //   82: aload_2
    //   83: invokevirtual 40	java/lang/Exception:printStackTrace	()V
    //   86: aload_1
    //   87: ifnull +14 -> 101
    //   90: aload_1
    //   91: invokevirtual 30	java/io/InputStream:close	()V
    //   94: aconst_null
    //   95: areturn
    //   96: astore_0
    //   97: aload_0
    //   98: invokevirtual 39	java/io/IOException:printStackTrace	()V
    //   101: aconst_null
    //   102: areturn
    //   103: astore_1
    //   104: aload_0
    //   105: ifnull +15 -> 120
    //   108: aload_0
    //   109: invokevirtual 30	java/io/InputStream:close	()V
    //   112: goto +8 -> 120
    //   115: astore_0
    //   116: aload_0
    //   117: invokevirtual 39	java/io/IOException:printStackTrace	()V
    //   120: aload_1
    //   121: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	122	0	paramJarFile	JarFile
    //   0	122	1	paramJarEntry	JarEntry
    //   0	122	2	paramArrayOfByte	byte[]
    //   8	59	3	localInputStream	java.io.InputStream
    //   1	32	4	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   52	56	58	java/io/IOException
    //   11	23	65	java/lang/Exception
    //   28	32	65	java/lang/Exception
    //   41	46	65	java/lang/Exception
    //   3	9	71	finally
    //   3	9	77	java/lang/Exception
    //   90	94	96	java/io/IOException
    //   11	23	103	finally
    //   28	32	103	finally
    //   41	46	103	finally
    //   82	86	103	finally
    //   108	112	115	java/io/IOException
  }
  
  public static Signature[] collectCertificates(String paramString)
  {
    byte[] arrayOfByte = new byte[' '];
    for (;;)
    {
      int i;
      Object localObject;
      int j;
      try
      {
        JarFile localJarFile = new JarFile(paramString);
        Enumeration localEnumeration = localJarFile.entries();
        paramString = null;
        boolean bool = localEnumeration.hasMoreElements();
        i = 0;
        if (bool)
        {
          localObject = (JarEntry)localEnumeration.nextElement();
          if ((((JarEntry)localObject).isDirectory()) || (((JarEntry)localObject).getName().startsWith("META-INF/"))) {
            continue;
          }
          localObject = a(localJarFile, (JarEntry)localObject, arrayOfByte);
          if (localObject == null)
          {
            localJarFile.close();
            return null;
            if (i >= paramString.length) {
              continue;
            }
            j = 0;
            if (j >= localObject.length) {
              break label250;
            }
            if ((paramString[i] == null) || (!paramString[i].equals(localObject[j]))) {
              break label243;
            }
            j = 1;
            if (j != 0) {
              if (paramString.length == localObject.length) {
                break label255;
              }
            }
            localJarFile.close();
            return null;
          }
        }
        else
        {
          localJarFile.close();
          if ((paramString != null) && (paramString.length > 0))
          {
            j = paramString.length;
            localObject = new Signature[paramString.length];
            if (i < j)
            {
              localObject[i] = new Signature(paramString[i].getEncoded());
              i += 1;
              continue;
            }
            return (Signature[])localObject;
          }
          return null;
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
        return null;
      }
      if (paramString == null)
      {
        paramString = (String)localObject;
      }
      else
      {
        i = 0;
        continue;
        label243:
        j += 1;
        continue;
        label250:
        j = 0;
        continue;
        label255:
        i += 1;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\DLSignatureUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */