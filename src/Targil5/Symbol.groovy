package Targil5

class Symbol {
    String name
    String type
    SymbolKind kind
    int id

    Symbol(String sname, String stype, SymbolKind skind, int sid) {
        this.name = sname
        this.type = stype
        this.kind = skind
        this.id = sid
    }
}
