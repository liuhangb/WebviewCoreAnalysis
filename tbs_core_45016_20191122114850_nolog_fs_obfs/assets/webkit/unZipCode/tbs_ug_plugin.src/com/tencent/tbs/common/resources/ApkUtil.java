package com.tencent.tbs.common.resources;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ApkUtil
{
  private static final String ATLAS_RES_CLASS = "android.taobao.atlas.runtime.DelegateResources";
  private static Class<AssetManager> CLASS_ASSET;
  private static Method METHOD_ADD_ASSET;
  private static final String TAG = "ApkUtil";
  private static boolean is_atlas_res_class = false;
  private static Constructor res_class_init;
  private static Class resource_impl_class;
  
  static
  {
    try
    {
      CLASS_ASSET = AssetManager.class;
      METHOD_ADD_ASSET = CLASS_ASSET.getDeclaredMethod("addAssetPath", new Class[] { String.class });
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
    }
    resource_impl_class = null;
    res_class_init = null;
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
    Object localObject2 = localObject3;
    if (!TBSResources.useReflection())
    {
      Object localObject1 = paramContext.getPackageManager().getPackageArchiveInfo(paramString, 0);
      if (localObject1 == null) {
        localObject1 = null;
      } else {
        localObject1 = ((PackageInfo)localObject1).applicationInfo;
      }
      localObject2 = localObject3;
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
    }
    if (localObject2 != null) {
      return (Resources)localObject2;
    }
    return getResourcesWithReflect(paramContext, paramString);
  }
  
  private static Resources getResourcesWithReflect(Context paramContext, String paramString)
  {
    Object localObject1 = CLASS_ASSET;
    Object localObject6 = null;
    Object localObject7 = null;
    Object localObject8 = null;
    Object localObject5 = null;
    if (localObject1 != null)
    {
      if (METHOD_ADD_ASSET == null) {
        return null;
      }
      if (!checkApkFile(paramString)) {
        return null;
      }
      initResourceClass(paramContext);
      localObject1 = localObject5;
      Object localObject2 = localObject6;
      Object localObject3 = localObject7;
      Object localObject4 = localObject8;
      try
      {
        AssetManager localAssetManager = (AssetManager)CLASS_ASSET.newInstance();
        localObject1 = localObject5;
        localObject2 = localObject6;
        localObject3 = localObject7;
        localObject4 = localObject8;
        METHOD_ADD_ASSET.invoke(localAssetManager, new Object[] { paramString });
        localObject1 = localObject5;
        localObject2 = localObject6;
        localObject3 = localObject7;
        localObject4 = localObject8;
        DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
        localObject1 = localObject5;
        localObject2 = localObject6;
        localObject3 = localObject7;
        localObject4 = localObject8;
        Configuration localConfiguration = paramContext.getResources().getConfiguration();
        localObject1 = localObject5;
        localObject2 = localObject6;
        localObject3 = localObject7;
        localObject4 = localObject8;
        paramString = newResourceObj(localAssetManager, localDisplayMetrics, localConfiguration, paramContext.getResources());
        paramContext = paramString;
        if (paramString == null)
        {
          localObject1 = paramString;
          localObject2 = paramString;
          localObject3 = paramString;
          localObject4 = paramString;
          paramContext = new Resources(localAssetManager, localDisplayMetrics, localConfiguration);
        }
        localObject1 = paramContext;
        localObject2 = paramContext;
        localObject3 = paramContext;
        localObject4 = paramContext;
        paramString = new StringBuilder();
        localObject1 = paramContext;
        localObject2 = paramContext;
        localObject3 = paramContext;
        localObject4 = paramContext;
        paramString.append("res_class_init result:");
        localObject1 = paramContext;
        localObject2 = paramContext;
        localObject3 = paramContext;
        localObject4 = paramContext;
        paramString.append(paramContext);
        localObject1 = paramContext;
        localObject2 = paramContext;
        localObject3 = paramContext;
        localObject4 = paramContext;
        paramString.toString();
        return paramContext;
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
        return (Resources)localObject1;
      }
      catch (IllegalAccessException paramContext)
      {
        paramContext.printStackTrace();
        return (Resources)localObject2;
      }
      catch (InstantiationException paramContext)
      {
        paramContext.printStackTrace();
        return (Resources)localObject3;
      }
      catch (InvocationTargetException paramContext)
      {
        paramContext.printStackTrace();
        return (Resources)localObject4;
      }
    }
    return null;
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
  
  private static void initResourceClass(Context paramContext)
  {
    if (resource_impl_class == null)
    {
      resource_impl_class = paramContext.getResources().getClass();
      paramContext = new StringBuilder();
      paramContext.append("getResourceClass::resource_impl_class: ");
      paramContext.append(resource_impl_class);
      paramContext.toString();
      try
      {
        if ("android.taobao.atlas.runtime.DelegateResources".equals(resource_impl_class.getName())) {
          is_atlas_res_class = true;
        } else {
          res_class_init = resource_impl_class.getConstructor(new Class[] { AssetManager.class, DisplayMetrics.class, Configuration.class });
        }
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    paramContext = new StringBuilder();
    paramContext.append("resource_impl_class: ");
    paramContext.append(resource_impl_class);
    paramContext.append(", is_atlas_res_class: ");
    paramContext.append(is_atlas_res_class);
    paramContext.toString();
  }
  
  private static Resources newResourceObj(AssetManager paramAssetManager, DisplayMetrics paramDisplayMetrics, Configuration paramConfiguration, Resources paramResources)
  {
    try
    {
      if (is_atlas_res_class) {
        return new Resources(paramAssetManager, paramDisplayMetrics, paramConfiguration);
      }
      paramAssetManager = (Resources)res_class_init.newInstance(new Object[] { paramAssetManager, paramDisplayMetrics, paramConfiguration });
      return paramAssetManager;
    }
    catch (Throwable paramAssetManager)
    {
      paramDisplayMetrics = new StringBuilder();
      paramDisplayMetrics.append("res_class_init exception:");
      paramDisplayMetrics.append(paramAssetManager);
      Log.e("ApkUtil", paramDisplayMetrics.toString());
    }
    return null;
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
      //   59: aload 4
      //   61: astore_2
      //   62: aload_3
      //   63: astore 4
      //   65: goto +23 -> 88
      //   68: astore_3
      //   69: aload 4
      //   71: astore_2
      //   72: aload_3
      //   73: astore 4
      //   75: goto +97 -> 172
      //   78: astore_0
      //   79: aconst_null
      //   80: astore_3
      //   81: goto +171 -> 252
      //   84: astore 4
      //   86: aconst_null
      //   87: astore_2
      //   88: aload_2
      //   89: astore_3
      //   90: new 113	java/lang/StringBuilder
      //   93: dup
      //   94: invokespecial 114	java/lang/StringBuilder:<init>	()V
      //   97: astore 5
      //   99: aload_2
      //   100: astore_3
      //   101: aload 5
      //   103: ldc -99
      //   105: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   108: pop
      //   109: aload_2
      //   110: astore_3
      //   111: aload 5
      //   113: aload_1
      //   114: invokevirtual 102	java/util/jar/JarEntry:getName	()Ljava/lang/String;
      //   117: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   120: pop
      //   121: aload_2
      //   122: astore_3
      //   123: aload 5
      //   125: ldc -49
      //   127: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   130: pop
      //   131: aload_2
      //   132: astore_3
      //   133: aload 5
      //   135: aload_0
      //   136: invokevirtual 208	java/util/jar/JarFile:getName	()Ljava/lang/String;
      //   139: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   142: pop
      //   143: aload_2
      //   144: astore_3
      //   145: ldc 25
      //   147: aload 5
      //   149: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   152: aload 4
      //   154: invokestatic 161	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   157: pop
      //   158: aload_2
      //   159: ifnull +90 -> 249
      //   162: aload_2
      //   163: invokevirtual 205	java/io/InputStream:close	()V
      //   166: aconst_null
      //   167: areturn
      //   168: astore 4
      //   170: aconst_null
      //   171: astore_2
      //   172: aload_2
      //   173: astore_3
      //   174: new 113	java/lang/StringBuilder
      //   177: dup
      //   178: invokespecial 114	java/lang/StringBuilder:<init>	()V
      //   181: astore 5
      //   183: aload_2
      //   184: astore_3
      //   185: aload 5
      //   187: ldc -99
      //   189: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   192: pop
      //   193: aload_2
      //   194: astore_3
      //   195: aload 5
      //   197: aload_1
      //   198: invokevirtual 102	java/util/jar/JarEntry:getName	()Ljava/lang/String;
      //   201: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   204: pop
      //   205: aload_2
      //   206: astore_3
      //   207: aload 5
      //   209: ldc -49
      //   211: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   214: pop
      //   215: aload_2
      //   216: astore_3
      //   217: aload 5
      //   219: aload_0
      //   220: invokevirtual 208	java/util/jar/JarFile:getName	()Ljava/lang/String;
      //   223: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   226: pop
      //   227: aload_2
      //   228: astore_3
      //   229: ldc 25
      //   231: aload 5
      //   233: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   236: aload 4
      //   238: invokestatic 161	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   241: pop
      //   242: aload_2
      //   243: ifnull +6 -> 249
      //   246: goto -84 -> 162
      //   249: aconst_null
      //   250: areturn
      //   251: astore_0
      //   252: aload_3
      //   253: ifnull +7 -> 260
      //   256: aload_3
      //   257: invokevirtual 205	java/io/InputStream:close	()V
      //   260: aload_0
      //   261: athrow
      //   262: astore_0
      //   263: aload_2
      //   264: areturn
      //   265: astore_0
      //   266: aconst_null
      //   267: areturn
      //   268: astore_1
      //   269: goto -9 -> 260
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	272	0	paramJarFile	JarFile
      //   0	272	1	paramJarEntry	JarEntry
      //   0	272	2	paramArrayOfByte	byte[]
      //   19	27	3	localObject1	Object
      //   58	5	3	localRuntimeException1	RuntimeException
      //   68	5	3	localIOException1	IOException
      //   80	177	3	arrayOfByte	byte[]
      //   15	59	4	localObject2	Object
      //   84	69	4	localRuntimeException2	RuntimeException
      //   168	69	4	localIOException2	IOException
      //   1	231	5	localStringBuilder	StringBuilder
      // Exception table:
      //   from	to	target	type
      //   20	33	58	java/lang/RuntimeException
      //   46	51	58	java/lang/RuntimeException
      //   20	33	68	java/io/IOException
      //   46	51	68	java/io/IOException
      //   3	17	78	finally
      //   3	17	84	java/lang/RuntimeException
      //   3	17	168	java/io/IOException
      //   20	33	251	finally
      //   46	51	251	finally
      //   90	99	251	finally
      //   101	109	251	finally
      //   111	121	251	finally
      //   123	131	251	finally
      //   133	143	251	finally
      //   145	158	251	finally
      //   174	183	251	finally
      //   185	193	251	finally
      //   195	205	251	finally
      //   207	215	251	finally
      //   217	227	251	finally
      //   229	242	251	finally
      //   51	56	262	java/lang/Exception
      //   162	166	265	java/lang/Exception
      //   256	260	268	java/lang/Exception
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\resources\ApkUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */