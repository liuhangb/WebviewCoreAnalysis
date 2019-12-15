package com.tencent.tbs.tbsshell.common.fingersearch.network;

import com.tencent.common.utils.LogUtils;
import java.io.IOException;

class PacketReader
{
  private static final String TAG = "PacketReader";
  private StreamConnection mConnection = null;
  private volatile boolean mDone = false;
  private PacketParser mParser = null;
  private Thread mReaderThread;
  
  protected PacketReader(StreamConnection paramStreamConnection)
  {
    this.mConnection = paramStreamConnection;
    init();
  }
  
  public void addParserObserver(PacketObserver paramPacketObserver)
  {
    PacketParser localPacketParser = this.mParser;
    if (localPacketParser != null) {
      localPacketParser.setMsgObserver(paramPacketObserver);
    }
  }
  
  protected void init()
  {
    this.mParser = new PacketParser(this.mConnection.getConfiguration().getHeadLen());
    this.mParser.setInput(this.mConnection.getInputStream());
    this.mParser.setCustomNeedHeadLen(this.mConnection.isCustomNeedHeadLen());
    this.mDone = false;
    this.mReaderThread = new Thread()
    {
      public void run()
      {
        PacketReader.this.parsePackets(this);
      }
    };
    this.mReaderThread.setName("PacketReader");
    this.mReaderThread.setDaemon(true);
    this.mReaderThread.start();
    LogUtils.d("PacketReader", "packetReaderThread start succ");
  }
  
  void parsePackets(Thread paramThread)
  {
    try
    {
      LogUtils.d("PacketReader", "PacketReader");
      boolean bool;
      do
      {
        if ((this.mParser != null) && (this.mConnection.isConnected())) {
          this.mParser.parse();
        }
        bool = this.mDone;
      } while (!bool);
      return;
    }
    catch (Exception paramThread)
    {
      LogUtils.e("PacketReader", paramThread);
      return;
    }
    catch (IOException paramThread)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("PacketReader IOException done:");
      localStringBuilder.append(this.mDone);
      LogUtils.d("PacketReader", localStringBuilder.toString());
      if (!this.mDone) {
        this.mConnection.notifyConnectionError(paramThread);
      }
    }
  }
  
  public void setCustomNeedHeadLen(boolean paramBoolean)
  {
    PacketParser localPacketParser = this.mParser;
    if (localPacketParser != null) {
      localPacketParser.setCustomNeedHeadLen(paramBoolean);
    }
  }
  
  public void shutdown()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("shutdown done:");
    localStringBuilder.append(this.mDone);
    LogUtils.d("PacketReader", localStringBuilder.toString());
    this.mDone = true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\fingersearch\network\PacketReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */