package com.tencent.smtt.jsApi.impl.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.common.utils.DomainMatcher;
import com.tencent.common.utils.Md5Utils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.mtt.browser.download.engine.DownloadTask;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsDomainListDataProvider;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.download.BaseDownloadManager;
import com.tencent.tbs.common.download.qb.InstallUtil;
import com.tencent.tbs.common.settings.PublicSettingManager;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class SystemInstallReceiver
  extends BroadcastReceiver
{
  private static volatile SystemInstallReceiver jdField_a_of_type_ComTencentSmttJsApiImplUtilsSystemInstallReceiver = null;
  private static String jdField_a_of_type_JavaLangString = "SIR";
  private static Map<String, CallbackInfo> jdField_a_of_type_JavaUtilMap = new HashMap();
  private static String c = "UMENG_CHANNEL";
  private static String d = "UMENG_CHANNEL_oral";
  private static String e = "qihoo";
  private static String f = "channel";
  private static String g = "ss.properties";
  private static String h = "conf";
  private static String i = ".channel";
  private static String j = "channel.ini";
  private static String k = "channel";
  private boolean jdField_a_of_type_Boolean = false;
  private String b = null;
  
  private static AppChannelInfo a(Context paramContext, File paramFile)
  {
    Object localObject3 = null;
    if (paramContext == null) {
      return null;
    }
    try
    {
      paramContext = paramContext.getPackageManager().getPackageArchiveInfo(paramFile.getAbsolutePath(), 128);
      if (paramContext == null) {
        return new AppChannelInfo();
      }
      Object localObject1 = paramContext.applicationInfo;
      paramContext = paramContext.packageName;
      if (localObject1 != null) {
        try
        {
          if (((ApplicationInfo)localObject1).metaData != null)
          {
            paramFile = ((ApplicationInfo)localObject1).metaData.get(c);
            if (paramFile == null) {
              try
              {
                localObject1 = ((ApplicationInfo)localObject1).metaData.get(d);
                paramFile = (File)localObject1;
              }
              catch (Exception localException1)
              {
                break label110;
              }
            }
          }
        }
        catch (Exception localException2)
        {
          paramFile = null;
          break label110;
        }
      }
      paramFile = null;
    }
    catch (Exception localException3)
    {
      paramFile = null;
      paramContext = paramFile;
      label110:
      Object localObject4 = jdField_a_of_type_JavaLangString;
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append("getAppMetaData:");
      ((StringBuilder)localObject4).append(localException3.toString());
      ((StringBuilder)localObject4).toString();
    }
    Object localObject2 = localObject3;
    if (paramFile != null) {
      localObject2 = paramFile.toString();
    }
    paramFile = jdField_a_of_type_JavaLangString;
    paramFile = new StringBuilder();
    paramFile.append("getAppMetaData:");
    paramFile.append((String)localObject2);
    paramFile.toString();
    return new AppChannelInfo(paramContext, (String)localObject2);
  }
  
  /* Error */
  @android.annotation.SuppressLint({"NewApi"})
  private static String a(Context paramContext, File paramFile)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 88	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   4: astore_0
    //   5: ldc -107
    //   7: astore 6
    //   9: ldc -105
    //   11: astore_3
    //   12: new 153	java/util/zip/ZipFile
    //   15: dup
    //   16: aload_0
    //   17: invokespecial 156	java/util/zip/ZipFile:<init>	(Ljava/lang/String;)V
    //   20: astore 4
    //   22: aload 6
    //   24: astore_1
    //   25: aload 6
    //   27: astore 5
    //   29: aload 4
    //   31: astore_0
    //   32: aload 4
    //   34: invokevirtual 159	java/util/zip/ZipFile:getComment	()Ljava/lang/String;
    //   37: astore 7
    //   39: aload 6
    //   41: astore_1
    //   42: aload 6
    //   44: astore 5
    //   46: aload 4
    //   48: astore_0
    //   49: aload 4
    //   51: invokevirtual 163	java/util/zip/ZipFile:entries	()Ljava/util/Enumeration;
    //   54: astore 8
    //   56: aload 6
    //   58: astore_1
    //   59: aload 6
    //   61: astore 5
    //   63: aload 4
    //   65: astore_0
    //   66: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   69: astore 9
    //   71: aload 6
    //   73: astore_1
    //   74: aload 6
    //   76: astore 5
    //   78: aload 4
    //   80: astore_0
    //   81: new 122	java/lang/StringBuilder
    //   84: dup
    //   85: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   88: astore 9
    //   90: aload 6
    //   92: astore_1
    //   93: aload 6
    //   95: astore 5
    //   97: aload 4
    //   99: astore_0
    //   100: aload 9
    //   102: ldc -91
    //   104: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: pop
    //   108: aload 6
    //   110: astore_1
    //   111: aload 6
    //   113: astore 5
    //   115: aload 4
    //   117: astore_0
    //   118: aload 9
    //   120: aload 7
    //   122: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   125: pop
    //   126: aload 6
    //   128: astore_1
    //   129: aload 6
    //   131: astore 5
    //   133: aload 4
    //   135: astore_0
    //   136: aload 9
    //   138: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   141: pop
    //   142: aload 6
    //   144: astore_1
    //   145: aload 6
    //   147: astore 5
    //   149: aload 4
    //   151: astore_0
    //   152: aload 7
    //   154: invokestatic 171	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   157: ifne +159 -> 316
    //   160: aload 6
    //   162: astore_1
    //   163: aload 6
    //   165: astore 5
    //   167: aload 4
    //   169: astore_0
    //   170: aload 7
    //   172: iconst_0
    //   173: aload 7
    //   175: invokevirtual 177	java/lang/String:length	()I
    //   178: iconst_2
    //   179: isub
    //   180: invokevirtual 181	java/lang/String:substring	(II)Ljava/lang/String;
    //   183: astore 6
    //   185: aload 6
    //   187: astore_1
    //   188: aload 6
    //   190: astore 5
    //   192: aload 4
    //   194: astore_0
    //   195: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   198: astore 7
    //   200: aload 6
    //   202: astore_1
    //   203: aload 6
    //   205: astore 5
    //   207: aload 4
    //   209: astore_0
    //   210: new 122	java/lang/StringBuilder
    //   213: dup
    //   214: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   217: astore 7
    //   219: aload 6
    //   221: astore_1
    //   222: aload 6
    //   224: astore 5
    //   226: aload 4
    //   228: astore_0
    //   229: aload 7
    //   231: ldc -73
    //   233: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   236: pop
    //   237: aload 6
    //   239: astore_1
    //   240: aload 6
    //   242: astore 5
    //   244: aload 4
    //   246: astore_0
    //   247: aload 7
    //   249: aload 6
    //   251: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   254: pop
    //   255: aload 6
    //   257: astore_1
    //   258: aload 6
    //   260: astore 5
    //   262: aload 4
    //   264: astore_0
    //   265: aload 7
    //   267: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   270: pop
    //   271: aload 4
    //   273: invokevirtual 186	java/util/zip/ZipFile:close	()V
    //   276: aload 6
    //   278: areturn
    //   279: astore_0
    //   280: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   283: astore_1
    //   284: new 122	java/lang/StringBuilder
    //   287: dup
    //   288: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   291: astore_1
    //   292: aload_1
    //   293: ldc -68
    //   295: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   298: pop
    //   299: aload_1
    //   300: aload_0
    //   301: invokevirtual 189	java/io/IOException:toString	()Ljava/lang/String;
    //   304: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   307: pop
    //   308: aload_1
    //   309: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   312: pop
    //   313: aload 6
    //   315: areturn
    //   316: aload 6
    //   318: astore_1
    //   319: aload 6
    //   321: astore 5
    //   323: aload 4
    //   325: astore_0
    //   326: aload 8
    //   328: invokeinterface 195 1 0
    //   333: ifeq +1263 -> 1596
    //   336: aload 6
    //   338: astore_1
    //   339: aload 6
    //   341: astore 5
    //   343: aload 4
    //   345: astore_0
    //   346: aload 8
    //   348: invokeinterface 199 1 0
    //   353: checkcast 201	java/util/zip/ZipEntry
    //   356: astore 9
    //   358: aload 6
    //   360: astore_1
    //   361: aload 6
    //   363: astore 5
    //   365: aload 4
    //   367: astore_0
    //   368: aload 9
    //   370: invokevirtual 204	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   373: astore 7
    //   375: aload 7
    //   377: ifnonnull +6 -> 383
    //   380: goto -64 -> 316
    //   383: aload 6
    //   385: astore_1
    //   386: aload 6
    //   388: astore 5
    //   390: aload 4
    //   392: astore_0
    //   393: aload 7
    //   395: ldc -50
    //   397: invokevirtual 209	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   400: ifeq +6 -> 406
    //   403: goto -87 -> 316
    //   406: aload 6
    //   408: astore_1
    //   409: aload 6
    //   411: astore 5
    //   413: aload 4
    //   415: astore_0
    //   416: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   419: astore 10
    //   421: aload 6
    //   423: astore_1
    //   424: aload 6
    //   426: astore 5
    //   428: aload 4
    //   430: astore_0
    //   431: new 122	java/lang/StringBuilder
    //   434: dup
    //   435: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   438: astore 10
    //   440: aload 6
    //   442: astore_1
    //   443: aload 6
    //   445: astore 5
    //   447: aload 4
    //   449: astore_0
    //   450: aload 10
    //   452: ldc -45
    //   454: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   457: pop
    //   458: aload 6
    //   460: astore_1
    //   461: aload 6
    //   463: astore 5
    //   465: aload 4
    //   467: astore_0
    //   468: aload 10
    //   470: aload 7
    //   472: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   475: pop
    //   476: aload 6
    //   478: astore_1
    //   479: aload 6
    //   481: astore 5
    //   483: aload 4
    //   485: astore_0
    //   486: aload 10
    //   488: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   491: pop
    //   492: aload 6
    //   494: astore_1
    //   495: aload 6
    //   497: astore 5
    //   499: aload 4
    //   501: astore_0
    //   502: new 122	java/lang/StringBuilder
    //   505: dup
    //   506: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   509: astore 10
    //   511: aload 6
    //   513: astore_1
    //   514: aload 6
    //   516: astore 5
    //   518: aload 4
    //   520: astore_0
    //   521: aload 10
    //   523: ldc -43
    //   525: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   528: pop
    //   529: aload 6
    //   531: astore_1
    //   532: aload 6
    //   534: astore 5
    //   536: aload 4
    //   538: astore_0
    //   539: aload 10
    //   541: getstatic 45	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:e	Ljava/lang/String;
    //   544: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   547: pop
    //   548: aload 6
    //   550: astore_1
    //   551: aload 6
    //   553: astore 5
    //   555: aload 4
    //   557: astore_0
    //   558: aload 7
    //   560: aload 10
    //   562: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   565: invokevirtual 217	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   568: ifeq +11 -> 579
    //   571: aload_3
    //   572: astore_0
    //   573: aload 7
    //   575: astore_1
    //   576: goto +1025 -> 1601
    //   579: aload 6
    //   581: astore_1
    //   582: aload 6
    //   584: astore 5
    //   586: aload 4
    //   588: astore_0
    //   589: new 122	java/lang/StringBuilder
    //   592: dup
    //   593: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   596: astore 10
    //   598: aload 6
    //   600: astore_1
    //   601: aload 6
    //   603: astore 5
    //   605: aload 4
    //   607: astore_0
    //   608: aload 10
    //   610: ldc -43
    //   612: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   615: pop
    //   616: aload 6
    //   618: astore_1
    //   619: aload 6
    //   621: astore 5
    //   623: aload 4
    //   625: astore_0
    //   626: aload 10
    //   628: getstatic 49	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:f	Ljava/lang/String;
    //   631: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   634: pop
    //   635: aload 6
    //   637: astore_1
    //   638: aload 6
    //   640: astore 5
    //   642: aload 4
    //   644: astore_0
    //   645: aload 7
    //   647: aload 10
    //   649: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   652: invokevirtual 217	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   655: ifeq +11 -> 666
    //   658: aload_3
    //   659: astore_0
    //   660: aload 7
    //   662: astore_1
    //   663: goto +938 -> 1601
    //   666: aload 6
    //   668: astore_1
    //   669: aload 6
    //   671: astore 5
    //   673: aload 4
    //   675: astore_0
    //   676: new 122	java/lang/StringBuilder
    //   679: dup
    //   680: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   683: astore 10
    //   685: aload 6
    //   687: astore_1
    //   688: aload 6
    //   690: astore 5
    //   692: aload 4
    //   694: astore_0
    //   695: aload 10
    //   697: ldc -43
    //   699: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   702: pop
    //   703: aload 6
    //   705: astore_1
    //   706: aload 6
    //   708: astore 5
    //   710: aload 4
    //   712: astore_0
    //   713: aload 10
    //   715: ldc -37
    //   717: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   720: pop
    //   721: aload 6
    //   723: astore_1
    //   724: aload 6
    //   726: astore 5
    //   728: aload 4
    //   730: astore_0
    //   731: aload 7
    //   733: aload 10
    //   735: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   738: invokevirtual 217	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   741: istore_2
    //   742: iload_2
    //   743: ifeq +12 -> 755
    //   746: ldc -35
    //   748: astore_0
    //   749: aload 7
    //   751: astore_1
    //   752: goto +849 -> 1601
    //   755: aload 6
    //   757: astore_1
    //   758: aload 6
    //   760: astore 5
    //   762: aload 4
    //   764: astore_0
    //   765: new 122	java/lang/StringBuilder
    //   768: dup
    //   769: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   772: astore 10
    //   774: aload 6
    //   776: astore_1
    //   777: aload 6
    //   779: astore 5
    //   781: aload 4
    //   783: astore_0
    //   784: aload 10
    //   786: ldc -43
    //   788: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   791: pop
    //   792: aload 6
    //   794: astore_1
    //   795: aload 6
    //   797: astore 5
    //   799: aload 4
    //   801: astore_0
    //   802: aload 10
    //   804: ldc -33
    //   806: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   809: pop
    //   810: aload 6
    //   812: astore_1
    //   813: aload 6
    //   815: astore 5
    //   817: aload 4
    //   819: astore_0
    //   820: aload 7
    //   822: aload 10
    //   824: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   827: invokevirtual 217	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   830: istore_2
    //   831: iload_2
    //   832: ifeq +12 -> 844
    //   835: ldc -31
    //   837: astore_0
    //   838: aload 7
    //   840: astore_1
    //   841: goto +760 -> 1601
    //   844: aload 6
    //   846: astore_1
    //   847: aload 6
    //   849: astore 5
    //   851: aload 4
    //   853: astore_0
    //   854: aload 9
    //   856: invokevirtual 228	java/util/zip/ZipEntry:isDirectory	()Z
    //   859: ifeq +6 -> 865
    //   862: goto -546 -> 316
    //   865: aload 6
    //   867: astore_1
    //   868: aload 6
    //   870: astore 5
    //   872: aload 4
    //   874: astore_0
    //   875: new 122	java/lang/StringBuilder
    //   878: dup
    //   879: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   882: astore 10
    //   884: aload 6
    //   886: astore_1
    //   887: aload 6
    //   889: astore 5
    //   891: aload 4
    //   893: astore_0
    //   894: aload 10
    //   896: ldc -26
    //   898: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   901: pop
    //   902: aload 6
    //   904: astore_1
    //   905: aload 6
    //   907: astore 5
    //   909: aload 4
    //   911: astore_0
    //   912: aload 10
    //   914: getstatic 53	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:g	Ljava/lang/String;
    //   917: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   920: pop
    //   921: aload 6
    //   923: astore_1
    //   924: aload 6
    //   926: astore 5
    //   928: aload 4
    //   930: astore_0
    //   931: aload 7
    //   933: aload 10
    //   935: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   938: invokevirtual 233	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   941: ifeq +71 -> 1012
    //   944: aload 6
    //   946: astore_1
    //   947: aload 6
    //   949: astore 5
    //   951: aload 4
    //   953: astore_0
    //   954: aload 4
    //   956: aload 9
    //   958: ldc -21
    //   960: ldc -19
    //   962: invokestatic 240	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:a	(Ljava/util/zip/ZipFile;Ljava/util/zip/ZipEntry;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   965: astore 6
    //   967: aload 4
    //   969: invokevirtual 186	java/util/zip/ZipFile:close	()V
    //   972: aload 6
    //   974: areturn
    //   975: astore_0
    //   976: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   979: astore_1
    //   980: new 122	java/lang/StringBuilder
    //   983: dup
    //   984: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   987: astore_1
    //   988: aload_1
    //   989: ldc -68
    //   991: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   994: pop
    //   995: aload_1
    //   996: aload_0
    //   997: invokevirtual 189	java/io/IOException:toString	()Ljava/lang/String;
    //   1000: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1003: pop
    //   1004: aload_1
    //   1005: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1008: pop
    //   1009: aload 6
    //   1011: areturn
    //   1012: aload 6
    //   1014: astore_1
    //   1015: aload 6
    //   1017: astore 5
    //   1019: aload 4
    //   1021: astore_0
    //   1022: new 122	java/lang/StringBuilder
    //   1025: dup
    //   1026: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   1029: astore 10
    //   1031: aload 6
    //   1033: astore_1
    //   1034: aload 6
    //   1036: astore 5
    //   1038: aload 4
    //   1040: astore_0
    //   1041: aload 10
    //   1043: ldc -26
    //   1045: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1048: pop
    //   1049: aload 6
    //   1051: astore_1
    //   1052: aload 6
    //   1054: astore 5
    //   1056: aload 4
    //   1058: astore_0
    //   1059: aload 10
    //   1061: getstatic 57	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:h	Ljava/lang/String;
    //   1064: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1067: pop
    //   1068: aload 6
    //   1070: astore_1
    //   1071: aload 6
    //   1073: astore 5
    //   1075: aload 4
    //   1077: astore_0
    //   1078: aload 7
    //   1080: aload 10
    //   1082: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1085: invokevirtual 233	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   1088: ifeq +71 -> 1159
    //   1091: aload 6
    //   1093: astore_1
    //   1094: aload 6
    //   1096: astore 5
    //   1098: aload 4
    //   1100: astore_0
    //   1101: aload 4
    //   1103: aload 9
    //   1105: ldc -14
    //   1107: ldc -12
    //   1109: invokestatic 240	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:a	(Ljava/util/zip/ZipFile;Ljava/util/zip/ZipEntry;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1112: astore 6
    //   1114: aload 4
    //   1116: invokevirtual 186	java/util/zip/ZipFile:close	()V
    //   1119: aload 6
    //   1121: areturn
    //   1122: astore_0
    //   1123: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1126: astore_1
    //   1127: new 122	java/lang/StringBuilder
    //   1130: dup
    //   1131: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   1134: astore_1
    //   1135: aload_1
    //   1136: ldc -68
    //   1138: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1141: pop
    //   1142: aload_1
    //   1143: aload_0
    //   1144: invokevirtual 189	java/io/IOException:toString	()Ljava/lang/String;
    //   1147: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1150: pop
    //   1151: aload_1
    //   1152: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1155: pop
    //   1156: aload 6
    //   1158: areturn
    //   1159: aload 6
    //   1161: astore_1
    //   1162: aload 6
    //   1164: astore 5
    //   1166: aload 4
    //   1168: astore_0
    //   1169: new 122	java/lang/StringBuilder
    //   1172: dup
    //   1173: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   1176: astore 10
    //   1178: aload 6
    //   1180: astore_1
    //   1181: aload 6
    //   1183: astore 5
    //   1185: aload 4
    //   1187: astore_0
    //   1188: aload 10
    //   1190: ldc -26
    //   1192: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1195: pop
    //   1196: aload 6
    //   1198: astore_1
    //   1199: aload 6
    //   1201: astore 5
    //   1203: aload 4
    //   1205: astore_0
    //   1206: aload 10
    //   1208: getstatic 61	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:i	Ljava/lang/String;
    //   1211: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1214: pop
    //   1215: aload 6
    //   1217: astore_1
    //   1218: aload 6
    //   1220: astore 5
    //   1222: aload 4
    //   1224: astore_0
    //   1225: aload 7
    //   1227: aload 10
    //   1229: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1232: invokevirtual 233	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   1235: ifeq +71 -> 1306
    //   1238: aload 6
    //   1240: astore_1
    //   1241: aload 6
    //   1243: astore 5
    //   1245: aload 4
    //   1247: astore_0
    //   1248: aload 4
    //   1250: aload 9
    //   1252: ldc -10
    //   1254: ldc -8
    //   1256: invokestatic 240	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:a	(Ljava/util/zip/ZipFile;Ljava/util/zip/ZipEntry;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1259: astore 6
    //   1261: aload 4
    //   1263: invokevirtual 186	java/util/zip/ZipFile:close	()V
    //   1266: aload 6
    //   1268: areturn
    //   1269: astore_0
    //   1270: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1273: astore_1
    //   1274: new 122	java/lang/StringBuilder
    //   1277: dup
    //   1278: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   1281: astore_1
    //   1282: aload_1
    //   1283: ldc -68
    //   1285: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1288: pop
    //   1289: aload_1
    //   1290: aload_0
    //   1291: invokevirtual 189	java/io/IOException:toString	()Ljava/lang/String;
    //   1294: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1297: pop
    //   1298: aload_1
    //   1299: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1302: pop
    //   1303: aload 6
    //   1305: areturn
    //   1306: aload 6
    //   1308: astore_1
    //   1309: aload 6
    //   1311: astore 5
    //   1313: aload 4
    //   1315: astore_0
    //   1316: new 122	java/lang/StringBuilder
    //   1319: dup
    //   1320: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   1323: astore 10
    //   1325: aload 6
    //   1327: astore_1
    //   1328: aload 6
    //   1330: astore 5
    //   1332: aload 4
    //   1334: astore_0
    //   1335: aload 10
    //   1337: ldc -26
    //   1339: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1342: pop
    //   1343: aload 6
    //   1345: astore_1
    //   1346: aload 6
    //   1348: astore 5
    //   1350: aload 4
    //   1352: astore_0
    //   1353: aload 10
    //   1355: getstatic 65	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:j	Ljava/lang/String;
    //   1358: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1361: pop
    //   1362: aload 6
    //   1364: astore_1
    //   1365: aload 6
    //   1367: astore 5
    //   1369: aload 4
    //   1371: astore_0
    //   1372: aload 7
    //   1374: aload 10
    //   1376: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1379: invokevirtual 233	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   1382: ifeq +71 -> 1453
    //   1385: aload 6
    //   1387: astore_1
    //   1388: aload 6
    //   1390: astore 5
    //   1392: aload 4
    //   1394: astore_0
    //   1395: aload 4
    //   1397: aload 9
    //   1399: ldc -6
    //   1401: ldc -12
    //   1403: invokestatic 240	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:a	(Ljava/util/zip/ZipFile;Ljava/util/zip/ZipEntry;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1406: astore 6
    //   1408: aload 4
    //   1410: invokevirtual 186	java/util/zip/ZipFile:close	()V
    //   1413: aload 6
    //   1415: areturn
    //   1416: astore_0
    //   1417: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1420: astore_1
    //   1421: new 122	java/lang/StringBuilder
    //   1424: dup
    //   1425: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   1428: astore_1
    //   1429: aload_1
    //   1430: ldc -68
    //   1432: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1435: pop
    //   1436: aload_1
    //   1437: aload_0
    //   1438: invokevirtual 189	java/io/IOException:toString	()Ljava/lang/String;
    //   1441: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1444: pop
    //   1445: aload_1
    //   1446: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1449: pop
    //   1450: aload 6
    //   1452: areturn
    //   1453: aload 6
    //   1455: astore_1
    //   1456: aload 6
    //   1458: astore 5
    //   1460: aload 4
    //   1462: astore_0
    //   1463: new 122	java/lang/StringBuilder
    //   1466: dup
    //   1467: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   1470: astore 10
    //   1472: aload 6
    //   1474: astore_1
    //   1475: aload 6
    //   1477: astore 5
    //   1479: aload 4
    //   1481: astore_0
    //   1482: aload 10
    //   1484: ldc -26
    //   1486: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1489: pop
    //   1490: aload 6
    //   1492: astore_1
    //   1493: aload 6
    //   1495: astore 5
    //   1497: aload 4
    //   1499: astore_0
    //   1500: aload 10
    //   1502: getstatic 67	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:k	Ljava/lang/String;
    //   1505: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1508: pop
    //   1509: aload 6
    //   1511: astore_1
    //   1512: aload 6
    //   1514: astore 5
    //   1516: aload 4
    //   1518: astore_0
    //   1519: aload 7
    //   1521: aload 10
    //   1523: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1526: invokevirtual 233	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   1529: ifeq -1213 -> 316
    //   1532: aload 6
    //   1534: astore_1
    //   1535: aload 6
    //   1537: astore 5
    //   1539: aload 4
    //   1541: astore_0
    //   1542: aload 4
    //   1544: aload 9
    //   1546: invokestatic 253	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:a	(Ljava/util/zip/ZipFile;Ljava/util/zip/ZipEntry;)Ljava/lang/String;
    //   1549: astore 6
    //   1551: aload 4
    //   1553: invokevirtual 186	java/util/zip/ZipFile:close	()V
    //   1556: aload 6
    //   1558: areturn
    //   1559: astore_0
    //   1560: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1563: astore_1
    //   1564: new 122	java/lang/StringBuilder
    //   1567: dup
    //   1568: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   1571: astore_1
    //   1572: aload_1
    //   1573: ldc -68
    //   1575: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1578: pop
    //   1579: aload_1
    //   1580: aload_0
    //   1581: invokevirtual 189	java/io/IOException:toString	()Ljava/lang/String;
    //   1584: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1587: pop
    //   1588: aload_1
    //   1589: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1592: pop
    //   1593: aload 6
    //   1595: areturn
    //   1596: ldc -107
    //   1598: astore_1
    //   1599: aload_3
    //   1600: astore_0
    //   1601: aload 4
    //   1603: invokevirtual 186	java/util/zip/ZipFile:close	()V
    //   1606: aload_0
    //   1607: astore 5
    //   1609: aload_1
    //   1610: astore_0
    //   1611: goto +295 -> 1906
    //   1614: astore_3
    //   1615: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1618: astore 4
    //   1620: new 122	java/lang/StringBuilder
    //   1623: dup
    //   1624: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   1627: astore 4
    //   1629: aload 4
    //   1631: ldc -68
    //   1633: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1636: pop
    //   1637: aload 4
    //   1639: aload_3
    //   1640: invokevirtual 189	java/io/IOException:toString	()Ljava/lang/String;
    //   1643: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1646: pop
    //   1647: aload 4
    //   1649: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1652: pop
    //   1653: aload_0
    //   1654: astore 5
    //   1656: aload_1
    //   1657: astore_0
    //   1658: goto +248 -> 1906
    //   1661: astore 5
    //   1663: goto +27 -> 1690
    //   1666: astore_0
    //   1667: aload 5
    //   1669: astore_1
    //   1670: aload_0
    //   1671: astore 5
    //   1673: goto +129 -> 1802
    //   1676: astore_1
    //   1677: aconst_null
    //   1678: astore_0
    //   1679: goto +363 -> 2042
    //   1682: astore 5
    //   1684: ldc -107
    //   1686: astore_1
    //   1687: aconst_null
    //   1688: astore 4
    //   1690: aload 4
    //   1692: astore_0
    //   1693: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1696: astore 6
    //   1698: aload 4
    //   1700: astore_0
    //   1701: new 122	java/lang/StringBuilder
    //   1704: dup
    //   1705: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   1708: astore 6
    //   1710: aload 4
    //   1712: astore_0
    //   1713: aload 6
    //   1715: ldc -68
    //   1717: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1720: pop
    //   1721: aload 4
    //   1723: astore_0
    //   1724: aload 6
    //   1726: aload 5
    //   1728: invokevirtual 254	java/lang/Throwable:toString	()Ljava/lang/String;
    //   1731: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1734: pop
    //   1735: aload 4
    //   1737: astore_0
    //   1738: aload 6
    //   1740: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1743: pop
    //   1744: aload_3
    //   1745: astore 5
    //   1747: aload_1
    //   1748: astore_0
    //   1749: aload 4
    //   1751: ifnull +155 -> 1906
    //   1754: aload 4
    //   1756: invokevirtual 186	java/util/zip/ZipFile:close	()V
    //   1759: aload_3
    //   1760: astore 5
    //   1762: aload_1
    //   1763: astore_0
    //   1764: goto +142 -> 1906
    //   1767: astore 4
    //   1769: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1772: astore_0
    //   1773: new 122	java/lang/StringBuilder
    //   1776: dup
    //   1777: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   1780: astore 5
    //   1782: aload_3
    //   1783: astore_0
    //   1784: aload 4
    //   1786: astore_3
    //   1787: aload 5
    //   1789: astore 4
    //   1791: goto -162 -> 1629
    //   1794: astore 5
    //   1796: ldc -107
    //   1798: astore_1
    //   1799: aconst_null
    //   1800: astore 4
    //   1802: aload 4
    //   1804: astore_0
    //   1805: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1808: astore 6
    //   1810: aload 4
    //   1812: astore_0
    //   1813: new 122	java/lang/StringBuilder
    //   1816: dup
    //   1817: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   1820: astore 6
    //   1822: aload 4
    //   1824: astore_0
    //   1825: aload 6
    //   1827: ldc -68
    //   1829: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1832: pop
    //   1833: aload 4
    //   1835: astore_0
    //   1836: aload 6
    //   1838: aload 5
    //   1840: invokevirtual 189	java/io/IOException:toString	()Ljava/lang/String;
    //   1843: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1846: pop
    //   1847: aload 4
    //   1849: astore_0
    //   1850: aload 6
    //   1852: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1855: pop
    //   1856: aload_3
    //   1857: astore 5
    //   1859: aload_1
    //   1860: astore_0
    //   1861: aload 4
    //   1863: ifnull +43 -> 1906
    //   1866: aload 4
    //   1868: invokevirtual 186	java/util/zip/ZipFile:close	()V
    //   1871: aload_3
    //   1872: astore 5
    //   1874: aload_1
    //   1875: astore_0
    //   1876: goto +30 -> 1906
    //   1879: astore 4
    //   1881: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1884: astore_0
    //   1885: new 122	java/lang/StringBuilder
    //   1888: dup
    //   1889: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   1892: astore 5
    //   1894: aload_3
    //   1895: astore_0
    //   1896: aload 4
    //   1898: astore_3
    //   1899: aload 5
    //   1901: astore 4
    //   1903: goto -274 -> 1629
    //   1906: aload_0
    //   1907: invokestatic 171	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   1910: ifne +129 -> 2039
    //   1913: aload_0
    //   1914: aload 5
    //   1916: invokevirtual 258	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   1919: astore_3
    //   1920: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1923: astore_0
    //   1924: new 122	java/lang/StringBuilder
    //   1927: dup
    //   1928: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   1931: astore_0
    //   1932: aload_0
    //   1933: ldc -68
    //   1935: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1938: pop
    //   1939: aload_0
    //   1940: aload_3
    //   1941: invokevirtual 261	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1944: pop
    //   1945: aload_0
    //   1946: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1949: pop
    //   1950: ldc -107
    //   1952: astore_1
    //   1953: aload_1
    //   1954: astore_0
    //   1955: aload_3
    //   1956: ifnull +50 -> 2006
    //   1959: aload_1
    //   1960: astore_0
    //   1961: aload_3
    //   1962: arraylength
    //   1963: iconst_2
    //   1964: if_icmplt +42 -> 2006
    //   1967: aload_3
    //   1968: iconst_1
    //   1969: aaload
    //   1970: astore_1
    //   1971: aload_1
    //   1972: astore_0
    //   1973: aload_3
    //   1974: arraylength
    //   1975: iconst_2
    //   1976: if_icmple +30 -> 2006
    //   1979: new 122	java/lang/StringBuilder
    //   1982: dup
    //   1983: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   1986: astore_0
    //   1987: aload_0
    //   1988: aload_1
    //   1989: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1992: pop
    //   1993: aload_0
    //   1994: aload_3
    //   1995: iconst_2
    //   1996: aaload
    //   1997: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2000: pop
    //   2001: aload_0
    //   2002: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2005: astore_0
    //   2006: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   2009: astore_1
    //   2010: new 122	java/lang/StringBuilder
    //   2013: dup
    //   2014: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   2017: astore_1
    //   2018: aload_1
    //   2019: ldc_w 263
    //   2022: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2025: pop
    //   2026: aload_1
    //   2027: aload_0
    //   2028: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2031: pop
    //   2032: aload_1
    //   2033: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2036: pop
    //   2037: aload_0
    //   2038: areturn
    //   2039: aconst_null
    //   2040: areturn
    //   2041: astore_1
    //   2042: aload_0
    //   2043: ifnull +44 -> 2087
    //   2046: aload_0
    //   2047: invokevirtual 186	java/util/zip/ZipFile:close	()V
    //   2050: goto +37 -> 2087
    //   2053: astore_0
    //   2054: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   2057: astore_3
    //   2058: new 122	java/lang/StringBuilder
    //   2061: dup
    //   2062: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   2065: astore_3
    //   2066: aload_3
    //   2067: ldc -68
    //   2069: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2072: pop
    //   2073: aload_3
    //   2074: aload_0
    //   2075: invokevirtual 189	java/io/IOException:toString	()Ljava/lang/String;
    //   2078: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2081: pop
    //   2082: aload_3
    //   2083: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2086: pop
    //   2087: aload_1
    //   2088: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	2089	0	paramContext	Context
    //   0	2089	1	paramFile	File
    //   741	91	2	bool	boolean
    //   11	1589	3	str	String
    //   1614	169	3	localIOException1	java.io.IOException
    //   1786	297	3	localObject1	Object
    //   20	1735	4	localObject2	Object
    //   1767	18	4	localIOException2	java.io.IOException
    //   1789	78	4	localObject3	Object
    //   1879	18	4	localIOException3	java.io.IOException
    //   1901	1	4	localObject4	Object
    //   27	1628	5	localObject5	Object
    //   1661	7	5	localThrowable1	Throwable
    //   1671	1	5	localContext	Context
    //   1682	45	5	localThrowable2	Throwable
    //   1745	43	5	localObject6	Object
    //   1794	45	5	localIOException4	java.io.IOException
    //   1857	58	5	localObject7	Object
    //   7	1844	6	localObject8	Object
    //   37	1483	7	localObject9	Object
    //   54	293	8	localEnumeration	java.util.Enumeration
    //   69	1476	9	localObject10	Object
    //   419	1103	10	localObject11	Object
    // Exception table:
    //   from	to	target	type
    //   271	276	279	java/io/IOException
    //   967	972	975	java/io/IOException
    //   1114	1119	1122	java/io/IOException
    //   1261	1266	1269	java/io/IOException
    //   1408	1413	1416	java/io/IOException
    //   1551	1556	1559	java/io/IOException
    //   1601	1606	1614	java/io/IOException
    //   32	39	1661	java/lang/Throwable
    //   49	56	1661	java/lang/Throwable
    //   66	71	1661	java/lang/Throwable
    //   81	90	1661	java/lang/Throwable
    //   100	108	1661	java/lang/Throwable
    //   118	126	1661	java/lang/Throwable
    //   136	142	1661	java/lang/Throwable
    //   152	160	1661	java/lang/Throwable
    //   170	185	1661	java/lang/Throwable
    //   195	200	1661	java/lang/Throwable
    //   210	219	1661	java/lang/Throwable
    //   229	237	1661	java/lang/Throwable
    //   247	255	1661	java/lang/Throwable
    //   265	271	1661	java/lang/Throwable
    //   326	336	1661	java/lang/Throwable
    //   346	358	1661	java/lang/Throwable
    //   368	375	1661	java/lang/Throwable
    //   393	403	1661	java/lang/Throwable
    //   416	421	1661	java/lang/Throwable
    //   431	440	1661	java/lang/Throwable
    //   450	458	1661	java/lang/Throwable
    //   468	476	1661	java/lang/Throwable
    //   486	492	1661	java/lang/Throwable
    //   502	511	1661	java/lang/Throwable
    //   521	529	1661	java/lang/Throwable
    //   539	548	1661	java/lang/Throwable
    //   558	571	1661	java/lang/Throwable
    //   589	598	1661	java/lang/Throwable
    //   608	616	1661	java/lang/Throwable
    //   626	635	1661	java/lang/Throwable
    //   645	658	1661	java/lang/Throwable
    //   676	685	1661	java/lang/Throwable
    //   695	703	1661	java/lang/Throwable
    //   713	721	1661	java/lang/Throwable
    //   731	742	1661	java/lang/Throwable
    //   765	774	1661	java/lang/Throwable
    //   784	792	1661	java/lang/Throwable
    //   802	810	1661	java/lang/Throwable
    //   820	831	1661	java/lang/Throwable
    //   854	862	1661	java/lang/Throwable
    //   875	884	1661	java/lang/Throwable
    //   894	902	1661	java/lang/Throwable
    //   912	921	1661	java/lang/Throwable
    //   931	944	1661	java/lang/Throwable
    //   954	967	1661	java/lang/Throwable
    //   1022	1031	1661	java/lang/Throwable
    //   1041	1049	1661	java/lang/Throwable
    //   1059	1068	1661	java/lang/Throwable
    //   1078	1091	1661	java/lang/Throwable
    //   1101	1114	1661	java/lang/Throwable
    //   1169	1178	1661	java/lang/Throwable
    //   1188	1196	1661	java/lang/Throwable
    //   1206	1215	1661	java/lang/Throwable
    //   1225	1238	1661	java/lang/Throwable
    //   1248	1261	1661	java/lang/Throwable
    //   1316	1325	1661	java/lang/Throwable
    //   1335	1343	1661	java/lang/Throwable
    //   1353	1362	1661	java/lang/Throwable
    //   1372	1385	1661	java/lang/Throwable
    //   1395	1408	1661	java/lang/Throwable
    //   1463	1472	1661	java/lang/Throwable
    //   1482	1490	1661	java/lang/Throwable
    //   1500	1509	1661	java/lang/Throwable
    //   1519	1532	1661	java/lang/Throwable
    //   1542	1551	1661	java/lang/Throwable
    //   32	39	1666	java/io/IOException
    //   49	56	1666	java/io/IOException
    //   66	71	1666	java/io/IOException
    //   81	90	1666	java/io/IOException
    //   100	108	1666	java/io/IOException
    //   118	126	1666	java/io/IOException
    //   136	142	1666	java/io/IOException
    //   152	160	1666	java/io/IOException
    //   170	185	1666	java/io/IOException
    //   195	200	1666	java/io/IOException
    //   210	219	1666	java/io/IOException
    //   229	237	1666	java/io/IOException
    //   247	255	1666	java/io/IOException
    //   265	271	1666	java/io/IOException
    //   326	336	1666	java/io/IOException
    //   346	358	1666	java/io/IOException
    //   368	375	1666	java/io/IOException
    //   393	403	1666	java/io/IOException
    //   416	421	1666	java/io/IOException
    //   431	440	1666	java/io/IOException
    //   450	458	1666	java/io/IOException
    //   468	476	1666	java/io/IOException
    //   486	492	1666	java/io/IOException
    //   502	511	1666	java/io/IOException
    //   521	529	1666	java/io/IOException
    //   539	548	1666	java/io/IOException
    //   558	571	1666	java/io/IOException
    //   589	598	1666	java/io/IOException
    //   608	616	1666	java/io/IOException
    //   626	635	1666	java/io/IOException
    //   645	658	1666	java/io/IOException
    //   676	685	1666	java/io/IOException
    //   695	703	1666	java/io/IOException
    //   713	721	1666	java/io/IOException
    //   731	742	1666	java/io/IOException
    //   765	774	1666	java/io/IOException
    //   784	792	1666	java/io/IOException
    //   802	810	1666	java/io/IOException
    //   820	831	1666	java/io/IOException
    //   854	862	1666	java/io/IOException
    //   875	884	1666	java/io/IOException
    //   894	902	1666	java/io/IOException
    //   912	921	1666	java/io/IOException
    //   931	944	1666	java/io/IOException
    //   954	967	1666	java/io/IOException
    //   1022	1031	1666	java/io/IOException
    //   1041	1049	1666	java/io/IOException
    //   1059	1068	1666	java/io/IOException
    //   1078	1091	1666	java/io/IOException
    //   1101	1114	1666	java/io/IOException
    //   1169	1178	1666	java/io/IOException
    //   1188	1196	1666	java/io/IOException
    //   1206	1215	1666	java/io/IOException
    //   1225	1238	1666	java/io/IOException
    //   1248	1261	1666	java/io/IOException
    //   1316	1325	1666	java/io/IOException
    //   1335	1343	1666	java/io/IOException
    //   1353	1362	1666	java/io/IOException
    //   1372	1385	1666	java/io/IOException
    //   1395	1408	1666	java/io/IOException
    //   1463	1472	1666	java/io/IOException
    //   1482	1490	1666	java/io/IOException
    //   1500	1509	1666	java/io/IOException
    //   1519	1532	1666	java/io/IOException
    //   1542	1551	1666	java/io/IOException
    //   12	22	1676	finally
    //   12	22	1682	java/lang/Throwable
    //   1754	1759	1767	java/io/IOException
    //   12	22	1794	java/io/IOException
    //   1866	1871	1879	java/io/IOException
    //   32	39	2041	finally
    //   49	56	2041	finally
    //   66	71	2041	finally
    //   81	90	2041	finally
    //   100	108	2041	finally
    //   118	126	2041	finally
    //   136	142	2041	finally
    //   152	160	2041	finally
    //   170	185	2041	finally
    //   195	200	2041	finally
    //   210	219	2041	finally
    //   229	237	2041	finally
    //   247	255	2041	finally
    //   265	271	2041	finally
    //   326	336	2041	finally
    //   346	358	2041	finally
    //   368	375	2041	finally
    //   393	403	2041	finally
    //   416	421	2041	finally
    //   431	440	2041	finally
    //   450	458	2041	finally
    //   468	476	2041	finally
    //   486	492	2041	finally
    //   502	511	2041	finally
    //   521	529	2041	finally
    //   539	548	2041	finally
    //   558	571	2041	finally
    //   589	598	2041	finally
    //   608	616	2041	finally
    //   626	635	2041	finally
    //   645	658	2041	finally
    //   676	685	2041	finally
    //   695	703	2041	finally
    //   713	721	2041	finally
    //   731	742	2041	finally
    //   765	774	2041	finally
    //   784	792	2041	finally
    //   802	810	2041	finally
    //   820	831	2041	finally
    //   854	862	2041	finally
    //   875	884	2041	finally
    //   894	902	2041	finally
    //   912	921	2041	finally
    //   931	944	2041	finally
    //   954	967	2041	finally
    //   1022	1031	2041	finally
    //   1041	1049	2041	finally
    //   1059	1068	2041	finally
    //   1078	1091	2041	finally
    //   1101	1114	2041	finally
    //   1169	1178	2041	finally
    //   1188	1196	2041	finally
    //   1206	1215	2041	finally
    //   1225	1238	2041	finally
    //   1248	1261	2041	finally
    //   1316	1325	2041	finally
    //   1335	1343	2041	finally
    //   1353	1362	2041	finally
    //   1372	1385	2041	finally
    //   1395	1408	2041	finally
    //   1463	1472	2041	finally
    //   1482	1490	2041	finally
    //   1500	1509	2041	finally
    //   1519	1532	2041	finally
    //   1542	1551	2041	finally
    //   1693	1698	2041	finally
    //   1701	1710	2041	finally
    //   1713	1721	2041	finally
    //   1724	1735	2041	finally
    //   1738	1744	2041	finally
    //   1805	1810	2041	finally
    //   1813	1822	2041	finally
    //   1825	1833	2041	finally
    //   1836	1847	2041	finally
    //   1850	1856	2041	finally
    //   2046	2050	2053	java/io/IOException
  }
  
  /* Error */
  private static String a(java.util.zip.ZipFile paramZipFile, java.util.zip.ZipEntry paramZipEntry)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 268	java/util/zip/ZipFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   5: astore_0
    //   6: aload_0
    //   7: astore_1
    //   8: new 270	java/io/BufferedReader
    //   11: dup
    //   12: new 272	java/io/InputStreamReader
    //   15: dup
    //   16: aload_0
    //   17: invokestatic 278	java/nio/charset/Charset:defaultCharset	()Ljava/nio/charset/Charset;
    //   20: invokespecial 281	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
    //   23: invokespecial 284	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   26: invokevirtual 287	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   29: astore_3
    //   30: aload_0
    //   31: astore_1
    //   32: aload_3
    //   33: invokestatic 171	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   36: istore_2
    //   37: iload_2
    //   38: ifne +13 -> 51
    //   41: aload_0
    //   42: ifnull +7 -> 49
    //   45: aload_0
    //   46: invokevirtual 290	java/io/InputStream:close	()V
    //   49: aload_3
    //   50: areturn
    //   51: aload_0
    //   52: ifnull +81 -> 133
    //   55: aload_0
    //   56: invokevirtual 290	java/io/InputStream:close	()V
    //   59: aconst_null
    //   60: areturn
    //   61: astore_0
    //   62: goto +73 -> 135
    //   65: astore_3
    //   66: goto +12 -> 78
    //   69: astore_0
    //   70: aconst_null
    //   71: astore_1
    //   72: goto +63 -> 135
    //   75: astore_3
    //   76: aconst_null
    //   77: astore_0
    //   78: aload_0
    //   79: astore_1
    //   80: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   83: astore 4
    //   85: aload_0
    //   86: astore_1
    //   87: new 122	java/lang/StringBuilder
    //   90: dup
    //   91: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   94: astore 4
    //   96: aload_0
    //   97: astore_1
    //   98: aload 4
    //   100: ldc -68
    //   102: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: pop
    //   106: aload_0
    //   107: astore_1
    //   108: aload 4
    //   110: aload_3
    //   111: invokevirtual 132	java/lang/Exception:toString	()Ljava/lang/String;
    //   114: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: pop
    //   118: aload_0
    //   119: astore_1
    //   120: aload 4
    //   122: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   125: pop
    //   126: aload_0
    //   127: ifnull +6 -> 133
    //   130: goto -75 -> 55
    //   133: aconst_null
    //   134: areturn
    //   135: aload_1
    //   136: ifnull +7 -> 143
    //   139: aload_1
    //   140: invokevirtual 290	java/io/InputStream:close	()V
    //   143: aload_0
    //   144: athrow
    //   145: astore_0
    //   146: aload_3
    //   147: areturn
    //   148: astore_0
    //   149: aconst_null
    //   150: areturn
    //   151: astore_1
    //   152: goto -9 -> 143
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	155	0	paramZipFile	java.util.zip.ZipFile
    //   0	155	1	paramZipEntry	java.util.zip.ZipEntry
    //   36	2	2	bool	boolean
    //   29	21	3	str	String
    //   65	1	3	localException1	Exception
    //   75	72	3	localException2	Exception
    //   83	38	4	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   8	30	61	finally
    //   32	37	61	finally
    //   80	85	61	finally
    //   87	96	61	finally
    //   98	106	61	finally
    //   108	118	61	finally
    //   120	126	61	finally
    //   8	30	65	java/lang/Exception
    //   32	37	65	java/lang/Exception
    //   0	6	69	finally
    //   0	6	75	java/lang/Exception
    //   45	49	145	java/lang/Exception
    //   55	59	148	java/lang/Exception
    //   139	143	151	java/lang/Exception
  }
  
  /* Error */
  private static String a(java.util.zip.ZipFile paramZipFile, java.util.zip.ZipEntry paramZipEntry, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 228	java/util/zip/ZipEntry:isDirectory	()Z
    //   4: ifeq +5 -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: aload_0
    //   10: aload_1
    //   11: invokevirtual 268	java/util/zip/ZipFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   14: astore_0
    //   15: aload_0
    //   16: astore_1
    //   17: new 270	java/io/BufferedReader
    //   20: dup
    //   21: new 272	java/io/InputStreamReader
    //   24: dup
    //   25: aload_0
    //   26: invokestatic 278	java/nio/charset/Charset:defaultCharset	()Ljava/nio/charset/Charset;
    //   29: invokespecial 281	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
    //   32: invokespecial 284	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   35: astore 5
    //   37: aload_0
    //   38: astore_1
    //   39: aload 5
    //   41: invokevirtual 287	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   44: astore 4
    //   46: aload 4
    //   48: ifnull +279 -> 327
    //   51: aload_0
    //   52: astore_1
    //   53: aload 4
    //   55: aload_2
    //   56: invokevirtual 209	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   59: ifeq -22 -> 37
    //   62: aload 4
    //   64: astore_2
    //   65: goto +3 -> 68
    //   68: aload_0
    //   69: astore_1
    //   70: aload_2
    //   71: invokestatic 171	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   74: ifne +154 -> 228
    //   77: aload_0
    //   78: astore_1
    //   79: aload_2
    //   80: aload_3
    //   81: invokevirtual 258	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   84: astore_3
    //   85: aload_0
    //   86: astore_1
    //   87: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   90: astore 4
    //   92: aload_0
    //   93: astore_1
    //   94: new 122	java/lang/StringBuilder
    //   97: dup
    //   98: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   101: astore 4
    //   103: aload_0
    //   104: astore_1
    //   105: aload 4
    //   107: ldc_w 292
    //   110: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: pop
    //   114: aload_0
    //   115: astore_1
    //   116: aload 4
    //   118: aload_2
    //   119: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: pop
    //   123: aload_0
    //   124: astore_1
    //   125: aload 4
    //   127: ldc_w 294
    //   130: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   133: pop
    //   134: aload_0
    //   135: astore_1
    //   136: aload 4
    //   138: aload_3
    //   139: invokevirtual 136	java/lang/Object:toString	()Ljava/lang/String;
    //   142: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: pop
    //   146: aload_0
    //   147: astore_1
    //   148: aload 4
    //   150: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   153: pop
    //   154: ldc -107
    //   156: astore_1
    //   157: aload_1
    //   158: astore_2
    //   159: aload_3
    //   160: ifnull +17 -> 177
    //   163: aload_1
    //   164: astore_2
    //   165: aload_0
    //   166: astore_1
    //   167: aload_3
    //   168: arraylength
    //   169: iconst_2
    //   170: if_icmplt +7 -> 177
    //   173: aload_3
    //   174: iconst_1
    //   175: aaload
    //   176: astore_2
    //   177: aload_0
    //   178: astore_1
    //   179: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   182: astore_3
    //   183: aload_0
    //   184: astore_1
    //   185: new 122	java/lang/StringBuilder
    //   188: dup
    //   189: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   192: astore_3
    //   193: aload_0
    //   194: astore_1
    //   195: aload_3
    //   196: ldc_w 296
    //   199: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   202: pop
    //   203: aload_0
    //   204: astore_1
    //   205: aload_3
    //   206: aload_2
    //   207: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   210: pop
    //   211: aload_0
    //   212: astore_1
    //   213: aload_3
    //   214: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   217: pop
    //   218: aload_0
    //   219: ifnull +7 -> 226
    //   222: aload_0
    //   223: invokevirtual 290	java/io/InputStream:close	()V
    //   226: aload_2
    //   227: areturn
    //   228: aload_0
    //   229: ifnull +76 -> 305
    //   232: aload_0
    //   233: invokevirtual 290	java/io/InputStream:close	()V
    //   236: aconst_null
    //   237: areturn
    //   238: astore_0
    //   239: goto +68 -> 307
    //   242: astore_2
    //   243: goto +12 -> 255
    //   246: astore_0
    //   247: aconst_null
    //   248: astore_1
    //   249: goto +58 -> 307
    //   252: astore_2
    //   253: aconst_null
    //   254: astore_0
    //   255: aload_0
    //   256: astore_1
    //   257: getstatic 120	com/tencent/smtt/jsApi/impl/utils/SystemInstallReceiver:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   260: astore_3
    //   261: aload_0
    //   262: astore_1
    //   263: new 122	java/lang/StringBuilder
    //   266: dup
    //   267: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   270: astore_3
    //   271: aload_0
    //   272: astore_1
    //   273: aload_3
    //   274: ldc -68
    //   276: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   279: pop
    //   280: aload_0
    //   281: astore_1
    //   282: aload_3
    //   283: aload_2
    //   284: invokevirtual 132	java/lang/Exception:toString	()Ljava/lang/String;
    //   287: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   290: pop
    //   291: aload_0
    //   292: astore_1
    //   293: aload_3
    //   294: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   297: pop
    //   298: aload_0
    //   299: ifnull +6 -> 305
    //   302: goto -70 -> 232
    //   305: aconst_null
    //   306: areturn
    //   307: aload_1
    //   308: ifnull +7 -> 315
    //   311: aload_1
    //   312: invokevirtual 290	java/io/InputStream:close	()V
    //   315: aload_0
    //   316: athrow
    //   317: astore_0
    //   318: aload_2
    //   319: areturn
    //   320: astore_0
    //   321: aconst_null
    //   322: areturn
    //   323: astore_1
    //   324: goto -9 -> 315
    //   327: aconst_null
    //   328: astore_2
    //   329: goto -261 -> 68
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	332	0	paramZipFile	java.util.zip.ZipFile
    //   0	332	1	paramZipEntry	java.util.zip.ZipEntry
    //   0	332	2	paramString1	String
    //   0	332	3	paramString2	String
    //   44	105	4	localObject	Object
    //   35	5	5	localBufferedReader	java.io.BufferedReader
    // Exception table:
    //   from	to	target	type
    //   17	37	238	finally
    //   39	46	238	finally
    //   53	62	238	finally
    //   70	77	238	finally
    //   79	85	238	finally
    //   87	92	238	finally
    //   94	103	238	finally
    //   105	114	238	finally
    //   116	123	238	finally
    //   125	134	238	finally
    //   136	146	238	finally
    //   148	154	238	finally
    //   167	173	238	finally
    //   179	183	238	finally
    //   185	193	238	finally
    //   195	203	238	finally
    //   205	211	238	finally
    //   213	218	238	finally
    //   257	261	238	finally
    //   263	271	238	finally
    //   273	280	238	finally
    //   282	291	238	finally
    //   293	298	238	finally
    //   17	37	242	java/lang/Exception
    //   39	46	242	java/lang/Exception
    //   53	62	242	java/lang/Exception
    //   70	77	242	java/lang/Exception
    //   79	85	242	java/lang/Exception
    //   87	92	242	java/lang/Exception
    //   94	103	242	java/lang/Exception
    //   105	114	242	java/lang/Exception
    //   116	123	242	java/lang/Exception
    //   125	134	242	java/lang/Exception
    //   136	146	242	java/lang/Exception
    //   148	154	242	java/lang/Exception
    //   167	173	242	java/lang/Exception
    //   179	183	242	java/lang/Exception
    //   185	193	242	java/lang/Exception
    //   195	203	242	java/lang/Exception
    //   205	211	242	java/lang/Exception
    //   213	218	242	java/lang/Exception
    //   9	15	246	finally
    //   9	15	252	java/lang/Exception
    //   222	226	317	java/lang/Exception
    //   232	236	320	java/lang/Exception
    //   311	315	323	java/lang/Exception
  }
  
  private void a(Context paramContext, String paramString)
  {
    if (jdField_a_of_type_JavaUtilMap.isEmpty()) {
      return;
    }
    jdField_a_of_type_JavaUtilMap.remove(paramString);
    if (jdField_a_of_type_JavaUtilMap.isEmpty()) {
      try
      {
        a(paramContext);
        return;
      }
      catch (IllegalArgumentException paramContext)
      {
        paramString = jdField_a_of_type_JavaLangString;
        paramString = new StringBuilder();
        paramString.append("msg:");
        paramString.append(paramContext.toString());
        paramString.toString();
      }
    }
  }
  
  private void a(Context paramContext, String paramString, CallbackInfo paramCallbackInfo)
  {
    if (jdField_a_of_type_JavaUtilMap.isEmpty()) {
      registerReceiver(paramContext);
    }
    jdField_a_of_type_JavaUtilMap.put(paramString, paramCallbackInfo);
  }
  
  private void a(IDownloadCallback paramIDownloadCallback, String paramString1, String paramString2, int paramInt)
  {
    paramIDownloadCallback.onApkInstallSuccess(paramInt);
    BaseDownloadManager.getInstance().deleteTask(paramInt, true);
    if (paramIDownloadCallback.destroyimpl()) {
      return;
    }
    JSONObject localJSONObject = new JSONObject();
    Object localObject = jdField_a_of_type_JavaLangString;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[jsapi] notifyJSInstallSucc callback: ");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(", ret: ");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).toString();
    try
    {
      localJSONObject.put("ret", paramString2);
      paramIDownloadCallback.sendSuccJsCallback(paramString1, localJSONObject);
      return;
    }
    catch (Exception paramIDownloadCallback) {}
  }
  
  public static AppChannelInfo getAPPChannel(Context paramContext, File paramFile)
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (paramFile != null)
    {
      localObject1 = localObject2;
      if (paramFile.isFile())
      {
        localObject1 = localObject2;
        if (paramFile.exists())
        {
          localObject1 = a(paramContext, paramFile);
          if (localObject1 == null) {
            return null;
          }
          if (TextUtils.isEmpty(((AppChannelInfo)localObject1).channel)) {
            ((AppChannelInfo)localObject1).channel = a(paramContext, paramFile);
          }
        }
      }
    }
    return (AppChannelInfo)localObject1;
  }
  
  public static SystemInstallReceiver getInstance()
  {
    if (jdField_a_of_type_ComTencentSmttJsApiImplUtilsSystemInstallReceiver == null) {
      try
      {
        if (jdField_a_of_type_ComTencentSmttJsApiImplUtilsSystemInstallReceiver == null) {
          jdField_a_of_type_ComTencentSmttJsApiImplUtilsSystemInstallReceiver = new SystemInstallReceiver();
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentSmttJsApiImplUtilsSystemInstallReceiver;
  }
  
  public void JsObjDestroyed(Context paramContext, IDownloadCallback paramIDownloadCallback)
  {
    if (paramIDownloadCallback == null) {
      return;
    }
    jdField_a_of_type_JavaUtilMap.clear();
    try
    {
      a(paramContext);
      return;
    }
    catch (IllegalArgumentException paramContext)
    {
      paramIDownloadCallback = jdField_a_of_type_JavaLangString;
      paramIDownloadCallback = new StringBuilder();
      paramIDownloadCallback.append("msg:");
      paramIDownloadCallback.append(paramContext.toString());
      paramIDownloadCallback.toString();
    }
  }
  
  void a(Context paramContext)
  {
    this.jdField_a_of_type_Boolean = false;
    paramContext.unregisterReceiver(this);
    paramContext = jdField_a_of_type_JavaLangString;
  }
  
  public String getCurrentPackageName()
  {
    return this.b;
  }
  
  public int installApkFile(Context paramContext, String paramString1, IDownloadCallback paramIDownloadCallback, String paramString2, int paramInt)
  {
    return installApkFile(paramContext, paramString1, paramIDownloadCallback, paramString2, paramInt, null);
  }
  
  public int installApkFile(Context paramContext, String paramString1, IDownloadCallback paramIDownloadCallback, String paramString2, int paramInt, String paramString3)
  {
    for (;;)
    {
      try
      {
        localObject2 = BaseDownloadManager.getInstance().getTask(paramString1);
        localObject1 = localObject2;
        if (localObject2 == null) {
          localObject1 = BaseDownloadManager.getInstance().getDownloadCompletedTaskFromDatabase(paramString1);
        }
        if (localObject1 != null)
        {
          if (((DownloadTask)localObject1).getStatus() != 3) {
            return 1;
          }
          localFile = BaseDownloadManager.getInstance().getDownloadFileByTask((DownloadTask)localObject1);
          if ((localFile != null) && (localFile.exists()))
          {
            boolean bool = localFile.isFile();
            if (bool) {}
          }
        }
      }
      catch (Exception paramContext)
      {
        Object localObject2;
        Object localObject1;
        File localFile;
        continue;
      }
      try
      {
        localObject2 = paramContext.getPackageManager().getPackageArchiveInfo(localFile.getAbsolutePath(), 0);
        if (localObject2 == null) {
          return 3;
        }
        localObject1 = ((PackageInfo)localObject2).packageName;
      }
      catch (Exception localException1)
      {
        continue;
      }
      try
      {
        this.b = ((PackageInfo)localObject2).packageName;
        localObject2 = Md5Utils.getMD5(localFile);
      }
      catch (Exception localException2) {}
    }
    localObject1 = null;
    localObject2 = null;
    if (needInstallUseYYB(paramContext, paramString1)) {
      InstallUtil.startInstall(paramContext, localFile, paramString3, true);
    } else {
      InstallUtil.startInstall(paramContext, localFile, paramString3);
    }
    if (localObject1 != null)
    {
      ReporterUtil.getSingleton().reportInstallStart(paramContext, paramString1, (String)localObject1);
      a(paramContext, (String)localObject1, new CallbackInfo(paramString2, paramString1, paramIDownloadCallback, paramInt, (String)localObject2));
    }
    return 0;
    return 2;
    return 1;
    return -10;
  }
  
  public boolean needInstallUseYYB(Context paramContext, String paramString)
  {
    if (!Configuration.getInstance(paramContext).isEnableInstallAppUseYYB())
    {
      paramContext = jdField_a_of_type_JavaLangString;
      return false;
    }
    paramContext = ReporterUtil.getSingleton().getReportInfo(paramContext, paramString, "2");
    if (paramContext == null)
    {
      paramContext = jdField_a_of_type_JavaLangString;
      return false;
    }
    paramContext = paramContext.pageUrl;
    if (TextUtils.isEmpty(paramContext))
    {
      paramContext = jdField_a_of_type_JavaLangString;
      return false;
    }
    paramString = UrlUtils.getHost(paramContext);
    if (TextUtils.isEmpty(paramString))
    {
      paramContext = jdField_a_of_type_JavaLangString;
      return false;
    }
    Object localObject = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(331);
    if (((ArrayList)localObject).isEmpty())
    {
      paramContext = jdField_a_of_type_JavaLangString;
      return false;
    }
    DomainMatcher localDomainMatcher = new DomainMatcher();
    localDomainMatcher.addDomainList((ArrayList)localObject);
    if (!localDomainMatcher.isContainsDomain(paramString))
    {
      paramContext = jdField_a_of_type_JavaLangString;
      return false;
    }
    localObject = PublicSettingManager.getInstance().getCloudStringSwitch("TBS_UNION_LAND_PAGE_PARAM", "siteset=15");
    if ((!TextUtils.isEmpty((CharSequence)localObject)) && (paramContext.contains((CharSequence)localObject)))
    {
      paramContext = jdField_a_of_type_JavaLangString;
      return true;
    }
    if (paramString.equals("ag.qq.com"))
    {
      paramContext = jdField_a_of_type_JavaLangString;
      return true;
    }
    paramContext = jdField_a_of_type_JavaLangString;
    return false;
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ((paramIntent != null) && (paramIntent.getAction() != null) && (paramIntent.getAction().equals("android.intent.action.PACKAGE_ADDED")))
    {
      Object localObject2 = "";
      Object localObject1 = jdField_a_of_type_JavaLangString;
      localObject1 = localObject2;
      if (paramIntent.getData() != null)
      {
        localObject1 = localObject2;
        if ("package".equals(paramIntent.getData().getScheme())) {
          localObject1 = paramIntent.getData().getSchemeSpecificPart();
        }
      }
      if (jdField_a_of_type_JavaUtilMap.containsKey(localObject1))
      {
        localObject2 = (CallbackInfo)jdField_a_of_type_JavaUtilMap.get(localObject1);
        if ((localObject2 != null) && (((CallbackInfo)localObject2).jdField_a_of_type_JavaLangString != null) && (((CallbackInfo)localObject2).jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback != null))
        {
          if (!"com.tencent.mtt".equals(localObject1))
          {
            paramIntent = BaseDownloadManager.getInstance().getTaskByID(((CallbackInfo)localObject2).jdField_a_of_type_Int);
            Object localObject5 = BaseDownloadManager.getInstance().getDownloadFileByTask(paramIntent);
            localObject3 = ReporterUtil.getSingleton().getReportInfo(((CallbackInfo)localObject2).jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext(), ((CallbackInfo)localObject2).b, "2");
            if ((localObject5 != null) && (((File)localObject5).exists()) && (localObject3 != null))
            {
              localObject4 = PackageUtils.getPackageMd5((String)localObject1);
              paramIntent = ((CallbackInfo)localObject2).c;
              if (TextUtils.isEmpty(paramIntent)) {
                paramIntent = Md5Utils.getMD5((File)localObject5);
              }
              localObject5 = jdField_a_of_type_JavaLangString;
              localObject5 = new StringBuilder();
              ((StringBuilder)localObject5).append("install:");
              ((StringBuilder)localObject5).append((String)localObject1);
              ((StringBuilder)localObject5).append(" installedMd5:");
              ((StringBuilder)localObject5).append((String)localObject4);
              ((StringBuilder)localObject5).append(" downloadMd5");
              ((StringBuilder)localObject5).append(paramIntent);
              ((StringBuilder)localObject5).toString();
              if ((!TextUtils.isEmpty(paramIntent)) && (!TextUtils.isEmpty((CharSequence)localObject4)) && (!((String)localObject4).equals(paramIntent))) {
                ReporterUtil.getSingleton().reportSystemReplacePackageEvent(paramContext, (String)localObject1, ((CallbackInfo)localObject2).b, paramIntent, (String)localObject4, ((ReporterUtil.ReportInfo)localObject3).refer);
              }
            }
          }
          ReporterUtil.getSingleton().reportDownloadMsg(((CallbackInfo)localObject2).jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext(), ((CallbackInfo)localObject2).b, "2", null, (String)localObject1);
          if (((CallbackInfo)localObject2).jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.isDownloadIntercept())
          {
            paramIntent = ((CallbackInfo)localObject2).jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getHostBeforeInterceptDownload();
            if (!TextUtils.isEmpty(paramIntent))
            {
              localObject3 = new HashMap();
              ((HashMap)localObject3).put("host", paramIntent);
              X5CoreBeaconUploader.getInstance(((CallbackInfo)localObject2).jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext()).upLoadToBeacon("MTT_DOWNLOAD_INTERCEPT_INSTALL_HOST", (Map)localObject3);
              localObject3 = new StringBuilder();
              ((StringBuilder)localObject3).append("host=");
              ((StringBuilder)localObject3).append(paramIntent);
              ((StringBuilder)localObject3).toString();
            }
          }
          paramIntent = ((CallbackInfo)localObject2).jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback;
          Object localObject3 = ((CallbackInfo)localObject2).jdField_a_of_type_JavaLangString;
          Object localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append("install:");
          ((StringBuilder)localObject4).append((String)localObject1);
          a(paramIntent, (String)localObject3, ((StringBuilder)localObject4).toString(), ((CallbackInfo)localObject2).jdField_a_of_type_Int);
        }
        a(paramContext, (String)localObject1);
      }
    }
  }
  
  public void registerReceiver(Context paramContext)
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    try
    {
      IntentFilter localIntentFilter = new IntentFilter();
      localIntentFilter.addAction("android.intent.action.PACKAGE_ADDED");
      localIntentFilter.addAction("android.intent.action.PACKAGE_INSTALL");
      localIntentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
      localIntentFilter.addDataScheme("package");
      paramContext.registerReceiver(this, localIntentFilter);
      this.jdField_a_of_type_Boolean = true;
      paramContext = jdField_a_of_type_JavaLangString;
      return;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\utils\SystemInstallReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */