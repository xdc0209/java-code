@echo off
title 本地构建脚本

:: if not defined MVN_HOME echo 请先设定环境变量MVN_HOME，并将 %%MVN_HOME%%\bin; 添加到环境变量PATH中。 && pause && exit

set java_src=%cd%

:: echo Java代码路径:     %java_src%
:: echo Settings文件位置: %java_src%\mvn_settings_windows.xml
:: echo.
::
:: echo 请确认settings文件的配置与当前代码库路径相符。如果不正确，点X关闭窗口！！！否则任意键继续。。。
:: echo.
:: pause

echo ==============================================================================
echo 本地构建开始: %java_src%
echo ==============================================================================
echo.

:: 开始时间
set start_time=%date% %time%

rmdir /S /Q %java_src%\virgo 2>nul

:: cd /d %java_src%\com.xdc.soft
:: call mvn clean install -Dmaven.test.skip=true || pause && exit 1
::
:: cd /d %java_src%\com.xdc.soft.virgo
:: call mvn clean install -Dmaven.test.skip=true || pause && exit 1
::
:: cd /d %java_src%\com.xdc.soft.opensource
:: call mvn clean install -Dmaven.test.skip=true || pause && exit 1
::
:: cd /d %java_src%\com.xdc.soft.mini
:: call mvn clean install -Dmaven.test.skip=true || pause && exit 1

call mvn clean install -Dmaven.test.skip=true -s %java_src%\mvn_settings_windows.xml -f %java_src%\com.xdc.soft\pom.xml            || pause && exit 1
call mvn clean install -Dmaven.test.skip=true -s %java_src%\mvn_settings_windows.xml -f %java_src%\com.xdc.soft.virgo\pom.xml      || pause && exit 1
call mvn clean install -Dmaven.test.skip=true -s %java_src%\mvn_settings_windows.xml -f %java_src%\com.xdc.soft.opensource\pom.xml || pause && exit 1
call mvn clean install -Dmaven.test.skip=true -s %java_src%\mvn_settings_windows.xml -f %java_src%\com.xdc.soft.mini\pom.xml       || pause && exit 1
echo.

echo 复制资源：%java_src%\com.xdc.soft.virgo\release\virgo
xcopy /E /Y %java_src%\com.xdc.soft.virgo\release\virgo %java_src%\virgo
echo.

echo 复制资源：%java_src%\com.xdc.soft.mini\release\virgo
xcopy /E /Y %java_src%\com.xdc.soft.mini\release\virgo %java_src%\virgo
echo.

:: 结束时间
set end_time=%date% %time%

echo ==============================================================================
echo 本地构建成功: %java_src%
echo ==============================================================================
echo 开始时间：%start_time%
echo 结束时间：%end_time%
echo.
pause
