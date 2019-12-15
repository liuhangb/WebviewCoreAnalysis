package com.tencent.tbs.tbsshell.common.fingersearch.network;

import com.tencent.common.utils.LogUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class PacketWriter
  implements Runnable
{
  private static final String TAG = "PacketWriter";
  private StreamConnection mConnection;
  private volatile boolean mDone;
  private final BlockingQueue<Packet> mQueue = new ArrayBlockingQueue(500, true);
  private OutputStream mWriter;
  private Thread mWriterThread;
  
  protected PacketWriter(StreamConnection paramStreamConnection)
  {
    this.mConnection = paramStreamConnection;
    init();
  }
  
  protected void init()
  {
    LogUtils.d("PacketWriter", "init");
    this.mWriter = this.mConnection.getOutputStream();
    this.mDone = false;
    this.mWriterThread = new Thread(this, "Packet Writer");
    this.mWriterThread.setDaemon(true);
    this.mWriterThread.start();
  }
  
  public void run()
  {
    while (!this.mDone) {
      try
      {
        Packet localPacket = (Packet)this.mQueue.take();
        if (localPacket != null) {
          writePacketSync(localPacket, -1);
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;) {}
      }
    }
    LogUtils.d("PacketWriter", "WriterThread Done!");
  }
  
  public boolean sendPacket(Packet paramPacket)
  {
    if (!this.mDone) {
      try
      {
        this.mQueue.put(paramPacket);
        return true;
      }
      catch (Exception paramPacket)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("sendPacket ex:");
        localStringBuilder.append(paramPacket.toString());
        LogUtils.d("PacketWriter", localStringBuilder.toString());
        paramPacket.printStackTrace();
        return false;
      }
    }
    return false;
  }
  
  public void shutdown()
  {
    this.mDone = true;
    this.mWriterThread.interrupt();
  }
  
  public void writePacketSync(Packet paramPacket, int paramInt)
  {
    synchronized (this.mWriter)
    {
      System.currentTimeMillis();
      try
      {
        try
        {
          this.mWriter.write(paramPacket.toByte());
          this.mWriter.flush();
          Object localObject = new StringBuilder();
          ((StringBuilder)localObject).append("write ");
          ((StringBuilder)localObject).append(paramPacket.toByte().length);
          ((StringBuilder)localObject).append(" bytes");
          LogUtils.d("PacketWriter", ((StringBuilder)localObject).toString());
          localObject = paramPacket.getCallback();
          if (localObject != null) {
            ((Packet.Callback)localObject).onSent(paramPacket);
          }
        }
        catch (IOException paramPacket)
        {
          LogUtils.d("PacketWriter", "write packet IOException");
          this.mConnection.notifyConnectionError(paramPacket);
        }
      }
      catch (Exception|Throwable paramPacket)
      {
        for (;;) {}
      }
      System.currentTimeMillis();
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\fingersearch\network\PacketWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */