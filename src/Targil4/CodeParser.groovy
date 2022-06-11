package Targil4

import Targil5.SymbolKind
import Targil5.SymbolTable

class CodeParser {

    public static SymbolTable classScope = new SymbolTable()

    public static String className

    public static ifLabelIndex = -1

    public static whileLabelIndex = -1

    public static functionName = ""

    public static functionType = ""

    public static isVoid = false

    private static String getNextToken(String tokens) {
        def start = tokens.indexOf("<")
        def firstStop = tokens.indexOf(">")
        def secondStop = tokens.indexOf(">", firstStop + 1)
        return tokens.substring(start, secondStop + 1)
    }

    private static String writeNextToken(CodeWriter codeWriter, String tokens, FileWriter fileWriter, String callerType = "", Boolean isWrite = true) {
        def start = tokens.indexOf("<")
        def firstStop = tokens.indexOf(">")
        def secondStop = tokens.indexOf(">", firstStop + 1)
        def token = tokens.substring(start, secondStop + 1)
        codeWriter.writeToken(token, fileWriter, classScope, callerType, isWrite)
        return tokens.substring(secondStop + 1)
    }

    private static String getIdentifierName(String tokens) {
        def token = getNextToken(tokens)
        return token.substring(13, token.indexOf("/") - 2)
    }

    private static String getOp(String tokens) {
        def token = getNextToken(tokens)
        def index = token.indexOf("/")
        if (!token.substring(token.indexOf("/") + 1).contains("/")) {
            return token.substring(9, token.indexOf("/") - 2)
        }
        return "/"
    }

    private static String getType(String tokens, int start=13) {
        def token = getNextToken(tokens)
        return token.substring(start, token.indexOf("/") - 2)
    }

    def static ParseClass(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        classScope = null
        classScope = new SymbolTable()
        tokens = tokens.substring(8)
        codeWriter.writeClassOpen(fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        className = getIdentifierName(tokens)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)

        tokens = ParseClassVarDec(codeWriter, tokens, fileWriter)

        boolean isMethod = false
        while (getNextToken(tokens).contains("constructor") || getNextToken(tokens).contains("function") || getNextToken(tokens).contains("method")) {
            if (getNextToken(tokens).contains("constructor")) {
                functionType = "constructor"
            } else if (getNextToken(tokens).contains("function")) {
                functionType = "function"
            } else if (getNextToken(tokens).contains("method")) {
                functionType = "method"
                isMethod = true
            }
            tokens = ParseSubDec(codeWriter, tokens, fileWriter, isMethod)
        }

        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        codeWriter.writeClassClose(fileWriter)
    }

    static String ParseClassVarDec(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        def nexttok = getNextToken(tokens)
        while (getNextToken(tokens).contains("static") || getNextToken(tokens).contains("field")) {
            codeWriter.writeClassVarDecOpen(fileWriter)
            SymbolKind kind = null
            if (getNextToken(tokens).contains("static")) {
                kind = SymbolKind.STATIC
            } else {
                kind = SymbolKind.FIELD
            }
            tokens = writeNextToken(codeWriter, tokens, fileWriter)
            def type = getType(tokens)
            tokens = ParseType(codeWriter, tokens, fileWriter)
            tokens = ParseVarName(codeWriter, tokens, fileWriter, kind, type)
            while (getNextToken(tokens).contains("<symbol> ,")) {
                tokens = writeNextToken(codeWriter, tokens, fileWriter)
                tokens = ParseVarName(codeWriter, tokens, fileWriter, kind, type)
            }
            tokens = writeNextToken(codeWriter, tokens, fileWriter)
            codeWriter.writeClassVarDecClose(fileWriter)
        }
        return tokens
    }

    static String ParseType(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        tokens = ParseClassName(codeWriter, tokens, fileWriter, true)
        return tokens
    }

    static String ParseClassName(CodeWriter codeWriter, String tokens, FileWriter fileWriter, boolean isWrite) {
        return writeNextToken(codeWriter, tokens, fileWriter, null, isWrite)
    }

    static String ParseSubroutineName(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        functionName = getIdentifierName(tokens)
        return writeNextToken(codeWriter, tokens, fileWriter, null, false)
    }

    static String ParseVarName(CodeWriter codeWriter, String tokens, FileWriter fileWriter, SymbolKind kind, String type) {
        classScope.define(getIdentifierName(tokens), type, kind)
        return writeNextToken(codeWriter, tokens, fileWriter, "", false)
    }

    static String ParseVarNameCall(CodeWriter codeWriter, String tokens, FileWriter fileWriter, boolean isWrite) {
        return writeNextToken(codeWriter, tokens, fileWriter, "", isWrite)
    }

    static String ParseSubDec(CodeWriter codeWriter, String tokens, FileWriter fileWriter, boolean isMethod) {
        classScope.startSubroutine(isMethod)
        isVoid = false
        ifLabelIndex = -1
        whileLabelIndex = -1
        codeWriter.writeSubDecOpen(fileWriter, className)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        if (getNextToken(tokens).contains("void")) {
            isVoid = true
            tokens = writeNextToken(codeWriter, tokens, fileWriter)
        } else {
            tokens = ParseType(codeWriter, tokens, fileWriter)
        }
        tokens = ParseSubroutineName(codeWriter, tokens, fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        tokens = ParseParameterList(codeWriter, tokens, fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        tokens = ParseSubroutineBody(codeWriter, tokens, fileWriter)
        codeWriter.writeSubDecClose(fileWriter)
        return tokens
    }

    static String ParseParameterList(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        codeWriter.writeParameterListOpen(fileWriter)
        def type = ""
        if (!getNextToken(tokens).contains(")")){
            if (getNextToken(tokens).contains("int") || getNextToken(tokens).contains("char") || getNextToken(tokens).contains("boolean")) {
                type = getType(tokens, 13)
            } else {
                type = getIdentifierName(tokens)
            }
            tokens = ParseType(codeWriter, tokens, fileWriter)
            tokens = ParseVarName(codeWriter, tokens, fileWriter, SymbolKind.ARG, type)
            while (getNextToken(tokens).contains("<symbol> ,")) {
                tokens = writeNextToken(codeWriter, tokens, fileWriter)
                if (getNextToken(tokens).contains("int") || getNextToken(tokens).contains("char") || getNextToken(tokens).contains("boolean")) {
                    type = getType(tokens, 13)
                } else {
                    type = getIdentifierName(tokens)
                }
                tokens = ParseType(codeWriter, tokens, fileWriter)
                tokens = ParseVarName(codeWriter, tokens, fileWriter, SymbolKind.ARG, type)
            }
        }
        codeWriter.writeParameterListClose(fileWriter)
        return tokens
    }

    static String ParseSubroutineBody(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        codeWriter.writeSubroutineBodyOpen1(fileWriter, className, functionName, classScope.varCount(SymbolKind.VAR))
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        while (getNextToken(tokens).contains("var")) {
            tokens = ParseVarDec(codeWriter, tokens, fileWriter)
        }
        codeWriter.writeSubroutineBodyOpen2(fileWriter, className, functionName, classScope.varCount(SymbolKind.VAR))
        if (functionType == "constructor") {
            codeWriter.writeMemAlloc(fileWriter, classScope.varCount(SymbolKind.FIELD))
        } else if (functionType == "method") {
            codeWriter.pushPopThis(fileWriter)
        }
        tokens = ParseStatements(codeWriter, tokens, fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        codeWriter.writeSubroutineBodyClose(fileWriter)
        return tokens
    }

    static String ParseVarDec(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        codeWriter.writeVarDecOpen(fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        def type = getType(tokens)
        tokens = ParseType(codeWriter, tokens, fileWriter)
        tokens = ParseVarName(codeWriter, tokens, fileWriter, SymbolKind.VAR, type)
        while (getNextToken(tokens).contains("<symbol> ,")) {
            tokens = writeNextToken(codeWriter, tokens, fileWriter)
            tokens = ParseVarName(codeWriter, tokens, fileWriter, SymbolKind.VAR, type)
        }
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        codeWriter.writeVarDecClose(fileWriter)
        return tokens
    }

    static String ParseStatements(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        codeWriter.writeStatementsOpen(fileWriter)
        while (getNextToken(tokens).contains("let") ||
                getNextToken(tokens).contains("if") ||
                getNextToken(tokens).contains("while") ||
                getNextToken(tokens).contains("do") ||
                getNextToken(tokens).contains("return")) {
            def doStat = false
            if (getNextToken(tokens).contains("do")) {
                doStat = true
            }
            tokens = ParseStatement(codeWriter, tokens, fileWriter)
            if (doStat) {
                codeWriter.popTemp(fileWriter)
            }
        }
        codeWriter.writeStatementsClose(fileWriter)
        return tokens
    }

    static String ParseStatement(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        def nextTok = getNextToken(tokens)
        if (nextTok.contains("let")) {
            tokens = ParseLetStatement(codeWriter, tokens, fileWriter)
        } else if (nextTok.contains("if")) {
            tokens = ParseIfStatement(codeWriter, tokens, fileWriter)
        } else if (nextTok.contains("while")) {
            tokens = ParseWhileStatement(codeWriter, tokens, fileWriter)
        } else if (nextTok.contains("do")) {
            tokens = ParseDoStatement(codeWriter, tokens, fileWriter)
        } else if (nextTok.contains("return")) {
            tokens = ParseReturnStatement(codeWriter, tokens, fileWriter)
        }
        return tokens
    }

    static String ParseLetStatement(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        def isArray = false
        codeWriter.writeLetStatementOpen(fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        def varname = getIdentifierName(tokens)
        tokens = ParseVarNameCall(codeWriter, tokens, fileWriter, false)
        if (getNextToken(tokens).contains("[")) {
            isArray = true
            tokens = writeNextToken(codeWriter, tokens, fileWriter)
            tokens = ParseExpression(codeWriter, tokens, fileWriter)
            codeWriter.pushCallerData(fileWriter, classScope.kindOf(varname).name(), classScope.indexOf(varname))
            codeWriter.writeAdd(fileWriter)
            tokens = writeNextToken(codeWriter, tokens, fileWriter)
        }
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        tokens = ParseExpression(codeWriter, tokens, fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        if (isArray) {
            codeWriter.writePopToArray(fileWriter)
            codeWriter.writeLetStatementClose1(fileWriter, classScope.kindOf(varname).name(), classScope.indexOf(varname))
        } else {
            codeWriter.writeLetStatementClose(fileWriter, classScope.kindOf(varname).name(), classScope.indexOf(varname))
        }
        return tokens
    }

    static String ParseIfStatement(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        ifLabelIndex++
        def tmpindex = ifLabelIndex
        codeWriter.writeIfStatementOpen(fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        tokens = ParseExpression(codeWriter, tokens, fileWriter)
        codeWriter.writeIf1(fileWriter, tmpindex)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        tokens = ParseStatements(codeWriter, tokens, fileWriter)
        codeWriter.writeIf2(fileWriter, tmpindex)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        if (getNextToken(tokens).contains("else")) {
            tokens = writeNextToken(codeWriter, tokens, fileWriter)
            tokens = writeNextToken(codeWriter, tokens, fileWriter)
            tokens = ParseStatements(codeWriter, tokens, fileWriter)
            tokens = writeNextToken(codeWriter, tokens, fileWriter)
        }
        codeWriter.writeIf3(fileWriter, tmpindex)

        codeWriter.writeIfStatementClose(fileWriter)
        return tokens
    }

    static String ParseWhileStatement(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        whileLabelIndex++
        def tmpindex = whileLabelIndex
        codeWriter.writeWhileStatementOpen(fileWriter)
        codeWriter.writeWhile1(fileWriter, tmpindex)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        tokens = ParseExpression(codeWriter, tokens, fileWriter)
        codeWriter.writeWhile2(fileWriter, tmpindex)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        tokens = ParseStatements(codeWriter, tokens, fileWriter)
        codeWriter.writeWhile3(fileWriter, tmpindex)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        codeWriter.writeWhileStatementClose(fileWriter)
        return tokens
    }

    static String ParseDoStatement(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        codeWriter.writeDoStatementOpen(fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        tokens = ParseSubRoutineCall(codeWriter, tokens, fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        codeWriter.writeDoStatementClose(fileWriter)
        return tokens
    }

    static String ParseReturnStatement(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        codeWriter.writeReturnStatementOpen(fileWriter)
        tokens = writeNextToken(codeWriter, tokens, fileWriter)

        if (getNextToken(tokens).contains("integerConstant") ||
                getNextToken(tokens).contains("stringConstant") ||
                getNextToken(tokens).contains("true") ||
                getNextToken(tokens).contains("false") ||
                getNextToken(tokens).contains("null") ||
                getNextToken(tokens).contains("this") ||
                getNextToken(tokens).contains("identifier") ||
                getNextToken(tokens).contains("(") ||
                getNextToken(tokens).contains("-") ||
                getNextToken(tokens).contains("~")
        ) {
            tokens = ParseExpression(codeWriter, tokens, fileWriter)
        }

        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        codeWriter.writeReturnStatementClose(fileWriter, functionType, isVoid)
        return tokens
    }

    static String ParseExpression(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        codeWriter.writeExpressionOpen(fileWriter)
        tokens = ParseTerm(codeWriter, tokens, fileWriter)
        while (getNextToken(tokens).contains("+") ||
                getNextToken(tokens).contains("-") ||
                getNextToken(tokens).contains("*") ||
                getNextToken(tokens).contains("<symbol> /") ||
                getNextToken(tokens).contains("&amp;") ||
                getNextToken(tokens).contains("|") ||
                getNextToken(tokens).contains("&lt;") ||
                getNextToken(tokens).contains("&gt;") ||
                getNextToken(tokens).contains("=")
        ) {
            def op = getOp(tokens)
            tokens = ParseOp(codeWriter, tokens, fileWriter)
            tokens = ParseTerm(codeWriter, tokens, fileWriter)
            codeWriter.writeOp(fileWriter, op, "expression")
        }
        codeWriter.writeExpressionClose(fileWriter)
        return tokens
    }

    static String ParseTerm(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        codeWriter.writeTermOpen(fileWriter)
        if (getNextToken(tokens).contains("integerConstant") ||
                getNextToken(tokens).contains("stringConstant")
        ) {
            tokens = writeNextToken(codeWriter, tokens, fileWriter)
        } else if (getNextToken(tokens).contains("true") ||
                getNextToken(tokens).contains("false") ||
                getNextToken(tokens).contains("null") ||
                getNextToken(tokens).contains("this")) {
            tokens = ParseKeywordConstant(codeWriter, tokens, fileWriter)
        } else if (getNextToken(tokens).contains("identifier")) {
            def nextToken = getNextToken(tokens.substring(tokens.indexOf("/") + 12))
            if (nextToken.contains("(") ||
                    nextToken.contains(".")
            ) {
                tokens = ParseSubRoutineCall(codeWriter, tokens, fileWriter)
            } else {
                tokens = ParseVarNameCall(codeWriter, tokens, fileWriter, true)
                if (getNextToken(tokens).contains("[")) {
                    tokens = writeNextToken(codeWriter, tokens, fileWriter)
                    tokens = ParseExpression(codeWriter, tokens, fileWriter)
                    codeWriter.writePushToArray(fileWriter)
                    tokens = writeNextToken(codeWriter, tokens, fileWriter)
                }
            }
        } else if (getNextToken(tokens).contains("(")) {
            tokens = writeNextToken(codeWriter, tokens, fileWriter)
            tokens = ParseExpression(codeWriter, tokens, fileWriter)
            tokens = writeNextToken(codeWriter, tokens, fileWriter)
        } else if (getNextToken(tokens).contains("-") ||
                getNextToken(tokens).contains("~")) {
            def op = getOp(tokens)
            tokens = ParseUnaryOp(codeWriter, tokens, fileWriter)
            tokens = ParseTerm(codeWriter, tokens, fileWriter)
            codeWriter.writeOp(fileWriter, op, "term")
        }
        codeWriter.writeTermClose(fileWriter)
        return tokens
    }

    static String ParseSubRoutineCall(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        codeWriter.writeSubroutineCallOpen1(fileWriter, null, false, null, 0, 0)
        def callFuncName = getIdentifierName(tokens)
        def varCount = 0
        def isMethod = false
        def callerKind = null
        def callerIndex = 0
        tokens = ParseClassName(codeWriter, tokens, fileWriter, false)
        if (getNextToken(tokens).contains("(")) {
            callFuncName = "${className}.${callFuncName}"
            tokens = writeNextToken(codeWriter, tokens, fileWriter, null, false)
            codeWriter.pushCallerData(fileWriter, "pointer", 0)
            classScope.argCounter = classScope.argCounter + 1
            (varCount, tokens) = ParseExpressionList(codeWriter, tokens, fileWriter, 1)
            tokens = writeNextToken(codeWriter, tokens, fileWriter)
        } else {
            tokens = writeNextToken(codeWriter, tokens, fileWriter)
            callerKind = classScope.kindOf(callFuncName)
            if (callerKind == null) {
                callFuncName = "${callFuncName}.${getIdentifierName(tokens)}"
                callFuncName = callFuncName.substring(0, 1).toUpperCase() + callFuncName.substring(1)
                tokens = ParseSubroutineName(codeWriter, tokens, fileWriter)
                tokens = writeNextToken(codeWriter, tokens, fileWriter, null, false)
                (varCount, tokens) = ParseExpressionList(codeWriter, tokens, fileWriter, 0)
                tokens = writeNextToken(codeWriter, tokens, fileWriter, null, false)
            } else {
                callerIndex = classScope.indexOf(callFuncName)
                callFuncName = "${classScope.typeOf(callFuncName)}.${getIdentifierName(tokens)}"
                codeWriter.pushCallerData(fileWriter, callerKind.name(), callerIndex)
                callFuncName = callFuncName.substring(0, 1).toUpperCase() + callFuncName.substring(1)
                tokens = ParseSubroutineName(codeWriter, tokens, fileWriter)
                tokens = writeNextToken(codeWriter, tokens, fileWriter, null, false)
                (varCount, tokens) = ParseExpressionList(codeWriter, tokens, fileWriter, 1)
                tokens = writeNextToken(codeWriter, tokens, fileWriter, null, false)
            }

        }
        codeWriter.writeSubroutineCallOpen2(fileWriter, callFuncName, isMethod, callerKind, callerIndex, varCount)
        return tokens
    }

    static def ParseExpressionList(CodeWriter codeWriter, String tokens, FileWriter fileWriter, int count) {
        codeWriter.writeExpressionListOpen(fileWriter)
        def varCount = count
        if (!getNextToken(tokens).contains(")")) {
            varCount = varCount + 1
            tokens = ParseExpression(codeWriter, tokens, fileWriter)
            while (getNextToken(tokens).contains(",")) {
                varCount = varCount + 1
                tokens = writeNextToken(codeWriter, tokens, fileWriter)
                tokens = ParseExpression(codeWriter, tokens, fileWriter)
            }
        }
        codeWriter.writeExpressionListClose(fileWriter)
        return [varCount, tokens]
    }

    static String ParseOp(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        return tokens
    }

    static String ParseUnaryOp(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        return tokens
    }

    static String ParseKeywordConstant(CodeWriter codeWriter, String tokens, FileWriter fileWriter) {
        tokens = writeNextToken(codeWriter, tokens, fileWriter)
        return tokens
    }
}
