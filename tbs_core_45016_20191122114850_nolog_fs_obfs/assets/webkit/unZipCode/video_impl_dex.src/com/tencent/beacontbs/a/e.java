package com.tencent.beacontbs.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Base64;
import com.tencent.beacontbs.event.d;
import com.tencent.beacontbs.event.j;
import com.tencent.beacontbs.event.k;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.util.EncodingUtils;

public final class e
{
  private static e a;
  
  public static int a(Context paramContext)
  {
    int j = 0;
    if (paramContext == null) {
      try
      {
        com.tencent.beacontbs.c.a.c("context == null ||key < -1}", new Object[0]);
        return 0;
      }
      finally
      {
        break label126;
      }
    }
    int i;
    try
    {
      paramContext = com.tencent.beacontbs.a.a.c.a(paramContext).getWritableDatabase();
      if (paramContext == null)
      {
        com.tencent.beacontbs.c.a.d("get db fail!,return ", new Object[0]);
        return 0;
      }
      i = paramContext.delete("t_strategy", String.format("%s = %d", new Object[] { "_key", Integer.valueOf(101) }), null);
      try
      {
        com.tencent.beacontbs.c.a.g("delete Strategy key} %d , num} %d", new Object[] { Integer.valueOf(101), Integer.valueOf(i) });
      }
      catch (Throwable paramContext) {}
      com.tencent.beacontbs.c.a.a(paramContext);
    }
    catch (Throwable paramContext)
    {
      i = j;
    }
    return i;
    label126:
    throw paramContext;
  }
  
  public static int a(Context paramContext, Long[] paramArrayOfLong)
  {
    com.tencent.beacontbs.c.a.a(" RecordDAO.deleteRecordList() start", new Object[0]);
    if (paramContext == null)
    {
      com.tencent.beacontbs.c.a.d(" deleteRecordList() have null args!", new Object[0]);
      return -1;
    }
    com.tencent.beacontbs.c.a.a(" RecordDAO.deleteRecordList() end", new Object[0]);
    return com.tencent.beacontbs.a.a.a.a(paramContext, paramArrayOfLong);
  }
  
  private static NetworkInfo a(Context paramContext)
  {
    try
    {
      paramContext = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (paramContext == null) {
        return null;
      }
      paramContext = paramContext.getActiveNetworkInfo();
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      com.tencent.beacontbs.c.a.a(paramContext);
    }
    return null;
  }
  
  /* Error */
  public static com.tencent.beacontbs.a.b.g a(Context paramContext)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: aconst_null
    //   4: astore 4
    //   6: aconst_null
    //   7: astore 7
    //   9: aconst_null
    //   10: astore_2
    //   11: aconst_null
    //   12: astore 5
    //   14: aconst_null
    //   15: astore 6
    //   17: aload_0
    //   18: ifnonnull +21 -> 39
    //   21: ldc 99
    //   23: iconst_0
    //   24: anewarray 4	java/lang/Object
    //   27: invokestatic 22	com/tencent/beacontbs/c/a:c	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   30: ldc 2
    //   32: monitorexit
    //   33: aconst_null
    //   34: areturn
    //   35: astore_0
    //   36: goto +359 -> 395
    //   39: aload_2
    //   40: astore_1
    //   41: aload_0
    //   42: invokestatic 27	com/tencent/beacontbs/a/a/c:a	(Landroid/content/Context;)Lcom/tencent/beacontbs/a/a/c;
    //   45: invokevirtual 31	com/tencent/beacontbs/a/a/c:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   48: astore_0
    //   49: aload_0
    //   50: ifnonnull +19 -> 69
    //   53: aload_2
    //   54: astore_1
    //   55: ldc 101
    //   57: iconst_0
    //   58: anewarray 4	java/lang/Object
    //   61: invokestatic 22	com/tencent/beacontbs/c/a:c	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   64: ldc 2
    //   66: monitorexit
    //   67: aconst_null
    //   68: areturn
    //   69: aload_2
    //   70: astore_1
    //   71: aload_0
    //   72: ldc 38
    //   74: aconst_null
    //   75: getstatic 107	java/util/Locale:US	Ljava/util/Locale;
    //   78: ldc 109
    //   80: iconst_2
    //   81: anewarray 4	java/lang/Object
    //   84: dup
    //   85: iconst_0
    //   86: ldc 42
    //   88: aastore
    //   89: dup
    //   90: iconst_1
    //   91: bipush 101
    //   93: invokestatic 48	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   96: aastore
    //   97: invokestatic 112	java/lang/String:format	(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   100: aconst_null
    //   101: aconst_null
    //   102: aconst_null
    //   103: aconst_null
    //   104: invokevirtual 116	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   107: astore_3
    //   108: aload 7
    //   110: astore_2
    //   111: aload_3
    //   112: ifnull +192 -> 304
    //   115: aload 4
    //   117: astore_1
    //   118: aload 7
    //   120: astore_2
    //   121: aload_3
    //   122: invokeinterface 122 1 0
    //   127: ifeq +177 -> 304
    //   130: aload 6
    //   132: astore_0
    //   133: aload_3
    //   134: ifnull +122 -> 256
    //   137: aload 6
    //   139: astore_0
    //   140: aload 4
    //   142: astore_1
    //   143: aload_3
    //   144: invokeinterface 125 1 0
    //   149: ifne +107 -> 256
    //   152: aload 4
    //   154: astore_1
    //   155: aload_3
    //   156: invokeinterface 128 1 0
    //   161: ifeq +9 -> 170
    //   164: aload 6
    //   166: astore_0
    //   167: goto +89 -> 256
    //   170: aload 4
    //   172: astore_1
    //   173: ldc -126
    //   175: iconst_0
    //   176: anewarray 4	java/lang/Object
    //   179: invokestatic 133	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   182: aload 4
    //   184: astore_1
    //   185: new 135	com/tencent/beacontbs/a/b/g
    //   188: dup
    //   189: invokespecial 136	com/tencent/beacontbs/a/b/g:<init>	()V
    //   192: astore_0
    //   193: aload 4
    //   195: astore_1
    //   196: aload_0
    //   197: aload_3
    //   198: aload_3
    //   199: ldc -118
    //   201: invokeinterface 142 2 0
    //   206: invokeinterface 146 2 0
    //   211: invokevirtual 149	com/tencent/beacontbs/a/b/g:a	(J)V
    //   214: aload 4
    //   216: astore_1
    //   217: aload_0
    //   218: aload_3
    //   219: aload_3
    //   220: ldc 42
    //   222: invokeinterface 142 2 0
    //   227: invokeinterface 153 2 0
    //   232: invokevirtual 156	com/tencent/beacontbs/a/b/g:a	(I)V
    //   235: aload 4
    //   237: astore_1
    //   238: aload_0
    //   239: aload_3
    //   240: aload_3
    //   241: ldc -98
    //   243: invokeinterface 142 2 0
    //   248: invokeinterface 162 2 0
    //   253: invokevirtual 165	com/tencent/beacontbs/a/b/g:a	([B)V
    //   256: aload_0
    //   257: astore_2
    //   258: aload_0
    //   259: ifnull +45 -> 304
    //   262: aload_0
    //   263: astore_1
    //   264: ldc -89
    //   266: iconst_1
    //   267: anewarray 4	java/lang/Object
    //   270: dup
    //   271: iconst_0
    //   272: aload_0
    //   273: invokevirtual 170	com/tencent/beacontbs/a/b/g:a	()I
    //   276: invokestatic 48	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   279: aastore
    //   280: invokestatic 65	com/tencent/beacontbs/c/a:g	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   283: aload_0
    //   284: astore_2
    //   285: goto +19 -> 304
    //   288: astore_0
    //   289: aload_3
    //   290: astore_1
    //   291: goto +83 -> 374
    //   294: astore_2
    //   295: aload_3
    //   296: astore_0
    //   297: aload_2
    //   298: astore_3
    //   299: aload_1
    //   300: astore_2
    //   301: goto +37 -> 338
    //   304: aload_3
    //   305: ifnull +18 -> 323
    //   308: aload_3
    //   309: invokeinterface 173 1 0
    //   314: ifne +9 -> 323
    //   317: aload_3
    //   318: invokeinterface 176 1 0
    //   323: aload_2
    //   324: astore_1
    //   325: goto +44 -> 369
    //   328: astore_0
    //   329: goto +45 -> 374
    //   332: astore_3
    //   333: aconst_null
    //   334: astore_2
    //   335: aload 5
    //   337: astore_0
    //   338: aload_0
    //   339: astore_1
    //   340: aload_3
    //   341: invokestatic 68	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   344: aload_2
    //   345: astore_1
    //   346: aload_0
    //   347: ifnull +22 -> 369
    //   350: aload_2
    //   351: astore_1
    //   352: aload_0
    //   353: invokeinterface 173 1 0
    //   358: ifne +11 -> 369
    //   361: aload_0
    //   362: invokeinterface 176 1 0
    //   367: aload_2
    //   368: astore_1
    //   369: ldc 2
    //   371: monitorexit
    //   372: aload_1
    //   373: areturn
    //   374: aload_1
    //   375: ifnull +18 -> 393
    //   378: aload_1
    //   379: invokeinterface 173 1 0
    //   384: ifne +9 -> 393
    //   387: aload_1
    //   388: invokeinterface 176 1 0
    //   393: aload_0
    //   394: athrow
    //   395: ldc 2
    //   397: monitorexit
    //   398: aload_0
    //   399: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	400	0	paramContext	Context
    //   40	348	1	localObject1	Object
    //   10	275	2	localObject2	Object
    //   294	4	2	localThrowable1	Throwable
    //   300	68	2	localObject3	Object
    //   107	211	3	localObject4	Object
    //   332	9	3	localThrowable2	Throwable
    //   4	232	4	localObject5	Object
    //   12	324	5	localObject6	Object
    //   15	150	6	localObject7	Object
    //   7	112	7	localObject8	Object
    // Exception table:
    //   from	to	target	type
    //   21	30	35	finally
    //   308	323	35	finally
    //   352	367	35	finally
    //   378	393	35	finally
    //   393	395	35	finally
    //   121	130	288	finally
    //   143	152	288	finally
    //   155	164	288	finally
    //   173	182	288	finally
    //   185	193	288	finally
    //   196	214	288	finally
    //   217	235	288	finally
    //   238	256	288	finally
    //   264	283	288	finally
    //   121	130	294	java/lang/Throwable
    //   143	152	294	java/lang/Throwable
    //   155	164	294	java/lang/Throwable
    //   173	182	294	java/lang/Throwable
    //   185	193	294	java/lang/Throwable
    //   196	214	294	java/lang/Throwable
    //   217	235	294	java/lang/Throwable
    //   238	256	294	java/lang/Throwable
    //   264	283	294	java/lang/Throwable
    //   41	49	328	finally
    //   55	64	328	finally
    //   71	108	328	finally
    //   340	344	328	finally
    //   41	49	332	java/lang/Throwable
    //   55	64	332	java/lang/Throwable
    //   71	108	332	java/lang/Throwable
  }
  
  public static e a(Context paramContext)
  {
    try
    {
      if ((a == null) && (paramContext != null)) {
        a = new e();
      }
      paramContext = a;
      return paramContext;
    }
    finally {}
  }
  
  public static com.tencent.beacontbs.b.a.b a(int paramInt1, c paramc, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    com.tencent.beacontbs.c.a.b("en2Req", new Object[0]);
    if (paramc == null)
    {
      com.tencent.beacontbs.c.a.d("error no com info! ", new Object[0]);
      return null;
    }
    for (;;)
    {
      try
      {
        com.tencent.beacontbs.b.a.b localb = new com.tencent.beacontbs.b.a.b();
        try
        {
          localb.e = paramc.a();
          localb.f = paramc.b();
          localb.jdField_a_of_type_Byte = paramc.a();
          localb.jdField_a_of_type_JavaLangString = paramc.h();
          localb.jdField_b_of_type_JavaLangString = paramc.c();
          localb.jdField_c_of_type_JavaLangString = paramc.d();
          localb.d = paramc.e();
          localb.g = "";
          if (paramInt1 == 100)
          {
            HashMap localHashMap = new HashMap();
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append(com.tencent.beacontbs.event.c.a());
            localHashMap.put("A1", ((StringBuilder)localObject).toString());
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append(paramc.g());
            localHashMap.put("A2", ((StringBuilder)localObject).toString());
            localObject = d.a(paramc.a());
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append(((d)localObject).c());
            localHashMap.put("A4", localStringBuilder.toString());
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(((d)localObject).b());
            localHashMap.put("A6", localStringBuilder.toString());
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(((d)localObject).d());
            localHashMap.put("A7", localStringBuilder.toString());
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(((d)localObject).e());
            localHashMap.put("A3", localStringBuilder.toString());
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(paramc.i());
            localHashMap.put("A23", localStringBuilder.toString());
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(((d)localObject).f());
            localHashMap.put("A31", localStringBuilder.toString());
            a(paramc.a());
            localHashMap.put("A33", e(paramc.a()));
            if (a.d(paramc.a())) {
              localHashMap.put("A66", "F");
            } else {
              localHashMap.put("A66", "B");
            }
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append(a.d(paramc.a()));
            localHashMap.put("A67", ((StringBuilder)localObject).toString());
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append(a.a(paramc.a()));
            localHashMap.put("A68", ((StringBuilder)localObject).toString());
            if (a.jdField_a_of_type_Boolean)
            {
              localObject = "Y";
              localHashMap.put("A85", localObject);
              localb.g = a(localHashMap);
            }
          }
          else
          {
            if ((paramInt1 == 4) || (paramInt1 == 2) || (paramInt1 == 1)) {
              localb.g = a(com.tencent.beacontbs.event.c.a());
            }
            localb.jdField_a_of_type_Int = paramInt1;
            localb.jdField_b_of_type_Byte = ((byte)paramInt3);
            localb.jdField_c_of_type_Byte = ((byte)paramInt2);
            paramc = paramArrayOfByte;
            if (paramArrayOfByte == null) {
              paramc = "".getBytes();
            }
            localb.jdField_a_of_type_ArrayOfByte = paramc;
            return localb;
          }
        }
        finally {}
        Object localObject = "N";
      }
      catch (Throwable paramc)
      {
        com.tencent.beacontbs.c.a.a(paramc);
        return null;
      }
    }
  }
  
  public static com.tencent.beacontbs.b.b.a a(j paramj)
  {
    if (paramj != null)
    {
      if (!"UA".equals(paramj.a())) {
        return null;
      }
      Map localMap = paramj.a();
      if (localMap == null) {
        return null;
      }
      try
      {
        com.tencent.beacontbs.b.b.a locala = new com.tencent.beacontbs.b.b.a();
        String str1 = (String)localMap.get("A19");
        String str2 = (String)localMap.get("A28");
        if (str1 != null) {
          locala.jdField_a_of_type_JavaLangString = str1;
        }
        locala.jdField_c_of_type_JavaLangString = paramj.b();
        locala.jdField_c_of_type_Long = paramj.b();
        if (str2 != null) {
          locala.jdField_b_of_type_JavaLangString = str2;
        }
        paramj = (String)localMap.get("A26");
        long l2 = 0L;
        long l1;
        if (paramj == null) {
          l1 = 0L;
        } else {
          l1 = Long.parseLong(paramj);
        }
        locala.jdField_b_of_type_Long = l1;
        locala.jdField_a_of_type_Boolean = Boolean.parseBoolean((String)localMap.get("A25"));
        paramj = (String)localMap.get("A27");
        if (paramj == null) {
          l1 = l2;
        } else {
          l1 = Long.parseLong(paramj);
        }
        locala.jdField_a_of_type_Long = l1;
        localMap.remove("A19");
        localMap.remove("A28");
        localMap.remove("A26");
        localMap.remove("A25");
        localMap.remove("A27");
        locala.d = a(localMap);
        return locala;
      }
      catch (Throwable paramj)
      {
        com.tencent.beacontbs.c.a.a(paramj);
        com.tencent.beacontbs.c.a.d(paramj.getMessage(), new Object[0]);
        return null;
      }
    }
    return null;
  }
  
  public static j a(Context paramContext, String paramString, boolean paramBoolean1, long paramLong1, long paramLong2, Map<String, String> paramMap, boolean paramBoolean2)
  {
    if (paramString == null) {
      return null;
    }
    Object localObject3 = c.a();
    int j = 0;
    if (localObject3 == null)
    {
      com.tencent.beacontbs.c.a.d("  CommonInfo or DeviceInfo have not been Created return null!", new Object[0]);
      return null;
    }
    com.tencent.beacontbs.event.c.a();
    long l1 = ((c)localObject3).a();
    long l2 = new Date().getTime();
    String str = ((c)localObject3).f();
    Object localObject2 = g(paramContext);
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = "null";
    }
    localObject2 = new HashMap();
    if (paramMap != null) {
      ((Map)localObject2).putAll(paramMap);
    }
    int i = j;
    if (k.a().a.c())
    {
      i = j;
      if (!paramString.startsWith("rqd_")) {
        i = 1;
      }
    }
    paramMap = d.a(paramContext);
    if (i == 0)
    {
      Object localObject4 = com.tencent.beacontbs.event.c.b();
      if ((localObject4 != null) && (!((String)localObject4).equals(""))) {
        ((Map)localObject2).put("QQ", localObject4);
      }
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append(((c)localObject3).g());
      ((Map)localObject2).put("A2", ((StringBuilder)localObject4).toString());
      ((Map)localObject2).put("A4", paramMap.c());
      ((Map)localObject2).put("A6", paramMap.b());
      ((Map)localObject2).put("A7", paramMap.d());
      ((Map)localObject2).put("A23", ((c)localObject3).i());
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append(paramMap.f());
      ((Map)localObject2).put("A31", ((StringBuilder)localObject3).toString());
      ((Map)localObject2).put("A67", a.d(paramContext));
      ((Map)localObject2).put("A76", a.a());
      ((Map)localObject2).put("A95", a.c(paramContext));
    }
    ((Map)localObject2).put("A19", localObject1);
    ((Map)localObject2).put("A28", str);
    ((Map)localObject2).put("A25", String.valueOf(paramBoolean1));
    ((Map)localObject2).put("A26", String.valueOf(paramLong1));
    ((Map)localObject2).put("A27", String.valueOf(paramLong2));
    paramContext = new j();
    paramContext.b(paramString);
    paramContext.b(l2 + l1);
    paramContext.a("UA");
    paramContext.a((Map)localObject2);
    paramContext.a(paramBoolean2);
    return paramContext;
  }
  
  private static j a(byte[] paramArrayOfByte)
  {
    try
    {
      j localj = new j();
      localj.a(new com.tencent.beacontbs.d.a(paramArrayOfByte));
      return localj;
    }
    catch (Exception paramArrayOfByte)
    {
      com.tencent.beacontbs.c.a.d(paramArrayOfByte.getMessage(), new Object[0]);
    }
    return null;
  }
  
  /* Error */
  private static Object a(byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: ldc_w 492
    //   3: iconst_0
    //   4: anewarray 4	java/lang/Object
    //   7: invokestatic 133	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   10: aconst_null
    //   11: astore_2
    //   12: aload_0
    //   13: ifnull +143 -> 156
    //   16: aload_0
    //   17: arraylength
    //   18: ifge +5 -> 23
    //   21: aconst_null
    //   22: areturn
    //   23: new 494	java/io/ByteArrayInputStream
    //   26: dup
    //   27: aload_0
    //   28: invokespecial 495	java/io/ByteArrayInputStream:<init>	([B)V
    //   31: astore_3
    //   32: new 497	java/io/ObjectInputStream
    //   35: dup
    //   36: aload_3
    //   37: invokespecial 500	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
    //   40: astore_1
    //   41: aload_1
    //   42: astore_0
    //   43: aload_1
    //   44: invokevirtual 504	java/io/ObjectInputStream:readObject	()Ljava/lang/Object;
    //   47: astore_2
    //   48: aload_1
    //   49: invokevirtual 505	java/io/ObjectInputStream:close	()V
    //   52: goto +8 -> 60
    //   55: astore_0
    //   56: aload_0
    //   57: invokestatic 68	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   60: aload_3
    //   61: invokevirtual 506	java/io/ByteArrayInputStream:close	()V
    //   64: aload_2
    //   65: areturn
    //   66: astore_0
    //   67: aload_0
    //   68: invokestatic 68	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   71: aload_2
    //   72: areturn
    //   73: astore_2
    //   74: goto +12 -> 86
    //   77: astore_0
    //   78: aload_2
    //   79: astore_1
    //   80: goto +46 -> 126
    //   83: astore_2
    //   84: aconst_null
    //   85: astore_1
    //   86: aload_1
    //   87: astore_0
    //   88: aload_2
    //   89: invokestatic 68	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   92: aload_1
    //   93: ifnull +15 -> 108
    //   96: aload_1
    //   97: invokevirtual 505	java/io/ObjectInputStream:close	()V
    //   100: goto +8 -> 108
    //   103: astore_0
    //   104: aload_0
    //   105: invokestatic 68	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   108: aload_3
    //   109: invokevirtual 506	java/io/ByteArrayInputStream:close	()V
    //   112: aconst_null
    //   113: areturn
    //   114: astore_0
    //   115: aload_0
    //   116: invokestatic 68	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   119: aconst_null
    //   120: areturn
    //   121: astore_2
    //   122: aload_0
    //   123: astore_1
    //   124: aload_2
    //   125: astore_0
    //   126: aload_1
    //   127: ifnull +15 -> 142
    //   130: aload_1
    //   131: invokevirtual 505	java/io/ObjectInputStream:close	()V
    //   134: goto +8 -> 142
    //   137: astore_1
    //   138: aload_1
    //   139: invokestatic 68	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   142: aload_3
    //   143: invokevirtual 506	java/io/ByteArrayInputStream:close	()V
    //   146: goto +8 -> 154
    //   149: astore_1
    //   150: aload_1
    //   151: invokestatic 68	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   154: aload_0
    //   155: athrow
    //   156: aconst_null
    //   157: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	158	0	paramArrayOfByte	byte[]
    //   40	91	1	localObject1	Object
    //   137	2	1	localIOException1	java.io.IOException
    //   149	2	1	localIOException2	java.io.IOException
    //   11	61	2	localObject2	Object
    //   73	6	2	localThrowable1	Throwable
    //   83	6	2	localThrowable2	Throwable
    //   121	4	2	localObject3	Object
    //   31	112	3	localByteArrayInputStream	ByteArrayInputStream
    // Exception table:
    //   from	to	target	type
    //   48	52	55	java/io/IOException
    //   60	64	66	java/io/IOException
    //   43	48	73	java/lang/Throwable
    //   32	41	77	finally
    //   32	41	83	java/lang/Throwable
    //   96	100	103	java/io/IOException
    //   108	112	114	java/io/IOException
    //   43	48	121	finally
    //   88	92	121	finally
    //   130	134	137	java/io/IOException
    //   142	146	149	java/io/IOException
  }
  
  public static String a()
  {
    try
    {
      String str = Build.MODEL;
      return str;
    }
    catch (Throwable localThrowable)
    {
      com.tencent.beacontbs.c.a.d("getDeviceName error", new Object[0]);
      com.tencent.beacontbs.c.a.a(localThrowable);
    }
    return "";
  }
  
  public static String a(Context paramContext)
  {
    String str2 = "";
    if (paramContext == null)
    {
      com.tencent.beacontbs.c.a.d("getImei but context == null!", new Object[0]);
      return "";
    }
    String str1 = str2;
    Object localObject = str2;
    try
    {
      if (a.c(paramContext))
      {
        localObject = str2;
        paramContext = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
        if (paramContext == null)
        {
          str1 = "";
        }
        else
        {
          localObject = paramContext;
          str1 = paramContext.toLowerCase();
        }
      }
      localObject = str1;
      paramContext = new StringBuilder("IMEI:");
      localObject = str1;
      paramContext.append(str1);
      localObject = str1;
      com.tencent.beacontbs.c.a.a(paramContext.toString(), new Object[0]);
      return str1;
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    com.tencent.beacontbs.c.a.d("getImei error!", new Object[0]);
    return (String)localObject;
  }
  
  public static String a(Context paramContext, int paramInt)
  {
    try
    {
      Object localObject = d.a(paramContext);
      paramContext = ((d)localObject).a();
      localObject = ((d)localObject).b();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramContext);
      localStringBuilder.append("_");
      localStringBuilder.append((String)localObject);
      localStringBuilder.append("_");
      localStringBuilder.append(new Date().getTime());
      localStringBuilder.append("_");
      localStringBuilder.append(paramInt);
      paramContext = e(localStringBuilder.toString());
      return paramContext;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    return null;
  }
  
  public static String a(String paramString)
  {
    Object localObject = paramString.replace('|', '_').trim();
    if (a((String)localObject))
    {
      if (((String)localObject).length() < 5) {
        com.tencent.beacontbs.c.a.c(" userID is invalid!! userID is too short, length < 5!", new Object[0]);
      }
      paramString = (String)localObject;
      if (((String)localObject).length() > 128) {
        return ((String)localObject).substring(0, 128);
      }
    }
    else
    {
      localObject = new StringBuilder("userID is invalid!! userID should be ASCII code in 32-126! userID:");
      ((StringBuilder)localObject).append(paramString);
      com.tencent.beacontbs.c.a.c(((StringBuilder)localObject).toString(), new Object[0]);
      paramString = "10000";
    }
    return paramString;
  }
  
  private static String a(Map<String, String> paramMap)
  {
    com.tencent.beacontbs.c.a.b("map 2 str", new Object[0]);
    if (paramMap == null) {
      return "";
    }
    Object localObject = paramMap.keySet();
    if (localObject == null) {
      return "";
    }
    if (((Set)localObject).size() > 50) {
      com.tencent.beacontbs.c.a.c("The Map<String, String> params size is more than 50, effective size is <= 50!", new Object[0]);
    }
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = ((Set)localObject).iterator();
    while (localIterator.hasNext())
    {
      String str2 = (String)localIterator.next();
      int i = str2.trim().length();
      if ((i > 0) && (a(str2)))
      {
        String str1 = str2.trim();
        localObject = str1;
        if (i > 64) {
          localObject = str1.substring(0, 64);
        }
        localStringBuffer.append("&");
        localStringBuffer.append(((String)localObject).replace("|", "%7C").replace("&", "%26").replace("=", "%3D"));
        localStringBuffer.append("=");
        localObject = (String)paramMap.get(str2);
        if (localObject != null)
        {
          str1 = ((String)localObject).trim();
          if (str1.contains(";"))
          {
            localObject = str1;
            if (str1.length() > 10240)
            {
              localObject = str1.substring(0, 10240);
              localObject = ((String)localObject).substring(0, ((String)localObject).lastIndexOf(";"));
            }
          }
          else
          {
            localObject = str1;
            if (str1.length() > 1024) {
              localObject = str1.substring(0, 1024);
            }
          }
          localStringBuffer.append(((String)localObject).replace('\n', ' ').replace('\r', ' ').replace("|", "%7C").replace("&", "%26").replace("=", "%3D"));
        }
      }
      else
      {
        localObject = new StringBuilder("The Map<String, String> params key is invalid!! key should be ASCII code in 32-126! key:");
        ((StringBuilder)localObject).append(str2);
        com.tencent.beacontbs.c.a.c(((StringBuilder)localObject).toString(), new Object[0]);
      }
    }
    paramMap = localStringBuffer.substring(1);
    localStringBuffer.setLength(0);
    return paramMap;
  }
  
  public static String a(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, String paramString)
  {
    String str;
    if (paramBoolean1)
    {
      if (paramString != null)
      {
        str = paramString;
        if (!"".equals(paramString)) {}
      }
      else
      {
        if (paramBoolean2) {
          paramString = "http://oth.eve.mdt.qq.com:8080/analytics/upload";
        } else {
          paramString = "http://oth.str.mdt.qq.com:8080/analytics/upload";
        }
        str = paramString;
      }
      if (paramBoolean3) {
        return "http://183.36.108.226:8080/analytics/upload";
      }
      return str;
    }
    if ((paramString != null) && (!"".equals(paramString)))
    {
      int i = paramString.indexOf("http://");
      if (i != -1) {
        paramString = paramString.substring(i + 7, paramString.indexOf(":", 7));
      }
    }
    else
    {
      paramString = "";
    }
    if (paramString != null)
    {
      str = paramString;
      if (!"".equals(paramString)) {}
    }
    else
    {
      if (paramBoolean2) {
        paramString = "oth.eve.mdt.qq.com";
      } else {
        paramString = "oth.str.mdt.qq.com";
      }
      str = paramString;
    }
    if (paramBoolean3) {
      return "183.36.108.226";
    }
    return str;
  }
  
  public static Date a(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.trim().length() <= 0) {
        return null;
      }
      try
      {
        paramString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(paramString);
        return paramString;
      }
      catch (ParseException paramString)
      {
        com.tencent.beacontbs.c.a.a(paramString);
        return null;
      }
    }
    return null;
  }
  
  public static HashSet<String> a(ArrayList<String> paramArrayList)
  {
    int i = paramArrayList.size();
    if ((paramArrayList != null) && (i > 0))
    {
      HashSet localHashSet = new HashSet(i);
      paramArrayList = paramArrayList.iterator();
      while (paramArrayList.hasNext()) {
        localHashSet.add((String)paramArrayList.next());
      }
      return localHashSet;
    }
    return null;
  }
  
  public static List<j> a(Context paramContext, int paramInt)
  {
    com.tencent.beacontbs.c.a.a(" RecordDAO.queryRecentRecord() start", new Object[0]);
    if (paramContext == null)
    {
      com.tencent.beacontbs.c.a.d(" queryRecentRecord() have null args!", new Object[0]);
      return null;
    }
    List localList = com.tencent.beacontbs.a.a.a.a(paramContext, new int[] { 1 }, paramInt);
    if (localList != null)
    {
      if (localList.size() <= 0) {
        return null;
      }
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        com.tencent.beacontbs.a.a.a locala = (com.tencent.beacontbs.a.a.a)localIterator.next();
        try
        {
          Object localObject1 = Base64.decode(locala.jdField_a_of_type_JavaLangString, 0);
          if ((localObject1 != null) && (localObject1.length > 0) && (locala.jdField_b_of_type_Long == localObject1.length))
          {
            if (Build.VERSION.SDK_INT == 22)
            {
              localObject1 = a((byte[])localObject1);
              com.tencent.beacontbs.c.a.a("Using level 22 deSerializable logic.", new Object[0]);
            }
            else
            {
              localObject1 = a((byte[])localObject1);
            }
            if ((localObject1 != null) && (j.class.isInstance(localObject1)))
            {
              localObject1 = (j)j.class.cast(localObject1);
              ((j)localObject1).a(locala.jdField_a_of_type_Long);
              localArrayList.add(localObject1);
              localIterator.remove();
            }
          }
        }
        catch (Throwable localThrowable)
        {
          com.tencent.beacontbs.c.a.a(localThrowable);
          com.tencent.beacontbs.c.a.d(" query have error!", new Object[0]);
        }
      }
      if (localList.size() > 0)
      {
        Object localObject2 = new StringBuilder(" there are error datas ,should be remove ");
        ((StringBuilder)localObject2).append(localList.size());
        com.tencent.beacontbs.c.a.a(((StringBuilder)localObject2).toString(), new Object[0]);
        localObject2 = new Long[localList.size()];
        paramInt = 0;
        while (paramInt < localList.size())
        {
          localObject2[paramInt] = Long.valueOf(((com.tencent.beacontbs.a.a.a)localList.get(paramInt)).jdField_a_of_type_Long);
          paramInt += 1;
        }
        com.tencent.beacontbs.a.a.a.a(paramContext, (Long[])localObject2);
      }
      com.tencent.beacontbs.c.a.a(" RecordDAO.queryRecentRecord() end", new Object[0]);
      return localArrayList;
    }
    return null;
  }
  
  public static boolean a(Context paramContext)
  {
    paramContext = a(paramContext);
    return (paramContext != null) && (paramContext.getType() == 1);
  }
  
  public static boolean a(Context paramContext, com.tencent.beacontbs.a.b.g paramg)
  {
    boolean bool = false;
    if (paramContext == null) {
      try
      {
        com.tencent.beacontbs.c.a.c("context == null || bean == null}", new Object[0]);
        return false;
      }
      finally
      {
        break label195;
      }
    }
    try
    {
      paramContext = com.tencent.beacontbs.a.a.c.a(paramContext).getWritableDatabase();
      if (paramContext == null)
      {
        com.tencent.beacontbs.c.a.d("get db fail!,return false ", new Object[0]);
        return false;
      }
      ContentValues localContentValues = new ContentValues();
      if (paramg.a() >= 0L) {
        localContentValues.put("_id", Long.valueOf(paramg.a()));
      }
      localContentValues.put("_key", Integer.valueOf(paramg.a()));
      localContentValues.put("_datas", paramg.a());
      long l = paramContext.replace("t_strategy", "_id", localContentValues);
      if (l < 0L)
      {
        com.tencent.beacontbs.c.a.c("insert failure! return false ", new Object[0]);
      }
      else
      {
        paramg.a(l);
        com.tencent.beacontbs.c.a.e("update strategy  %d true ", new Object[] { Integer.valueOf(paramg.a()) });
        bool = true;
      }
    }
    catch (Throwable paramContext)
    {
      com.tencent.beacontbs.c.a.d("Error strategy update!  %s", new Object[] { paramContext.toString() });
    }
    return bool;
    label195:
    throw paramContext;
  }
  
  /* Error */
  public static boolean a(Context paramContext, String paramString, Object[] paramArrayOfObject)
  {
    // Byte code:
    //   0: getstatic 779	com/tencent/beacontbs/a/a/c:a	Ljava/lang/Object;
    //   3: astore 4
    //   5: aload 4
    //   7: monitorenter
    //   8: iconst_0
    //   9: istore_3
    //   10: aload_0
    //   11: ifnonnull +12 -> 23
    //   14: aload 4
    //   16: monitorexit
    //   17: iconst_0
    //   18: ireturn
    //   19: astore_0
    //   20: goto +154 -> 174
    //   23: aload_0
    //   24: invokestatic 27	com/tencent/beacontbs/a/a/c:a	(Landroid/content/Context;)Lcom/tencent/beacontbs/a/a/c;
    //   27: invokevirtual 31	com/tencent/beacontbs/a/a/c:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   30: astore_0
    //   31: new 234	java/lang/StringBuilder
    //   34: dup
    //   35: ldc_w 781
    //   38: invokespecial 531	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   41: astore 5
    //   43: aload 5
    //   45: aload_1
    //   46: invokevirtual 242	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: pop
    //   50: aload 5
    //   52: ldc_w 783
    //   55: invokevirtual 242	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   58: pop
    //   59: aload_0
    //   60: ldc_w 785
    //   63: aload 5
    //   65: invokevirtual 247	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   68: aconst_null
    //   69: invokevirtual 60	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   72: pop
    //   73: new 750	android/content/ContentValues
    //   76: dup
    //   77: invokespecial 751	android/content/ContentValues:<init>	()V
    //   80: astore 5
    //   82: aload 5
    //   84: ldc 42
    //   86: aload_1
    //   87: invokevirtual 788	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   90: aload 5
    //   92: ldc_w 790
    //   95: aload_2
    //   96: iconst_0
    //   97: aaload
    //   98: checkcast 50	java/lang/String
    //   101: invokevirtual 788	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   104: aload 5
    //   106: ldc_w 792
    //   109: aload_2
    //   110: iconst_1
    //   111: aaload
    //   112: checkcast 379	java/lang/Long
    //   115: invokevirtual 755	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   118: aload 5
    //   120: ldc_w 794
    //   123: new 415	java/util/Date
    //   126: dup
    //   127: invokespecial 416	java/util/Date:<init>	()V
    //   130: invokevirtual 419	java/util/Date:getTime	()J
    //   133: invokestatic 733	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   136: invokevirtual 755	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   139: aload_0
    //   140: ldc_w 785
    //   143: aconst_null
    //   144: aload 5
    //   146: invokevirtual 797	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   149: lconst_0
    //   150: lcmp
    //   151: ifge +16 -> 167
    //   154: ldc_w 799
    //   157: iconst_0
    //   158: anewarray 4	java/lang/Object
    //   161: invokestatic 36	com/tencent/beacontbs/c/a:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   164: goto +5 -> 169
    //   167: iconst_1
    //   168: istore_3
    //   169: aload 4
    //   171: monitorexit
    //   172: iload_3
    //   173: ireturn
    //   174: aload 4
    //   176: monitorexit
    //   177: aload_0
    //   178: athrow
    //   179: astore_0
    //   180: goto -11 -> 169
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	183	0	paramContext	Context
    //   0	183	1	paramString	String
    //   0	183	2	paramArrayOfObject	Object[]
    //   9	164	3	bool	boolean
    //   3	172	4	localObject1	Object
    //   41	104	5	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   14	17	19	finally
    //   23	164	19	finally
    //   169	172	19	finally
    //   174	177	19	finally
    //   23	164	179	java/lang/Exception
  }
  
  public static boolean a(String paramString)
  {
    int i = paramString.length();
    boolean bool = true;
    for (;;)
    {
      int j = i - 1;
      if (j < 0) {
        break;
      }
      int k = paramString.charAt(j);
      if (k >= 32)
      {
        i = j;
        if (k < 127) {}
      }
      else
      {
        bool = false;
        i = j;
      }
    }
    return bool;
  }
  
  private static byte[] a(j paramj)
  {
    try
    {
      com.tencent.beacontbs.d.b localb = new com.tencent.beacontbs.d.b();
      paramj.a(localb);
      paramj = localb.a();
      return paramj;
    }
    catch (Exception paramj)
    {
      com.tencent.beacontbs.c.a.d(paramj.getMessage(), new Object[0]);
    }
    return null;
  }
  
  /* Error */
  private static byte[] a(Object paramObject)
  {
    // Byte code:
    //   0: ldc_w 816
    //   3: iconst_0
    //   4: anewarray 4	java/lang/Object
    //   7: invokestatic 133	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   10: aload_0
    //   11: ifnull +161 -> 172
    //   14: ldc_w 818
    //   17: aload_0
    //   18: invokevirtual 715	java/lang/Class:isInstance	(Ljava/lang/Object;)Z
    //   21: ifne +6 -> 27
    //   24: goto +148 -> 172
    //   27: new 820	java/io/ByteArrayOutputStream
    //   30: dup
    //   31: invokespecial 821	java/io/ByteArrayOutputStream:<init>	()V
    //   34: astore_3
    //   35: new 823	java/io/ObjectOutputStream
    //   38: dup
    //   39: aload_3
    //   40: invokespecial 826	java/io/ObjectOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   43: astore_2
    //   44: aload_2
    //   45: astore_1
    //   46: aload_2
    //   47: aload_0
    //   48: invokevirtual 830	java/io/ObjectOutputStream:writeObject	(Ljava/lang/Object;)V
    //   51: aload_2
    //   52: astore_1
    //   53: aload_2
    //   54: invokevirtual 833	java/io/ObjectOutputStream:flush	()V
    //   57: aload_2
    //   58: astore_1
    //   59: aload_3
    //   60: invokevirtual 836	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   63: astore_0
    //   64: aload_2
    //   65: invokevirtual 837	java/io/ObjectOutputStream:close	()V
    //   68: goto +8 -> 76
    //   71: astore_1
    //   72: aload_1
    //   73: invokestatic 68	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   76: aload_3
    //   77: invokevirtual 838	java/io/ByteArrayOutputStream:close	()V
    //   80: aload_0
    //   81: areturn
    //   82: astore_1
    //   83: aload_1
    //   84: invokestatic 68	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   87: aload_0
    //   88: areturn
    //   89: astore_1
    //   90: aload_2
    //   91: astore_0
    //   92: aload_1
    //   93: astore_2
    //   94: goto +12 -> 106
    //   97: astore_0
    //   98: aconst_null
    //   99: astore_1
    //   100: goto +42 -> 142
    //   103: astore_2
    //   104: aconst_null
    //   105: astore_0
    //   106: aload_0
    //   107: astore_1
    //   108: aload_2
    //   109: invokestatic 68	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   112: aload_0
    //   113: ifnull +15 -> 128
    //   116: aload_0
    //   117: invokevirtual 837	java/io/ObjectOutputStream:close	()V
    //   120: goto +8 -> 128
    //   123: astore_0
    //   124: aload_0
    //   125: invokestatic 68	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   128: aload_3
    //   129: invokevirtual 838	java/io/ByteArrayOutputStream:close	()V
    //   132: aconst_null
    //   133: areturn
    //   134: astore_0
    //   135: aload_0
    //   136: invokestatic 68	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   139: aconst_null
    //   140: areturn
    //   141: astore_0
    //   142: aload_1
    //   143: ifnull +15 -> 158
    //   146: aload_1
    //   147: invokevirtual 837	java/io/ObjectOutputStream:close	()V
    //   150: goto +8 -> 158
    //   153: astore_1
    //   154: aload_1
    //   155: invokestatic 68	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   158: aload_3
    //   159: invokevirtual 838	java/io/ByteArrayOutputStream:close	()V
    //   162: goto +8 -> 170
    //   165: astore_1
    //   166: aload_1
    //   167: invokestatic 68	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   170: aload_0
    //   171: athrow
    //   172: ldc_w 840
    //   175: iconst_0
    //   176: anewarray 4	java/lang/Object
    //   179: invokestatic 22	com/tencent/beacontbs/c/a:c	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   182: aconst_null
    //   183: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	184	0	paramObject	Object
    //   45	14	1	localObject1	Object
    //   71	2	1	localIOException1	java.io.IOException
    //   82	2	1	localIOException2	java.io.IOException
    //   89	4	1	localThrowable1	Throwable
    //   99	48	1	localObject2	Object
    //   153	2	1	localIOException3	java.io.IOException
    //   165	2	1	localIOException4	java.io.IOException
    //   43	51	2	localObject3	Object
    //   103	6	2	localThrowable2	Throwable
    //   34	125	3	localByteArrayOutputStream	ByteArrayOutputStream
    // Exception table:
    //   from	to	target	type
    //   64	68	71	java/io/IOException
    //   76	80	82	java/io/IOException
    //   46	51	89	java/lang/Throwable
    //   53	57	89	java/lang/Throwable
    //   59	64	89	java/lang/Throwable
    //   35	44	97	finally
    //   35	44	103	java/lang/Throwable
    //   116	120	123	java/io/IOException
    //   128	132	134	java/io/IOException
    //   46	51	141	finally
    //   53	57	141	finally
    //   59	64	141	finally
    //   108	112	141	finally
    //   146	150	153	java/io/IOException
    //   158	162	165	java/io/IOException
  }
  
  public static byte[] a(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    try
    {
      Object localObject = new X509EncodedKeySpec(Base64.decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsAxNCSLyNUCOP1QqYStE8ZeiU\nv4afaMqEmoLCKb0mUZYvYOoVN7LPMi2IVY2MRaFJvuND3glVw1RDm2VJJtjQkwUd\n3kpR9TrHAf7UQOVTpNo3Vi7pXTOqZ6bh3ZA/fs56jDCCKV6+wT/pCeu8N6vVnPrD\nz3SdHIeNeWb/woazCwIDAQAB", 0));
      localObject = (RSAPublicKey)KeyFactory.getInstance("RSA", "BC").generatePublic((KeySpec)localObject);
      Cipher localCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      localCipher.init(1, (Key)localObject);
      paramString = localCipher.doFinal(paramString.getBytes());
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  private static byte[] a(String paramString, byte[] paramArrayOfByte)
    throws Exception
  {
    if ((paramString != null) && (paramArrayOfByte != null))
    {
      int i = paramString.length();
      while (i < 16)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append("0");
        paramString = ((StringBuilder)localObject).toString();
        i += 1;
      }
      paramString = paramString.substring(0, 16);
      Object localObject = new SecretKeySpec(paramString.getBytes(), "AES");
      Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      localCipher.init(2, (Key)localObject, new IvParameterSpec(paramString.getBytes()));
      return localCipher.doFinal(paramArrayOfByte);
    }
    return null;
  }
  
  private static byte[] a(byte[] paramArrayOfByte)
    throws Exception
  {
    paramArrayOfByte = new ByteArrayInputStream(paramArrayOfByte);
    GZIPInputStream localGZIPInputStream = new GZIPInputStream(paramArrayOfByte);
    byte[] arrayOfByte = new byte['Ѐ'];
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    for (;;)
    {
      int i = localGZIPInputStream.read(arrayOfByte, 0, 1024);
      if (i == -1) {
        break;
      }
      localByteArrayOutputStream.write(arrayOfByte, 0, i);
    }
    arrayOfByte = localByteArrayOutputStream.toByteArray();
    localByteArrayOutputStream.flush();
    localByteArrayOutputStream.close();
    localGZIPInputStream.close();
    paramArrayOfByte.close();
    return arrayOfByte;
  }
  
  private static byte[] a(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte != null)
    {
      if (paramInt == -1) {
        return paramArrayOfByte;
      }
      com.tencent.beacontbs.c.a.b("zp: %s len: %s", new Object[] { Integer.valueOf(paramInt), Integer.valueOf(paramArrayOfByte.length) });
      if (paramInt != 1) {}
    }
    try
    {
      localByteArrayOutputStream = new ByteArrayOutputStream();
      localObject = new ZipOutputStream(localByteArrayOutputStream);
      ZipEntry localZipEntry = new ZipEntry("zip");
      localZipEntry.setSize(paramArrayOfByte.length);
      ((ZipOutputStream)localObject).putNextEntry(localZipEntry);
      ((ZipOutputStream)localObject).write(paramArrayOfByte);
      ((ZipOutputStream)localObject).closeEntry();
      ((ZipOutputStream)localObject).close();
      paramArrayOfByte = localByteArrayOutputStream.toByteArray();
      localByteArrayOutputStream.close();
      return paramArrayOfByte;
    }
    catch (Throwable paramArrayOfByte)
    {
      ByteArrayOutputStream localByteArrayOutputStream;
      Object localObject;
      for (;;) {}
    }
    if (paramInt == 2)
    {
      localByteArrayOutputStream = new ByteArrayOutputStream();
      localObject = new GZIPOutputStream(localByteArrayOutputStream);
      ((GZIPOutputStream)localObject).write(paramArrayOfByte);
      ((GZIPOutputStream)localObject).finish();
      ((GZIPOutputStream)localObject).close();
      paramArrayOfByte = localByteArrayOutputStream.toByteArray();
      localByteArrayOutputStream.close();
      return paramArrayOfByte;
      com.tencent.beacontbs.c.a.a(paramArrayOfByte);
      com.tencent.beacontbs.c.a.d("err zp : %s", new Object[] { paramArrayOfByte.toString() });
      return null;
    }
    return null;
    return paramArrayOfByte;
  }
  
  public static byte[] a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, String paramString)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    try
    {
      paramArrayOfByte = a(a(paramArrayOfByte, paramInt1), paramInt2, paramString);
      return paramArrayOfByte;
    }
    catch (Throwable paramArrayOfByte)
    {
      com.tencent.beacontbs.c.a.a(paramArrayOfByte);
    }
    return null;
  }
  
  private static byte[] a(byte[] paramArrayOfByte, int paramInt, String paramString)
  {
    if (paramArrayOfByte != null)
    {
      if (paramInt == -1) {
        return paramArrayOfByte;
      }
      com.tencent.beacontbs.c.a.b("enD:} %d %d", new Object[] { Integer.valueOf(paramArrayOfByte.length), Integer.valueOf(paramInt) });
      if (paramInt == 1)
      {
        if (paramString == null) {
          break label151;
        }
        if (paramArrayOfByte == null) {
          return null;
        }
      }
      try
      {
        Cipher localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec localDESKeySpec = new DESKeySpec(paramString.getBytes("UTF-8"));
        localCipher.init(1, SecretKeyFactory.getInstance("DES").generateSecret(localDESKeySpec), new IvParameterSpec(paramString.getBytes("UTF-8")));
        return localCipher.doFinal(paramArrayOfByte);
      }
      catch (Throwable paramArrayOfByte)
      {
        com.tencent.beacontbs.c.a.a(paramArrayOfByte);
        com.tencent.beacontbs.c.a.d("err enD: %s", new Object[] { paramArrayOfByte.toString() });
        return null;
      }
      if (paramInt == 3)
      {
        paramArrayOfByte = b(paramString, paramArrayOfByte);
        return paramArrayOfByte;
      }
      return null;
    }
    else
    {
      return paramArrayOfByte;
    }
    label151:
    return null;
  }
  
  public static Long[] a(Context paramContext, List<j> paramList)
  {
    int j = 0;
    com.tencent.beacontbs.c.a.a(" RecordDAO.insertList() start", new Object[0]);
    if ((paramContext != null) && (paramList != null))
    {
      int k = paramList.size();
      if (k == 0)
      {
        com.tencent.beacontbs.c.a.b(" list siez == 0 , return true!", new Object[0]);
        return null;
      }
      Long[] arrayOfLong = new Long[k];
      ArrayList localArrayList = new ArrayList();
      int i = 0;
      while (i < k)
      {
        j localj = (j)paramList.get(i);
        if ((localj != null) && (localj.a() != null) && (localj.a().equals("UA"))) {
          try
          {
            byte[] arrayOfByte;
            if (Build.VERSION.SDK_INT == 22)
            {
              arrayOfByte = a(localj);
              com.tencent.beacontbs.c.a.a("Using level 22 serializable logic.", new Object[0]);
            }
            else
            {
              arrayOfByte = a(localj);
            }
            localArrayList.add(new com.tencent.beacontbs.a.a.a(localj.b(), Base64.encodeToString(arrayOfByte, 0), arrayOfByte.length));
          }
          catch (Throwable localThrowable)
          {
            com.tencent.beacontbs.c.a.a(localThrowable);
          }
        }
        com.tencent.beacontbs.c.a.d(" bean's type is error!", new Object[0]);
        i += 1;
      }
      if (!com.tencent.beacontbs.a.a.a.a(paramContext, localArrayList)) {
        return null;
      }
      paramContext = localArrayList.iterator();
      i = j;
      while (paramContext.hasNext())
      {
        paramList = (com.tencent.beacontbs.a.a.a)paramContext.next();
        if (i < k) {
          arrayOfLong[i] = Long.valueOf(paramList.jdField_a_of_type_Long);
        }
        i += 1;
      }
      return arrayOfLong;
    }
    com.tencent.beacontbs.c.a.d(" insertList() have null args!", new Object[0]);
    return null;
  }
  
  public static Object[] a(Context paramContext, String paramString)
  {
    Object localObject = com.tencent.beacontbs.a.a.c.a;
    str = null;
    if (paramContext == null) {
      try
      {
        com.tencent.beacontbs.c.a.c("context == null ", new Object[0]);
        return null;
      }
      finally
      {
        break label259;
      }
    }
    do
    {
      for (;;)
      {
        try
        {
          paramContext = com.tencent.beacontbs.a.a.c.a(paramContext).getWritableDatabase();
          if (paramContext == null)
          {
            com.tencent.beacontbs.c.a.c("getWritableDatabase fail! ", new Object[0]);
            return null;
          }
          StringBuilder localStringBuilder = new StringBuilder("_key = '");
          localStringBuilder.append(paramString);
          localStringBuilder.append("'");
          paramContext = paramContext.query("t_conf", null, localStringBuilder.toString(), null, null, null, null);
          if (paramContext == null) {}
        }
        catch (Exception paramContext) {}finally
        {
          continue;
        }
        try
        {
          if (paramContext.moveToNext())
          {
            str = paramContext.getString(paramContext.getColumnIndex("_value"));
            long l = paramContext.getLong(paramContext.getColumnIndex("_vdate"));
            if ((paramContext != null) && (!paramContext.isClosed())) {
              paramContext.close();
            }
            return new Object[] { paramString, str, Long.valueOf(l) };
          }
          if ((paramContext == null) || (paramContext.isClosed())) {
            continue;
          }
          paramContext.close();
        }
        catch (Exception paramString) {}finally
        {
          continue;
          paramString = finally;
          paramContext = str;
          continue;
          paramContext = null;
        }
      }
      if ((paramContext != null) && (!paramContext.isClosed())) {
        paramContext.close();
      }
      throw paramString;
    } while ((paramContext != null) && (!paramContext.isClosed()));
    return null;
    label259:
    throw paramContext;
  }
  
  public static int b(Context paramContext)
  {
    com.tencent.beacontbs.c.a.a(" RecordDAO.countRecordNum() start", new Object[0]);
    if (paramContext == null)
    {
      com.tencent.beacontbs.c.a.d(" countRecordNum() have null args!", new Object[0]);
      return -1;
    }
    return com.tencent.beacontbs.a.a.a.b(paramContext, new int[] { 1 });
  }
  
  public static String b()
  {
    try
    {
      String str = Build.VERSION.RELEASE;
      return str;
    }
    catch (Throwable localThrowable)
    {
      com.tencent.beacontbs.c.a.d("getVersion error", new Object[0]);
      com.tencent.beacontbs.c.a.a(localThrowable);
    }
    return "";
  }
  
  public static String b(Context paramContext)
  {
    String str = "";
    if (paramContext == null)
    {
      com.tencent.beacontbs.c.a.d("getImsi but context == null!", new Object[0]);
      return "";
    }
    Object localObject = str;
    try
    {
      if (a.c(paramContext))
      {
        localObject = str;
        paramContext = ((TelephonyManager)paramContext.getSystemService("phone")).getSubscriberId();
        if (paramContext == null) {
          return "";
        }
        localObject = paramContext;
        paramContext = paramContext.toLowerCase();
        return paramContext;
      }
      return "";
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    com.tencent.beacontbs.c.a.d("getImsi error!", new Object[0]);
    return (String)localObject;
  }
  
  public static String b(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.trim().length() == 0) {
        return "unknown";
      }
      Object localObject = paramString.trim();
      int i = ((String)localObject).length();
      int j;
      do
      {
        i -= 1;
        if (i < 0) {
          break;
        }
        j = ((String)localObject).charAt(i);
      } while ((j >= 48) && (j <= 57));
      i = 0;
      break label61;
      i = 1;
      label61:
      if (i != 0)
      {
        localObject = paramString.trim();
        paramString = (String)localObject;
        if (((String)localObject).length() > 16) {
          paramString = ((String)localObject).substring(0, 16);
        }
        return paramString;
      }
      localObject = new StringBuilder("channelID is invalid!! channelID should be Numeric! channelID:");
      ((StringBuilder)localObject).append(paramString);
      com.tencent.beacontbs.c.a.c(((StringBuilder)localObject).toString(), new Object[0]);
      return "unknown";
    }
    return "unknown";
  }
  
  public static boolean b(Context paramContext)
  {
    paramContext = a(paramContext);
    return (paramContext != null) && (paramContext.isConnected());
  }
  
  private static byte[] b(String paramString, byte[] paramArrayOfByte)
    throws Exception, NoSuchAlgorithmException
  {
    if ((paramString != null) && (paramArrayOfByte != null))
    {
      int i = paramString.length();
      while (i < 16)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append("0");
        paramString = ((StringBuilder)localObject).toString();
        i += 1;
      }
      paramString = paramString.substring(0, 16);
      Object localObject = new SecretKeySpec(paramString.getBytes(), "AES");
      Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      localCipher.init(1, (Key)localObject, new IvParameterSpec(paramString.getBytes()));
      return localCipher.doFinal(paramArrayOfByte);
    }
    return null;
  }
  
  private static byte[] b(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte != null)
    {
      if (paramInt == -1) {
        return paramArrayOfByte;
      }
      com.tencent.beacontbs.c.a.b("unzp: %s len: %s", new Object[] { Integer.valueOf(paramInt), Integer.valueOf(paramArrayOfByte.length) });
      if (paramInt != 1) {}
    }
    try
    {
      localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
      ZipInputStream localZipInputStream = new ZipInputStream(localByteArrayInputStream);
      paramArrayOfByte = null;
      while (localZipInputStream.getNextEntry() != null)
      {
        paramArrayOfByte = new byte['Ѐ'];
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        for (;;)
        {
          paramInt = localZipInputStream.read(paramArrayOfByte, 0, 1024);
          if (paramInt == -1) {
            break;
          }
          localByteArrayOutputStream.write(paramArrayOfByte, 0, paramInt);
        }
        paramArrayOfByte = localByteArrayOutputStream.toByteArray();
        localByteArrayOutputStream.flush();
        localByteArrayOutputStream.close();
      }
      localZipInputStream.close();
      localByteArrayInputStream.close();
      return paramArrayOfByte;
    }
    catch (Throwable localThrowable)
    {
      ByteArrayInputStream localByteArrayInputStream;
      for (;;) {}
    }
    if (paramInt == 2)
    {
      paramArrayOfByte = a(paramArrayOfByte);
      return paramArrayOfByte;
      com.tencent.beacontbs.c.a.a(localByteArrayInputStream);
      paramArrayOfByte = new StringBuilder("err unzp}");
      paramArrayOfByte.append(localByteArrayInputStream.toString());
      com.tencent.beacontbs.c.a.d(paramArrayOfByte.toString(), new Object[0]);
      return null;
    }
    return null;
    return paramArrayOfByte;
  }
  
  public static byte[] b(byte[] paramArrayOfByte, int paramInt1, int paramInt2, String paramString)
  {
    try
    {
      paramArrayOfByte = b(b(paramArrayOfByte, paramInt2, paramString), paramInt1);
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte)
    {
      com.tencent.beacontbs.c.a.a(paramArrayOfByte);
    }
    return null;
  }
  
  private static byte[] b(byte[] paramArrayOfByte, int paramInt, String paramString)
  {
    if (paramArrayOfByte != null)
    {
      if (paramInt == -1) {
        return paramArrayOfByte;
      }
      if (paramInt == 1)
      {
        if (paramString == null) {
          break label126;
        }
        if (paramArrayOfByte == null) {
          return null;
        }
      }
      try
      {
        Cipher localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec localDESKeySpec = new DESKeySpec(paramString.getBytes("UTF-8"));
        localCipher.init(2, SecretKeyFactory.getInstance("DES").generateSecret(localDESKeySpec), new IvParameterSpec(paramString.getBytes("UTF-8")));
        return localCipher.doFinal(paramArrayOfByte);
      }
      catch (Throwable paramArrayOfByte)
      {
        com.tencent.beacontbs.c.a.a(paramArrayOfByte);
        com.tencent.beacontbs.c.a.d("err unD: %s", new Object[] { paramArrayOfByte.toString() });
        return null;
      }
      if (paramInt == 3)
      {
        paramArrayOfByte = a(paramString, paramArrayOfByte);
        return paramArrayOfByte;
      }
      return null;
    }
    else
    {
      return paramArrayOfByte;
    }
    label126:
    return null;
  }
  
  public static String c()
  {
    try
    {
      String str = Build.VERSION.SDK;
      return str;
    }
    catch (Throwable localThrowable)
    {
      com.tencent.beacontbs.c.a.d("getApiLevel error", new Object[0]);
      com.tencent.beacontbs.c.a.a(localThrowable);
    }
    return "";
  }
  
  public static String c(Context paramContext)
  {
    Object localObject = "";
    if (paramContext == null)
    {
      com.tencent.beacontbs.c.a.d("getAndroidId but context == null!", new Object[0]);
      return "";
    }
    try
    {
      paramContext = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
      if (paramContext == null) {
        return "";
      }
      localObject = paramContext;
      paramContext = paramContext.toLowerCase();
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      com.tencent.beacontbs.c.a.d("getAndroidId error!", new Object[0]);
      com.tencent.beacontbs.c.a.a(paramContext);
    }
    return (String)localObject;
  }
  
  public static String c(String paramString)
  {
    String str2 = "";
    String str1 = str2;
    try
    {
      FileInputStream localFileInputStream = new FileInputStream(paramString);
      str1 = str2;
      byte[] arrayOfByte = new byte[localFileInputStream.available()];
      str1 = str2;
      localFileInputStream.read(arrayOfByte);
      str1 = str2;
      str2 = EncodingUtils.getString(arrayOfByte, "UTF-8");
      str1 = str2;
      localFileInputStream.close();
      return str2;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    com.tencent.beacontbs.c.a.d("Read file %s failed.", new Object[] { paramString });
    return str1;
  }
  
  public static String d()
  {
    try
    {
      String str = Build.BRAND;
      return str;
    }
    catch (Throwable localThrowable)
    {
      com.tencent.beacontbs.c.a.d("getBrand error!", new Object[0]);
      com.tencent.beacontbs.c.a.a(localThrowable);
    }
    return null;
  }
  
  public static String d(Context paramContext)
  {
    String str = "";
    int i = 0;
    if (paramContext == null)
    {
      com.tencent.beacontbs.c.a.d("getMacAddress but context == null!", new Object[0]);
      return "";
    }
    Object localObject = str;
    try
    {
      if (Integer.valueOf(Build.VERSION.SDK).intValue() < 23)
      {
        localObject = str;
        paramContext = ((WifiManager)paramContext.getSystemService("wifi")).getConnectionInfo().getMacAddress();
        if (paramContext == null) {
          return "";
        }
        localObject = paramContext;
        return paramContext.toLowerCase();
      }
      for (localObject = str; i < 2; localObject = paramContext)
      {
        paramContext = c(new String[] { "/sys/class/net/wlan0/address", "/sys/devices/virtual/net/wlan0/address" }[i]).toString().trim();
        if (paramContext != null)
        {
          localObject = paramContext;
          if (paramContext.length() > 0)
          {
            localObject = paramContext;
            paramContext = paramContext.toLowerCase();
            return paramContext;
          }
        }
        i += 1;
      }
      return (String)localObject;
    }
    catch (Throwable paramContext)
    {
      com.tencent.beacontbs.c.a.a(paramContext);
    }
    return (String)localObject;
  }
  
  private static String d(String paramString)
  {
    try
    {
      Object localObject = MessageDigest.getInstance("MD5").digest(paramString.getBytes());
      StringBuffer localStringBuffer = new StringBuffer();
      int j = localObject.length;
      int i = 0;
      while (i < j)
      {
        int k = localObject[i] & 0xFF;
        if (k < 16) {
          localStringBuffer.append(0);
        }
        localStringBuffer.append(Integer.toHexString(k));
        i += 1;
      }
      localObject = localStringBuffer.toString();
      return (String)localObject;
    }
    catch (Exception localException)
    {
      com.tencent.beacontbs.c.a.a(localException);
    }
    return paramString;
  }
  
  public static String e()
  {
    try
    {
      Object localObject = new StatFs(Environment.getDataDirectory().getPath());
      long l1 = ((StatFs)localObject).getBlockSize();
      long l2 = ((StatFs)localObject).getBlockCount();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(l2 * l1 / 1024L / 1024L);
      localObject = ((StringBuilder)localObject).toString();
      return (String)localObject;
    }
    catch (Throwable localThrowable)
    {
      com.tencent.beacontbs.c.a.d("getDisplayMetrics error!", new Object[0]);
      com.tencent.beacontbs.c.a.a(localThrowable);
    }
    return null;
  }
  
  public static String e(Context paramContext)
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo == null) {
        return "unknown";
      }
      if (localNetworkInfo.getType() == 1) {
        return "wifi";
      }
      if (localNetworkInfo.getType() == 0)
      {
        paramContext = (TelephonyManager)paramContext.getSystemService("phone");
        if (paramContext != null) {
          switch (paramContext.getNetworkType())
          {
          case 1: 
            return "GPRS";
          }
        }
      }
    }
    catch (Exception paramContext)
    {
      com.tencent.beacontbs.c.a.a(paramContext);
    }
    return "unknown";
    return "unknown";
    return "HSPA+";
    return "eHRPD";
    return "LTE";
    return "EVDO_B";
    return "iDen";
    return "HSPA";
    return "HSUPA";
    return "HSDPA";
    return "1xRTT";
    return "EVDO_A";
    return "EVDO_0";
    return "CDMA";
    return "UMTS";
    return "EDGE";
  }
  
  private static String e(String paramString)
  {
    String str = d(paramString);
    paramString = str;
    if (str != null) {}
    try
    {
      paramString = str.substring(8, 24);
      return paramString;
    }
    catch (Exception paramString) {}
    return str;
  }
  
  public static String f()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new FileReader(new File("/sys/block/mmcblk0/device/type")));
      String str = localBufferedReader.readLine();
      localBufferedReader.close();
      localStringBuilder.append(str);
      localStringBuilder.append(",");
      localBufferedReader = new BufferedReader(new FileReader(new File("/sys/block/mmcblk0/device/name")));
      str = localBufferedReader.readLine();
      localBufferedReader.close();
      localStringBuilder.append(str);
      localStringBuilder.append(",");
      localBufferedReader = new BufferedReader(new FileReader(new File("/sys/block/mmcblk0/device/cid")));
      str = localBufferedReader.readLine();
      localBufferedReader.close();
      localStringBuilder.append(str);
    }
    catch (Exception localException)
    {
      com.tencent.beacontbs.c.a.c(localException.getMessage(), new Object[0]);
    }
    return localStringBuilder.toString();
  }
  
  public static String f(Context paramContext)
  {
    try
    {
      String str = d.a(paramContext).d();
      paramContext = "";
      Object localObject = c.a();
      if (localObject != null) {
        paramContext = ((c)localObject).h();
      }
      int i = (int)(Math.random() * 2.147483647E9D);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramContext);
      ((StringBuilder)localObject).append("_");
      ((StringBuilder)localObject).append(str);
      ((StringBuilder)localObject).append("_");
      ((StringBuilder)localObject).append(new Date().getTime());
      ((StringBuilder)localObject).append("_");
      ((StringBuilder)localObject).append(i);
      paramContext = e(((StringBuilder)localObject).toString());
      return paramContext;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    return null;
  }
  
  public static String g()
  {
    try
    {
      String str = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
      return str;
    }
    catch (Throwable localThrowable)
    {
      com.tencent.beacontbs.c.a.a(localThrowable);
    }
    return "";
  }
  
  public static String g(Context paramContext)
  {
    paramContext = a(paramContext);
    if (paramContext == null) {
      return "unknown";
    }
    if (paramContext.getType() == 1) {
      return "wifi";
    }
    String str = paramContext.getExtraInfo();
    paramContext = str;
    if (str != null)
    {
      paramContext = str;
      if (str.length() > 64) {
        paramContext = str.substring(0, 64);
      }
    }
    return paramContext;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\a\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */