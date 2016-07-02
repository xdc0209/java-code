package com.xdc.basic.api.thread.timer;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.api.json.jackson.JsonTool;
import com.xdc.basic.api.thread.executor.threadfactory.NamePrefixThreadFactory;

public class TaskManager
{
    private static Logger                   log   = LoggerFactory.getLogger(TaskManager.class);

    private static Map<File, Task>          tasks = new ConcurrentHashMap<File, Task>();

    // 参数corePoolSize代表线程池中线程个数，即使它们空闲
    private static ScheduledExecutorService exec  = Executors
            .newSingleThreadScheduledExecutor(new NamePrefixThreadFactory("Task-Handle"));

    public static void addTask(File file) throws IOException, ParseException
    {
        log.info("Add task start. file=[{}].", file.getAbsolutePath());

        TaskFile taskFile = parseTaskFile(file);
        Task task = new Task(exec, taskFile);
        task.start();

        tasks.put(file, task);

        log.info("Add task finish. file=[{}].", file.getAbsolutePath());
    }

    public static void removeTask(File file)
    {
        log.info("Remove task start. file=[{}].", file.getAbsolutePath());

        Task task = tasks.get(file);
        if (task != null)
        {
            task.stop();
        }

        tasks.remove(file);

        log.info("Remove task finish. file=[{}].", file.getAbsolutePath());
    }

    public static void updateTask(File file) throws IOException, ParseException
    {
        log.info("Update task start. file=[{}].", file.getAbsolutePath());

        removeTask(file);
        addTask(file);

        log.info("Update task finish. file=[{}].", file.getAbsolutePath());
    }

    private static TaskFile parseTaskFile(File file) throws IOException
    {
        String taskString = FileUtils.readFileToString(file);
        TaskFile taskFile = JsonTool.parse(taskString, TaskFile.class);
        taskFile.setPath(file.getAbsolutePath());
        taskFile.checkAndTrim();
        return taskFile;
    }
}
