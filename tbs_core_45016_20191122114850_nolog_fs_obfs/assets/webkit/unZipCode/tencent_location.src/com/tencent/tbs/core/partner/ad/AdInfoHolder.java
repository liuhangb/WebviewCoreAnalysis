package com.tencent.tbs.core.partner.ad;

import android.text.TextUtils;
import com.tencent.smtt.export.internal.interfaces.IAdSettings;
import java.util.HashMap;
import java.util.Map;
import org.chromium.base.annotations.UsedByReflection;
import org.json.JSONException;
import org.json.JSONObject;

public class AdInfoHolder
  implements IAdSettings
{
  private static volatile AdInfoHolder mInstance;
  private String mAdId = "";
  private AdInfoUnit mAdInfoUnit = null;
  private Map<String, AdInfoUnit> mAdInfoUnitMap = new HashMap();
  private String mAdShape = "";
  private String mAppId = "";
  private AdInfoUnit mCurrentAdInfoUnit = null;
  private int mShouldShow = -1;
  
  @UsedByReflection("X5JsHelper.java")
  public static AdInfoHolder getInstance()
  {
    if (mInstance == null) {
      try
      {
        if (mInstance == null) {
          mInstance = new AdInfoHolder();
        }
      }
      finally {}
    }
    return mInstance;
  }
  
  public void addAdToAdInfoMap(String paramString, AdInfoUnit paramAdInfoUnit)
  {
    if (paramAdInfoUnit != null) {
      this.mAdInfoUnitMap.put(paramString, (AdInfoUnit)paramAdInfoUnit.clone());
    }
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void clearAdInfo()
  {
    this.mAdInfoUnit = null;
    this.mShouldShow = 0;
  }
  
  @UsedByReflection("X5JsHelper.java")
  public String getAdInfo()
  {
    return getAdInfo(0);
  }
  
  @UsedByReflection("X5JsHelper.java")
  public String getAdInfo(int paramInt)
  {
    JSONObject localJSONObject = new JSONObject();
    for (;;)
    {
      try
      {
        if (this.mAdInfoUnit == null) {
          str1 = "";
        } else {
          str1 = this.mAdInfoUnit.getMainUrl();
        }
        if (!TextUtils.isEmpty(str1)) {
          localJSONObject.put("domain", str1);
        }
        if (!TextUtils.isEmpty(this.mAppId)) {
          localJSONObject.put("appId", this.mAppId);
        }
        if (!TextUtils.isEmpty(this.mAdId)) {
          localJSONObject.put("adId", this.mAdId);
        }
        if (!TextUtils.isEmpty(this.mAdShape)) {
          localJSONObject.put("adShape", this.mAdShape);
        }
        String str1 = this.mCurrentAdInfoUnit.getAdPositionString();
        String str4 = this.mCurrentAdInfoUnit.getTypeString();
        String str2 = str1;
        String str3 = str4;
        if (this.mAdInfoUnit != null)
        {
          if (!TextUtils.isEmpty(this.mAdInfoUnit.getAdPositionString())) {
            str1 = this.mAdInfoUnit.getAdPositionString();
          }
          str2 = str1;
          str3 = str4;
          if (!TextUtils.isEmpty(this.mAdInfoUnit.getTypeString()))
          {
            str3 = this.mAdInfoUnit.getTypeString();
            str2 = str1;
          }
        }
        localJSONObject.put("adPos", str2);
        localJSONObject.put("adType", str3);
        if (this.mShouldShow == 0)
        {
          bool = false;
          localJSONObject.put("ifShowAd", bool);
          str1 = localJSONObject.toString();
          return str1;
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return null;
      }
      boolean bool = true;
    }
  }
  
  public AdInfoUnit getAdInfoByMainUrl(String paramString)
  {
    return (AdInfoUnit)this.mAdInfoUnitMap.get(paramString);
  }
  
  public AdInfoUnit getmAdInfoUnit()
  {
    return this.mAdInfoUnit;
  }
  
  public boolean isAdShouldHide()
  {
    return this.mShouldShow == 0;
  }
  
  public boolean isAdShouldShow()
  {
    return this.mShouldShow != 0;
  }
  
  public void setAdInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    setAdInfoUnit(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8);
  }
  
  @UsedByReflection("X5JsHelper.java")
  public void setAdInfoUnit(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, String paramString4, int paramInt3, int paramInt4)
  {
    AdInfoUnit localAdInfoUnit = this.mAdInfoUnit;
    if (localAdInfoUnit == null) {
      this.mAdInfoUnit = new AdInfoUnit(paramInt1, paramString1, paramString2, paramString3, paramInt2, paramString4, paramInt3, paramInt4);
    } else {
      localAdInfoUnit.updateAdInfo(paramInt1, paramString1, paramString2, paramString3, paramInt2, paramString4, paramInt3, paramInt4);
    }
    this.mShouldShow = 1;
  }
  
  @UsedByReflection("X5JsHelper.java")
  public void setAdInfoUnit(AdInfoUnit paramAdInfoUnit)
  {
    this.mAdInfoUnit = paramAdInfoUnit;
    if (paramAdInfoUnit != null)
    {
      this.mShouldShow = 1;
      return;
    }
    this.mShouldShow = 0;
  }
  
  @UsedByReflection("X5JsHelper.java")
  public void setAdInfoUnit(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public void setCurrentAdInfo(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, String paramString4, int paramInt3, int paramInt4)
  {
    this.mCurrentAdInfoUnit = new AdInfoUnit(paramInt1, paramString1, paramString2, paramString3, paramInt2, paramString4, paramInt3, paramInt4);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void setShouldShow(boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\AdInfoHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */