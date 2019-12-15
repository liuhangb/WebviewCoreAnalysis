package com.tencent.tbs.common.MTT;

import com.taf.JceDisplayer;
import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import com.taf.JceUtil;

public final class GetAdBlockSourceReq
  extends JceStruct
  implements Cloneable
{
  public String sGuid = "";
  public String sLastMd5 = "";
  public String sQua = "";
  
  public GetAdBlockSourceReq() {}
  
  public GetAdBlockSourceReq(String paramString1, String paramString2, String paramString3)
  {
    this.sGuid = paramString1;
    this.sQua = paramString2;
    this.sLastMd5 = paramString3;
  }
  
  public String className()
  {
    return "MTT.GetAdBlockSourceReq";
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
    paramStringBuilder.display(this.sLastMd5, "sLastMd5");
  }
  
  public void displaySimple(StringBuilder paramStringBuilder, int paramInt)
  {
    paramStringBuilder = new JceDisplayer(paramStringBuilder, paramInt);
    paramStringBuilder.displaySimple(this.sGuid, true);
    paramStringBuilder.displaySimple(this.sQua, true);
    paramStringBuilder.displaySimple(this.sLastMd5, false);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    if (paramObject == null) {
      return false;
    }
    paramObject = (GetAdBlockSourceReq)paramObject;
    boolean bool1 = bool2;
    if (JceUtil.equals(this.sGuid, ((GetAdBlockSourceReq)paramObject).sGuid))
    {
      bool1 = bool2;
      if (JceUtil.equals(this.sQua, ((GetAdBlockSourceReq)paramObject).sQua))
      {
        bool1 = bool2;
        if (JceUtil.equals(this.sLastMd5, ((GetAdBlockSourceReq)paramObject).sLastMd5)) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public String fullClassName()
  {
    return "MTT.GetAdBlockSourceReq";
  }
  
  public String getSGuid()
  {
    return this.sGuid;
  }
  
  public String getSLastMd5()
  {
    return this.sLastMd5;
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
    this.sLastMd5 = paramJceInputStream.readString(2, true);
  }
  
  public void setSGuid(String paramString)
  {
    this.sGuid = paramString;
  }
  
  public void setSLastMd5(String paramString)
  {
    this.sLastMd5 = paramString;
  }
  
  public void setSQua(String paramString)
  {
    this.sQua = paramString;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sGuid, 0);
    paramJceOutputStream.write(this.sQua, 1);
    paramJceOutputStream.write(this.sLastMd5, 2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\GetAdBlockSourceReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */