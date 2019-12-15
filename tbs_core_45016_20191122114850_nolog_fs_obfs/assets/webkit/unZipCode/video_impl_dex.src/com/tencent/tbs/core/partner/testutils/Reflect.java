package com.tencent.tbs.core.partner.testutils;

import java.lang.reflect.Field;

class Reflect
{
  private Object object;
  
  public Reflect(Object paramObject)
  {
    if (paramObject != null)
    {
      this.object = paramObject;
      return;
    }
    throw new IllegalArgumentException("Object can not be null.");
  }
  
  public FieldRf field(String paramString)
  {
    return new FieldRf(this.object, paramString);
  }
  
  public class FieldRf
  {
    private Class<?> clazz;
    private String name;
    private Object object;
    
    public FieldRf(Object paramObject, String paramString)
    {
      this.object = paramObject;
      this.name = paramString;
    }
    
    private Field getField()
    {
      if (this.clazz == null) {
        this.clazz = this.object.getClass();
      }
      Object localObject = null;
      try
      {
        Field localField = this.clazz.getDeclaredField(this.name);
        localObject = localField;
        localField.setAccessible(true);
        return localField;
      }
      catch (NoSuchFieldException localNoSuchFieldException) {}
      return (Field)localObject;
    }
    
    private Object getValue(Field paramField)
    {
      if (paramField == null) {
        return null;
      }
      try
      {
        paramField = paramField.get(this.object);
        return paramField;
      }
      catch (IllegalAccessException paramField)
      {
        paramField.printStackTrace();
        return null;
      }
      catch (IllegalArgumentException paramField)
      {
        paramField.printStackTrace();
      }
      return null;
    }
    
    public void in(Object paramObject)
    {
      Field localField = getField();
      if (localField != null) {
        try
        {
          localField.set(this.object, paramObject);
          return;
        }
        catch (IllegalAccessException paramObject)
        {
          ((IllegalAccessException)paramObject).printStackTrace();
          return;
        }
        catch (IllegalArgumentException paramObject)
        {
          ((IllegalArgumentException)paramObject).printStackTrace();
        }
      }
    }
    
    public <T> T out(Class<T> paramClass)
    {
      return (T)paramClass.cast(getValue(getField()));
    }
    
    public FieldRf type(Class<?> paramClass)
    {
      this.clazz = paramClass;
      return this;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\testutils\Reflect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */