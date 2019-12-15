package org.chromium.base;

import android.support.annotation.NonNull;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class CollectionUtil
{
  public static <T> void forEach(Collection<? extends T> paramCollection, Callback<T> paramCallback)
  {
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      paramCallback.onResult(paramCollection.next());
    }
  }
  
  public static <K, V> void forEach(Map<? extends K, ? extends V> paramMap, Callback<Map.Entry<K, V>> paramCallback)
  {
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext()) {
      paramCallback.onResult((Map.Entry)paramMap.next());
    }
  }
  
  public static int[] integerListToIntArray(@NonNull List<Integer> paramList)
  {
    int[] arrayOfInt = new int[paramList.size()];
    int i = 0;
    while (i < paramList.size())
    {
      arrayOfInt[i] = ((Integer)paramList.get(i)).intValue();
      i += 1;
    }
    return arrayOfInt;
  }
  
  public static long[] longListToLongArray(@NonNull List<Long> paramList)
  {
    long[] arrayOfLong = new long[paramList.size()];
    int i = 0;
    while (i < paramList.size())
    {
      arrayOfLong[i] = ((Long)paramList.get(i)).longValue();
      i += 1;
    }
    return arrayOfLong;
  }
  
  @VisibleForTesting
  public static <E> ArrayList<E> newArrayList(Iterable<E> paramIterable)
  {
    ArrayList localArrayList = new ArrayList();
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext()) {
      localArrayList.add(paramIterable.next());
    }
    return localArrayList;
  }
  
  @SafeVarargs
  public static <E> ArrayList<E> newArrayList(E... paramVarArgs)
  {
    ArrayList localArrayList = new ArrayList(paramVarArgs.length);
    Collections.addAll(localArrayList, paramVarArgs);
    return localArrayList;
  }
  
  @SafeVarargs
  public static <K, V> HashMap<K, V> newHashMap(Pair<? extends K, ? extends V>... paramVarArgs)
  {
    HashMap localHashMap = new HashMap();
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      Pair<? extends K, ? extends V> localPair = paramVarArgs[i];
      localHashMap.put(localPair.first, localPair.second);
      i += 1;
    }
    return localHashMap;
  }
  
  @SafeVarargs
  public static <E> HashSet<E> newHashSet(E... paramVarArgs)
  {
    HashSet localHashSet = new HashSet(paramVarArgs.length);
    Collections.addAll(localHashSet, paramVarArgs);
    return localHashSet;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\CollectionUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */