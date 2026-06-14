package br.rosa.svthread.service;

import br.rosa.svthread.record.Test;
import br.rosa.svthread.record.TestUm;
import br.rosa.svthread.util.Util;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    public Test fazTest(Integer id, String texto) {
        Util.threadSleep(1_000);
        return new Test(id, "Teste para " + texto);
    }

    public TestUm geraInformacaoUm(Integer id, String texto) {
        Util.threadSleep(500);
        return new TestUm(id, "Teste Um para " + texto);
    }

    public TestUm geraInformacaoDois(Integer id, String texto) {
        Util.threadSleep(500);
        return new TestUm(id, "Teste Um do método Dois para " + texto);
    }

    public TestUm geraInformacaoTres(Integer id, String texto) {
        Util.threadSleep(500);
        return new TestUm(id, "Teste Um do método Três para " + texto);
    }

    public Object geraInformacaoQuatroComExcecao(Integer id, String texto) {
        Util.threadSleep(600);
        throw new RuntimeException("Deu ruim no método Quatro");
    }
}
