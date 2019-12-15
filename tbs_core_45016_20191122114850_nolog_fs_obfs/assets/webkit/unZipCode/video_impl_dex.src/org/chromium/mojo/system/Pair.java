package org.chromium.mojo.system;

public class Pair<F, S>
{
  public final F a;
  public final S b;
  
  public Pair(F paramF, S paramS)
  {
    this.a = paramF;
    this.b = paramS;
  }
  
  public static <A, B> Pair<A, B> a(A paramA, B paramB)
  {
    return new Pair(paramA, paramB);
  }
  
  private boolean a(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == null) {
      return paramObject2 == null;
    }
    return paramObject1.equals(paramObject2);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof Pair;
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    paramObject = (Pair)paramObject;
    bool1 = bool2;
    if (a(this.a, ((Pair)paramObject).a))
    {
      bool1 = bool2;
      if (a(this.b, ((Pair)paramObject).b)) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public int hashCode()
  {
    Object localObject = this.a;
    int j = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = localObject.hashCode();
    }
    localObject = this.b;
    if (localObject != null) {
      j = localObject.hashCode();
    }
    return i ^ j;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\Pair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */