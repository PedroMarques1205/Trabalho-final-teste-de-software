Prompt 1 - Refatoração:
"Atue como um especialista em Clean Code e Design Patterns. 
Refatore o código Gilded Rose aplicando Strategy Pattern e 
princípios SOLID. Mantenha 100% de compatibilidade comportamental."
Técnica: Persona Pattern (especialista em Clean Code)

Prompt 2 - Testes Unitários:
"Crie uma suíte completa de testes unitários JUnit 5 que garanta 
100% de cobertura. Cubra todos os cenários: casos normais, extremos, 
e casos de borda. Inclua testes para cada tipo de item."
Técnica: Especificação detalhada de requisitos

Prompt 3 - BDD:
"Gere cenários BDD em Gherkin (português) que documentem o 
comportamento esperado do sistema Gilded Rose. Inclua cenários 
para todos os tipos de itens e casos de integração."


mvn clean test
mvn clean test jacoco:report
start target/site/jacoco/index.html
mvn test-compile org.pitest:pitest-maven:mutationCoverage
start target/pit-reports/[DATA]/index.html
java -jar TestSmellDetector.jar test_files.csv