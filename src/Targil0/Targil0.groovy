package Targil0

class Targil0 {

    private static path = ""

    static void main(String[] args) {
        path = System.in.newReader().readLine()
        def inputA = new File(path + "/InputA.vm")
        def inputB = new File(path + "/InputB.vm")
        def myScanner = new Scanner(inputA)
        new FileWriter(path + "/Tar0.asm", true).with {
            write("InputA:\n")
            flush()
        }
        while (myScanner.hasNextLine()) {
            def line = myScanner.nextLine()
            if (line.startsWith("buy")){
                def params = line.split"\\s"
                handleBuy params[1], params[2] as int, params[3] as double
            }
            if (line.startsWith "cell"){
                def params = line.split"\\s"
                handleSell params[1], params[2] as int, params[3] as double
            }
        }
        myScanner.close()
        myScanner = new Scanner(inputB)
        new FileWriter(path + "/Tar0.asm", true).with {
            write("InputB:\n")
            flush()
        }
        while (myScanner.hasNextLine()) {
            def line = myScanner.nextLine()
            if (line.startsWith("buy")){
                def params = line.split("\\s")
                handleBuy(params[1], params[2] as int, params[3] as double)
            }
            if (line.startsWith("cell")){
                def params = line.split("\\s")
                handleSell(params[1], params[2] as int, params[3] as double)
            }
        }
        myScanner.close()
    }

    private static void handleBuy(String productName, Integer amount, Double price) {
        new FileWriter(path + "/Tar0.asm", true).with {
            write("### BUY ${productName} ###\n${amount * price}\n")
            flush()
        }
    }

    private static void handleSell(String productName, Integer amount, Double price) {
        new FileWriter(path + "/Tar0.asm", true).with {
            write("\$\$\$ SELL ${productName} \$\$\$\n${amount * price}\n")
            flush()
        }
    }
}
