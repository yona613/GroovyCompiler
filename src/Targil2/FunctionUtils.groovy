package Targil2

class FunctionUtils {

    static def opNumber = 0

    static String label(String label) {
        opNumber++
        return "(${label})\n"
    }

    static String goto_func(String label) {
        opNumber++
        return "@${label}\n" +
                "0;JMP\n"
    }

    static String push_pointer(String pointerName) {
        opNumber++
        return "\n//push ${pointerName}\n" +
                "@${pointerName}\n" +
                "D=M\n" +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1\n"
    }

    static String if_goto(String label) {
        opNumber++
        return "\n//if-goto ${label}\n" +
                "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "D=M\n" +
                "@IF_GOTO_FALSE${opNumber}\n" +
                "D;JEQ\n" +
                "@${label}\n" +
                "0;JMP\n" +
                "(IF_GOTO_FALSE${opNumber})\n"
    }

    static String call(String functionName, String numArgs) {
        opNumber++
        def commands = ""
        def ret_address = "RETURN_ADDRESS_${functionName}_${opNumber}"
        //push (return_address)
        commands += "\n//push ${ret_address}\n" +
                "@${ret_address}\n" +
                "D=A\n" +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1\n"

        //push LCL
        //push ARG
        //push THIS
        //push THAT
        for (String pointer : ["LCL", "ARG", "THIS", "THAT"]) {
            commands += push_pointer(pointer)
        }

        //ARG = SP-n-5
        commands += "\n//ARG = SP-n-5\n" +
                "@SP\n" +
                "D=M\n" +
                "@5\n" +
                "D=D-A\n" +
                "@${numArgs}\n" +
                "D=D-A\n" +
                "@ARG\n" +
                "M=D\n"

        //LCL = SP
        commands += "\n//LCL = SP\n" +
                "@SP\n" +
                "D=M\n" +
                "@LCL\n" +
                "M=D\n"

        //goto f
        commands += goto_func(functionName)

        // (return_address)
        commands += "(${ret_address})\n"

        return commands
    }

    static String return_function() {
        opNumber++
        def commands = ""

        // FRAME=LCL
        commands += "\n//FRAME=LCL\n" +
                "@LCL\n" +
                "D=M\n" +
                "@FRAME\n" +
                "M=D\n"

        //RET = (FRAME-5)
        commands += "\n//RET = (FRAME-5)\n" +
                "@FRAME\n" +
                "D=M\n" +
                "@5\n" +
                "A=D-A\n" +
                "D=M\n" +
                "@RET\n" +
                "M=D\n"

        //ARG = pop()
        commands += "\n//ARG = pop()\n" +
                "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "D=M\n" +
                "@ARG\n" +
                "A=M\n" +
                "M=D\n"

        //SP=ARG+1
        commands += "\n//SP=ARG+1\n" +
                "@ARG\n" +
                "D=M+1\n" +
                "@SP\n" +
                "M=D\n"

        //THAT = FRAME-1
        commands += "\n//THAT = FRAME-1\n" +
                "@FRAME\n" +
                "A=M-1\n" +
                "D=M\n" +
                "@THAT\n" +
                "M=D\n"

        //THIS = FRAME-2
        commands += "\n//THIS = FRAME-2\n" +
                "@FRAME\n" +
                "D=M\n" +
                "@2\n" +
                "A=D-A\n" +
                "D=M\n" +
                "@THIS\n" +
                "M=D\n"

        //ARG = FRAME-3
        commands += "\n//ARG = FRAME-3\n" +
                "@FRAME\n" +
                "D=M\n" +
                "@3\n" +
                "A=D-A\n" +
                "D=M\n" +
                "@ARG\n" +
                "M=D\n"

        //LCL = FRAME-4
        commands += "\n//LCL = FRAME-4\n" +
                "@FRAME\n" +
                "D=M\n" +
                "@4\n" +
                "A=D-A\n" +
                "D=M\n" +
                "@LCL\n" +
                "M=D\n"

        //goto RET
        commands += "@RET\n" +
                "A=M\n" +
                "0;JMP\n"

        return commands
    }

    static String function(String functionName, String numVar) {
        opNumber++
        int numVarInt = Integer.parseInt(numVar)
        //(f)
        def commands = "(${functionName})\n"

        //repeat k times
        //Push 0
        for (i in 0..<numVarInt) {
            commands += "\n//push 0 for var\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=0\n" +
                    "@SP\n" +
                    "M=M+1\n"
        }
        return commands
    }

    static String bootstrap() {
        opNumber++
        def commands = ""

        //SP = 256
        commands += "@256\n" +
                "D=A\n" +
                "@SP\n" +
                "M=D\n"

        //call Sys.init
        commands += call("Sys.init", "0")
        return commands
    }

}
