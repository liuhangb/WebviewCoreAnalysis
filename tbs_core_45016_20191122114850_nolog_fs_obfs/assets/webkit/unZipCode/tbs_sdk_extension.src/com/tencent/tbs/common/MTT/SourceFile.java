package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class SourceFile
  extends JceStruct
  implements Cloneable
{
  static int cache_eState;
  public int eState = ET_ADFILTER_SOURCE_STATE.ET_AFS_STATE_EQ.value();
  public int iFileSize = 0;
  public long lFileTimestamp = 0L;
  public String sDownloadUrl = "";
  public String sFile = "";
  public String sMd5 = "";
  
  public SourceFile() {}
  
  public SourceFile(int paramInt1, String paramString1, String paramString2, int paramInt2, long paramLong, String paramString3)
  {
    this.eState = paramInt1;
    this.sMd5 = paramString1;
    this.sFile = paramString2;
    this.iFileSize = paramInt2;
    this.lFileTimestamp = paramLong;
    this.sDownloadUrl = paramString3;
  }
  
  public int getEState()
  {
    return this.eState;
  }
  
  public int getIFileSize()
  {
    return this.iFileSize;
  }
  
  public long getLFileTimestamp()
  {
    return this.lFileTimestamp;
  }
  
  public String getSDownloadUrl()
  {
    return this.sDownloadUrl;
  }
  
  public String getSFile()
  {
    return this.sFile;
  }
  
  public String getSMd5()
  {
    return this.sMd5;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.eState = paramJceInputStream.read(this.eState, 0, true);
    this.sMd5 = paramJceInputStream.readString(1, true);
    this.sFile = paramJceInputStream.readString(2, true);
    this.iFileSize = paramJceInputStream.read(this.iFileSize, 3, false);
    this.lFileTimestamp = paramJceInputStream.read(this.lFileTimestamp, 4, false);
    this.sDownloadUrl = paramJceInputStream.readString(5, false);
  }
  
  public void setEState(int paramInt)
  {
    this.eState = paramInt;
  }
  
  public void setIFileSize(int paramInt)
  {
    this.iFileSize = paramInt;
  }
  
  public void setLFileTimestamp(long paramLong)
  {
    this.lFileTimestamp = paramLong;
  }
  
  public void setSDownloadUrl(String paramString)
  {
    this.sDownloadUrl = paramString;
  }
  
  public void setSFile(String paramString)
  {
    this.sFile = paramString;
  }
  
  public void setSMd5(String paramString)
  {
    this.sMd5 = paramString;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.eState, 0);
    paramJceOutputStream.write(this.sMd5, 1);
    paramJceOutputStream.write(this.sFile, 2);
    paramJceOutputStream.write(this.iFileSize, 3);
    paramJceOutputStream.write(this.lFileTimestamp, 4);
    String str = this.sDownloadUrl;
    if (str != null) {
      paramJceOutputStream.write(str, 5);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\SourceFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */