package com.tencent.smtt.processutil.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public final class Cgroup
  extends ProcFile
{
  public static final Parcelable.Creator<Cgroup> CREATOR = new Parcelable.Creator()
  {
    public Cgroup a(Parcel paramAnonymousParcel)
    {
      return new Cgroup(paramAnonymousParcel, null);
    }
    
    public Cgroup[] a(int paramAnonymousInt)
    {
      return new Cgroup[paramAnonymousInt];
    }
  };
  public final ArrayList<ControlGroup> a;
  
  private Cgroup(Parcel paramParcel)
  {
    super(paramParcel);
    this.jdField_a_of_type_JavaUtilArrayList = paramParcel.createTypedArrayList(ControlGroup.CREATOR);
  }
  
  private Cgroup(String paramString)
    throws IOException
  {
    super(paramString);
    paramString = this.jdField_a_of_type_JavaLangString.split("\n");
    this.jdField_a_of_type_JavaUtilArrayList = new ArrayList();
    int j = paramString.length;
    int i = 0;
    for (;;)
    {
      String str;
      if (i < j) {
        str = paramString[i];
      }
      try
      {
        this.jdField_a_of_type_JavaUtilArrayList.add(new ControlGroup(str));
        i += 1;
        continue;
        return;
      }
      catch (Exception localException)
      {
        for (;;) {}
      }
    }
  }
  
  public static Cgroup a(int paramInt)
    throws IOException
  {
    return new Cgroup(String.format("/proc/%d/cgroup", new Object[] { Integer.valueOf(paramInt) }));
  }
  
  public ControlGroup a(String paramString)
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
    while (localIterator.hasNext())
    {
      ControlGroup localControlGroup = (ControlGroup)localIterator.next();
      String[] arrayOfString = localControlGroup.jdField_a_of_type_JavaLangString.split(",");
      int j = arrayOfString.length;
      int i = 0;
      while (i < j)
      {
        if (arrayOfString[i].equals(paramString)) {
          return localControlGroup;
        }
        i += 1;
      }
    }
    return null;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeTypedList(this.jdField_a_of_type_JavaUtilArrayList);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\processutil\models\Cgroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */