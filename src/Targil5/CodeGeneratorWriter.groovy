package Targil5

import Targil4.CodeWriter

class CodeGeneratorWriter extends CodeWriter {

    @Override
    def writeSymbol(String symbol, FileWriter fileWriter, String type) {
        if (type == "expression") {
            switch (symbol) {
                case "+":
                    fileWriter.write("add\n")
                    break
                case "-":
                    fileWriter.write("sub\n")
                    break
                case "*":
                    fileWriter.write("call Math.multiply 2\n")
                    break
                case "/":
                    fileWriter.write("call Math.divide 2\n")
                    break
                case "&amp;":
                    fileWriter.write("and\n")
                    break
                case "|":
                    fileWriter.write("or\n")
                    break
                case "&lt;":
                    fileWriter.write("lt\n")
                    break
                case "&gt;":
                    fileWriter.write("gt\n")
                    break
                case "=":
                    fileWriter.write("eq\n")
                    break
                default:
                    break
            }
        } else if (type == "term") {
            switch (symbol) {
                case "~":
                    fileWriter.write("not\n")
                    break
                case "-":
                    fileWriter.write("neg\n")
                    break
                case "|":
                    fileWriter.write("or\n")
                    break
                case "&lt;":
                    fileWriter.write("lt\n")
                    break
                case "&gt;":
                    fileWriter.write("gt\n")
                    break
                case "=":
                    fileWriter.write("eq\n")
                    break
                default:
                    break
            }
        }

    }

    @Override
    def writeKeyword(String keyword, FileWriter fileWriter) {
        switch (keyword) {
            case "true" -> fileWriter.write("push constant 0\nnot\n")
            case "false", "null" -> fileWriter.write("push constant 0\n")
            case "this" -> fileWriter.write("push pointer 0\n")
            default -> ""
        }
    }

    @Override
    def writeIntegerConstant(String integerConstant, FileWriter fileWriter) {
        fileWriter.write("push constant ${integerConstant}\n")
    }

    @Override
    def writeStringConstant(String stringConstant, FileWriter fileWriter) {
        fileWriter.write("push constant ${stringConstant.length()}\n")
        fileWriter.write("call String.new 1\n")
        for (i in 0..<stringConstant.length()) {
            def temp = (int) (stringConstant[i] as char)
            fileWriter.write("push constant ${temp}\n")
            fileWriter.write("call String.appendChar 2\n")
        }
    }

    @Override
    def writeIdentifier(String identifier, FileWriter fileWriter, SymbolTable tableScope, boolean isWrite) {
        if (isWrite){
            if (tableScope.kindOf(identifier) != null){
                def kind =  tableScope.kindOf(identifier).name()
                def index = tableScope.indexOf(identifier)
                if (kind == "VAR") kind = "local"
                if (kind == "ARG") kind = "argument"
                if (kind == "FIELD") kind = "this"
                println "push ${kind.toLowerCase()} ${index}\n"
                fileWriter.write("push ${kind.toLowerCase()} ${index}\n")
            }

        }

    }

    @Override
    def writeClassOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeClassClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeClassVarDecOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeClassVarDecClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeTypeOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeTypeClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeSubDecOpen(FileWriter fileWriter, String className) {
        return null
    }

    @Override
    def writeSubDecClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeParameterListOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeParameterListClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeSubroutineBodyOpen1(FileWriter fileWriter, String className, String functionName, int varCount) {}

    @Override
    def writeSubroutineBodyOpen2(FileWriter fileWriter, String className, String functionName, int varCount) {
        fileWriter.write("function ${className}.${functionName} ${varCount}\n")
    }

    @Override
    def writeSubroutineBodyClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeVarDecOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeVarDecClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeStatementsOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeStatementsClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeStatementOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeStatementClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeLetStatementOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeLetStatementClose(FileWriter fileWriter, String kind, int index) {
        if (kind == "VAR") kind = "local"
        if (kind == "ARG") kind = "argument"
        if (kind == "FIELD") kind = "this"
        println "pop ${kind.toLowerCase()} ${index}\n"
        fileWriter.write("pop ${kind.toLowerCase()} ${index}\n")
    }

    @Override
    def writeLetStatementClose1(FileWriter fileWriter, String kind, int index) {
    }

    @Override
    def writeIfStatementOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeIfStatementClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeWhileStatementOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeWhileStatementClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeDoStatementOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeDoStatementClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeReturnStatementOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeReturnStatementClose(FileWriter fileWriter, String functionType, boolean isVoid) {
//        if (functionType == "constructor") {
//            fileWriter.write("push pointer 0\n")
//        } else
        if (isVoid) {
            fileWriter.write("push constant 0\n")
        }
        fileWriter.write("return\n")
    }

