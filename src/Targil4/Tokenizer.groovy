package Targil4

class Tokenizer {

    static private def _keywords = ["class", "constructor", "function", "method", "field", "static", "var", "int", "char",
                             "boolean", "void", "true", "false", "null", "this", "let", "do", "if", "else", "while", "return"]

    static private def _symbols = ["{", "}", "(", ")", "[", "]", ".", ",", ";", "+", "-", "*", "/", "&", "|", "<", ">", "=", "~"]


    static String tokenize(String inputString, FileWriter fileWriter){
        fileWriter.write("<tokens>")
        while(!inputString.isEmpty()){
            inputString = flushComments(inputString)
            if (inputString[0] != " " && inputString[0] != "\t" && inputString[0] != "\n" && inputString[0] != "\r"){
                def buffer = ""
                inputString = q0(inputString, buffer, fileWriter)
            }
            else{
                if (inputString.length() > 1)
                    inputString = inputString[1..-1]
                else inputString = ""
            }
        }
        fileWriter.write("\n</tokens>\n")
    }

    private static String flushComments(String inputString) {
        while ((inputString.startsWith("//")) || (inputString.startsWith("/*"))) {
            if (inputString.startsWith("//")){
                while (!inputString.startsWith("\n") && !inputString.startsWith("\r") && !inputString.isEmpty()) {
                    inputString = inputString[1..-1]
                }
                if (inputString.length() > 1)
                    inputString = inputString[1..-1]
                else inputString = ""
            }
            if (inputString.startsWith("/*")) {
                while (!inputString.startsWith("*/") && !inputString.isEmpty()) {
                    if (inputString.length() > 1)
                        inputString = inputString[1..-1]
                    else inputString = ""
                }
                if (inputString.length() > 2)
                    inputString = inputString[2..-1]
                else inputString = ""
            }
        }
        return inputString
    }

    static private String q0(String inputString, String buffer, FileWriter fileWriter){
        if (inputString[0].matches("[a-zA-Z]")){
            buffer += inputString[0]
            inputString = q1(inputString[1..-1], buffer, fileWriter)
        }
        else if (inputString[0] == "_"){
            buffer += inputString[0]
            inputString = q2(inputString[1..-1], buffer, fileWriter)
        }
        else if (inputString[0].matches("[0-9]")){
            buffer += inputString[0]
            inputString = q3(inputString[1..-1], buffer, fileWriter)
        }
        else if (inputString[0] in _symbols){
            def symbolToPrint = inputString[0]
            if (symbolToPrint == "<")
                symbolToPrint = "&lt;"
            else if (symbolToPrint == ">")
                symbolToPrint = "&gt;"
            else if (symbolToPrint == "&")
                symbolToPrint = "&amp;"
            else if (symbolToPrint == "\"")
                symbolToPrint = "&quet;"
            print("\n\t<symbol> ${symbolToPrint} </symbol>")
            fileWriter.write("\n<symbol> ${symbolToPrint} </symbol>")
            if (inputString.length() > 2)
                inputString = inputString[1..-1]
            else inputString = ""
        }
        else if (inputString[0] == "\""){
            inputString = q5(inputString[1..-1], buffer, fileWriter)
        }
        return inputString
    }

    static private String q1(String inputString, String buffer, FileWriter fileWriter){
        if (!inputString.isEmpty()){
            if (inputString[0].matches("[a-zA-Z]")){
                buffer += inputString[0]
                return q1(inputString[1..-1], buffer, fileWriter)
            }
            else if (inputString[0].matches("[0-9_]")){
                buffer += inputString[0]
                return q2(inputString[1..-1], buffer, fileWriter)
            }
            else{
                if (buffer in _keywords){
                    print("\n\t<keyword> ${buffer} </keyword>")
                    fileWriter.write("\n<keyword> ${buffer} </keyword>")
                }
                else{
                    print("\n\t<identifier> ${buffer} </identifier>")
                    fileWriter.write("\n<identifier> ${buffer} </identifier>")
                }
                return inputString[0..-1]
            }
        }
        else{
            if (buffer in _keywords){
                print("\n\t<keyword> ${buffer} </keyword>")
                fileWriter.write("\n<keyword> ${buffer} </keyword>")
            }
            else{
                print("\n\t<identifier> ${buffer} </identifier>")
                fileWriter.write("\n<identifier> ${buffer} </keyword>")
            }
            return inputString[0..-1]
        }

    }

    static private String q2(String inputString, String buffer, FileWriter fileWriter){
        if (!inputString.isEmpty()){
            if (inputString[0].matches("[a-zA-Z0-9_]")){
                buffer += inputString[0]
                return q2(inputString[1..-1], buffer, fileWriter)
            }
            else{
                print("\n\t<identifier> ${buffer} </identifier>")
                fileWriter.write("\n<identifier> ${buffer} </identifier>")
            }
        }
        else{
            print("\n\t<identifier> ${buffer} </identifier>")
            fileWriter.write("\n<identifier> ${buffer} </identifier>")
        }
        return inputString[0..-1]
    }

    static private String q3(String inputString, String buffer, FileWriter fileWriter){
        if (!inputString.isEmpty()){
            if (inputString[0].matches("[0-9]")){
                buffer += inputString[0]
                return q3(inputString[1..-1], buffer, fileWriter)
            }
            else{
                print("\n\t<integerConstant> ${buffer} </integerConstant>")
                fileWriter.write("\n<integerConstant> ${buffer} </integerConstant>")
            }
        }
        else{
            print("\n\t<integerConstant> ${buffer} </integerConstant>")
            fileWriter.write("\n<integerConstant> ${buffer} </integerConstant>")
        }
        return inputString[0..-1]
    }

    static private String q5(String inputString, String buffer, FileWriter fileWriter){
        if (!inputString.isEmpty()){
            if (inputString[0] != "\""){
                buffer += inputString[0]
                return q5(inputString[1..-1], buffer, fileWriter)
            }
            else{
                print("\n\t<stringConstant> ${buffer} </stringConstant>")
                fileWriter.write("\n<stringConstant> ${buffer} </stringConstant>")
            }
        }
        else{
            print("\n\t<stringConstant> ${buffer} </stringConstant>")
            fileWriter.write("\n<stringConstant> ${buffer} </stringConstant>")
        }
        return inputString[1..-1]
    }

}
