# Recipe Management Shared Models - Copilot Instructions

## Repository Overview

This is a **dual-language shared model library** that provides consistent data structures for the Recipe Management platform. The library exports identical models in both TypeScript (for frontend) and Java (for backend services).

### Key Services Using This Library
- **Frontend** (React/TypeScript)
- **AI Service** (Spring Boot/Java)
- **Storage Service** (Spring Boot/Java)

### Core Purpose
Ensure consistent field names, types, and structures across all Recipe Management platform services to prevent integration issues.

## Architecture

### Dual-Language Design
- **TypeScript models** are in `src/types/` - exported via `src/index.ts`
- **Java models** are in `src/main/java/com/recipe/shared/model/`
- Both language implementations must stay synchronized

### Critical Consistency Rules
1. Field names must match exactly between TypeScript and Java (use `@JsonProperty` in Java when needed)
2. Both implementations must support the same data transformations
3. Changes to one language must be mirrored in the other

## Coding Standards

### TypeScript
- Use **strict TypeScript** (`strict: true` in tsconfig.json)
- Export all types and interfaces from `src/index.ts`
- Use `interface` for data models (not `type` unless necessary)
- Follow existing naming: `camelCase` for fields
- Include JSDoc comments for all public interfaces and methods
- Target: ES2020

### Java
- Use **Java 21** language features
- Use **Lombok** annotations: `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`
- Use **Jackson** annotations for JSON serialization: `@JsonProperty`, `@JsonIgnore`
- Use **Jakarta Validation** annotations when validation is needed
- Follow existing naming: `camelCase` for fields (consistent with TypeScript)
- Include Javadoc comments for all public classes and methods
- Package structure: `com.recipe.shared.model` for models, `com.recipe.shared.schema` for schema utilities

### Naming Conventions
Based on existing code patterns:
- Boolean fields use `isPublic` pattern (not just `public`)
- Recipe name field: `recipeName` (not `title` or `name`)
- Time fields: `prepTimeMinutes`, `cookTimeMinutes`, `totalTimeMinutes` (integer minutes)
- Human-readable time: `prepTime`, `cookTime`, `totalTime` (string format like "15 minutes")
- Nutritional info: use structured `NutritionalInfo` object (not a raw Map)
- Tips: use structured `RecipeTips` object (not a raw Map)

### Documentation Requirements
- All fields in models should have inline comments explaining their purpose
- Include migration notes when field names or structures change from previous versions
- Document any cross-language compatibility considerations

## Build, Test, and Validation

### Prerequisites
- **Node.js**: 20.x
- **Java**: 21
- **Maven**: 3.x (included via wrapper)

### Installation
```bash
npm ci  # Install Node.js dependencies (not npm install)
```

### Build Commands
```bash
# Build TypeScript only
npm run build:ts

# Build Java only  
npm run build:java

# Build both (recommended)
npm run build

# Clean build artifacts
npm run clean
```

### Test Commands
```bash
# Run TypeScript tests only
npm run test:ts

# Run Java tests only
npm run test:java

# Run all tests (both TypeScript and Java)
npm run test
```

### Testing Practices
- Use **Jest** for TypeScript tests (test files in `src/__tests__/`)
- Use **JUnit 5** (jupiter) for Java tests
- Test file pattern: `**/__tests__/**/*.test.ts` for TypeScript
- Java tests in: `src/test/java/`
- All new functionality requires tests in **both** languages
- Utility functions should have comprehensive unit tests
- Test data transformations and cross-language compatibility

### Validation Steps Before PR
1. Run `npm run test` - ensure all tests pass
2. Run `npm run build` - ensure both TypeScript and Java compile
3. Verify build artifacts exist:
   - TypeScript: `dist/` directory with `.js` and `.d.ts` files
   - Java: `target/classes/` directory with `.class` files
4. For model changes: manually verify field parity between TypeScript and Java
5. Update README.md if public API changes

## Common Task Flows

### Adding a New Field to Recipe Model

**Acceptance Criteria:**
- Field added to both TypeScript and Java models
- Field names and types match between languages
- Inline comments explain field purpose
- Tests updated in both languages
- README.md updated with field description
- Backward compatibility maintained (field should be optional if possible)

**Steps:**
1. Add field to `src/types/recipe.ts` TypeScript interface
2. Add corresponding field to `src/main/java/com/recipe/shared/model/Recipe.java`
3. Use `@JsonProperty` in Java if name differs from Java conventions
4. Add inline comment in both files
5. Update existing tests or add new tests in both languages
6. Update README.md "Key Fields" section
7. Run full test suite: `npm run test`
8. Build everything: `npm run build`

### Adding a New Model/Interface

**Acceptance Criteria:**
- Model created in both TypeScript and Java
- Model exported from `src/index.ts` (TypeScript)
- Tests exist for both implementations
- README.md updated if model is part of public API
- Model follows naming conventions

**Steps:**
1. Create TypeScript interface in `src/types/`
2. Create Java class in `src/main/java/com/recipe/shared/model/`
3. Add Lombok annotations to Java: `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`
4. Export TypeScript interface from `src/index.ts`
5. Write tests in both languages
6. Document in README.md if applicable

### Fixing a Bug

**Acceptance Criteria:**
- Bug fix applied to both TypeScript and Java if applicable
- Test added to prevent regression
- Minimal changes - only fix the specific issue
- All existing tests still pass

**Steps:**
1. Write a failing test that demonstrates the bug
2. Fix the bug in TypeScript
3. Fix the bug in Java (if applicable)
4. Ensure all tests pass
5. Run full build to verify

### Updating Dependencies

**Acceptance Criteria:**
- Dependencies updated in appropriate lock file
- All tests pass after update
- No breaking changes introduced
- Security vulnerabilities addressed

**TypeScript Dependencies:**
```bash
# Update package.json and package-lock.json
npm install <package>@<version>
npm run test:ts
```

**Java Dependencies:**
```bash
# Update pom.xml
# Edit version in <dependencies> section
mvn clean test
```

## Version Management

- Version is maintained in both `package.json` and `pom.xml`
- Both files must have matching version numbers
- Use semantic versioning: MAJOR.MINOR.PATCH

## Publishing

**Before Publishing:**
1. Ensure version numbers match in `package.json` and `pom.xml`
2. All tests pass: `npm run test`
3. All builds succeed: `npm run build`
4. Update README.md with any changes
5. Create git tag for version

**TypeScript to npm:**
```bash
npm publish
```

**Java to Maven/GitHub Packages:**
```bash
mvn deploy
```

## Important Notes

### Do Not
- Don't break backward compatibility without major version bump
- Don't add fields that only exist in one language
- Don't modify working test files unless fixing a bug in tests
- Don't remove or comment out existing tests
- Don't use `npm install` - use `npm ci` for consistent installs
- Don't commit `node_modules/`, `dist/`, or `target/` directories (excluded via .gitignore)

### Always
- Keep TypeScript and Java models in sync
- Write tests for both languages when adding functionality
- Document field changes in inline comments
- Use consistent naming between languages
- Run the full test suite before committing
- Update README.md for public API changes

## File Structure

```
recipe-management-shared/
├── .github/
│   ├── workflows/          # CI/CD workflows
│   └── copilot-instructions.md  # This file
├── src/
│   ├── types/              # TypeScript models
│   │   └── recipe.ts       # Main Recipe interface
│   ├── index.ts            # TypeScript exports
│   ├── __tests__/          # TypeScript tests
│   ├── main/java/          # Java source
│   │   └── com/recipe/shared/
│   │       ├── model/      # Java models
│   │       └── schema/     # Schema utilities
│   └── test/java/          # Java tests
├── dist/                   # TypeScript build output (gitignored)
├── target/                 # Java build output (gitignored)
├── package.json            # Node.js dependencies and scripts
├── pom.xml                 # Maven dependencies and config
├── tsconfig.json           # TypeScript compiler config
├── jest.config.js          # Jest test config
└── README.md               # User-facing documentation
```

## CI/CD

GitHub Actions workflow (`.github/workflows/ci.yml`) runs on push and PR to main:
1. Setup Node.js 20 and Java 21
2. Install dependencies (`npm ci`)
3. Run TypeScript tests
4. Run Java tests  
5. Build both TypeScript and Java
6. Verify build artifacts exist

All checks must pass before merging.

## Schema Utilities (Java)

The repository includes schema generation utilities in `com.recipe.shared.schema` for converting Java models to JSON schemas compatible with Gemini AI. When working with these:
- `GeminiSchemaBuilder` converts models to Gemini format
- `JsonSchema` provides flexible JSON schema generation
- `RecipeSchema` generates Recipe-specific schemas
- These are primarily for AI service integration

## Getting Help

- Review existing models in `src/types/recipe.ts` and `src/main/java/com/recipe/shared/model/Recipe.java` as examples
- Check test files for usage examples: `src/__tests__/` and `src/test/java/`
- Refer to README.md for high-level architecture and migration guides
- Follow established patterns rather than introducing new approaches
