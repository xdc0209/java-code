@echo off
title ���ع����ű�

:: if not defined MVN_HOME echo �����趨��������MVN_HOME������ %%MVN_HOME%%\bin; ��ӵ���������PATH�С� && pause && exit

set java_src=%cd%

:: echo Java����·��:     %java_src%
:: echo Settings�ļ�λ��: %java_src%\mvn_settings_windows.xml
:: echo.
::
:: echo ��ȷ��settings�ļ��������뵱ǰ�����·��������������ȷ����X�رմ��ڣ������������������������
:: echo.
:: pause

echo ==============================================================================
echo ���ع�����ʼ: %java_src%
echo ==============================================================================
echo.

:: ��ʼʱ��
set start_time=%date% %time%

:: cd /d %java_src%\com.xdc.soft
:: call mvn source:jar || pause && exit 1
::
:: cd /d %java_src%\com.xdc.soft.virgo
:: call mvn source:jar || pause && exit 1
::
:: cd /d %java_src%\com.xdc.soft.opensource
:: call mvn source:jar || pause && exit 1
::
:: cd /d %java_src%\com.xdc.soft.mini
:: call mvn source:jar || pause && exit 1

call mvn source:jar -s %java_src%\mvn_settings_windows.xml -f %java_src%\com.xdc.soft\pom.xml            || pause && exit 1
call mvn source:jar -s %java_src%\mvn_settings_windows.xml -f %java_src%\com.xdc.soft.virgo\pom.xml      || pause && exit 1
call mvn source:jar -s %java_src%\mvn_settings_windows.xml -f %java_src%\com.xdc.soft.opensource\pom.xml || pause && exit 1
call mvn source:jar -s %java_src%\mvn_settings_windows.xml -f %java_src%\com.xdc.soft.mini\pom.xml       || pause && exit 1
echo.

:: ����ʱ��
set end_time=%date% %time%

echo ==============================================================================
echo ���ع����ɹ�: %java_src%
echo ==============================================================================
echo ��ʼʱ�䣺%start_time%
echo ����ʱ�䣺%end_time%
echo.
pause
