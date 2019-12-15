package com.tencent.tbs.common.utils;

import java.util.ArrayList;

public class VideoDomainMatcher
{
  private DomainMatcher mVideoSameLayerDomainMatcher = null;
  private DomainMatcher mVideoSameLayerUrlMatcher = null;
  
  public void addDomain(String paramString)
  {
    if (paramString.contains("/"))
    {
      if (paramString.startsWith("^")) {
        this.mVideoSameLayerUrlMatcher.addDomain(paramString);
      }
    }
    else {
      this.mVideoSameLayerDomainMatcher.addDomain(paramString);
    }
  }
  
  public void addDomainList(ArrayList<String> paramArrayList)
  {
    if ((paramArrayList != null) && (!paramArrayList.isEmpty()))
    {
      int i = 0;
      while (i < paramArrayList.size())
      {
        addDomain((String)paramArrayList.get(i));
        i += 1;
      }
    }
  }
  
  public void clear()
  {
    this.mVideoSameLayerDomainMatcher.clear();
    this.mVideoSameLayerUrlMatcher.clear();
  }
  
  public boolean isContainsDomain(String paramString)
  {
    return this.mVideoSameLayerDomainMatcher.isContainsDomain(paramString);
  }
  
  public boolean isContainsUrl(String paramString)
  {
    return this.mVideoSameLayerUrlMatcher.isContainsDomain(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\VideoDomainMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */