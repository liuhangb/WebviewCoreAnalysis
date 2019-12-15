package com.tencent.common.utils;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.util.LruCache;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@TargetApi(14)
public class QBLruCache<K, V>
  extends LruCache<K, V>
{
  public QBLruCache(int paramInt)
  {
    super(paramInt);
  }
  
  public void trimToSize(int paramInt)
  {
    if (Build.VERSION.SDK_INT < 17)
    {
      Iterator localIterator = super.snapshot().entrySet().iterator();
      while (localIterator.hasNext())
      {
        if (size() <= paramInt) {
          return;
        }
        remove(((Map.Entry)localIterator.next()).getKey());
      }
    }
    super.trimToSize(paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\QBLruCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */