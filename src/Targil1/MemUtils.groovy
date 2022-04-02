package Targil1

class MemUtils {

    static String push(String segment, String x, String className) {
        def groupA = ['local', 'argument', 'this', 'that']
        if (segment in groupA) {
            def first_cmd = ""
            switch (segment) {
                case "local":
                    first_cmd = "@1\n"
                    break
                case "argument":
                    first_cmd = "@2\n"
                    break
                case "this":
                    first_cmd = "@3\n"
                    break
                case "that":
                    first_cmd = "@4\n"
                    break
            }
            return first_cmd +
                    "D=M\n" +
                    "@${x}\n" +
                    "A=D+A\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n"
        } else if (segment == "temp") {
            return "@5\n" +
                    "D=A\n" +
                    "@${x}\n" +
                    "A=D+A\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n"
        } else if (segment == "static") {
            return "@${className}.${x}\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n"
        } else if (segment == "pointer") {
            if (x as int == 0) {
                return "@3\n" +
                        "D=M\n" +
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        "@SP\n" +
                        "M=M+1\n"
            } else if (x as int == 1) {
                return "@4\n" +
                        "D=M\n" +
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        "@SP\n" +
                        "M=M+1\n"
            }
        } else if (segment == "constant") {
            return "@${x}\n" +
                    "D=A\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n"
        }
        return ""
    }

    static String pop(String segment, String x, String className) {
        def groupA = ['local', 'argument', 'this', 'that']
        if (segment in groupA) {
            def first_cmd = ""
            switch (segment) {
                case "local":
                    first_cmd = "@1\n"
                    break
                case "argument":
                    first_cmd = "@2\n"
                    break
                case "this":
                    first_cmd = "@3\n"
                    break
                case "that":
                    first_cmd = "@4\n"
                    break
            }
            return first_cmd +
                    "D=M\n" +
                    "@${x}\n" +
                    "D=D+A\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "A=M\n" +
                    "A=A+D\n" +
                    "D=A-D\n" +
                    "A=A-D\n" +
                    "M=D\n"
        } else if (segment == "temp") {
            return "@5\n" +
                    "D=A\n" +
                    "@${x}\n" +
                    "D=D+A\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "A=M\n" +
                    "A=A+D\n" +
                    "D=A-D\n" +
                    "A=A-D\n" +
                    "M=D\n"
        } else if (segment == "static") {
            return "@${className}.${x}\n" +
                    "D=A\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "A=M\n" +
                    "A=A+D\n" +
                    "D=A-D\n" +
                    "A=A-D\n" +
                    "M=D\n"
        } else if (segment == "pointer") {
            if (x as int == 0) {
                return "@SP\n" +
                        "M=M-1\n" +
                        "A=M\n" +
                        "D=M\n" +
                        "@3\n" +
                        "M=D\n"
            } else if (x as int == 1) {
                return "@SP\n" +
                        "M=M-1\n" +
                        "A=M\n" +
                        "D=M\n" +
                        "@4\n" +
                        "M=D\n"
            }
        }
        return ""
    }
}
