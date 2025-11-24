Comando rodar o jml no pc de kaio:
~/Downloads/openjml-ubuntu-22.04-21-0.18/openjml -esc \
-classpath "$(cat '/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/classpath.txt')" \
-sourcepath "/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/src/main/java" \
--dir "/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/src/main/java/com/br/model" \
"/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/src/main/java/com/br/controller/Cadastrar_clienteController.java"

opção com check ao inves de esc:
~/Downloads/openjml-ubuntu-22.04-21-0.18/openjml -check \
-classpath "$(cat '/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/classpath.txt')" \
-sourcepath "/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/src/main/java" \
--dir "/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/src/main/java/com/br/model" \
"/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/src/main/java/com/br/controller/Cadastrar_clienteController.java"

tem que mudar o classpath.txt tbm para o jml encontrar os arquivo javafx, se não da um bocado de erro

o arquivo module-info.java foi renomeado para poder rodar o jml (não sei pq tava dando erro), para voltar ao q era antes, bastar rodar o comando mudando os path:
mv "/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/src/main/java/module-info.java.bak" "/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/src/main/java/module-info.java"