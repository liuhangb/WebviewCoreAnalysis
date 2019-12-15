package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class GetAdFilterSourceReq
  extends JceStruct
  implements Cloneable
{
  public String sGuid = "";
  public String sLastJsMd5 = "";
  public String sLastModelMd5 = "";
  public String sQua = "";
  
  public GetAdFilterSourceReq() {}
  
  public GetAdFilterSourceReq(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.sGuid = paramString1;
    this.sQua = paramString2;
    this.sLastModelMd5 = paramString3;
    this.sLastJsMd5 = paramString4;
  }
  
  public String getSGuid()
  {
    return this.sGuid;
  }
  
  public String getSLastJsMd5()
  {
    return this.sLastJsMd5;
  }
  
  public String getSLastModelMd5()
  {
    return this.sLastModelMd5;
  }
  
  public String getSQua()
  {
    return this.sQua;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sGuid = paramJceInputStream.readString(0, true);
    this.sQua = paramJceInputStream.readString(1, true);
    this.sLastModelMd5 = paramJceInputStream.readString(2, true);
    this.sLastJsMd5 = paramJceInputStream.readString(3, true);
  }
  
  public void setSGuid(String paramString)
  {
    this.sGuid = paramString;
  }
  
  public void setSLastJsMd5(String paramString)
  {
    this.sLastJsMd5 = paramString;
  }
  
  public void setSLastModelMd5(String paramString)
  {
    this.sLastModelMd5 = paramString;
  }
  
  public void setSQua(String paramString)
  {
    this.sQua = paramString;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sGuid, 0);
    paramJceOutputStream.write(this.sQua, 1);
    paramJceOutputStream.write(this.sLastModelMd5, 2);
    paramJceOutputStream.write(this.sLastJsMd5, 3);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\GetAdFilterSourceReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */