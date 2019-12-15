package com.taf;

import java.util.Map;

public class UniPacketEx
  extends UniPacket
{
  public String getContext(String paramString)
  {
    if (this._package == null) {
      return null;
    }
    Map localMap = this._package.context;
    if (localMap == null) {
      return null;
    }
    return (String)localMap.get(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\taf\UniPacketEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */