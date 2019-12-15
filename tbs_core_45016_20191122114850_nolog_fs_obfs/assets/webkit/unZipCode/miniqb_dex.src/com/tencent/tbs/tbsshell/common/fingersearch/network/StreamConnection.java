package com.tencent.tbs.tbsshell.common.fingersearch.network;

import com.tencent.common.utils.LogUtils;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class StreamConnection
{
  private static final String TAG = "StreamConnection";
  public String ipList = "";
  public boolean isLastServer = false;
  private ConnectionConfiguration mConfiguration = null;
  private boolean mConnected = false;
  private StreamConnectionListener mConnectionListener = null;
  private boolean mCustomNeedHeadLen = false;
  private String mHost = null;
  private InputStream mInputStream = null;
  private CopyOnWriteArrayList<ConnectionListener> mListeners = new CopyOnWriteArrayList();
  private PacketObserver mObserver = null;
  private OutputStream mOutputStream = null;
  private PacketReader mPacketReader = null;
  private PacketWriter mPacketWriter = null;
  private int mPort = 0;
  private Socket mSocket = null;
  
  public StreamConnection() {}
  
  public StreamConnection(ConnectionConfiguration paramConnectionConfiguration)
  {
    this.mConfiguration = paramConnectionConfiguration;
  }
  
  /* Error */
  private void connectUsingConfiguration(ConnectionConfiguration paramConnectionConfiguration)
    throws Exception
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 87	com/tencent/tbs/tbsshell/common/fingersearch/network/ConnectionConfiguration:getHost	()Ljava/lang/String;
    //   5: putfield 41	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mHost	Ljava/lang/String;
    //   8: aload_0
    //   9: aload_1
    //   10: invokevirtual 91	com/tencent/tbs/tbsshell/common/fingersearch/network/ConnectionConfiguration:getPort	()I
    //   13: putfield 43	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mPort	I
    //   16: new 93	java/lang/StringBuilder
    //   19: dup
    //   20: invokespecial 94	java/lang/StringBuilder:<init>	()V
    //   23: astore_2
    //   24: aload_2
    //   25: ldc 96
    //   27: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: pop
    //   31: aload_2
    //   32: aload_0
    //   33: getfield 41	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mHost	Ljava/lang/String;
    //   36: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   39: pop
    //   40: aload_2
    //   41: ldc 102
    //   43: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: pop
    //   47: aload_2
    //   48: aload_0
    //   49: getfield 43	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mPort	I
    //   52: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   55: pop
    //   56: ldc 8
    //   58: aload_2
    //   59: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   62: invokestatic 114	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   65: aload_0
    //   66: new 116	java/net/Socket
    //   69: dup
    //   70: invokespecial 117	java/net/Socket:<init>	()V
    //   73: putfield 45	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mSocket	Ljava/net/Socket;
    //   76: new 119	java/net/InetSocketAddress
    //   79: dup
    //   80: aload_0
    //   81: getfield 41	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mHost	Ljava/lang/String;
    //   84: aload_0
    //   85: getfield 43	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mPort	I
    //   88: invokespecial 122	java/net/InetSocketAddress:<init>	(Ljava/lang/String;I)V
    //   91: astore_2
    //   92: aload_0
    //   93: getfield 45	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mSocket	Ljava/net/Socket;
    //   96: ifnull +176 -> 272
    //   99: aload_0
    //   100: getfield 45	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mSocket	Ljava/net/Socket;
    //   103: aload_2
    //   104: aload_1
    //   105: invokevirtual 125	com/tencent/tbs/tbsshell/common/fingersearch/network/ConnectionConfiguration:getConnectTimeout	()I
    //   108: invokevirtual 129	java/net/Socket:connect	(Ljava/net/SocketAddress;I)V
    //   111: new 93	java/lang/StringBuilder
    //   114: dup
    //   115: invokespecial 94	java/lang/StringBuilder:<init>	()V
    //   118: astore_2
    //   119: aload_2
    //   120: ldc -125
    //   122: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   125: pop
    //   126: aload_2
    //   127: aload_0
    //   128: getfield 45	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mSocket	Ljava/net/Socket;
    //   131: invokevirtual 134	java/net/Socket:getSoTimeout	()I
    //   134: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   137: pop
    //   138: ldc 8
    //   140: aload_2
    //   141: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   144: invokestatic 114	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   147: aload_0
    //   148: getfield 45	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mSocket	Ljava/net/Socket;
    //   151: aload_1
    //   152: invokevirtual 137	com/tencent/tbs/tbsshell/common/fingersearch/network/ConnectionConfiguration:getReadTimeout	()I
    //   155: invokevirtual 141	java/net/Socket:setSoTimeout	(I)V
    //   158: new 93	java/lang/StringBuilder
    //   161: dup
    //   162: invokespecial 94	java/lang/StringBuilder:<init>	()V
    //   165: astore_1
    //   166: aload_1
    //   167: ldc -125
    //   169: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   172: pop
    //   173: aload_1
    //   174: aload_0
    //   175: getfield 45	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mSocket	Ljava/net/Socket;
    //   178: invokevirtual 134	java/net/Socket:getSoTimeout	()I
    //   181: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   184: pop
    //   185: ldc 8
    //   187: aload_1
    //   188: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   191: invokestatic 114	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   194: aload_0
    //   195: aload_0
    //   196: getfield 45	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mSocket	Ljava/net/Socket;
    //   199: invokevirtual 145	java/net/Socket:getInputStream	()Ljava/io/InputStream;
    //   202: putfield 55	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mInputStream	Ljava/io/InputStream;
    //   205: aload_0
    //   206: aload_0
    //   207: getfield 45	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mSocket	Ljava/net/Socket;
    //   210: invokevirtual 149	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
    //   213: putfield 53	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mOutputStream	Ljava/io/OutputStream;
    //   216: new 93	java/lang/StringBuilder
    //   219: dup
    //   220: invokespecial 94	java/lang/StringBuilder:<init>	()V
    //   223: astore_1
    //   224: aload_1
    //   225: ldc -105
    //   227: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   230: pop
    //   231: aload_1
    //   232: aload_0
    //   233: getfield 41	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mHost	Ljava/lang/String;
    //   236: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   239: pop
    //   240: aload_1
    //   241: ldc 102
    //   243: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   246: pop
    //   247: aload_1
    //   248: aload_0
    //   249: getfield 43	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mPort	I
    //   252: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   255: pop
    //   256: aload_1
    //   257: ldc -103
    //   259: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   262: pop
    //   263: ldc 8
    //   265: aload_1
    //   266: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   269: invokestatic 114	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   272: aload_0
    //   273: invokespecial 156	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:init	()V
    //   276: aload_0
    //   277: getfield 59	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mConnectionListener	Lcom/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnectionListener;
    //   280: ifnull +12 -> 292
    //   283: aload_0
    //   284: getfield 59	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mConnectionListener	Lcom/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnectionListener;
    //   287: invokeinterface 161 1 0
    //   292: return
    //   293: astore_1
    //   294: aload_0
    //   295: getfield 59	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mConnectionListener	Lcom/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnectionListener;
    //   298: astore_2
    //   299: aload_2
    //   300: ifnull +10 -> 310
    //   303: aload_2
    //   304: aload_1
    //   305: invokeinterface 165 2 0
    //   310: aload_1
    //   311: athrow
    //   312: astore_1
    //   313: goto +123 -> 436
    //   316: astore_1
    //   317: ldc 8
    //   319: ldc -89
    //   321: invokestatic 114	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   324: aload_1
    //   325: invokevirtual 170	java/lang/Exception:printStackTrace	()V
    //   328: aload_0
    //   329: getfield 59	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mConnectionListener	Lcom/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnectionListener;
    //   332: ifnull +13 -> 345
    //   335: aload_0
    //   336: getfield 59	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mConnectionListener	Lcom/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnectionListener;
    //   339: aload_1
    //   340: invokeinterface 165 2 0
    //   345: aload_1
    //   346: athrow
    //   347: astore_1
    //   348: new 93	java/lang/StringBuilder
    //   351: dup
    //   352: invokespecial 94	java/lang/StringBuilder:<init>	()V
    //   355: astore_2
    //   356: aload_2
    //   357: ldc -84
    //   359: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   362: pop
    //   363: aload_2
    //   364: aload_0
    //   365: getfield 41	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mHost	Ljava/lang/String;
    //   368: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   371: pop
    //   372: aload_2
    //   373: ldc 102
    //   375: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   378: pop
    //   379: aload_2
    //   380: aload_0
    //   381: getfield 43	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mPort	I
    //   384: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   387: pop
    //   388: aload_2
    //   389: ldc -103
    //   391: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   394: pop
    //   395: aload_2
    //   396: aload_1
    //   397: invokevirtual 173	java/io/IOException:toString	()Ljava/lang/String;
    //   400: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   403: pop
    //   404: ldc 8
    //   406: aload_2
    //   407: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   410: invokestatic 114	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   413: aload_1
    //   414: invokevirtual 174	java/io/IOException:printStackTrace	()V
    //   417: aload_0
    //   418: getfield 59	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mConnectionListener	Lcom/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnectionListener;
    //   421: ifnull +13 -> 434
    //   424: aload_0
    //   425: getfield 59	com/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnection:mConnectionListener	Lcom/tencent/tbs/tbsshell/common/fingersearch/network/StreamConnectionListener;
    //   428: aload_1
    //   429: invokeinterface 165 2 0
    //   434: aload_1
    //   435: athrow
    //   436: aload_1
    //   437: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	438	0	this	StreamConnection
    //   0	438	1	paramConnectionConfiguration	ConnectionConfiguration
    //   23	384	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   272	292	293	java/lang/Exception
    //   16	272	312	finally
    //   317	345	312	finally
    //   345	347	312	finally
    //   348	434	312	finally
    //   434	436	312	finally
    //   16	272	316	java/lang/Exception
    //   16	272	347	java/io/IOException
  }
  
  private void init()
    throws Exception
  {
    int i;
    if ((this.mPacketReader != null) && (this.mPacketWriter != null)) {
      i = 0;
    } else {
      i = 1;
    }
    if (i != 0) {}
    label69:
    Object localObject;
    try
    {
      this.mPacketWriter = new PacketWriter(this);
      this.mPacketReader = new PacketReader(this);
      break label69;
      this.mPacketWriter.init();
      this.mPacketReader.init();
      if (this.mObserver != null) {
        addObserver(this.mObserver);
      }
      this.mConnected = true;
      LogUtils.d("StreamConnection", "initConnection succ ");
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      localObject = this.mPacketWriter;
      if (localObject == null) {}
    }
    try
    {
      ((PacketWriter)localObject).shutdown();
      this.mPacketWriter = null;
      localObject = this.mPacketReader;
      if (localObject == null) {}
    }
    catch (Throwable localThrowable4)
    {
      try
      {
        ((PacketReader)localObject).shutdown();
        this.mPacketReader = null;
        localObject = this.mInputStream;
        if (localObject == null) {}
      }
      catch (Throwable localThrowable4)
      {
        try
        {
          ((InputStream)localObject).close();
          this.mInputStream = null;
          localObject = this.mOutputStream;
          if (localObject == null) {}
        }
        catch (Throwable localThrowable4)
        {
          try
          {
            ((OutputStream)localObject).close();
            this.mOutputStream = null;
            localObject = this.mSocket;
            if (localObject == null) {}
          }
          catch (Throwable localThrowable4)
          {
            try
            {
              for (;;)
              {
                ((Socket)localObject).close();
                this.mSocket = null;
                this.mConnected = false;
                LogUtils.d("StreamConnection", "initConnection failed ");
                throw localException;
                localThrowable1 = localThrowable1;
                continue;
                localThrowable2 = localThrowable2;
                continue;
                localThrowable3 = localThrowable3;
              }
              localThrowable4 = localThrowable4;
            }
            catch (Throwable localThrowable5)
            {
              for (;;) {}
            }
          }
        }
      }
    }
  }
  
  public void addConnectionListener(ConnectionListener paramConnectionListener)
  {
    LogUtils.d("StreamConnection", "addConnectionListener");
    if (!isConnected()) {
      LogUtils.d("StreamConnection", "Not connected to server.");
    }
    if (paramConnectionListener == null)
    {
      LogUtils.d("StreamConnection", "connectionListener == null");
      return;
    }
    if (!this.mListeners.contains(paramConnectionListener)) {
      this.mListeners.add(paramConnectionListener);
    }
  }
  
  public void addObserver(PacketObserver paramPacketObserver)
  {
    PacketReader localPacketReader = this.mPacketReader;
    if (localPacketReader != null) {
      localPacketReader.addParserObserver(paramPacketObserver);
    }
    this.mObserver = paramPacketObserver;
  }
  
  public void connect()
    throws Exception
  {
    LogUtils.d("StreamConnection", "connect...");
    try
    {
      connectUsingConfiguration(this.mConfiguration);
      return;
    }
    catch (Throwable localThrowable)
    {
      throw new Exception(localThrowable);
    }
    catch (Exception localException)
    {
      throw localException;
    }
  }
  
  public void disconnect()
  {
    try
    {
      LogUtils.d("StreamConnection", "disconnect");
      if ((this.mPacketReader != null) && (this.mPacketWriter != null))
      {
        shutdown();
        return;
      }
      return;
    }
    finally {}
  }
  
  public ConnectionConfiguration getConfiguration()
  {
    return this.mConfiguration;
  }
  
  public String getHost()
  {
    return this.mHost;
  }
  
  InputStream getInputStream()
  {
    return this.mInputStream;
  }
  
  OutputStream getOutputStream()
  {
    return this.mOutputStream;
  }
  
  public PacketReader getPacketReader()
  {
    return this.mPacketReader;
  }
  
  public int getPort()
  {
    return this.mPort;
  }
  
  public boolean isConnected()
  {
    return this.mConnected;
  }
  
  public boolean isCustomNeedHeadLen()
  {
    return this.mCustomNeedHeadLen;
  }
  
  void notifyConnectionError(Exception paramException)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("notifyConnectionError ex:");
    ((StringBuilder)localObject).append(paramException.toString());
    LogUtils.d("StreamConnection", ((StringBuilder)localObject).toString());
    shutdown();
    localObject = this.mListeners.iterator();
    for (;;)
    {
      ConnectionListener localConnectionListener;
      if (((Iterator)localObject).hasNext()) {
        localConnectionListener = (ConnectionListener)((Iterator)localObject).next();
      }
      try
      {
        localConnectionListener.connectionClosedOnError(paramException);
      }
      catch (Throwable localThrowable) {}
      return;
    }
  }
  
  public void removeConnectionListener(ConnectionListener paramConnectionListener)
  {
    LogUtils.d("StreamConnection", "removeConnectionListener");
    this.mListeners.remove(paramConnectionListener);
  }
  
  public boolean sendPacket(Packet paramPacket)
  {
    if (!isConnected())
    {
      LogUtils.d("StreamConnection", "Not connected to server.");
      return false;
    }
    if (paramPacket == null)
    {
      LogUtils.d("StreamConnection", "Packet is null.");
      return false;
    }
    PacketWriter localPacketWriter = this.mPacketWriter;
    if (localPacketWriter != null) {
      return localPacketWriter.sendPacket(paramPacket);
    }
    return false;
  }
  
  public boolean sendPacketSync(Packet paramPacket, int paramInt)
  {
    if (!isConnected())
    {
      LogUtils.d("StreamConnection", "Not connected to server.");
      return false;
    }
    if (paramPacket == null)
    {
      LogUtils.d("StreamConnection", "Packet is null.");
      return false;
    }
    PacketWriter localPacketWriter = this.mPacketWriter;
    if (localPacketWriter != null)
    {
      localPacketWriter.writePacketSync(paramPacket, paramInt);
      return true;
    }
    return false;
  }
  
  public void setConfiguration(ConnectionConfiguration paramConnectionConfiguration)
  {
    this.mConfiguration = paramConnectionConfiguration;
  }
  
  public void setConnectionListener(StreamConnectionListener paramStreamConnectionListener)
  {
    this.mConnectionListener = paramStreamConnectionListener;
  }
  
  public void setCustomNeedHeadLen(boolean paramBoolean)
  {
    PacketReader localPacketReader = this.mPacketReader;
    if (localPacketReader != null) {
      localPacketReader.setCustomNeedHeadLen(paramBoolean);
    }
    this.mCustomNeedHeadLen = paramBoolean;
  }
  
  protected void shutdown()
  {
    LogUtils.d("StreamConnection", "shutdown");
    this.mConnected = false;
    Object localObject = this.mPacketReader;
    if (localObject != null)
    {
      ((PacketReader)localObject).shutdown();
      this.mPacketReader = null;
    }
    localObject = this.mPacketWriter;
    if (localObject != null)
    {
      ((PacketWriter)localObject).shutdown();
      this.mPacketWriter = null;
    }
    localObject = this.mInputStream;
    if (localObject != null) {}
    try
    {
      ((InputStream)localObject).close();
      this.mInputStream = null;
      localObject = this.mOutputStream;
      if (localObject == null) {}
    }
    catch (Throwable localThrowable1)
    {
      try
      {
        ((OutputStream)localObject).close();
        this.mOutputStream = null;
        localObject = this.mSocket;
        if (localObject != null) {}
        try
        {
          ((Socket)localObject).close();
          return;
        }
        catch (Throwable localThrowable3) {}
        localThrowable1 = localThrowable1;
      }
      catch (Throwable localThrowable2)
      {
        for (;;) {}
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\fingersearch\network\StreamConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */