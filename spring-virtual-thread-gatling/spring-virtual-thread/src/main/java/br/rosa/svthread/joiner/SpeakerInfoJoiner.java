package br.rosa.svthread.joiner;

import br.rosa.svthread.record.TestUm;

import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.StructuredTaskScope.Subtask;

import static java.util.concurrent.StructuredTaskScope.Joiner;

public class SpeakerInfoJoiner implements Joiner<TestUm, TestUm> {

    private final Collection<TestUm> resultados = new ConcurrentLinkedQueue<>();
    private final Collection<Throwable> excecoes = new ConcurrentLinkedQueue<>();

    @Override
    public boolean onComplete(Subtask<? extends TestUm> subtask) {
        switch (subtask.state()) {
            case SUCCESS -> resultados.add(subtask.get());
            case FAILED -> excecoes.add(subtask.exception());
            default -> throw new RuntimeException("Subtask estado errado");
        }

        // impedi que a finalização de uma subtask cancele a execução das outras
        return false;
    }

    @Override
    public TestUm result() throws Throwable {
        if (resultados.isEmpty()) {
            var re = new RuntimeException("Nenhum sucesso teve sucesso");
            excecoes.forEach(re::addSuppressed);
            throw re;
        }

        return resultados.stream().
                max(Comparator.comparingInt(t -> t.id())).orElseThrow();
    }
}
