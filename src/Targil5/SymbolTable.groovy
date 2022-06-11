package Targil5

class SymbolTable {

    public Hashtable<String, Symbol> classTable = new Hashtable<>()
    public Hashtable<String, Symbol> methodTable = new Hashtable<>()
    public int staticCounter = 0
    public int fieldCounter = 0
    public int argCounter = 0
    public int varCounter = 0

    SymbolTable(){
        this.classTable = new Hashtable<>()
        this.methodTable = new Hashtable<>()
        this.staticCounter = 0
        this.fieldCounter = 0
        this.argCounter = 0
        this.varCounter = 0
    }

    void startSubroutine(boolean  isMethod) {
        this.methodTable = new Hashtable<>()
        this.argCounter = 0
        this.varCounter = 0
        if (isMethod) this.argCounter = 1
    }

    void define(String name, String type, SymbolKind kind) {
        switch (kind) {
            case SymbolKind.FIELD:
                this.classTable.put(name, new Symbol(name, type, kind, this.fieldCounter))
                this.fieldCounter = this.fieldCounter + 1
                break
            case SymbolKind.STATIC:
                this.classTable.put(name, new Symbol(name, type, kind, this.staticCounter))
                this.staticCounter = this.staticCounter + 1
                break
            case SymbolKind.ARG:
                this.methodTable.put(name, new Symbol(name, type, kind, this.argCounter))
                this.argCounter = this.argCounter + 1
                break
            case SymbolKind.VAR:
                this.methodTable.put(name, new Symbol(name, type, kind, this.varCounter))
                this.varCounter = this.varCounter + 1
                break
        }
    }

    int varCount(SymbolKind kind) {
        switch (kind) {
            case SymbolKind.FIELD:
                return this.fieldCounter
            case SymbolKind.STATIC:
                return this.staticCounter
            case SymbolKind.ARG:
                return this.argCounter
            case SymbolKind.VAR:
                return this.varCounter
        }
    }

    SymbolKind kindOf(String name) {
        if (methodTable.containsKey(name)) {
            return methodTable.get(name).with {
                it.kind
            }
        } else if (classTable.containsKey(name)) {
            return classTable.get(name).with {
                it.kind
            }
        } else return null
    }

    String typeOf(String name) {
        if (methodTable.containsKey(name)) {
            return methodTable.get(name).with {
                it.type
            }
        } else if (classTable.containsKey(name)) {
            return classTable.get(name).with {
                it.type
            }
        } else return null
    }

    int indexOf(String name) {
        if (methodTable.containsKey(name)) {
            return methodTable.get(name).with {
                it.id
            }
        } else if (classTable.containsKey(name)) {
            return classTable.get(name).with {
                it.id
            }
        } else return 0
    }
}
