package com.tencent.smtt.jsApi.impl;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import android.widget.Toast;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.SignatureUtil;
import com.tencent.common.utils.bitmap.BitmapUtils;
import com.tencent.smtt.jsApi.export.IOpenJsApis;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.utils.DeviceUtils;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class jsApp
  implements IOpenJsApis
{
  public static final String ACTION_SHORT_CUT = "com.tencent.QQBrowser.action.SHORTCUT";
  private static int jdField_a_of_type_Int = -1;
  private static String jdField_a_of_type_JavaLangString = "jsApi@app";
  private Context jdField_a_of_type_AndroidContentContext;
  private Bitmap jdField_a_of_type_AndroidGraphicsBitmap;
  OpenJsHelper jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
  private String b = null;
  
  public jsApp(OpenJsHelper paramOpenJsHelper)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper = paramOpenJsHelper;
    paramOpenJsHelper = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
    if (paramOpenJsHelper != null) {
      this.jdField_a_of_type_AndroidContentContext = paramOpenJsHelper.getContext();
    }
  }
  
  private int a()
  {
    return ((ActivityManager)this.jdField_a_of_type_AndroidContentContext.getSystemService("activity")).getLauncherLargeIconSize();
  }
  
  private static ProviderInfo a(PackageInfo paramPackageInfo)
  {
    Object localObject1;
    if ((paramPackageInfo != null) && (paramPackageInfo.providers != null) && (paramPackageInfo.providers.length > 0))
    {
      ProviderInfo localProviderInfo2 = a(paramPackageInfo.packageName, paramPackageInfo.providers);
      localObject1 = localProviderInfo2;
      if (localProviderInfo2 == null)
      {
        localObject1 = paramPackageInfo.providers;
        int j = localObject1.length;
        int i = 0;
        ProviderInfo localProviderInfo1;
        for (;;)
        {
          localProviderInfo1 = localProviderInfo2;
          if (i >= j) {
            break;
          }
          localProviderInfo1 = localObject1[i];
          Object localObject2 = jdField_a_of_type_JavaLangString;
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("name : ");
          ((StringBuilder)localObject2).append(localProviderInfo1.name);
          ((StringBuilder)localObject2).toString();
          localObject2 = jdField_a_of_type_JavaLangString;
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("authority : ");
          ((StringBuilder)localObject2).append(localProviderInfo1.authority);
          ((StringBuilder)localObject2).toString();
          localObject2 = jdField_a_of_type_JavaLangString;
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("labelRes : ");
          ((StringBuilder)localObject2).append(localProviderInfo1.labelRes);
          ((StringBuilder)localObject2).toString();
          localObject2 = jdField_a_of_type_JavaLangString;
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("readPermission : ");
          ((StringBuilder)localObject2).append(localProviderInfo1.readPermission);
          ((StringBuilder)localObject2).toString();
          localObject2 = jdField_a_of_type_JavaLangString;
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("writePermission : ");
          ((StringBuilder)localObject2).append(localProviderInfo1.writePermission);
          ((StringBuilder)localObject2).toString();
          localObject2 = jdField_a_of_type_JavaLangString;
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("enabled : ");
          ((StringBuilder)localObject2).append(localProviderInfo1.enabled);
          ((StringBuilder)localObject2).toString();
          localObject2 = jdField_a_of_type_JavaLangString;
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("exported : ");
          ((StringBuilder)localObject2).append(localProviderInfo1.exported);
          ((StringBuilder)localObject2).toString();
          if ((!TextUtils.isEmpty(localProviderInfo1.readPermission)) && (localProviderInfo1.exported) && (localProviderInfo1.readPermission.contains("READ_SETTINGS")) && ((!TextUtils.isEmpty(localProviderInfo1.writePermission)) && (localProviderInfo1.writePermission.contains("WRITE_SETTINGS")))) {
            break;
          }
          i += 1;
        }
        localObject1 = localProviderInfo1;
        if (localProviderInfo1 == null) {
          return paramPackageInfo.providers[0];
        }
      }
    }
    else
    {
      localObject1 = null;
    }
    return (ProviderInfo)localObject1;
  }
  
  private static ProviderInfo a(String paramString, ProviderInfo[] paramArrayOfProviderInfo)
  {
    boolean bool = paramString.equals("com.baidu.launcher");
    int j = 0;
    int i = 0;
    if (bool)
    {
      j = paramArrayOfProviderInfo.length;
      while (i < j)
      {
        paramString = paramArrayOfProviderInfo[i];
        if (paramString.authority.equals("com.baidu.launcher")) {
          return paramString;
        }
        i += 1;
      }
    }
    if (paramString.equals("com.baidu.home2"))
    {
      int k = paramArrayOfProviderInfo.length;
      i = j;
      while (i < k)
      {
        paramString = paramArrayOfProviderInfo[i];
        if (paramString.authority.equals("com.baidu.home2")) {
          return paramString;
        }
        i += 1;
      }
    }
    return null;
  }
  
  private Bitmap a(Bitmap paramBitmap)
  {
    if (paramBitmap != null)
    {
      if (paramBitmap.isRecycled()) {
        return null;
      }
      if (jdField_a_of_type_Int == -1) {
        if (DeviceUtils.getSdkVersion() >= 11) {
          jdField_a_of_type_Int = a();
        } else {
          jdField_a_of_type_Int = 48;
        }
      }
      if ((jdField_a_of_type_Int > 0) && ((paramBitmap.getWidth() != jdField_a_of_type_Int) || (paramBitmap.getHeight() != jdField_a_of_type_Int))) {
        try
        {
          paramBitmap = Bitmap.createScaledBitmap(paramBitmap, jdField_a_of_type_Int, jdField_a_of_type_Int, true);
          return paramBitmap;
        }
        catch (OutOfMemoryError paramBitmap)
        {
          paramBitmap.printStackTrace();
          return null;
        }
      }
      return paramBitmap;
    }
    return null;
  }
  
  private Bitmap a(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    try
    {
      paramString = com.tencent.common.utils.Base64.decode(paramString);
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
      paramString = null;
    }
    return BitmapUtils.decodeBitmap(paramString, null);
  }
  
  private static Uri a(Context paramContext, String paramString)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getApplicationInfo(paramString, 128);
      if (paramContext != null)
      {
        if (paramContext.metaData == null) {
          return null;
        }
        paramContext = paramContext.metaData.getString("launcher_authority");
        if (!TextUtils.isEmpty(paramContext))
        {
          paramString = new StringBuilder();
          paramString.append("content://");
          paramString.append(paramContext);
          paramString.append("/item");
          paramContext = Uri.parse(paramString.toString());
          return paramContext;
        }
      }
      else
      {
        return null;
      }
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  private static Uri a(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return null;
    }
    if ((!TextUtils.equals(paramString1, "com.nd.android.pandahome2")) && (!TextUtils.equals(paramString1, "com.nd.android.smarthome"))) {
      return null;
    }
    paramString1 = new StringBuilder();
    paramString1.append("content://");
    paramString1.append(paramString2);
    paramString1.append("/favorites1/favorites/?notify=true");
    return Uri.parse(paramString1.toString());
  }
  
  private static String a(Context paramContext)
  {
    Object localObject = a(paramContext);
    if (((ArrayList)localObject).size() == 0) {
      return null;
    }
    paramContext = (ActivityManager)paramContext.getSystemService("activity");
    try
    {
      paramContext = paramContext.getRunningTasks(50);
    }
    catch (Throwable paramContext)
    {
      paramContext.toString();
      paramContext = null;
    }
    if (paramContext != null)
    {
      if (paramContext.isEmpty()) {
        return null;
      }
      Iterator localIterator = paramContext.iterator();
      while (localIterator.hasNext())
      {
        paramContext = ((ActivityManager.RunningTaskInfo)localIterator.next()).topActivity.getPackageName();
        if (((ArrayList)localObject).contains(paramContext))
        {
          localObject = jdField_a_of_type_JavaLangString;
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("getRunningHomePackage => ");
          ((StringBuilder)localObject).append(paramContext);
          ((StringBuilder)localObject).toString();
          return paramContext;
        }
      }
      return null;
    }
    return null;
  }
  
  /* Error */
  private String a(Bitmap paramBitmap)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 34	com/tencent/smtt/jsApi/impl/jsApp:b	Ljava/lang/String;
    //   4: ifnonnull +98 -> 102
    //   7: aload_0
    //   8: getfield 44	com/tencent/smtt/jsApi/impl/jsApp:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   11: invokestatic 296	com/tencent/smtt/jsApi/impl/jsApp:getSDcardDir	(Landroid/content/Context;)Ljava/io/File;
    //   14: astore_2
    //   15: aload_2
    //   16: ifnull +79 -> 95
    //   19: new 75	java/lang/StringBuilder
    //   22: dup
    //   23: invokespecial 76	java/lang/StringBuilder:<init>	()V
    //   26: astore_3
    //   27: aload_3
    //   28: aload_2
    //   29: invokevirtual 301	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   32: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: pop
    //   36: aload_3
    //   37: getstatic 304	java/io/File:separator	Ljava/lang/String;
    //   40: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: pop
    //   44: aload_3
    //   45: ldc_w 306
    //   48: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: pop
    //   52: aload_3
    //   53: getstatic 304	java/io/File:separator	Ljava/lang/String;
    //   56: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: pop
    //   60: aload_3
    //   61: ldc_w 308
    //   64: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   67: pop
    //   68: aload_3
    //   69: getstatic 304	java/io/File:separator	Ljava/lang/String;
    //   72: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: pop
    //   76: aload_3
    //   77: ldc_w 310
    //   80: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   83: pop
    //   84: aload_0
    //   85: aload_3
    //   86: invokevirtual 91	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   89: putfield 34	com/tencent/smtt/jsApi/impl/jsApp:b	Ljava/lang/String;
    //   92: goto +10 -> 102
    //   95: aload_0
    //   96: ldc_w 312
    //   99: putfield 34	com/tencent/smtt/jsApi/impl/jsApp:b	Ljava/lang/String;
    //   102: aconst_null
    //   103: astore 5
    //   105: aconst_null
    //   106: astore_3
    //   107: aload_1
    //   108: ifnull +178 -> 286
    //   111: aload_0
    //   112: getfield 34	com/tencent/smtt/jsApi/impl/jsApp:b	Ljava/lang/String;
    //   115: astore_2
    //   116: aload_2
    //   117: ifnull +169 -> 286
    //   120: aload_2
    //   121: ldc_w 312
    //   124: if_acmpne +6 -> 130
    //   127: goto +159 -> 286
    //   130: new 298	java/io/File
    //   133: dup
    //   134: aload_2
    //   135: invokespecial 315	java/io/File:<init>	(Ljava/lang/String;)V
    //   138: astore 6
    //   140: ldc_w 312
    //   143: astore 4
    //   145: aload_3
    //   146: astore_2
    //   147: aload 6
    //   149: invokevirtual 318	java/io/File:exists	()Z
    //   152: ifne +11 -> 163
    //   155: aload_3
    //   156: astore_2
    //   157: aload 6
    //   159: invokevirtual 321	java/io/File:mkdirs	()Z
    //   162: pop
    //   163: aload_3
    //   164: astore_2
    //   165: new 298	java/io/File
    //   168: dup
    //   169: aload_0
    //   170: getfield 34	com/tencent/smtt/jsApi/impl/jsApp:b	Ljava/lang/String;
    //   173: ldc_w 323
    //   176: invokespecial 326	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   179: astore 6
    //   181: aload_3
    //   182: astore_2
    //   183: new 328	java/io/FileOutputStream
    //   186: dup
    //   187: aload 6
    //   189: invokespecial 331	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   192: astore_3
    //   193: aload_1
    //   194: getstatic 337	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   197: bipush 70
    //   199: aload_3
    //   200: invokevirtual 341	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   203: pop
    //   204: aload 6
    //   206: invokevirtual 301	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   209: astore_1
    //   210: aload_1
    //   211: astore_2
    //   212: aload_3
    //   213: invokevirtual 344	java/io/FileOutputStream:close	()V
    //   216: aload_1
    //   217: areturn
    //   218: astore_1
    //   219: aload_1
    //   220: invokevirtual 192	java/lang/Exception:printStackTrace	()V
    //   223: aload_2
    //   224: areturn
    //   225: astore_1
    //   226: aload_3
    //   227: astore_2
    //   228: goto +40 -> 268
    //   231: astore_2
    //   232: aload_3
    //   233: astore_1
    //   234: aload_2
    //   235: astore_3
    //   236: goto +11 -> 247
    //   239: astore_1
    //   240: goto +28 -> 268
    //   243: astore_3
    //   244: aload 5
    //   246: astore_1
    //   247: aload_1
    //   248: astore_2
    //   249: aload_3
    //   250: invokevirtual 192	java/lang/Exception:printStackTrace	()V
    //   253: aload_1
    //   254: ifnull +10 -> 264
    //   257: aload 4
    //   259: astore_2
    //   260: aload_1
    //   261: invokevirtual 344	java/io/FileOutputStream:close	()V
    //   264: ldc_w 312
    //   267: areturn
    //   268: aload_2
    //   269: ifnull +15 -> 284
    //   272: aload_2
    //   273: invokevirtual 344	java/io/FileOutputStream:close	()V
    //   276: goto +8 -> 284
    //   279: astore_2
    //   280: aload_2
    //   281: invokevirtual 192	java/lang/Exception:printStackTrace	()V
    //   284: aload_1
    //   285: athrow
    //   286: getstatic 73	com/tencent/smtt/jsApi/impl/jsApp:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   289: astore_1
    //   290: aconst_null
    //   291: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	292	0	this	jsApp
    //   0	292	1	paramBitmap	Bitmap
    //   14	214	2	localObject1	Object
    //   231	4	2	localException1	Exception
    //   248	25	2	localObject2	Object
    //   279	2	2	localException2	Exception
    //   26	210	3	localObject3	Object
    //   243	7	3	localException3	Exception
    //   143	115	4	str	String
    //   103	142	5	localObject4	Object
    //   138	67	6	localFile	File
    // Exception table:
    //   from	to	target	type
    //   212	216	218	java/lang/Exception
    //   260	264	218	java/lang/Exception
    //   193	210	225	finally
    //   193	210	231	java/lang/Exception
    //   147	155	239	finally
    //   157	163	239	finally
    //   165	181	239	finally
    //   183	193	239	finally
    //   249	253	239	finally
    //   147	155	243	java/lang/Exception
    //   157	163	243	java/lang/Exception
    //   165	181	243	java/lang/Exception
    //   183	193	243	java/lang/Exception
    //   272	276	279	java/lang/Exception
  }
  
  private static String a(String paramString1, String paramString2, int paramInt)
  {
    paramString2 = paramString1;
    if (paramString1.indexOf("//") == -1)
    {
      paramString2 = paramString1;
      if (paramString1.indexOf("://") == -1)
      {
        paramString2 = new StringBuilder();
        paramString2.append("http://");
        paramString2.append(paramString1.trim());
        paramString2 = paramString2.toString();
      }
    }
    return paramString2;
  }
  
  private String a(JSONObject paramJSONObject, String paramString)
  {
    if (!a())
    {
      a(paramString, 1, "");
      return null;
    }
    if (PublicSettingManager.getInstance().getIsUploadPvinWup()) {
      TBSStatManager.getInstance().userBehaviorStatistics("PAGESAVE10", 1, true, false);
    }
    X5CoreBeaconUploader.getInstance(this.jdField_a_of_type_AndroidContentContext.getApplicationContext()).userBehaviorStatistics("PAGESAVE10");
    b(paramString);
    return null;
  }
  
  private static ArrayList<String> a(Context paramContext)
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      paramContext = paramContext.getPackageManager();
      Intent localIntent = new Intent("android.intent.action.MAIN");
      localIntent.addCategory("android.intent.category.HOME");
      paramContext = paramContext.queryIntentActivities(localIntent, 65536).iterator();
      while (paramContext.hasNext()) {
        localArrayList.add(((ResolveInfo)paramContext.next()).activityInfo.packageName);
      }
      return localArrayList;
    }
    catch (Exception paramContext) {}
    return localArrayList;
  }
  
  private void a(String paramString)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("ret", " fail");
      this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendFailJsCallback(paramString, localJSONObject);
      return;
    }
    catch (Exception paramString) {}
  }
  
  private void a(String paramString1, String paramString2, Bitmap paramBitmap, int paramInt, Intent paramIntent, boolean paramBoolean, String paramString3)
  {
    if (TextUtils.isEmpty(paramString2)) {
      return;
    }
    paramString3 = a(paramBitmap);
    Intent localIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
    Context localContext = this.jdField_a_of_type_AndroidContentContext;
    paramBitmap = b(localContext);
    paramString1 = paramBitmap;
    if (TextUtils.isEmpty(paramBitmap)) {
      paramString1 = a(localContext);
    }
    if (paramString1 != null) {
      localIntent.setPackage(paramString1);
    }
    localIntent.putExtra("android.intent.extra.shortcut.INTENT", paramIntent);
    localIntent.putExtra("android.intent.extra.shortcut.NAME", paramString2);
    localIntent.putExtra("android.intent.extra.shortcut.ICON", paramString3);
    localContext.sendBroadcast(localIntent);
    if (paramBoolean) {
      Toast.makeText(this.jdField_a_of_type_AndroidContentContext, "已添加至手机桌面", 2000).show();
    }
  }
  
  private void a(String paramString1, String paramString2, Bitmap paramBitmap, int paramInt, boolean paramBoolean, String paramString3)
  {
    Uri localUri = Uri.parse(a(paramString1, paramString2, paramInt));
    Intent localIntent;
    if (paramString3.equals("0"))
    {
      if (SignatureUtil.getInstalledPKGInfo("com.tencent.mtt", this.jdField_a_of_type_AndroidContentContext, 0) != null)
      {
        localIntent = new Intent("com.tencent.QQBrowser.action.SHORTCUT", localUri);
      }
      else
      {
        localIntent = new Intent();
        localIntent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        localIntent.setData(localUri);
      }
    }
    else {
      localIntent = null;
    }
    if (localIntent == null) {
      return;
    }
    localIntent.addCategory("android.intent.category.DEFAULT");
    a(paramString1, paramString2, paramBitmap, paramInt, localIntent, paramBoolean, paramString3);
  }
  
  private boolean a(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    paramString = isShortcutExists(new String[] { paramString });
    if (paramString != null)
    {
      if (paramString.length < 1) {
        return false;
      }
      return paramString[0];
    }
    return false;
  }
  
  private static Uri b(Context paramContext, String paramString)
  {
    try
    {
      Uri localUri = a(paramContext, paramString);
      if (localUri != null) {
        return localUri;
      }
      paramContext = a(paramContext.getPackageManager().getPackageInfo(paramString, 8));
      if (paramContext != null)
      {
        paramString = a(paramString, paramContext.authority);
        if (paramString != null) {
          return paramString;
        }
        paramString = new StringBuilder();
        paramString.append("content://");
        paramString.append(paramContext.authority);
        paramString.append("/favorites?notify=true");
        paramContext = Uri.parse(paramString.toString());
        return paramContext;
      }
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  private static String b(Context paramContext)
  {
    if (paramContext != null)
    {
      Intent localIntent = new Intent("android.intent.action.MAIN");
      localIntent.addCategory("android.intent.category.HOME");
      try
      {
        paramContext = paramContext.getPackageManager().resolveActivity(localIntent, 65536);
        if (paramContext != null)
        {
          if (paramContext.activityInfo == null) {
            return "";
          }
          if (!TextUtils.isEmpty(paramContext.activityInfo.packageName))
          {
            if (TextUtils.equals("android", paramContext.activityInfo.packageName)) {
              return "";
            }
            paramContext = paramContext.activityInfo.packageName;
            return paramContext;
          }
        }
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return "";
  }
  
  private void b(final String paramString)
  {
    final Object localObject1 = new File("/sdcard/QQBrowser/网页保存");
    if (!((File)localObject1).exists()) {
      ((File)localObject1).mkdirs();
    }
    String str1 = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getTitle();
    String str2 = ((File)localObject1).getAbsolutePath();
    localObject1 = "";
    if (TextUtils.isEmpty(str1)) {
      localObject1 = "(1)";
    }
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(str1);
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(".mht");
    localObject1 = FileUtils.renameFileIfExist(str2, ((StringBuilder)localObject2).toString()).replaceAll("%", "");
    localObject2 = new File((String)localObject1);
    int i = 1;
    while (((File)localObject2).exists())
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("(");
      ((StringBuilder)localObject1).append(i);
      ((StringBuilder)localObject1).append(")");
      localObject1 = ((StringBuilder)localObject1).toString();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(str1);
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append(".mht");
      localObject1 = FileUtils.renameFileIfExist(str2, ((StringBuilder)localObject2).toString()).replaceAll("%", "");
      localObject2 = new File((String)localObject1);
      i += 1;
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(str2);
    ((StringBuilder)localObject2).append(File.separator);
    ((StringBuilder)localObject2).append((String)localObject1);
    localObject1 = ((StringBuilder)localObject2).toString();
    paramString = new ValueCallback()
    {
      public void onReceiveValue(String paramAnonymousString)
      {
        if (paramAnonymousString != null)
        {
          jsApp.this.a(paramString, 0, localObject1);
          return;
        }
        jsApp.this.a(paramString, 1, localObject1);
      }
    };
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.savePageToDisk((String)localObject1, true, 2, paramString);
  }
  
  private void b(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return;
    }
    PublicSettingManager localPublicSettingManager = PublicSettingManager.getInstance();
    if ((!localPublicSettingManager.getHasReportLauncherPkgName()) && (TextUtils.isEmpty(localPublicSettingManager.getLauncherPkgName())))
    {
      String str = paramString2;
      if (!TextUtils.isEmpty(paramString2)) {
        str = paramString2.replaceAll("=", " ");
      }
      paramString2 = new StringBuilder();
      paramString2.append(paramString1);
      paramString2.append(" ");
      paramString2.append(str);
      localPublicSettingManager.putLauncherPkgName(paramString2.toString());
    }
  }
  
  public static File getSDcardDir(Context paramContext)
  {
    if (paramContext != null) {}
    for (;;)
    {
      try
      {
        paramContext = paramContext.getExternalCacheDir();
        localObject = paramContext;
        if (paramContext != null) {
          continue;
        }
        localObject = Environment.getExternalStorageDirectory();
      }
      catch (Exception paramContext)
      {
        Object localObject;
        continue;
      }
      paramContext.printStackTrace();
      continue;
      if (localObject != null) {
        return (File)localObject;
      }
      paramContext = new File("/mnt/sdcard");
      if (paramContext.exists()) {
        return paramContext;
      }
      paramContext = new File("/storage/sdcard0");
      if (paramContext.exists()) {
        return paramContext;
      }
      return null;
      paramContext = null;
    }
  }
  
  public static boolean isQbInstalled(Context paramContext)
  {
    if (paramContext == null) {
      return false;
    }
    paramContext = paramContext.getPackageManager();
    try
    {
      paramContext = paramContext.getPackageInfo("com.tencent.mtt", 128);
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    paramContext = null;
    return paramContext != null;
  }
  
  void a(String paramString1, int paramInt, String paramString2)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("ret", paramInt);
      localJSONObject.put("path", paramString2);
      this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendSuccJsCallback(paramString1, localJSONObject);
      return;
    }
    catch (Exception paramString1) {}
  }
  
  void a(String paramString1, String paramString2)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("ret", paramString2);
      this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendSuccJsCallback(paramString1, localJSONObject);
      return;
    }
    catch (Exception paramString1) {}
  }
  
  void a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    a(paramString2, paramString1, a(paramString3), -1, false, paramString5);
    a(paramString4, "true");
  }
  
  protected boolean a()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("app");
    ((StringBuilder)localObject).append(".");
    ((StringBuilder)localObject).append(Thread.currentThread().getStackTrace()[3].getMethodName());
    localObject = ((StringBuilder)localObject).toString();
    if (this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.checkJsApiDomain((String)localObject) == 1) {
      return true;
    }
    return this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.checkQQDomain();
  }
  
  public void destroy()
  {
    Bitmap localBitmap = this.jdField_a_of_type_AndroidGraphicsBitmap;
    if ((localBitmap != null) && (!localBitmap.isRecycled()))
    {
      this.jdField_a_of_type_AndroidGraphicsBitmap.recycle();
      this.jdField_a_of_type_AndroidGraphicsBitmap = null;
    }
  }
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.setOriginUrl(paramString3);
    if ("isLinkOnDesktop".equals(paramString1)) {
      return isLinkOnDesktop(paramJSONObject, paramString2);
    }
    if ("sendLinkToDesktop".equals(paramString1)) {
      return sendLinkToDesktop(paramJSONObject, paramString2);
    }
    if ("saveOfflinePage".equals(paramString1)) {
      return a(paramJSONObject, paramString2);
    }
    if (("sharePicture".equals(paramString1)) && (paramJSONObject != null)) {
      return sharePicture(paramJSONObject);
    }
    return null;
  }
  
  public String isLinkOnDesktop(JSONObject paramJSONObject, String paramString)
  {
    if (!a())
    {
      paramJSONObject = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
      a(paramString, "this api without user authorization");
      return null;
    }
    try
    {
      paramJSONObject = paramJSONObject.getString("url");
      if (TextUtils.isEmpty(paramJSONObject)) {
        return null;
      }
      if (a(paramJSONObject))
      {
        a(paramString, "true");
        return null;
      }
      a(paramString, "false");
      return null;
    }
    catch (Exception paramJSONObject)
    {
      for (;;) {}
    }
    a(paramString);
    return null;
  }
  
  /* Error */
  public boolean[] isShortcutExists(String[] paramArrayOfString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 7
    //   3: aconst_null
    //   4: astore 5
    //   6: aload_1
    //   7: ifnull +283 -> 290
    //   10: aload_1
    //   11: arraylength
    //   12: ifne +5 -> 17
    //   15: aconst_null
    //   16: areturn
    //   17: aload_1
    //   18: arraylength
    //   19: newarray <illegal type>
    //   21: astore 8
    //   23: aload_0
    //   24: getfield 44	com/tencent/smtt/jsApi/impl/jsApp:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   27: invokestatic 449	com/tencent/smtt/jsApi/impl/jsApp:b	(Landroid/content/Context;)Ljava/lang/String;
    //   30: astore_3
    //   31: aload_3
    //   32: astore 6
    //   34: aload_3
    //   35: invokestatic 134	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   38: ifeq +12 -> 50
    //   41: aload_0
    //   42: getfield 44	com/tencent/smtt/jsApi/impl/jsApp:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   45: invokestatic 451	com/tencent/smtt/jsApi/impl/jsApp:a	(Landroid/content/Context;)Ljava/lang/String;
    //   48: astore 6
    //   50: aload 5
    //   52: astore 4
    //   54: aload 7
    //   56: astore_3
    //   57: aload_0
    //   58: getfield 44	com/tencent/smtt/jsApi/impl/jsApp:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   61: aload 6
    //   63: invokestatic 679	com/tencent/smtt/jsApi/impl/jsApp:b	(Landroid/content/Context;Ljava/lang/String;)Landroid/net/Uri;
    //   66: astore 9
    //   68: aload 9
    //   70: ifnonnull +22 -> 92
    //   73: aload 5
    //   75: astore 4
    //   77: aload 7
    //   79: astore_3
    //   80: aload_0
    //   81: aload 6
    //   83: ldc_w 681
    //   86: invokespecial 683	com/tencent/smtt/jsApi/impl/jsApp:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   89: aload 8
    //   91: areturn
    //   92: aload 5
    //   94: astore 4
    //   96: aload 7
    //   98: astore_3
    //   99: aload_0
    //   100: getfield 44	com/tencent/smtt/jsApi/impl/jsApp:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   103: invokevirtual 687	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   106: aload 9
    //   108: iconst_1
    //   109: anewarray 138	java/lang/String
    //   112: dup
    //   113: iconst_0
    //   114: ldc_w 689
    //   117: aastore
    //   118: aconst_null
    //   119: aconst_null
    //   120: aconst_null
    //   121: invokevirtual 695	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   124: astore 5
    //   126: aload 5
    //   128: ifnull +83 -> 211
    //   131: aload 5
    //   133: astore 4
    //   135: aload 5
    //   137: astore_3
    //   138: aload 5
    //   140: invokeinterface 700 1 0
    //   145: ifeq +82 -> 227
    //   148: aload 5
    //   150: astore 4
    //   152: aload 5
    //   154: astore_3
    //   155: aload 5
    //   157: iconst_0
    //   158: invokeinterface 703 2 0
    //   163: astore 7
    //   165: iconst_0
    //   166: istore_2
    //   167: aload 5
    //   169: astore 4
    //   171: aload 5
    //   173: astore_3
    //   174: iload_2
    //   175: aload_1
    //   176: arraylength
    //   177: if_icmpge -46 -> 131
    //   180: aload 7
    //   182: ifnull +110 -> 292
    //   185: aload 5
    //   187: astore 4
    //   189: aload 5
    //   191: astore_3
    //   192: aload 7
    //   194: aload_1
    //   195: iload_2
    //   196: aaload
    //   197: invokevirtual 141	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   200: ifeq +92 -> 292
    //   203: aload 8
    //   205: iload_2
    //   206: iconst_1
    //   207: bastore
    //   208: goto +84 -> 292
    //   211: aload 5
    //   213: astore 4
    //   215: aload 5
    //   217: astore_3
    //   218: aload_0
    //   219: aload 6
    //   221: ldc_w 705
    //   224: invokespecial 683	com/tencent/smtt/jsApi/impl/jsApp:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   227: aload 5
    //   229: ifnull +44 -> 273
    //   232: aload 5
    //   234: astore_3
    //   235: goto +32 -> 267
    //   238: astore_1
    //   239: goto +37 -> 276
    //   242: astore_1
    //   243: aload_3
    //   244: astore 4
    //   246: aload_0
    //   247: aload 6
    //   249: aload_1
    //   250: invokevirtual 708	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   253: invokespecial 683	com/tencent/smtt/jsApi/impl/jsApp:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   256: aload_3
    //   257: astore 4
    //   259: aload_1
    //   260: invokevirtual 192	java/lang/Exception:printStackTrace	()V
    //   263: aload_3
    //   264: ifnull +9 -> 273
    //   267: aload_3
    //   268: invokeinterface 709 1 0
    //   273: aload 8
    //   275: areturn
    //   276: aload 4
    //   278: ifnull +10 -> 288
    //   281: aload 4
    //   283: invokeinterface 709 1 0
    //   288: aload_1
    //   289: athrow
    //   290: aconst_null
    //   291: areturn
    //   292: iload_2
    //   293: iconst_1
    //   294: iadd
    //   295: istore_2
    //   296: goto -129 -> 167
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	299	0	this	jsApp
    //   0	299	1	paramArrayOfString	String[]
    //   166	130	2	i	int
    //   30	238	3	localObject1	Object
    //   52	230	4	localObject2	Object
    //   4	229	5	localCursor	android.database.Cursor
    //   32	216	6	localObject3	Object
    //   1	192	7	str	String
    //   21	253	8	arrayOfBoolean	boolean[]
    //   66	41	9	localUri	Uri
    // Exception table:
    //   from	to	target	type
    //   57	68	238	finally
    //   80	89	238	finally
    //   99	126	238	finally
    //   138	148	238	finally
    //   155	165	238	finally
    //   174	180	238	finally
    //   192	203	238	finally
    //   218	227	238	finally
    //   246	256	238	finally
    //   259	263	238	finally
    //   57	68	242	java/lang/Exception
    //   80	89	242	java/lang/Exception
    //   99	126	242	java/lang/Exception
    //   138	148	242	java/lang/Exception
    //   155	165	242	java/lang/Exception
    //   174	180	242	java/lang/Exception
    //   192	203	242	java/lang/Exception
    //   218	227	242	java/lang/Exception
  }
  
  public String sendLinkToDesktop(JSONObject paramJSONObject, final String paramString)
  {
    if (!a())
    {
      paramJSONObject = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
      a(paramString, "this api without user authorization");
      return null;
    }
    try
    {
      str2 = paramJSONObject.optString("url");
      if (TextUtils.isEmpty(str2))
      {
        a(paramString, "url is  null");
        return null;
      }
      str3 = paramJSONObject.optString("title");
      if (TextUtils.isEmpty(str3))
      {
        a(paramString, "title is null");
        return null;
      }
      if (a(str2))
      {
        a(paramString, "exist");
        return null;
      }
      if (!paramJSONObject.has("icon")) {
        break label162;
      }
      str1 = paramJSONObject.optString("icon");
    }
    catch (Exception paramJSONObject)
    {
      for (;;)
      {
        final String str2;
        final String str3;
        continue;
        final String str1 = "";
      }
    }
    paramJSONObject = new OpenJsHelper.PermissionCallback()
    {
      public String getPromptMessage()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("是否允许当前网站添加 ");
        localStringBuilder.append(str3);
        localStringBuilder.append(" 至手机桌面？");
        return localStringBuilder.toString();
      }
      
      public void onResult(boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          jsApp.this.a(str3, str2, str1, paramString, this.e);
          return;
        }
        jsApp.this.a(paramString, "false");
      }
    };
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.callWithPermission("shortcut", paramJSONObject);
    return null;
    a(paramString);
    return null;
  }
  
  public void setJsApiImpl(Object paramObject) {}
  
  public void shareImageSync(String paramString1, String paramString2)
  {
    String str2 = a(this.jdField_a_of_type_AndroidGraphicsBitmap);
    String str3 = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getUrl();
    String str1 = paramString1;
    if (TextUtils.isEmpty(paramString1))
    {
      paramString1 = new StringBuilder();
      paramString1.append("來自:");
      paramString1.append(this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getTitle());
      str1 = paramString1.toString();
    }
    if (isQbInstalled(this.jdField_a_of_type_AndroidContentContext))
    {
      paramString1 = jdField_a_of_type_JavaLangString;
      paramString1 = new StringBuilder();
      paramString1.append("shareImageSync srcpath:");
      paramString1.append(str2);
      paramString1.toString();
      Intent localIntent = new Intent();
      localIntent.setData(Uri.parse("qb://share"));
      localIntent.setAction("android.intent.action.VIEW");
      localIntent.setPackage("com.tencent.mtt");
      if (TextUtils.isEmpty(str2))
      {
        localIntent.putExtra("title", str1);
        localIntent.putExtra("url", str3);
        localIntent.putExtra("sharePicUrl", "http://res.imtt.qq.com/hotvideo/res/img/videoshare.png");
      }
      else
      {
        localIntent.putExtra("type", 1);
        localIntent.putExtra("title", str1);
        paramString1 = paramString2;
        if (TextUtils.isEmpty(paramString2)) {
          paramString1 = "我看到一张很赞的图片，分享给你，快来看！";
        }
        localIntent.putExtra("des", paramString1);
        localIntent.putExtra("srcPath", str2);
      }
      try
      {
        this.jdField_a_of_type_AndroidContentContext.startActivity(localIntent);
        return;
      }
      catch (Throwable paramString1)
      {
        paramString1.printStackTrace();
        return;
      }
    }
    paramString1 = new Intent("android.intent.action.SEND");
    if (TextUtils.isEmpty(str2))
    {
      paramString1.setType("text/plain");
      paramString1.putExtra("android.intent.extra.SUBJECT", str1);
      paramString1.putExtra("android.intent.extra.TEXT", str3);
    }
    else
    {
      paramString1.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(str2)));
      paramString1.setType("image/jpeg");
    }
    paramString1 = Intent.createChooser(paramString1, "请选择");
    try
    {
      this.jdField_a_of_type_AndroidContentContext.startActivity(paramString1);
      return;
    }
    catch (Throwable paramString1)
    {
      paramString1.printStackTrace();
    }
  }
  
  public String sharePicture(JSONObject paramJSONObject)
  {
    try
    {
      final String str1 = paramJSONObject.optString("title");
      final String str2 = paramJSONObject.optString("content");
      String str3 = paramJSONObject.optString("options");
      byte[] arrayOfByte = str3.getBytes("UTF-8");
      if (!TextUtils.isEmpty(str3))
      {
        int i = str3.indexOf(";base64,");
        paramJSONObject = "";
        if (i != -1) {
          paramJSONObject = str3.substring(0, i + 8);
        }
        Object localObject = jdField_a_of_type_JavaLangString;
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("base64Prefix:");
        ((StringBuilder)localObject).append(paramJSONObject);
        ((StringBuilder)localObject).toString();
        paramJSONObject = android.util.Base64.decode(arrayOfByte, paramJSONObject.length(), str3.length() - paramJSONObject.length(), 0);
        if ((paramJSONObject != null) && (paramJSONObject.length > 0))
        {
          if ((this.jdField_a_of_type_AndroidGraphicsBitmap != null) && (!this.jdField_a_of_type_AndroidGraphicsBitmap.isRecycled())) {
            this.jdField_a_of_type_AndroidGraphicsBitmap.recycle();
          }
          this.jdField_a_of_type_AndroidGraphicsBitmap = BitmapFactory.decodeByteArray(paramJSONObject, 0, paramJSONObject.length);
        }
      }
      else
      {
        if ((this.jdField_a_of_type_AndroidGraphicsBitmap != null) && (!this.jdField_a_of_type_AndroidGraphicsBitmap.isRecycled())) {
          this.jdField_a_of_type_AndroidGraphicsBitmap.recycle();
        }
        this.jdField_a_of_type_AndroidGraphicsBitmap = null;
      }
      new Thread(new Runnable()
      {
        public void run()
        {
          jsApp.this.shareImageSync(str1, str2);
        }
      }).start();
      return null;
    }
    catch (UnsupportedEncodingException paramJSONObject) {}
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\jsApp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */