@echo off

:: ע�⣺�˲���Ϊ���ɺ��java�����·������Ҫ����ʵ�����ָ��
set java_package=com.xdc.basic.api.jaxb.user
set xml_file=xml-xsd-java.xml
set xsd_file=xml-xsd-java.xsd

:: ͨ�� "����xml" ����� "�����ļ�xsd"
java -jar trang.jar %xml_file% %xsd_file% || pause && exit 1

:: ͨ�� "�����ļ�xsd" ���� "java��"
xjc -xmlschema %xsd_file% -p %java_package% || pause && exit 1

:: ɾ�� "�����ļ�xsd"
del %xsd_file% || pause && exit 1

echo java�����������.
pause
