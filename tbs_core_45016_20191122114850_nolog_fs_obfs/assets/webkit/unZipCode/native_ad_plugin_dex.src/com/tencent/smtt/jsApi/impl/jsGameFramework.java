package com.tencent.smtt.jsApi.impl;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.utils.LogUtils;
import com.tencent.smtt.jsApi.export.IOpenJsApis;
import com.tencent.smtt.jsApi.export.IWebviewNotifyListener;
import com.tencent.tbs.common.config.Configuration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class jsGameFramework
  implements IOpenJsApis
{
  public static final String TAG = "jsGameFramework";
  OpenJsHelper jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper = null;
  private Object jdField_a_of_type_JavaLangObject = null;
  private ArrayList<IWebviewNotifyListener> jdField_a_of_type_JavaUtilArrayList = null;
  
  public jsGameFramework(OpenJsHelper paramOpenJsHelper, ArrayList<IWebviewNotifyListener> paramArrayList)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper = paramOpenJsHelper;
    this.jdField_a_of_type_JavaUtilArrayList = paramArrayList;
  }
  
  /* Error */
  private int a(File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 6
    //   6: aconst_null
    //   7: astore 7
    //   9: aconst_null
    //   10: astore_3
    //   11: new 38	java/io/BufferedInputStream
    //   14: dup
    //   15: new 40	java/io/FileInputStream
    //   18: dup
    //   19: aload_1
    //   20: invokespecial 43	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   23: sipush 4096
    //   26: invokespecial 46	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;I)V
    //   29: astore_1
    //   30: sipush 1024
    //   33: newarray <illegal type>
    //   35: astore_3
    //   36: aload_1
    //   37: aload_3
    //   38: invokevirtual 52	java/io/InputStream:read	([B)I
    //   41: istore_2
    //   42: iload_2
    //   43: ifle +40 -> 83
    //   46: new 54	org/json/JSONObject
    //   49: dup
    //   50: new 56	java/lang/String
    //   53: dup
    //   54: aload_3
    //   55: iconst_0
    //   56: iload_2
    //   57: invokespecial 59	java/lang/String:<init>	([BII)V
    //   60: invokespecial 62	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   63: ldc 64
    //   65: iconst_m1
    //   66: invokevirtual 68	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   69: istore_2
    //   70: aload_1
    //   71: invokevirtual 71	java/io/InputStream:close	()V
    //   74: iload_2
    //   75: ireturn
    //   76: astore_1
    //   77: aload_1
    //   78: invokevirtual 74	java/io/IOException:printStackTrace	()V
    //   81: iload_2
    //   82: ireturn
    //   83: aload_1
    //   84: invokevirtual 71	java/io/InputStream:close	()V
    //   87: iconst_m1
    //   88: ireturn
    //   89: astore_3
    //   90: goto +101 -> 191
    //   93: astore 4
    //   95: goto +28 -> 123
    //   98: astore 4
    //   100: goto +45 -> 145
    //   103: astore 4
    //   105: goto +62 -> 167
    //   108: astore 4
    //   110: aload_3
    //   111: astore_1
    //   112: aload 4
    //   114: astore_3
    //   115: goto +76 -> 191
    //   118: astore 4
    //   120: aload 5
    //   122: astore_1
    //   123: aload_1
    //   124: astore_3
    //   125: aload 4
    //   127: invokevirtual 75	org/json/JSONException:printStackTrace	()V
    //   130: aload_1
    //   131: ifnull +58 -> 189
    //   134: aload_1
    //   135: invokevirtual 71	java/io/InputStream:close	()V
    //   138: iconst_m1
    //   139: ireturn
    //   140: astore 4
    //   142: aload 6
    //   144: astore_1
    //   145: aload_1
    //   146: astore_3
    //   147: aload 4
    //   149: invokevirtual 74	java/io/IOException:printStackTrace	()V
    //   152: aload_1
    //   153: ifnull +36 -> 189
    //   156: aload_1
    //   157: invokevirtual 71	java/io/InputStream:close	()V
    //   160: iconst_m1
    //   161: ireturn
    //   162: astore 4
    //   164: aload 7
    //   166: astore_1
    //   167: aload_1
    //   168: astore_3
    //   169: aload 4
    //   171: invokevirtual 76	java/io/FileNotFoundException:printStackTrace	()V
    //   174: aload_1
    //   175: ifnull +14 -> 189
    //   178: aload_1
    //   179: invokevirtual 71	java/io/InputStream:close	()V
    //   182: iconst_m1
    //   183: ireturn
    //   184: astore_1
    //   185: aload_1
    //   186: invokevirtual 74	java/io/IOException:printStackTrace	()V
    //   189: iconst_m1
    //   190: ireturn
    //   191: aload_1
    //   192: ifnull +15 -> 207
    //   195: aload_1
    //   196: invokevirtual 71	java/io/InputStream:close	()V
    //   199: goto +8 -> 207
    //   202: astore_1
    //   203: aload_1
    //   204: invokevirtual 74	java/io/IOException:printStackTrace	()V
    //   207: aload_3
    //   208: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	209	0	this	jsGameFramework
    //   0	209	1	paramFile	File
    //   41	41	2	i	int
    //   10	45	3	arrayOfByte	byte[]
    //   89	22	3	localObject1	Object
    //   114	94	3	localObject2	Object
    //   93	1	4	localJSONException1	org.json.JSONException
    //   98	1	4	localIOException1	IOException
    //   103	1	4	localFileNotFoundException1	FileNotFoundException
    //   108	5	4	localObject3	Object
    //   118	8	4	localJSONException2	org.json.JSONException
    //   140	8	4	localIOException2	IOException
    //   162	8	4	localFileNotFoundException2	FileNotFoundException
    //   1	120	5	localObject4	Object
    //   4	139	6	localObject5	Object
    //   7	158	7	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   70	74	76	java/io/IOException
    //   30	42	89	finally
    //   46	70	89	finally
    //   30	42	93	org/json/JSONException
    //   46	70	93	org/json/JSONException
    //   30	42	98	java/io/IOException
    //   46	70	98	java/io/IOException
    //   30	42	103	java/io/FileNotFoundException
    //   46	70	103	java/io/FileNotFoundException
    //   11	30	108	finally
    //   125	130	108	finally
    //   147	152	108	finally
    //   169	174	108	finally
    //   11	30	118	org/json/JSONException
    //   11	30	140	java/io/IOException
    //   11	30	162	java/io/FileNotFoundException
    //   83	87	184	java/io/IOException
    //   134	138	184	java/io/IOException
    //   156	160	184	java/io/IOException
    //   178	182	184	java/io/IOException
    //   195	199	202	java/io/IOException
  }
  
  private int a(String paramString, JSONObject paramJSONObject)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("do launch game ");
    ((StringBuilder)localObject).append(String.format("callbackId:%s argsJson:%s", new Object[] { paramString, paramJSONObject }));
    ((StringBuilder)localObject).toString();
    if (this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper == null) {
      return -10;
    }
    if (!a("gameframework.launchGame")) {
      return -13;
    }
    if (!a()) {
      return -12;
    }
    if (this.jdField_a_of_type_JavaLangObject == null) {
      this.jdField_a_of_type_JavaLangObject = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.doCreateEmbeddedGameFramework(this.jdField_a_of_type_JavaUtilArrayList);
    }
    paramString = this.jdField_a_of_type_JavaLangObject;
    if (paramString == null) {
      return -11;
    }
    localObject = Integer.TYPE;
    String str1 = paramJSONObject.optString("gameId", "");
    int i = paramJSONObject.optInt("type");
    String str2 = paramJSONObject.optString("accountInfo");
    String str3 = paramJSONObject.optString("channel");
    String str4 = paramJSONObject.optString("x5custom");
    paramJSONObject = paramJSONObject.optString("exitUrl");
    invokeMethod(paramString, "run", new Class[] { String.class, localObject, String.class, String.class, String.class, String.class }, new Object[] { str1, Integer.valueOf(i), str2, str3, str4, paramJSONObject });
    return 0;
  }
  
  private FileLock a(FileOutputStream paramFileOutputStream)
  {
    if (paramFileOutputStream == null) {
      return null;
    }
    try
    {
      paramFileOutputStream = paramFileOutputStream.getChannel().tryLock();
      boolean bool = paramFileOutputStream.isValid();
      if (bool) {
        return paramFileOutputStream;
      }
    }
    catch (Exception paramFileOutputStream)
    {
      paramFileOutputStream.printStackTrace();
    }
    return null;
  }
  
  /* Error */
  private void a(int paramInt, File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 6
    //   6: aconst_null
    //   7: astore_3
    //   8: new 176	java/io/BufferedOutputStream
    //   11: dup
    //   12: new 157	java/io/FileOutputStream
    //   15: dup
    //   16: aload_2
    //   17: invokespecial 177	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   20: sipush 4096
    //   23: invokespecial 180	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;I)V
    //   26: astore_2
    //   27: new 54	org/json/JSONObject
    //   30: dup
    //   31: invokespecial 181	org/json/JSONObject:<init>	()V
    //   34: astore_3
    //   35: aload_3
    //   36: ldc 64
    //   38: iload_1
    //   39: invokevirtual 185	org/json/JSONObject:put	(Ljava/lang/String;I)Lorg/json/JSONObject;
    //   42: pop
    //   43: goto +10 -> 53
    //   46: astore 4
    //   48: aload 4
    //   50: invokevirtual 75	org/json/JSONException:printStackTrace	()V
    //   53: aload_2
    //   54: aload_3
    //   55: invokevirtual 186	org/json/JSONObject:toString	()Ljava/lang/String;
    //   58: invokevirtual 190	java/lang/String:getBytes	()[B
    //   61: invokevirtual 196	java/io/OutputStream:write	([B)V
    //   64: aload_2
    //   65: invokevirtual 197	java/io/OutputStream:close	()V
    //   68: return
    //   69: astore_3
    //   70: goto +71 -> 141
    //   73: astore 4
    //   75: goto +23 -> 98
    //   78: astore 4
    //   80: goto +39 -> 119
    //   83: astore 4
    //   85: aload_3
    //   86: astore_2
    //   87: aload 4
    //   89: astore_3
    //   90: goto +51 -> 141
    //   93: astore 4
    //   95: aload 5
    //   97: astore_2
    //   98: aload_2
    //   99: astore_3
    //   100: aload 4
    //   102: invokevirtual 74	java/io/IOException:printStackTrace	()V
    //   105: aload_2
    //   106: ifnull +34 -> 140
    //   109: aload_2
    //   110: invokevirtual 197	java/io/OutputStream:close	()V
    //   113: return
    //   114: astore 4
    //   116: aload 6
    //   118: astore_2
    //   119: aload_2
    //   120: astore_3
    //   121: aload 4
    //   123: invokevirtual 76	java/io/FileNotFoundException:printStackTrace	()V
    //   126: aload_2
    //   127: ifnull +13 -> 140
    //   130: aload_2
    //   131: invokevirtual 197	java/io/OutputStream:close	()V
    //   134: return
    //   135: astore_2
    //   136: aload_2
    //   137: invokevirtual 74	java/io/IOException:printStackTrace	()V
    //   140: return
    //   141: aload_2
    //   142: ifnull +15 -> 157
    //   145: aload_2
    //   146: invokevirtual 197	java/io/OutputStream:close	()V
    //   149: goto +8 -> 157
    //   152: astore_2
    //   153: aload_2
    //   154: invokevirtual 74	java/io/IOException:printStackTrace	()V
    //   157: aload_3
    //   158: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	159	0	this	jsGameFramework
    //   0	159	1	paramInt	int
    //   0	159	2	paramFile	File
    //   7	48	3	localJSONObject	JSONObject
    //   69	17	3	localObject1	Object
    //   89	69	3	localObject2	Object
    //   46	3	4	localJSONException	org.json.JSONException
    //   73	1	4	localIOException1	IOException
    //   78	1	4	localFileNotFoundException1	FileNotFoundException
    //   83	5	4	localObject3	Object
    //   93	8	4	localIOException2	IOException
    //   114	8	4	localFileNotFoundException2	FileNotFoundException
    //   1	95	5	localObject4	Object
    //   4	113	6	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   35	43	46	org/json/JSONException
    //   27	35	69	finally
    //   35	43	69	finally
    //   48	53	69	finally
    //   53	64	69	finally
    //   27	35	73	java/io/IOException
    //   35	43	73	java/io/IOException
    //   48	53	73	java/io/IOException
    //   53	64	73	java/io/IOException
    //   27	35	78	java/io/FileNotFoundException
    //   35	43	78	java/io/FileNotFoundException
    //   48	53	78	java/io/FileNotFoundException
    //   53	64	78	java/io/FileNotFoundException
    //   8	27	83	finally
    //   100	105	83	finally
    //   121	126	83	finally
    //   8	27	93	java/io/IOException
    //   8	27	114	java/io/FileNotFoundException
    //   64	68	135	java/io/IOException
    //   109	113	135	java/io/IOException
    //   130	134	135	java/io/IOException
    //   145	149	152	java/io/IOException
  }
  
  private void a(FileLock paramFileLock)
  {
    if (paramFileLock != null) {
      try
      {
        paramFileLock.release();
        return;
      }
      catch (Exception paramFileLock)
      {
        paramFileLock.printStackTrace();
      }
    }
  }
  
  private void a(JSONObject paramJSONObject)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onResult: ");
    ((StringBuilder)localObject).append(paramJSONObject);
    LogUtils.d("jsGameFramework", ((StringBuilder)localObject).toString());
    if (!a("gameframework.launchGame")) {
      return;
    }
    if (this.jdField_a_of_type_JavaLangObject != null)
    {
      localObject = paramJSONObject.optString("reqCode", "");
      if (!TextUtils.isEmpty((CharSequence)localObject))
      {
        int i = paramJSONObject.optInt("result", -1);
        int j = paramJSONObject.optInt("type", 0);
        paramJSONObject = paramJSONObject.optJSONObject("data");
        invokeMethod(this.jdField_a_of_type_JavaLangObject, "onResult", new Class[] { String.class, Integer.TYPE, Integer.TYPE, JSONObject.class }, new Object[] { localObject, Integer.valueOf(i), Integer.valueOf(j), paramJSONObject });
      }
    }
  }
  
  private boolean a()
  {
    boolean bool2 = a("gameframework.launchGame");
    boolean bool1 = false;
    if (!bool2) {
      return false;
    }
    Object localObject = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
    if (localObject != null)
    {
      bool2 = Configuration.getInstance(((OpenJsHelper)localObject).getContext()).isGameEmbeddedFrameworkEnabled();
      bool1 = bool2;
      if (bool2) {
        bool1 = b();
      }
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("isGameEmbeddedFrameworkEnabled: ");
    ((StringBuilder)localObject).append(bool1);
    LogUtils.d("jsGameFramework", ((StringBuilder)localObject).toString());
    return bool1;
  }
  
  private boolean a(File paramFile)
  {
    if (!paramFile.exists())
    {
      LogUtils.d("jsGameFramework", "pid file not exist yet");
      return true;
    }
    int i = a(paramFile);
    paramFile = new StringBuilder();
    paramFile.append("pid compare: ");
    paramFile.append(i);
    paramFile.append(" v.s. ");
    paramFile.append(Process.myPid());
    LogUtils.d("jsGameFramework", paramFile.toString());
    if (i >= 0)
    {
      if (i == Process.myPid()) {
        return true;
      }
      paramFile = ((ActivityManager)this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getContext().getSystemService("activity")).getRunningAppProcesses();
      if ((paramFile != null) && (!paramFile.isEmpty()))
      {
        paramFile = paramFile.iterator();
        while (paramFile.hasNext()) {
          if (i == ((ActivityManager.RunningAppProcessInfo)paramFile.next()).pid)
          {
            LogUtils.d("jsGameFramework", "previous game process still running");
            return false;
          }
        }
        return true;
      }
      LogUtils.d("jsGameFramework", "running processes is empty");
      return true;
    }
    return true;
  }
  
  private boolean a(String paramString)
  {
    OpenJsHelper localOpenJsHelper = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
    if (localOpenJsHelper == null) {
      return false;
    }
    int i = localOpenJsHelper.checkJsApiDomain(paramString);
    boolean bool = true;
    if (i != 1)
    {
      if (this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.checkQQDomain()) {
        return true;
      }
      bool = false;
    }
    return bool;
  }
  
  private boolean b()
  {
    File localFile = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getContext().getDir("files", 0);
    if ((localFile != null) && (!localFile.exists())) {
      localFile.mkdirs();
    }
    Object localObject2 = new File(localFile, "game_fw_lock");
    localFile = new File(localFile, "game_fw_pid");
    try
    {
      localObject2 = new FileOutputStream((File)localObject2);
      localFileLock = a((FileOutputStream)localObject2);
      if (localFileLock == null) {}
      try
      {
        LogUtils.d("jsGameFramework", "failed to get lock for pid lock file");
        return false;
      }
      finally
      {
        if (localFileLock == null) {
          break label217;
        }
        a(localFileLock);
        try
        {
          ((FileOutputStream)localObject2).close();
        }
        catch (IOException localIOException4)
        {
          localIOException4.printStackTrace();
        }
        LogUtils.d("jsGameFramework", "release lock for lock file");
      }
      if (!a(localIOException1))
      {
        LogUtils.d("jsGameFramework", "game running in another process");
        if (localFileLock != null) {
          a(localFileLock);
        }
        try
        {
          ((FileOutputStream)localObject2).close();
        }
        catch (IOException localIOException2)
        {
          localIOException2.printStackTrace();
        }
        LogUtils.d("jsGameFramework", "release lock for lock file");
        return false;
      }
      LogUtils.d("jsGameFramework", "save pid info to lock file");
      a(Process.myPid(), localIOException2);
      if (localFileLock != null) {
        a(localFileLock);
      }
      try
      {
        ((FileOutputStream)localObject2).close();
      }
      catch (IOException localIOException3)
      {
        localIOException3.printStackTrace();
      }
      LogUtils.d("jsGameFramework", "release lock for lock file");
      return true;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      FileLock localFileLock;
      label217:
      localFileNotFoundException.printStackTrace();
    }
    return false;
  }
  
  public void destroy() {}
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.setOriginUrl(paramString3);
    LogUtils.d("jsGameFramework", String.format("exec action:%s callbackId:%s argsJson:%s", new Object[] { paramString1, paramString2, paramJSONObject }));
    if ("launchGame".equals(paramString1)) {
      return String.valueOf(a(paramString2, paramJSONObject));
    }
    if ((!"onResult".equals(paramString1)) && (!"webToNativeCallback".equals(paramString1)))
    {
      if ("isGameFrameworkSupported".equals(paramString1)) {
        return Boolean.toString(a());
      }
    }
    else {
      a(paramJSONObject);
    }
    return null;
  }
  
  public Object invokeMethod(Object paramObject, String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    if (paramObject == null) {
      return null;
    }
    for (;;)
    {
      try
      {
        localClass = paramObject.getClass();
      }
      catch (Throwable paramArrayOfClass)
      {
        Class localClass;
        Object localObject1;
        Object localObject2;
        paramVarArgs = new StringBuilder();
        paramVarArgs.append("'");
        paramVarArgs.append(paramObject.getClass().getName());
        paramVarArgs.append("' invoke method '");
        paramVarArgs.append(paramString);
        paramVarArgs.append("' failed");
        Log.e("jsGameFramework", paramVarArgs.toString(), paramArrayOfClass);
        return null;
      }
      try
      {
        localObject1 = localClass.getMethod(paramString, paramArrayOfClass);
      }
      catch (NoSuchMethodException localNoSuchMethodException1) {}
    }
    localObject1 = null;
    localObject2 = localObject1;
    if (localObject1 == null) {
      for (;;)
      {
        localObject2 = localObject1;
        if (localClass == null) {
          break;
        }
        localObject2 = localObject1;
        if (localClass == Object.class) {
          break;
        }
        try
        {
          localObject2 = localClass.getDeclaredMethod(paramString, paramArrayOfClass);
          localObject1 = localObject2;
        }
        catch (NoSuchMethodException localNoSuchMethodException2)
        {
          for (;;) {}
        }
        if (localObject1 != null)
        {
          localObject2 = localObject1;
          break;
        }
        localClass = localClass.getSuperclass();
      }
    }
    paramArrayOfClass = ((Method)localObject2).invoke(paramObject, paramVarArgs);
    return paramArrayOfClass;
  }
  
  public void setJsApiImpl(Object paramObject) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\jsGameFramework.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */