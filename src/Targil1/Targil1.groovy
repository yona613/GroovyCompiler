package Targil1

class Targil1 {

    static void main(String[] args) {
        def sourcePath = args[0]
        new File(sourcePath).eachFile {file ->
            if (file.name.endsWith(".vm")) {
                def index = file.path.lastIndexOf(".")
                if (index > -1){
                    def destinationPath = file.path.substring(0, index) + ".asm"
                    Parser.convertFile(file.path, destinationPath, false)
                }
            }
        }
    }
}
