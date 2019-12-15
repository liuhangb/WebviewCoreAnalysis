package org.chromium.skia.mojom;

import java.util.Arrays;
import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class Bitmap
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(48, 0) };
  public int a;
  public long a;
  public byte[] a;
  public int b;
  public int c;
  public int d;
  public int e;
  
  public Bitmap()
  {
    this(0);
  }
  
  private Bitmap(int paramInt)
  {
    super(48, paramInt);
  }
  
  public static Bitmap a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      Bitmap localBitmap = new Bitmap(localDataHeader.b);
      if (localDataHeader.b >= 0)
      {
        localBitmap.jdField_a_of_type_Int = paramDecoder.a(8);
        ColorType.a(localBitmap.jdField_a_of_type_Int);
      }
      if (localDataHeader.b >= 0)
      {
        localBitmap.b = paramDecoder.a(12);
        AlphaType.a(localBitmap.b);
      }
      if (localDataHeader.b >= 0)
      {
        localBitmap.c = paramDecoder.a(16);
        ColorProfileType.a(localBitmap.c);
      }
      if (localDataHeader.b >= 0) {
        localBitmap.d = paramDecoder.a(20);
      }
      if (localDataHeader.b >= 0) {
        localBitmap.e = paramDecoder.a(24);
      }
      if (localDataHeader.b >= 0) {
        localBitmap.jdField_a_of_type_Long = paramDecoder.a(32);
      }
      if (localDataHeader.b >= 0) {
        localBitmap.jdField_a_of_type_ArrayOfByte = paramDecoder.a(40, 0, -1);
      }
      return localBitmap;
    }
    finally
    {
      paramDecoder.b();
    }
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
    paramEncoder.a(this.jdField_a_of_type_Int, 8);
    paramEncoder.a(this.b, 12);
    paramEncoder.a(this.c, 16);
    paramEncoder.a(this.d, 20);
    paramEncoder.a(this.e, 24);
    paramEncoder.a(this.jdField_a_of_type_Long, 32);
    paramEncoder.a(this.jdField_a_of_type_ArrayOfByte, 40, 0, -1);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    if (getClass() != paramObject.getClass()) {
      return false;
    }
    paramObject = (Bitmap)paramObject;
    if (this.jdField_a_of_type_Int != ((Bitmap)paramObject).jdField_a_of_type_Int) {
      return false;
    }
    if (this.b != ((Bitmap)paramObject).b) {
      return false;
    }
    if (this.c != ((Bitmap)paramObject).c) {
      return false;
    }
    if (this.d != ((Bitmap)paramObject).d) {
      return false;
    }
    if (this.e != ((Bitmap)paramObject).e) {
      return false;
    }
    if (this.jdField_a_of_type_Long != ((Bitmap)paramObject).jdField_a_of_type_Long) {
      return false;
    }
    return Arrays.equals(this.jdField_a_of_type_ArrayOfByte, ((Bitmap)paramObject).jdField_a_of_type_ArrayOfByte);
  }
  
  public int hashCode()
  {
    return (((((((getClass().hashCode() + 31) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int)) * 31 + BindingsHelper.b(this.b)) * 31 + BindingsHelper.b(this.c)) * 31 + BindingsHelper.b(this.d)) * 31 + BindingsHelper.b(this.e)) * 31 + BindingsHelper.a(this.jdField_a_of_type_Long)) * 31 + Arrays.hashCode(this.jdField_a_of_type_ArrayOfByte);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\skia\mojom\Bitmap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */