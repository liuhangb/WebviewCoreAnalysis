package org.chromium.net;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.os.Build.VERSION;
import android.os.ParcelFileDescriptor;
import android.security.NetworkSecurityPolicy;
import android.telephony.TelephonyManager;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.net.URLConnection;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.List;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.CalledByNativeUnchecked;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

class AndroidNetworkLibrary
{
  @CalledByNativeUnchecked
  public static void addTestRootCertificate(byte[] paramArrayOfByte)
    throws CertificateException, KeyStoreException, NoSuchAlgorithmException
  {
    X509Util.a(paramArrayOfByte);
  }
  
  @CalledByNativeUnchecked
  public static void clearTestRootCertificates()
    throws NoSuchAlgorithmException, CertificateException, KeyStoreException
  {}
  
  @TargetApi(23)
  @CalledByNative
  private static byte[][] getDnsServers()
  {
    Object localObject1 = (ConnectivityManager)ContextUtils.getApplicationContext().getSystemService("connectivity");
    int i = 0;
    if (localObject1 == null) {
      return (byte[][])Array.newInstance(Byte.TYPE, new int[] { 0, 0 });
    }
    Object localObject2 = ((ConnectivityManager)localObject1).getActiveNetwork();
    if (localObject2 == null) {
      return (byte[][])Array.newInstance(Byte.TYPE, new int[] { 0, 0 });
    }
    localObject1 = ((ConnectivityManager)localObject1).getLinkProperties((Network)localObject2);
    if (localObject1 == null) {
      return (byte[][])Array.newInstance(Byte.TYPE, new int[] { 0, 0 });
    }
    localObject1 = ((LinkProperties)localObject1).getDnsServers();
    localObject2 = new byte[((List)localObject1).size()][];
    while (i < ((List)localObject1).size())
    {
      localObject2[i] = ((InetAddress)((List)localObject1).get(i)).getAddress();
      i += 1;
    }
    return (byte[][])localObject2;
  }
  
  @TargetApi(23)
  @CalledByNative
  private static boolean getIsCaptivePortal()
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    if (i < 23) {
      return false;
    }
    Object localObject = (ConnectivityManager)ContextUtils.getApplicationContext().getSystemService("connectivity");
    if (localObject == null) {
      return false;
    }
    Network localNetwork = ((ConnectivityManager)localObject).getActiveNetwork();
    if (localNetwork == null) {
      return false;
    }
    localObject = ((ConnectivityManager)localObject).getNetworkCapabilities(localNetwork);
    boolean bool1 = bool2;
    if (localObject != null)
    {
      bool1 = bool2;
      if (((NetworkCapabilities)localObject).hasCapability(17)) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  @CalledByNative
  private static boolean getIsRoaming()
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)ContextUtils.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
    if (localNetworkInfo == null) {
      return false;
    }
    return localNetworkInfo.isRoaming();
  }
  
  @CalledByNative
  public static String getMimeTypeFromExtension(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("foo.");
    localStringBuilder.append(paramString);
    return URLConnection.guessContentTypeFromName(localStringBuilder.toString());
  }
  
  @CalledByNative
  private static String getNetworkCountryIso()
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)ContextUtils.getApplicationContext().getSystemService("phone");
    if (localTelephonyManager == null) {
      return "";
    }
    return localTelephonyManager.getNetworkCountryIso();
  }
  
  @CalledByNative
  private static String getNetworkOperator()
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)ContextUtils.getApplicationContext().getSystemService("phone");
    if (localTelephonyManager == null) {
      return "";
    }
    return localTelephonyManager.getNetworkOperator();
  }
  
  @CalledByNative
  private static String getSimOperator()
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)ContextUtils.getApplicationContext().getSystemService("phone");
    if (localTelephonyManager == null) {
      return "";
    }
    return localTelephonyManager.getSimOperator();
  }
  
  @CalledByNative
  public static String getWifiSSID()
  {
    Object localObject = X5ApiCompatibilityUtils.x5RegisterReceiver(ContextUtils.getApplicationContext(), null, new IntentFilter("android.net.wifi.STATE_CHANGE"));
    if (localObject != null)
    {
      localObject = (WifiInfo)((Intent)localObject).getParcelableExtra("wifiInfo");
      if (localObject != null)
      {
        localObject = ((WifiInfo)localObject).getSSID();
        if (localObject != null) {
          return (String)localObject;
        }
      }
    }
    return "";
  }
  
  @CalledByNative
  public static boolean haveOnlyLoopbackAddresses()
  {
    for (;;)
    {
      try
      {
        Enumeration localEnumeration = NetworkInterface.getNetworkInterfaces();
        if (localEnumeration == null) {
          return false;
        }
        if (localEnumeration.hasMoreElements()) {
          localNetworkInterface = (NetworkInterface)localEnumeration.nextElement();
        }
      }
      catch (Exception localException)
      {
        NetworkInterface localNetworkInterface;
        return false;
      }
      try
      {
        if (localNetworkInterface.isUp())
        {
          boolean bool = localNetworkInterface.isLoopback();
          if (!bool) {
            return false;
          }
        }
      }
      catch (SocketException localSocketException) {}
    }
    return true;
  }
  
  @CalledByNative
  private static boolean isCleartextPermitted(String paramString)
  {
    try
    {
      boolean bool = NetworkSecurityPolicyProxy.a().a(paramString);
      return bool;
    }
    catch (IllegalArgumentException paramString)
    {
      for (;;) {}
    }
    return NetworkSecurityPolicyProxy.a().a();
  }
  
  @CalledByNative
  private static void tagSocket(int paramInt1, int paramInt2, int paramInt3)
    throws IOException
  {
    int i = TrafficStats.getThreadStatsTag();
    if (paramInt3 != i) {
      TrafficStats.setThreadStatsTag(paramInt3);
    }
    if (paramInt2 != -1) {
      ThreadStatsUid.a(paramInt2);
    }
    ParcelFileDescriptor localParcelFileDescriptor;
    if (Build.VERSION.SDK_INT < 23)
    {
      localParcelFileDescriptor = null;
      localObject = SetFileDescriptor.a(paramInt1);
    }
    else
    {
      localParcelFileDescriptor = ParcelFileDescriptor.adoptFd(paramInt1);
      localObject = localParcelFileDescriptor.getFileDescriptor();
    }
    Object localObject = new SocketFd((FileDescriptor)localObject);
    TrafficStats.tagSocket((Socket)localObject);
    ((Socket)localObject).close();
    if (localParcelFileDescriptor != null) {
      localParcelFileDescriptor.detachFd();
    }
    if (paramInt3 != i) {
      TrafficStats.setThreadStatsTag(i);
    }
    if (paramInt2 != -1) {
      ThreadStatsUid.a();
    }
  }
  
  @CalledByNative
  public static AndroidCertVerifyResult verifyServerCertificates(byte[][] paramArrayOfByte, String paramString1, String paramString2)
  {
    try
    {
      paramArrayOfByte = X509Util.a(paramArrayOfByte, paramString1, paramString2);
      return paramArrayOfByte;
    }
    catch (KeyStoreException paramArrayOfByte)
    {
      for (;;) {}
    }
    catch (NoSuchAlgorithmException paramArrayOfByte)
    {
      for (;;) {}
    }
    catch (IllegalArgumentException paramArrayOfByte)
    {
      for (;;) {}
    }
    catch (NullPointerException paramArrayOfByte)
    {
      for (;;) {}
    }
    return new AndroidCertVerifyResult(-1);
    return new AndroidCertVerifyResult(-1);
    return new AndroidCertVerifyResult(-1);
    return new AndroidCertVerifyResult(-1);
  }
  
  public static class NetworkSecurityPolicyProxy
  {
    private static NetworkSecurityPolicyProxy a = new NetworkSecurityPolicyProxy();
    
    public static NetworkSecurityPolicyProxy a()
    {
      return a;
    }
    
    @TargetApi(23)
    public boolean a()
    {
      if (Build.VERSION.SDK_INT < 23) {
        return true;
      }
      return NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted();
    }
    
    @TargetApi(24)
    public boolean a(String paramString)
    {
      if (Build.VERSION.SDK_INT < 24) {
        return a();
      }
      return NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted(paramString);
    }
  }
  
  private static class SetFileDescriptor
  {
    private static final Method a;
    
    static
    {
      try
      {
        a = FileDescriptor.class.getMethod("setInt$", new Class[] { Integer.TYPE });
        return;
      }
      catch (SecurityException localSecurityException) {}catch (NoSuchMethodException localNoSuchMethodException) {}
      throw new RuntimeException("Unable to get FileDescriptor.setInt$", localNoSuchMethodException);
    }
    
    public static FileDescriptor a(int paramInt)
    {
      try
      {
        FileDescriptor localFileDescriptor = new FileDescriptor();
        a.invoke(localFileDescriptor, new Object[] { Integer.valueOf(paramInt) });
        return localFileDescriptor;
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        throw new RuntimeException("FileDescriptor.setInt$() failed", localInvocationTargetException);
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new RuntimeException("FileDescriptor.setInt$() failed", localIllegalAccessException);
      }
    }
  }
  
  private static class SocketFd
    extends Socket
  {
    SocketFd(FileDescriptor paramFileDescriptor)
      throws IOException
    {
      super();
    }
    
    private static class SocketImplFd
      extends SocketImpl
    {
      SocketImplFd(FileDescriptor paramFileDescriptor)
      {
        this.fd = paramFileDescriptor;
      }
      
      protected void accept(SocketImpl paramSocketImpl)
      {
        throw new RuntimeException("accept not implemented");
      }
      
      protected int available()
      {
        throw new RuntimeException("accept not implemented");
      }
      
      protected void bind(InetAddress paramInetAddress, int paramInt)
      {
        throw new RuntimeException("accept not implemented");
      }
      
      protected void close() {}
      
      protected void connect(String paramString, int paramInt)
      {
        throw new RuntimeException("connect not implemented");
      }
      
      protected void connect(InetAddress paramInetAddress, int paramInt)
      {
        throw new RuntimeException("connect not implemented");
      }
      
      protected void connect(SocketAddress paramSocketAddress, int paramInt)
      {
        throw new RuntimeException("connect not implemented");
      }
      
      protected void create(boolean paramBoolean) {}
      
      protected InputStream getInputStream()
      {
        throw new RuntimeException("getInputStream not implemented");
      }
      
      public Object getOption(int paramInt)
      {
        throw new RuntimeException("getOption not implemented");
      }
      
      protected OutputStream getOutputStream()
      {
        throw new RuntimeException("getOutputStream not implemented");
      }
      
      protected void listen(int paramInt)
      {
        throw new RuntimeException("listen not implemented");
      }
      
      protected void sendUrgentData(int paramInt)
      {
        throw new RuntimeException("sendUrgentData not implemented");
      }
      
      public void setOption(int paramInt, Object paramObject)
      {
        throw new RuntimeException("setOption not implemented");
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\net\AndroidNetworkLibrary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */