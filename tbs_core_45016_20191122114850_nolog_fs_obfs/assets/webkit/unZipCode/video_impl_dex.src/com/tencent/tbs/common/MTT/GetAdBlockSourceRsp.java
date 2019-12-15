package com.tencent.tbs.common.MTT;

import com.taf.JceDisplayer;
import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import com.taf.JceUtil;

public final class GetAdBlockSourceRsp
  extends JceStruct
  implements Cloneable
{
  static int cache_eUseType = 0;
  static SourceFile cache_stFile = new SourceFile();
  public int eUseType = ET_GET_FILE_TYPE.ET_USE_DIRECT.value();
  public int iRet = 0;
  public SourceFile stFile = null;
  
  public GetAdBlockSourceRsp() {}
  
  public GetAdBlockSourceRsp(int paramInt1, SourceFile paramSourceFile, int paramInt2)
  {
    this.iRet = paramInt1;
    this.stFile = paramSourceFile;
    this.eUseType = paramInt2;
  }
  
  public String className()
  {
    return "MTT.GetAdBlockSourceRsp";
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
    paramStringBuilder.display(this.stFile, "stFile");
    paramStringBuilder.display(this.eUseType, "eUseType");
  }
  
  public void displaySimple(StringBuilder paramStringBuilder, int paramInt)
  {
    paramStringBuilder = new JceDisplayer(paramStringBuilder, paramInt);
    paramStringBuilder.displaySimple(this.iRet, true);
    paramStringBuilder.displaySimple(this.stFile, true);
    paramStringBuilder.displaySimple(this.eUseType, false);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    if (paramObject == null) {
      return false;
    }
    paramObject = (GetAdBlockSourceRsp)paramObject;
    boolean bool1 = bool2;
    if (JceUtil.equals(this.iRet, ((GetAdBlockSourceRsp)paramObject).iRet))
    {
      bool1 = bool2;
      if (JceUtil.equals(this.stFile, ((GetAdBlockSourceRsp)paramObject).stFile))
      {
        bool1 = bool2;
        if (JceUtil.equals(this.eUseType, ((GetAdBlockSourceRsp)paramObject).eUseType)) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public String fullClassName()
  {
    return "MTT.GetAdBlockSourceRsp";
  }
  
  public int getEUseType()
  {
    return this.eUseType;
  }
  
  public int getIRet()
  {
    return this.iRet;
  }
  
  public SourceFile getStFile()
  {
    return this.stFile;
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
    this.stFile = ((SourceFile)paramJceInputStream.read(cache_stFile, 1, true));
    this.eUseType = paramJceInputStream.read(this.eUseType, 2, false);
  }
  
  public void setEUseType(int paramInt)
  {
    this.eUseType = paramInt;
  }
  
  public void setIRet(int paramInt)
  {
    this.iRet = paramInt;
  }
  
  public void setStFile(SourceFile paramSourceFile)
  {
    this.stFile = paramSourceFile;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iRet, 0);
    paramJceOutputStream.write(this.stFile, 1);
    paramJceOutputStream.write(this.eUseType, 2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\GetAdBlockSourceRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */