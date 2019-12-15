package com.tencent.mtt.external.reader;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PDFOutlineData
  implements Parcelable
{
  public static final Parcelable.Creator<PDFOutlineData> CREATOR = new Parcelable.Creator()
  {
    public PDFOutlineData a(Parcel paramAnonymousParcel)
    {
      PDFOutlineData localPDFOutlineData = new PDFOutlineData();
      PDFOutlineData.a(localPDFOutlineData, paramAnonymousParcel.readString());
      PDFOutlineData.a(localPDFOutlineData, paramAnonymousParcel.readInt());
      PDFOutlineData.b(localPDFOutlineData, paramAnonymousParcel.readInt());
      PDFOutlineData.c(localPDFOutlineData, paramAnonymousParcel.readInt());
      return localPDFOutlineData;
    }
    
    public PDFOutlineData[] a(int paramAnonymousInt)
    {
      return new PDFOutlineData[paramAnonymousInt];
    }
  };
  private int jdField_a_of_type_Int;
  private String jdField_a_of_type_JavaLangString;
  private int b;
  private int c;
  
  public PDFOutlineData() {}
  
  public PDFOutlineData(String paramString, int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
    this.jdField_a_of_type_Int = paramInt1;
    this.b = paramInt2;
    this.c = 0;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int getOutlineLevel()
  {
    return this.b;
  }
  
  public int getPage()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public String getTitle()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public boolean isCurrOutline()
  {
    return this.c == 1;
  }
  
  public void setCurrOutline(boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.jdField_a_of_type_JavaLangString);
    paramParcel.writeInt(this.jdField_a_of_type_Int);
    paramParcel.writeInt(this.b);
    paramParcel.writeInt(this.c);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\PDFOutlineData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */