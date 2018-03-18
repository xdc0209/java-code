package com.xdc.basic.tools.restserver.jersey.application.resource.school;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import com.xdc.basic.tools.restserver.jersey.application.database.school.StudentDao;
import com.xdc.basic.tools.restserver.jersey.application.domain.school.Student;
import com.xdc.basic.tools.restserver.jersey.application.exceptionmapper.common.RESTServiceException;

// 摘自：http://liugang594.iteye.com/blog/1491434
// JAX-RS是一套用java实现REST服务的规范，提供了一些标注将一个资源类，一个POJOJava类，封装为Web资源。标注包括：
// @Path，标注资源类或方法的相对路径
// @GET，@PUT，@POST，@DELETE，标注方法是用的HTTP请求的类型
// @Produces，标注返回的MIME媒体类型
// @Consumes，标注可接受请求的MIME媒体类型
// @PathParam，@QueryParam，@HeaderParam，@CookieParam，@MatrixParam，@FormParam,分别标注方法的参数来自于HTTP请求的不同位置，例如@PathParam来自于URL的路径，@QueryParam来自于URL的查询参数，@HeaderParam来自于HTTP请求的头信息，@CookieParam来自于HTTP请求的Cookie。

@Path("school/student")
public class StudentResouce
{
    // ----------------------- json start ----------------------------------------
    @POST
    @Path("json/student")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addStudentInJson(Student student)
    {
        if (student.getId() == 0)
        {
            // 生成id
            student.setId(System.currentTimeMillis());
        }
        else if (StudentDao.query(student.getId()) != null)
        {
            throw RESTServiceException.resourceNotUnique("学号冲突。");
        }

        StudentDao.insert(student);
    }

    @DELETE
    @Path("json/student/{id}")
    public void deleteStudentInJson(@PathParam("id") long id)
    {
        StudentDao.delete(id);
    }

    @PUT
    @Path("json/student/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void modifyStudentInJson(@PathParam("id") long id, Student student)
    {
        if (StudentDao.query(id) == null)
        {
            throw RESTServiceException.resourceNotExist("此学生不存在。");
        }

        student.setId(id);
        StudentDao.update(student);
    }

    @GET
    @Path("json/student/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudentInJson(@PathParam("id") long id)
    {
        Student student = StudentDao.query(id);
        if (student == null)
        {
            throw RESTServiceException.resourceNotExist("此学生不存在。");
        }

        return student;
    }

    @GET
    @Path("json/student")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getStudentsInJson()
    {
        return StudentDao.queryAll();
    }

    // ----------------------- json end -----------------------------------------

    // ----------------------- xml start ----------------------------------------
    @POST
    @Path("xml/student")
    @Consumes(MediaType.APPLICATION_XML)
    public void addStudentInXml(Student student)
    {
        // 生成id
        student.setId(System.currentTimeMillis());
        StudentDao.insert(student);
    }

    @DELETE
    @Path("xml/student/{id}")
    public void deleteStudentInXml(@PathParam("id") long id)
    {
        StudentDao.delete(id);
    }

    @PUT
    @Path("xml/student/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void modifyStudentInXml(@PathParam("id") long id, Student student)
    {
        student.setId(id);
        StudentDao.update(student);
    }

    @GET
    @Path("xml/student/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Student getStudentInXml(@PathParam("id") long id)
    {
        return StudentDao.query(id);
    }

    @GET
    @Path("xml/student")
    @Produces(MediaType.APPLICATION_XML)
    public List<Student> getStudentsInXml()
    {
        return StudentDao.queryAll();
    }

    @GET
    @Path("xml/student2/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public JAXBElement<Student> getStudentInXml2(@PathParam("id") long id)
    {
        final String XMLNS_NAMESPACE = "http://www.xdc0209.com/rest/service";
        final String ROOT_NODE = "students";

        return new JAXBElement<Student>(new QName(XMLNS_NAMESPACE, ROOT_NODE), Student.class, StudentDao.query(id));
    }
    // ----------------------- xml end ----------------------------------------
}
