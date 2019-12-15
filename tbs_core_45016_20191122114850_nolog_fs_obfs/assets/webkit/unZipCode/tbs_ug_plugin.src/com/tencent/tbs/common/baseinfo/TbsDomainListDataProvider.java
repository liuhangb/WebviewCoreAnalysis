package com.tencent.tbs.common.baseinfo;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.utils.AES256Utils;
import com.tencent.tbs.common.utils.TbsFileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TbsDomainListDataProvider
{
  private static final String TAG = "TbsDomainListDataProvider";
  private Context mContext = null;
  private SparseArray<ArrayList<String>> mDomainList = new SparseArray();
  private JSONObject mJsonObject = null;
  private byte mReqType = TbsBaseModuleShell.REQ_SRC_TBS;
  private ArrayList<String> reportUpdataDomainTypeList = new ArrayList();
  
  private TbsDomainListDataProvider()
  {
    LogUtils.d("TbsDomainListDataProvider", "init  TbsDomainListDataProvider ");
    this.mContext = TbsBaseModuleShell.getContext();
  }
  
  public static TbsDomainListDataProvider getInstance(byte paramByte)
  {
    if (paramByte == TbsBaseModuleShell.REQ_SRC_MINI_QB)
    {
      InstanceHolder.INSTANCE_FOR_QB.mReqType = paramByte;
      return InstanceHolder.INSTANCE_FOR_QB;
    }
    if (paramByte == TbsBaseModuleShell.REQ_SRC_TBS_VIDEO)
    {
      InstanceHolder.INSTANCE_FOR_VIDEO.mReqType = paramByte;
      return InstanceHolder.INSTANCE_FOR_VIDEO;
    }
    InstanceHolder.INSTANCE.mReqType = paramByte;
    return InstanceHolder.INSTANCE;
  }
  
  private ArrayList<String> jsonArrayToArrayList(JSONArray paramJSONArray)
  {
    if (paramJSONArray == null) {
      return null;
    }
    localArrayList = new ArrayList();
    int i = 0;
    try
    {
      while (i < paramJSONArray.length())
      {
        String str = paramJSONArray.getString(i);
        if (str != null) {
          localArrayList.add(str);
        }
        i += 1;
      }
      return localArrayList;
    }
    catch (JSONException paramJSONArray)
    {
      LogUtils.d("TbsDomainListDataProvider", Log.getStackTraceString(paramJSONArray));
    }
  }
  
  private String sparseArrayToJson()
  {
    localJSONObject = new JSONObject();
    int i = 0;
    try
    {
      while (i < this.mDomainList.size())
      {
        Object localObject1 = (ArrayList)this.mDomainList.valueAt(i);
        if ((localObject1 != null) && (!((ArrayList)localObject1).isEmpty()))
        {
          int j = this.mDomainList.keyAt(i);
          Object localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("sparseArrayToJson type =");
          ((StringBuilder)localObject2).append(j);
          LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject2).toString());
          localObject2 = new JSONArray();
          localObject1 = ((ArrayList)localObject1).iterator();
          while (((Iterator)localObject1).hasNext()) {
            ((JSONArray)localObject2).put((String)((Iterator)localObject1).next());
          }
          localJSONObject.put(String.valueOf(j), localObject2);
        }
        i += 1;
      }
      return localJSONObject.toString();
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      LogUtils.d("TbsDomainListDataProvider", Log.getStackTraceString(localOutOfMemoryError));
    }
    catch (Exception localException)
    {
      LogUtils.d("TbsDomainListDataProvider", Log.getStackTraceString(localException));
    }
  }
  
  /* Error */
  public void addDomainList(int paramInt, ArrayList<String> paramArrayList)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new 135	java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial 136	java/lang/StringBuilder:<init>	()V
    //   9: astore_3
    //   10: aload_3
    //   11: ldc -77
    //   13: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: pop
    //   17: aload_3
    //   18: iload_1
    //   19: invokevirtual 145	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   22: pop
    //   23: aload_3
    //   24: ldc -75
    //   26: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   29: pop
    //   30: aload_3
    //   31: aload_2
    //   32: invokevirtual 184	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   35: pop
    //   36: ldc 13
    //   38: aload_3
    //   39: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   42: invokestatic 58	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   45: iload_1
    //   46: ifge +6 -> 52
    //   49: aload_0
    //   50: monitorexit
    //   51: return
    //   52: aload_0
    //   53: getfield 34	com/tencent/tbs/common/baseinfo/TbsDomainListDataProvider:mDomainList	Landroid/util/SparseArray;
    //   56: iload_1
    //   57: invokevirtual 187	android/util/SparseArray:get	(I)Ljava/lang/Object;
    //   60: checkcast 47	java/util/ArrayList
    //   63: astore_3
    //   64: aload_0
    //   65: iload_1
    //   66: aload_3
    //   67: aload_2
    //   68: invokevirtual 191	com/tencent/tbs/common/baseinfo/TbsDomainListDataProvider:recodeDomainUpdateInfo	(ILjava/util/ArrayList;Ljava/util/ArrayList;)V
    //   71: aload_3
    //   72: ifnonnull +15 -> 87
    //   75: aload_0
    //   76: getfield 34	com/tencent/tbs/common/baseinfo/TbsDomainListDataProvider:mDomainList	Landroid/util/SparseArray;
    //   79: iload_1
    //   80: aload_2
    //   81: invokevirtual 194	android/util/SparseArray:put	(ILjava/lang/Object;)V
    //   84: goto +34 -> 118
    //   87: new 47	java/util/ArrayList
    //   90: dup
    //   91: invokespecial 48	java/util/ArrayList:<init>	()V
    //   94: astore_3
    //   95: aload_3
    //   96: aload_2
    //   97: invokevirtual 198	java/util/ArrayList:addAll	(Ljava/util/Collection;)Z
    //   100: pop
    //   101: aload_0
    //   102: getfield 34	com/tencent/tbs/common/baseinfo/TbsDomainListDataProvider:mDomainList	Landroid/util/SparseArray;
    //   105: iload_1
    //   106: invokevirtual 202	android/util/SparseArray:remove	(I)V
    //   109: aload_0
    //   110: getfield 34	com/tencent/tbs/common/baseinfo/TbsDomainListDataProvider:mDomainList	Landroid/util/SparseArray;
    //   113: iload_1
    //   114: aload_3
    //   115: invokevirtual 194	android/util/SparseArray:put	(ILjava/lang/Object;)V
    //   118: aload_0
    //   119: monitorexit
    //   120: return
    //   121: astore_2
    //   122: aload_0
    //   123: monitorexit
    //   124: aload_2
    //   125: athrow
    //   126: astore_2
    //   127: goto -9 -> 118
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	130	0	this	TbsDomainListDataProvider
    //   0	130	1	paramInt	int
    //   0	130	2	paramArrayList	ArrayList<String>
    //   9	106	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	45	121	finally
    //   52	71	121	finally
    //   75	84	121	finally
    //   87	118	121	finally
    //   87	118	126	java/lang/Exception
  }
  
  /* Error */
  public boolean addDomainList(JSONObject paramJSONObject)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnonnull +7 -> 10
    //   6: aload_0
    //   7: monitorexit
    //   8: iconst_0
    //   9: ireturn
    //   10: aload_1
    //   11: invokevirtual 207	org/json/JSONObject:keys	()Ljava/util/Iterator;
    //   14: astore_2
    //   15: aload_2
    //   16: invokeinterface 158 1 0
    //   21: ifeq +74 -> 95
    //   24: aload_2
    //   25: invokeinterface 162 1 0
    //   30: checkcast 164	java/lang/String
    //   33: astore_3
    //   34: aload_0
    //   35: aload_1
    //   36: aload_3
    //   37: invokevirtual 211	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   40: invokespecial 213	com/tencent/tbs/common/baseinfo/TbsDomainListDataProvider:jsonArrayToArrayList	(Lorg/json/JSONArray;)Ljava/util/ArrayList;
    //   43: astore 4
    //   45: new 135	java/lang/StringBuilder
    //   48: dup
    //   49: invokespecial 136	java/lang/StringBuilder:<init>	()V
    //   52: astore 5
    //   54: aload 5
    //   56: ldc -41
    //   58: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: pop
    //   62: aload 5
    //   64: aload_3
    //   65: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   68: pop
    //   69: ldc 13
    //   71: aload 5
    //   73: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   76: invokestatic 58	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   79: aload_0
    //   80: getfield 34	com/tencent/tbs/common/baseinfo/TbsDomainListDataProvider:mDomainList	Landroid/util/SparseArray;
    //   83: aload_3
    //   84: invokestatic 221	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   87: aload 4
    //   89: invokevirtual 194	android/util/SparseArray:put	(ILjava/lang/Object;)V
    //   92: goto -77 -> 15
    //   95: aload_0
    //   96: monitorexit
    //   97: iconst_1
    //   98: ireturn
    //   99: astore_1
    //   100: goto +17 -> 117
    //   103: astore_1
    //   104: ldc 13
    //   106: aload_1
    //   107: invokestatic 107	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   110: invokestatic 58	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   113: aload_0
    //   114: monitorexit
    //   115: iconst_0
    //   116: ireturn
    //   117: aload_0
    //   118: monitorexit
    //   119: aload_1
    //   120: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	121	0	this	TbsDomainListDataProvider
    //   0	121	1	paramJSONObject	JSONObject
    //   14	11	2	localIterator	Iterator
    //   33	51	3	str	String
    //   43	45	4	localArrayList	ArrayList
    //   52	20	5	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   10	15	99	finally
    //   15	92	99	finally
    //   104	113	99	finally
    //   10	15	103	java/lang/Exception
    //   15	92	103	java/lang/Exception
  }
  
  public int addDomainsGodCmd(int paramInt, ArrayList<String> paramArrayList)
  {
    Object localObject1;
    for (;;)
    {
      Object localObject2;
      int i;
      Object localObject3;
      try
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("addDomainsGodCmd: type=");
        ((StringBuilder)localObject1).append(paramInt);
        ((StringBuilder)localObject1).append(", domains=");
        ((StringBuilder)localObject1).append(paramArrayList);
        ((StringBuilder)localObject1).append(" [ mReqType = ");
        ((StringBuilder)localObject1).append(this.mReqType);
        ((StringBuilder)localObject1).append("]");
        LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject1).toString());
        if (paramInt < 0) {
          return -1;
        }
        localObject1 = (ArrayList)this.mDomainList.get(paramInt);
        if (localObject1 == null)
        {
          this.mDomainList.put(paramInt, paramArrayList);
          paramInt = paramArrayList.size();
          return paramInt;
        }
      }
      finally {}
      try
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("origin resultList size=");
        ((StringBuilder)localObject2).append(((ArrayList)localObject1).size());
        LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject2).toString());
        i = 0;
        if (i >= paramArrayList.size()) {
          continue;
        }
        localObject2 = (String)paramArrayList.get(i);
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append(" addDomainsGodCmd god cmd add domain =");
        ((StringBuilder)localObject3).append((String)localObject2);
        LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject3).toString());
        j = 0;
      }
      catch (Exception paramArrayList)
      {
        continue;
        j += 1;
        continue;
      }
      if (j < ((ArrayList)localObject1).size())
      {
        localObject3 = (String)((ArrayList)localObject1).get(j);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("addDomainsGodCmd ---List ");
        localStringBuilder.append((String)localObject3);
        LogUtils.d("TbsDomainListDataProvider", localStringBuilder.toString());
        if (!((String)localObject2).equals(localObject3)) {
          break label396;
        }
        LogUtils.d("TbsDomainListDataProvider", " addDomainsGodCmd god cmd add domain Override");
        ((ArrayList)localObject1).remove(j);
        break label396;
      }
      ((ArrayList)localObject1).add(localObject2);
      i += 1;
    }
    paramArrayList = (ArrayList)this.mDomainList.get(paramInt);
    if (paramArrayList != null)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(" addDomainsGodCmd origin resultList size=");
      ((StringBuilder)localObject1).append(paramArrayList.size());
      LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject1).toString());
      paramInt = paramArrayList.size();
      return paramInt;
    }
    return 0;
    return -1;
  }
  
  public int clearDomainsGodCmd(int paramInt)
  {
    if (paramInt < 0) {
      return -1;
    }
    try
    {
      ArrayList localArrayList = (ArrayList)this.mDomainList.get(paramInt);
      if (localArrayList == null) {
        return -1;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(" (GodCmdWupManager) clearDomainsGodCmd origin resultList size=");
      localStringBuilder.append(localArrayList.size());
      LogUtils.d("TbsDomainListDataProvider", localStringBuilder.toString());
      localArrayList.clear();
      return 0;
    }
    finally {}
  }
  
  public int deleteDomainsGodCmd(int paramInt, ArrayList<String> paramArrayList)
  {
    Object localObject1;
    Object localObject2;
    label149:
    label206:
    label396:
    try
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(" deleteDomainsGodCmd (GodCmdWupManager): type=");
      ((StringBuilder)localObject1).append(paramInt);
      ((StringBuilder)localObject1).append(", domains=");
      ((StringBuilder)localObject1).append(paramArrayList);
      ((StringBuilder)localObject1).append(" [ mReqType = ");
      ((StringBuilder)localObject1).append(this.mReqType);
      ((StringBuilder)localObject1).append("]");
      LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject1).toString());
      if (paramInt < 0) {
        return -1;
      }
      localObject1 = (ArrayList)this.mDomainList.get(paramInt);
      if (localObject1 == null) {
        return 0;
      }
    }
    finally {}
    try
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("deleteDomainsGodCmd (GodCmdWupManager) prigin resultList size=");
      ((StringBuilder)localObject2).append(((ArrayList)localObject1).size());
      LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject2).toString());
      i = 0;
    }
    catch (Exception paramArrayList)
    {
      break label396;
      j += 1;
      break label206;
      i += 1;
      break label149;
    }
    if (i < paramArrayList.size())
    {
      localObject2 = (String)paramArrayList.get(i);
      Object localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append(" (GodCmdWupManager)  god cmd delete domain =");
      ((StringBuilder)localObject3).append((String)localObject2);
      LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject3).toString());
      j = 0;
      if (j >= ((ArrayList)localObject1).size()) {
        break label418;
      }
      localObject3 = (String)((ArrayList)localObject1).get(j);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("  (GodCmdWupManager)  ---List ");
      localStringBuilder.append((String)localObject3);
      LogUtils.d("TbsDomainListDataProvider", localStringBuilder.toString());
      if (((String)localObject2).equals(localObject3))
      {
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append(" (GodCmdWupManager)  god cmd delete Domain =");
        ((StringBuilder)localObject3).append((String)((ArrayList)localObject1).get(j));
        LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject3).toString());
        ((ArrayList)localObject1).remove(j);
      }
    }
    else
    {
      paramArrayList = (ArrayList)this.mDomainList.get(paramInt);
      if (paramArrayList != null)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(" (GodCmdWupManager)  origin resultList size=");
        ((StringBuilder)localObject1).append(paramArrayList.size());
        LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject1).toString());
        paramInt = paramArrayList.size();
        return paramInt;
      }
      return 0;
      return -1;
    }
  }
  
  public ArrayList<String> getDomainList(int paramInt)
  {
    Object localObject3;
    label75:
    try
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("getDomainList: type=");
      ((StringBuilder)localObject1).append(paramInt);
      LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject1).toString());
      if (paramInt < 0)
      {
        localObject1 = new ArrayList();
        return (ArrayList<String>)localObject1;
      }
      localObject1 = null;
    }
    finally {}
    try
    {
      localObject3 = (ArrayList)this.mDomainList.get(paramInt);
      localObject1 = localObject3;
    }
    catch (Exception localException)
    {
      break label75;
    }
    if (localObject1 != null) {
      return (ArrayList<String>)localObject1;
    }
    if (this.mJsonObject == null)
    {
      long l = System.currentTimeMillis();
      this.mJsonObject = loadJsonObject();
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("TBSDOMAINLIST load time = ");
      ((StringBuilder)localObject1).append(System.currentTimeMillis() - l);
      LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject1).toString());
    }
    if (this.mJsonObject == null)
    {
      localObject1 = new ArrayList();
      return (ArrayList<String>)localObject1;
    }
    Object localObject1 = jsonArrayToArrayList(this.mJsonObject.optJSONArray(String.valueOf(paramInt)));
    if (localObject1 != null)
    {
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("getDomainList: type=");
      ((StringBuilder)localObject3).append(paramInt);
      ((StringBuilder)localObject3).append(", domains=");
      ((StringBuilder)localObject3).append(localObject1);
      LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject3).toString());
      return (ArrayList<String>)localObject1;
    }
    localObject1 = new ArrayList();
    return (ArrayList<String>)localObject1;
  }
  
  public ArrayList<String> getDomainListFromJsonTest(int paramInt)
  {
    try
    {
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("(GodCmdWupManager)  getDomainListFromJsonTest: type=");
      ((StringBuilder)localObject1).append(paramInt);
      LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject1).toString());
      if (paramInt < 0)
      {
        localObject1 = new ArrayList();
        return (ArrayList<String>)localObject1;
      }
      localObject1 = loadJsonObject();
      long l = System.currentTimeMillis();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(" (GodCmdWupManager)  getDomainListFromJsonTest load time = ");
      localStringBuilder.append(System.currentTimeMillis() - l);
      LogUtils.d("TbsDomainListDataProvider", localStringBuilder.toString());
      if (localObject1 == null)
      {
        localObject1 = new ArrayList();
        return (ArrayList<String>)localObject1;
      }
      localObject1 = jsonArrayToArrayList(((JSONObject)localObject1).optJSONArray(String.valueOf(paramInt)));
      if (localObject1 != null)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(" (GodCmdWupManager)  getDomainListFromJsonTest: type=");
        localStringBuilder.append(paramInt);
        localStringBuilder.append(", domains= [ ");
        localStringBuilder.append(((ArrayList)localObject1).toString());
        localStringBuilder.append("]");
        LogUtils.d("TbsDomainListDataProvider", localStringBuilder.toString());
        return (ArrayList<String>)localObject1;
      }
      localObject1 = new ArrayList();
      return (ArrayList<String>)localObject1;
    }
    finally {}
  }
  
  public boolean hasDomainListData()
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("hasDomainListData: mDomainList.size() ");
      localStringBuilder.append(this.mDomainList.size());
      LogUtils.d("TbsDomainListDataProvider", localStringBuilder.toString());
      int i = this.mDomainList.size();
      boolean bool;
      if (i > 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public JSONObject loadJsonObject()
  {
    Object localObject = NioFileUtils.getInstance().readWithMappedByteBuffer(TbsFileUtils.getDomainJsonFile(this.mContext, this.mReqType), 2);
    if (localObject != null)
    {
      if (localObject.length <= 2) {
        return null;
      }
      long l = System.currentTimeMillis();
      try
      {
        byte[] arrayOfByte = AES256Utils.decrypt((byte[])localObject, AES256Utils.generateKey());
        localObject = arrayOfByte;
      }
      catch (Exception localException2)
      {
        LogUtils.d("TbsDomainListDataProvider", Log.getStackTraceString(localException2));
      }
      if ((localObject != null) && (localObject.length > 2))
      {
        StringBuilder localStringBuilder2 = new StringBuilder();
        localStringBuilder2.append("loadJsonObject bytes =");
        localStringBuilder2.append(localObject.length);
        localStringBuilder2.append(", decrypt time = ");
        localStringBuilder2.append(System.currentTimeMillis() - l);
        LogUtils.d("TbsDomainListDataProvider", localStringBuilder2.toString());
        try
        {
          localObject = new JSONObject(new String((byte[])localObject));
          return (JSONObject)localObject;
        }
        catch (OutOfMemoryError localOutOfMemoryError)
        {
          LogUtils.d("TbsDomainListDataProvider", Log.getStackTraceString(localOutOfMemoryError));
          return null;
        }
        catch (Exception localException1)
        {
          LogUtils.d("TbsDomainListDataProvider", Log.getStackTraceString(localException1));
          return null;
        }
      }
      StringBuilder localStringBuilder1 = new StringBuilder();
      localStringBuilder1.append("key is error. delete file : ");
      localStringBuilder1.append(TbsFileUtils.getDomainJsonFile(this.mContext, this.mReqType).getName());
      LogUtils.d("TbsDomainListDataProvider", localStringBuilder1.toString());
      TbsFileUtils.getDomainJsonFile(this.mContext, this.mReqType).delete();
      return null;
    }
    return null;
  }
  
  public void recodeDomainUpdateInfo(int paramInt, ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2)
  {
    if (this.mReqType == TbsBaseModuleShell.REQ_SRC_TBS)
    {
      StringBuilder localStringBuilder;
      if (paramArrayList1 == null)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(" (GodCmdWupManager) reportDomainUpdateInfo infDomains null = ");
        localStringBuilder.append(paramInt);
        LogUtils.d("TbsDomainListDataProvider", localStringBuilder.toString());
      }
      int i;
      if (paramArrayList1 != null) {
        i = paramArrayList1.size();
      } else {
        i = 0;
      }
      if ((paramArrayList1 == null) && (paramArrayList2 != null))
      {
        i = paramArrayList2.size();
        paramArrayList1 = new StringBuilder();
        paramArrayList1.append(" (GodCmdWupManager)  reportDomainUpdateInfo new Type ");
        paramArrayList1.append(paramInt);
        paramArrayList1.append(" size = ");
        paramArrayList1.append(i);
        LogUtils.d("TbsDomainListDataProvider", paramArrayList1.toString());
        paramArrayList1 = this.reportUpdataDomainTypeList;
        paramArrayList2 = new StringBuilder();
        paramArrayList2.append(paramInt);
        paramArrayList2.append("");
        paramArrayList1.add(paramArrayList2.toString());
        return;
      }
      if ((paramArrayList1 != null) && (paramArrayList2 != null))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("(GodCmdWupManager)  reportDomainUpdateInf type=");
        localStringBuilder.append(paramInt);
        localStringBuilder.append(" infDomainsSize=");
        localStringBuilder.append(i);
        localStringBuilder.append(" wupDomainsSize=");
        localStringBuilder.append(0);
        localStringBuilder.append("infDomainsHash=");
        localStringBuilder.append(paramArrayList1.hashCode());
        localStringBuilder.append(" wupDomainsHash=");
        localStringBuilder.append(paramArrayList2.hashCode());
        LogUtils.d("TbsDomainListDataProvider", localStringBuilder.toString());
        if (paramArrayList1.hashCode() != paramArrayList2.hashCode())
        {
          paramArrayList1 = this.reportUpdataDomainTypeList;
          paramArrayList2 = new StringBuilder();
          paramArrayList2.append(paramInt);
          paramArrayList2.append("");
          paramArrayList1.add(paramArrayList2.toString());
          paramArrayList1 = new StringBuilder();
          paramArrayList1.append("(GodCmdWupManager) reportDomainUpdateInfo the UpdateInfo Type ");
          paramArrayList1.append(paramInt);
          paramArrayList1.append(" size = ");
          paramArrayList1.append(0);
          paramArrayList1.append(" infDomainsSize=");
          paramArrayList1.append(i);
          LogUtils.d("TbsDomainListDataProvider", paramArrayList1.toString());
          return;
        }
        paramArrayList1 = new StringBuilder();
        paramArrayList1.append("(GodCmdWupManager) reportDomainUpdateInfo No UpdateInfo type=");
        paramArrayList1.append(paramInt);
        LogUtils.d("TbsDomainListDataProvider", paramArrayList1.toString());
      }
    }
  }
  
  public void reportDomainUpdateInfo()
  {
    if (this.reportUpdataDomainTypeList.size() > 0)
    {
      HashMap localHashMap = new HashMap();
      Object localObject1 = this.reportUpdataDomainTypeList.toString();
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("reportDomainUpdateInfo Beacon UpdataInfo=");
      ((StringBuilder)localObject2).append((String)localObject1);
      LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject2).toString());
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(this.reportUpdataDomainTypeList.size());
      ((StringBuilder)localObject2).append("");
      localHashMap.put("updateTypesCount", ((StringBuilder)localObject2).toString());
      localHashMap.put("updateTypes", localObject1);
      localObject2 = "";
      ArrayList localArrayList = (ArrayList)this.mDomainList.get(1001);
      localObject1 = localObject2;
      if (localArrayList != null)
      {
        localObject1 = localObject2;
        if (localArrayList.size() > 0) {
          localObject1 = (String)localArrayList.get(0);
        }
      }
      localHashMap.put("domainTime", localObject1);
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(System.currentTimeMillis());
      ((StringBuilder)localObject1).append("");
      localHashMap.put("savaDomainTime", ((StringBuilder)localObject1).toString());
      localHashMap.put("processName", ThreadUtils.getCurrentProcessNameIngoreColon(this.mContext));
      localHashMap.put("version", "1");
      X5CoreBeaconUploader.getInstance(this.mContext).upLoadToBeacon("MTT_CORE_DOMAIN_UPDATE", localHashMap);
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(" (GodCmdWupManager)  reportDomainUpdateInfo MTT_CORE_DOMAIN_UPDATE = ");
      ((StringBuilder)localObject1).append(localHashMap.toString());
      LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject1).toString());
      this.reportUpdataDomainTypeList.clear();
      return;
    }
    LogUtils.d("TbsDomainListDataProvider", " (GodCmdWupManager)  reportDomainUpdateInfo Not Update");
  }
  
  public void saveDomainList()
  {
    try
    {
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("saveDomainList -- start --   count =");
      ((StringBuilder)localObject1).append(this.mDomainList.size());
      LogUtils.d("TbsDomainListDataProvider", ((StringBuilder)localObject1).toString());
      if (this.mDomainList.size() <= 1)
      {
        LogUtils.d("TbsDomainListDataProvider", "ERROR=DOMAIN LIST TOO SMALL Null");
        return;
      }
      if (this.mReqType == TbsBaseModuleShell.REQ_SRC_TBS) {
        reportDomainUpdateInfo();
      }
      Object localObject3 = sparseArrayToJson();
      long l = System.currentTimeMillis();
      localObject1 = new byte[0];
      try
      {
        localObject3 = AES256Utils.encrypt(((String)localObject3).getBytes(), AES256Utils.generateKey());
        localObject1 = localObject3;
      }
      catch (Exception localException)
      {
        LogUtils.d("TbsDomainListDataProvider", Log.getStackTraceString(localException));
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("saveDomainList bytes =");
      localStringBuilder.append(localObject1.length);
      localStringBuilder.append(", encrypt time = ");
      localStringBuilder.append(System.currentTimeMillis() - l);
      LogUtils.d("TbsDomainListDataProvider", localStringBuilder.toString());
      NioFileUtils.getInstance().writeWithMappedByteBuffer(TbsFileUtils.getDomainJsonFile(this.mContext, this.mReqType), (byte[])localObject1, 2);
      return;
    }
    finally {}
  }
  
  private static class InstanceHolder
  {
    private static final TbsDomainListDataProvider INSTANCE = new TbsDomainListDataProvider(null);
    private static final TbsDomainListDataProvider INSTANCE_FOR_QB = new TbsDomainListDataProvider(null);
    private static final TbsDomainListDataProvider INSTANCE_FOR_VIDEO = new TbsDomainListDataProvider(null);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\TbsDomainListDataProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */