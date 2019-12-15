package org.chromium.mojo.bindings;

public abstract interface Callbacks
{
  public static abstract interface Callback0
  {
    public abstract void call();
  }
  
  public static abstract interface Callback1<T1>
  {
    public abstract void call(T1 paramT1);
  }
  
  public static abstract interface Callback2<T1, T2>
  {
    public abstract void call(T1 paramT1, T2 paramT2);
  }
  
  public static abstract interface Callback3<T1, T2, T3>
  {
    public abstract void call(T1 paramT1, T2 paramT2, T3 paramT3);
  }
  
  public static abstract interface Callback4<T1, T2, T3, T4>
  {
    public abstract void call(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  }
  
  public static abstract interface Callback5<T1, T2, T3, T4, T5>
  {
    public abstract void call(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  }
  
  public static abstract interface Callback6<T1, T2, T3, T4, T5, T6>
  {
    public abstract void call(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  }
  
  public static abstract interface Callback7<T1, T2, T3, T4, T5, T6, T7>
  {
    public abstract void call(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\Callbacks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */