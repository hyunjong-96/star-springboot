package com.hyunjong.book.springboot.service;

import lombok.RequiredArgsConstructor;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class PythonTest {
    private static PythonInterpreter intPre;

    public String pythontest(){
        intPre = new PythonInterpreter();
        intPre.execfile("../test.py");
        intPre.exec("print(testFunc(5,10))");

        PyFunction pyFunction = (PyFunction) intPre.get("testFunc",PyFunction.class);
        int a = 10, b=20;
        PyObject pyobj = pyFunction.__call__(new PyInteger(a), new PyInteger(b));
        System.out.println(pyobj.toString());

        return pyobj.toString();
    }
}
