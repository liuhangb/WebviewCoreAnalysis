package com.tencent.common.utils;

public class HashCodeUtil
{
  public static int hashCode(int paramInt)
  {
    return paramInt + 31;
  }
  
  public static int hashCode(int paramInt1, int paramInt2)
  {
    return (paramInt1 + 31) * 31 + paramInt2;
  }
  
  public static int hashCode(int paramInt1, int paramInt2, int paramInt3)
  {
    return ((paramInt1 + 31) * 31 + paramInt2) * 31 + paramInt3;
  }
  
  public static int hashCode(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return (((paramInt1 + 31) * 31 + paramInt2) * 31 + paramInt3) * 31 + paramInt4;
  }
  
  public static int hashCode(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    return ((((paramInt1 + 31) * 31 + paramInt2) * 31 + paramInt3) * 31 + paramInt4) * 31 + paramInt5;
  }
  
  public static int hashCode(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    return (((((paramInt1 + 31) * 31 + paramInt2) * 31 + paramInt3) * 31 + paramInt4) * 31 + paramInt5) * 31 + paramInt6;
  }
  
  public static int hashCode(Object paramObject)
  {
    int i;
    if (paramObject == null) {
      i = 0;
    } else {
      i = paramObject.hashCode();
    }
    return hashCode(i);
  }
  
  public static int hashCode(Object paramObject1, Object paramObject2)
  {
    int j = 0;
    int i;
    if (paramObject1 == null) {
      i = 0;
    } else {
      i = paramObject1.hashCode();
    }
    if (paramObject2 != null) {
      j = paramObject2.hashCode();
    }
    return hashCode(i, j);
  }
  
  public static int hashCode(Object paramObject1, Object paramObject2, Object paramObject3)
  {
    int k = 0;
    int i;
    if (paramObject1 == null) {
      i = 0;
    } else {
      i = paramObject1.hashCode();
    }
    int j;
    if (paramObject2 == null) {
      j = 0;
    } else {
      j = paramObject2.hashCode();
    }
    if (paramObject3 != null) {
      k = paramObject3.hashCode();
    }
    return hashCode(i, j, k);
  }
  
  public static int hashCode(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4)
  {
    int m = 0;
    int i;
    if (paramObject1 == null) {
      i = 0;
    } else {
      i = paramObject1.hashCode();
    }
    int j;
    if (paramObject2 == null) {
      j = 0;
    } else {
      j = paramObject2.hashCode();
    }
    int k;
    if (paramObject3 == null) {
      k = 0;
    } else {
      k = paramObject3.hashCode();
    }
    if (paramObject4 != null) {
      m = paramObject4.hashCode();
    }
    return hashCode(i, j, k, m);
  }
  
  public static int hashCode(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5)
  {
    int n = 0;
    int i;
    if (paramObject1 == null) {
      i = 0;
    } else {
      i = paramObject1.hashCode();
    }
    int j;
    if (paramObject2 == null) {
      j = 0;
    } else {
      j = paramObject2.hashCode();
    }
    int k;
    if (paramObject3 == null) {
      k = 0;
    } else {
      k = paramObject3.hashCode();
    }
    int m;
    if (paramObject4 == null) {
      m = 0;
    } else {
      m = paramObject4.hashCode();
    }
    if (paramObject5 != null) {
      n = paramObject5.hashCode();
    }
    return hashCode(i, j, k, m, n);
  }
  
  public static int hashCode(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6)
  {
    int i;
    if (paramObject1 == null) {
      i = 0;
    } else {
      i = paramObject1.hashCode();
    }
    int j;
    if (paramObject2 == null) {
      j = 0;
    } else {
      j = paramObject2.hashCode();
    }
    int k;
    if (paramObject3 == null) {
      k = 0;
    } else {
      k = paramObject3.hashCode();
    }
    int m;
    if (paramObject4 == null) {
      m = 0;
    } else {
      m = paramObject4.hashCode();
    }
    int n;
    if (paramObject5 == null) {
      n = 0;
    } else {
      n = paramObject5.hashCode();
    }
    int i1;
    if (paramObject6 == null) {
      i1 = 0;
    } else {
      i1 = paramObject6.hashCode();
    }
    return hashCode(i, j, k, m, n, i1);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\HashCodeUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */