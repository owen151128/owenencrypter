import kr.owens.parser.InputArgumentsParser;

public class Main {
    int main(String[] args) {
        InputArgumentsParser parser = new InputArgumentsParser(args);
        parser.parser();
        return 0;
    }
}
