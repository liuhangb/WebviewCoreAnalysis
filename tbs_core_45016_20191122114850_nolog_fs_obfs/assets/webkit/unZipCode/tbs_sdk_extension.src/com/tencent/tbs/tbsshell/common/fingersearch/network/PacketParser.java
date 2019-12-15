package com.tencent.tbs.tbsshell.common.fingersearch.network;

import com.tencent.common.utils.ByteUtils;
import com.tencent.common.utils.LogUtils;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PacketParser
{
  private static final String TAG = "PacketParser";
  private DataInputStream mDis = null;
  private int mHeadLen = 2;
  private InputStream mIn = null;
  private boolean mNeedHeadLen = false;
  private PacketObserver mObserver = null;
  
  public PacketParser(int paramInt)
  {
    if (paramInt > 0) {
      this.mHeadLen = paramInt;
    }
  }
  
  public void parse()
    throws IOException
  {
    Object localObject1 = this.mDis;
    if (localObject1 == null)
    {
      LogUtils.d("PacketParser", "dataInputStream null");
      return;
    }
    int i = this.mHeadLen;
    if (i == 2) {
      i = ((DataInputStream)localObject1).readUnsignedShort();
    } else if (i == 4) {
      i = ((DataInputStream)localObject1).readInt();
    } else {
      i = 0;
    }
    localObject1 = this.mObserver;
    if ((localObject1 != null) && (((PacketObserver)localObject1).shouldReceivePacketWithLength(i)))
    {
      int j;
      if (this.mNeedHeadLen)
      {
        localObject1 = new byte[i];
        localObject2 = this.mDis;
        j = this.mHeadLen;
        ((DataInputStream)localObject2).readFully((byte[])localObject1, j, i - j);
        j = this.mHeadLen;
        localObject2 = new byte[j];
        if (j == 2) {
          ByteUtils.Word2Byte((byte[])localObject2, 0, (short)i);
        } else if (j == 4) {
          ByteUtils.DWord2Byte((byte[])localObject2, 0, i);
        }
        System.arraycopy(localObject2, 0, localObject1, 0, this.mHeadLen);
      }
      else
      {
        j = this.mHeadLen;
        localObject1 = new byte[i - j];
        this.mDis.readFully((byte[])localObject1, 0, i - j);
      }
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("recv ");
      ((StringBuilder)localObject2).append(localObject1.length);
      ((StringBuilder)localObject2).append(" bytes");
      LogUtils.d("PacketParser", ((StringBuilder)localObject2).toString());
      localObject2 = this.mObserver;
      if (localObject2 != null) {
        try
        {
          ((PacketObserver)localObject2).packetReceived(new Packet((byte[])localObject1));
          return;
        }
        catch (Throwable localThrowable)
        {
          LogUtils.e("PacketParser", localThrowable);
        }
      }
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Ignore packet len:");
    localStringBuilder.append(i);
    LogUtils.d("PacketParser", localStringBuilder.toString());
  }
  
  public void setCustomNeedHeadLen(boolean paramBoolean)
  {
    this.mNeedHeadLen = paramBoolean;
  }
  
  public void setInput(InputStream paramInputStream)
  {
    this.mIn = paramInputStream;
    this.mDis = new DataInputStream(this.mIn);
  }
  
  public void setMsgObserver(PacketObserver paramPacketObserver)
  {
    this.mObserver = paramPacketObserver;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\fingersearch\network\PacketParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */