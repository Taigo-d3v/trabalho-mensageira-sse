package com.trabalho.mensageira_sse.controller;

import com.trabalho.mensageira_sse.model.Message;
import com.trabalho.mensageira_sse.service.SseService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;

/**
 * Controller que expõe os endpoints para conexão SSE e envio de mensagens.
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final SseService sseService;

    public MessageController(SseService sseService) {
        this.sseService = sseService;
    }

    /**
     * Endpoint para os clientes se inscreverem e receberem atualizações via SSE.
     */
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        return sseService.subscribe();
    }

    /**
     * Endpoint para enviar uma nova mensagem que será distribuída para todos os inscritos.
     */
    @PostMapping("/send")
    public void sendMessage(@RequestBody Message message) {
        message.setTimestamp(LocalDateTime.now());
        sseService.broadcast(message);
    }
}
