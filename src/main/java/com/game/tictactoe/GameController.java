package com.game.tictactoe;

import com.game.tictactoe.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/move")
    @ResponseBody
    public String moveGet() {
        return "Move endpoint reached";
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("board", gameService.getBoard());
        model.addAttribute("message", gameService.getMessage());

        return "index";
    }

    @PostMapping("/move")
public String playMove(@RequestParam int row,
                       @RequestParam int col,
                       Model model) {

    gameService.playMove(row, col);

    model.addAttribute("board", gameService.getBoard());
    model.addAttribute("message", gameService.getMessage());

    return "index";
}
}