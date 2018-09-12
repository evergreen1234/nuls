package io.nuls.contract.vm.natives.java.lang;

import io.nuls.contract.vm.Frame;
import io.nuls.contract.vm.MethodArgs;
import io.nuls.contract.vm.ObjectRef;
import io.nuls.contract.vm.Result;
import io.nuls.contract.vm.code.MethodCode;
import io.nuls.contract.vm.code.VariableType;
import io.nuls.contract.vm.instructions.references.Instanceof;
import io.nuls.contract.vm.natives.NativeMethod;

import java.util.ArrayList;
import java.util.List;

public class NativeThrowable {

    public static final String TYPE = "java/lang/Throwable";

    public static Result run(MethodCode methodCode, MethodArgs methodArgs, Frame frame) {
        Result result = null;
        switch (methodCode.name) {
            case "fillInStackTrace":
                result = fillInStackTrace(methodCode, methodArgs, frame);
                break;
            case "getStackTraceDepth":
                result = getStackTraceDepth(methodCode, methodArgs, frame);
                break;
            case "getStackTraceElement":
                result = getStackTraceElement(methodCode, methodArgs, frame);
                break;
            default:
                frame.nonsupportMethod(methodCode);
                break;
        }
        return result;
    }

    private static Result fillInStackTrace(MethodCode methodCode, MethodArgs methodArgs, Frame frame) {
        int dummy = (int) methodArgs.invokeArgs[0];
        ObjectRef objectRef = methodArgs.objectRef;

        int size = frame.vm.getVmStack().size();
        boolean isThrowable = true;
        List<Frame> frames = new ArrayList<>();
        for (int i = size - 1; i >= 0; i--) {
            Frame frame1 = frame.vm.getVmStack().get(i);
            if (isThrowable) {
                if (Instanceof.instanceof_(frame1.methodCode.classCode.name, "java/lang/Throwable", frame)) {
                    continue;
                } else {
                    isThrowable = false;
                }
            }
            frames.add(frame1);
        }

        ObjectRef stackTraceElementsRef = frame.heap.newArray(VariableType.STACK_TRACE_ELEMENT_ARRAY_TYPE, frames.size());
        frame.heap.putField(objectRef, "stackTraceElements", stackTraceElementsRef);
        int index = 0;
        for (Frame frame1 : frames) {
            ObjectRef declaringClass = frame.heap.newString(frame1.methodCode.classCode.name);
            ObjectRef methodName = frame.heap.newString(frame1.methodCode.name);
            ObjectRef fileName = frame.heap.newString(frame1.methodCode.classCode.sourceFile);
            int lineNumber = frame1.getLine();
            ObjectRef stackTraceElementRef = frame.heap.runNewObjectWithArgs(VariableType.STACK_TRACE_ELEMENT_TYPE, null, declaringClass, methodName, fileName, lineNumber);
            frame.heap.putArray(stackTraceElementsRef, index++, stackTraceElementRef);
        }

        Result result = NativeMethod.result(methodCode, objectRef, frame);
        return result;
    }

    private static Result getStackTraceDepth(MethodCode methodCode, MethodArgs methodArgs, Frame frame) {
        ObjectRef objectRef = methodArgs.objectRef;
        ObjectRef stackTraceElementsRef = (ObjectRef) frame.heap.getField(objectRef, "stackTraceElements");
        int depth = stackTraceElementsRef.getDimensions()[0];
        Result result = NativeMethod.result(methodCode, depth, frame);
        return result;
    }

    private static Result getStackTraceElement(MethodCode methodCode, MethodArgs methodArgs, Frame frame) {
        int index = (int) methodArgs.invokeArgs[0];
        ObjectRef objectRef = methodArgs.objectRef;
        ObjectRef stackTraceElementsRef = (ObjectRef) frame.heap.getField(objectRef, "stackTraceElements");
        ObjectRef stackTraceElementRef = (ObjectRef) frame.heap.getArray(stackTraceElementsRef, index);
        Result result = NativeMethod.result(methodCode, stackTraceElementRef, frame);
        return result;
    }

}