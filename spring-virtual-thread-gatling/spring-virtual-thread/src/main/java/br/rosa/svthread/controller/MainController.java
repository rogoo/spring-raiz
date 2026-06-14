package br.rosa.svthread.controller;

import br.rosa.svthread.joiner.SpeakerInfoJoiner;
import br.rosa.svthread.record.TestPrinc;
import br.rosa.svthread.record.TestUm;
import br.rosa.svthread.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.stream.Stream;

import static java.util.concurrent.StructuredTaskScope.Joiner;
import static java.util.concurrent.StructuredTaskScope.Subtask;
import static java.util.concurrent.StructuredTaskScope.open;

@RestController
@RequestMapping("/api")
public class MainController {
    @Autowired
    private TestService testService;

    @GetMapping("/thread-sem/{id}/{texto}")
    TestPrinc getSpeakersOld(@PathVariable Integer id, @PathVariable String texto) throws InterruptedException {
        var teste = testService.fazTest(id, texto);
        var maiorLength = obtemTextoMaiorLength(id, texto);

        return new TestPrinc(teste, maiorLength);
    }

    TestUm obtemTextoMaiorLength(Integer id, String texto) throws InterruptedException {
        var infoUm = testService.geraInformacaoUm(id, texto);
        var infoDois = testService.geraInformacaoDois(id, texto);
        var infoTres = testService.geraInformacaoTres(id, texto);
        //var infoQuatroExcecao = scope.fork(() -> testService.geraInformacaoQuatroComExcecao(id, texto));

        return Stream.of(infoUm, infoDois, infoTres)
                .max(Comparator.comparing(info -> info.texto().length()))
                .orElseThrow(() -> new RuntimeException("Encontrado Nada Meu Chefeeeeee"));
    }

    @GetMapping("/thread-com/{id}/{texto}")
    TestPrinc getSpeakers(@PathVariable Integer id, @PathVariable String texto) throws InterruptedException {
        try (var scope = open()) {
            var teste = scope.fork(() -> testService.fazTest(id, texto));
            var info = scope.fork(() -> obtemTextoMaiorLengthJoiner(id, texto));
            scope.join();
            return new TestPrinc(teste.get(), info.get());
        }
    }

    TestUm obtemTextoMaiorLengthJoiner(Integer id, String texto) throws InterruptedException {
        try (var scope = open(new SpeakerInfoJoiner())) {
            scope.fork(() -> testService.geraInformacaoUm(id, texto));
            scope.fork(() -> testService.geraInformacaoDois(id, texto));
            scope.fork(() -> testService.geraInformacaoTres(id, texto));

            return scope.join();
        }
    }

    TestUm obtemTextoMaiorLengthInfo(Integer id, String texto) throws InterruptedException {
        try (var scope = open(Joiner.awaitAll())) {
            var infoUm = scope.fork(() -> testService.geraInformacaoUm(id, texto));
            var infoDois = scope.fork(() -> testService.geraInformacaoDois(id, texto));
            var infoTres = scope.fork(() -> testService.geraInformacaoTres(id, texto));
            //var infoQuatroExcecao = scope.fork(() -> testService.geraInformacaoQuatroComExcecao(id, texto));

            scope.join();

            return Stream.of(infoTres, infoUm, infoDois)
                    .filter(task -> Subtask.State.SUCCESS == task.state())
                    .map(Subtask::get)
                    .max(Comparator.comparing(info -> info.texto().length()))
                    .orElseThrow(() -> new RuntimeException("No info found"));
        }
    }
}

