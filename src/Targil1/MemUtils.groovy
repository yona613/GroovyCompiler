package Targil1

class MemUtils {

    static String push(String segment, String index) {
        def groupA = ['local', 'argument', 'this', 'that']
        if (segment in groupA) {
            def first_cmd = ""
            switch (segment) {
                case "local":
                    first_cmd = "@1"
                    break
                case "argument":
                    first_cmd = "@2"
                    break
                case "this":
                    first_cmd = "@3"
                    break
                case "that":
                    first_cmd = "@4"
                    break
            }
            return first_cmd +
                    "D=M\n" +
                    "@${index}\n" +
                    "A=D+A\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1"
        } else if (segment == "temp") {
            return "@5" +
                    "D=A\n" +
                    "@${index}\n" +
                    "A=D+A\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1"
        }
        else if (segment == "static")

    }
}
