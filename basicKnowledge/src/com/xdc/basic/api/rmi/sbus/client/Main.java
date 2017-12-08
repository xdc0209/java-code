package com.xdc.basic.api.rmi.sbus.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.api.rmi.sbus.server.SbusServer;
import com.xdc.basic.api.rmi.sbus.test.entity.Student;
import com.xdc.basic.api.rmi.sbus.test.intf.StudentService;
import com.xdc.basic.api.rmi.sbus.transport.MessageClient;
import com.xdc.basic.api.rmi.sbus.transport.socket.SocketClient;

public class Main
{
    private static Logger log = LoggerFactory.getLogger(SbusServer.class);

    public static void main(String[] args) throws Throwable
    {
        MessageClient messageClient = new SocketClient("127.0.0.1", 1989);

        StudentService studentService = SbusProxyManager.getSbusProxy(StudentService.class, messageClient);

        Student student = new Student();
        student.setId("20082890");
        student.setName("xudachao");
        student.setAge(25);

        Student student2 = new Student();
        student2.setId("20082891");
        student2.setName("wangziwei");
        student2.setAge(24);

        studentService.addStudent(student);
        studentService.addStudent(student2);
        List<Student> students = studentService.getStudents();
        log.info("Get all students after adding. Students=[{}].", students);

        studentService.delStudent(student2);
        students = studentService.getStudents();
        log.info("Get all students after deleting. Students=[{}].", students);
    }
}
