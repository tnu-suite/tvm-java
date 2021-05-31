package com.techzealot.tvm.hotspot.src.share.vm.oops;

import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantClassInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantNameAndTypeInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantUtf8Info;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.AbstractConstantMemberRefInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolItem;
import com.techzealot.tvm.hotspot.src.share.vm.utilities.PrintUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
public class ConstantPool {

    private Klass klass;

    //u2
    private int length;

    //ux
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<Integer, ConstantPoolItem> dataMap;

    public void init() {
        dataMap = new HashMap<>(length);
    }

    public <T extends ConstantPoolItem> T get(int index, Class<T> clazz) {
        return clazz.cast(dataMap.get(index));
    }

    public ConstantPoolItem get(int index) {
        return dataMap.get(index);
    }

    public void put(int index, ConstantPoolItem constantPoolItem) {
        dataMap.put(index, constantPoolItem);
    }

    /**
     * 获取成员所在类或接口的全限定名 需要将/转为.
     *
     * @param index
     * @return
     */
    public String getMemberContainerClass(int index) {
        AbstractConstantMemberRefInfo memberRefInfo = get(index, AbstractConstantMemberRefInfo.class);
        int classNameIndex = get(memberRefInfo.getClassIndex(), ConstantClassInfo.class).getNameIndex();
        return get(classNameIndex, ConstantUtf8Info.class).getValue().replace("/", ".");
    }

    /**
     * 获取类或接口成员的名称
     *
     * @param index
     * @return
     */
    public String getMemberName(int index) {
        AbstractConstantMemberRefInfo memberRefInfo = get(index, AbstractConstantMemberRefInfo.class);
        ConstantNameAndTypeInfo nameAndTypeInfo = get(memberRefInfo.getNameAndTypeIndex(), ConstantNameAndTypeInfo.class);
        return get(nameAndTypeInfo.getNameIndex(), ConstantUtf8Info.class).getValue();
    }

    public String getMemberDescriptor(int index) {
        AbstractConstantMemberRefInfo memberRefInfo = get(index, AbstractConstantMemberRefInfo.class);
        ConstantNameAndTypeInfo nameAndTypeInfo = get(memberRefInfo.getNameAndTypeIndex(), ConstantNameAndTypeInfo.class);
        return get(nameAndTypeInfo.getDescriptorIndex(), ConstantUtf8Info.class).getValue();
    }


    public void print() {
        log.info("constant pool class :{}, size: {}", this.getKlass(), this.getLength());
        log.info("constant pool items:");
        PrintUtils.printMap(dataMap);
    }

    public void validate() {
        log.error("not implement");
    }
}
