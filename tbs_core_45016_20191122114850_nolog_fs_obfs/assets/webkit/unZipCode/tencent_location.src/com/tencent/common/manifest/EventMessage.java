package com.tencent.common.manifest;

import java.util.Arrays;

public class EventMessage
{
  public final Object arg;
  public final int arg0;
  public final int arg1;
  public final Object[] args;
  public final String eventName;
  
  public EventMessage(String paramString)
  {
    this(paramString, 0, 0, null, null);
  }
  
  public EventMessage(String paramString, int paramInt1, int paramInt2)
  {
    this(paramString, paramInt1, paramInt2, null, null);
  }
  
  public EventMessage(String paramString, int paramInt1, int paramInt2, Object paramObject, Object[] paramArrayOfObject)
  {
    this.eventName = paramString;
    this.arg0 = paramInt1;
    this.arg1 = paramInt2;
    this.arg = paramObject;
    this.args = paramArrayOfObject;
  }
  
  public EventMessage(String paramString, Object paramObject)
  {
    this(paramString, 0, 0, paramObject, null);
  }
  
  public EventMessage(String paramString, Object... paramVarArgs)
  {
    this(paramString, 0, 0, null, paramVarArgs);
  }
  
  public String toString()
  {
    String str = this.eventName;
    int i = this.arg0;
    int j = this.arg1;
    Object localObject1 = this.arg;
    if (localObject1 == null) {
      localObject1 = "null";
    } else {
      localObject1 = localObject1.toString();
    }
    Object localObject2 = this.args;
    if (localObject2 == null) {
      localObject2 = "null";
    } else {
      localObject2 = Arrays.deepToString((Object[])localObject2);
    }
    return String.format("EventMessage{ eventName=%s, arg0=%d, arg1=%d, arg=%s, args=%s }", new Object[] { str, Integer.valueOf(i), Integer.valueOf(j), localObject1, localObject2 });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\manifest\EventMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */