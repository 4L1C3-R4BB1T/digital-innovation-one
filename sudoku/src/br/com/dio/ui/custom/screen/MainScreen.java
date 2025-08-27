package br.com.dio.ui.custom.screen;

import br.com.dio.model.Space;
import br.com.dio.service.BoardService;
import br.com.dio.service.NotifierService;
import br.com.dio.ui.custom.button.CheckGameStatusButton;
import br.com.dio.ui.custom.button.FinishGameButton;
import br.com.dio.ui.custom.button.ResetButton;
import br.com.dio.ui.custom.frame.MainFrame;
import br.com.dio.ui.custom.input.NumberText;
import br.com.dio.ui.custom.panel.MainPanel;
import br.com.dio.ui.custom.panel.SudokuSector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static br.com.dio.service.EventEnum.CLEAR_SPACE;
import static javax.swing.JOptionPane.*;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;
    private final NotifierService notifierService;

    JButton checkGameStatusButton;
    JButton finishGameButton;
    JButton resetButton;

    public MainScreen(final Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
        this.notifierService = new NotifierService();
    }

    public void buildMainScreen() {
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);

        for (int r = 0; r < 9; r += 3) {
            var endRow = r + 2;
            for (int c = 0; c < 9; c += 3) {
                var endCol = c + 2;
                var spaces = getSpacesFromSector(boardService.getSpaces(), c, endCol, r, endRow);
                JPanel sector = generateSection(spaces);
                mainPanel.add(sector);
            }
        }
        
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        addResetButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private List<Space> getSpacesFromSector(final List<List<Space>> spaces, final int initCol,
                                            final int endCol, final int initRow, final int endRow) {
        List<Space> spaceSector = new ArrayList<>();
        for (int r = initRow; r <= endRow; r++) {
            for (int c = initCol; c <= endCol; c++) {
                spaceSector.add(spaces.get(c).get(r));
            }
        }
        return spaceSector;
    }

    private JPanel generateSection(final List<Space> spaces) {
        List<NumberText> fields = new ArrayList<>(spaces.stream().map(NumberText::new).toList());
        fields.forEach(t -> notifierService.subscriber(CLEAR_SPACE, t));
        return new SudokuSector(fields);
    }

    private void addCheckGameStatusButton(JPanel mainPanel) {
        checkGameStatusButton = new CheckGameStatusButton(e -> {
            var hasErrors = boardService.hasErrors();
            var gameStatus = boardService.getStatus();
            var message = switch (gameStatus) {
                case NON_STARTED -> "O jogo não foi iniciado";
                case INCOMPLETE -> "O jogo está incompleto";
                case COMPLETE -> "O jogo está completo";
            };
            message += hasErrors ? " e contém erros!" : " e não contém erros!";
            showMessageDialog(
                    null,
                    message,
                    "Verificar Jogo",
                    WARNING_MESSAGE
            );
        });
        mainPanel.add(checkGameStatusButton);
    }

    private void addFinishGameButton(JPanel mainPanel) {
        finishGameButton = new FinishGameButton(e -> {
            if (boardService.gameIsFinished()) {
                showConfirmDialog(null, "Parabéns! Você concluiu o jogo.");
                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            } else {
                showMessageDialog(
                        null,
                        "Seu jogo tem alguma inconsistência. Ajuste e tente novamente.",
                        "Concluir Jogo",
                        WARNING_MESSAGE
                );
            }
        });
        mainPanel.add(finishGameButton);
    }

    private void addResetButton(JPanel mainPanel) {
        resetButton = new ResetButton(e -> {
            var dialogResult = showConfirmDialog(
                    null,
                    "Deseja realmente reiniciar o jogo?",
                    "Reiniciar Jogo",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
            );
            if (dialogResult == 0) {
                boardService.reset();
                notifierService.notify(CLEAR_SPACE);
            }
        });
        mainPanel.add(resetButton);
    }

}
