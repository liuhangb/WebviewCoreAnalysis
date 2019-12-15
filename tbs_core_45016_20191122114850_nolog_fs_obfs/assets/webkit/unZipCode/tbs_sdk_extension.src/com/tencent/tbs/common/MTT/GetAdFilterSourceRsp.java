package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class GetAdFilterSourceRsp
  extends JceStruct
  implements Cloneable
{
  static int cache_eUseType = 0;
  static SourceFile cache_stJs;
  static SourceFile cache_stModel = new SourceFile();
  public int eUseType = ET_GET_FILE_TYPE.ET_USE_DIRECT.value();
  public int iRet = 0;
  public SourceFile stJs = null;
  public SourceFile stModel = null;
  
  static
  {
    cache_stJs = new SourceFile();
  }
  
  public GetAdFilterSourceRsp() {}
  
  public GetAdFilterSourceRsp(int paramInt1, SourceFile paramSourceFile1, SourceFile paramSourceFile2, int paramInt2)
  {
    this.iRet = paramInt1;
    this.stModel = paramSourceFile1;
    this.stJs = paramSourceFile2;
    this.eUseType = paramInt2;
  }
  
  public int getEUseType()
  {
    return this.eUseType;
  }
  
  public int getIRet()
  {
    return this.iRet;
  }
  
  public SourceFile getStJs()
  {
    return this.stJs;
  }
  
  public SourceFile getStModel()
  {
    return this.stModel;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iRet = paramJceInputStream.read(this.iRet, 0, true);
    this.stModel = ((SourceFile)paramJceInputStream.read(cache_stModel, 1, true));
    this.stJs = ((SourceFile)paramJceInputStream.read(cache_stJs, 2, true));
    this.eUseType = paramJceInputStream.read(this.eUseType, 3, false);
  }
  
  public void setEUseType(int paramInt)
  {
    this.eUseType = paramInt;
  }
  
  public void setIRet(int paramInt)
  {
    this.iRet = paramInt;
  }
  
  public void setStJs(SourceFile paramSourceFile)
  {
    this.stJs = paramSourceFile;
  }
  
  public void setStModel(SourceFile paramSourceFile)
  {
    this.stModel = paramSourceFile;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iRet, 0);
    paramJceOutputStream.write(this.stModel, 1);
    paramJceOutputStream.write(this.stJs, 2);
    paramJceOutputStream.write(this.eUseType, 3);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\GetAdFilterSourceRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */