package org.chromium.base.process_launcher;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import java.io.IOException;
import java.util.List;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.TraceEvent;

public class ChildProcessLauncher
{
  private final Handler jdField_a_of_type_AndroidOsHandler;
  private final List<IBinder> jdField_a_of_type_JavaUtilList;
  private final ChildConnectionAllocator jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator;
  private ChildProcessConnection jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection;
  private final Delegate jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher$Delegate;
  private final String[] jdField_a_of_type_ArrayOfJavaLangString;
  private final FileDescriptorInfo[] jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherFileDescriptorInfo;
  
  public ChildProcessLauncher(Handler paramHandler, Delegate paramDelegate, String[] paramArrayOfString, FileDescriptorInfo[] paramArrayOfFileDescriptorInfo, ChildConnectionAllocator paramChildConnectionAllocator, List<IBinder> paramList)
  {
    if ((!jdField_a_of_type_Boolean) && (paramChildConnectionAllocator == null)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_AndroidOsHandler = paramHandler;
    a();
    this.jdField_a_of_type_ArrayOfJavaLangString = paramArrayOfString;
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator = paramChildConnectionAllocator;
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher$Delegate = paramDelegate;
    this.jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherFileDescriptorInfo = paramArrayOfFileDescriptorInfo;
    this.jdField_a_of_type_JavaUtilList = paramList;
  }
  
  private Bundle a()
  {
    Bundle localBundle = new Bundle();
    localBundle.putStringArray("org.chromium.base.process_launcher.extra.command_line", this.jdField_a_of_type_ArrayOfJavaLangString);
    localBundle.putParcelableArray("org.chromium.base.process_launcher.extra.extraFiles", this.jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherFileDescriptorInfo);
    return localBundle;
  }
  
  private void a()
  {
    ChildProcessConnection.ConnectionCallback local3 = new ChildProcessConnection.ConnectionCallback()
    {
      public void onConnected(ChildProcessConnection paramAnonymousChildProcessConnection)
      {
        if ((!jdField_a_of_type_Boolean) && (ChildProcessLauncher.a(ChildProcessLauncher.this) != paramAnonymousChildProcessConnection)) {
          throw new AssertionError();
        }
        ChildProcessLauncher.b(ChildProcessLauncher.this);
      }
    };
    Bundle localBundle = a();
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher$Delegate.onBeforeConnectionSetup(localBundle);
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection.setupConnection(localBundle, getClientInterfaces(), local3);
  }
  
  private boolean a()
  {
    return this.jdField_a_of_type_AndroidOsHandler.getLooper() == Looper.myLooper();
  }
  
  private boolean a(final ChildProcessConnection.ServiceCallback paramServiceCallback, final boolean paramBoolean1, final boolean paramBoolean2)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection != null)) {
      throw new AssertionError();
    }
    Bundle localBundle = new Bundle();
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher$Delegate.onBeforeConnectionAllocated(localBundle);
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection = this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator.allocate(ContextUtils.getApplicationContext(), localBundle, paramServiceCallback);
    if (this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection == null)
    {
      if (!paramBoolean2) {
        return false;
      }
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator.addListener(new ChildConnectionAllocator.Listener()
      {
        public void onConnectionFreed(ChildConnectionAllocator paramAnonymousChildConnectionAllocator, ChildProcessConnection paramAnonymousChildProcessConnection)
        {
          if ((!jdField_a_of_type_Boolean) && (paramAnonymousChildConnectionAllocator != ChildProcessLauncher.a(ChildProcessLauncher.this))) {
            throw new AssertionError();
          }
          if (!paramAnonymousChildConnectionAllocator.isFreeConnectionAvailable()) {
            return;
          }
          paramAnonymousChildConnectionAllocator.removeListener(this);
          ChildProcessLauncher.a(ChildProcessLauncher.this, paramServiceCallback, paramBoolean1, paramBoolean2);
        }
      });
      return false;
    }
    if (paramBoolean1) {
      a();
    }
    return true;
  }
  
  private void b()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher$Delegate.onConnectionEstablished(this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection);
    try
    {
      FileDescriptorInfo[] arrayOfFileDescriptorInfo = this.jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherFileDescriptorInfo;
      int j = arrayOfFileDescriptorInfo.length;
      int i = 0;
      while (i < j)
      {
        arrayOfFileDescriptorInfo[i].fd.close();
        i += 1;
      }
      return;
    }
    catch (IOException localIOException) {}
  }
  
  private void c()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    if (getPid() != 0) {
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher$Delegate.onConnectionLost(this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection);
    }
  }
  
  public List<IBinder> getClientInterfaces()
  {
    return this.jdField_a_of_type_JavaUtilList;
  }
  
  public ChildProcessConnection getConnection()
  {
    return this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection;
  }
  
  public ChildConnectionAllocator getConnectionAllocator()
  {
    return this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator;
  }
  
  public int getPid()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    ChildProcessConnection localChildProcessConnection = this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection;
    if (localChildProcessConnection == null) {
      return 0;
    }
    return localChildProcessConnection.getPid();
  }
  
  public boolean start(final boolean paramBoolean1, final boolean paramBoolean2)
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    try
    {
      TraceEvent.begin("ChildProcessLauncher.start");
      ChildProcessConnection.ServiceCallback local1 = new ChildProcessConnection.ServiceCallback()
      {
        public void onChildProcessDied(ChildProcessConnection paramAnonymousChildProcessConnection)
        {
          if ((!jdField_a_of_type_Boolean) && (!ChildProcessLauncher.a(ChildProcessLauncher.this))) {
            throw new AssertionError();
          }
          if ((!jdField_a_of_type_Boolean) && (ChildProcessLauncher.a(ChildProcessLauncher.this) != paramAnonymousChildProcessConnection)) {
            throw new AssertionError();
          }
          ChildProcessLauncher.a(ChildProcessLauncher.this);
        }
        
        public void onChildStartFailed(ChildProcessConnection paramAnonymousChildProcessConnection)
        {
          if ((!jdField_a_of_type_Boolean) && (!ChildProcessLauncher.a(ChildProcessLauncher.this))) {
            throw new AssertionError();
          }
          if ((!jdField_a_of_type_Boolean) && (ChildProcessLauncher.a(ChildProcessLauncher.this) != paramAnonymousChildProcessConnection)) {
            throw new AssertionError();
          }
          Log.e("ChildProcLauncher", "ChildProcessConnection.start failed, trying again", new Object[0]);
          ChildProcessLauncher.a(ChildProcessLauncher.this).post(new Runnable()
          {
            public void run()
            {
              ChildProcessLauncher.a(ChildProcessLauncher.this, null);
              ChildProcessLauncher.this.start(ChildProcessLauncher.1.this.b, ChildProcessLauncher.1.this.c);
            }
          });
        }
        
        public void onChildStarted() {}
      };
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection = this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher$Delegate.getBoundConnection(this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator, local1);
      if (this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection != null)
      {
        if ((!jdField_a_of_type_Boolean) && (!this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator.isConnectionFromAllocator(this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection))) {
          throw new AssertionError();
        }
        a();
        return true;
      }
      paramBoolean1 = a(local1, paramBoolean1, paramBoolean2);
      return (paramBoolean1) || (paramBoolean2);
    }
    finally
    {
      TraceEvent.end("ChildProcessLauncher.start");
    }
  }
  
  public void stop()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection.stop();
  }
  
  public static abstract class Delegate
  {
    public ChildProcessConnection getBoundConnection(ChildConnectionAllocator paramChildConnectionAllocator, ChildProcessConnection.ServiceCallback paramServiceCallback)
    {
      return null;
    }
    
    public void onBeforeConnectionAllocated(Bundle paramBundle) {}
    
    public void onBeforeConnectionSetup(Bundle paramBundle) {}
    
    public void onConnectionEstablished(ChildProcessConnection paramChildProcessConnection) {}
    
    public void onConnectionLost(ChildProcessConnection paramChildProcessConnection) {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\process_launcher\ChildProcessLauncher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */