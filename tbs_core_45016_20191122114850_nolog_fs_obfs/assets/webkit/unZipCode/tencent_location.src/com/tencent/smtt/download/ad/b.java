package com.tencent.smtt.download.ad;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.Md5Utils;
import com.tencent.common.utils.ReflectionUtils;
import com.tencent.common.utils.SignatureUtil;
import com.tencent.common.utils.UrlUtils;
import com.tencent.mtt.ContextHolder;
import com.tencent.mtt.base.utils.PackageUtils;
import com.tencent.tbs.common.baseinfo.CommonConfigListMangager;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.download.qb.InstallUtil;
import com.tencent.tbs.common.resources.ApkUtil;
import com.tencent.tbs.common.resources.PluginContextWrapper;
import com.tencent.tbs.common.settings.PublicSettingManager;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class b
{
  private static volatile b jdField_a_of_type_ComTencentSmttDownloadAdB;
  private Context jdField_a_of_type_AndroidContentContext = ContextHolder.getAppContext();
  private String jdField_a_of_type_JavaLangString = this.jdField_a_of_type_AndroidContentContext.getPackageName();
  private Map<String, String> jdField_a_of_type_JavaUtilMap = new HashMap();
  private final byte[] jdField_a_of_type_ArrayOfByte = new byte[0];
  
  private b()
  {
    DownloadAppInfoManager.a().a(this.jdField_a_of_type_AndroidContentContext);
  }
  
  private int a(Context paramContext, String paramString1, String paramString2)
  {
    try
    {
      paramString1 = new File(paramString1);
      if ((paramString1.exists()) && (paramString1.isFile()))
      {
        boolean bool = Configuration.getInstance(paramContext).isEnableInstallAppUseYYB();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("installApkFileWithLocalFile:");
        localStringBuilder.append(bool);
        Log.d("DownloadPersistence", localStringBuilder.toString());
        InstallUtil.startInstall(paramContext, paramString1, paramString2, bool);
        return 0;
      }
      return 1;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return -10;
  }
  
  private Resources a(Context paramContext, String paramString)
  {
    return new PluginContextWrapper(paramContext, paramString).getResources();
  }
  
  private Drawable a(Context paramContext, Resources paramResources, ApplicationInfo paramApplicationInfo)
  {
    try
    {
      new Rect();
      Object localObject1 = new BitmapFactory.Options();
      Object localObject2 = BitmapFactory.decodeResource(paramResources, paramApplicationInfo.icon, (BitmapFactory.Options)localObject1);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("get icon drawable:");
      localStringBuilder.append(localObject2);
      Log.d("DownloadPersistence", localStringBuilder.toString());
      if ((localObject2 != null) && (((Bitmap)localObject2).getWidth() > 10)) {
        return new BitmapDrawable(paramResources, (Bitmap)localObject2);
      }
      localObject1 = BitmapFactory.decodeResource(paramResources, paramApplicationInfo.logo, (BitmapFactory.Options)localObject1);
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("get logo drawable:");
      ((StringBuilder)localObject2).append(localObject1);
      Log.d("DownloadPersistence", ((StringBuilder)localObject2).toString());
      if ((localObject1 != null) && (((Bitmap)localObject1).getWidth() > 10)) {
        return new BitmapDrawable(paramResources, (Bitmap)localObject1);
      }
      paramContext = paramApplicationInfo.loadIcon(paramContext.getPackageManager());
      paramResources = new StringBuilder();
      paramResources.append("get loadIcon:");
      paramResources.append(paramContext);
      Log.d("DownloadPersistence", paramResources.toString());
      return paramContext;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    return null;
  }
  
  public static b a()
  {
    if (jdField_a_of_type_ComTencentSmttDownloadAdB == null) {
      try
      {
        if (jdField_a_of_type_ComTencentSmttDownloadAdB == null) {
          jdField_a_of_type_ComTencentSmttDownloadAdB = new b();
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentSmttDownloadAdB;
  }
  
  private String a(Context paramContext, String paramString, ApplicationInfo paramApplicationInfo)
  {
    String str = "";
    paramString = a(paramContext, a(paramContext, paramString), paramApplicationInfo);
    paramContext = str;
    if (paramString != null) {
      paramContext = a(paramString);
    }
    return paramContext;
  }
  
  private String a(Context paramContext, String paramString1, String paramString2, String paramString3, ApplicationInfo paramApplicationInfo)
  {
    String str = paramApplicationInfo.loadLabel(paramContext.getPackageManager()).toString();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("apkName:");
    ((StringBuilder)localObject).append(str);
    ((StringBuilder)localObject).append(" pkgName:");
    ((StringBuilder)localObject).append(paramString3);
    ((StringBuilder)localObject).append(" appInfo.icon:");
    ((StringBuilder)localObject).append(paramApplicationInfo.icon);
    Log.d("DownloadPersistence", ((StringBuilder)localObject).toString());
    if (!TextUtils.isEmpty(str))
    {
      localObject = str;
      if (!str.equals(paramString3)) {}
    }
    else
    {
      paramContext = a(paramContext, paramString2).getString(paramApplicationInfo.labelRes);
      paramString2 = new StringBuilder();
      paramString2.append("labelRes:");
      paramString2.append(paramContext);
      Log.d("DownloadPersistence", paramString2.toString());
      if ((!TextUtils.isEmpty(paramContext)) && (!str.equals(paramString3))) {
        return paramContext;
      }
      localObject = paramString1.substring(0, paramString1.indexOf('.'));
    }
    return (String)localObject;
  }
  
  /* Error */
  private static String a(Drawable paramDrawable)
  {
    // Byte code:
    //   0: aload_0
    //   1: instanceof 152
    //   4: ifeq +15 -> 19
    //   7: aload_0
    //   8: checkcast 152	android/graphics/drawable/BitmapDrawable
    //   11: invokevirtual 238	android/graphics/drawable/BitmapDrawable:getBitmap	()Landroid/graphics/Bitmap;
    //   14: astore 5
    //   16: goto +73 -> 89
    //   19: aload_0
    //   20: invokevirtual 243	android/graphics/drawable/Drawable:getIntrinsicWidth	()I
    //   23: istore_1
    //   24: iconst_1
    //   25: istore_2
    //   26: iload_1
    //   27: ifle +6 -> 33
    //   30: goto +5 -> 35
    //   33: iconst_1
    //   34: istore_1
    //   35: aload_0
    //   36: invokevirtual 246	android/graphics/drawable/Drawable:getIntrinsicHeight	()I
    //   39: istore_3
    //   40: iload_3
    //   41: ifle +5 -> 46
    //   44: iload_3
    //   45: istore_2
    //   46: iload_1
    //   47: iload_2
    //   48: getstatic 252	android/graphics/Bitmap$Config:ARGB_8888	Landroid/graphics/Bitmap$Config;
    //   51: invokestatic 256	android/graphics/Bitmap:createBitmap	(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;
    //   54: astore 5
    //   56: new 258	android/graphics/Canvas
    //   59: dup
    //   60: aload 5
    //   62: invokespecial 261	android/graphics/Canvas:<init>	(Landroid/graphics/Bitmap;)V
    //   65: astore 4
    //   67: aload_0
    //   68: iconst_0
    //   69: iconst_0
    //   70: aload 4
    //   72: invokevirtual 262	android/graphics/Canvas:getWidth	()I
    //   75: aload 4
    //   77: invokevirtual 265	android/graphics/Canvas:getHeight	()I
    //   80: invokevirtual 269	android/graphics/drawable/Drawable:setBounds	(IIII)V
    //   83: aload_0
    //   84: aload 4
    //   86: invokevirtual 273	android/graphics/drawable/Drawable:draw	(Landroid/graphics/Canvas;)V
    //   89: aconst_null
    //   90: astore 4
    //   92: aconst_null
    //   93: astore_0
    //   94: aload 5
    //   96: ifnull +258 -> 354
    //   99: new 79	java/lang/StringBuilder
    //   102: dup
    //   103: invokespecial 80	java/lang/StringBuilder:<init>	()V
    //   106: astore 4
    //   108: aload 4
    //   110: ldc_w 275
    //   113: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: pop
    //   117: aload 4
    //   119: aload 5
    //   121: invokevirtual 150	android/graphics/Bitmap:getWidth	()I
    //   124: invokevirtual 201	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   127: pop
    //   128: aload 4
    //   130: ldc_w 277
    //   133: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   136: pop
    //   137: aload 4
    //   139: aload 5
    //   141: invokevirtual 278	android/graphics/Bitmap:getHeight	()I
    //   144: invokevirtual 201	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   147: pop
    //   148: ldc 91
    //   150: aload 4
    //   152: invokevirtual 94	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   155: invokestatic 100	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   158: pop
    //   159: new 280	java/io/ByteArrayOutputStream
    //   162: dup
    //   163: invokespecial 281	java/io/ByteArrayOutputStream:<init>	()V
    //   166: astore 4
    //   168: aload 4
    //   170: astore_0
    //   171: aload 5
    //   173: getstatic 287	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
    //   176: bipush 90
    //   178: aload 4
    //   180: invokevirtual 291	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   183: pop
    //   184: aload 4
    //   186: astore_0
    //   187: aload 4
    //   189: invokevirtual 294	java/io/ByteArrayOutputStream:flush	()V
    //   192: aload 4
    //   194: astore_0
    //   195: aload 4
    //   197: invokevirtual 297	java/io/ByteArrayOutputStream:close	()V
    //   200: aload 4
    //   202: astore_0
    //   203: aload 4
    //   205: invokevirtual 301	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   208: astore 5
    //   210: aload 4
    //   212: astore_0
    //   213: new 79	java/lang/StringBuilder
    //   216: dup
    //   217: invokespecial 80	java/lang/StringBuilder:<init>	()V
    //   220: astore 6
    //   222: aload 4
    //   224: astore_0
    //   225: aload 6
    //   227: ldc_w 303
    //   230: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   233: pop
    //   234: aload 4
    //   236: astore_0
    //   237: aload 6
    //   239: aload 5
    //   241: iconst_2
    //   242: invokestatic 309	android/util/Base64:encodeToString	([BI)Ljava/lang/String;
    //   245: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   248: pop
    //   249: aload 4
    //   251: astore_0
    //   252: aload 6
    //   254: invokevirtual 94	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   257: astore 5
    //   259: aload 5
    //   261: astore_0
    //   262: goto +94 -> 356
    //   265: astore 5
    //   267: goto +19 -> 286
    //   270: astore 5
    //   272: aload_0
    //   273: astore 4
    //   275: aload 5
    //   277: astore_0
    //   278: goto +49 -> 327
    //   281: astore 5
    //   283: aconst_null
    //   284: astore 4
    //   286: aload 4
    //   288: astore_0
    //   289: aload 5
    //   291: invokevirtual 310	java/io/IOException:printStackTrace	()V
    //   294: aload 4
    //   296: ifnull +21 -> 317
    //   299: aload 4
    //   301: invokevirtual 294	java/io/ByteArrayOutputStream:flush	()V
    //   304: aload 4
    //   306: invokevirtual 297	java/io/ByteArrayOutputStream:close	()V
    //   309: goto +8 -> 317
    //   312: astore_0
    //   313: aload_0
    //   314: invokevirtual 310	java/io/IOException:printStackTrace	()V
    //   317: aconst_null
    //   318: areturn
    //   319: astore 5
    //   321: aload_0
    //   322: astore 4
    //   324: aload 5
    //   326: astore_0
    //   327: aload 4
    //   329: ifnull +23 -> 352
    //   332: aload 4
    //   334: invokevirtual 294	java/io/ByteArrayOutputStream:flush	()V
    //   337: aload 4
    //   339: invokevirtual 297	java/io/ByteArrayOutputStream:close	()V
    //   342: goto +10 -> 352
    //   345: astore 4
    //   347: aload 4
    //   349: invokevirtual 310	java/io/IOException:printStackTrace	()V
    //   352: aload_0
    //   353: athrow
    //   354: aconst_null
    //   355: astore_0
    //   356: aload 4
    //   358: ifnull +22 -> 380
    //   361: aload 4
    //   363: invokevirtual 294	java/io/ByteArrayOutputStream:flush	()V
    //   366: aload 4
    //   368: invokevirtual 297	java/io/ByteArrayOutputStream:close	()V
    //   371: aload_0
    //   372: areturn
    //   373: astore 4
    //   375: aload 4
    //   377: invokevirtual 310	java/io/IOException:printStackTrace	()V
    //   380: aload_0
    //   381: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	382	0	paramDrawable	Drawable
    //   23	24	1	i	int
    //   25	23	2	j	int
    //   39	6	3	k	int
    //   65	273	4	localObject1	Object
    //   345	22	4	localIOException1	java.io.IOException
    //   373	3	4	localIOException2	java.io.IOException
    //   14	246	5	localObject2	Object
    //   265	1	5	localIOException3	java.io.IOException
    //   270	6	5	localObject3	Object
    //   281	9	5	localIOException4	java.io.IOException
    //   319	6	5	localObject4	Object
    //   220	33	6	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   171	184	265	java/io/IOException
    //   187	192	265	java/io/IOException
    //   195	200	265	java/io/IOException
    //   203	210	265	java/io/IOException
    //   213	222	265	java/io/IOException
    //   225	234	265	java/io/IOException
    //   237	249	265	java/io/IOException
    //   252	259	265	java/io/IOException
    //   99	168	270	finally
    //   99	168	281	java/io/IOException
    //   299	309	312	java/io/IOException
    //   171	184	319	finally
    //   187	192	319	finally
    //   195	200	319	finally
    //   203	210	319	finally
    //   213	222	319	finally
    //   225	234	319	finally
    //   237	249	319	finally
    //   252	259	319	finally
    //   289	294	319	finally
    //   332	342	345	java/io/IOException
    //   361	371	373	java/io/IOException
  }
  
  private static String a(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("(^|&)");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("=([^&]*)(&|$)");
    paramString2 = localStringBuilder.toString();
    try
    {
      paramString1 = Pattern.compile(paramString2).matcher(paramString1);
      if (paramString1.find())
      {
        paramString1 = paramString1.group(0).split("=")[1].replace("&", "");
        return paramString1;
      }
    }
    catch (Exception paramString1)
    {
      for (;;) {}
    }
    return null;
  }
  
  /* Error */
  private JSONArray a(Context paramContext, String paramString1, String paramString2, int paramInt, boolean paramBoolean)
  {
    // Byte code:
    //   0: invokestatic 360	com/tencent/tbs/common/settings/PublicSettingManager:getInstance	()Lcom/tencent/tbs/common/settings/PublicSettingManager;
    //   3: ldc_w 362
    //   6: iconst_1
    //   7: invokevirtual 366	com/tencent/tbs/common/settings/PublicSettingManager:getCloudSwitch	(Ljava/lang/String;I)I
    //   10: istore 8
    //   12: bipush 10
    //   14: istore 6
    //   16: iload 4
    //   18: bipush 10
    //   20: if_icmple +10 -> 30
    //   23: iload 6
    //   25: istore 4
    //   27: goto +3 -> 30
    //   30: new 368	java/util/HashSet
    //   33: dup
    //   34: invokespecial 369	java/util/HashSet:<init>	()V
    //   37: astore 13
    //   39: new 371	org/json/JSONArray
    //   42: dup
    //   43: invokespecial 372	org/json/JSONArray:<init>	()V
    //   46: astore 14
    //   48: ldc 46
    //   50: monitorenter
    //   51: invokestatic 49	com/tencent/smtt/download/ad/DownloadAppInfoManager:a	()Lcom/tencent/smtt/download/ad/DownloadAppInfoManager;
    //   54: astore 15
    //   56: aload 15
    //   58: aload_0
    //   59: getfield 36	com/tencent/smtt/download/ad/b:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   62: aload_2
    //   63: invokestatic 378	com/tencent/common/utils/UrlUtils:getHost	(Ljava/lang/String;)Ljava/lang/String;
    //   66: invokevirtual 381	com/tencent/smtt/download/ad/DownloadAppInfoManager:a	(Landroid/content/Context;Ljava/lang/String;)Ljava/util/List;
    //   69: astore 15
    //   71: new 79	java/lang/StringBuilder
    //   74: dup
    //   75: invokespecial 80	java/lang/StringBuilder:<init>	()V
    //   78: astore_2
    //   79: aload_2
    //   80: ldc_w 383
    //   83: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: pop
    //   87: aload_2
    //   88: aload 15
    //   90: invokeinterface 388 1 0
    //   95: invokevirtual 201	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   98: pop
    //   99: ldc 91
    //   101: aload_2
    //   102: invokevirtual 94	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   105: invokestatic 100	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   108: pop
    //   109: aload 15
    //   111: invokeinterface 388 1 0
    //   116: istore 6
    //   118: iload 6
    //   120: iconst_1
    //   121: isub
    //   122: istore 7
    //   124: iload 7
    //   126: iflt +382 -> 508
    //   129: iload 4
    //   131: ifle +377 -> 508
    //   134: aload 15
    //   136: iload 7
    //   138: invokeinterface 392 2 0
    //   143: checkcast 394	com/tencent/smtt/download/ad/a
    //   146: astore 16
    //   148: new 58	java/io/File
    //   151: dup
    //   152: aload 16
    //   154: getfield 396	com/tencent/smtt/download/ad/a:d	Ljava/lang/String;
    //   157: invokespecial 61	java/io/File:<init>	(Ljava/lang/String;)V
    //   160: astore_2
    //   161: aload 13
    //   163: aload 16
    //   165: getfield 397	com/tencent/smtt/download/ad/a:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   168: invokevirtual 400	java/util/HashSet:contains	(Ljava/lang/Object;)Z
    //   171: ifeq +10 -> 181
    //   174: iload 4
    //   176: istore 6
    //   178: goto +317 -> 495
    //   181: invokestatic 406	java/lang/System:currentTimeMillis	()J
    //   184: lstore 9
    //   186: aload_0
    //   187: aload_2
    //   188: aload 16
    //   190: iload 8
    //   192: i2l
    //   193: iconst_0
    //   194: invokespecial 409	com/tencent/smtt/download/ad/b:a	(Ljava/io/File;Lcom/tencent/smtt/download/ad/a;JZ)Z
    //   197: ifne +10 -> 207
    //   200: iload 4
    //   202: istore 6
    //   204: goto +291 -> 495
    //   207: invokestatic 406	java/lang/System:currentTimeMillis	()J
    //   210: lstore 11
    //   212: new 79	java/lang/StringBuilder
    //   215: dup
    //   216: invokespecial 80	java/lang/StringBuilder:<init>	()V
    //   219: astore 17
    //   221: aload 17
    //   223: ldc_w 411
    //   226: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   229: pop
    //   230: aload 17
    //   232: lload 11
    //   234: lload 9
    //   236: lsub
    //   237: invokevirtual 414	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   240: pop
    //   241: ldc 91
    //   243: aload 17
    //   245: invokevirtual 94	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   248: invokestatic 100	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   251: pop
    //   252: aload_0
    //   253: aload_1
    //   254: aload_2
    //   255: aload 16
    //   257: getfield 417	com/tencent/smtt/download/ad/a:e	Ljava/lang/String;
    //   260: aload 16
    //   262: getfield 397	com/tencent/smtt/download/ad/a:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   265: aload_3
    //   266: iconst_1
    //   267: invokespecial 420	com/tencent/smtt/download/ad/b:a	(Landroid/content/Context;Ljava/io/File;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)Z
    //   270: ifne +10 -> 280
    //   273: iload 4
    //   275: istore 6
    //   277: goto +218 -> 495
    //   280: new 79	java/lang/StringBuilder
    //   283: dup
    //   284: invokespecial 80	java/lang/StringBuilder:<init>	()V
    //   287: astore_2
    //   288: aload_2
    //   289: ldc_w 422
    //   292: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   295: pop
    //   296: aload_2
    //   297: invokestatic 406	java/lang/System:currentTimeMillis	()J
    //   300: lload 11
    //   302: lsub
    //   303: invokevirtual 414	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   306: pop
    //   307: ldc 91
    //   309: aload_2
    //   310: invokevirtual 94	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   313: invokestatic 100	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   316: pop
    //   317: aload 16
    //   319: getfield 417	com/tencent/smtt/download/ad/a:e	Ljava/lang/String;
    //   322: astore 17
    //   324: aload 16
    //   326: getfield 425	com/tencent/smtt/download/ad/a:jdField_b_of_type_JavaLangString	Ljava/lang/String;
    //   329: astore 18
    //   331: aconst_null
    //   332: astore_2
    //   333: iload 5
    //   335: ifeq +9 -> 344
    //   338: aload 16
    //   340: getfield 428	com/tencent/smtt/download/ad/a:g	Ljava/lang/String;
    //   343: astore_2
    //   344: iload 4
    //   346: istore 6
    //   348: aload 18
    //   350: invokestatic 207	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   353: ifne +142 -> 495
    //   356: aload 17
    //   358: invokestatic 207	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   361: ifeq +10 -> 371
    //   364: iload 4
    //   366: istore 6
    //   368: goto +127 -> 495
    //   371: new 430	org/json/JSONObject
    //   374: dup
    //   375: invokespecial 431	org/json/JSONObject:<init>	()V
    //   378: astore 19
    //   380: iload 4
    //   382: istore 6
    //   384: aload 19
    //   386: ldc_w 433
    //   389: aload 18
    //   391: invokevirtual 437	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   394: pop
    //   395: iload 4
    //   397: istore 6
    //   399: aload 19
    //   401: ldc_w 439
    //   404: aload 16
    //   406: getfield 397	com/tencent/smtt/download/ad/a:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   409: invokevirtual 437	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   412: pop
    //   413: iload 4
    //   415: istore 6
    //   417: aload 19
    //   419: ldc_w 441
    //   422: aload_2
    //   423: invokevirtual 437	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   426: pop
    //   427: iload 4
    //   429: istore 6
    //   431: aload 19
    //   433: ldc_w 443
    //   436: aload 17
    //   438: invokevirtual 437	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   441: pop
    //   442: iload 4
    //   444: istore 6
    //   446: aload 19
    //   448: ldc_w 445
    //   451: ldc_w 447
    //   454: invokevirtual 437	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   457: pop
    //   458: iload 4
    //   460: istore 6
    //   462: aload 14
    //   464: aload 19
    //   466: invokevirtual 450	org/json/JSONArray:put	(Ljava/lang/Object;)Lorg/json/JSONArray;
    //   469: pop
    //   470: iload 4
    //   472: iconst_1
    //   473: isub
    //   474: istore 4
    //   476: iload 4
    //   478: istore 6
    //   480: aload 13
    //   482: aload 16
    //   484: getfield 397	com/tencent/smtt/download/ad/a:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   487: invokevirtual 453	java/util/HashSet:add	(Ljava/lang/Object;)Z
    //   490: pop
    //   491: iload 4
    //   493: istore 6
    //   495: iload 7
    //   497: iconst_1
    //   498: isub
    //   499: istore 7
    //   501: iload 6
    //   503: istore 4
    //   505: goto -381 -> 124
    //   508: ldc 46
    //   510: monitorexit
    //   511: aload 14
    //   513: areturn
    //   514: ldc 46
    //   516: monitorexit
    //   517: aload_1
    //   518: athrow
    //   519: astore_2
    //   520: goto -25 -> 495
    //   523: astore_1
    //   524: goto -10 -> 514
    //   527: astore_1
    //   528: goto -14 -> 514
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	531	0	this	b
    //   0	531	1	paramContext	Context
    //   0	531	2	paramString1	String
    //   0	531	3	paramString2	String
    //   0	531	4	paramInt	int
    //   0	531	5	paramBoolean	boolean
    //   14	488	6	i	int
    //   122	378	7	j	int
    //   10	181	8	k	int
    //   184	51	9	l1	long
    //   210	91	11	l2	long
    //   37	444	13	localHashSet	HashSet
    //   46	466	14	localJSONArray	JSONArray
    //   54	81	15	localObject1	Object
    //   146	337	16	locala	a
    //   219	218	17	localObject2	Object
    //   329	61	18	str	String
    //   378	87	19	localJSONObject	JSONObject
    // Exception table:
    //   from	to	target	type
    //   384	395	519	org/json/JSONException
    //   399	413	519	org/json/JSONException
    //   417	427	519	org/json/JSONException
    //   431	442	519	org/json/JSONException
    //   446	458	519	org/json/JSONException
    //   462	470	519	org/json/JSONException
    //   480	491	519	org/json/JSONException
    //   56	118	523	finally
    //   134	174	523	finally
    //   181	200	523	finally
    //   207	273	523	finally
    //   280	331	523	finally
    //   338	344	523	finally
    //   348	364	523	finally
    //   371	380	523	finally
    //   384	395	523	finally
    //   399	413	523	finally
    //   417	427	523	finally
    //   431	442	523	finally
    //   446	458	523	finally
    //   462	470	523	finally
    //   480	491	523	finally
    //   508	511	523	finally
    //   514	517	523	finally
    //   51	56	527	finally
  }
  
  private JSONObject a(Context paramContext, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2)
  {
    int j = PublicSettingManager.getInstance().getCloudSwitch("TBS_AD_REINSTALL_TIPS_SHOW_TIME", 1);
    for (;;)
    {
      int i;
      try
      {
        localObject1 = DownloadAppInfoManager.a();
        try
        {
          List localList = ((DownloadAppInfoManager)localObject1).a(this.jdField_a_of_type_AndroidContentContext, UrlUtils.getHost(paramString1));
          paramString1 = new StringBuilder();
          paramString1.append("getOneDownloadAppInfo size:");
          paramString1.append(localList.size());
          Log.d("DownloadPersistence", paramString1.toString());
          i = localList.size() - 1;
          localObject3 = null;
          localObject1 = null;
          paramString1 = null;
          localObject2 = null;
          if (i < 0) {
            break label400;
          }
          localObject4 = (a)localList.get(i);
          localObject5 = new File(((a)localObject4).d);
          if (!a((File)localObject5, (a)localObject4, j, true))
          {
            localObject5 = localObject1;
            localObject4 = localObject2;
            break label383;
          }
          if (!a(paramContext, (File)localObject5, ((a)localObject4).e, ((a)localObject4).jdField_a_of_type_JavaLangString, paramString2, paramBoolean2))
          {
            localObject5 = localObject1;
            localObject4 = localObject2;
            break label383;
          }
          localObject3 = ((a)localObject4).e;
          localObject1 = ((a)localObject4).jdField_a_of_type_JavaLangString;
          localObject2 = ((a)localObject4).jdField_b_of_type_JavaLangString;
          if (!paramBoolean1) {
            break label366;
          }
          paramString1 = ((a)localObject4).g;
          if ((TextUtils.isEmpty((CharSequence)localObject2)) || (TextUtils.isEmpty((CharSequence)localObject3))) {
            break label371;
          }
          paramContext = (Context)localObject3;
          localObject3 = localObject1;
          localObject1 = localObject2;
          if ((!TextUtils.isEmpty((CharSequence)localObject1)) && (!TextUtils.isEmpty(paramContext))) {
            paramString2 = new JSONObject();
          }
        }
        finally {}
      }
      finally {}
      try
      {
        paramString2.put("appName", localObject1);
        paramString2.put("packageName", localObject3);
        paramString2.put("picSource", paramString1);
        paramString2.put("key", paramContext);
        paramString2.put("ret", "0");
      }
      catch (JSONException paramContext)
      {
        continue;
      }
      try
      {
        paramString2.put("pageUrl", null);
        return paramString2;
      }
      catch (JSONException paramContext)
      {
        continue;
      }
      return null;
      return null;
      return null;
      label366:
      paramString1 = null;
      continue;
      label371:
      Object localObject4 = localObject3;
      Object localObject5 = localObject2;
      Object localObject3 = localObject1;
      label383:
      i -= 1;
      Object localObject1 = localObject5;
      Object localObject2 = localObject4;
      continue;
      label400:
      paramContext = (Context)localObject2;
    }
  }
  
  private boolean a(Context paramContext, a parama)
  {
    if (parama.jdField_b_of_type_Int != 1) {
      return false;
    }
    if (PackageUtils.getInstalledPKGInfo(parama.jdField_a_of_type_JavaLangString, paramContext) == null)
    {
      paramContext = new StringBuilder();
      paramContext.append("getInstalledAppInfo: ");
      paramContext.append(parama.jdField_a_of_type_JavaLangString);
      paramContext.append(" has been removed!");
      Log.d("DownloadPersistence", paramContext.toString());
      return false;
    }
    paramContext = DownloadAppInfoManager.a(paramContext, parama.jdField_a_of_type_JavaLangString);
    if ((!TextUtils.isEmpty(paramContext)) && (!paramContext.equals(parama.e)))
    {
      Log.d("DownloadPersistence", "getInstalledAppInfo: check md5 failed");
      return false;
    }
    return true;
  }
  
  private boolean a(Context paramContext, File paramFile, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("passPackageInfoChecks: pack=");
    localStringBuilder.append(paramFile.getAbsolutePath());
    Log.d("DownloadPersistence", localStringBuilder.toString());
    if ((!TextUtils.isEmpty(paramString2)) && (!paramString2.equals(paramString3)))
    {
      if (SignatureUtil.getInstalledPKGInfo(paramString2, paramContext, 0) != null)
      {
        paramFile.delete();
        return false;
      }
      if (paramBoolean)
      {
        long l = System.currentTimeMillis();
        paramContext = Md5Utils.getMD5(paramFile);
        if ((paramContext != null) && (!paramContext.equals(paramString1)))
        {
          paramFile.delete();
          return false;
        }
        paramContext = new StringBuilder();
        paramContext.append("passPackageInfoChecks: check md5 cost time=");
        paramContext.append(System.currentTimeMillis() - l);
        Log.d("DownloadPersistence", paramContext.toString());
      }
      return true;
    }
    paramContext = new StringBuilder();
    paramContext.append("passPackageInfoChecks: excludePkg=");
    paramContext.append(paramString3);
    Log.d("DownloadPersistence", paramContext.toString());
    return false;
  }
  
  private boolean a(File paramFile, a parama, long paramLong, boolean paramBoolean)
  {
    if (!paramFile.exists()) {
      return false;
    }
    if (parama.jdField_b_of_type_Int != 0)
    {
      Log.d("DownloadPersistence", "satisfyDownloadAppInfoRestrictions: appState");
      return false;
    }
    paramFile = new StringBuilder();
    paramFile.append(" test:");
    paramFile.append(parama.d);
    Log.d("DownloadPersistence", paramFile.toString());
    if (parama.jdField_a_of_type_Int >= paramLong)
    {
      paramFile = new StringBuilder();
      paramFile.append(" has show :");
      paramFile.append(parama.jdField_a_of_type_Int);
      paramFile.append(" time");
      Log.d("DownloadPersistence", paramFile.toString());
      return false;
    }
    if ((paramBoolean) && (System.currentTimeMillis() - parama.jdField_b_of_type_Long < 86400000L))
    {
      paramFile = new StringBuilder();
      paramFile.append("has show before:");
      paramFile.append((System.currentTimeMillis() - parama.jdField_b_of_type_Long) / 1000L);
      Log.d("DownloadPersistence", paramFile.toString());
      return false;
    }
    return true;
  }
  
  private JSONObject b(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    try
    {
      Iterator localIterator = DownloadAppInfoManager.a().a(this.jdField_a_of_type_AndroidContentContext).iterator();
      while (localIterator.hasNext())
      {
        a locala = (a)localIterator.next();
        if (paramString.equals(locala.d))
        {
          paramString = locala.a();
          return paramString;
        }
      }
      return null;
    }
    finally {}
  }
  
  public int a(Context arg1, String paramString)
  {
    if (paramString == null) {
      return -3;
    }
    Object localObject2 = null;
    try
    {
      Iterator localIterator = DownloadAppInfoManager.a().a(this.jdField_a_of_type_AndroidContentContext).iterator();
      Object localObject1;
      do
      {
        localObject1 = localObject2;
        if (!localIterator.hasNext()) {
          break;
        }
        localObject1 = (a)localIterator.next();
      } while (!paramString.equals(((a)localObject1).e));
      if ((localObject1 != null) && (!TextUtils.isEmpty(((a)localObject1).d)))
      {
        int i = a(???, ((a)localObject1).d, ((a)localObject1).f);
        if (i == 0) {
          synchronized (this.jdField_a_of_type_ArrayOfByte)
          {
            this.jdField_a_of_type_JavaUtilMap.put(((a)localObject1).jdField_a_of_type_JavaLangString, ((a)localObject1).d);
            return i;
          }
        }
        return i;
      }
      return -5;
    }
    finally {}
  }
  
  public int a(final String paramString1, final String paramString2, final String paramString3, final String paramString4, final String paramString5)
  {
    if (TextUtils.isEmpty(paramString4)) {
      return -1;
    }
    final File localFile = new File(paramString4);
    if (!localFile.exists()) {
      return -2;
    }
    if (TextUtils.isEmpty(paramString1)) {
      return -3;
    }
    Log.d("DownloadPersistence", "addDownloadAppInfoAsync");
    BrowserExecutorSupplier.forIoTasks().execute(new Runnable()
    {
      public void run()
      {
        Object localObject5 = Md5Utils.getMD5(localFile);
        Object localObject3 = paramString1;
        Object localObject2 = ApkUtil.getPackageInfo(b.a(b.this), paramString4);
        Object localObject1;
        if (localObject2 != null)
        {
          localObject1 = localObject3;
          if (TextUtils.isEmpty((CharSequence)localObject3)) {
            localObject1 = ((PackageInfo)localObject2).packageName;
          }
          localObject3 = ((PackageInfo)localObject2).applicationInfo;
          if (localObject3 != null)
          {
            ((ApplicationInfo)localObject3).sourceDir = localFile.getAbsolutePath();
            ((ApplicationInfo)localObject3).publicSourceDir = localFile.getAbsolutePath();
            localObject2 = b.this;
            localObject2 = b.a((b)localObject2, b.a((b)localObject2), localFile.getName(), paramString4, (String)localObject1, (ApplicationInfo)localObject3);
            localObject4 = b.this;
            localObject4 = b.a((b)localObject4, b.a((b)localObject4), paramString4, (ApplicationInfo)localObject3);
            localObject3 = localObject1;
            localObject1 = localObject2;
            localObject2 = localObject4;
          }
          else
          {
            localObject4 = "";
            localObject2 = "";
            localObject3 = localObject1;
            localObject1 = localObject4;
          }
        }
        else
        {
          localObject1 = "";
          localObject2 = "";
        }
        Object localObject4 = new a((String)localObject3, paramString2, paramString3, paramString4, (String)localObject5, localFile.lastModified(), paramString5, (String)localObject1, (String)localObject2, b.a(b.this));
        localObject5 = new StringBuilder();
        ((StringBuilder)localObject5).append("addDownloadAppInfoAsync:pkgName=");
        ((StringBuilder)localObject5).append((String)localObject3);
        ((StringBuilder)localObject5).append(" appName=");
        ((StringBuilder)localObject5).append((String)localObject1);
        ((StringBuilder)localObject5).append(" image.length=");
        ((StringBuilder)localObject5).append(((String)localObject2).length());
        Log.d("DownloadPersistence", ((StringBuilder)localObject5).toString());
        DownloadAppInfoManager.a().a(b.a(b.this), (a)localObject4);
      }
    });
    com.tencent.smtt.a.a.a().a();
    return 0;
  }
  
  public JSONArray a(Context paramContext, final String paramString, int paramInt)
  {
    i = paramInt;
    if (paramInt > 10) {
      i = 10;
    }
    HashSet localHashSet = new HashSet();
    paramString = UrlUtils.getHost(paramString);
    JSONArray localJSONArray = new JSONArray();
    for (;;)
    {
      Object localObject;
      int j;
      JSONObject localJSONObject;
      try
      {
        List localList = DownloadAppInfoManager.a().a(this.jdField_a_of_type_AndroidContentContext, paramString);
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("getInstalledAppInfo: app list size=");
        ((StringBuilder)localObject).append(localList.size());
        Log.d("DownloadPersistence", ((StringBuilder)localObject).toString());
        j = localList.size() - 1;
        paramInt = i;
        if ((j >= 0) && (paramInt > 0))
        {
          localObject = (a)localList.get(j);
          if ((!localHashSet.contains(((a)localObject).jdField_a_of_type_JavaLangString)) && (a(paramContext, (a)localObject)))
          {
            localJSONObject = new JSONObject();
            i = paramInt;
          }
        }
      }
      finally {}
      try
      {
        localJSONObject.put("appName", ((a)localObject).jdField_b_of_type_JavaLangString);
        i = paramInt;
        localJSONObject.put("packageName", ((a)localObject).jdField_a_of_type_JavaLangString);
        i = paramInt;
        localJSONObject.put("picSource", ((a)localObject).g);
        i = paramInt;
        localJSONObject.put("installedTimestamp", ((a)localObject).c);
        i = paramInt;
        localJSONArray.put(localJSONObject);
        paramInt -= 1;
        i = paramInt;
        localHashSet.add(((a)localObject).jdField_a_of_type_JavaLangString);
      }
      catch (JSONException localJSONException)
      {
        paramInt = i;
        continue;
      }
      j -= 1;
    }
    BrowserExecutorSupplier.forBackgroundTasks().execute(new Runnable()
    {
      public void run()
      {
        DownloadAppInfoManager.c(b.a(b.this), paramString);
      }
    });
    return localJSONArray;
  }
  
  public JSONArray a(Context paramContext, String paramString1, String paramString2, int paramInt)
  {
    return a(paramContext, paramString1, paramString2, paramInt, true);
  }
  
  public JSONObject a(Context paramContext, String paramString1, String paramString2)
  {
    return a(paramContext, paramString1, paramString2, true, true);
  }
  
  public JSONObject a(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    try
    {
      Iterator localIterator = DownloadAppInfoManager.a().a(this.jdField_a_of_type_AndroidContentContext).iterator();
      while (localIterator.hasNext())
      {
        a locala = (a)localIterator.next();
        if (paramString.equals(locala.e))
        {
          paramString = locala.a();
          return paramString;
        }
      }
      return null;
    }
    finally {}
  }
  
  public JSONObject a(boolean paramBoolean, String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onAppInstalled: isWebViewDestroyed=");
    ((StringBuilder)localObject).append(paramBoolean);
    ((StringBuilder)localObject).append(" package name=");
    ((StringBuilder)localObject).append(paramString);
    Log.d("DownloadPersistence", ((StringBuilder)localObject).toString());
    for (;;)
    {
      synchronized (this.jdField_a_of_type_ArrayOfByte)
      {
        localObject = (String)this.jdField_a_of_type_JavaUtilMap.get(paramString);
        if (localObject != null)
        {
          localObject = b((String)localObject);
          this.jdField_a_of_type_JavaUtilMap.remove(paramString);
          DownloadAppInfoManager.a().a(this.jdField_a_of_type_AndroidContentContext, paramString, paramBoolean);
          return (JSONObject)localObject;
        }
      }
      localObject = null;
    }
  }
  
  public void a(Object paramObject, String paramString)
  {
    Log.d("DownloadPersistence", "notifyStartInstallApk 1");
    CommonConfigListMangager.getInstance().requestCommonConfigList(false, 1006, "");
    ReflectionUtils.invokeInstance(ReflectionUtils.getInstanceField(paramObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "mAdWebViewController"), "notifyInstallApk", new Class[] { String.class }, new Object[] { paramString });
  }
  
  public void a(Object paramObject, boolean paramBoolean)
  {
    ReflectionUtils.invokeInstance(ReflectionUtils.getInstanceField(paramObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "mAdWebViewController"), "setEncourageWindowShowedFlag", new Class[] { Boolean.TYPE }, new Object[] { Boolean.valueOf(paramBoolean) });
  }
  
  public void a(final String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    BrowserExecutorSupplier.forBackgroundTasks().execute(new Runnable()
    {
      public void run()
      {
        for (;;)
        {
          int i;
          try
          {
            List localList = DownloadAppInfoManager.a().a(b.a(b.this));
            i = 0;
            if (i < localList.size())
            {
              a locala = (a)localList.get(i);
              if (paramString.equals(paramString))
              {
                String str = DownloadAppInfoManager.a(b.a(b.this), paramString);
                if ((TextUtils.isEmpty(str)) || (str.equals(locala.e))) {
                  DownloadAppInfoManager.a(b.a(b.this), locala, 1);
                }
              }
            }
            else
            {
              return;
            }
          }
          finally {}
          i += 1;
        }
      }
    });
  }
  
  public void a(JSONObject paramJSONObject)
  {
    PublicSettingManager.getInstance().setTbsAdReinstallTipsShowTime(System.currentTimeMillis());
    if (paramJSONObject == null) {
      return;
    }
    try
    {
      paramJSONObject = paramJSONObject.getString("packagePath");
      if (paramJSONObject == null) {
        return;
      }
      DownloadAppInfoManager.a().a(this.jdField_a_of_type_AndroidContentContext, paramJSONObject);
      return;
    }
    catch (JSONException paramJSONObject) {}
  }
  
  public boolean a(Context paramContext, String paramString, boolean paramBoolean)
  {
    Log.d("DownloadPersistence", "canShowInstallTips 1");
    boolean bool = TextUtils.isEmpty(paramString);
    paramBoolean = false;
    if (bool) {
      return false;
    }
    String str1;
    if (paramString.indexOf('?') > 0)
    {
      String str2 = paramString.substring(paramString.indexOf('?') + 1);
      str1 = a(str2, "packageName");
      if (TextUtils.isEmpty(str1)) {
        str1 = a(str2, "gameId");
      }
    }
    else
    {
      str1 = null;
    }
    Log.d("DownloadPersistence", "canShowInstallTips 2");
    if (a(paramContext, paramString, str1, false, true) != null) {
      paramBoolean = true;
    }
    return paramBoolean;
  }
  
  public boolean a(Object paramObject)
  {
    paramObject = ReflectionUtils.invokeInstance(ReflectionUtils.getInstanceField(paramObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "mAdWebViewController"), "getEncourageWindowShowedFlag");
    if ((paramObject != null) && ((paramObject instanceof Boolean))) {
      return ((Boolean)paramObject).booleanValue();
    }
    return false;
  }
  
  public JSONObject b(Context paramContext, String paramString1, String paramString2)
  {
    return a(paramContext, paramString1, paramString2, true, false);
  }
  
  public void b(Object paramObject, String paramString)
  {
    Log.d("DownloadPersistence", "reportEncourageWindowInstalledIfNeed");
    if (paramObject == null) {
      return;
    }
    if (a(paramObject))
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("cPackageName", this.jdField_a_of_type_JavaLangString);
      localHashMap.put("packageName", paramString);
      localHashMap.put("type", "4");
      X5CoreBeaconUploader.getInstance(this.jdField_a_of_type_AndroidContentContext).upLoadToBeacon("TBS_ENCOURAGE_WINDOW_REPORT", localHashMap);
      a(paramObject, false);
    }
  }
  
  public void b(String paramString)
  {
    int i = PublicSettingManager.getInstance().getCloudSwitch("TBS_AD_REINSTALL_TIPS_SHOW_TIME", 1);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onUserCloseTips key:");
    localStringBuilder.append(paramString);
    localStringBuilder.append(" maxShowTime:");
    localStringBuilder.append(i);
    Log.d("DownloadPersistence", localStringBuilder.toString());
    if (i < 2) {
      DownloadAppInfoManager.a().b(this.jdField_a_of_type_AndroidContentContext, paramString);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\download\ad\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */