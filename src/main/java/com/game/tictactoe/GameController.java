package com.game.tictactoe;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String home(Model model) {


        model.addAttribute("board", gameService.getBoard());
        model.addAttribute("message", "New Game Started!");
        model.addAttribute("playerWins", gameService.getPlayerWins());
        model.addAttribute("systemWins", gameService.getSystemWins());
        model.addAttribute("draws", gameService.getDraws());

        return "index";
    }

@PostMapping("/move")
public String playMove(@RequestParam int row,
                       @RequestParam int col,
                       Model model) {

    gameService.playMove(row, col);

    model.addAttribute("board", gameService.getBoard());
    model.addAttribute("message", gameService.getMessage());
    model.addAttribute("playerWins", gameService.getPlayerWins());
    model.addAttribute("systemWins", gameService.getSystemWins());
    model.addAttribute("draws", gameService.getDraws());

    return "index";
}

    

    @PostMapping("/new-game")
    public String newGame() {

        gameService.resetBoard();

        return "redirect:/";
    }
}