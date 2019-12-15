package com.tencent.tbs.common.baseinfo;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.utils.AES256Utils;
import com.tencent.tbs.common.utils.TbsFileUtils;
import java.io.File;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class TbsUserInfoDataProvider
{
  private static final String TAG = "TbsUserInfoDataProvider";
  private Context mContext = null;
  private JSONObject mJsonObject = null;
  private SparseArray<Object> mUserInfo = new SparseArray();
  
  public static TbsUserInfoDataProvider getInstance()
  {
    return InstanceHolder.INSTANCE;
  }
  
  public void addUserInfoData(int paramInt, Object paramObject)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("addUserInfoData type = ");
      localStringBuilder.append(paramInt);
      localStringBuilder.append(",  flag = ");
      localStringBuilder.append(paramObject);
      LogUtils.d("TbsUserInfoDataProvider", localStringBuilder.toString());
      if (paramInt < 0) {
        return;
      }
      this.mUserInfo.put(paramInt, paramObject);
      return;
    }
    finally {}
  }
  
  public boolean addUserInfoData(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return false;
    }
    for (;;)
    {
      String str;
      Object localObject;
      try
      {
        Iterator localIterator = paramJSONObject.keys();
        if (localIterator.hasNext())
        {
          str = (String)localIterator.next();
          localObject = paramJSONObject.opt(str);
        }
      }
      finally {}
      try
      {
        this.mUserInfo.put(Integer.parseInt(str), localObject);
      }
      catch (NumberFormatException paramJSONObject) {}
    }
    this.mUserInfo.clear();
    return false;
    return true;
  }
  
  /* Error */
  public Object getUserInfoData(int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new 51	java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial 52	java/lang/StringBuilder:<init>	()V
    //   9: astore 4
    //   11: aload 4
    //   13: ldc 119
    //   15: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: pop
    //   19: aload 4
    //   21: iload_1
    //   22: invokevirtual 61	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   25: pop
    //   26: ldc 13
    //   28: aload 4
    //   30: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   33: invokestatic 76	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   36: iload_1
    //   37: ifge +7 -> 44
    //   40: aload_0
    //   41: monitorexit
    //   42: aconst_null
    //   43: areturn
    //   44: aload_0
    //   45: getfield 29	com/tencent/tbs/common/baseinfo/TbsUserInfoDataProvider:mUserInfo	Landroid/util/SparseArray;
    //   48: iload_1
    //   49: invokevirtual 122	android/util/SparseArray:get	(I)Ljava/lang/Object;
    //   52: astore 4
    //   54: aload 4
    //   56: ifnull +8 -> 64
    //   59: aload_0
    //   60: monitorexit
    //   61: aload 4
    //   63: areturn
    //   64: aload_0
    //   65: getfield 31	com/tencent/tbs/common/baseinfo/TbsUserInfoDataProvider:mJsonObject	Lorg/json/JSONObject;
    //   68: ifnonnull +53 -> 121
    //   71: invokestatic 128	java/lang/System:currentTimeMillis	()J
    //   74: lstore_2
    //   75: aload_0
    //   76: aload_0
    //   77: invokevirtual 132	com/tencent/tbs/common/baseinfo/TbsUserInfoDataProvider:loadJsonObject	()Lorg/json/JSONObject;
    //   80: putfield 31	com/tencent/tbs/common/baseinfo/TbsUserInfoDataProvider:mJsonObject	Lorg/json/JSONObject;
    //   83: new 51	java/lang/StringBuilder
    //   86: dup
    //   87: invokespecial 52	java/lang/StringBuilder:<init>	()V
    //   90: astore 5
    //   92: aload 5
    //   94: ldc -122
    //   96: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: pop
    //   100: aload 5
    //   102: invokestatic 128	java/lang/System:currentTimeMillis	()J
    //   105: lload_2
    //   106: lsub
    //   107: invokevirtual 137	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   110: pop
    //   111: ldc 13
    //   113: aload 5
    //   115: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   118: invokestatic 76	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   121: aload_0
    //   122: getfield 31	com/tencent/tbs/common/baseinfo/TbsUserInfoDataProvider:mJsonObject	Lorg/json/JSONObject;
    //   125: astore 5
    //   127: aload 5
    //   129: ifnonnull +7 -> 136
    //   132: aload_0
    //   133: monitorexit
    //   134: aconst_null
    //   135: areturn
    //   136: aload_0
    //   137: getfield 31	com/tencent/tbs/common/baseinfo/TbsUserInfoDataProvider:mJsonObject	Lorg/json/JSONObject;
    //   140: iload_1
    //   141: invokestatic 141	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   144: invokevirtual 145	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   147: ifeq +23 -> 170
    //   150: aload_0
    //   151: getfield 31	com/tencent/tbs/common/baseinfo/TbsUserInfoDataProvider:mJsonObject	Lorg/json/JSONObject;
    //   154: iload_1
    //   155: invokestatic 141	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   158: invokevirtual 147	org/json/JSONObject:get	(Ljava/lang/String;)Ljava/lang/Object;
    //   161: astore 5
    //   163: aload 5
    //   165: astore 4
    //   167: goto +19 -> 186
    //   170: aload_0
    //   171: monitorexit
    //   172: aconst_null
    //   173: areturn
    //   174: astore 5
    //   176: ldc 13
    //   178: aload 5
    //   180: invokestatic 153	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   183: invokestatic 76	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   186: aload 4
    //   188: ifnull +58 -> 246
    //   191: new 51	java/lang/StringBuilder
    //   194: dup
    //   195: invokespecial 52	java/lang/StringBuilder:<init>	()V
    //   198: astore 5
    //   200: aload 5
    //   202: ldc 119
    //   204: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   207: pop
    //   208: aload 5
    //   210: iload_1
    //   211: invokevirtual 61	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   214: pop
    //   215: aload 5
    //   217: ldc -101
    //   219: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   222: pop
    //   223: aload 5
    //   225: aload 4
    //   227: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   230: pop
    //   231: ldc 13
    //   233: aload 5
    //   235: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   238: invokestatic 76	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   241: aload_0
    //   242: monitorexit
    //   243: aload 4
    //   245: areturn
    //   246: aload_0
    //   247: monitorexit
    //   248: aconst_null
    //   249: areturn
    //   250: astore 4
    //   252: aload_0
    //   253: monitorexit
    //   254: aload 4
    //   256: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	257	0	this	TbsUserInfoDataProvider
    //   0	257	1	paramInt	int
    //   74	32	2	l	long
    //   9	235	4	localObject1	Object
    //   250	5	4	localObject2	Object
    //   90	74	5	localObject3	Object
    //   174	5	5	localJSONException	JSONException
    //   198	36	5	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   136	163	174	org/json/JSONException
    //   2	36	250	finally
    //   44	54	250	finally
    //   64	121	250	finally
    //   121	127	250	finally
    //   136	163	250	finally
    //   176	186	250	finally
    //   191	241	250	finally
  }
  
  public JSONObject loadJsonObject()
  {
    byte[] arrayOfByte = NioFileUtils.getInstance().readWithMappedByteBuffer(TbsFileUtils.getUserInfoFile(this.mContext), 1);
    if (arrayOfByte != null)
    {
      if (arrayOfByte.length <= 2) {
        return null;
      }
      Object localObject2;
      try
      {
        StringBuilder localStringBuilder2 = new StringBuilder();
        localStringBuilder2.append("TbsUserInfoDataProvider decrypt key = ");
        localStringBuilder2.append(AES256Utils.generateKey());
        LogUtils.d("TbsUserInfoDataProvider", localStringBuilder2.toString());
        arrayOfByte = AES256Utils.decrypt(arrayOfByte, AES256Utils.generateKey());
      }
      catch (StackOverflowError localStackOverflowError)
      {
        LogUtils.d("TbsUserInfoDataProvider", Log.getStackTraceString(localStackOverflowError));
        Object localObject1 = null;
      }
      catch (Exception localException)
      {
        LogUtils.d("TbsUserInfoDataProvider", Log.getStackTraceString(localException));
        localObject2 = null;
      }
      if ((localObject2 != null) && (localObject2.length > 2)) {
        try
        {
          localObject2 = new JSONObject(new String((byte[])localObject2));
          return (JSONObject)localObject2;
        }
        catch (JSONException localJSONException)
        {
          LogUtils.d("TbsUserInfoDataProvider", Log.getStackTraceString(localJSONException));
          return null;
        }
      }
      StringBuilder localStringBuilder1 = new StringBuilder();
      localStringBuilder1.append("key is error. delete file : ");
      localStringBuilder1.append(TbsFileUtils.getUserInfoFile(this.mContext).getName());
      LogUtils.d("TbsUserInfoDataProvider", localStringBuilder1.toString());
      TbsFileUtils.getUserInfoFile(this.mContext).delete();
      return null;
    }
    return null;
  }
  
  public void saveUserInfoData()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("saveUserInfoData  size = ");
    ((StringBuilder)localObject).append(this.mUserInfo.size());
    LogUtils.d("TbsUserInfoDataProvider", ((StringBuilder)localObject).toString());
    localObject = sparseArrayToString();
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("TbsUserInfoDataProvider encrypt key = ");
      localStringBuilder.append(AES256Utils.generateKey());
      LogUtils.d("TbsUserInfoDataProvider", localStringBuilder.toString());
      localObject = AES256Utils.encrypt(((String)localObject).getBytes(), AES256Utils.generateKey());
      NioFileUtils.getInstance().writeWithMappedByteBuffer(TbsFileUtils.getUserInfoFile(this.mContext), (byte[])localObject, 1);
      return;
    }
    catch (Exception localException)
    {
      LogUtils.d("TbsUserInfoDataProvider", Log.getStackTraceString(localException));
    }
  }
  
  public String sparseArrayToString()
  {
    localJSONObject = new JSONObject();
    int i = 0;
    try
    {
      while (i < this.mUserInfo.size())
      {
        Object localObject = this.mUserInfo.valueAt(i);
        if (localObject != null) {
          localJSONObject.put(String.valueOf(this.mUserInfo.keyAt(i)), localObject);
        }
        i += 1;
      }
      return localJSONObject.toString();
    }
    catch (Exception localException)
    {
      LogUtils.d("TbsUserInfoDataProvider", Log.getStackTraceString(localException));
    }
  }
  
  private static class InstanceHolder
  {
    private static final TbsUserInfoDataProvider INSTANCE = new TbsUserInfoDataProvider(null);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\TbsUserInfoDataProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */