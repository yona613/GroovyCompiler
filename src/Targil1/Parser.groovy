package Targil1

class Parser {

    static def convertFile(String sourcePath, String destinationPath) {
        def inputFile = new File(sourcePath)
        def myScanner = new Scanner(inputFile)
        def asmCode = ""
        while (myScanner.hasNextLine()) {
            def line = myScanner.nextLine()
            asmCode += convertLine(line)
        }
        new FileWriter(destinationPath, false).with {
            write(asmCode)
            flush()
        }
        myScanner.close()
    }

    private static String convertLine(String line) {
        if (!line.startsWith("//")){
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
                        if (lineData.length == 3){
                            return MemUtils.push(lineData[1], lineData[2])
                        }
                        break
                    case "pop":
                        if (lineData.length == 3){
                            return MemUtils.pop(lineData[1], lineData[2])
                        }
                        break
                }
            }
        }

        return ""
    }
}
