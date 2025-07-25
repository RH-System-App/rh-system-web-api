#!/usr/bin/env bash
set -euo pipefail

# ------------------------------------------------------------------
# Script para finalizar a release:
# - Faz merge da branch atual (release-x.y.z) em main
# - Cria tag vX.Y.Z
# - Cria GitHub Release
# - Se houver conflito, abre PR automático via gh CLI
# ------------------------------------------------------------------

# 1. Detecta branch de release
RELEASE_BRANCH=$(git rev-parse --abbrev-ref HEAD)
echo "🔍 Branch de release detectada: $RELEASE_BRANCH"

# 2. Extrai versão do pom.xml (sem -SNAPSHOT)
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout | grep -Eo '[0-9]+\.[0-9]+\.[0-9]+')
echo "🎯 Versão alvo para release: $VERSION"

# 3. Configura usuário Git
git config user.name "github-actions[bot]"
git config user.email "github-actions[bot]@users.noreply.github.com"

# 4. Prepara main local
git fetch origin main
git checkout main
git reset --hard origin/main

echo "⬇️ main sincronizada com origin/main"

# 5. Tenta merge hardfast-forward
if git merge --no-ff "$RELEASE_BRANCH" -m "Merge release $VERSION"; then
  echo "✅ Merge bem-sucedido: $RELEASE_BRANCH → main"
  git push origin main

  # 6. Tag e Release
  git tag -a "v$VERSION" -m "Release v$VERSION"
  git push origin "v$VERSION"

  echo "🏷️ Tag v$VERSION criada"

  # 7. Cria GitHub Release via gh CLI
  echo "🚀 Criando GitHub Release v$VERSION"
  echo "$GITHUB_TOKEN" | gh auth login --with-token
  gh release create "v$VERSION" \
    --title "Release v$VERSION" \
    --notes "Release automatizado v$VERSION via GitHub Actions"

  echo "✅ GitHub Release criada"
else
  echo "⚠️ Conflito detectado ao mesclar $RELEASE_BRANCH em main"

  # 8. Criar PR de forma automática
  echo "🔀 Abrindo Pull Request para resolução manual"
  echo "$GITHUB_TOKEN" | gh auth login --with-token
  gh pr create \
    --base main \
    --head "$RELEASE_BRANCH" \
    --title "Finalize release $VERSION" \
    --body "⚠️ Conflito ao tentar mesclar $RELEASE_BRANCH em main. Esta PR foi criada para resolver o merge manualmente."

  echo "✅ Pull Request criada"
fi
