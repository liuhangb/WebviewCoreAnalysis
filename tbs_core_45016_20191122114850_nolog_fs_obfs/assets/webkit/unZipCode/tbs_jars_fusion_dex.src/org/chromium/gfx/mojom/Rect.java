package org.chromium.gfx.mojom;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class Rect
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
  public int a;
  public int b;
  public int c;
  public int d;
  
  public Rect()
  {
    this(0);
  }
  
  private Rect(int paramInt)
  {
    super(24, paramInt);
  }
  
  public static Rect a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      Rect localRect = new Rect(localDataHeader.b);
      if (localDataHeader.b >= 0) {
        localRect.jdField_a_of_type_Int = paramDecoder.a(8);
      }
      if (localDataHeader.b >= 0) {
        localRect.b = paramDecoder.a(12);
      }
      if (localDataHeader.b >= 0) {
        localRect.c = paramDecoder.a(16);
      }
      if (localDataHeader.b >= 0) {
        localRect.d = paramDecoder.a(20);
      }
      return localRect;
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
    paramObject = (Rect)paramObject;
    if (this.jdField_a_of_type_Int != ((Rect)paramObject).jdField_a_of_type_Int) {
      return false;
    }
    if (this.b != ((Rect)paramObject).b) {
      return false;
    }
    if (this.c != ((Rect)paramObject).c) {
      return false;
    }
    return this.d == ((Rect)paramObject).d;
  }
  
  public int hashCode()
  {
    return ((((getClass().hashCode() + 31) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int)) * 31 + BindingsHelper.b(this.b)) * 31 + BindingsHelper.b(this.c)) * 31 + BindingsHelper.b(this.d);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\gfx\mojom\Rect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */