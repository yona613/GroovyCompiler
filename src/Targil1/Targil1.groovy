package Targil1

class Targil1 {

    static void main(String[] args) {
        def sourcePath = System.in.newReader().readLine()
        def destinationPath = System.in.newReader().readLine()
        Parser.convertFile(sourcePath,destinationPath)
    }
}
