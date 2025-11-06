# 📱 MiniPokedex (Projeto Android Nativo)

Um aplicativo Android nativo, construído 100% em **Kotlin**, que funciona como uma Pokedéx moderna. O app consome a [PokeAPI](https://pokeapi.co/) para buscar e exibir informações detalhadas sobre Pokémon.

Este projeto foi desenvolvido para a trilha de Android nativo da VNT school (Universidade Corporativa do Venturus), cobrindo desde a arquitetura MVVM e consumo de APIs até a criação de interfaces de usuário ricas e dinâmicas com componentes do Material Design.


<img width="254" height="536" alt="image" src="https://github.com/user-attachments/assets/0afe5140-4f33-41c2-b1d9-107e1abd766a" />  <img width="249" height="534" alt="image" src="https://github.com/user-attachments/assets/f41c4ce4-db49-4d38-a8c3-3888ecfa8ce6" />  <img width="248" height="531" alt="image" src="https://github.com/user-attachments/assets/a0dae709-687d-4322-9818-f4123c93b543" />



## ✨ Funcionalidades Principais

* **Lista de Pokémon:** Exibe a lista inicial de Pokémon com imagens e informações.
* **Barra de Busca:** Permite filtrar a lista em tempo real pelo nome do Pokémon.
* **Filtros Avançados:** Filtra a lista inteira por **Tipo** (ex: Fogo, Água) ou por **Geração** (ex: Geração I, Geração II).
* **Tela de Detalhes:** Ao clicar em um card, uma tela de detalhes é aberta, mostrando:
    * Imagem oficial.
    * Tipos do Pokémon (em `Chip`s).
    * Peso e Altura.
    * Stats Base (HP, Ataque, Defesa, etc.) com barras de progresso.
* **UI Dinâmica:** A tela de detalhes **muda sua cor de fundo** (em tom pastel) para combinar com o tipo principal do Pokémon, criando uma experiência confortável.
* **Splash Screen:** Tela de entrada animada (usando a `Core SplashScreen API`).
* **Ícone Adaptativo:** Ícone de app customizado (Pokébola) que se adapta a diferentes formatos de tela de celular.

## 🛠️ Tecnologias e Bibliotecas Utilizadas

* **Linguagem:** [Kotlin](https://kotlinlang.org/)
* **Arquitetura:** MVVM (Model-View-ViewModel)
* **Assincronismo:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) para chamadas de API em background.
* **Componentes de UI:**
    * [Material Design 3](https://m3.material.io/) (com `MaterialCardView`, `Chip`, etc.)
    * `RecyclerView` para a lista de Pokémon (com `ListAdapter` e `DiffUtil` para performance).
    * `SearchView` para a barra de busca.
* **Networking (API):**
    * [Retrofit](https://square.github.io/retrofit/) para fazer as chamadas HTTP à PokeAPI.
    * [Gson](https://github.com/google/gson) para converter o JSON da API em objetos Kotlin.
* **Carregamento de Imagens:**
    * [Glide](https://github.com/bumptech/glide) para carregar e exibir as imagens dos Pokémon de forma eficiente.
* **Componentes de Arquitetura Android Jetpack:**
    * `ViewModel` para gerenciar o estado da UI.
    * `LiveData` para observar mudanças nos dados.
    * `Core SplashScreen` para a tela de entrada.

## 🌐 API

Este projeto utiliza a **[PokeAPI](https://pokeapi.co/)**, uma API pública e gratuita para dados de Pokémon.

## 🚀 Como Executar

1.  Clone este repositório: `git clone https://github.com/SEU-USUARIO/MiniPokedex.git`
2.  Abra o projeto no [Android Studio](https://developer.android.com/studio).
3.  Aguarde o Gradle sincronizar as dependências.
4.  Conecte um emulador ou dispositivo Android (API 24+).
5.  Clique em "Run" (▶).
