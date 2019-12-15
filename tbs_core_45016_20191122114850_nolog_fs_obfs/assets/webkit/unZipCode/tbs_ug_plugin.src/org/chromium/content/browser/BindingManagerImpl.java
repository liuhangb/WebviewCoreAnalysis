package org.chromium.content.browser;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.util.SparseArray;
import java.util.LinkedList;
import org.chromium.base.SysUtils;
import org.chromium.base.ThreadUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.metrics.RecordHistogram;
import org.chromium.base.process_launcher.ChildProcessConnection;

class BindingManagerImpl
  implements BindingManager
{
  private final SparseArray<ManagedConnection> jdField_a_of_type_AndroidUtilSparseArray = new SparseArray();
  private ModerateBindingPool jdField_a_of_type_OrgChromiumContentBrowserBindingManagerImpl$ModerateBindingPool;
  private final boolean b;
  private final boolean c;
  
  private BindingManagerImpl(boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    this.b = paramBoolean1;
    this.c = paramBoolean2;
  }
  
  public static BindingManagerImpl createBindingManager()
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    return new BindingManagerImpl(SysUtils.isLowEndDevice(), false);
  }
  
  public static BindingManagerImpl createBindingManagerForTesting(boolean paramBoolean)
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    return new BindingManagerImpl(paramBoolean, true);
  }
  
  public void addNewConnection(int paramInt, ChildProcessConnection paramChildProcessConnection)
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_AndroidUtilSparseArray.put(paramInt, new ManagedConnection(paramChildProcessConnection));
  }
  
  @VisibleForTesting
  public boolean isConnectionCleared(int paramInt)
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    return this.jdField_a_of_type_AndroidUtilSparseArray.get(paramInt) == null;
  }
  
  public void onBroughtToForeground()
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    ModerateBindingPool localModerateBindingPool = this.jdField_a_of_type_OrgChromiumContentBrowserBindingManagerImpl$ModerateBindingPool;
    if (localModerateBindingPool != null) {
      localModerateBindingPool.b();
    }
  }
  
  public void onSentToBackground()
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    ModerateBindingPool localModerateBindingPool = this.jdField_a_of_type_OrgChromiumContentBrowserBindingManagerImpl$ModerateBindingPool;
    if (localModerateBindingPool != null) {
      localModerateBindingPool.a(this.c);
    }
  }
  
  public void releaseAllModerateBindings()
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    ModerateBindingPool localModerateBindingPool = this.jdField_a_of_type_OrgChromiumContentBrowserBindingManagerImpl$ModerateBindingPool;
    if (localModerateBindingPool != null) {
      localModerateBindingPool.a();
    }
  }
  
  public void removeConnection(int paramInt)
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    ManagedConnection localManagedConnection = (ManagedConnection)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramInt);
    if (localManagedConnection == null) {
      return;
    }
    this.jdField_a_of_type_AndroidUtilSparseArray.remove(paramInt);
    ModerateBindingPool localModerateBindingPool = this.jdField_a_of_type_OrgChromiumContentBrowserBindingManagerImpl$ModerateBindingPool;
    if (localModerateBindingPool != null) {
      localModerateBindingPool.b(localManagedConnection);
    }
  }
  
  public void setPriority(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    ManagedConnection localManagedConnection = (ManagedConnection)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramInt);
    if (localManagedConnection == null) {
      return;
    }
    localManagedConnection.a(paramBoolean1, paramBoolean2);
  }
  
  public void startModerateBindingManagement(Context paramContext, int paramInt)
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    if (this.b) {
      return;
    }
    if (this.jdField_a_of_type_OrgChromiumContentBrowserBindingManagerImpl$ModerateBindingPool == null)
    {
      this.jdField_a_of_type_OrgChromiumContentBrowserBindingManagerImpl$ModerateBindingPool = new ModerateBindingPool(paramInt);
      if (paramContext != null) {
        paramContext.registerComponentCallbacks(this.jdField_a_of_type_OrgChromiumContentBrowserBindingManagerImpl$ModerateBindingPool);
      }
    }
  }
  
  private class ManagedConnection
  {
    private final ChildProcessConnection jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection;
    private boolean jdField_a_of_type_Boolean;
    private boolean b = true;
    
    ManagedConnection(ChildProcessConnection paramChildProcessConnection)
    {
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection = paramChildProcessConnection;
    }
    
    private void a()
    {
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection.addStrongBinding();
      if (BindingManagerImpl.a(BindingManagerImpl.this) != null) {
        BindingManagerImpl.a(BindingManagerImpl.this).b(this);
      }
    }
    
    private void a(ChildProcessConnection paramChildProcessConnection)
    {
      if ((BindingManagerImpl.a(BindingManagerImpl.this) != null) && (!paramChildProcessConnection.isStrongBindingBound())) {
        BindingManagerImpl.a(BindingManagerImpl.this).a(this);
      }
    }
    
    private void a(boolean paramBoolean)
    {
      if (!this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection.isStrongBindingBound()) {
        return;
      }
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection.removeStrongBinding();
      if (paramBoolean) {
        a(this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection);
      }
    }
    
    private void b()
    {
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection.addModerateBinding();
    }
    
    void a(boolean paramBoolean1, boolean paramBoolean2)
    {
      if ((!this.jdField_a_of_type_Boolean) && (paramBoolean1)) {
        a();
      }
      if ((!this.b) && (paramBoolean2)) {
        this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection.addInitialBinding();
      }
      if ((this.jdField_a_of_type_Boolean) && (!paramBoolean1)) {
        a(true);
      }
      if ((this.b) && (!paramBoolean2))
      {
        a(this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection);
        this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessConnection.removeInitialBinding();
      }
      this.jdField_a_of_type_Boolean = paramBoolean1;
      this.b = paramBoolean2;
    }
  }
  
  private static class ModerateBindingPool
    implements ComponentCallbacks2
  {
    private final int jdField_a_of_type_Int;
    private Runnable jdField_a_of_type_JavaLangRunnable;
    private final LinkedList<BindingManagerImpl.ManagedConnection> jdField_a_of_type_JavaUtilLinkedList = new LinkedList();
    
    public ModerateBindingPool(int paramInt)
    {
      this.jdField_a_of_type_Int = paramInt;
    }
    
    private void a(float paramFloat)
    {
      int i = this.jdField_a_of_type_JavaUtilLinkedList.size();
      int j = (int)(i * (1.0F - paramFloat));
      a(i - j);
      if (!jdField_a_of_type_Boolean)
      {
        if (this.jdField_a_of_type_JavaUtilLinkedList.size() == j) {
          return;
        }
        throw new AssertionError();
      }
    }
    
    private void a(int paramInt)
    {
      if ((!jdField_a_of_type_Boolean) && (paramInt > this.jdField_a_of_type_JavaUtilLinkedList.size())) {
        throw new AssertionError();
      }
      int i = 0;
      while (i < paramInt)
      {
        BindingManagerImpl.ManagedConnection.a((BindingManagerImpl.ManagedConnection)this.jdField_a_of_type_JavaUtilLinkedList.removeLast()).removeModerateBinding();
        i += 1;
      }
    }
    
    private void c(BindingManagerImpl.ManagedConnection paramManagedConnection)
    {
      this.jdField_a_of_type_JavaUtilLinkedList.removeFirstOccurrence(paramManagedConnection);
      if (this.jdField_a_of_type_JavaUtilLinkedList.size() == this.jdField_a_of_type_Int) {
        a(1);
      }
      this.jdField_a_of_type_JavaUtilLinkedList.add(0, paramManagedConnection);
      if (!jdField_a_of_type_Boolean)
      {
        if (this.jdField_a_of_type_JavaUtilLinkedList.size() <= this.jdField_a_of_type_Int) {
          return;
        }
        throw new AssertionError();
      }
    }
    
    private void d(BindingManagerImpl.ManagedConnection paramManagedConnection)
    {
      int i = this.jdField_a_of_type_JavaUtilLinkedList.indexOf(paramManagedConnection);
      if (i != -1) {
        BindingManagerImpl.ManagedConnection.a((BindingManagerImpl.ManagedConnection)this.jdField_a_of_type_JavaUtilLinkedList.remove(i)).removeModerateBinding();
      }
    }
    
    void a()
    {
      a(this.jdField_a_of_type_JavaUtilLinkedList.size());
    }
    
    void a(BindingManagerImpl.ManagedConnection paramManagedConnection)
    {
      if (!this.jdField_a_of_type_JavaUtilLinkedList.contains(paramManagedConnection)) {
        BindingManagerImpl.ManagedConnection.a(paramManagedConnection);
      }
      c(paramManagedConnection);
    }
    
    void a(final boolean paramBoolean)
    {
      if (this.jdField_a_of_type_JavaUtilLinkedList.isEmpty()) {
        return;
      }
      this.jdField_a_of_type_JavaLangRunnable = new Runnable()
      {
        public void run()
        {
          if (!paramBoolean) {
            RecordHistogram.recordCountHistogram("Android.ModerateBindingCount", BindingManagerImpl.ModerateBindingPool.a(BindingManagerImpl.ModerateBindingPool.this).size());
          }
          BindingManagerImpl.ModerateBindingPool.this.a();
        }
      };
      LauncherThread.postDelayed(this.jdField_a_of_type_JavaLangRunnable, 10000L);
    }
    
    void b()
    {
      Runnable localRunnable = this.jdField_a_of_type_JavaLangRunnable;
      if (localRunnable != null)
      {
        LauncherThread.removeCallbacks(localRunnable);
        this.jdField_a_of_type_JavaLangRunnable = null;
      }
    }
    
    void b(BindingManagerImpl.ManagedConnection paramManagedConnection)
    {
      d(paramManagedConnection);
    }
    
    public void onConfigurationChanged(Configuration paramConfiguration) {}
    
    public void onLowMemory()
    {
      ThreadUtils.assertOnUiThread();
      LauncherThread.post(new Runnable()
      {
        public void run()
        {
          BindingManagerImpl.ModerateBindingPool.this.a();
        }
      });
    }
    
    public void onTrimMemory(final int paramInt)
    {
      ThreadUtils.assertOnUiThread();
      LauncherThread.post(new Runnable()
      {
        public void run()
        {
          if (BindingManagerImpl.ModerateBindingPool.a(BindingManagerImpl.ModerateBindingPool.this).isEmpty()) {
            return;
          }
          int i = paramInt;
          if (i <= 5)
          {
            BindingManagerImpl.ModerateBindingPool.a(BindingManagerImpl.ModerateBindingPool.this, 0.25F);
            return;
          }
          if (i <= 10)
          {
            BindingManagerImpl.ModerateBindingPool.a(BindingManagerImpl.ModerateBindingPool.this, 0.5F);
            return;
          }
          if (i == 20) {
            return;
          }
          BindingManagerImpl.ModerateBindingPool.this.a();
        }
      });
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\BindingManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */