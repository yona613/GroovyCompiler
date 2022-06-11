package Targil4

import Targil5.SymbolKind
import Targil5.SymbolTable

class XMLCodeWriter extends CodeWriter {

    int indents = 0

    @Override
    def writeKeyword(String keyword, FileWriter fileWriter) {
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<keyword> ${keyword} </keyword>\n")
    }

    @Override
    def writeSymbol(String symbol, FileWriter fileWriter, String kind) {
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<symbol> ${symbol} </symbol>\n")
    }

    @Override
    def writeIntegerConstant(String integerConstant, FileWriter fileWriter) {
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<integerConstant> ${integerConstant} </integerConstant>\n")
    }

    @Override
    def writeStringConstant(String stringConstant, FileWriter fileWriter) {
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<stringConstant> ${stringConstant} </stringConstant>\n")
    }

    @Override
    def writeIdentifier(String identifier, FileWriter fileWriter, SymbolTable tableScope, boolean isWrite) {
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<identifier> ${identifier} </identifier>\n")
    }

    @Override
    def writeClassOpen(FileWriter fileWriter) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<class>\n")
        indents++
    }

    @Override
    def writeClassClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</class>\n")
    }

    @Override
    def writeClassVarDecOpen(FileWriter fileWriter) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<classVarDec>\n")
        indents++
    }

    @Override
    def writeClassVarDecClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</classVarDec>\n")
    }

    @Override
    def writeTypeOpen(FileWriter fileWriter) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<type>\n")
        indents++
    }

    @Override
    def writeTypeClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</type>\n")
    }

    @Override
    def writeSubDecOpen(FileWriter fileWriter, String className) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<subroutineDec>\n")
        indents++
    }

    @Override
    def writeSubDecClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</subroutineDec>\n")
    }

    @Override
    def writeParameterListOpen(FileWriter fileWriter) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<parameterList>\n")
        indents++
    }

    @Override
    def writeParameterListClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</parameterList>\n")
    }

    @Override
    def writeSubroutineBodyOpen1(FileWriter fileWriter, String className, String functionName, int varCount) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<subroutineBody>\n")
        indents++
    }

    @Override
    def writeSubroutineBodyOpen2(FileWriter fileWriter, String className, String functionName, int varCount) {
    }

    @Override
    def writeSubroutineBodyClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</subroutineBody>\n")
    }

    @Override
    def writeVarDecOpen(FileWriter fileWriter) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<varDec>\n")
        indents++
    }

    @Override
    def writeVarDecClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</varDec>\n")
    }

    @Override
    def writeStatementsOpen(FileWriter fileWriter) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<statements>\n")
        indents++
    }

    @Override
    def writeStatementsClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</statements>\n")
    }

    @Override
    def writeStatementOpen(FileWriter fileWriter) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<statement>\n")
        indents++
    }

    @Override
    def writeStatementClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</statement>\n")
    }

    @Override
    def writeIfStatementOpen(FileWriter fileWriter) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<ifStatement>\n")
        indents++
    }

    @Override
    def writeIfStatementClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</ifStatement>\n")
    }

    @Override
    def writeWhileStatementOpen(FileWriter fileWriter) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<whileStatement>\n")
        indents++
    }

    @Override
    def writeWhileStatementClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</whileStatement>\n")
    }

    @Override
    def writeDoStatementOpen(FileWriter fileWriter) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<doStatement>\n")
        indents++
    }

    @Override
    def writeDoStatementClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</doStatement>\n")
    }

    @Override
    def writeLetStatementOpen(FileWriter fileWriter) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<letStatement>\n")
        indents++
    }

    @Override
    def writeLetStatementClose(FileWriter fileWriter, String kind, int index) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</letStatement>\n")
    }

    @Override
    def writeLetStatementClose1(FileWriter fileWriter, String kind, int index) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</letStatement>\n")
    }

    @Override
    def writeReturnStatementOpen(FileWriter fileWriter) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<returnStatement>\n")
        indents++
    }

    @Override
    def writeReturnStatementClose(FileWriter fileWriter, String functionType, boolean isVoid) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</returnStatement>\n")
    }

    @Override
    def writeExpressionOpen(FileWriter fileWriter) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<expression>\n")
        indents++
    }

    @Override
    def writeExpressionClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</expression>\n")
    }

    @Override
    def writeTermOpen(FileWriter fileWriter) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<term>\n")
        indents++
    }

    @Override
    def writeTermClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</term>\n")
    }

    @Override
    def writeOpOpen(FileWriter fileWriter) {
     for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<op>\n")
        indents++
    }

    @Override
    def writeOpClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</op>\n")
    }

    @Override
    def writeSubroutineCallOpen1(FileWriter fileWriter, String funcCallName, boolean isMethod, SymbolKind kind, int index, int varCount) {
    /*    for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<subroutineCall>\n")
        indents++*/
    }

    @Override
    def writeSubroutineCallOpen2(FileWriter fileWriter, String funcCallName, boolean isMethod, SymbolKind kind, int index, int varCount) {
    }

    @Override
    def writeSubroutineCallClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</subroutineCall>\n")
    }

    @Override
    def writeExpressionListOpen(FileWriter fileWriter) {
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("<expressionList>\n")
        indents++
    }

    @Override
    def writeExpressionListClose(FileWriter fileWriter) {
        indents--
        for (i in 0..<indents) {
            fileWriter.write("  ")
        }
        fileWriter.write("</expressionList>\n")
    }

    @Override
    def writeIf1(FileWriter fileWriter, int index) {
        return null
    }

    @Override
    def writeIf2(FileWriter fileWriter, int index) {
        return null
    }

    @Override
    def writeIf3(FileWriter fileWriter, int index) {
        return null
    }

    @Override
    def writeWhile1(FileWriter fileWriter, int index) {
        return null
    }

    @Override
    def writeWhile2(FileWriter fileWriter, int index) {
        return null
    }

    @Override
    def writeWhile3(FileWriter fileWriter, int index) {
        return null
    }

    @Override
    def writeMemAlloc(FileWriter fileWriter, int varCount) {
        return null
    }

    @Override
    def pushPopThis(FileWriter fileWriter) {
        return null
    }

    @Override
    def popTemp(FileWriter fileWriter) {
        return null
    }

    @Override
    def pushCallerData(FileWriter fileWriter, String kind, int index) {
        return null
    }

    @Override
    def writePushArr(FileWriter fileWriter, String varName) {
        return null
    }

    @Override
    def writeAdd(FileWriter fileWriter) {
        return null
    }

    @Override
    def writePopToArray(FileWriter fileWriter) {
        return null
    }

    @Override
    def writePushToArray(FileWriter fileWriter) {
        return null
    }

    @Override
    def writeOp(FileWriter fileWriter, String op, String type) {
        return null
    }
}
