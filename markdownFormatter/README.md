# Mardown格式化工具

## Jar包导出方式说明

Eclipse--目标项目--右键--Java--可运行的Jar文件--下一步--Extract required libraries into generated JAR  
Eclipse--目标项目--右键--Java--可运行的Jar文件--下一步--Package required libraries into generated JAR  
Eclipse--目标项目--右键--Java--可运行的Jar文件--下一步--Copy required libraries into a sub-folder next to the generated JAR  

> 验证发现，通过`Extract required libraries into generated JAR`导出的jar包运行最快。  

## Mardown格式化工具使用的导出方式

使用`Extract required libraries into generated JAR`方式导出。  

由于`flexmark`无法完全满足要求，本工具覆写了其中几个类。在导出过程中，本工具实现的类优先导出，`flexmark-*.jar`的类随后导出，当导出工具发现已经存在同路径同名的类时，会忽略后面的类，然后只是在窗口提示存在重复的类，这样就达到了覆盖`flexmark-*.jar`的类的目的。  
