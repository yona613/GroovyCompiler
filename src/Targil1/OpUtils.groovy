package Targil1

class OpUtils {

    static def opNumber = 0

    static String add() {
        opNumber++
        return "@SP\n" +
                "A=M-1\n" +
                "D=M\n" +
                "A=A-1\n" +
                "M=D+M\n" +
                "@SP\n" +
                "M=M-1\n"
    }

    static String sub() {
        opNumber++
        return "@SP\n" +
                "A=M-1\n" +
                "D=M\n" +
                "A=A-1\n" +
                "M=M-D\n" +
                "@SP\n" +
                "M=M-1\n"
    }

    static String neg() {
        opNumber++
        return "@SP\n" +
                "A=M-1\n" +
                "M=-M\n"
    }

    static String eq() {
        opNumber++
        return "@SP\n" +
                "A=M-1\n" +
                "D=M\n" +
                "A=A-1\n" +
                "D=M-D\n" +
                "@LABEL_TRUE${opNumber}\n" +
                "D;JEQ\n" +
                "@SP\n" +
                "A=M-1\n" +
                "A=A-1\n" +
                "M=0\n" +
                "@LABEL_FALSE${opNumber}\n" +
                "0;JMP\n" +
                "(LABEL_TRUE${opNumber})\n" +
                "@SP\n" +
                "A=M-1\n" +
                "A=A-1\n" +
                "M=-1\n" +
                "(LABEL_FALSE${opNumber})\n" +
                "@SP\n" +
                "M=M-1\n"
    }

    static String gt() {
        opNumber++
        return "@SP\n" +
                "A=M-1\n" +
                "D=M\n" +
                "A=A-1\n" +
                "D=M-D\n" +
                "@LABEL_TRUE${opNumber}\n" +
                "D;JGT\n" +
                "@SP\n" +
                "A=M-1\n" +
                "A=A-1\n" +
                "M=0\n" +
                "@LABEL_FALSE${opNumber}\n" +
                "0;JMP\n" +
                "(LABEL_TRUE${opNumber})\n" +
                "@SP\n" +
                "A=M-1\n" +
                "A=A-1\n" +
                "M=-1\n" +
                "(LABEL_FALSE${opNumber})\n" +
                "@SP\n" +
                "M=M-1\n"
    }

    static String lt() {
        opNumber++
        return "@SP\n" +
                "A=M-1\n" +
                "D=M\n" +
                "A=A-1\n" +
                "D=M-D\n" +
                "@LABEL_TRUE${opNumber}\n" +
                "D;JLT\n" +
                "@SP\n" +
                "A=M-1\n" +
                "A=A-1\n" +
                "M=0\n" +
                "@LABEL_FALSE${opNumber}\n" +
                "0;JMP\n" +
                "(LABEL_TRUE${opNumber})\n" +
                "@SP\n" +
                "A=M-1\n" +
                "A=A-1\n" +
                "M=-1\n" +
                "(LABEL_FALSE${opNumber})\n" +
                "@SP\n" +
                "M=M-1\n"
    }

    static String and() {
        opNumber++
        return "@SP\n" +
                "A=M-1\n" +
                "D=M\n" +
                "A=A-1\n" +
                "M=D&M\n" +
                "@SP\n" +
                "M=M-1\n"
    }

    static String or() {
        opNumber++
        return "@SP\n" +
                "A=M-1\n" +
                "D=M\n" +
                "A=A-1\n" +
                "M=D|M\n" +
                "@SP\n" +
                "M=M-1\n"
    }

    static String not() {
        opNumber++
        return "@SP\n" +
                "A=M-1\n" +
                "M=!M\n"
    }
}
