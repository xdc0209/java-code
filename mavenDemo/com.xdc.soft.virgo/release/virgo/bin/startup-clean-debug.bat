@echo off
 
set SCRIPT_DIR=%~dp0

:: 启动virgo: 清理、打开调试端口、等待调试连接 
:: call "%SCRIPT_DIR%startup.bat" -clean -debug 8000 -suspend

:: 启动virgo: 清理、打开调试端口 
call "%SCRIPT_DIR%startup.bat" -clean -debug 8000