    @Override
    def writeExpressionOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeExpressionClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeTermOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeTermClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeOpOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeOpClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeSubroutineCallOpen1(FileWriter fileWriter, String funcCallName, boolean isMethod, SymbolKind kind, int index, int varCount) {}

    @Override
    def writeSubroutineCallOpen2(FileWriter fileWriter, String funcCallName, boolean isMethod, SymbolKind kind, int index, int varCount) {
        fileWriter.write("call ${funcCallName} ${varCount}\n")
    }

    @Override
    def writeSubroutineCallClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeExpressionListOpen(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeExpressionListClose(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeIf1(FileWriter fileWriter, int index) {
        fileWriter.write("not\nif-goto IF_FALSE${index}\n")
    }

    @Override
    def writeIf2(FileWriter fileWriter, int index) {
        fileWriter.write("goto IF_END${index}\n" +
                "label IF_FALSE${index}\n"
        )
    }

    @Override
    def writeIf3(FileWriter fileWriter, int index) {
        fileWriter.write("label IF_END${index}\n")
    }

    @Override
    def writeWhile1(FileWriter fileWriter, int index) {
        fileWriter.write("label WHILE_EXP${index}\n")
    }

    @Override
    def writeWhile2(FileWriter fileWriter, int index) {
        fileWriter.write("not\n" +
                "if-goto WHILE_END${index}\n"
        )
    }

    @Override
    def writeWhile3(FileWriter fileWriter, int index) {
        fileWriter.write("goto WHILE_EXP${index}\n" +
                "label WHILE_END${index}\n"
        )
    }

    @Override
    def writeMemAlloc(FileWriter fileWriter, int varCount) {
        fileWriter.write("push constant ${varCount}\n" +
                "call Memory.alloc 1\n" +
                "pop pointer 0\n")
    }

    @Override
    def pushPopThis(FileWriter fileWriter) {
        fileWriter.write("push argument 0\n" +
                "pop pointer 0\n")
    }

    @Override
    def popTemp(FileWriter fileWriter) {
        fileWriter.write("pop temp 0\n")
    }

    @Override
    def pushCallerData(FileWriter fileWriter, String kind, int index) {
        if (kind == "VAR") kind = "local"
        if (kind == "ARG") kind = "argument"
        if (kind == "FIELD") kind = "this"
        println "push ${kind.toLowerCase()} ${index}\n"
        fileWriter.write("push ${kind.toLowerCase()} ${index}\n")
    }

    @Override
    def writePushArr(FileWriter fileWriter, String varName) {
        fileWriter.write("push ${varName}\n")
    }

    @Override
    def writeAdd(FileWriter fileWriter) {
        fileWriter.write("add\n")
    }

    @Override
    def writePopToArray(FileWriter fileWriter) {
        fileWriter.write("pop temp 0\n" +
                "pop pointer 1\n" +
                "push temp 0\n" +
                "pop that 0\n")
    }

    def writePushToArray(FileWriter fileWriter) {
        fileWriter.write("add\n" +
                "pop pointer 1\n" +
                "push that 0\n")
    }

    @Override
    def writeOp(FileWriter fileWriter, String op, String type) {
        if (type == "expression") {
            switch (op) {
                case "+":
                    fileWriter.write("add\n")
                    break
                case "-":
                    fileWriter.write("sub\n")
                    break
                case "*":
                    fileWriter.write("call Math.multiply 2\n")
                    break
                case "/":
                    fileWriter.write("call Math.divide 2\n")
                    break
                case "&amp;":
                    fileWriter.write("and\n")
                    break
                case "|":
                    fileWriter.write("or\n")
                    break
                case "&lt;":
                    fileWriter.write("lt\n")
                    break
                case "&gt;":
                    fileWriter.write("gt\n")
                    break
                case "=":
                    fileWriter.write("eq\n")
                    break
                default:
                    break
            }
        } else if (type == "term") {
            switch (op) {
                case "~":
                    fileWriter.write("not\n")
                    break
                case "-":
                    fileWriter.write("neg\n")
                    break
                case "|":
                    fileWriter.write("or\n")
                    break
                case "&lt;":
                    fileWriter.write("lt\n")
                    break
                case "&gt;":
                    fileWriter.write("gt\n")
                    break
                case "=":
                    fileWriter.write("eq\n")
                    break
                default:
                    break
            }
        }

    }
}
