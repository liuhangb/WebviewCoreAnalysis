package org.chromium.mojo.bindings;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;
import org.chromium.mojo.system.MojoException;

public class DelegatingConnectionErrorHandler
  implements ConnectionErrorHandler
{
  private final Set<ConnectionErrorHandler> a = Collections.newSetFromMap(new WeakHashMap());
  
  public void a(ConnectionErrorHandler paramConnectionErrorHandler)
  {
    this.a.add(paramConnectionErrorHandler);
  }
  
  public void onConnectionError(MojoException paramMojoException)
  {
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext()) {
      ((ConnectionErrorHandler)localIterator.next()).onConnectionError(paramMojoException);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\DelegatingConnectionErrorHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */