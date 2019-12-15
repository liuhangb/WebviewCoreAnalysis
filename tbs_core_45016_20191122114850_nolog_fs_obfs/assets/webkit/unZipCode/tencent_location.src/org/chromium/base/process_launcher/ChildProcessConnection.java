package org.chromium.base.process_launcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import java.util.List;
import org.chromium.base.Log;
import org.chromium.base.TraceEvent;
import org.chromium.base.VisibleForTesting;

public class ChildProcessConnection
{
  private int jdField_a_of_type_Int;
  private final ComponentName jdField_a_of_type_AndroidContentComponentName;
  private final Bundle jdField_a_of_type_AndroidOsBundle;
  private final Handler jdField_a_of_type_AndroidOsHandler = new Handler();
  private final ChildServiceConnection jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection;
  private ConnectionCallback jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionCallback;
  private ConnectionParams jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionParams;
  private ServiceCallback jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ServiceCallback;
  private IChildProcessService jdField_a_of_type_OrgChromiumBaseProcess_launcherIChildProcessService;
  private int jdField_b_of_type_Int;
  private final ChildServiceConnection jdField_b_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection;
  private final boolean jdField_b_of_type_Boolean;
  private int jdField_c_of_type_Int;
  private final ChildServiceConnection jdField_c_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection;
  private boolean jdField_c_of_type_Boolean;
  private final ChildServiceConnection jdField_d_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection;
  private boolean jdField_d_of_type_Boolean;
  private boolean e;
  private boolean f;
  private boolean g;
  
  public ChildProcessConnection(Context paramContext, ComponentName paramComponentName, boolean paramBoolean1, boolean paramBoolean2, Bundle paramBundle)
  {
    this(paramContext, paramComponentName, paramBoolean1, paramBoolean2, paramBundle, null);
  }
  
  @VisibleForTesting
  public ChildProcessConnection(final Context paramContext, ComponentName paramComponentName, boolean paramBoolean1, boolean paramBoolean2, Bundle paramBundle, ChildServiceConnectionFactory paramChildServiceConnectionFactory)
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_AndroidContentComponentName = paramComponentName;
    if (paramBundle != null) {
      localObject = paramBundle;
    } else {
      localObject = new Bundle();
    }
    this.jdField_a_of_type_AndroidOsBundle = ((Bundle)localObject);
    this.jdField_a_of_type_AndroidOsBundle.putBoolean("org.chromium.base.process_launcher.extra.bind_to_caller", paramBoolean1);
    this.jdField_b_of_type_Boolean = paramBoolean1;
    Object localObject = paramChildServiceConnectionFactory;
    if (paramChildServiceConnectionFactory == null) {
      localObject = new ChildServiceConnectionFactory()
      {
        public ChildProcessConnection.ChildServiceConnection createConnection(Intent paramAnonymousIntent, int paramAnonymousInt, ChildProcessConnection.ChildServiceConnectionDelegate paramAnonymousChildServiceConnectionDelegate)
        {
          return new ChildProcessConnection.ChildServiceConnectionImpl(paramContext, paramAnonymousIntent, paramAnonymousInt, paramAnonymousChildServiceConnectionDelegate, null);
        }
      };
    }
    paramContext = new ChildServiceConnectionDelegate()
    {
      public void onServiceConnected(final IBinder paramAnonymousIBinder)
      {
        ChildProcessConnection.a(ChildProcessConnection.this).post(new Runnable()
        {
          public void run()
          {
            ChildProcessConnection.a(ChildProcessConnection.this, paramAnonymousIBinder);
          }
        });
      }
      
      public void onServiceDisconnected()
      {
        ChildProcessConnection.a(ChildProcessConnection.this).post(new Runnable()
        {
          public void run()
          {
            ChildProcessConnection.a(ChildProcessConnection.this);
          }
        });
      }
    };
    paramChildServiceConnectionFactory = new Intent();
    paramChildServiceConnectionFactory.setComponent(paramComponentName);
    if (paramBundle != null) {
      paramChildServiceConnectionFactory.putExtras(paramBundle);
    }
    int i;
    if (paramBoolean2) {
      i = Integer.MIN_VALUE;
    } else {
      i = 0;
    }
    i |= 0x1;
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection = ((ChildServiceConnectionFactory)localObject).createConnection(paramChildServiceConnectionFactory, i, paramContext);
    this.jdField_c_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection = ((ChildServiceConnectionFactory)localObject).createConnection(paramChildServiceConnectionFactory, i, paramContext);
    this.jdField_b_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection = ((ChildServiceConnectionFactory)localObject).createConnection(paramChildServiceConnectionFactory, i | 0x40, paramContext);
    this.jdField_d_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection = ((ChildServiceConnectionFactory)localObject).createConnection(paramChildServiceConnectionFactory, i | 0x20, paramContext);
  }
  
  private void a(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Int == 0)) {
      throw new AssertionError("Child service claims to be run by a process of pid=0.");
    }
    ConnectionCallback localConnectionCallback = this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionCallback;
    if (localConnectionCallback != null) {
      localConnectionCallback.onConnected(this);
    }
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionCallback = null;
  }
  
  private void a(IBinder paramIBinder)
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    if (this.jdField_c_of_type_Boolean) {
      return;
    }
    try
    {
      TraceEvent.begin("ChildProcessConnection.ChildServiceConnection.onServiceConnected");
      this.jdField_c_of_type_Boolean = true;
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherIChildProcessService = IChildProcessService.Stub.asInterface(paramIBinder);
      boolean bool = this.jdField_b_of_type_Boolean;
      if (bool) {
        try
        {
          if (!this.jdField_a_of_type_OrgChromiumBaseProcess_launcherIChildProcessService.bindToCaller())
          {
            if (this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ServiceCallback != null) {
              this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ServiceCallback.onChildStartFailed(this);
            }
            a();
            return;
          }
        }
        catch (RemoteException paramIBinder)
        {
          Log.e("ChildProcessConn", "Failed to bind service to connection.", new Object[] { paramIBinder });
          return;
        }
      }
      if (this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ServiceCallback != null) {
        this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ServiceCallback.onChildStarted();
      }
      this.jdField_d_of_type_Boolean = true;
      if (this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionParams != null) {
        c();
      }
      return;
    }
    finally
    {
      TraceEvent.end("ChildProcessConnection.ChildServiceConnection.onServiceConnected");
    }
  }
  
  private boolean a()
  {
    return this.jdField_a_of_type_AndroidOsHandler.getLooper() == Looper.myLooper();
  }
  
  private boolean a(boolean paramBoolean)
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (this.g)) {
      throw new AssertionError();
    }
    ChildServiceConnection localChildServiceConnection;
    if (paramBoolean) {
      localChildServiceConnection = this.jdField_b_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection;
    } else {
      localChildServiceConnection = this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection;
    }
    if (!localChildServiceConnection.bind()) {
      return false;
    }
    d();
    this.jdField_d_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.bind();
    return true;
  }
  
  private void b()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    if (this.e) {
      return;
    }
    this.e = true;
    stop();
    ConnectionCallback localConnectionCallback = this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionCallback;
    if (localConnectionCallback != null)
    {
      localConnectionCallback.onConnected(null);
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionCallback = null;
    }
  }
  
  private void c()
  {
    try
    {
      TraceEvent.begin("ChildProcessConnection.doConnectionSetup");
      if ((!jdField_a_of_type_Boolean) && ((!this.jdField_d_of_type_Boolean) || (this.jdField_a_of_type_OrgChromiumBaseProcess_launcherIChildProcessService == null))) {
        throw new AssertionError();
      }
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionParams == null)) {
        throw new AssertionError();
      }
      ICallbackInt.Stub local3 = new ICallbackInt.Stub()
      {
        public void call(final int paramAnonymousInt)
        {
          ChildProcessConnection.a(ChildProcessConnection.this).post(new Runnable()
          {
            public void run()
            {
              ChildProcessConnection.a(ChildProcessConnection.this, paramAnonymousInt);
            }
          });
        }
      };
      try
      {
        this.jdField_a_of_type_OrgChromiumBaseProcess_launcherIChildProcessService.setupConnection(this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionParams.jdField_a_of_type_AndroidOsBundle, local3, this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionParams.jdField_a_of_type_JavaUtilList);
      }
      catch (RemoteException localRemoteException)
      {
        Log.e("ChildProcessConn", "Failed to setup connection.", new Object[] { localRemoteException });
      }
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionParams = null;
      return;
    }
    finally
    {
      TraceEvent.end("ChildProcessConnection.doConnectionSetup");
    }
  }
  
  private void d()
  {
    if (!this.g)
    {
      boolean bool;
      if ((!this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.isBound()) && (!this.jdField_b_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.isBound()) && (!this.jdField_c_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.isBound())) {
        bool = true;
      } else {
        bool = false;
      }
      this.f = bool;
    }
  }
  
  private void e()
  {
    ServiceCallback localServiceCallback = this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ServiceCallback;
    if (localServiceCallback != null)
    {
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ServiceCallback = null;
      localServiceCallback.onChildProcessDied(this);
    }
  }
  
  @VisibleForTesting
  protected void a()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherIChildProcessService = null;
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionParams = null;
    this.g = true;
    this.jdField_b_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.unbind();
    this.jdField_d_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.unbind();
    this.jdField_c_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.unbind();
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.unbind();
  }
  
  public void addInitialBinding()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.bind();
    d();
  }
  
  public void addModerateBinding()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    if (!isConnected()) {
      return;
    }
    if (this.jdField_c_of_type_Int == 0)
    {
      this.jdField_c_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.bind();
      d();
    }
    this.jdField_c_of_type_Int += 1;
  }
  
  public void addStrongBinding()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    if (!isConnected()) {
      return;
    }
    if (this.jdField_b_of_type_Int == 0)
    {
      this.jdField_b_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.bind();
      d();
    }
    this.jdField_b_of_type_Int += 1;
  }
  
  @VisibleForTesting
  public void crashServiceForTesting()
    throws RemoteException
  {
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherIChildProcessService.crashIntentionallyForTesting();
  }
  
  @VisibleForTesting
  public boolean didOnServiceConnectedForTesting()
  {
    return this.jdField_c_of_type_Boolean;
  }
  
  public int getPid()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    return this.jdField_a_of_type_Int;
  }
  
  public final IChildProcessService getService()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    return this.jdField_a_of_type_OrgChromiumBaseProcess_launcherIChildProcessService;
  }
  
  public final ComponentName getServiceName()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    return this.jdField_a_of_type_AndroidContentComponentName;
  }
  
  public boolean isConnected()
  {
    return this.jdField_a_of_type_OrgChromiumBaseProcess_launcherIChildProcessService != null;
  }
  
  public boolean isInitialBindingBound()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    return this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.isBound();
  }
  
  public boolean isModerateBindingBound()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    return this.jdField_c_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.isBound();
  }
  
  public boolean isStrongBindingBound()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    return this.jdField_b_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.isBound();
  }
  
  public boolean isWaivedBoundOnlyOrWasWhenDied()
  {
    return this.f;
  }
  
  public void removeInitialBinding()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.unbind();
    d();
  }
  
  public void removeModerateBinding()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    if (!isConnected()) {
      return;
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_c_of_type_Int <= 0)) {
      throw new AssertionError();
    }
    this.jdField_c_of_type_Int -= 1;
    if (this.jdField_c_of_type_Int == 0)
    {
      this.jdField_c_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.unbind();
      d();
    }
  }
  
  public void removeStrongBinding()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    if (!isConnected()) {
      return;
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_b_of_type_Int <= 0)) {
      throw new AssertionError();
    }
    this.jdField_b_of_type_Int -= 1;
    if (this.jdField_b_of_type_Int == 0)
    {
      this.jdField_b_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnection.unbind();
      d();
    }
  }
  
  public void setupConnection(Bundle paramBundle, List<IBinder> paramList, ConnectionCallback paramConnectionCallback)
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionParams != null)) {
      throw new AssertionError();
    }
    if (this.e)
    {
      paramConnectionCallback.onConnected(null);
      return;
    }
    try
    {
      TraceEvent.begin("ChildProcessConnection.setupConnection");
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionCallback = paramConnectionCallback;
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionParams = new ConnectionParams(paramBundle, paramList);
      if (this.jdField_d_of_type_Boolean) {
        c();
      }
      return;
    }
    finally
    {
      TraceEvent.end("ChildProcessConnection.setupConnection");
    }
  }
  
  public void start(boolean paramBoolean, ServiceCallback paramServiceCallback)
  {
    try
    {
      TraceEvent.begin("ChildProcessConnection.start");
      if ((!jdField_a_of_type_Boolean) && (!a())) {
        throw new AssertionError();
      }
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ConnectionParams != null)) {
        throw new AssertionError("setupConnection() called before start() in ChildProcessConnection.");
      }
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ServiceCallback = paramServiceCallback;
      if (!a(paramBoolean))
      {
        Log.e("ChildProcessConn", "Failed to establish the service connection.", new Object[0]);
        e();
      }
      return;
    }
    finally
    {
      TraceEvent.end("ChildProcessConnection.start");
    }
  }
  
  public void stop()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    a();
    e();
  }
  
  @VisibleForTesting
  protected static abstract interface ChildServiceConnection
  {
    public abstract boolean bind();
    
    public abstract boolean isBound();
    
    public abstract void unbind();
  }
  
  @VisibleForTesting
  protected static abstract interface ChildServiceConnectionDelegate
  {
    public abstract void onServiceConnected(IBinder paramIBinder);
    
    public abstract void onServiceDisconnected();
  }
  
  @VisibleForTesting
  protected static abstract interface ChildServiceConnectionFactory
  {
    public abstract ChildProcessConnection.ChildServiceConnection createConnection(Intent paramIntent, int paramInt, ChildProcessConnection.ChildServiceConnectionDelegate paramChildServiceConnectionDelegate);
  }
  
  private static class ChildServiceConnectionImpl
    implements ServiceConnection, ChildProcessConnection.ChildServiceConnection
  {
    private final int jdField_a_of_type_Int;
    private final Context jdField_a_of_type_AndroidContentContext;
    private final Intent jdField_a_of_type_AndroidContentIntent;
    private final ChildProcessConnection.ChildServiceConnectionDelegate jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnectionDelegate;
    private boolean jdField_a_of_type_Boolean;
    
    private ChildServiceConnectionImpl(Context paramContext, Intent paramIntent, int paramInt, ChildProcessConnection.ChildServiceConnectionDelegate paramChildServiceConnectionDelegate)
    {
      this.jdField_a_of_type_AndroidContentContext = paramContext;
      this.jdField_a_of_type_AndroidContentIntent = paramIntent;
      this.jdField_a_of_type_Int = paramInt;
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnectionDelegate = paramChildServiceConnectionDelegate;
    }
    
    /* Error */
    public boolean bind()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 38	org/chromium/base/process_launcher/ChildProcessConnection$ChildServiceConnectionImpl:jdField_a_of_type_Boolean	Z
      //   4: ifne +44 -> 48
      //   7: ldc 40
      //   9: invokestatic 46	org/chromium/base/TraceEvent:begin	(Ljava/lang/String;)V
      //   12: aload_0
      //   13: aload_0
      //   14: getfield 24	org/chromium/base/process_launcher/ChildProcessConnection$ChildServiceConnectionImpl:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
      //   17: aload_0
      //   18: getfield 26	org/chromium/base/process_launcher/ChildProcessConnection$ChildServiceConnectionImpl:jdField_a_of_type_AndroidContentIntent	Landroid/content/Intent;
      //   21: aload_0
      //   22: aload_0
      //   23: getfield 28	org/chromium/base/process_launcher/ChildProcessConnection$ChildServiceConnectionImpl:jdField_a_of_type_Int	I
      //   26: invokevirtual 52	android/content/Context:bindService	(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z
      //   29: putfield 38	org/chromium/base/process_launcher/ChildProcessConnection$ChildServiceConnectionImpl:jdField_a_of_type_Boolean	Z
      //   32: ldc 40
      //   34: invokestatic 55	org/chromium/base/TraceEvent:end	(Ljava/lang/String;)V
      //   37: goto +11 -> 48
      //   40: astore_1
      //   41: ldc 40
      //   43: invokestatic 55	org/chromium/base/TraceEvent:end	(Ljava/lang/String;)V
      //   46: aload_1
      //   47: athrow
      //   48: aload_0
      //   49: getfield 38	org/chromium/base/process_launcher/ChildProcessConnection$ChildServiceConnectionImpl:jdField_a_of_type_Boolean	Z
      //   52: ireturn
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	53	0	this	ChildServiceConnectionImpl
      //   40	7	1	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   7	32	40	finally
    }
    
    public boolean isBound()
    {
      return this.jdField_a_of_type_Boolean;
    }
    
    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnectionDelegate.onServiceConnected(paramIBinder);
    }
    
    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ChildServiceConnectionDelegate.onServiceDisconnected();
    }
    
    public void unbind()
    {
      if (this.jdField_a_of_type_Boolean)
      {
        this.jdField_a_of_type_AndroidContentContext.unbindService(this);
        this.jdField_a_of_type_Boolean = false;
      }
    }
  }
  
  public static abstract interface ConnectionCallback
  {
    public abstract void onConnected(ChildProcessConnection paramChildProcessConnection);
  }
  
  private static class ConnectionParams
  {
    final Bundle jdField_a_of_type_AndroidOsBundle;
    final List<IBinder> jdField_a_of_type_JavaUtilList;
    
    ConnectionParams(Bundle paramBundle, List<IBinder> paramList)
    {
      this.jdField_a_of_type_AndroidOsBundle = paramBundle;
      this.jdField_a_of_type_JavaUtilList = paramList;
    }
  }
  
  public static abstract interface ServiceCallback
  {
    public abstract void onChildProcessDied(ChildProcessConnection paramChildProcessConnection);
    
    public abstract void onChildStartFailed(ChildProcessConnection paramChildProcessConnection);
    
    public abstract void onChildStarted();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\process_launcher\ChildProcessConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */