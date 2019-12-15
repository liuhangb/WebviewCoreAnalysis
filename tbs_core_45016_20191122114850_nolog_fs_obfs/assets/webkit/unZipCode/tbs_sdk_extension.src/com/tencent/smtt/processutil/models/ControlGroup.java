package com.tencent.smtt.processutil.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ControlGroup
  implements Parcelable
{
  public static final Parcelable.Creator<ControlGroup> CREATOR = new Parcelable.Creator()
  {
    public ControlGroup a(Parcel paramAnonymousParcel)
    {
      return new ControlGroup(paramAnonymousParcel);
    }
    
    public ControlGroup[] a(int paramAnonymousInt)
    {
      return new ControlGroup[paramAnonymousInt];
    }
  };
  public final int a;
  public final String a;
  public final String b;
  
  protected ControlGroup(Parcel paramParcel)
  {
    this.jdField_a_of_type_Int = paramParcel.readInt();
    this.jdField_a_of_type_JavaLangString = paramParcel.readString();
    this.b = paramParcel.readString();
  }
  
  protected ControlGroup(String paramString)
    throws NumberFormatException, IndexOutOfBoundsException
  {
    paramString = paramString.split(":");
    this.jdField_a_of_type_Int = Integer.parseInt(paramString[0]);
    this.jdField_a_of_type_JavaLangString = paramString[1];
    this.b = paramString[2];
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String toString()
  {
    return String.format("%d:%s:%s", new Object[] { Integer.valueOf(this.jdField_a_of_type_Int), this.jdField_a_of_type_JavaLangString, this.b });
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.jdField_a_of_type_Int);
    paramParcel.writeString(this.jdField_a_of_type_JavaLangString);
    paramParcel.writeString(this.b);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\processutil\models\ControlGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */