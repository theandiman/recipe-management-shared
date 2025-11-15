#!/bin/bash

# Version bump script for recipe-management-shared
# Usage: ./scripts/bump-version.sh [patch|minor|major]

set -e

# Default to patch if no argument provided
VERSION_TYPE="${1:-patch}"

# Validate version type
case $VERSION_TYPE in
    patch|minor|major)
        ;;
    *)
        echo "Error: Version type must be 'patch', 'minor', or 'major'"
        echo "Usage: $0 [patch|minor|major]"
        exit 1
        ;;
esac

echo "🔍 Getting current version..."
CURRENT_VERSION=$(node -p "require('./package.json').version")
echo "Current version: $CURRENT_VERSION"

# Parse version
IFS='.' read -ra VERSION_PARTS <<< "$CURRENT_VERSION"
MAJOR=${VERSION_PARTS[0]}
MINOR=${VERSION_PARTS[1]}
PATCH=${VERSION_PARTS[2]}

# Calculate new version
case $VERSION_TYPE in
    major)
        NEW_VERSION="$((MAJOR + 1)).0.0"
        ;;
    minor)
        NEW_VERSION="$MAJOR.$((MINOR + 1)).0"
        ;;
    patch)
        NEW_VERSION="$MAJOR.$MINOR.$((PATCH + 1))"
        ;;
esac

echo "📈 Bumping version from $CURRENT_VERSION to $NEW_VERSION"

# Update package.json
echo "📝 Updating package.json..."
npm version $NEW_VERSION --no-git-tag-version

# Update pom.xml
echo "📝 Updating pom.xml..."
sed -i.bak "s/<version>.*<\/version>/<version>$NEW_VERSION<\/version>/" pom.xml
rm pom.xml.bak

echo "✅ Version bumped successfully!"
echo ""
echo "Next steps:"
echo "1. Review changes: git diff"
echo "2. Test builds: npm run build"
echo "3. Commit changes: git add . && git commit -m 'chore: bump version to $NEW_VERSION'"
echo "4. Create tag: git tag v$NEW_VERSION"
echo "5. Push: git push origin main && git push origin v$NEW_VERSION"