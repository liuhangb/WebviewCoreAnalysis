package com.tencent.smtt.processutil.models;

import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.tencent.smtt.processutil.a;
import java.io.File;
import java.io.IOException;

public class AndroidAppProcess
  extends AndroidProcess
{
  public static final Parcelable.Creator<AndroidAppProcess> CREATOR = new Parcelable.Creator()
  {
    public AndroidAppProcess a(Parcel paramAnonymousParcel)
    {
      return new AndroidAppProcess(paramAnonymousParcel);
    }
    
    public AndroidAppProcess[] a(int paramAnonymousInt)
    {
      return new AndroidAppProcess[paramAnonymousInt];
    }
  };
  private static final boolean b = new File("/dev/cpuctl/tasks").exists();
  public final int a;
  public final boolean a;
  
  public AndroidAppProcess(int paramInt)
    throws IOException, AndroidAppProcess.NotAndroidAppProcessException
  {
    super(paramInt);
    Object localObject2;
    Object localObject1;
    boolean bool;
    if (b)
    {
      localObject2 = a();
      localObject1 = ((Cgroup)localObject2).a("cpuacct");
      localObject2 = ((Cgroup)localObject2).a("cpu");
      if (Build.VERSION.SDK_INT >= 21) {
        if ((localObject2 != null) && (localObject1 != null) && (((ControlGroup)localObject1).b.contains("pid_"))) {
          bool = ((ControlGroup)localObject2).b.contains("bg_non_interactive") ^ true;
        }
      }
    }
    try
    {
      i = Integer.parseInt(localObject1.b.split("/")[1].replace("uid_", ""));
    }
    catch (Exception localException1)
    {
      int i;
      for (;;) {}
    }
    i = a().a();
    a.a("name=%s, pid=%d, uid=%d, foreground=%b, cpuacct=%s, cpu=%s", new Object[] { this.jdField_a_of_type_JavaLangString, Integer.valueOf(paramInt), Integer.valueOf(i), Boolean.valueOf(bool), ((ControlGroup)localObject1).toString(), ((ControlGroup)localObject2).toString() });
    break label417;
    throw new NotAndroidAppProcessException(paramInt);
    if ((localObject2 != null) && (localObject1 != null) && (((ControlGroup)localObject2).b.contains("apps"))) {
      bool = ((ControlGroup)localObject2).b.contains("bg_non_interactive") ^ true;
    }
    try
    {
      i = Integer.parseInt(((ControlGroup)localObject1).b.substring(((ControlGroup)localObject1).b.lastIndexOf("/") + 1));
    }
    catch (Exception localException2)
    {
      for (;;) {}
    }
    i = a().a();
    a.a("name=%s, pid=%d, uid=%d foreground=%b, cpuacct=%s, cpu=%s", new Object[] { this.jdField_a_of_type_JavaLangString, Integer.valueOf(paramInt), Integer.valueOf(i), Boolean.valueOf(bool), ((ControlGroup)localObject1).toString(), ((ControlGroup)localObject2).toString() });
    break label417;
    throw new NotAndroidAppProcessException(paramInt);
    if ((!this.jdField_a_of_type_JavaLangString.startsWith("/")) && (new File("/data/data", a()).exists()))
    {
      localObject1 = a();
      localObject2 = a();
      if (((Stat)localObject1).a() == 0) {
        bool = true;
      } else {
        bool = false;
      }
      i = ((Status)localObject2).a();
      a.a("name=%s, pid=%d, uid=%d foreground=%b", new Object[] { this.jdField_a_of_type_JavaLangString, Integer.valueOf(paramInt), Integer.valueOf(i), Boolean.valueOf(bool) });
      label417:
      this.jdField_a_of_type_Boolean = bool;
      this.jdField_a_of_type_Int = i;
      return;
    }
    throw new NotAndroidAppProcessException(paramInt);
  }
  
  protected AndroidAppProcess(Parcel paramParcel)
  {
    super(paramParcel);
    boolean bool;
    if (paramParcel.readByte() != 0) {
      bool = true;
    } else {
      bool = false;
    }
    this.jdField_a_of_type_Boolean = bool;
    this.jdField_a_of_type_Int = paramParcel.readInt();
  }
  
  public String a()
  {
    return this.jdField_a_of_type_JavaLangString.split(":")[0];
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeByte((byte)this.jdField_a_of_type_Boolean);
    paramParcel.writeInt(this.jdField_a_of_type_Int);
  }
  
  public static final class NotAndroidAppProcessException
    extends Exception
  {
    public NotAndroidAppProcessException(int paramInt)
    {
      super();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\processutil\models\AndroidAppProcess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */