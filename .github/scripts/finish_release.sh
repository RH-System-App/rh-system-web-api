#!/usr/bin/env bash
set -euo pipefail

# ------------------------------------------------------------------
# Script para finalizar a release:
# - Busca automaticamente a branch remota com prefixo 'release-'
# - Faz checkout dela
# - Remove '-SNAPSHOT' no pom.xml e faz commit nessa branch
# - Tenta merge na main com resolução automática de conflitos (favor "theirs")
#   * Se merge automático for bem-sucedido, continua release
#   * Caso contrário, abre PR para resolução manual
# ------------------------------------------------------------------

# 0. Verifica token de acesso (usado pelo GH CLI)
if [ -z "${GITHUB_TOKEN:-}" ]; then
  echo "❌ GITHUB_TOKEN não definido. Defina o token no workflow env." >&2
  exit 1
fi

# 1. Configura usuário Git para commits
git config user.name "github-actions[bot]"
git config user.email "github-actions[bot]@users.noreply.github.com"

# 2. Detecta branch de release remota
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

# 3. Definindo versão de release (remove -SNAPSHOT)
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout \
  | grep -Eo '[0-9]+\.[0-9]+\.[0-9]+' || true)
echo "🎯 Versão alvo: $VERSION"

# Ajusta pom para release
echo "✂️ Removendo '-SNAPSHOT' no pom.xml"
mvn versions:set -DnewVersion=$VERSION
mvn versions:commit
git add pom.xml
git commit -m "chore: set release version to $VERSION" || echo "Nada para commitar"
git push origin "$BRANCH"

echo "✅ pom.xml ajustado na branch $BRANCH"

# 4. Prepara main localmente
echo "⬇️ Sincronizando main..."
git fetch origin main
git checkout main
git reset --hard origin/main
echo "✅ main sincronizada"

# 5. Merge com resolução automática de conflitos
echo "🔀 Mesclando $BRANCH → main com estratégia -X theirs"
if git merge --no-ff -X theirs "$BRANCH" -m "Merge release $VERSION"; then
  echo "✅ Merge automático bem-sucedido"
  # Continue release
  RELEASE_OK=true
else
  echo "⚠️ Merge automático encontrou conflitos que não pôde resolver totalmente"
  RELEASE_OK=false
fi

# 6. Push main e criar tag/release se merge ok
echo "⬆️ Push main"
git push origin main || echo "Nothing to push"

echo "🏷️ Criando tag v$VERSION"
git tag -a "v$VERSION" -m "Release v$VERSION" || echo "Tag já existe"
git push origin "v$VERSION" || echo "Tag não enviada"

echo "🚀 Criando GitHub Release v$VERSION"
gh release create "v$VERSION" \
  --title "Release v$VERSION" \
  --notes "Release v$VERSION via script automatizado"

echo "✅ GitHub Release criada"

# 7. Se merge não ok, abre PR para resolução manual
if [ "$RELEASE_OK" = false ]; then
  echo "🔀 Abrindo PR para resolver conflitos manualmente"
  gh pr create \
    --base main \
    --head "$BRANCH" \
    --title "Finalize release $VERSION" \
    --body "⚠️ Merge automático de $BRANCH em main gerou conflitos. PR criada para resolução manual."
  echo "✅ Pull Request criada"
fi
