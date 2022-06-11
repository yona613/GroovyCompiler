package Targil4

import Targil5.SymbolKind
import Targil5.SymbolTable

abstract class CodeWriter {

    def writeToken(String token, FileWriter fileWriter, SymbolTable tableScope, String callerType, boolean write = true) {
        def startType = token.indexOf("<")
        def stopType = token.indexOf(">")
        def type = token.substring(startType + 1, stopType)
        token = token.substring(stopType + 1)
        def endData = token.indexOf("<") - 1
        def data = token.substring(1, endData)
        switch (type) {
            case "keyword":
                writeKeyword(data, fileWriter)
                break
            case "symbol":
                writeSymbol(data, fileWriter, callerType)
                break
            case "integerConstant":
                writeIntegerConstant(data, fileWriter)
                break
            case "stringConstant":
                writeStringConstant(data, fileWriter)
                break
            case "identifier":
                writeIdentifier(data, fileWriter, tableScope, write)
                break
            default:
                break
        }
    }

    def abstract writeKeyword(String keyword, FileWriter fileWriter)

    def abstract writeSymbol(String symbol, FileWriter fileWriter, String type)

    def abstract writeIntegerConstant(String integerConstant, FileWriter fileWriter)

    def abstract writeStringConstant(String stringConstant, FileWriter fileWriter)

    def abstract writeIdentifier(String identifier, FileWriter fileWriter, SymbolTable tableScope, boolean isWrite)

    def abstract writeClassOpen(FileWriter fileWriter)

    def abstract writeClassClose(FileWriter fileWriter)

    def abstract writeClassVarDecOpen(FileWriter fileWriter)

    def abstract writeClassVarDecClose(FileWriter fileWriter)

    def abstract writeTypeOpen(FileWriter fileWriter)

    def abstract writeTypeClose(FileWriter fileWriter)

    abstract def writeSubDecOpen(FileWriter fileWriter, String className)

    abstract def writeSubDecClose(FileWriter fileWriter)

    abstract def writeParameterListOpen(FileWriter fileWriter)

    abstract def writeParameterListClose(FileWriter fileWriter)

    abstract def writeSubroutineBodyOpen1(FileWriter fileWriter, String className, String functionName, int varCount)
    abstract def writeSubroutineBodyOpen2(FileWriter fileWriter, String className, String functionName, int varCount)

    abstract def writeSubroutineBodyClose(FileWriter fileWriter)

    def abstract writeVarDecOpen(FileWriter fileWriter)

    def abstract writeVarDecClose(FileWriter fileWriter)

    abstract def writeStatementsOpen(FileWriter fileWriter)

    abstract def writeStatementsClose(FileWriter fileWriter)

    abstract def writeStatementOpen(FileWriter fileWriter)

    abstract def writeStatementClose(FileWriter fileWriter)

    abstract def writeLetStatementOpen(FileWriter fileWriter)

    abstract def writeLetStatementClose(FileWriter fileWriter, String kind, int index)
    abstract def writeLetStatementClose1(FileWriter fileWriter, String kind, int index)

    abstract def writeIfStatementOpen(FileWriter fileWriter)

    abstract def writeIfStatementClose(FileWriter fileWriter)

    abstract def writeWhileStatementOpen(FileWriter fileWriter)

    abstract def writeWhileStatementClose(FileWriter fileWriter)

    abstract def writeDoStatementOpen(FileWriter fileWriter)

    abstract def writeDoStatementClose(FileWriter fileWriter)

    abstract def writeReturnStatementOpen(FileWriter fileWriter)

    abstract def writeReturnStatementClose(FileWriter fileWriter, String functionType, boolean isVoid)

    abstract def writeExpressionOpen(FileWriter fileWriter)

    abstract def writeExpressionClose(FileWriter fileWriter)

    abstract def writeTermOpen(FileWriter fileWriter)

    abstract def writeTermClose(FileWriter fileWriter)

    abstract def writeOpOpen(FileWriter fileWriter)

    abstract def writeOpClose(FileWriter fileWriter)

    abstract def writeSubroutineCallOpen1(FileWriter fileWriter, String funcCallName, boolean isMethod, SymbolKind kind, int index, int varCount)
    abstract def writeSubroutineCallOpen2(FileWriter fileWriter, String funcCallName, boolean isMethod, SymbolKind kind, int index, int varCount)

    abstract def writeSubroutineCallClose(FileWriter fileWriter)

    abstract def writeExpressionListOpen(FileWriter fileWriter)

    abstract def writeExpressionListClose(FileWriter fileWriter)

    abstract def writeIf1(FileWriter fileWriter, int index)

    abstract def writeIf2(FileWriter fileWriter, int index)

    abstract def writeIf3(FileWriter fileWriter, int index)

    abstract def writeWhile1(FileWriter fileWriter, int index)

    abstract def writeWhile2(FileWriter fileWriter, int index)

    abstract def writeWhile3(FileWriter fileWriter, int index)

    abstract def writeMemAlloc(FileWriter fileWriter, int varCount)

    abstract def pushPopThis(FileWriter fileWriter)

    abstract def popTemp(FileWriter fileWriter)

    abstract def pushCallerData(FileWriter fileWriter, String kind, int index)

    abstract def writePushArr(FileWriter fileWriter, String varName)

    abstract def writeAdd(FileWriter fileWriter)

    abstract def writePopToArray(FileWriter fileWriter)

    abstract def writePushToArray(FileWriter fileWriter)

    abstract def writeOp(FileWriter fileWriter, String op, String type)
}