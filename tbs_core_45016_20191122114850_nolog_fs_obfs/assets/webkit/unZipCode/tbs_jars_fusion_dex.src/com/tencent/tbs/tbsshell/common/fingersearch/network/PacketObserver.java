package com.tencent.tbs.tbsshell.common.fingersearch.network;

public abstract interface PacketObserver
{
  public abstract void packetReceived(Packet paramPacket);
  
  public abstract boolean shouldReceivePacketWithLength(int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\fingersearch\network\PacketObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */