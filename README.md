# TesteIsaacW
Teste realizado para a empresa 

O teste consiste em projetar um estacionamento utilizando principios de orientação a objetos.

## Estrutura:

O código possui as seguintas classes:

Parking: A abstração do estacionamento.

Vehicle: Classe abstrata que pode ser que é herdade pelas classes Motorbike, Car e Van.
Onde cada uma possui sua propria regra de estacionamento

Ticket: Uma data class que contem o carro e a vaga utilizada

SpaceTypeAndAmount: Uma data class usada para armazenar o tipo de vaga e a quantidade dela necessária

Space: Um Enum utilizado parar diferenciar os tipos de vagas

## Objetivo:
O objetivo desta estrutura é a rápida inserção de novos tipos de veiculos ou vagas.

Ex. Novo veiculo: Onibus.
- Criar class Onibus que herda de Vehicle
- Colocar sua regra de estacionamento

Ex. Nova vaga: Gigante
- Criar tipo GIGANTE no Enum
- Adicionar SubParking ao estacionamento, passando o novo tipo e sua quantidade
- Usar novo tipo de vaga para os veiculos
