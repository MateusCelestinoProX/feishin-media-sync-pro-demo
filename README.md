# 📱 Feishin Media Sync Pro — Aplicativo Android Nativo (Portfolio Demo)

[![Deploy](https://img.shields.io/badge/Deploy-Vercel-black?style=for-the-badge&logo=vercel)](https://feishin-media-sync-pro-demo.vercel.app)

> **Simulador Online Live**: https://feishin-media-sync-pro-demo.vercel.app  
> **Demonstração & Release Oficial** do ecossistema mobile de sincronização e processamento audiófilo de alta performance desenvolvido por **Mateus Celestino Pro**.

---

## ⚠️ Natureza do Software: Exclusivamente Android Nativo (.APK)

> [!IMPORTANT]
> **Aviso de Escopo de Engenharia**: Este projeto foi arquitetado, implementado e compilado primariamente como um **aplicativo nativo para o sistema operacional Android (`.apk`)**.  
> A versão web acessível neste repositório atua como um **Simulador Interativo Live & Showcase** da interface Apple VisionOS Liquid Glass e dos 37 shaders acelerados por GPU, permitindo testes ágeis no navegador sem a necessidade de um emulador Android.

### 📥 Download Direto do APK de Produção:
* **Arquivo**: [`feishin-media-sync-pro-release.apk`](./feishin-media-sync-pro-release.apk)
* **Tamanho**: **5.4 MB**
* **Compatibilidade**: Android 9.0 (API 28) até Android 14+ (API 34)
* **Target Architecture**: Universal (ARM64, ARMv7, x86_64)

---

## ⚡ Recursos Nativos de Engenharia Android

1. **Direct Memory Governor (1 GB RAM Física Travada)**:
   - Alocação direta via subsistema Linux nativo (`SharedMemory`, `ashmem` e `memfd_create`).
   - Garante que o motor de streaming e sincronização retenha buffers imunes a *low-memory killer* do Android.
2. **Foreground Audio Service & MediaSessionCompat**:
   - Serviço contínuo em primeiro plano integrado com a Central de Notificações do Android e tela de bloqueio.
   - Controles de reprodução nativos, metadados ao vivo e botões de transporte.
3. **Janela Flutuante (Samsung Edge Panel)**:
   - Bubble flutuante no cantinho da tela com permissão `SYSTEM_ALERT_WINDOW`, animação de pulso e overlay sobre qualquer app.
4. **Motor Gráfico com 37 Shaders WebGL OGL**:
   - Renderização fluida a 60 FPS de efeitos visuais atmosféricos, partículas 3D, fluidos e refração de prisma.
5. **Topologia Tri-Direcional de Sincronização**:
   - Celular Local (Music + SnapTube) ➔ VPS Navidrome (Subsonic REST) ➔ Nuvem (Google Drive Backup).
   - Validação criptográfica de integridade via hashes SHA-256.

---

## 🛠️ Estrutura do Repositório

```
feishin-media-sync-pro-demo/
├── feishin-media-sync-pro-release.apk  # APK Oficial Release Compilado (5.4 MB)
├── index.html                         # Simulador Web Interativo (VisionOS Liquid Glass)
├── style.css                          # Estilos Liquid Glass translúcidos
├── app.js                             # Lógica de controle e renderização
├── demo-android-bridge.js             # Simulador de ponte nativa window.Android
├── demo-sync-ui.css / demo-sync-ui.js # Splash Reveal & Glassmorphism Modal
├── js/                                # 37 Shaders WebGL OGL e bibliotecas gráficas
├── android/                           # Projeto Android Nativo Gradle Completo
│   ├── app/src/main/java/             # Fontes Java (Services, Governor, NativeBridge)
│   └── app/src/main/res/              # Recursos e ícones adaptativos One UI
├── build_android_apk.sh               # Script de build automatizado do APK
└── package.json
```

---

## 🚀 Execução do Simulador Web

Para rodar o simulador web em seu ambiente de desenvolvimento:

```bash
# Python:
python3 -m http.server 3001

# Ou Node:
npx serve -l 3001
```

Acesse em `http://localhost:3001` para experimentar o simulador ou baixe o APK para testar diretamente em um smartphone Android.

---

**Engenharia & Arquitetura:** Mateus Celestino Pro  
**Identidade Oficial:** `Mateus Celestino <202405039459@alunos.estacio.br>`
