package org.chromium.content.browser;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import org.chromium.base.Log;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.process_launcher.ChildConnectionAllocator;
import org.chromium.base.process_launcher.ChildProcessConnection;
import org.chromium.base.process_launcher.ChildProcessConnection.ServiceCallback;

public class SpareChildConnection
{
  private final ChildConnectionAllocator jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator;
  private ChildProcessConnection.ServiceCallback jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ServiceCallback;
  private ChildProcessConnection jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection;
  private boolean b;
  
  public SpareChildConnection(Context paramContext, ChildConnectionAllocator paramChildConnectionAllocator, Bundle paramBundle)
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator = paramChildConnectionAllocator;
    paramChildConnectionAllocator = new ChildProcessConnection.ServiceCallback()
    {
      public void onChildProcessDied(ChildProcessConnection paramAnonymousChildProcessConnection)
      {
        if (SpareChildConnection.a(SpareChildConnection.this) != null) {
          SpareChildConnection.a(SpareChildConnection.this).onChildProcessDied(paramAnonymousChildProcessConnection);
        }
        if (SpareChildConnection.a(SpareChildConnection.this) != null)
        {
          if ((!jdField_a_of_type_Boolean) && (paramAnonymousChildProcessConnection != SpareChildConnection.a(SpareChildConnection.this))) {
            throw new AssertionError();
          }
          SpareChildConnection.a(SpareChildConnection.this);
        }
      }
      
      public void onChildStartFailed(ChildProcessConnection paramAnonymousChildProcessConnection)
      {
        if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
          throw new AssertionError();
        }
        Log.e("SpareChildConn", "Failed to warm up the spare sandbox service", new Object[0]);
        if (SpareChildConnection.a(SpareChildConnection.this) != null) {
          SpareChildConnection.a(SpareChildConnection.this).onChildStartFailed(paramAnonymousChildProcessConnection);
        }
        SpareChildConnection.a(SpareChildConnection.this);
      }
      
      public void onChildStarted()
      {
        if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
          throw new AssertionError();
        }
        SpareChildConnection.a(SpareChildConnection.this, true);
        if (SpareChildConnection.a(SpareChildConnection.this) != null)
        {
          SpareChildConnection.a(SpareChildConnection.this).onChildStarted();
          SpareChildConnection.a(SpareChildConnection.this);
        }
      }
    };
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection = this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator.allocate(paramContext, paramBundle, paramChildConnectionAllocator);
  }
  
  private void a()
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection = null;
    this.b = false;
  }
  
  @VisibleForTesting
  public ChildProcessConnection getConnection()
  {
    return this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection;
  }
  
  public ChildProcessConnection getConnection(ChildConnectionAllocator paramChildConnectionAllocator, @NonNull final ChildProcessConnection.ServiceCallback paramServiceCallback)
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    if ((!isEmpty()) && (this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator == paramChildConnectionAllocator) && (this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ServiceCallback == null))
    {
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ServiceCallback = paramServiceCallback;
      paramChildConnectionAllocator = this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection;
      if (this.b)
      {
        if (paramServiceCallback != null) {
          LauncherThread.post(new Runnable()
          {
            public void run()
            {
              paramServiceCallback.onChildStarted();
            }
          });
        }
        a();
      }
      return paramChildConnectionAllocator;
    }
    return null;
  }
  
  public boolean isEmpty()
  {
    return (this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection == null) || (this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection$ServiceCallback != null);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\SpareChildConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */