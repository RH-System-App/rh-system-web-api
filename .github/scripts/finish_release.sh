# .github/scripts/finish_release.sh
#!/usr/bin/env bash
set -euo pipefail

# 1. Detecta branch de release
RELEASE_BRANCH=$(git rev-parse --abbrev-ref HEAD)
echo "🔍 Branch de release detectada: $RELEASE_BRANCH"

# 2. Extrai versão do pom.xml (sem -SNAPSHOT)
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout \
  | grep -Eo '[0-9]+\.[0-9]+\.[0-9]+')
echo "🎯 Versão alvo para release: $VERSION"

# 3. Configura usuário Git
git config user.name "github-actions[bot]"
git config user.email "github-actions[bot]@users.noreply.github.com"

# 4. Prepara main local
git fetch origin main
git checkout main
git reset --hard origin/main
echo "⬇️ main sincronizada com origin/main"

# 5. Tenta merge fast‑forward
if git merge --no-ff "$RELEASE_BRANCH" -m "Merge release $VERSION"; then
  echo "✅ Merge bem‑sucedido: $RELEASE_BRANCH → main"
  git push origin main

  # 6. Tag
  git tag -a "v$VERSION" -m "Release v$VERSION"
  git push origin "v$VERSION"
  echo "🏷️ Tag v$VERSION criada"

  # 7. Cria GitHub Release
  echo "🚀 Criando GitHub Release v$VERSION"
  # O GH CLI usa automaticamente a variável GH_TOKEN para autenticação em Actions
  gh release create "v$VERSION" \
    --title "Release v$VERSION" \
    --notes "Release automatizado v$VERSION via GitHub Actions" \
    --target main

  echo "✅ GitHub Release criada"

else
  echo "⚠️ Conflito detectado ao mesclar $RELEASE_BRANCH em main"
  echo "🔀 Abrindo Pull Request para resolução manual"
  gh pr create \
    --base main \
    --head "$RELEASE_BRANCH" \
    --title "Finalize release $VERSION" \
    --body "⚠️ Conflito ao tentar mesclar $RELEASE_BRANCH em main. Resolva manualmente neste PR."
  echo "✅ Pull Request criada"
fi
