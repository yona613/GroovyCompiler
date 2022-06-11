package Targil5

public enum SymbolKind {
    STATIC('static'),
    FIELD('field'),
    ARG('arg'),
    VAR('var')

    final String id
    static final Map map

    static {
        map = [:] as TreeMap
        values().each{ symbol ->
            println "id: " + symbol.id
            map.put(symbol.id, symbol)
        }

    }

    private SymbolKind(String id) {
        this.id = id
    }

    static getSymbolKindEnum( id ) {
        map[id]
    }
}