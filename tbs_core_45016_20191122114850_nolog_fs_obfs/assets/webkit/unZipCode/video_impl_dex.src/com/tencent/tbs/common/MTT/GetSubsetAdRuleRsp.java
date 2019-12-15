package com.tencent.tbs.common.MTT;

import com.taf.JceDisplayer;
import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import com.taf.JceUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class GetSubsetAdRuleRsp
  extends JceStruct
  implements Cloneable
{
  static Map<String, ArrayList<String>> cache_mapClientFeatureRule;
  static Map<String, ArrayList<String>> cache_mapClientFilterRule;
  static Map<String, ArrayList<String>> cache_mapClientHideRule = new HashMap();
  static CRExtraAdRule cache_stCRExtraAdRule = new CRExtraAdRule();
  public int iRet = 0;
  public Map<String, ArrayList<String>> mapClientFeatureRule = null;
  public Map<String, ArrayList<String>> mapClientFilterRule = null;
  public Map<String, ArrayList<String>> mapClientHideRule = null;
  public String sMd5 = "";
  public CRExtraAdRule stCRExtraAdRule = null;
  
  static
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add("");
    cache_mapClientHideRule.put("", localArrayList);
    cache_mapClientFilterRule = new HashMap();
    localArrayList = new ArrayList();
    localArrayList.add("");
    cache_mapClientFilterRule.put("", localArrayList);
    cache_mapClientFeatureRule = new HashMap();
    localArrayList = new ArrayList();
    localArrayList.add("");
    cache_mapClientFeatureRule.put("", localArrayList);
  }
  
  public GetSubsetAdRuleRsp() {}
  
  public GetSubsetAdRuleRsp(int paramInt, String paramString, Map<String, ArrayList<String>> paramMap1, Map<String, ArrayList<String>> paramMap2, Map<String, ArrayList<String>> paramMap3, CRExtraAdRule paramCRExtraAdRule)
  {
    this.iRet = paramInt;
    this.sMd5 = paramString;
    this.mapClientHideRule = paramMap1;
    this.mapClientFilterRule = paramMap2;
    this.mapClientFeatureRule = paramMap3;
    this.stCRExtraAdRule = paramCRExtraAdRule;
  }
  
  public String className()
  {
    return "MTT.GetSubsetAdRuleRsp";
  }
  
  public Object clone()
  {
    try
    {
      Object localObject = super.clone();
      return localObject;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      for (;;) {}
    }
    return null;
  }
  
  public void display(StringBuilder paramStringBuilder, int paramInt)
  {
    paramStringBuilder = new JceDisplayer(paramStringBuilder, paramInt);
    paramStringBuilder.display(this.iRet, "iRet");
    paramStringBuilder.display(this.sMd5, "sMd5");
    paramStringBuilder.display(this.mapClientHideRule, "mapClientHideRule");
    paramStringBuilder.display(this.mapClientFilterRule, "mapClientFilterRule");
    paramStringBuilder.display(this.mapClientFeatureRule, "mapClientFeatureRule");
    paramStringBuilder.display(this.stCRExtraAdRule, "stCRExtraAdRule");
  }
  
  public void displaySimple(StringBuilder paramStringBuilder, int paramInt)
  {
    paramStringBuilder = new JceDisplayer(paramStringBuilder, paramInt);
    paramStringBuilder.displaySimple(this.iRet, true);
    paramStringBuilder.displaySimple(this.sMd5, true);
    paramStringBuilder.displaySimple(this.mapClientHideRule, true);
    paramStringBuilder.displaySimple(this.mapClientFilterRule, true);
    paramStringBuilder.displaySimple(this.mapClientFeatureRule, true);
    paramStringBuilder.displaySimple(this.stCRExtraAdRule, false);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    if (paramObject == null) {
      return false;
    }
    paramObject = (GetSubsetAdRuleRsp)paramObject;
    boolean bool1 = bool2;
    if (JceUtil.equals(this.iRet, ((GetSubsetAdRuleRsp)paramObject).iRet))
    {
      bool1 = bool2;
      if (JceUtil.equals(this.sMd5, ((GetSubsetAdRuleRsp)paramObject).sMd5))
      {
        bool1 = bool2;
        if (JceUtil.equals(this.mapClientHideRule, ((GetSubsetAdRuleRsp)paramObject).mapClientHideRule))
        {
          bool1 = bool2;
          if (JceUtil.equals(this.mapClientFilterRule, ((GetSubsetAdRuleRsp)paramObject).mapClientFilterRule))
          {
            bool1 = bool2;
            if (JceUtil.equals(this.mapClientFeatureRule, ((GetSubsetAdRuleRsp)paramObject).mapClientFeatureRule))
            {
              bool1 = bool2;
              if (JceUtil.equals(this.stCRExtraAdRule, ((GetSubsetAdRuleRsp)paramObject).stCRExtraAdRule)) {
                bool1 = true;
              }
            }
          }
        }
      }
    }
    return bool1;
  }
  
  public String fullClassName()
  {
    return "MTT.GetSubsetAdRuleRsp";
  }
  
  public int getIRet()
  {
    return this.iRet;
  }
  
  public Map<String, ArrayList<String>> getMapClientFeatureRule()
  {
    return this.mapClientFeatureRule;
  }
  
  public Map<String, ArrayList<String>> getMapClientFilterRule()
  {
    return this.mapClientFilterRule;
  }
  
  public Map<String, ArrayList<String>> getMapClientHideRule()
  {
    return this.mapClientHideRule;
  }
  
  public String getSMd5()
  {
    return this.sMd5;
  }
  
  public CRExtraAdRule getStCRExtraAdRule()
  {
    return this.stCRExtraAdRule;
  }
  
  public int hashCode()
  {
    try
    {
      throw new Exception("Need define key first!");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iRet = paramJceInputStream.read(this.iRet, 0, false);
    this.sMd5 = paramJceInputStream.readString(1, false);
    this.mapClientHideRule = ((Map)paramJceInputStream.read(cache_mapClientHideRule, 2, true));
    this.mapClientFilterRule = ((Map)paramJceInputStream.read(cache_mapClientFilterRule, 3, true));
    this.mapClientFeatureRule = ((Map)paramJceInputStream.read(cache_mapClientFeatureRule, 4, false));
    this.stCRExtraAdRule = ((CRExtraAdRule)paramJceInputStream.read(cache_stCRExtraAdRule, 5, false));
  }
  
  public void setIRet(int paramInt)
  {
    this.iRet = paramInt;
  }
  
  public void setMapClientFeatureRule(Map<String, ArrayList<String>> paramMap)
  {
    this.mapClientFeatureRule = paramMap;
  }
  
  public void setMapClientFilterRule(Map<String, ArrayList<String>> paramMap)
  {
    this.mapClientFilterRule = paramMap;
  }
  
  public void setMapClientHideRule(Map<String, ArrayList<String>> paramMap)
  {
    this.mapClientHideRule = paramMap;
  }
  
  public void setSMd5(String paramString)
  {
    this.sMd5 = paramString;
  }
  
  public void setStCRExtraAdRule(CRExtraAdRule paramCRExtraAdRule)
  {
    this.stCRExtraAdRule = paramCRExtraAdRule;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iRet, 0);
    Object localObject = this.sMd5;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    paramJceOutputStream.write(this.mapClientHideRule, 2);
    paramJceOutputStream.write(this.mapClientFilterRule, 3);
    localObject = this.mapClientFeatureRule;
    if (localObject != null) {
      paramJceOutputStream.write((Map)localObject, 4);
    }
    localObject = this.stCRExtraAdRule;
    if (localObject != null) {
      paramJceOutputStream.write((JceStruct)localObject, 5);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\GetSubsetAdRuleRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */