package Targil1

import Targil2.FunctionUtils

class Parser {

    static def convertFile(String sourcePath, String destinationPath, boolean isFunction) {
        def inputFile = new File(sourcePath)
        def myScanner = new Scanner(inputFile)
        def asmCode = "// Translation from ${inputFile.name} to Hack assembly\n" +
                "// @Avraham Glasberg, @YonaSzmerla\n"
        if (isFunction){
            asmCode += FunctionUtils.bootstrap()
        }
        while (myScanner.hasNextLine()) {
            def line = myScanner.nextLine()
            if (!line.startsWith("//") && !line.isEmpty()) {
                asmCode += "//" + line + "\n" + convertLine(line,inputFile.name.substring(0, inputFile.name.indexOf('.')))
            }
        }
        new FileWriter(destinationPath, true).with {
            write(asmCode)
            flush()
        }
        myScanner.close()
    }

    private static String convertLine(String line, String fileName) {
        def lineData = line.split("\\s")
        if (lineData.length > 0) {
            switch (lineData[0]) {
                case "add":
                    return OpUtils.add()
                case "sub":
                    return OpUtils.sub()
                case "neg":
                    return OpUtils.neg()
                case "eq":
                    return OpUtils.eq()
                case "gt":
                    return OpUtils.gt()
                case "lt":
                    return OpUtils.lt()
                case "and":
                    return OpUtils.and()
                case "or":
                    return OpUtils.or()
                case "not":
                    return OpUtils.not()
                case "push":
                    if (lineData.length >= 3) {
                        return MemUtils.push(lineData[1], lineData[2], fileName)
                    }
                    break
                case "pop":
                    if (lineData.length >= 3) {
                        return MemUtils.pop(lineData[1], lineData[2], fileName)
                    }
                    break
                case "label":
                    if (lineData.length >= 2){
                        return FunctionUtils.label(lineData[1])
                    }
                    break
                case "goto":
                    if (lineData.length >= 2){
                        return FunctionUtils.goto_func(lineData[1])
                    }
                    break
                case "if-goto":
                    if (lineData.length >= 2){
                        return FunctionUtils.if_goto(lineData[1])
                    }
                    break
                case "function":
                    if (lineData.length >= 3){
                        return FunctionUtils.function(lineData[1],lineData[2])
                    }
                    break
                case "return":
                    return FunctionUtils.return_function()
                    break
                case "call":
                    if (lineData.length >= 3){
                        return FunctionUtils.call(lineData[1],lineData[2])
                    }
                    break
            }
        }
        return ""
    }
}
