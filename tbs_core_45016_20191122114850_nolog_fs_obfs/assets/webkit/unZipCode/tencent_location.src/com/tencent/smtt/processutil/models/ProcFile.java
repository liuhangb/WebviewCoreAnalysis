package com.tencent.smtt.processutil.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.File;
import java.io.IOException;

public class ProcFile
  extends File
  implements Parcelable
{
  public static final Parcelable.Creator<ProcFile> CREATOR = new Parcelable.Creator()
  {
    public ProcFile a(Parcel paramAnonymousParcel)
    {
      return new ProcFile(paramAnonymousParcel);
    }
    
    public ProcFile[] a(int paramAnonymousInt)
    {
      return new ProcFile[paramAnonymousInt];
    }
  };
  public final String a;
  
  protected ProcFile(Parcel paramParcel)
  {
    super(paramParcel.readString());
    this.a = paramParcel.readString();
  }
  
  protected ProcFile(String paramString)
    throws IOException
  {
    super(paramString);
    this.a = a(paramString);
  }
  
  /* Error */
  protected static String a(String paramString)
    throws IOException
  {
    // Byte code:
    //   0: new 41	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 42	java/lang/StringBuilder:<init>	()V
    //   7: astore_3
    //   8: new 44	java/io/BufferedReader
    //   11: dup
    //   12: new 46	java/io/FileReader
    //   15: dup
    //   16: aload_0
    //   17: invokespecial 47	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   20: invokespecial 50	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   23: astore_2
    //   24: aload_2
    //   25: invokevirtual 53	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   28: astore_1
    //   29: ldc 55
    //   31: astore_0
    //   32: aload_1
    //   33: ifnull +26 -> 59
    //   36: aload_3
    //   37: aload_0
    //   38: invokevirtual 59	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: pop
    //   42: aload_3
    //   43: aload_1
    //   44: invokevirtual 59	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: pop
    //   48: ldc 61
    //   50: astore_0
    //   51: aload_2
    //   52: invokevirtual 53	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   55: astore_1
    //   56: goto -24 -> 32
    //   59: aload_3
    //   60: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   63: astore_0
    //   64: aload_2
    //   65: invokevirtual 67	java/io/BufferedReader:close	()V
    //   68: aload_0
    //   69: areturn
    //   70: astore_1
    //   71: aload_2
    //   72: astore_0
    //   73: goto +6 -> 79
    //   76: astore_1
    //   77: aconst_null
    //   78: astore_0
    //   79: aload_0
    //   80: ifnull +7 -> 87
    //   83: aload_0
    //   84: invokevirtual 67	java/io/BufferedReader:close	()V
    //   87: aload_1
    //   88: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	89	0	paramString	String
    //   28	28	1	str	String
    //   70	1	1	localObject1	Object
    //   76	12	1	localObject2	Object
    //   23	49	2	localBufferedReader	java.io.BufferedReader
    //   7	53	3	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   24	29	70	finally
    //   36	48	70	finally
    //   51	56	70	finally
    //   59	64	70	finally
    //   0	24	76	finally
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public long length()
  {
    return this.a.length();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(getAbsolutePath());
    paramParcel.writeString(this.a);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\processutil\models\ProcFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */