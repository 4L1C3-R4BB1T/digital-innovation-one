package br.com.dio;

import br.com.dio.ui.custom.screen.MainScreen;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        final var gameConfig = Stream.of(args)
                .collect(Collectors.toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));
        var mainScreen = new MainScreen(gameConfig);
        mainScreen.buildMainScreen();
    }

}
