@echo off

:: 注意：此参数为生成后的java代码包路径，请要根据实际情况指定
set java_package=com.xdc.basic.api.jaxb.user
set xml_file=xml-xsd-java.xml
set xsd_file=xml-xsd-java.xsd

:: 通过 "样例xml" 抽象出 "描述文件xsd"
java -jar trang.jar %xml_file% %xsd_file% || pause && exit 1

:: 通过 "描述文件xsd" 生成 "java类"
xjc -xmlschema %xsd_file% -p %java_package% || pause && exit 1

:: 删除 "描述文件xsd"
del %xsd_file% || pause && exit 1

echo java代码生成完成.
pause

