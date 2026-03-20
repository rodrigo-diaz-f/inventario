package com.ittalent.desafio.inventario.config;

import com.ittalent.desafio.inventario.exception.LimitePeticionesExcedidoException;
import com.ittalent.desafio.inventario.util.AppConstantes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LimitePeticionesInterceptor implements HandlerInterceptor {

    private final ConcurrentHashMap<String, Deque<Long>> requestLog = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ip = extraerIP(request);
        long ahora = System.currentTimeMillis();
        long tiempoTranscurridoEnMs = ahora - AppConstantes.MINUTO_EN_MILISEGUNDOS;

        Deque<Long> instantesDePeticiones = requestLog.computeIfAbsent(ip, k -> new ArrayDeque<>());

        synchronized (instantesDePeticiones) {
            while (!instantesDePeticiones.isEmpty() && instantesDePeticiones.peekFirst() < tiempoTranscurridoEnMs) {
                instantesDePeticiones.pollFirst();
            }

            if (instantesDePeticiones.size() >= AppConstantes.MAXIMAS_PETICIONES) {
                throw new LimitePeticionesExcedidoException(ip);
            }

            instantesDePeticiones.addLast(ahora);
        }

        return true;
    }

    private String extraerIP(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
