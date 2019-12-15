package com.tencent.common.plugin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.utils.TbsMode;
import com.tencent.tbs.common.MTT.PluginItem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class QBPluginDBHelper
{
  public static final String COLUMN_ANTIHIJACK_URL = "antihijackurl";
  public static final String COLUMN_APP_SUB_TYPE = "appSubtype";
  public static final String COLUMN_DOWNLOAD_DIR = "downPath";
  public static final String COLUMN_EXT_INTEGER_1 = "extInteger1";
  public static final String COLUMN_EXT_INTEGER_2 = "extInteger2";
  public static final String COLUMN_EXT_STRING_1 = "extString1";
  public static final String COLUMN_EXT_STRING_2 = "extString2";
  public static final String COLUMN_EXT_STRING_3 = "extString3";
  public static final String COLUMN_EXT_STRING_4 = "extString4";
  public static final String COLUMN_ICON_URL = "iconUrl";
  public static final String COLUMN_ID = "ID";
  public static final String COLUMN_INSTALL_DIR = "installPath";
  public static final String COLUMN_INSTALL_VERSION = "installVersion";
  public static final String COLUMN_ISFORCEUPDATE = "isforceupdate";
  public static final String COLUMN_ISINSTALL = "isInstall";
  public static final String COLUMN_ISNOTICE = "isNotice";
  public static final String COLUMN_ISOPEN = "isOpen";
  public static final String COLUMN_IsZipFileUpdate = "isZipFileUpdate";
  public static final String COLUMN_LOCATION = "location";
  public static final String COLUMN_ORDER = "order_index";
  public static final String COLUMN_PACKAGE_NAME = "packageName";
  public static final String COLUMN_PACKAGE_SIZE = "packageSize";
  public static final String COLUMN_PLUGININFO_FROM = "infofrom";
  public static final String COLUMN_PLUGININFO_ISNEEDUPDATE = "needUpdate";
  public static final String COLUMN_SIGNATURE = "signature";
  public static final String COLUMN_TITLE = "title";
  public static final String COLUMN_UNZIP_DIR = "unzipPath";
  public static final String COLUMN_UPDATE_SIZE = "updatePackageSize";
  public static final String COLUMN_UPDATE_STATUS = "updateStatus";
  public static final String COLUMN_UPDATE_URL = "updateUrl";
  public static final String COLUMN_URL = "url";
  public static final String COLUMN_VERSION = "version";
  public static final String PLUGIN_DB_NAME = "plugin_db";
  public static final int PLUGIN_DB_VERSION = 1;
  public static final String TABLE_DEFAULT_PLUGS_NAME = "plugins";
  private static QBPluginDBHelper jdField_a_of_type_ComTencentCommonPluginQBPluginDBHelper;
  private Context jdField_a_of_type_AndroidContentContext = null;
  private DBHelper jdField_a_of_type_ComTencentCommonPluginDBHelper = null;
  PluginComparator jdField_a_of_type_ComTencentCommonPluginQBPluginDBHelper$PluginComparator;
  private Object jdField_a_of_type_JavaLangObject = new Object();
  private String jdField_a_of_type_JavaLangString = "PluginCacheDBHelper";
  private List<QBPluginItemInfo> jdField_a_of_type_JavaUtilList = new ArrayList();
  private Object b = new Object();
  public Exception mLastException = null;
  public boolean mPluginDatasInited = false;
  public int mPluginGetError = 0;
  
  public QBPluginDBHelper(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_ComTencentCommonPluginQBPluginDBHelper$PluginComparator = new PluginComparator();
    if ((!a(paramContext)) && (QBPluginServiceImpl.getPluginRelateFunc(1) != null)) {
      QBPluginServiceImpl.getPluginRelateFunc(1).userBehaviorStatistics("ZZNR8");
    }
  }
  
  private int a(Map<String, String> paramMap, String paramString, int paramInt)
    throws Exception
  {
    HashMap localHashMap = new HashMap();
    paramMap = paramMap.entrySet().iterator();
    Map.Entry localEntry;
    while (paramMap.hasNext())
    {
      localEntry = (Map.Entry)paramMap.next();
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("packageName = '");
      ((StringBuilder)localObject).append((String)localEntry.getKey());
      ((StringBuilder)localObject).append("' AND ");
      ((StringBuilder)localObject).append("infofrom");
      ((StringBuilder)localObject).append("=");
      ((StringBuilder)localObject).append(paramInt);
      localObject = ((StringBuilder)localObject).toString();
      ContentValues localContentValues = new ContentValues();
      localContentValues.put(paramString, (String)localEntry.getValue());
      localHashMap.put(localObject, localContentValues);
    }
    paramMap = this.jdField_a_of_type_ComTencentCommonPluginDBHelper;
    paramMap.beginTransaction();
    try
    {
      paramString = localHashMap.entrySet().iterator();
      while (paramString.hasNext())
      {
        localEntry = (Map.Entry)paramString.next();
        paramMap.update("plugins", (ContentValues)localEntry.getValue(), (String)localEntry.getKey());
      }
      paramMap.endTransaction();
      return localHashMap.size();
    }
    catch (Exception paramString)
    {
      paramMap.endTransactionOnly();
      throw paramString;
    }
  }
  
  private ContentValues a(PluginItem paramPluginItem, int paramInt)
  {
    if (paramPluginItem == null) {
      return null;
    }
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("title", paramPluginItem.sTitle);
    localContentValues.put("url", paramPluginItem.sUrl);
    if ((paramPluginItem.vBackURL != null) && (paramPluginItem.vBackURL.size() > 0)) {
      localContentValues.put("antihijackurl", (String)paramPluginItem.vBackURL.get(0));
    } else {
      localContentValues.put("antihijackurl", "");
    }
    localContentValues.put("iconUrl", paramPluginItem.sIconUrl);
    localContentValues.put("packageName", paramPluginItem.sPkgName);
    localContentValues.put("appSubtype", Integer.valueOf(paramPluginItem.iPluginType));
    localContentValues.put("version", Integer.valueOf(paramPluginItem.iVersion));
    localContentValues.put("packageSize", Integer.valueOf(paramPluginItem.iSize));
    localContentValues.put("isforceupdate", Integer.valueOf(paramPluginItem.iUpdateType));
    localContentValues.put("order_index", Integer.valueOf(paramPluginItem.iOrder));
    localContentValues.put("extString1", paramPluginItem.sTips);
    localContentValues.put("extString2", paramPluginItem.sExt);
    localContentValues.put("signature", paramPluginItem.sSignature);
    localContentValues.put("location", Integer.valueOf(paramPluginItem.iLocation));
    if (!TextUtils.isEmpty(paramPluginItem.s256MD5)) {
      localContentValues.put("extString4", paramPluginItem.s256MD5);
    }
    localContentValues.put("infofrom", Integer.valueOf(paramInt));
    return localContentValues;
  }
  
  private QBPluginItemInfo a(Cursor paramCursor)
  {
    QBPluginItemInfo localQBPluginItemInfo = new QBPluginItemInfo();
    try
    {
      localQBPluginItemInfo.mTitle = paramCursor.getString(paramCursor.getColumnIndex("title"));
      localQBPluginItemInfo.mUrl = paramCursor.getString(paramCursor.getColumnIndex("url"));
      localQBPluginItemInfo.mIconUrl = paramCursor.getString(paramCursor.getColumnIndex("iconUrl"));
      localQBPluginItemInfo.mPackageName = paramCursor.getString(paramCursor.getColumnIndex("packageName"));
      localQBPluginItemInfo.mPluginType = paramCursor.getInt(paramCursor.getColumnIndex("appSubtype"));
      localQBPluginItemInfo.mVersion = paramCursor.getString(paramCursor.getColumnIndex("version"));
      localQBPluginItemInfo.mPackageSize = paramCursor.getString(paramCursor.getColumnIndex("packageSize"));
      localQBPluginItemInfo.mIsInstall = paramCursor.getInt(paramCursor.getColumnIndex("isInstall"));
      localQBPluginItemInfo.mUpdateType = paramCursor.getInt(paramCursor.getColumnIndex("isforceupdate"));
      localQBPluginItemInfo.mOrder = paramCursor.getInt(paramCursor.getColumnIndex("order_index"));
      localQBPluginItemInfo.mLocation = paramCursor.getInt(paramCursor.getColumnIndex("location"));
      localQBPluginItemInfo.mDetailSumary = paramCursor.getString(paramCursor.getColumnIndex("extString1"));
      localQBPluginItemInfo.mExt = paramCursor.getString(paramCursor.getColumnIndex("extString2"));
      localQBPluginItemInfo.mSignature = paramCursor.getString(paramCursor.getColumnIndex("signature"));
      localQBPluginItemInfo.mDownloadDir = paramCursor.getString(paramCursor.getColumnIndex("downPath"));
      localQBPluginItemInfo.mInstallDir = paramCursor.getString(paramCursor.getColumnIndex("installPath"));
      localQBPluginItemInfo.mUnzipDir = paramCursor.getString(paramCursor.getColumnIndex("unzipPath"));
      localQBPluginItemInfo.mIsZipFileUpdate = paramCursor.getInt(paramCursor.getColumnIndex("isZipFileUpdate"));
      localQBPluginItemInfo.mPluginCompatiID = paramCursor.getString(paramCursor.getColumnIndex("extString3"));
      localQBPluginItemInfo.mMd5 = paramCursor.getString(paramCursor.getColumnIndex("extString4"));
      localQBPluginItemInfo.mZipJarPluginType = paramCursor.getInt(paramCursor.getColumnIndex("isNotice"));
      localQBPluginItemInfo.mDownloadFileName = paramCursor.getString(paramCursor.getColumnIndex("updateUrl"));
      localQBPluginItemInfo.mInstallVersion = paramCursor.getString(paramCursor.getColumnIndex("installVersion"));
      localQBPluginItemInfo.mAntiHijackUrl = paramCursor.getString(paramCursor.getColumnIndex("antihijackurl"));
      localQBPluginItemInfo.mInfoFrom = paramCursor.getInt(paramCursor.getColumnIndex("infofrom"));
      localQBPluginItemInfo.isNeedUpdate = paramCursor.getInt(paramCursor.getColumnIndex("needUpdate"));
      return localQBPluginItemInfo;
    }
    catch (Exception paramCursor)
    {
      paramCursor.printStackTrace();
    }
    return null;
  }
  
  /* Error */
  private void a()
  {
    // Byte code:
    //   0: new 253	com/tencent/common/plugin/DBHelper
    //   3: dup
    //   4: aload_0
    //   5: getfield 157	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   8: ldc_w 446
    //   11: bipush 37
    //   13: invokespecial 449	com/tencent/common/plugin/DBHelper:<init>	(Landroid/content/Context;Ljava/lang/String;I)V
    //   16: astore 6
    //   18: aload 6
    //   20: ldc 117
    //   22: invokevirtual 453	com/tencent/common/plugin/DBHelper:exist	(Ljava/lang/String;)Z
    //   25: istore_2
    //   26: iload_2
    //   27: ifne +9 -> 36
    //   30: return
    //   31: astore_3
    //   32: aload_3
    //   33: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   36: aconst_null
    //   37: astore_3
    //   38: aconst_null
    //   39: astore 4
    //   41: aload 6
    //   43: ldc 117
    //   45: aconst_null
    //   46: ldc_w 455
    //   49: invokevirtual 459	com/tencent/common/plugin/DBHelper:query	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   52: astore 5
    //   54: aload 5
    //   56: ifnonnull +16 -> 72
    //   59: aload 5
    //   61: ifnull +10 -> 71
    //   64: aload 5
    //   66: invokeinterface 462 1 0
    //   71: return
    //   72: aload 5
    //   74: astore 4
    //   76: aload 5
    //   78: astore_3
    //   79: aload 6
    //   81: ldc 117
    //   83: ldc 27
    //   85: invokevirtual 466	com/tencent/common/plugin/DBHelper:isColumnExist	(Ljava/lang/String;Ljava/lang/String;)Z
    //   88: istore_2
    //   89: iload_2
    //   90: ifne +16 -> 106
    //   93: aload 5
    //   95: ifnull +10 -> 105
    //   98: aload 5
    //   100: invokeinterface 462 1 0
    //   105: return
    //   106: aload 5
    //   108: astore 4
    //   110: aload 5
    //   112: astore_3
    //   113: aload_0
    //   114: getfield 144	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper	Lcom/tencent/common/plugin/DBHelper;
    //   117: invokevirtual 256	com/tencent/common/plugin/DBHelper:beginTransaction	()V
    //   120: aload 5
    //   122: astore 4
    //   124: aload 5
    //   126: astore_3
    //   127: aload 5
    //   129: invokeinterface 469 1 0
    //   134: istore_2
    //   135: iload_2
    //   136: ifeq +1226 -> 1362
    //   139: aload 5
    //   141: astore 4
    //   143: aload 5
    //   145: aload 5
    //   147: ldc 75
    //   149: invokeinterface 472 2 0
    //   154: invokeinterface 359 2 0
    //   159: astore_3
    //   160: iconst_1
    //   161: istore_1
    //   162: aload 5
    //   164: astore 4
    //   166: ldc_w 474
    //   169: aload_3
    //   170: invokevirtual 477	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   173: ifeq +5 -> 178
    //   176: iconst_2
    //   177: istore_1
    //   178: aload 5
    //   180: astore 4
    //   182: new 215	java/lang/StringBuilder
    //   185: dup
    //   186: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   189: astore_3
    //   190: aload 5
    //   192: astore 4
    //   194: aload_3
    //   195: ldc_w 479
    //   198: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   201: pop
    //   202: aload 5
    //   204: astore 4
    //   206: aload_3
    //   207: aload 5
    //   209: aload 5
    //   211: ldc 45
    //   213: invokeinterface 472 2 0
    //   218: invokeinterface 375 2 0
    //   223: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   226: pop
    //   227: aload 5
    //   229: astore 4
    //   231: aload_3
    //   232: ldc_w 481
    //   235: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   238: pop
    //   239: aload 5
    //   241: astore 4
    //   243: aload_3
    //   244: aload 5
    //   246: aload 5
    //   248: ldc 90
    //   250: invokeinterface 472 2 0
    //   255: invokeinterface 359 2 0
    //   260: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   263: pop
    //   264: aload 5
    //   266: astore 4
    //   268: aload_3
    //   269: ldc_w 483
    //   272: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   275: pop
    //   276: aload 5
    //   278: astore 4
    //   280: aload_3
    //   281: aload 5
    //   283: aload 5
    //   285: ldc 105
    //   287: invokeinterface 472 2 0
    //   292: invokeinterface 359 2 0
    //   297: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   300: pop
    //   301: aload 5
    //   303: astore 4
    //   305: aload_3
    //   306: ldc_w 483
    //   309: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   312: pop
    //   313: aload 5
    //   315: astore 4
    //   317: aload_3
    //   318: aload 5
    //   320: aload 5
    //   322: ldc 42
    //   324: invokeinterface 472 2 0
    //   329: invokeinterface 359 2 0
    //   334: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   337: pop
    //   338: aload 5
    //   340: astore 4
    //   342: aload_3
    //   343: ldc_w 483
    //   346: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   349: pop
    //   350: aload 5
    //   352: astore 4
    //   354: aload_3
    //   355: aload 5
    //   357: aload 5
    //   359: ldc 75
    //   361: invokeinterface 472 2 0
    //   366: invokeinterface 359 2 0
    //   371: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   374: pop
    //   375: aload 5
    //   377: astore 4
    //   379: aload_3
    //   380: ldc_w 485
    //   383: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   386: pop
    //   387: aload 5
    //   389: astore 4
    //   391: aload_3
    //   392: aload 5
    //   394: aload 5
    //   396: ldc 18
    //   398: invokeinterface 472 2 0
    //   403: invokeinterface 375 2 0
    //   408: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   411: pop
    //   412: aload 5
    //   414: astore 4
    //   416: aload_3
    //   417: ldc_w 487
    //   420: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   423: pop
    //   424: aload 5
    //   426: astore 4
    //   428: aload_3
    //   429: aload 5
    //   431: aload 5
    //   433: ldc 108
    //   435: invokeinterface 472 2 0
    //   440: invokeinterface 375 2 0
    //   445: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   448: pop
    //   449: aload 5
    //   451: astore 4
    //   453: aload_3
    //   454: ldc_w 487
    //   457: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   460: pop
    //   461: aload 5
    //   463: astore 4
    //   465: aload_3
    //   466: aload 5
    //   468: aload 5
    //   470: ldc 78
    //   472: invokeinterface 472 2 0
    //   477: invokeinterface 375 2 0
    //   482: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   485: pop
    //   486: aload 5
    //   488: astore 4
    //   490: aload_3
    //   491: ldc_w 487
    //   494: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   497: pop
    //   498: aload 5
    //   500: astore 4
    //   502: aload_3
    //   503: aload 5
    //   505: aload 5
    //   507: ldc 99
    //   509: invokeinterface 472 2 0
    //   514: invokeinterface 375 2 0
    //   519: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   522: pop
    //   523: aload 5
    //   525: astore 4
    //   527: aload_3
    //   528: ldc_w 481
    //   531: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   534: pop
    //   535: aload 5
    //   537: astore 4
    //   539: aload_3
    //   540: aload 5
    //   542: aload 5
    //   544: ldc 102
    //   546: invokeinterface 472 2 0
    //   551: invokeinterface 359 2 0
    //   556: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   559: pop
    //   560: aload 5
    //   562: astore 4
    //   564: aload_3
    //   565: ldc_w 485
    //   568: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   571: pop
    //   572: aload 5
    //   574: astore 4
    //   576: aload_3
    //   577: aload 5
    //   579: aload 5
    //   581: ldc 96
    //   583: invokeinterface 472 2 0
    //   588: invokeinterface 375 2 0
    //   593: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   596: pop
    //   597: aload 5
    //   599: astore 4
    //   601: aload_3
    //   602: ldc_w 487
    //   605: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   608: pop
    //   609: aload 5
    //   611: astore 4
    //   613: aload_3
    //   614: aload 5
    //   616: aload 5
    //   618: ldc 54
    //   620: invokeinterface 472 2 0
    //   625: invokeinterface 375 2 0
    //   630: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   633: pop
    //   634: aload 5
    //   636: astore 4
    //   638: aload_3
    //   639: ldc_w 487
    //   642: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   645: pop
    //   646: aload 5
    //   648: astore 4
    //   650: aload_3
    //   651: aload 5
    //   653: aload 5
    //   655: ldc 69
    //   657: invokeinterface 472 2 0
    //   662: invokeinterface 375 2 0
    //   667: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   670: pop
    //   671: aload 5
    //   673: astore 4
    //   675: aload_3
    //   676: ldc_w 481
    //   679: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   682: pop
    //   683: aload 5
    //   685: astore 4
    //   687: aload_3
    //   688: aload 5
    //   690: aload 5
    //   692: ldc 21
    //   694: invokeinterface 472 2 0
    //   699: invokeinterface 359 2 0
    //   704: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   707: pop
    //   708: aload 5
    //   710: astore 4
    //   712: aload_3
    //   713: ldc_w 483
    //   716: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   719: pop
    //   720: aload 5
    //   722: astore 4
    //   724: aload_3
    //   725: aload 5
    //   727: aload 5
    //   729: ldc 48
    //   731: invokeinterface 472 2 0
    //   736: invokeinterface 359 2 0
    //   741: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   744: pop
    //   745: aload 5
    //   747: astore 4
    //   749: aload_3
    //   750: ldc_w 483
    //   753: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   756: pop
    //   757: aload 5
    //   759: astore 4
    //   761: aload_3
    //   762: aload 5
    //   764: aload 5
    //   766: ldc 93
    //   768: invokeinterface 472 2 0
    //   773: invokeinterface 359 2 0
    //   778: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   781: pop
    //   782: aload 5
    //   784: astore 4
    //   786: aload_3
    //   787: ldc_w 485
    //   790: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   793: pop
    //   794: aload 5
    //   796: astore 4
    //   798: aload_3
    //   799: aload 5
    //   801: aload 5
    //   803: ldc 66
    //   805: invokeinterface 472 2 0
    //   810: invokeinterface 375 2 0
    //   815: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   818: pop
    //   819: aload 5
    //   821: astore 4
    //   823: aload_3
    //   824: ldc_w 487
    //   827: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   830: pop
    //   831: aload 5
    //   833: astore 4
    //   835: aload_3
    //   836: aload 5
    //   838: aload 5
    //   840: ldc 57
    //   842: invokeinterface 472 2 0
    //   847: invokeinterface 375 2 0
    //   852: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   855: pop
    //   856: aload 5
    //   858: astore 4
    //   860: aload_3
    //   861: ldc_w 487
    //   864: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   867: pop
    //   868: aload 5
    //   870: astore 4
    //   872: aload_3
    //   873: aload 5
    //   875: aload 5
    //   877: ldc 60
    //   879: invokeinterface 472 2 0
    //   884: invokeinterface 375 2 0
    //   889: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   892: pop
    //   893: aload 5
    //   895: astore 4
    //   897: aload_3
    //   898: ldc_w 487
    //   901: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   904: pop
    //   905: aload 5
    //   907: astore 4
    //   909: aload_3
    //   910: aload 5
    //   912: aload 5
    //   914: ldc 63
    //   916: invokeinterface 472 2 0
    //   921: invokeinterface 375 2 0
    //   926: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   929: pop
    //   930: aload 5
    //   932: astore 4
    //   934: aload_3
    //   935: ldc_w 487
    //   938: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   941: pop
    //   942: aload 5
    //   944: astore 4
    //   946: aload_3
    //   947: aload 5
    //   949: aload 5
    //   951: ldc 72
    //   953: invokeinterface 472 2 0
    //   958: invokeinterface 375 2 0
    //   963: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   966: pop
    //   967: aload 5
    //   969: astore 4
    //   971: aload_3
    //   972: ldc_w 481
    //   975: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   978: pop
    //   979: aload 5
    //   981: astore 4
    //   983: aload_3
    //   984: aload 5
    //   986: aload 5
    //   988: ldc 30
    //   990: invokeinterface 472 2 0
    //   995: invokeinterface 359 2 0
    //   1000: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1003: pop
    //   1004: aload 5
    //   1006: astore 4
    //   1008: aload_3
    //   1009: ldc_w 483
    //   1012: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1015: pop
    //   1016: aload 5
    //   1018: astore 4
    //   1020: aload_3
    //   1021: aload 5
    //   1023: aload 5
    //   1025: ldc 33
    //   1027: invokeinterface 472 2 0
    //   1032: invokeinterface 359 2 0
    //   1037: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1040: pop
    //   1041: aload 5
    //   1043: astore 4
    //   1045: aload_3
    //   1046: ldc_w 483
    //   1049: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1052: pop
    //   1053: aload 5
    //   1055: astore 4
    //   1057: aload_3
    //   1058: aload 5
    //   1060: aload 5
    //   1062: ldc 87
    //   1064: invokeinterface 472 2 0
    //   1069: invokeinterface 359 2 0
    //   1074: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1077: pop
    //   1078: aload 5
    //   1080: astore 4
    //   1082: aload_3
    //   1083: ldc_w 483
    //   1086: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1089: pop
    //   1090: aload 5
    //   1092: astore 4
    //   1094: aload_3
    //   1095: aload 5
    //   1097: aload 5
    //   1099: ldc 36
    //   1101: invokeinterface 472 2 0
    //   1106: invokeinterface 359 2 0
    //   1111: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1114: pop
    //   1115: aload 5
    //   1117: astore 4
    //   1119: aload_3
    //   1120: ldc_w 483
    //   1123: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1126: pop
    //   1127: aload 5
    //   1129: astore 4
    //   1131: aload_3
    //   1132: aload 5
    //   1134: aload 5
    //   1136: ldc 39
    //   1138: invokeinterface 472 2 0
    //   1143: invokeinterface 359 2 0
    //   1148: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1151: pop
    //   1152: aload 5
    //   1154: astore 4
    //   1156: aload_3
    //   1157: ldc_w 485
    //   1160: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1163: pop
    //   1164: aload 5
    //   1166: astore 4
    //   1168: aload_3
    //   1169: aload 5
    //   1171: aload 5
    //   1173: ldc 24
    //   1175: invokeinterface 472 2 0
    //   1180: invokeinterface 375 2 0
    //   1185: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1188: pop
    //   1189: aload 5
    //   1191: astore 4
    //   1193: aload_3
    //   1194: ldc_w 487
    //   1197: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1200: pop
    //   1201: aload 5
    //   1203: astore 4
    //   1205: aload_3
    //   1206: aload 5
    //   1208: aload 5
    //   1210: ldc 27
    //   1212: invokeinterface 472 2 0
    //   1217: invokeinterface 375 2 0
    //   1222: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1225: pop
    //   1226: aload 5
    //   1228: astore 4
    //   1230: aload_3
    //   1231: ldc_w 487
    //   1234: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1237: pop
    //   1238: aload 5
    //   1240: astore 4
    //   1242: aload_3
    //   1243: iload_1
    //   1244: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1247: pop
    //   1248: aload 5
    //   1250: astore 4
    //   1252: aload_3
    //   1253: ldc_w 489
    //   1256: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1259: pop
    //   1260: aload 5
    //   1262: astore 4
    //   1264: aload_3
    //   1265: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1268: astore_3
    //   1269: aload 5
    //   1271: astore 4
    //   1273: aload_0
    //   1274: getfield 142	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1277: astore 7
    //   1279: aload 5
    //   1281: astore 4
    //   1283: new 215	java/lang/StringBuilder
    //   1286: dup
    //   1287: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   1290: astore 8
    //   1292: aload 5
    //   1294: astore 4
    //   1296: aload 8
    //   1298: ldc_w 491
    //   1301: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1304: pop
    //   1305: aload 5
    //   1307: astore 4
    //   1309: aload 8
    //   1311: aload_3
    //   1312: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1315: pop
    //   1316: aload 5
    //   1318: astore 4
    //   1320: aload 7
    //   1322: aload 8
    //   1324: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1327: invokestatic 496	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   1330: aload 5
    //   1332: astore 4
    //   1334: aload_0
    //   1335: getfield 144	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper	Lcom/tencent/common/plugin/DBHelper;
    //   1338: aload_3
    //   1339: invokevirtual 499	com/tencent/common/plugin/DBHelper:execSQL	(Ljava/lang/String;)V
    //   1342: goto -1222 -> 120
    //   1345: astore 7
    //   1347: aload 5
    //   1349: astore 4
    //   1351: aload 5
    //   1353: astore_3
    //   1354: aload 7
    //   1356: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   1359: goto -1239 -> 120
    //   1362: aload 5
    //   1364: astore 4
    //   1366: aload 5
    //   1368: astore_3
    //   1369: aload 6
    //   1371: ldc_w 501
    //   1374: invokevirtual 499	com/tencent/common/plugin/DBHelper:execSQL	(Ljava/lang/String;)V
    //   1377: aload 5
    //   1379: astore 4
    //   1381: aload 5
    //   1383: astore_3
    //   1384: aload_0
    //   1385: getfield 144	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper	Lcom/tencent/common/plugin/DBHelper;
    //   1388: invokevirtual 263	com/tencent/common/plugin/DBHelper:endTransaction	()V
    //   1391: aload 5
    //   1393: ifnull +43 -> 1436
    //   1396: aload 5
    //   1398: astore_3
    //   1399: goto +31 -> 1430
    //   1402: astore_3
    //   1403: goto +34 -> 1437
    //   1406: astore 5
    //   1408: aload_3
    //   1409: astore 4
    //   1411: aload_0
    //   1412: getfield 144	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper	Lcom/tencent/common/plugin/DBHelper;
    //   1415: invokevirtual 270	com/tencent/common/plugin/DBHelper:endTransactionOnly	()V
    //   1418: aload_3
    //   1419: astore 4
    //   1421: aload 5
    //   1423: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   1426: aload_3
    //   1427: ifnull +9 -> 1436
    //   1430: aload_3
    //   1431: invokeinterface 462 1 0
    //   1436: return
    //   1437: aload 4
    //   1439: ifnull +10 -> 1449
    //   1442: aload 4
    //   1444: invokeinterface 462 1 0
    //   1449: aload_3
    //   1450: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1451	0	this	QBPluginDBHelper
    //   161	1083	1	i	int
    //   25	111	2	bool	boolean
    //   31	2	3	localException1	Exception
    //   37	1362	3	localObject1	Object
    //   1402	48	3	localObject2	Object
    //   39	1404	4	localObject3	Object
    //   52	1345	5	localCursor	Cursor
    //   1406	16	5	localException2	Exception
    //   16	1354	6	localDBHelper	DBHelper
    //   1277	44	7	str	String
    //   1345	10	7	localException3	Exception
    //   1290	33	8	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   18	26	31	java/lang/Exception
    //   143	160	1345	java/lang/Exception
    //   166	176	1345	java/lang/Exception
    //   182	190	1345	java/lang/Exception
    //   194	202	1345	java/lang/Exception
    //   206	227	1345	java/lang/Exception
    //   231	239	1345	java/lang/Exception
    //   243	264	1345	java/lang/Exception
    //   268	276	1345	java/lang/Exception
    //   280	301	1345	java/lang/Exception
    //   305	313	1345	java/lang/Exception
    //   317	338	1345	java/lang/Exception
    //   342	350	1345	java/lang/Exception
    //   354	375	1345	java/lang/Exception
    //   379	387	1345	java/lang/Exception
    //   391	412	1345	java/lang/Exception
    //   416	424	1345	java/lang/Exception
    //   428	449	1345	java/lang/Exception
    //   453	461	1345	java/lang/Exception
    //   465	486	1345	java/lang/Exception
    //   490	498	1345	java/lang/Exception
    //   502	523	1345	java/lang/Exception
    //   527	535	1345	java/lang/Exception
    //   539	560	1345	java/lang/Exception
    //   564	572	1345	java/lang/Exception
    //   576	597	1345	java/lang/Exception
    //   601	609	1345	java/lang/Exception
    //   613	634	1345	java/lang/Exception
    //   638	646	1345	java/lang/Exception
    //   650	671	1345	java/lang/Exception
    //   675	683	1345	java/lang/Exception
    //   687	708	1345	java/lang/Exception
    //   712	720	1345	java/lang/Exception
    //   724	745	1345	java/lang/Exception
    //   749	757	1345	java/lang/Exception
    //   761	782	1345	java/lang/Exception
    //   786	794	1345	java/lang/Exception
    //   798	819	1345	java/lang/Exception
    //   823	831	1345	java/lang/Exception
    //   835	856	1345	java/lang/Exception
    //   860	868	1345	java/lang/Exception
    //   872	893	1345	java/lang/Exception
    //   897	905	1345	java/lang/Exception
    //   909	930	1345	java/lang/Exception
    //   934	942	1345	java/lang/Exception
    //   946	967	1345	java/lang/Exception
    //   971	979	1345	java/lang/Exception
    //   983	1004	1345	java/lang/Exception
    //   1008	1016	1345	java/lang/Exception
    //   1020	1041	1345	java/lang/Exception
    //   1045	1053	1345	java/lang/Exception
    //   1057	1078	1345	java/lang/Exception
    //   1082	1090	1345	java/lang/Exception
    //   1094	1115	1345	java/lang/Exception
    //   1119	1127	1345	java/lang/Exception
    //   1131	1152	1345	java/lang/Exception
    //   1156	1164	1345	java/lang/Exception
    //   1168	1189	1345	java/lang/Exception
    //   1193	1201	1345	java/lang/Exception
    //   1205	1226	1345	java/lang/Exception
    //   1230	1238	1345	java/lang/Exception
    //   1242	1248	1345	java/lang/Exception
    //   1252	1260	1345	java/lang/Exception
    //   1264	1269	1345	java/lang/Exception
    //   1273	1279	1345	java/lang/Exception
    //   1283	1292	1345	java/lang/Exception
    //   1296	1305	1345	java/lang/Exception
    //   1309	1316	1345	java/lang/Exception
    //   1320	1330	1345	java/lang/Exception
    //   1334	1342	1345	java/lang/Exception
    //   41	54	1402	finally
    //   79	89	1402	finally
    //   113	120	1402	finally
    //   127	135	1402	finally
    //   143	160	1402	finally
    //   166	176	1402	finally
    //   182	190	1402	finally
    //   194	202	1402	finally
    //   206	227	1402	finally
    //   231	239	1402	finally
    //   243	264	1402	finally
    //   268	276	1402	finally
    //   280	301	1402	finally
    //   305	313	1402	finally
    //   317	338	1402	finally
    //   342	350	1402	finally
    //   354	375	1402	finally
    //   379	387	1402	finally
    //   391	412	1402	finally
    //   416	424	1402	finally
    //   428	449	1402	finally
    //   453	461	1402	finally
    //   465	486	1402	finally
    //   490	498	1402	finally
    //   502	523	1402	finally
    //   527	535	1402	finally
    //   539	560	1402	finally
    //   564	572	1402	finally
    //   576	597	1402	finally
    //   601	609	1402	finally
    //   613	634	1402	finally
    //   638	646	1402	finally
    //   650	671	1402	finally
    //   675	683	1402	finally
    //   687	708	1402	finally
    //   712	720	1402	finally
    //   724	745	1402	finally
    //   749	757	1402	finally
    //   761	782	1402	finally
    //   786	794	1402	finally
    //   798	819	1402	finally
    //   823	831	1402	finally
    //   835	856	1402	finally
    //   860	868	1402	finally
    //   872	893	1402	finally
    //   897	905	1402	finally
    //   909	930	1402	finally
    //   934	942	1402	finally
    //   946	967	1402	finally
    //   971	979	1402	finally
    //   983	1004	1402	finally
    //   1008	1016	1402	finally
    //   1020	1041	1402	finally
    //   1045	1053	1402	finally
    //   1057	1078	1402	finally
    //   1082	1090	1402	finally
    //   1094	1115	1402	finally
    //   1119	1127	1402	finally
    //   1131	1152	1402	finally
    //   1156	1164	1402	finally
    //   1168	1189	1402	finally
    //   1193	1201	1402	finally
    //   1205	1226	1402	finally
    //   1230	1238	1402	finally
    //   1242	1248	1402	finally
    //   1252	1260	1402	finally
    //   1264	1269	1402	finally
    //   1273	1279	1402	finally
    //   1283	1292	1402	finally
    //   1296	1305	1402	finally
    //   1309	1316	1402	finally
    //   1320	1330	1402	finally
    //   1334	1342	1402	finally
    //   1354	1359	1402	finally
    //   1369	1377	1402	finally
    //   1384	1391	1402	finally
    //   1411	1418	1402	finally
    //   1421	1426	1402	finally
    //   41	54	1406	java/lang/Exception
    //   79	89	1406	java/lang/Exception
    //   113	120	1406	java/lang/Exception
    //   127	135	1406	java/lang/Exception
    //   1354	1359	1406	java/lang/Exception
    //   1369	1377	1406	java/lang/Exception
    //   1384	1391	1406	java/lang/Exception
  }
  
  static void a(int paramInt, Object... paramVarArgs)
  {
    int i = paramInt + 360;
    if (i <= 399)
    {
      if ((paramVarArgs != null) && (paramVarArgs.length > 0))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(i);
        localStringBuilder.append("#");
        String str = "";
        i = paramVarArgs.length;
        paramInt = 0;
        while (paramInt < i)
        {
          Object localObject = paramVarArgs[paramInt];
          localStringBuilder.append(str);
          localStringBuilder.append(localObject);
          str = "#";
          paramInt += 1;
        }
        PluginStatBehavior.addLogPath("PluginDB", 7, localStringBuilder.toString());
        return;
      }
      PluginStatBehavior.addLogPath("PluginDB", 7, i);
      return;
    }
    paramVarArgs = new StringBuilder();
    paramVarArgs.append("code is to large: ");
    paramVarArgs.append(paramInt);
    throw new IllegalArgumentException(paramVarArgs.toString());
  }
  
  /* Error */
  private void a(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 153	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   4: astore 5
    //   6: aload 5
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield 151	com/tencent/common/plugin/QBPluginDBHelper:mPluginDatasInited	Z
    //   13: ifeq +7 -> 20
    //   16: aload 5
    //   18: monitorexit
    //   19: return
    //   20: bipush 7
    //   22: iconst_2
    //   23: anewarray 4	java/lang/Object
    //   26: dup
    //   27: iconst_0
    //   28: iconst_2
    //   29: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   32: aastore
    //   33: dup
    //   34: iconst_1
    //   35: aload_0
    //   36: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   39: invokeinterface 527 1 0
    //   44: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   47: aastore
    //   48: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   51: aconst_null
    //   52: astore_3
    //   53: aconst_null
    //   54: astore_1
    //   55: aload_0
    //   56: getfield 144	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper	Lcom/tencent/common/plugin/DBHelper;
    //   59: ldc 117
    //   61: aconst_null
    //   62: ldc_w 531
    //   65: invokevirtual 459	com/tencent/common/plugin/DBHelper:query	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   68: astore 4
    //   70: aload 4
    //   72: ifnonnull +19 -> 91
    //   75: aload 4
    //   77: ifnull +10 -> 87
    //   80: aload 4
    //   82: invokeinterface 462 1 0
    //   87: aload 5
    //   89: monitorexit
    //   90: return
    //   91: aload 4
    //   93: astore_1
    //   94: aload 4
    //   96: astore_3
    //   97: aload 4
    //   99: invokeinterface 469 1 0
    //   104: ifeq +315 -> 419
    //   107: aload 4
    //   109: astore_1
    //   110: aload 4
    //   112: astore_3
    //   113: aload_0
    //   114: aload 4
    //   116: invokespecial 533	com/tencent/common/plugin/QBPluginDBHelper:a	(Landroid/database/Cursor;)Lcom/tencent/common/plugin/QBPluginItemInfo;
    //   119: astore 6
    //   121: aload 6
    //   123: ifnonnull +437 -> 560
    //   126: goto -35 -> 91
    //   129: aload 4
    //   131: astore_1
    //   132: aload 4
    //   134: astore_3
    //   135: iload_2
    //   136: aload_0
    //   137: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   140: invokeinterface 527 1 0
    //   145: if_icmpge +427 -> 572
    //   148: aload 4
    //   150: astore_1
    //   151: aload 4
    //   153: astore_3
    //   154: aload_0
    //   155: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   158: iload_2
    //   159: invokeinterface 534 2 0
    //   164: checkcast 348	com/tencent/common/plugin/QBPluginItemInfo
    //   167: getfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   170: aload 6
    //   172: getfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   175: invokevirtual 477	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   178: ifeq +387 -> 565
    //   181: aload 4
    //   183: astore_1
    //   184: aload 4
    //   186: astore_3
    //   187: aload_0
    //   188: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   191: iload_2
    //   192: invokeinterface 534 2 0
    //   197: checkcast 348	com/tencent/common/plugin/QBPluginItemInfo
    //   200: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   203: aload 6
    //   205: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   208: if_icmpne +357 -> 565
    //   211: aload 4
    //   213: astore_1
    //   214: aload 4
    //   216: astore_3
    //   217: aload_0
    //   218: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   221: iload_2
    //   222: invokeinterface 537 2 0
    //   227: pop
    //   228: aload 4
    //   230: astore_1
    //   231: aload 4
    //   233: astore_3
    //   234: aload_0
    //   235: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   238: iload_2
    //   239: aload 6
    //   241: invokeinterface 541 3 0
    //   246: iconst_1
    //   247: istore_2
    //   248: goto +3 -> 251
    //   251: iload_2
    //   252: ifne -161 -> 91
    //   255: aload 4
    //   257: astore_1
    //   258: aload 4
    //   260: astore_3
    //   261: aload_0
    //   262: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   265: aload 6
    //   267: invokeinterface 544 2 0
    //   272: pop
    //   273: aload 4
    //   275: astore_1
    //   276: aload 4
    //   278: astore_3
    //   279: aload_0
    //   280: getfield 142	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   283: astore 7
    //   285: aload 4
    //   287: astore_1
    //   288: aload 4
    //   290: astore_3
    //   291: new 215	java/lang/StringBuilder
    //   294: dup
    //   295: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   298: astore 8
    //   300: aload 4
    //   302: astore_1
    //   303: aload 4
    //   305: astore_3
    //   306: aload 8
    //   308: ldc_w 546
    //   311: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   314: pop
    //   315: aload 4
    //   317: astore_1
    //   318: aload 4
    //   320: astore_3
    //   321: aload 8
    //   323: aload_0
    //   324: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   327: invokeinterface 527 1 0
    //   332: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   335: pop
    //   336: aload 4
    //   338: astore_1
    //   339: aload 4
    //   341: astore_3
    //   342: aload 8
    //   344: ldc_w 548
    //   347: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   350: pop
    //   351: aload 4
    //   353: astore_1
    //   354: aload 4
    //   356: astore_3
    //   357: aload 8
    //   359: aload 6
    //   361: getfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   364: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   367: pop
    //   368: aload 4
    //   370: astore_1
    //   371: aload 4
    //   373: astore_3
    //   374: aload 8
    //   376: ldc_w 550
    //   379: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   382: pop
    //   383: aload 4
    //   385: astore_1
    //   386: aload 4
    //   388: astore_3
    //   389: aload 8
    //   391: aload 6
    //   393: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   396: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   399: pop
    //   400: aload 4
    //   402: astore_1
    //   403: aload 4
    //   405: astore_3
    //   406: aload 7
    //   408: aload 8
    //   410: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   413: invokestatic 496	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   416: goto -325 -> 91
    //   419: aload 4
    //   421: ifnull +10 -> 431
    //   424: aload 4
    //   426: invokeinterface 462 1 0
    //   431: aload_0
    //   432: iconst_1
    //   433: putfield 151	com/tencent/common/plugin/QBPluginDBHelper:mPluginDatasInited	Z
    //   436: aload 5
    //   438: monitorexit
    //   439: bipush 7
    //   441: iconst_2
    //   442: anewarray 4	java/lang/Object
    //   445: dup
    //   446: iconst_0
    //   447: iconst_1
    //   448: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   451: aastore
    //   452: dup
    //   453: iconst_1
    //   454: aload_0
    //   455: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   458: invokeinterface 527 1 0
    //   463: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   466: aastore
    //   467: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   470: aload_0
    //   471: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   474: invokeinterface 527 1 0
    //   479: ifne +13 -> 492
    //   482: invokestatic 555	com/tencent/common/utils/TbsMode:TBSISQB	()Z
    //   485: ifeq +7 -> 492
    //   488: aload_0
    //   489: invokespecial 557	com/tencent/common/plugin/QBPluginDBHelper:b	()V
    //   492: return
    //   493: astore_3
    //   494: goto +48 -> 542
    //   497: astore 4
    //   499: aload_3
    //   500: astore_1
    //   501: bipush 7
    //   503: iconst_2
    //   504: anewarray 4	java/lang/Object
    //   507: dup
    //   508: iconst_0
    //   509: iconst_3
    //   510: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   513: aastore
    //   514: dup
    //   515: iconst_1
    //   516: aload 4
    //   518: invokevirtual 561	java/lang/Object:getClass	()Ljava/lang/Class;
    //   521: invokevirtual 566	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   524: aastore
    //   525: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   528: aload_3
    //   529: ifnull +9 -> 538
    //   532: aload_3
    //   533: invokeinterface 462 1 0
    //   538: aload 5
    //   540: monitorexit
    //   541: return
    //   542: aload_1
    //   543: ifnull +9 -> 552
    //   546: aload_1
    //   547: invokeinterface 462 1 0
    //   552: aload_3
    //   553: athrow
    //   554: astore_1
    //   555: aload 5
    //   557: monitorexit
    //   558: aload_1
    //   559: athrow
    //   560: iconst_0
    //   561: istore_2
    //   562: goto -433 -> 129
    //   565: iload_2
    //   566: iconst_1
    //   567: iadd
    //   568: istore_2
    //   569: goto -440 -> 129
    //   572: iconst_0
    //   573: istore_2
    //   574: goto -323 -> 251
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	577	0	this	QBPluginDBHelper
    //   0	577	1	paramContext	Context
    //   135	439	2	i	int
    //   52	354	3	localObject1	Object
    //   493	60	3	localObject2	Object
    //   68	357	4	localCursor	Cursor
    //   497	20	4	localException	Exception
    //   4	552	5	localObject3	Object
    //   119	273	6	localQBPluginItemInfo	QBPluginItemInfo
    //   283	124	7	str	String
    //   298	111	8	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   55	70	493	finally
    //   97	107	493	finally
    //   113	121	493	finally
    //   135	148	493	finally
    //   154	181	493	finally
    //   187	211	493	finally
    //   217	228	493	finally
    //   234	246	493	finally
    //   261	273	493	finally
    //   279	285	493	finally
    //   291	300	493	finally
    //   306	315	493	finally
    //   321	336	493	finally
    //   342	351	493	finally
    //   357	368	493	finally
    //   374	383	493	finally
    //   389	400	493	finally
    //   406	416	493	finally
    //   501	528	493	finally
    //   55	70	497	java/lang/Exception
    //   97	107	497	java/lang/Exception
    //   113	121	497	java/lang/Exception
    //   135	148	497	java/lang/Exception
    //   154	181	497	java/lang/Exception
    //   187	211	497	java/lang/Exception
    //   217	228	497	java/lang/Exception
    //   234	246	497	java/lang/Exception
    //   261	273	497	java/lang/Exception
    //   279	285	497	java/lang/Exception
    //   291	300	497	java/lang/Exception
    //   306	315	497	java/lang/Exception
    //   321	336	497	java/lang/Exception
    //   342	351	497	java/lang/Exception
    //   357	368	497	java/lang/Exception
    //   374	383	497	java/lang/Exception
    //   389	400	497	java/lang/Exception
    //   406	416	497	java/lang/Exception
    //   9	19	554	finally
    //   20	51	554	finally
    //   80	87	554	finally
    //   87	90	554	finally
    //   424	431	554	finally
    //   431	439	554	finally
    //   532	538	554	finally
    //   538	541	554	finally
    //   546	552	554	finally
    //   552	554	554	finally
    //   555	558	554	finally
  }
  
  private void a(Context paramContext, DBHelper paramDBHelper)
  {
    paramContext = new StringBuffer();
    try
    {
      paramDBHelper.beginTransaction();
      if (!paramDBHelper.isColumnExist("plugins", "needUpdate"))
      {
        paramContext.delete(0, paramContext.length());
        paramContext.append("ALTER TABLE ");
        paramContext.append("plugins");
        paramContext.append(" ADD ");
        paramContext.append("needUpdate");
        paramContext.append("  INTEGER DEFAULT 0");
        paramDBHelper.execSQL(paramContext.toString());
        if (!paramDBHelper.isColumnExist("plugins", "location"))
        {
          paramContext.delete(0, paramContext.length());
          paramContext.append("ALTER TABLE ");
          paramContext.append("plugins");
          paramContext.append(" ADD ");
          paramContext.append("location");
          paramContext.append(" INTEGER DEFAULT 0");
          paramDBHelper.execSQL(paramContext.toString());
        }
        if (!paramDBHelper.isColumnExist("plugins", "signature"))
        {
          paramContext.delete(0, paramContext.length());
          paramContext.append("ALTER TABLE ");
          paramContext.append("plugins");
          paramContext.append(" ADD ");
          paramContext.append("signature");
          paramContext.append(" TEXT DEFAULT NULL");
          paramDBHelper.execSQL(paramContext.toString());
        }
        if (!paramDBHelper.isColumnExist("plugins", "extString3"))
        {
          paramContext.delete(0, paramContext.length());
          paramContext.append("ALTER TABLE ");
          paramContext.append("plugins");
          paramContext.append(" ADD ");
          paramContext.append("extString3");
          paramContext.append(" TEXT DEFAULT NULL");
          paramDBHelper.execSQL(paramContext.toString());
        }
        if (!paramDBHelper.isColumnExist("plugins", "extString4"))
        {
          paramContext.delete(0, paramContext.length());
          paramContext.append("ALTER TABLE ");
          paramContext.append("plugins");
          paramContext.append(" ADD ");
          paramContext.append("extString4");
          paramContext.append(" TEXT DEFAULT NULL");
          paramDBHelper.execSQL(paramContext.toString());
        }
        if (!paramDBHelper.isColumnExist("plugins", "isZipFileUpdate"))
        {
          paramContext.delete(0, paramContext.length());
          paramContext.append("ALTER TABLE ");
          paramContext.append("plugins");
          paramContext.append(" ADD ");
          paramContext.append("isZipFileUpdate");
          paramContext.append(" INTEGER DEFAULT 0");
          paramDBHelper.execSQL(paramContext.toString());
        }
        if (!paramDBHelper.isColumnExist("plugins", "downPath"))
        {
          paramDBHelper.beginTransaction();
          paramContext.delete(0, paramContext.length());
          paramContext.append("ALTER TABLE ");
          paramContext.append("plugins");
          paramContext.append(" ADD ");
          paramContext.append("downPath");
          paramContext.append(" TEXT DEFAULT NULL");
          paramDBHelper.execSQL(paramContext.toString());
          paramContext.delete(0, paramContext.length());
          paramContext.append("ALTER TABLE ");
          paramContext.append("plugins");
          paramContext.append(" ADD ");
          paramContext.append("installPath");
          paramContext.append(" TEXT DEFAULT NULL");
          paramDBHelper.execSQL(paramContext.toString());
          paramContext.delete(0, paramContext.length());
          paramContext.append("ALTER TABLE ");
          paramContext.append("plugins");
          paramContext.append(" ADD ");
          paramContext.append("unzipPath");
          paramContext.append(" TEXT DEFAULT NULL");
          paramDBHelper.execSQL(paramContext.toString());
          paramDBHelper.endTransaction();
        }
        if (!paramDBHelper.isColumnExist("plugins", "installVersion"))
        {
          paramContext.delete(0, paramContext.length());
          paramContext.append("ALTER TABLE ");
          paramContext.append("plugins");
          paramContext.append(" ADD ");
          paramContext.append("installVersion");
          paramContext.append(" TEXT DEFAULT NULL");
          paramDBHelper.execSQL(paramContext.toString());
        }
        if (!paramDBHelper.isColumnExist("plugins", "infofrom"))
        {
          paramContext.delete(0, paramContext.length());
          if (!TbsMode.TBSISQB())
          {
            paramContext.append("ALTER TABLE ");
            paramContext.append("plugins");
            paramContext.append(" ADD ");
            paramContext.append("infofrom");
            paramContext.append(" INTEGER DEFAULT 2");
          }
          else
          {
            paramContext.append("ALTER TABLE ");
            paramContext.append("plugins");
            paramContext.append(" ADD ");
            paramContext.append("infofrom");
            paramContext.append(" INTEGER DEFAULT 1");
          }
          paramDBHelper.execSQL(paramContext.toString());
        }
        if (!paramDBHelper.isColumnExist("plugins", "antihijackurl"))
        {
          paramContext.delete(0, paramContext.length());
          paramContext.append("ALTER TABLE ");
          paramContext.append("plugins");
          paramContext.append(" ADD ");
          paramContext.append("antihijackurl");
          paramContext.append(" TEXT DEFAULT NULL");
          paramDBHelper.execSQL(paramContext.toString());
        }
        paramDBHelper.endTransaction();
        return;
      }
      paramDBHelper.endTransactionOnly();
      return;
    }
    catch (Exception paramContext)
    {
      paramDBHelper.endTransactionOnly();
      paramContext.printStackTrace();
    }
  }
  
  private void a(PluginItem paramPluginItem, int paramInt)
  {
    Object localObject2 = this.jdField_a_of_type_JavaUtilList.iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject1 = (QBPluginItemInfo)((Iterator)localObject2).next();
      if ((localObject1 != null) && (((QBPluginItemInfo)localObject1).mPackageName.equalsIgnoreCase(paramPluginItem.sPkgName)) && (((QBPluginItemInfo)localObject1).mInfoFrom == paramInt)) {
        break label68;
      }
    }
    Object localObject1 = null;
    label68:
    localObject2 = localObject1;
    if (localObject1 == null)
    {
      localObject2 = new QBPluginItemInfo();
      ((QBPluginItemInfo)localObject2).mPackageName = paramPluginItem.sPkgName;
      this.jdField_a_of_type_JavaUtilList.add(localObject2);
      localObject1 = this.jdField_a_of_type_JavaLangString;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("mPluginListMem.size=");
      localStringBuilder.append(this.jdField_a_of_type_JavaUtilList.size());
      localStringBuilder.append("insertOrUpdate=");
      localStringBuilder.append(paramPluginItem.sPkgName);
      localStringBuilder.append(",infoFrom=");
      localStringBuilder.append(paramInt);
      FLogger.i((String)localObject1, localStringBuilder.toString());
    }
    int i = StringUtils.parseInt(((QBPluginItemInfo)localObject2).mVersion, -1);
    int j = paramPluginItem.iVersion;
    ((QBPluginItemInfo)localObject2).mTitle = paramPluginItem.sTitle;
    ((QBPluginItemInfo)localObject2).mUrl = paramPluginItem.sUrl;
    if ((paramPluginItem.vBackURL != null) && (paramPluginItem.vBackURL.size() > 0)) {
      ((QBPluginItemInfo)localObject2).mAntiHijackUrl = ((String)paramPluginItem.vBackURL.get(0));
    } else {
      ((QBPluginItemInfo)localObject2).mAntiHijackUrl = "";
    }
    ((QBPluginItemInfo)localObject2).mIconUrl = paramPluginItem.sIconUrl;
    ((QBPluginItemInfo)localObject2).mPluginType = paramPluginItem.iPluginType;
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(paramPluginItem.iVersion);
    ((StringBuilder)localObject1).append("");
    ((QBPluginItemInfo)localObject2).mVersion = ((StringBuilder)localObject1).toString();
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(paramPluginItem.iSize);
    ((StringBuilder)localObject1).append("");
    ((QBPluginItemInfo)localObject2).mPackageSize = ((StringBuilder)localObject1).toString();
    ((QBPluginItemInfo)localObject2).mUpdateType = paramPluginItem.iUpdateType;
    ((QBPluginItemInfo)localObject2).mOrder = paramPluginItem.iOrder;
    ((QBPluginItemInfo)localObject2).mDetailSumary = paramPluginItem.sTips;
    ((QBPluginItemInfo)localObject2).mExt = paramPluginItem.sExt;
    ((QBPluginItemInfo)localObject2).mSignature = paramPluginItem.sSignature;
    ((QBPluginItemInfo)localObject2).mLocation = paramPluginItem.iLocation;
    if (TextUtils.isEmpty(paramPluginItem.s256MD5)) {
      localObject1 = "";
    } else {
      localObject1 = paramPluginItem.s256MD5;
    }
    ((QBPluginItemInfo)localObject2).mMd5 = ((String)localObject1);
    ((QBPluginItemInfo)localObject2).mInfoFrom = paramInt;
    if ((i != -1) && (j > i))
    {
      updatePluginNeesUpdateFlag(paramPluginItem.sPkgName, 1, paramInt);
      localObject1 = QBPluginSystem.TAG;
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("插件列表更新，发现插件=");
      ((StringBuilder)localObject2).append(paramPluginItem.sPkgName);
      ((StringBuilder)localObject2).append(" 需要升级，新版本：");
      ((StringBuilder)localObject2).append(j);
      ((StringBuilder)localObject2).append(",老版本:");
      ((StringBuilder)localObject2).append(i);
      FLogger.i((String)localObject1, ((StringBuilder)localObject2).toString());
    }
  }
  
  /* Error */
  private void a(ArrayList<QBPluginItemInfo> paramArrayList)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +531 -> 532
    //   4: aload_1
    //   5: invokevirtual 287	java/util/ArrayList:size	()I
    //   8: ifgt +4 -> 12
    //   11: return
    //   12: aload_0
    //   13: getfield 144	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper	Lcom/tencent/common/plugin/DBHelper;
    //   16: astore 6
    //   18: aconst_null
    //   19: astore 5
    //   21: aload 6
    //   23: invokevirtual 628	com/tencent/common/plugin/DBHelper:getSQLiteDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   26: astore 6
    //   28: aload 6
    //   30: astore 5
    //   32: goto +10 -> 42
    //   35: astore 6
    //   37: aload 6
    //   39: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   42: aload 5
    //   44: ifnonnull +4 -> 48
    //   47: return
    //   48: aload_1
    //   49: invokevirtual 287	java/util/ArrayList:size	()I
    //   52: istore 4
    //   54: aload 5
    //   56: invokevirtual 631	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   59: aload_0
    //   60: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   63: invokeinterface 527 1 0
    //   68: istore_2
    //   69: iload_2
    //   70: ifeq +15 -> 85
    //   73: aload 5
    //   75: invokevirtual 632	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   78: return
    //   79: astore_1
    //   80: aload_1
    //   81: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   84: return
    //   85: iconst_0
    //   86: istore_2
    //   87: iload_2
    //   88: iload 4
    //   90: if_icmpge +393 -> 483
    //   93: aload_1
    //   94: iload_2
    //   95: invokevirtual 291	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   98: checkcast 348	com/tencent/common/plugin/QBPluginItemInfo
    //   101: astore 6
    //   103: aload 6
    //   105: ifnonnull +6 -> 111
    //   108: goto +430 -> 538
    //   111: new 240	android/content/ContentValues
    //   114: dup
    //   115: invokespecial 241	android/content/ContentValues:<init>	()V
    //   118: astore 7
    //   120: aload 7
    //   122: ldc 18
    //   124: aload 6
    //   126: getfield 378	com/tencent/common/plugin/QBPluginItemInfo:mPluginType	I
    //   129: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   132: invokevirtual 311	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   135: aload 7
    //   137: ldc 90
    //   139: aload 6
    //   141: getfield 362	com/tencent/common/plugin/QBPluginItemInfo:mTitle	Ljava/lang/String;
    //   144: invokevirtual 248	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   147: aload 7
    //   149: ldc 105
    //   151: aload 6
    //   153: getfield 365	com/tencent/common/plugin/QBPluginItemInfo:mUrl	Ljava/lang/String;
    //   156: invokevirtual 248	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   159: aload 7
    //   161: ldc 72
    //   163: aload 6
    //   165: getfield 393	com/tencent/common/plugin/QBPluginItemInfo:mOrder	I
    //   168: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   171: invokevirtual 311	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   174: aload 7
    //   176: ldc 75
    //   178: aload 6
    //   180: getfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   183: invokevirtual 248	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   186: aload 7
    //   188: ldc 78
    //   190: aload 6
    //   192: getfield 384	com/tencent/common/plugin/QBPluginItemInfo:mPackageSize	Ljava/lang/String;
    //   195: invokevirtual 248	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   198: aload 7
    //   200: ldc 42
    //   202: aload 6
    //   204: getfield 368	com/tencent/common/plugin/QBPluginItemInfo:mIconUrl	Ljava/lang/String;
    //   207: invokevirtual 248	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   210: aload 7
    //   212: ldc 69
    //   214: aload 6
    //   216: getfield 396	com/tencent/common/plugin/QBPluginItemInfo:mLocation	I
    //   219: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   222: invokevirtual 311	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   225: aload 7
    //   227: ldc 81
    //   229: aload 6
    //   231: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   234: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   237: invokevirtual 311	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   240: aload_0
    //   241: aload 6
    //   243: getfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   246: aload 6
    //   248: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   251: invokevirtual 636	com/tencent/common/plugin/QBPluginDBHelper:getPluginItemInfo	(Ljava/lang/String;I)Lcom/tencent/common/plugin/QBPluginItemInfo;
    //   254: ifnull +6 -> 260
    //   257: goto +281 -> 538
    //   260: aload_0
    //   261: getfield 144	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper	Lcom/tencent/common/plugin/DBHelper;
    //   264: ldc 117
    //   266: aload 7
    //   268: invokevirtual 640	com/tencent/common/plugin/DBHelper:insert	(Ljava/lang/String;Landroid/content/ContentValues;)I
    //   271: pop
    //   272: aload_0
    //   273: getfield 153	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   276: astore 7
    //   278: aload 7
    //   280: monitorenter
    //   281: aload_0
    //   282: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   285: invokeinterface 597 1 0
    //   290: astore 8
    //   292: aload 8
    //   294: invokeinterface 207 1 0
    //   299: ifeq +234 -> 533
    //   302: aload 8
    //   304: invokeinterface 211 1 0
    //   309: checkcast 348	com/tencent/common/plugin/QBPluginItemInfo
    //   312: astore 9
    //   314: aload 9
    //   316: ifnull -24 -> 292
    //   319: aload 9
    //   321: getfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   324: aload 6
    //   326: getfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   329: invokevirtual 477	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   332: ifeq -40 -> 292
    //   335: aload 9
    //   337: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   340: aload 6
    //   342: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   345: if_icmpne -53 -> 292
    //   348: iconst_1
    //   349: istore_3
    //   350: goto +3 -> 353
    //   353: iload_3
    //   354: iconst_1
    //   355: if_icmpeq +104 -> 459
    //   358: aload_0
    //   359: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   362: aload 6
    //   364: invokeinterface 544 2 0
    //   369: pop
    //   370: aload_0
    //   371: getfield 142	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   374: astore 8
    //   376: new 215	java/lang/StringBuilder
    //   379: dup
    //   380: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   383: astore 9
    //   385: aload 9
    //   387: ldc_w 546
    //   390: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   393: pop
    //   394: aload 9
    //   396: aload_0
    //   397: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   400: invokeinterface 527 1 0
    //   405: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   408: pop
    //   409: aload 9
    //   411: ldc_w 642
    //   414: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   417: pop
    //   418: aload 9
    //   420: aload 6
    //   422: getfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   425: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   428: pop
    //   429: aload 9
    //   431: ldc_w 550
    //   434: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   437: pop
    //   438: aload 9
    //   440: aload 6
    //   442: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   445: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   448: pop
    //   449: aload 8
    //   451: aload 9
    //   453: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   456: invokestatic 496	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   459: aload 7
    //   461: monitorexit
    //   462: goto +76 -> 538
    //   465: astore 6
    //   467: aload 7
    //   469: monitorexit
    //   470: aload 6
    //   472: athrow
    //   473: astore 6
    //   475: aload 6
    //   477: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   480: goto +58 -> 538
    //   483: aload 5
    //   485: invokevirtual 645	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   488: aload 5
    //   490: invokevirtual 632	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   493: return
    //   494: astore_1
    //   495: goto +20 -> 515
    //   498: astore_1
    //   499: aload_1
    //   500: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   503: aload 5
    //   505: invokevirtual 632	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   508: return
    //   509: astore_1
    //   510: aload_1
    //   511: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   514: return
    //   515: aload 5
    //   517: invokevirtual 632	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   520: goto +10 -> 530
    //   523: astore 5
    //   525: aload 5
    //   527: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   530: aload_1
    //   531: athrow
    //   532: return
    //   533: iconst_0
    //   534: istore_3
    //   535: goto -182 -> 353
    //   538: iload_2
    //   539: iconst_1
    //   540: iadd
    //   541: istore_2
    //   542: goto -455 -> 87
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	545	0	this	QBPluginDBHelper
    //   0	545	1	paramArrayList	ArrayList<QBPluginItemInfo>
    //   68	474	2	i	int
    //   349	186	3	j	int
    //   52	39	4	k	int
    //   19	497	5	localObject1	Object
    //   523	3	5	localException1	Exception
    //   16	13	6	localObject2	Object
    //   35	3	6	localException2	Exception
    //   101	340	6	localQBPluginItemInfo	QBPluginItemInfo
    //   465	6	6	localObject3	Object
    //   473	3	6	localException3	Exception
    //   290	160	8	localObject5	Object
    //   312	140	9	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   21	28	35	java/lang/Exception
    //   73	78	79	java/lang/Exception
    //   281	292	465	finally
    //   292	314	465	finally
    //   319	348	465	finally
    //   358	459	465	finally
    //   459	462	465	finally
    //   467	470	465	finally
    //   240	257	473	java/lang/Exception
    //   260	281	473	java/lang/Exception
    //   470	473	473	java/lang/Exception
    //   48	69	494	finally
    //   93	103	494	finally
    //   111	240	494	finally
    //   240	257	494	finally
    //   260	281	494	finally
    //   470	473	494	finally
    //   475	480	494	finally
    //   483	488	494	finally
    //   499	503	494	finally
    //   48	69	498	java/lang/Exception
    //   93	103	498	java/lang/Exception
    //   111	240	498	java/lang/Exception
    //   475	480	498	java/lang/Exception
    //   483	488	498	java/lang/Exception
    //   488	493	509	java/lang/Exception
    //   503	508	509	java/lang/Exception
    //   515	520	523	java/lang/Exception
  }
  
  private boolean a(Context paramContext)
  {
    try
    {
      if (TbsMode.TBSISQB()) {
        this.jdField_a_of_type_ComTencentCommonPluginDBHelper = new DBHelper(paramContext, "plugin_db", 1);
      } else {
        this.jdField_a_of_type_ComTencentCommonPluginDBHelper = new DBHelper(paramContext, "tes_db", 1);
      }
      if (!this.jdField_a_of_type_ComTencentCommonPluginDBHelper.exist("plugins"))
      {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append("CREATE TABLE ");
        localStringBuffer.append("plugins");
        localStringBuffer.append(" ( ");
        localStringBuffer.append("ID");
        localStringBuffer.append(" INTEGER PRIMARY KEY autoincrement, ");
        localStringBuffer.append("title");
        localStringBuffer.append(" TEXT, ");
        localStringBuffer.append("url");
        localStringBuffer.append(" TEXT, ");
        localStringBuffer.append("iconUrl");
        localStringBuffer.append(" TEXT, ");
        localStringBuffer.append("packageName");
        localStringBuffer.append(" TEXT, ");
        localStringBuffer.append("appSubtype");
        localStringBuffer.append(" INTEGER, ");
        localStringBuffer.append("version");
        localStringBuffer.append(" TEXT, ");
        localStringBuffer.append("packageSize");
        localStringBuffer.append(" INTEGER, ");
        localStringBuffer.append("updateStatus");
        localStringBuffer.append(" INTEGER DEFAULT -1, ");
        localStringBuffer.append("updateUrl");
        localStringBuffer.append(" TEXT, ");
        localStringBuffer.append("updatePackageSize");
        localStringBuffer.append(" INTEGER,");
        localStringBuffer.append("isInstall");
        localStringBuffer.append(" INTEGER DEFAULT 0,");
        localStringBuffer.append("isNotice");
        localStringBuffer.append(" INTEGER  DEFAULT 1,");
        localStringBuffer.append("isOpen");
        localStringBuffer.append(" INTEGER  DEFAULT 0,");
        localStringBuffer.append("isforceupdate");
        localStringBuffer.append(" INTEGER  DEFAULT 0,");
        localStringBuffer.append("location");
        localStringBuffer.append(" INTEGER DEFAULT 0, ");
        localStringBuffer.append("order_index");
        localStringBuffer.append(" INTEGER, ");
        localStringBuffer.append("extString1");
        localStringBuffer.append(" TEXT,");
        localStringBuffer.append("extString2");
        localStringBuffer.append(" TEXT,");
        localStringBuffer.append("extInteger1");
        localStringBuffer.append(" INTEGER,");
        localStringBuffer.append("signature");
        localStringBuffer.append(" TEXT, ");
        localStringBuffer.append("extString3");
        localStringBuffer.append(" TEXT, ");
        localStringBuffer.append("extString4");
        localStringBuffer.append(" TEXT, ");
        localStringBuffer.append("extInteger2");
        localStringBuffer.append(" INTEGER,");
        localStringBuffer.append("downPath");
        localStringBuffer.append(" TEXT DEFAULT NULL, ");
        localStringBuffer.append("installPath");
        localStringBuffer.append(" TEXT  DEFAULT NULL, ");
        localStringBuffer.append("unzipPath");
        localStringBuffer.append(" TEXT DEFAULT NULL, ");
        localStringBuffer.append("isZipFileUpdate");
        localStringBuffer.append(" INTEGER DEFAULT 0, ");
        localStringBuffer.append("installVersion");
        localStringBuffer.append(" TEXT  DEFAULT NULL, ");
        localStringBuffer.append("infofrom");
        localStringBuffer.append(" INTEGER DEFAULT 0, ");
        localStringBuffer.append("antihijackurl");
        localStringBuffer.append(" TEXT  DEFAULT NULL,");
        localStringBuffer.append("needUpdate");
        localStringBuffer.append(" INTEGER DEFAULT 0 ");
        localStringBuffer.append(");");
        this.jdField_a_of_type_ComTencentCommonPluginDBHelper.execSQL(localStringBuffer.toString());
        a();
      }
      else
      {
        a(paramContext, this.jdField_a_of_type_ComTencentCommonPluginDBHelper);
      }
      a(paramContext);
      return true;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  private boolean a(String paramString)
  {
    return TextUtils.isEmpty(paramString);
  }
  
  private boolean a(final String paramString1, final String paramString2, final int paramInt1, final int paramInt2)
  {
    BrowserExecutorSupplier.postForDbTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        if (paramString1 != null)
        {
          if (paramString2 == null) {
            return;
          }
          DBHelper localDBHelper = QBPluginDBHelper.a(QBPluginDBHelper.this);
          Object localObject = new StringBuilder();
          ((StringBuilder)localObject).append("packageName = '");
          ((StringBuilder)localObject).append(paramString1);
          ((StringBuilder)localObject).append("' AND ");
          ((StringBuilder)localObject).append("infofrom");
          ((StringBuilder)localObject).append("=");
          ((StringBuilder)localObject).append(paramInt2);
          localObject = ((StringBuilder)localObject).toString();
          ContentValues localContentValues = new ContentValues();
          localContentValues.put(paramString2, Integer.valueOf(paramInt1));
          try
          {
            localDBHelper.update("plugins", localContentValues, (String)localObject);
            QBPluginDBHelper.this.mLastException = null;
            return;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            QBPluginDBHelper.this.mLastException = localException;
            return;
          }
        }
      }
    });
    return true;
  }
  
  private boolean a(final String paramString1, final String paramString2, final String paramString3, final int paramInt)
  {
    BrowserExecutorSupplier.postForDbTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        QBPluginDBHelper.a(QBPluginDBHelper.this, paramString1, paramString2, paramString3, paramInt);
      }
    });
    return true;
  }
  
  private void b()
  {
    ArrayList localArrayList;
    if (QBPluginServiceImpl.msIPluginDirUi != null) {
      localArrayList = QBPluginServiceImpl.msIPluginDirUi.getDefaultPluginList();
    } else {
      localArrayList = null;
    }
    if ((localArrayList != null) && (localArrayList.size() >= 0))
    {
      FLogger.d("QBPluginServiceImpl_TBS", "Insert Default Tool PluginList Into DB.");
      a(localArrayList);
    }
  }
  
  private boolean b(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    if ((!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2)))
    {
      if (TextUtils.isEmpty(paramString3)) {
        return false;
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("packageName = '");
      ((StringBuilder)localObject).append(paramString1);
      ((StringBuilder)localObject).append("' AND ");
      ((StringBuilder)localObject).append("infofrom");
      ((StringBuilder)localObject).append("=");
      ((StringBuilder)localObject).append(paramInt);
      paramString1 = ((StringBuilder)localObject).toString();
      localObject = new ContentValues();
      ((ContentValues)localObject).put(paramString2, paramString3);
      try
      {
        this.jdField_a_of_type_ComTencentCommonPluginDBHelper.update("plugins", (ContentValues)localObject, paramString1);
        this.mLastException = null;
        return true;
      }
      catch (Exception paramString1)
      {
        paramString1.printStackTrace();
        a(8, new Object[] { Integer.valueOf(2), paramString1 });
        this.mLastException = paramString1;
        return false;
      }
    }
    return false;
  }
  
  public static QBPluginDBHelper getInstance(Context paramContext)
  {
    try
    {
      if (jdField_a_of_type_ComTencentCommonPluginQBPluginDBHelper == null) {
        jdField_a_of_type_ComTencentCommonPluginQBPluginDBHelper = new QBPluginDBHelper(paramContext);
      }
      paramContext = jdField_a_of_type_ComTencentCommonPluginQBPluginDBHelper;
      return paramContext;
    }
    finally {}
  }
  
  boolean a(String paramString, boolean paramBoolean, int paramInt)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public boolean cleanPluginData()
  {
    this.jdField_a_of_type_JavaUtilList.clear();
    try
    {
      this.jdField_a_of_type_ComTencentCommonPluginDBHelper.deleteTable("plugins");
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public void deleteSinglePluginMem(String paramString, int paramInt)
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (QBPluginItemInfo)localIterator.next();
      if ((localObject != null) && (paramString.equalsIgnoreCase(((QBPluginItemInfo)localObject).mPackageName)) && (((QBPluginItemInfo)localObject).mInfoFrom == paramInt))
      {
        localIterator.remove();
        localObject = this.jdField_a_of_type_JavaLangString;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("mPluginListMem.size=");
        localStringBuilder.append(this.jdField_a_of_type_JavaUtilList.size());
        localStringBuilder.append("deleteSinglePlugin=");
        localStringBuilder.append(paramString);
        localStringBuilder.append(",infoFrom=");
        localStringBuilder.append(paramInt);
        FLogger.d((String)localObject, localStringBuilder.toString());
      }
    }
  }
  
  public boolean deleteSinglePluginSqlite(String paramString, int paramInt)
  {
    try
    {
      localObject = this.jdField_a_of_type_ComTencentCommonPluginDBHelper;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramInt);
      localStringBuilder.append("");
      ((DBHelper)localObject).delete("plugins", "packageName=? AND infofrom=?", new String[] { paramString, localStringBuilder.toString() });
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object localObject = paramString;
      if (paramString.split("\\.") != null) {
        localObject = paramString.split("\\.")[(paramString.split("\\.").length - 1)];
      }
      paramInt = PluginStatBehavior.getOpTyepPluginList(paramInt);
      paramString = new StringBuilder();
      paramString.append("D");
      paramString.append((String)localObject);
      PluginStatBehavior.addLogPath("PluginList", paramInt, paramString.toString());
      localException.printStackTrace();
    }
    return false;
  }
  
  /* Error */
  public ArrayList<QBPluginItemInfo> getAllPluginList(int paramInt)
  {
    // Byte code:
    //   0: new 146	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 147	java/util/ArrayList:<init>	()V
    //   7: astore 5
    //   9: aload_0
    //   10: getfield 153	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   13: astore_2
    //   14: aload_2
    //   15: monitorenter
    //   16: aload_0
    //   17: getfield 151	com/tencent/common/plugin/QBPluginDBHelper:mPluginDatasInited	Z
    //   20: ifeq +72 -> 92
    //   23: aload_0
    //   24: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   27: invokeinterface 597 1 0
    //   32: astore_3
    //   33: aload_3
    //   34: invokeinterface 207 1 0
    //   39: ifeq +39 -> 78
    //   42: aload_3
    //   43: invokeinterface 211 1 0
    //   48: checkcast 348	com/tencent/common/plugin/QBPluginItemInfo
    //   51: astore 4
    //   53: aload 4
    //   55: ifnull -22 -> 33
    //   58: aload 4
    //   60: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   63: iload_1
    //   64: if_icmpne -31 -> 33
    //   67: aload 5
    //   69: aload 4
    //   71: invokevirtual 771	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   74: pop
    //   75: goto -42 -> 33
    //   78: aload 5
    //   80: aload_0
    //   81: getfield 166	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginQBPluginDBHelper$PluginComparator	Lcom/tencent/common/plugin/QBPluginDBHelper$PluginComparator;
    //   84: invokestatic 777	java/util/Collections:sort	(Ljava/util/List;Ljava/util/Comparator;)V
    //   87: aload_2
    //   88: monitorexit
    //   89: aload 5
    //   91: areturn
    //   92: aload_2
    //   93: monitorexit
    //   94: aload_0
    //   95: getfield 144	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper	Lcom/tencent/common/plugin/DBHelper;
    //   98: astore 4
    //   100: aconst_null
    //   101: astore_2
    //   102: aconst_null
    //   103: astore_3
    //   104: aload 4
    //   106: ldc 117
    //   108: aconst_null
    //   109: ldc_w 531
    //   112: invokevirtual 459	com/tencent/common/plugin/DBHelper:query	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   115: astore 4
    //   117: aload 4
    //   119: astore_3
    //   120: aload 4
    //   122: astore_2
    //   123: aload 4
    //   125: invokeinterface 469 1 0
    //   130: ifeq +39 -> 169
    //   133: aload 4
    //   135: astore_3
    //   136: aload 4
    //   138: astore_2
    //   139: aload_0
    //   140: aload 4
    //   142: invokespecial 533	com/tencent/common/plugin/QBPluginDBHelper:a	(Landroid/database/Cursor;)Lcom/tencent/common/plugin/QBPluginItemInfo;
    //   145: astore 6
    //   147: aload 6
    //   149: ifnull -32 -> 117
    //   152: aload 4
    //   154: astore_3
    //   155: aload 4
    //   157: astore_2
    //   158: aload 5
    //   160: aload 6
    //   162: invokevirtual 771	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   165: pop
    //   166: goto -49 -> 117
    //   169: aload 4
    //   171: ifnull +62 -> 233
    //   174: aload 4
    //   176: astore_2
    //   177: aload_2
    //   178: invokeinterface 462 1 0
    //   183: goto +50 -> 233
    //   186: astore_2
    //   187: goto +68 -> 255
    //   190: astore 4
    //   192: aload_2
    //   193: astore_3
    //   194: aload 4
    //   196: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   199: aload_2
    //   200: astore_3
    //   201: iconst_3
    //   202: iconst_1
    //   203: anewarray 4	java/lang/Object
    //   206: dup
    //   207: iconst_0
    //   208: aload 4
    //   210: invokevirtual 778	java/lang/Exception:toString	()Ljava/lang/String;
    //   213: ldc_w 780
    //   216: ldc_w 782
    //   219: invokevirtual 786	java/lang/String:replaceAll	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   222: aastore
    //   223: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   226: aload_2
    //   227: ifnull +6 -> 233
    //   230: goto -53 -> 177
    //   233: iconst_2
    //   234: iconst_1
    //   235: anewarray 4	java/lang/Object
    //   238: dup
    //   239: iconst_0
    //   240: aload 5
    //   242: invokevirtual 287	java/util/ArrayList:size	()I
    //   245: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   248: aastore
    //   249: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   252: aload 5
    //   254: areturn
    //   255: aload_3
    //   256: ifnull +9 -> 265
    //   259: aload_3
    //   260: invokeinterface 462 1 0
    //   265: aload_2
    //   266: athrow
    //   267: astore_3
    //   268: aload_2
    //   269: monitorexit
    //   270: aload_3
    //   271: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	272	0	this	QBPluginDBHelper
    //   0	272	1	paramInt	int
    //   13	165	2	localObject1	Object
    //   186	83	2	localObject2	Object
    //   32	228	3	localObject3	Object
    //   267	4	3	localObject4	Object
    //   51	124	4	localObject5	Object
    //   190	19	4	localException	Exception
    //   7	246	5	localArrayList	ArrayList
    //   145	16	6	localQBPluginItemInfo	QBPluginItemInfo
    // Exception table:
    //   from	to	target	type
    //   104	117	186	finally
    //   123	133	186	finally
    //   139	147	186	finally
    //   158	166	186	finally
    //   194	199	186	finally
    //   201	226	186	finally
    //   104	117	190	java/lang/Exception
    //   123	133	190	java/lang/Exception
    //   139	147	190	java/lang/Exception
    //   158	166	190	java/lang/Exception
    //   16	33	267	finally
    //   33	53	267	finally
    //   58	75	267	finally
    //   78	89	267	finally
    //   92	94	267	finally
    //   268	270	267	finally
  }
  
  public String getDbFileName()
  {
    try
    {
      if (this.jdField_a_of_type_ComTencentCommonPluginDBHelper != null)
      {
        Object localObject = this.jdField_a_of_type_ComTencentCommonPluginDBHelper.getSQLiteDatabase();
        if (localObject != null)
        {
          localObject = ((SQLiteDatabase)localObject).getPath();
          return (String)localObject;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  /* Error */
  public QBPluginItemInfo getPluginItemInfo(String paramString, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: iconst_0
    //   2: putfield 161	com/tencent/common/plugin/QBPluginDBHelper:mPluginGetError	I
    //   5: aload_0
    //   6: getfield 153	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   9: astore 4
    //   11: aload 4
    //   13: monitorenter
    //   14: aload_0
    //   15: getfield 151	com/tencent/common/plugin/QBPluginDBHelper:mPluginDatasInited	Z
    //   18: istore_3
    //   19: aconst_null
    //   20: astore 7
    //   22: aconst_null
    //   23: astore 8
    //   25: aconst_null
    //   26: astore 5
    //   28: aconst_null
    //   29: astore 6
    //   31: iload_3
    //   32: ifeq +383 -> 415
    //   35: aload_0
    //   36: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   39: invokeinterface 597 1 0
    //   44: astore 6
    //   46: aload 6
    //   48: invokeinterface 207 1 0
    //   53: ifeq +352 -> 405
    //   56: aload 6
    //   58: invokeinterface 211 1 0
    //   63: checkcast 348	com/tencent/common/plugin/QBPluginItemInfo
    //   66: astore 5
    //   68: aload 5
    //   70: ifnull -24 -> 46
    //   73: aload 5
    //   75: getfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   78: aload_1
    //   79: invokevirtual 477	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   82: ifeq -36 -> 46
    //   85: aload 5
    //   87: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   90: iload_2
    //   91: if_icmpne -45 -> 46
    //   94: new 348	com/tencent/common/plugin/QBPluginItemInfo
    //   97: dup
    //   98: invokespecial 349	com/tencent/common/plugin/QBPluginItemInfo:<init>	()V
    //   101: astore 6
    //   103: aload 6
    //   105: aload 5
    //   107: getfield 399	com/tencent/common/plugin/QBPluginItemInfo:mDetailSumary	Ljava/lang/String;
    //   110: putfield 399	com/tencent/common/plugin/QBPluginItemInfo:mDetailSumary	Ljava/lang/String;
    //   113: aload 6
    //   115: aload 5
    //   117: getfield 408	com/tencent/common/plugin/QBPluginItemInfo:mDownloadDir	Ljava/lang/String;
    //   120: putfield 408	com/tencent/common/plugin/QBPluginItemInfo:mDownloadDir	Ljava/lang/String;
    //   123: aload 6
    //   125: aload 5
    //   127: getfield 429	com/tencent/common/plugin/QBPluginItemInfo:mDownloadFileName	Ljava/lang/String;
    //   130: putfield 429	com/tencent/common/plugin/QBPluginItemInfo:mDownloadFileName	Ljava/lang/String;
    //   133: aload 6
    //   135: aload 5
    //   137: getfield 402	com/tencent/common/plugin/QBPluginItemInfo:mExt	Ljava/lang/String;
    //   140: putfield 402	com/tencent/common/plugin/QBPluginItemInfo:mExt	Ljava/lang/String;
    //   143: aload 6
    //   145: aload 5
    //   147: getfield 368	com/tencent/common/plugin/QBPluginItemInfo:mIconUrl	Ljava/lang/String;
    //   150: putfield 368	com/tencent/common/plugin/QBPluginItemInfo:mIconUrl	Ljava/lang/String;
    //   153: aload 6
    //   155: aload 5
    //   157: getfield 411	com/tencent/common/plugin/QBPluginItemInfo:mInstallDir	Ljava/lang/String;
    //   160: putfield 411	com/tencent/common/plugin/QBPluginItemInfo:mInstallDir	Ljava/lang/String;
    //   163: aload 6
    //   165: aload 5
    //   167: getfield 432	com/tencent/common/plugin/QBPluginItemInfo:mInstallVersion	Ljava/lang/String;
    //   170: putfield 432	com/tencent/common/plugin/QBPluginItemInfo:mInstallVersion	Ljava/lang/String;
    //   173: aload 6
    //   175: aload 5
    //   177: getfield 387	com/tencent/common/plugin/QBPluginItemInfo:mIsInstall	I
    //   180: putfield 387	com/tencent/common/plugin/QBPluginItemInfo:mIsInstall	I
    //   183: aload 6
    //   185: aload 5
    //   187: getfield 417	com/tencent/common/plugin/QBPluginItemInfo:mIsZipFileUpdate	I
    //   190: putfield 417	com/tencent/common/plugin/QBPluginItemInfo:mIsZipFileUpdate	I
    //   193: aload 6
    //   195: aload 5
    //   197: getfield 396	com/tencent/common/plugin/QBPluginItemInfo:mLocation	I
    //   200: putfield 396	com/tencent/common/plugin/QBPluginItemInfo:mLocation	I
    //   203: aload 6
    //   205: aload 5
    //   207: getfield 423	com/tencent/common/plugin/QBPluginItemInfo:mMd5	Ljava/lang/String;
    //   210: putfield 423	com/tencent/common/plugin/QBPluginItemInfo:mMd5	Ljava/lang/String;
    //   213: aload 6
    //   215: aload 5
    //   217: getfield 393	com/tencent/common/plugin/QBPluginItemInfo:mOrder	I
    //   220: putfield 393	com/tencent/common/plugin/QBPluginItemInfo:mOrder	I
    //   223: aload 6
    //   225: aload 5
    //   227: getfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   230: putfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   233: aload 6
    //   235: aload 5
    //   237: getfield 384	com/tencent/common/plugin/QBPluginItemInfo:mPackageSize	Ljava/lang/String;
    //   240: putfield 384	com/tencent/common/plugin/QBPluginItemInfo:mPackageSize	Ljava/lang/String;
    //   243: aload 6
    //   245: aload 5
    //   247: getfield 420	com/tencent/common/plugin/QBPluginItemInfo:mPluginCompatiID	Ljava/lang/String;
    //   250: putfield 420	com/tencent/common/plugin/QBPluginItemInfo:mPluginCompatiID	Ljava/lang/String;
    //   253: aload 6
    //   255: aload 5
    //   257: getfield 378	com/tencent/common/plugin/QBPluginItemInfo:mPluginType	I
    //   260: putfield 378	com/tencent/common/plugin/QBPluginItemInfo:mPluginType	I
    //   263: aload 6
    //   265: aload 5
    //   267: getfield 365	com/tencent/common/plugin/QBPluginItemInfo:mUrl	Ljava/lang/String;
    //   270: putfield 365	com/tencent/common/plugin/QBPluginItemInfo:mUrl	Ljava/lang/String;
    //   273: aload 6
    //   275: aload 5
    //   277: getfield 381	com/tencent/common/plugin/QBPluginItemInfo:mVersion	Ljava/lang/String;
    //   280: putfield 381	com/tencent/common/plugin/QBPluginItemInfo:mVersion	Ljava/lang/String;
    //   283: aload 6
    //   285: aload 5
    //   287: getfield 414	com/tencent/common/plugin/QBPluginItemInfo:mUnzipDir	Ljava/lang/String;
    //   290: putfield 414	com/tencent/common/plugin/QBPluginItemInfo:mUnzipDir	Ljava/lang/String;
    //   293: aload 6
    //   295: aload 5
    //   297: getfield 426	com/tencent/common/plugin/QBPluginItemInfo:mZipJarPluginType	I
    //   300: putfield 426	com/tencent/common/plugin/QBPluginItemInfo:mZipJarPluginType	I
    //   303: aload 6
    //   305: aload 5
    //   307: getfield 362	com/tencent/common/plugin/QBPluginItemInfo:mTitle	Ljava/lang/String;
    //   310: putfield 362	com/tencent/common/plugin/QBPluginItemInfo:mTitle	Ljava/lang/String;
    //   313: aload 6
    //   315: aload 5
    //   317: getfield 390	com/tencent/common/plugin/QBPluginItemInfo:mUpdateType	I
    //   320: putfield 390	com/tencent/common/plugin/QBPluginItemInfo:mUpdateType	I
    //   323: aload 6
    //   325: aload 5
    //   327: getfield 405	com/tencent/common/plugin/QBPluginItemInfo:mSignature	Ljava/lang/String;
    //   330: putfield 405	com/tencent/common/plugin/QBPluginItemInfo:mSignature	Ljava/lang/String;
    //   333: aload 6
    //   335: aload 5
    //   337: getfield 435	com/tencent/common/plugin/QBPluginItemInfo:mAntiHijackUrl	Ljava/lang/String;
    //   340: putfield 435	com/tencent/common/plugin/QBPluginItemInfo:mAntiHijackUrl	Ljava/lang/String;
    //   343: aload 6
    //   345: aload 5
    //   347: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   350: putfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   353: aload 6
    //   355: aload 5
    //   357: getfield 441	com/tencent/common/plugin/QBPluginItemInfo:isNeedUpdate	I
    //   360: putfield 441	com/tencent/common/plugin/QBPluginItemInfo:isNeedUpdate	I
    //   363: new 215	java/lang/StringBuilder
    //   366: dup
    //   367: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   370: astore 5
    //   372: aload 5
    //   374: ldc_w 793
    //   377: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   380: pop
    //   381: aload 5
    //   383: aload_1
    //   384: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   387: pop
    //   388: ldc_w 717
    //   391: aload 5
    //   393: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   396: invokestatic 496	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   399: aload 4
    //   401: monitorexit
    //   402: aload 6
    //   404: areturn
    //   405: aload_0
    //   406: iconst_2
    //   407: putfield 161	com/tencent/common/plugin/QBPluginDBHelper:mPluginGetError	I
    //   410: aload 4
    //   412: monitorexit
    //   413: aconst_null
    //   414: areturn
    //   415: aload 4
    //   417: monitorexit
    //   418: aload_0
    //   419: getfield 144	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper	Lcom/tencent/common/plugin/DBHelper;
    //   422: astore 4
    //   424: new 215	java/lang/StringBuilder
    //   427: dup
    //   428: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   431: astore 9
    //   433: aload 9
    //   435: iload_2
    //   436: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   439: pop
    //   440: aload 9
    //   442: ldc_w 293
    //   445: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   448: pop
    //   449: aload 4
    //   451: iconst_0
    //   452: ldc 117
    //   454: ldc_w 752
    //   457: iconst_2
    //   458: anewarray 227	java/lang/String
    //   461: dup
    //   462: iconst_0
    //   463: aload_1
    //   464: aastore
    //   465: dup
    //   466: iconst_1
    //   467: aload 9
    //   469: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   472: aastore
    //   473: aconst_null
    //   474: aconst_null
    //   475: aconst_null
    //   476: aconst_null
    //   477: invokevirtual 796	com/tencent/common/plugin/DBHelper:query	(ZLjava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   480: astore 4
    //   482: aload 4
    //   484: ifnull +41 -> 525
    //   487: aload 4
    //   489: astore 5
    //   491: aload 4
    //   493: invokeinterface 799 1 0
    //   498: ifeq +27 -> 525
    //   501: aload 4
    //   503: astore 5
    //   505: aload_0
    //   506: bipush 10
    //   508: putfield 161	com/tencent/common/plugin/QBPluginDBHelper:mPluginGetError	I
    //   511: aload 4
    //   513: astore 5
    //   515: aload_0
    //   516: aload 4
    //   518: invokespecial 533	com/tencent/common/plugin/QBPluginDBHelper:a	(Landroid/database/Cursor;)Lcom/tencent/common/plugin/QBPluginItemInfo;
    //   521: astore_1
    //   522: goto +15 -> 537
    //   525: aload 4
    //   527: astore 5
    //   529: aload_0
    //   530: iconst_4
    //   531: putfield 161	com/tencent/common/plugin/QBPluginDBHelper:mPluginGetError	I
    //   534: aload 6
    //   536: astore_1
    //   537: aload_1
    //   538: astore 5
    //   540: aload 4
    //   542: ifnull +94 -> 636
    //   545: aload 4
    //   547: invokeinterface 462 1 0
    //   552: aload_1
    //   553: astore 5
    //   555: goto +81 -> 636
    //   558: astore 6
    //   560: aload 4
    //   562: astore_1
    //   563: goto +15 -> 578
    //   566: astore_1
    //   567: aload 5
    //   569: astore 4
    //   571: goto +91 -> 662
    //   574: astore 6
    //   576: aconst_null
    //   577: astore_1
    //   578: aload_1
    //   579: astore 5
    //   581: aload_0
    //   582: iconst_5
    //   583: putfield 161	com/tencent/common/plugin/QBPluginDBHelper:mPluginGetError	I
    //   586: aload_1
    //   587: astore 5
    //   589: iconst_1
    //   590: invokestatic 175	com/tencent/common/plugin/QBPluginServiceImpl:getPluginRelateFunc	(I)Lcom/tencent/common/plugin/QBPluginServiceImpl$IPluginRelateFunc;
    //   593: ifnull +18 -> 611
    //   596: aload_1
    //   597: astore 5
    //   599: iconst_1
    //   600: invokestatic 175	com/tencent/common/plugin/QBPluginServiceImpl:getPluginRelateFunc	(I)Lcom/tencent/common/plugin/QBPluginServiceImpl$IPluginRelateFunc;
    //   603: ldc_w 801
    //   606: invokeinterface 183 2 0
    //   611: aload_1
    //   612: astore 5
    //   614: aload 6
    //   616: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   619: aload 8
    //   621: astore 5
    //   623: aload_1
    //   624: ifnull +12 -> 636
    //   627: aload_1
    //   628: astore 4
    //   630: aload 7
    //   632: astore_1
    //   633: goto -88 -> 545
    //   636: iconst_4
    //   637: iconst_1
    //   638: anewarray 4	java/lang/Object
    //   641: dup
    //   642: iconst_0
    //   643: aload_0
    //   644: getfield 161	com/tencent/common/plugin/QBPluginDBHelper:mPluginGetError	I
    //   647: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   650: aastore
    //   651: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   654: aload 5
    //   656: areturn
    //   657: astore_1
    //   658: aload 5
    //   660: astore 4
    //   662: aload 4
    //   664: ifnull +10 -> 674
    //   667: aload 4
    //   669: invokeinterface 462 1 0
    //   674: aload_1
    //   675: athrow
    //   676: astore_1
    //   677: aload 4
    //   679: monitorexit
    //   680: aload_1
    //   681: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	682	0	this	QBPluginDBHelper
    //   0	682	1	paramString	String
    //   0	682	2	paramInt	int
    //   18	14	3	bool	boolean
    //   9	669	4	localObject1	Object
    //   26	633	5	localObject2	Object
    //   29	506	6	localObject3	Object
    //   558	1	6	localException1	Exception
    //   574	41	6	localException2	Exception
    //   20	611	7	localObject4	Object
    //   23	597	8	localObject5	Object
    //   431	37	9	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   491	501	558	java/lang/Exception
    //   505	511	558	java/lang/Exception
    //   515	522	558	java/lang/Exception
    //   529	534	558	java/lang/Exception
    //   418	482	566	finally
    //   418	482	574	java/lang/Exception
    //   491	501	657	finally
    //   505	511	657	finally
    //   515	522	657	finally
    //   529	534	657	finally
    //   581	586	657	finally
    //   589	596	657	finally
    //   599	611	657	finally
    //   614	619	657	finally
    //   14	19	676	finally
    //   35	46	676	finally
    //   46	68	676	finally
    //   73	402	676	finally
    //   405	413	676	finally
    //   415	418	676	finally
    //   677	680	676	finally
  }
  
  /* Error */
  public ArrayList<QBPluginItemInfo> getPluginListByPos(int paramInt1, int paramInt2)
  {
    // Byte code:
    //   0: new 146	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 147	java/util/ArrayList:<init>	()V
    //   7: astore 6
    //   9: aload_0
    //   10: getfield 153	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_3
    //   15: monitorenter
    //   16: aload_0
    //   17: getfield 151	com/tencent/common/plugin/QBPluginDBHelper:mPluginDatasInited	Z
    //   20: ifeq +84 -> 104
    //   23: aload_0
    //   24: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   27: invokeinterface 597 1 0
    //   32: astore 4
    //   34: aload 4
    //   36: invokeinterface 207 1 0
    //   41: ifeq +49 -> 90
    //   44: aload 4
    //   46: invokeinterface 211 1 0
    //   51: checkcast 348	com/tencent/common/plugin/QBPluginItemInfo
    //   54: astore 5
    //   56: aload 5
    //   58: ifnull -24 -> 34
    //   61: aload 5
    //   63: getfield 396	com/tencent/common/plugin/QBPluginItemInfo:mLocation	I
    //   66: iload_1
    //   67: if_icmpne -33 -> 34
    //   70: aload 5
    //   72: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   75: iload_2
    //   76: if_icmpne -42 -> 34
    //   79: aload 6
    //   81: aload 5
    //   83: invokevirtual 771	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   86: pop
    //   87: goto -53 -> 34
    //   90: aload 6
    //   92: aload_0
    //   93: getfield 166	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginQBPluginDBHelper$PluginComparator	Lcom/tencent/common/plugin/QBPluginDBHelper$PluginComparator;
    //   96: invokestatic 777	java/util/Collections:sort	(Ljava/util/List;Ljava/util/Comparator;)V
    //   99: aload_3
    //   100: monitorexit
    //   101: aload 6
    //   103: areturn
    //   104: aload_3
    //   105: monitorexit
    //   106: new 215	java/lang/StringBuilder
    //   109: dup
    //   110: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   113: astore_3
    //   114: aload_3
    //   115: ldc_w 805
    //   118: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: pop
    //   122: aload_3
    //   123: iload_1
    //   124: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   127: pop
    //   128: aload_3
    //   129: ldc_w 807
    //   132: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: pop
    //   136: aload_3
    //   137: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   140: astore 5
    //   142: aload_0
    //   143: getfield 144	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper	Lcom/tencent/common/plugin/DBHelper;
    //   146: astore 7
    //   148: aconst_null
    //   149: astore_3
    //   150: aconst_null
    //   151: astore 4
    //   153: aload 7
    //   155: ldc 117
    //   157: aload 5
    //   159: ldc_w 531
    //   162: invokevirtual 459	com/tencent/common/plugin/DBHelper:query	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   165: astore 5
    //   167: aload 5
    //   169: astore 4
    //   171: aload 5
    //   173: astore_3
    //   174: aload 5
    //   176: invokeinterface 469 1 0
    //   181: ifeq +60 -> 241
    //   184: aload 5
    //   186: astore 4
    //   188: aload 5
    //   190: astore_3
    //   191: aload_0
    //   192: aload 5
    //   194: invokespecial 533	com/tencent/common/plugin/QBPluginDBHelper:a	(Landroid/database/Cursor;)Lcom/tencent/common/plugin/QBPluginItemInfo;
    //   197: astore 7
    //   199: aload 7
    //   201: ifnull -34 -> 167
    //   204: aload 5
    //   206: astore 4
    //   208: aload 5
    //   210: astore_3
    //   211: aload_0
    //   212: aload 7
    //   214: getfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   217: invokespecial 809	com/tencent/common/plugin/QBPluginDBHelper:a	(Ljava/lang/String;)Z
    //   220: ifne -53 -> 167
    //   223: aload 5
    //   225: astore 4
    //   227: aload 5
    //   229: astore_3
    //   230: aload 6
    //   232: aload 7
    //   234: invokevirtual 771	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   237: pop
    //   238: goto -71 -> 167
    //   241: aload 5
    //   243: ifnull +33 -> 276
    //   246: aload 5
    //   248: astore_3
    //   249: goto +21 -> 270
    //   252: astore_3
    //   253: goto +26 -> 279
    //   256: astore 5
    //   258: aload_3
    //   259: astore 4
    //   261: aload 5
    //   263: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   266: aload_3
    //   267: ifnull +9 -> 276
    //   270: aload_3
    //   271: invokeinterface 462 1 0
    //   276: aload 6
    //   278: areturn
    //   279: aload 4
    //   281: ifnull +10 -> 291
    //   284: aload 4
    //   286: invokeinterface 462 1 0
    //   291: aload_3
    //   292: athrow
    //   293: astore 4
    //   295: aload_3
    //   296: monitorexit
    //   297: aload 4
    //   299: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	300	0	this	QBPluginDBHelper
    //   0	300	1	paramInt1	int
    //   0	300	2	paramInt2	int
    //   13	236	3	localObject1	Object
    //   252	44	3	localObject2	Object
    //   32	253	4	localObject3	Object
    //   293	5	4	localObject4	Object
    //   54	193	5	localObject5	Object
    //   256	6	5	localException	Exception
    //   7	270	6	localArrayList	ArrayList
    //   146	87	7	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   153	167	252	finally
    //   174	184	252	finally
    //   191	199	252	finally
    //   211	223	252	finally
    //   230	238	252	finally
    //   261	266	252	finally
    //   153	167	256	java/lang/Exception
    //   174	184	256	java/lang/Exception
    //   191	199	256	java/lang/Exception
    //   211	223	256	java/lang/Exception
    //   230	238	256	java/lang/Exception
    //   16	34	293	finally
    //   34	56	293	finally
    //   61	87	293	finally
    //   90	101	293	finally
    //   104	106	293	finally
    //   295	297	293	finally
  }
  
  /* Error */
  public ArrayList<QBPluginItemInfo> getPluginListByType(int paramInt1, int paramInt2)
  {
    // Byte code:
    //   0: new 146	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 147	java/util/ArrayList:<init>	()V
    //   7: astore 6
    //   9: aload_0
    //   10: getfield 153	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   13: astore_3
    //   14: aload_3
    //   15: monitorenter
    //   16: aload_0
    //   17: getfield 151	com/tencent/common/plugin/QBPluginDBHelper:mPluginDatasInited	Z
    //   20: ifeq +84 -> 104
    //   23: aload_0
    //   24: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   27: invokeinterface 597 1 0
    //   32: astore 4
    //   34: aload 4
    //   36: invokeinterface 207 1 0
    //   41: ifeq +49 -> 90
    //   44: aload 4
    //   46: invokeinterface 211 1 0
    //   51: checkcast 348	com/tencent/common/plugin/QBPluginItemInfo
    //   54: astore 5
    //   56: aload 5
    //   58: ifnull -24 -> 34
    //   61: aload 5
    //   63: getfield 378	com/tencent/common/plugin/QBPluginItemInfo:mPluginType	I
    //   66: iload_1
    //   67: if_icmpne -33 -> 34
    //   70: aload 5
    //   72: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   75: iload_2
    //   76: if_icmpne -42 -> 34
    //   79: aload 6
    //   81: aload 5
    //   83: invokevirtual 771	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   86: pop
    //   87: goto -53 -> 34
    //   90: aload 6
    //   92: aload_0
    //   93: getfield 166	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginQBPluginDBHelper$PluginComparator	Lcom/tencent/common/plugin/QBPluginDBHelper$PluginComparator;
    //   96: invokestatic 777	java/util/Collections:sort	(Ljava/util/List;Ljava/util/Comparator;)V
    //   99: aload_3
    //   100: monitorexit
    //   101: aload 6
    //   103: areturn
    //   104: aload_3
    //   105: monitorexit
    //   106: new 215	java/lang/StringBuilder
    //   109: dup
    //   110: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   113: astore_3
    //   114: aload_3
    //   115: ldc_w 813
    //   118: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: pop
    //   122: aload_3
    //   123: iload_1
    //   124: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   127: pop
    //   128: aload_3
    //   129: ldc_w 807
    //   132: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: pop
    //   136: aload_3
    //   137: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   140: astore 5
    //   142: aload_0
    //   143: getfield 144	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper	Lcom/tencent/common/plugin/DBHelper;
    //   146: astore 7
    //   148: aconst_null
    //   149: astore_3
    //   150: aconst_null
    //   151: astore 4
    //   153: aload 7
    //   155: ldc 117
    //   157: aload 5
    //   159: ldc_w 531
    //   162: invokevirtual 459	com/tencent/common/plugin/DBHelper:query	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   165: astore 5
    //   167: aload 5
    //   169: astore 4
    //   171: aload 5
    //   173: astore_3
    //   174: aload 5
    //   176: invokeinterface 469 1 0
    //   181: ifeq +41 -> 222
    //   184: aload 5
    //   186: astore 4
    //   188: aload 5
    //   190: astore_3
    //   191: aload_0
    //   192: aload 5
    //   194: invokespecial 533	com/tencent/common/plugin/QBPluginDBHelper:a	(Landroid/database/Cursor;)Lcom/tencent/common/plugin/QBPluginItemInfo;
    //   197: astore 7
    //   199: aload 7
    //   201: ifnull -34 -> 167
    //   204: aload 5
    //   206: astore 4
    //   208: aload 5
    //   210: astore_3
    //   211: aload 6
    //   213: aload 7
    //   215: invokevirtual 771	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   218: pop
    //   219: goto -52 -> 167
    //   222: aload 5
    //   224: ifnull +33 -> 257
    //   227: aload 5
    //   229: astore_3
    //   230: goto +21 -> 251
    //   233: astore_3
    //   234: goto +26 -> 260
    //   237: astore 5
    //   239: aload_3
    //   240: astore 4
    //   242: aload 5
    //   244: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   247: aload_3
    //   248: ifnull +9 -> 257
    //   251: aload_3
    //   252: invokeinterface 462 1 0
    //   257: aload 6
    //   259: areturn
    //   260: aload 4
    //   262: ifnull +10 -> 272
    //   265: aload 4
    //   267: invokeinterface 462 1 0
    //   272: aload_3
    //   273: athrow
    //   274: astore 4
    //   276: aload_3
    //   277: monitorexit
    //   278: aload 4
    //   280: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	281	0	this	QBPluginDBHelper
    //   0	281	1	paramInt1	int
    //   0	281	2	paramInt2	int
    //   13	217	3	localObject1	Object
    //   233	44	3	localObject2	Object
    //   32	234	4	localObject3	Object
    //   274	5	4	localObject4	Object
    //   54	174	5	localObject5	Object
    //   237	6	5	localException	Exception
    //   7	251	6	localArrayList	ArrayList
    //   146	68	7	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   153	167	233	finally
    //   174	184	233	finally
    //   191	199	233	finally
    //   211	219	233	finally
    //   242	247	233	finally
    //   153	167	237	java/lang/Exception
    //   174	184	237	java/lang/Exception
    //   191	199	237	java/lang/Exception
    //   211	219	237	java/lang/Exception
    //   16	34	274	finally
    //   34	56	274	finally
    //   61	87	274	finally
    //   90	101	274	finally
    //   104	106	274	finally
    //   276	278	274	finally
  }
  
  /* Error */
  public boolean insertPluginList(com.tencent.tbs.common.MTT.UniPluginRsp paramUniPluginRsp, Context paramContext, int paramInt)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +1762 -> 1763
    //   4: aload_1
    //   5: getfield 820	com/tencent/tbs/common/MTT/UniPluginRsp:vPluginList	Ljava/util/ArrayList;
    //   8: invokevirtual 287	java/util/ArrayList:size	()I
    //   11: ifge +6 -> 17
    //   14: goto +1749 -> 1763
    //   17: aload_1
    //   18: getfield 820	com/tencent/tbs/common/MTT/UniPluginRsp:vPluginList	Ljava/util/ArrayList;
    //   21: invokevirtual 287	java/util/ArrayList:size	()I
    //   24: ifne +48 -> 72
    //   27: ldc_w 768
    //   30: iload_3
    //   31: invokestatic 764	com/tencent/common/plugin/PluginStatBehavior:getOpTyepPluginList	(I)I
    //   34: sipush 324
    //   37: invokestatic 518	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   40: iconst_5
    //   41: iconst_2
    //   42: anewarray 4	java/lang/Object
    //   45: dup
    //   46: iconst_0
    //   47: iconst_0
    //   48: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   51: aastore
    //   52: dup
    //   53: iconst_1
    //   54: aload_0
    //   55: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   58: invokeinterface 527 1 0
    //   63: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   66: aastore
    //   67: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   70: iconst_1
    //   71: ireturn
    //   72: aload_0
    //   73: getfield 142	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   76: astore_2
    //   77: new 215	java/lang/StringBuilder
    //   80: dup
    //   81: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   84: astore 7
    //   86: aload 7
    //   88: ldc_w 822
    //   91: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: pop
    //   95: aload 7
    //   97: aload_1
    //   98: getfield 820	com/tencent/tbs/common/MTT/UniPluginRsp:vPluginList	Ljava/util/ArrayList;
    //   101: invokevirtual 287	java/util/ArrayList:size	()I
    //   104: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   107: pop
    //   108: aload_2
    //   109: aload 7
    //   111: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   114: invokestatic 496	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   117: iload_3
    //   118: invokestatic 764	com/tencent/common/plugin/PluginStatBehavior:getOpTyepPluginList	(I)I
    //   121: istore 4
    //   123: new 215	java/lang/StringBuilder
    //   126: dup
    //   127: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   130: astore_2
    //   131: aload_2
    //   132: ldc_w 824
    //   135: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: pop
    //   139: aload_2
    //   140: aload_1
    //   141: getfield 820	com/tencent/tbs/common/MTT/UniPluginRsp:vPluginList	Ljava/util/ArrayList;
    //   144: invokevirtual 287	java/util/ArrayList:size	()I
    //   147: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   150: pop
    //   151: aload_2
    //   152: ldc_w 489
    //   155: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: pop
    //   159: ldc_w 768
    //   162: iload 4
    //   164: aload_2
    //   165: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   168: invokestatic 515	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;ILjava/lang/String;)V
    //   171: iconst_5
    //   172: iconst_3
    //   173: anewarray 4	java/lang/Object
    //   176: dup
    //   177: iconst_0
    //   178: iconst_1
    //   179: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   182: aastore
    //   183: dup
    //   184: iconst_1
    //   185: aload_1
    //   186: getfield 820	com/tencent/tbs/common/MTT/UniPluginRsp:vPluginList	Ljava/util/ArrayList;
    //   189: invokevirtual 287	java/util/ArrayList:size	()I
    //   192: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   195: aastore
    //   196: dup
    //   197: iconst_2
    //   198: aload_0
    //   199: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   202: invokeinterface 527 1 0
    //   207: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   210: aastore
    //   211: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   214: new 146	java/util/ArrayList
    //   217: dup
    //   218: invokespecial 147	java/util/ArrayList:<init>	()V
    //   221: astore 10
    //   223: aload_0
    //   224: getfield 155	com/tencent/common/plugin/QBPluginDBHelper:b	Ljava/lang/Object;
    //   227: astore 9
    //   229: aload 9
    //   231: monitorenter
    //   232: aload_0
    //   233: getfield 144	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper	Lcom/tencent/common/plugin/DBHelper;
    //   236: astore 8
    //   238: new 215	java/lang/StringBuilder
    //   241: dup
    //   242: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   245: astore_2
    //   246: aload_2
    //   247: ldc_w 826
    //   250: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   253: pop
    //   254: aload_2
    //   255: iload_3
    //   256: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   259: pop
    //   260: aload 8
    //   262: ldc 117
    //   264: aload_2
    //   265: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   268: invokevirtual 829	com/tencent/common/plugin/DBHelper:query	(Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   271: astore_2
    //   272: aload_2
    //   273: invokeinterface 469 1 0
    //   278: ifeq +92 -> 370
    //   281: aload_2
    //   282: aload_2
    //   283: ldc 75
    //   285: invokeinterface 355 2 0
    //   290: invokeinterface 359 2 0
    //   295: astore 7
    //   297: aload_0
    //   298: getfield 142	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   301: astore 11
    //   303: new 215	java/lang/StringBuilder
    //   306: dup
    //   307: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   310: astore 12
    //   312: aload 12
    //   314: ldc_w 831
    //   317: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   320: pop
    //   321: aload 12
    //   323: aload 7
    //   325: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   328: pop
    //   329: aload 12
    //   331: ldc_w 487
    //   334: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   337: pop
    //   338: aload 12
    //   340: aload 10
    //   342: invokevirtual 287	java/util/ArrayList:size	()I
    //   345: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   348: pop
    //   349: aload 11
    //   351: aload 12
    //   353: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   356: invokestatic 496	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   359: aload 10
    //   361: aload 7
    //   363: invokevirtual 771	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   366: pop
    //   367: goto -95 -> 272
    //   370: aload_2
    //   371: ifnull +85 -> 456
    //   374: aload_2
    //   375: invokeinterface 462 1 0
    //   380: goto +76 -> 456
    //   383: astore_1
    //   384: goto +1361 -> 1745
    //   387: astore 7
    //   389: goto +13 -> 402
    //   392: astore_1
    //   393: aconst_null
    //   394: astore_2
    //   395: goto +1350 -> 1745
    //   398: astore 7
    //   400: aconst_null
    //   401: astore_2
    //   402: aload 7
    //   404: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   407: ldc_w 768
    //   410: iload_3
    //   411: invokestatic 764	com/tencent/common/plugin/PluginStatBehavior:getOpTyepPluginList	(I)I
    //   414: ldc_w 833
    //   417: invokestatic 515	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;ILjava/lang/String;)V
    //   420: iconst_5
    //   421: iconst_2
    //   422: anewarray 4	java/lang/Object
    //   425: dup
    //   426: iconst_0
    //   427: iconst_2
    //   428: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   431: aastore
    //   432: dup
    //   433: iconst_1
    //   434: aload 7
    //   436: invokevirtual 561	java/lang/Object:getClass	()Ljava/lang/Class;
    //   439: invokevirtual 566	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   442: aastore
    //   443: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   446: aload_2
    //   447: ifnull +9 -> 456
    //   450: aload_2
    //   451: invokeinterface 462 1 0
    //   456: iconst_5
    //   457: iconst_2
    //   458: anewarray 4	java/lang/Object
    //   461: dup
    //   462: iconst_0
    //   463: iconst_2
    //   464: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   467: aastore
    //   468: dup
    //   469: iconst_1
    //   470: aload 10
    //   472: invokevirtual 287	java/util/ArrayList:size	()I
    //   475: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   478: aastore
    //   479: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   482: aload 8
    //   484: invokevirtual 628	com/tencent/common/plugin/DBHelper:getSQLiteDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   487: astore 7
    //   489: aload 7
    //   491: ifnonnull +26 -> 517
    //   494: aload 7
    //   496: ifnull +16 -> 512
    //   499: aload 7
    //   501: invokevirtual 632	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   504: goto +8 -> 512
    //   507: astore_1
    //   508: aload_1
    //   509: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   512: aload 9
    //   514: monitorexit
    //   515: iconst_0
    //   516: ireturn
    //   517: aload 7
    //   519: astore_2
    //   520: aload 7
    //   522: invokevirtual 631	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   525: aload 7
    //   527: astore_2
    //   528: aload_1
    //   529: getfield 820	com/tencent/tbs/common/MTT/UniPluginRsp:vPluginList	Ljava/util/ArrayList;
    //   532: invokevirtual 834	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   535: astore 8
    //   537: iconst_1
    //   538: istore 5
    //   540: aload 7
    //   542: astore_2
    //   543: aload 8
    //   545: invokeinterface 207 1 0
    //   550: ifeq +385 -> 935
    //   553: aload 7
    //   555: astore_2
    //   556: aload 8
    //   558: invokeinterface 211 1 0
    //   563: checkcast 276	com/tencent/tbs/common/MTT/PluginItem
    //   566: astore 11
    //   568: aload 11
    //   570: ifnull +119 -> 689
    //   573: aload 7
    //   575: astore_2
    //   576: aload 10
    //   578: aload 11
    //   580: getfield 299	com/tencent/tbs/common/MTT/PluginItem:sPkgName	Ljava/lang/String;
    //   583: invokevirtual 837	java/util/ArrayList:contains	(Ljava/lang/Object;)Z
    //   586: ifeq +103 -> 689
    //   589: aload 7
    //   591: astore_2
    //   592: aload_0
    //   593: getfield 142	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   596: astore 12
    //   598: aload 7
    //   600: astore_2
    //   601: new 215	java/lang/StringBuilder
    //   604: dup
    //   605: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   608: astore 13
    //   610: aload 7
    //   612: astore_2
    //   613: aload 13
    //   615: ldc_w 839
    //   618: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   621: pop
    //   622: aload 7
    //   624: astore_2
    //   625: aload 13
    //   627: aload 11
    //   629: getfield 299	com/tencent/tbs/common/MTT/PluginItem:sPkgName	Ljava/lang/String;
    //   632: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   635: pop
    //   636: aload 7
    //   638: astore_2
    //   639: aload 13
    //   641: ldc_w 487
    //   644: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   647: pop
    //   648: aload 7
    //   650: astore_2
    //   651: aload 13
    //   653: aload 10
    //   655: invokevirtual 287	java/util/ArrayList:size	()I
    //   658: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   661: pop
    //   662: aload 7
    //   664: astore_2
    //   665: aload 12
    //   667: aload 13
    //   669: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   672: invokestatic 496	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   675: aload 7
    //   677: astore_2
    //   678: aload 10
    //   680: aload 11
    //   682: getfield 299	com/tencent/tbs/common/MTT/PluginItem:sPkgName	Ljava/lang/String;
    //   685: invokevirtual 841	java/util/ArrayList:remove	(Ljava/lang/Object;)Z
    //   688: pop
    //   689: aload 7
    //   691: astore_2
    //   692: aload_0
    //   693: aload 11
    //   695: iload_3
    //   696: invokevirtual 845	com/tencent/common/plugin/QBPluginDBHelper:insertSinglePluginSqlite	(Lcom/tencent/tbs/common/MTT/PluginItem;I)Z
    //   699: ifne +139 -> 838
    //   702: aload 7
    //   704: astore_2
    //   705: aload_0
    //   706: getfield 142	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   709: astore 12
    //   711: aload 7
    //   713: astore_2
    //   714: new 215	java/lang/StringBuilder
    //   717: dup
    //   718: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   721: astore 13
    //   723: aload 7
    //   725: astore_2
    //   726: aload 13
    //   728: ldc_w 847
    //   731: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   734: pop
    //   735: aload 7
    //   737: astore_2
    //   738: aload 13
    //   740: aload 11
    //   742: getfield 299	com/tencent/tbs/common/MTT/PluginItem:sPkgName	Ljava/lang/String;
    //   745: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   748: pop
    //   749: aload 7
    //   751: astore_2
    //   752: aload 13
    //   754: ldc_w 550
    //   757: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   760: pop
    //   761: aload 7
    //   763: astore_2
    //   764: aload 13
    //   766: iload_3
    //   767: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   770: pop
    //   771: aload 7
    //   773: astore_2
    //   774: aload 13
    //   776: ldc_w 849
    //   779: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   782: pop
    //   783: aload 7
    //   785: astore_2
    //   786: aload 12
    //   788: aload 13
    //   790: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   793: invokestatic 496	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   796: aload 7
    //   798: astore_2
    //   799: aload 8
    //   801: invokeinterface 746 1 0
    //   806: aload 7
    //   808: astore_2
    //   809: iconst_5
    //   810: iconst_2
    //   811: anewarray 4	java/lang/Object
    //   814: dup
    //   815: iconst_0
    //   816: iconst_3
    //   817: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   820: aastore
    //   821: dup
    //   822: iconst_1
    //   823: aload 11
    //   825: getfield 299	com/tencent/tbs/common/MTT/PluginItem:sPkgName	Ljava/lang/String;
    //   828: aastore
    //   829: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   832: iconst_0
    //   833: istore 5
    //   835: goto +973 -> 1808
    //   838: aload 7
    //   840: astore_2
    //   841: aload_0
    //   842: getfield 142	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   845: astore 12
    //   847: aload 7
    //   849: astore_2
    //   850: new 215	java/lang/StringBuilder
    //   853: dup
    //   854: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   857: astore 13
    //   859: aload 7
    //   861: astore_2
    //   862: aload 13
    //   864: ldc_w 847
    //   867: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   870: pop
    //   871: aload 7
    //   873: astore_2
    //   874: aload 13
    //   876: aload 11
    //   878: getfield 299	com/tencent/tbs/common/MTT/PluginItem:sPkgName	Ljava/lang/String;
    //   881: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   884: pop
    //   885: aload 7
    //   887: astore_2
    //   888: aload 13
    //   890: ldc_w 550
    //   893: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   896: pop
    //   897: aload 7
    //   899: astore_2
    //   900: aload 13
    //   902: iload_3
    //   903: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   906: pop
    //   907: aload 7
    //   909: astore_2
    //   910: aload 13
    //   912: ldc_w 851
    //   915: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   918: pop
    //   919: aload 7
    //   921: astore_2
    //   922: aload 12
    //   924: aload 13
    //   926: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   929: invokestatic 496	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   932: goto +876 -> 1808
    //   935: aload 7
    //   937: astore_2
    //   938: aload 10
    //   940: invokevirtual 834	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   943: astore 8
    //   945: aload 7
    //   947: astore_2
    //   948: iconst_5
    //   949: iconst_2
    //   950: anewarray 4	java/lang/Object
    //   953: dup
    //   954: iconst_0
    //   955: iconst_4
    //   956: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   959: aastore
    //   960: dup
    //   961: iconst_1
    //   962: aload 10
    //   964: invokevirtual 287	java/util/ArrayList:size	()I
    //   967: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   970: aastore
    //   971: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   974: aload 7
    //   976: astore_2
    //   977: aload 8
    //   979: invokeinterface 207 1 0
    //   984: ifeq +252 -> 1236
    //   987: aload 7
    //   989: astore_2
    //   990: aload 8
    //   992: invokeinterface 211 1 0
    //   997: checkcast 227	java/lang/String
    //   1000: astore 11
    //   1002: aload 7
    //   1004: astore_2
    //   1005: aload_0
    //   1006: aload 11
    //   1008: iload_3
    //   1009: invokevirtual 853	com/tencent/common/plugin/QBPluginDBHelper:deleteSinglePluginSqlite	(Ljava/lang/String;I)Z
    //   1012: ifne +130 -> 1142
    //   1015: aload 7
    //   1017: astore_2
    //   1018: aload_0
    //   1019: getfield 142	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1022: astore 12
    //   1024: aload 7
    //   1026: astore_2
    //   1027: new 215	java/lang/StringBuilder
    //   1030: dup
    //   1031: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   1034: astore 13
    //   1036: aload 7
    //   1038: astore_2
    //   1039: aload 13
    //   1041: ldc_w 855
    //   1044: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1047: pop
    //   1048: aload 7
    //   1050: astore_2
    //   1051: aload 13
    //   1053: aload 11
    //   1055: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1058: pop
    //   1059: aload 7
    //   1061: astore_2
    //   1062: aload 13
    //   1064: ldc_w 550
    //   1067: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1070: pop
    //   1071: aload 7
    //   1073: astore_2
    //   1074: aload 13
    //   1076: iload_3
    //   1077: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1080: pop
    //   1081: aload 7
    //   1083: astore_2
    //   1084: aload 13
    //   1086: ldc_w 849
    //   1089: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1092: pop
    //   1093: aload 7
    //   1095: astore_2
    //   1096: aload 12
    //   1098: aload 13
    //   1100: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1103: invokestatic 496	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   1106: aload 7
    //   1108: astore_2
    //   1109: aload 8
    //   1111: invokeinterface 746 1 0
    //   1116: aload 7
    //   1118: astore_2
    //   1119: iconst_5
    //   1120: iconst_2
    //   1121: anewarray 4	java/lang/Object
    //   1124: dup
    //   1125: iconst_0
    //   1126: iconst_5
    //   1127: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1130: aastore
    //   1131: dup
    //   1132: iconst_1
    //   1133: aload 11
    //   1135: aastore
    //   1136: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   1139: goto -165 -> 974
    //   1142: aload 7
    //   1144: astore_2
    //   1145: aload_0
    //   1146: getfield 142	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1149: astore 12
    //   1151: aload 7
    //   1153: astore_2
    //   1154: new 215	java/lang/StringBuilder
    //   1157: dup
    //   1158: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   1161: astore 13
    //   1163: aload 7
    //   1165: astore_2
    //   1166: aload 13
    //   1168: ldc_w 855
    //   1171: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1174: pop
    //   1175: aload 7
    //   1177: astore_2
    //   1178: aload 13
    //   1180: aload 11
    //   1182: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1185: pop
    //   1186: aload 7
    //   1188: astore_2
    //   1189: aload 13
    //   1191: ldc_w 550
    //   1194: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1197: pop
    //   1198: aload 7
    //   1200: astore_2
    //   1201: aload 13
    //   1203: iload_3
    //   1204: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1207: pop
    //   1208: aload 7
    //   1210: astore_2
    //   1211: aload 13
    //   1213: ldc_w 851
    //   1216: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1219: pop
    //   1220: aload 7
    //   1222: astore_2
    //   1223: aload 12
    //   1225: aload 13
    //   1227: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1230: invokestatic 496	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   1233: goto -259 -> 974
    //   1236: aload 7
    //   1238: astore_2
    //   1239: aload 7
    //   1241: invokevirtual 645	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   1244: iload 5
    //   1246: istore 6
    //   1248: aload 7
    //   1250: ifnull +167 -> 1417
    //   1253: aload 7
    //   1255: invokevirtual 632	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   1258: iload 5
    //   1260: istore 6
    //   1262: goto +155 -> 1417
    //   1265: astore_2
    //   1266: aload_2
    //   1267: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   1270: iconst_0
    //   1271: istore 6
    //   1273: goto +144 -> 1417
    //   1276: astore 8
    //   1278: goto +14 -> 1292
    //   1281: astore_1
    //   1282: aconst_null
    //   1283: astore_2
    //   1284: goto +443 -> 1727
    //   1287: astore 8
    //   1289: aconst_null
    //   1290: astore 7
    //   1292: aload 7
    //   1294: astore_2
    //   1295: iload_3
    //   1296: invokestatic 764	com/tencent/common/plugin/PluginStatBehavior:getOpTyepPluginList	(I)I
    //   1299: istore 4
    //   1301: aload 7
    //   1303: astore_2
    //   1304: new 215	java/lang/StringBuilder
    //   1307: dup
    //   1308: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   1311: astore 11
    //   1313: aload 7
    //   1315: astore_2
    //   1316: aload 11
    //   1318: ldc_w 857
    //   1321: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1324: pop
    //   1325: aload 7
    //   1327: astore_2
    //   1328: aload 11
    //   1330: aload 8
    //   1332: invokevirtual 778	java/lang/Exception:toString	()Ljava/lang/String;
    //   1335: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1338: pop
    //   1339: aload 7
    //   1341: astore_2
    //   1342: aload 11
    //   1344: ldc_w 489
    //   1347: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1350: pop
    //   1351: aload 7
    //   1353: astore_2
    //   1354: ldc_w 768
    //   1357: iload 4
    //   1359: aload 11
    //   1361: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1364: invokestatic 515	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;ILjava/lang/String;)V
    //   1367: aload 7
    //   1369: astore_2
    //   1370: iconst_5
    //   1371: iconst_2
    //   1372: anewarray 4	java/lang/Object
    //   1375: dup
    //   1376: iconst_0
    //   1377: bipush 6
    //   1379: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1382: aastore
    //   1383: dup
    //   1384: iconst_1
    //   1385: aload 8
    //   1387: invokevirtual 561	java/lang/Object:getClass	()Ljava/lang/Class;
    //   1390: invokevirtual 566	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   1393: aastore
    //   1394: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   1397: aload 7
    //   1399: ifnull +15 -> 1414
    //   1402: aload 7
    //   1404: invokevirtual 632	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   1407: goto +7 -> 1414
    //   1410: astore_2
    //   1411: goto -145 -> 1266
    //   1414: iconst_0
    //   1415: istore 6
    //   1417: aload 9
    //   1419: monitorexit
    //   1420: aload_0
    //   1421: getfield 153	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   1424: astore_2
    //   1425: aload_2
    //   1426: monitorenter
    //   1427: iconst_5
    //   1428: iconst_2
    //   1429: anewarray 4	java/lang/Object
    //   1432: dup
    //   1433: iconst_0
    //   1434: bipush 7
    //   1436: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1439: aastore
    //   1440: dup
    //   1441: iconst_1
    //   1442: aload_0
    //   1443: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   1446: invokeinterface 527 1 0
    //   1451: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1454: aastore
    //   1455: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   1458: aload_0
    //   1459: getfield 151	com/tencent/common/plugin/QBPluginDBHelper:mPluginDatasInited	Z
    //   1462: ifeq +169 -> 1631
    //   1465: aload_1
    //   1466: getfield 820	com/tencent/tbs/common/MTT/UniPluginRsp:vPluginList	Ljava/util/ArrayList;
    //   1469: invokevirtual 834	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   1472: astore_1
    //   1473: aload_1
    //   1474: invokeinterface 207 1 0
    //   1479: ifeq +20 -> 1499
    //   1482: aload_0
    //   1483: aload_1
    //   1484: invokeinterface 211 1 0
    //   1489: checkcast 276	com/tencent/tbs/common/MTT/PluginItem
    //   1492: iload_3
    //   1493: invokespecial 859	com/tencent/common/plugin/QBPluginDBHelper:a	(Lcom/tencent/tbs/common/MTT/PluginItem;I)V
    //   1496: goto -23 -> 1473
    //   1499: aload 10
    //   1501: invokevirtual 834	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   1504: astore_1
    //   1505: aload_1
    //   1506: invokeinterface 207 1 0
    //   1511: ifeq +86 -> 1597
    //   1514: aload_1
    //   1515: invokeinterface 211 1 0
    //   1520: checkcast 227	java/lang/String
    //   1523: astore 7
    //   1525: aload_0
    //   1526: getfield 142	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1529: astore 8
    //   1531: new 215	java/lang/StringBuilder
    //   1534: dup
    //   1535: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   1538: astore 9
    //   1540: aload 9
    //   1542: ldc_w 861
    //   1545: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1548: pop
    //   1549: aload 9
    //   1551: aload 7
    //   1553: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1556: pop
    //   1557: aload 9
    //   1559: ldc_w 487
    //   1562: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1565: pop
    //   1566: aload 9
    //   1568: aload 10
    //   1570: invokevirtual 287	java/util/ArrayList:size	()I
    //   1573: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1576: pop
    //   1577: aload 8
    //   1579: aload 9
    //   1581: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1584: invokestatic 496	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   1587: aload_0
    //   1588: aload 7
    //   1590: iload_3
    //   1591: invokevirtual 863	com/tencent/common/plugin/QBPluginDBHelper:deleteSinglePluginMem	(Ljava/lang/String;I)V
    //   1594: goto -89 -> 1505
    //   1597: iconst_5
    //   1598: iconst_2
    //   1599: anewarray 4	java/lang/Object
    //   1602: dup
    //   1603: iconst_0
    //   1604: bipush 8
    //   1606: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1609: aastore
    //   1610: dup
    //   1611: iconst_1
    //   1612: aload_0
    //   1613: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   1616: invokeinterface 527 1 0
    //   1621: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1624: aastore
    //   1625: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   1628: goto +88 -> 1716
    //   1631: iload 6
    //   1633: ifeq +11 -> 1644
    //   1636: aload_0
    //   1637: aload_0
    //   1638: getfield 157	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   1641: invokespecial 688	com/tencent/common/plugin/QBPluginDBHelper:a	(Landroid/content/Context;)V
    //   1644: iload_3
    //   1645: invokestatic 764	com/tencent/common/plugin/PluginStatBehavior:getOpTyepPluginList	(I)I
    //   1648: istore_3
    //   1649: new 215	java/lang/StringBuilder
    //   1652: dup
    //   1653: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   1656: astore_1
    //   1657: aload_1
    //   1658: ldc_w 865
    //   1661: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1664: pop
    //   1665: aload_1
    //   1666: aload_0
    //   1667: getfield 151	com/tencent/common/plugin/QBPluginDBHelper:mPluginDatasInited	Z
    //   1670: invokevirtual 868	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   1673: pop
    //   1674: ldc_w 768
    //   1677: iload_3
    //   1678: aload_1
    //   1679: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1682: invokestatic 515	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;ILjava/lang/String;)V
    //   1685: iconst_5
    //   1686: iconst_2
    //   1687: anewarray 4	java/lang/Object
    //   1690: dup
    //   1691: iconst_0
    //   1692: bipush 9
    //   1694: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1697: aastore
    //   1698: dup
    //   1699: iconst_1
    //   1700: aload_0
    //   1701: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   1704: invokeinterface 527 1 0
    //   1709: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1712: aastore
    //   1713: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   1716: aload_2
    //   1717: monitorexit
    //   1718: iload 6
    //   1720: ireturn
    //   1721: astore_1
    //   1722: aload_2
    //   1723: monitorexit
    //   1724: aload_1
    //   1725: athrow
    //   1726: astore_1
    //   1727: aload_2
    //   1728: ifnull +15 -> 1743
    //   1731: aload_2
    //   1732: invokevirtual 632	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   1735: goto +8 -> 1743
    //   1738: astore_2
    //   1739: aload_2
    //   1740: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   1743: aload_1
    //   1744: athrow
    //   1745: aload_2
    //   1746: ifnull +9 -> 1755
    //   1749: aload_2
    //   1750: invokeinterface 462 1 0
    //   1755: aload_1
    //   1756: athrow
    //   1757: astore_1
    //   1758: aload 9
    //   1760: monitorexit
    //   1761: aload_1
    //   1762: athrow
    //   1763: ldc_w 768
    //   1766: iload_3
    //   1767: invokestatic 764	com/tencent/common/plugin/PluginStatBehavior:getOpTyepPluginList	(I)I
    //   1770: sipush 318
    //   1773: invokestatic 518	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   1776: iconst_5
    //   1777: iconst_2
    //   1778: anewarray 4	java/lang/Object
    //   1781: dup
    //   1782: iconst_0
    //   1783: iconst_0
    //   1784: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1787: aastore
    //   1788: dup
    //   1789: iconst_1
    //   1790: aload_0
    //   1791: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   1794: invokeinterface 527 1 0
    //   1799: invokestatic 308	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1802: aastore
    //   1803: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   1806: iconst_0
    //   1807: ireturn
    //   1808: goto -1268 -> 540
    //   1811: astore_1
    //   1812: goto -67 -> 1745
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1815	0	this	QBPluginDBHelper
    //   0	1815	1	paramUniPluginRsp	com.tencent.tbs.common.MTT.UniPluginRsp
    //   0	1815	2	paramContext	Context
    //   0	1815	3	paramInt	int
    //   121	1237	4	i	int
    //   538	721	5	bool1	boolean
    //   1246	473	6	bool2	boolean
    //   84	278	7	localObject1	Object
    //   387	1	7	localException1	Exception
    //   398	37	7	localException2	Exception
    //   487	1102	7	localObject2	Object
    //   236	874	8	localObject3	Object
    //   1276	1	8	localException3	Exception
    //   1287	99	8	localException4	Exception
    //   1529	49	8	str	String
    //   227	1532	9	localObject4	Object
    //   221	1348	10	localArrayList	ArrayList
    //   301	1059	11	localObject5	Object
    //   310	914	12	localObject6	Object
    //   608	618	13	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   272	367	383	finally
    //   272	367	387	java/lang/Exception
    //   238	272	392	finally
    //   238	272	398	java/lang/Exception
    //   499	504	507	java/lang/Exception
    //   1253	1258	1265	java/lang/Exception
    //   520	525	1276	java/lang/Exception
    //   528	537	1276	java/lang/Exception
    //   543	553	1276	java/lang/Exception
    //   556	568	1276	java/lang/Exception
    //   576	589	1276	java/lang/Exception
    //   592	598	1276	java/lang/Exception
    //   601	610	1276	java/lang/Exception
    //   613	622	1276	java/lang/Exception
    //   625	636	1276	java/lang/Exception
    //   639	648	1276	java/lang/Exception
    //   651	662	1276	java/lang/Exception
    //   665	675	1276	java/lang/Exception
    //   678	689	1276	java/lang/Exception
    //   692	702	1276	java/lang/Exception
    //   705	711	1276	java/lang/Exception
    //   714	723	1276	java/lang/Exception
    //   726	735	1276	java/lang/Exception
    //   738	749	1276	java/lang/Exception
    //   752	761	1276	java/lang/Exception
    //   764	771	1276	java/lang/Exception
    //   774	783	1276	java/lang/Exception
    //   786	796	1276	java/lang/Exception
    //   799	806	1276	java/lang/Exception
    //   809	832	1276	java/lang/Exception
    //   841	847	1276	java/lang/Exception
    //   850	859	1276	java/lang/Exception
    //   862	871	1276	java/lang/Exception
    //   874	885	1276	java/lang/Exception
    //   888	897	1276	java/lang/Exception
    //   900	907	1276	java/lang/Exception
    //   910	919	1276	java/lang/Exception
    //   922	932	1276	java/lang/Exception
    //   938	945	1276	java/lang/Exception
    //   948	974	1276	java/lang/Exception
    //   977	987	1276	java/lang/Exception
    //   990	1002	1276	java/lang/Exception
    //   1005	1015	1276	java/lang/Exception
    //   1018	1024	1276	java/lang/Exception
    //   1027	1036	1276	java/lang/Exception
    //   1039	1048	1276	java/lang/Exception
    //   1051	1059	1276	java/lang/Exception
    //   1062	1071	1276	java/lang/Exception
    //   1074	1081	1276	java/lang/Exception
    //   1084	1093	1276	java/lang/Exception
    //   1096	1106	1276	java/lang/Exception
    //   1109	1116	1276	java/lang/Exception
    //   1119	1139	1276	java/lang/Exception
    //   1145	1151	1276	java/lang/Exception
    //   1154	1163	1276	java/lang/Exception
    //   1166	1175	1276	java/lang/Exception
    //   1178	1186	1276	java/lang/Exception
    //   1189	1198	1276	java/lang/Exception
    //   1201	1208	1276	java/lang/Exception
    //   1211	1220	1276	java/lang/Exception
    //   1223	1233	1276	java/lang/Exception
    //   1239	1244	1276	java/lang/Exception
    //   482	489	1281	finally
    //   482	489	1287	java/lang/Exception
    //   1402	1407	1410	java/lang/Exception
    //   1427	1473	1721	finally
    //   1473	1496	1721	finally
    //   1499	1505	1721	finally
    //   1505	1594	1721	finally
    //   1597	1628	1721	finally
    //   1636	1644	1721	finally
    //   1644	1716	1721	finally
    //   1716	1718	1721	finally
    //   1722	1724	1721	finally
    //   520	525	1726	finally
    //   528	537	1726	finally
    //   543	553	1726	finally
    //   556	568	1726	finally
    //   576	589	1726	finally
    //   592	598	1726	finally
    //   601	610	1726	finally
    //   613	622	1726	finally
    //   625	636	1726	finally
    //   639	648	1726	finally
    //   651	662	1726	finally
    //   665	675	1726	finally
    //   678	689	1726	finally
    //   692	702	1726	finally
    //   705	711	1726	finally
    //   714	723	1726	finally
    //   726	735	1726	finally
    //   738	749	1726	finally
    //   752	761	1726	finally
    //   764	771	1726	finally
    //   774	783	1726	finally
    //   786	796	1726	finally
    //   799	806	1726	finally
    //   809	832	1726	finally
    //   841	847	1726	finally
    //   850	859	1726	finally
    //   862	871	1726	finally
    //   874	885	1726	finally
    //   888	897	1726	finally
    //   900	907	1726	finally
    //   910	919	1726	finally
    //   922	932	1726	finally
    //   938	945	1726	finally
    //   948	974	1726	finally
    //   977	987	1726	finally
    //   990	1002	1726	finally
    //   1005	1015	1726	finally
    //   1018	1024	1726	finally
    //   1027	1036	1726	finally
    //   1039	1048	1726	finally
    //   1051	1059	1726	finally
    //   1062	1071	1726	finally
    //   1074	1081	1726	finally
    //   1084	1093	1726	finally
    //   1096	1106	1726	finally
    //   1109	1116	1726	finally
    //   1119	1139	1726	finally
    //   1145	1151	1726	finally
    //   1154	1163	1726	finally
    //   1166	1175	1726	finally
    //   1178	1186	1726	finally
    //   1189	1198	1726	finally
    //   1201	1208	1726	finally
    //   1211	1220	1726	finally
    //   1223	1233	1726	finally
    //   1239	1244	1726	finally
    //   1295	1301	1726	finally
    //   1304	1313	1726	finally
    //   1316	1325	1726	finally
    //   1328	1339	1726	finally
    //   1342	1351	1726	finally
    //   1354	1367	1726	finally
    //   1370	1397	1726	finally
    //   1731	1735	1738	java/lang/Exception
    //   232	238	1757	finally
    //   374	380	1757	finally
    //   450	456	1757	finally
    //   456	482	1757	finally
    //   499	504	1757	finally
    //   508	512	1757	finally
    //   512	515	1757	finally
    //   1253	1258	1757	finally
    //   1266	1270	1757	finally
    //   1402	1407	1757	finally
    //   1417	1420	1757	finally
    //   1731	1735	1757	finally
    //   1739	1743	1757	finally
    //   1743	1745	1757	finally
    //   1749	1755	1757	finally
    //   1755	1757	1757	finally
    //   1758	1761	1757	finally
    //   402	446	1811	finally
  }
  
  /* Error */
  public boolean insertSinglePluginSqlite(PluginItem paramPluginItem, int paramInt)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 7
    //   3: aconst_null
    //   4: astore 6
    //   6: aload 6
    //   8: astore 4
    //   10: aload 7
    //   12: astore 5
    //   14: aload_0
    //   15: getfield 144	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper	Lcom/tencent/common/plugin/DBHelper;
    //   18: astore 8
    //   20: aload 6
    //   22: astore 4
    //   24: aload 7
    //   26: astore 5
    //   28: aload_1
    //   29: getfield 299	com/tencent/tbs/common/MTT/PluginItem:sPkgName	Ljava/lang/String;
    //   32: astore 9
    //   34: aload 6
    //   36: astore 4
    //   38: aload 7
    //   40: astore 5
    //   42: new 215	java/lang/StringBuilder
    //   45: dup
    //   46: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   49: astore 10
    //   51: aload 6
    //   53: astore 4
    //   55: aload 7
    //   57: astore 5
    //   59: aload 10
    //   61: iload_2
    //   62: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   65: pop
    //   66: aload 6
    //   68: astore 4
    //   70: aload 7
    //   72: astore 5
    //   74: aload 10
    //   76: ldc_w 293
    //   79: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: pop
    //   83: aload 6
    //   85: astore 4
    //   87: aload 7
    //   89: astore 5
    //   91: aload 8
    //   93: iconst_0
    //   94: ldc 117
    //   96: ldc_w 870
    //   99: iconst_2
    //   100: anewarray 227	java/lang/String
    //   103: dup
    //   104: iconst_0
    //   105: aload 9
    //   107: aastore
    //   108: dup
    //   109: iconst_1
    //   110: aload 10
    //   112: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   115: aastore
    //   116: aconst_null
    //   117: aconst_null
    //   118: aconst_null
    //   119: aconst_null
    //   120: invokevirtual 796	com/tencent/common/plugin/DBHelper:query	(ZLjava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   123: astore 6
    //   125: aload 6
    //   127: ifnull +369 -> 496
    //   130: aload 6
    //   132: astore 4
    //   134: aload 6
    //   136: astore 5
    //   138: aload 6
    //   140: invokeinterface 799 1 0
    //   145: ifeq +351 -> 496
    //   148: iconst_1
    //   149: istore_3
    //   150: goto +3 -> 153
    //   153: aload 6
    //   155: astore 4
    //   157: aload 6
    //   159: astore 5
    //   161: aload_0
    //   162: aload_1
    //   163: iload_2
    //   164: invokespecial 872	com/tencent/common/plugin/QBPluginDBHelper:a	(Lcom/tencent/tbs/common/MTT/PluginItem;I)Landroid/content/ContentValues;
    //   167: astore 7
    //   169: iload_3
    //   170: ifeq +121 -> 291
    //   173: aload 6
    //   175: astore 4
    //   177: aload 6
    //   179: astore 5
    //   181: aload_0
    //   182: getfield 144	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper	Lcom/tencent/common/plugin/DBHelper;
    //   185: astore 8
    //   187: aload 6
    //   189: astore 4
    //   191: aload 6
    //   193: astore 5
    //   195: aload_1
    //   196: getfield 299	com/tencent/tbs/common/MTT/PluginItem:sPkgName	Ljava/lang/String;
    //   199: astore 9
    //   201: aload 6
    //   203: astore 4
    //   205: aload 6
    //   207: astore 5
    //   209: new 215	java/lang/StringBuilder
    //   212: dup
    //   213: invokespecial 216	java/lang/StringBuilder:<init>	()V
    //   216: astore 10
    //   218: aload 6
    //   220: astore 4
    //   222: aload 6
    //   224: astore 5
    //   226: aload 10
    //   228: iload_2
    //   229: invokevirtual 234	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   232: pop
    //   233: aload 6
    //   235: astore 4
    //   237: aload 6
    //   239: astore 5
    //   241: aload 10
    //   243: ldc_w 293
    //   246: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   249: pop
    //   250: aload 6
    //   252: astore 4
    //   254: aload 6
    //   256: astore 5
    //   258: aload 8
    //   260: ldc 117
    //   262: aload 7
    //   264: ldc_w 870
    //   267: iconst_2
    //   268: anewarray 227	java/lang/String
    //   271: dup
    //   272: iconst_0
    //   273: aload 9
    //   275: aastore
    //   276: dup
    //   277: iconst_1
    //   278: aload 10
    //   280: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   283: aastore
    //   284: invokevirtual 875	com/tencent/common/plugin/DBHelper:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   287: pop
    //   288: goto +23 -> 311
    //   291: aload 6
    //   293: astore 4
    //   295: aload 6
    //   297: astore 5
    //   299: aload_0
    //   300: getfield 144	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_ComTencentCommonPluginDBHelper	Lcom/tencent/common/plugin/DBHelper;
    //   303: ldc 117
    //   305: aload 7
    //   307: invokevirtual 640	com/tencent/common/plugin/DBHelper:insert	(Ljava/lang/String;Landroid/content/ContentValues;)I
    //   310: pop
    //   311: aload 6
    //   313: ifnull +10 -> 323
    //   316: aload 6
    //   318: invokeinterface 462 1 0
    //   323: iconst_1
    //   324: ireturn
    //   325: astore_1
    //   326: goto +156 -> 482
    //   329: astore 6
    //   331: aload 5
    //   333: astore 4
    //   335: aload 6
    //   337: invokevirtual 444	java/lang/Exception:printStackTrace	()V
    //   340: aload 5
    //   342: astore 4
    //   344: aload_1
    //   345: getfield 299	com/tencent/tbs/common/MTT/PluginItem:sPkgName	Ljava/lang/String;
    //   348: astore 7
    //   350: aload 5
    //   352: astore 4
    //   354: aload_1
    //   355: getfield 299	com/tencent/tbs/common/MTT/PluginItem:sPkgName	Ljava/lang/String;
    //   358: astore_1
    //   359: aload 5
    //   361: astore 4
    //   363: aload 7
    //   365: ldc_w 757
    //   368: invokevirtual 761	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   371: ifnull +28 -> 399
    //   374: aload 5
    //   376: astore 4
    //   378: aload 7
    //   380: ldc_w 757
    //   383: invokevirtual 761	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   386: aload 7
    //   388: ldc_w 757
    //   391: invokevirtual 761	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   394: arraylength
    //   395: iconst_1
    //   396: isub
    //   397: aaload
    //   398: astore_1
    //   399: aload 5
    //   401: astore 4
    //   403: ldc_w 768
    //   406: iload_2
    //   407: invokestatic 764	com/tencent/common/plugin/PluginStatBehavior:getOpTyepPluginList	(I)I
    //   410: aload_1
    //   411: invokestatic 515	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;ILjava/lang/String;)V
    //   414: aload 5
    //   416: astore 4
    //   418: bipush 6
    //   420: iconst_2
    //   421: anewarray 4	java/lang/Object
    //   424: dup
    //   425: iconst_0
    //   426: aload_1
    //   427: aastore
    //   428: dup
    //   429: iconst_1
    //   430: aload 6
    //   432: invokevirtual 561	java/lang/Object:getClass	()Ljava/lang/Class;
    //   435: invokevirtual 566	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   438: aastore
    //   439: invokestatic 529	com/tencent/common/plugin/QBPluginDBHelper:a	(I[Ljava/lang/Object;)V
    //   442: aload 5
    //   444: astore 4
    //   446: iconst_1
    //   447: invokestatic 175	com/tencent/common/plugin/QBPluginServiceImpl:getPluginRelateFunc	(I)Lcom/tencent/common/plugin/QBPluginServiceImpl$IPluginRelateFunc;
    //   450: ifnull +18 -> 468
    //   453: aload 5
    //   455: astore 4
    //   457: iconst_1
    //   458: invokestatic 175	com/tencent/common/plugin/QBPluginServiceImpl:getPluginRelateFunc	(I)Lcom/tencent/common/plugin/QBPluginServiceImpl$IPluginRelateFunc;
    //   461: ldc -79
    //   463: invokeinterface 183 2 0
    //   468: aload 5
    //   470: ifnull +10 -> 480
    //   473: aload 5
    //   475: invokeinterface 462 1 0
    //   480: iconst_0
    //   481: ireturn
    //   482: aload 4
    //   484: ifnull +10 -> 494
    //   487: aload 4
    //   489: invokeinterface 462 1 0
    //   494: aload_1
    //   495: athrow
    //   496: iconst_0
    //   497: istore_3
    //   498: goto -345 -> 153
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	501	0	this	QBPluginDBHelper
    //   0	501	1	paramPluginItem	PluginItem
    //   0	501	2	paramInt	int
    //   149	349	3	i	int
    //   8	480	4	localObject1	Object
    //   12	462	5	localObject2	Object
    //   4	313	6	localCursor	Cursor
    //   329	102	6	localException	Exception
    //   1	386	7	localObject3	Object
    //   18	241	8	localDBHelper	DBHelper
    //   32	242	9	str	String
    //   49	230	10	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   14	20	325	finally
    //   28	34	325	finally
    //   42	51	325	finally
    //   59	66	325	finally
    //   74	83	325	finally
    //   91	125	325	finally
    //   138	148	325	finally
    //   161	169	325	finally
    //   181	187	325	finally
    //   195	201	325	finally
    //   209	218	325	finally
    //   226	233	325	finally
    //   241	250	325	finally
    //   258	288	325	finally
    //   299	311	325	finally
    //   335	340	325	finally
    //   344	350	325	finally
    //   354	359	325	finally
    //   363	374	325	finally
    //   378	399	325	finally
    //   403	414	325	finally
    //   418	442	325	finally
    //   446	453	325	finally
    //   457	468	325	finally
    //   14	20	329	java/lang/Exception
    //   28	34	329	java/lang/Exception
    //   42	51	329	java/lang/Exception
    //   59	66	329	java/lang/Exception
    //   74	83	329	java/lang/Exception
    //   91	125	329	java/lang/Exception
    //   138	148	329	java/lang/Exception
    //   161	169	329	java/lang/Exception
    //   181	187	329	java/lang/Exception
    //   195	201	329	java/lang/Exception
    //   209	218	329	java/lang/Exception
    //   226	233	329	java/lang/Exception
    //   241	250	329	java/lang/Exception
    //   258	288	329	java/lang/Exception
    //   299	311	329	java/lang/Exception
  }
  
  public boolean setIsPluginUpdate(String paramString, boolean paramBoolean, int paramInt)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public boolean setPluginHijackUrl(String paramString1, String paramString2, int paramInt)
  {
    if (TextUtils.isEmpty(paramString2)) {
      return false;
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext())
      {
        QBPluginItemInfo localQBPluginItemInfo = (QBPluginItemInfo)localIterator.next();
        if ((localQBPluginItemInfo != null) && (localQBPluginItemInfo.mPackageName.equalsIgnoreCase(paramString1)) && (localQBPluginItemInfo.mInfoFrom == paramInt)) {
          localQBPluginItemInfo.mAntiHijackUrl = paramString2;
        }
      }
      a(paramString1, "antihijackurl", paramString2, paramInt);
      return true;
    }
  }
  
  public boolean setPluginUrl(String paramString1, String paramString2, int paramInt)
  {
    if (TextUtils.isEmpty(paramString2)) {
      return false;
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext())
      {
        QBPluginItemInfo localQBPluginItemInfo = (QBPluginItemInfo)localIterator.next();
        if ((localQBPluginItemInfo != null) && (localQBPluginItemInfo.mPackageName.equalsIgnoreCase(paramString1)) && (localQBPluginItemInfo.mInfoFrom == paramInt)) {
          localQBPluginItemInfo.mUrl = paramString2;
        }
      }
      a(paramString1, "url", paramString2, paramInt);
      return true;
    }
  }
  
  public void updatePluginCompatId(Map<String, String> paramMap, int paramInt)
  {
    HashMap localHashMap = new HashMap();
    Object localObject2;
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      Object localObject3 = this.jdField_a_of_type_JavaUtilList.iterator();
      Object localObject5;
      while (((Iterator)localObject3).hasNext())
      {
        localObject4 = (QBPluginItemInfo)((Iterator)localObject3).next();
        if ((localObject4 != null) && (!TextUtils.isEmpty(((QBPluginItemInfo)localObject4).mPackageName)) && (((QBPluginItemInfo)localObject4).mInfoFrom == paramInt))
        {
          localObject5 = (String)paramMap.get(((QBPluginItemInfo)localObject4).mPackageName);
          if (localObject5 != null)
          {
            ((QBPluginItemInfo)localObject4).mPluginCompatiID = ((String)localObject5);
            localHashMap.put(((QBPluginItemInfo)localObject4).mPackageName, localObject5);
          }
        }
      }
      if (localHashMap.isEmpty())
      {
        paramInt = PluginStatBehavior.getOpTyepPluginList(paramInt);
        ??? = new StringBuilder();
        ((StringBuilder)???).append("333(1_");
        ((StringBuilder)???).append(paramMap.size());
        ((StringBuilder)???).append("_0_0)");
        PluginStatBehavior.addLogPath("PluginList", paramInt, ((StringBuilder)???).toString());
        return;
      }
      Object localObject4 = "batch";
      int j;
      Iterator localIterator;
      try
      {
        j = a(localHashMap, "extString3", paramInt);
        localObject3 = null;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        localObject5 = "each";
        localIterator = localHashMap.entrySet().iterator();
        i = 0;
      }
      for (;;)
      {
        j = i;
        localObject4 = localObject5;
        localObject3 = localException;
        if (!localIterator.hasNext()) {
          break;
        }
        localObject3 = (Map.Entry)localIterator.next();
        if (b((String)((Map.Entry)localObject3).getKey(), "extString3", (String)((Map.Entry)localObject3).getValue(), paramInt))
        {
          i += 1;
        }
        else
        {
          localObject3 = this.mLastException;
          if (localObject3 != null) {
            localObject2 = localObject3;
          }
        }
      }
      int i = PluginStatBehavior.getOpTyepPluginList(paramInt);
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("333(1_");
      ((StringBuilder)localObject2).append(paramMap.size());
      ((StringBuilder)localObject2).append("_");
      ((StringBuilder)localObject2).append(localHashMap.size());
      ((StringBuilder)localObject2).append("_");
      ((StringBuilder)localObject2).append((String)localObject4);
      ((StringBuilder)localObject2).append("_");
      ((StringBuilder)localObject2).append(j);
      ((StringBuilder)localObject2).append(")");
      PluginStatBehavior.addLogPath("PluginList", i, ((StringBuilder)localObject2).toString());
      if (paramMap.size() > localHashMap.size())
      {
        localObject2 = new StringBuilder();
        paramMap = new HashSet(paramMap.keySet());
        paramMap.removeAll(localHashMap.keySet());
        localObject4 = new String[8];
        localObject4[0] = "com.tencent.mtt.external.";
        localObject4[1] = "com.tencent.mtt.";
        localObject4[2] = "com.tencent.qb.plugin.";
        localObject4[3] = "com.tencent.qb.";
        localObject4[4] = "com.tencent.";
        localObject4[5] = ".wxapkg";
        localObject4[6] = "com.qq.wx.";
        localObject4[7] = "com.coloros.";
        localObject5 = paramMap.iterator();
        while (((Iterator)localObject5).hasNext())
        {
          paramMap = (String)((Iterator)localObject5).next();
          j = localObject4.length;
          i = 0;
          while (i < j)
          {
            paramMap = paramMap.replace(localObject4[i], "");
            i += 1;
          }
          ((StringBuilder)localObject2).append("_");
          ((StringBuilder)localObject2).append(paramMap);
        }
        i = PluginStatBehavior.getOpTyepPluginList(paramInt);
        paramMap = new StringBuilder();
        paramMap.append("333(2");
        paramMap.append(((StringBuilder)localObject2).toString());
        paramMap.append(")");
        PluginStatBehavior.addLogPath("PluginList", i, paramMap.toString());
      }
      if (localObject3 != null)
      {
        paramInt = PluginStatBehavior.getOpTyepPluginList(paramInt);
        paramMap = new StringBuilder();
        paramMap.append("333(3_");
        paramMap.append(((Exception)localObject3).toString());
        paramMap.append(")");
        PluginStatBehavior.addLogPath("PluginList", paramInt, paramMap.toString());
      }
      return;
    }
  }
  
  public void updatePluginDownloadDir(String paramString1, String paramString2, int paramInt)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return;
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext())
      {
        QBPluginItemInfo localQBPluginItemInfo = (QBPluginItemInfo)localIterator.next();
        if ((localQBPluginItemInfo != null) && (paramString1.equalsIgnoreCase(localQBPluginItemInfo.mPackageName)) && (localQBPluginItemInfo.mInfoFrom == paramInt)) {
          localQBPluginItemInfo.mDownloadDir = paramString2;
        }
      }
      a(paramString1, "downPath", paramString2, paramInt);
      return;
    }
  }
  
  /* Error */
  public boolean updatePluginDownloadFileName(String paramString1, String paramString2, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokestatic 344	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   6: istore 4
    //   8: iload 4
    //   10: ifeq +7 -> 17
    //   13: aload_0
    //   14: monitorexit
    //   15: iconst_0
    //   16: ireturn
    //   17: aload_0
    //   18: getfield 153	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   21: astore 5
    //   23: aload 5
    //   25: monitorenter
    //   26: aload_0
    //   27: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   30: invokeinterface 597 1 0
    //   35: astore 6
    //   37: aload 6
    //   39: invokeinterface 207 1 0
    //   44: ifeq +50 -> 94
    //   47: aload 6
    //   49: invokeinterface 211 1 0
    //   54: checkcast 348	com/tencent/common/plugin/QBPluginItemInfo
    //   57: astore 7
    //   59: aload 7
    //   61: ifnull -24 -> 37
    //   64: aload_1
    //   65: aload 7
    //   67: getfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   70: invokevirtual 477	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   73: ifeq -36 -> 37
    //   76: aload 7
    //   78: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   81: iload_3
    //   82: if_icmpne -45 -> 37
    //   85: aload 7
    //   87: aload_2
    //   88: putfield 429	com/tencent/common/plugin/QBPluginItemInfo:mDownloadFileName	Ljava/lang/String;
    //   91: goto -54 -> 37
    //   94: aload 5
    //   96: monitorexit
    //   97: aload_0
    //   98: aload_1
    //   99: ldc 102
    //   101: aload_2
    //   102: iload_3
    //   103: invokespecial 880	com/tencent/common/plugin/QBPluginDBHelper:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)Z
    //   106: pop
    //   107: aload_0
    //   108: monitorexit
    //   109: iconst_1
    //   110: ireturn
    //   111: astore_1
    //   112: aload 5
    //   114: monitorexit
    //   115: aload_1
    //   116: athrow
    //   117: astore_1
    //   118: aload_0
    //   119: monitorexit
    //   120: aload_1
    //   121: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	122	0	this	QBPluginDBHelper
    //   0	122	1	paramString1	String
    //   0	122	2	paramString2	String
    //   0	122	3	paramInt	int
    //   6	3	4	bool	boolean
    //   35	13	6	localIterator	Iterator
    //   57	29	7	localQBPluginItemInfo	QBPluginItemInfo
    // Exception table:
    //   from	to	target	type
    //   26	37	111	finally
    //   37	59	111	finally
    //   64	91	111	finally
    //   94	97	111	finally
    //   112	115	111	finally
    //   2	8	117	finally
    //   17	26	117	finally
    //   97	107	117	finally
    //   115	117	117	finally
  }
  
  public void updatePluginInstallDir(String paramString1, String paramString2, int paramInt)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return;
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext())
      {
        QBPluginItemInfo localQBPluginItemInfo = (QBPluginItemInfo)localIterator.next();
        if ((localQBPluginItemInfo != null) && (paramString1.equalsIgnoreCase(localQBPluginItemInfo.mPackageName)) && (localQBPluginItemInfo.mInfoFrom == paramInt)) {
          localQBPluginItemInfo.mInstallDir = paramString2;
        }
      }
      a(paramString1, "installPath", paramString2, paramInt);
      return;
    }
  }
  
  /* Error */
  public boolean updatePluginInstallVersion(String paramString1, String paramString2, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokestatic 344	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   6: istore 4
    //   8: iload 4
    //   10: ifeq +7 -> 17
    //   13: aload_0
    //   14: monitorexit
    //   15: iconst_0
    //   16: ireturn
    //   17: aload_0
    //   18: getfield 153	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   21: astore 5
    //   23: aload 5
    //   25: monitorenter
    //   26: aload_0
    //   27: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   30: invokeinterface 597 1 0
    //   35: astore 6
    //   37: aload 6
    //   39: invokeinterface 207 1 0
    //   44: ifeq +50 -> 94
    //   47: aload 6
    //   49: invokeinterface 211 1 0
    //   54: checkcast 348	com/tencent/common/plugin/QBPluginItemInfo
    //   57: astore 7
    //   59: aload 7
    //   61: ifnull -24 -> 37
    //   64: aload_1
    //   65: aload 7
    //   67: getfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   70: invokevirtual 477	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   73: ifeq -36 -> 37
    //   76: aload 7
    //   78: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   81: iload_3
    //   82: if_icmpne -45 -> 37
    //   85: aload 7
    //   87: aload_2
    //   88: putfield 432	com/tencent/common/plugin/QBPluginItemInfo:mInstallVersion	Ljava/lang/String;
    //   91: goto -54 -> 37
    //   94: aload 5
    //   96: monitorexit
    //   97: aload_0
    //   98: aload_1
    //   99: ldc 51
    //   101: aload_2
    //   102: iload_3
    //   103: invokespecial 880	com/tencent/common/plugin/QBPluginDBHelper:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)Z
    //   106: pop
    //   107: aload_0
    //   108: monitorexit
    //   109: iconst_1
    //   110: ireturn
    //   111: astore_1
    //   112: aload 5
    //   114: monitorexit
    //   115: aload_1
    //   116: athrow
    //   117: astore_1
    //   118: aload_0
    //   119: monitorexit
    //   120: aload_1
    //   121: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	122	0	this	QBPluginDBHelper
    //   0	122	1	paramString1	String
    //   0	122	2	paramString2	String
    //   0	122	3	paramInt	int
    //   6	3	4	bool	boolean
    //   35	13	6	localIterator	Iterator
    //   57	29	7	localQBPluginItemInfo	QBPluginItemInfo
    // Exception table:
    //   from	to	target	type
    //   26	37	111	finally
    //   37	59	111	finally
    //   64	91	111	finally
    //   94	97	111	finally
    //   112	115	111	finally
    //   2	8	117	finally
    //   17	26	117	finally
    //   97	107	117	finally
    //   115	117	117	finally
  }
  
  public void updatePluginIsZipFileUpdatel(String paramString, int paramInt1, int paramInt2)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext())
      {
        QBPluginItemInfo localQBPluginItemInfo = (QBPluginItemInfo)localIterator.next();
        if ((localQBPluginItemInfo != null) && (paramString.equalsIgnoreCase(localQBPluginItemInfo.mPackageName)) && (localQBPluginItemInfo.mInfoFrom == paramInt2)) {
          localQBPluginItemInfo.mIsZipFileUpdate = paramInt1;
        }
      }
      a(paramString, "isZipFileUpdate", paramInt1, paramInt2);
      return;
    }
  }
  
  /* Error */
  public boolean updatePluginNeesUpdateFlag(String paramString, int paramInt1, int paramInt2)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokestatic 344	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   6: istore 4
    //   8: iload 4
    //   10: ifeq +7 -> 17
    //   13: aload_0
    //   14: monitorexit
    //   15: iconst_0
    //   16: ireturn
    //   17: aload_0
    //   18: getfield 153	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   21: astore 5
    //   23: aload 5
    //   25: monitorenter
    //   26: aload_0
    //   27: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   30: invokeinterface 597 1 0
    //   35: astore 6
    //   37: aload 6
    //   39: invokeinterface 207 1 0
    //   44: ifeq +50 -> 94
    //   47: aload 6
    //   49: invokeinterface 211 1 0
    //   54: checkcast 348	com/tencent/common/plugin/QBPluginItemInfo
    //   57: astore 7
    //   59: aload 7
    //   61: ifnull -24 -> 37
    //   64: aload_1
    //   65: aload 7
    //   67: getfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   70: invokevirtual 477	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   73: ifeq -36 -> 37
    //   76: aload 7
    //   78: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   81: iload_3
    //   82: if_icmpne -45 -> 37
    //   85: aload 7
    //   87: iload_2
    //   88: putfield 441	com/tencent/common/plugin/QBPluginItemInfo:isNeedUpdate	I
    //   91: goto -54 -> 37
    //   94: aload 5
    //   96: monitorexit
    //   97: aload_0
    //   98: aload_1
    //   99: ldc 84
    //   101: iload_2
    //   102: iload_3
    //   103: invokespecial 945	com/tencent/common/plugin/QBPluginDBHelper:a	(Ljava/lang/String;Ljava/lang/String;II)Z
    //   106: pop
    //   107: aload_0
    //   108: monitorexit
    //   109: iconst_1
    //   110: ireturn
    //   111: astore_1
    //   112: aload 5
    //   114: monitorexit
    //   115: aload_1
    //   116: athrow
    //   117: astore_1
    //   118: aload_0
    //   119: monitorexit
    //   120: aload_1
    //   121: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	122	0	this	QBPluginDBHelper
    //   0	122	1	paramString	String
    //   0	122	2	paramInt1	int
    //   0	122	3	paramInt2	int
    //   6	3	4	bool	boolean
    //   35	13	6	localIterator	Iterator
    //   57	29	7	localQBPluginItemInfo	QBPluginItemInfo
    // Exception table:
    //   from	to	target	type
    //   26	37	111	finally
    //   37	59	111	finally
    //   64	91	111	finally
    //   94	97	111	finally
    //   112	115	111	finally
    //   2	8	117	finally
    //   17	26	117	finally
    //   97	107	117	finally
    //   115	117	117	finally
  }
  
  /* Error */
  public boolean updatePluginType(String paramString, int paramInt1, int paramInt2)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokestatic 344	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   6: istore 4
    //   8: iload 4
    //   10: ifeq +7 -> 17
    //   13: aload_0
    //   14: monitorexit
    //   15: iconst_0
    //   16: ireturn
    //   17: aload_0
    //   18: getfield 153	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   21: astore 5
    //   23: aload 5
    //   25: monitorenter
    //   26: aload_0
    //   27: getfield 149	com/tencent/common/plugin/QBPluginDBHelper:jdField_a_of_type_JavaUtilList	Ljava/util/List;
    //   30: invokeinterface 597 1 0
    //   35: astore 6
    //   37: aload 6
    //   39: invokeinterface 207 1 0
    //   44: ifeq +50 -> 94
    //   47: aload 6
    //   49: invokeinterface 211 1 0
    //   54: checkcast 348	com/tencent/common/plugin/QBPluginItemInfo
    //   57: astore 7
    //   59: aload 7
    //   61: ifnull -24 -> 37
    //   64: aload_1
    //   65: aload 7
    //   67: getfield 371	com/tencent/common/plugin/QBPluginItemInfo:mPackageName	Ljava/lang/String;
    //   70: invokevirtual 477	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   73: ifeq -36 -> 37
    //   76: aload 7
    //   78: getfield 438	com/tencent/common/plugin/QBPluginItemInfo:mInfoFrom	I
    //   81: iload_3
    //   82: if_icmpne -45 -> 37
    //   85: aload 7
    //   87: iload_2
    //   88: putfield 426	com/tencent/common/plugin/QBPluginItemInfo:mZipJarPluginType	I
    //   91: goto -54 -> 37
    //   94: aload 5
    //   96: monitorexit
    //   97: aload_0
    //   98: aload_1
    //   99: ldc 60
    //   101: iload_2
    //   102: iload_3
    //   103: invokespecial 945	com/tencent/common/plugin/QBPluginDBHelper:a	(Ljava/lang/String;Ljava/lang/String;II)Z
    //   106: pop
    //   107: aload_0
    //   108: monitorexit
    //   109: iconst_1
    //   110: ireturn
    //   111: astore_1
    //   112: aload 5
    //   114: monitorexit
    //   115: aload_1
    //   116: athrow
    //   117: astore_1
    //   118: aload_0
    //   119: monitorexit
    //   120: aload_1
    //   121: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	122	0	this	QBPluginDBHelper
    //   0	122	1	paramString	String
    //   0	122	2	paramInt1	int
    //   0	122	3	paramInt2	int
    //   6	3	4	bool	boolean
    //   35	13	6	localIterator	Iterator
    //   57	29	7	localQBPluginItemInfo	QBPluginItemInfo
    // Exception table:
    //   from	to	target	type
    //   26	37	111	finally
    //   37	59	111	finally
    //   64	91	111	finally
    //   94	97	111	finally
    //   112	115	111	finally
    //   2	8	117	finally
    //   17	26	117	finally
    //   97	107	117	finally
    //   115	117	117	finally
  }
  
  public void updatePluginUnzipDir(String paramString1, String paramString2, int paramInt)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return;
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext())
      {
        QBPluginItemInfo localQBPluginItemInfo = (QBPluginItemInfo)localIterator.next();
        if ((localQBPluginItemInfo != null) && (paramString1.equalsIgnoreCase(localQBPluginItemInfo.mPackageName)) && (localQBPluginItemInfo.mInfoFrom == paramInt)) {
          localQBPluginItemInfo.mUnzipDir = paramString2;
        }
      }
      a(paramString1, "unzipPath", paramString2, paramInt);
      return;
    }
  }
  
  public class PluginComparator
    implements Comparator<QBPluginItemInfo>
  {
    public PluginComparator() {}
    
    public int compare(QBPluginItemInfo paramQBPluginItemInfo1, QBPluginItemInfo paramQBPluginItemInfo2)
    {
      if (paramQBPluginItemInfo1.mOrder == paramQBPluginItemInfo2.mOrder) {
        return 0;
      }
      if (paramQBPluginItemInfo1.mOrder < paramQBPluginItemInfo2.mOrder) {
        return -1;
      }
      return 1;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\QBPluginDBHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */