package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class PlugInPushCmdS
  extends JceStruct
  implements Cloneable
{
  public int iRetCode = 0;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iRetCode = paramJceInputStream.read(this.iRetCode, 1, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iRetCode, 1);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\PlugInPushCmdS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */