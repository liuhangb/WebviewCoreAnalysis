package com.tencent.tbs.common.download.qb;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.tencent.mtt.ContextHolder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONObject;

public class ChannelUtil
{
  private static final short BYTE_DATA_LEN = 2;
  private static final String CHARSET = "utf-8";
  private static final String COMMENT_MAGIC = "TBS";
  private static final String TAG = "ChannelUtil";
  
  private static Context createHostContext(String paramString)
  {
    try
    {
      paramString = ContextHolder.getAppContext().createPackageContext(paramString, 2);
      return paramString;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    return null;
  }
  
  private static String getDeviceBrand()
  {
    return Build.BRAND;
  }
  
  public static Map<String, String> getDeviceInfo()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("SystemLanguage", getSystemLanguage());
    localHashMap.put("SystemVersion", getSystemVersion());
    localHashMap.put("SystemModel", upper(getSystemModel()));
    localHashMap.put("DeviceBrand", upper(getDeviceBrand()));
    return localHashMap;
  }
  
  public static String getInstalledQbChannelId()
  {
    String str = "";
    Context localContext = createHostContext("com.tencent.mtt");
    if (localContext != null) {
      str = getQbChannelIdByApkFile(localContext.getPackageResourcePath());
    }
    return str;
  }
  
  public static String getInstalledQbSubChannelId()
  {
    String str2 = "";
    Object localObject = createHostContext("com.tencent.mtt");
    String str1 = str2;
    if (localObject != null) {}
    try
    {
      localObject = getQbSubChannelIdByApkFile(((Context)localObject).getPackageResourcePath());
      str1 = str2;
      if (localObject != null)
      {
        str1 = str2;
        if (!"".equals(localObject)) {
          str1 = new JSONObject((String)localObject).getString("channel");
        }
      }
      return str1;
    }
    catch (Exception localException) {}
    return "";
  }
  
  /* Error */
  public static String getQbChannelIdByApkFile(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_1
    //   4: aconst_null
    //   5: astore 5
    //   7: aload_0
    //   8: ifnonnull +5 -> 13
    //   11: aconst_null
    //   12: areturn
    //   13: new 116	java/util/zip/ZipFile
    //   16: dup
    //   17: aload_0
    //   18: invokespecial 117	java/util/zip/ZipFile:<init>	(Ljava/lang/String;)V
    //   21: astore 4
    //   23: aload 4
    //   25: ldc 119
    //   27: invokevirtual 123	java/util/zip/ZipFile:getEntry	(Ljava/lang/String;)Ljava/util/zip/ZipEntry;
    //   30: astore_0
    //   31: aload_0
    //   32: ifnonnull +42 -> 74
    //   35: aload 4
    //   37: invokevirtual 126	java/util/zip/ZipFile:close	()V
    //   40: aconst_null
    //   41: areturn
    //   42: astore_0
    //   43: new 128	java/lang/StringBuilder
    //   46: dup
    //   47: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   50: astore_1
    //   51: aload_1
    //   52: ldc -125
    //   54: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   57: pop
    //   58: aload_1
    //   59: aload_0
    //   60: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   63: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: pop
    //   67: aload_1
    //   68: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   71: pop
    //   72: aconst_null
    //   73: areturn
    //   74: aload 4
    //   76: aload_0
    //   77: invokevirtual 143	java/util/zip/ZipFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   80: astore_0
    //   81: aload_0
    //   82: ifnonnull +83 -> 165
    //   85: aload_0
    //   86: ifnull +40 -> 126
    //   89: aload_0
    //   90: invokevirtual 146	java/io/InputStream:close	()V
    //   93: goto +33 -> 126
    //   96: astore_0
    //   97: new 128	java/lang/StringBuilder
    //   100: dup
    //   101: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   104: astore_1
    //   105: aload_1
    //   106: ldc -125
    //   108: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: pop
    //   112: aload_1
    //   113: aload_0
    //   114: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   117: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   120: pop
    //   121: aload_1
    //   122: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   125: pop
    //   126: aload 4
    //   128: invokevirtual 126	java/util/zip/ZipFile:close	()V
    //   131: aconst_null
    //   132: areturn
    //   133: astore_0
    //   134: new 128	java/lang/StringBuilder
    //   137: dup
    //   138: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   141: astore_1
    //   142: aload_1
    //   143: ldc -125
    //   145: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   148: pop
    //   149: aload_1
    //   150: aload_0
    //   151: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   154: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   157: pop
    //   158: aload_1
    //   159: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   162: pop
    //   163: aconst_null
    //   164: areturn
    //   165: new 148	java/io/ByteArrayOutputStream
    //   168: dup
    //   169: invokespecial 149	java/io/ByteArrayOutputStream:<init>	()V
    //   172: astore_3
    //   173: aload 5
    //   175: astore_1
    //   176: sipush 128
    //   179: newarray <illegal type>
    //   181: astore_2
    //   182: aload 5
    //   184: astore_1
    //   185: aload_3
    //   186: aload_2
    //   187: iconst_0
    //   188: aload_0
    //   189: aload_2
    //   190: invokevirtual 153	java/io/InputStream:read	([B)I
    //   193: invokevirtual 157	java/io/ByteArrayOutputStream:write	([BII)V
    //   196: aload 5
    //   198: astore_1
    //   199: aload_3
    //   200: ldc -97
    //   202: invokevirtual 161	java/io/ByteArrayOutputStream:toString	(Ljava/lang/String;)Ljava/lang/String;
    //   205: astore_2
    //   206: aload_2
    //   207: astore_1
    //   208: aload_2
    //   209: ldc -93
    //   211: invokevirtual 167	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   214: astore 5
    //   216: aload_2
    //   217: astore_1
    //   218: aload 5
    //   220: arraylength
    //   221: iconst_2
    //   222: if_icmplt +125 -> 347
    //   225: aload 5
    //   227: iconst_1
    //   228: aaload
    //   229: astore_1
    //   230: aload_0
    //   231: ifnull +40 -> 271
    //   234: aload_0
    //   235: invokevirtual 146	java/io/InputStream:close	()V
    //   238: goto +33 -> 271
    //   241: astore_0
    //   242: new 128	java/lang/StringBuilder
    //   245: dup
    //   246: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   249: astore_2
    //   250: aload_2
    //   251: ldc -125
    //   253: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   256: pop
    //   257: aload_2
    //   258: aload_0
    //   259: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   262: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   265: pop
    //   266: aload_2
    //   267: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   270: pop
    //   271: aload_3
    //   272: invokevirtual 168	java/io/ByteArrayOutputStream:close	()V
    //   275: goto +33 -> 308
    //   278: astore_0
    //   279: new 128	java/lang/StringBuilder
    //   282: dup
    //   283: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   286: astore_2
    //   287: aload_2
    //   288: ldc -125
    //   290: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   293: pop
    //   294: aload_2
    //   295: aload_0
    //   296: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   299: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   302: pop
    //   303: aload_2
    //   304: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   307: pop
    //   308: aload 4
    //   310: invokevirtual 126	java/util/zip/ZipFile:close	()V
    //   313: aload_1
    //   314: areturn
    //   315: astore_0
    //   316: new 128	java/lang/StringBuilder
    //   319: dup
    //   320: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   323: astore_2
    //   324: aload_2
    //   325: ldc -125
    //   327: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   330: pop
    //   331: aload_2
    //   332: aload_0
    //   333: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   336: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   339: pop
    //   340: aload_2
    //   341: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   344: pop
    //   345: aload_1
    //   346: areturn
    //   347: aload_0
    //   348: ifnull +40 -> 388
    //   351: aload_0
    //   352: invokevirtual 146	java/io/InputStream:close	()V
    //   355: goto +33 -> 388
    //   358: astore_0
    //   359: new 128	java/lang/StringBuilder
    //   362: dup
    //   363: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   366: astore_1
    //   367: aload_1
    //   368: ldc -125
    //   370: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   373: pop
    //   374: aload_1
    //   375: aload_0
    //   376: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   379: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   382: pop
    //   383: aload_1
    //   384: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   387: pop
    //   388: aload_3
    //   389: invokevirtual 168	java/io/ByteArrayOutputStream:close	()V
    //   392: goto +33 -> 425
    //   395: astore_0
    //   396: new 128	java/lang/StringBuilder
    //   399: dup
    //   400: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   403: astore_1
    //   404: aload_1
    //   405: ldc -125
    //   407: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   410: pop
    //   411: aload_1
    //   412: aload_0
    //   413: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   416: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   419: pop
    //   420: aload_1
    //   421: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   424: pop
    //   425: aload 4
    //   427: invokevirtual 126	java/util/zip/ZipFile:close	()V
    //   430: goto +33 -> 463
    //   433: astore_0
    //   434: new 128	java/lang/StringBuilder
    //   437: dup
    //   438: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   441: astore_1
    //   442: aload_1
    //   443: ldc -125
    //   445: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   448: pop
    //   449: aload_1
    //   450: aload_0
    //   451: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   454: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   457: pop
    //   458: aload_1
    //   459: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   462: pop
    //   463: aload_2
    //   464: areturn
    //   465: astore_1
    //   466: aload_0
    //   467: astore_2
    //   468: aload_3
    //   469: astore_0
    //   470: goto +242 -> 712
    //   473: astore 5
    //   475: aload_0
    //   476: astore_2
    //   477: aload_3
    //   478: astore_0
    //   479: aload 5
    //   481: astore_3
    //   482: goto +70 -> 552
    //   485: astore_1
    //   486: aconst_null
    //   487: astore_3
    //   488: aload_0
    //   489: astore_2
    //   490: aload_3
    //   491: astore_0
    //   492: goto +220 -> 712
    //   495: astore 5
    //   497: aconst_null
    //   498: astore_3
    //   499: aload_3
    //   500: astore_1
    //   501: aload_0
    //   502: astore_2
    //   503: aload_3
    //   504: astore_0
    //   505: aload 5
    //   507: astore_3
    //   508: goto +44 -> 552
    //   511: astore_3
    //   512: aconst_null
    //   513: astore_0
    //   514: aload_1
    //   515: astore_2
    //   516: aload_3
    //   517: astore_1
    //   518: goto +194 -> 712
    //   521: astore_3
    //   522: aconst_null
    //   523: astore_1
    //   524: aload_1
    //   525: astore_0
    //   526: goto +26 -> 552
    //   529: astore_3
    //   530: aconst_null
    //   531: astore 4
    //   533: aload 4
    //   535: astore_0
    //   536: aload_1
    //   537: astore_2
    //   538: aload_3
    //   539: astore_1
    //   540: goto +172 -> 712
    //   543: astore_3
    //   544: aconst_null
    //   545: astore_1
    //   546: aload_1
    //   547: astore 4
    //   549: aload 4
    //   551: astore_0
    //   552: new 128	java/lang/StringBuilder
    //   555: dup
    //   556: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   559: astore 5
    //   561: aload 5
    //   563: ldc -125
    //   565: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   568: pop
    //   569: aload 5
    //   571: aload_3
    //   572: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   575: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   578: pop
    //   579: aload 5
    //   581: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   584: pop
    //   585: aload_2
    //   586: ifnull +40 -> 626
    //   589: aload_2
    //   590: invokevirtual 146	java/io/InputStream:close	()V
    //   593: goto +33 -> 626
    //   596: astore_2
    //   597: new 128	java/lang/StringBuilder
    //   600: dup
    //   601: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   604: astore_3
    //   605: aload_3
    //   606: ldc -125
    //   608: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   611: pop
    //   612: aload_3
    //   613: aload_2
    //   614: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   617: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   620: pop
    //   621: aload_3
    //   622: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   625: pop
    //   626: aload_0
    //   627: ifnull +40 -> 667
    //   630: aload_0
    //   631: invokevirtual 168	java/io/ByteArrayOutputStream:close	()V
    //   634: goto +33 -> 667
    //   637: astore_0
    //   638: new 128	java/lang/StringBuilder
    //   641: dup
    //   642: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   645: astore_2
    //   646: aload_2
    //   647: ldc -125
    //   649: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   652: pop
    //   653: aload_2
    //   654: aload_0
    //   655: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   658: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   661: pop
    //   662: aload_2
    //   663: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   666: pop
    //   667: aload 4
    //   669: ifnull +40 -> 709
    //   672: aload 4
    //   674: invokevirtual 126	java/util/zip/ZipFile:close	()V
    //   677: aload_1
    //   678: areturn
    //   679: astore_0
    //   680: new 128	java/lang/StringBuilder
    //   683: dup
    //   684: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   687: astore_2
    //   688: aload_2
    //   689: ldc -125
    //   691: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   694: pop
    //   695: aload_2
    //   696: aload_0
    //   697: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   700: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   703: pop
    //   704: aload_2
    //   705: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   708: pop
    //   709: aload_1
    //   710: areturn
    //   711: astore_1
    //   712: aload_2
    //   713: ifnull +40 -> 753
    //   716: aload_2
    //   717: invokevirtual 146	java/io/InputStream:close	()V
    //   720: goto +33 -> 753
    //   723: astore_2
    //   724: new 128	java/lang/StringBuilder
    //   727: dup
    //   728: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   731: astore_3
    //   732: aload_3
    //   733: ldc -125
    //   735: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   738: pop
    //   739: aload_3
    //   740: aload_2
    //   741: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   744: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   747: pop
    //   748: aload_3
    //   749: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   752: pop
    //   753: aload_0
    //   754: ifnull +40 -> 794
    //   757: aload_0
    //   758: invokevirtual 168	java/io/ByteArrayOutputStream:close	()V
    //   761: goto +33 -> 794
    //   764: astore_0
    //   765: new 128	java/lang/StringBuilder
    //   768: dup
    //   769: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   772: astore_2
    //   773: aload_2
    //   774: ldc -125
    //   776: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   779: pop
    //   780: aload_2
    //   781: aload_0
    //   782: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   785: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   788: pop
    //   789: aload_2
    //   790: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   793: pop
    //   794: aload 4
    //   796: ifnull +41 -> 837
    //   799: aload 4
    //   801: invokevirtual 126	java/util/zip/ZipFile:close	()V
    //   804: goto +33 -> 837
    //   807: astore_0
    //   808: new 128	java/lang/StringBuilder
    //   811: dup
    //   812: invokespecial 129	java/lang/StringBuilder:<init>	()V
    //   815: astore_2
    //   816: aload_2
    //   817: ldc -125
    //   819: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   822: pop
    //   823: aload_2
    //   824: aload_0
    //   825: invokevirtual 138	java/lang/Exception:toString	()Ljava/lang/String;
    //   828: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   831: pop
    //   832: aload_2
    //   833: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   836: pop
    //   837: aload_1
    //   838: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	839	0	paramString	String
    //   3	456	1	localObject1	Object
    //   465	1	1	localObject2	Object
    //   485	1	1	localObject3	Object
    //   500	210	1	localObject4	Object
    //   711	127	1	localObject5	Object
    //   1	589	2	localObject6	Object
    //   596	18	2	localException1	Exception
    //   645	72	2	localStringBuilder1	StringBuilder
    //   723	18	2	localException2	Exception
    //   772	61	2	localStringBuilder2	StringBuilder
    //   172	336	3	localObject7	Object
    //   511	6	3	localObject8	Object
    //   521	1	3	localException3	Exception
    //   529	10	3	localObject9	Object
    //   543	29	3	localException4	Exception
    //   604	145	3	localStringBuilder3	StringBuilder
    //   21	779	4	localObject10	Object
    //   5	221	5	arrayOfString	String[]
    //   473	7	5	localException5	Exception
    //   495	11	5	localException6	Exception
    //   559	21	5	localStringBuilder4	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   35	40	42	java/lang/Exception
    //   89	93	96	java/lang/Exception
    //   126	131	133	java/lang/Exception
    //   234	238	241	java/lang/Exception
    //   271	275	278	java/lang/Exception
    //   308	313	315	java/lang/Exception
    //   351	355	358	java/lang/Exception
    //   388	392	395	java/lang/Exception
    //   425	430	433	java/lang/Exception
    //   176	182	465	finally
    //   185	196	465	finally
    //   199	206	465	finally
    //   208	216	465	finally
    //   218	225	465	finally
    //   176	182	473	java/lang/Exception
    //   185	196	473	java/lang/Exception
    //   199	206	473	java/lang/Exception
    //   208	216	473	java/lang/Exception
    //   218	225	473	java/lang/Exception
    //   165	173	485	finally
    //   165	173	495	java/lang/Exception
    //   23	31	511	finally
    //   74	81	511	finally
    //   23	31	521	java/lang/Exception
    //   74	81	521	java/lang/Exception
    //   13	23	529	finally
    //   13	23	543	java/lang/Exception
    //   589	593	596	java/lang/Exception
    //   630	634	637	java/lang/Exception
    //   672	677	679	java/lang/Exception
    //   552	585	711	finally
    //   716	720	723	java/lang/Exception
    //   757	761	764	java/lang/Exception
    //   799	804	807	java/lang/Exception
  }
  
  public static String getQbSubChannelByApk(String paramString)
  {
    String str1 = "";
    try
    {
      String str2 = getQbSubChannelIdByApkFile(paramString);
      paramString = str1;
      if (str2 != null)
      {
        paramString = str1;
        if (!"".equals(str2)) {
          paramString = new JSONObject(str2).getString("channel");
        }
      }
      return paramString;
    }
    catch (Exception paramString) {}
    return "";
  }
  
  /* Error */
  public static String getQbSubChannelIdByApkFile(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +6 -> 7
    //   4: ldc 84
    //   6: areturn
    //   7: new 173	java/io/File
    //   10: dup
    //   11: aload_0
    //   12: invokespecial 174	java/io/File:<init>	(Ljava/lang/String;)V
    //   15: astore_0
    //   16: aload_0
    //   17: invokevirtual 178	java/io/File:exists	()Z
    //   20: ifne +6 -> 26
    //   23: ldc 84
    //   25: areturn
    //   26: new 180	java/io/RandomAccessFile
    //   29: dup
    //   30: aload_0
    //   31: ldc -74
    //   33: invokespecial 185	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   36: astore 4
    //   38: aload 4
    //   40: astore_0
    //   41: aload 4
    //   43: invokevirtual 189	java/io/RandomAccessFile:getChannel	()Ljava/nio/channels/FileChannel;
    //   46: astore 5
    //   48: aload 4
    //   50: astore_0
    //   51: aload 4
    //   53: invokevirtual 193	java/io/RandomAccessFile:length	()J
    //   56: iconst_3
    //   57: i2l
    //   58: lsub
    //   59: lstore_2
    //   60: aload 4
    //   62: astore_0
    //   63: aload 5
    //   65: lload_2
    //   66: invokevirtual 199	java/nio/channels/FileChannel:position	(J)Ljava/nio/channels/FileChannel;
    //   69: pop
    //   70: aload 4
    //   72: astore_0
    //   73: iconst_3
    //   74: invokestatic 205	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
    //   77: astore 6
    //   79: aload 4
    //   81: astore_0
    //   82: aload 5
    //   84: aload 6
    //   86: invokevirtual 208	java/nio/channels/FileChannel:read	(Ljava/nio/ByteBuffer;)I
    //   89: pop
    //   90: aload 4
    //   92: astore_0
    //   93: aload 6
    //   95: getstatic 214	java/nio/ByteOrder:LITTLE_ENDIAN	Ljava/nio/ByteOrder;
    //   98: invokevirtual 218	java/nio/ByteBuffer:order	(Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
    //   101: pop
    //   102: aload 4
    //   104: astore_0
    //   105: new 100	java/lang/String
    //   108: dup
    //   109: aload 6
    //   111: invokevirtual 222	java/nio/ByteBuffer:array	()[B
    //   114: ldc 11
    //   116: invokespecial 225	java/lang/String:<init>	([BLjava/lang/String;)V
    //   119: ldc 14
    //   121: invokevirtual 104	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   124: ifne +11 -> 135
    //   127: aload 4
    //   129: invokevirtual 226	java/io/RandomAccessFile:close	()V
    //   132: ldc 84
    //   134: areturn
    //   135: lload_2
    //   136: ldc2_w 227
    //   139: lsub
    //   140: lstore_2
    //   141: aload 4
    //   143: astore_0
    //   144: aload 5
    //   146: lload_2
    //   147: invokevirtual 199	java/nio/channels/FileChannel:position	(J)Ljava/nio/channels/FileChannel;
    //   150: pop
    //   151: aload 4
    //   153: astore_0
    //   154: iconst_2
    //   155: invokestatic 205	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
    //   158: astore 6
    //   160: aload 4
    //   162: astore_0
    //   163: aload 5
    //   165: aload 6
    //   167: invokevirtual 208	java/nio/channels/FileChannel:read	(Ljava/nio/ByteBuffer;)I
    //   170: pop
    //   171: aload 4
    //   173: astore_0
    //   174: aload 6
    //   176: getstatic 214	java/nio/ByteOrder:LITTLE_ENDIAN	Ljava/nio/ByteOrder;
    //   179: invokevirtual 218	java/nio/ByteBuffer:order	(Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
    //   182: pop
    //   183: aload 4
    //   185: astore_0
    //   186: aload 6
    //   188: iconst_0
    //   189: invokevirtual 232	java/nio/ByteBuffer:getShort	(I)S
    //   192: istore_1
    //   193: aload 4
    //   195: astore_0
    //   196: aload 5
    //   198: lload_2
    //   199: iload_1
    //   200: i2l
    //   201: lsub
    //   202: invokevirtual 199	java/nio/channels/FileChannel:position	(J)Ljava/nio/channels/FileChannel;
    //   205: pop
    //   206: aload 4
    //   208: astore_0
    //   209: iload_1
    //   210: invokestatic 205	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
    //   213: astore 6
    //   215: aload 4
    //   217: astore_0
    //   218: aload 5
    //   220: aload 6
    //   222: invokevirtual 208	java/nio/channels/FileChannel:read	(Ljava/nio/ByteBuffer;)I
    //   225: pop
    //   226: aload 4
    //   228: astore_0
    //   229: aload 6
    //   231: getstatic 214	java/nio/ByteOrder:LITTLE_ENDIAN	Ljava/nio/ByteOrder;
    //   234: invokevirtual 218	java/nio/ByteBuffer:order	(Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
    //   237: pop
    //   238: aload 4
    //   240: astore_0
    //   241: new 100	java/lang/String
    //   244: dup
    //   245: aload 6
    //   247: invokevirtual 222	java/nio/ByteBuffer:array	()[B
    //   250: ldc 11
    //   252: invokespecial 225	java/lang/String:<init>	([BLjava/lang/String;)V
    //   255: astore 5
    //   257: aload 4
    //   259: invokevirtual 226	java/io/RandomAccessFile:close	()V
    //   262: aload 5
    //   264: areturn
    //   265: astore 5
    //   267: goto +15 -> 282
    //   270: astore 4
    //   272: aconst_null
    //   273: astore_0
    //   274: goto +31 -> 305
    //   277: astore 5
    //   279: aconst_null
    //   280: astore 4
    //   282: aload 4
    //   284: astore_0
    //   285: aload 5
    //   287: invokevirtual 235	java/io/IOException:printStackTrace	()V
    //   290: aload 4
    //   292: ifnull +8 -> 300
    //   295: aload 4
    //   297: invokevirtual 226	java/io/RandomAccessFile:close	()V
    //   300: ldc 84
    //   302: areturn
    //   303: astore 4
    //   305: aload_0
    //   306: ifnull +7 -> 313
    //   309: aload_0
    //   310: invokevirtual 226	java/io/RandomAccessFile:close	()V
    //   313: aload 4
    //   315: athrow
    //   316: astore_0
    //   317: ldc 84
    //   319: areturn
    //   320: astore_0
    //   321: aload 5
    //   323: areturn
    //   324: astore_0
    //   325: goto -25 -> 300
    //   328: astore_0
    //   329: goto -16 -> 313
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	332	0	paramString	String
    //   192	18	1	i	int
    //   59	140	2	l	long
    //   36	222	4	localRandomAccessFile	java.io.RandomAccessFile
    //   270	1	4	localObject1	Object
    //   280	16	4	localObject2	Object
    //   303	11	4	localObject3	Object
    //   46	217	5	localObject4	Object
    //   265	1	5	localIOException1	java.io.IOException
    //   277	45	5	localIOException2	java.io.IOException
    //   77	169	6	localByteBuffer	java.nio.ByteBuffer
    // Exception table:
    //   from	to	target	type
    //   41	48	265	java/io/IOException
    //   51	60	265	java/io/IOException
    //   63	70	265	java/io/IOException
    //   73	79	265	java/io/IOException
    //   82	90	265	java/io/IOException
    //   93	102	265	java/io/IOException
    //   105	127	265	java/io/IOException
    //   144	151	265	java/io/IOException
    //   154	160	265	java/io/IOException
    //   163	171	265	java/io/IOException
    //   174	183	265	java/io/IOException
    //   186	193	265	java/io/IOException
    //   196	206	265	java/io/IOException
    //   209	215	265	java/io/IOException
    //   218	226	265	java/io/IOException
    //   229	238	265	java/io/IOException
    //   241	257	265	java/io/IOException
    //   26	38	270	finally
    //   26	38	277	java/io/IOException
    //   41	48	303	finally
    //   51	60	303	finally
    //   63	70	303	finally
    //   73	79	303	finally
    //   82	90	303	finally
    //   93	102	303	finally
    //   105	127	303	finally
    //   144	151	303	finally
    //   154	160	303	finally
    //   163	171	303	finally
    //   174	183	303	finally
    //   186	193	303	finally
    //   196	206	303	finally
    //   209	215	303	finally
    //   218	226	303	finally
    //   229	238	303	finally
    //   241	257	303	finally
    //   285	290	303	finally
    //   127	132	316	java/lang/Exception
    //   257	262	320	java/lang/Exception
    //   295	300	324	java/lang/Exception
    //   309	313	328	java/lang/Exception
  }
  
  private static String getSystemLanguage()
  {
    return Locale.getDefault().getLanguage();
  }
  
  private static String getSystemModel()
  {
    return Build.MODEL;
  }
  
  private static String getSystemVersion()
  {
    return Build.VERSION.RELEASE;
  }
  
  private static String upper(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return paramString.toUpperCase();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\ChannelUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */