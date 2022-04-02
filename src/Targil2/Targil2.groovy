package Targil2

import Targil1.Parser

class Targil2 {

    static void main(String[] args) {
        def sourcePath = args[0]
        boolean isBootstrap = false
        def indexPath = sourcePath.lastIndexOf("\\")
        def destinationPath = sourcePath + "\\" + sourcePath.substring(indexPath + 1, sourcePath.length()) + ".asm"
        new File(sourcePath).eachFile {file ->
            if (file.name.endsWith(".vm")) {
                def index = file.path.lastIndexOf(".")
                if (index > -1){
                    Parser.convertFile(file.path, destinationPath,isBootstrap)
                    if (isBootstrap) isBootstrap = false
                }
            }
        }
    }

}
