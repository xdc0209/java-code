@echo off
 
set SCRIPT_DIR=%~dp0
call "%SCRIPT_DIR%setupVars.bat"

"%JAVA_HOME%\bin\java" -classpath "%CLASSPATH%" com.xdc.basic.api.jmx.virgo.client.VirgoMain plan

