# Controle de Carga

[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/augusto10/Controle_Carga/blob/main/LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](https://developer.android.com)
[![Language](https://img.shields.io/badge/language-Kotlin-blue.svg)](https://kotlinlang.org)

Aplicativo Android para gestão completa de notas fiscais e manifestos de carga.

## 📱 Sobre o Projeto

O Controle de Carga é uma aplicação Android desenvolvida para facilitar o gerenciamento de notas fiscais e manifestos de carga. O app oferece uma solução completa para empresas de transporte e logística, permitindo o registro, controle e geração de documentos de forma eficiente.

## ✨ Funcionalidades Principais

- 📱 **Leitura de Notas Fiscais**: Escaneamento rápido de notas fiscais via código de barras
- 📦 **Controle de Cargas**: Gestão completa de controles de carga com múltiplas notas fiscais
- 📄 **Manifestos**: Geração de manifestos em PDF com todos os dados necessários
- ✍️ **Assinatura Digital**: Captura de assinatura digital diretamente no dispositivo
- 📧 **Compartilhamento**: Envio de documentos via email e WhatsApp
- 🖨️ **Impressão**: Impressão direta via Bluetooth

## 🛠️ Tecnologias Utilizadas

- **Kotlin**: Linguagem principal do projeto
- **Jetpack Compose**: Framework de UI moderno do Android
- **Room Database**: Banco de dados local para armazenamento
- **Hilt**: Injeção de dependências
- **ZXing**: Leitura de códigos de barras
- **iText**: Geração de PDFs
- **Coroutines**: Programação assíncrona

## 📋 Requisitos

- Android 5.0 (API 21) ou superior
- Permissões necessárias:
  - Câmera (para escanear códigos de barras)
  - Armazenamento (para salvar PDFs)
  - Bluetooth (para impressão)

## 🚀 Instalação

1. Clone o repositório:
```bash
git clone https://github.com/augusto10/Controle_Carga.git
```

2. Abra o projeto no Android Studio:
   - Abra o Android Studio
   - Clique em "Open an existing Android Studio project"
   - Selecione a pasta do projeto clonado

3. Configure o ambiente:
   - O Android Studio irá baixar as dependências automaticamente
   - Configure um emulador Android ou conecte um dispositivo físico

4. Execute o projeto:
   - Clique no botão "Run" (▶️) ou pressione Shift+F10

## 📱 Uso

1. **Criar um Novo Controle**:
   - Acesse a tela principal
   - Clique em "Novo Controle"
   - Preencha os dados do motorista e responsável
   - Salve o controle

2. **Escanear Notas Fiscais**:
   - Acesse a tela de escaneamento
   - Posicione o código de barras na tela
   - O app irá escanear automaticamente

3. **Associar Notas ao Controle**:
   - Selecione o controle desejado
   - Clique em "Selecionar NF-e"
   - Marque as notas que deseja associar
   - Confirme a seleção

4. **Gerar Manifesto**:
   - Selecione o controle com as notas associadas
   - Clique no botão de assinatura
   - Assine o manifesto digitalmente
   - O PDF será gerado automaticamente

## 🤝 Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## 📄 Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 📞 Contato

Para dúvidas ou sugestões, entre em contato através do email: [seuemail@email.com]
