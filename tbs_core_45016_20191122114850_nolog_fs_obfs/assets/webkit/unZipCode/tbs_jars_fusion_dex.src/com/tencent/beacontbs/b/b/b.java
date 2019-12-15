package com.tencent.beacontbs.b.b;

import com.tencent.beacontbs.d.c;
import java.util.ArrayList;

public final class b
  extends c
  implements Cloneable
{
  private static ArrayList<a> b;
  public ArrayList<a> a = null;
  
  public final void a(com.tencent.beacontbs.d.a parama)
  {
    if (b == null)
    {
      b = new ArrayList();
      a locala = new a();
      b.add(locala);
    }
    this.a = ((ArrayList)parama.a(b, 0, true));
  }
  
  public final void a(com.tencent.beacontbs.d.b paramb)
  {
    paramb.a(this.a, 0);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\b\b\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */