package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

class ApkUtil
{
  private static Class<AssetManager> CLASS_ASSET;
  private static final String LOGTAG = "ApkUtil";
  private static Method METHOD_ADD_ASSET;
  public static final String TBS_APK_SIG = "3082023f308201a8a00302010202044c46914a300d06092a864886f70d01010505003064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f301e170d3130303732313036313835305a170d3430303731333036313835305a3064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f30819f300d06092a864886f70d010101050003818d0030818902818100c209077044bd0d63ea00ede5b839914cabcc912a87f0f8b390877e0f7a2583f0d5933443c40431c35a4433bc4c965800141961adc44c9625b1d321385221fd097e5bdc2f44a1840d643ab59dc070cf6c4b4b4d98bed5cbb8046e0a7078ae134da107cdf2bfc9b440fe5cb2f7549b44b73202cc6f7c2c55b8cfb0d333a021f01f0203010001300d06092a864886f70d010105050003818100b007db9922774ef4ccfee81ba514a8d57c410257e7a2eba64bfa17c9e690da08106d32f637ac41fbc9f205176c71bde238c872c3ee2f8313502bee44c80288ea4ef377a6f2cdfe4d3653c145c4acfedbfbadea23b559d41980cc3cdd35d79a68240693739aabf5c5ed26148756cf88264226de394c8a24ac35b712b120d4d23a";
  
  static
  {
    try
    {
      CLASS_ASSET = AssetManager.class;
      METHOD_ADD_ASSET = CLASS_ASSET.getDeclaredMethod("addAssetPath", new Class[] { String.class });
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
    }
  }
  
  private static boolean checkApkFile(String paramString)
  {
    boolean bool2 = false;
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return false;
      }
      paramString = new File(paramString);
      boolean bool1 = bool2;
      if (paramString.exists())
      {
        bool1 = bool2;
        if (paramString.isFile()) {
          bool1 = true;
        }
      }
      return bool1;
    }
    return false;
  }
  
  public static ApkInfo getApkInfo(Context paramContext, String paramString)
  {
    boolean bool = checkApkFile(paramString);
    Object localObject2 = null;
    if (!bool) {
      return null;
    }
    for (;;)
    {
      ApplicationInfo localApplicationInfo;
      try
      {
        PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageArchiveInfo(paramString, 0);
        paramString = getResources(paramContext, paramString);
        if ((localPackageInfo != null) && (paramString != null))
        {
          localApplicationInfo = localPackageInfo.applicationInfo;
          if (localApplicationInfo == null)
          {
            str = null;
            break label168;
          }
          String str = getString(paramString, localApplicationInfo.labelRes);
          break label168;
          paramString = getDrawable(paramString, localApplicationInfo.icon);
          Object localObject1 = paramString;
          if (paramString == null)
          {
            localObject1 = paramString;
            if (localApplicationInfo != null) {
              localObject1 = paramContext.getPackageManager().getApplicationIcon(localApplicationInfo);
            }
          }
          paramContext = new ApkInfo();
          try
          {
            paramContext.packageInfo = localPackageInfo;
            paramContext.packageName = localPackageInfo.packageName;
            paramContext.name = str;
            paramContext.icon = ((Drawable)localObject1);
            paramContext.version = localPackageInfo.versionCode;
            return paramContext;
          }
          catch (Throwable paramString) {}
        }
        else
        {
          return null;
        }
      }
      catch (Throwable paramString)
      {
        paramContext = (Context)localObject2;
        paramString.printStackTrace();
        return paramContext;
      }
      label168:
      if (localApplicationInfo == null) {
        paramString = null;
      }
    }
  }
  
  public static int getApkVersion(Context paramContext, File paramFile)
  {
    if ((paramFile != null) && (paramFile.exists()))
    {
      paramContext = paramContext.getPackageManager().getPackageArchiveInfo(paramFile.getAbsolutePath(), 1);
      if (paramContext != null) {
        return paramContext.versionCode;
      }
    }
    return 0;
  }
  
  public static ApplicationInfo getApplicationInfo(Context paramContext, String paramString)
  {
    return getApplicationInfo(paramContext, paramString, 0);
  }
  
  public static ApplicationInfo getApplicationInfo(Context paramContext, String paramString, int paramInt)
  {
    boolean bool = checkApkFile(paramString);
    Object localObject = null;
    if (!bool) {
      return null;
    }
    paramContext = paramContext.getPackageManager().getPackageArchiveInfo(paramString, paramInt);
    if (paramContext == null) {
      paramContext = (Context)localObject;
    } else {
      paramContext = paramContext.applicationInfo;
    }
    if (paramContext != null)
    {
      paramContext.sourceDir = paramString;
      paramContext.publicSourceDir = paramString;
    }
    return paramContext;
  }
  
  private static Drawable getDrawable(Resources paramResources, int paramInt)
  {
    try
    {
      paramResources = paramResources.getDrawable(paramInt);
      return paramResources;
    }
    catch (Resources.NotFoundException paramResources)
    {
      for (;;) {}
    }
    return null;
  }
  
  /* Error */
  public static String getMd5(File paramFile)
  {
    // Byte code:
    //   0: bipush 16
    //   2: newarray <illegal type>
    //   4: astore 6
    //   6: aload 6
    //   8: dup
    //   9: iconst_0
    //   10: ldc -83
    //   12: castore
    //   13: dup
    //   14: iconst_1
    //   15: ldc -82
    //   17: castore
    //   18: dup
    //   19: iconst_2
    //   20: ldc -81
    //   22: castore
    //   23: dup
    //   24: iconst_3
    //   25: ldc -80
    //   27: castore
    //   28: dup
    //   29: iconst_4
    //   30: ldc -79
    //   32: castore
    //   33: dup
    //   34: iconst_5
    //   35: ldc -78
    //   37: castore
    //   38: dup
    //   39: bipush 6
    //   41: ldc -77
    //   43: castore
    //   44: dup
    //   45: bipush 7
    //   47: ldc -76
    //   49: castore
    //   50: dup
    //   51: bipush 8
    //   53: ldc -75
    //   55: castore
    //   56: dup
    //   57: bipush 9
    //   59: ldc -74
    //   61: castore
    //   62: dup
    //   63: bipush 10
    //   65: ldc -73
    //   67: castore
    //   68: dup
    //   69: bipush 11
    //   71: ldc -72
    //   73: castore
    //   74: dup
    //   75: bipush 12
    //   77: ldc -71
    //   79: castore
    //   80: dup
    //   81: bipush 13
    //   83: ldc -70
    //   85: castore
    //   86: dup
    //   87: bipush 14
    //   89: ldc -69
    //   91: castore
    //   92: dup
    //   93: bipush 15
    //   95: ldc -68
    //   97: castore
    //   98: pop
    //   99: bipush 32
    //   101: newarray <illegal type>
    //   103: astore 7
    //   105: ldc -66
    //   107: invokestatic 196	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   110: astore 8
    //   112: new 198	java/io/FileInputStream
    //   115: dup
    //   116: aload_0
    //   117: invokespecial 201	java/io/FileInputStream:<init>	(Ljava/io/File;)V
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
    //   139: invokevirtual 205	java/io/FileInputStream:read	([B)I
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
    //   159: invokevirtual 209	java/security/MessageDigest:update	([BII)V
    //   162: goto -30 -> 132
    //   165: aload 5
    //   167: astore_0
    //   168: aload 8
    //   170: invokevirtual 213	java/security/MessageDigest:digest	()[B
    //   173: astore 8
    //   175: iconst_0
    //   176: istore_1
    //   177: goto +98 -> 275
    //   180: aload 5
    //   182: astore_0
    //   183: new 41	java/lang/String
    //   186: dup
    //   187: aload 7
    //   189: invokespecial 216	java/lang/String:<init>	([C)V
    //   192: astore 6
    //   194: aload 5
    //   196: invokevirtual 219	java/io/FileInputStream:close	()V
    //   199: aload 6
    //   201: areturn
    //   202: astore_0
    //   203: aload_0
    //   204: invokevirtual 220	java/io/IOException:printStackTrace	()V
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
    //   232: invokevirtual 221	java/lang/Exception:printStackTrace	()V
    //   235: aload 5
    //   237: ifnull +15 -> 252
    //   240: aload 5
    //   242: invokevirtual 219	java/io/FileInputStream:close	()V
    //   245: aconst_null
    //   246: areturn
    //   247: astore_0
    //   248: aload_0
    //   249: invokevirtual 220	java/io/IOException:printStackTrace	()V
    //   252: aconst_null
    //   253: areturn
    //   254: astore 5
    //   256: aload_0
    //   257: ifnull +15 -> 272
    //   260: aload_0
    //   261: invokevirtual 219	java/io/FileInputStream:close	()V
    //   264: goto +8 -> 272
    //   267: astore_0
    //   268: aload_0
    //   269: invokevirtual 220	java/io/IOException:printStackTrace	()V
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
  
  private static PackageInfo getPackageArchiveInfo(String paramString, int paramInt)
  {
    for (;;)
    {
      int i;
      try
      {
        Object localObject2 = Class.forName("android.content.pm.PackageParser");
        Object localObject1 = ((Class)localObject2).getDeclaredClasses();
        int j = localObject1.length;
        i = 0;
        if (i >= j) {
          break label351;
        }
        localMethod1 = localObject1[i];
        if (localMethod1.getName().compareTo("android.content.pm.PackageParser$Package") == 0)
        {
          Object localObject3 = ((Class)localObject2).getConstructor(new Class[] { String.class });
          Method localMethod2 = ((Class)localObject2).getDeclaredMethod("parsePackage", new Class[] { File.class, String.class, DisplayMetrics.class, Integer.TYPE });
          localObject1 = ((Class)localObject2).getDeclaredMethod("collectCertificates", new Class[] { localMethod1, Integer.TYPE });
          localMethod1 = ((Class)localObject2).getDeclaredMethod("generatePackageInfo", new Class[] { localMethod1, int[].class, Integer.TYPE, Long.TYPE, Long.TYPE });
          ((Constructor)localObject3).setAccessible(true);
          localMethod2.setAccessible(true);
          ((Method)localObject1).setAccessible(true);
          localMethod1.setAccessible(true);
          localObject2 = ((Constructor)localObject3).newInstance(new Object[] { paramString });
          localObject3 = new DisplayMetrics();
          ((DisplayMetrics)localObject3).setToDefaults();
          paramString = localMethod2.invoke(localObject2, new Object[] { new File(paramString), paramString, localObject3, Integer.valueOf(0) });
          if (paramString == null) {
            return null;
          }
          if ((paramInt & 0x40) != 0) {
            ((Method)localObject1).invoke(localObject2, new Object[] { paramString, Integer.valueOf(0) });
          }
          paramString = (PackageInfo)localMethod1.invoke(null, new Object[] { paramString, null, Integer.valueOf(paramInt), Integer.valueOf(0), Integer.valueOf(0) });
          return paramString;
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
        return null;
      }
      i += 1;
      continue;
      label351:
      Method localMethod1 = null;
    }
  }
  
  public static PackageInfo getPackageInfo(Context paramContext, String paramString)
  {
    return getPackageInfo(paramContext, paramString, 0);
  }
  
  public static PackageInfo getPackageInfo(Context paramContext, String paramString, int paramInt)
  {
    if (!checkApkFile(paramString)) {
      return null;
    }
    paramContext = paramContext.getPackageManager().getPackageArchiveInfo(paramString, paramInt);
    if (paramContext == null) {
      return null;
    }
    if (((paramInt & 0x40) != 0) && (paramContext.signatures == null)) {
      paramContext.signatures = Certificates.collectCertificates(paramString);
    }
    return paramContext;
  }
  
  public static Resources getResources(Context paramContext, String paramString)
  {
    boolean bool = checkApkFile(paramString);
    Object localObject3 = null;
    if (!bool) {
      return null;
    }
    Object localObject1 = paramContext.getPackageManager().getPackageArchiveInfo(paramString, 0);
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = ((PackageInfo)localObject1).applicationInfo;
    }
    Object localObject2 = localObject3;
    if (localObject1 != null)
    {
      ((ApplicationInfo)localObject1).sourceDir = paramString;
      ((ApplicationInfo)localObject1).publicSourceDir = paramString;
      try
      {
        localObject2 = paramContext.getPackageManager().getResourcesForApplication((ApplicationInfo)localObject1);
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
        localObject2 = localObject3;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        localNameNotFoundException.printStackTrace();
        localObject2 = localObject3;
      }
    }
    if (localObject2 != null) {
      return (Resources)localObject2;
    }
    return getResourcesWithReflect(paramContext, paramString);
  }
  
  private static Resources getResourcesWithReflect(Context paramContext, String paramString)
  {
    if (CLASS_ASSET != null)
    {
      if (METHOD_ADD_ASSET == null) {
        return null;
      }
      if (!checkApkFile(paramString)) {
        return null;
      }
      try
      {
        AssetManager localAssetManager = (AssetManager)CLASS_ASSET.newInstance();
        METHOD_ADD_ASSET.invoke(localAssetManager, new Object[] { paramString });
        paramContext = new Resources(localAssetManager, paramContext.getResources().getDisplayMetrics(), paramContext.getResources().getConfiguration());
        return paramContext;
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
        return null;
      }
      catch (IllegalAccessException paramContext)
      {
        paramContext.printStackTrace();
        return null;
      }
      catch (InstantiationException paramContext)
      {
        paramContext.printStackTrace();
        return null;
      }
      catch (InvocationTargetException paramContext)
      {
        paramContext.printStackTrace();
        return null;
      }
    }
    return null;
  }
  
  public static String getSignatureFromApk(Context paramContext, File paramFile)
  {
    if (!com.tencent.tbs.common.utils.ApkUtil.isRealApkFile(paramFile)) {
      return "";
    }
    try
    {
      if (paramContext.getApplicationContext().getPackageName().contains("com.jd.jrapp"))
      {
        localObject1 = getSignatureFromApk(paramFile);
        if (localObject1 != null) {
          return (String)localObject1;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject1;
      Object localObject2;
      for (;;) {}
    }
    localObject2 = getSignatureFromApk(paramContext, paramFile, false);
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("[ApkUtil.getSignatureFromApk]  android api signature=");
    ((StringBuilder)localObject1).append((String)localObject2);
    ((StringBuilder)localObject1).toString();
    localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject1 = getSignatureFromApk(paramFile);
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("[ApkUtil.getSignatureFromApk]  java get signature=");
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).toString();
    }
    localObject2 = localObject1;
    if (localObject1 == null)
    {
      localObject2 = getSignatureFromApk(paramContext, paramFile, true);
      paramContext = new StringBuilder();
      paramContext.append("[ApkUtil.getSignatureFromApk]  android reflection signature=");
      paramContext.append((String)localObject2);
      paramContext.toString();
    }
    return (String)localObject2;
  }
  
  private static String getSignatureFromApk(Context paramContext, File paramFile, boolean paramBoolean)
  {
    if (paramBoolean) {
      paramContext = getPackageArchiveInfo(paramFile.getAbsolutePath(), 65);
    } else {
      paramContext = paramContext.getPackageManager().getPackageArchiveInfo(paramFile.getAbsolutePath(), 65);
    }
    paramFile = null;
    if (paramContext != null)
    {
      if ((paramContext.signatures != null) && (paramContext.signatures.length > 0)) {
        paramContext = paramContext.signatures[0];
      } else {
        Log.w("ApkUtil", "[getSignatureFromApk] pkgInfo is not null BUT signatures is null!");
      }
    }
    else {
      paramContext = null;
    }
    if (paramContext != null) {
      paramFile = paramContext.toCharsString();
    }
    return paramFile;
  }
  
  /* Error */
  private static String getSignatureFromApk(File paramFile)
  {
    // Byte code:
    //   0: new 365	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 366	java/lang/StringBuilder:<init>	()V
    //   7: astore_2
    //   8: aload_2
    //   9: ldc_w 395
    //   12: invokevirtual 372	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   15: pop
    //   16: aload_2
    //   17: aload_0
    //   18: invokevirtual 398	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   21: pop
    //   22: aload_2
    //   23: invokevirtual 375	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   26: pop
    //   27: aconst_null
    //   28: astore 4
    //   30: aconst_null
    //   31: astore 5
    //   33: new 400	java/util/jar/JarFile
    //   36: dup
    //   37: aload_0
    //   38: invokespecial 401	java/util/jar/JarFile:<init>	(Ljava/io/File;)V
    //   41: astore_2
    //   42: aload_2
    //   43: astore_0
    //   44: aload_2
    //   45: ldc_w 403
    //   48: invokevirtual 407	java/util/jar/JarFile:getJarEntry	(Ljava/lang/String;)Ljava/util/jar/JarEntry;
    //   51: astore_3
    //   52: aload_2
    //   53: astore_0
    //   54: sipush 8192
    //   57: newarray <illegal type>
    //   59: astore 7
    //   61: aload_2
    //   62: astore_0
    //   63: aload_2
    //   64: aload_3
    //   65: aload 7
    //   67: invokestatic 411	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/ApkUtil:loadCertificates	(Ljava/util/jar/JarFile;Ljava/util/jar/JarEntry;[B)[Ljava/security/cert/Certificate;
    //   70: astore_3
    //   71: aload_3
    //   72: ifnull +235 -> 307
    //   75: aload_3
    //   76: iconst_0
    //   77: aaload
    //   78: ifnonnull +6 -> 84
    //   81: goto +226 -> 307
    //   84: aload_2
    //   85: astore_0
    //   86: aload_3
    //   87: iconst_0
    //   88: aaload
    //   89: invokevirtual 416	java/security/cert/Certificate:getEncoded	()[B
    //   92: invokestatic 419	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/ApkUtil:toCharsString	([B)Ljava/lang/String;
    //   95: astore 6
    //   97: aload_2
    //   98: astore_0
    //   99: aload_2
    //   100: invokevirtual 423	java/util/jar/JarFile:entries	()Ljava/util/Enumeration;
    //   103: astore 8
    //   105: aload_2
    //   106: astore_0
    //   107: aload 8
    //   109: invokeinterface 428 1 0
    //   114: ifeq +175 -> 289
    //   117: aload_2
    //   118: astore_0
    //   119: aload 8
    //   121: invokeinterface 431 1 0
    //   126: checkcast 433	java/util/jar/JarEntry
    //   129: astore_3
    //   130: aload_2
    //   131: astore_0
    //   132: aload_3
    //   133: invokevirtual 434	java/util/jar/JarEntry:getName	()Ljava/lang/String;
    //   136: astore 9
    //   138: aload 9
    //   140: ifnull -35 -> 105
    //   143: aload_2
    //   144: astore_0
    //   145: new 365	java/lang/StringBuilder
    //   148: dup
    //   149: invokespecial 366	java/lang/StringBuilder:<init>	()V
    //   152: astore 10
    //   154: aload_2
    //   155: astore_0
    //   156: aload 10
    //   158: ldc_w 436
    //   161: invokevirtual 372	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   164: pop
    //   165: aload_2
    //   166: astore_0
    //   167: aload 10
    //   169: aload 9
    //   171: invokevirtual 372	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   174: pop
    //   175: aload_2
    //   176: astore_0
    //   177: aload 10
    //   179: invokevirtual 375	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   182: pop
    //   183: aload_2
    //   184: astore_0
    //   185: aload_2
    //   186: aload_3
    //   187: aload 7
    //   189: invokestatic 411	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/ApkUtil:loadCertificates	(Ljava/util/jar/JarFile;Ljava/util/jar/JarEntry;[B)[Ljava/security/cert/Certificate;
    //   192: astore_3
    //   193: aload_3
    //   194: ifnull +177 -> 371
    //   197: aload_2
    //   198: astore_0
    //   199: aload_3
    //   200: iconst_0
    //   201: aaload
    //   202: invokevirtual 416	java/security/cert/Certificate:getEncoded	()[B
    //   205: invokestatic 419	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/ApkUtil:toCharsString	([B)Ljava/lang/String;
    //   208: astore_3
    //   209: goto +3 -> 212
    //   212: aload_3
    //   213: ifnonnull +22 -> 235
    //   216: aload_2
    //   217: astore_0
    //   218: aload 9
    //   220: ldc_w 438
    //   223: invokevirtual 441	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   226: ifne -121 -> 105
    //   229: aload 5
    //   231: astore_0
    //   232: goto +60 -> 292
    //   235: aload_2
    //   236: astore_0
    //   237: aload_3
    //   238: aload 6
    //   240: invokevirtual 445	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   243: istore_1
    //   244: aload_2
    //   245: astore_0
    //   246: new 365	java/lang/StringBuilder
    //   249: dup
    //   250: invokespecial 366	java/lang/StringBuilder:<init>	()V
    //   253: astore_3
    //   254: aload_2
    //   255: astore_0
    //   256: aload_3
    //   257: ldc_w 447
    //   260: invokevirtual 372	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   263: pop
    //   264: aload_2
    //   265: astore_0
    //   266: aload_3
    //   267: iload_1
    //   268: invokevirtual 450	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   271: pop
    //   272: aload_2
    //   273: astore_0
    //   274: aload_3
    //   275: invokevirtual 375	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   278: pop
    //   279: iload_1
    //   280: ifne -175 -> 105
    //   283: aload 5
    //   285: astore_0
    //   286: goto +6 -> 292
    //   289: aload 6
    //   291: astore_0
    //   292: aload_0
    //   293: astore_3
    //   294: aload_2
    //   295: invokevirtual 451	java/util/jar/JarFile:close	()V
    //   298: aload_0
    //   299: areturn
    //   300: astore_0
    //   301: aload_0
    //   302: invokevirtual 220	java/io/IOException:printStackTrace	()V
    //   305: aload_3
    //   306: areturn
    //   307: aload_2
    //   308: invokevirtual 451	java/util/jar/JarFile:close	()V
    //   311: aconst_null
    //   312: areturn
    //   313: astore_0
    //   314: aload_0
    //   315: invokevirtual 220	java/io/IOException:printStackTrace	()V
    //   318: aconst_null
    //   319: areturn
    //   320: astore_3
    //   321: goto +12 -> 333
    //   324: astore_2
    //   325: aconst_null
    //   326: astore_0
    //   327: goto +26 -> 353
    //   330: astore_3
    //   331: aconst_null
    //   332: astore_2
    //   333: aload_2
    //   334: astore_0
    //   335: aload_3
    //   336: invokevirtual 221	java/lang/Exception:printStackTrace	()V
    //   339: aload_2
    //   340: ifnull +10 -> 350
    //   343: aload 4
    //   345: astore_3
    //   346: aload_2
    //   347: invokevirtual 451	java/util/jar/JarFile:close	()V
    //   350: aconst_null
    //   351: areturn
    //   352: astore_2
    //   353: aload_0
    //   354: ifnull +15 -> 369
    //   357: aload_0
    //   358: invokevirtual 451	java/util/jar/JarFile:close	()V
    //   361: goto +8 -> 369
    //   364: astore_0
    //   365: aload_0
    //   366: invokevirtual 220	java/io/IOException:printStackTrace	()V
    //   369: aload_2
    //   370: athrow
    //   371: aconst_null
    //   372: astore_3
    //   373: goto -161 -> 212
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	376	0	paramFile	File
    //   243	37	1	bool	boolean
    //   7	301	2	localObject1	Object
    //   324	1	2	localObject2	Object
    //   332	15	2	localObject3	Object
    //   352	18	2	localObject4	Object
    //   51	255	3	localObject5	Object
    //   320	1	3	localException1	Exception
    //   330	6	3	localException2	Exception
    //   345	28	3	localObject6	Object
    //   28	316	4	localObject7	Object
    //   31	253	5	localObject8	Object
    //   95	195	6	str1	String
    //   59	129	7	arrayOfByte	byte[]
    //   103	17	8	localEnumeration	Enumeration
    //   136	83	9	str2	String
    //   152	26	10	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   294	298	300	java/io/IOException
    //   346	350	300	java/io/IOException
    //   307	311	313	java/io/IOException
    //   44	52	320	java/lang/Exception
    //   54	61	320	java/lang/Exception
    //   63	71	320	java/lang/Exception
    //   86	97	320	java/lang/Exception
    //   99	105	320	java/lang/Exception
    //   107	117	320	java/lang/Exception
    //   119	130	320	java/lang/Exception
    //   132	138	320	java/lang/Exception
    //   145	154	320	java/lang/Exception
    //   156	165	320	java/lang/Exception
    //   167	175	320	java/lang/Exception
    //   177	183	320	java/lang/Exception
    //   185	193	320	java/lang/Exception
    //   199	209	320	java/lang/Exception
    //   218	229	320	java/lang/Exception
    //   237	244	320	java/lang/Exception
    //   246	254	320	java/lang/Exception
    //   256	264	320	java/lang/Exception
    //   266	272	320	java/lang/Exception
    //   274	279	320	java/lang/Exception
    //   33	42	324	finally
    //   33	42	330	java/lang/Exception
    //   44	52	352	finally
    //   54	61	352	finally
    //   63	71	352	finally
    //   86	97	352	finally
    //   99	105	352	finally
    //   107	117	352	finally
    //   119	130	352	finally
    //   132	138	352	finally
    //   145	154	352	finally
    //   156	165	352	finally
    //   167	175	352	finally
    //   177	183	352	finally
    //   185	193	352	finally
    //   199	209	352	finally
    //   218	229	352	finally
    //   237	244	352	finally
    //   246	254	352	finally
    //   256	264	352	finally
    //   266	272	352	finally
    //   274	279	352	finally
    //   335	339	352	finally
    //   357	361	364	java/io/IOException
  }
  
  private static String getString(Resources paramResources, int paramInt)
  {
    try
    {
      paramResources = paramResources.getString(paramInt);
      return paramResources;
    }
    catch (Resources.NotFoundException paramResources)
    {
      for (;;) {}
    }
    return null;
  }
  
  private static Certificate[] loadCertificates(JarFile paramJarFile, JarEntry paramJarEntry, byte[] paramArrayOfByte)
    throws Exception
  {
    paramJarFile = paramJarFile.getInputStream(paramJarEntry);
    while (paramJarFile.read(paramArrayOfByte, 0, paramArrayOfByte.length) != -1) {}
    paramJarFile.close();
    if (paramJarEntry != null) {
      return paramJarEntry.getCertificates();
    }
    return null;
  }
  
  private static String toCharsString(byte[] paramArrayOfByte)
  {
    int k = paramArrayOfByte.length;
    char[] arrayOfChar = new char[k * 2];
    int i = 0;
    while (i < k)
    {
      int n = paramArrayOfByte[i];
      int j = n >> 4 & 0xF;
      int m = i * 2;
      if (j >= 10) {
        j = j + 97 - 10;
      } else {
        j += 48;
      }
      arrayOfChar[m] = ((char)j);
      j = n & 0xF;
      if (j >= 10) {
        j = j + 97 - 10;
      } else {
        j += 48;
      }
      arrayOfChar[(m + 1)] = ((char)j);
      i += 1;
    }
    return new String(arrayOfChar);
  }
  
  public static boolean verifyTbsApk(Context paramContext, File paramFile, long paramLong, int paramInt)
  {
    if (paramFile != null)
    {
      if (!paramFile.exists()) {
        return false;
      }
      if ((paramLong > 0L) && (paramLong != paramFile.length())) {
        return false;
      }
    }
    try
    {
      if (paramInt != getApkVersion(paramContext, paramFile)) {
        return false;
      }
      boolean bool = "3082023f308201a8a00302010202044c46914a300d06092a864886f70d01010505003064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f301e170d3130303732313036313835305a170d3430303731333036313835305a3064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f30819f300d06092a864886f70d010101050003818d0030818902818100c209077044bd0d63ea00ede5b839914cabcc912a87f0f8b390877e0f7a2583f0d5933443c40431c35a4433bc4c965800141961adc44c9625b1d321385221fd097e5bdc2f44a1840d643ab59dc070cf6c4b4b4d98bed5cbb8046e0a7078ae134da107cdf2bfc9b440fe5cb2f7549b44b73202cc6f7c2c55b8cfb0d333a021f01f0203010001300d06092a864886f70d010105050003818100b007db9922774ef4ccfee81ba514a8d57c410257e7a2eba64bfa17c9e690da08106d32f637ac41fbc9f205176c71bde238c872c3ee2f8313502bee44c80288ea4ef377a6f2cdfe4d3653c145c4acfedbfbadea23b559d41980cc3cdd35d79a68240693739aabf5c5ed26148756cf88264226de394c8a24ac35b712b120d4d23a".equals(getSignatureFromApk(paramContext, paramFile));
      return bool;
    }
    catch (Exception paramContext) {}
    return false;
    return false;
  }
  
  public static class ApkInfo
  {
    public Drawable icon;
    public String name;
    public PackageInfo packageInfo;
    public String packageName;
    public float version;
  }
  
  public static class Certificates
  {
    private static final String ANDROID_DEX_FILENAME = "classes.dex";
    private static final String ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml";
    private static final boolean DEBUG_JAR = false;
    private static final String[] IMPORTANT_ENTRY = { "AndroidManifest.xml", "classes.dex" };
    private static final String[] MANIFEST_ENTRY = { "AndroidManifest.xml" };
    private static final String TAG = "Certificates";
    private static WeakReference<byte[]> mReadBuffer;
    private static final Object mSync = new Object();
    
    public static Signature[] collectCertificates(String paramString)
    {
      return collectCertificates(paramString, false);
    }
    
    public static Signature[] collectCertificates(String paramString, boolean paramBoolean)
    {
      String[] arrayOfString;
      if (paramBoolean) {
        arrayOfString = IMPORTANT_ENTRY;
      } else {
        arrayOfString = null;
      }
      return collectCertificates(paramString, arrayOfString);
    }
    
    public static Signature[] collectCertificates(String paramString, String... paramVarArgs)
    {
      if (!isArchiveValid(paramString)) {
        return null;
      }
      for (;;)
      {
        int i;
        int j;
        synchronized (mSync)
        {
          WeakReference localWeakReference = mReadBuffer;
          if (localWeakReference != null)
          {
            mReadBuffer = null;
            ??? = (byte[])localWeakReference.get();
            Object localObject2 = ???;
            if (??? == null)
            {
              localObject2 = new byte[' '];
              localWeakReference = new WeakReference(localObject2);
            }
            try
            {
              ??? = new JarFile(paramString);
              Enumeration localEnumeration = createJarEntries((JarFile)???, paramVarArgs);
              paramVarArgs = null;
              boolean bool = localEnumeration.hasMoreElements();
              i = 0;
              if (bool)
              {
                JarEntry localJarEntry = (JarEntry)localEnumeration.nextElement();
                if ((localJarEntry == null) || (localJarEntry.isDirectory()) || (localJarEntry.getName().startsWith("META-INF/"))) {
                  continue;
                }
                ??? = loadCertificates((JarFile)???, localJarEntry, (byte[])localObject2);
                if (??? != null) {
                  break label595;
                }
                paramVarArgs = new StringBuilder();
                paramVarArgs.append("File ");
                paramVarArgs.append(paramString);
                paramVarArgs.append(" has no certificates at entry ");
                paramVarArgs.append(localJarEntry.getName());
                paramVarArgs.append("; ignoring!");
                Log.e("Certificates", paramVarArgs.toString());
                ((JarFile)???).close();
                return null;
                if (i >= paramVarArgs.length) {
                  continue;
                }
                j = 0;
                if (j >= ???.length) {
                  break label617;
                }
                if ((paramVarArgs[i] == null) || (!paramVarArgs[i].equals(???[j]))) {
                  break label610;
                }
                j = 1;
                if (j != 0) {
                  if (paramVarArgs.length == ???.length) {
                    break label622;
                  }
                }
                paramVarArgs = new StringBuilder();
                paramVarArgs.append("File ");
                paramVarArgs.append(paramString);
                paramVarArgs.append(" has mismatched certificates at entry ");
                paramVarArgs.append(localJarEntry.getName());
                paramVarArgs.append("; ignoring!");
                Log.e("Certificates", paramVarArgs.toString());
                ((JarFile)???).close();
                return null;
              }
              ((JarFile)???).close();
              synchronized (mSync)
              {
                mReadBuffer = localWeakReference;
                if ((paramVarArgs != null) && (paramVarArgs.length > 0))
                {
                  j = paramVarArgs.length;
                  ??? = new Signature[paramVarArgs.length];
                  if (i >= j) {
                    break;
                  }
                  ???[i] = new Signature(paramVarArgs[i].getEncoded());
                  i += 1;
                  continue;
                }
                paramVarArgs = new StringBuilder();
                paramVarArgs.append("File ");
                paramVarArgs.append(paramString);
                paramVarArgs.append(" has no certificates; ignoring!");
                Log.e("Certificates", paramVarArgs.toString());
                return null;
              }
              paramString = finally;
            }
            catch (RuntimeException paramVarArgs)
            {
              ??? = new StringBuilder();
              ((StringBuilder)???).append("Exception reading ");
              ((StringBuilder)???).append(paramString);
              Log.w("Certificates", ((StringBuilder)???).toString(), paramVarArgs);
              return null;
            }
            catch (IOException paramVarArgs)
            {
              ??? = new StringBuilder();
              ((StringBuilder)???).append("Exception reading ");
              ((StringBuilder)???).append(paramString);
              Log.w("Certificates", ((StringBuilder)???).toString(), paramVarArgs);
              return null;
            }
            catch (CertificateEncodingException paramVarArgs)
            {
              ??? = new StringBuilder();
              ((StringBuilder)???).append("Exception reading ");
              ((StringBuilder)???).append(paramString);
              Log.w("Certificates", ((StringBuilder)???).toString(), paramVarArgs);
              return null;
            }
          }
        }
        ??? = null;
        continue;
        label595:
        if (paramVarArgs == null)
        {
          paramVarArgs = (String[])???;
        }
        else
        {
          i = 0;
          continue;
          label610:
          j += 1;
          continue;
          label617:
          j = 0;
          continue;
          label622:
          i += 1;
        }
      }
      return (Signature[])???;
    }
    
    private static Enumeration<JarEntry> createJarEntries(JarFile paramJarFile, String... paramVarArgs)
    {
      if ((paramVarArgs != null) && (paramVarArgs.length != 0)) {
        return new JarFileEnumerator(paramJarFile, paramVarArgs);
      }
      return paramJarFile.entries();
    }
    
    private static boolean isArchiveValid(String paramString)
    {
      boolean bool2 = false;
      if (paramString != null)
      {
        if (paramString.length() == 0) {
          return false;
        }
        paramString = new File(paramString);
        boolean bool1 = bool2;
        if (paramString.exists())
        {
          bool1 = bool2;
          if (paramString.isFile()) {
            bool1 = true;
          }
        }
        return bool1;
      }
      return false;
    }
    
    /* Error */
    private static Certificate[] loadCertificates(JarFile paramJarFile, JarEntry paramJarEntry, byte[] paramArrayOfByte)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore 5
      //   3: new 187	java/io/BufferedInputStream
      //   6: dup
      //   7: aload_0
      //   8: aload_1
      //   9: invokevirtual 191	java/util/jar/JarFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
      //   12: invokespecial 194	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
      //   15: astore 4
      //   17: aload 4
      //   19: astore_3
      //   20: aload 4
      //   22: aload_2
      //   23: iconst_0
      //   24: aload_2
      //   25: arraylength
      //   26: invokevirtual 200	java/io/InputStream:read	([BII)I
      //   29: iconst_m1
      //   30: if_icmpeq +6 -> 36
      //   33: goto -16 -> 17
      //   36: aload 5
      //   38: astore_2
      //   39: aload_1
      //   40: ifnull +11 -> 51
      //   43: aload 4
      //   45: astore_3
      //   46: aload_1
      //   47: invokevirtual 204	java/util/jar/JarEntry:getCertificates	()[Ljava/security/cert/Certificate;
      //   50: astore_2
      //   51: aload 4
      //   53: invokevirtual 205	java/io/InputStream:close	()V
      //   56: aload_2
      //   57: areturn
      //   58: astore_3
      //   59: new 113	java/lang/StringBuilder
      //   62: dup
      //   63: invokespecial 114	java/lang/StringBuilder:<init>	()V
      //   66: astore 4
      //   68: aload 4
      //   70: ldc -49
      //   72: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   75: pop
      //   76: aload 4
      //   78: aload_1
      //   79: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   82: pop
      //   83: aload 4
      //   85: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   88: ifnonnull +9 -> 97
      //   91: ldc -44
      //   93: astore_0
      //   94: goto +46 -> 140
      //   97: new 113	java/lang/StringBuilder
      //   100: dup
      //   101: invokespecial 114	java/lang/StringBuilder:<init>	()V
      //   104: astore 4
      //   106: aload 4
      //   108: aload_1
      //   109: invokevirtual 102	java/util/jar/JarEntry:getName	()Ljava/lang/String;
      //   112: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   115: pop
      //   116: aload 4
      //   118: ldc -42
      //   120: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   123: pop
      //   124: aload 4
      //   126: aload_0
      //   127: invokevirtual 215	java/util/jar/JarFile:getName	()Ljava/lang/String;
      //   130: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   133: pop
      //   134: aload 4
      //   136: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   139: astore_0
      //   140: ldc 25
      //   142: aload_0
      //   143: aload_3
      //   144: invokestatic 161	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   147: pop
      //   148: aload_2
      //   149: areturn
      //   150: astore_2
      //   151: goto +17 -> 168
      //   154: astore_2
      //   155: goto +149 -> 304
      //   158: astore_2
      //   159: aconst_null
      //   160: astore_3
      //   161: goto +318 -> 479
      //   164: astore_2
      //   165: aconst_null
      //   166: astore 4
      //   168: aload 4
      //   170: astore_3
      //   171: new 113	java/lang/StringBuilder
      //   174: dup
      //   175: invokespecial 114	java/lang/StringBuilder:<init>	()V
      //   178: astore 5
      //   180: aload 4
      //   182: astore_3
      //   183: aload 5
      //   185: ldc -99
      //   187: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   190: pop
      //   191: aload 4
      //   193: astore_3
      //   194: aload 5
      //   196: aload_1
      //   197: invokevirtual 102	java/util/jar/JarEntry:getName	()Ljava/lang/String;
      //   200: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   203: pop
      //   204: aload 4
      //   206: astore_3
      //   207: aload 5
      //   209: ldc -42
      //   211: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   214: pop
      //   215: aload 4
      //   217: astore_3
      //   218: aload 5
      //   220: aload_0
      //   221: invokevirtual 215	java/util/jar/JarFile:getName	()Ljava/lang/String;
      //   224: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   227: pop
      //   228: aload 4
      //   230: astore_3
      //   231: ldc 25
      //   233: aload 5
      //   235: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   238: aload_2
      //   239: invokestatic 161	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   242: pop
      //   243: aload 4
      //   245: ifnull +231 -> 476
      //   248: aload 4
      //   250: invokevirtual 205	java/io/InputStream:close	()V
      //   253: aconst_null
      //   254: areturn
      //   255: astore_2
      //   256: new 113	java/lang/StringBuilder
      //   259: dup
      //   260: invokespecial 114	java/lang/StringBuilder:<init>	()V
      //   263: astore_3
      //   264: aload_3
      //   265: ldc -49
      //   267: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   270: pop
      //   271: aload_3
      //   272: aload_1
      //   273: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   276: pop
      //   277: aload_3
      //   278: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   281: ifnonnull +8 -> 289
      //   284: aload_2
      //   285: astore_0
      //   286: goto +136 -> 422
      //   289: new 113	java/lang/StringBuilder
      //   292: dup
      //   293: invokespecial 114	java/lang/StringBuilder:<init>	()V
      //   296: astore_3
      //   297: goto +139 -> 436
      //   300: astore_2
      //   301: aconst_null
      //   302: astore 4
      //   304: aload 4
      //   306: astore_3
      //   307: new 113	java/lang/StringBuilder
      //   310: dup
      //   311: invokespecial 114	java/lang/StringBuilder:<init>	()V
      //   314: astore 5
      //   316: aload 4
      //   318: astore_3
      //   319: aload 5
      //   321: ldc -99
      //   323: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   326: pop
      //   327: aload 4
      //   329: astore_3
      //   330: aload 5
      //   332: aload_1
      //   333: invokevirtual 102	java/util/jar/JarEntry:getName	()Ljava/lang/String;
      //   336: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   339: pop
      //   340: aload 4
      //   342: astore_3
      //   343: aload 5
      //   345: ldc -42
      //   347: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   350: pop
      //   351: aload 4
      //   353: astore_3
      //   354: aload 5
      //   356: aload_0
      //   357: invokevirtual 215	java/util/jar/JarFile:getName	()Ljava/lang/String;
      //   360: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   363: pop
      //   364: aload 4
      //   366: astore_3
      //   367: ldc 25
      //   369: aload 5
      //   371: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   374: aload_2
      //   375: invokestatic 161	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   378: pop
      //   379: aload 4
      //   381: ifnull +95 -> 476
      //   384: aload 4
      //   386: invokevirtual 205	java/io/InputStream:close	()V
      //   389: aconst_null
      //   390: areturn
      //   391: astore_2
      //   392: new 113	java/lang/StringBuilder
      //   395: dup
      //   396: invokespecial 114	java/lang/StringBuilder:<init>	()V
      //   399: astore_3
      //   400: aload_3
      //   401: ldc -49
      //   403: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   406: pop
      //   407: aload_3
      //   408: aload_1
      //   409: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   412: pop
      //   413: aload_3
      //   414: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   417: ifnonnull +11 -> 428
      //   420: aload_2
      //   421: astore_0
      //   422: ldc -44
      //   424: astore_1
      //   425: goto +43 -> 468
      //   428: new 113	java/lang/StringBuilder
      //   431: dup
      //   432: invokespecial 114	java/lang/StringBuilder:<init>	()V
      //   435: astore_3
      //   436: aload_3
      //   437: aload_1
      //   438: invokevirtual 102	java/util/jar/JarEntry:getName	()Ljava/lang/String;
      //   441: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   444: pop
      //   445: aload_3
      //   446: ldc -42
      //   448: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   451: pop
      //   452: aload_3
      //   453: aload_0
      //   454: invokevirtual 215	java/util/jar/JarFile:getName	()Ljava/lang/String;
      //   457: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   460: pop
      //   461: aload_3
      //   462: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   465: astore_1
      //   466: aload_2
      //   467: astore_0
      //   468: ldc 25
      //   470: aload_1
      //   471: aload_0
      //   472: invokestatic 161	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   475: pop
      //   476: aconst_null
      //   477: areturn
      //   478: astore_2
      //   479: aload_3
      //   480: ifnull +100 -> 580
      //   483: aload_3
      //   484: invokevirtual 205	java/io/InputStream:close	()V
      //   487: goto +93 -> 580
      //   490: astore_3
      //   491: new 113	java/lang/StringBuilder
      //   494: dup
      //   495: invokespecial 114	java/lang/StringBuilder:<init>	()V
      //   498: astore 4
      //   500: aload 4
      //   502: ldc -49
      //   504: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   507: pop
      //   508: aload 4
      //   510: aload_1
      //   511: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   514: pop
      //   515: aload 4
      //   517: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   520: ifnonnull +9 -> 529
      //   523: ldc -44
      //   525: astore_0
      //   526: goto +46 -> 572
      //   529: new 113	java/lang/StringBuilder
      //   532: dup
      //   533: invokespecial 114	java/lang/StringBuilder:<init>	()V
      //   536: astore 4
      //   538: aload 4
      //   540: aload_1
      //   541: invokevirtual 102	java/util/jar/JarEntry:getName	()Ljava/lang/String;
      //   544: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   547: pop
      //   548: aload 4
      //   550: ldc -42
      //   552: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   555: pop
      //   556: aload 4
      //   558: aload_0
      //   559: invokevirtual 215	java/util/jar/JarFile:getName	()Ljava/lang/String;
      //   562: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   565: pop
      //   566: aload 4
      //   568: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   571: astore_0
      //   572: ldc 25
      //   574: aload_0
      //   575: aload_3
      //   576: invokestatic 161	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   579: pop
      //   580: aload_2
      //   581: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	582	0	paramJarFile	JarFile
      //   0	582	1	paramJarEntry	JarEntry
      //   0	582	2	paramArrayOfByte	byte[]
      //   19	27	3	localObject1	Object
      //   58	86	3	localException1	Exception
      //   160	324	3	localObject2	Object
      //   490	86	3	localException2	Exception
      //   15	552	4	localObject3	Object
      //   1	369	5	localStringBuilder	StringBuilder
      // Exception table:
      //   from	to	target	type
      //   51	56	58	java/lang/Exception
      //   20	33	150	java/lang/RuntimeException
      //   46	51	150	java/lang/RuntimeException
      //   20	33	154	java/io/IOException
      //   46	51	154	java/io/IOException
      //   3	17	158	finally
      //   3	17	164	java/lang/RuntimeException
      //   248	253	255	java/lang/Exception
      //   3	17	300	java/io/IOException
      //   384	389	391	java/lang/Exception
      //   20	33	478	finally
      //   46	51	478	finally
      //   171	180	478	finally
      //   183	191	478	finally
      //   194	204	478	finally
      //   207	215	478	finally
      //   218	228	478	finally
      //   231	243	478	finally
      //   307	316	478	finally
      //   319	327	478	finally
      //   330	340	478	finally
      //   343	351	478	finally
      //   354	364	478	finally
      //   367	379	478	finally
      //   483	487	490	java/lang/Exception
    }
    
    static class JarFileEnumerator
      implements Enumeration<JarEntry>
    {
      private final String[] entryNames;
      private int index = 0;
      private final JarFile jarFile;
      
      public JarFileEnumerator(JarFile paramJarFile, String... paramVarArgs)
      {
        this.jarFile = paramJarFile;
        this.entryNames = paramVarArgs;
      }
      
      public boolean hasMoreElements()
      {
        return this.index < this.entryNames.length;
      }
      
      public JarEntry nextElement()
      {
        JarFile localJarFile = this.jarFile;
        String[] arrayOfString = this.entryNames;
        int i = this.index;
        this.index = (i + 1);
        return localJarFile.getJarEntry(arrayOfString[i]);
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\ApkUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */