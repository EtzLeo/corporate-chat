package ru.simbirsoft.corporatechat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.corporatechat.service.impl.CommandService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bot")
public class BotController {
    private final CommandService commandService;

    @GetMapping
    public String createMessage(@RequestParam String command) throws IOException {
        return commandService.recognizeCommandType(command);
    }
}
