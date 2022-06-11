package Targil4

class Targil4 {

    static void main(String[] args) {
        def sourcePath = args[0]
        new File(sourcePath).eachFile { file ->
            if (file.name.endsWith(".jack")) {
                def index = file.path.lastIndexOf(".")
                if (index > -1) {
                    def destinationPath = file.path.substring(0, index) + "T.xml"
                    def inputString = file.text
                    def writeFile = new FileWriter(destinationPath)
                    Tokenizer.tokenize(inputString, writeFile)
                    writeFile.flush()
                    new File(destinationPath).with {readFile ->
                        index = file.path.lastIndexOf(".")
                        if (index > -1) {
                            def destinationPathParse = file.path.substring(0, index) + ".xml"
                            def inputStringParse = readFile.text
                            def writeFileParse = new FileWriter(destinationPathParse)
                            CodeParser.ParseClass(new XMLCodeWriter(), inputStringParse, writeFileParse)
                            writeFileParse.flush()
                        }
                    }
                }
            }
        }
    }
}
