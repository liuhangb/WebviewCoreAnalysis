package com.tencent.beacontbs.upload;

import android.content.Context;
import android.util.SparseArray;
import com.tencent.beacontbs.a.b.d;
import com.tencent.beacontbs.a.b.d.a;
import com.tencent.beacontbs.a.b.g;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class e
{
  private Context a = null;
  
  public e(Context paramContext)
  {
    this.a = paramContext;
  }
  
  public final void a(int paramInt, byte[] paramArrayOfByte, boolean paramBoolean)
  {
    int i = 0;
    int j = 0;
    if (paramInt != 101)
    {
      com.tencent.beacontbs.c.a.c("com strategy unmatch key: %d", new Object[] { Integer.valueOf(paramInt) });
      return;
    }
    d locald;
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0))
    {
      locald = com.tencent.beacontbs.a.b.b.a(this.a).a();
      if (locald == null)
      {
        com.tencent.beacontbs.c.a.c("imposible! common strategy null!", new Object[0]);
        return;
      }
    }
    for (;;)
    {
      int m;
      try
      {
        Object localObject1 = new com.tencent.beacontbs.b.c.a();
        ((com.tencent.beacontbs.b.c.a)localObject1).a(new com.tencent.beacontbs.d.a(paramArrayOfByte));
        Object localObject2;
        if (locald == null)
        {
          i = j;
        }
        else
        {
          if (((com.tencent.beacontbs.b.c.a)localObject1).jdField_a_of_type_JavaLangString.equals(locald.a())) {
            break label1172;
          }
          com.tencent.beacontbs.c.a.b("strategy url changed to: %s", new Object[] { ((com.tencent.beacontbs.b.c.a)localObject1).jdField_a_of_type_JavaLangString });
          locald.a(((com.tencent.beacontbs.b.c.a)localObject1).jdField_a_of_type_JavaLangString);
          k = 1;
          if (((com.tencent.beacontbs.b.c.a)localObject1).jdField_a_of_type_Int != locald.a())
          {
            com.tencent.beacontbs.c.a.b("QueryPeriod changed to: %d", new Object[] { Integer.valueOf(((com.tencent.beacontbs.b.c.a)localObject1).jdField_a_of_type_Int) });
            locald.a(((com.tencent.beacontbs.b.c.a)localObject1).jdField_a_of_type_Int);
            k = 1;
          }
          localObject2 = ((com.tencent.beacontbs.b.c.a)localObject1).jdField_a_of_type_JavaUtilArrayList;
          if (locald == null) {
            break label1241;
          }
          if (localObject2 != null)
          {
            localObject3 = locald.a();
            if (localObject3 == null) {
              break label1220;
            }
            m = 0;
            i = 0;
            if (m >= ((SparseArray)localObject3).size()) {
              break label1217;
            }
            d.a locala = (d.a)((SparseArray)localObject3).valueAt(m);
            Iterator localIterator = ((ArrayList)localObject2).iterator();
            if (!localIterator.hasNext()) {
              break label1208;
            }
            com.tencent.beacontbs.b.c.b localb = (com.tencent.beacontbs.b.c.b)localIterator.next();
            j = i;
            if (localb.jdField_a_of_type_Byte != locala.a()) {
              break label1201;
            }
            com.tencent.beacontbs.c.a.a("server module strategy mid: %d", new Object[] { Byte.valueOf(localb.jdField_a_of_type_Byte) });
            if (localb.jdField_b_of_type_Byte != 1) {
              break label1178;
            }
            bool = true;
            if (locala.a() == bool) {
              break label1184;
            }
            com.tencent.beacontbs.c.a.b("mid: %d , isUsable changed: %b ", new Object[] { Byte.valueOf(localb.jdField_a_of_type_Byte), Boolean.valueOf(bool) });
            locala.a(bool);
            j = 1;
            if (!locala.a().equals(localb.jdField_a_of_type_JavaLangString))
            {
              com.tencent.beacontbs.c.a.b("mid: %d , url changed: %s", new Object[] { Byte.valueOf(localb.jdField_a_of_type_Byte), localb.jdField_a_of_type_JavaLangString });
              locala.a(localb.jdField_a_of_type_JavaLangString);
              j = 1;
            }
            if (localb.jdField_a_of_type_Byte != 1)
            {
              i = j;
              if (localb.jdField_a_of_type_Byte != 2) {}
            }
            else if ((locala.a() != null) && (localb.jdField_a_of_type_JavaUtilMap != null))
            {
              i = j;
              if (!locala.a().equals(localb.jdField_a_of_type_JavaUtilMap))
              {
                com.tencent.beacontbs.c.a.b("mid: %d , detail changed...", new Object[] { Byte.valueOf(localb.jdField_a_of_type_Byte) });
                locala.a(localb.jdField_a_of_type_JavaUtilMap);
                com.tencent.beacontbs.a.b.b.a(this.a).a(localb.jdField_a_of_type_Byte, localb.jdField_a_of_type_JavaUtilMap);
                i = 1;
              }
            }
            else
            {
              i = j;
              if (localb.jdField_a_of_type_JavaUtilMap != null)
              {
                i = j;
                if (locala.a() == null)
                {
                  com.tencent.beacontbs.c.a.b("mid: %d , detail changed...", new Object[] { Byte.valueOf(localb.jdField_a_of_type_Byte) });
                  locala.a(localb.jdField_a_of_type_JavaUtilMap);
                  com.tencent.beacontbs.a.b.b.a(this.a).a(localb.jdField_a_of_type_Byte, localb.jdField_a_of_type_JavaUtilMap);
                  i = 1;
                }
              }
            }
            if (localb.jdField_a_of_type_Byte != 1) {
              break label1197;
            }
            if ((locala.a() != null) && (localb.jdField_a_of_type_JavaUtilArrayList != null)) {
              if (locala.a().equals(localb.jdField_a_of_type_JavaUtilArrayList)) {
                break label1191;
              }
            } else if (locala.a() == null) {
              if (localb.jdField_a_of_type_JavaUtilArrayList == null) {
                break label1191;
              }
            }
            com.tencent.beacontbs.c.a.b("mid: %d , PreventEventCode changed...", new Object[] { Byte.valueOf(localb.jdField_a_of_type_Byte) });
            locala.a(com.tencent.beacontbs.a.e.a(localb.jdField_a_of_type_JavaUtilArrayList));
            i = 1;
            if ((locala.b() != null) && (localb.jdField_b_of_type_JavaUtilArrayList != null))
            {
              j = i;
              if (locala.b().equals(localb.jdField_b_of_type_JavaUtilArrayList)) {
                break label1201;
              }
            }
            else if (locala.b() == null)
            {
              if (localb.jdField_b_of_type_JavaUtilArrayList == null) {
                break label1194;
              }
            }
            com.tencent.beacontbs.c.a.b("mid: %d , SampleEventSet changed...", new Object[] { Byte.valueOf(localb.jdField_a_of_type_Byte) });
            locala.b(com.tencent.beacontbs.a.e.a(localb.jdField_b_of_type_JavaUtilArrayList));
            j = 1;
            break label1201;
          }
          localObject2 = locald.a();
          if (localObject2 == null) {
            break label1238;
          }
          m = ((SparseArray)localObject2).size();
          j = 0;
          i = 0;
          if (j >= m) {
            break label1235;
          }
          Object localObject3 = (d.a)((SparseArray)localObject2).valueAt(j);
          if (!((d.a)localObject3).a()) {
            break label1226;
          }
          com.tencent.beacontbs.c.a.b("mid: %d , server not response strategy, sdk local close it!", new Object[] { Integer.valueOf(((d.a)localObject3).a()) });
          ((d.a)localObject3).a(false);
          i = 1;
          break label1226;
          localObject1 = ((com.tencent.beacontbs.b.c.a)localObject1).jdField_a_of_type_JavaUtilMap;
          if (locald == null)
          {
            i = 0;
            break label1255;
          }
          if ((localObject1 != null) && (locald.a() != null))
          {
            if (((Map)localObject1).equals(locald.a())) {
              break label1252;
            }
            locald.a((Map)localObject1);
            i = 1;
            break label1255;
          }
          if ((localObject1 == null) || (locald.a() != null)) {
            break label1252;
          }
          locald.a((Map)localObject1);
          i = 1;
          break label1255;
        }
        if (i != 0)
        {
          if (paramBoolean)
          {
            com.tencent.beacontbs.c.a.e("update common strategy should save ", new Object[0]);
            localObject1 = this.a;
            if (paramArrayOfByte != null)
            {
              localObject2 = new g();
              ((g)localObject2).a(paramInt);
              ((g)localObject2).a(paramArrayOfByte);
              com.tencent.beacontbs.a.e.a((Context)localObject1, (g)localObject2);
            }
          }
          com.tencent.beacontbs.c.a.e("com strategy changed notify! ", new Object[0]);
          paramArrayOfByte = com.tencent.beacontbs.a.b.b.a(this.a);
          localObject1 = paramArrayOfByte.a();
          if (localObject1 != null)
          {
            i = localObject1.length;
            paramInt = 0;
            if (paramInt < i)
            {
              localObject2 = localObject1[paramInt];
              try
              {
                ((com.tencent.beacontbs.a.b.e)localObject2).a(locald);
              }
              catch (Throwable localThrowable)
              {
                com.tencent.beacontbs.c.a.a(localThrowable);
                com.tencent.beacontbs.c.a.d("com strategy changed error %s", new Object[] { localThrowable.toString() });
              }
            }
          }
          if (paramBoolean) {
            paramArrayOfByte.a();
          }
        }
        return;
      }
      catch (Throwable paramArrayOfByte)
      {
        com.tencent.beacontbs.c.a.a(paramArrayOfByte);
        com.tencent.beacontbs.c.a.d("error to common strategy!", new Object[0]);
      }
      return;
      label1172:
      int k = 0;
      continue;
      label1178:
      boolean bool = false;
      continue;
      label1184:
      j = i;
      continue;
      label1191:
      continue;
      label1194:
      continue;
      label1197:
      j = i;
      label1201:
      i = j;
      continue;
      label1208:
      m += 1;
      continue;
      label1217:
      break label1241;
      label1220:
      i = 0;
      break label1241;
      label1226:
      j += 1;
      continue;
      label1235:
      break label1241;
      label1238:
      i = 0;
      label1241:
      if (i != 0)
      {
        k = 1;
        continue;
        label1252:
        i = 0;
        label1255:
        if (i != 0)
        {
          i = 1;
        }
        else
        {
          i = k;
          continue;
          paramInt += 1;
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\upload\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */