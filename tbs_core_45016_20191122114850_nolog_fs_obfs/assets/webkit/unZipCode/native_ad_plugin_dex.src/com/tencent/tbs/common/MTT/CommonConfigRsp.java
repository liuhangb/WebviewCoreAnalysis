package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;
import java.util.Collection;

public final class CommonConfigRsp
  extends JceStruct
{
  static ArrayList<String> cache_vConfig = new ArrayList();
  public int iAppId = 0;
  public int iDBTime = 0;
  public int iRet = 0;
  public String sContentMd5 = "";
  public ArrayList<String> vConfig = null;
  
  static
  {
    cache_vConfig.add("");
  }
  
  public CommonConfigRsp() {}
  
  public CommonConfigRsp(int paramInt1, String paramString, ArrayList<String> paramArrayList, int paramInt2, int paramInt3)
  {
    this.iRet = paramInt1;
    this.sContentMd5 = paramString;
    this.vConfig = paramArrayList;
    this.iDBTime = paramInt2;
    this.iAppId = paramInt3;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iRet = paramJceInputStream.read(this.iRet, 0, true);
    this.sContentMd5 = paramJceInputStream.readString(1, false);
    this.vConfig = ((ArrayList)paramJceInputStream.read(cache_vConfig, 2, false));
    this.iDBTime = paramJceInputStream.read(this.iDBTime, 3, false);
    this.iAppId = paramJceInputStream.read(this.iAppId, 4, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iRet, 0);
    Object localObject = this.sContentMd5;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    localObject = this.vConfig;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 2);
    }
    paramJceOutputStream.write(this.iDBTime, 3);
    paramJceOutputStream.write(this.iAppId, 4);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\CommonConfigRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */