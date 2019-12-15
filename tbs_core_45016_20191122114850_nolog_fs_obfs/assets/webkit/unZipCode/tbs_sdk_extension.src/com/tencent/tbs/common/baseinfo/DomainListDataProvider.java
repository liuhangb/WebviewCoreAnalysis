package com.tencent.tbs.common.baseinfo;

import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.tbs.common.utils.DomainMatcher;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class DomainListDataProvider
{
  private static String FILE_DOMAIN_LIST_INFO;
  private static String FILE_VIDEO_DOMAIN_LIST_INFO;
  private static final String TAG = "DomainListDataProvider";
  private static DomainListDataProvider sInstance = null;
  private static DomainListDataProvider sVideoInstance = null;
  HashMap<Integer, DomainMatcher> mDomainMatcherCache = new HashMap();
  private boolean mIsVideoInstance = false;
  
  static
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(ThreadUtils.getCurrentProcessNameIngoreColon(TbsBaseModuleShell.getCallerContext()));
    localStringBuilder.append("_tbs_dwl.inf");
    FILE_DOMAIN_LIST_INFO = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(ThreadUtils.getCurrentProcessNameIngoreColon(TbsBaseModuleShell.getCallerContext()));
    localStringBuilder.append("_tbs_video_dwl.inf");
    FILE_VIDEO_DOMAIN_LIST_INFO = localStringBuilder.toString();
  }
  
  private DomainListDataProvider()
  {
    File localFile = new File(FileUtils.getDataDir(TbsBaseModuleShell.getCallerContext()), FILE_DOMAIN_LIST_INFO);
    if (localFile.exists()) {
      localFile.delete();
    }
    localFile = new File(FileUtils.getDataDir(TbsBaseModuleShell.getCallerContext()), "tbs_dwl.inf");
    if (localFile.exists()) {
      localFile.delete();
    }
    localFile = new File(FileUtils.getDataDir(TbsBaseModuleShell.getCallerContext()), FILE_VIDEO_DOMAIN_LIST_INFO);
    if (localFile.exists()) {
      localFile.delete();
    }
  }
  
  private DomainMatcher createDomainMather(byte paramByte, int paramInt)
  {
    DomainMatcher localDomainMatcher = new DomainMatcher();
    ArrayList localArrayList2 = TbsDomainListDataProvider.getInstance(paramByte).getDomainList(paramInt);
    ArrayList localArrayList1 = localArrayList2;
    if (paramInt == 171) {
      if (localArrayList2 != null)
      {
        localArrayList1 = localArrayList2;
        if (localArrayList2.size() != 0) {}
      }
      else
      {
        localArrayList1 = localArrayList2;
        if (localArrayList2 == null) {
          localArrayList1 = new ArrayList();
        }
        localArrayList1.add("*.qq.com");
      }
    }
    localDomainMatcher.addDomainList(localArrayList1);
    return localDomainMatcher;
  }
  
  private DomainMatcher getDomainMatherInCache(int paramInt, HashMap<Integer, DomainMatcher> paramHashMap)
  {
    try
    {
      paramHashMap = (DomainMatcher)paramHashMap.get(Integer.valueOf(paramInt));
      return paramHashMap;
    }
    catch (Throwable paramHashMap)
    {
      for (;;) {}
    }
    return null;
  }
  
  public static DomainListDataProvider getInstance()
  {
    if (sInstance == null)
    {
      sInstance = new DomainListDataProvider();
      sInstance.mIsVideoInstance = false;
    }
    return sInstance;
  }
  
  public static DomainListDataProvider getVideoInstance()
  {
    if (sVideoInstance == null)
    {
      sVideoInstance = new DomainListDataProvider();
      sVideoInstance.mIsVideoInstance = true;
    }
    return sVideoInstance;
  }
  
  private void putDomainMatherToCache(int paramInt, HashMap<Integer, DomainMatcher> paramHashMap, DomainMatcher paramDomainMatcher)
  {
    try
    {
      paramHashMap.put(Integer.valueOf(paramInt), paramDomainMatcher);
      return;
    }
    catch (Throwable paramHashMap) {}
  }
  
  public void createDomainMatherCache()
  {
    this.mDomainMatcherCache = new HashMap();
  }
  
  public boolean isUrlInDomainList(byte paramByte, int paramInt, String paramString)
  {
    String str = UrlUtils.getHost(paramString);
    HashMap localHashMap = this.mDomainMatcherCache;
    if (localHashMap == null) {
      return createDomainMather(paramByte, paramInt).isContainsDomain(str);
    }
    DomainMatcher localDomainMatcher = getDomainMatherInCache(paramInt, localHashMap);
    paramString = localDomainMatcher;
    if (localDomainMatcher == null)
    {
      paramString = createDomainMather(paramByte, paramInt);
      putDomainMatherToCache(paramInt, localHashMap, paramString);
    }
    return paramString.isContainsDomain(str);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\DomainListDataProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */