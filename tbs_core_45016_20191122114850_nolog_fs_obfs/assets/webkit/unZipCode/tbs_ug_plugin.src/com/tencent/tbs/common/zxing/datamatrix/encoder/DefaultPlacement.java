package com.tencent.tbs.common.zxing.datamatrix.encoder;

import java.util.Arrays;

public class DefaultPlacement
{
  private final byte[] bits;
  private final CharSequence codewords;
  private final int numcols;
  private final int numrows;
  
  public DefaultPlacement(CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    this.codewords = paramCharSequence;
    this.numcols = paramInt1;
    this.numrows = paramInt2;
    this.bits = new byte[paramInt1 * paramInt2];
    Arrays.fill(this.bits, (byte)-1);
  }
  
  private void corner1(int paramInt)
  {
    module(this.numrows - 1, 0, paramInt, 1);
    module(this.numrows - 1, 1, paramInt, 2);
    module(this.numrows - 1, 2, paramInt, 3);
    module(0, this.numcols - 2, paramInt, 4);
    module(0, this.numcols - 1, paramInt, 5);
    module(1, this.numcols - 1, paramInt, 6);
    module(2, this.numcols - 1, paramInt, 7);
    module(3, this.numcols - 1, paramInt, 8);
  }
  
  private void corner2(int paramInt)
  {
    module(this.numrows - 3, 0, paramInt, 1);
    module(this.numrows - 2, 0, paramInt, 2);
    module(this.numrows - 1, 0, paramInt, 3);
    module(0, this.numcols - 4, paramInt, 4);
    module(0, this.numcols - 3, paramInt, 5);
    module(0, this.numcols - 2, paramInt, 6);
    module(0, this.numcols - 1, paramInt, 7);
    module(1, this.numcols - 1, paramInt, 8);
  }
  
  private void corner3(int paramInt)
  {
    module(this.numrows - 3, 0, paramInt, 1);
    module(this.numrows - 2, 0, paramInt, 2);
    module(this.numrows - 1, 0, paramInt, 3);
    module(0, this.numcols - 2, paramInt, 4);
    module(0, this.numcols - 1, paramInt, 5);
    module(1, this.numcols - 1, paramInt, 6);
    module(2, this.numcols - 1, paramInt, 7);
    module(3, this.numcols - 1, paramInt, 8);
  }
  
  private void corner4(int paramInt)
  {
    module(this.numrows - 1, 0, paramInt, 1);
    module(this.numrows - 1, this.numcols - 1, paramInt, 2);
    module(0, this.numcols - 3, paramInt, 3);
    module(0, this.numcols - 2, paramInt, 4);
    module(0, this.numcols - 1, paramInt, 5);
    module(1, this.numcols - 3, paramInt, 6);
    module(1, this.numcols - 2, paramInt, 7);
    module(1, this.numcols - 1, paramInt, 8);
  }
  
  private boolean hasBit(int paramInt1, int paramInt2)
  {
    return this.bits[(paramInt2 * this.numcols + paramInt1)] >= 0;
  }
  
  private void module(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt1;
    int j = paramInt2;
    if (paramInt1 < 0)
    {
      j = this.numrows;
      i = paramInt1 + j;
      j = paramInt2 + (4 - (j + 4) % 8);
    }
    paramInt2 = i;
    paramInt1 = j;
    if (j < 0)
    {
      paramInt2 = this.numcols;
      paramInt1 = j + paramInt2;
      paramInt2 = i + (4 - (paramInt2 + 4) % 8);
    }
    paramInt3 = this.codewords.charAt(paramInt3);
    boolean bool = true;
    if ((paramInt3 & 1 << 8 - paramInt4) == 0) {
      bool = false;
    }
    setBit(paramInt1, paramInt2, bool);
  }
  
  private void setBit(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.bits[(paramInt2 * this.numcols + paramInt1)] = ((byte)paramBoolean);
  }
  
  private void utah(int paramInt1, int paramInt2, int paramInt3)
  {
    int k = paramInt1 - 2;
    int i = paramInt2 - 2;
    module(k, i, paramInt3, 1);
    int j = paramInt2 - 1;
    module(k, j, paramInt3, 2);
    k = paramInt1 - 1;
    module(k, i, paramInt3, 3);
    module(k, j, paramInt3, 4);
    module(k, paramInt2, paramInt3, 5);
    module(paramInt1, i, paramInt3, 6);
    module(paramInt1, j, paramInt3, 7);
    module(paramInt1, paramInt2, paramInt3, 8);
  }
  
  public final boolean getBit(int paramInt1, int paramInt2)
  {
    return this.bits[(paramInt2 * this.numcols + paramInt1)] == 1;
  }
  
  final byte[] getBits()
  {
    return this.bits;
  }
  
  final int getNumcols()
  {
    return this.numcols;
  }
  
  final int getNumrows()
  {
    return this.numrows;
  }
  
  public final void place()
  {
    int j = 4;
    int i = 0;
    int m = 0;
    int i1;
    int i2;
    int i3;
    do
    {
      int k;
      int n;
      do
      {
        k = m;
        if (j == this.numrows)
        {
          k = m;
          if (i == 0)
          {
            corner1(m);
            k = m + 1;
          }
        }
        n = k;
        if (j == this.numrows - 2)
        {
          n = k;
          if (i == 0)
          {
            n = k;
            if (this.numcols % 4 != 0)
            {
              corner2(k);
              n = k + 1;
            }
          }
        }
        m = n;
        if (j == this.numrows - 2)
        {
          m = n;
          if (i == 0)
          {
            m = n;
            if (this.numcols % 8 == 4)
            {
              corner3(n);
              m = n + 1;
            }
          }
        }
        n = j;
        i1 = i;
        k = m;
        if (j == this.numrows + 4)
        {
          n = j;
          i1 = i;
          k = m;
          if (i == 2)
          {
            n = j;
            i1 = i;
            k = m;
            if (this.numcols % 8 == 0)
            {
              corner4(m);
              k = m + 1;
              i1 = i;
              n = j;
            }
          }
        }
        do
        {
          i = k;
          if (n < this.numrows)
          {
            i = k;
            if (i1 >= 0)
            {
              i = k;
              if (!hasBit(i1, n))
              {
                utah(n, i1, k);
                i = k + 1;
              }
            }
          }
          m = n - 2;
          j = i1 + 2;
          if (m < 0) {
            break;
          }
          n = m;
          i1 = j;
          k = i;
        } while (j < this.numcols);
        m += 1;
        k = j + 3;
        j = i;
        i = k;
        do
        {
          k = j;
          if (m >= 0)
          {
            k = j;
            if (i < this.numcols)
            {
              k = j;
              if (!hasBit(i, m))
              {
                utah(m, i, j);
                k = j + 1;
              }
            }
          }
          n = m + 2;
          i1 = i - 2;
          if (n >= this.numrows) {
            break;
          }
          m = n;
          i = i1;
          j = k;
        } while (i1 >= 0);
        n += 3;
        i1 += 1;
        i2 = this.numrows;
        j = n;
        i = i1;
        m = k;
      } while (n < i2);
      i3 = this.numcols;
      j = n;
      i = i1;
      m = k;
    } while (i1 < i3);
    if (!hasBit(i3 - 1, i2 - 1))
    {
      setBit(this.numcols - 1, this.numrows - 1, true);
      setBit(this.numcols - 2, this.numrows - 2, true);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\zxing\datamatrix\encoder\DefaultPlacement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */