package com.xdc.basic.tools.json2javacode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import com.xdc.basic.api.json.jackson.JsonTool;
import com.xdc.basic.skills.GetPath;
import com.xdc.basic.tools.json2javacode.define.ClassDefine;
import com.xdc.basic.tools.json2javacode.define.FieldDefine;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        String javaPackageName = "com.xdc.soft";
        String objectClassName = "Student";

        // 读取json文件
        File jsonFile = FileUtils.getFile(GetPath.getRelativePath() + "demo.json");
        String jsonString = FileUtils.readFileToString(jsonFile, "UTF8");

        // 先解析成Map类型
        Map<String, Object> map = JsonTool.parseToMap(jsonString);

        // 生成类型定义
        List<ClassDefine> classDefines = generateClassDefines(javaPackageName, objectClassName, map);

        // 生成java代码并写入文件
        for (ClassDefine classDefine : classDefines)
        {
            String javaCode = generateJavaCode(classDefine);
            System.out.println(javaCode);

            writeStringToFile(classDefine, javaCode);
        }
    }

    /**
     * 保存java代码，默认保存到工程的源码目录
     * 
     * @param classDefine
     * @param javaCode
     * @throws IOException
     */
    private static void writeStringToFile(ClassDefine classDefine, String javaCode) throws IOException
    {
        String javaPackageName = classDefine.getJavaPackageName();
        String objectClassName = classDefine.getObjectClassName();

        String packagePath = "src\\" + javaPackageName.replace(".", "\\") + "\\";

        // 不管存不存在，创建一下目录
        File packageDir = FileUtils.getFile(packagePath);
        packageDir.mkdirs();

        // 写入文件
        File file = FileUtils.getFile(packagePath + objectClassName + ".java");
        FileUtils.writeStringToFile(file, javaCode, "UTF8");
    }

    private static final String package_header      = "package ";
    private static final String import_header       = "import ";
    private static final String class_header        = "public class ";
    private static final String field_header        = "private ";
    private static final String get_method_header   = "public ";
    private static final String set_method_header   = "public void ";
    private static final String get_method          = "get";
    private static final String set_method          = "set";
    private static final String return_header       = "return ";
    private static final String this_header         = "this.";

    private static final String line_end            = SystemUtils.LINE_SEPARATOR;
    private static final String indentation         = "    ";
    private static final String code_end            = ";" + line_end;
    private static final String brace_start         = "{" + line_end;
    private static final String brace_end           = "}" + line_end;
    private static final String parenthesis_start   = "(";
    private static final String parenthesis_end     = ")";
    private static final String angle_bracket_start = "<";
    private static final String angle_bracket_end   = ">";

    // PS： These are Parentheses, Square brackets, Angle brackets and Brace.
    // 提示：括号有小括号 、 中括号 、 角括号和大括号.

    /**
     * 根据类定义生成java代码
     * 
     * @param classDefine
     * @return
     */
    private static String generateJavaCode(ClassDefine classDefine)
    {
        String javaPackageName = classDefine.getJavaPackageName();
        String objectClassName = classDefine.getObjectClassName();
        List<FieldDefine> fields = classDefine.getFields();

        if (StringUtils.isBlank(objectClassName))
        {
            throw new IllegalArgumentException("objectClassName can not be blank.");
        }

        StringBuilder sb = new StringBuilder();

        // 生成包名
        if (StringUtils.isNotBlank(javaPackageName))
        {
            sb.append(package_header).append(javaPackageName).append(code_end).append(line_end);
        }

        // 生成导入包语句
        List<String> importList = new ArrayList<String>();
        for (FieldDefine fieldDefine : fields)
        {
            String type = fieldDefine.getType();
            String parameterizedType = fieldDefine.getParameterizedType();

            if (StringUtils.isNotBlank(type) && !importList.contains(type))
            {
                importList.add(type);
            }

            if (StringUtils.isNotBlank(parameterizedType) && !importList.contains(parameterizedType))
            {
                importList.add(parameterizedType);
            }
        }
        Collections.sort(importList);
        for (String importString : importList)
        {
            sb.append(import_header).append(importString).append(code_end);
        }
        if (!importList.isEmpty())
        {
            sb.append(line_end);
        }

        // 生成类名
        sb.append(class_header).append(objectClassName).append(line_end);

        // 大括号开始
        sb.append(brace_start);

        // 生成字段
        for (FieldDefine fieldDefine : fields)
        {
            String name = fieldDefine.getName();
            String type = fieldDefine.getType();
            String parameterizedType = fieldDefine.getParameterizedType();

            sb.append(indentation).append(field_header);
            sb.append(StringUtils.substringAfterLast(type, "."));
            if (StringUtils.isNotBlank(parameterizedType))
            {
                sb.append(angle_bracket_start);
                sb.append(StringUtils.substringAfterLast(parameterizedType, "."));
                sb.append(angle_bracket_end);
            }
            sb.append(" ").append(name).append(code_end).append(line_end);
        }

        // 生成getter、setter方法
        for (FieldDefine fieldDefine : fields)
        {
            String name = fieldDefine.getName();
            String type = fieldDefine.getType();
            String parameterizedType = fieldDefine.getParameterizedType();

            // getter方法
            sb.append(indentation).append(get_method_header);
            sb.append(StringUtils.substringAfterLast(type, "."));
            if (StringUtils.isNotBlank(parameterizedType))
            {
                sb.append(angle_bracket_start);
                sb.append(StringUtils.substringAfterLast(parameterizedType, "."));
                sb.append(angle_bracket_end);
            }
            sb.append(" ").append(get_method).append(StringUtils.capitalize(name));
            sb.append(parenthesis_start).append(parenthesis_end).append(line_end);
            sb.append(indentation).append(brace_start);
            sb.append(indentation).append(indentation).append(return_header).append(name).append(code_end);
            sb.append(indentation).append(brace_end).append(line_end);

            // setter方法
            sb.append(indentation).append(set_method_header);
            sb.append(set_method).append(StringUtils.capitalize(name));
            sb.append(parenthesis_start);
            sb.append(StringUtils.substringAfterLast(type, "."));
            if (StringUtils.isNotBlank(parameterizedType))
            {
                sb.append(angle_bracket_start);
                sb.append(StringUtils.substringAfterLast(parameterizedType, "."));
                sb.append(angle_bracket_end);
            }
            sb.append(" ").append(name);
            sb.append(parenthesis_end).append(line_end);
            sb.append(indentation).append(brace_start);
            sb.append(indentation).append(indentation);
            sb.append(this_header).append(name).append(" = ").append(name).append(code_end);
            sb.append(indentation).append(brace_end).append(line_end);
        }

        // 大括号结束
        sb.append(brace_end);

        return sb.toString();
    }

    /**
     * 生成类定义
     * 
     * 1.不支持数组的多级嵌套[ [ "a", "b", "c" ], [ "a", "b", "c" ] ]，对应的java类型为 List<List<String>> 因这种json在一般场景下不会使用，不予支持。
     * 2.对于null或空数组将被解析成Object
     * 
     * @param javaPackageName
     * @param objectClassName
     * @param map
     * @return List<ClassDefine>
     */
    @SuppressWarnings("unchecked")
    private static List<ClassDefine> generateClassDefines(String javaPackageName, String objectClassName,
            Map<String, Object> map)
    {
        // 存放结果
        List<ClassDefine> classDefines = new ArrayList<ClassDefine>();

        ClassDefine classDefine = new ClassDefine(javaPackageName, objectClassName);
        for (Entry<String, Object> entry : map.entrySet())
        {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null)
            {
                // 值为null，将被解析成Obect类型
                value = new Object();
            }

            String fieldName = key;
            String fieldType = value.getClass().getCanonicalName();
            String fieldParameterizedType = null;

            // 值为Map类型时, 需递归处理
            if (value instanceof Map)
            {
                // key首字母大写，作为新类名
                String fieldClassName = StringUtils.capitalize(key);
                fieldType = javaPackageName + "." + fieldClassName;

                List<ClassDefine> fieldClassDefines = generateClassDefines(javaPackageName, fieldClassName,
                        (Map<String, Object>) value);

                classDefines.addAll(fieldClassDefines);
            }
            // 值为List类型时，需要泛型化处理
            else if (value instanceof List)
            {
                List<Object> list = (List<Object>) value;
                if (CollectionUtils.isNotEmpty(list))
                {
                    // 使用接口List，更符合类定义的习惯
                    fieldType = List.class.getCanonicalName();

                    Object element = list.get(0);
                    fieldParameterizedType = element.getClass().getCanonicalName();

                    // 值为Map类型时, 需递归处理
                    if (element instanceof Map)
                    {
                        String fieldClassName = StringUtils.capitalize(key);
                        fieldParameterizedType = javaPackageName + "." + fieldClassName;

                        List<ClassDefine> fieldClassDefines = generateClassDefines(javaPackageName, fieldClassName,
                                (Map<String, Object>) element);

                        classDefines.addAll(fieldClassDefines);
                    }
                }
                else
                {
                    // 列表为空，泛型参数处理为Object类型
                    fieldType = List.class.getCanonicalName();
                    fieldParameterizedType = Object.class.getCanonicalName();
                }
            }

            classDefine.addField(fieldName, fieldType, fieldParameterizedType);
        }
        classDefines.add(classDefine);

        return classDefines;
    }
}
