package by.it.vchernetski.calcwithpatterns;

final class Patterns {
    private  Patterns(){}
    static final String OPERATION = "(?<![-+*/={,]|,\\s|^)([-+=*/])";
    static final String SCALAR = "-?[0-9]+(.[0-9]+)?";
    static final String VECTOR = "\\{(-?([0-9]+)(\\.[0-9]+)*,? ?)+\\}";
    static final String MATRIX = "\\{(\\{(-?([0-9]+)(\\.[0-9]+)*,? ?)+\\}[, ]*)+\\}";
}
