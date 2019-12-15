package com.tencent.smtt.webkit.icu.text;

import java.util.Arrays;

abstract class CharsetRecog_mbcs
  extends CharsetRecognizer
{
  int a(a parama, int[] paramArrayOfInt)
  {
    e locale = new e();
    locale.a();
    int k = 0;
    int n = 0;
    int j = 0;
    int i = 0;
    while (nextChar(locale, parama))
    {
      int i3 = n + 1;
      int i1;
      int m;
      int i2;
      if (locale.jdField_a_of_type_Boolean)
      {
        i1 = j + 1;
        m = k;
        i2 = i;
      }
      else
      {
        long l = locale.jdField_a_of_type_Int & 0xFFFFFFFF;
        if (l <= 255L)
        {
          m = k;
          i1 = j;
          i2 = i;
        }
        else
        {
          k += 1;
          m = k;
          i1 = j;
          i2 = i;
          if (paramArrayOfInt != null)
          {
            m = k;
            i1 = j;
            i2 = i;
            if (Arrays.binarySearch(paramArrayOfInt, (int)l) >= 0)
            {
              i2 = i + 1;
              i1 = j;
              m = k;
            }
          }
        }
      }
      k = m;
      n = i3;
      j = i1;
      i = i2;
      if (i1 >= 2)
      {
        k = m;
        n = i3;
        j = i1;
        i = i2;
        if (i1 * 5 >= m) {
          return 0;
        }
      }
    }
    if ((k <= 10) && (j == 0))
    {
      if ((k == 0) && (n < 10)) {
        return 0;
      }
      return 10;
    }
    j *= 20;
    if (k < j) {
      return 0;
    }
    if (paramArrayOfInt == null)
    {
      j = k + 30 - j;
      i = j;
      if (j > 100) {
        return 100;
      }
    }
    else
    {
      double d = 90.0D / Math.log(k / 4.0F);
      i = Math.min((int)(Math.log(i + 1) * d + 10.0D), 100);
    }
    return i;
  }
  
  abstract String getName();
  
  abstract boolean nextChar(e parame, a parama);
  
  static class a
    extends CharsetRecog_mbcs
  {
    static int[] a = { 41280, 41281, 41282, 41283, 41287, 41289, 41333, 41334, 42048, 42054, 42055, 42056, 42065, 42068, 42071, 42084, 42090, 42092, 42103, 42147, 42148, 42151, 42177, 42190, 42193, 42207, 42216, 42237, 42304, 42312, 42328, 42345, 42445, 42471, 42583, 42593, 42594, 42600, 42608, 42664, 42675, 42681, 42707, 42715, 42726, 42738, 42816, 42833, 42841, 42970, 43171, 43173, 43181, 43217, 43219, 43236, 43260, 43456, 43474, 43507, 43627, 43706, 43710, 43724, 43772, 44103, 44111, 44208, 44242, 44377, 44745, 45024, 45290, 45423, 45747, 45764, 45935, 46156, 46158, 46412, 46501, 46525, 46544, 46552, 46705, 47085, 47207, 47428, 47832, 47940, 48033, 48593, 49860, 50105, 50240, 50271 };
    
    public String a()
    {
      return "zh";
    }
    
    String getName()
    {
      return "Big5";
    }
    
    b match(a parama)
    {
      int i = a(parama, a);
      if (i == 0) {
        return null;
      }
      return new b(parama, this, i);
    }
    
    boolean nextChar(CharsetRecog_mbcs.e parame, a parama)
    {
      parame.b = parame.c;
      parame.jdField_a_of_type_Boolean = false;
      int i = parame.a(parama);
      parame.jdField_a_of_type_Int = i;
      if (i < 0) {
        return false;
      }
      if (i > 127)
      {
        if (i == 255) {
          return true;
        }
        i = parame.a(parama);
        if (i < 0) {
          return false;
        }
        parame.jdField_a_of_type_Int = (parame.jdField_a_of_type_Int << 8 | i);
        if ((i < 64) || (i == 127) || (i == 255)) {
          parame.jdField_a_of_type_Boolean = true;
        }
        return true;
      }
      return true;
    }
  }
  
  static abstract class b
    extends CharsetRecog_mbcs
  {
    boolean nextChar(CharsetRecog_mbcs.e parame, a parama)
    {
      parame.jdField_b_of_type_Int = parame.c;
      parame.jdField_a_of_type_Boolean = false;
      int i = parame.a(parama);
      parame.jdField_a_of_type_Int = i;
      if (i < 0)
      {
        parame.jdField_b_of_type_Boolean = true;
      }
      else if (i > 141)
      {
        int j = parame.a(parama);
        parame.jdField_a_of_type_Int = (parame.jdField_a_of_type_Int << 8 | j);
        if ((i >= 161) && (i <= 254))
        {
          if (j < 161) {
            parame.jdField_a_of_type_Boolean = true;
          }
        }
        else if (i == 142)
        {
          if (j < 161) {
            parame.jdField_a_of_type_Boolean = true;
          }
        }
        else if (i == 143)
        {
          i = parame.a(parama);
          parame.jdField_a_of_type_Int = (parame.jdField_a_of_type_Int << 8 | i);
          if (i < 161) {
            parame.jdField_a_of_type_Boolean = true;
          }
        }
      }
      return parame.jdField_b_of_type_Boolean ^ true;
    }
    
    static class a
      extends CharsetRecog_mbcs.b
    {
      static int[] a = { 41377, 41378, 41379, 41382, 41404, 41418, 41419, 41430, 41431, 42146, 42148, 42150, 42152, 42154, 42155, 42156, 42157, 42159, 42161, 42163, 42165, 42167, 42169, 42171, 42173, 42175, 42176, 42177, 42179, 42180, 42182, 42183, 42184, 42185, 42186, 42187, 42190, 42191, 42192, 42206, 42207, 42209, 42210, 42212, 42216, 42217, 42218, 42219, 42220, 42223, 42226, 42227, 42402, 42403, 42404, 42406, 42407, 42410, 42413, 42415, 42416, 42419, 42421, 42423, 42424, 42425, 42431, 42435, 42438, 42439, 42440, 42441, 42443, 42448, 42453, 42454, 42455, 42462, 42464, 42465, 42469, 42473, 42474, 42475, 42476, 42477, 42483, 47273, 47572, 47854, 48072, 48880, 49079, 50410, 50940, 51133, 51896, 51955, 52188, 52689 };
      
      public String a()
      {
        return "ja";
      }
      
      String getName()
      {
        return "EUC-JP";
      }
      
      b match(a parama)
      {
        int i = a(parama, a);
        if (i == 0) {
          return null;
        }
        return new b(parama, this, i);
      }
    }
    
    static class b
      extends CharsetRecog_mbcs.b
    {
      static int[] a = { 45217, 45235, 45253, 45261, 45268, 45286, 45293, 45304, 45306, 45308, 45496, 45497, 45511, 45527, 45538, 45994, 46011, 46274, 46287, 46297, 46315, 46501, 46517, 46527, 46535, 46569, 46835, 47023, 47042, 47054, 47270, 47278, 47286, 47288, 47291, 47337, 47531, 47534, 47564, 47566, 47613, 47800, 47822, 47824, 47857, 48103, 48115, 48125, 48301, 48314, 48338, 48374, 48570, 48576, 48579, 48581, 48838, 48840, 48863, 48878, 48888, 48890, 49057, 49065, 49088, 49124, 49131, 49132, 49144, 49319, 49327, 49336, 49338, 49339, 49341, 49351, 49356, 49358, 49359, 49366, 49370, 49381, 49403, 49404, 49572, 49574, 49590, 49622, 49631, 49654, 49656, 50337, 50637, 50862, 51151, 51153, 51154, 51160, 51173, 51373 };
      
      public String a()
      {
        return "ko";
      }
      
      String getName()
      {
        return "EUC-KR";
      }
      
      b match(a parama)
      {
        int i = a(parama, a);
        if (i == 0) {
          return null;
        }
        return new b(parama, this, i);
      }
    }
  }
  
  static class c
    extends CharsetRecog_mbcs
  {
    static int[] a = { 41377, 41378, 41379, 41380, 41392, 41393, 41457, 41459, 41889, 41900, 41914, 45480, 45496, 45502, 45755, 46025, 46070, 46323, 46525, 46532, 46563, 46767, 46804, 46816, 47010, 47016, 47037, 47062, 47069, 47284, 47327, 47350, 47531, 47561, 47576, 47610, 47613, 47821, 48039, 48086, 48097, 48122, 48316, 48347, 48382, 48588, 48845, 48861, 49076, 49094, 49097, 49332, 49389, 49611, 49883, 50119, 50396, 50410, 50636, 50935, 51192, 51371, 51403, 51413, 51431, 51663, 51706, 51889, 51893, 51911, 51920, 51926, 51957, 51965, 52460, 52728, 52906, 52932, 52946, 52965, 53173, 53186, 53206, 53442, 53445, 53456, 53460, 53671, 53930, 53938, 53941, 53947, 53972, 54211, 54224, 54269, 54466, 54490, 54754, 54992 };
    
    public String a()
    {
      return "zh";
    }
    
    String getName()
    {
      return "GB18030";
    }
    
    b match(a parama)
    {
      int i = a(parama, a);
      if (i == 0) {
        return null;
      }
      return new b(parama, this, i);
    }
    
    boolean nextChar(CharsetRecog_mbcs.e parame, a parama)
    {
      parame.jdField_b_of_type_Int = parame.c;
      parame.jdField_a_of_type_Boolean = false;
      int i = parame.a(parama);
      parame.jdField_a_of_type_Int = i;
      if (i < 0)
      {
        parame.jdField_b_of_type_Boolean = true;
      }
      else if (i > 128)
      {
        int j = parame.a(parama);
        parame.jdField_a_of_type_Int = (parame.jdField_a_of_type_Int << 8 | j);
        if ((i >= 129) && (i <= 254) && ((j < 64) || (j > 126)) && ((j < 80) || (j > 254)))
        {
          if ((j >= 48) && (j <= 57))
          {
            i = parame.a(parama);
            if ((i >= 129) && (i <= 254))
            {
              j = parame.a(parama);
              if ((j >= 48) && (j <= 57))
              {
                parame.jdField_a_of_type_Int = (j | parame.jdField_a_of_type_Int << 16 | i << 8);
                break label195;
              }
            }
          }
          parame.jdField_a_of_type_Boolean = true;
        }
      }
      label195:
      return parame.jdField_b_of_type_Boolean ^ true;
    }
  }
  
  static class d
    extends CharsetRecog_mbcs
  {
    static int[] a = { 33088, 33089, 33090, 33093, 33115, 33129, 33130, 33141, 33142, 33440, 33442, 33444, 33449, 33450, 33451, 33453, 33455, 33457, 33459, 33461, 33463, 33469, 33470, 33473, 33476, 33477, 33478, 33480, 33481, 33484, 33485, 33500, 33504, 33511, 33512, 33513, 33514, 33520, 33521, 33601, 33603, 33614, 33615, 33624, 33630, 33634, 33639, 33653, 33654, 33673, 33674, 33675, 33677, 33683, 36502, 37882, 38314 };
    
    public String a()
    {
      return "ja";
    }
    
    String getName()
    {
      return "Shift_JIS";
    }
    
    b match(a parama)
    {
      int i = a(parama, a);
      if (i == 0) {
        return null;
      }
      return new b(parama, this, i);
    }
    
    boolean nextChar(CharsetRecog_mbcs.e parame, a parama)
    {
      parame.b = parame.c;
      parame.jdField_a_of_type_Boolean = false;
      int i = parame.a(parama);
      parame.jdField_a_of_type_Int = i;
      if (i < 0) {
        return false;
      }
      if (i > 127)
      {
        if ((i > 160) && (i <= 223)) {
          return true;
        }
        int j = parame.a(parama);
        if (j < 0) {
          return false;
        }
        parame.jdField_a_of_type_Int = (i << 8 | j);
        if (((j < 64) || (j > 127)) && ((j < 128) || (j > 255))) {
          parame.jdField_a_of_type_Boolean = true;
        }
        return true;
      }
      return true;
    }
  }
  
  static class e
  {
    int jdField_a_of_type_Int = 0;
    boolean jdField_a_of_type_Boolean = false;
    int jdField_b_of_type_Int = 0;
    boolean jdField_b_of_type_Boolean = false;
    int c = 0;
    
    int a(a parama)
    {
      if (this.c >= parama.jdField_b_of_type_Int)
      {
        this.jdField_b_of_type_Boolean = true;
        return -1;
      }
      parama = parama.jdField_b_of_type_ArrayOfByte;
      int i = this.c;
      this.c = (i + 1);
      return parama[i] & 0xFF;
    }
    
    void a()
    {
      this.jdField_a_of_type_Int = 0;
      this.jdField_b_of_type_Int = -1;
      this.c = 0;
      this.jdField_a_of_type_Boolean = false;
      this.jdField_b_of_type_Boolean = false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\text\CharsetRecog_mbcs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */