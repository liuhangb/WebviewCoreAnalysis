package com.tencent.common.http;

import android.net.SSLCertificateSocketFactory;
import android.os.Build.VERSION;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class NoSSLv3Factory
  extends SSLCertificateSocketFactory
{
  private static final List<String> jdField_a_of_type_JavaUtilList = Arrays.asList(new String[] { "TLS_RSA_EXPORT_WITH_RC4_40_MD5", "TLS_RSA_EXPORT_WITH_DES40_CBC_SHA", "TLS_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", "TLS_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA", "TLS_RSA_WITH_NULL_MD5", "TLS_RSA_WITH_NULL_SHA", "TLS_ECDH_ECDSA_WITH_NULL_SHA", "TLS_ECDH_RSA_WITH_NULL_SHA", "TLS_ECDHE_ECDSA_WITH_NULL_SHA", "TLS_ECDHE_RSA_WITH_NULL_SHA", "TLS_DH_anon_WITH_RC4_128_MD5", "TLS_DH_anon_WITH_AES_128_CBC_SHA", "TLS_DH_anon_WITH_AES_256_CBC_SHA", "TLS_DH_anon_WITH_3DES_EDE_CBC_SHA", "TLS_DH_anon_WITH_DES_CBC_SHA", "TLS_ECDH_anon_WITH_RC4_128_SHA", "TLS_ECDH_anon_WITH_AES_128_CBC_SHA", "TLS_ECDH_anon_WITH_AES_256_CBC_SHA", "TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA", "TLS_DH_anon_EXPORT_WITH_RC4_40_MD5", "TLS_DH_anon_EXPORT_WITH_DES40_CBC_SHA", "TLS_ECDH_anon_WITH_NULL_SHA", "TLS_FALLBACK_SCSV" });
  private final SSLSocketFactory jdField_a_of_type_JavaxNetSslSSLSocketFactory;
  
  public NoSSLv3Factory(SSLSocketFactory paramSSLSocketFactory)
  {
    super(0);
    this.jdField_a_of_type_JavaxNetSslSSLSocketFactory = paramSSLSocketFactory;
  }
  
  private static Socket a(Socket paramSocket)
  {
    Object localObject = paramSocket;
    if ((paramSocket instanceof SSLSocket)) {
      localObject = new b((SSLSocket)paramSocket, null);
    }
    return (Socket)localObject;
  }
  
  static String[] a(String[] paramArrayOfString)
  {
    ArrayList localArrayList = new ArrayList();
    int j = paramArrayOfString.length;
    int i = 0;
    while (i < j)
    {
      String str = paramArrayOfString[i];
      if (!jdField_a_of_type_JavaUtilList.contains(str)) {
        localArrayList.add(str);
      }
      i += 1;
    }
    return (String[])localArrayList.toArray(new String[0]);
  }
  
  public Socket createSocket(String paramString, int paramInt)
    throws IOException
  {
    String.format("createSocket host:%s port:%s", new Object[] { paramString, Integer.valueOf(paramInt) });
    Socket localSocket = a(this.jdField_a_of_type_JavaxNetSslSSLSocketFactory.createSocket(paramString, paramInt));
    setHostname(localSocket, paramString);
    return localSocket;
  }
  
  public Socket createSocket(String paramString, int paramInt1, InetAddress paramInetAddress, int paramInt2)
    throws IOException
  {
    String.format("createSocket host:%s port:%s localHost:%s localPort:%s", new Object[] { paramString, Integer.valueOf(paramInt1), paramInetAddress, Integer.valueOf(paramInt2) });
    paramInetAddress = a(this.jdField_a_of_type_JavaxNetSslSSLSocketFactory.createSocket(paramString, paramInt1, paramInetAddress, paramInt2));
    setHostname(paramInetAddress, paramString);
    return paramInetAddress;
  }
  
  public Socket createSocket(InetAddress paramInetAddress, int paramInt)
    throws IOException
  {
    String.format("createSocket host:%s port:%s", new Object[] { paramInetAddress, Integer.valueOf(paramInt) });
    Socket localSocket = a(this.jdField_a_of_type_JavaxNetSslSSLSocketFactory.createSocket(paramInetAddress, paramInt));
    if (paramInetAddress != null) {
      setHostname(localSocket, paramInetAddress.getHostName());
    }
    return localSocket;
  }
  
  public Socket createSocket(InetAddress paramInetAddress1, int paramInt1, InetAddress paramInetAddress2, int paramInt2)
    throws IOException
  {
    String.format("createSocket address:%s port:%s localAddress:%s localPort:%s", new Object[] { paramInetAddress1, Integer.valueOf(paramInt1), paramInetAddress2, Integer.valueOf(paramInt2) });
    paramInetAddress2 = a(this.jdField_a_of_type_JavaxNetSslSSLSocketFactory.createSocket(paramInetAddress1, paramInt1, paramInetAddress2, paramInt2));
    if (paramInetAddress1 != null) {
      setHostname(paramInetAddress2, paramInetAddress1.getHostName());
    }
    return paramInetAddress2;
  }
  
  public Socket createSocket(Socket paramSocket, String paramString, int paramInt, boolean paramBoolean)
    throws IOException
  {
    String.format("createSocket socket:%s host:%s port:%s autoClose:%s", new Object[] { paramSocket, paramString, Integer.valueOf(paramInt), Boolean.valueOf(paramBoolean) });
    paramSocket = a(this.jdField_a_of_type_JavaxNetSslSSLSocketFactory.createSocket(paramSocket, paramString, paramInt, paramBoolean));
    setHostname(paramSocket, paramString);
    return paramSocket;
  }
  
  public String[] getDefaultCipherSuites()
  {
    return this.jdField_a_of_type_JavaxNetSslSSLSocketFactory.getDefaultCipherSuites();
  }
  
  public String[] getSupportedCipherSuites()
  {
    return this.jdField_a_of_type_JavaxNetSslSSLSocketFactory.getSupportedCipherSuites();
  }
  
  public void setHostname(Socket paramSocket, String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("setHostname ");
    ((StringBuilder)localObject).append(String.format("socket:%s hostname:%s", new Object[] { paramSocket, paramString }));
    ((StringBuilder)localObject).toString();
    if ((paramSocket instanceof b))
    {
      ((b)paramSocket).a(paramString);
      return;
    }
    if (Build.VERSION.SDK_INT >= 17)
    {
      super.setHostname(paramSocket, paramString);
      return;
    }
    try
    {
      localObject = paramSocket.getClass().getMethod("setHostname", new Class[] { String.class });
      if (localObject != null)
      {
        ((Method)localObject).setAccessible(true);
        ((Method)localObject).invoke(paramSocket, new Object[] { paramString });
        return;
      }
    }
    catch (Exception paramSocket)
    {
      paramSocket.printStackTrace();
    }
  }
  
  private static class a
    extends SSLSocket
  {
    protected final SSLSocket a;
    
    a(SSLSocket paramSSLSocket)
    {
      this.a = paramSSLSocket;
    }
    
    public void addHandshakeCompletedListener(HandshakeCompletedListener paramHandshakeCompletedListener)
    {
      this.a.addHandshakeCompletedListener(paramHandshakeCompletedListener);
    }
    
    public void bind(SocketAddress paramSocketAddress)
      throws IOException
    {
      this.a.bind(paramSocketAddress);
    }
    
    public void close()
      throws IOException
    {
      try
      {
        this.a.close();
        return;
      }
      finally
      {
        localObject = finally;
        throw ((Throwable)localObject);
      }
    }
    
    public void connect(SocketAddress paramSocketAddress)
      throws IOException
    {
      this.a.connect(paramSocketAddress);
    }
    
    public void connect(SocketAddress paramSocketAddress, int paramInt)
      throws IOException
    {
      this.a.connect(paramSocketAddress, paramInt);
    }
    
    public boolean equals(Object paramObject)
    {
      return this.a.equals(paramObject);
    }
    
    public SocketChannel getChannel()
    {
      return this.a.getChannel();
    }
    
    public boolean getEnableSessionCreation()
    {
      return this.a.getEnableSessionCreation();
    }
    
    public String[] getEnabledCipherSuites()
    {
      return this.a.getEnabledCipherSuites();
    }
    
    public String[] getEnabledProtocols()
    {
      return this.a.getEnabledProtocols();
    }
    
    public InetAddress getInetAddress()
    {
      return this.a.getInetAddress();
    }
    
    public InputStream getInputStream()
      throws IOException
    {
      return this.a.getInputStream();
    }
    
    public boolean getKeepAlive()
      throws SocketException
    {
      return this.a.getKeepAlive();
    }
    
    public InetAddress getLocalAddress()
    {
      return this.a.getLocalAddress();
    }
    
    public int getLocalPort()
    {
      return this.a.getLocalPort();
    }
    
    public SocketAddress getLocalSocketAddress()
    {
      return this.a.getLocalSocketAddress();
    }
    
    public boolean getNeedClientAuth()
    {
      return this.a.getNeedClientAuth();
    }
    
    public boolean getOOBInline()
      throws SocketException
    {
      return this.a.getOOBInline();
    }
    
    public OutputStream getOutputStream()
      throws IOException
    {
      return this.a.getOutputStream();
    }
    
    public int getPort()
    {
      return this.a.getPort();
    }
    
    public int getReceiveBufferSize()
      throws SocketException
    {
      try
      {
        int i = this.a.getReceiveBufferSize();
        return i;
      }
      finally
      {
        localObject = finally;
        throw ((Throwable)localObject);
      }
    }
    
    public SocketAddress getRemoteSocketAddress()
    {
      return this.a.getRemoteSocketAddress();
    }
    
    public boolean getReuseAddress()
      throws SocketException
    {
      return this.a.getReuseAddress();
    }
    
    public int getSendBufferSize()
      throws SocketException
    {
      try
      {
        int i = this.a.getSendBufferSize();
        return i;
      }
      finally
      {
        localObject = finally;
        throw ((Throwable)localObject);
      }
    }
    
    public SSLSession getSession()
    {
      return this.a.getSession();
    }
    
    public int getSoLinger()
      throws SocketException
    {
      return this.a.getSoLinger();
    }
    
    public int getSoTimeout()
      throws SocketException
    {
      try
      {
        int i = this.a.getSoTimeout();
        return i;
      }
      finally
      {
        localObject = finally;
        throw ((Throwable)localObject);
      }
    }
    
    public String[] getSupportedCipherSuites()
    {
      return this.a.getSupportedCipherSuites();
    }
    
    public String[] getSupportedProtocols()
    {
      return this.a.getSupportedProtocols();
    }
    
    public boolean getTcpNoDelay()
      throws SocketException
    {
      return this.a.getTcpNoDelay();
    }
    
    public int getTrafficClass()
      throws SocketException
    {
      return this.a.getTrafficClass();
    }
    
    public boolean getUseClientMode()
    {
      return this.a.getUseClientMode();
    }
    
    public boolean getWantClientAuth()
    {
      return this.a.getWantClientAuth();
    }
    
    public boolean isBound()
    {
      return this.a.isBound();
    }
    
    public boolean isClosed()
    {
      return this.a.isClosed();
    }
    
    public boolean isConnected()
    {
      return this.a.isConnected();
    }
    
    public boolean isInputShutdown()
    {
      return this.a.isInputShutdown();
    }
    
    public boolean isOutputShutdown()
    {
      return this.a.isOutputShutdown();
    }
    
    public void removeHandshakeCompletedListener(HandshakeCompletedListener paramHandshakeCompletedListener)
    {
      this.a.removeHandshakeCompletedListener(paramHandshakeCompletedListener);
    }
    
    public void sendUrgentData(int paramInt)
      throws IOException
    {
      this.a.sendUrgentData(paramInt);
    }
    
    public void setEnableSessionCreation(boolean paramBoolean)
    {
      this.a.setEnableSessionCreation(paramBoolean);
    }
    
    public void setEnabledCipherSuites(String[] paramArrayOfString)
    {
      this.a.setEnabledCipherSuites(NoSSLv3Factory.a(paramArrayOfString));
    }
    
    public void setEnabledProtocols(String[] paramArrayOfString)
    {
      this.a.setEnabledProtocols(paramArrayOfString);
    }
    
    public void setKeepAlive(boolean paramBoolean)
      throws SocketException
    {
      this.a.setKeepAlive(paramBoolean);
    }
    
    public void setNeedClientAuth(boolean paramBoolean)
    {
      this.a.setNeedClientAuth(paramBoolean);
    }
    
    public void setOOBInline(boolean paramBoolean)
      throws SocketException
    {
      this.a.setOOBInline(paramBoolean);
    }
    
    public void setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3)
    {
      this.a.setPerformancePreferences(paramInt1, paramInt2, paramInt3);
    }
    
    public void setReceiveBufferSize(int paramInt)
      throws SocketException
    {
      try
      {
        this.a.setReceiveBufferSize(paramInt);
        return;
      }
      finally
      {
        localObject = finally;
        throw ((Throwable)localObject);
      }
    }
    
    public void setReuseAddress(boolean paramBoolean)
      throws SocketException
    {
      this.a.setReuseAddress(paramBoolean);
    }
    
    public void setSSLParameters(SSLParameters paramSSLParameters)
    {
      this.a.setSSLParameters(paramSSLParameters);
    }
    
    public void setSendBufferSize(int paramInt)
      throws SocketException
    {
      try
      {
        this.a.setSendBufferSize(paramInt);
        return;
      }
      finally
      {
        localObject = finally;
        throw ((Throwable)localObject);
      }
    }
    
    public void setSoLinger(boolean paramBoolean, int paramInt)
      throws SocketException
    {
      this.a.setSoLinger(paramBoolean, paramInt);
    }
    
    public void setSoTimeout(int paramInt)
      throws SocketException
    {
      try
      {
        this.a.setSoTimeout(paramInt);
        return;
      }
      finally
      {
        localObject = finally;
        throw ((Throwable)localObject);
      }
    }
    
    public void setTcpNoDelay(boolean paramBoolean)
      throws SocketException
    {
      this.a.setTcpNoDelay(paramBoolean);
    }
    
    public void setTrafficClass(int paramInt)
      throws SocketException
    {
      this.a.setTrafficClass(paramInt);
    }
    
    public void setUseClientMode(boolean paramBoolean)
    {
      this.a.setUseClientMode(paramBoolean);
    }
    
    public void setWantClientAuth(boolean paramBoolean)
    {
      this.a.setWantClientAuth(paramBoolean);
    }
    
    public void shutdownInput()
      throws IOException
    {
      this.a.shutdownInput();
    }
    
    public void shutdownOutput()
      throws IOException
    {
      this.a.shutdownOutput();
    }
    
    public void startHandshake()
      throws IOException
    {
      this.a.startHandshake();
    }
    
    public String toString()
    {
      return this.a.toString();
    }
  }
  
  private static class b
    extends NoSSLv3Factory.a
  {
    private b(SSLSocket paramSSLSocket)
    {
      super();
      try
      {
        Method localMethod = paramSSLSocket.getClass().getMethod("setUseSessionTickets", new Class[] { Boolean.TYPE });
        if (localMethod == null) {
          break label55;
        }
        localMethod.invoke(paramSSLSocket, new Object[] { Boolean.valueOf(true) });
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        for (;;) {}
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        for (;;) {}
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        label55:
        for (;;) {}
      }
      if (paramSSLSocket != null) {
        paramSSLSocket.setEnabledCipherSuites(NoSSLv3Factory.a(paramSSLSocket.getEnabledCipherSuites()));
      }
    }
    
    public void a(String paramString)
    {
      try
      {
        Method localMethod = this.a.getClass().getMethod("setHostname", new Class[] { String.class });
        if (localMethod != null)
        {
          localMethod.setAccessible(true);
          localMethod.invoke(this.a, new Object[] { paramString });
        }
        return;
      }
      catch (NoSuchMethodException|InvocationTargetException|IllegalAccessException paramString) {}
    }
    
    public void setEnabledProtocols(String[] paramArrayOfString)
    {
      String[] arrayOfString = paramArrayOfString;
      if (paramArrayOfString != null)
      {
        arrayOfString = paramArrayOfString;
        if (paramArrayOfString.length == 1)
        {
          arrayOfString = paramArrayOfString;
          if ("SSLv3".equals(paramArrayOfString[0]))
          {
            paramArrayOfString = new ArrayList(Arrays.asList(this.a.getEnabledProtocols()));
            if (paramArrayOfString.size() > 1) {
              paramArrayOfString.remove("SSLv3");
            }
            arrayOfString = (String[])paramArrayOfString.toArray(new String[paramArrayOfString.size()]);
          }
        }
      }
      super.setEnabledProtocols(arrayOfString);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\NoSSLv3Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */