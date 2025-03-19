# 🌱 Eco Label Scanner

![Kotlin](https://img.shields.io/badge/Kotlin-100%25-blue)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-orange)
![Room Database](https://img.shields.io/badge/Room%20Database-Local%20Storage-green)
![Retrofit](https://img.shields.io/badge/Retrofit-API%20Client-lightgrey)

## 🎓 Projeto Acadêmico
Este aplicativo foi desenvolvido como parte de um **trabalho acadêmico da FIAP** para a disciplina de desenvolvimento mobile.  
O objetivo era criar um **MVP de um app nativo Android** que se integrasse a um serviço existente e promovesse **insights ou melhorias no contexto ESG**.  

O **Eco Label Scanner** atende a esse requisito ao permitir que usuários pesquisem produtos e avaliem **seu impacto ambiental e qualidade nutricional**, incentivando um consumo mais responsável.

## 📌 Sobre o Projeto
O **Eco Label Scanner** é um aplicativo mobile desenvolvido em **Kotlin com Jetpack Compose** que permite aos usuários **pesquisar produtos pelo nome ou código de barras**, acessando informações detalhadas sobre **impacto ambiental (Eco-Score) e qualidade nutricional (Nutri-Score)**.  

O app se conecta à **API Open Food Facts** para obter os dados e fornece um sistema de **favoritos** para que os usuários possam acompanhar produtos de interesse.

## 🎯 Objetivo
A missão do **Eco Label Scanner** é **empoderar consumidores** com informações transparentes e acessíveis sobre sustentabilidade e nutrição. Ao facilitar a análise dos produtos, o app incentiva escolhas mais responsáveis, alinhadas ao pilar **Ambiental do ESG**.

## 🏗️ Tecnologias Utilizadas
- **Kotlin** → Linguagem oficial para Android.
- **Jetpack Compose** → Construção da interface moderna e responsiva.
- **Retrofit** → Consumo eficiente da **API Open Food Facts**.
- **Room Database** → Armazenamento local de produtos favoritados.
- **Navigation Component** → Gerenciamento de navegação entre telas.
- **ViewModel + StateFlow** → Gerenciamento de estado eficiente.

## 🌍 Aplicação no Contexto ESG
O **Eco Label Scanner** está alinhado ao pilar **Ambiental** do ESG ao fornecer dados sobre o impacto ecológico dos produtos consumidos.  
Ele incentiva práticas sustentáveis, promovendo **transparência** e um consumo mais **consciente**.

## 🔥 Funcionalidades Principais
✅ **Pesquisa de Produtos** → Busca por nome ou código de barras.  
✅ **Lista de Resultados** → Exibição de produtos encontrados com informações básicas.  
✅ **Detalhes do Produto** → Exibição de **Eco-Score e Nutri-Score**, além de informações nutricionais.  
✅ **Sistema de Favoritos** → Permite salvar produtos para acesso rápido.  
✅ **Interface Intuitiva** → UI moderna e otimizada para experiência do usuário.

## 🔗 API Utilizada
O aplicativo consome dados da API **Open Food Facts**, acessível em:  
🔗 [https://world.openfoodfacts.org/api/v2](https://world.openfoodfacts.org/api/v2)  

A integração foi realizada via **Retrofit**, permitindo requisições seguras e eficientes.

## 🛠️ Como Rodar o Projeto
Siga os passos abaixo para rodar o projeto localmente:

1. Clone este repositório:
```
git clone https://github.com/jpncaetano/fiap-eco-label-scanner.git
```

2. Abra o projeto no Android Studio.
3. Sincronize as dependências do Gradle.
4. Compile e execute no emulador ou dispositivo físico.

## ⚡ Estrutura do Projeto
Abaixo está a estrutura de diretórios do projeto:
```
EcoLabelScanner/
├── .kotlin/
├── app/
│   └── src/
│       └── main/
│           └── java/
│               └── br/
│                   └── fiap/
│                       └── ecolabelscanner/
│                           ├── data/
│                           ├── model/
│                           ├── navigation/
│                           ├── network/
│                           ├── ui/
│                           ├── viewmodel/
│                           └── MainActivity.kt
├── gradle/
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle.kts
```

### Descrição dos Diretórios e Arquivos
- **.kotlin/**: Pasta gerada automaticamente pelo Kotlin para armazenar metadados.
- **app/**: Contém o código-fonte do aplicativo.
  - **data/**: Persistência e repositórios do Room Database.
  - **model/**: Modelos de dados usados no app.
  - **navigation/**: Configuração de navegação com o Navigation Component.
  - **network/**: Configuração do Retrofit e chamadas à API Open Food Facts.
  - **ui/**: Telas do aplicativo (Jetpack Compose).
  - **viewmodel/**: Gerenciamento de estado (StateFlow e ViewModel).
  - **MainActivity.kt**: Ponto de entrada do aplicativo.
- **gradle/**: Contém arquivos de configuração do Gradle.
- **.gitignore**: Arquivo para especificar quais arquivos e pastas devem ser ignorados pelo Git.
- **build.gradle.kts**: Arquivo de configuração do Gradle no nível do projeto, escrito em Kotlin Script.
- **gradle.properties**: Arquivo de propriedades do Gradle, usado para configurações globais.
- **gradlew**: Script Gradle Wrapper para sistemas Unix/Linux/Mac.
- **gradlew.bat**: Script Gradle Wrapper para sistemas Windows.
- **settings.gradle.kts**: Arquivo de configurações do Gradle, define os módulos do projeto.


## 📌 Status do Projeto
🚀 Projeto finalizado e entregue – versão v1.0 disponível.  
🔄 Possíveis melhorias futuras: testes unitários, ajustes de UI/UX e novos filtros de pesquisa.

## 👨‍💻 Autor
📌 João Pedro Nogueira Caetano  
📌 RM555667 - 2TDSOA  
📌 [GitHub](https://github.com/jpncaetano)
