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





comando para rodar controllers
# 1. Definir o caminho base do repositório Maven
REPO="/home/kaio/.m2/repository/org/openjfx"

# 2. Montar o classpath do JavaFX manualmente apontando para os jars Linux versão 13
JFX_CP="$REPO/javafx-controls/13/javafx-controls-13-linux.jar:$REPO/javafx-fxml/13/javafx-fxml-13-linux.jar:$REPO/javafx-base/13/javafx-base-13-linux.jar:$REPO/javafx-graphics/13/javafx-graphics-13-linux.jar"

# 3. Rodar a verificação
~/Downloads/openjml-ubuntu-22.04-21-0.18/openjml -esc -progress \
-classpath "$(cat '/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/classpath.txt'):$JFX_CP" \
-sourcepath "/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/src/main/java" \
"/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/src/main/java/com/br/model"/*.java \
"/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/src/main/java/com/br/controller/Cadastrar_clienteController.java"


# 1. Configurar variáveis de ambiente (para garantir)
EPO="/home/kaio/.m2/repository/org/openjfx"
JFX_CP="$REPO/javafx-controls/13/javafx-controls-13-linux.jar:$REPO/javafx-fxml/13/javafx-fxml-13-linux.jar:$REPO/javafx-base/13/javafx-base-13-linux.jar:$REPO/javafx-graphics/13/javafx-graphics-13-linux.jar"

# 2. Rodar a verificação
~/Downloads/openjml-ubuntu-22.04-21-0.18/openjml -esc -progress -timeout 240 \
-classpath "$(cat '/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/classpath.txt'):$JFX_CP" \
-sourcepath "/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/src/main/java" \
"/home/kaio/Documentos/ufrn/6 periodo/logica/TI-Motors/timotors/src/main/java/com/br/controller/Vender_veiculoController.java"