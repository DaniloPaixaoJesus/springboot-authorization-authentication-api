
CRUD completo de usuários, tela responsiva, API REST
API REST para CRUD da entidade "Master" e "Detail"

## Algumas considerações a respeito de algumas decisões arquiteturais:

Package-by-feature -> os pacotes estão organizados por FEATURES ao invés de LAYERS

SpringBoot -> framework java com a filosofia Convention over Configuration. Facilita a configuração da aplicação e a execução no ambiente DEV. Uma vez que este traz um servidor embarcado.

Liquibase -> Mantém histórico de todas as alterações da estrutura do banco de dados. Facilita o "rollback" de releases, facilidade para gerar script de carga.

LomBok ->  excelente para abstracao de implementação de alguns padrões de projeto, além de acelerar o desenvolvimento e escrita do código.

Swwager -> framework para documentação de API REST.
http://localhost:8080/swagger-ui.html -> API