package android.webview.chromium.tencent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.webview.chromium.WebIconDatabaseAdapter;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewFactory;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewFactoryProvider;
import java.io.File;
import java.util.Locale;
import org.chromium.android_webview.AwContents;
import org.chromium.base.annotations.UsedByReflection;

public class TencentWebIconDatabaseAdapter
  extends WebIconDatabaseAdapter
{
  private static final String TAG = "TencentWebIconDatabaseAdapter";
  private Context mContext = null;
  private IconDBHelper mDbHelper = null;
  
  @UsedByReflection("WebCoreProxy.java")
  public static TencentWebIconDatabaseAdapter getInstance()
  {
    return (TencentWebIconDatabaseAdapter)TencentWebViewFactory.getProvider().getWebIconDatabase();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void close()
  {
    IconDBHelper localIconDBHelper = this.mDbHelper;
    if (localIconDBHelper != null) {
      localIconDBHelper.close();
    }
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public Bitmap getIconForPageUrl(String paramString)
  {
    IconDBHelper localIconDBHelper = this.mDbHelper;
    if (localIconDBHelper != null) {
      return localIconDBHelper.getIconForPageUrl(paramString);
    }
    return null;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void open(String paramString)
  {
    if (WebSettingsExtension.getShouldRequestFavicon()) {
      AwContents.setShouldDownloadFavicons();
    }
    if (this.mContext == null) {
      this.mContext = ContextHolder.getInstance().getApplicationContext();
    }
    this.mDbHelper = IconDBHelper.getInstance(this.mContext, paramString);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void removeAllIcons()
  {
    IconDBHelper localIconDBHelper = this.mDbHelper;
    if (localIconDBHelper != null) {
      localIconDBHelper.removeAllIcons();
    }
  }
  
  public void updateReceivedIcon(final String paramString, final Bitmap paramBitmap)
  {
    try
    {
      new Thread(new Runnable()
      {
        public void run()
        {
          if (TencentWebIconDatabaseAdapter.this.mDbHelper == null) {
            return;
          }
          TencentWebIconDatabaseAdapter.this.mDbHelper.updateReceivedIcon(paramString, paramBitmap);
        }
      }).start();
      return;
    }
    catch (Throwable paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  static class IconDBHelper
    extends SQLiteOpenHelper
  {
    private static final int COL_HOST_INFO = 0;
    private static final int COL_ICON_INFO = 1;
    private static final String TAG = "IconDBHelper";
    private static final String sDbFile = "WebpageIconsX5.db";
    private static final int sDbVersion = 1;
    private static IconDBHelper sInstance;
    private static final String sTableName = "favicon";
    private final String[] sColNames = { "host", "icon" };
    
    public IconDBHelper(Context paramContext, String paramString)
    {
      super(GenDBPath(paramContext, paramString, "WebpageIconsX5.db"), null, 1);
    }
    
    private static String GenDBPath(Context paramContext, String paramString1, String paramString2)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramContext.getApplicationInfo().dataDir);
      ((StringBuilder)localObject).append("/databases/");
      localObject = ((StringBuilder)localObject).toString();
      if (paramString1 != null)
      {
        paramContext = paramString1;
        if (paramString1.length() != 0) {}
      }
      else
      {
        paramContext = (Context)localObject;
      }
      paramString1 = new File(paramContext);
      if (paramString1.exists())
      {
        paramContext = paramString1;
        if (paramString1.isDirectory()) {}
      }
      else
      {
        paramContext = new File((String)localObject);
      }
      return new File(paramContext, paramString2).getPath();
    }
    
    public static IconDBHelper getInstance(Context paramContext, String paramString)
    {
      try
      {
        if (sInstance == null) {
          sInstance = new IconDBHelper(paramContext, paramString);
        }
        paramContext = sInstance;
        return paramContext;
      }
      finally {}
    }
    
    /* Error */
    @UsedByReflection("WebCoreProxy.java")
    public Bitmap getIconForPageUrl(String paramString)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_3
      //   2: aload_0
      //   3: invokevirtual 110	android/webview/chromium/tencent/TencentWebIconDatabaseAdapter$IconDBHelper:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
      //   6: astore_2
      //   7: aload_2
      //   8: ifnonnull +5 -> 13
      //   11: aconst_null
      //   12: areturn
      //   13: aload_2
      //   14: ldc 24
      //   16: aload_0
      //   17: getfield 46	android/webview/chromium/tencent/TencentWebIconDatabaseAdapter$IconDBHelper:sColNames	[Ljava/lang/String;
      //   20: ldc 112
      //   22: iconst_1
      //   23: anewarray 40	java/lang/String
      //   26: dup
      //   27: iconst_0
      //   28: aload_1
      //   29: invokestatic 118	com/tencent/tbs/core/webkit/tencent/TencentURLUtil:getHost	(Ljava/lang/String;)Ljava/lang/String;
      //   32: aastore
      //   33: aconst_null
      //   34: aconst_null
      //   35: aconst_null
      //   36: invokevirtual 124	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
      //   39: astore_2
      //   40: aload_2
      //   41: astore_1
      //   42: aload_2
      //   43: invokeinterface 129 1 0
      //   48: iconst_1
      //   49: if_icmpne +34 -> 83
      //   52: aload_2
      //   53: astore_1
      //   54: aload_2
      //   55: aload_2
      //   56: aload_0
      //   57: getfield 46	android/webview/chromium/tencent/TencentWebIconDatabaseAdapter$IconDBHelper:sColNames	[Ljava/lang/String;
      //   60: iconst_1
      //   61: aaload
      //   62: invokeinterface 133 2 0
      //   67: invokeinterface 137 2 0
      //   72: astore_3
      //   73: aload_2
      //   74: astore_1
      //   75: aload_3
      //   76: iconst_0
      //   77: aload_3
      //   78: arraylength
      //   79: invokestatic 143	android/graphics/BitmapFactory:decodeByteArray	([BII)Landroid/graphics/Bitmap;
      //   82: astore_3
      //   83: aload_2
      //   84: ifnull +9 -> 93
      //   87: aload_2
      //   88: invokeinterface 146 1 0
      //   93: aload_3
      //   94: areturn
      //   95: astore_3
      //   96: goto +12 -> 108
      //   99: astore_1
      //   100: aconst_null
      //   101: astore_2
      //   102: goto +41 -> 143
      //   105: astore_3
      //   106: aconst_null
      //   107: astore_2
      //   108: aload_2
      //   109: astore_1
      //   110: ldc 15
      //   112: aload_3
      //   113: invokevirtual 149	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   116: invokestatic 155	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
      //   119: pop
      //   120: aload_2
      //   121: astore_1
      //   122: aload_3
      //   123: invokevirtual 158	java/lang/Exception:printStackTrace	()V
      //   126: aload_2
      //   127: ifnull +9 -> 136
      //   130: aload_2
      //   131: invokeinterface 146 1 0
      //   136: aconst_null
      //   137: areturn
      //   138: astore_3
      //   139: aload_1
      //   140: astore_2
      //   141: aload_3
      //   142: astore_1
      //   143: aload_2
      //   144: ifnull +9 -> 153
      //   147: aload_2
      //   148: invokeinterface 146 1 0
      //   153: aload_1
      //   154: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	155	0	this	IconDBHelper
      //   0	155	1	paramString	String
      //   6	142	2	localObject1	Object
      //   1	93	3	localObject2	Object
      //   95	1	3	localException1	Exception
      //   105	18	3	localException2	Exception
      //   138	4	3	localObject3	Object
      // Exception table:
      //   from	to	target	type
      //   42	52	95	java/lang/Exception
      //   54	73	95	java/lang/Exception
      //   75	83	95	java/lang/Exception
      //   2	7	99	finally
      //   13	40	99	finally
      //   2	7	105	java/lang/Exception
      //   13	40	105	java/lang/Exception
      //   42	52	138	finally
      //   54	73	138	finally
      //   75	83	138	finally
      //   110	120	138	finally
      //   122	126	138	finally
    }
    
    @SuppressLint({"NewApi"})
    public void onConfigure(SQLiteDatabase paramSQLiteDatabase)
    {
      super.onConfigure(paramSQLiteDatabase);
      paramSQLiteDatabase.setLocale(Locale.getDefault());
    }
    
    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      try
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("CREATE TABLE IF NOT EXISTS favicon (");
        localStringBuilder.append(this.sColNames[0]);
        localStringBuilder.append(" TEXT , ");
        localStringBuilder.append(this.sColNames[1]);
        localStringBuilder.append(" BYTE );");
        paramSQLiteDatabase.execSQL(localStringBuilder.toString());
        return;
      }
      catch (Exception paramSQLiteDatabase)
      {
        paramSQLiteDatabase.printStackTrace();
      }
    }
    
    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {}
    
    @UsedByReflection("WebCoreProxy.java")
    public void removeAllIcons()
    {
      try
      {
        SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
        if (localSQLiteDatabase == null) {
          return;
        }
        localSQLiteDatabase.execSQL("TRUNCATE TABLE favicon");
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    
    /* Error */
    public void updateReceivedIcon(String paramString, Bitmap paramBitmap)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore 6
      //   3: aconst_null
      //   4: astore 5
      //   6: aload 5
      //   8: astore 4
      //   10: aload 6
      //   12: astore_3
      //   13: aload_0
      //   14: invokevirtual 191	android/webview/chromium/tencent/TencentWebIconDatabaseAdapter$IconDBHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
      //   17: astore 7
      //   19: aload 7
      //   21: ifnonnull +4 -> 25
      //   24: return
      //   25: aload 5
      //   27: astore 4
      //   29: aload 6
      //   31: astore_3
      //   32: aload 7
      //   34: ldc 24
      //   36: aload_0
      //   37: getfield 46	android/webview/chromium/tencent/TencentWebIconDatabaseAdapter$IconDBHelper:sColNames	[Ljava/lang/String;
      //   40: ldc 112
      //   42: iconst_1
      //   43: anewarray 40	java/lang/String
      //   46: dup
      //   47: iconst_0
      //   48: aload_1
      //   49: invokestatic 118	com/tencent/tbs/core/webkit/tencent/TencentURLUtil:getHost	(Ljava/lang/String;)Ljava/lang/String;
      //   52: aastore
      //   53: aconst_null
      //   54: aconst_null
      //   55: aconst_null
      //   56: invokevirtual 124	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
      //   59: astore 5
      //   61: aload 5
      //   63: astore 4
      //   65: aload 5
      //   67: astore_3
      //   68: new 197	java/io/ByteArrayOutputStream
      //   71: dup
      //   72: invokespecial 198	java/io/ByteArrayOutputStream:<init>	()V
      //   75: astore 6
      //   77: aload 5
      //   79: astore 4
      //   81: aload 5
      //   83: astore_3
      //   84: aload_2
      //   85: getstatic 204	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
      //   88: bipush 100
      //   90: aload 6
      //   92: invokevirtual 210	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
      //   95: pop
      //   96: aload 5
      //   98: astore 4
      //   100: aload 5
      //   102: astore_3
      //   103: new 212	android/content/ContentValues
      //   106: dup
      //   107: invokespecial 213	android/content/ContentValues:<init>	()V
      //   110: astore_2
      //   111: aload 5
      //   113: astore 4
      //   115: aload 5
      //   117: astore_3
      //   118: aload 5
      //   120: invokeinterface 129 1 0
      //   125: iconst_1
      //   126: if_icmpne +57 -> 183
      //   129: aload 5
      //   131: astore 4
      //   133: aload 5
      //   135: astore_3
      //   136: aload_2
      //   137: aload_0
      //   138: getfield 46	android/webview/chromium/tencent/TencentWebIconDatabaseAdapter$IconDBHelper:sColNames	[Ljava/lang/String;
      //   141: iconst_1
      //   142: aaload
      //   143: aload 6
      //   145: invokevirtual 217	java/io/ByteArrayOutputStream:toByteArray	()[B
      //   148: invokevirtual 221	android/content/ContentValues:put	(Ljava/lang/String;[B)V
      //   151: aload 5
      //   153: astore 4
      //   155: aload 5
      //   157: astore_3
      //   158: aload 7
      //   160: ldc 24
      //   162: aload_2
      //   163: ldc 112
      //   165: iconst_1
      //   166: anewarray 40	java/lang/String
      //   169: dup
      //   170: iconst_0
      //   171: aload_1
      //   172: invokestatic 118	com/tencent/tbs/core/webkit/tencent/TencentURLUtil:getHost	(Ljava/lang/String;)Ljava/lang/String;
      //   175: aastore
      //   176: invokevirtual 225	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
      //   179: pop
      //   180: goto +68 -> 248
      //   183: aload 5
      //   185: astore 4
      //   187: aload 5
      //   189: astore_3
      //   190: aload_2
      //   191: aload_0
      //   192: getfield 46	android/webview/chromium/tencent/TencentWebIconDatabaseAdapter$IconDBHelper:sColNames	[Ljava/lang/String;
      //   195: iconst_0
      //   196: aaload
      //   197: aload_1
      //   198: invokestatic 118	com/tencent/tbs/core/webkit/tencent/TencentURLUtil:getHost	(Ljava/lang/String;)Ljava/lang/String;
      //   201: invokevirtual 228	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
      //   204: aload 5
      //   206: astore 4
      //   208: aload 5
      //   210: astore_3
      //   211: aload_2
      //   212: aload_0
      //   213: getfield 46	android/webview/chromium/tencent/TencentWebIconDatabaseAdapter$IconDBHelper:sColNames	[Ljava/lang/String;
      //   216: iconst_1
      //   217: aaload
      //   218: aload 6
      //   220: invokevirtual 217	java/io/ByteArrayOutputStream:toByteArray	()[B
      //   223: invokevirtual 221	android/content/ContentValues:put	(Ljava/lang/String;[B)V
      //   226: aload 5
      //   228: astore 4
      //   230: aload 5
      //   232: astore_3
      //   233: aload 7
      //   235: ldc 24
      //   237: aload_0
      //   238: getfield 46	android/webview/chromium/tencent/TencentWebIconDatabaseAdapter$IconDBHelper:sColNames	[Ljava/lang/String;
      //   241: iconst_1
      //   242: aaload
      //   243: aload_2
      //   244: invokevirtual 232	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
      //   247: pop2
      //   248: aload 5
      //   250: astore 4
      //   252: aload 5
      //   254: astore_3
      //   255: aload 5
      //   257: invokeinterface 146 1 0
      //   262: aload 5
      //   264: ifnull +44 -> 308
      //   267: aload 5
      //   269: astore_3
      //   270: goto +32 -> 302
      //   273: astore_1
      //   274: goto +35 -> 309
      //   277: astore_1
      //   278: aload_3
      //   279: astore 4
      //   281: ldc 15
      //   283: aload_1
      //   284: invokevirtual 149	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   287: invokestatic 155	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
      //   290: pop
      //   291: aload_3
      //   292: astore 4
      //   294: aload_1
      //   295: invokevirtual 158	java/lang/Exception:printStackTrace	()V
      //   298: aload_3
      //   299: ifnull +9 -> 308
      //   302: aload_3
      //   303: invokeinterface 146 1 0
      //   308: return
      //   309: aload 4
      //   311: ifnull +10 -> 321
      //   314: aload 4
      //   316: invokeinterface 146 1 0
      //   321: aload_1
      //   322: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	323	0	this	IconDBHelper
      //   0	323	1	paramString	String
      //   0	323	2	paramBitmap	Bitmap
      //   12	291	3	localObject1	Object
      //   8	307	4	localObject2	Object
      //   4	264	5	localCursor	android.database.Cursor
      //   1	218	6	localByteArrayOutputStream	java.io.ByteArrayOutputStream
      //   17	217	7	localSQLiteDatabase	SQLiteDatabase
      // Exception table:
      //   from	to	target	type
      //   13	19	273	finally
      //   32	61	273	finally
      //   68	77	273	finally
      //   84	96	273	finally
      //   103	111	273	finally
      //   118	129	273	finally
      //   136	151	273	finally
      //   158	180	273	finally
      //   190	204	273	finally
      //   211	226	273	finally
      //   233	248	273	finally
      //   255	262	273	finally
      //   281	291	273	finally
      //   294	298	273	finally
      //   13	19	277	java/lang/Exception
      //   32	61	277	java/lang/Exception
      //   68	77	277	java/lang/Exception
      //   84	96	277	java/lang/Exception
      //   103	111	277	java/lang/Exception
      //   118	129	277	java/lang/Exception
      //   136	151	277	java/lang/Exception
      //   158	180	277	java/lang/Exception
      //   190	204	277	java/lang/Exception
      //   211	226	277	java/lang/Exception
      //   233	248	277	java/lang/Exception
      //   255	262	277	java/lang/Exception
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentWebIconDatabaseAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */