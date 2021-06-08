package com.techzealot.tvm.hotspot.src.share.vm.interpreter;

public class Bytecodes {

    public static final int _nop = 0;
    public static final int _aconst_null = 1;
    public static final int _iconst_m1 = 2;
    public static final int _iconst_0 = 3;
    public static final int _iconst_1 = 4;
    public static final int _iconst_2 = 5;
    public static final int _iconst_3 = 6;
    public static final int _iconst_4 = 7;
    public static final int _iconst_5 = 8;
    public static final int _lconst_0 = 9;
    public static final int _lconst_1 = 10;
    public static final int _fconst_0 = 11;
    public static final int _fconst_1 = 12;
    public static final int _fconst_2 = 13;
    public static final int _dconst_0 = 14;
    public static final int _dconst_1 = 15;
    public static final int _bipush = 16;
    public static final int _sipush = 17;
    public static final int _ldc = 18;
    public static final int _ldc_w = 19;
    public static final int _ldc2_w = 20;
    public static final int _iload = 21;
    public static final int _lload = 22;
    public static final int _fload = 23;
    public static final int _dload = 24;
    public static final int _aload = 25;
    public static final int _iload_0 = 26;
    public static final int _iload_1 = 27;
    public static final int _iload_2 = 28;
    public static final int _iload_3 = 29;
    public static final int _lload_0 = 30;
    public static final int _lload_1 = 31;
    public static final int _lload_2 = 32;
    public static final int _lload_3 = 33;
    public static final int _fload_0 = 34;
    public static final int _fload_1 = 35;
    public static final int _fload_2 = 36;
    public static final int _fload_3 = 37;
    public static final int _dload_0 = 38;
    public static final int _dload_1 = 39;
    public static final int _dload_2 = 40;
    public static final int _dload_3 = 41;
    public static final int _aload_0 = 42;
    public static final int _aload_1 = 43;
    public static final int _aload_2 = 44;
    public static final int _aload_3 = 45;
    public static final int _iaload = 46;
    public static final int _laload = 47;
    public static final int _faload = 48;
    public static final int _daload = 49;
    public static final int _aaload = 50;
    public static final int _baload = 51;
    public static final int _caload = 52;
    public static final int _saload = 53;
    public static final int _istore = 54;
    public static final int _lstore = 55;
    public static final int _fstore = 56;
    public static final int _dstore = 57;
    public static final int _astore = 58;
    public static final int _istore_0 = 59;
    public static final int _istore_1 = 60;
    public static final int _istore_2 = 61;
    public static final int _istore_3 = 62;
    public static final int _lstore_0 = 63;
    public static final int _lstore_1 = 64;
    public static final int _lstore_2 = 65;
    public static final int _lstore_3 = 66;
    public static final int _fstore_0 = 67;
    public static final int _fstore_1 = 68;
    public static final int _fstore_2 = 69;
    public static final int _fstore_3 = 70;
    public static final int _dstore_0 = 71;
    public static final int _dstore_1 = 72;
    public static final int _dstore_2 = 73;
    public static final int _dstore_3 = 74;
    public static final int _astore_0 = 75;
    public static final int _astore_1 = 76;
    public static final int _astore_2 = 77;
    public static final int _astore_3 = 78;
    public static final int _iastore = 79;
    public static final int _lastore = 80;
    public static final int _fastore = 81;
    public static final int _dastore = 82;
    public static final int _aastore = 83;
    public static final int _bastore = 84;
    public static final int _castore = 85;
    public static final int _sastore = 86;
    public static final int _pop = 87;
    public static final int _pop2 = 88;
    public static final int _dup = 89;
    public static final int _dup_x1 = 90;
    public static final int _dup_x2 = 91;
    public static final int _dup2 = 92;
    public static final int _dup2_x1 = 93;
    public static final int _dup2_x2 = 94;
    public static final int _swap = 95;
    public static final int _iadd = 96;
    public static final int _ladd = 97;
    public static final int _fadd = 98;
    public static final int _dadd = 99;
    public static final int _isub = 100;
    public static final int _lsub = 101;
    public static final int _fsub = 102;
    public static final int _dsub = 103;
    public static final int _imul = 104;
    public static final int _lmul = 105;
    public static final int _fmul = 106;
    public static final int _dmul = 107;
    public static final int _idiv = 108;
    public static final int _ldiv = 109;
    public static final int _fdiv = 110;
    public static final int _ddiv = 111;
    public static final int _irem = 112;
    public static final int _lrem = 113;
    public static final int _frem = 114;
    public static final int _drem = 115;
    public static final int _ineg = 116;
    public static final int _lneg = 117;
    public static final int _fneg = 118;
    public static final int _dneg = 119;
    public static final int _ishl = 120;
    public static final int _lshl = 121;
    public static final int _ishr = 122;
    public static final int _lshr = 123;
    public static final int _iushr = 124;
    public static final int _lushr = 125;
    public static final int _iand = 126;
    public static final int _land = 127;
    public static final int _ior = 128;
    public static final int _lor = 129;
    public static final int _ixor = 130;
    public static final int _lxor = 131;
    public static final int _iinc = 132;
    public static final int _i2l = 133;
    public static final int _i2f = 134;
    public static final int _i2d = 135;
    public static final int _l2i = 136;
    public static final int _l2f = 137;
    public static final int _l2d = 138;
    public static final int _f2i = 139;
    public static final int _f2l = 140;
    public static final int _f2d = 141;
    public static final int _d2i = 142;
    public static final int _d2l = 143;
    public static final int _d2f = 144;
    public static final int _i2b = 145;
    public static final int _i2c = 146;
    public static final int _i2s = 147;
    public static final int _lcmp = 148;
    public static final int _fcmpl = 149;
    public static final int _fcmpg = 150;
    public static final int _dcmpl = 151;
    public static final int _dcmpg = 152;
    public static final int _ifeq = 153;
    public static final int _ifne = 154;
    public static final int _iflt = 155;
    public static final int _ifge = 156;
    public static final int _ifgt = 157;
    public static final int _ifle = 158;
    public static final int _if_icmpeq = 159;
    public static final int _if_icmpne = 160;
    public static final int _if_icmplt = 161;
    public static final int _if_icmpge = 162;
    public static final int _if_icmpgt = 163;
    public static final int _if_icmple = 164;
    public static final int _if_acmpeq = 165;
    public static final int _if_acmpne = 166;
    public static final int _goto = 167;
    public static final int _jsr = 168;
    public static final int _ret = 169;
    public static final int _tableswitch = 170;
    public static final int _lookupswitch = 171;
    public static final int _ireturn = 172;
    public static final int _lreturn = 173;
    public static final int _freturn = 174;
    public static final int _dreturn = 175;
    public static final int _areturn = 176;
    public static final int _return = 177;
    public static final int _getstatic = 178;
    public static final int _putstatic = 179;
    public static final int _getfield = 180;
    public static final int _putfield = 181;
    public static final int _invokevirtual = 182;
    public static final int _invokespecial = 183;
    public static final int _invokestatic = 184;
    public static final int _invokeinterface = 185;
    public static final int _invokedynamic = 186;
    public static final int _new = 187;
    public static final int _newarray = 188;
    public static final int _anewarray = 189;
    public static final int _arraylength = 190;
    public static final int _athrow = 191;
    public static final int _checkcast = 192;
    public static final int _instanceof = 193;
    public static final int _monitorenter = 194;
    public static final int _monitorexit = 195;
    public static final int _wide = 196;
    public static final int _multianewarray = 197;
    public static final int _ifnull = 198;
    public static final int _ifnonnull = 199;
    public static final int _goto_w = 200;
    public static final int _jsr_w = 201;
    public static final int _breakpoint = 202;
    public static final int number_of_java_codes = 203;
}
