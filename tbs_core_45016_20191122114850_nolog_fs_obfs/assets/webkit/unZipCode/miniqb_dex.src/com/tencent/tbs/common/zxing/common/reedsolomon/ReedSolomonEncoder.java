package com.tencent.tbs.common.zxing.common.reedsolomon;

import java.util.ArrayList;
import java.util.List;

public final class ReedSolomonEncoder
{
  private final List<GenericGFPoly> cachedGenerators;
  private final GenericGF field;
  
  public ReedSolomonEncoder(GenericGF paramGenericGF)
  {
    this.field = paramGenericGF;
    this.cachedGenerators = new ArrayList();
    this.cachedGenerators.add(new GenericGFPoly(paramGenericGF, new int[] { 1 }));
  }
  
  private GenericGFPoly buildGenerator(int paramInt)
  {
    if (paramInt >= this.cachedGenerators.size())
    {
      Object localObject = this.cachedGenerators;
      localObject = (GenericGFPoly)((List)localObject).get(((List)localObject).size() - 1);
      int i = this.cachedGenerators.size();
      while (i <= paramInt)
      {
        GenericGF localGenericGF = this.field;
        localObject = ((GenericGFPoly)localObject).multiply(new GenericGFPoly(localGenericGF, new int[] { 1, localGenericGF.exp(i - 1 + localGenericGF.getGeneratorBase()) }));
        this.cachedGenerators.add(localObject);
        i += 1;
      }
    }
    return (GenericGFPoly)this.cachedGenerators.get(paramInt);
  }
  
  public void encode(int[] paramArrayOfInt, int paramInt)
  {
    if (paramInt != 0)
    {
      int i = paramArrayOfInt.length - paramInt;
      if (i > 0)
      {
        Object localObject = buildGenerator(paramInt);
        int[] arrayOfInt = new int[i];
        System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, i);
        localObject = new GenericGFPoly(this.field, arrayOfInt).multiplyByMonomial(paramInt, 1).divide(localObject)[1].getCoefficients();
        int j = paramInt - localObject.length;
        paramInt = 0;
        while (paramInt < j)
        {
          paramArrayOfInt[(i + paramInt)] = 0;
          paramInt += 1;
        }
        System.arraycopy(localObject, 0, paramArrayOfInt, i + j, localObject.length);
        return;
      }
      throw new IllegalArgumentException("No data bytes provided");
    }
    throw new IllegalArgumentException("No error correction bytes");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\zxing\common\reedsolomon\ReedSolomonEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */