package org.chromium.android_webview;

import java.util.Iterator;
import org.chromium.base.ObserverList;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public class AwContentsLifecycleNotifier
{
  private static int jdField_a_of_type_Int;
  private static final ObserverList<Observer> jdField_a_of_type_OrgChromiumBaseObserverList = new ObserverList();
  
  public static void addObserver(Observer paramObserver)
  {
    jdField_a_of_type_OrgChromiumBaseObserverList.addObserver(paramObserver);
  }
  
  public static boolean hasWebViewInstances()
  {
    return jdField_a_of_type_Int > 0;
  }
  
  @CalledByNative
  private static void onWebViewCreated()
  {
    
    if ((!jdField_a_of_type_Boolean) && (jdField_a_of_type_Int < 0)) {
      throw new AssertionError();
    }
    jdField_a_of_type_Int += 1;
    if (jdField_a_of_type_Int == 1)
    {
      Iterator localIterator = jdField_a_of_type_OrgChromiumBaseObserverList.iterator();
      while (localIterator.hasNext()) {
        ((Observer)localIterator.next()).onFirstWebViewCreated();
      }
    }
  }
  
  @CalledByNative
  private static void onWebViewDestroyed()
  {
    
    if ((!jdField_a_of_type_Boolean) && (jdField_a_of_type_Int <= 0)) {
      throw new AssertionError();
    }
    jdField_a_of_type_Int -= 1;
    if (jdField_a_of_type_Int == 0)
    {
      Iterator localIterator = jdField_a_of_type_OrgChromiumBaseObserverList.iterator();
      while (localIterator.hasNext()) {
        ((Observer)localIterator.next()).onLastWebViewDestroyed();
      }
    }
  }
  
  public static void removeObserver(Observer paramObserver)
  {
    jdField_a_of_type_OrgChromiumBaseObserverList.removeObserver(paramObserver);
  }
  
  public static abstract interface Observer
  {
    public abstract void onFirstWebViewCreated();
    
    public abstract void onLastWebViewDestroyed();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwContentsLifecycleNotifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */