package org.chromium.net;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.NetworkInfo.State;
import android.net.NetworkRequest;
import android.net.NetworkRequest.Builder;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import java.util.Arrays;
import javax.annotation.concurrent.GuardedBy;
import org.chromium.base.ApplicationStatus;
import org.chromium.base.BuildConfig;
import org.chromium.base.ContextUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

@SuppressLint({"NewApi"})
public class NetworkChangeNotifierAutoDetect
  extends BroadcastReceiver
{
  private static final String jdField_a_of_type_JavaLangString = "NetworkChangeNotifierAutoDetect";
  private Intent jdField_a_of_type_AndroidContentIntent;
  private NetworkRequest jdField_a_of_type_AndroidNetNetworkRequest;
  private final Handler jdField_a_of_type_AndroidOsHandler = new Handler(this.jdField_a_of_type_AndroidOsLooper);
  private final Looper jdField_a_of_type_AndroidOsLooper = Looper.myLooper();
  private ConnectivityManagerDelegate jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$ConnectivityManagerDelegate;
  private MyNetworkCallback jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$MyNetworkCallback;
  private final NetworkConnectivityIntentFilter jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$NetworkConnectivityIntentFilter;
  private NetworkState jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$NetworkState;
  private final Observer jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$Observer;
  private final RegistrationPolicy jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$RegistrationPolicy;
  private WifiManagerDelegate jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$WifiManagerDelegate;
  private boolean jdField_a_of_type_Boolean;
  private boolean b;
  private boolean c;
  private boolean d;
  
  @TargetApi(21)
  public NetworkChangeNotifierAutoDetect(Observer paramObserver, RegistrationPolicy paramRegistrationPolicy)
  {
    this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$Observer = paramObserver;
    this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$ConnectivityManagerDelegate = new ConnectivityManagerDelegate(ContextUtils.getApplicationContext());
    this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$WifiManagerDelegate = new WifiManagerDelegate(ContextUtils.getApplicationContext());
    if (Build.VERSION.SDK_INT >= 21)
    {
      this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$MyNetworkCallback = new MyNetworkCallback(null);
      this.jdField_a_of_type_AndroidNetNetworkRequest = new NetworkRequest.Builder().addCapability(12).removeCapability(15).build();
    }
    else
    {
      this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$MyNetworkCallback = null;
      this.jdField_a_of_type_AndroidNetNetworkRequest = null;
    }
    this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$NetworkState = a();
    this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$NetworkConnectivityIntentFilter = new NetworkConnectivityIntentFilter();
    this.b = false;
    this.c = false;
    this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$RegistrationPolicy = paramRegistrationPolicy;
    this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$RegistrationPolicy.a(this);
    this.c = true;
  }
  
  @TargetApi(21)
  @VisibleForTesting
  static long a(Network paramNetwork)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return paramNetwork.getNetworkHandle();
    }
    return Integer.parseInt(paramNetwork.toString());
  }
  
  private void a(Runnable paramRunnable)
  {
    if (b())
    {
      paramRunnable.run();
      return;
    }
    this.jdField_a_of_type_AndroidOsHandler.post(paramRunnable);
  }
  
  private void a(boolean paramBoolean)
  {
    Observer localObserver = this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$Observer;
    if (localObserver != null) {
      localObserver.onConnectionVPNChanged(paramBoolean);
    }
  }
  
  private static int b(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    default: 
      return 0;
    case 9: 
      return 1;
    case 7: 
      return 7;
    case 6: 
      return 5;
    case 1: 
      return 2;
    }
    switch (paramInt2)
    {
    default: 
      return 0;
    case 13: 
      return 5;
    case 3: 
    case 5: 
    case 6: 
    case 8: 
    case 9: 
    case 10: 
    case 12: 
    case 14: 
    case 15: 
      return 4;
    }
    return 3;
  }
  
  private boolean b()
  {
    return this.jdField_a_of_type_AndroidOsLooper == Looper.myLooper();
  }
  
  @TargetApi(21)
  private static Network[] b(ConnectivityManagerDelegate paramConnectivityManagerDelegate, Network paramNetwork)
  {
    Network[] arrayOfNetwork = paramConnectivityManagerDelegate.a();
    int m = arrayOfNetwork.length;
    int i = 0;
    int k;
    for (int j = 0; i < m; j = k)
    {
      Network localNetwork = arrayOfNetwork[i];
      if (localNetwork.equals(paramNetwork))
      {
        k = j;
      }
      else
      {
        NetworkCapabilities localNetworkCapabilities = paramConnectivityManagerDelegate.a(localNetwork);
        k = j;
        if (localNetworkCapabilities != null) {
          if (!localNetworkCapabilities.hasCapability(12))
          {
            k = j;
          }
          else if (localNetworkCapabilities.hasTransport(4))
          {
            k = j;
            if (paramConnectivityManagerDelegate.a(localNetwork)) {
              return new Network[] { localNetwork };
            }
          }
          else
          {
            arrayOfNetwork[j] = localNetwork;
            k = j + 1;
          }
        }
      }
      i += 1;
    }
    return (Network[])Arrays.copyOf(arrayOfNetwork, j);
  }
  
  private void d()
  {
    if (BuildConfig.DCHECK_IS_ON)
    {
      if (b()) {
        return;
      }
      throw new IllegalStateException("Must be called on NetworkChangeNotifierAutoDetect thread.");
    }
  }
  
  private void e()
  {
    NetworkState localNetworkState = a();
    if ((localNetworkState.c() != this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$NetworkState.c()) || (!localNetworkState.a().equals(this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$NetworkState.a()))) {
      this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$Observer.onConnectionTypeChanged(localNetworkState.c());
    }
    if ((localNetworkState.c() != this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$NetworkState.c()) || (localNetworkState.d() != this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$NetworkState.d())) {
      this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$Observer.onConnectionSubtypeChanged(localNetworkState.d());
    }
    this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$NetworkState = localNetworkState;
  }
  
  public long a()
  {
    if (Build.VERSION.SDK_INT < 21) {
      return -1L;
    }
    return this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$ConnectivityManagerDelegate.a();
  }
  
  public NetworkState a()
  {
    return this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$ConnectivityManagerDelegate.a(this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$WifiManagerDelegate);
  }
  
  public void a()
  {
    d();
    this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$RegistrationPolicy.destroy();
    c();
  }
  
  public boolean a()
  {
    return this.d;
  }
  
  public long[] a()
  {
    int j = Build.VERSION.SDK_INT;
    int i = 0;
    if (j < 21) {
      return new long[0];
    }
    Network[] arrayOfNetwork = b(this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$ConnectivityManagerDelegate, null);
    long[] arrayOfLong = new long[arrayOfNetwork.length * 2];
    int k = arrayOfNetwork.length;
    j = 0;
    while (i < k)
    {
      Network localNetwork = arrayOfNetwork[i];
      int m = j + 1;
      arrayOfLong[j] = a(localNetwork);
      j = m + 1;
      arrayOfLong[m] = this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$ConnectivityManagerDelegate.a(localNetwork);
      i += 1;
    }
    return arrayOfLong;
  }
  
  public void b()
  {
    d();
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    if (this.c) {
      e();
    }
    int i = 0;
    for (;;)
    {
      try
      {
        if (X5ApiCompatibilityUtils.x5RegisterReceiver(ContextUtils.getApplicationContext(), this, this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$NetworkConnectivityIntentFilter) == null) {
          break label171;
        }
        bool = true;
        this.b = bool;
      }
      catch (SecurityException localSecurityException)
      {
        localSecurityException.printStackTrace();
      }
      this.jdField_a_of_type_Boolean = true;
      Object localObject = this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$MyNetworkCallback;
      if (localObject != null) {
        ((MyNetworkCallback)localObject).a();
      }
      try
      {
        this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$ConnectivityManagerDelegate.a(this.jdField_a_of_type_AndroidNetNetworkRequest, this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$MyNetworkCallback);
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        long[] arrayOfLong;
        for (;;) {}
      }
      this.d = true;
      this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$MyNetworkCallback = null;
      if ((!this.d) && (this.c))
      {
        localObject = b(this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$ConnectivityManagerDelegate, null);
        arrayOfLong = new long[localObject.length];
        while (i < localObject.length)
        {
          arrayOfLong[i] = a(localObject[i]);
          i += 1;
        }
        this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$Observer.purgeActiveNetworkList(arrayOfLong);
      }
      return;
      label171:
      boolean bool = false;
    }
  }
  
  public void c()
  {
    d();
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    X5ApiCompatibilityUtils.x5UnRegisterReceiver(ContextUtils.getApplicationContext(), this);
    this.jdField_a_of_type_Boolean = false;
    MyNetworkCallback localMyNetworkCallback = this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$MyNetworkCallback;
    if (localMyNetworkCallback != null) {}
    try
    {
      this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect$ConnectivityManagerDelegate.a(localMyNetworkCallback);
      return;
    }
    catch (Exception localException) {}
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    this.jdField_a_of_type_AndroidContentIntent = paramIntent;
    a(new Runnable()
    {
      public void run()
      {
        if (!NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this)) {
          return;
        }
        if (NetworkChangeNotifierAutoDetect.b(NetworkChangeNotifierAutoDetect.this))
        {
          NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this, false);
          return;
        }
        if ((NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this) != null) && ("android.net.conn.CONNECTIVITY_CHANGE".equals(NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this).getAction()))) {}
        try
        {
          NetworkInfo localNetworkInfo = (NetworkInfo)NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this).getExtras().get("networkInfo");
          if ((localNetworkInfo != null) && (localNetworkInfo.getType() == 17)) {
            if (localNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
              NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this, true);
            } else if (localNetworkInfo.getState() == NetworkInfo.State.DISCONNECTED) {
              NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this, false);
            }
          }
          NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this, null);
        }
        catch (Exception localException)
        {
          for (;;) {}
        }
        NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this, null);
        NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this);
      }
    });
  }
  
  static class ConnectivityManagerDelegate
  {
    private final ConnectivityManager jdField_a_of_type_AndroidNetConnectivityManager;
    
    ConnectivityManagerDelegate()
    {
      this.jdField_a_of_type_AndroidNetConnectivityManager = null;
    }
    
    ConnectivityManagerDelegate(Context paramContext)
    {
      this.jdField_a_of_type_AndroidNetConnectivityManager = ((ConnectivityManager)paramContext.getSystemService("connectivity"));
    }
    
    @TargetApi(21)
    private NetworkInfo a()
    {
      NetworkInfo localNetworkInfo = this.jdField_a_of_type_AndroidNetConnectivityManager.getActiveNetworkInfo();
      if (localNetworkInfo == null) {
        return null;
      }
      if (localNetworkInfo.isConnected()) {
        return localNetworkInfo;
      }
      if (Build.VERSION.SDK_INT < 21) {
        return null;
      }
      if (localNetworkInfo.getDetailedState() != NetworkInfo.DetailedState.BLOCKED) {
        return null;
      }
      if (ApplicationStatus.getStateForApplication() != 1) {
        return null;
      }
      return localNetworkInfo;
    }
    
    private NetworkInfo a(Network paramNetwork)
    {
      try
      {
        NetworkInfo localNetworkInfo = this.jdField_a_of_type_AndroidNetConnectivityManager.getNetworkInfo(paramNetwork);
        return localNetworkInfo;
      }
      catch (NullPointerException localNullPointerException)
      {
        label22:
        for (;;) {}
      }
      try
      {
        paramNetwork = this.jdField_a_of_type_AndroidNetConnectivityManager.getNetworkInfo(paramNetwork);
        return paramNetwork;
      }
      catch (NullPointerException paramNetwork)
      {
        break label22;
      }
      return null;
    }
    
    @TargetApi(21)
    int a(Network paramNetwork)
    {
      NetworkInfo localNetworkInfo = a(paramNetwork);
      paramNetwork = localNetworkInfo;
      if (localNetworkInfo != null)
      {
        paramNetwork = localNetworkInfo;
        if (localNetworkInfo.getType() == 17) {
          paramNetwork = this.jdField_a_of_type_AndroidNetConnectivityManager.getActiveNetworkInfo();
        }
      }
      if ((paramNetwork != null) && (paramNetwork.isConnected())) {
        return NetworkChangeNotifierAutoDetect.a(paramNetwork.getType(), paramNetwork.getSubtype());
      }
      return 6;
    }
    
    @TargetApi(21)
    long a()
    {
      NetworkInfo localNetworkInfo1 = this.jdField_a_of_type_AndroidNetConnectivityManager.getActiveNetworkInfo();
      if (localNetworkInfo1 == null) {
        return -1L;
      }
      Network[] arrayOfNetwork = NetworkChangeNotifierAutoDetect.a(this, null);
      int j = arrayOfNetwork.length;
      int i = 0;
      long l2;
      for (long l1 = -1L; i < j; l1 = l2)
      {
        Network localNetwork = arrayOfNetwork[i];
        NetworkInfo localNetworkInfo2 = a(localNetwork);
        l2 = l1;
        if (localNetworkInfo2 != null) {
          if (localNetworkInfo2.getType() != localNetworkInfo1.getType())
          {
            l2 = l1;
            if (localNetworkInfo2.getType() != 17) {}
          }
          else
          {
            if ((!jdField_a_of_type_Boolean) && (l1 != -1L)) {
              throw new AssertionError();
            }
            l2 = NetworkChangeNotifierAutoDetect.a(localNetwork);
          }
        }
        i += 1;
      }
      return l1;
    }
    
    @TargetApi(21)
    @VisibleForTesting
    protected NetworkCapabilities a(Network paramNetwork)
    {
      return this.jdField_a_of_type_AndroidNetConnectivityManager.getNetworkCapabilities(paramNetwork);
    }
    
    NetworkChangeNotifierAutoDetect.NetworkState a(NetworkChangeNotifierAutoDetect.WifiManagerDelegate paramWifiManagerDelegate)
    {
      NetworkInfo localNetworkInfo = a();
      if (localNetworkInfo == null) {
        return new NetworkChangeNotifierAutoDetect.NetworkState(false, -1, -1, null);
      }
      if (localNetworkInfo.getType() == 1)
      {
        if ((localNetworkInfo.getExtraInfo() != null) && (!"".equals(localNetworkInfo.getExtraInfo()))) {
          return new NetworkChangeNotifierAutoDetect.NetworkState(true, localNetworkInfo.getType(), localNetworkInfo.getSubtype(), localNetworkInfo.getExtraInfo());
        }
        return new NetworkChangeNotifierAutoDetect.NetworkState(true, localNetworkInfo.getType(), localNetworkInfo.getSubtype(), paramWifiManagerDelegate.a());
      }
      return new NetworkChangeNotifierAutoDetect.NetworkState(true, localNetworkInfo.getType(), localNetworkInfo.getSubtype(), null);
    }
    
    @TargetApi(21)
    void a(ConnectivityManager.NetworkCallback paramNetworkCallback)
    {
      try
      {
        this.jdField_a_of_type_AndroidNetConnectivityManager.unregisterNetworkCallback(paramNetworkCallback);
        return;
      }
      catch (IllegalArgumentException paramNetworkCallback) {}
    }
    
    @TargetApi(21)
    void a(NetworkRequest paramNetworkRequest, ConnectivityManager.NetworkCallback paramNetworkCallback)
    {
      try
      {
        this.jdField_a_of_type_AndroidNetConnectivityManager.registerNetworkCallback(paramNetworkRequest, paramNetworkCallback);
        return;
      }
      catch (Exception paramNetworkRequest) {}
    }
    
    /* Error */
    @TargetApi(21)
    @VisibleForTesting
    protected boolean a(Network paramNetwork)
    {
      // Byte code:
      //   0: new 148	java/net/Socket
      //   3: dup
      //   4: invokespecial 149	java/net/Socket:<init>	()V
      //   7: astore_2
      //   8: aload_1
      //   9: aload_2
      //   10: invokevirtual 155	android/net/Network:bindSocket	(Ljava/net/Socket;)V
      //   13: aload_2
      //   14: invokevirtual 158	java/net/Socket:close	()V
      //   17: iconst_1
      //   18: ireturn
      //   19: astore_1
      //   20: aload_2
      //   21: invokevirtual 158	java/net/Socket:close	()V
      //   24: aload_1
      //   25: athrow
      //   26: aload_2
      //   27: invokevirtual 158	java/net/Socket:close	()V
      //   30: iconst_0
      //   31: ireturn
      //   32: astore_1
      //   33: goto -7 -> 26
      //   36: astore_1
      //   37: goto -20 -> 17
      //   40: astore_2
      //   41: goto -17 -> 24
      //   44: astore_1
      //   45: iconst_0
      //   46: ireturn
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	47	0	this	ConnectivityManagerDelegate
      //   0	47	1	paramNetwork	Network
      //   7	20	2	localSocket	java.net.Socket
      //   40	1	2	localIOException	java.io.IOException
      // Exception table:
      //   from	to	target	type
      //   8	13	19	finally
      //   8	13	32	java/io/IOException
      //   13	17	36	java/io/IOException
      //   20	24	40	java/io/IOException
      //   26	30	44	java/io/IOException
    }
    
    @TargetApi(21)
    @VisibleForTesting
    protected Network[] a()
    {
      Network[] arrayOfNetwork2 = this.jdField_a_of_type_AndroidNetConnectivityManager.getAllNetworks();
      Network[] arrayOfNetwork1 = arrayOfNetwork2;
      if (arrayOfNetwork2 == null) {
        arrayOfNetwork1 = new Network[0];
      }
      return arrayOfNetwork1;
    }
  }
  
  @TargetApi(21)
  private class MyNetworkCallback
    extends ConnectivityManager.NetworkCallback
  {
    private Network jdField_a_of_type_AndroidNetNetwork;
    
    private MyNetworkCallback() {}
    
    private boolean a(Network paramNetwork)
    {
      Network localNetwork = this.jdField_a_of_type_AndroidNetNetwork;
      return (localNetwork != null) && (!localNetwork.equals(paramNetwork));
    }
    
    private boolean a(Network paramNetwork, NetworkCapabilities paramNetworkCapabilities)
    {
      NetworkCapabilities localNetworkCapabilities = paramNetworkCapabilities;
      if (paramNetworkCapabilities == null) {
        localNetworkCapabilities = NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this).a(paramNetwork);
      }
      return (localNetworkCapabilities == null) || ((localNetworkCapabilities.hasTransport(4)) && (!NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this).a(paramNetwork)));
    }
    
    private boolean b(Network paramNetwork, NetworkCapabilities paramNetworkCapabilities)
    {
      return (a(paramNetwork)) || (a(paramNetwork, paramNetworkCapabilities));
    }
    
    void a()
    {
      Network[] arrayOfNetwork = NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this), null);
      this.jdField_a_of_type_AndroidNetNetwork = null;
      if (arrayOfNetwork.length == 1)
      {
        NetworkCapabilities localNetworkCapabilities = NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this).a(arrayOfNetwork[0]);
        if ((localNetworkCapabilities != null) && (localNetworkCapabilities.hasTransport(4))) {
          this.jdField_a_of_type_AndroidNetNetwork = arrayOfNetwork[0];
        }
      }
    }
    
    public void onAvailable(Network paramNetwork)
    {
      NetworkCapabilities localNetworkCapabilities = NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this).a(paramNetwork);
      if (b(paramNetwork, localNetworkCapabilities)) {
        return;
      }
      final boolean bool = localNetworkCapabilities.hasTransport(4);
      if (bool) {
        this.jdField_a_of_type_AndroidNetNetwork = paramNetwork;
      }
      final long l = NetworkChangeNotifierAutoDetect.a(paramNetwork);
      int i = NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this).a(paramNetwork);
      NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this, new Runnable()
      {
        public void run()
        {
          NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this).onNetworkConnect(l, bool);
          if (this.jdField_a_of_type_Boolean)
          {
            NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this).onConnectionTypeChanged(bool);
            NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this).purgeActiveNetworkList(new long[] { l });
          }
        }
      });
    }
    
    public void onCapabilitiesChanged(Network paramNetwork, NetworkCapabilities paramNetworkCapabilities)
    {
      if (b(paramNetwork, paramNetworkCapabilities)) {
        return;
      }
      final long l = NetworkChangeNotifierAutoDetect.a(paramNetwork);
      int i = NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this).a(paramNetwork);
      NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this, new Runnable()
      {
        public void run()
        {
          NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this).onNetworkConnect(l, this.jdField_a_of_type_Int);
        }
      });
    }
    
    public void onLosing(Network paramNetwork, int paramInt)
    {
      if (b(paramNetwork, null)) {
        return;
      }
      final long l = NetworkChangeNotifierAutoDetect.a(paramNetwork);
      NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this, new Runnable()
      {
        public void run()
        {
          NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this).onNetworkSoonToDisconnect(l);
        }
      });
    }
    
    public void onLost(final Network paramNetwork)
    {
      if (a(paramNetwork)) {
        return;
      }
      NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this, new Runnable()
      {
        public void run()
        {
          NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this).onNetworkDisconnect(NetworkChangeNotifierAutoDetect.a(paramNetwork));
        }
      });
      Network localNetwork = this.jdField_a_of_type_AndroidNetNetwork;
      if (localNetwork != null)
      {
        if ((!jdField_a_of_type_Boolean) && (!paramNetwork.equals(localNetwork))) {
          throw new AssertionError();
        }
        this.jdField_a_of_type_AndroidNetNetwork = null;
        paramNetwork = NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this), paramNetwork);
        int j = paramNetwork.length;
        final int i = 0;
        while (i < j)
        {
          onAvailable(paramNetwork[i]);
          i += 1;
        }
        i = NetworkChangeNotifierAutoDetect.this.a().c();
        NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this, new Runnable()
        {
          public void run()
          {
            NetworkChangeNotifierAutoDetect.a(NetworkChangeNotifierAutoDetect.this).onConnectionTypeChanged(i);
          }
        });
      }
    }
  }
  
  @SuppressLint({"NewApi", "ParcelCreator"})
  private static class NetworkConnectivityIntentFilter
    extends IntentFilter
  {
    NetworkConnectivityIntentFilter()
    {
      addAction("android.net.conn.CONNECTIVITY_CHANGE");
    }
  }
  
  public static class NetworkState
  {
    private final int jdField_a_of_type_Int;
    private final String jdField_a_of_type_JavaLangString;
    private final int jdField_b_of_type_Int;
    private final boolean jdField_b_of_type_Boolean;
    
    public NetworkState(boolean paramBoolean, int paramInt1, int paramInt2, String paramString)
    {
      this.jdField_b_of_type_Boolean = paramBoolean;
      this.jdField_a_of_type_Int = paramInt1;
      this.jdField_b_of_type_Int = paramInt2;
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Int != 1) && (paramString != null)) {
        throw new AssertionError();
      }
      String str = paramString;
      if (paramString == null) {
        str = "";
      }
      this.jdField_a_of_type_JavaLangString = str;
    }
    
    public int a()
    {
      return this.jdField_a_of_type_Int;
    }
    
    public String a()
    {
      return this.jdField_a_of_type_JavaLangString;
    }
    
    public boolean a()
    {
      return this.jdField_b_of_type_Boolean;
    }
    
    public int b()
    {
      return this.jdField_b_of_type_Int;
    }
    
    public int c()
    {
      if (!a()) {
        return 6;
      }
      return NetworkChangeNotifierAutoDetect.a(a(), b());
    }
    
    public int d()
    {
      if (!a()) {
        return 1;
      }
      switch (a())
      {
      default: 
        return 0;
      case 1: 
      case 6: 
      case 7: 
      case 9: 
        return 0;
      }
      switch (b())
      {
      default: 
        return 0;
      case 15: 
        return 17;
      case 14: 
        return 16;
      case 13: 
        return 18;
      case 12: 
        return 13;
      case 11: 
        return 4;
      case 10: 
        return 12;
      case 9: 
        return 15;
      case 8: 
        return 14;
      case 7: 
        return 6;
      case 6: 
        return 11;
      case 5: 
        return 10;
      case 4: 
        return 5;
      case 3: 
        return 9;
      case 2: 
        return 8;
      }
      return 7;
    }
  }
  
  public static abstract interface Observer
  {
    public abstract void onConnectionSubtypeChanged(int paramInt);
    
    public abstract void onConnectionTypeChanged(int paramInt);
    
    public abstract void onConnectionVPNChanged(boolean paramBoolean);
    
    public abstract void onNetworkConnect(long paramLong, int paramInt);
    
    public abstract void onNetworkDisconnect(long paramLong);
    
    public abstract void onNetworkSoonToDisconnect(long paramLong);
    
    public abstract void purgeActiveNetworkList(long[] paramArrayOfLong);
  }
  
  public static abstract class RegistrationPolicy
  {
    private NetworkChangeNotifierAutoDetect jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect;
    
    protected void a(NetworkChangeNotifierAutoDetect paramNetworkChangeNotifierAutoDetect)
    {
      this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect = paramNetworkChangeNotifierAutoDetect;
    }
    
    protected abstract void destroy();
    
    protected final void register()
    {
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect == null)) {
        throw new AssertionError();
      }
      this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect.b();
    }
    
    protected final void unregister()
    {
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect == null)) {
        throw new AssertionError();
      }
      this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect.c();
    }
  }
  
  static class WifiManagerDelegate
  {
    private final Context jdField_a_of_type_AndroidContentContext;
    @GuardedBy("mLock")
    private WifiManager jdField_a_of_type_AndroidNetWifiWifiManager;
    private final Object jdField_a_of_type_JavaLangObject = new Object();
    @GuardedBy("mLock")
    private boolean jdField_a_of_type_Boolean;
    @GuardedBy("mLock")
    private boolean b;
    
    WifiManagerDelegate()
    {
      this.jdField_a_of_type_AndroidContentContext = null;
    }
    
    WifiManagerDelegate(Context paramContext)
    {
      this.jdField_a_of_type_AndroidContentContext = paramContext;
    }
    
    @GuardedBy("mLock")
    private WifiInfo a()
    {
      try
      {
        localWifiInfo = this.jdField_a_of_type_AndroidNetWifiWifiManager.getConnectionInfo();
        return localWifiInfo;
      }
      catch (NullPointerException localNullPointerException1)
      {
        WifiInfo localWifiInfo;
        label20:
        for (;;) {}
      }
      try
      {
        localWifiInfo = this.jdField_a_of_type_AndroidNetWifiWifiManager.getConnectionInfo();
        return localWifiInfo;
      }
      catch (NullPointerException localNullPointerException2)
      {
        break label20;
      }
      return null;
    }
    
    @SuppressLint({"WifiManagerPotentialLeak"})
    @GuardedBy("mLock")
    private boolean a()
    {
      if (this.jdField_a_of_type_Boolean) {
        return this.b;
      }
      boolean bool;
      if (this.jdField_a_of_type_AndroidContentContext.getPackageManager().checkPermission("android.permission.ACCESS_WIFI_STATE", this.jdField_a_of_type_AndroidContentContext.getPackageName()) == 0) {
        bool = true;
      } else {
        bool = false;
      }
      this.b = bool;
      WifiManager localWifiManager;
      if (this.b) {
        localWifiManager = (WifiManager)this.jdField_a_of_type_AndroidContentContext.getSystemService("wifi");
      } else {
        localWifiManager = null;
      }
      this.jdField_a_of_type_AndroidNetWifiWifiManager = localWifiManager;
      this.jdField_a_of_type_Boolean = true;
      return this.b;
    }
    
    String a()
    {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        if (a())
        {
          Object localObject2 = a();
          if (localObject2 != null)
          {
            localObject2 = ((WifiInfo)localObject2).getSSID();
            return (String)localObject2;
          }
          return "";
        }
        return AndroidNetworkLibrary.getWifiSSID();
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\net\NetworkChangeNotifierAutoDetect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */