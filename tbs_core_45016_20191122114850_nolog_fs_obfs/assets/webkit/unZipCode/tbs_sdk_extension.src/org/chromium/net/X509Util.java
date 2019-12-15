package org.chromium.net;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.http.X509TrustManagerExtensions;
import android.os.Build.VERSION;
import android.util.Log;
import android.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("net")
public class X509Util
{
  private static File jdField_a_of_type_JavaIoFile;
  private static final Object jdField_a_of_type_JavaLangObject = new Object();
  private static KeyStore jdField_a_of_type_JavaSecurityKeyStore;
  private static CertificateFactory jdField_a_of_type_JavaSecurityCertCertificateFactory;
  private static Set<Pair<X500Principal, PublicKey>> jdField_a_of_type_JavaUtilSet;
  private static TrustStorageListener jdField_a_of_type_OrgChromiumNetX509Util$TrustStorageListener;
  private static X509TrustManagerImplementation jdField_a_of_type_OrgChromiumNetX509Util$X509TrustManagerImplementation;
  private static final char[] jdField_a_of_type_ArrayOfChar = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  private static KeyStore jdField_b_of_type_JavaSecurityKeyStore;
  private static X509TrustManagerImplementation jdField_b_of_type_OrgChromiumNetX509Util$X509TrustManagerImplementation;
  private static boolean jdField_b_of_type_Boolean;
  private static boolean c;
  
  private static String a(X500Principal paramX500Principal)
    throws NoSuchAlgorithmException
  {
    paramX500Principal = MessageDigest.getInstance("MD5").digest(paramX500Principal.getEncoded());
    char[] arrayOfChar1 = new char[8];
    int i = 0;
    while (i < 4)
    {
      int j = i * 2;
      char[] arrayOfChar2 = jdField_a_of_type_ArrayOfChar;
      int k = 3 - i;
      arrayOfChar1[j] = arrayOfChar2[(paramX500Principal[k] >> 4 & 0xF)];
      arrayOfChar1[(j + 1)] = arrayOfChar2[(paramX500Principal[k] & 0xF)];
      i += 1;
    }
    return new String(arrayOfChar1);
  }
  
  public static X509Certificate a(byte[] paramArrayOfByte)
    throws CertificateException, KeyStoreException, NoSuchAlgorithmException
  {
    c();
    return (X509Certificate)jdField_a_of_type_JavaSecurityCertCertificateFactory.generateCertificate(new ByteArrayInputStream(paramArrayOfByte));
  }
  
  /* Error */
  public static AndroidCertVerifyResult a(byte[][] paramArrayOfByte, String paramString1, String paramString2)
    throws KeyStoreException, NoSuchAlgorithmException
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +303 -> 304
    //   4: aload_0
    //   5: arraylength
    //   6: ifeq +298 -> 304
    //   9: aload_0
    //   10: iconst_0
    //   11: aaload
    //   12: ifnull +292 -> 304
    //   15: aload_2
    //   16: aload_0
    //   17: arraylength
    //   18: invokestatic 128	org/chromium/tencent/net/TencentAndroidCertVerifyResult:setHostCertificateLength	(Ljava/lang/String;I)V
    //   21: invokestatic 102	org/chromium/net/X509Util:c	()V
    //   24: new 130	java/util/ArrayList
    //   27: dup
    //   28: invokespecial 131	java/util/ArrayList:<init>	()V
    //   31: astore 5
    //   33: aload 5
    //   35: aload_0
    //   36: iconst_0
    //   37: aaload
    //   38: invokestatic 133	org/chromium/net/X509Util:a	([B)Ljava/security/cert/X509Certificate;
    //   41: invokeinterface 139 2 0
    //   46: pop
    //   47: iconst_1
    //   48: istore_3
    //   49: iload_3
    //   50: aload_0
    //   51: arraylength
    //   52: if_icmpge +24 -> 76
    //   55: aload 5
    //   57: aload_0
    //   58: iload_3
    //   59: aaload
    //   60: invokestatic 133	org/chromium/net/X509Util:a	([B)Ljava/security/cert/X509Certificate;
    //   63: invokeinterface 139 2 0
    //   68: pop
    //   69: iload_3
    //   70: iconst_1
    //   71: iadd
    //   72: istore_3
    //   73: goto -24 -> 49
    //   76: aload 5
    //   78: aload 5
    //   80: invokeinterface 143 1 0
    //   85: anewarray 117	java/security/cert/X509Certificate
    //   88: invokeinterface 147 2 0
    //   93: checkcast 149	[Ljava/security/cert/X509Certificate;
    //   96: astore 6
    //   98: aload 6
    //   100: iconst_0
    //   101: aaload
    //   102: invokevirtual 152	java/security/cert/X509Certificate:checkValidity	()V
    //   105: aload 6
    //   107: iconst_0
    //   108: aaload
    //   109: invokestatic 155	org/chromium/net/X509Util:a	(Ljava/security/cert/X509Certificate;)Z
    //   112: ifne +15 -> 127
    //   115: new 157	org/chromium/net/AndroidCertVerifyResult
    //   118: dup
    //   119: bipush -6
    //   121: invokespecial 160	org/chromium/net/AndroidCertVerifyResult:<init>	(I)V
    //   124: astore_0
    //   125: aload_0
    //   126: areturn
    //   127: getstatic 49	org/chromium/net/X509Util:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   130: astore 5
    //   132: aload 5
    //   134: monitorenter
    //   135: getstatic 162	org/chromium/net/X509Util:jdField_a_of_type_OrgChromiumNetX509Util$X509TrustManagerImplementation	Lorg/chromium/net/X509Util$X509TrustManagerImplementation;
    //   138: ifnonnull +17 -> 155
    //   141: new 157	org/chromium/net/AndroidCertVerifyResult
    //   144: dup
    //   145: iconst_m1
    //   146: invokespecial 160	org/chromium/net/AndroidCertVerifyResult:<init>	(I)V
    //   149: astore_0
    //   150: aload 5
    //   152: monitorexit
    //   153: aload_0
    //   154: areturn
    //   155: getstatic 162	org/chromium/net/X509Util:jdField_a_of_type_OrgChromiumNetX509Util$X509TrustManagerImplementation	Lorg/chromium/net/X509Util$X509TrustManagerImplementation;
    //   158: aload 6
    //   160: aload_1
    //   161: aload_2
    //   162: invokeinterface 166 4 0
    //   167: astore_0
    //   168: goto +16 -> 184
    //   171: getstatic 168	org/chromium/net/X509Util:jdField_b_of_type_OrgChromiumNetX509Util$X509TrustManagerImplementation	Lorg/chromium/net/X509Util$X509TrustManagerImplementation;
    //   174: aload 6
    //   176: aload_1
    //   177: aload_2
    //   178: invokeinterface 166 4 0
    //   183: astore_0
    //   184: aload_0
    //   185: invokeinterface 143 1 0
    //   190: ifle +183 -> 373
    //   193: aload_0
    //   194: aload_0
    //   195: invokeinterface 143 1 0
    //   200: iconst_1
    //   201: isub
    //   202: invokeinterface 172 2 0
    //   207: checkcast 117	java/security/cert/X509Certificate
    //   210: invokestatic 174	org/chromium/net/X509Util:b	(Ljava/security/cert/X509Certificate;)Z
    //   213: istore 4
    //   215: goto +3 -> 218
    //   218: new 157	org/chromium/net/AndroidCertVerifyResult
    //   221: dup
    //   222: iconst_0
    //   223: iload 4
    //   225: aload_0
    //   226: invokespecial 177	org/chromium/net/AndroidCertVerifyResult:<init>	(IZLjava/util/List;)V
    //   229: astore_0
    //   230: aload 5
    //   232: monitorexit
    //   233: aload_0
    //   234: areturn
    //   235: new 157	org/chromium/net/AndroidCertVerifyResult
    //   238: dup
    //   239: bipush -2
    //   241: invokespecial 160	org/chromium/net/AndroidCertVerifyResult:<init>	(I)V
    //   244: astore_0
    //   245: aload 5
    //   247: monitorexit
    //   248: aload_0
    //   249: areturn
    //   250: astore_0
    //   251: aload 5
    //   253: monitorexit
    //   254: aload_0
    //   255: athrow
    //   256: new 157	org/chromium/net/AndroidCertVerifyResult
    //   259: dup
    //   260: iconst_m1
    //   261: invokespecial 160	org/chromium/net/AndroidCertVerifyResult:<init>	(I)V
    //   264: areturn
    //   265: new 157	org/chromium/net/AndroidCertVerifyResult
    //   268: dup
    //   269: bipush -4
    //   271: invokespecial 160	org/chromium/net/AndroidCertVerifyResult:<init>	(I)V
    //   274: areturn
    //   275: new 157	org/chromium/net/AndroidCertVerifyResult
    //   278: dup
    //   279: bipush -3
    //   281: invokespecial 160	org/chromium/net/AndroidCertVerifyResult:<init>	(I)V
    //   284: areturn
    //   285: new 157	org/chromium/net/AndroidCertVerifyResult
    //   288: dup
    //   289: bipush -5
    //   291: invokespecial 160	org/chromium/net/AndroidCertVerifyResult:<init>	(I)V
    //   294: areturn
    //   295: new 157	org/chromium/net/AndroidCertVerifyResult
    //   298: dup
    //   299: iconst_m1
    //   300: invokespecial 160	org/chromium/net/AndroidCertVerifyResult:<init>	(I)V
    //   303: areturn
    //   304: new 179	java/lang/StringBuilder
    //   307: dup
    //   308: invokespecial 180	java/lang/StringBuilder:<init>	()V
    //   311: astore_1
    //   312: aload_1
    //   313: ldc -74
    //   315: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   318: pop
    //   319: aload_1
    //   320: aload_0
    //   321: invokestatic 192	java/util/Arrays:deepToString	([Ljava/lang/Object;)Ljava/lang/String;
    //   324: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   327: pop
    //   328: new 194	java/lang/IllegalArgumentException
    //   331: dup
    //   332: aload_1
    //   333: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   336: invokespecial 201	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   339: athrow
    //   340: astore_0
    //   341: goto -46 -> 295
    //   344: astore_0
    //   345: goto -60 -> 285
    //   348: astore 6
    //   350: goto -281 -> 69
    //   353: astore_0
    //   354: goto -79 -> 275
    //   357: astore_0
    //   358: goto -93 -> 265
    //   361: astore_0
    //   362: goto -106 -> 256
    //   365: astore_0
    //   366: goto -195 -> 171
    //   369: astore_0
    //   370: goto -135 -> 235
    //   373: iconst_0
    //   374: istore 4
    //   376: goto -158 -> 218
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	379	0	paramArrayOfByte	byte[][]
    //   0	379	1	paramString1	String
    //   0	379	2	paramString2	String
    //   48	25	3	i	int
    //   213	162	4	bool	boolean
    //   96	79	6	arrayOfX509Certificate	X509Certificate[]
    //   348	1	6	localCertificateException	CertificateException
    // Exception table:
    //   from	to	target	type
    //   135	153	250	finally
    //   155	168	250	finally
    //   171	184	250	finally
    //   184	215	250	finally
    //   218	233	250	finally
    //   235	248	250	finally
    //   251	254	250	finally
    //   21	24	340	java/security/cert/CertificateException
    //   33	47	344	java/security/cert/CertificateException
    //   55	69	348	java/security/cert/CertificateException
    //   98	125	353	java/security/cert/CertificateExpiredException
    //   98	125	357	java/security/cert/CertificateNotYetValidException
    //   98	125	361	java/security/cert/CertificateException
    //   155	168	365	java/security/cert/CertificateException
    //   171	184	369	java/security/cert/CertificateException
  }
  
  private static X509TrustManagerImplementation a(KeyStore paramKeyStore)
    throws KeyStoreException, NoSuchAlgorithmException
  {
    Object localObject = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    ((TrustManagerFactory)localObject).init(paramKeyStore);
    paramKeyStore = ((TrustManagerFactory)localObject).getTrustManagers();
    int j = paramKeyStore.length;
    int i = 0;
    while (i < j)
    {
      String str = paramKeyStore[i];
      if ((str instanceof X509TrustManager)) {
        try
        {
          if (Build.VERSION.SDK_INT >= 17) {
            return new X509TrustManagerJellyBean((X509TrustManager)str);
          }
          localObject = new X509TrustManagerIceCreamSandwich((X509TrustManager)str);
          return (X509TrustManagerImplementation)localObject;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          str = str.getClass().getName();
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Error creating trust manager (");
          localStringBuilder.append(str);
          localStringBuilder.append("): ");
          localStringBuilder.append(localIllegalArgumentException);
          Log.e("X509Util", localStringBuilder.toString());
        }
      }
      i += 1;
    }
    Log.e("X509Util", "Could not find suitable trust manager");
    return null;
  }
  
  public static void a()
    throws NoSuchAlgorithmException, CertificateException, KeyStoreException
  {
    
    try
    {
      synchronized (jdField_a_of_type_JavaLangObject)
      {
        jdField_a_of_type_JavaSecurityKeyStore.load(null);
        e();
      }
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
    return;
    throw ((Throwable)localObject2);
  }
  
  public static void a(byte[] arg0)
    throws CertificateException, KeyStoreException, NoSuchAlgorithmException
  {
    c();
    X509Certificate localX509Certificate = a(???);
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      KeyStore localKeyStore = jdField_a_of_type_JavaSecurityKeyStore;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("root_cert_");
      localStringBuilder.append(Integer.toString(jdField_a_of_type_JavaSecurityKeyStore.size()));
      localKeyStore.setCertificateEntry(localStringBuilder.toString(), localX509Certificate);
      e();
      return;
    }
  }
  
  static boolean a(X509Certificate paramX509Certificate)
    throws CertificateException
  {
    try
    {
      paramX509Certificate = paramX509Certificate.getExtendedKeyUsage();
      if (paramX509Certificate == null) {
        return true;
      }
      paramX509Certificate = paramX509Certificate.iterator();
      while (paramX509Certificate.hasNext())
      {
        String str = (String)paramX509Certificate.next();
        if ((str.equals("1.3.6.1.5.5.7.3.1")) || (str.equals("2.5.29.37.0")) || (str.equals("2.16.840.1.113730.4.1")) || (str.equals("1.3.6.1.4.1.311.10.3.3"))) {
          return true;
        }
      }
      return false;
    }
    catch (NullPointerException paramX509Certificate) {}
    return false;
  }
  
  private static boolean b(X509Certificate paramX509Certificate)
    throws NoSuchAlgorithmException, KeyStoreException
  {
    if ((!jdField_a_of_type_Boolean) && (!Thread.holdsLock(jdField_a_of_type_JavaLangObject))) {
      throw new AssertionError();
    }
    if (jdField_b_of_type_JavaSecurityKeyStore == null) {
      return false;
    }
    Pair localPair = new Pair(paramX509Certificate.getSubjectX500Principal(), paramX509Certificate.getPublicKey());
    if (jdField_a_of_type_JavaUtilSet.contains(localPair)) {
      return true;
    }
    String str = a(paramX509Certificate.getSubjectX500Principal());
    int i = 0;
    for (;;)
    {
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(str);
      ((StringBuilder)localObject1).append('.');
      ((StringBuilder)localObject1).append(i);
      localObject1 = ((StringBuilder)localObject1).toString();
      if (!new File(jdField_a_of_type_JavaIoFile, (String)localObject1).exists()) {
        return false;
      }
      Object localObject2 = jdField_b_of_type_JavaSecurityKeyStore;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("system:");
      localStringBuilder.append((String)localObject1);
      localObject2 = ((KeyStore)localObject2).getCertificate(localStringBuilder.toString());
      if (localObject2 != null) {
        if (!(localObject2 instanceof X509Certificate))
        {
          localObject2 = localObject2.getClass().getName();
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Anchor ");
          localStringBuilder.append((String)localObject1);
          localStringBuilder.append(" not an X509Certificate: ");
          localStringBuilder.append((String)localObject2);
          Log.e("X509Util", localStringBuilder.toString());
        }
        else
        {
          localObject1 = (X509Certificate)localObject2;
          if ((paramX509Certificate.getSubjectX500Principal().equals(((X509Certificate)localObject1).getSubjectX500Principal())) && (paramX509Certificate.getPublicKey().equals(((X509Certificate)localObject1).getPublicKey())))
          {
            jdField_a_of_type_JavaUtilSet.add(localPair);
            return true;
          }
        }
      }
      i += 1;
    }
  }
  
  private static void c()
    throws CertificateException, KeyStoreException, NoSuchAlgorithmException
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      d();
      return;
    }
  }
  
  /* Error */
  private static void d()
    throws CertificateException, KeyStoreException, NoSuchAlgorithmException
  {
    // Byte code:
    //   0: getstatic 44	org/chromium/net/X509Util:jdField_a_of_type_Boolean	Z
    //   3: ifne +23 -> 26
    //   6: getstatic 49	org/chromium/net/X509Util:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   9: invokestatic 316	java/lang/Thread:holdsLock	(Ljava/lang/Object;)Z
    //   12: ifeq +6 -> 18
    //   15: goto +11 -> 26
    //   18: new 318	java/lang/AssertionError
    //   21: dup
    //   22: invokespecial 319	java/lang/AssertionError:<init>	()V
    //   25: athrow
    //   26: getstatic 104	org/chromium/net/X509Util:jdField_a_of_type_JavaSecurityCertCertificateFactory	Ljava/security/cert/CertificateFactory;
    //   29: ifnonnull +12 -> 41
    //   32: ldc_w 377
    //   35: invokestatic 380	java/security/cert/CertificateFactory:getInstance	(Ljava/lang/String;)Ljava/security/cert/CertificateFactory;
    //   38: putstatic 104	org/chromium/net/X509Util:jdField_a_of_type_JavaSecurityCertCertificateFactory	Ljava/security/cert/CertificateFactory;
    //   41: getstatic 162	org/chromium/net/X509Util:jdField_a_of_type_OrgChromiumNetX509Util$X509TrustManagerImplementation	Lorg/chromium/net/X509Util$X509TrustManagerImplementation;
    //   44: ifnonnull +10 -> 54
    //   47: aconst_null
    //   48: invokestatic 382	org/chromium/net/X509Util:a	(Ljava/security/KeyStore;)Lorg/chromium/net/X509Util$X509TrustManagerImplementation;
    //   51: putstatic 162	org/chromium/net/X509Util:jdField_a_of_type_OrgChromiumNetX509Util$X509TrustManagerImplementation	Lorg/chromium/net/X509Util$X509TrustManagerImplementation;
    //   54: getstatic 384	org/chromium/net/X509Util:jdField_b_of_type_Boolean	Z
    //   57: ifne +98 -> 155
    //   60: ldc_w 386
    //   63: invokestatic 389	java/security/KeyStore:getInstance	(Ljava/lang/String;)Ljava/security/KeyStore;
    //   66: putstatic 321	org/chromium/net/X509Util:jdField_b_of_type_JavaSecurityKeyStore	Ljava/security/KeyStore;
    //   69: getstatic 321	org/chromium/net/X509Util:jdField_b_of_type_JavaSecurityKeyStore	Ljava/security/KeyStore;
    //   72: aconst_null
    //   73: invokevirtual 264	java/security/KeyStore:load	(Ljava/security/KeyStore$LoadStoreParameter;)V
    //   76: new 179	java/lang/StringBuilder
    //   79: dup
    //   80: invokespecial 180	java/lang/StringBuilder:<init>	()V
    //   83: astore_1
    //   84: aload_1
    //   85: ldc_w 391
    //   88: invokestatic 397	java/lang/System:getenv	(Ljava/lang/String;)Ljava/lang/String;
    //   91: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: pop
    //   95: aload_1
    //   96: ldc_w 399
    //   99: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: pop
    //   103: new 351	java/io/File
    //   106: dup
    //   107: aload_1
    //   108: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   111: invokespecial 400	java/io/File:<init>	(Ljava/lang/String;)V
    //   114: putstatic 353	org/chromium/net/X509Util:jdField_a_of_type_JavaIoFile	Ljava/io/File;
    //   117: getstatic 402	org/chromium/net/X509Util:c	Z
    //   120: ifne +31 -> 151
    //   123: getstatic 226	android/os/Build$VERSION:SDK_INT	I
    //   126: bipush 17
    //   128: if_icmplt +23 -> 151
    //   131: getstatic 321	org/chromium/net/X509Util:jdField_b_of_type_JavaSecurityKeyStore	Ljava/security/KeyStore;
    //   134: ifnull +8 -> 142
    //   137: iconst_1
    //   138: istore_0
    //   139: goto +5 -> 144
    //   142: iconst_0
    //   143: istore_0
    //   144: ldc_w 404
    //   147: iload_0
    //   148: invokestatic 410	org/chromium/base/metrics/RecordHistogram:recordBooleanHistogram	(Ljava/lang/String;Z)V
    //   151: iconst_1
    //   152: putstatic 384	org/chromium/net/X509Util:jdField_b_of_type_Boolean	Z
    //   155: getstatic 336	org/chromium/net/X509Util:jdField_a_of_type_JavaUtilSet	Ljava/util/Set;
    //   158: ifnonnull +13 -> 171
    //   161: new 412	java/util/HashSet
    //   164: dup
    //   165: invokespecial 413	java/util/HashSet:<init>	()V
    //   168: putstatic 336	org/chromium/net/X509Util:jdField_a_of_type_JavaUtilSet	Ljava/util/Set;
    //   171: getstatic 258	org/chromium/net/X509Util:jdField_a_of_type_JavaSecurityKeyStore	Ljava/security/KeyStore;
    //   174: ifnonnull +22 -> 196
    //   177: invokestatic 416	java/security/KeyStore:getDefaultType	()Ljava/lang/String;
    //   180: invokestatic 389	java/security/KeyStore:getInstance	(Ljava/lang/String;)Ljava/security/KeyStore;
    //   183: putstatic 258	org/chromium/net/X509Util:jdField_a_of_type_JavaSecurityKeyStore	Ljava/security/KeyStore;
    //   186: getstatic 258	org/chromium/net/X509Util:jdField_a_of_type_JavaSecurityKeyStore	Ljava/security/KeyStore;
    //   189: aconst_null
    //   190: invokevirtual 264	java/security/KeyStore:load	(Ljava/security/KeyStore$LoadStoreParameter;)V
    //   193: goto +3 -> 196
    //   196: getstatic 168	org/chromium/net/X509Util:jdField_b_of_type_OrgChromiumNetX509Util$X509TrustManagerImplementation	Lorg/chromium/net/X509Util$X509TrustManagerImplementation;
    //   199: ifnonnull +12 -> 211
    //   202: getstatic 258	org/chromium/net/X509Util:jdField_a_of_type_JavaSecurityKeyStore	Ljava/security/KeyStore;
    //   205: invokestatic 382	org/chromium/net/X509Util:a	(Ljava/security/KeyStore;)Lorg/chromium/net/X509Util$X509TrustManagerImplementation;
    //   208: putstatic 168	org/chromium/net/X509Util:jdField_b_of_type_OrgChromiumNetX509Util$X509TrustManagerImplementation	Lorg/chromium/net/X509Util$X509TrustManagerImplementation;
    //   211: getstatic 402	org/chromium/net/X509Util:c	Z
    //   214: ifne +78 -> 292
    //   217: getstatic 418	org/chromium/net/X509Util:jdField_a_of_type_OrgChromiumNetX509Util$TrustStorageListener	Lorg/chromium/net/X509Util$TrustStorageListener;
    //   220: ifnonnull +72 -> 292
    //   223: new 8	org/chromium/net/X509Util$TrustStorageListener
    //   226: dup
    //   227: aconst_null
    //   228: invokespecial 421	org/chromium/net/X509Util$TrustStorageListener:<init>	(Lorg/chromium/net/X509Util$1;)V
    //   231: putstatic 418	org/chromium/net/X509Util:jdField_a_of_type_OrgChromiumNetX509Util$TrustStorageListener	Lorg/chromium/net/X509Util$TrustStorageListener;
    //   234: new 423	android/content/IntentFilter
    //   237: dup
    //   238: invokespecial 424	android/content/IntentFilter:<init>	()V
    //   241: astore_1
    //   242: getstatic 226	android/os/Build$VERSION:SDK_INT	I
    //   245: bipush 26
    //   247: if_icmplt +27 -> 274
    //   250: aload_1
    //   251: ldc_w 426
    //   254: invokevirtual 429	android/content/IntentFilter:addAction	(Ljava/lang/String;)V
    //   257: aload_1
    //   258: ldc_w 431
    //   261: invokevirtual 429	android/content/IntentFilter:addAction	(Ljava/lang/String;)V
    //   264: aload_1
    //   265: ldc_w 433
    //   268: invokevirtual 429	android/content/IntentFilter:addAction	(Ljava/lang/String;)V
    //   271: goto +10 -> 281
    //   274: aload_1
    //   275: ldc_w 435
    //   278: invokevirtual 429	android/content/IntentFilter:addAction	(Ljava/lang/String;)V
    //   281: invokestatic 441	org/chromium/base/ContextUtils:getApplicationContext	()Landroid/content/Context;
    //   284: getstatic 418	org/chromium/net/X509Util:jdField_a_of_type_OrgChromiumNetX509Util$TrustStorageListener	Lorg/chromium/net/X509Util$TrustStorageListener;
    //   287: aload_1
    //   288: invokestatic 447	org/chromium/tencent/utils/X5ApiCompatibilityUtils:x5RegisterReceiver	(Landroid/content/Context;Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;
    //   291: pop
    //   292: return
    //   293: astore_1
    //   294: goto -177 -> 117
    //   297: astore_1
    //   298: goto -222 -> 76
    //   301: astore_1
    //   302: goto -106 -> 196
    // Local variable table:
    //   start	length	slot	name	signature
    //   138	10	0	bool	boolean
    //   83	205	1	localObject	Object
    //   293	1	1	localKeyStoreException	KeyStoreException
    //   297	1	1	localIOException1	IOException
    //   301	1	1	localIOException2	IOException
    // Exception table:
    //   from	to	target	type
    //   60	69	293	java/security/KeyStoreException
    //   69	76	293	java/security/KeyStoreException
    //   76	117	293	java/security/KeyStoreException
    //   69	76	297	java/io/IOException
    //   186	193	301	java/io/IOException
  }
  
  private static void e()
    throws KeyStoreException, NoSuchAlgorithmException
  {
    if ((!jdField_a_of_type_Boolean) && (!Thread.holdsLock(jdField_a_of_type_JavaLangObject))) {
      throw new AssertionError();
    }
    jdField_b_of_type_OrgChromiumNetX509Util$X509TrustManagerImplementation = a(jdField_a_of_type_JavaSecurityKeyStore);
  }
  
  private static void f()
    throws KeyStoreException, NoSuchAlgorithmException, CertificateException
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      jdField_a_of_type_OrgChromiumNetX509Util$X509TrustManagerImplementation = null;
      jdField_a_of_type_JavaUtilSet = null;
      d();
      nativeNotifyKeyChainChanged();
      return;
    }
  }
  
  private static native void nativeNotifyKeyChainChanged();
  
  private static final class TrustStorageListener
    extends BroadcastReceiver
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      int i = Build.VERSION.SDK_INT;
      boolean bool2 = true;
      boolean bool1;
      if (i >= 26)
      {
        bool1 = bool2;
        if (!"android.security.action.KEYCHAIN_CHANGED".equals(paramIntent.getAction())) {
          if ("android.security.action.TRUST_STORE_CHANGED".equals(paramIntent.getAction())) {
            bool1 = bool2;
          } else if (("android.security.action.KEY_ACCESS_CHANGED".equals(paramIntent.getAction())) && (!paramIntent.getBooleanExtra("android.security.extra.KEY_ACCESSIBLE", false))) {
            bool1 = bool2;
          } else {
            bool1 = false;
          }
        }
      }
      else
      {
        bool1 = "android.security.STORAGE_CHANGED".equals(paramIntent.getAction());
      }
      if (bool1) {
        try
        {
          X509Util.b();
          return;
        }
        catch (NoSuchAlgorithmException paramContext)
        {
          Log.e("X509Util", "Unable to reload the default TrustManager", paramContext);
          return;
        }
        catch (KeyStoreException paramContext)
        {
          Log.e("X509Util", "Unable to reload the default TrustManager", paramContext);
          return;
        }
        catch (CertificateException paramContext)
        {
          Log.e("X509Util", "Unable to reload the default TrustManager", paramContext);
        }
      }
    }
  }
  
  private static final class X509TrustManagerIceCreamSandwich
    implements X509Util.X509TrustManagerImplementation
  {
    private final X509TrustManager a;
    
    public X509TrustManagerIceCreamSandwich(X509TrustManager paramX509TrustManager)
    {
      this.a = paramX509TrustManager;
    }
    
    public List<X509Certificate> checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString1, String paramString2)
      throws CertificateException
    {
      this.a.checkServerTrusted(paramArrayOfX509Certificate, paramString1);
      return Collections.emptyList();
    }
  }
  
  private static abstract interface X509TrustManagerImplementation
  {
    public abstract List<X509Certificate> checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString1, String paramString2)
      throws CertificateException;
  }
  
  private static final class X509TrustManagerJellyBean
    implements X509Util.X509TrustManagerImplementation
  {
    private final X509TrustManagerExtensions a;
    
    @SuppressLint({"NewApi"})
    public X509TrustManagerJellyBean(X509TrustManager paramX509TrustManager)
    {
      this.a = new X509TrustManagerExtensions(paramX509TrustManager);
    }
    
    @SuppressLint({"NewApi"})
    public List<X509Certificate> checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString1, String paramString2)
      throws CertificateException
    {
      return this.a.checkServerTrusted(paramArrayOfX509Certificate, paramString1, paramString2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\net\X509Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */