package com.tencent.tbs.common.MTT;

import com.taf.JceDisplayer;
import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import com.taf.JceUtil;
import java.util.HashMap;
import java.util.Map;

public final class GetDomainReq
  extends JceStruct
  implements Cloneable
{
  static Map<Integer, String> cache_mLastMd5 = new HashMap();
  public Map<Integer, String> mLastMd5 = null;
  public String sGuid = "";
  public String sQua = "";
  
  static
  {
    cache_mLastMd5.put(Integer.valueOf(0), "");
  }
  
  public GetDomainReq() {}
  
  public GetDomainReq(String paramString1, String paramString2, Map<Integer, String> paramMap)
  {
    this.sGuid = paramString1;
    this.sQua = paramString2;
    this.mLastMd5 = paramMap;
  }
  
  public String className()
  {
    return "MTT.GetDomainReq";
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
    paramStringBuilder.display(this.sGuid, "sGuid");
    paramStringBuilder.display(this.sQua, "sQua");
    paramStringBuilder.display(this.mLastMd5, "mLastMd5");
  }
  
  public void displaySimple(StringBuilder paramStringBuilder, int paramInt)
  {
    paramStringBuilder = new JceDisplayer(paramStringBuilder, paramInt);
    paramStringBuilder.displaySimple(this.sGuid, true);
    paramStringBuilder.displaySimple(this.sQua, true);
    paramStringBuilder.displaySimple(this.mLastMd5, false);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    if (paramObject == null) {
      return false;
    }
    paramObject = (GetDomainReq)paramObject;
    boolean bool1 = bool2;
    if (JceUtil.equals(this.sGuid, ((GetDomainReq)paramObject).sGuid))
    {
      bool1 = bool2;
      if (JceUtil.equals(this.sQua, ((GetDomainReq)paramObject).sQua))
      {
        bool1 = bool2;
        if (JceUtil.equals(this.mLastMd5, ((GetDomainReq)paramObject).mLastMd5)) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public String fullClassName()
  {
    return "MTT.GetDomainReq";
  }
  
  public Map<Integer, String> getMLastMd5()
  {
    return this.mLastMd5;
  }
  
  public String getSGuid()
  {
    return this.sGuid;
  }
  
  public String getSQua()
  {
    return this.sQua;
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
    this.sGuid = paramJceInputStream.readString(0, true);
    this.sQua = paramJceInputStream.readString(1, true);
    this.mLastMd5 = ((Map)paramJceInputStream.read(cache_mLastMd5, 2, true));
  }
  
  public void setMLastMd5(Map<Integer, String> paramMap)
  {
    this.mLastMd5 = paramMap;
  }
  
  public void setSGuid(String paramString)
  {
    this.sGuid = paramString;
  }
  
  public void setSQua(String paramString)
  {
    this.sQua = paramString;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sGuid, 0);
    paramJceOutputStream.write(this.sQua, 1);
    paramJceOutputStream.write(this.mLastMd5, 2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\GetDomainReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */