package org.chromium.base;

public class NonThreadSafe
{
  private Long a;
  
  public NonThreadSafe()
  {
    a();
  }
  
  private void a()
  {
    if (this.a == null) {
      this.a = Long.valueOf(Thread.currentThread().getId());
    }
  }
  
  public boolean calledOnValidThread()
  {
    try
    {
      a();
      boolean bool = this.a.equals(Long.valueOf(Thread.currentThread().getId()));
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  @VisibleForTesting
  public void detachFromThread()
  {
    try
    {
      this.a = null;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\NonThreadSafe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */