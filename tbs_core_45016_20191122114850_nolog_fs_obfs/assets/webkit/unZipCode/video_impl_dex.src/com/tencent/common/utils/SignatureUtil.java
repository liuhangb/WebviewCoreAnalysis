package com.tencent.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class SignatureUtil
{
  public static final int STATUS_CAN_INSTALL = 1;
  public static final int STATUS_ERROR_INFO = -1;
  public static final int STATUS_SIGNATURES_CONFLICT = 0;
  
  private static int a(PackageInfo paramPackageInfo, String paramString)
  {
    Signature[] arrayOfSignature = paramPackageInfo.signatures;
    Object localObject = "";
    int i = 0;
    paramPackageInfo = (PackageInfo)localObject;
    if (arrayOfSignature != null)
    {
      paramPackageInfo = (PackageInfo)localObject;
      if (arrayOfSignature.length > 0) {
        paramPackageInfo = arrayOfSignature[0].toCharsString();
      }
    }
    localObject = Md5Utils.getMD5(paramPackageInfo);
    paramPackageInfo = "";
    if (localObject != null) {
      paramPackageInfo = ((String)localObject).toLowerCase();
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("installedSinMd5 : ");
    ((StringBuilder)localObject).append(paramPackageInfo);
    FLogger.d("SignatureUtil", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("software.sSignMd5 : ");
    ((StringBuilder)localObject).append(paramString);
    FLogger.d("SignatureUtil", ((StringBuilder)localObject).toString());
    if ((TextUtils.isEmpty(paramPackageInfo)) || (paramString.toLowerCase().equals(paramPackageInfo))) {
      i = 1;
    }
    return i;
  }
  
  /* Error */
  private static Certificate[] a(JarFile paramJarFile, JarEntry paramJarEntry, byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aload_0
    //   4: aload_1
    //   5: invokevirtual 86	java/util/jar/JarFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   8: astore_3
    //   9: aload_3
    //   10: astore_0
    //   11: aload_3
    //   12: aload_2
    //   13: iconst_0
    //   14: aload_2
    //   15: arraylength
    //   16: invokevirtual 92	java/io/InputStream:read	([BII)I
    //   19: iconst_m1
    //   20: if_icmpeq +6 -> 26
    //   23: goto -14 -> 9
    //   26: aload_3
    //   27: astore_0
    //   28: aload_3
    //   29: invokevirtual 95	java/io/InputStream:close	()V
    //   32: aload 4
    //   34: astore_0
    //   35: aload_1
    //   36: ifnull +12 -> 48
    //   39: aload_3
    //   40: astore_0
    //   41: aload_1
    //   42: invokevirtual 101	java/util/jar/JarEntry:getCertificates	()[Ljava/security/cert/Certificate;
    //   45: astore_1
    //   46: aload_1
    //   47: astore_0
    //   48: aload_3
    //   49: ifnull +14 -> 63
    //   52: aload_3
    //   53: invokevirtual 95	java/io/InputStream:close	()V
    //   56: aload_0
    //   57: areturn
    //   58: astore_1
    //   59: aload_1
    //   60: invokevirtual 104	java/io/IOException:printStackTrace	()V
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
    //   83: invokevirtual 105	java/lang/Throwable:printStackTrace	()V
    //   86: aload_1
    //   87: ifnull +14 -> 101
    //   90: aload_1
    //   91: invokevirtual 95	java/io/InputStream:close	()V
    //   94: aconst_null
    //   95: areturn
    //   96: astore_0
    //   97: aload_0
    //   98: invokevirtual 104	java/io/IOException:printStackTrace	()V
    //   101: aconst_null
    //   102: areturn
    //   103: astore_1
    //   104: aload_0
    //   105: ifnull +15 -> 120
    //   108: aload_0
    //   109: invokevirtual 95	java/io/InputStream:close	()V
    //   112: goto +8 -> 120
    //   115: astore_0
    //   116: aload_0
    //   117: invokevirtual 104	java/io/IOException:printStackTrace	()V
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
    //   11	23	65	java/lang/Throwable
    //   28	32	65	java/lang/Throwable
    //   41	46	65	java/lang/Throwable
    //   3	9	71	finally
    //   3	9	77	java/lang/Throwable
    //   90	94	96	java/io/IOException
    //   11	23	103	finally
    //   28	32	103	finally
    //   41	46	103	finally
    //   82	86	103	finally
    //   108	112	115	java/io/IOException
  }
  
  public static int checkSignature(PackageInfo paramPackageInfo, Context paramContext)
  {
    if ((paramPackageInfo != null) && (paramContext != null) && (paramPackageInfo.signatures != null))
    {
      paramContext = getInstalledPKGInfo(paramPackageInfo.packageName, paramContext, 64);
      if (paramContext == null) {
        return 1;
      }
      if (isSignaturesSame(paramContext.signatures, paramPackageInfo.signatures)) {
        return 1;
      }
      return 0;
    }
    return -1;
  }
  
  public static int checkSignature(String paramString1, String paramString2, Context paramContext)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return -1;
    }
    if (TextUtils.isEmpty(paramString2)) {
      return 1;
    }
    paramString1 = getInstalledPKGInfo(paramString1, paramContext, 64);
    if (paramString1 == null) {
      return 1;
    }
    return a(paramString1, paramString2);
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
  
  public static String getHainaSignMd5(PackageInfo paramPackageInfo)
  {
    String str = "";
    Object localObject = str;
    if (paramPackageInfo != null)
    {
      paramPackageInfo = paramPackageInfo.signatures;
      localObject = str;
      if (paramPackageInfo != null)
      {
        localObject = str;
        if (paramPackageInfo.length > 0)
        {
          paramPackageInfo = ByteUtils.byteToHexString(Md5Utils.getMD5(paramPackageInfo[0].toByteArray()));
          localObject = paramPackageInfo;
          if (!TextUtils.isEmpty(paramPackageInfo)) {
            localObject = paramPackageInfo.toLowerCase();
          }
        }
      }
    }
    return (String)localObject;
  }
  
  public static PackageInfo getInstalledPKGInfo(String paramString, Context paramContext, int paramInt)
  {
    if (paramContext != null)
    {
      if (TextUtils.isEmpty(paramString)) {
        return null;
      }
      paramContext = paramContext.getPackageManager();
    }
    try
    {
      paramString = paramContext.getPackageInfo(paramString, paramInt);
      return paramString;
    }
    catch (Exception paramString) {}
    return null;
    return null;
  }
  
  public static boolean isSignaturesSame(Signature[] paramArrayOfSignature1, Signature[] paramArrayOfSignature2)
  {
    if (paramArrayOfSignature1 == null) {
      return true;
    }
    if (paramArrayOfSignature2 == null) {
      return true;
    }
    HashSet localHashSet = new HashSet();
    int k = paramArrayOfSignature1.length;
    int j = 0;
    int i = 0;
    while (i < k)
    {
      localHashSet.add(paramArrayOfSignature1[i]);
      i += 1;
    }
    paramArrayOfSignature1 = new HashSet();
    k = paramArrayOfSignature2.length;
    i = j;
    while (i < k)
    {
      paramArrayOfSignature1.add(paramArrayOfSignature2[i]);
      i += 1;
    }
    return localHashSet.equals(paramArrayOfSignature1);
  }
  
  public static String[] jarSignatureString(String paramString)
  {
    Signature[] arrayOfSignature = collectCertificates(paramString);
    if ((arrayOfSignature != null) && (arrayOfSignature.length > 0))
    {
      String[] arrayOfString = new String[arrayOfSignature.length];
      int i = 0;
      for (;;)
      {
        paramString = arrayOfString;
        if (i >= arrayOfSignature.length) {
          break;
        }
        arrayOfString[i] = arrayOfSignature[i].toCharsString();
        i += 1;
      }
    }
    paramString = null;
    return paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\SignatureUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */