package com.tencent.tbs.tbsshell.common.fingersearch.network;

public class Packet
{
  private Callback mCallback;
  private byte[] mData;
  
  public Packet(byte[] paramArrayOfByte)
  {
    this.mData = paramArrayOfByte;
  }
  
  public Callback getCallback()
  {
    return this.mCallback;
  }
  
  public void setCallback(Callback paramCallback)
  {
    this.mCallback = paramCallback;
  }
  
  public byte[] toByte()
  {
    return this.mData;
  }
  
  public static abstract interface Callback
  {
    public abstract void onSent(Packet paramPacket);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\fingersearch\network\Packet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */