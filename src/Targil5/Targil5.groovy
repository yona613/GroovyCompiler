package Targil5

import Targil4.CodeParser
import Targil4.Tokenizer
import Targil4.XMLCodeWriter

class Targil5 {

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
                            def destinationPathParse = file.path.substring(0, index) + ".vm"
                            def inputStringParse = readFile.text
                            def writeFileParse = new FileWriter(destinationPathParse)
                            CodeParser.ParseClass(new CodeGeneratorWriter(), inputStringParse, writeFileParse)
                            writeFileParse.flush()
                        }
                    }
                }
            }
        }
    }
}
