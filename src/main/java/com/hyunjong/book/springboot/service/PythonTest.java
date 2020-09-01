package com.hyunjong.book.springboot.service;

import lombok.RequiredArgsConstructor;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


@RequiredArgsConstructor
@Service
public class PythonTest {
    private static PythonInterpreter intPre;

    public String pythontest(){
        Properties props = new Properties();
        props.put("python.home", "C:/Users/jython-standalone-2.7.2");
        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
        props.put("python.import.site", "false");

        Properties preprops = System.getProperties();

        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter intPre = new PythonInterpreter();

        intPre.execfile("src/main/java/com/hyunjong/book/springboot/web/test.py");
        //intPre.exec("print(testFunc(5,10))");

        PyFunction pyFunction = (PyFunction) intPre.get("testFunc", PyFunction.class);
        int a = 10, b = 20;
        PyObject pyobj = pyFunction.__call__(new PyInteger(a), new PyInteger(b));
        System.out.println(pyobj.toString());

        return pyobj.toString();
    }

    public String uploadImage(MultipartFile file,String signal, String name) throws IOException{
        System.out.println(file.getSize());
        System.out.println(name+","+signal);

        File upl = new File("/static/images/student/"+file.getOriginalFilename());
        upl.createNewFile();
        FileOutputStream fout = new FileOutputStream(upl);

        byte[] image = file.getBytes();
        fout.write(image);
        fout.close();

        return file.getOriginalFilename()+"UPLOAD SUCCESS!";
    }
}
