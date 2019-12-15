package com.tencent.tbs.common.MTT;

import com.taf.JceDisplayer;
import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import com.taf.JceUtil;
import java.util.ArrayList;

public final class CRExtraAdRule
  extends JceStruct
  implements Cloneable
{
  static ArrayList<String> cache_vFilterRule;
  static ArrayList<String> cache_vHideRule = new ArrayList();
  public ArrayList<String> vFilterRule = null;
  public ArrayList<String> vHideRule = null;
  
  static
  {
    cache_vHideRule.add("");
    cache_vFilterRule = new ArrayList();
    cache_vFilterRule.add("");
  }
  
  public CRExtraAdRule() {}
  
  public CRExtraAdRule(ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2)
  {
    this.vHideRule = paramArrayList1;
    this.vFilterRule = paramArrayList2;
  }
  
  public String className()
  {
    return "MTT.CRExtraAdRule";
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
    paramStringBuilder.display(this.vHideRule, "vHideRule");
    paramStringBuilder.display(this.vFilterRule, "vFilterRule");
  }
  
  public void displaySimple(StringBuilder paramStringBuilder, int paramInt)
  {
    paramStringBuilder = new JceDisplayer(paramStringBuilder, paramInt);
    paramStringBuilder.displaySimple(this.vHideRule, true);
    paramStringBuilder.displaySimple(this.vFilterRule, false);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    if (paramObject == null) {
      return false;
    }
    paramObject = (CRExtraAdRule)paramObject;
    boolean bool1 = bool2;
    if (JceUtil.equals(this.vHideRule, ((CRExtraAdRule)paramObject).vHideRule))
    {
      bool1 = bool2;
      if (JceUtil.equals(this.vFilterRule, ((CRExtraAdRule)paramObject).vFilterRule)) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public String fullClassName()
  {
    return "MTT.CRExtraAdRule";
  }
  
  public ArrayList<String> getVFilterRule()
  {
    return this.vFilterRule;
  }
  
  public ArrayList<String> getVHideRule()
  {
    return this.vHideRule;
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
    this.vHideRule = ((ArrayList)paramJceInputStream.read(cache_vHideRule, 0, false));
    this.vFilterRule = ((ArrayList)paramJceInputStream.read(cache_vFilterRule, 1, false));
  }
  
  public void setVFilterRule(ArrayList<String> paramArrayList)
  {
    this.vFilterRule = paramArrayList;
  }
  
  public void setVHideRule(ArrayList<String> paramArrayList)
  {
    this.vHideRule = paramArrayList;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    ArrayList localArrayList = this.vHideRule;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 0);
    }
    localArrayList = this.vFilterRule;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 1);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\CRExtraAdRule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */