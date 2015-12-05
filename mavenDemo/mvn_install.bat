@echo off
title ���ع����ű�

:: if not defined MVN_HOME echo �����趨��������MVN_HOME������ %%MVN_HOME%%\bin; ��ӵ���������PATH�С� && pause && exit

set java_src=%cd%

:: echo Java����·��:     %java_src%
:: echo Settings�ļ�λ��: %java_src%\mvn_settings.xml
:: echo.
::
:: echo ��ȷ��settings�ļ��������뵱ǰ�����·��������������ȷ����X�رմ��ڣ������������������������
:: echo.
:: pause

echo.
echo ==============================================================================
echo ���ع�����ʼ: %java_src%
echo ==============================================================================
echo.

:: ��ʼʱ��
set start_time=%date% %time%

:: cd /d %java_src%\com.xdc.soft
:: call mvn clean install -Dmaven.test.skip=true || pause && exit 1
::
:: cd /d %java_src%\com.xdc.soft.mini
:: call mvn clean install -Dmaven.test.skip=true || pause && exit 1
::
:: cd /d %java_src%\com.xdc.soft.opensource
:: call mvn clean install -Dmaven.test.skip=true || pause && exit 1

call mvn clean install -Dmaven.test.skip=true -s %java_src%\mvn_settings.xml -f %java_src%\com.xdc.soft\pom.xml            || pause && exit 1
call mvn clean install -Dmaven.test.skip=true -s %java_src%\mvn_settings.xml -f %java_src%\com.xdc.soft.mini\pom.xml       || pause && exit 1
call mvn clean install -Dmaven.test.skip=true -s %java_src%\mvn_settings.xml -f %java_src%\com.xdc.soft.opensource\pom.xml || pause && exit 1

:: ����ʱ��
set end_time=%date% %time%

echo.
echo ==============================================================================
echo ���ع����ɹ�: %java_src%
echo ==============================================================================
echo ��ʼʱ�䣺%start_time%
echo ����ʱ�䣺%end_time%
echo.
pause
