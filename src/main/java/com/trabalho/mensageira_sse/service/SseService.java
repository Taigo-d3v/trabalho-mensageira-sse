package com.trabalho.mensageira_sse.service;

import com.trabalho.mensageira_sse.model.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Serviço responsável por gerenciar as conexões SSE e realizar o broadcast de mensagens.
 */
@Service
public class SseService {

    // Lista thread-safe para armazenar os emissores ativos
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    /**
     * Adiciona um novo emissor (cliente) à lista de assinantes.
     * @return SseEmitter configurado.
     */
    public SseEmitter subscribe() {
        // Timeout de 30 minutos (1800000 ms)
        SseEmitter emitter = new SseEmitter(1800_000L);

        this.emitters.add(emitter);

        // Remove o emissor da lista quando a conexão é finalizada ou ocorre erro
        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));
        emitter.onError((e) -> this.emitters.remove(emitter));

        return emitter;
    }

    /**
     * Envia um pulso (heartbeat) a cada 15 segundos para manter as conexões ativas.
     */
    @Scheduled(fixedRate = 15000)
    public void sendHeartbeat() {
        List<SseEmitter> deadEmitters = new CopyOnWriteArrayList<>();
        
        this.emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("ping")
                        .data(""));
            } catch (IOException e) {
                deadEmitters.add(emitter);
            }
        });
        
        this.emitters.removeAll(deadEmitters);
    }

    /**
     * Envia uma mensagem para todos os clientes conectados.
     * @param message Mensagem a ser transmitida.
     */
    public void broadcast(Message message) {
        List<SseEmitter> deadEmitters = new CopyOnWriteArrayList<>();

        this.emitters.forEach(emitter -> {
            try {
                // Envia o objeto message como um evento SSE
                emitter.send(SseEmitter.event()
                        .name("message")
                        .data(message));
            } catch (IOException e) {
                // Marca emissores que falharam para remoção
                deadEmitters.add(emitter);
            }
        });

        this.emitters.removeAll(deadEmitters);
    }
}
