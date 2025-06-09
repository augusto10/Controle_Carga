# Controle_Carga

Aplicativo Android para gestão de notas fiscais e manifestos de carga.

## Funcionalidades

- Leitura de notas fiscais via código de barras
- Armazenamento local de dados
- Controle de cargas com múltiplas notas fiscais
- Geração de manifesto em PDF
- Assinatura digital do manifesto
- Compartilhamento via email e WhatsApp
- Impressão direta via Bluetooth

## Tecnologias

- Kotlin
- Jetpack Compose
- Room Database
- Hilt
- ZXing
- iText
- Coroutines

## Configuração

1. Clone o repositório:
```bash
git clone https://github.com/augusto10/Controle_Carga.git
```

2. Abra o projeto no Android Studio

3. Configure as dependências:
```gradle
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
```

4. Execute o projeto:
- Configure um emulador Android
- Ou conecte um dispositivo físico via USB

## Permissões Necessárias

- Câmera (para escanear códigos de barras)
- Armazenamento (para salvar PDFs)
- Bluetooth (para impressão)

## Licença

MIT License
