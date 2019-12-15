package com.tencent.common.utils.gzip;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class GZipOutputStream
  extends OutputStream
{
  public static final int TYPE_DEFLATE = 0;
  public static final int TYPE_GZIP = 1;
  private int jdField_a_of_type_Int;
  private OutputStream jdField_a_of_type_JavaIoOutputStream;
  private boolean jdField_a_of_type_Boolean;
  byte[] jdField_a_of_type_ArrayOfByte;
  int[] jdField_a_of_type_ArrayOfInt;
  ZipIntMultShortHashMap[] jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap = new ZipIntMultShortHashMap[5];
  private int jdField_b_of_type_Int;
  private boolean jdField_b_of_type_Boolean;
  byte[] jdField_b_of_type_ArrayOfByte;
  int[] jdField_b_of_type_ArrayOfInt;
  private int jdField_c_of_type_Int;
  private byte[] jdField_c_of_type_ArrayOfByte;
  private int[] jdField_c_of_type_ArrayOfInt;
  private int jdField_d_of_type_Int;
  private byte[] jdField_d_of_type_ArrayOfByte;
  private int[] jdField_d_of_type_ArrayOfInt;
  private int jdField_e_of_type_Int;
  private byte[] jdField_e_of_type_ArrayOfByte;
  private int[] jdField_e_of_type_ArrayOfInt;
  private int jdField_f_of_type_Int;
  private int[] jdField_f_of_type_ArrayOfInt = new int['Ā'];
  private int g;
  private int h;
  
  public GZipOutputStream(OutputStream paramOutputStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws IOException
  {
    this.jdField_a_of_type_JavaIoOutputStream = paramOutputStream;
    this.jdField_e_of_type_ArrayOfByte = new byte[paramInt1 + 300];
    this.jdField_d_of_type_ArrayOfInt = new int['Ğ'];
    this.jdField_e_of_type_ArrayOfInt = new int[30];
    this.jdField_c_of_type_ArrayOfInt = new int[2];
    if (paramInt3 <= 32768)
    {
      paramInt1 = 0;
      if (paramInt3 >= 100)
      {
        this.jdField_d_of_type_ArrayOfByte = new byte[paramInt3 / 4 * 4];
        this.jdField_b_of_type_Boolean = true;
      }
      else
      {
        this.jdField_d_of_type_ArrayOfByte = null;
        this.jdField_b_of_type_Boolean = false;
      }
      if (paramInt4 <= 32768)
      {
        paramInt3 = 1024;
        if ((paramInt4 < 1024) && (paramInt4 > 0)) {
          paramInt4 = paramInt3;
        }
        this.jdField_c_of_type_ArrayOfByte = new byte[paramInt4];
        if (paramInt4 == 0)
        {
          this.jdField_a_of_type_Boolean = true;
          this.h = 1;
          a();
          this.g = 4;
        }
        else
        {
          this.h = 2;
          this.g = 0;
        }
        while (paramInt1 < 4)
        {
          this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap[paramInt1] = new ZipIntMultShortHashMap(2048);
          paramInt1 += 1;
        }
        if (paramInt2 == 1)
        {
          this.jdField_a_of_type_JavaIoOutputStream.write(31);
          this.jdField_a_of_type_JavaIoOutputStream.write(139);
          this.jdField_a_of_type_JavaIoOutputStream.write(8);
          this.jdField_a_of_type_JavaIoOutputStream.write(new byte[6]);
          this.jdField_a_of_type_JavaIoOutputStream.write(255);
        }
        return;
      }
      throw new IllegalArgumentException("plainWindowSize > 32768");
    }
    throw new IllegalArgumentException("plainWindowSize > 32768");
  }
  
  private void a()
    throws IOException
  {
    if (this.g == 0) {
      this.g = 4;
    } else {
      a(this.jdField_a_of_type_ArrayOfInt['Ā'], this.jdField_a_of_type_ArrayOfByte['Ā']);
    }
    if (this.jdField_a_of_type_Boolean)
    {
      a(1, (byte)1);
      System.out.println("final block");
    }
    else
    {
      a(0, (byte)1);
    }
    a(this.h, (byte)2);
    this.jdField_a_of_type_ArrayOfInt = new int['Ğ'];
    this.jdField_a_of_type_ArrayOfByte = new byte['Ğ'];
    this.jdField_b_of_type_ArrayOfInt = new int[30];
    this.jdField_b_of_type_ArrayOfByte = new byte[30];
    int i = this.h;
    if (i == 1)
    {
      ZipHelper.genFixedTree(this.jdField_a_of_type_ArrayOfInt, this.jdField_a_of_type_ArrayOfByte, this.jdField_b_of_type_ArrayOfInt, this.jdField_b_of_type_ArrayOfByte);
      return;
    }
    if (i == 2)
    {
      i = 0;
      while (i < 2)
      {
        arrayOfInt = this.jdField_e_of_type_ArrayOfInt;
        if (arrayOfInt[i] == 0) {
          arrayOfInt[i] = 1;
        }
        i += 1;
      }
      int[] arrayOfInt = this.jdField_d_of_type_ArrayOfInt;
      arrayOfInt['Ā'] = 1;
      ZipHelper.genTreeLength(arrayOfInt, this.jdField_a_of_type_ArrayOfByte, 15);
      ZipHelper.genHuffTree(this.jdField_a_of_type_ArrayOfInt, this.jdField_a_of_type_ArrayOfByte);
      ZipHelper.revHuffTree(this.jdField_a_of_type_ArrayOfInt, this.jdField_a_of_type_ArrayOfByte);
      ZipHelper.genTreeLength(this.jdField_e_of_type_ArrayOfInt, this.jdField_b_of_type_ArrayOfByte, 15);
      ZipHelper.genHuffTree(this.jdField_b_of_type_ArrayOfInt, this.jdField_b_of_type_ArrayOfByte);
      ZipHelper.revHuffTree(this.jdField_b_of_type_ArrayOfInt, this.jdField_b_of_type_ArrayOfByte);
      a(this.jdField_a_of_type_ArrayOfByte, this.jdField_b_of_type_ArrayOfByte);
      i = 0;
      while (i < 286)
      {
        this.jdField_d_of_type_ArrayOfInt[i] = 0;
        i += 1;
      }
      i = 0;
      while (i < 30)
      {
        this.jdField_e_of_type_ArrayOfInt[i] = 0;
        i += 1;
      }
    }
  }
  
  private void a(int paramInt)
    throws IOException
  {
    paramInt = this.jdField_e_of_type_ArrayOfByte[paramInt] + 256 & 0xFF;
    byte[] arrayOfByte = this.jdField_c_of_type_ArrayOfByte;
    if (arrayOfByte.length != 0)
    {
      int[] arrayOfInt = this.jdField_d_of_type_ArrayOfInt;
      arrayOfInt[paramInt] += 1;
      int i = this.jdField_a_of_type_Int;
      arrayOfByte[i] = ((byte)paramInt);
      if (paramInt == 255)
      {
        this.jdField_a_of_type_Int = (i + 1);
        arrayOfByte[this.jdField_a_of_type_Int] = 0;
      }
    }
    else
    {
      a(this.jdField_a_of_type_ArrayOfInt[paramInt], this.jdField_a_of_type_ArrayOfByte[paramInt]);
    }
  }
  
  private void a(int paramInt, byte paramByte)
    throws IOException
  {
    int[] arrayOfInt = this.jdField_c_of_type_ArrayOfInt;
    int j = arrayOfInt[0];
    int i = arrayOfInt[1];
    paramInt = paramInt << i | j & ((1 << paramByte) - 1 << i ^ 0xFFFFFFFF);
    i += paramByte;
    paramByte = paramInt;
    paramInt = i;
    while (paramInt >= 8)
    {
      this.jdField_a_of_type_JavaIoOutputStream.write(paramByte & 0xFF);
      paramByte >>>= 8;
      paramInt -= 8;
    }
    arrayOfInt = this.jdField_c_of_type_ArrayOfInt;
    arrayOfInt[0] = paramByte;
    arrayOfInt[1] = paramInt;
  }
  
  private void a(int paramInt1, int paramInt2)
    throws IOException
  {
    int k = 0;
    int j = 0;
    while ((j < ZipHelper.LENGTH_CODE.length >> 1) && (paramInt1 >= ZipHelper.LENGTH_CODE[((j << 1) + 1)])) {
      j += 1;
    }
    int m = j - 1;
    j = m + 257;
    int i = (byte)(paramInt2 - ZipHelper.LENGTH_CODE[(m * 2 + 1)]);
    paramInt2 = k;
    while ((paramInt2 < ZipHelper.DISTANCE_CODE.length >> 1) && (paramInt1 >= ZipHelper.DISTANCE_CODE[((paramInt2 << 1) + 1)])) {
      paramInt2 += 1;
    }
    paramInt2 -= 1;
    Object localObject = ZipHelper.DISTANCE_CODE;
    k = paramInt2 * 2;
    paramInt1 -= localObject[(k + 1)];
    localObject = this.jdField_c_of_type_ArrayOfByte;
    if (localObject.length != 0)
    {
      k = this.jdField_a_of_type_Int;
      localObject[k] = -1;
      localObject[(k + 1)] = ((byte)(j - 255));
      localObject[(k + 2)] = i;
      localObject[(k + 3)] = ((byte)paramInt2);
      localObject[(k + 4)] = ((byte)(paramInt1 & 0xFF));
      localObject[(k + 5)] = ((byte)(paramInt1 >> 8 & 0xFF));
      localObject[(k + 6)] = ((byte)(paramInt1 >> 16 & 0xFF));
      this.jdField_a_of_type_Int = (k + 6);
      localObject = this.jdField_d_of_type_ArrayOfInt;
      localObject[j] += 1;
      localObject = this.jdField_e_of_type_ArrayOfInt;
      localObject[paramInt2] += 1;
      return;
    }
    a(this.jdField_a_of_type_ArrayOfInt[j], this.jdField_a_of_type_ArrayOfByte[j]);
    a(i, (byte)ZipHelper.LENGTH_CODE[((j - 257) * 2)]);
    a(this.jdField_b_of_type_ArrayOfInt[paramInt2], this.jdField_b_of_type_ArrayOfByte[paramInt2]);
    a(paramInt1, (byte)ZipHelper.DISTANCE_CODE[k]);
  }
  
  private void a(ZipIntMultShortHashMap.Element paramElement, int[] paramArrayOfInt, int paramInt)
  {
    int i = paramElement.size - 1;
    int m = 0;
    int n;
    for (int k = 0; i >= 0; k = n)
    {
      int j = 3;
      while (j < 258)
      {
        int i1 = paramInt + j;
        if (i1 >= this.jdField_e_of_type_ArrayOfByte.length) {
          break;
        }
        if (paramElement.values[i] < this.jdField_b_of_type_Int) {
          n = (paramElement.values[i] + j % (this.jdField_b_of_type_Int - paramElement.values[i])) % this.jdField_d_of_type_ArrayOfByte.length;
        } else {
          n = (paramElement.values[i] + j % (this.jdField_b_of_type_Int + this.jdField_d_of_type_ArrayOfByte.length - paramElement.values[i])) % this.jdField_d_of_type_ArrayOfByte.length;
        }
        if (this.jdField_e_of_type_ArrayOfByte[i1] != this.jdField_d_of_type_ArrayOfByte[n]) {
          break;
        }
        j += 1;
      }
      n = k;
      if (j > k)
      {
        if (j == 258)
        {
          k = j;
          break label208;
        }
        m = i;
        n = j;
      }
      i -= 1;
    }
    i = m;
    label208:
    paramInt = this.jdField_b_of_type_Int;
    i = paramElement.values[i];
    paramElement = this.jdField_d_of_type_ArrayOfByte;
    paramArrayOfInt[0] = ((paramInt - i + paramElement.length) % paramElement.length);
    paramArrayOfInt[1] = k;
  }
  
  private void a(boolean paramBoolean)
    throws IOException
  {
    int i = this.jdField_d_of_type_Int;
    if (i != 0)
    {
      localObject1 = this.jdField_e_of_type_ArrayOfByte;
      System.arraycopy(localObject1, i, localObject1, 0, this.jdField_c_of_type_Int - i);
      this.jdField_c_of_type_Int -= this.jdField_d_of_type_Int;
      this.jdField_d_of_type_Int = 0;
    }
    int j;
    if (paramBoolean) {
      j = this.jdField_c_of_type_Int;
    } else {
      j = this.jdField_c_of_type_Int - 300;
    }
    Object localObject1 = new int[2];
    int[] arrayOfInt = new int[2];
    int k = 0;
    while (k < j)
    {
      if ((this.jdField_b_of_type_Boolean) && (k < j - 2) && (a((int[])localObject1, k))) {
        if (localObject1[1] > arrayOfInt[1])
        {
          arrayOfInt[0] = localObject1[0];
          arrayOfInt[1] = localObject1[1];
        }
        else
        {
          n = localObject1[0];
          i = localObject1[1];
          break label163;
        }
      }
      int n = 0;
      i = 1;
      label163:
      int m = i;
      int i1;
      if (paramBoolean)
      {
        i1 = j - k;
        m = i;
        if (i1 < i) {
          m = i1;
        }
      }
      if (m > 2) {
        a(n, m);
      } else {
        a(k);
      }
      byte[] arrayOfByte = this.jdField_c_of_type_ArrayOfByte;
      if (arrayOfByte.length != 0)
      {
        this.jdField_a_of_type_Int += 1;
        if (this.jdField_a_of_type_Int + 8 > arrayOfByte.length) {
          b();
        }
      }
      if (this.jdField_b_of_type_Boolean)
      {
        i = 0;
        while (i < m)
        {
          Object localObject2 = this.jdField_d_of_type_ArrayOfByte;
          n = this.jdField_b_of_type_Int;
          arrayOfByte = this.jdField_e_of_type_ArrayOfByte;
          i1 = k + i;
          localObject2[n] = arrayOfByte[i1];
          localObject2 = this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap[(n / (localObject2.length / 4))];
          int i2 = arrayOfByte[i1];
          int i3 = arrayOfByte[(i1 + 1)];
          ((ZipIntMultShortHashMap)localObject2).put(arrayOfByte[(i1 + 2)] + 128 | i2 + 128 << 16 | i3 + 128 << 8, (short)n);
          n = this.jdField_b_of_type_Int + 1;
          this.jdField_b_of_type_Int = n;
          arrayOfByte = this.jdField_d_of_type_ArrayOfByte;
          if (n % (arrayOfByte.length / 4) == 0)
          {
            if (this.jdField_b_of_type_Int == arrayOfByte.length) {
              this.jdField_b_of_type_Int = 0;
            }
            this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap[(this.jdField_b_of_type_Int / (this.jdField_d_of_type_ArrayOfByte.length / 4) % 4)].clear();
          }
          i += 1;
        }
      }
      k += m;
    }
    this.jdField_d_of_type_Int = k;
  }
  
  private void a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws IOException
  {
    int i = 285;
    for (;;)
    {
      k = paramArrayOfByte1[i];
      j = 29;
      if ((k != 0) || (i <= 29)) {
        break;
      }
      i -= 1;
    }
    int n = i + 1;
    i = j;
    while ((paramArrayOfByte2[i] == 0) && (i > 0)) {
      i -= 1;
    }
    int i1 = i + 1;
    int i2 = n + i1;
    Object localObject1 = new byte[i2];
    int m = 0;
    int j = 0;
    i = 0;
    while (j < n)
    {
      localObject1[i] = paramArrayOfByte1[j];
      i += 1;
      j += 1;
    }
    int k = 0;
    j = i;
    i = k;
    while (i < i1)
    {
      localObject1[j] = paramArrayOfByte2[i];
      j += 1;
      i += 1;
    }
    Object localObject2 = new int[19];
    Object tmp154_152 = localObject2;
    tmp154_152[0] = 16;
    Object tmp159_154 = tmp154_152;
    tmp159_154[1] = 17;
    Object tmp164_159 = tmp159_154;
    tmp164_159[2] = 18;
    Object tmp169_164 = tmp164_159;
    tmp169_164[3] = 0;
    Object tmp173_169 = tmp169_164;
    tmp173_169[4] = 8;
    Object tmp178_173 = tmp173_169;
    tmp178_173[5] = 7;
    Object tmp183_178 = tmp178_173;
    tmp183_178[6] = 9;
    Object tmp189_183 = tmp183_178;
    tmp189_183[7] = 6;
    Object tmp195_189 = tmp189_183;
    tmp195_189[8] = 10;
    Object tmp201_195 = tmp195_189;
    tmp201_195[9] = 5;
    Object tmp206_201 = tmp201_195;
    tmp206_201[10] = 11;
    Object tmp212_206 = tmp206_201;
    tmp212_206[11] = 4;
    Object tmp217_212 = tmp212_206;
    tmp217_212[12] = 12;
    Object tmp223_217 = tmp217_212;
    tmp223_217[13] = 3;
    Object tmp228_223 = tmp223_217;
    tmp228_223[14] = 13;
    Object tmp234_228 = tmp228_223;
    tmp234_228[15] = 2;
    Object tmp239_234 = tmp234_228;
    tmp239_234[16] = 14;
    Object tmp245_239 = tmp239_234;
    tmp245_239[17] = 1;
    Object tmp250_245 = tmp245_239;
    tmp250_245[18] = 15;
    tmp250_245;
    paramArrayOfByte1 = new byte[i2];
    Object localObject3 = new int[19];
    i = 0;
    j = 0;
    for (;;)
    {
      i2 = localObject1.length;
      k = 18;
      if (i >= i2) {
        break;
      }
      k = i + 3;
      if ((k < localObject1.length) && (localObject1[i] == localObject1[(i + 1)]) && (localObject1[i] == localObject1[(i + 2)]) && (localObject1[i] == localObject1[k]))
      {
        if (localObject1[i] == 0)
        {
          paramArrayOfByte1[j] = 0;
          for (k = 4;; k = (short)(k + 1))
          {
            i2 = i + k;
            if ((i2 >= localObject1.length) || (localObject1[i] != localObject1[i2]) || (k >= 139)) {
              break;
            }
          }
          if (k < 12)
          {
            paramArrayOfByte1[(j + 1)] = 17;
            paramArrayOfByte1[(j + 2)] = ((byte)(k - 3 - 1));
          }
          else
          {
            paramArrayOfByte1[(j + 1)] = 18;
            paramArrayOfByte1[(j + 2)] = ((byte)(k - 11 - 1));
          }
          i += k - 1;
        }
        else
        {
          paramArrayOfByte1[j] = localObject1[i];
          paramArrayOfByte1[(j + 1)] = 16;
          for (k = 4;; k = (short)(k + 1))
          {
            i2 = i + k;
            if ((i2 >= localObject1.length) || (localObject1[i] != localObject1[i2]) || (k >= 7)) {
              break;
            }
          }
          paramArrayOfByte1[(j + 2)] = ((byte)(k - 4));
          i += k - 1;
        }
        k = paramArrayOfByte1[j];
        localObject3[k] += 1;
        k = paramArrayOfByte1[(j + 1)];
        localObject3[k] += 1;
        j += 2;
      }
      else
      {
        paramArrayOfByte1[j] = localObject1[i];
        k = paramArrayOfByte1[j];
        localObject3[k] += 1;
      }
      j += 1;
      i += 1;
    }
    paramArrayOfByte2 = new byte[19];
    localObject1 = new int[19];
    ZipHelper.genTreeLength((int[])localObject3, paramArrayOfByte2, 7);
    ZipHelper.genHuffTree((int[])localObject1, paramArrayOfByte2);
    ZipHelper.revHuffTree((int[])localObject1, paramArrayOfByte2);
    a(n - 257, (byte)5);
    a(i1 - 1, (byte)5);
    i = k;
    while ((paramArrayOfByte2[localObject2[i]] == 0) && (i > 0)) {
      i -= 1;
    }
    k = i + 1;
    a(k - 4, (byte)4);
    i = 0;
    while (i < k)
    {
      a(paramArrayOfByte2[localObject2[i]], (byte)3);
      i += 1;
    }
    localObject2 = System.out;
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append(" HLIT: ");
    ((StringBuilder)localObject3).append(n);
    ((PrintStream)localObject2).println(((StringBuilder)localObject3).toString());
    localObject2 = System.out;
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append(" HDIST: ");
    ((StringBuilder)localObject3).append(i1);
    ((PrintStream)localObject2).println(((StringBuilder)localObject3).toString());
    localObject2 = System.out;
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append(" HCLEN: ");
    ((StringBuilder)localObject3).append(k);
    ((PrintStream)localObject2).println(((StringBuilder)localObject3).toString());
    for (k = m; k < j; k = i + 1)
    {
      a(localObject1[paramArrayOfByte1[k]], paramArrayOfByte2[paramArrayOfByte1[k]]);
      i = k;
      if (paramArrayOfByte1[k] > 15) {
        switch (paramArrayOfByte1[k])
        {
        default: 
          i = k + 1;
          a(paramArrayOfByte1[i], (byte)7);
          break;
        case 17: 
          i = k + 1;
          a(paramArrayOfByte1[i], (byte)3);
          break;
        case 16: 
          i = k + 1;
          a(paramArrayOfByte1[i], (byte)2);
        }
      }
    }
  }
  
  private boolean a(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt = new int[2];
    boolean bool = false;
    paramArrayOfInt[1] = 0;
    int i = 0;
    while (i < 4)
    {
      Object localObject = this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap[i];
      byte[] arrayOfByte = this.jdField_e_of_type_ArrayOfByte;
      int j = arrayOfByte[paramInt];
      int k = arrayOfByte[(paramInt + 1)];
      localObject = ((ZipIntMultShortHashMap)localObject).get(arrayOfByte[(paramInt + 2)] + 128 | j + 128 << 16 | k + 128 << 8);
      if ((localObject != null) && (((ZipIntMultShortHashMap.Element)localObject).size != 0))
      {
        a((ZipIntMultShortHashMap.Element)localObject, arrayOfInt, paramInt);
        if (arrayOfInt[1] > paramArrayOfInt[1])
        {
          paramArrayOfInt[0] = arrayOfInt[0];
          paramArrayOfInt[1] = arrayOfInt[1];
        }
      }
      i += 1;
    }
    if (paramArrayOfInt[1] != 0) {
      bool = true;
    }
    return bool;
  }
  
  private void b()
    throws IOException
  {
    System.out.println("  compile Output; new Block");
    a();
    int j;
    for (int i = 0; i < this.jdField_a_of_type_Int; i = j + 1)
    {
      j = this.jdField_c_of_type_ArrayOfByte[i];
      int k = j;
      if (j < 0) {
        k = j + 256;
      }
      if (k != 255)
      {
        a(this.jdField_a_of_type_ArrayOfInt[k], this.jdField_a_of_type_ArrayOfByte[k]);
        j = i;
      }
      else
      {
        j = i;
        if (k == 255)
        {
          j = i + 1;
          byte[] arrayOfByte = this.jdField_c_of_type_ArrayOfByte;
          if (arrayOfByte[j] == 0)
          {
            a(this.jdField_a_of_type_ArrayOfInt['ÿ'], this.jdField_a_of_type_ArrayOfByte['ÿ']);
          }
          else if (arrayOfByte[j] > 0)
          {
            i = arrayOfByte[j] + 255;
            k = j + 1;
            j = arrayOfByte[k];
            int m = k + 1;
            k = arrayOfByte[m];
            m += 1;
            int n = arrayOfByte[m];
            int i1 = arrayOfByte[(m + 1)];
            int i2 = arrayOfByte[(m + 2)];
            a(this.jdField_a_of_type_ArrayOfInt[i], this.jdField_a_of_type_ArrayOfByte[i]);
            a(j, (byte)ZipHelper.LENGTH_CODE[((i - 257) * 2)]);
            a(this.jdField_b_of_type_ArrayOfInt[k], this.jdField_b_of_type_ArrayOfByte[k]);
            a((i2 + 256 & 0xFF) << 16 | n + 256 & 0xFF | (i1 + 256 & 0xFF) << 8, (byte)ZipHelper.DISTANCE_CODE[(k * 2)]);
            j = m + 3 - 1;
          }
          else
          {
            throw new IOException("illegal code decoded");
          }
        }
      }
    }
    this.jdField_a_of_type_Int = 0;
  }
  
  private void c()
    throws IOException
  {
    a(this.jdField_a_of_type_ArrayOfInt['Ā'], this.jdField_a_of_type_ArrayOfByte['Ā']);
    System.out.println(" wrote final 256;");
    int[] arrayOfInt = this.jdField_c_of_type_ArrayOfInt;
    if ((arrayOfInt[1] & 0x7) != 0) {
      a(0, (byte)(8 - (arrayOfInt[1] & 0x7)));
    }
    this.jdField_a_of_type_JavaIoOutputStream.write(this.jdField_f_of_type_Int & 0xFF);
    this.jdField_a_of_type_JavaIoOutputStream.write(this.jdField_f_of_type_Int >>> 8 & 0xFF);
    this.jdField_a_of_type_JavaIoOutputStream.write(this.jdField_f_of_type_Int >>> 16 & 0xFF);
    this.jdField_a_of_type_JavaIoOutputStream.write(this.jdField_f_of_type_Int >>> 24 & 0xFF);
    this.jdField_a_of_type_JavaIoOutputStream.write(this.jdField_e_of_type_Int & 0xFF);
    this.jdField_a_of_type_JavaIoOutputStream.write(this.jdField_e_of_type_Int >>> 8 & 0xFF);
    this.jdField_a_of_type_JavaIoOutputStream.write(this.jdField_e_of_type_Int >>> 16 & 0xFF);
    this.jdField_a_of_type_JavaIoOutputStream.write(this.jdField_e_of_type_Int >>> 24 & 0xFF);
    this.jdField_a_of_type_JavaIoOutputStream.flush();
    this.jdField_a_of_type_JavaIoOutputStream.close();
    System.out.println(" output finished");
  }
  
  public void close()
    throws IOException
  {
    flush();
    if (this.h == 2)
    {
      if (this.jdField_a_of_type_Int + 8 + (this.jdField_c_of_type_Int - this.jdField_d_of_type_Int) * 8 / 3 > this.jdField_c_of_type_ArrayOfByte.length) {
        b();
      }
      a(true);
      this.jdField_a_of_type_Boolean = true;
      b();
    }
    else
    {
      a(true);
    }
    c();
    this.jdField_a_of_type_JavaIoOutputStream = null;
    this.jdField_c_of_type_ArrayOfByte = null;
    this.jdField_e_of_type_ArrayOfByte = null;
    this.jdField_d_of_type_ArrayOfInt = null;
  }
  
  public void flush()
    throws IOException
  {
    a(false);
  }
  
  public void write(int paramInt)
    throws IOException
  {
    if (this.jdField_e_of_type_ArrayOfByte.length == this.jdField_c_of_type_Int) {
      a(false);
    }
    byte[] arrayOfByte = this.jdField_e_of_type_ArrayOfByte;
    int j = this.jdField_c_of_type_Int;
    this.jdField_c_of_type_Int = (j + 1);
    int i = (byte)paramInt;
    arrayOfByte[j] = i;
    this.jdField_e_of_type_Int += 1;
    this.jdField_f_of_type_Int = ZipHelper.crc32(this.jdField_f_of_type_ArrayOfInt, this.jdField_f_of_type_Int, new byte[] { i }, 0, 1);
  }
  
  public void write(byte[] paramArrayOfByte)
    throws IOException
  {
    write(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    this.jdField_f_of_type_Int = ZipHelper.crc32(this.jdField_f_of_type_ArrayOfInt, this.jdField_f_of_type_Int, paramArrayOfByte, paramInt1, paramInt2);
    this.jdField_e_of_type_Int += paramInt2;
    int i = 0;
    while (i != paramInt2)
    {
      byte[] arrayOfByte = this.jdField_e_of_type_ArrayOfByte;
      int j = arrayOfByte.length;
      int k = this.jdField_c_of_type_Int;
      int m = paramInt2 - i;
      if (j - k >= m)
      {
        System.arraycopy(paramArrayOfByte, i + paramInt1, arrayOfByte, k, m);
        this.jdField_c_of_type_Int += m;
        i = paramInt2;
      }
      else
      {
        System.arraycopy(paramArrayOfByte, i + paramInt1, arrayOfByte, k, arrayOfByte.length - k);
        arrayOfByte = this.jdField_e_of_type_ArrayOfByte;
        i += arrayOfByte.length - this.jdField_c_of_type_Int;
        this.jdField_c_of_type_Int = arrayOfByte.length;
      }
      a(false);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\gzip\GZipOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */