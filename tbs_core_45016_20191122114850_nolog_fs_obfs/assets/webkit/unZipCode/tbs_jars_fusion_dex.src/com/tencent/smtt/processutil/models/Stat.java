package com.tencent.smtt.processutil.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.io.IOException;

public final class Stat
  extends ProcFile
{
  public static final Parcelable.Creator<Stat> CREATOR = new Parcelable.Creator()
  {
    public Stat a(Parcel paramAnonymousParcel)
    {
      return new Stat(paramAnonymousParcel, null);
    }
    
    public Stat[] a(int paramAnonymousInt)
    {
      return new Stat[paramAnonymousInt];
    }
  };
  private final String[] a;
  
  private Stat(Parcel paramParcel)
  {
    super(paramParcel);
    this.jdField_a_of_type_ArrayOfJavaLangString = paramParcel.createStringArray();
  }
  
  private Stat(String paramString)
    throws IOException
  {
    super(paramString);
    this.jdField_a_of_type_ArrayOfJavaLangString = this.jdField_a_of_type_JavaLangString.split("\\s+");
  }
  
  public static Stat a(int paramInt)
    throws IOException
  {
    return new Stat(String.format("/proc/%d/stat", new Object[] { Integer.valueOf(paramInt) }));
  }
  
  public int a()
  {
    return Integer.parseInt(this.jdField_a_of_type_ArrayOfJavaLangString[40]);
  }
  
  public String a()
  {
    return this.jdField_a_of_type_ArrayOfJavaLangString[1].replace("(", "").replace(")", "");
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeStringArray(this.jdField_a_of_type_ArrayOfJavaLangString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\processutil\models\Stat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */