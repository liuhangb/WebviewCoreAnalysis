package com.tencent.common.manifest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

public class AppManifest
{
  private static AppManifest jdField_a_of_type_ComTencentCommonManifestAppManifest;
  HashMap<String, Implementation> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  HashSet<ModuleManifest> jdField_a_of_type_JavaUtilHashSet = new HashSet();
  HashMap<String, HashSet<Implementation>> b = new HashMap();
  HashMap<String, ArrayList<EventReceiverImpl>> c = new HashMap();
  HashMap<Class<?>, Object> d = new HashMap();
  HashMap<Class<?>, HashSet<Implementation>> e = new HashMap();
  
  private HashSet<Implementation> a(Class<?> paramClass)
  {
    HashSet localHashSet2 = (HashSet)this.e.get(paramClass);
    HashSet localHashSet1 = localHashSet2;
    if (localHashSet2 == null)
    {
      localHashSet2 = (HashSet)this.b.get(paramClass.getName());
      localHashSet1 = localHashSet2;
      if (localHashSet2 != null)
      {
        this.e.put(paramClass, localHashSet2);
        localHashSet1 = localHashSet2;
      }
    }
    return localHashSet1;
  }
  
  private void a(ModuleManifest paramModuleManifest)
  {
    if (this.jdField_a_of_type_JavaUtilHashSet.contains(paramModuleManifest)) {
      return;
    }
    this.jdField_a_of_type_JavaUtilHashSet.add(paramModuleManifest);
    Object localObject1 = paramModuleManifest.serviceImpl();
    int j = 0;
    int k;
    int i;
    if ((localObject1 != null) && (localObject1.length > 0))
    {
      k = localObject1.length;
      i = 0;
      while (i < k)
      {
        localObject2 = localObject1[i];
        if (!this.jdField_a_of_type_JavaUtilHashMap.containsKey(((Implementation)localObject2).a)) {
          this.jdField_a_of_type_JavaUtilHashMap.put(((Implementation)localObject2).a, localObject2);
        }
        i += 1;
      }
    }
    Implementation[] arrayOfImplementation = paramModuleManifest.extensionImpl();
    if ((arrayOfImplementation != null) && (arrayOfImplementation.length > 0))
    {
      k = arrayOfImplementation.length;
      i = 0;
      while (i < k)
      {
        Implementation localImplementation = arrayOfImplementation[i];
        localObject2 = (HashSet)this.b.get(localImplementation.a);
        localObject1 = localObject2;
        if (localObject2 == null)
        {
          localObject1 = new HashSet();
          this.b.put(localImplementation.a, localObject1);
        }
        ((HashSet)localObject1).add(localImplementation);
        i += 1;
      }
    }
    Object localObject2 = paramModuleManifest.eventReceivers();
    if ((localObject2 != null) && (localObject2.length > 0))
    {
      k = localObject2.length;
      i = j;
      while (i < k)
      {
        arrayOfImplementation = localObject2[i];
        localObject1 = (ArrayList)this.c.get(arrayOfImplementation.a);
        paramModuleManifest = (ModuleManifest)localObject1;
        if (localObject1 == null)
        {
          paramModuleManifest = new ArrayList();
          this.c.put(arrayOfImplementation.a, paramModuleManifest);
        }
        paramModuleManifest.add(arrayOfImplementation);
        i += 1;
      }
    }
  }
  
  public static AppManifest getInstance()
  {
    if (jdField_a_of_type_ComTencentCommonManifestAppManifest == null) {
      try
      {
        if (jdField_a_of_type_ComTencentCommonManifestAppManifest == null) {
          jdField_a_of_type_ComTencentCommonManifestAppManifest = new AppManifest();
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentCommonManifestAppManifest;
  }
  
  public void addModules(ModuleManifest... paramVarArgs)
  {
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      ModuleManifest localModuleManifest = paramVarArgs[i];
      if (localModuleManifest != null) {
        a(localModuleManifest);
      }
      i += 1;
    }
    paramVarArgs = this.c.values().iterator();
    while (paramVarArgs.hasNext()) {
      Collections.sort((ArrayList)paramVarArgs.next(), new Comparator()
      {
        public int a(EventReceiverImpl paramAnonymousEventReceiverImpl1, EventReceiverImpl paramAnonymousEventReceiverImpl2)
        {
          return paramAnonymousEventReceiverImpl2.a - paramAnonymousEventReceiverImpl1.a;
        }
      });
    }
  }
  
  public void addModules(String... paramVarArgs)
  {
    ModuleManifest[] arrayOfModuleManifest = new ModuleManifest[paramVarArgs.length];
    int i = 0;
    while (i < paramVarArgs.length)
    {
      String str = paramVarArgs[i];
      if ((str != null) && (!str.trim().isEmpty())) {
        try
        {
          arrayOfModuleManifest[i] = ((ModuleManifest)Class.forName(str).newInstance());
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
        }
      }
      i += 1;
    }
    addModules(arrayOfModuleManifest);
  }
  
  public <T> Iterable<Map.Entry<String[], T>> hintExtensions(Class<T> paramClass)
  {
    return new ExtensionHint.HintIterable(a(paramClass));
  }
  
  public EventReceiverImpl[] queryEventReceivers(String paramString)
  {
    paramString = (ArrayList)this.c.get(paramString);
    if (paramString == null) {
      return new EventReceiverImpl[0];
    }
    return (EventReceiverImpl[])paramString.toArray(new EventReceiverImpl[paramString.size()]);
  }
  
  public <T> T queryExtension(Class<T> paramClass, Object paramObject)
  {
    paramClass = queryExtensions(paramClass, paramObject, 1);
    if (paramClass.length == 0) {
      return null;
    }
    return paramClass[0];
  }
  
  public <T> T[] queryExtensions(Class<T> paramClass)
  {
    return queryExtensions(paramClass, null);
  }
  
  public <T> T[] queryExtensions(Class<T> paramClass, Object paramObject)
  {
    return queryExtensions(paramClass, paramObject, 0);
  }
  
  public <T> T[] queryExtensions(Class<T> paramClass, Object paramObject, int paramInt)
  {
    Object localObject1 = a(paramClass);
    ArrayList localArrayList = new ArrayList();
    if (localObject1 != null)
    {
      localObject1 = ((HashSet)localObject1).iterator();
      do
      {
        if (!((Iterator)localObject1).hasNext()) {
          break;
        }
        Object localObject2 = ((Implementation)((Iterator)localObject1).next()).query(paramObject);
        if (localObject2 != null) {
          localArrayList.add(localObject2);
        }
      } while ((paramInt <= 0) || (localArrayList.size() < paramInt));
    }
    return localArrayList.toArray((Object[])Array.newInstance(paramClass, localArrayList.size()));
  }
  
  public <T> T queryService(Class<T> paramClass)
  {
    Object localObject = this.d.get(paramClass);
    if (localObject == null)
    {
      localObject = (Implementation)this.jdField_a_of_type_JavaUtilHashMap.get(paramClass.getName());
      if (localObject != null)
      {
        localObject = ((Implementation)localObject).query();
        if (localObject != null)
        {
          this.d.put(paramClass, localObject);
          return (T)localObject;
        }
      }
      return null;
    }
    return (T)localObject;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\manifest\AppManifest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */