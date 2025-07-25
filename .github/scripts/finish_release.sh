#!/usr/bin/env bash
set -euo pipefail

# ------------------------------------------------------------------
# Script para finalizar a release:
# - Busca automaticamente a branch remota com prefixo 'release-'
# - Faz checkout dela
# - Remove '-SNAPSHOT' no pom.xml e faz commit nessa branch
# - Faz merge na main, cria tag e GitHub Release
# - Se houver conflitos, abre PR automático via gh CLI
# ------------------------------------------------------------------

# 0. Verifica token de acesso (usado pelo GH CLI)
if [ -z "${GITHUB_TOKEN:-}" ]; then
  echo "❌ GITHUB_TOKEN não definido. Defina o token no workflow env." >&2
  exit 1
fi

# 1. Configura usuário Git para commits
git config user.name "github-actions[bot]"
git config user.email "github-actions[bot]@users.noreply.github.com"

# 2. Buscar e detectar a branch de release remota
echo "🔍 Buscando branches remotas de release..."
git fetch --all --prune
BRANCH=$(git for-each-ref --format='%(refname:short)' refs/remotes/origin \
  | grep -E '^origin/release-' \
  | sed 's#^origin/##' \
  | head -n1 | xargs)
if [ -z "$BRANCH" ]; then
  echo "❌ Nenhuma branch remota começando com 'release-' encontrada." >&2
  exit 1
fi

echo "🚀 Fazendo checkout da branch: $BRANCH"
git checkout -B "$BRANCH" "origin/$BRANCH"

# 3. Remover '-SNAPSHOT' no pom.xml na branch de release
echo "✂️ Removendo '-SNAPSHOT' do pom.xml na branch $BRANCH"
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout \
  | grep -Eo '[0-9]+\.[0-9]+\.[0-9]+' || true)
echo "🎯 Versão alvo: $VERSION"

mvn versions:set -DnewVersion=$VERSION
mvn versions:commit

git add pom.xml
git commit -m "chore: set release version to $VERSION" || echo "Nenhuma mudança no pom.xml"
git push origin "$BRANCH"

echo "✅ Pom ajustado na branch $BRANCH"

# 4. Sincroniza main localmente
echo "⬇️ Sincronizando main..."
git fetch origin main
git checkout main
git reset --hard origin/main
echo "main sincronizada"

# 5. Merge da release em main
echo "🔀 Tentando merge $BRANCH → main"
if git merge --no-ff "$BRANCH" -m "Merge release $VERSION"; then
  echo "✅ Merge bem-sucedido"

  # 6. Push main e cria tag
  git push origin main
  git tag -a "v$VERSION" -m "Release v$VERSION"
  git push origin "v$VERSION"
  echo "🏷️ Tag v$VERSION criada"

  # 7. Cria GitHub Release via gh CLI
  echo "🚀 Criando GitHub Release v$VERSION"
  gh release create "v$VERSION" \
    --title "Release v$VERSION" \
    --notes "Release v$VERSION via script automatizado"
  echo "✅ Release criada"
else
  echo "⚠️ Conflito detectado ao mesclar $BRANCH"

  # 8. Cria PR para resolução manual
echo "🚀 Criando Pull Request para $BRANCH → main"
  gh pr create \
    --base main \
    --head "$BRANCH" \
    --title "Finalize release $VERSION" \
    --body "⚠️ Conflito ao mesclar $BRANCH em main. PR criada para resolução manual."
  echo "✅ Pull Request criada"
fi
