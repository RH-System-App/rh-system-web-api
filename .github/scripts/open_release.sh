#!/usr/bin/env bash
set -euo pipefail

# ------------------------------------------------------------------
# Script para abertura de release:
# 1. Lê versão do pom.xml (com -SNAPSHOT)
# 2. Remove -SNAPSHOT para formar X.Y.Z
# 3. Cria e faz push da branch release-X.Y.Z
# 4. Incrementa a versão na develop (minor+1, patch=0) com -SNAPSHOT
# ------------------------------------------------------------------

# Configura usuário Git
git config user.name "github-actions[bot]"
git config user.email "github-actions[bot]@users.noreply.github.com"

# Sincroniza develop
git fetch origin develop
git checkout develop
git reset --hard origin/develop

echo "✅ develop sincronizada"

# Extrai versão atual do pom.xml (x.y.z-SNAPSHOT)
RAW_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout | grep -Eo '^[0-9]+\.[0-9]+\.[0-9]+-SNAPSHOT' | head -n1)
if [ -z "$RAW_VERSION" ]; then
  echo "❌ Falha ao extrair versão SNAPSHOT do pom.xml" >&2
  exit 1
fi

echo "🔍 Versão bruta: $RAW_VERSION"
# Remove sufixo -SNAPSHOT
VERSION=${RAW_VERSION%-SNAPSHOT}
echo "🎯 Versão limpa para release: $VERSION"

# Cria release branch
RELEASE_BRANCH="release-$VERSION"

echo "🚀 Criando branch $RELEASE_BRANCH"
git checkout -b "$RELEASE_BRANCH"
git push origin "$RELEASE_BRANCH"

echo "✅ Branch $RELEASE_BRANCH criada e enviada"

# Bump develop version: minor+1, patch=0, com SNAPSHOT
IFS='.' read -r MAJOR MINOR PATCH <<< "$VERSION"
NEW_MINOR=$((MINOR + 1))
NEXT_VERSION="$MAJOR.$NEW_MINOR.0-SNAPSHOT"

echo "🔄 Atualizando develop para $NEXT_VERSION"
mvn versions:set -DnewVersion="$NEXT_VERSION"
mvn versions:commit || true

git checkout develop
git add pom.xml
git commit -m "chore: bump develop version to $NEXT_VERSION" || echo "No changes to commit"
git push origin develop

echo "✅ develop atualizado para $NEXT_VERSION"
