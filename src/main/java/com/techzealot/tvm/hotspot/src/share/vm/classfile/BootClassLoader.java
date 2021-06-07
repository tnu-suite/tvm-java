package com.techzealot.tvm.hotspot.src.share.vm.classfile;

import com.techzealot.tvm.hotspot.src.share.vm.oops.InstanceKlass;
import lombok.Data;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 根类加载器
 */
@Data
public class BootClassLoader {
    public static final String SUFFIX = ".class";

    private static String searchPath = "/Users/techzealot/IdeaProjects/tvm-java/build/classes/java/main";

    /**
     * 存储根类加载器加载的所有类
     */
    private static Map<String, InstanceKlass> classMetadata = new HashMap<>();

    public static InstanceKlass loadKlass(String name) {
        InstanceKlass klass = findLoadedKlass(name);
        if (Objects.nonNull(klass)) {
            return klass;
        }
        klass = readAndParse(name);
        return klass;
    }

    /**
     * 全限定名，形如 java.lang.String
     *
     * @param name
     * @return
     */
    @SneakyThrows
    private static InstanceKlass readAndParse(String name) {
        Path filePath = Paths.get(searchPath, name.replace(".", "/") + SUFFIX);
        InstanceKlass klass = ClassFileParser.parseClassFile(Files.readAllBytes((filePath)));
        classMetadata.put(name, klass);
        return klass;
    }

    private static InstanceKlass findLoadedKlass(String name) {
        return classMetadata.get(name);
    }
}
