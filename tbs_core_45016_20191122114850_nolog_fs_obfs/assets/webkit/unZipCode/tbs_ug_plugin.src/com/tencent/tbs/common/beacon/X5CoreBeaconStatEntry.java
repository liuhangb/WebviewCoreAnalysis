package com.tencent.tbs.common.beacon;

import android.text.TextUtils;
import java.util.HashMap;

public class X5CoreBeaconStatEntry
{
  public boolean isProxy = false;
  public int mCacheType = 0;
  public int mConnectInfo = 0;
  public String mDomain = "";
  public int mFlow = -1;
  public String mMiniQBversion = "";
  public int mProxyType = 0;
  public int mPv = 0;
  public String mPvType = "";
  public long mStartTime = -1L;
  public String mUrl = "";
  public boolean misRes = true;
  
  public boolean isValidate()
  {
    return (!TextUtils.isEmpty(this.mDomain)) && (!TextUtils.isEmpty(this.mPvType));
  }
  
  public HashMap<String, String> toPVHashMap()
  {
    if (!isValidate()) {
      return null;
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("PvType", this.mPvType);
    localHashMap.put("domain", this.mDomain);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mPv);
    localStringBuilder.append("");
    localHashMap.put("pvcount", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mFlow);
    localStringBuilder.append("");
    localHashMap.put("Flow", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.isProxy);
    localStringBuilder.append("");
    localHashMap.put("isProxy", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mMiniQBversion);
    localStringBuilder.append("");
    localHashMap.put("MTT_MINIQB_VERSION", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mProxyType);
    localStringBuilder.append("");
    localHashMap.put("mProxyType", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mCacheType);
    localStringBuilder.append("");
    localHashMap.put("mCacheType", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mConnectInfo);
    localStringBuilder.append("");
    localHashMap.put("connectInfo", localStringBuilder.toString());
    return localHashMap;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("mPvType=");
    localStringBuilder.append(this.mPvType);
    localStringBuilder.append(", misRes=");
    localStringBuilder.append(this.misRes);
    localStringBuilder.append(", mDomain=");
    localStringBuilder.append(this.mDomain);
    localStringBuilder.append(", mPv=");
    localStringBuilder.append(this.mPv);
    localStringBuilder.append(", mFlow=");
    localStringBuilder.append(this.mFlow);
    localStringBuilder.append(", isProxy=");
    localStringBuilder.append(this.isProxy);
    return localStringBuilder.toString();
  }
  
  public HashMap<String, String> toTrafHashMap()
  {
    if (!isValidate()) {
      return null;
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("domain", this.mDomain);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mFlow);
    localStringBuilder.append("");
    localHashMap.put("Flow", localStringBuilder.toString());
    return localHashMap;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\beacon\X5CoreBeaconStatEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */