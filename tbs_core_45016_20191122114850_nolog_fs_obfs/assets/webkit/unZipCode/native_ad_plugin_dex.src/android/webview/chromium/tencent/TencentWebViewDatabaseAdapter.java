package android.webview.chromium.tencent;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.webview.chromium.WebViewChromiumFactoryProvider;
import android.webview.chromium.WebViewDatabaseAdapter;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.smtt.net.SessionInfo;
import com.tencent.smtt.util.n;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewFactory;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewFactoryProvider;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import org.chromium.android_webview.HttpAuthDatabase;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.annotations.UsedByReflection;

public class TencentWebViewDatabaseAdapter
  extends WebViewDatabaseAdapter
{
  private static final String ADINFO_CONTENT_COL = "content";
  private static final String ADINFO_DOMAIN_COL = "domain";
  private static final String ADINFO_DS_COL = "ds";
  private static final String ADINFO_EXPIRES_COL = "expires";
  private static final String ADINFO_FIX_COL = "fix";
  private static final String ADINFO_ISTOUCH_COL = "istouch";
  private static final String ADINFO_LAST_REQUEST_TIME_COL = "lastrequesttime";
  private static final String ADINFO_RD_COL = "rd";
  private static final String ADINFO_TYPE_COL = "type";
  private static final String DATABASE_FILE = "webview_core_x5.db";
  private static final int DATABASE_VERSION = 8;
  private static final String FORCE_PINCH_SCALE_DOMAIN_COL = "host";
  private static final String ID_COL = "_id";
  private static final String PASSWORD_HOST_COL = "host";
  private static final String PASSWORD_PASSWORD_COL = "password";
  private static final String PASSWORD_PASSWORD_ELEMENT_COL = "pElement";
  private static final String PASSWORD_USERNAME_COL = "username";
  private static final String PASSWORD_USERNAME_ELEMENT_COL = "uElement";
  private static final String POPWINDOW_DOMAIN_COL = "host";
  public static final int POPWINDOW_OPERATE_ALLOWED = 1;
  private static final String POPWINDOW_OPERATE_COL = "operate";
  public static final int POPWINDOW_OPERATE_SHOW = 0;
  public static final int POPWINDOW_UNAVAILABLE = -1;
  private static final String SESSION_CACHE_KEY_COL = "cachekey";
  private static final String SESSION_CIPHER_COL = "cipher";
  private static final String SESSION_MKLENGTH_COL = "mklength";
  private static final String SESSION_MK_COL = "mk";
  private static final String SESSION_SIDLENGTH_COL = "sidlength";
  private static final String SESSION_SID_COL = "sid";
  private static final String SESSION_STHINT_COL = "skhint";
  private static final String SESSION_STLENGTH_COL = "stlength";
  private static final String SESSION_ST_COL = "sk";
  private static final int TABLE_ADBLOCK_INFO = 1;
  private static final int TABLE_FORCE_PINCH_SCALE_INFO = 3;
  private static final int TABLE_PASSWORD_ID = 0;
  private static final int TABLE_POPWINDOW_INFO = 2;
  private static final int TABLE_SESSION_INFO = 4;
  private static final boolean USE_PERSISTENT_FORCE_PINCH_SCALE = false;
  private static SQLiteDatabase mDatabase;
  private static final String[] mTableNames = { "password", "adinfo", "popwindowinfo", "force_pinch_scale", "session" };
  private static ArrayList<String> sForcePinchScaleDomainList = new ArrayList();
  private static boolean sForcePinchScaleDomainListInit;
  private final Object mAdInfoLock = new Object();
  private final Object mForcePinchScaleLock = new Object();
  private final Object mPasswordLock = new Object();
  private final Object mPopwindowInfoLock = new Object();
  private final Object mSessionInfoLock = new Object();
  
  static
  {
    mDatabase = null;
    sForcePinchScaleDomainListInit = false;
  }
  
  public TencentWebViewDatabaseAdapter(WebViewChromiumFactoryProvider paramWebViewChromiumFactoryProvider, HttpAuthDatabase paramHttpAuthDatabase)
  {
    super(paramWebViewChromiumFactoryProvider, paramHttpAuthDatabase);
    paramWebViewChromiumFactoryProvider = ContextUtils.getApplicationContext();
    try
    {
      try
      {
        mDatabase = paramWebViewChromiumFactoryProvider.openOrCreateDatabase("webview_core_x5.db", 0, null);
      }
      catch (Throwable paramWebViewChromiumFactoryProvider)
      {
        paramWebViewChromiumFactoryProvider.printStackTrace();
      }
    }
    catch (SQLiteException paramHttpAuthDatabase)
    {
      for (;;) {}
    }
    if (paramWebViewChromiumFactoryProvider.deleteDatabase("webview_core_x5.db")) {
      try
      {
        mDatabase = paramWebViewChromiumFactoryProvider.openOrCreateDatabase("webview_core_x5.db", 0, null);
      }
      catch (Throwable paramWebViewChromiumFactoryProvider)
      {
        paramWebViewChromiumFactoryProvider.printStackTrace();
        paramHttpAuthDatabase = new StringBuilder();
        paramHttpAuthDatabase.append("WebViewDatabaseAdapter create error: ");
        paramHttpAuthDatabase.append(paramWebViewChromiumFactoryProvider.toString());
        Log.e("webviewdatabase", paramHttpAuthDatabase.toString(), new Object[0]);
      }
    }
    paramWebViewChromiumFactoryProvider = mDatabase;
    if ((paramWebViewChromiumFactoryProvider != null) && (paramWebViewChromiumFactoryProvider.getVersion() != 8)) {
      mDatabase.beginTransaction();
    }
    try
    {
      upgradeDatabase(mDatabase.getVersion(), 8);
      mDatabase.setTransactionSuccessful();
      mDatabase.endTransaction();
    }
    finally
    {
      mDatabase.endTransaction();
    }
    if (paramWebViewChromiumFactoryProvider != null) {
      paramWebViewChromiumFactoryProvider.setLockingEnabled(false);
    }
    getAllForcePinchScaleHosts();
  }
  
  private void clearForcePinchScaleHosts()
  {
    if (mDatabase == null) {
      return;
    }
    try
    {
      synchronized (this.mForcePinchScaleLock)
      {
        mDatabase.delete(mTableNames[3], null, null);
        sForcePinchScaleDomainList.clear();
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
  }
  
  private void clearJavaScriptOpenWindowAutomaticallyOperation()
  {
    if (mDatabase == null) {
      return;
    }
    try
    {
      synchronized (this.mPopwindowInfoLock)
      {
        mDatabase.delete(mTableNames[2], null, null);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
  }
  
  private String decryption(String paramString)
  {
    if ((paramString != null) && (!TextUtils.isEmpty(paramString))) {
      try
      {
        paramString = n.b(paramString.getBytes("ISO-8859-1"));
        if (paramString != null)
        {
          paramString = new String(paramString);
          return paramString;
        }
      }
      catch (UnsupportedEncodingException paramString)
      {
        paramString.printStackTrace();
      }
    }
    return new String();
  }
  
  private String encryption(String paramString)
  {
    if (paramString != null) {
      try
      {
        paramString = new String(n.a(paramString.getBytes()), "ISO-8859-1");
        return paramString;
      }
      catch (UnsupportedEncodingException paramString)
      {
        paramString.printStackTrace();
      }
    }
    return new String();
  }
  
  private void getAllForcePinchScaleHosts()
  {
    synchronized (this.mForcePinchScaleLock)
    {
      if (sForcePinchScaleDomainListInit) {
        return;
      }
      BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        /* Error */
        public void doRun()
        {
          // Byte code:
          //   0: invokestatic 25	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:access$200	()Landroid/database/sqlite/SQLiteDatabase;
          //   3: ifnonnull +4 -> 7
          //   6: return
          //   7: aload_0
          //   8: getfield 15	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$2:this$0	Landroid/webview/chromium/tencent/TencentWebViewDatabaseAdapter;
          //   11: invokestatic 29	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:access$300	(Landroid/webview/chromium/tencent/TencentWebViewDatabaseAdapter;)Ljava/lang/Object;
          //   14: astore 4
          //   16: aload 4
          //   18: monitorenter
          //   19: invokestatic 33	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:access$400	()Z
          //   22: ifeq +7 -> 29
          //   25: aload 4
          //   27: monitorexit
          //   28: return
          //   29: aconst_null
          //   30: astore_1
          //   31: aconst_null
          //   32: astore_2
          //   33: invokestatic 25	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:access$200	()Landroid/database/sqlite/SQLiteDatabase;
          //   36: invokestatic 37	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:access$100	()[Ljava/lang/String;
          //   39: iconst_3
          //   40: aaload
          //   41: iconst_1
          //   42: anewarray 39	java/lang/String
          //   45: dup
          //   46: iconst_0
          //   47: ldc 41
          //   49: aastore
          //   50: aconst_null
          //   51: aconst_null
          //   52: aconst_null
          //   53: aconst_null
          //   54: aconst_null
          //   55: invokevirtual 47	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
          //   58: astore_3
          //   59: aload_3
          //   60: ifnull +54 -> 114
          //   63: aload_3
          //   64: astore_2
          //   65: aload_3
          //   66: astore_1
          //   67: aload_3
          //   68: invokeinterface 52 1 0
          //   73: ifeq +41 -> 114
          //   76: aload_3
          //   77: astore_2
          //   78: aload_3
          //   79: astore_1
          //   80: invokestatic 56	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:access$500	()Ljava/util/ArrayList;
          //   83: aload_3
          //   84: aload_3
          //   85: ldc 41
          //   87: invokeinterface 60 2 0
          //   92: invokeinterface 64 2 0
          //   97: invokevirtual 70	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   100: pop
          //   101: aload_3
          //   102: astore_2
          //   103: aload_3
          //   104: astore_1
          //   105: aload_3
          //   106: invokeinterface 73 1 0
          //   111: ifne -35 -> 76
          //   114: aload_3
          //   115: astore_2
          //   116: aload_3
          //   117: astore_1
          //   118: iconst_1
          //   119: invokestatic 77	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:access$402	(Z)Z
          //   122: pop
          //   123: aload_3
          //   124: ifnull +32 -> 156
          //   127: aload_3
          //   128: astore_1
          //   129: aload_1
          //   130: invokeinterface 80 1 0
          //   135: goto +21 -> 156
          //   138: astore_1
          //   139: goto +21 -> 160
          //   142: astore_3
          //   143: aload_1
          //   144: astore_2
          //   145: aload_3
          //   146: invokevirtual 83	java/lang/Exception:printStackTrace	()V
          //   149: aload_1
          //   150: ifnull +6 -> 156
          //   153: goto -24 -> 129
          //   156: aload 4
          //   158: monitorexit
          //   159: return
          //   160: aload_2
          //   161: ifnull +9 -> 170
          //   164: aload_2
          //   165: invokeinterface 80 1 0
          //   170: aload_1
          //   171: athrow
          //   172: astore_1
          //   173: aload 4
          //   175: monitorexit
          //   176: aload_1
          //   177: athrow
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	178	0	this	2
          //   30	100	1	localObject1	Object
          //   138	33	1	localObject2	Object
          //   172	5	1	localObject3	Object
          //   32	133	2	localObject4	Object
          //   58	70	3	localCursor	android.database.Cursor
          //   142	4	3	localException	Exception
          //   14	160	4	localObject5	Object
          // Exception table:
          //   from	to	target	type
          //   33	59	138	finally
          //   67	76	138	finally
          //   80	101	138	finally
          //   105	114	138	finally
          //   118	123	138	finally
          //   145	149	138	finally
          //   33	59	142	java/lang/Exception
          //   67	76	142	java/lang/Exception
          //   80	101	142	java/lang/Exception
          //   105	114	142	java/lang/Exception
          //   118	123	142	java/lang/Exception
          //   19	28	172	finally
          //   129	135	172	finally
          //   156	159	172	finally
          //   164	170	172	finally
          //   170	172	172	finally
          //   173	176	172	finally
        }
      });
      return;
    }
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static TencentWebViewDatabaseAdapter getInstance(Context paramContext)
  {
    return (TencentWebViewDatabaseAdapter)TencentWebViewFactory.getProvider().getWebViewDatabase(paramContext);
  }
  
  private void insertUsernamePassword(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if ((mDatabase != null) && (!TextUtils.isEmpty(paramString1)))
    {
      if (paramString2 == null) {
        return;
      }
      synchronized (this.mPasswordLock)
      {
        ContentValues localContentValues = new ContentValues();
        paramString2 = encryption(paramString2);
        paramString3 = encryption(paramString3);
        paramString4 = encryption(paramString4);
        paramString5 = encryption(paramString5);
        try
        {
          localContentValues.put("host", paramString1);
          localContentValues.put("username", paramString2);
          localContentValues.put("password", paramString3);
          localContentValues.put("uElement", paramString4);
          localContentValues.put("pElement", paramString5);
          mDatabase.insert(mTableNames[0], "host", localContentValues);
        }
        catch (Exception paramString1)
        {
          paramString1.printStackTrace();
        }
        return;
      }
    }
  }
  
  private void updateUsernamePassword(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if ((mDatabase != null) && (!TextUtils.isEmpty(paramString1)))
    {
      if (paramString2 == null) {
        return;
      }
      synchronized (this.mPasswordLock)
      {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("username", encryption(paramString2));
        localContentValues.put("password", encryption(paramString3));
        localContentValues.put("uElement", encryption(paramString4));
        localContentValues.put("pElement", encryption(paramString5));
        try
        {
          mDatabase.update(mTableNames[0], localContentValues, "(host == ?)", new String[] { paramString1 });
        }
        catch (Exception paramString1)
        {
          paramString1.printStackTrace();
        }
        return;
      }
    }
  }
  
  private static void upgradeDatabase(int paramInt1, int paramInt2)
  {
    if (paramInt1 < 1) {}
    try
    {
      localSQLiteDatabase = mDatabase;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("DROP TABLE IF EXISTS ");
      localStringBuilder.append(mTableNames[0]);
      localSQLiteDatabase.execSQL(localStringBuilder.toString());
      localSQLiteDatabase = mDatabase;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("CREATE TABLE ");
      localStringBuilder.append(mTableNames[0]);
      localStringBuilder.append(" (");
      localStringBuilder.append("_id");
      localStringBuilder.append(" INTEGER PRIMARY KEY, ");
      localStringBuilder.append("host");
      localStringBuilder.append(" TEXT, ");
      localStringBuilder.append("username");
      localStringBuilder.append(" TEXT, ");
      localStringBuilder.append("password");
      localStringBuilder.append(" TEXT,");
      localStringBuilder.append("uElement");
      localStringBuilder.append(" TEXT,");
      localStringBuilder.append("pElement");
      localStringBuilder.append(" TEXT, UNIQUE (");
      localStringBuilder.append("host");
      localStringBuilder.append(", ");
      localStringBuilder.append("username");
      localStringBuilder.append(") ON CONFLICT REPLACE);");
      localSQLiteDatabase.execSQL(localStringBuilder.toString());
      if (paramInt1 < 2)
      {
        localSQLiteDatabase = mDatabase;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("DROP TABLE IF EXISTS ");
        localStringBuilder.append(mTableNames[1]);
        localSQLiteDatabase.execSQL(localStringBuilder.toString());
        localSQLiteDatabase = mDatabase;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("CREATE TABLE ");
        localStringBuilder.append(mTableNames[1]);
        localStringBuilder.append(" (");
        localStringBuilder.append("_id");
        localStringBuilder.append(" INTEGER PRIMARY KEY , ");
        localStringBuilder.append("domain");
        localStringBuilder.append(" TEXT, ");
        localStringBuilder.append("content");
        localStringBuilder.append(" TEXT, ");
        localStringBuilder.append("type");
        localStringBuilder.append(" INTEGER, ");
        localStringBuilder.append("expires");
        localStringBuilder.append(" INTEGER, ");
        localStringBuilder.append("istouch");
        localStringBuilder.append(" INTEGER, ");
        localStringBuilder.append("lastrequesttime");
        localStringBuilder.append(" INTEGER, UNIQUE (");
        localStringBuilder.append("domain");
        localStringBuilder.append(", ");
        localStringBuilder.append("content");
        localStringBuilder.append(") ON CONFLICT REPLACE);");
        localSQLiteDatabase.execSQL(localStringBuilder.toString());
        localSQLiteDatabase = mDatabase;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("CREATE INDEX domianIndex ON ");
        localStringBuilder.append(mTableNames[1]);
        localStringBuilder.append(" (domain)");
        localSQLiteDatabase.execSQL(localStringBuilder.toString());
      }
      if (paramInt1 < 3)
      {
        localSQLiteDatabase = mDatabase;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("DROP TABLE IF EXISTS ");
        localStringBuilder.append(mTableNames[2]);
        localSQLiteDatabase.execSQL(localStringBuilder.toString());
        localSQLiteDatabase = mDatabase;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("CREATE TABLE ");
        localStringBuilder.append(mTableNames[2]);
        localStringBuilder.append(" (");
        localStringBuilder.append("_id");
        localStringBuilder.append(" INTEGER PRIMARY KEY , ");
        localStringBuilder.append("host");
        localStringBuilder.append(" TEXT, ");
        localStringBuilder.append("operate");
        localStringBuilder.append(" INTEGER, UNIQUE (");
        localStringBuilder.append("host");
        localStringBuilder.append(") ON CONFLICT REPLACE);");
        localSQLiteDatabase.execSQL(localStringBuilder.toString());
      }
      if (paramInt1 < 4)
      {
        localSQLiteDatabase = mDatabase;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("DROP TABLE IF EXISTS ");
        localStringBuilder.append(mTableNames[3]);
        localSQLiteDatabase.execSQL(localStringBuilder.toString());
        localSQLiteDatabase = mDatabase;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("CREATE TABLE ");
        localStringBuilder.append(mTableNames[3]);
        localStringBuilder.append(" (");
        localStringBuilder.append("_id");
        localStringBuilder.append(" INTEGER PRIMARY KEY , ");
        localStringBuilder.append("host");
        localStringBuilder.append(" TEXT,  UNIQUE (");
        localStringBuilder.append("host");
        localStringBuilder.append(") ON CONFLICT REPLACE);");
        localSQLiteDatabase.execSQL(localStringBuilder.toString());
      }
      if (paramInt1 < 6)
      {
        localSQLiteDatabase = mDatabase;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("DROP TABLE IF EXISTS ");
        localStringBuilder.append(mTableNames[4]);
        localSQLiteDatabase.execSQL(localStringBuilder.toString());
        localSQLiteDatabase = mDatabase;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("CREATE TABLE ");
        localStringBuilder.append(mTableNames[4]);
        localStringBuilder.append(" (");
        localStringBuilder.append("_id");
        localStringBuilder.append(" INTEGER PRIMARY KEY , ");
        localStringBuilder.append("cachekey");
        localStringBuilder.append(" TEXT, ");
        localStringBuilder.append("mklength");
        localStringBuilder.append(" INTEGER, ");
        localStringBuilder.append("mk");
        localStringBuilder.append(" BLOB, ");
        localStringBuilder.append("sidlength");
        localStringBuilder.append(" INTEGER, ");
        localStringBuilder.append("sid");
        localStringBuilder.append(" BLOB, ");
        localStringBuilder.append("stlength");
        localStringBuilder.append(" INTEGER, ");
        localStringBuilder.append("sk");
        localStringBuilder.append(" BLOB, ");
        localStringBuilder.append("skhint");
        localStringBuilder.append(" INTEGER, ");
        localStringBuilder.append("cipher");
        localStringBuilder.append(" INTEGER,  UNIQUE (");
        localStringBuilder.append("cachekey");
        localStringBuilder.append(") ON CONFLICT REPLACE);");
        localSQLiteDatabase.execSQL(localStringBuilder.toString());
      }
      if (paramInt1 < 8)
      {
        localSQLiteDatabase = mDatabase;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("DROP TABLE IF EXISTS ");
        localStringBuilder.append(mTableNames[1]);
        localSQLiteDatabase.execSQL(localStringBuilder.toString());
        localSQLiteDatabase = mDatabase;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("CREATE TABLE ");
        localStringBuilder.append(mTableNames[1]);
        localStringBuilder.append(" (");
        localStringBuilder.append("_id");
        localStringBuilder.append(" INTEGER PRIMARY KEY , ");
        localStringBuilder.append("domain");
        localStringBuilder.append(" TEXT, ");
        localStringBuilder.append("content");
        localStringBuilder.append(" TEXT, ");
        localStringBuilder.append("type");
        localStringBuilder.append(" INTEGER, ");
        localStringBuilder.append("expires");
        localStringBuilder.append(" INTEGER, ");
        localStringBuilder.append("istouch");
        localStringBuilder.append(" INTEGER, ");
        localStringBuilder.append("ds");
        localStringBuilder.append(" INTEGER, ");
        localStringBuilder.append("rd");
        localStringBuilder.append(" INTEGER, ");
        localStringBuilder.append("fix");
        localStringBuilder.append(" INTEGER, ");
        localStringBuilder.append("lastrequesttime");
        localStringBuilder.append(" INTEGER, UNIQUE (");
        localStringBuilder.append("domain");
        localStringBuilder.append(", ");
        localStringBuilder.append("content");
        localStringBuilder.append(") ON CONFLICT REPLACE);");
        localSQLiteDatabase.execSQL(localStringBuilder.toString());
        localSQLiteDatabase = mDatabase;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("CREATE INDEX domianIndex ON ");
        localStringBuilder.append(mTableNames[1]);
        localStringBuilder.append(" (domain)");
        localSQLiteDatabase.execSQL(localStringBuilder.toString());
      }
      mDatabase.setVersion(8);
      return;
    }
    catch (Exception localException)
    {
      SQLiteDatabase localSQLiteDatabase;
      for (;;) {}
    }
    localSQLiteDatabase.printStackTrace();
  }
  
  public void addRule(String paramString1, String paramString2, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, long paramLong)
  {
    if ((paramString1 != null) && (paramString2 != null))
    {
      if (mDatabase == null) {
        return;
      }
      try
      {
        synchronized (this.mAdInfoLock)
        {
          ContentValues localContentValues = new ContentValues();
          localContentValues.put("domain", paramString1);
          localContentValues.put("content", paramString2);
          localContentValues.put("type", Integer.valueOf(paramInt1));
          localContentValues.put("expires", Integer.valueOf(0));
          localContentValues.put("istouch", Boolean.valueOf(paramBoolean));
          localContentValues.put("ds", Integer.valueOf(paramInt2));
          localContentValues.put("rd", Integer.valueOf(paramInt3));
          localContentValues.put("fix", Integer.valueOf(paramInt4));
          localContentValues.put("lastrequesttime", Long.valueOf(paramLong));
          mDatabase.insert(mTableNames[1], null, localContentValues);
        }
      }
      catch (Exception paramString1)
      {
        paramString1.printStackTrace();
        return;
      }
      throw paramString1;
    }
  }
  
  public void addSessionInfo(SessionInfo paramSessionInfo)
  {
    if ((paramSessionInfo != null) && (paramSessionInfo.cacheKey != null))
    {
      if (mDatabase == null) {
        return;
      }
      try
      {
        synchronized (this.mSessionInfoLock)
        {
          ContentValues localContentValues = new ContentValues();
          localContentValues.put("cachekey", paramSessionInfo.cacheKey);
          localContentValues.put("mklength", Integer.valueOf(paramSessionInfo.masterKeyLength));
          localContentValues.put("mk", paramSessionInfo.masterKey);
          localContentValues.put("sidlength", Integer.valueOf(paramSessionInfo.sessionIDLength));
          localContentValues.put("sid", paramSessionInfo.sessionID);
          localContentValues.put("stlength", Integer.valueOf(paramSessionInfo.sessionTicketsLength));
          localContentValues.put("sk", paramSessionInfo.sessionTickets);
          localContentValues.put("skhint", Long.valueOf(paramSessionInfo.sessionTicketsLifeHint));
          localContentValues.put("cipher", Integer.valueOf(paramSessionInfo.cipherValue));
          mDatabase.insert(mTableNames[4], null, localContentValues);
        }
      }
      catch (Exception paramSessionInfo)
      {
        paramSessionInfo.printStackTrace();
        return;
      }
      throw paramSessionInfo;
    }
  }
  
  public void clearADBlockInfos()
  {
    if (mDatabase == null) {
      return;
    }
    BrowserExecutorSupplier.postForDbTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        try
        {
          synchronized (TencentWebViewDatabaseAdapter.this.mAdInfoLock)
          {
            TencentWebViewDatabaseAdapter.mDatabase.delete(TencentWebViewDatabaseAdapter.mTableNames[1], null, null);
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return;
        }
      }
    });
  }
  
  public void clearFormData()
  {
    super.clearFormData();
    clearJavaScriptOpenWindowAutomaticallyOperation();
    clearForcePinchScaleHosts();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void clearUsernamePassword()
  {
    if (mDatabase == null) {
      return;
    }
    try
    {
      synchronized (this.mPasswordLock)
      {
        mDatabase.delete(mTableNames[0], null, null);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
  }
  
  public void deleteAdFeatureRule(String paramString)
  {
    if (paramString != null) {
      if (mDatabase == null) {
        return;
      }
    }
    try
    {
      synchronized (this.mAdInfoLock)
      {
        mDatabase.delete(mTableNames[1], "(domain == ?) AND (type == ?)", new String[] { paramString, "6" });
      }
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    return;
    throw paramString;
  }
  
  public void deleteRule(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString2 != null))
    {
      if (mDatabase == null) {
        return;
      }
      try
      {
        synchronized (this.mAdInfoLock)
        {
          mDatabase.delete(mTableNames[1], "(domain == ?) AND (content == ?)", new String[] { paramString1, paramString2 });
        }
      }
      catch (Exception paramString1)
      {
        paramString1.printStackTrace();
        return;
      }
      throw paramString1;
    }
  }
  
  public void deleteSessionInfo(SessionInfo paramSessionInfo)
  {
    if ((paramSessionInfo != null) && (paramSessionInfo.cacheKey != null))
    {
      if (mDatabase == null) {
        return;
      }
      try
      {
        synchronized (this.mSessionInfoLock)
        {
          mDatabase.delete(mTableNames[4], "(cachekey == ?) ", new String[] { paramSessionInfo.cacheKey });
        }
      }
      catch (Exception paramSessionInfo)
      {
        paramSessionInfo.printStackTrace();
        return;
      }
      throw paramSessionInfo;
    }
  }
  
  /* Error */
  public AdInfo getAdInfoByDomain(String paramString)
  {
    // Byte code:
    //   0: new 10	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$AdInfo
    //   3: dup
    //   4: invokespecial 487	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$AdInfo:<init>	()V
    //   7: astore 10
    //   9: aload_1
    //   10: ifnull +428 -> 438
    //   13: getstatic 147	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   16: ifnonnull +6 -> 22
    //   19: aload 10
    //   21: areturn
    //   22: aconst_null
    //   23: astore 7
    //   25: aconst_null
    //   26: astore 8
    //   28: aload_0
    //   29: getfield 171	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:mAdInfoLock	Ljava/lang/Object;
    //   32: astore 9
    //   34: aload 9
    //   36: monitorenter
    //   37: getstatic 147	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   40: getstatic 145	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:mTableNames	[Ljava/lang/String;
    //   43: iconst_1
    //   44: aaload
    //   45: iconst_3
    //   46: anewarray 135	java/lang/String
    //   49: dup
    //   50: iconst_0
    //   51: ldc 21
    //   53: aastore
    //   54: dup
    //   55: iconst_1
    //   56: ldc 45
    //   58: aastore
    //   59: dup
    //   60: iconst_2
    //   61: ldc 30
    //   63: aastore
    //   64: ldc_w 489
    //   67: iconst_1
    //   68: anewarray 135	java/lang/String
    //   71: dup
    //   72: iconst_0
    //   73: aload_1
    //   74: aastore
    //   75: aconst_null
    //   76: aconst_null
    //   77: aconst_null
    //   78: invokevirtual 493	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   81: astore_1
    //   82: aload_1
    //   83: ifnull +315 -> 398
    //   86: aload_1
    //   87: astore 8
    //   89: aload_1
    //   90: astore 7
    //   92: aload_1
    //   93: invokeinterface 498 1 0
    //   98: ifeq +300 -> 398
    //   101: aload_1
    //   102: astore 8
    //   104: aload_1
    //   105: astore 7
    //   107: aload_1
    //   108: ldc 21
    //   110: invokeinterface 502 2 0
    //   115: istore_2
    //   116: aload_1
    //   117: astore 8
    //   119: aload_1
    //   120: astore 7
    //   122: aload_1
    //   123: ldc 45
    //   125: invokeinterface 502 2 0
    //   130: istore_3
    //   131: aload_1
    //   132: astore 8
    //   134: aload_1
    //   135: astore 7
    //   137: aload_1
    //   138: ldc 30
    //   140: invokeinterface 502 2 0
    //   145: istore 4
    //   147: aload_1
    //   148: astore 8
    //   150: aload_1
    //   151: astore 7
    //   153: aload_1
    //   154: iload_2
    //   155: invokeinterface 506 2 0
    //   160: astore 11
    //   162: aload_1
    //   163: astore 8
    //   165: aload_1
    //   166: astore 7
    //   168: aload_1
    //   169: iload_3
    //   170: invokeinterface 510 2 0
    //   175: istore 5
    //   177: aload_1
    //   178: astore 8
    //   180: aload_1
    //   181: astore 7
    //   183: aload_1
    //   184: iload 4
    //   186: invokeinterface 514 2 0
    //   191: pop2
    //   192: iload 5
    //   194: ifne +33 -> 227
    //   197: aload_1
    //   198: astore 8
    //   200: aload_1
    //   201: astore 7
    //   203: aload 10
    //   205: getfield 518	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$AdInfo:filterRulesMap	Ljava/util/Map;
    //   208: aload 11
    //   210: new 16	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$Values
    //   213: dup
    //   214: iconst_1
    //   215: invokespecial 521	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$Values:<init>	(B)V
    //   218: invokeinterface 526 3 0
    //   223: pop
    //   224: goto +155 -> 379
    //   227: iload 5
    //   229: iconst_1
    //   230: if_icmpne +33 -> 263
    //   233: aload_1
    //   234: astore 8
    //   236: aload_1
    //   237: astore 7
    //   239: aload 10
    //   241: getfield 529	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$AdInfo:hiddenRulesMap	Ljava/util/Map;
    //   244: aload 11
    //   246: new 16	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$Values
    //   249: dup
    //   250: iconst_1
    //   251: invokespecial 521	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$Values:<init>	(B)V
    //   254: invokeinterface 526 3 0
    //   259: pop
    //   260: goto +119 -> 379
    //   263: iload 5
    //   265: iconst_2
    //   266: if_icmpne +38 -> 304
    //   269: aload_1
    //   270: astore 8
    //   272: aload_1
    //   273: astore 7
    //   275: aload 10
    //   277: aload 11
    //   279: putfield 532	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$AdInfo:scriptArguments	Ljava/lang/String;
    //   282: aload_1
    //   283: astore 8
    //   285: aload_1
    //   286: astore 7
    //   288: aload 10
    //   290: new 16	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$Values
    //   293: dup
    //   294: iconst_1
    //   295: invokespecial 521	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$Values:<init>	(B)V
    //   298: putfield 536	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$AdInfo:ScriptArgumentsStatus	Landroid/webview/chromium/tencent/TencentWebViewDatabaseAdapter$Values;
    //   301: goto +78 -> 379
    //   304: iload 5
    //   306: iconst_4
    //   307: if_icmpne +33 -> 340
    //   310: aload_1
    //   311: astore 8
    //   313: aload_1
    //   314: astore 7
    //   316: aload 10
    //   318: getfield 539	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$AdInfo:userSelectHiddenRulesMap	Ljava/util/Map;
    //   321: aload 11
    //   323: new 16	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$Values
    //   326: dup
    //   327: iconst_1
    //   328: invokespecial 521	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$Values:<init>	(B)V
    //   331: invokeinterface 526 3 0
    //   336: pop
    //   337: goto +42 -> 379
    //   340: iload 5
    //   342: bipush 6
    //   344: if_icmpne +35 -> 379
    //   347: aload_1
    //   348: astore 8
    //   350: aload_1
    //   351: astore 7
    //   353: aload 10
    //   355: aload 11
    //   357: putfield 542	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$AdInfo:featureRulesStr	Ljava/lang/String;
    //   360: aload_1
    //   361: astore 8
    //   363: aload_1
    //   364: astore 7
    //   366: aload 10
    //   368: new 16	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$Values
    //   371: dup
    //   372: iconst_1
    //   373: invokespecial 521	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$Values:<init>	(B)V
    //   376: putfield 545	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$AdInfo:featureRulesStatus	Landroid/webview/chromium/tencent/TencentWebViewDatabaseAdapter$Values;
    //   379: aload_1
    //   380: astore 8
    //   382: aload_1
    //   383: astore 7
    //   385: aload_1
    //   386: invokeinterface 548 1 0
    //   391: istore 6
    //   393: iload 6
    //   395: ifne -248 -> 147
    //   398: aload_1
    //   399: ifnull +27 -> 426
    //   402: aload_1
    //   403: invokeinterface 551 1 0
    //   408: goto +18 -> 426
    //   411: astore_1
    //   412: aload 8
    //   414: ifnull +10 -> 424
    //   417: aload 8
    //   419: invokeinterface 551 1 0
    //   424: aload_1
    //   425: athrow
    //   426: aload 9
    //   428: monitorexit
    //   429: aload 10
    //   431: areturn
    //   432: astore_1
    //   433: aload 9
    //   435: monitorexit
    //   436: aload_1
    //   437: athrow
    //   438: aload 10
    //   440: areturn
    //   441: astore_1
    //   442: aload 7
    //   444: ifnull -18 -> 426
    //   447: aload 7
    //   449: astore_1
    //   450: goto -48 -> 402
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	453	0	this	TencentWebViewDatabaseAdapter
    //   0	453	1	paramString	String
    //   115	40	2	i	int
    //   130	40	3	j	int
    //   145	40	4	k	int
    //   175	170	5	m	int
    //   391	3	6	bool	boolean
    //   23	425	7	str1	String
    //   26	392	8	str2	String
    //   32	402	9	localObject	Object
    //   7	432	10	localAdInfo	AdInfo
    //   160	196	11	str3	String
    // Exception table:
    //   from	to	target	type
    //   37	82	411	finally
    //   92	101	411	finally
    //   107	116	411	finally
    //   122	131	411	finally
    //   137	147	411	finally
    //   153	162	411	finally
    //   168	177	411	finally
    //   183	192	411	finally
    //   203	224	411	finally
    //   239	260	411	finally
    //   275	282	411	finally
    //   288	301	411	finally
    //   316	337	411	finally
    //   353	360	411	finally
    //   366	379	411	finally
    //   385	393	411	finally
    //   402	408	432	finally
    //   417	424	432	finally
    //   424	426	432	finally
    //   426	429	432	finally
    //   433	436	432	finally
    //   37	82	441	java/lang/Exception
    //   92	101	441	java/lang/Exception
    //   107	116	441	java/lang/Exception
    //   122	131	441	java/lang/Exception
    //   137	147	441	java/lang/Exception
    //   153	162	441	java/lang/Exception
    //   168	177	441	java/lang/Exception
    //   183	192	441	java/lang/Exception
    //   203	224	441	java/lang/Exception
    //   239	260	441	java/lang/Exception
    //   275	282	441	java/lang/Exception
    //   288	301	441	java/lang/Exception
    //   316	337	441	java/lang/Exception
    //   353	360	441	java/lang/Exception
    //   366	379	441	java/lang/Exception
    //   385	393	441	java/lang/Exception
  }
  
  /* Error */
  public java.util.List<AdStatus> getAllAdInfoDomain()
  {
    // Byte code:
    //   0: getstatic 147	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   3: astore_2
    //   4: aconst_null
    //   5: astore 4
    //   7: aconst_null
    //   8: astore 5
    //   10: aload_2
    //   11: ifnonnull +5 -> 16
    //   14: aconst_null
    //   15: areturn
    //   16: aload_2
    //   17: ldc_w 555
    //   20: aconst_null
    //   21: invokevirtual 559	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   24: astore_3
    //   25: aload 5
    //   27: astore_2
    //   28: aload_3
    //   29: ifnull +124 -> 153
    //   32: aload 5
    //   34: astore_2
    //   35: aload_3
    //   36: invokeinterface 498 1 0
    //   41: ifeq +112 -> 153
    //   44: new 151	java/util/ArrayList
    //   47: dup
    //   48: aload_3
    //   49: invokeinterface 562 1 0
    //   54: invokespecial 564	java/util/ArrayList:<init>	(I)V
    //   57: astore_2
    //   58: aload_3
    //   59: iconst_0
    //   60: invokeinterface 506 2 0
    //   65: astore 4
    //   67: iconst_1
    //   68: aload_3
    //   69: iconst_1
    //   70: invokeinterface 510 2 0
    //   75: if_icmpne +139 -> 214
    //   78: iconst_1
    //   79: istore_1
    //   80: goto +3 -> 83
    //   83: aload_2
    //   84: new 13	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$AdStatus
    //   87: dup
    //   88: aload 4
    //   90: iload_1
    //   91: aload_3
    //   92: iconst_2
    //   93: invokeinterface 510 2 0
    //   98: aload_3
    //   99: iconst_3
    //   100: invokeinterface 510 2 0
    //   105: aload_3
    //   106: iconst_4
    //   107: invokeinterface 510 2 0
    //   112: aload_3
    //   113: iconst_5
    //   114: invokeinterface 514 2 0
    //   119: invokespecial 567	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter$AdStatus:<init>	(Ljava/lang/String;ZIIIJ)V
    //   122: invokevirtual 571	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   125: pop
    //   126: aload_3
    //   127: invokeinterface 548 1 0
    //   132: istore_1
    //   133: iload_1
    //   134: ifne -76 -> 58
    //   137: goto +16 -> 153
    //   140: goto +48 -> 188
    //   143: astore_2
    //   144: goto +27 -> 171
    //   147: aload 4
    //   149: astore_2
    //   150: goto +38 -> 188
    //   153: aload_2
    //   154: astore 4
    //   156: aload_3
    //   157: ifnull +41 -> 198
    //   160: aload_3
    //   161: invokeinterface 551 1 0
    //   166: aload_2
    //   167: areturn
    //   168: astore_2
    //   169: aconst_null
    //   170: astore_3
    //   171: aload_3
    //   172: ifnull +9 -> 181
    //   175: aload_3
    //   176: invokeinterface 551 1 0
    //   181: aload_2
    //   182: athrow
    //   183: aconst_null
    //   184: astore_3
    //   185: aload 4
    //   187: astore_2
    //   188: aload_2
    //   189: astore 4
    //   191: aload_3
    //   192: ifnull +6 -> 198
    //   195: goto -35 -> 160
    //   198: aload 4
    //   200: areturn
    //   201: astore_2
    //   202: goto -19 -> 183
    //   205: astore_2
    //   206: goto -59 -> 147
    //   209: astore 4
    //   211: goto -71 -> 140
    //   214: iconst_0
    //   215: istore_1
    //   216: goto -133 -> 83
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	219	0	this	TencentWebViewDatabaseAdapter
    //   79	137	1	bool	boolean
    //   3	81	2	localObject1	Object
    //   143	1	2	localObject2	Object
    //   149	18	2	localObject3	Object
    //   168	14	2	localObject4	Object
    //   187	2	2	localObject5	Object
    //   201	1	2	localException1	Exception
    //   205	1	2	localException2	Exception
    //   24	168	3	localCursor	android.database.Cursor
    //   5	194	4	localObject6	Object
    //   209	1	4	localException3	Exception
    //   8	25	5	localObject7	Object
    // Exception table:
    //   from	to	target	type
    //   35	58	143	finally
    //   58	78	143	finally
    //   83	133	143	finally
    //   16	25	168	finally
    //   16	25	201	java/lang/Exception
    //   35	58	205	java/lang/Exception
    //   58	78	209	java/lang/Exception
    //   83	133	209	java/lang/Exception
  }
  
  /* Error */
  public java.util.List<SessionInfo> getAllSessionResult()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: getstatic 147	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   9: ifnull +17 -> 26
    //   12: getstatic 147	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   15: ldc_w 576
    //   18: aconst_null
    //   19: invokevirtual 559	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   22: astore_2
    //   23: goto +5 -> 28
    //   26: aconst_null
    //   27: astore_2
    //   28: aload 5
    //   30: astore_3
    //   31: aload_2
    //   32: ifnull +192 -> 224
    //   35: aload 5
    //   37: astore_3
    //   38: aload_2
    //   39: invokeinterface 498 1 0
    //   44: ifeq +180 -> 224
    //   47: new 151	java/util/ArrayList
    //   50: dup
    //   51: aload_2
    //   52: invokeinterface 562 1 0
    //   57: invokespecial 564	java/util/ArrayList:<init>	(I)V
    //   60: astore_3
    //   61: new 428	com/tencent/smtt/net/SessionInfo
    //   64: dup
    //   65: invokespecial 577	com/tencent/smtt/net/SessionInfo:<init>	()V
    //   68: astore 4
    //   70: aload 4
    //   72: aload_2
    //   73: iconst_0
    //   74: invokeinterface 506 2 0
    //   79: putfield 431	com/tencent/smtt/net/SessionInfo:cacheKey	Ljava/lang/String;
    //   82: aload 4
    //   84: aload_2
    //   85: iconst_1
    //   86: invokeinterface 510 2 0
    //   91: putfield 434	com/tencent/smtt/net/SessionInfo:masterKeyLength	I
    //   94: aload 4
    //   96: aload_2
    //   97: iconst_2
    //   98: invokeinterface 581 2 0
    //   103: putfield 438	com/tencent/smtt/net/SessionInfo:masterKey	[B
    //   106: aload 4
    //   108: aload_2
    //   109: iconst_3
    //   110: invokeinterface 510 2 0
    //   115: putfield 444	com/tencent/smtt/net/SessionInfo:sessionIDLength	I
    //   118: aload 4
    //   120: aload_2
    //   121: iconst_4
    //   122: invokeinterface 581 2 0
    //   127: putfield 447	com/tencent/smtt/net/SessionInfo:sessionID	[B
    //   130: aload 4
    //   132: aload_2
    //   133: iconst_5
    //   134: invokeinterface 510 2 0
    //   139: putfield 450	com/tencent/smtt/net/SessionInfo:sessionTicketsLength	I
    //   142: aload 4
    //   144: aload_2
    //   145: bipush 6
    //   147: invokeinterface 581 2 0
    //   152: putfield 453	com/tencent/smtt/net/SessionInfo:sessionTickets	[B
    //   155: aload 4
    //   157: aload_2
    //   158: bipush 7
    //   160: invokeinterface 514 2 0
    //   165: putfield 457	com/tencent/smtt/net/SessionInfo:sessionTicketsLifeHint	J
    //   168: aload 4
    //   170: aload_2
    //   171: bipush 8
    //   173: invokeinterface 510 2 0
    //   178: putfield 460	com/tencent/smtt/net/SessionInfo:cipherValue	I
    //   181: aload 4
    //   183: iconst_1
    //   184: putfield 585	com/tencent/smtt/net/SessionInfo:dbmode	B
    //   187: aload_3
    //   188: aload 4
    //   190: invokevirtual 571	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   193: pop
    //   194: aload_2
    //   195: invokeinterface 548 1 0
    //   200: istore_1
    //   201: iload_1
    //   202: ifne -141 -> 61
    //   205: goto +19 -> 224
    //   208: goto +56 -> 264
    //   211: astore_3
    //   212: aload_2
    //   213: astore 4
    //   215: goto +30 -> 245
    //   218: aload 4
    //   220: astore_3
    //   221: goto +43 -> 264
    //   224: aload_3
    //   225: astore 4
    //   227: aload_2
    //   228: ifnull +46 -> 274
    //   231: aload_2
    //   232: invokeinterface 551 1 0
    //   237: aload_3
    //   238: areturn
    //   239: astore_2
    //   240: aconst_null
    //   241: astore 4
    //   243: aload_2
    //   244: astore_3
    //   245: aload 4
    //   247: ifnull +10 -> 257
    //   250: aload 4
    //   252: invokeinterface 551 1 0
    //   257: aload_3
    //   258: athrow
    //   259: aconst_null
    //   260: astore_2
    //   261: aload 4
    //   263: astore_3
    //   264: aload_3
    //   265: astore 4
    //   267: aload_2
    //   268: ifnull +6 -> 274
    //   271: goto -40 -> 231
    //   274: aload 4
    //   276: areturn
    //   277: astore_2
    //   278: goto -19 -> 259
    //   281: astore_3
    //   282: goto -64 -> 218
    //   285: astore 4
    //   287: goto -79 -> 208
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	290	0	this	TencentWebViewDatabaseAdapter
    //   200	2	1	bool	boolean
    //   22	210	2	localCursor	android.database.Cursor
    //   239	5	2	localObject1	Object
    //   260	8	2	localObject2	Object
    //   277	1	2	localException1	Exception
    //   30	158	3	localObject3	Object
    //   211	1	3	localObject4	Object
    //   220	45	3	localObject5	Object
    //   281	1	3	localException2	Exception
    //   1	274	4	localObject6	Object
    //   285	1	4	localException3	Exception
    //   4	32	5	localObject7	Object
    // Exception table:
    //   from	to	target	type
    //   38	61	211	finally
    //   61	201	211	finally
    //   6	23	239	finally
    //   6	23	277	java/lang/Exception
    //   38	61	281	java/lang/Exception
    //   61	201	285	java/lang/Exception
  }
  
  public boolean getForcePinchScale(String paramString)
  {
    if ((mDatabase != null) && (!TextUtils.isEmpty(paramString))) {
      synchronized (this.mForcePinchScaleLock)
      {
        boolean bool = sForcePinchScaleDomainList.contains(paramString);
        return bool;
      }
    }
    return false;
  }
  
  /* Error */
  public int getJavaScriptOpenWindowAutomaticallyOperation(String paramString)
  {
    // Byte code:
    //   0: getstatic 147	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   3: astore 5
    //   5: iconst_m1
    //   6: istore_3
    //   7: aload 5
    //   9: ifnull +229 -> 238
    //   12: aload_1
    //   13: invokestatic 279	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   16: ifeq +5 -> 21
    //   19: iconst_m1
    //   20: ireturn
    //   21: aload_0
    //   22: getfield 173	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:mPopwindowInfoLock	Ljava/lang/Object;
    //   25: astore 7
    //   27: aload 7
    //   29: monitorenter
    //   30: aconst_null
    //   31: astore 5
    //   33: aconst_null
    //   34: astore 6
    //   36: getstatic 147	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   39: getstatic 145	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:mTableNames	[Ljava/lang/String;
    //   42: iconst_2
    //   43: aaload
    //   44: iconst_1
    //   45: anewarray 135	java/lang/String
    //   48: dup
    //   49: iconst_0
    //   50: ldc 76
    //   52: aastore
    //   53: ldc_w 351
    //   56: iconst_1
    //   57: anewarray 135	java/lang/String
    //   60: dup
    //   61: iconst_0
    //   62: aload_1
    //   63: aastore
    //   64: aconst_null
    //   65: aconst_null
    //   66: aconst_null
    //   67: invokevirtual 493	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   70: astore_1
    //   71: iload_3
    //   72: istore_2
    //   73: aload_1
    //   74: astore 6
    //   76: aload_1
    //   77: astore 5
    //   79: aload_1
    //   80: invokeinterface 498 1 0
    //   85: ifeq +24 -> 109
    //   88: aload_1
    //   89: astore 6
    //   91: aload_1
    //   92: astore 5
    //   94: aload_1
    //   95: aload_1
    //   96: ldc 76
    //   98: invokeinterface 502 2 0
    //   103: invokeinterface 510 2 0
    //   108: istore_2
    //   109: iload_2
    //   110: istore 4
    //   112: aload_1
    //   113: ifnull +99 -> 212
    //   116: aload_1
    //   117: invokeinterface 551 1 0
    //   122: iload_2
    //   123: istore 4
    //   125: goto +87 -> 212
    //   128: astore_1
    //   129: goto +89 -> 218
    //   132: astore_1
    //   133: aload 5
    //   135: astore 6
    //   137: new 198	java/lang/StringBuilder
    //   140: dup
    //   141: invokespecial 199	java/lang/StringBuilder:<init>	()V
    //   144: astore 8
    //   146: aload 5
    //   148: astore 6
    //   150: aload 8
    //   152: ldc_w 593
    //   155: invokevirtual 205	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: pop
    //   159: aload 5
    //   161: astore 6
    //   163: aload 8
    //   165: aload_1
    //   166: invokevirtual 596	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   169: pop
    //   170: aload 5
    //   172: astore 6
    //   174: ldc -45
    //   176: aload 8
    //   178: invokevirtual 212	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   181: iconst_0
    //   182: anewarray 166	java/lang/Object
    //   185: invokestatic 218	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   188: aload 5
    //   190: astore 6
    //   192: aload_1
    //   193: invokevirtual 268	java/lang/Exception:printStackTrace	()V
    //   196: iload_3
    //   197: istore 4
    //   199: aload 5
    //   201: ifnull +11 -> 212
    //   204: iload_3
    //   205: istore_2
    //   206: aload 5
    //   208: astore_1
    //   209: goto -93 -> 116
    //   212: aload 7
    //   214: monitorexit
    //   215: iload 4
    //   217: ireturn
    //   218: aload 6
    //   220: ifnull +10 -> 230
    //   223: aload 6
    //   225: invokeinterface 551 1 0
    //   230: aload_1
    //   231: athrow
    //   232: astore_1
    //   233: aload 7
    //   235: monitorexit
    //   236: aload_1
    //   237: athrow
    //   238: iconst_m1
    //   239: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	240	0	this	TencentWebViewDatabaseAdapter
    //   0	240	1	paramString	String
    //   72	134	2	i	int
    //   6	199	3	j	int
    //   110	106	4	k	int
    //   3	204	5	localObject1	Object
    //   34	190	6	localObject2	Object
    //   25	209	7	localObject3	Object
    //   144	33	8	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   36	71	128	finally
    //   79	88	128	finally
    //   94	109	128	finally
    //   137	146	128	finally
    //   150	159	128	finally
    //   163	170	128	finally
    //   174	188	128	finally
    //   192	196	128	finally
    //   36	71	132	java/lang/Exception
    //   79	88	132	java/lang/Exception
    //   94	109	132	java/lang/Exception
    //   116	122	232	finally
    //   212	215	232	finally
    //   223	230	232	finally
    //   230	232	232	finally
    //   233	236	232	finally
  }
  
  /* Error */
  public String[] getUsernamePassword(String paramString)
  {
    // Byte code:
    //   0: getstatic 147	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   3: astore_3
    //   4: aconst_null
    //   5: astore 5
    //   7: aconst_null
    //   8: astore_2
    //   9: aconst_null
    //   10: astore 4
    //   12: aconst_null
    //   13: astore 6
    //   15: aload_3
    //   16: ifnull +288 -> 304
    //   19: aload_1
    //   20: invokestatic 279	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   23: ifeq +5 -> 28
    //   26: aconst_null
    //   27: areturn
    //   28: aload_0
    //   29: getfield 169	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:mPasswordLock	Ljava/lang/Object;
    //   32: astore 7
    //   34: aload 7
    //   36: monitorenter
    //   37: getstatic 147	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   40: getstatic 145	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:mTableNames	[Ljava/lang/String;
    //   43: iconst_0
    //   44: aaload
    //   45: iconst_4
    //   46: anewarray 135	java/lang/String
    //   49: dup
    //   50: iconst_0
    //   51: ldc 67
    //   53: aastore
    //   54: dup
    //   55: iconst_1
    //   56: ldc 61
    //   58: aastore
    //   59: dup
    //   60: iconst_2
    //   61: ldc 70
    //   63: aastore
    //   64: dup
    //   65: iconst_3
    //   66: ldc 64
    //   68: aastore
    //   69: ldc_w 351
    //   72: iconst_1
    //   73: anewarray 135	java/lang/String
    //   76: dup
    //   77: iconst_0
    //   78: aload_1
    //   79: aastore
    //   80: aconst_null
    //   81: aconst_null
    //   82: aconst_null
    //   83: invokevirtual 493	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   86: astore_3
    //   87: aload 6
    //   89: astore_2
    //   90: aload 5
    //   92: astore_1
    //   93: aload_3
    //   94: invokeinterface 498 1 0
    //   99: ifeq +103 -> 202
    //   102: aload 5
    //   104: astore_1
    //   105: iconst_4
    //   106: anewarray 135	java/lang/String
    //   109: astore_2
    //   110: aload_2
    //   111: astore_1
    //   112: aload_2
    //   113: iconst_0
    //   114: aload_0
    //   115: aload_3
    //   116: aload_3
    //   117: ldc 67
    //   119: invokeinterface 502 2 0
    //   124: invokeinterface 506 2 0
    //   129: invokespecial 600	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:decryption	(Ljava/lang/String;)Ljava/lang/String;
    //   132: aastore
    //   133: aload_2
    //   134: astore_1
    //   135: aload_2
    //   136: iconst_1
    //   137: aload_0
    //   138: aload_3
    //   139: aload_3
    //   140: ldc 61
    //   142: invokeinterface 502 2 0
    //   147: invokeinterface 506 2 0
    //   152: invokespecial 600	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:decryption	(Ljava/lang/String;)Ljava/lang/String;
    //   155: aastore
    //   156: aload_2
    //   157: astore_1
    //   158: aload_2
    //   159: iconst_2
    //   160: aload_0
    //   161: aload_3
    //   162: aload_3
    //   163: ldc 70
    //   165: invokeinterface 502 2 0
    //   170: invokeinterface 506 2 0
    //   175: invokespecial 600	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:decryption	(Ljava/lang/String;)Ljava/lang/String;
    //   178: aastore
    //   179: aload_2
    //   180: astore_1
    //   181: aload_2
    //   182: iconst_3
    //   183: aload_0
    //   184: aload_3
    //   185: aload_3
    //   186: ldc 64
    //   188: invokeinterface 502 2 0
    //   193: invokeinterface 506 2 0
    //   198: invokespecial 600	android/webview/chromium/tencent/TencentWebViewDatabaseAdapter:decryption	(Ljava/lang/String;)Ljava/lang/String;
    //   201: aastore
    //   202: aload_2
    //   203: astore_1
    //   204: aload_3
    //   205: ifnull +76 -> 281
    //   208: aload_3
    //   209: invokeinterface 551 1 0
    //   214: aload_2
    //   215: astore_1
    //   216: goto +65 -> 281
    //   219: astore_1
    //   220: aload_3
    //   221: astore_2
    //   222: goto +64 -> 286
    //   225: astore 4
    //   227: aload_1
    //   228: astore_2
    //   229: aload_3
    //   230: astore_1
    //   231: aload_2
    //   232: astore_3
    //   233: goto +16 -> 249
    //   236: astore_1
    //   237: goto +49 -> 286
    //   240: astore_2
    //   241: aconst_null
    //   242: astore_3
    //   243: aload 4
    //   245: astore_1
    //   246: aload_2
    //   247: astore 4
    //   249: aload_1
    //   250: astore_2
    //   251: ldc -45
    //   253: ldc_w 601
    //   256: iconst_1
    //   257: anewarray 166	java/lang/Object
    //   260: dup
    //   261: iconst_0
    //   262: aload 4
    //   264: aastore
    //   265: invokestatic 218	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   268: aload_1
    //   269: ifnull +37 -> 306
    //   272: aload_1
    //   273: invokeinterface 551 1 0
    //   278: goto +28 -> 306
    //   281: aload 7
    //   283: monitorexit
    //   284: aload_1
    //   285: areturn
    //   286: aload_2
    //   287: ifnull +9 -> 296
    //   290: aload_2
    //   291: invokeinterface 551 1 0
    //   296: aload_1
    //   297: athrow
    //   298: astore_1
    //   299: aload 7
    //   301: monitorexit
    //   302: aload_1
    //   303: athrow
    //   304: aconst_null
    //   305: areturn
    //   306: aload_3
    //   307: astore_1
    //   308: goto -27 -> 281
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	311	0	this	TencentWebViewDatabaseAdapter
    //   0	311	1	paramString	String
    //   8	224	2	localObject1	Object
    //   240	7	2	localException1	Exception
    //   250	41	2	str	String
    //   3	304	3	localObject2	Object
    //   10	1	4	localObject3	Object
    //   225	19	4	localException2	Exception
    //   247	16	4	localException3	Exception
    //   5	98	5	localObject4	Object
    //   13	75	6	localObject5	Object
    //   32	268	7	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   93	102	219	finally
    //   105	110	219	finally
    //   112	133	219	finally
    //   135	156	219	finally
    //   158	179	219	finally
    //   181	202	219	finally
    //   93	102	225	java/lang/Exception
    //   105	110	225	java/lang/Exception
    //   112	133	225	java/lang/Exception
    //   135	156	225	java/lang/Exception
    //   158	179	225	java/lang/Exception
    //   181	202	225	java/lang/Exception
    //   37	87	236	finally
    //   251	268	236	finally
    //   37	87	240	java/lang/Exception
    //   208	214	298	finally
    //   272	278	298	finally
    //   281	284	298	finally
    //   290	296	298	finally
    //   296	298	298	finally
    //   299	302	298	finally
  }
  
  public void insertOrUpdateUsernamePassword(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if (getUsernamePassword(paramString1) == null)
    {
      insertUsernamePassword(paramString1, paramString2, paramString3, paramString4, paramString5);
      return;
    }
    updateUsernamePassword(paramString1, paramString2, paramString3, paramString4, paramString5);
  }
  
  public void setForcePinchScale(String paramString)
  {
    if (mDatabase != null)
    {
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
      synchronized (this.mForcePinchScaleLock)
      {
        synchronized (this.mForcePinchScaleLock)
        {
          ContentValues localContentValues = new ContentValues();
          try
          {
            localContentValues.put("host", paramString);
            sForcePinchScaleDomainList.add(paramString);
          }
          catch (Exception paramString)
          {
            paramString.printStackTrace();
          }
          return;
        }
      }
    }
  }
  
  public void setJavaScriptOpenWindowAutomaticallyOperation(String paramString, int paramInt)
  {
    if ((mDatabase != null) && (!TextUtils.isEmpty(paramString)))
    {
      if ((paramInt != 0) && (paramInt != 1)) {
        return;
      }
      synchronized (this.mPopwindowInfoLock)
      {
        ContentValues localContentValues = new ContentValues();
        try
        {
          localContentValues.put("host", paramString);
          localContentValues.put("operate", Integer.valueOf(paramInt));
          mDatabase.insert(mTableNames[2], "host", localContentValues);
        }
        catch (Exception paramString)
        {
          paramString.printStackTrace();
        }
        return;
      }
    }
  }
  
  public static class AdInfo
  {
    public static final byte MODE_DELETED = 2;
    public static final byte MODE_NEW = 0;
    public static final byte MODE_NORMAL = 1;
    public static final byte MODE_REPLACED = 3;
    public TencentWebViewDatabaseAdapter.Values ScriptArgumentsStatus = null;
    public TencentWebViewDatabaseAdapter.Values featureRulesStatus = null;
    public String featureRulesStr = null;
    public Map<String, TencentWebViewDatabaseAdapter.Values> filterRulesMap = null;
    public Map<String, TencentWebViewDatabaseAdapter.Values> hiddenRulesMap = null;
    public String host;
    public String scriptArguments = null;
    public boolean useThirdPartyAdRules = false;
    public Map<String, TencentWebViewDatabaseAdapter.Values> userSelectHiddenRulesMap = null;
  }
  
  public static class AdStatus
  {
    public int directStrategy = -1;
    public String domain;
    public int fixSwitch = 0;
    public boolean isTouch = false;
    public long lastRequestTime = 0L;
    public int randomIdSwitch = 0;
    
    public AdStatus(String paramString, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, long paramLong)
    {
      this.domain = paramString;
      this.isTouch = paramBoolean;
      this.directStrategy = paramInt1;
      this.randomIdSwitch = paramInt2;
      this.fixSwitch = paramInt3;
      this.lastRequestTime = paramLong;
    }
  }
  
  public static class Values
  {
    public byte dbmode;
    
    public Values(byte paramByte)
    {
      this.dbmode = paramByte;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentWebViewDatabaseAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */