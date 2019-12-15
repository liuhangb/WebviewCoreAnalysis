package com.tencent.tbs.common.MTT;

import com.taf.JceDisplayer;
import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import com.taf.JceUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class GetDomainRsp
  extends JceStruct
  implements Cloneable
{
  static Map<Integer, Integer> cache_mState = new HashMap();
  static ArrayList<DomainInfo> cache_vAddOrDiff;
  public int iRet = 0;
  public Map<Integer, Integer> mState = null;
  public ArrayList<DomainInfo> vAddOrDiff = null;
  
  static
  {
    cache_mState.put(Integer.valueOf(0), Integer.valueOf(0));
    cache_vAddOrDiff = new ArrayList();
    DomainInfo localDomainInfo = new DomainInfo();
    cache_vAddOrDiff.add(localDomainInfo);
  }
  
  public GetDomainRsp() {}
  
  public GetDomainRsp(int paramInt, Map<Integer, Integer> paramMap, ArrayList<DomainInfo> paramArrayList)
  {
    this.iRet = paramInt;
    this.mState = paramMap;
    this.vAddOrDiff = paramArrayList;
  }
  
  public String className()
  {
    return "MTT.GetDomainRsp";
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
    paramStringBuilder.display(this.mState, "mState");
    paramStringBuilder.display(this.vAddOrDiff, "vAddOrDiff");
  }
  
  public void displaySimple(StringBuilder paramStringBuilder, int paramInt)
  {
    paramStringBuilder = new JceDisplayer(paramStringBuilder, paramInt);
    paramStringBuilder.displaySimple(this.iRet, true);
    paramStringBuilder.displaySimple(this.mState, true);
    paramStringBuilder.displaySimple(this.vAddOrDiff, false);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    if (paramObject == null) {
      return false;
    }
    paramObject = (GetDomainRsp)paramObject;
    boolean bool1 = bool2;
    if (JceUtil.equals(this.iRet, ((GetDomainRsp)paramObject).iRet))
    {
      bool1 = bool2;
      if (JceUtil.equals(this.mState, ((GetDomainRsp)paramObject).mState))
      {
        bool1 = bool2;
        if (JceUtil.equals(this.vAddOrDiff, ((GetDomainRsp)paramObject).vAddOrDiff)) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public String fullClassName()
  {
    return "MTT.GetDomainRsp";
  }
  
  public int getIRet()
  {
    return this.iRet;
  }
  
  public Map<Integer, Integer> getMState()
  {
    return this.mState;
  }
  
  public ArrayList<DomainInfo> getVAddOrDiff()
  {
    return this.vAddOrDiff;
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
    this.iRet = paramJceInputStream.read(this.iRet, 0, true);
    this.mState = ((Map)paramJceInputStream.read(cache_mState, 1, false));
    this.vAddOrDiff = ((ArrayList)paramJceInputStream.read(cache_vAddOrDiff, 2, false));
  }
  
  public void setIRet(int paramInt)
  {
    this.iRet = paramInt;
  }
  
  public void setMState(Map<Integer, Integer> paramMap)
  {
    this.mState = paramMap;
  }
  
  public void setVAddOrDiff(ArrayList<DomainInfo> paramArrayList)
  {
    this.vAddOrDiff = paramArrayList;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iRet, 0);
    Object localObject = this.mState;
    if (localObject != null) {
      paramJceOutputStream.write((Map)localObject, 1);
    }
    localObject = this.vAddOrDiff;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\GetDomainRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */