## Teste de Virtual Thread
Um projeto Spring para teste do uso do **virtual thread**, no Java 25. Até o 24 o virtual thread não funcionava corretamente (a virtual thread não conseguia desconectar do worker thread).

O teste foi feito usando **gastling**, com um cenário bastando simples.

### Projeto Spring
A API tem duas chamadas, uma com o uso do virtual thread e outra sem, para servir de comparação no resultado do teste do gastling.

O Service usa um thread.sleep para simular um delay. Tem um método no Service que joga execeção que não uso, mas que o virtual thread poderia tratar melhor.

### Projeto Gastling
Foi usado testes com constantes 100 usuários, por 20 segundos. Como dito anteriormente, os cenários foram bastantes simples.

Isso ai, vamos que vamosssss...